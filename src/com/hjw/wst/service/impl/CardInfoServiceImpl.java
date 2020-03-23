package com.hjw.wst.service.impl;

import java.util.List;

import com.hjw.util.DateTimeUtil;
import com.hjw.util.StringUtil;
import com.hjw.wst.DTO.BatchDTO;
import com.hjw.wst.DTO.CardInfoDTO;
import com.hjw.wst.DTO.CardSaleDetailDTO;
import com.hjw.wst.DTO.CardSaleWayDTO;
import com.hjw.wst.DTO.ChargingItemDTO;
import com.hjw.wst.DTO.CollectFeesResult;
import com.hjw.wst.DTO.DataDictionaryDTO;
import com.hjw.wst.DTO.ExamSetDTO;
import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.wst.DTO.UserDTO;
import com.hjw.wst.config.GetNumContral;
import com.hjw.wst.domain.CardDeal;
import com.hjw.wst.domain.CardExamSet;
import com.hjw.wst.domain.CardExamSetItem;
import com.hjw.wst.domain.CardInfo;
import com.hjw.wst.domain.CardSaleDetail;
import com.hjw.wst.domain.CardSaleSummary;
import com.hjw.wst.domain.CardSaleWay;
import com.hjw.wst.domain.ChargingInvoiceSingle;
import com.hjw.wst.domain.CustomerMemberInfo;
import com.hjw.wst.model.CardInfoModel;
import com.hjw.wst.service.CardDealService;
import com.hjw.wst.service.CardInfoService;
import com.hjw.wst.service.CardManageService;
import com.synjones.framework.exception.ServiceException;
import com.synjones.framework.persistence.JdbcPersistenceManager;
import com.synjones.framework.persistence.JdbcQueryManager;
import com.synjones.framework.persistence.PersistenceManager;
import com.synjones.framework.persistence.QueryManager;
import com.synjones.framework.support.PageSupport;

public class CardInfoServiceImpl implements CardInfoService {
	private QueryManager qm;
	private JdbcQueryManager jqm;
	private JdbcPersistenceManager jpm;
	private PersistenceManager pm;
	private CardManageService cardManageService;
	private CardInfoService  cardInfoService;
	private CardDealService cardDealService;
	
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

	public void setCardManageService(CardManageService cardManageService) {
		this.cardManageService = cardManageService;
	}

	public void setCardInfoService(CardInfoService cardInfoService) {
		this.cardInfoService = cardInfoService;
	}

	public void setCardDealService(CardDealService cardDealService) {
		this.cardDealService = cardDealService;
	}

	@Override
	public CardInfoDTO getCardInfoByCardNum(UserDTO user,String cardNum) throws ServiceException{
		String sql = "select c.id,c.physical_num,c.card_num,c.card_pwd,c.card_type,c.status,c.amount,c.card_count,CONVERT(varchar(50),"
				+ "c.limit_card_count) as limit_card_count,CONVERT(varchar(100),c.deadline,23) as deadline,c.card_level,"
				+ "c.remark,d.data_name as card_level_y,c.member_id,ci.user_name member_name,c.sale_amount,c.company,c.discount "
				+ "from card_info c left join customer_member_info cm on c.member_id=cm.arch_num "
				+ "left join customer_info ci on ci.arch_num = c.member_id left join data_dictionary d "
				+ "on c.card_level=d.id where  "
				+ "c.card_num = '"+cardNum.trim()+"'  and c.center_num = '"+user.getCenter_num()+"'";
		List ls = this.jqm.getList(sql, CardInfoDTO.class);
		if(ls.size() != 0){
			return (CardInfoDTO) ls.get(0);
		}
		return null;
	}

	@Override
	public CardInfo getCardInfoById(String id) throws ServiceException{
		List ls = this.qm.find("from CardInfo where id = '"+id.trim()+"'");
		if(ls.size() != 0){
			return (CardInfo) ls.get(0);
		}
		return null;
	}

	@Override
	public CardInfo saveCardInfo(CardInfo cardInfo) throws ServiceException{
		this.pm.save(cardInfo);
		return cardInfo;
	}
	@Override
	public CardExamSet saveCardExamSet(CardExamSet cardExamSet) throws ServiceException{
		this.pm.save(cardExamSet);
		return cardExamSet;
	}
	@Override
	public CardExamSetItem saveCardExamSetItem(CardExamSetItem cardExamSetItem) throws ServiceException{
		this.pm.save(cardExamSetItem);
		return cardExamSetItem;
	}
	@Override
	public CardInfo updateCardInfo(CardInfo cardInfo) throws ServiceException{
		this.pm.update(cardInfo);
		return cardInfo;
	}

	@Override
	public PageReturnDTO cardInfoList(UserDTO user,String card_num,String status,String card_type,String card_level,String time1,String time2,String company,long hair_card_status,long hair_card_creater,int pagesize, int pageno) throws ServiceException {
		String sql = "select c.id,c.face_amount,c.card_num,c.card_type,c.status,c.amount,c.discount,c.company,c.card_count,"
				+ "CONVERT(varchar(50),c.limit_card_count) as limit_card_count,CONVERT(varchar(100),c.deadline,23) as deadline,"
				+ "d.data_name as card_level,c.remark,c.member_id,ci.user_name member_name,c.card_pwd,c.sale_amount,c.hair_card_status"
				+ ",u.chi_name as hair_card_creater,c.hair_card_create_time,u1.chi_name as creater,c.create_time from card_info c "
				+ "left join data_dictionary d on d.id = c.card_level left join customer_member_info cm on c.member_id=cm.arch_num"
				+ " left join customer_info ci on ci.arch_num = c.member_id "
				+ " left join user_usr u on u.id = c.hair_card_creater "
				+ " left join user_usr u1 on u1.id = c.creater "
				+ " where c.center_num ='"+user.getCenter_num()+"'";
		int count = 0;
		if(card_num != null && !card_num.equals("")){
			sql += " and c.card_num like '%"+card_num.trim()+"'";
			count++;
		}
		if(status != null && !status.equals("")){
			sql += " and c.status = '"+status.trim()+"'";
			count++;
		}
		if(card_type != null && !card_type.equals("")){
			sql += " and c.card_type = '"+card_type.trim()+"'";
			count++;
		}
		if(card_level != null && !card_level.equals("")){
			sql += " and c.card_level = '"+card_level.trim()+"'";
			count++;
		}
		if(time1 != null && !time1.equals("")){
			sql += " and c.create_time >= '"+time1+" 00:00:00.000'";
			count ++;
		}
		if(time2 != null && !time2.equals("")){
			sql += " and c.create_time <= '"+time2+" 23:59:59.000'";
			count ++;
		}
		if(company != null && !company.equals("")){
			sql += " and c.company like '"+company+"%'";
			count ++;
		}
		if(hair_card_status >= 0){
			sql += " and c.hair_card_status = '"+hair_card_status+"'";
			count ++;
		}
		if(hair_card_creater > 0){
			sql += " and c.hair_card_creater = '"+hair_card_creater+"'";
			count ++ ;
		}
		
		sql +=" order by c.company , c.card_num ,c.create_time  " ;
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

	public String saveCardBatch(CardInfoModel model,UserDTO user,String center_num) throws ServiceException {
		String num = model.getS_card_num();
		Integer cgCount = 0, sbCount = 0;
		String numstring = "";
		while(num.compareTo(model.getE_card_num()) <= 0){
			String sql = "SELECT count(*) FROM CardInfo where card_num ='"+num+"'";
			long count = (Long) this.qm.find(sql).iterator().next();
			if(count > 0 ){
				numstring += num+",";
				num = StringUtil.addOne(num);
				sbCount ++;
				continue;
			}
			CardInfo cardInfo = new CardInfo();
			cardInfo.setCard_pwd(model.getCard_pwd());
			cardInfo.setCard_num(num.toString());
			cardInfo.setCard_type(model.getCard_type());
			cardInfo.setAmount(model.getAmount());
			cardInfo.setLimit_card_count(model.getLimit_card_count());
			cardInfo.setCard_level(model.getCard_level());
			cardInfo.setDeadline(DateTimeUtil.parse0(model.getDeadline()));
			cardInfo.setRemark(model.getCard_remark());
			cardInfo.setStatus("1");
			cardInfo.setDiscount(model.getDiscount());
			cardInfo.setCompany(model.getCompany());
			cardInfo.setUpdater(user.getUserid());
			cardInfo.setUpdate_time(DateTimeUtil.parse());
			cardInfo.setCenter_num(user.getCenter_num());//体检中心编码
			cardInfo.setCreater(user.getUserid());
			cardInfo.setCreate_time(DateTimeUtil.parse());
			cardInfo.setHair_card_status(0);
			cardInfo.setSale_amount(model.getSale_amount());
			cardInfo.setIs_set_status(0l);
//			if(model.getTijiantaocan_id().equals("")) {
//				cardInfo.setIs_set_status(0l);
//			}else {
//				cardInfo.setIs_set_status(1l);
//				//保存卡信息对应套餐信息表
//				List<ExamSetDTO>  list =   cardInfoService.getijiantaocanlists(model.getTijiantaocan_id(),center_num);
//				CardExamSet cardExamSet = new CardExamSet();
//				cardExamSet.setCard_num(num.toString());
//				cardExamSet.setSet_num(list.get(0).getSet_num());
//				cardExamSet.setSet_amount(list.get(0).getSet_amount());
//				cardExamSet.setSet_name(list.get(0).getSet_name());
//				cardExamSet.setSet_discount(list.get(0).getSet_discount());
//				cardExamSet.setCenter_num(model.getExam_num());
//				cardExamSet.setCreater(user.getUserid());
//				cardExamSet.setCreate_time(DateTimeUtil.parse());
//				this.cardInfoService.saveCardExamSet(cardExamSet);
//				//保存卡信息对应套餐项目信息表
//				List<ExamSetDTO>  lists =   cardInfoService.getijiantaocanlistss(model.getTijiantaocan_id(),center_num);
//				for (int i = 0; i < lists.size(); i++) {
//					CardExamSetItem cardExamSetItem = new CardExamSetItem();
//					cardExamSetItem.setCard_num(num.toString());
//					cardExamSetItem.setCharging_item_code(lists.get(i).getItem_code());
//					cardExamSetItem.setSet_num(lists.get(i).getSet_num());
//					cardExamSetItem.setItemnum(lists.get(i).getItemnum());
//					cardExamSetItem.setDiscount(lists.get(i).getDiscount());
//					cardExamSetItem.setAmount(lists.get(i).getAmount());
//					cardExamSetItem.setItem_amount(lists.get(i).getItem_amount());
//					cardExamSetItem.setCreate_time(DateTimeUtil.parse());
//					cardExamSetItem.setCreater(user.getUserid());
//					saveCardExamSetItem(cardExamSetItem);
//				}
//			}
			if(cardInfo.getCard_type().equals("2")){
				cardInfo.setFace_amount(model.getAmount());
			}
			this.pm.save(cardInfo);
			CardDeal cardDeal = new CardDeal();
			
			cardDeal.setCard_num(cardInfo.getCard_num());
			cardDeal.setDeal_type("5");
			cardDeal.setAmount(cardInfo.getAmount());
			cardDeal.setCreater(user.getUserid());
			cardDeal.setDeal_date(DateTimeUtil.getDate2());
			cardDeal.setDeal_time(DateTimeUtil.parse());
			cardDeal.setOld_amount(0.00);
			cardDeal.setTrancode("001");
			this.cardDealService.saveCardDeal(cardDeal);
			num = StringUtil.addOne(num);
			cgCount ++;
		}
		if("".equals(numstring)){
			numstring = "无";
		}
		return "批量制卡成功，制卡成功"+cgCount+"个，制卡失败"+sbCount+"个。失败卡号:"+numstring+"已经存在!";
	}

	@Override
	public CardInfo getCardInfoByPhyNum(String phyNum) throws ServiceException {
		List ls = this.qm.find("from CardInfo c where c.physical_num = '"+phyNum+"'");
		if(ls.size() != 0){
			return (CardInfo) ls.get(0);
		}
		return null;
	}

	@Override
	public CardInfo getCardInfoByCarNum(String cardNum) throws ServiceException {
		List ls = this.qm.find("from CardInfo c where c.card_num = '"+cardNum+"'");
		if(ls.size() != 0){
			return (CardInfo) ls.get(0);
		}
		return null;
	}
	
	@Override
	public PageReturnDTO getDeleteCardList(UserDTO user,String starDate, String endDate, int pagesize, int pageno)
			throws ServiceException {
		String sql = "select c.face_amount,c.card_num,c.card_type,c.amount,CONVERT(varchar(100),c.deadline,23) as deadline,"
				   +" d.data_name as card_level,c.remark,c.member_id,ci.user_name member_name,c.card_pwd,"
				   +" CONVERT(varchar(100),c.create_time,23) as create_time,u.chi_name as creater from user_usr u,close_card c "
				   +" left join data_dictionary d on d.id = c.card_level left join customer_member_info cm on c.member_id=cm.arch_num"
				   +" left join customer_info ci on ci.arch_num = c.member_id"
				   +" where u.id = c.creater ";
		if(starDate != null && !starDate.equals("")){
			sql += " and c.create_time >= '"+starDate.trim()+" 00:00:00.000'";
		}
		if(endDate != null && !endDate.equals("")){
			sql += " and c.create_time < '"+endDate.trim()+" 23:59:59.999'";
		}
		
		PageSupport map = this.jqm.getList(sql, pageno, pagesize, CardInfoDTO.class);
		List mapList = map.getList();
		PageReturnDTO pageDto = new PageReturnDTO();
		pageDto.setPage(pageno);
		pageDto.setRp(pagesize);
		pageDto.setTotal(map.getRecTotal());
		pageDto.setRows(map.getList());
		return pageDto;
	}
	public void updateCard(CardInfoModel model) throws ServiceException {
		String hql = " from CardInfo c where c.card_num = '"+model.getCard_num()+"'";
		List<CardInfo> ls = this.qm.find(hql);
		ls.get(0).setDeadline(DateTimeUtil.parse0(model.getDeadline()));
		ls.get(0).setLimit_card_count(model.getLimit_card_count());
		ls.get(0).setRemark(model.getCard_remark());
		ls.get(0).setCompany(model.getCompany());
		ls.get(0).setDiscount(model.getDiscount());
		ls.get(0).setSale_amount(model.getSale_amount());
		this.pm.update(ls.get(0));
	}
	@Override
	public String saveHairCardInfo(String card_nums, long user_id) throws ServiceException {
		String[] card_num = card_nums.split(",");
		for (int i = 0; i < card_num.length; i++) {
			String sql = "update card_info set hair_card_status=1,hair_card_creater="+user_id+",hair_card_create_time='"+DateTimeUtil.getDateTime()+"' where card_num='"+card_num[i]+"'";
			this.jpm.executeSql(sql);
		}
		return "ok-保存发卡信息成功！";
	}

	@Override
	public String delHairCardInfo(String card_nums, long userid) throws ServiceException {
		String[] card_num = card_nums.split(",");
		for (int i = 0; i < card_num.length; i++) {
			String sql = "update card_info set hair_card_status=0,hair_card_creater="+null+",hair_card_create_time="+null+" where card_num='"+card_num[i]+"'";
			this.jpm.executeSql(sql);
		}
		return "ok-撤销发卡信息成功！";
	}
	
	@Override
	public String getCardInfoPREFIX(String card_level) throws ServiceException {
		String sql = "select d.remark from data_dictionary d where d.id = "+card_level;
		List<DataDictionaryDTO> list = this.jqm.getList(sql, DataDictionaryDTO.class);
		if(list.size() > 0){
			return list.get(0).getRemark();
		}
		return "";
	}

	@Override
	public CollectFeesResult saveRechargeInfo(CardInfoModel model, UserDTO user) throws ServiceException {
		CollectFeesResult result = new CollectFeesResult();
		String sale_trade_num = GetNumContral.getInstance().getParamNum("card_sale_num", user.getCenter_num());
		if(sale_trade_num == null || "".equals(sale_trade_num)){
			result.setUser_id(user.getUserid());
			result.setFlag("error");
			result.setInfo("请更新生成售卡交易流水号存储过程，参数：card_sale_num");
			return result;
		}
		ChargingInvoiceSingle chargingInvoiceSingle = null;
			List<CardInfo> cardlist = this.qm.find("from CardInfo c where c.card_num = '"+model.getCard_num()+"'");
			if(cardlist.size() > 0){
				CardInfo cardifo = cardlist.get(0);
				if(!"1".equals(cardifo.getStatus())){
					result.setUser_id(user.getUserid());
					result.setFlag("error");
					result.setInfo("卡号"+model.getCard_num()+"以被锁定，不能再充值!");
					return result;
				}
			}else{
				result.setUser_id(user.getUserid());
				result.setFlag("error");
				result.setInfo("卡号为"+model.getCard_num()+"不存在，请检查!");
				return result;
			}
		
			
			CardSaleSummary cardSaleSummary = new CardSaleSummary();
			if("Y".equals(model.getIsPrintRecepit())){//保存售卡发票记录
				chargingInvoiceSingle = new ChargingInvoiceSingle();
		
				chargingInvoiceSingle.setTitle_info(model.getTitle_info());
				chargingInvoiceSingle.setInvoice_num(model.getInvoice_num());
				chargingInvoiceSingle.setInvoice_type(model.getInvoice_type());
				chargingInvoiceSingle.setInvoice_status("Y");
				chargingInvoiceSingle.setInvoice_maker(user.getUserid());
				chargingInvoiceSingle.setInvoice_amount(model.getSale_amount());//实际金额
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
				cardDeal.setOld_amount(model.getAmount());
				cardDeal.setInvoice_class(chargingInvoiceSingle.getInvoice_class());
	            this.pm.save(cardDeal);
				
	            cardSaleSummary.setInvoice_id(chargingInvoiceSingle.getId());
			}
			
			//1售卡总记录表
			cardSaleSummary.setSale_trade_num(sale_trade_num);
			cardSaleSummary.setIs_print_recepit(model.getIsPrintRecepit());
			cardSaleSummary.setAmount(model.getC_amount());
			cardSaleSummary.setSale_amount(model.getSale_amount());
			cardSaleSummary.setSale_status(1);
			cardSaleSummary.setSale_type(1);
			cardSaleSummary.setDaily_status(0);
			
			cardSaleSummary.setSale_user(user.getUserid());
			cardSaleSummary.setSale_time(DateTimeUtil.parse());
			cardSaleSummary.setCenter_num(user.getCenter_num());
			this.pm.save(cardSaleSummary);  //保存售卡总记录表
			
			//2保存售卡明细
			CardSaleDetail cardSaleDetail = new CardSaleDetail();
			
			cardSaleDetail.setSale_trade_num(sale_trade_num);
			cardSaleDetail.setCard_num(model.getCard_num());
			cardSaleDetail.setAmount(model.getC_amount());
			cardSaleDetail.setSale_amount(model.getSale_amount());
			cardSaleDetail.setCreater(user.getUserid());
			cardSaleDetail.setCreate_time(DateTimeUtil.parse());
			this.pm.save(cardSaleDetail);  //保存售卡明细
				
			
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
		
		
		//会员信息
		CustomerMemberInfo cardMember = this.cardManageService.getCardMemberBymId(model.getMember_id());
		if(cardMember != null){
			cardMember.setPreintegral(cardMember.getIntegral());
			cardMember.setIntegeraltime(DateTimeUtil.parse());
			cardMember.setIntegral(cardMember.getIntegral()+model.getC_points());
			this.cardManageService.updateCardMember(cardMember);
		}
		//卡信息
		CardInfo cardInfo = this.cardInfoService.getCardInfoById(model.getCard_id());
		cardInfo.setAmount(model.getAmount()+model.getC_amount());
		cardInfo.setFace_amount(cardInfo.getFace_amount() + model.getC_amount());
		cardInfo.setUpdater(user.getUserid());
		cardInfo.setUpdate_time(DateTimeUtil.parse());
		this.cardInfoService.updateCardInfo(cardInfo);
		//卡流水明细信息
		CardDeal cardDeal = new CardDeal();
		cardDeal.setCard_num(model.getCard_num());
		cardDeal.setDeal_type("5");
		cardDeal.setAmount(model.getC_amount());
		cardDeal.setCreater(user.getUserid());
		cardDeal.setRemark(model.getCard_remark());
		cardDeal.setDeal_date(DateTimeUtil.getDate2());
		cardDeal.setDeal_time(DateTimeUtil.parse());
		cardDeal.setOld_amount(model.getAmount());
		cardDeal.setTrancode("001");
		cardDeal.setExam_num(model.getExam_num());
		this.cardDealService.saveCardDeal(cardDeal);
		
		result.setReq_num(sale_trade_num);
		result.setUser_id(user.getUserid());
		result.setFlag("ok");
		result.setInfo("充值成功。。");
		return result;
	}

	@Override
	public  List<ExamSetDTO> getijiantaocanlist(String center_num) throws ServiceException {
		String sql = "select set_num,set_name from exam_set where center_num='"+center_num+"'";
		List<ExamSetDTO> list = this.jqm.getList(sql, ExamSetDTO.class);
		return list;
	}
	@Override
	public  List<ExamSetDTO> getijiantaocanlists(String setName,String center_num) throws ServiceException {
		String sql = "select a.set_num,a.set_name,a.set_amount,a.set_discount,c.exam_num from  examinfo_set as b " + 
				"INNER  JOIN exam_set as a on a.id=b.exam_set_id " + 
				"INNER JOIN exam_info as c on c.id=b.examinfo_id " + 
				"where a.set_name='"+setName+"' and a.center_num='"+center_num+"'";
		List<ExamSetDTO> list = this.jqm.getList(sql, ExamSetDTO.class);
		return list;
	}
	@Override
	public  List<ExamSetDTO> getijiantaocanlistss(String setName,String center_num) throws ServiceException {
		String sql = "select c.item_code,b.set_num,a.itemnum,a.discount,a.amount,a.item_amount from set_charging_item as a " + 
				"INNER JOIN  exam_set as b on b.id=a.exam_set_id " + 
				"INNER JOIN charging_item as c on c.item_code=a.charging_item_code " + 
				"where b.set_name='"+setName+"' and b.center_num='"+center_num+"'";
		List<ExamSetDTO> list = this.jqm.getList(sql, ExamSetDTO.class);
		return list;
	}

	@Override
	public CardExamSet delCardExamSet(CardExamSet cardExamSetDel) throws ServiceException {
		this.pm.remove(cardExamSetDel);
		return cardExamSetDel;
	}

	@Override
	public CardExamSetItem delCardExamSetItem(CardExamSetItem cardExamSetItemdel) throws ServiceException {
		this.pm.remove(cardExamSetItemdel);
		return cardExamSetItemdel;
	}

	@Override
	public List<CardExamSet> selCardExamSetList(String card_num) throws ServiceException {
		String hql = "from CardExamSet where card_num = '"+card_num.trim()+"'";
		List<CardExamSet> cardExamSetList = this.qm.find(hql);
		return cardExamSetList;
	}

	@Override
	public List<CardExamSetItem> selCardExamSetItemList(String card_num) throws ServiceException {
		String hql = "from CardExamSetItem where card_num = '"+card_num.trim()+"'";
		List<CardExamSetItem> cardExamSetItemList = this.qm.find(hql);
		return cardExamSetItemList;
	}
	@Override
	public  List<BatchDTO> getCardBatchlists(String card_num,String center_num) throws ServiceException {
		String sql = "select top 1 b.company_id,b.batch_name,b.batch_num,b.id,d.com_name from card_info c left join  batch b  on b.company_id=c.company_id left join company_info d on c.company_id =d.id "
				+"where c.card_num='"+card_num+"' and (c.sale_status='1' or c.sale_status='2') and b.center_num='"+center_num+"' and b.apptype='1' and b.is_Active='Y' and d.is_Active='Y'  order by b.update_time desc ";
		List<BatchDTO> list = this.jqm.getList(sql, BatchDTO.class);
		return list;
	}
}
