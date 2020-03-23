package com.hjw.wst.service.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.util.StringUtils;

import com.hjw.util.DateTimeUtil;
import com.hjw.util.IsPrivateCenterConfig;
import com.hjw.util.TranLogTxt;
import com.hjw.webService.client.FEESendMessage;
import com.hjw.webService.client.UNFEESendMessage;
import com.hjw.webService.client.Bean.Fee;
import com.hjw.webService.client.Bean.Fees;
import com.hjw.webService.client.Bean.ReqUnNo;
import com.hjw.webService.client.body.FeeMessage;
import com.hjw.webService.client.body.FeeReqBody;
import com.hjw.webService.client.body.FeeResultBody;
import com.hjw.webService.client.body.UnFeeMessage;
import com.hjw.wst.DTO.CardDealDTO;
import com.hjw.wst.DTO.CardExamSetItemDTO;
import com.hjw.wst.DTO.CardInfoDTO;
import com.hjw.wst.DTO.ChargingDetailSingleDTO;
import com.hjw.wst.DTO.ChargingInvoiceSingleDTO;
import com.hjw.wst.DTO.ChargingSummarySingleDTO;
import com.hjw.wst.DTO.ChargingWayDTO;
import com.hjw.wst.DTO.CollectFeesResult;
import com.hjw.wst.DTO.CompanyAccountDTO;
import com.hjw.wst.DTO.CompanyAccountDetailDTO;
import com.hjw.wst.DTO.CustomerExamDTO;
import com.hjw.wst.DTO.DataDictionaryDTO;
import com.hjw.wst.DTO.ExamInfoUserDTO;
import com.hjw.wst.DTO.ExamResultChargingItemDTO;
import com.hjw.wst.DTO.HisClinicItemPriceListDTO;
import com.hjw.wst.DTO.MenuDTO;
import com.hjw.wst.DTO.UserDTO;
import com.hjw.wst.DTO.UserFeeDTO;
import com.hjw.wst.DTO.UserInfoDTO;
import com.hjw.wst.DTO.WebserviceConfigurationDTO;
import com.hjw.wst.config.GetNumContral;
import com.hjw.wst.domain.CardDeal;
import com.hjw.wst.domain.CardInfo;
import com.hjw.wst.domain.ChargingDetailSingle;
import com.hjw.wst.domain.ChargingInvoiceSingle;
import com.hjw.wst.domain.ChargingItem;
import com.hjw.wst.domain.ChargingSummaryGroup;
import com.hjw.wst.domain.ChargingSummarySingle;
import com.hjw.wst.domain.ChargingWaySingle;
import com.hjw.wst.domain.CompanyAccount;
import com.hjw.wst.domain.CompanyAccountDetail;
import com.hjw.wst.domain.CompanyInfo;
import com.hjw.wst.domain.CustomerInfo;
import com.hjw.wst.domain.ExamInfo;
import com.hjw.wst.domain.ExamResultChargingItem;
import com.hjw.wst.domain.Examinatioin_center;
import com.hjw.wst.domain.ExaminfoChargingItem;
import com.hjw.wst.domain.NullifyInvoice;
import com.hjw.wst.domain.PacsSummary;
import com.hjw.wst.domain.Pacsdetail;
import com.hjw.wst.domain.SampleExamDetail;
import com.hjw.wst.domain.TeamInvoiceAccount;
import com.hjw.wst.domain.UserInvoice;
import com.hjw.wst.model.CollectFeesModel;
import com.hjw.wst.service.CollectFeesService;
import com.hjw.wst.service.CompanyService;
import com.hjw.wst.service.CustomerInfoService;
import com.hjw.wst.service.WebserviceConfigurationService;
import com.synjones.framework.exception.ServiceException;
import com.synjones.framework.persistence.JdbcPersistenceManager;
import com.synjones.framework.persistence.JdbcQueryManager;
import com.synjones.framework.persistence.PersistenceManager;
import com.synjones.framework.persistence.QueryManager;

public class CollectFeesServiceImpl implements CollectFeesService {
	private QueryManager qm;
	private JdbcQueryManager jqm;
	private JdbcPersistenceManager jpm;
	private PersistenceManager pm;
	private WebserviceConfigurationService webserviceConfigurationService;
	private CustomerInfoService customerInfoService;
	private CompanyService companyService;	
	
	public void setCompanyService(CompanyService companyService) {
		this.companyService = companyService;
	}

	public void setPersistenceManager(PersistenceManager pm) {
		this.pm = pm;
	}

	public QueryManager getQueryManager(){
		return this.qm;
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
	
	public void setWebserviceConfigurationService(WebserviceConfigurationService webserviceConfigurationService) {
		this.webserviceConfigurationService = webserviceConfigurationService;
	}

	public void setCustomerInfoService(CustomerInfoService customerInfoService) {
		this.customerInfoService = customerInfoService;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<ExamResultChargingItemDTO> getwitemList(String examNum,String is_fees_qijian,String center_num) throws ServiceException {
		String sql = "select c.item_discount,ec.id,c.id as item_id,c.item_code,c.item_name,d.dep_name,ec.exam_status,ec.item_amount,ec.discount,ec.amount,ec.itemnum,"
				   + "ec.personal_pay,ec.team_pay,u.chi_name as creater,CONVERT(varchar(100),ec.create_time,23) as create_time,ec.his_req_status,"
				   + " ec.calculation_amount,ec.calculation_rate "
				   + " from exam_info e,charging_item c,department_dep d,examinfo_charging_item ec left join user_usr u "
				   + "on u.id = ec.creater"
				   + " where ec.exam_num = e.exam_num and ec.charging_item_code = c.item_code and c.dep_id = d.id and ec.pay_status = 'N' "
				   + " and ec.isActive = 'Y' and ec.change_item <> 'C' and ec.center_num = '"+center_num+"' and e.is_Active='Y' and e.exam_num = '"+examNum+"'"
				   + " and ec.center_num = '"+center_num+"'";
		if("N".equals(is_fees_qijian)){
			sql += " and ec.exam_status <> 'G'";
		}
		sql += " order by ec.is_new_added desc,d.seq_code,c.item_seq";
		
		List<ExamResultChargingItemDTO> list = this.jqm.getList(sql, ExamResultChargingItemDTO.class);
		return list;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<ExamResultChargingItemDTO> getyitemList(String examNum,String center_num) throws ServiceException {
		String sql = "select ec.id,ec.exam_status,ec.personal_pay,ec.team_pay,c.id as item_id,c.item_code,c.item_name,d.dep_name,"
				+ "cs.amount as item_amount,cs.discount,cs.amount1 as amount,u.chi_name as creater,CONVERT(varchar(100),cs.create_time,23) as create_time,"
				+ "ec.calculation_amount,ec.calculation_rate "
				+ " from examinfo_charging_item ec,exam_info e,charging_item c,department_dep d,charging_summary_single s,"
				+ " charging_detail_single cs left join user_usr u on u.id = cs.creater"
				+ " where ec.exam_num = e.exam_num and ec.charging_item_code = c.item_code and c.dep_id = d.id and "
				+ " ec.charging_item_code = cs.charging_item_code and s.exam_num = ec.exam_num and s.id = cs.summary_id "
				+ " and ec.pay_status = 'Y' and ec.center_num = '"+center_num+"' and e.is_Active='Y' and e.exam_num = '"+examNum+"' and s.center_num = '"+center_num+"'"
				+ " and cs.id in (select max(css.id) from charging_detail_single css,charging_summary_single s where s.id = css.summary_id and s.exam_num = e.exam_num and s.center_num='"+center_num+"' group by css.charging_item_id)"
				+ " order by d.seq_code,c.item_seq";
		List<ExamResultChargingItemDTO> list = this.jqm.getList(sql, ExamResultChargingItemDTO.class);
		return list;
	}

	@Override
	@SuppressWarnings("unchecked")
	public CustomerExamDTO getCustomerInfo(String examNum,UserDTO user) throws ServiceException {
		String isprivateflag = IsPrivateCenterConfig.isprivateflag(user);//判断用户是否有隐私资源  Y 有 N无
		String sql = "select c.arch_num,e.exam_num,e.is_guide_back,e.id,e.wuxuzongjian,dbo.fun_CharToStar(e.exam_num,'name',c.user_name,'"+isprivateflag+"') as user_name,c.sex,e.age,"
				+ " (case when e.exam_type = 'G' then '个检' else e.company end) as company,ct.type_name as customer_type,"
				+ " dbo.GetExamSetNameByExamID(e.exam_num) as set_name,e.picture_path, "
				+ " e.past_medical_history,convert(varchar(50),e.join_date,23) as join_date,e.status,d.data_code_children as exam_type_code,"
				+ " d.data_name as exam_type,g.group_name,g.amount,dbo.fun_CharToStar(e.exam_num,'phone',e.phone,'"+isprivateflag+"') as phone,cev.vip_name as vipflag,e.getReportWay,e.reportAddress  "
				+ ",(select count(*) as num from exam_info where customer_id=e.customer_id and is_Active='Y' and join_date <= e.join_date ) exam_count"
				+ ",e.is_guide_date,d.data_name exam_types ,e.email ,ecd.id as ecd_id,e.customer_type_id"
				+ " from customer_info c,exam_info e left join customer_type ct on ct.id = e.customer_type_id "
				+ " left join data_dictionary d on d.id = e.customer_type "
				+ " left join group_info g on e.group_id=g.id "
				+ " left join config_exam_vip cev on cev.id=e.vipflag "
				+ " left join exam_Critical_detail  ecd on ecd.exam_num = e.exam_num "
				+ "where e.customer_id = c.id and e.is_Active='Y' and e.exam_num = '"+examNum+"' and e.center_num='"+user.getCenter_num()+"' order by e.id desc";
		List<CustomerExamDTO> list = this.jqm.getList(sql, CustomerExamDTO.class);
		if(list.size() >0){
			CustomerExamDTO ce=new CustomerExamDTO();
			ce=list.get(0);
			try{
				String IS_CUSTOM_IDENTIFICATION = this.customerInfoService.getCenterconfigByKey("IS_CUSTOM_IDENTIFICATION",user.getCenter_num()).getConfig_value();
				int vipsingin = com.hjw.util.StringUtil.getcheckStr(IS_CUSTOM_IDENTIFICATION,ce.getCustomer_type_id()+"");
				ce.setVipsigin(vipsingin);
			}catch(Exception ex){}
			return ce;
		}
		return null;
	}
	//
	@Override
	public CollectFeesResult saveCollectFees(CollectFeesModel model,UserDTO user,String invoice_class) throws ServiceException {
		
		ChargingSummarySingle chargingSummarySingle = new ChargingSummarySingle();
		CollectFeesResult result = new CollectFeesResult();
		
		for(ExamResultChargingItemDTO item : model.getExamInfoCharingItem()){
			ExaminfoChargingItem ec = (ExaminfoChargingItem) this.qm.load(ExaminfoChargingItem.class, item.getId());
			if(ec == null){
				result.setUser_id(user.getUserid());
				result.setFlag("error");
				result.setInfo("收费项目不存在,请先查询未检收费项目！");
				return result;
			}else if("N".equals(ec.getIsActive())){
				result.setUser_id(user.getUserid());
				result.setFlag("error");
				result.setInfo("存在已减项收费项目,请先查询未检收费项目！");
				return result;
			}
			if("Y".equals(ec.getPay_status())){
				result.setUser_id(user.getUserid());
				result.setFlag("error");
				result.setInfo("存在已收费项目,请先查询未检收费项目！");
				return result;
			}
		}
		String charging_summary_num = GetNumContral.getInstance().getParamNum("rcpt_num",user.getCenter_num());
		ChargingInvoiceSingle chargingInvoiceSingle = null;
		if("Y".equals(model.getIsPrintRecepit())){
			
			chargingInvoiceSingle = new ChargingInvoiceSingle();
	
			chargingInvoiceSingle.setTitle_info(model.getTitle_info());
			chargingInvoiceSingle.setInvoice_num(model.getInvoice_num());
			chargingInvoiceSingle.setInvoice_type(model.getInvoice_type());
			chargingInvoiceSingle.setInvoice_status("Y");
			chargingInvoiceSingle.setInvoice_maker(user.getUserid());
			chargingInvoiceSingle.setInvoice_amount(model.getAmount2());
			chargingInvoiceSingle.setInvoice_time(DateTimeUtil.parse());
			chargingInvoiceSingle.setCreater(user.getUserid());
			chargingInvoiceSingle.setCreate_time(DateTimeUtil.parse());
			chargingInvoiceSingle.setUpdater(user.getUserid());
			chargingInvoiceSingle.setUpdate_time(DateTimeUtil.parse());
			chargingInvoiceSingle.setAccount_num(GetNumContral.getInstance().getParamNum("account_num",user.getCenter_num()));
			chargingInvoiceSingle.setExam_type("G");
			chargingInvoiceSingle.setBarchid(model.getExam_id());
			chargingInvoiceSingle.setCenter_num(user.getCenter_num());
			chargingInvoiceSingle.setInvoice_class(invoice_class);
			chargingInvoiceSingle.setDaily_status("0");
			chargingInvoiceSingle.setCharging_summary_num(charging_summary_num);
			this.pm.save(chargingInvoiceSingle);
			
			//保存发票记录表
			CardDeal cardDeal= new CardDeal();
			cardDeal.setAmount(model.getAmount2());
			cardDeal.setCard_num(chargingInvoiceSingle.getInvoice_num());
			cardDeal.setTrancode("002");
			cardDeal.setCreater(user.getUserid());
			cardDeal.setDeal_type("0");
			cardDeal.setDeal_date(DateTimeUtil.getDate2());
			cardDeal.setDeal_time(DateTimeUtil.parse());
			cardDeal.setOld_amount(model.getAmount2());
			cardDeal.setInvoice_class(chargingInvoiceSingle.getInvoice_class());
			cardDeal.setExam_num(model.getExam_num());
            this.pm.save(cardDeal);
			
			chargingSummarySingle.setInvoice_id(chargingInvoiceSingle.getId());
		}
		//1收费总记录
		chargingSummarySingle.setExam_id(model.getExam_id());
		chargingSummarySingle.setReq_num(charging_summary_num);
		chargingSummarySingle.setCharging_status("Y");
		chargingSummarySingle.setAmount1(model.getAmount1());
		chargingSummarySingle.setAmount2(model.getAmount2());
		chargingSummarySingle.setDiscount(model.getDiscount());
		chargingSummarySingle.setCashier(user.getUserid());
		chargingSummarySingle.setCash_date(DateTimeUtil.parse());
		chargingSummarySingle.setIs_print_recepit(model.getIsPrintRecepit());
		chargingSummarySingle.setIs_active("Y");
		chargingSummarySingle.setCreater(user.getUserid());
		chargingSummarySingle.setCreate_time(DateTimeUtil.parse());
		chargingSummarySingle.setUpdater(user.getUserid());
		chargingSummarySingle.setUpdate_time(DateTimeUtil.parse());
		chargingSummarySingle.setDaily_status("0");
		chargingSummarySingle.setExam_num(model.getExam_num());
		chargingSummarySingle.setCenter_num(user.getCenter_num());
		
		this.pm.save(chargingSummarySingle);  //保存收费总表
		
		if(chargingInvoiceSingle != null){
			TeamInvoiceAccount team = new TeamInvoiceAccount();
			team.setAccount_num(chargingInvoiceSingle.getAccount_num());
			team.setAcc_num(chargingSummarySingle.getReq_num());
			team.setAudit_date(DateTimeUtil.parse());
			team.setAuditor(user.getUserid());
			team.setCenter_num(user.getCenter_num());
			this.pm.save(team);
		}
		
		List<ExamResultChargingItemDTO> listItem = model.getExamInfoCharingItem();
		
		//2保存结算明细
		for(ExamResultChargingItemDTO item : listItem){
			ChargingDetailSingle chargingDetailSingle = new ChargingDetailSingle();
			
			chargingDetailSingle.setSummary_id(chargingSummarySingle.getId());
			chargingDetailSingle.setAmount(item.getItem_amount());
			chargingDetailSingle.setAmount1(item.getPersonal_pay());
			chargingDetailSingle.setCharging_item_id(item.getItem_id());
			chargingDetailSingle.setDiscount(item.getDiscount());
			chargingDetailSingle.setCreater(user.getUserid());
			chargingDetailSingle.setCreate_time(DateTimeUtil.parse());
			chargingDetailSingle.setUpdater(user.getUserid());
			chargingDetailSingle.setUpdate_time(DateTimeUtil.parse());
			chargingDetailSingle.setCharging_item_code(item.getItem_code());
			this.pm.save(chargingDetailSingle);  //保存结算明细
			
			//3修改项目状态
			ExaminfoChargingItem ec = (ExaminfoChargingItem) this.qm.load(ExaminfoChargingItem.class, item.getId());
			ec.setPay_status("Y");
			ec.setAmount(item.getAmount());
			ec.setDiscount(item.getDiscount());
			ec.setPersonal_pay(item.getPersonal_pay());
			ec.setUpdater(user.getUserid());
			ec.setUpdate_time(DateTimeUtil.parse());
			this.pm.update(ec);//修改项目状态
		}
		
		boolean isCard = false;
		//4收费方式
		List<ChargingWayDTO> listway = model.getCharingWay();
		for(ChargingWayDTO chargingWay : listway){
			ChargingWaySingle chargingWaySingle = new ChargingWaySingle();
			
			chargingWaySingle.setSummary_id(chargingSummarySingle.getId());
			chargingWaySingle.setAmount(chargingWay.getAmount());
			chargingWaySingle.setCharging_way(chargingWay.getCharging_way());
			chargingWaySingle.setCreater(user.getUserid());
			chargingWaySingle.setCreate_time(DateTimeUtil.parse());
			chargingWaySingle.setUpdater(user.getUserid());
			chargingWaySingle.setUpdate_time(DateTimeUtil.parse());
			if(chargingInvoiceSingle != null){
				chargingWaySingle.setAccount_num(chargingInvoiceSingle.getAccount_num());
			}
			if(chargingWay.getCharging_way().equals("122")){
				isCard = true;
			}
			this.pm.save(chargingWaySingle);
		}
		
		if(isCard){//会员刷卡，保存卡消费信息
			List<CardInfoDTO> listCard = model.getCardInfo();
			for(CardInfoDTO cardInfo : listCard){
				CardDeal cardDeal = new CardDeal();
				
				cardDeal.setCard_num(cardInfo.getCard_num());
				cardDeal.setAmount(cardInfo.getAmount());
				cardDeal.setCard_count(cardInfo.getCard_count()+1);
				cardDeal.setSummary_id(chargingSummarySingle.getId());
				cardDeal.setExaminfo_id(model.getExam_id());
				cardDeal.setDeal_type("6");
				cardDeal.setCreater(user.getUserid());
				cardDeal.setDeal_date(DateTimeUtil.getDate2());
				cardDeal.setDeal_time(DateTimeUtil.parse());
				cardDeal.setOld_amount(cardInfo.getFace_amount());
				cardDeal.setTrancode("001");
				cardDeal.setExam_num(model.getExam_num());
				this.pm.save(cardDeal);
				
				List<CardInfo> cardlist = this.qm.find("from CardInfo c where c.card_num = '"+cardInfo.getCard_num()+"'");
				if(cardlist.size() > 0){
					CardInfo cardifo = cardlist.get(0);
					cardifo.setAmount(cardInfo.getFace_amount()-cardInfo.getAmount());
					cardifo.setCard_count(cardInfo.getCard_count()+1);
					this.pm.update(cardifo);
				}
			}
		}
		
		if(chargingInvoiceSingle != null){
			result.setAccount_num(chargingInvoiceSingle.getAccount_num());
		}
		result.setReq_num(chargingSummarySingle.getReq_num());
		result.setUser_id(user.getUserid());
		result.setFlag("ok");
		result.setInfo("收费成功！");
		return result;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<CardInfoDTO> getCardListByexamId(long exam_id,String center_num) throws ServiceException {
		String sql = "select c.id,c.card_num,c.card_type,c.status,c.amount,c.card_count,"
				+ "convert(varchar(50),c.limit_card_count) as limit_card_count,"
				+ "CONVERT(varchar(100),c.deadline,23) as deadline,d.data_name as card_level,"
				+ "c.remark,c.company from exam_info e,customer_info ci,card_info c left join data_dictionary d on d.id = c.card_level "
				+ "where c.member_id = ci.arch_num and ci.id = e.customer_id and e.id = "+exam_id +" and c.center_num = '"+center_num+"'";
		List<CardInfoDTO> list = this.jqm.getList(sql, CardInfoDTO.class);
		return list;
	}

	@Override
	@SuppressWarnings("unchecked")
	public CardInfoDTO getCardInfoByNum(String card_num,UserDTO user) throws ServiceException {
		String sql = "select c.id,c.card_num,c.card_pwd,c.card_type,c.status,c.amount,c.card_count,"
				+ "CONVERT(varchar(50),c.limit_card_count) as limit_card_count,"
				+ "CONVERT(varchar(100),c.deadline,23) as deadline,c.card_level,c.remark,c.company,"
				+ "d.data_name as card_level_y,c.member_id,cm.user_name as member_name,convert(varchar(100), GETDATE(),23) as get_date ,c.hair_card_status from card_info c "
				+ "left join customer_info cm on cm.arch_num=c.member_id left join data_dictionary d on c.card_level=d.id where "
				+ "c.card_num = '"+card_num.trim()+"' and  c.center_num = '"+user.getCenter_num()+"'";
		List<CardInfoDTO> list = this.jqm.getList(sql, CardInfoDTO.class);
		if(list.size() !=0){
			return list.get(0);
		}
		return null;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<ChargingSummarySingleDTO> getChargingSummary(String exam_num,String center_num) throws ServiceException {
		String sql = "select c.id,c.charging_status,(case when c.charging_status = 'Y' then c.amount2 when c.charging_status = 'R' then c.amount2 "
				+ " else -c.amount2 end) amount,c.discount,u.chi_name as cashier,c.cash_date,c.req_num, "
				+ "(select count(*) from charging_detail_single cd where cd.summary_id = c.id) as item_num"
				+ " from exam_info e,charging_summary_single c "
				+ " left join user_usr u on c.cashier = u.id where e.exam_num = c.exam_num and "
				+ " e.exam_num = '"+exam_num+"' and c.center_num = '"+center_num+"' order by c.id";
		List<ChargingSummarySingleDTO> list = this.jqm.getList(sql, ChargingSummarySingleDTO.class);
		for (ChargingSummarySingleDTO chargingSummarySingleDTO : list) {
			List<ChargingWayDTO> waylist = this.getChargingWay(chargingSummarySingleDTO.getId(),center_num);
			chargingSummarySingleDTO.setChargingWay(waylist);
			String sqlc = "select c.card_num,c.amount from card_deal c where c.trancode = '001' and c.summary_id = '"+chargingSummarySingleDTO.getId()+"'";
			List<CardDealDTO> cardeal = this.jqm.getList(sqlc, CardDealDTO.class);
			chargingSummarySingleDTO.setCard_deal(cardeal);
		}
		return list;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<ChargingDetailSingleDTO> getChargingDetail(long summaryId,String center_num) throws ServiceException {
		String sql = "select ci.item_name,ci.item_code,d.dep_name,c.amount as item_amount,"
				+ "(case when s.charging_status = 'Y' then c.amount1 when s.charging_status = 'R' then c.amount1 else -c.amount1 end) as amount,"
				+ "c.discount, CONVERT(varchar(100),c.create_time,23) as create_time,u.chi_name as creater "
				+ "from charging_item ci,department_dep d,charging_summary_single s, charging_detail_single c "
				+ "left join user_usr u on c.creater = u.id where c.charging_item_code = ci.item_code "
				+ "and ci.dep_id = d.id and s.id = c.summary_id and c.summary_id ="+summaryId 
				+ " and s.center_num = '"+center_num+"' order by d.seq_code,ci.item_seq";
		List<ChargingDetailSingleDTO> list = this.jqm.getList(sql, ChargingDetailSingleDTO.class);
		return list;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<ChargingWayDTO> getChargingWay(long summaryId,String center_num) throws ServiceException {
		String sql = "select c.id,d.data_name as charging_way,(case when s.charging_status = 'Y' then c.amount else -c.amount end) as amount,u.chi_name as creater,"
				   + " CONVERT(varchar(100),c.create_time,23) as create_time from charging_summary_single s, charging_way_single c "
				   + " left join data_dictionary d on c.charging_way = d.id left join user_usr u on c.creater = u.id "
				   + " where s.id = c.summary_id and s.center_num = '"+center_num+"' and c.summary_id = "+summaryId;
		List<ChargingWayDTO> list = this.jqm.getList(sql, ChargingWayDTO.class);
		return list;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<ChargingWayDTO> getChargingWayByExamNum(String exam_num,String req_nums,String center_num) throws ServiceException {
		String sql = "select d.id,d.data_name as charging_way,a.amount from data_dictionary d "
				+ "left join (select sum(case when c.charging_status = 'Y' then w.amount else -w.amount end) as amount,"
				+ "w.charging_way from charging_summary_single c,charging_way_single w,exam_info e "
				+ "where e.exam_num = c.exam_num and c.id = w.summary_id and e.exam_num = '"+exam_num.trim()+"' and c.center_num='"+center_num+"' and c.req_num in ("+req_nums+") group by w.charging_way) a "
				+ "on a.charging_way = d.id where d.data_code = 'SFFSLX' and d.isActive = 'Y'";
		List<ChargingWayDTO> list = this.jqm.getList(sql, ChargingWayDTO.class);
		return list;
	}
	
	/**
	 * 
	     * @Title: getChargingInvoiceSingleOfaccountnum   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param account_num
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: ChargingInvoiceSingle      
	     * @throws
	 */
	@Override
	@SuppressWarnings("unchecked")
	public ChargingInvoiceSingle getChargingInvoiceSingleOfaccountnum(String account_num) throws ServiceException{
		List<ChargingInvoiceSingle> invoiceList = this.qm.find("from ChargingInvoiceSingle where account_num = '"+account_num+"'");
		ChargingInvoiceSingle cis= new ChargingInvoiceSingle();
		if(invoiceList.size() != 0){
			cis = invoiceList.get(0);
		}
		return cis;
	}

	@Override
	@SuppressWarnings("unchecked")
	public String saveTuifei(CollectFeesModel model, UserDTO user) throws ServiceException {
		//查询webservice地址
		WebserviceConfigurationDTO wcf=new WebserviceConfigurationDTO();
		wcf=this.webserviceConfigurationService.getWebServiceConfig("REFUND_APPLICATION",user.getCenter_num());
		String IS_SINGLE_REFUND = this.customerInfoService.getCenterconfigByKey("IS_SINGLE_REFUND",user.getCenter_num()).getConfig_value().trim();
		
		List<ExamInfo> listinfo = this.qm.find("from ExamInfo e where e.exam_num = '"+model.getExam_num()+"'");
		ExamInfo examinfo = null;
		if(listinfo.size() == 0){
			return "error-该体检号不存在体检信息";
		}else{
			examinfo = listinfo.get(0);
		}
		String sql ="select cd.card_num,sum(cd.amount) amount from card_deal cd where cd.exam_num = '" + model.getExam_num()
					+ "' and cd.deal_type in (6,7) group by cd.card_num";
		List<CardInfoDTO> listCard = this.jqm.getList(sql, CardInfoDTO.class);
		if("Y".equals(IS_SINGLE_REFUND)){//表示单项退费
			
			if("Y".equals(model.getIsPrintRecepit())){//表示同时作废发票
				
				List<ChargingInvoiceSingle> invoiceList = this.qm.find("from ChargingInvoiceSingle where account_num = '"+model.getAccount_num()+"'");
				if(invoiceList.size() != 0){
					
					ChargingInvoiceSingle chargingInvoiceSingle = invoiceList.get(0);
					
					List<NullifyInvoice> zuofeilist = this.qm.find("from NullifyInvoice where invoice_num = '"+chargingInvoiceSingle.getInvoice_num()+"' and invoice_class = '"+chargingInvoiceSingle.getInvoice_class()+"'");
					if(zuofeilist.size() > 0){
						return "error-该发票号（"+chargingInvoiceSingle.getInvoice_num()+"）已作废，不能再次作废，请先查询!";
					}
//					chargingInvoiceSingle.setInvoice_status("N");
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
					cardDeal.setExam_num(model.getExam_num());
					this.pm.save(cardDeal);
		            
		            //保存作废发票流水表
		            NullifyInvoice nullifyInvoice = new NullifyInvoice();
		            nullifyInvoice.setInvoice_num(chargingInvoiceSingle.getInvoice_num());
		            nullifyInvoice.setInvoice_status("N");
		            nullifyInvoice.setInvoice_class(chargingInvoiceSingle.getInvoice_class());
		            nullifyInvoice.setCreater(user.getUserid());
		            nullifyInvoice.setCreate_time(DateTimeUtil.parse());
		            nullifyInvoice.setDaily_status("0");
		            nullifyInvoice.setAccount_num(GetNumContral.getInstance().getParamNum("account_num",user.getCenter_num()));
		            nullifyInvoice.setCenter_num(user.getCenter_num());
		            this.pm.save(nullifyInvoice);
					
					List<ChargingSummarySingle> summarySingleList = this.qm.find("from ChargingSummarySingle where invoice_id = "+chargingInvoiceSingle.getId());
					
					for(ChargingSummarySingle chargingSummarySingle : summarySingleList){
						
						chargingSummarySingle.setInvoice_id(0);
						chargingSummarySingle.setIs_print_recepit("N");
						
						this.pm.update(chargingSummarySingle);
					}
				}
			}
			
			List<ExamResultChargingItemDTO> listItem = model.getExamInfoCharingItem();
			for(ExamResultChargingItemDTO item : listItem){
				List<ExaminfoChargingItem> examinfoChargingItemList = this.qm.find("from ExaminfoChargingItem where id = "+item.getId()+" and center_num = '"+user.getCenter_num()+"'");
				if(examinfoChargingItemList.size() != 0){
					if(examinfoChargingItemList.get(0).getPay_status().equals("M")){
						return "error-存在已退费的收费项目，请查询加载最新数据!";
					}
				}
			}
			ChargingSummarySingle chargingSummarySingle = new ChargingSummarySingle();
			
			chargingSummarySingle.setReq_num(model.getReq_nums());
			chargingSummarySingle.setExam_id(model.getExam_id());
			chargingSummarySingle.setCharging_status("M");
			chargingSummarySingle.setAmount1(model.getAmount1());
			chargingSummarySingle.setAmount2(model.getAmount2());
			chargingSummarySingle.setDiscount(model.getDiscount());
			chargingSummarySingle.setCashier(user.getUserid());
			chargingSummarySingle.setCash_date(DateTimeUtil.parse());
			chargingSummarySingle.setIs_print_recepit("N");
			chargingSummarySingle.setIs_active("Y");
			chargingSummarySingle.setCreater(user.getUserid());
			chargingSummarySingle.setCreate_time(DateTimeUtil.parse());
			chargingSummarySingle.setUpdater(user.getUserid());
			chargingSummarySingle.setUpdate_time(DateTimeUtil.parse());
			chargingSummarySingle.setDaily_status("0");
			chargingSummarySingle.setExam_num(model.getExam_num());
			chargingSummarySingle.setCenter_num(user.getCenter_num());
			
			this.pm.save(chargingSummarySingle);  //保存收费总表
			
			
			for(ExamResultChargingItemDTO item : listItem){
				ChargingDetailSingle chargingDetailSingle = new ChargingDetailSingle();
				
				chargingDetailSingle.setSummary_id(chargingSummarySingle.getId());
				chargingDetailSingle.setAmount(item.getItem_amount());
				chargingDetailSingle.setAmount1(item.getPersonal_pay());
				chargingDetailSingle.setCharging_item_id(item.getItem_id());
				chargingDetailSingle.setDiscount(item.getDiscount());
				chargingDetailSingle.setCreater(user.getUserid());
				chargingDetailSingle.setCreate_time(DateTimeUtil.parse());
				chargingDetailSingle.setUpdater(user.getUserid());
				chargingDetailSingle.setUpdate_time(DateTimeUtil.parse());
				chargingDetailSingle.setCharging_item_code(item.getItem_code());
				
				this.pm.save(chargingDetailSingle);  //保存结算明细
				
				List<ExaminfoChargingItem> examinfoChargingItemList = this.qm.find("from ExaminfoChargingItem where id = "+item.getId()+" and center_num = '"+user.getCenter_num()+"'");
				if(examinfoChargingItemList.size() != 0){
					ExaminfoChargingItem examinfoChargingItem = examinfoChargingItemList.get(0);
					
					examinfoChargingItem.setPay_status("M");
					examinfoChargingItem.setIsActive("N");
					examinfoChargingItem.setUpdater(user.getUserid());
					examinfoChargingItem.setUpdate_time(DateTimeUtil.parse());
					
					this.pm.update(examinfoChargingItem);
					delLisAndPacsDetail(examinfo,examinfoChargingItem.getCharge_item_id());//这个方法考虑是不是登记台退费那里复制一份？
				}
			}
			boolean isCard = false;
			Double amount = 0.0;
			List<ChargingWayDTO> listway = model.getCharingWay();
			for(ChargingWayDTO chargingWay : listway){
				ChargingWaySingle chargingWaySingle = new ChargingWaySingle();
				
				chargingWaySingle.setSummary_id(chargingSummarySingle.getId());
				chargingWaySingle.setAmount(chargingWay.getAmount());
				chargingWaySingle.setCharging_way(chargingWay.getCharging_way());
				chargingWaySingle.setCreater(user.getUserid());
				chargingWaySingle.setCreate_time(DateTimeUtil.parse());
				chargingWaySingle.setUpdater(user.getUserid());
				chargingWaySingle.setUpdate_time(DateTimeUtil.parse());
				
				if(chargingWay.getCharging_way().equals("122")){
					isCard = true;
					amount = chargingWay.getAmount();
				}
				this.pm.save(chargingWaySingle);
			}
			
			if(isCard){//会员刷卡，保存卡消费信息
				for(CardInfoDTO carddto : listCard){
					List<CardInfo> cardlist = this.qm.find("from CardInfo c where c.card_num = '"+carddto.getCard_num()+"'");
					if(cardlist.size() > 0 && carddto.getAmount() > 0 && amount > 0){
						CardInfo cardInfo = cardlist.get(0);
						CardDeal cardDeals = new CardDeal();
						cardDeals.setCard_num(cardInfo.getCard_num());
						cardDeals.setCard_count(cardInfo.getCard_count());
						cardDeals.setOld_amount(cardInfo.getAmount());
						cardDeals.setSummary_id(chargingSummarySingle.getId());
						cardDeals.setExaminfo_id(model.getExam_id());
						cardDeals.setDeal_type("7");
						cardDeals.setCreater(user.getUserid());
						cardDeals.setDeal_date(DateTimeUtil.getDate2());
						cardDeals.setDeal_time(DateTimeUtil.parse());
						cardDeals.setTrancode("001");
						cardDeals.setExam_num(model.getExam_num());
						if(carddto.getAmount() > amount){
							cardDeals.setAmount(-amount);
							cardInfo.setAmount(amount+cardInfo.getAmount());
							amount = 0.0;
						}else{
							cardDeals.setAmount(-carddto.getAmount());
							cardInfo.setAmount(carddto.getAmount()+cardInfo.getAmount());
							cardInfo.setCard_count(cardInfo.getCard_count()-1);
							amount = amount - carddto.getAmount();
						}
						this.pm.save(cardDeals);
						this.pm.update(cardInfo);
					}
				}
			}
			return "ok-退费成功！";
		}else{//一退全退  
			
			boolean is_his_sq = false; //项目是否发送申请
			
			List<String> REQ_NOS= new ArrayList<String>();//申请单号
			if("Y".equals(model.getIsPrintRecepit())){//按收据单号退费
				
				List<ChargingInvoiceSingle> invoiceList = this.qm.find("from ChargingInvoiceSingle where account_num = '"+model.getAccount_num()+"'");
				if(invoiceList.size() != 0){
					ChargingInvoiceSingle chargingInvoiceSingle = invoiceList.get(0);
					
					List<NullifyInvoice> zuofeilist = this.qm.find("from NullifyInvoice where invoice_num = '"+chargingInvoiceSingle.getInvoice_num()+"' and invoice_class = '"+chargingInvoiceSingle.getInvoice_class()+"'");
					if(zuofeilist.size() > 0){
						return "error-该发票号（"+chargingInvoiceSingle.getInvoice_num()+"）已作废，不能再次作废，请先查询!";
					}
					List<ChargingSummarySingle> summarySingleList = this.qm.find("from ChargingSummarySingle where invoice_id = "+chargingInvoiceSingle.getId());
					for(ChargingSummarySingle chargingSummary : summarySingleList){
						if(chargingSummary.getIs_active().equals("N")){
							return "error-该收费记录已作废，请查询加载最新数据!";
						}
					}
//					chargingInvoiceSingle.setInvoice_status("N");
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
					cardDeal.setExam_num(model.getExam_num());
		            this.pm.save(cardDeal);
		            
		            //保存作废发票流水表
		            NullifyInvoice nullifyInvoice = new NullifyInvoice();
		            nullifyInvoice.setInvoice_num(chargingInvoiceSingle.getInvoice_num());
		            nullifyInvoice.setInvoice_status("N");
		            nullifyInvoice.setInvoice_class(chargingInvoiceSingle.getInvoice_class());
		            nullifyInvoice.setCreater(user.getUserid());
		            nullifyInvoice.setCreate_time(DateTimeUtil.parse());
		            nullifyInvoice.setDaily_status("0");
		            nullifyInvoice.setAccount_num(GetNumContral.getInstance().getParamNum("account_num",user.getCenter_num()));
		            nullifyInvoice.setCenter_num(user.getCenter_num());
		            this.pm.save(nullifyInvoice);
					
					for(ChargingSummarySingle chargingSummary : summarySingleList){
						
						REQ_NOS.add(chargingSummary.getReq_num());
						chargingSummary.setIs_active("N");
						this.pm.update(chargingSummary);
						
						ChargingSummarySingle chargingSummarySingle = new ChargingSummarySingle();
						
						chargingSummarySingle.setReq_num(chargingSummary.getReq_num());
						chargingSummarySingle.setExam_id(chargingSummary.getExam_id());
						chargingSummarySingle.setCharging_status("M");
						chargingSummarySingle.setAmount1(chargingSummary.getAmount1());
						chargingSummarySingle.setAmount2(chargingSummary.getAmount2());
						chargingSummarySingle.setDiscount(chargingSummary.getDiscount());
						chargingSummarySingle.setCashier(user.getUserid());
						chargingSummarySingle.setCash_date(DateTimeUtil.parse());
						chargingSummarySingle.setIs_print_recepit("Y");
						chargingSummarySingle.setIs_active("N");
						chargingSummarySingle.setCreater(user.getUserid());
						chargingSummarySingle.setCreate_time(DateTimeUtil.parse());
						chargingSummarySingle.setUpdater(user.getUserid());
						chargingSummarySingle.setUpdate_time(DateTimeUtil.parse());
						chargingSummarySingle.setInvoice_id(chargingInvoiceSingle.getId());
						chargingSummarySingle.setDaily_status("0");
						chargingSummarySingle.setExam_num(model.getExam_num());
						chargingSummarySingle.setCenter_num(user.getCenter_num());
						
						this.pm.save(chargingSummarySingle); //保存退费信息总表
						
						List<ChargingDetailSingle> detailSingleList = this.qm.find("from ChargingDetailSingle where summary_id = "+chargingSummary.getId());
						
						for(ChargingDetailSingle detailSingle : detailSingleList){
							ChargingDetailSingle chargingDetailSingle = new ChargingDetailSingle();
							
							chargingDetailSingle.setSummary_id(chargingSummarySingle.getId());
							chargingDetailSingle.setAmount(detailSingle.getAmount());
							chargingDetailSingle.setAmount1(detailSingle.getAmount1());
							chargingDetailSingle.setCharging_item_id(detailSingle.getCharging_item_id());
							chargingDetailSingle.setDiscount(detailSingle.getDiscount());
							chargingDetailSingle.setCreater(user.getUserid());
							chargingDetailSingle.setCreate_time(DateTimeUtil.parse());
							chargingDetailSingle.setUpdater(user.getUserid());
							chargingDetailSingle.setUpdate_time(DateTimeUtil.parse());
							chargingDetailSingle.setCharging_item_code(detailSingle.getCharging_item_code());
							
							this.pm.save(chargingDetailSingle);  //保存结算明细
							
							List<ExaminfoChargingItem> examinfoChargingItemList = this.qm.find("from ExaminfoChargingItem where pay_status != 'M' and center_num = '"+user.getCenter_num()+"'"
									+ " and isActive = 'Y' and center_num = '"+user.getCenter_num()+"' and exam_num = '" + chargingSummarySingle.getExam_num() 
									+"' and charge_item_id = "+chargingDetailSingle.getCharging_item_id());
							
							if(examinfoChargingItemList.size() != 0){
								ExaminfoChargingItem examItem = examinfoChargingItemList.get(0);
								
								if("Y".equals(examItem.getHis_req_status())){
									is_his_sq = true;
								}
								examItem.setPay_status("N");
								examItem.setHis_req_status("N");
								this.pm.update(examItem);
							}
						}
						
						List<ChargingWaySingle> waySingleList = this.qm.find("from ChargingWaySingle where summary_id = " +chargingSummary.getId());
						boolean isCard = false;
						Double amount = 0.0;
						for(ChargingWaySingle WaySingle : waySingleList){
							ChargingWaySingle chargingWaySingle = new ChargingWaySingle();
							chargingWaySingle.setSummary_id(chargingSummarySingle.getId());
							chargingWaySingle.setAmount(WaySingle.getAmount());
							chargingWaySingle.setCharging_way(WaySingle.getCharging_way());
							chargingWaySingle.setCreater(user.getUserid());
							chargingWaySingle.setCreate_time(DateTimeUtil.parse());
							chargingWaySingle.setUpdater(user.getUserid());
							chargingWaySingle.setUpdate_time(DateTimeUtil.parse());
							if(WaySingle.getCharging_way().equals("122")){
								isCard = true;
								amount = WaySingle.getAmount();
							}
							this.pm.save(chargingWaySingle);
						}
						
						if(isCard){//会员刷卡，保存卡消费信息
							for(CardInfoDTO carddto : listCard){
								List<CardInfo> cardlist = this.qm.find("from CardInfo c where c.card_num = '"+carddto.getCard_num()+"'");
								if(cardlist.size() > 0 && carddto.getAmount() > 0 && amount > 0){
									CardInfo cardInfo = cardlist.get(0);
									CardDeal cardDeals = new CardDeal();
									cardDeals.setCard_num(cardInfo.getCard_num());
									cardDeals.setCard_count(cardInfo.getCard_count());
									cardDeals.setOld_amount(cardInfo.getAmount());
									cardDeals.setSummary_id(chargingSummarySingle.getId());
									cardDeals.setExaminfo_id(model.getExam_id());
									cardDeals.setDeal_type("7");
									cardDeals.setCreater(user.getUserid());
									cardDeals.setDeal_date(DateTimeUtil.getDate2());
									cardDeals.setDeal_time(DateTimeUtil.parse());
									cardDeals.setTrancode("001");
									if(carddto.getAmount() > amount){
										cardDeals.setAmount(-amount);
										cardInfo.setAmount(amount+cardInfo.getAmount());
										amount = 0.0;
									}else{
										cardDeals.setAmount(-carddto.getAmount());
										cardInfo.setAmount(carddto.getAmount()+cardInfo.getAmount());
										cardInfo.setCard_count(cardInfo.getCard_count()-1);
										amount = amount - carddto.getAmount();
									}
									cardDeal.setExam_num(model.getExam_num());
									this.pm.save(cardDeals);
									this.pm.update(cardInfo);
								}
							}
						}
					}
					
					List<ExamResultChargingItemDTO> listItem = model.getExamInfoCharingItem();
					for(ExamResultChargingItemDTO item : listItem){
						List<ExaminfoChargingItem> examinfoChargingItemList = this.qm.find("from ExaminfoChargingItem where id = "+item.getId()+" and center_num = '"+user.getCenter_num()+"'");
						if(examinfoChargingItemList.size() != 0){
							ExaminfoChargingItem examinfoChargingItem = examinfoChargingItemList.get(0);
							
							examinfoChargingItem.setPay_status("M");
							examinfoChargingItem.setIsActive("N");
							examinfoChargingItem.setUpdater(user.getUserid());
							examinfoChargingItem.setUpdate_time(DateTimeUtil.parse());
							
							this.pm.update(examinfoChargingItem);
							delLisAndPacsDetail(examinfo,examinfoChargingItem.getCharge_item_id());
						}
					}
					
					if(is_his_sq){//已发送申请，发送退费申请
						UnFeeMessage fm = new UnFeeMessage();
						
						ReqUnNo rrn=new ReqUnNo();
						rrn.setREQ_NO(REQ_NOS);
						fm.setREQ_NOS(rrn);//申请单号
						fm.setEXAM_NUM(examinfo.getPatient_id());
						fm.setRCPT_NO(model.getAccount_num());//收据号
						fm.setCenter_num(user.getCenter_num());
						
						UNFEESendMessage fsm= new UNFEESendMessage(fm);		
						FeeReqBody frb= new FeeReqBody();
						frb = fsm.feeSend(wcf.getConfig_url().trim(),wcf.getConfig_method().trim(), true);
						System.out.println(frb.getResultHeader().getTypeCode() + "-" + frb.getResultHeader().getText());
						if("AA".equals(frb.getResultHeader().getTypeCode())){//申请发送成功
							return "ok-退费成功！请发送缴费申请。";
						}else{
							throw new ServiceException(frb.getResultHeader().getText());
						}
					}
				}
			}else{//按申请单号退费
			
				List<ChargingSummarySingle> sumarySingleList = this.qm.find("from ChargingSummarySingle where req_num = '"+model.getReq_nums()+"'");
				for(ChargingSummarySingle chargingSummary : sumarySingleList){
					if(chargingSummary.getIs_active().equals("N")){
						return "error-该收费记录已作废，请查询加载最新数据!";
					}
				}
				if(sumarySingleList.size() != 0){
					sumarySingleList.get(0).setIs_active("N");
					this.pm.update(sumarySingleList.get(0)); //将原来的总表置为N
					
					ChargingSummarySingle chargingSummarySingle = new ChargingSummarySingle();
					
					chargingSummarySingle.setReq_num(sumarySingleList.get(0).getReq_num());
					chargingSummarySingle.setExam_id(sumarySingleList.get(0).getExam_id());
					chargingSummarySingle.setCharging_status("M");
					chargingSummarySingle.setAmount1(sumarySingleList.get(0).getAmount1());
					chargingSummarySingle.setAmount2(sumarySingleList.get(0).getAmount2());
					chargingSummarySingle.setDiscount(sumarySingleList.get(0).getDiscount());
					chargingSummarySingle.setCashier(user.getUserid());
					chargingSummarySingle.setCash_date(DateTimeUtil.parse());
					chargingSummarySingle.setIs_print_recepit("N");
					chargingSummarySingle.setIs_active("N");
					chargingSummarySingle.setCreater(user.getUserid());
					chargingSummarySingle.setCreate_time(DateTimeUtil.parse());
					chargingSummarySingle.setUpdater(user.getUserid());
					chargingSummarySingle.setUpdate_time(DateTimeUtil.parse());
					chargingSummarySingle.setDaily_status("0");
					chargingSummarySingle.setExam_num(model.getExam_num());
					chargingSummarySingle.setCenter_num(user.getCenter_num());
					
					
					this.pm.save(chargingSummarySingle); //保存退费信息总表
					
					List<ChargingDetailSingle> detailSingleList = this.qm.find("from ChargingDetailSingle where summary_id = "+sumarySingleList.get(0).getId());
					
					for(ChargingDetailSingle detailSingle : detailSingleList){
						ChargingDetailSingle chargingDetailSingle = new ChargingDetailSingle();
						
						chargingDetailSingle.setSummary_id(chargingSummarySingle.getId());
						chargingDetailSingle.setAmount(detailSingle.getAmount());
						chargingDetailSingle.setAmount1(detailSingle.getAmount1());
						chargingDetailSingle.setCharging_item_id(detailSingle.getCharging_item_id());
						chargingDetailSingle.setDiscount(detailSingle.getDiscount());
						chargingDetailSingle.setCreater(user.getUserid());
						chargingDetailSingle.setCreate_time(DateTimeUtil.parse());
						chargingDetailSingle.setUpdater(user.getUserid());
						chargingDetailSingle.setUpdate_time(DateTimeUtil.parse());
						chargingDetailSingle.setCharging_item_code(detailSingle.getCharging_item_code());
						
						this.pm.save(chargingDetailSingle);  //保存结算明细
						
						List<ExaminfoChargingItem> examinfoChargingItemList = this.qm.find("from ExaminfoChargingItem where pay_status != 'M'"
								+ " and isActive = 'Y' and center_num = '"+user.getCenter_num()+"' and exam_num = '" + chargingSummarySingle.getExam_num() 
								+"' and charge_item_id = "+chargingDetailSingle.getCharging_item_id());
						
						if(examinfoChargingItemList.size() != 0){
							ExaminfoChargingItem examItem = examinfoChargingItemList.get(0);
							
							if("Y".equals(examItem.getHis_req_status())){
								is_his_sq = true;
							}
							examItem.setPay_status("N");
							examItem.setHis_req_status("N");
							this.pm.update(examItem);
						}
					}
					
					List<ChargingWaySingle> waySingleList = this.qm.find("from ChargingWaySingle where summary_id = " +sumarySingleList.get(0).getId());
					boolean isCard = false;
					Double amount = 0.0;
					for(ChargingWaySingle WaySingle : waySingleList){
						ChargingWaySingle chargingWaySingle = new ChargingWaySingle();
						chargingWaySingle.setSummary_id(chargingSummarySingle.getId());
						chargingWaySingle.setAmount(WaySingle.getAmount());
						chargingWaySingle.setCharging_way(WaySingle.getCharging_way());
						chargingWaySingle.setCreater(user.getUserid());
						chargingWaySingle.setCreate_time(DateTimeUtil.parse());
						chargingWaySingle.setUpdater(user.getUserid());
						chargingWaySingle.setUpdate_time(DateTimeUtil.parse());
						if(WaySingle.getCharging_way().equals("122")){
							isCard = true;
							amount = WaySingle.getAmount();
						}
						this.pm.save(chargingWaySingle);
					}
					if(isCard){//会员刷卡，保存卡消费信息
						for(CardInfoDTO carddto : listCard){
							List<CardInfo> cardlist = this.qm.find("from CardInfo c where c.card_num = '"+carddto.getCard_num()+"'");
							if(cardlist.size() > 0 && carddto.getAmount() > 0 && amount > 0){
								CardInfo cardInfo = cardlist.get(0);
								CardDeal cardDeals = new CardDeal();
								cardDeals.setCard_num(cardInfo.getCard_num());
								cardDeals.setCard_count(cardInfo.getCard_count());
								cardDeals.setOld_amount(cardInfo.getAmount());
								cardDeals.setSummary_id(chargingSummarySingle.getId());
								cardDeals.setExaminfo_id(model.getExam_id());
								cardDeals.setDeal_type("7");
								cardDeals.setCreater(user.getUserid());
								cardDeals.setDeal_date(DateTimeUtil.getDate2());
								cardDeals.setDeal_time(DateTimeUtil.parse());
								cardDeals.setTrancode("001");
								if(carddto.getAmount() > amount){
									cardDeals.setAmount(-amount);
									cardInfo.setAmount(amount+cardInfo.getAmount());
									amount = 0.0;
								}else{
									cardDeals.setAmount(-carddto.getAmount());
									cardInfo.setAmount(carddto.getAmount()+cardInfo.getAmount());
									cardInfo.setCard_count(cardInfo.getCard_count()-1);
									amount = amount - carddto.getAmount();
								}
								cardDeals.setExam_num(model.getExam_num());
								this.pm.save(cardDeals);
								this.pm.update(cardInfo);
							}
						}
					}
				}
				List<ExamResultChargingItemDTO> listItem = model.getExamInfoCharingItem();
				for(ExamResultChargingItemDTO item : listItem){
					List<ExaminfoChargingItem> examinfoChargingItemList = this.qm.find("from ExaminfoChargingItem where id = "+item.getId()+" and center_num = '"+user.getCenter_num()+"'");
					if(examinfoChargingItemList.size() != 0){
						ExaminfoChargingItem examinfoChargingItem = examinfoChargingItemList.get(0);
						
						examinfoChargingItem.setPay_status("M");
						examinfoChargingItem.setIsActive("N");
						examinfoChargingItem.setUpdater(user.getUserid());
						examinfoChargingItem.setUpdate_time(DateTimeUtil.parse());
						
						this.pm.update(examinfoChargingItem);
						delLisAndPacsDetail(examinfo,examinfoChargingItem.getCharge_item_id());
					}
				}
				
			}
			return "ok-退费成功！";
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<CardInfoDTO> getCardInfo(long exam_id) throws ServiceException{
		String sql =" select ci.card_num,cd.amount,ci.card_count,ci.card_type from card_deal cd,card_info ci "
				+ "where ci.card_num = cd.card_num and cd.examinfo_id = "+exam_id+" and cd.deal_type = 6 order by ci.card_type";
		List<CardInfoDTO> listCard = this.jqm.getList(sql, CardInfoDTO.class);
		return listCard;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public String getMaxFaPiaoHaoByUser(long userId) throws ServiceException {
		String sqlstr = "select invoice_num,id from [charging_invoice_group] "
				+ "union select invoice_num,id from [charging_invoice_single] " 
				+ " where invoice_maker = "+userId+" order by id desc ";
		List<ChargingInvoiceSingle> list = this.jqm.getList(sqlstr, ChargingInvoiceSingle.class);
		if (list.size() != 0) {
			ChargingInvoiceSingle map = list.get(0);
			return map.getInvoice_num();
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public String getChargingInvoiceSingleByNum(String invoiceNum,String invoice_class) throws ServiceException {
		String sqlstr = "select invoice_num from charging_invoice_single g where g.invoice_num = '"+invoiceNum.trim()+"' " 
				+ " and g.invoice_class =  '"+invoice_class+"'";
		List<ChargingInvoiceSingle> list = this.jqm.getList(sqlstr, ChargingInvoiceSingle.class);
		if (list.size() != 0) {
			ChargingInvoiceSingle map = list.get(0);
			return map.getInvoice_num();
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public String getNullifyInvoice(String invoiceNum,String invoice_class,String center_num) throws ServiceException{
		String sql = "select n.invoice_num from nullify_invoice n where n.invoice_num = '"+invoiceNum.trim()+"' "
				+ " and n.invoice_class = '"+invoice_class+"' and n.center_num = '"+center_num+"'";
		List<ChargingInvoiceSingle> list = this.jqm.getList(sql, ChargingInvoiceSingle.class);
		if (list.size() != 0) {
			ChargingInvoiceSingle map = list.get(0);
			return map.getInvoice_num();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ChargingInvoiceSingleDTO> getsingleInvoice(String chargingIds, String examNum,String center_num) throws ServiceException {
		String sql = "select distinct cv.id,cv.invoice_num,cv.title_info,cv.invoice_type,"
				+ " cv.invoice_amount,u.chi_name as invoice_maker,CONVERT(varchar(50),cv.invoice_time,23) "
				+ " as invoice_time from charging_detail_single c ,charging_summary_single sc"
				+ " ,exam_info e, charging_invoice_single cv left join user_usr u "
				+ " on u.id = cv.invoice_maker"
				+ " where c.summary_id = sc.id and c.charging_item_id in ("+chargingIds+") "
				+ " and sc.is_print_recepit = 'Y' and sc.exam_num = e.exam_num "
				+ " and e.exam_num = '"+examNum+"' and sc.invoice_id = cv.id and sc.center_num='"+center_num+"'";
		List<ChargingInvoiceSingleDTO> list = this.jqm.getList(sql, ChargingInvoiceSingleDTO.class);
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ChargingSummarySingleDTO> getChargingSingleInvoickList(String examNum,String center_num) throws ServiceException {
		String sql = "SELECT sum(case when s.charging_status = 'M' then -amount2 else amount2 end) as amount,s.req_num "
//				+" ,u.chi_name as cashier,CONVERT(varchar(100),s.cash_date,23) as cash_date "
				+" FROM exam_info e,charging_summary_single s "
//				+ "left join user_usr u on u.id = s.cashier "
				+" where s.exam_num = e.exam_num and e.exam_num = '"+examNum.trim()+"' and s.is_print_recepit = 'N' "
				+" and s.is_active = 'Y' and s.center_num='"+center_num+"' group by s.req_num";
//				+ ",u.chi_name,CONVERT(varchar(100),s.cash_date,23)";
		List<ChargingSummarySingleDTO> list = this.jqm.getList(sql, ChargingSummarySingleDTO.class);
		
		List<ChargingSummarySingleDTO> temp = new ArrayList<ChargingSummarySingleDTO>();
		for (ChargingSummarySingleDTO chargingSummarySingleDTO : list) {
			if(chargingSummarySingleDTO.getAmount() <= 0){
				temp.add(chargingSummarySingleDTO);
			}
		}
		list.removeAll(temp);
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public CollectFeesResult saveSingleInviockBu(CollectFeesModel model, UserDTO user,String invoice_class) throws ServiceException {
		CollectFeesResult result = new CollectFeesResult();
		String[] req_nums = model.getReq_nums().split(",");
		for (int i = 0; i < req_nums.length; i++) {
			String sql = "from ChargingSummarySingle c where c.req_num = '"+req_nums[i]+"'";
			List<ChargingSummarySingle> list = this.qm.find(sql);
			for (ChargingSummarySingle chargingSummarySingle : list) {
				if(chargingSummarySingle.getIs_print_recepit().equals("Y")){
					result.setUser_id(user.getUserid());
					result.setFlag("error");
					result.setInfo("存在已开发票收费记录，请查询最新的收费记录！");
					return result;
				}
			}
		}
		ChargingInvoiceSingle chargingInvoiceSingle = new ChargingInvoiceSingle();
		
		chargingInvoiceSingle.setTitle_info(model.getTitle_info());
		chargingInvoiceSingle.setInvoice_num(model.getInvoice_num());
		chargingInvoiceSingle.setInvoice_type(model.getInvoice_type());
		chargingInvoiceSingle.setInvoice_status("Y");
		chargingInvoiceSingle.setInvoice_maker(user.getUserid());
		chargingInvoiceSingle.setInvoice_amount(model.getAmount2());
		chargingInvoiceSingle.setInvoice_time(DateTimeUtil.parse());
		chargingInvoiceSingle.setCreater(user.getUserid());
		chargingInvoiceSingle.setCreate_time(DateTimeUtil.parse());
		chargingInvoiceSingle.setUpdater(user.getUserid());
		chargingInvoiceSingle.setUpdate_time(DateTimeUtil.parse());
		chargingInvoiceSingle.setAccount_num(GetNumContral.getInstance().getParamNum("account_num",user.getCenter_num()));
		chargingInvoiceSingle.setExam_type("G");
		chargingInvoiceSingle.setBarchid(model.getExam_id());
		chargingInvoiceSingle.setCenter_num(user.getCenter_num());
		chargingInvoiceSingle.setInvoice_class(invoice_class);
		chargingInvoiceSingle.setDaily_status("0");
		chargingInvoiceSingle.setCharging_summary_num(model.getReq_nums());
		
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
        
		for (int i = 0; i < req_nums.length; i++) {
			String sql = "from ChargingSummarySingle c where c.req_num = '"+req_nums[i]+"'";
			List<ChargingSummarySingle> list = this.qm.find(sql);
			for (ChargingSummarySingle chargingSummarySingle : list) {
				chargingSummarySingle.setInvoice_id(chargingInvoiceSingle.getId());
				chargingSummarySingle.setIs_print_recepit("Y");
				chargingSummarySingle.setUpdater(user.getUserid());
				chargingSummarySingle.setUpdate_time(DateTimeUtil.parse());
				this.pm.update(chargingSummarySingle);
				
				String sql1 = "from ChargingWaySingle c where c.summary_id ="+chargingSummarySingle.getId();
				List<ChargingWaySingle> cList = this.qm.find(sql1);
				for(ChargingWaySingle chargingWaySingle : cList){
					chargingWaySingle.setAccount_num(chargingInvoiceSingle.getAccount_num());
					this.pm.update(chargingInvoiceSingle);
				}
				
				TeamInvoiceAccount team = new TeamInvoiceAccount();
				team.setAccount_num(chargingInvoiceSingle.getAccount_num());
				team.setAcc_num(chargingSummarySingle.getReq_num());
				team.setAudit_date(DateTimeUtil.parse());
				team.setAuditor(user.getUserid());
				team.setCenter_num(user.getCenter_num());
				this.pm.save(team);
			}
		}
		
		result.setUser_id(user.getUserid());
		result.setAccount_num(chargingInvoiceSingle.getAccount_num());
		result.setFlag("ok");
		result.setInfo("补打发票成功");
		return result;
	}
	
	    
    /**
	 * 
	     * @Title: updateSingleInviockTT   
	     * @Description: 团体打印发票   
	     * @param: @param model
	     * @param: @param userId
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: String      
	     * @throws
	 */
	public MenuDTO updateSingleInviockTT(long barchid,long id, String invoid_num, String title, String invoidtype, double amt,
           String invoiceclass,long userId, String center_num) throws ServiceException {
		MenuDTO m= new MenuDTO();
		ChargingSummaryGroup chargingSummaryGroup= new ChargingSummaryGroup();
		chargingSummaryGroup = (ChargingSummaryGroup) this.qm.load(ChargingSummaryGroup.class, id);
		if (chargingSummaryGroup.getInvoice_id()>0)  {
	           m.setValue("error");
	           m.setText("结帐单发票不能重复打印");
		} else {
			ChargingInvoiceSingle chargingInvoiceSingle = new ChargingInvoiceSingle();
			chargingInvoiceSingle.setTitle_info(title);
			chargingInvoiceSingle.setInvoice_num(invoid_num);
			chargingInvoiceSingle.setInvoice_type(invoidtype);
			chargingInvoiceSingle.setInvoice_status("Y");// 有效
			chargingInvoiceSingle.setInvoice_maker(userId);
			chargingInvoiceSingle.setInvoice_amount(amt);
			chargingInvoiceSingle.setInvoice_time(DateTimeUtil.parse());
			chargingInvoiceSingle.setUpdater(userId);
			chargingInvoiceSingle.setUpdate_time(DateTimeUtil.parse());
			chargingInvoiceSingle.setCreater(userId);
			chargingInvoiceSingle.setUpdate_time(DateTimeUtil.parse());
			chargingInvoiceSingle.setAccount_num(GetNumContral.getInstance().getParamNum("account_num",center_num));
			chargingInvoiceSingle.setCenter_num(center_num);
			chargingInvoiceSingle.setBarchid(barchid);
			chargingInvoiceSingle.setExam_type("T");
			chargingInvoiceSingle.setInvoice_class(invoiceclass);
			chargingInvoiceSingle.setDaily_status("0");
			chargingInvoiceSingle.setCharging_summary_num(chargingSummaryGroup.getAccount_num());
			this.pm.save(chargingInvoiceSingle);
			
			//增加发票
			
			chargingSummaryGroup.setUpdate_time(DateTimeUtil.parse());
			chargingSummaryGroup.setUpdater(userId);
			chargingSummaryGroup.setIs_print_recepit("Y");
			chargingSummaryGroup.setInvoice_id(chargingInvoiceSingle.getId());
			this.pm.update(chargingSummaryGroup);
			
			CardDeal cardDeal= new CardDeal();
			cardDeal.setAmount(amt);
			cardDeal.setCard_num(chargingInvoiceSingle.getInvoice_num());
			cardDeal.setTrancode("002");
			cardDeal.setCreater(userId);
			cardDeal.setDeal_type("0");
			cardDeal.setDeal_date(DateTimeUtil.getDate2());
			cardDeal.setDeal_time(DateTimeUtil.parse());
			cardDeal.setOld_amount(amt);
			cardDeal.setInvoice_class(chargingInvoiceSingle.getInvoice_class());
            this.pm.save(cardDeal);			
			m.setValue("ok");
	        m.setText(chargingInvoiceSingle.getAccount_num());
	        m.setId(userId+"");
		}
		return m;
	}
    

	/**
	 * 
	 * @Title: delSingleInviockTT @Description:
	 * TODO(这里用一句话描述这个方法的作用) @param: @param invoice_num @param: @param
	 * userId @param: @return @param: @throws ServiceException @return:
	 * String @throws
	 */
	public String delSingleInviockTT(long barchid,long invoice_id,long userId,String center_num) throws ServiceException {
		String sql = "from ChargingInvoiceSingle c where c.id = '" + invoice_id + "' ";
		List<ChargingInvoiceSingle> list = this.qm.find(sql);
		String message="error-error";
		if((list==null)||(list.size()<=0)){
			message="error-无效发票";
		}else{
			ChargingInvoiceSingle cs = new ChargingInvoiceSingle();
			cs=list.get(0);
			
			//写 作废发票表
			
			NullifyInvoice ni= new NullifyInvoice();
			ni.setCreate_time(DateTimeUtil.parse());
			ni.setCreater(userId);
			ni.setDaily_status("0");
			ni.setInvoice_class(cs.getInvoice_class());
			ni.setBatch_id(barchid);
			ni.setInvoice_num(cs.getInvoice_num());
			ni.setInvoice_status("N");
			ni.setAccount_num(GetNumContral.getInstance().getParamNum("account_num",center_num));
			ni.setCenter_num(center_num);
			this.pm.save(ni);	
			
			CardDeal cardDeal= new CardDeal();
			cardDeal.setAmount(cs.getInvoice_amount());
			cardDeal.setCard_num(cs.getInvoice_num());
			cardDeal.setTrancode("002");
			cardDeal.setCreater(userId);
			cardDeal.setDeal_type("1");
			cardDeal.setDeal_date(DateTimeUtil.getDate2());
			cardDeal.setDeal_time(DateTimeUtil.parse());
			cardDeal.setOld_amount(cs.getInvoice_amount());
			cardDeal.setInvoice_class(cs.getInvoice_class());
            this.pm.save(cardDeal);		
			//设置结帐表发票号为0
			String sql2 = "from ChargingSummaryGroup c where c.invoice_id = '"+invoice_id+"'";
			List<ChargingSummaryGroup> list2 = this.qm.find(sql2);
			for (ChargingSummaryGroup ta2:list2){
				ta2.setInvoice_id(0);
				ta2.setIs_print_recepit("N");
				ta2.setUpdater(userId);
				ta2.setUpdate_time(DateTimeUtil.parse());
				this.pm.update(ta2);
			}
			message = "ok-发票作废处理完成";
		}	
		return message;
	}

	@SuppressWarnings("unchecked")
	@Override
	public String paymentApplication(String examNum, UserDTO user) throws ServiceException {
		//查询webservice地址
		WebserviceConfigurationDTO wcf=new WebserviceConfigurationDTO();
		wcf=this.webserviceConfigurationService.getWebServiceConfig("PAYMENT_APPLICATION",user.getCenter_num());
		//根据体检号查询体检信息
		List<ExamInfo> examList = this.qm.find("from ExamInfo e where e.exam_num = '"+examNum+"'");
		if(examList.size() == 0){
			return "error-该体检信息不存在!";
		}
		ExamInfo examInfo = examList.get(0);
		String IS_HIS_EXAMBRID_CHECK = this.customerInfoService.getCenterconfigByKey("IS_HIS_EXAMBRID_CHECK",user.getCenter_num()).getConfig_value().trim();
		if ("Y".equals(IS_HIS_EXAMBRID_CHECK)) {
			if(examInfo.getVisit_no() == null || "".equals(examInfo.getVisit_no())
				|| examInfo.getVisit_date() == null || "".equals(examInfo.getVisit_date())){
				return "error-没有就诊卡号或就诊日期，不能发送缴费申请!";
			}
		}
		
//		//查询人员信息
		List<CustomerInfo> cusList = this.qm.find("from CustomerInfo c where c.id = "+examInfo.getCustomer_id());
		CustomerInfo customerInfo = cusList.get(0);
		String sql = "from ExaminfoChargingItem ec where "
				+ "ec.isActive = 'Y' and ec.his_req_status = 'N' and ec.exam_num = '"+examInfo.getExam_num()+"' and ec.center_num='"+user.getCenter_num()+"'  ";
		if("1".equals(wcf.getConfig_method().trim())){//东北国际过滤团体的，只发个人的项目
			sql += " and ec.exam_indicator != 'T' and ec.pay_status = 'N'";
		}else if("5".equals(wcf.getConfig_method().trim())){//武汉黄冈 团体个人的项目都发
			sql += " and ec.pay_status in ('N','R') and ec.amount > 0 ";
		}else{//默认只发个人
			sql += " and ec.exam_indicator != 'T' and ec.pay_status = 'N'";
		}
		
		TranLogTxt.liswriteEror_to_txt("reqFee", "根据体检id查询需要申请的项目:"+sql);
		//根据体检号查询需要申请的项目
		List<ExaminfoChargingItem> itemList = this.qm.find(sql);
		if(itemList.size() == 0){
			return "error-该体检者不存在需要发缴费申请的项目!";
		}
		
		Double amount = 0.0;  //计算总金额
		List<Fee> feeList = new ArrayList<Fee>(); //发送申请项目集合
		List<ChargingDetailSingle> detailList = new ArrayList<ChargingDetailSingle>();  //结算明细
		
		for(ExaminfoChargingItem item : itemList) {
			sql = " FROM ChargingItem WHERE  item_code='"+item.getCharging_item_code()+"'";
			//ChargingItem charitem = (ChargingItem) this.qm.load(ChargingItem.class,item.getCharge_item_id()); 
			List<ChargingItem> charitemList = qm.find(sql);
			ChargingItem charitem = new ChargingItem();
			if(charitemList!=null && charitemList.size()>0) {
				charitem = charitemList.get(0);
			}
			amount += item.getPersonal_pay();
			//组装明细表
			ChargingDetailSingle chargingDetailSingle = new ChargingDetailSingle();
			chargingDetailSingle.setAmount(item.getItem_amount());
			chargingDetailSingle.setAmount1(item.getPersonal_pay());
			chargingDetailSingle.setCharging_item_id(item.getCharge_item_id());
			chargingDetailSingle.setDiscount(item.getDiscount());
			chargingDetailSingle.setCreater(user.getUserid());
			chargingDetailSingle.setCreate_time(DateTimeUtil.parse());
			chargingDetailSingle.setUpdater(user.getUserid());
			chargingDetailSingle.setUpdate_time(DateTimeUtil.parse());
			chargingDetailSingle.setCharging_item_code(item.getCharging_item_code());
			detailList.add(chargingDetailSingle);
			
			String HIS_FEE_TYPE = this.customerInfoService.getCenterconfigByKey("HIS_FEE_TYPE",user.getCenter_num()).getConfig_value();
			if(HIS_FEE_TYPE != null && "1".equals(HIS_FEE_TYPE.trim())){
				Fee fee = new Fee();				
				fee.setPATIENT_ID(examInfo.getPatient_id());
				fee.setEXAM_NUM(examInfo.getExam_num());
				fee.setVISIT_DATE(examInfo.getVisit_date());
				fee.setVISIT_NO(examInfo.getVisit_no());
				fee.setSERIAL_NO("");
				fee.setORDER_CLASS("");
				fee.setORDER_NO("1");
				fee.setORDER_SUB_NO("1");
				fee.setITEM_NO("1");
				fee.setITEM_CLASS("");
				fee.setITEM_NAME(charitem.getItem_name());
				fee.setITEM_CODE(charitem.getHis_num());
				fee.setITEM_SPEC("");
				fee.setUNITS("");
				fee.setREPETITION("1");
				
				fee.setORDERED_BY_DEPT(companyService.getDatadis("SQKS","1").get(0).getRemark());
				fee.setORDERED_BY_DOCTOR(user.getName());
				fee.setUSER_NAME(user.getWork_num());
				fee.setPERFORMED_BY(charitem.getPerform_dept());
				fee.setCLASS_ON_RCPT("");
				fee.setITEM_PRICE(charitem.getAmount().toString());
				fee.setRCPT_NO("");
				fee.setCHARGE_INDICATOR("0");
				fee.setCLASS_ON_RECKONING("");
				fee.setSUBJ_CODE("");
				fee.setPRICE_QUOTIETY("");
				fee.setCLINIC_NO(examInfo.getClinic_no());
				fee.setBILL_DATE("");
				fee.setBILL_NO("");
				fee.setSKINTEST("");// 皮试标志
				fee.setPRESC_PSNO(""); //皮试结果
				fee.setINSURANCE_FLAG("0"); //适用医保内外标志：0自费，1医保，2免费
				fee.setINSURANCE_CONSTRAINED_LEVEL("");// 公费用药级别
				fee.setSKIN_SAVE("");//皮试记录时间
				fee.setSKIN_START("");//皮试时间
				fee.setSKIN_BATH("");//药品批号
				fee.setCHARGES(item.getPersonal_pay()+"");//实收金额
				fee.setExam_chargeItem_code(charitem.getItem_code());
				feeList.add(fee);
			}else{
				String sql1 ="select distinct c.item_class as item_class_c,c.item_code as item_code_c,c.item_name as item_name_c,c.item_status,"
						+" c.input_code as input_code_c,c.expand1,c.expand2,c.expand3,p.item_class as item_class_p,p.item_code as item_code_p,"
						+" p.item_name as item_name_p,p.input_code as input_code_p,p.item_spec,p.units,p.price,p.prefer_price,p.performed_by,"
						+" p.class_on_inp_rcpt,p.class_on_outp_rcpt,p.class_on_reckoning,p.subj_code,p.memo,cp.*"
						+" from his_clinic_item c,his_clinic_item_v_price_list cp,his_price_list p,charging_item i"
						+" where c.item_code = cp.clinic_item_code and p.item_code = cp.charge_item_code and c.item_code = i.his_num "
						+" and c.item_class = i.item_class and c.item_class = cp.clinic_item_class and cp.charge_item_class = p.item_class"
						+" and i.item_code = '"+charitem.getItem_code()+"'  and i.item_class = '"+charitem.getItem_class()+"'";
				if(examInfo.getVisit_date() != null && !"".equals(examInfo.getVisit_date())){
					sql1 += " and  '"+examInfo.getVisit_date()+"'>=p.start_date   and  '"+examInfo.getVisit_date()+"'<=p.stop_date ";
				}
				
				if(!"5".equals(wcf.getConfig_method())){
					sql1 += " and cp.charge_item_spec = p.item_spec and cp.units = p.units";
				}
				
				TranLogTxt.liswriteEror_to_txt("reqFee", "根据项目信息查询价表数据:"+sql1);
				List<HisClinicItemPriceListDTO> hisList = this.jqm.getList(sql1, HisClinicItemPriceListDTO.class);
				
				if(hisList.size() == 0){
					return "error-收费项目"+charitem.getItem_name()+"找不到HIS价表信息!";
				}
				
				for(HisClinicItemPriceListDTO hiscp : hisList){
					Fee fee = new Fee();				
					fee.setPATIENT_ID(examInfo.getPatient_id());
					fee.setEXAM_NUM(examInfo.getExam_num());
					fee.setVISIT_DATE(examInfo.getVisit_date());
					fee.setVISIT_NO(examInfo.getVisit_no());
					fee.setSERIAL_NO("");
					fee.setORDER_CLASS(hiscp.getItem_class_c());
					fee.setORDER_NO("1");
					fee.setORDER_SUB_NO("1");
					fee.setITEM_NO("1");
					fee.setITEM_CLASS(hiscp.getItem_class_p());
					fee.setITEM_NAME(hiscp.getItem_name_p());
					fee.setITEM_CODE(hiscp.getItem_code_p());
					fee.setITEM_SPEC(hiscp.getItem_spec());
					fee.setUNITS(hiscp.getUnits());
					fee.setREPETITION("1");
					
					fee.setORDERED_BY_DEPT(companyService.getDatadis("SQKS","1").get(0).getRemark());
					fee.setORDERED_BY_DOCTOR(user.getName());
					fee.setUSER_NAME(user.getWork_num());
					fee.setPERFORMED_BY(charitem.getPerform_dept());
					fee.setCLASS_ON_RCPT(hiscp.getCharge_item_class());
					fee.setITEM_PRICE(hiscp.getPrice().toString());
					fee.setRCPT_NO("");
					fee.setCHARGE_INDICATOR("0");
					fee.setCLASS_ON_RECKONING(hiscp.getClass_on_reckoning());
					fee.setSUBJ_CODE(hiscp.getSubj_code());
					fee.setPRICE_QUOTIETY("");
					fee.setITEM_PRICE(hiscp.getPrice().toString());
					fee.setCLINIC_NO(examInfo.getClinic_no());
					fee.setBILL_DATE("");
					fee.setBILL_NO("");
					fee.setSKINTEST("");// 皮试标志
					fee.setPRESC_PSNO(""); //皮试结果
					fee.setINSURANCE_FLAG("0"); //适用医保内外标志：0自费，1医保，2免费
					fee.setINSURANCE_CONSTRAINED_LEVEL("");// 公费用药级别
					fee.setSKIN_SAVE("");//皮试记录时间
					fee.setSKIN_START("");//皮试时间
					fee.setSKIN_BATH("");//药品批号
					fee.setExam_chargeItem_code(charitem.getItem_code());
					
					if("1".equals(wcf.getConfig_method().trim())){//东北国际
						fee.setAMOUNT(hiscp.getAmount().toString());
						double charges = hiscp.getAmount()*hiscp.getPrice();
						fee.setCHARGES(charges+"");//实收金额
						fee.setCOSTS(charges+"");  //计价金额
					}else if("5".equals(wcf.getConfig_method().trim())){//武汉黄冈
						fee.setAMOUNT((item.getDiscount()*0.1*hiscp.getCharge_item_no())+"");//数量
						double charges = item.getDiscount()*0.1*hiscp.getCharge_item_no()*hiscp.getPrice();//折后金额
						fee.setCHARGES(charges+"");//实收金额
						fee.setCOSTS(charges+"");  //计价金额
						fee.setBILL_NO(hiscp.getItem_code_c());//组套ID
						
						if("G".equals(item.getExam_indicator())){//自费标志
							fee.setINSURANCE_FLAG("1");//
						}else{
							fee.setINSURANCE_FLAG("0");
						}
						fee.setSERIAL_NO(examInfo.getCompany_id()+"");//单位ID
						fee.setRCPT_NO(examInfo.getBatch_id()+"");//批次ID
						fee.setUSER_NAME(customerInfo.getUser_name());
						fee.setYB_DOCTOR(user.getWork_num());
						fee.setVISIT_DATE(DateTimeUtil.shortFmt6(DateTimeUtil.parse()));
						
					}else{
						fee.setAMOUNT(hiscp.getAmount().toString());
						double charges = hiscp.getAmount()*hiscp.getPrice();
						fee.setCHARGES(charges+"");//实收金额
						fee.setCOSTS(charges+"");  //计价金额
					}
					feeList.add(fee);
				}
			}
		}
		
		//保存个人收费总表记录
		ChargingSummarySingle chargingSummarySingle = new ChargingSummarySingle();
		
		chargingSummarySingle.setExam_id(examInfo.getId());
		chargingSummarySingle.setReq_num(GetNumContral.getInstance().getParamNum("rcpt_num",user.getCenter_num()));
		chargingSummarySingle.setCharging_status("R");//预付费为R
		chargingSummarySingle.setAmount1(Double.parseDouble(String.format("%.2f", amount)));
		chargingSummarySingle.setAmount2(Double.parseDouble(String.format("%.2f", amount)));
		chargingSummarySingle.setDiscount(10.00);
		chargingSummarySingle.setCashier(user.getUserid());
		chargingSummarySingle.setCash_date(DateTimeUtil.parse());
		chargingSummarySingle.setIs_print_recepit("N");
		chargingSummarySingle.setIs_active("Y");
		chargingSummarySingle.setCreater(user.getUserid());
		chargingSummarySingle.setCreate_time(DateTimeUtil.parse());
		chargingSummarySingle.setUpdater(user.getUserid());
		chargingSummarySingle.setUpdate_time(DateTimeUtil.parse());
		chargingSummarySingle.setDaily_status("0");
		chargingSummarySingle.setExam_num(examInfo.getExam_num());
		chargingSummarySingle.setCenter_num(user.getCenter_num());
		
		this.pm.save(chargingSummarySingle);  //保存收费总表
		
		for(ChargingDetailSingle chargingDetailSingle : detailList){//遍历保存明细数据
			chargingDetailSingle.setSummary_id(chargingSummarySingle.getId());
			this.pm.save(chargingDetailSingle);
		}
		
		//开始发送申请
		
		FeeMessage fm = new FeeMessage();
		fm.setCenter_num(user.getCenter_num());
		fm.setREQ_NO(chargingSummarySingle.getReq_num());
		Fees fees = new Fees();
		fees.setPROJECT(feeList);
		fm.setPROJECTS(fees);
		
		FEESendMessage fsm= new FEESendMessage(fm);		
		FeeResultBody frb= new FeeResultBody();
		frb = fsm.feeSend(wcf.getConfig_url().trim(),wcf.getConfig_method().trim(), true);
		System.out.println(frb.getResultHeader().getTypeCode() + "-" + frb.getResultHeader().getText());
		
		if("AA".equals(frb.getResultHeader().getTypeCode())){//申请发送成功
			for(ExaminfoChargingItem examitem : itemList){
				examitem.setHis_req_status("Y");
				this.pm.update(examitem);
			}
			if("8".equals(wcf.getConfig_method().trim()) || "10".equals(wcf.getConfig_method().trim())){
				String num = frb.getControlActProcess().getList().get(0).getThird_req_id();
				chargingSummarySingle.setReq_num(num);
				this.pm.update(chargingSummarySingle);
			}
			return "ok-缴费申请发送成功!";
		}else{
			throw new ServiceException(frb.getResultHeader().getText());
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ExamResultChargingItemDTO> getykfpItemList(String examNum,String center_num) throws ServiceException {
		
		String sql ="select ec.id,i.id as item_id,ci.invoice_num,ci.invoice_class,ci.account_num,cs.req_num,i.item_code,i.item_name,"
				+ " i.amount as item_amount,cd.amount1 as amount, cd.discount,ec.exam_status,a.amount as all_amount,ci.invoice_amount,ec.itemnum,"
				+ " u.chi_name as creater,CONVERT(varchar(20),cs.cash_date,23) as create_time,"
				+ " ec.is_application,cs.charging_status,ec.personal_pay from charging_invoice_single ci"
				+ " inner join charging_summary_single cs on cs.invoice_id=ci.id"
				+ " inner join charging_detail_single cd on cd.summary_id=cs.id"
				+ " inner join exam_info e on cs.exam_id=e.id "
				+ " inner join charging_item i on cd.charging_item_code=i.item_code "
				+ " left join user_usr u on cs.cashier = u.id "
				+ " left join examinfo_charging_item ec on ec.exam_num=e.exam_num "
				+ " and ec.charging_item_code=cd.charging_item_code "
				+ " left join (SELECT sum(case when s.charging_status = 'M' then -amount2 else amount2 end) as amount,s.req_num "
				+ " FROM exam_info e,charging_summary_single s where s.exam_num = e.exam_num and e.exam_num = '"+examNum+"' and s.center_num='"+center_num+"' "
				+ " and s.is_print_recepit = 'Y' and s.is_active = 'Y' group by s.req_num) a on a.req_num = cs.req_num"
				+ " where ec.isActive='Y' and ec.pay_status='Y' and ec.center_num = '"+center_num+"' and cs.is_active = 'Y' and cs.merge_charge = 0"
				+ " and e.exam_num='"+examNum+"' and cs.center_num='"+center_num+"' order by ci.account_num,cs.req_num";
		 
		List<ExamResultChargingItemDTO> list = this.jqm.getList(sql, ExamResultChargingItemDTO.class);
		List<ExamResultChargingItemDTO> tempList = new ArrayList<ExamResultChargingItemDTO>();
		List<ExamResultChargingItemDTO> tempList1 = new ArrayList<ExamResultChargingItemDTO>();
		for (ExamResultChargingItemDTO examResult: list) {
			if("M".equals(examResult.getCharging_status())){
				tempList.add(examResult);
			}
		}
		for(ExamResultChargingItemDTO tempDto : tempList){
			for (int i=0;i<list.size();i++) {
				if(tempDto.getReq_num().equals(list.get(i).getReq_num()) && tempDto.getItem_id() == list.get(i).getItem_id()){
					tempList1.add(list.get(i));
				}
			}
		}
		list.removeAll(tempList1);
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ExamResultChargingItemDTO> getyksjItemList(String examNum,String center_num) throws ServiceException {
		
		String sql =" select ec.id,i.id as item_id,cs.req_num,i.item_code,i.item_name,i.amount as item_amount,cd.amount1 as amount,a.amount as all_amount,"
				   +" cd.discount,ec.exam_status,u.chi_name as creater,CONVERT(varchar(20),cs.cash_date,23) as create_time,ec.is_application,"
				   + "cs.charging_status,ec.itemnum,ec.personal_pay,ec.calculation_amount,ec.calculation_rate "
				   +" from exam_info e,examinfo_charging_item ec,charging_item i,charging_detail_single cd,"
				   +" charging_summary_single cs left join user_usr u on cs.cashier = u.id"
				   +" left join (SELECT sum(case when s.charging_status = 'M' then -amount2 else amount2 end) as amount,s.req_num "
				   +" FROM exam_info e,charging_summary_single s where s.exam_num = e.exam_num and e.exam_num = '"+examNum+"' and s.center_num='"+center_num+"' "
				   + "and s.is_print_recepit = 'N' and s.is_active = 'Y' group by s.req_num) a on a.req_num = cs.req_num"
				   +" where e.exam_num = ec.exam_num and e.id = cs.exam_id and "
				   +" cs.id = cd.summary_id and cd.charging_item_code = ec.charging_item_code and cd.charging_item_code = i.item_code"
				   +" and ec.charging_item_code = i.item_code and cs.is_active = 'Y' and ec.pay_status != 'M' and ec.isActive = 'Y' and ec.center_num = '"+center_num+"' "
				   + "and cs.is_print_recepit ='N' and cs.merge_charge = 0 and e.exam_num = '"+examNum+"' and cs.center_num='"+center_num+"' order by cs.req_num";
		
		List<ExamResultChargingItemDTO> list = this.jqm.getList(sql, ExamResultChargingItemDTO.class);
		List<ExamResultChargingItemDTO> tempList = new ArrayList<ExamResultChargingItemDTO>();
		List<ExamResultChargingItemDTO> tempList1 = new ArrayList<ExamResultChargingItemDTO>();
		for (ExamResultChargingItemDTO examResult: list) {
			if("M".equals(examResult.getCharging_status())){
				tempList.add(examResult);
			}
		}
		for(ExamResultChargingItemDTO tempDto : tempList){
			for (int i=0;i<list.size();i++) {
				if(tempDto.getReq_num().equals(list.get(i).getReq_num()) && tempDto.getItem_id() == list.get(i).getItem_id()){
					tempList1.add(list.get(i));
				}
			}
		}
		list.removeAll(tempList1);
		return list;
	}

	@Override
	public UserFeeDTO saveCollectFeesYiBao(String rcpt,String operator, Date visit_date, String invoice_num, String req_num,
			Double amount,long summaryId) throws ServiceException {
		UserFeeDTO uf=new UserFeeDTO();
		String sql3 = "select d.id from data_dictionary d where "
				+ "d.data_code = 'SFFSLX' and d.isActive = 'Y' and d.remark = 'his收费'";
		List<DataDictionaryDTO> dicList = this.jqm.getList(sql3, DataDictionaryDTO.class);
		if (dicList.size() == 0) {
			uf.setFlags(false);
			uf.setError("未匹配到His收费方式,请检查His收费方式编码是否对应");
            return uf;
		}
		long sffslx = dicList.get(0).getId();
		
		String sql1 = "select u.id,u.chi_name,u.work_num from user_usr u where u.work_num = '"+ operator + "'";
		List<UserInfoDTO> userList = this.jqm.getList(sql1, UserInfoDTO.class);
		if (userList.size() == 0) {
			uf.setFlags(false);
			uf.setError("未匹配到收费员信息!");
            return uf;
		}
		
		/** 保存HIS收费方式表*/
//		HisChargingWaySingle hisChargingWaySingle = new HisChargingWaySingle();
//
//		hisChargingWaySingle.setAccount_num(req_num);
//		hisChargingWaySingle.setCharging_way(dicList.get(0).getId() + "");
//		hisChargingWaySingle.setAmount(amount);
//		hisChargingWaySingle.setCreater(Long.valueOf(userList.get(0).getId()));
//		hisChargingWaySingle.setCreate_time(visit_date);
//		hisChargingWaySingle.setUpdater(Long.valueOf(userList.get(0).getId()));
//		hisChargingWaySingle.setUpdate_time(visit_date);
//		this.pm.save(hisChargingWaySingle);
		
		String sqlhisWay = "insert into his_charging_way_single (account_num,charging_way,amount,creater,create_time,updater,update_time) values('"+req_num+"','"+dicList.get(0).getId()+"','"+amount
				+"','"+userList.get(0).getId()+"','"+visit_date+"','"+userList.get(0).getId()+"','"+visit_date+"')";
		
		this.jpm.executeSql(sqlhisWay);
		
		
		/**保存发票表**/
//		ChargingInvoiceSingle chargingInvoiceSingle = new ChargingInvoiceSingle();
//
//		chargingInvoiceSingle.setTitle_info("");
//		chargingInvoiceSingle.setInvoice_type("");
//		chargingInvoiceSingle.setInvoice_num(invoice_num);
//		chargingInvoiceSingle.setInvoice_amount(amount);
//		chargingInvoiceSingle.setInvoice_status("Y");
//		chargingInvoiceSingle.setInvoice_maker(Long.valueOf(userList.get(0).getId()));
//		chargingInvoiceSingle.setInvoice_time(visit_date);
//		chargingInvoiceSingle.setCreate_time(visit_date);
//		chargingInvoiceSingle.setCreater(Long.valueOf(userList.get(0).getId()));
//		chargingInvoiceSingle.setUpdate_time(visit_date);
//		chargingInvoiceSingle.setUpdater(Long.valueOf(userList.get(0).getId()));
//		chargingInvoiceSingle.setAccount_num(rcpt);
//		this.pm.save(chargingInvoiceSingle);
		
		String charingInvoicesql = "insert into charging_invoice_single (title_info,invoice_type,invoice_num,invoice_amount,invoice_status,invoice_maker"
				                 + ",invoice_time,creater,create_time,updater,update_time,account_num,exam_type) values('','','"+invoice_num+"','"+amount+"','Y','"+userList.get(0).getId()
				                 + "','"+visit_date+"','"+userList.get(0).getId()+"','"+visit_date+"','"+userList.get(0).getId()+"','"+visit_date+"','"+rcpt+"','G')";
		this.jpm.executeSql(charingInvoicesql);
		
		/**保存结算总记录与发票关系表**/
		String teamAmount = "insert into team_invoice_account (account_num,acc_num,auditor,audit_date) values('"+rcpt+"','"+req_num+"','"+userList.get(0).getId()+"','"+visit_date+"')";
		this.jpm.executeSql(teamAmount);
		
		
		String invoiceIdsql = "select c.id from charging_invoice_single c where c.account_num = '"+rcpt+"'";
		
		List<ChargingInvoiceSingleDTO> invoiceidlist = this.jqm.getList(invoiceIdsql, ChargingInvoiceSingleDTO.class);
		
		long invoiceid = 0;
		if(invoiceidlist.size() > 0){
			invoiceid = invoiceidlist.get(0).getId();
		}
		
		/**更新结算总记录信息**/
//		chargingSummarySingle.setCharging_status("Y");
//		chargingSummarySingle.setIs_print_recepit("Y");
//		chargingSummarySingle.setCashier(Long.valueOf(userList.get(0).getId()));
//		chargingSummarySingle.setCash_date(visit_date);
//		chargingSummarySingle.setUpdate_time(visit_date);
//		chargingSummarySingle.setUpdater(Long.valueOf(userList.get(0).getId()));
//
//		chargingSummarySingle.setInvoice_id(chargingInvoiceSingle.getId());
//		this.pm.update(chargingSummarySingle);
		
		String summaryup = "update charging_summary_single set invoice_id = '"+invoiceid+"',charging_status = 'Y',"
				+ "is_print_recepit = 'Y',cashier='"+userList.get(0).getId()+"',cash_date='"+visit_date+"',updater='"+userList.get(0).getId()+"',update_time='"+visit_date+"' where req_num = '"+req_num+"'";
		this.jpm.executeSql(summaryup);
		
		/**保存收费方式信息**/
//		ChargingWaySingle chargingWaySingle = new ChargingWaySingle();
//		chargingWaySingle.setSummary_id(chargingSummarySingle.getId());
//		chargingWaySingle.setCharging_way(sffslx + "");
//		chargingWaySingle.setAmount(chargingSummarySingle.getAmount2());
//		chargingWaySingle.setCreater(Long.valueOf(userList.get(0).getId()));
//		chargingWaySingle.setCreate_time(visit_date);
//		chargingWaySingle.setUpdater(Long.valueOf(userList.get(0).getId()));
//		chargingWaySingle.setUpdate_time(visit_date);
//		this.pm.save(chargingWaySingle);
		
		String charingWaySql = "insert into charging_way_single (summary_id,charging_way,amount,creater,create_time,updater,update_time,account_num) values('"+summaryId+"','"+sffslx+"','"+amount
							 + "','"+userList.get(0).getId()+"','"+visit_date+"','"+userList.get(0).getId()+"','"+visit_date+"','"+rcpt+"')";
		this.jpm.executeSql(charingWaySql);
		
		
		
		String sql4 = " select cs.exam_num, cd.charging_item_id" + " from ChargingSummarySingle cs,"
				+ "ChargingDetailSingle cd where cd.summary_id=cs.id " + " and cs.req_num ='"+req_num+"'";
		List iteList = new ArrayList();
		iteList = this.qm.find(sql4);
		for (int j = 0; j < iteList.size(); j++) {
			Object[] object = (Object[]) iteList.get(j);
			String exam_num = (String) object[0];
			long ciid = (long) object[1];
			String sql2 = "from ExaminfoChargingItem c where exam_num = '" + exam_num + "' and charge_item_id=" + ciid
					+ " and pay_status='N' and isActive='Y' ";
			List<ExaminfoChargingItem> eciList = this.qm.find(sql2);
			if (eciList.size() > 0) {
				ExaminfoChargingItem ecinew = new ExaminfoChargingItem();
				ecinew = eciList.get(0);
				ecinew.setPay_status("Y");
				this.pm.update(ecinew);
			}
		}
		
		uf.setUsername(userList.get(0).getChi_Name());
		uf.setWork_num(userList.get(0).getWork_num());
		uf.setReq_nums(req_num);
		uf.setFlags(true);
		uf.setError("保存成功");
		return uf;
	}

	@Override
	public String invalidInvoiceGe(String invoice_nums,UserDTO user,String invoice_class) throws ServiceException {
		
		String sql = "from ChargingInvoiceSingle c where c.invoice_num in("+invoice_nums+") and c.invoice_class = '"+invoice_class+"'";
		List<ChargingInvoiceSingle> list = this.qm.find(sql);
		List<NullifyInvoice> zuofeilist = this.qm.find("from NullifyInvoice where invoice_num in ("+invoice_nums+") and invoice_class = '"+invoice_class+"'");
		if(zuofeilist.size() > 0){
			return "error-存在已作废发票，不能再次作废，请查询最新发票信息!";
		}
		for(ChargingInvoiceSingle chargingInvoiceSingle : list){
//			chargingInvoiceSingle.setInvoice_status("N");
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
            nullifyInvoice.setAccount_num(GetNumContral.getInstance().getParamNum("account_num",user.getCenter_num()));
            nullifyInvoice.setCenter_num(user.getCenter_num());
            this.pm.save(nullifyInvoice);
			
			List<ChargingSummarySingle> summarySingleList = this.qm.find("from ChargingSummarySingle where invoice_id = "+chargingInvoiceSingle.getId());
			
			for(ChargingSummarySingle chargingSummarySingle : summarySingleList){
				
				chargingSummarySingle.setInvoice_id(0);
				chargingSummarySingle.setIs_print_recepit("N");
				
				this.pm.update(chargingSummarySingle);
			}
		}
		
		return "ok-作废发票成功!";
	}

	@Override
	@SuppressWarnings("unchecked")
	public String chexiaoshoufei(CollectFeesModel model, UserDTO user) throws ServiceException {
		String sql ="select cd.card_num,sum(cd.amount) amount from card_deal cd where cd.examinfo_id = " + model.getExam_id()
					+ " and cd.deal_type in (6,7) group by cd.card_num";
		List<CardInfoDTO> listCard = this.jqm.getList(sql, CardInfoDTO.class);
		if("Y".equals(model.getIsPrintRecepit())){//按收据单号退费
			List<ChargingInvoiceSingle> invoiceList = this.qm.find("from ChargingInvoiceSingle where account_num in ("+model.getAccount_num()+")");
			
			
			for(ChargingInvoiceSingle chargingInvoiceSingle : invoiceList){
				String now_date = DateTimeUtil.getDate2();
				String date = DateTimeUtil.shortFmt3(chargingInvoiceSingle.getInvoice_time());
				
				if(!now_date.equals(date)){
					return "error-收据号为"+chargingInvoiceSingle.getAccount_num()+"不是当天收费，不能撤销收费!";
				}
				List<NullifyInvoice> zuofeilist = this.qm.find("from NullifyInvoice where invoice_num ='"+chargingInvoiceSingle.getInvoice_num()+"' and invoice_class = '"+chargingInvoiceSingle.getInvoice_class()+"'");
				if(zuofeilist.size() > 0){
					return "error-该发票号（"+chargingInvoiceSingle.getInvoice_num()+"）已作废，不能再次作废，请先查询!";
				}
			}
			
			for(ChargingInvoiceSingle chargingInvoiceSingle : invoiceList){

//				chargingInvoiceSingle.setInvoice_status("N");
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
				cardDeal.setExam_num(model.getExam_num());
		        this.pm.save(cardDeal);
		        
		        //保存作废发票流水表
	            NullifyInvoice nullifyInvoice = new NullifyInvoice();
	            nullifyInvoice.setInvoice_num(chargingInvoiceSingle.getInvoice_num());
	            nullifyInvoice.setInvoice_status("N");
	            nullifyInvoice.setInvoice_class(chargingInvoiceSingle.getInvoice_class());
	            nullifyInvoice.setCreater(user.getUserid());
	            nullifyInvoice.setCreate_time(DateTimeUtil.parse());
	            nullifyInvoice.setDaily_status("0");
	            nullifyInvoice.setAccount_num(GetNumContral.getInstance().getParamNum("account_num",user.getCenter_num()));
	            nullifyInvoice.setCenter_num(user.getCenter_num());
	            this.pm.save(nullifyInvoice);
				
				List<ChargingSummarySingle> summarySingleList = this.qm.find("from ChargingSummarySingle where invoice_id = "+chargingInvoiceSingle.getId());
				
				for(ChargingSummarySingle chargingSummary : summarySingleList){
					
					chargingSummary.setIs_active("N");
					this.pm.update(chargingSummary);
					
					ChargingSummarySingle chargingSummarySingle = new ChargingSummarySingle();
					
					chargingSummarySingle.setReq_num(chargingSummary.getReq_num());
					chargingSummarySingle.setExam_id(chargingSummary.getExam_id());
					chargingSummarySingle.setCharging_status("N");//撤销收费状态
					chargingSummarySingle.setAmount1(chargingSummary.getAmount1());
					chargingSummarySingle.setAmount2(chargingSummary.getAmount2());
					chargingSummarySingle.setDiscount(chargingSummary.getDiscount());
					chargingSummarySingle.setCashier(user.getUserid());
					chargingSummarySingle.setCash_date(DateTimeUtil.parse());
					chargingSummarySingle.setIs_print_recepit("Y");
					chargingSummarySingle.setIs_active("N");
					chargingSummarySingle.setCreater(user.getUserid());
					chargingSummarySingle.setCreate_time(DateTimeUtil.parse());
					chargingSummarySingle.setUpdater(user.getUserid());
					chargingSummarySingle.setUpdate_time(DateTimeUtil.parse());
					chargingSummarySingle.setInvoice_id(chargingInvoiceSingle.getId());
					chargingSummarySingle.setDaily_status("0");
					chargingSummarySingle.setExam_num(model.getExam_num());
					chargingSummarySingle.setCenter_num(user.getCenter_num());
					
					this.pm.save(chargingSummarySingle); //保存退费信息总表
					
					List<ChargingDetailSingle> detailSingleList = this.qm.find("from ChargingDetailSingle where summary_id = "+chargingSummary.getId());
					
					for(ChargingDetailSingle detailSingle : detailSingleList){
						ChargingDetailSingle chargingDetailSingle = new ChargingDetailSingle();
						
						chargingDetailSingle.setSummary_id(chargingSummarySingle.getId());
						chargingDetailSingle.setAmount(detailSingle.getAmount());
						chargingDetailSingle.setAmount1(detailSingle.getAmount1());
						chargingDetailSingle.setCharging_item_id(detailSingle.getCharging_item_id());
						chargingDetailSingle.setDiscount(detailSingle.getDiscount());
						chargingDetailSingle.setCreater(user.getUserid());
						chargingDetailSingle.setCreate_time(DateTimeUtil.parse());
						chargingDetailSingle.setUpdater(user.getUserid());
						chargingDetailSingle.setUpdate_time(DateTimeUtil.parse());
						
						this.pm.save(chargingDetailSingle);  //保存结算明细
						
						List<ExaminfoChargingItem> examinfoChargingItemList = this.qm.find("from ExaminfoChargingItem where pay_status != 'M'"
								+ " and isActive = 'Y' and center_num = '"+user.getCenter_num()+"' and exam_num = '" + chargingSummarySingle.getExam_num() 
								+"' and charge_item_id = "+chargingDetailSingle.getCharging_item_id());
						
						if(examinfoChargingItemList.size() != 0){
							ExaminfoChargingItem examItem = examinfoChargingItemList.get(0);
							examItem.setPay_status("N");
							this.pm.update(examItem);
						}
					}
					
					List<ChargingWaySingle> waySingleList = this.qm.find("from ChargingWaySingle where summary_id = " +chargingSummary.getId());
					boolean isCard = false;
					Double amount = 0.0;
					for(ChargingWaySingle WaySingle : waySingleList){
						ChargingWaySingle chargingWaySingle = new ChargingWaySingle();
						chargingWaySingle.setSummary_id(chargingSummarySingle.getId());
						chargingWaySingle.setAmount(WaySingle.getAmount());
						chargingWaySingle.setCharging_way(WaySingle.getCharging_way());
						chargingWaySingle.setCreater(user.getUserid());
						chargingWaySingle.setCreate_time(DateTimeUtil.parse());
						chargingWaySingle.setUpdater(user.getUserid());
						chargingWaySingle.setUpdate_time(DateTimeUtil.parse());
						if(WaySingle.getCharging_way().equals("122")){
							isCard = true;
							amount = WaySingle.getAmount();
						}
						this.pm.save(chargingWaySingle);
					}
					
					if(isCard){//会员刷卡，保存卡消费信息
						for(CardInfoDTO carddto : listCard){
							List<CardInfo> cardlist = this.qm.find("from CardInfo c where c.card_num = '"+carddto.getCard_num()+"'");
							if(cardlist.size() > 0 && carddto.getAmount() > 0 && amount > 0){
								CardInfo cardInfo = cardlist.get(0);
								CardDeal cardDeals = new CardDeal();
								cardDeals.setCard_num(cardInfo.getCard_num());
								cardDeals.setCard_count(cardInfo.getCard_count());
								cardDeals.setOld_amount(cardInfo.getAmount());
								cardDeals.setSummary_id(chargingSummarySingle.getId());
								cardDeals.setExaminfo_id(model.getExam_id());
								cardDeals.setDeal_type("7");
								cardDeals.setCreater(user.getUserid());
								cardDeals.setDeal_date(DateTimeUtil.getDate2());
								cardDeals.setDeal_time(DateTimeUtil.parse());
								cardDeals.setTrancode("001");
								if(carddto.getAmount() > amount){
									cardDeals.setAmount(-amount);
									cardInfo.setAmount(amount+cardInfo.getAmount());
									amount = 0.0;
								}else{
									cardDeals.setAmount(-carddto.getAmount());
									cardInfo.setAmount(carddto.getAmount()+cardInfo.getAmount());
									cardInfo.setCard_count(cardInfo.getCard_count()-1);
									amount = amount - carddto.getAmount();
								}
								cardDeals.setExam_num(model.getExam_num());
								this.pm.save(cardDeals);
								this.pm.update(cardInfo);
							}
						}
					}
				}
			}
		}else{//按申请单号退费
			List<ChargingSummarySingle> sumarySingleList = this.qm.find("from ChargingSummarySingle where req_num in ("+model.getReq_nums()+")");
			for(ChargingSummarySingle chargingSummarySingle : sumarySingleList){
				String now_date = DateTimeUtil.getDate2();
				String date = DateTimeUtil.shortFmt3(chargingSummarySingle.getCash_date());
				
				if(!now_date.equals(date)){
					return "error-申请单号为"+chargingSummarySingle.getReq_num()+"不是当天收费，不能撤销收费!";
				}
				if(chargingSummarySingle.getIs_active().equals("N")){
					return "error-申请单号为"+chargingSummarySingle.getReq_num()+"收费记录已无效，请先查询最新收费记录!";
				}
			}
			
			for(ChargingSummarySingle oldchargingSummarySingle : sumarySingleList){
				oldchargingSummarySingle.setIs_active("N");
				this.pm.update(oldchargingSummarySingle); //将原来的总表置为N
				
				ChargingSummarySingle chargingSummarySingle = new ChargingSummarySingle();
				
				chargingSummarySingle.setReq_num(oldchargingSummarySingle.getReq_num());
				chargingSummarySingle.setExam_id(oldchargingSummarySingle.getExam_id());
				chargingSummarySingle.setCharging_status("N");
				chargingSummarySingle.setAmount1(oldchargingSummarySingle.getAmount1());
				chargingSummarySingle.setAmount2(oldchargingSummarySingle.getAmount2());
				chargingSummarySingle.setDiscount(oldchargingSummarySingle.getDiscount());
				chargingSummarySingle.setCashier(user.getUserid());
				chargingSummarySingle.setCash_date(DateTimeUtil.parse());
				chargingSummarySingle.setIs_print_recepit("N");
				chargingSummarySingle.setIs_active("N");
				chargingSummarySingle.setCreater(user.getUserid());
				chargingSummarySingle.setCreate_time(DateTimeUtil.parse());
				chargingSummarySingle.setUpdater(user.getUserid());
				chargingSummarySingle.setUpdate_time(DateTimeUtil.parse());
				chargingSummarySingle.setDaily_status("0");
				chargingSummarySingle.setExam_num(model.getExam_num());
				chargingSummarySingle.setCenter_num(user.getCenter_num());
				
				this.pm.save(chargingSummarySingle); //保存退费信息总表
				
				List<ChargingDetailSingle> detailSingleList = this.qm.find("from ChargingDetailSingle where summary_id = "+oldchargingSummarySingle.getId());
				
				for(ChargingDetailSingle detailSingle : detailSingleList){
					ChargingDetailSingle chargingDetailSingle = new ChargingDetailSingle();
					
					chargingDetailSingle.setSummary_id(chargingSummarySingle.getId());
					chargingDetailSingle.setAmount(detailSingle.getAmount());
					chargingDetailSingle.setAmount1(detailSingle.getAmount1());
					chargingDetailSingle.setCharging_item_id(detailSingle.getCharging_item_id());
					chargingDetailSingle.setDiscount(detailSingle.getDiscount());
					chargingDetailSingle.setCreater(user.getUserid());
					chargingDetailSingle.setCreate_time(DateTimeUtil.parse());
					chargingDetailSingle.setUpdater(user.getUserid());
					chargingDetailSingle.setUpdate_time(DateTimeUtil.parse());
					
					this.pm.save(chargingDetailSingle);  //保存结算明细
					
					List<ExaminfoChargingItem> examinfoChargingItemList = this.qm.find("from ExaminfoChargingItem where pay_status != 'M'"
							+ " and isActive = 'Y' and center_num = '"+user.getCenter_num()+"' and exam_num = '" + chargingSummarySingle.getExam_num() 
							+"' and charge_item_id = "+chargingDetailSingle.getCharging_item_id());
					
					if(examinfoChargingItemList.size() != 0){
						ExaminfoChargingItem examItem = examinfoChargingItemList.get(0);
						
						examItem.setPay_status("N");
						this.pm.update(examItem);
					}
				}
				
				List<ChargingWaySingle> waySingleList = this.qm.find("from ChargingWaySingle where summary_id = " +oldchargingSummarySingle.getId());
				boolean isCard = false;
				Double amount = 0.0;
				for(ChargingWaySingle WaySingle : waySingleList){
					ChargingWaySingle chargingWaySingle = new ChargingWaySingle();
					chargingWaySingle.setSummary_id(chargingSummarySingle.getId());
					chargingWaySingle.setAmount(WaySingle.getAmount());
					chargingWaySingle.setCharging_way(WaySingle.getCharging_way());
					chargingWaySingle.setCreater(user.getUserid());
					chargingWaySingle.setCreate_time(DateTimeUtil.parse());
					chargingWaySingle.setUpdater(user.getUserid());
					chargingWaySingle.setUpdate_time(DateTimeUtil.parse());
					if(WaySingle.getCharging_way().equals("122")){
						isCard = true;
						amount = WaySingle.getAmount();
					}
					this.pm.save(chargingWaySingle);
				}
				if(isCard){//会员刷卡，保存卡消费信息
					for(CardInfoDTO carddto : listCard){
						List<CardInfo> cardlist = this.qm.find("from CardInfo c where c.card_num = '"+carddto.getCard_num()+"'");
						if(cardlist.size() > 0 && carddto.getAmount() > 0 && amount > 0){
							CardInfo cardInfo = cardlist.get(0);
							CardDeal cardDeals = new CardDeal();
							cardDeals.setCard_num(cardInfo.getCard_num());
							cardDeals.setCard_count(cardInfo.getCard_count());
							cardDeals.setOld_amount(cardInfo.getAmount());
							cardDeals.setSummary_id(chargingSummarySingle.getId());
							cardDeals.setExaminfo_id(model.getExam_id());
							cardDeals.setDeal_type("7");
							cardDeals.setCreater(user.getUserid());
							cardDeals.setDeal_date(DateTimeUtil.getDate2());
							cardDeals.setDeal_time(DateTimeUtil.parse());
							cardDeals.setTrancode("001");
							if(carddto.getAmount() > amount){
								cardDeals.setAmount(-amount);
								cardInfo.setAmount(amount+cardInfo.getAmount());
								amount = 0.0;
							}else{
								cardDeals.setAmount(-carddto.getAmount());
								cardInfo.setAmount(carddto.getAmount()+cardInfo.getAmount());
								cardInfo.setCard_count(cardInfo.getCard_count()-1);
								amount = amount - carddto.getAmount();
							}
							cardDeals.setExam_num(model.getExam_num());
							this.pm.save(cardDeals);
							this.pm.update(cardInfo);
						}
					}
				}
			}
		}
		return "ok-撤销结算成功!";
	}
	
	/**
	 * 退费删除lis与pacs信息
	     * @Title: delLisAndPacsDetail   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param exam_id
	     * @param: @param charingid      
	     * @return: void      
	     * @throws
	 */
	private void delLisAndPacsDetail(ExamInfo examInfo,long charingid) throws ServiceException {
		List<ChargingItem> cList = this.qm.find("from ChargingItem c where c.id = "+charingid);
		ChargingItem chargingItem = null;
		if(cList.size() != 0){
			chargingItem = cList.get(0);
		}else{
			return;
		}
		List<SampleExamDetail> samList = this.qm.find("from SampleExamDetail s where s.exam_num = '"+examInfo.getExam_num() +"' and s.sample_id = "+chargingItem.getSam_demo_id()); 
		for(SampleExamDetail sampleExamDetail : samList){
			List<ExamResultChargingItem> examList = this.qm.find("from ExamResultChargingItem e where e.charging_id = "+charingid+" and e.bar_code = '"+sampleExamDetail.getSample_barcode()+"' and e.result_type = 'sample'");
			if(examList.size() != 0 ){
				this.pm.remove(examList.get(0));
			}
			List<ExamResultChargingItem> examrList = this.qm.find("from ExamResultChargingItem e where e.bar_code = '"+sampleExamDetail.getSample_barcode()+"' and e.result_type = 'sample' and e.isActive = 'Y' ");
			if(examrList.size() == 0){
				this.pm.remove(sampleExamDetail);
			}
		}
		
		List<PacsSummary> pacsList = this.qm.find("from PacsSummary p where p.examinfo_num = '"+examInfo.getExam_num()+"' and p.examinfo_sampleId ="+chargingItem.getSam_demo_id());
		if(pacsList.size() != 0){
			PacsSummary pacsSummary = pacsList.get(0);
			List<Pacsdetail> detailList = this.qm.find("from Pacsdetail p where p.summary_id = "+pacsSummary.getId()+" and p.chargingItem_num = '"+chargingItem.getItem_code()+"'");
			if(detailList.size() != 0 ){
				this.pm.remove(detailList.get(0));
			}
			List<Pacsdetail> pdetailList = this.qm.find("from Pacsdetail p where p.summary_id = "+pacsSummary.getId());
			if(pdetailList.size() == 0){
				this.pm.remove(pacsSummary);
			}
		}
	}
	public void saveWuxaiofapiaozuifei(CollectFeesModel model, UserDTO user) throws ServiceException {
		List<UserInvoice> li = this.qm.find("from UserInvoice u where u.user_id = "+model.getUser_id()+" and u.invoice_class ='"+model.getInvoice_class()+"'");
		li.get(0).setInvoice_num_used(model.getInvoice_num_used());
		UserInvoice u = li.get(0);
		this.pm.update(u);
		
		NullifyInvoice nullifyInvoice = new NullifyInvoice();
        nullifyInvoice.setInvoice_num(model.getInvoice_num_used());
        nullifyInvoice.setInvoice_status("B");
        nullifyInvoice.setInvoice_class(model.getInvoice_class());
        nullifyInvoice.setCreater(user.getUserid());
        nullifyInvoice.setCreate_time(DateTimeUtil.parse());
        nullifyInvoice.setDaily_status("0");
        nullifyInvoice.setAccount_num(GetNumContral.getInstance().getParamNum("account_num",user.getCenter_num()));
        nullifyInvoice.setCenter_num(user.getCenter_num());
        this.pm.save(nullifyInvoice);
	}

	@Override
	public ExamInfoUserDTO getMergeChargeByExamNum(String exam_num, String center_num) throws ServiceException {
		String sql = "select e.status,e.id,c.arch_num,c.id_num,e.exam_num,e.age,c.user_name,c.sex,e.phone,e.address,e.introducer,"
				+ "convert(varchar(50),e.join_date,23) as join_date,e.exam_type,dbo.GetExamSetNameByExamID(e.exam_num) as set_name,"
				+ "v.com_name as company,e.is_guide_back,e.is_need_guide,e.is_need_barcode,(select sum(ec.personal_pay) "
				+ "from examinfo_charging_item ec where ec.exam_num = e.exam_num and ec.isActive='Y' and ec.pay_status = 'N' and ec.center_num = '"+center_num+"') personal_pay "
				+ ",(select count(ec.id) from examinfo_charging_item ec where ec.exam_num = e.exam_num and ec.isActive='Y' and ec.pay_status = 'N' and ec.center_num = '"+center_num+"') wpacs  "
				+ "from customer_info c,exam_info e"
				+ " left join company_info v on v.id=e.company_id "
				+ " where e.customer_id = c.id "
				+ "and e.is_Active = 'Y' and e.exam_num = '"+exam_num+"'";
		List<ExamInfoUserDTO> list = this.jqm.getList(sql, ExamInfoUserDTO.class);
		if(list.size() > 0 )
			return list.get(0);
		return null;
	}

	@Override
	public CollectFeesResult saveMergeChargeInfo(CollectFeesModel model, UserDTO user, String invoice_class)
			throws ServiceException {
		CollectFeesResult result = new CollectFeesResult();
		
		ChargingInvoiceSingle chargingInvoiceSingle = null;
		if("Y".equals(model.getIsPrintRecepit())){
			chargingInvoiceSingle = new ChargingInvoiceSingle();
			
			chargingInvoiceSingle.setTitle_info(model.getTitle_info());
			chargingInvoiceSingle.setInvoice_num(model.getInvoice_num());
			chargingInvoiceSingle.setInvoice_type(model.getInvoice_type());
			chargingInvoiceSingle.setInvoice_status("Y");
			chargingInvoiceSingle.setInvoice_maker(user.getUserid());
			chargingInvoiceSingle.setInvoice_amount(model.getAmount2());
			chargingInvoiceSingle.setInvoice_time(DateTimeUtil.parse());
			chargingInvoiceSingle.setCreater(user.getUserid());
			chargingInvoiceSingle.setCreate_time(DateTimeUtil.parse());
			chargingInvoiceSingle.setUpdater(user.getUserid());
			chargingInvoiceSingle.setUpdate_time(DateTimeUtil.parse());
			chargingInvoiceSingle.setAccount_num(GetNumContral.getInstance().getParamNum("account_num",user.getCenter_num()));
			chargingInvoiceSingle.setExam_type("G");
			chargingInvoiceSingle.setBarchid(model.getExam_id());
			chargingInvoiceSingle.setCenter_num(user.getCenter_num());
			chargingInvoiceSingle.setInvoice_class(invoice_class);
			chargingInvoiceSingle.setDaily_status("0");
			
			this.pm.save(chargingInvoiceSingle);
			
			//保存发票记录表
			CardDeal cardDeal= new CardDeal();
			cardDeal.setAmount(model.getAmount2());
			cardDeal.setCard_num(chargingInvoiceSingle.getInvoice_num());
			cardDeal.setTrancode("002");
			cardDeal.setCreater(user.getUserid());
			cardDeal.setDeal_type("0");
			cardDeal.setDeal_date(DateTimeUtil.getDate2());
			cardDeal.setDeal_time(DateTimeUtil.parse());
			cardDeal.setOld_amount(model.getAmount2());
			cardDeal.setInvoice_class(chargingInvoiceSingle.getInvoice_class());
            this.pm.save(cardDeal);
			
		}
		for(ExamResultChargingItemDTO itemdto : model.getExamInfoCharingItem()){
			ChargingSummarySingle chargingSummarySingle = new ChargingSummarySingle();
			//1收费总记录
			chargingSummarySingle.setExam_id(itemdto.getId());
			chargingSummarySingle.setReq_num(GetNumContral.getInstance().getParamNum("rcpt_num",user.getCenter_num()));
			chargingSummarySingle.setCharging_status("Y");
			chargingSummarySingle.setAmount1(itemdto.getAmount());
			chargingSummarySingle.setAmount2(itemdto.getAmount());
			chargingSummarySingle.setDiscount(model.getDiscount());
			chargingSummarySingle.setCashier(user.getUserid());
			chargingSummarySingle.setCash_date(DateTimeUtil.parse());
			chargingSummarySingle.setIs_print_recepit(model.getIsPrintRecepit());
			chargingSummarySingle.setIs_active("Y");
			chargingSummarySingle.setCreater(user.getUserid());
			chargingSummarySingle.setCreate_time(DateTimeUtil.parse());
			chargingSummarySingle.setUpdater(user.getUserid());
			chargingSummarySingle.setUpdate_time(DateTimeUtil.parse());
			chargingSummarySingle.setDaily_status("0");
			chargingSummarySingle.setMerge_charge(1);
			if(chargingInvoiceSingle != null){
				chargingSummarySingle.setInvoice_id(chargingInvoiceSingle.getId());
			}
		
			this.pm.save(chargingSummarySingle);  //保存收费总表
		
			if(chargingInvoiceSingle != null){
				TeamInvoiceAccount team = new TeamInvoiceAccount();
				team.setAccount_num(chargingInvoiceSingle.getAccount_num());
				team.setAcc_num(chargingSummarySingle.getReq_num());
				team.setAudit_date(DateTimeUtil.parse());
				team.setAuditor(user.getUserid());
				team.setCenter_num(user.getCenter_num());
				this.pm.save(team);
			}
		
			List<ExaminfoChargingItem> listItem = this.qm.find("from ExaminfoChargingItem ec where ec.exam_num = "+itemdto.getExam_num()+" and ec.isActive='Y' and ec.pay_status = 'N' and ec.center_num = '"+user.getCenter_num()+"'");
			//2保存结算明细
			for(ExaminfoChargingItem item : listItem){
				ChargingDetailSingle chargingDetailSingle = new ChargingDetailSingle();
				chargingDetailSingle.setSummary_id(chargingSummarySingle.getId());
				chargingDetailSingle.setAmount(item.getItem_amount());
				chargingDetailSingle.setAmount1(item.getPersonal_pay());
				chargingDetailSingle.setCharging_item_id(item.getCharge_item_id());
				chargingDetailSingle.setDiscount(item.getDiscount());
				chargingDetailSingle.setCreater(user.getUserid());
				chargingDetailSingle.setCreate_time(DateTimeUtil.parse());
				chargingDetailSingle.setUpdater(user.getUserid());
				chargingDetailSingle.setUpdate_time(DateTimeUtil.parse());
				this.pm.save(chargingDetailSingle);  //保存结算明细
				
				//3修改项目状态
				item.setPay_status("Y");
				item.setUpdater(user.getUserid());
				item.setUpdate_time(DateTimeUtil.parse());
				this.pm.update(item);//修改项目状态
			}
		
			//4收费方式
			List<ChargingWayDTO> listway = model.getCharingWay();
			for(ChargingWayDTO chargingWay : listway){
				ChargingWaySingle chargingWaySingle = new ChargingWaySingle();
				
				chargingWaySingle.setSummary_id(chargingSummarySingle.getId());
				chargingWaySingle.setAmount(itemdto.getAmount());
				chargingWaySingle.setCharging_way(chargingWay.getCharging_way());
				chargingWaySingle.setCreater(user.getUserid());
				chargingWaySingle.setCreate_time(DateTimeUtil.parse());
				chargingWaySingle.setUpdater(user.getUserid());
				chargingWaySingle.setUpdate_time(DateTimeUtil.parse());
				if(chargingInvoiceSingle != null){
					chargingWaySingle.setAccount_num(chargingInvoiceSingle.getAccount_num());
				}
				this.pm.save(chargingWaySingle);
			}
		}
		
		if(chargingInvoiceSingle != null){
			result.setAccount_num(chargingInvoiceSingle.getAccount_num());
		}
		result.setUser_id(user.getUserid());
		result.setFlag("ok");
		result.setInfo("收费成功！");
		return result;
	}

	@Override
	public String savePricing(CollectFeesModel model, UserDTO user) throws ServiceException {
		List<ExamResultChargingItemDTO> ecilist = model.getExamInfoCharingItem();
		for (ExamResultChargingItemDTO eci : ecilist) {
			ExaminfoChargingItem ec = (ExaminfoChargingItem) this.qm.load(ExaminfoChargingItem.class, eci.getId());
			ec.setUpdater(user.getUserid());
			ec.setUpdate_time(DateTimeUtil.parse());
			ec.setDiscount(eci.getDiscount());
			ec.setAmount(eci.getAmount());
			if (!"".equals(model.getPay_mode()) && model.getPay_mode().equals("0")) {//体检收费
				ec.setTj_charge_amount(eci.getAmount());
				ec.setPay_mode(model.getPay_mode());
			}else if(!"".equals(model.getPay_mode()) && model.getPay_mode().equals("1")){//his收费
				ec.setHis_charge_amount(eci.getAmount());
				ec.setPay_mode(model.getPay_mode());
			}else if(!"".equals(model.getPay_mode()) && model.getPay_mode().equals("2")){//混合收费
				ec.setTj_charge_amount(eci.getTj_charge_amount());
				ec.setHis_charge_amount(eci.getHis_charge_amount());
				ec.setPay_mode(eci.getPay_mode());
			}else{
				return "error-体检类型不名确!!";
			}
			
			this.pm.update(ec);
		}
		return "ok-划价成功";
	}

	@Override
	public String cancel_pricing(CollectFeesModel model, UserDTO user) {
		int  count = 0;
		String[] ids = model.getEci_ids().split(",");
		if(ids.length > 0){
			for (String ecid : ids) {
				int id = Integer.parseInt(ecid);
				ExaminfoChargingItem ec = (ExaminfoChargingItem) this.qm.load(ExaminfoChargingItem.class, id);
				if(!StringUtils.isEmpty(ec)){
					ec.setUpdater(user.getUserid());
					ec.setUpdate_time(DateTimeUtil.parse());
					ec.setDiscount(10);
					ec.setAmount(ec.getItem_amount());
					ec.setHis_charge_amount(0);
					ec.setTj_charge_amount(0);
					ec.setPay_mode("");
					this.pm.update(ec);
					count++;
				}
			}
		}else{
			return "error-操作错误!!";
		}
		return "ok-批量撤销完成 "+count+" 条";
	}

	@Override
	public CollectFeesResult savePricingCollectFees(CollectFeesModel model, UserDTO user, String invoice_class)throws ServiceException {
		ChargingSummarySingle chargingSummarySingle = new ChargingSummarySingle();
		CollectFeesResult result = new CollectFeesResult();
		List<ExaminfoChargingItem>  list = new ArrayList<ExaminfoChargingItem>();
		for(ExamResultChargingItemDTO item : model.getExamInfoCharingItem()){
			ExaminfoChargingItem ec = (ExaminfoChargingItem) this.qm.load(ExaminfoChargingItem.class, item.getId());
			if (!StringUtils.isEmpty(ec) && !ec.getPay_mode().equals("0") 
					&& ec.getHis_charge_amount() > 0 && ec.getHis_charge_status().equals("N") && !ec.getHis_req_status().equals("Y")) {
				list.add(ec);
			}
			if(ec == null){
				result.setUser_id(user.getUserid());
				result.setFlag("error");
				result.setInfo("收费项目不存在,请先查询未检收费项目！");
				return result;
			}else if("N".equals(ec.getIsActive())){
				result.setUser_id(user.getUserid());
				result.setFlag("error");
				result.setInfo("存在已减项收费项目,请先查询未检收费项目！");
				return result;
			}
			if("Y".equals(ec.getPay_status())){
				result.setUser_id(user.getUserid());
				result.setFlag("error");
				result.setInfo("存在已收费项目,请先查询未检收费项目！");
				return result;
			}
		}
		
		if (list.size() > 0) {
			String res = this.pricingPaymentApplication(list, model.getExam_num(), user);
			if (res.startsWith("error")) {
				result.setUser_id(user.getUserid());
				result.setFlag("error");
				result.setInfo(res.split("-")[1]);
				return result;
			}
		}
		
		ChargingInvoiceSingle chargingInvoiceSingle = null;
		if("Y".equals(model.getIsPrintRecepit())){
			chargingInvoiceSingle = new ChargingInvoiceSingle();
			chargingInvoiceSingle.setTitle_info(model.getTitle_info());
			chargingInvoiceSingle.setInvoice_num(model.getInvoice_num());
			chargingInvoiceSingle.setInvoice_type(model.getInvoice_type());
			chargingInvoiceSingle.setInvoice_status("Y");
			chargingInvoiceSingle.setInvoice_maker(user.getUserid());
			chargingInvoiceSingle.setInvoice_amount(model.getAmount2());
			chargingInvoiceSingle.setInvoice_time(DateTimeUtil.parse());
			chargingInvoiceSingle.setCreater(user.getUserid());
			chargingInvoiceSingle.setCreate_time(DateTimeUtil.parse());
			chargingInvoiceSingle.setUpdater(user.getUserid());
			chargingInvoiceSingle.setUpdate_time(DateTimeUtil.parse());
			chargingInvoiceSingle.setAccount_num(GetNumContral.getInstance().getParamNum("account_num",user.getCenter_num()));
			chargingInvoiceSingle.setExam_type("G");
			chargingInvoiceSingle.setBarchid(model.getExam_id());
			chargingInvoiceSingle.setCenter_num(user.getCenter_num());
			chargingInvoiceSingle.setInvoice_class(invoice_class);
			chargingInvoiceSingle.setDaily_status("0");
			
			this.pm.save(chargingInvoiceSingle);
			
			//保存发票记录表
			CardDeal cardDeal= new CardDeal();
			cardDeal.setAmount(model.getAmount2());
			cardDeal.setCard_num(chargingInvoiceSingle.getInvoice_num());
			cardDeal.setTrancode("002");
			cardDeal.setCreater(user.getUserid());
			cardDeal.setDeal_type("0");
			cardDeal.setDeal_date(DateTimeUtil.getDate2());
			cardDeal.setDeal_time(DateTimeUtil.parse());
			cardDeal.setOld_amount(model.getAmount2());
			cardDeal.setInvoice_class(chargingInvoiceSingle.getInvoice_class());
			cardDeal.setExam_num(model.getExam_num());
            this.pm.save(cardDeal);
			
			chargingSummarySingle.setInvoice_id(chargingInvoiceSingle.getId());
		}
		//1收费总记录
		chargingSummarySingle.setExam_id(model.getExam_id());
		chargingSummarySingle.setReq_num(GetNumContral.getInstance().getParamNum("rcpt_num",user.getCenter_num()));
		chargingSummarySingle.setCharging_status("Y");
		chargingSummarySingle.setAmount1(model.getAmount1());
		chargingSummarySingle.setAmount2(model.getTj_amount());
		chargingSummarySingle.setDiscount(model.getDiscount());
		chargingSummarySingle.setCashier(user.getUserid());
		chargingSummarySingle.setCash_date(DateTimeUtil.parse());
		chargingSummarySingle.setIs_print_recepit(model.getIsPrintRecepit());
		chargingSummarySingle.setIs_active("Y");
		chargingSummarySingle.setCreater(user.getUserid());
		chargingSummarySingle.setCreate_time(DateTimeUtil.parse());
		chargingSummarySingle.setUpdater(user.getUserid());
		chargingSummarySingle.setUpdate_time(DateTimeUtil.parse());
		chargingSummarySingle.setDaily_status("0");
		chargingSummarySingle.setExam_num(model.getExam_num());
		chargingSummarySingle.setPay_mode("1");// 体检收费
		chargingSummarySingle.setCenter_num(user.getCenter_num());
		this.pm.save(chargingSummarySingle);  //保存收费总表
		
		if(chargingInvoiceSingle != null){
			TeamInvoiceAccount team = new TeamInvoiceAccount();
			team.setAccount_num(chargingInvoiceSingle.getAccount_num());
			team.setAcc_num(chargingSummarySingle.getReq_num());
			team.setAudit_date(DateTimeUtil.parse());
			team.setAuditor(user.getUserid());
			team.setCenter_num(user.getCenter_num());
			this.pm.save(team);
		}
		
		List<ExamResultChargingItemDTO> listItem = model.getExamInfoCharingItem();
		
		//2保存结算明细
		for(ExamResultChargingItemDTO item : listItem){
			ChargingDetailSingle chargingDetailSingle = new ChargingDetailSingle();
			chargingDetailSingle.setCharging_item_code(item.getItem_code());
			chargingDetailSingle.setSummary_id(chargingSummarySingle.getId());
			chargingDetailSingle.setAmount(item.getItem_amount());
			chargingDetailSingle.setAmount1(item.getTj_charge_amount());
			chargingDetailSingle.setCharging_item_id(item.getItem_id());
			chargingDetailSingle.setDiscount(item.getDiscount());
			chargingDetailSingle.setCreater(user.getUserid());
			chargingDetailSingle.setCreate_time(DateTimeUtil.parse());
			chargingDetailSingle.setUpdater(user.getUserid());
			chargingDetailSingle.setUpdate_time(DateTimeUtil.parse());
			
			this.pm.save(chargingDetailSingle);  //保存结算明细
			
			//3修改项目状态
			ExaminfoChargingItem ec = (ExaminfoChargingItem) this.qm.load(ExaminfoChargingItem.class, item.getId());
			ec.setAmount(item.getAmount());
			ec.setDiscount(item.getDiscount());
			ec.setPersonal_pay(item.getHis_charge_amount()+item.getTj_charge_amount());
			ec.setUpdater(user.getUserid());
			ec.setUpdate_time(DateTimeUtil.parse());
			ec.setTj_charge_amount(item.getTj_charge_amount());
			if (ec.getTj_charge_amount() > 0) {
				ec.setTj_charge_status("Y");
			}
			if(ec.getHis_charge_amount() > 0){
				ec.setHis_charge_amount(item.getHis_charge_amount());
			}
			if(!StringUtils.isEmpty(ec.getPay_mode()) && ec.getPay_mode().equals("0")){
				ec.setPay_status("Y");
			}
			this.pm.update(ec);//修改项目状态
		}
		
		boolean isCard = false;
		//4收费方式
		List<ChargingWayDTO> listway = model.getCharingWay();
		for(ChargingWayDTO chargingWay : listway){
			ChargingWaySingle chargingWaySingle = new ChargingWaySingle();
			
			chargingWaySingle.setSummary_id(chargingSummarySingle.getId());
			chargingWaySingle.setAmount(chargingWay.getAmount());
			chargingWaySingle.setCharging_way(chargingWay.getCharging_way());
			chargingWaySingle.setCreater(user.getUserid());
			chargingWaySingle.setCreate_time(DateTimeUtil.parse());
			chargingWaySingle.setUpdater(user.getUserid());
			chargingWaySingle.setUpdate_time(DateTimeUtil.parse());
			if(chargingInvoiceSingle != null){
				chargingWaySingle.setAccount_num(chargingInvoiceSingle.getAccount_num());
			}
			if(chargingWay.getCharging_way().equals("122")){
				isCard = true;
			}
			this.pm.save(chargingWaySingle);
		}
		
		if(isCard){//会员刷卡，保存卡消费信息
			List<CardInfoDTO> listCard = model.getCardInfo();
			for(CardInfoDTO cardInfo : listCard){
				CardDeal cardDeal = new CardDeal();
				
				cardDeal.setCard_num(cardInfo.getCard_num());
				cardDeal.setAmount(cardInfo.getAmount());
				cardDeal.setCard_count(cardInfo.getCard_count()+1);
				cardDeal.setSummary_id(chargingSummarySingle.getId());
				cardDeal.setExaminfo_id(model.getExam_id());
				cardDeal.setDeal_type("6");
				cardDeal.setCreater(user.getUserid());
				cardDeal.setDeal_date(DateTimeUtil.getDate2());
				cardDeal.setDeal_time(DateTimeUtil.parse());
				cardDeal.setOld_amount(cardInfo.getFace_amount());
				cardDeal.setTrancode("001");
				cardDeal.setExam_num(model.getExam_num());
				this.pm.save(cardDeal);
				
				List<CardInfo> cardlist = this.qm.find("from CardInfo c where c.card_num = '"+cardInfo.getCard_num()+"'");
				if(cardlist.size() > 0){
					CardInfo cardifo = cardlist.get(0);
					cardifo.setAmount(cardInfo.getFace_amount()-cardInfo.getAmount());
					cardifo.setCard_count(cardInfo.getCard_count()+1);
					this.pm.update(cardifo);
				}
			}
		}
		
		if(chargingInvoiceSingle != null){
			result.setAccount_num(chargingInvoiceSingle.getAccount_num());
		}
		result.setUser_id(user.getUserid());
		result.setFlag("ok");
		result.setInfo("收费成功！");
		return result;
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public  String pricingPaymentApplication(List<ExaminfoChargingItem> itemList,String examNum, UserDTO user) throws ServiceException {
		//查询webservice地址
		WebserviceConfigurationDTO wcf=new WebserviceConfigurationDTO();
		wcf=this.webserviceConfigurationService.getWebServiceConfig("PAYMENT_APPLICATION",user.getCenter_num());
		//根据体检号查询体检信息
		List<ExamInfo> examList = this.qm.find("from ExamInfo e where e.exam_num = '"+examNum+"'");
		if(examList.size() == 0){
			return "error-该体检信息不存在!";
		}
		ExamInfo examInfo = examList.get(0);
		String IS_HIS_EXAMBRID_CHECK = this.customerInfoService.getCenterconfigByKey("IS_HIS_EXAMBRID_CHECK",user.getCenter_num()).getConfig_value().trim();
		if ("Y".equals(IS_HIS_EXAMBRID_CHECK)) {
			if(examInfo.getVisit_no() == null || "".equals(examInfo.getVisit_no())
				|| examInfo.getVisit_date() == null || "".equals(examInfo.getVisit_date())){
				return "error-没有就诊卡号或就诊日期，不能发送缴费申请!";
			}
		}
		
		Double amount = 0.0;  //计算总金额
		List<Fee> feeList = new ArrayList<Fee>(); //发送申请项目集合
		List<ChargingDetailSingle> detailList = new ArrayList<ChargingDetailSingle>();  //结算明细
		
		for(ExaminfoChargingItem item : itemList) {
			
			ChargingItem charitem = (ChargingItem) this.qm.load(ChargingItem.class,item.getCharge_item_id()); 
			
			amount += item.getHis_charge_amount();
			//组装明细表
			ChargingDetailSingle chargingDetailSingle = new ChargingDetailSingle();
			chargingDetailSingle.setAmount(item.getItem_amount());
			chargingDetailSingle.setAmount1(item.getHis_charge_amount());
			chargingDetailSingle.setCharging_item_id(item.getCharge_item_id());
			chargingDetailSingle.setDiscount(item.getDiscount());
			chargingDetailSingle.setCreater(user.getUserid());
			chargingDetailSingle.setCreate_time(DateTimeUtil.parse());
			chargingDetailSingle.setUpdater(user.getUserid());
			chargingDetailSingle.setUpdate_time(DateTimeUtil.parse());
			detailList.add(chargingDetailSingle);
			
				Fee fee = new Fee();				
				fee.setPATIENT_ID(examInfo.getPatient_id());
				fee.setEXAM_NUM(examInfo.getExam_num());
				fee.setVISIT_DATE(examInfo.getVisit_date());
				fee.setVISIT_NO(examInfo.getVisit_no());
				fee.setSERIAL_NO("");
				fee.setORDER_CLASS("");
				fee.setORDER_NO("1");
				fee.setORDER_SUB_NO("1");
				fee.setITEM_NO("1");
				fee.setITEM_CLASS("");
				fee.setITEM_NAME(charitem.getItem_name());
				fee.setITEM_CODE(charitem.getHis_num());
				fee.setITEM_SPEC("");
				fee.setUNITS("");
				fee.setREPETITION("1");
				
				fee.setORDERED_BY_DEPT(companyService.getDatadis("SQKS","1").get(0).getRemark());
				fee.setORDERED_BY_DOCTOR(user.getName());
				fee.setUSER_NAME(user.getWork_num());
				fee.setPERFORMED_BY(charitem.getPerform_dept());
				fee.setCLASS_ON_RCPT("");
				fee.setITEM_PRICE(charitem.getAmount().toString());
				fee.setRCPT_NO("");
				fee.setCHARGE_INDICATOR("0");
				fee.setCLASS_ON_RECKONING("");
				fee.setSUBJ_CODE("");
				fee.setPRICE_QUOTIETY("");
				fee.setCLINIC_NO(examInfo.getClinic_no());
				fee.setBILL_DATE("");
				fee.setBILL_NO("");
				fee.setSKINTEST("");// 皮试标志
				fee.setPRESC_PSNO(""); //皮试结果
				fee.setINSURANCE_FLAG("0"); //适用医保内外标志：0自费，1医保，2免费
				fee.setINSURANCE_CONSTRAINED_LEVEL("");// 公费用药级别
				fee.setSKIN_SAVE("");//皮试记录时间
				fee.setSKIN_START("");//皮试时间
				fee.setSKIN_BATH("");//药品批号
				fee.setCHARGES(item.getHis_charge_amount()+"");//实收金额
				fee.setExam_chargeItem_code(charitem.getItem_code());
				feeList.add(fee);
		}
		
		//保存个人收费总表记录
		ChargingSummarySingle chargingSummarySingle = new ChargingSummarySingle();
		
		chargingSummarySingle.setExam_id(examInfo.getId());
		chargingSummarySingle.setReq_num(GetNumContral.getInstance().getParamNum("rcpt_num",user.getCenter_num()));
		chargingSummarySingle.setCharging_status("R");//预付费为R
		chargingSummarySingle.setAmount1(Double.parseDouble(String.format("%.2f", amount)));
		chargingSummarySingle.setAmount2(Double.parseDouble(String.format("%.2f", amount)));
		chargingSummarySingle.setDiscount(10.00);
		chargingSummarySingle.setCashier(user.getUserid());
		chargingSummarySingle.setCash_date(DateTimeUtil.parse());
		chargingSummarySingle.setIs_print_recepit("N");
		chargingSummarySingle.setIs_active("Y");
		chargingSummarySingle.setCreater(user.getUserid());
		chargingSummarySingle.setCreate_time(DateTimeUtil.parse());
		chargingSummarySingle.setUpdater(user.getUserid());
		chargingSummarySingle.setUpdate_time(DateTimeUtil.parse());
		chargingSummarySingle.setDaily_status("0");
		chargingSummarySingle.setPay_mode("2");// 1 体检收费,2 his收费
		this.pm.save(chargingSummarySingle);  //保存收费总表
		
		for(ChargingDetailSingle chargingDetailSingle : detailList){//遍历保存明细数据
			chargingDetailSingle.setSummary_id(chargingSummarySingle.getId());
			this.pm.save(chargingDetailSingle);
		}
		
		//开始发送申请
		
		FeeMessage fm = new FeeMessage();
		fm.setCenter_num(user.getCenter_num());
		fm.setREQ_NO(chargingSummarySingle.getReq_num());
		Fees fees = new Fees();
		fees.setPROJECT(feeList);
		fm.setPROJECTS(fees);
		
		FEESendMessage fsm= new FEESendMessage(fm);		
		FeeResultBody frb= new FeeResultBody();
		frb = fsm.feeSend(wcf.getConfig_url().trim(),wcf.getConfig_method().trim(), true);
		System.out.println(frb.getResultHeader().getTypeCode() + "-" + frb.getResultHeader().getText());
		
		if("AA".equals(frb.getResultHeader().getTypeCode())){//申请发送成功
			for(ExaminfoChargingItem examitem : itemList){
				examitem.setHis_req_status("Y");
				this.pm.update(examitem);
			}
			return "ok-缴费申请发送成功!";
		}else{
			throw new ServiceException(frb.getResultHeader().getText());
		}
	}
	@Override
	@SuppressWarnings("unchecked")
	public List<ExamResultChargingItemDTO> getPricingWitemList(String examNum,String is_fees_qijian,String center_num) throws ServiceException {
		String sql = "select ec.id,c.id as item_id,c.item_code,c.item_name,d.dep_name,ec.exam_status,ec.item_amount,ec.discount,ec.amount,ec.itemnum, "
				   + "ec.personal_pay,ec.team_pay,u.chi_name as creater,CONVERT(varchar(100),ec.create_time,23) as create_time,ec.his_req_status, ec.pay_status, "
				   + " ec.calculation_amount,ec.calculation_rate ,ec.tj_charge_amount,ec.his_charge_amount,ec.pay_mode ,ec.his_charge_status,ec.tj_charge_status , ec.is_application, "
				   +" (select  css.is_print_recepit from  charging_detail_single  cds ,charging_summary_single css  where  css.id = cds.summary_id and css.is_active <> 'N'  "
				   + "and   cds.charging_item_id = c.id  and css.exam_id = e.id  and css.center_num='"+center_num+"' )  as is_print_recepit  "
				   + " from exam_info e,charging_item c,department_dep d,examinfo_charging_item ec left join user_usr u "
				   + " on u.id = ec.creater"
				   + " where ec.exam_num = e.exam_num  and ec.charging_item_code = c.item_code and c.dep_id = d.id  and  ec.exam_status = 'N'  and  ec.pay_status  <> 'M' "
				   + " and ec.isActive = 'Y' and ec.change_item <> 'C' and ec.center_num = '"+center_num+"' and e.is_Active='Y' and e.exam_num = '"+examNum+"'";
		if("N".equals(is_fees_qijian)){
			sql += " and ec.exam_status <> 'G'";
		}
		sql += " order by ec.is_new_added desc,d.seq_code,c.item_seq";
		List<ExamResultChargingItemDTO> list = this.jqm.getList(sql, ExamResultChargingItemDTO.class);
		return list;
	}

	@SuppressWarnings("all")
	@Override
	public String savePricingRefund(CollectFeesModel model, UserDTO user) throws ServiceException {
		
		//查询webservice地址
		WebserviceConfigurationDTO wcf=new WebserviceConfigurationDTO();
		wcf=this.webserviceConfigurationService.getWebServiceConfig("REFUND_APPLICATION",user.getCenter_num());
		String IS_SINGLE_REFUND = this.customerInfoService.getCenterconfigByKey("IS_SINGLE_REFUND",user.getCenter_num()).getConfig_value().trim();
		List<ExamInfo> listinfo = this.qm.find("from ExamInfo e where e.exam_num = '"+model.getExam_num()+"'");
		ExamInfo examinfo = null;
		if(listinfo.size() == 0){
			return "error-该体检号不存在体检信息";
		}else{
			examinfo = listinfo.get(0);
		}
		String message = "error-操作错误!!";
		String item_id = "";
		String sql ="select cd.card_num,sum(cd.amount) amount from card_deal cd where cd.examinfo_id = " + model.getExam_id()
					+ " and cd.deal_type in (6,7) group by cd.card_num";
		List<CardInfoDTO> listCard = this.jqm.getList(sql, CardInfoDTO.class);
		Set<String>  reqList =  new HashSet<String>();
 		List<ExamResultChargingItemDTO>  li = model.getExamInfoCharingItem();
		if (li.size() > 0) {
			for (ExamResultChargingItemDTO eci : li) {
				item_id += eci.getItem_id()+"," ;
				String sq = " select   css.* from  charging_summary_single  css , charging_detail_single  cds , exam_info  e where e.exam_num = css.exam_num and "
						   +" css.is_active = 'Y' and  css.id = cds.summary_id and  css.charging_status = 'Y'   and  css.is_active = 'Y' "
						   +" and e.exam_num = '"+model.getExam_num()+"' and css.center_num='"+user.getCenter_num()+"'  and cds.charging_item_id = '"+eci.getItem_id()+"'";
				
				List<ChargingSummarySingle> list = this.jqm.getList(sq, ChargingSummarySingle.class);
				if (list.size() > 0) {
					for (ChargingSummarySingle css : list) {
						reqList.add(css.getReq_num());
					}
				}
			}
		}
		
		boolean is_his_sq = false;
		List<String> REQ_NOS= new ArrayList<String>();//申请单号
		if (reqList.size() > 0) {//获取多个申请单号
			for (String req_num : reqList) {
				List<ChargingSummarySingle> list = 	this.qm.find("from ChargingSummarySingle where req_num = '" +req_num+"'  and charging_status = 'Y' and is_active = 'Y' ");
				if (list.size() > 0) {
					
					List<ChargingInvoiceSingle> invoiceList = this.qm.find("from ChargingInvoiceSingle where id = '"+list.get(0).getInvoice_id()+"'");
					if(invoiceList.size() != 0){
						ChargingInvoiceSingle chargingInvoiceSingle = invoiceList.get(0);
						chargingInvoiceSingle.setInvoice_status("N");
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
						cardDeal.setExam_num(model.getExam_num());
						this.pm.save(cardDeal);
			            
			            //保存作废发票流水表
			            NullifyInvoice nullifyInvoice = new NullifyInvoice();
			            nullifyInvoice.setInvoice_num(chargingInvoiceSingle.getInvoice_num());
			            nullifyInvoice.setInvoice_status("N");
			            nullifyInvoice.setInvoice_class(chargingInvoiceSingle.getInvoice_class());
			            nullifyInvoice.setCreater(user.getUserid());
			            nullifyInvoice.setCreate_time(DateTimeUtil.parse());
			            nullifyInvoice.setDaily_status("0");
			            nullifyInvoice.setAccount_num(GetNumContral.getInstance().getParamNum("account_num",user.getCenter_num()));
			            nullifyInvoice.setCenter_num(user.getCenter_num());
			            this.pm.save(nullifyInvoice);
						
						List<ChargingSummarySingle> summarySingleList = this.qm.find("from ChargingSummarySingle where invoice_id = "+chargingInvoiceSingle.getId());
						for(ChargingSummarySingle chargingSummarySingle : summarySingleList){
							chargingSummarySingle.setInvoice_id(0);
							chargingSummarySingle.setIs_print_recepit("N");
							this.pm.update(chargingSummarySingle);
						}
					}
					
					//判断 是否his 收费 
					
					if (!StringUtils.isEmpty(list.get(0).getPay_mode()) && list.get(0).getPay_mode().equals("2")) {//his收费 一退全部退 （申请单下的所有项目全部退费）
						ChargingSummarySingle chargingSummary = list.get(0);

						
						REQ_NOS.add(chargingSummary.getReq_num());
						chargingSummary.setIs_active("N");
						this.pm.update(chargingSummary);
						
						ChargingSummarySingle chargingSummarySingle = new ChargingSummarySingle();
						
						chargingSummarySingle.setReq_num(chargingSummary.getReq_num());
						chargingSummarySingle.setExam_id(chargingSummary.getExam_id());
						chargingSummarySingle.setCharging_status("M");
						chargingSummarySingle.setAmount1(chargingSummary.getAmount1());
						chargingSummarySingle.setAmount2(chargingSummary.getAmount2());
						chargingSummarySingle.setDiscount(chargingSummary.getDiscount());
						chargingSummarySingle.setCashier(user.getUserid());
						chargingSummarySingle.setCash_date(DateTimeUtil.parse());
						chargingSummarySingle.setIs_print_recepit("Y");
						chargingSummarySingle.setIs_active("N");
						chargingSummarySingle.setCreater(user.getUserid());
						chargingSummarySingle.setCreate_time(DateTimeUtil.parse());
						chargingSummarySingle.setUpdater(user.getUserid());
						chargingSummarySingle.setUpdate_time(DateTimeUtil.parse());
						chargingSummarySingle.setInvoice_id(invoiceList.get(0).getId());
						chargingSummarySingle.setDaily_status("0");
						chargingSummarySingle.setExam_num(model.getExam_num());
						
						this.pm.save(chargingSummarySingle); //保存退费信息总表
						
						List<ChargingDetailSingle> detailSingleList = this.qm.find("from ChargingDetailSingle where summary_id = "+chargingSummary.getId());
						
						for(ChargingDetailSingle detailSingle : detailSingleList){
							ChargingDetailSingle chargingDetailSingle = new ChargingDetailSingle();
							
							chargingDetailSingle.setSummary_id(chargingSummarySingle.getId());
							chargingDetailSingle.setAmount(detailSingle.getAmount());
							chargingDetailSingle.setAmount1(detailSingle.getAmount1());
							chargingDetailSingle.setCharging_item_id(detailSingle.getCharging_item_id());
							chargingDetailSingle.setDiscount(detailSingle.getDiscount());
							chargingDetailSingle.setCreater(user.getUserid());
							chargingDetailSingle.setCreate_time(DateTimeUtil.parse());
							chargingDetailSingle.setUpdater(user.getUserid());
							chargingDetailSingle.setUpdate_time(DateTimeUtil.parse());
							
							this.pm.save(chargingDetailSingle);  //保存结算明细
							
							List<ExaminfoChargingItem> examinfoChargingItemList = this.qm.find("from ExaminfoChargingItem where pay_status != 'M'"
									+ " and isActive = 'Y' and center_num = '"+user.getCenter_num()+"' and exam_num = '" + chargingSummarySingle.getExam_num() 
									+"' and charge_item_id = "+chargingDetailSingle.getCharging_item_id());
							
							if(examinfoChargingItemList.size() != 0){
								ExaminfoChargingItem examItem = examinfoChargingItemList.get(0);
								examItem.setPay_status("N");
								examItem.setHis_req_status("N");
								this.pm.update(examItem);
							}
						}
						
						List<ChargingWaySingle> waySingleList = this.qm.find("from ChargingWaySingle where summary_id = " +chargingSummary.getId());
						boolean isCard = false;
						Double amount = 0.0;
						for(ChargingWaySingle WaySingle : waySingleList){
							ChargingWaySingle chargingWaySingle = new ChargingWaySingle();
							chargingWaySingle.setSummary_id(chargingSummarySingle.getId());
							chargingWaySingle.setAmount(WaySingle.getAmount());
							chargingWaySingle.setCharging_way(WaySingle.getCharging_way());
							chargingWaySingle.setCreater(user.getUserid());
							chargingWaySingle.setCreate_time(DateTimeUtil.parse());
							chargingWaySingle.setUpdater(user.getUserid());
							chargingWaySingle.setUpdate_time(DateTimeUtil.parse());
							if(WaySingle.getCharging_way().equals("122")){
								isCard = true;
								amount = WaySingle.getAmount();
							}
							this.pm.save(chargingWaySingle);
						}
						
						if(isCard){//会员刷卡，保存卡消费信息
							for(CardInfoDTO carddto : listCard){
								List<CardInfo> cardlist = this.qm.find("from CardInfo c where c.card_num = '"+carddto.getCard_num()+"'");
								if(cardlist.size() > 0 && carddto.getAmount() > 0 && amount > 0){
									CardInfo cardInfo = cardlist.get(0);
									CardDeal cardDeals = new CardDeal();
									cardDeals.setCard_num(cardInfo.getCard_num());
									cardDeals.setCard_count(cardInfo.getCard_count());
									cardDeals.setOld_amount(cardInfo.getAmount());
									cardDeals.setSummary_id(chargingSummarySingle.getId());
									cardDeals.setExaminfo_id(model.getExam_id());
									cardDeals.setDeal_type("7");
									cardDeals.setCreater(user.getUserid());
									cardDeals.setDeal_date(DateTimeUtil.getDate2());
									cardDeals.setDeal_time(DateTimeUtil.parse());
									cardDeals.setTrancode("001");
									if(carddto.getAmount() > amount){
										cardDeals.setAmount(-amount);
										cardInfo.setAmount(amount+cardInfo.getAmount());
										amount = 0.0;
									}else{
										cardDeals.setAmount(-carddto.getAmount());
										cardInfo.setAmount(carddto.getAmount()+cardInfo.getAmount());
										cardInfo.setCard_count(cardInfo.getCard_count()-1);
										amount = amount - carddto.getAmount();
									}
									this.pm.save(cardDeals);
									this.pm.update(cardInfo);
								}
							}
						}
						
						
						UnFeeMessage fm = new UnFeeMessage();
						
						ReqUnNo rrn=new ReqUnNo();
						rrn.setREQ_NO(REQ_NOS);
						fm.setREQ_NOS(rrn);//申请单号
						fm.setEXAM_NUM(examinfo.getPatient_id());
						fm.setRCPT_NO(invoiceList.get(0).getAccount_num());//收据号
						fm.setCenter_num(user.getCenter_num());
						
						UNFEESendMessage fsm= new UNFEESendMessage(fm);		
						FeeReqBody frb= new FeeReqBody();
						frb = fsm.feeSend(wcf.getConfig_url().trim(),wcf.getConfig_method().trim(), true);
						System.out.println(frb.getResultHeader().getTypeCode() + "-" + frb.getResultHeader().getText());
						if("AA".equals(frb.getResultHeader().getTypeCode())){//申请发送成功
							message =  "退费成功！请发送缴费申请。";
						}else{
							throw new ServiceException(frb.getResultHeader().getText());
						}
					
					}else if(!StringUtils.isEmpty(list.get(0).getPay_mode()) && list.get(0).getPay_mode().equals("1")){//体检收费 按照具体项目退费 
						List<ChargingDetailSingle>  cdsList =  this.qm.find(" from  ChargingDetailSingle  where summary_id = '"+list.get(0).getId()+"'  and charging_item_id in ("+item_id.substring(0,item_id.length()-1)+") ");
						double amount1 = 0.0;
						double amount2 = 0.0;
						for (ChargingDetailSingle cds : cdsList) {
							amount1 += cds.getAmount();
							amount2 += cds.getAmount1();
						}
						
						ChargingSummarySingle chargingSummarySingle = new ChargingSummarySingle();
						chargingSummarySingle.setReq_num(list.get(0).getReq_num());
						chargingSummarySingle.setExam_id(list.get(0).getExam_id());
						chargingSummarySingle.setCharging_status("M");
						chargingSummarySingle.setAmount1(amount1);
						chargingSummarySingle.setAmount2(amount2);
						chargingSummarySingle.setDiscount(list.get(0).getDiscount());
						chargingSummarySingle.setCashier(user.getUserid());
						chargingSummarySingle.setCash_date(DateTimeUtil.parse());
						chargingSummarySingle.setIs_print_recepit("N");
						chargingSummarySingle.setIs_active("N");
						chargingSummarySingle.setCreater(user.getUserid());
						chargingSummarySingle.setCreate_time(DateTimeUtil.parse());
						chargingSummarySingle.setUpdater(user.getUserid());
						chargingSummarySingle.setUpdate_time(DateTimeUtil.parse());
						chargingSummarySingle.setDaily_status("0");
						chargingSummarySingle.setExam_num(model.getExam_num());
						chargingSummarySingle.setPay_mode(list.get(0).getPay_mode());
						
						this.pm.save(chargingSummarySingle); //保存退费信息总表
						for (ChargingDetailSingle cdsl : cdsList) {
							ChargingDetailSingle chargingDetailSingle = new ChargingDetailSingle();
							chargingDetailSingle.setSummary_id(chargingSummarySingle.getId());
							chargingDetailSingle.setAmount(cdsl.getAmount());
							chargingDetailSingle.setAmount1(cdsl.getAmount1());
							chargingDetailSingle.setCharging_item_id(cdsl.getCharging_item_id());
							chargingDetailSingle.setDiscount(cdsl.getDiscount());
							chargingDetailSingle.setCreater(user.getUserid());
							chargingDetailSingle.setCreate_time(DateTimeUtil.parse());
							chargingDetailSingle.setUpdater(user.getUserid());
							chargingDetailSingle.setUpdate_time(DateTimeUtil.parse());
							this.pm.save(chargingDetailSingle);  //保存结算明细
						
							List<ExaminfoChargingItem> examinfoChargingItemList = this.qm.find("from ExaminfoChargingItem where pay_status != 'M'"
									+ " and isActive = 'Y' and center_num = '"+user.getCenter_num()+"' and exam_num = '" + chargingSummarySingle.getExam_num() 
									+"' and charge_item_id = "+cdsl.getCharging_item_id());
							
							if(examinfoChargingItemList.size() != 0){
								ExaminfoChargingItem examItem = examinfoChargingItemList.get(0);
								examItem.setPay_status("N");
								examItem.setHis_req_status("N");
								this.pm.update(examItem);
							}
						}
						boolean isCard = false;
						Double amount = 0.0;
						List<ChargingWayDTO> listway = model.getCharingWay();
						for(ChargingWayDTO chargingWay : listway){
							ChargingWaySingle chargingWaySingle = new ChargingWaySingle();
							
							chargingWaySingle.setSummary_id(chargingSummarySingle.getId());
							chargingWaySingle.setAmount(chargingWay.getAmount());
							chargingWaySingle.setCharging_way(chargingWay.getCharging_way());
							chargingWaySingle.setCreater(user.getUserid());
							chargingWaySingle.setCreate_time(DateTimeUtil.parse());
							chargingWaySingle.setUpdater(user.getUserid());
							chargingWaySingle.setUpdate_time(DateTimeUtil.parse());
							
							if(chargingWay.getCharging_way().equals("122")){
								isCard = true;
								amount = chargingWay.getAmount();
							}
							this.pm.save(chargingWaySingle);
						}
						
						if(isCard){//会员刷卡，保存卡消费信息
							for(CardInfoDTO carddto : listCard){
								List<CardInfo> cardlist = this.qm.find("from CardInfo c where c.card_num = '"+carddto.getCard_num()+"'");
								if(cardlist.size() > 0 && carddto.getAmount() > 0 && amount > 0){
									CardInfo cardInfo = cardlist.get(0);
									CardDeal cardDeals = new CardDeal();
									cardDeals.setCard_num(cardInfo.getCard_num());
									cardDeals.setCard_count(cardInfo.getCard_count());
									cardDeals.setOld_amount(cardInfo.getAmount());
									cardDeals.setSummary_id(chargingSummarySingle.getId());
									cardDeals.setExaminfo_id(model.getExam_id());
									cardDeals.setDeal_type("7");
									cardDeals.setCreater(user.getUserid());
									cardDeals.setDeal_date(DateTimeUtil.getDate2());
									cardDeals.setDeal_time(DateTimeUtil.parse());
									cardDeals.setTrancode("001");
									cardDeals.setExam_num(model.getExam_num());
									if(carddto.getAmount() > amount){
										cardDeals.setAmount(-amount);
										cardInfo.setAmount(amount+cardInfo.getAmount());
										amount = 0.0;
									}else{
										cardDeals.setAmount(-carddto.getAmount());
										cardInfo.setAmount(carddto.getAmount()+cardInfo.getAmount());
										cardInfo.setCard_count(cardInfo.getCard_count()-1);
										amount = amount - carddto.getAmount();
									}
									this.pm.save(cardDeals);
									this.pm.update(cardInfo);
								}
							}
						}
						message = "ok-退费成功！！";
					}
					//更改页面选中的项目状态
					List<ExamResultChargingItemDTO> listItem = model.getExamInfoCharingItem();
					for(ExamResultChargingItemDTO item : listItem){
						List<ExaminfoChargingItem> examinfoChargingItemList = this.qm.find("from ExaminfoChargingItem where id = "+item.getId()+" and center_num = '"+user.getCenter_num()+"'");
						if(examinfoChargingItemList.size() != 0){
							ExaminfoChargingItem examinfoChargingItem = examinfoChargingItemList.get(0);
							examinfoChargingItem.setPay_status("M");
							examinfoChargingItem.setUpdater(user.getUserid());
							examinfoChargingItem.setUpdate_time(DateTimeUtil.parse());
							this.pm.update(examinfoChargingItem);
							delLisAndPacsDetail(examinfo,examinfoChargingItem.getCharge_item_id());
						}
					}
					
				 }
			}
		}
	  return message;
	}

	@Override
	public List<ChargingWayDTO> getChargingWayByItemIds(String exam_num, String item_ids) throws ServiceException {
		String sql = " select dd.id, sum(amount) as amount  ,dd.data_name as charging_way from  charging_way_single  cws  right join data_dictionary dd on dd.id  = cws.charging_way where summary_id in ( "
					+" select distinct css.id from  charging_summary_single  css, charging_detail_single cds,charging_way_single  cws , exam_info e "
					+" where e.exam_num = css.exam_num  and  css.id = cds.summary_id  and css.is_active = 'Y' and css.charging_status = 'Y'  and "
				    +" cws.summary_id = css.id  and  e.exam_num = '"+exam_num+"'"
				    +" and   cds.charging_item_id in ("+item_ids+")"
				    +" )  group by cws.charging_way , dd.data_name ,dd.id ";
		List<ChargingWayDTO> list = this.jqm.getList(sql, ChargingWayDTO.class);
		return list;
	}

	@Override
	public List<ExaminfoChargingItem> getHisApplicationItemList(String exam_num, String center_num) throws ServiceException {
		String   sql = " select  eci.charge_item_id , eci.his_charge_amount,eci.item_amount,eci.discount  from  exam_info e ,  examinfo_charging_item  eci "
				+ " where   e.exam_num = eci.exam_num  and  "
					  +" eci.isActive = 'Y'  and  eci.pay_status = 'N'  and eci.his_req_status = 'N'  "
					  +" and his_charge_status = 'N'  and   pay_mode <> '0'  and eci.his_charge_amount > '0'  and eci.center_num = '"+center_num+"'  and  e.exam_num = '"+exam_num+"'"  ;
		List<ExaminfoChargingItem>  list = this.jqm.getList(sql, ExaminfoChargingItem.class);
		return list;
	}

	@Override
	public List<CollectFeesResult> queryPrintNum(String exam_num) throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@SuppressWarnings("all")
	public CompanyAccountDTO getCompanyAccounthantInfoByCom_id(long com_id) {
		String sq = "select * from company_info where id = "+com_id;
		List<CompanyInfo> list = this.jqm.getList(sq, CompanyInfo.class);
		if (list.size() > 0) {
			String sql = "select m.id, m.com_num,m.com_code,m.balance,m.com_type,CONVERT(varchar(100),m.create_date,20) as create_date,CONVERT(varchar(100),m.update_date,20) as update_date, u.chi_name as creater ,uu.chi_name as updater from  company_Account m " 
						 +" left  join  user_usr u on m.creater = u.id"
						 +" left  join  user_usr  uu on uu.id = m.updater"
						 +" where  m.com_num = '"+list.get(0).getCom_Num()+"'";
			List<CompanyAccountDTO> md = this.jqm.getList(sql, CompanyAccountDTO.class);
			if (md.size() > 0) {
				return md.get(0);
			}
		}
		return null;
	}

	@Override
	@SuppressWarnings("all")
	public String saveMerchantInfo(long com_id,UserDTO user) throws ServiceException {
		List<CompanyInfo> comList = this.qm.find("from  CompanyInfo  where id = "+com_id);
		List<Examinatioin_center>  ecLit = this.qm.find("from Examinatioin_center where is_active = 'Y' ");
		if (comList.size() > 0 && ecLit.size() > 0) {
			CompanyAccount  companyAccount = new CompanyAccount();
			companyAccount.setCom_num(comList.get(0).getCom_Num());
			companyAccount.setCenter_num(ecLit.get(0).getCenter_num());
			companyAccount.setBalance(0.00);
			companyAccount.setCom_type(0);
			companyAccount.setCreater((int)user.getUserid());
			companyAccount.setCreate_date(DateTimeUtil.parse());
			companyAccount.setUpdater((int)user.getUserid());
			companyAccount.setUpdate_date(DateTimeUtil.parse());
			this.pm.save(companyAccount);
			return "ok-开户成功!!!";
		}else{
			return "error-操作错误!!";
		}
	}

	@Override
	@SuppressWarnings("all")
	public String saveRecharge(CollectFeesModel model, UserDTO user) throws ServiceException {
		String sql = "select  * from  company_account  where com_num = '"+model.getCom_num()+"'";
		List<CompanyAccount>  list = this.jqm.getList(sql, CompanyAccount.class);
		if (list.size() > 0) {
			CompanyAccount companyAccount = list.get(0);
			if (companyAccount.getCom_type() == 1) {
				return "error-此商户以被冻结,操作不能继续。";
			}else if (companyAccount.getCom_type() == 2) {
				return "error-此商户以被销户,操作不能继续。";
			}else{
				String sq = "select  * from company_account_detail where com_num = '"+model.getCom_num()+"'  order by jndatetime desc";
				List<CompanyAccountDetail> li = this.jqm.getList(sq, CompanyAccountDetail.class);

				NumberFormat nf = NumberFormat.getInstance();// double  大数字科学计数法显示转换
				nf.setGroupingUsed(false);

				String num = GetNumContral.getInstance().getParamNum("sign_num",user.getCenter_num());
				CompanyAccountDetail companyAccountDetail  = new CompanyAccountDetail();
				companyAccountDetail.setLogicdate(DateTimeUtil.parse());
				companyAccountDetail.setCom_num(model.getCom_num());
				companyAccountDetail.setCreater((int)user.getUserid());
				companyAccountDetail.setTrancode("001");
				companyAccountDetail.setJnstatus(1);
				companyAccountDetail.setJndatetime(DateTimeUtil.parse());
				companyAccountDetail.setTrantmt(model.getAmount());
				companyAccountDetail.setChargingway(model.getCharging_way_id());
				companyAccountDetail.setInvaioce_type(0);
				companyAccountDetail.setJnnumber(num);//流水号
				companyAccountDetail.setAccount_num("");
				companyAccountDetail.setCenter_num(user.getCenter_num());
				if (li.size() > 0) {
					companyAccountDetail.setBalance(model.getAmount()+companyAccount.getBalance());
					companyAccountDetail.setOldbalance(li.get(0).getOldbalance());
					companyAccountDetail.setPrejnnumber(li.get(0).getJnnumber());
				}else {
					companyAccountDetail.setBalance(model.getAmount());
					companyAccountDetail.setOldbalance(model.getAmount());
					companyAccountDetail.setPrejnnumber("");
				}
				String  insert = "insert into  company_account_detail (logicdate,com_num,jnnumber,prejnnumber,account_num,creater,trancode,jnstatus,jndatetime,balance,oldbalance,usednum,trantmt,chargingway,invaioce_type,center_num) "
								+" values("+"'"+DateTimeUtil.getDateTime()+"','"+companyAccountDetail.getCom_num()+"','"+companyAccountDetail.getJnnumber()+"','"+companyAccountDetail.getPrejnnumber()+"','"+companyAccountDetail.getAccount_num()+"','"+companyAccountDetail.getCreater()+"','"+companyAccountDetail.getTrancode()
								+ "','"+companyAccountDetail.getJnstatus()+"','"+DateTimeUtil.getDateTime()+"','"+nf.format(companyAccountDetail.getBalance())+"','"+nf.format(companyAccountDetail.getOldbalance())+"','"+companyAccountDetail.getUsednum()+"','"+nf.format(companyAccountDetail.getTrantmt())+"','"+companyAccountDetail.getChargingway()+"','"+companyAccountDetail.getInvaioce_type()+"','"+companyAccountDetail.getCenter_num()+"')";
				this.jpm.execSql(insert);
				
				double amount = companyAccount.getBalance()+model.getAmount();
				String update = " update company_account  set balance = "+nf.format(amount)+", updater = '"+user.getUserid()+"',update_date = '"+DateTimeUtil.getDateTime()+"' where  com_num = '"+model.getCom_num()+"'";
				this.jpm.execSql(update);
				return "ok-充值成功 !-"+amount;
			}
			
		}else {
			return "error-商户信息不存在！！,请核对信息。";
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<CompanyAccountDetailDTO> getTransactionFlowList(String com_num,UserDTO user) {
		String sql = "select m.id, CONVERT(varchar(100),m.logicdate,20) as logicdate,u.chi_name as creater,m.trancode ,m.jnnumber, "
					+"m.jnstatus,CONVERT(varchar(100),m.jndatetime,20) as jndatetime ,m.balance,m.oldbalance,m.usednum,m.trantmt,m.invaioce_type,m.resume,d.data_name as chargingway,d.data_code_children as chargingwaycode "
					+"from company_account_detail  m left join user_usr u on u.id = m.creater  left join data_dictionary d on d.id = m.chargingway "
					+"where  m.center_num = '"+user.getCenter_num()+"' and m.com_num = '"+com_num+"' order by jndatetime desc ";
		List<CompanyAccountDetailDTO>  list = this.jqm.getList(sql, CompanyAccountDetailDTO.class);
		return list;
	}

	@Override
	@SuppressWarnings("all")
	public String saveInvoiceInfo(CollectFeesModel model, UserDTO user) throws ServiceException {
		if(model.getJnnumbers().length() <= 0 ){
			return "error-流水号错误!!";
		}
		ChargingInvoiceSingle chargingInvoiceSingle = new ChargingInvoiceSingle();
		chargingInvoiceSingle.setInvoice_amount(model.getInvoice_amount());
		chargingInvoiceSingle.setInvoice_num(model.getInvoice_num());
		chargingInvoiceSingle.setTitle_info(model.getInvoice_name());
		chargingInvoiceSingle.setInvoice_status("Y");
		chargingInvoiceSingle.setInvoice_maker(user.getUserid());
		chargingInvoiceSingle.setInvoice_time(DateTimeUtil.parse());
		chargingInvoiceSingle.setCreater(user.getUserid());
		chargingInvoiceSingle.setCreate_time(DateTimeUtil.parse());
		chargingInvoiceSingle.setUpdater(user.getUserid());
		chargingInvoiceSingle.setUpdate_time(DateTimeUtil.parse());
		chargingInvoiceSingle.setAccount_num(GetNumContral.getInstance().getParamNum("account_num",user.getCenter_num()));
		chargingInvoiceSingle.setExam_type("R");//  团体预付费
		chargingInvoiceSingle.setDaily_status("0");
		chargingInvoiceSingle.setInvoice_type("");
		chargingInvoiceSingle.setInvoice_class(model.getInvoice_class());
		chargingInvoiceSingle.setCenter_num(user.getCenter_num());
		this.pm.save(chargingInvoiceSingle);
		
		List<CompanyAccountDetail> list = this.qm.find("from  CompanyAccountDetail where jnnumber in ("+model.getJnnumbers()+")");
		for (CompanyAccountDetail companyAccountDetail : list) {
			companyAccountDetail.setInvoice_id(chargingInvoiceSingle.getId());
			companyAccountDetail.setInvaioce_type(1);
			this.pm.update(companyAccountDetail);
		}
		return "ok-开票成功！！";
	}

	@Override
	public List<ChargingInvoiceSingleDTO> getInvoiceInfoList(CollectFeesModel model,UserDTO user) throws ServiceException {
		String sql = "select distinct  cis.id,cis.title_info,invoice_num,invoice_amount,u.chi_name as creater ,   CONVERT(varchar(100),cis.create_time,20) as create_time  ,cis.account_num from company_account_detail  m ,charging_invoice_single cis "
					+" left join user_usr u on u.id = cis.creater "
					+" where m.center_num = '"+user.getCenter_num()+"' and  m.invoice_id = cis.id  and cis.invoice_status = 'Y' and m.com_num = '"+model.getCom_num()+"'";
			List<ChargingInvoiceSingleDTO> list = this.jqm.getList(sql, ChargingInvoiceSingleDTO.class);	
		return list;
	}

	@Override
	@SuppressWarnings("all")
	public String deleteInvoiceInfo(CollectFeesModel model, UserDTO user) throws ServiceException {
		String[] ids = model.getIds().split(",");
		if (ids.length <= 0) {
			return "error-发票单号无效 !,请重新操作。。";
		}
		int account = 0;
		for (int i = 0; i < ids.length; i++) {
			long id = Long.parseLong(ids[i]);
			ChargingInvoiceSingle chargingInvoiceSingle = (ChargingInvoiceSingle) this.qm.load(ChargingInvoiceSingle.class, id);
			chargingInvoiceSingle.setCancel_time(DateTimeUtil.parse());
			chargingInvoiceSingle.setCanceller(user.getUserid());
			this.pm.update(chargingInvoiceSingle);
			
			//保存发票作废流水
			NullifyInvoice invoice = new NullifyInvoice();
			invoice.setInvoice_num(chargingInvoiceSingle.getInvoice_num());
			invoice.setInvoice_class(chargingInvoiceSingle.getInvoice_class());
			invoice.setInvoice_status("N");
			invoice.setCreater(user.getUserid());
			invoice.setCreate_time(DateTimeUtil.parse());
			invoice.setDaily_status("0");
			invoice.setAccount_num(GetNumContral.getInstance().getParamNum("account_num",user.getCenter_num()));
			invoice.setCenter_num(user.getCenter_num());
			this.pm.save(invoice);
			
			//修改商户流水
			CompanyAccountDetail companyAccountDetail = new CompanyAccountDetail();
			List<CompanyAccountDetail> list = this.qm.find("from CompanyAccountDetail  where invaioce_type = 1 and trancode = '001' and invoice_id = "+chargingInvoiceSingle.getId());
			for (CompanyAccountDetail mer : list) {
				mer.setInvaioce_type(0);
				mer.setInvoice_id(0);
				this.pm.update(mer);
			}
			account++;
		}
		return "ok-作废发票成功"+account+"条 。。";
	}

	@Override
	public String editCompanyAccount(CollectFeesModel model, UserDTO user) throws ServiceException {
		String sql = "select  * from company_account  where com_num = '"+model.getCom_num()+"'";
		List<CompanyAccount> list = this.jqm.getList(sql, CompanyAccount.class);
		if(list.size() == 1){
			CompanyAccount companyAccount = list.get(0);
			if(companyAccount.getCom_type() == 2){
				return "error-商户已经销户，操作不能继续。。";
			}else{
				String  update = "update  company_account set  com_type = '"+model.getType()+"' ,updater = '"+user.getUserid()+"' , update_date = '"+DateTimeUtil.getDateTime()+"' where com_num = '"+companyAccount.getCom_num()+"'";
				this.jpm.execSql(update);
				return "ok-操作成功。。";
			}
		}else{
			return "error-商户信息错误。。";
		}
	}

	@Override
	public CardExamSetItemDTO getCardExamSet(String card_num,String center_num) throws ServiceException {
		String sql = "select si.set_num,e.set_name,e.sex,e.set_discount,e.set_amount " + 
				"from card_exam_set si " + 
				"left join exam_set e on si.set_num = e.set_num " + 
				"where si.card_num = '"+card_num.trim()+"' and e.center_num='"+center_num+"'";
		List<CardExamSetItemDTO> list = this.jqm.getList(sql, CardExamSetItemDTO.class);
		if(list.size() !=0){
			return list.get(0);
		}
		return null;
	}
	
	@Override
	public List<CardExamSetItemDTO> getCardExamCharing(String card_num,String center_num) throws ServiceException {
		String sql = "select si.card_num,h.item_code,h.item_name,h.item_category,h.sex,h.amount, " + 
				"h.item_type,si.itemnum,si.item_amount,si.discount,si.amount,si.set_num,e.set_name " + 
				"from card_exam_set_item si " + 
				"left join charging_item h on si.charging_item_code = h.item_code " + 
				"left join exam_set e on si.set_num = e.set_num " + 
				"where si.card_num = '"+card_num.trim()+"' and e.center_num='"+center_num+"'";
		List<CardExamSetItemDTO> list = this.jqm.getList(sql, CardExamSetItemDTO.class);
		if(list.size() !=0){
			return list;
		}
		return null;
	}

	@Override
	public String updExamForCardnum(String card_num,String exam_num) throws ServiceException {
		List<ExamInfo> examInfoLi = this.qm.find("from ExamInfo c where c.card_num = '"+card_num.trim()+"'");
		if(examInfoLi.size()>0) {
			return "error-该卡已使用，不能进行操作！";
		}
		List<ExamInfo> examInfoList = this.qm.find("from ExamInfo c where c.exam_num = '"+exam_num+"'");
		for (ExamInfo examInfo : examInfoList) {
			if(examInfo.getCard_num()!=null&&!"".equals(examInfo.getCard_num())) {
				return "error-体检信息已绑定卡，不能进行操作！";
			}
			examInfo.setCard_num(card_num);
			this.pm.update(examInfo);
		}
		return "ok";
	}
	
	@Override
	public List<CardInfo> getCardInfo(String card_num)throws ServiceException{
		List<CardInfo> cardlist = this.qm.find("from CardInfo c where c.card_num = '"+card_num+"'");
		return cardlist;
	}
	
	
	@Override
	public CollectFeesResult saveSettlements(CollectFeesModel model,UserDTO user,String invoice_class) throws ServiceException {
		
		ChargingSummarySingle chargingSummarySingle = new ChargingSummarySingle();
		CollectFeesResult result = new CollectFeesResult();
		
		for(ExamResultChargingItemDTO item : model.getExamInfoCharingItem()){
			ExaminfoChargingItem ec = (ExaminfoChargingItem) this.qm.load(ExaminfoChargingItem.class, item.getId());
			if(ec == null){
				result.setUser_id(user.getUserid());
				result.setFlag("error");
				result.setInfo("收费项目不存在,请先查询未检收费项目！");
				return result;
			}else if("N".equals(ec.getIsActive())){
				result.setUser_id(user.getUserid());
				result.setFlag("error");
				result.setInfo("存在已减项收费项目,请先查询未检收费项目！");
				return result;
			}
			if("Y".equals(ec.getPay_status())){
				result.setUser_id(user.getUserid());
				result.setFlag("error");
				result.setInfo("存在已收费项目,请先查询未检收费项目！");
				return result;
			}
		}
		String charging_summary_num = GetNumContral.getInstance().getParamNum("rcpt_num",user.getCenter_num());
		//1收费总记录
		chargingSummarySingle.setExam_id(model.getExam_id());
		chargingSummarySingle.setReq_num(charging_summary_num);
		chargingSummarySingle.setCharging_status("P");
		chargingSummarySingle.setAmount1(model.getAmount1());
		chargingSummarySingle.setAmount2(model.getAmount2());
		chargingSummarySingle.setDiscount(model.getDiscount());
		chargingSummarySingle.setCashier(user.getUserid());
		chargingSummarySingle.setCash_date(DateTimeUtil.parse());
		chargingSummarySingle.setIs_print_recepit(model.getIsPrintRecepit());
		chargingSummarySingle.setIs_active("Y");
		chargingSummarySingle.setCreater(user.getUserid());
		chargingSummarySingle.setCreate_time(DateTimeUtil.parse());
		chargingSummarySingle.setUpdater(user.getUserid());
		chargingSummarySingle.setUpdate_time(DateTimeUtil.parse());
		chargingSummarySingle.setDaily_status("0");
		chargingSummarySingle.setExam_num(model.getExam_num());
		chargingSummarySingle.setCenter_num(user.getCenter_num());
		
		this.pm.save(chargingSummarySingle);  //保存收费总表
	
		List<ExamResultChargingItemDTO> listItem = model.getExamInfoCharingItem();
		
		//2保存结算明细
		for(ExamResultChargingItemDTO item : listItem){
			ChargingDetailSingle chargingDetailSingle = new ChargingDetailSingle();
			
			chargingDetailSingle.setSummary_id(chargingSummarySingle.getId());
			chargingDetailSingle.setAmount(item.getItem_amount());
			chargingDetailSingle.setAmount1(item.getPersonal_pay());
			chargingDetailSingle.setCharging_item_id(item.getItem_id());
			chargingDetailSingle.setDiscount(item.getDiscount());
			chargingDetailSingle.setCreater(user.getUserid());
			chargingDetailSingle.setCreate_time(DateTimeUtil.parse());
			chargingDetailSingle.setUpdater(user.getUserid());
			chargingDetailSingle.setUpdate_time(DateTimeUtil.parse());
			
			this.pm.save(chargingDetailSingle);  //保存结算明细
		}
		
		result.setReq_num(chargingSummarySingle.getReq_num());
		result.setSummary_id(chargingSummarySingle.getId());
		result.setUser_id(user.getUserid());
		result.setFlag("ok");
		result.setInfo("结算订单生成完成！");
		return result;
	}
//	@Override
//	@SuppressWarnings("unchecked")
//	public List<ChargingDetailSingleDTO> getsettlementList(long summary_id) throws ServiceException {
//			String sql = "SELECT c.item_name,a.* FROM charging_detail_single a left join charging_item c on a.charging_item_id=c.id"
//					+ " where  a.summary_id = "+summary_id;
//			List<ChargingDetailSingleDTO> list = this.jqm.getList(sql, ChargingDetailSingleDTO.class);
//			return list;
//		}
	public void delSummaryId(long exam_id)throws ServiceException {
		Connection connection = null;		
		try {
			// 读取记录数
			connection = this.jqm.getConnection();
			connection.createStatement().executeUpdate("delete a from  charging_detail_single a left join  charging_summary_single b on a.summary_id = b.id where b.charging_status='P' and b.exam_id="+exam_id);
			connection.createStatement().executeUpdate("delete b from  charging_summary_single b where b.charging_status='P' and b.exam_id="+exam_id);
			
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			try {
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException sqle4) {
				sqle4.printStackTrace();
			}
		}
	}

	@Override
	public List<ChargingSummarySingleDTO> getChargingSummary() throws ServiceException {
		String date = DateTimeUtil.getDate2();
		String sql = "select c.id,c.req_num from charging_summary_single c where c.charging_status = 'R' and c.is_active = 'Y' and c.create_time >='"+date+" 00:00:00.000' and c.create_time <'"+date+" 23:59:59.999'";
		List<ChargingSummarySingleDTO> list = this.jqm.getList(sql, ChargingSummarySingleDTO.class);
		return list;
	}
}
