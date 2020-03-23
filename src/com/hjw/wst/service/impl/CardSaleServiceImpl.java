package com.hjw.wst.service.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.hjw.util.DateTimeUtil;
import com.hjw.wst.DTO.CardInfoDTO;
import com.hjw.wst.DTO.CardSaleDetailDTO;
import com.hjw.wst.DTO.CardSaleSummaryDTO;
import com.hjw.wst.DTO.CardSaleWayDTO;
import com.hjw.wst.DTO.CollectFeesResult;
import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.wst.DTO.TreeDTO;
import com.hjw.wst.DTO.UserDTO;
import com.hjw.wst.config.GetNumContral;
import com.hjw.wst.domain.CardDeal;
import com.hjw.wst.domain.CardInfo;
import com.hjw.wst.domain.CardSaleDetail;
import com.hjw.wst.domain.CardSaleSummary;
import com.hjw.wst.domain.CardSaleWay;
import com.hjw.wst.domain.ChargingInvoiceSingle;
import com.hjw.wst.domain.CompanyInfo;
import com.hjw.wst.domain.NullifyInvoice;
import com.hjw.wst.model.CardSaleModel;
import com.hjw.wst.service.CardSaleService;
import com.synjones.framework.exception.ServiceException;
import com.synjones.framework.persistence.JdbcPersistenceManager;
import com.synjones.framework.persistence.JdbcQueryManager;
import com.synjones.framework.persistence.PersistenceManager;
import com.synjones.framework.persistence.QueryManager;
import com.synjones.framework.support.PageSupport;

public class CardSaleServiceImpl implements CardSaleService{
	private QueryManager qm;
	private JdbcQueryManager jqm;
	private JdbcPersistenceManager jpm;
	private PersistenceManager pm;
	
	public void setPersistenceManager(PersistenceManager pm) {
		this.pm = pm;
	}

	public void setQueryManager(QueryManager qm) {
		this.qm = qm;
	}

	public void setJdbcQueryManager(JdbcQueryManager jqm) {
		this.jqm = jqm;
	}

	public void setJdbcPersistenceManager(JdbcPersistenceManager jpm) {
		this.jpm = jpm;
	}

	@Override
	public PageReturnDTO getNotSaleCardInfoList(String card_num, String card_num_s, String card_num_e,
			String start_time, String end_time, String company, int pagesize, int pageno,UserDTO user) throws ServiceException {
		String sql = "select c.id,c.face_amount,c.card_num,c.card_type,c.status,c.amount,c.discount,c.company,c.card_count,"
				+ "CONVERT(varchar(50),c.limit_card_count) as limit_card_count,CONVERT(varchar(100),c.deadline,23) as deadline,"
				+ "d.data_name as card_level,c.remark,c.member_id,ci.user_name member_name,c.card_pwd,c.sale_amount,c.hair_card_status"
				+ ",u.chi_name as hair_card_creater,c.hair_card_create_time,u1.chi_name as creater,c.create_time from card_info c "
				+ "left join data_dictionary d on d.id = c.card_level left join customer_member_info cm on c.member_id=cm.arch_num"
				+ " left join customer_info ci on ci.arch_num = c.member_id "
				+ " left join user_usr u on u.id = c.hair_card_creater "
				+ " left join user_usr u1 on u1.id = c.creater "
				+ "where  c.sale_status = 0  and  c.center_num = '"+user.getCenter_num()+"'";
		int count = 0;
		if(card_num != null && !card_num.equals("")){
			sql += " and c.card_num like '%"+card_num.trim()+"'";
			count++;
		}
		if(card_num_s != null && !card_num_s.equals("") && card_num_e != null && !card_num_e.equals("")){
			sql += " and c.card_num >= '"+card_num_s.trim()+"' and c.card_num <= '"+card_num_e+"'";
			count++;
		}
		if(start_time != null && !start_time.equals("")){
			sql += " and c.create_time >= '"+start_time+" 00:00:00.000'";
			count ++;
		}
		if(end_time != null && !end_time.equals("")){
			sql += " and c.create_time <= '"+end_time+" 23:59:59.000'";
			count ++;
		}
		if(company != null && !company.equals("")){
			sql += " and c.company like '"+company+"%'";
			count ++;
		}
		sql +=" order by c.company , c.card_num ,c.create_time " ;
		PageReturnDTO pageDto = new PageReturnDTO();
		pageDto.setPage(pageno);
		pageDto.setRp(pagesize);
		if(count == 0){
			return pageDto;
		}
		PageSupport map = this.jqm.getList(sql, pageno, pagesize, CardInfoDTO.class);
		List mapList = map.getList();
		pageDto.setTotal(map.getRecTotal());
		pageDto.setRows(map.getList());
		return pageDto;
	}

	@Override
	public CollectFeesResult saveCardSaleInfo(CardSaleModel model, UserDTO user) throws ServiceException {
		CollectFeesResult result = new CollectFeesResult();
		String sale_trade_num = GetNumContral.getInstance().getParamNum("card_sale_num", user.getCenter_num());
		if(sale_trade_num == null || "".equals(sale_trade_num)){
			result.setUser_id(user.getUserid());
			result.setFlag("error");
			result.setInfo("请更新生成售卡交易流水号存储过程，参数：card_sale_num");
			return result;
		}
		ChargingInvoiceSingle chargingInvoiceSingle = null;
		List<CardSaleDetailDTO> listCardinfo = model.getCardSaleDetail();
		for(CardSaleDetailDTO item : listCardinfo){
			List<CardInfo> cardlist = this.qm.find("from CardInfo c where c.card_num = '"+item.getCard_num()+"'");
			if(cardlist.size() > 0){
				CardInfo cardifo = cardlist.get(0);
				if(cardifo.getSale_status() == 1){
					result.setUser_id(user.getUserid());
					result.setFlag("error");
					result.setInfo("卡号"+item.getCard_num()+"为已售卡，不能再次售卡!");
					return result;
				}
			}else{
				result.setUser_id(user.getUserid());
				result.setFlag("error");
				result.setInfo("卡号为"+item.getCard_num()+"不存在，请检查!");
				return result;
			}
		}
		
		CardSaleSummary cardSaleSummary = new CardSaleSummary();
		if("Y".equals(model.getIsPrintRecepit())){//保存售卡发票记录
			chargingInvoiceSingle = new ChargingInvoiceSingle();
	
			chargingInvoiceSingle.setTitle_info(model.getTitle_info());
			chargingInvoiceSingle.setInvoice_num(model.getInvoice_num());
			chargingInvoiceSingle.setInvoice_type(model.getInvoice_type());
			chargingInvoiceSingle.setInvoice_status("Y");
			chargingInvoiceSingle.setInvoice_maker(user.getUserid());
			chargingInvoiceSingle.setInvoice_amount(model.getSale_amount());
			chargingInvoiceSingle.setInvoice_time(DateTimeUtil.parse());
			chargingInvoiceSingle.setCreater(user.getUserid());
			chargingInvoiceSingle.setCreate_time(DateTimeUtil.parse());
			chargingInvoiceSingle.setUpdater(user.getUserid());
			chargingInvoiceSingle.setUpdate_time(DateTimeUtil.parse());
			chargingInvoiceSingle.setAccount_num(GetNumContral.getInstance().getParamNum("account_num", user.getCenter_num()));
			chargingInvoiceSingle.setExam_type("C");
			chargingInvoiceSingle.setCenter_num(user.getCenter_num());
			chargingInvoiceSingle.setInvoice_class(model.getInvoice_class());
			chargingInvoiceSingle.setDaily_status("0");
			chargingInvoiceSingle.setCharging_summary_num(sale_trade_num);
			this.pm.save(chargingInvoiceSingle);
			
			//保存发票记录表
			CardDeal cardDeal= new CardDeal();
			cardDeal.setAmount(model.getSale_amount());
			cardDeal.setCard_num(chargingInvoiceSingle.getInvoice_num());
			cardDeal.setTrancode("002");
			cardDeal.setCreater(user.getUserid());
			cardDeal.setDeal_type("0");
			cardDeal.setDeal_date(DateTimeUtil.getDate2());
			cardDeal.setDeal_time(DateTimeUtil.parse());
			cardDeal.setOld_amount(model.getSale_amount());
			cardDeal.setInvoice_class(chargingInvoiceSingle.getInvoice_class());
            this.pm.save(cardDeal);
			
            cardSaleSummary.setInvoice_id(chargingInvoiceSingle.getId());
		}
		//1售卡总记录表
		cardSaleSummary.setSale_trade_num(sale_trade_num);
		cardSaleSummary.setIs_print_recepit(model.getIsPrintRecepit());
		cardSaleSummary.setAmount(model.getAmount());
		cardSaleSummary.setSale_amount(model.getSale_amount());
		cardSaleSummary.setSale_status(model.getSale_status());
		cardSaleSummary.setSale_type(model.getSale_type());
		cardSaleSummary.setDaily_status(0);
		cardSaleSummary.setCenter_num(user.getCenter_num());//体检中心编码
		if(cardSaleSummary.getSale_status() == 1){
			cardSaleSummary.setSale_user(user.getUserid());
			cardSaleSummary.setSale_time(DateTimeUtil.parse());
		}else if(cardSaleSummary.getSale_status() == 0){
			cardSaleSummary.setAdvance_sale_user(user.getUserid());
			cardSaleSummary.setAdvance_sale_time(DateTimeUtil.parse());
		}
		this.pm.save(cardSaleSummary);  //保存售卡总记录表
		
		//2保存售卡明细
		for(CardSaleDetailDTO item : listCardinfo){
			CardSaleDetail cardSaleDetail = new CardSaleDetail();
			
			cardSaleDetail.setSale_trade_num(sale_trade_num);
			cardSaleDetail.setCard_num(item.getCard_num());
			cardSaleDetail.setAmount(item.getAmount());
			cardSaleDetail.setSale_amount(item.getSale_amount());
			cardSaleDetail.setCreater(user.getUserid());
			cardSaleDetail.setCreate_time(DateTimeUtil.parse());
			this.pm.save(cardSaleDetail);  //保存售卡明细
			
			List<CardInfo> cardlist = this.qm.find("from CardInfo c where c.card_num = '"+item.getCard_num()+"'");
			if(cardlist.size() > 0){
				CardInfo cardifo = cardlist.get(0);
				cardifo.setSale_amount(item.getSale_amount());
				cardifo.setSale_status(1);
				this.pm.update(cardifo); //修改售卡状态同步售卡金额
			}
		}
		
		//4收费方式
		List<CardSaleWayDTO> listway = model.getCharingWay();
		for(CardSaleWayDTO chargingWay : listway){
			CardSaleWay cardSaleWay = new CardSaleWay();
			
			cardSaleWay.setSale_trade_num(sale_trade_num);
			cardSaleWay.setAmount(chargingWay.getAmount());
			cardSaleWay.setCharging_way(chargingWay.getCharging_way());
			cardSaleWay.setCreater(user.getUserid());
			cardSaleWay.setCreate_time(DateTimeUtil.parse());
			this.pm.save(cardSaleWay);
		}
		
		result.setReq_num(sale_trade_num);
		result.setUser_id(user.getUserid());
		result.setFlag("ok");
		if(cardSaleSummary.getSale_status() == 1){
			result.setInfo("售卡成功！");
		}else{
			result.setInfo("预售卡成功！");
		}
		return result;
	}
	
	@Override
	public CollectFeesResult saveAdvCardSaleInfo(CardSaleModel model, UserDTO user) throws ServiceException {
		CollectFeesResult result = new CollectFeesResult();
		String sale_trade_num = GetNumContral.getInstance().getParamNum("card_sale_num", user.getCenter_num());
		if(sale_trade_num == null || "".equals(sale_trade_num)){
			result.setUser_id(user.getUserid());
			result.setFlag("error");
			result.setInfo("请更新生成售卡交易流水号存储过程，参数：card_sale_num");
			return result;
		}
		ChargingInvoiceSingle chargingInvoiceSingle = null;
		List<CardSaleDetailDTO> listCardinfo = model.getCardSaleDetail();
		for(CardSaleDetailDTO item : listCardinfo){
			List<CardInfo> cardlist = this.qm.find("from CardInfo c where c.card_num = '"+item.getCard_num()+"'");
			if(cardlist.size() > 0){
				CardInfo cardifo = cardlist.get(0);
				if(cardifo.getSale_status() == 1){
					result.setUser_id(user.getUserid());
					result.setFlag("error");
					result.setInfo("卡号"+item.getCard_num()+"为已售卡，不能再次售卡!");
					return result;
				}
			}else{
				result.setUser_id(user.getUserid());
				result.setFlag("error");
				result.setInfo("卡号为"+item.getCard_num()+"不存在，请检查!");
				return result;
			}
		}
		
		CardSaleSummary cardSaleSummary = new CardSaleSummary();
//		if("Y".equals(model.getIsPrintRecepit())){//保存售卡发票记录
//			chargingInvoiceSingle = new ChargingInvoiceSingle();
//	
//			chargingInvoiceSingle.setTitle_info(model.getTitle_info());
//			chargingInvoiceSingle.setInvoice_num(model.getInvoice_num());
//			chargingInvoiceSingle.setInvoice_type(model.getInvoice_type());
//			chargingInvoiceSingle.setInvoice_status("Y");
//			chargingInvoiceSingle.setInvoice_maker(user.getUserid());
//			chargingInvoiceSingle.setInvoice_amount(model.getSale_amount());
//			chargingInvoiceSingle.setInvoice_time(DateTimeUtil.parse());
//			chargingInvoiceSingle.setCreater(user.getUserid());
//			chargingInvoiceSingle.setCreate_time(DateTimeUtil.parse());
//			chargingInvoiceSingle.setUpdater(user.getUserid());
//			chargingInvoiceSingle.setUpdate_time(DateTimeUtil.parse());
//			chargingInvoiceSingle.setAccount_num(GetNumContral.getInstance().getParamNum("account_num"));
//			chargingInvoiceSingle.setExam_type("C");
//			chargingInvoiceSingle.setCenter_num(user.getCenter_num());
//			chargingInvoiceSingle.setInvoice_class(model.getInvoice_class());
//			chargingInvoiceSingle.setDaily_status("0");
//			chargingInvoiceSingle.setCharging_summary_num(sale_trade_num);
//			this.pm.save(chargingInvoiceSingle);
//			
//			//保存发票记录表
//			CardDeal cardDeal= new CardDeal();
//			cardDeal.setAmount(model.getSale_amount());
//			cardDeal.setCard_num(chargingInvoiceSingle.getInvoice_num());
//			cardDeal.setTrancode("002");
//			cardDeal.setCreater(user.getUserid());
//			cardDeal.setDeal_type("0");
//			cardDeal.setDeal_date(DateTimeUtil.getDate2());
//			cardDeal.setDeal_time(DateTimeUtil.parse());
//			cardDeal.setOld_amount(model.getSale_amount());
//			cardDeal.setInvoice_class(chargingInvoiceSingle.getInvoice_class());
//            this.pm.save(cardDeal);
//			
//            cardSaleSummary.setInvoice_id(chargingInvoiceSingle.getId());
//		}
		//1售卡总记录表
		cardSaleSummary.setSale_trade_num(sale_trade_num);
		cardSaleSummary.setIs_print_recepit(model.getIsPrintRecepit());
		cardSaleSummary.setAmount(model.getAmount());
		cardSaleSummary.setSale_amount(model.getSale_amount());
		cardSaleSummary.setSale_status(model.getSale_status());
		cardSaleSummary.setSale_type(model.getSale_type());
		cardSaleSummary.setDaily_status(0);
		cardSaleSummary.setCenter_num(user.getCenter_num());
		if(cardSaleSummary.getSale_status() == 1){
			cardSaleSummary.setSale_user(user.getUserid());
			cardSaleSummary.setSale_time(DateTimeUtil.parse());
		}else if(cardSaleSummary.getSale_status() == 0){
			cardSaleSummary.setAdvance_sale_user(user.getUserid());
			cardSaleSummary.setAdvance_sale_time(DateTimeUtil.parse());
		}
		this.pm.save(cardSaleSummary);  //保存售卡总记录表
		
		//2保存售卡明细
		for(CardSaleDetailDTO item : listCardinfo){
			CardSaleDetail cardSaleDetail = new CardSaleDetail();
			
			cardSaleDetail.setSale_trade_num(sale_trade_num);
			cardSaleDetail.setCard_num(item.getCard_num());
			cardSaleDetail.setAmount(item.getAmount());
			cardSaleDetail.setSale_amount(item.getSale_amount());
			cardSaleDetail.setCreater(user.getUserid());
			cardSaleDetail.setCreate_time(DateTimeUtil.parse());
			this.pm.save(cardSaleDetail);  //保存售卡明细
			
			List<CardInfo> cardlist = this.qm.find("from CardInfo c where c.card_num = '"+item.getCard_num()+"'");
			if(cardlist.size() > 0){
				CardInfo cardifo = cardlist.get(0);
				cardifo.setSale_amount(item.getSale_amount());
				cardifo.setSale_status(2);
				this.pm.update(cardifo); //修改售卡状态同步售卡金额
			}
		}
		
		//4收费方式
		List<CardSaleWayDTO> listway = model.getCharingWay();
		for(CardSaleWayDTO chargingWay : listway){
			CardSaleWay cardSaleWay = new CardSaleWay();
			
			cardSaleWay.setSale_trade_num(sale_trade_num);
			cardSaleWay.setAmount(chargingWay.getAmount());
			cardSaleWay.setCharging_way(chargingWay.getCharging_way());
			cardSaleWay.setCreater(user.getUserid());
			cardSaleWay.setCreate_time(DateTimeUtil.parse());
			this.pm.save(cardSaleWay);
		}
		
		result.setReq_num(sale_trade_num);
		result.setUser_id(user.getUserid());
		result.setFlag("ok");
		if(cardSaleSummary.getSale_status() == 1){
			result.setInfo("售卡成功！");
		}else{
			result.setInfo("预售卡成功！");
		}
		return result;
	}
	
	@Override
	public CollectFeesResult saveAdvanceCardSale(CardSaleModel model, UserDTO user) throws ServiceException {
		CollectFeesResult result = new CollectFeesResult();
		CardSaleSummary cardSaleSummary = null;
		List<CardSaleSummary> cardSaleSummarylist = this.qm.find("from CardSaleSummary where sale_trade_num = '"+model.getSale_trade_num()+"'");
		if(cardSaleSummarylist.size() <= 0){
			result.setUser_id(user.getUserid());
			result.setFlag("error");
			result.setInfo("预售卡信息不存在,请重新操作!");
			return result;
		}else{
			cardSaleSummary = cardSaleSummarylist.get(0);
			if(cardSaleSummary.getSale_status() == 1){
				result.setUser_id(user.getUserid());
				result.setFlag("error");
				result.setInfo("预售卡信息已确认收费,不能再次确认收费!");
				return result;
			}
		}
		ChargingInvoiceSingle chargingInvoiceSingle = null;
		
		if("Y".equals(model.getIsPrintRecepit())){//保存售卡发票记录
			chargingInvoiceSingle = new ChargingInvoiceSingle();
	
			chargingInvoiceSingle.setTitle_info(model.getTitle_info());
			chargingInvoiceSingle.setInvoice_num(model.getInvoice_num());
			chargingInvoiceSingle.setInvoice_type(model.getInvoice_type());
			chargingInvoiceSingle.setInvoice_status("Y");
			chargingInvoiceSingle.setInvoice_maker(user.getUserid());
			chargingInvoiceSingle.setInvoice_amount(model.getSale_amount());
			chargingInvoiceSingle.setInvoice_time(DateTimeUtil.parse());
			chargingInvoiceSingle.setCreater(user.getUserid());
			chargingInvoiceSingle.setCreate_time(DateTimeUtil.parse());
			chargingInvoiceSingle.setUpdater(user.getUserid());
			chargingInvoiceSingle.setUpdate_time(DateTimeUtil.parse());
			chargingInvoiceSingle.setAccount_num(GetNumContral.getInstance().getParamNum("account_num", user.getCenter_num()));
			chargingInvoiceSingle.setExam_type("C");
			chargingInvoiceSingle.setCenter_num(user.getCenter_num());
			chargingInvoiceSingle.setInvoice_class(model.getInvoice_class());
			chargingInvoiceSingle.setDaily_status("0");
			chargingInvoiceSingle.setCharging_summary_num(model.getSale_trade_num());
			this.pm.save(chargingInvoiceSingle);
			
			//保存发票记录表
			CardDeal cardDeal= new CardDeal();
			cardDeal.setAmount(model.getSale_amount());
			cardDeal.setCard_num(chargingInvoiceSingle.getInvoice_num());
			cardDeal.setTrancode("002");
			cardDeal.setCreater(user.getUserid());
			cardDeal.setDeal_type("0");
			cardDeal.setDeal_date(DateTimeUtil.getDate2());
			cardDeal.setDeal_time(DateTimeUtil.parse());
			cardDeal.setOld_amount(model.getSale_amount());
			cardDeal.setInvoice_class(chargingInvoiceSingle.getInvoice_class());
            this.pm.save(cardDeal);
			
            cardSaleSummary.setInvoice_id(chargingInvoiceSingle.getId());
		}
		//1售卡总记录表
		cardSaleSummary.setIs_print_recepit(model.getIsPrintRecepit());
		cardSaleSummary.setSale_status(1);
		cardSaleSummary.setSale_user(user.getUserid());
		cardSaleSummary.setSale_time(DateTimeUtil.parse());
		
		this.pm.update(cardSaleSummary);  //保存售卡总记录表
		
		//4收费方式
		List<CardSaleWayDTO> listway = model.getCharingWay();
		for(CardSaleWayDTO chargingWay : listway){
			CardSaleWay cardSaleWay = new CardSaleWay();
			
			cardSaleWay.setSale_trade_num(model.getSale_trade_num());
			cardSaleWay.setAmount(chargingWay.getAmount());
			cardSaleWay.setCharging_way(chargingWay.getCharging_way());
			cardSaleWay.setCreater(user.getUserid());
			cardSaleWay.setCreate_time(DateTimeUtil.parse());
			this.pm.save(cardSaleWay);
		}
		result.setReq_num(model.getSale_trade_num());
		result.setUser_id(user.getUserid());
		result.setFlag("ok");
		result.setInfo("确认售卡成功！");
		return result;
	}

	@Override
	public PageReturnDTO getCardSaleList(long sale_status, String start_time, String end_time, String sale_trade_num, String isPrintRecepit,
			int pagesize, int pageno,UserDTO user) throws ServiceException {
		int count = 0;
		String sql = "select s.sale_trade_num,s.sale_status,s.invoice_id,i.invoice_num,s.is_print_recepit,s.amount"
				+ ",s.sale_amount,s.sale_type,u.chi_name as sale_user,s.sale_time,u1.chi_name as advance_sale_user"
				+ ",s.advance_sale_time,(select COUNT(*) from card_sale_detail c where c.sale_trade_num = s.sale_trade_num) as card_info_count"
				+ " from card_sale_summary s left join charging_invoice_single i on i.id = s.invoice_id "
				+ "left join user_usr u on u.id = s.sale_user left join user_usr u1 on u1.id = s.advance_sale_user"
				+ " where  s.center_num = '"+user.getCenter_num()+"' and  s.sale_status = "+sale_status;
		if(sale_trade_num != null && !"".equals(sale_trade_num)){
			sql += " and s.sale_trade_num = '"+sale_trade_num+"'";
			count++;
		}
		if(start_time != null && !"".equals(start_time)){
			if(sale_status == 0){
				sql += " and s.advance_sale_time >= '"+start_time+" 00:00:00.000'";
			}else{
				sql += " and s.sale_time >= '"+start_time+" 00:00:00.000'";
			}
			count++;
		}
		if(end_time != null && !"".equals(end_time)){
			if(sale_status == 0){
				sql += " and s.advance_sale_time <= '"+end_time+" 23:59:59.000'";
			}else{
				sql += " and s.sale_time <= '"+end_time+" 23:59:59.000'";
			}
			count++;
		}
		if(isPrintRecepit != null && !"".equals(isPrintRecepit)){
			sql += " and s.is_print_recepit = '"+isPrintRecepit+"'";
			count++;
		}
		sql += " order by s.sale_trade_num";
		PageReturnDTO pageDto = new PageReturnDTO();
		pageDto.setPage(pageno);
		pageDto.setRp(pagesize);
		if(count == 0){
			return pageDto;
		}
		PageSupport map = this.jqm.getList(sql, pageno, pagesize, CardSaleSummaryDTO.class);
		List mapList = map.getList();
		pageDto.setTotal(map.getRecTotal());
		pageDto.setRows(map.getList());
		return pageDto;
	}

	@Override
	public List<CardInfoDTO> getCardSaleDetailList(String sale_trade_num,UserDTO user) throws ServiceException {
		String sql = "select c.id,c.face_amount,c.card_num,c.card_type,c.status,c.discount,c.company,c.card_count,"
				+ "CONVERT(varchar(50),c.limit_card_count) as limit_card_count,CONVERT(varchar(100),c.deadline,23) as deadline,"
				+ "d.data_name as card_level,c.remark,c.member_id,c.card_pwd,s.sale_amount,s.amount,c.sale_status "
				+ "from card_sale_detail s,card_info c left join data_dictionary d on d.id = c.card_level "
				+ "where c.center_num = '"+user.getCenter_num()+"' and  c.card_num = s.card_num and s.sale_trade_num = '"+sale_trade_num+"'";
		List<CardInfoDTO> list = this.jqm.getList(sql, CardInfoDTO.class);
		return list;
	}

	@Override
	public String invalidCardSaleInvoice(String sale_trade_num,UserDTO user) throws ServiceException {
		CardSaleSummary cardSaleSummary = null;
		List<CardSaleSummary> cardSaleSummarylist = this.qm.find("from CardSaleSummary where sale_trade_num = '"+sale_trade_num+"'");
		if(cardSaleSummarylist.size() <= 0){
			return "error-售卡信息不存在,请重新操作!";
		}else{
			cardSaleSummary = cardSaleSummarylist.get(0);
			if(cardSaleSummary.getIs_print_recepit().equals("N")){
				return "error-该售卡记录无发票信息，不能作废发票!";
			}
		}
		String sql = "from ChargingInvoiceSingle c where c.id = " + cardSaleSummary.getInvoice_id();
		List<ChargingInvoiceSingle> list = this.qm.find(sql);
		for (ChargingInvoiceSingle chargingInvoiceSingle : list) {
			chargingInvoiceSingle.setCancel_time(DateTimeUtil.parse());
			chargingInvoiceSingle.setCanceller(user.getUserid());
			
			this.pm.update(chargingInvoiceSingle);
			//保存发票记录表
			CardDeal cardDeal= new CardDeal();
			cardDeal.setAmount(chargingInvoiceSingle.getInvoice_amount());
			cardDeal.setCard_num(chargingInvoiceSingle.getInvoice_num());
			cardDeal.setTrancode("002");
			cardDeal.setCreater(user.getUserid());
			cardDeal.setDeal_type("1");
			cardDeal.setDeal_date(DateTimeUtil.getDate2());
			cardDeal.setDeal_time(DateTimeUtil.parse());
			cardDeal.setOld_amount(chargingInvoiceSingle.getInvoice_amount());
			cardDeal.setInvoice_class(chargingInvoiceSingle.getInvoice_class());
			//cardDeal.setExam_num(model.getExam_num());
	        this.pm.save(cardDeal);
	        
	        //保存作废发票流水表
            NullifyInvoice nullifyInvoice = new NullifyInvoice();
            nullifyInvoice.setInvoice_num(chargingInvoiceSingle.getInvoice_num());
            nullifyInvoice.setInvoice_status("N");
            nullifyInvoice.setInvoice_class(chargingInvoiceSingle.getInvoice_class());
            nullifyInvoice.setCreater(user.getUserid());
            nullifyInvoice.setCreate_time(DateTimeUtil.parse());
            nullifyInvoice.setDaily_status("0");
            nullifyInvoice.setAccount_num(GetNumContral.getInstance().getParamNum("account_num", user.getCenter_num()));
            nullifyInvoice.setCenter_num(user.getCenter_num());
            this.pm.save(nullifyInvoice);
		}
		cardSaleSummary.setInvoice_id(0);
		cardSaleSummary.setIs_print_recepit("N");
		this.pm.update(cardSaleSummary);
		return "ok-作废发票成功!";
	}

	@Override
	public CollectFeesResult saveCardSaleInvoice(CardSaleModel model, UserDTO user) throws ServiceException {
		CollectFeesResult result = new CollectFeesResult();
		CardSaleSummary cardSaleSummary = null;
		List<CardSaleSummary> cardSaleSummarylist = this.qm.find("from CardSaleSummary where sale_trade_num = '"+model.getSale_trade_num()+"'");
		if(cardSaleSummarylist.size() <= 0){
			result.setUser_id(user.getUserid());
			result.setFlag("error");
			result.setInfo("售卡信息不存在,请重新操作!");
			return result;
		}else{
			cardSaleSummary = cardSaleSummarylist.get(0);
			if(cardSaleSummary.getIs_print_recepit().equals("Y")){
				result.setUser_id(user.getUserid());
				result.setFlag("error");
				result.setInfo("该售卡记录已开发票信息，不能再次开发票!");
				return result;
			}
		}
		ChargingInvoiceSingle chargingInvoiceSingle = new ChargingInvoiceSingle();
		
		chargingInvoiceSingle.setTitle_info(model.getTitle_info());
		chargingInvoiceSingle.setInvoice_num(model.getInvoice_num());
		chargingInvoiceSingle.setInvoice_type(model.getInvoice_type());
		chargingInvoiceSingle.setInvoice_status("Y");
		chargingInvoiceSingle.setInvoice_maker(user.getUserid());
		chargingInvoiceSingle.setInvoice_amount(cardSaleSummary.getSale_amount());
		chargingInvoiceSingle.setInvoice_time(DateTimeUtil.parse());
		chargingInvoiceSingle.setCreater(user.getUserid());
		chargingInvoiceSingle.setCreate_time(DateTimeUtil.parse());
		chargingInvoiceSingle.setUpdater(user.getUserid());
		chargingInvoiceSingle.setUpdate_time(DateTimeUtil.parse());
		chargingInvoiceSingle.setAccount_num(GetNumContral.getInstance().getParamNum("account_num", user.getCenter_num()));
		chargingInvoiceSingle.setExam_type("C");
		chargingInvoiceSingle.setInvoice_class(model.getInvoice_class());
		chargingInvoiceSingle.setDaily_status("0");
		chargingInvoiceSingle.setCharging_summary_num(cardSaleSummary.getSale_trade_num());
		
		this.pm.save(chargingInvoiceSingle);
		//保存发票记录表
		CardDeal cardDeal= new CardDeal();
		cardDeal.setAmount(chargingInvoiceSingle.getInvoice_amount());
		cardDeal.setCard_num(chargingInvoiceSingle.getInvoice_num());
		cardDeal.setTrancode("002");
		cardDeal.setCreater(user.getUserid());
		cardDeal.setDeal_type("0");
		cardDeal.setDeal_date(DateTimeUtil.getDate2());
		cardDeal.setDeal_time(DateTimeUtil.parse());
		cardDeal.setOld_amount(chargingInvoiceSingle.getInvoice_amount());
		cardDeal.setInvoice_class(chargingInvoiceSingle.getInvoice_class());
        this.pm.save(cardDeal);
        
        cardSaleSummary.setInvoice_id(chargingInvoiceSingle.getId());
        cardSaleSummary.setIs_print_recepit("Y");
        this.pm.update(cardSaleSummary);
	
		result.setUser_id(user.getUserid());
		result.setAccount_num(chargingInvoiceSingle.getAccount_num());
		result.setFlag("ok");
		result.setInfo("补打发票成功");
		return result;
	}

	@Override
	public List<CompanyInfo> getCompanyList() throws ServiceException {
		String sql = "SELECT DISTINCT c.id,c.com_name,c.com_num,c.create_time "
				+ "FROM company_info c  WHERE c.is_Active='Y' ORDER BY  create_time desc";
		Connection connection = null;
		Statement statement = null;
		List<CompanyInfo> companyList = new ArrayList<CompanyInfo>();
		try {
			// 读取记录数
			connection = this.jqm.getConnection();
			statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			while (rs.next()) {
				CompanyInfo cl =new CompanyInfo();
				cl.setId(rs.getLong("id"));
				cl.setCom_Name(rs.getString("com_name"));
				cl.setCom_Num(rs.getString("com_num"));
				companyList.add(cl);
			}
			rs.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			try {
				if (statement != null) {
					statement.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException sqle4) {
				sqle4.printStackTrace();
			}
		}
				
		return companyList;
	}

	@Override
	public List<CardInfoDTO> getCardCompany(String sale_trade_num) throws ServiceException {
		String sql = "select ca.id,ca.card_num,ca.company_id,cs.sale_trade_num from card_info ca " + 
				"left join card_sale_detail cd on ca.card_num = cd.card_num " + 
				"left join card_sale_summary cs on cd.sale_trade_num = cs.sale_trade_num " + 
				"where cs.sale_trade_num = '"+sale_trade_num.trim()+"'";
		Connection connection = null;
		Statement statement = null;
		List<CardInfoDTO> CardInfoDTOList = new ArrayList<CardInfoDTO>();
		try {
			// 读取记录数
			connection = this.jqm.getConnection();
			statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			while (rs.next()) {
				CardInfoDTO cl =new CardInfoDTO();
				cl.setId(rs.getString("id"));
				cl.setCard_num(rs.getString("card_num"));
				cl.setCompany(rs.getString("company_id"));
				cl.setCompany_id(rs.getInt("company_id"));
				CardInfoDTOList.add(cl);
			}
			rs.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			try {
				if (statement != null) {
					statement.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException sqle4) {
				sqle4.printStackTrace();
			}
		}
		return CardInfoDTOList;
	}

	@Override
	public List<CardInfoDTO> getCardSaleDetailList2(String sale_trade_nums) throws ServiceException {
		String sql = "select c.id,c.face_amount,c.card_num,c.card_type,c.status,c.discount,c.company,c.card_count,"
				+ "CONVERT(varchar(50),c.limit_card_count) as limit_card_count,CONVERT(varchar(100),c.deadline,23) as deadline,"
				+ "d.data_name as card_level,c.remark,c.member_id,c.card_pwd,s.sale_amount,s.amount,c.sale_status "
				+ "from card_sale_detail s,card_info c left join data_dictionary d on d.id = c.card_level "
				+ "where  c.card_num = s.card_num and s.sale_trade_num in ("+sale_trade_nums+")";
		List<CardInfoDTO> list = this.jqm.getList(sql, CardInfoDTO.class);
		return list;
	}

}
