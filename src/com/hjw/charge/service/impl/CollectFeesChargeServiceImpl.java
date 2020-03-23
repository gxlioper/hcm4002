package com.hjw.charge.service.impl;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.hjw.charge.DTO.ChargingDetailSingleDTO;
import com.hjw.charge.DTO.ChargingInvoiceSingleDTO;
import com.hjw.charge.DTO.ChargingSummarySingleDTO;
import com.hjw.charge.DTO.ChargingWayDTO;
import com.hjw.charge.DTO.CollectFeesResult;
import com.hjw.charge.DTO.DictInsuranceClinicItemDTO;
import com.hjw.charge.DTO.IdentityAuthenticationDTO;
import com.hjw.charge.DTO.InsuranceOpSignStatusDTO;
import com.hjw.charge.DTO.InsureAccountDTO;
import com.hjw.charge.DTO.InsureAccountDmDTO;
import com.hjw.charge.DTO.InvoiceInformationDTO;
import com.hjw.charge.DTO.PosTransListDTO;
import com.hjw.charge.domain.CardDealCharge;
import com.hjw.charge.domain.ChargingDetailSingleCharge;
import com.hjw.charge.domain.ChargingInvoiceSingleCharge;
import com.hjw.charge.domain.ChargingSummarySingleCharge;
import com.hjw.charge.domain.ChargingWaySingleCharge;
import com.hjw.charge.domain.IdentityAuthenticationCharge;
import com.hjw.charge.model.CollectFeesModel;
import com.hjw.charge.service.CollectFeesChargeService;
import com.hjw.charge.service.PosTransListChargeService;
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
import com.hjw.charge.DTO.CardDealDTO;
import com.hjw.wst.DTO.ChargingItemDTO;
import com.hjw.wst.DTO.CustomerExamDTO;
import com.hjw.wst.DTO.CustomerInfoDTO;
import com.hjw.wst.DTO.ExamInfoDTO;
import com.hjw.wst.DTO.ExamInfoUserDTO;
import com.hjw.wst.DTO.ExamResultChargingItemDTO;
import com.hjw.wst.DTO.ExaminfoChargingItemDTO;
import com.hjw.wst.DTO.HisClinicItemPriceListDTO;
import com.hjw.wst.DTO.JobDTO;
import com.hjw.charge.DTO.InsuranceFeeDetailDTO;
import com.hjw.wst.DTO.UserDTO;
import com.hjw.wst.DTO.WebserviceConfigurationDTO;
import com.hjw.wst.config.GetNumContral;
import com.hjw.wst.domain.ChargingItem;
import com.hjw.wst.domain.CustomerInfo;
import com.hjw.wst.domain.DataDictionary;
import com.hjw.wst.domain.ExamInfo;
import com.hjw.wst.domain.ExamResultChargingItem;
import com.hjw.wst.domain.ExaminfoChargingItem;
import com.hjw.wst.domain.PacsSummary;
import com.hjw.wst.domain.Pacsdetail;
import com.hjw.wst.domain.SampleExamDetail;
import com.hjw.charge.domain.InsuranceFeeDetailCharge;
import com.hjw.charge.domain.InsurancePayerInfoCharge;
import com.hjw.charge.domain.InsureAccountCharge;
import com.hjw.charge.domain.InvoiceInformationCharge;
import com.hjw.charge.domain.NullifyInvoiceCharge;
import com.hjw.charge.domain.PosDetailCharge;
import com.hjw.charge.domain.UserInvoiceCharge;
import com.hjw.wst.domain.TeamInvoiceAccount;
import com.hjw.wst.service.CompanyService;
import com.hjw.wst.service.CustomerInfoService;
import com.hjw.wst.service.WebserviceConfigurationService;
import com.synjones.framework.exception.ServiceException;
import com.synjones.framework.persistence.JdbcPersistenceManager;
import com.synjones.framework.persistence.JdbcQueryManager;
import com.synjones.framework.persistence.PersistenceManager;
import com.synjones.framework.persistence.QueryManager;

import net.sf.json.JSONObject;

public class CollectFeesChargeServiceImpl implements CollectFeesChargeService {
    private QueryManager qm;
    private JdbcQueryManager jqm;
    private JdbcPersistenceManager jpm;
    private PersistenceManager pm;
    private WebserviceConfigurationService webserviceConfigurationService;
    private CustomerInfoService customerInfoService;
    private CompanyService companyService;
    private PosTransListChargeService posTransListService;


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

    public void setCompanyService(CompanyService companyService) {
        this.companyService = companyService;
    }

    public void setPosTransListService(PosTransListChargeService posTransListService) {
        this.posTransListService = posTransListService;
    }
    @Override
    public List<ExamResultChargingItemDTO> getwitemList(String examNum,String is_fees_qijian,String center_num) throws ServiceException {
        String sql = "select ec.id,c.id as item_id,c.item_code,c.item_name,d.dep_name,ec.exam_status,ec.item_amount,ec.discount,ec.amount,ec.itemnum,"
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
    public CustomerExamDTO getCustomerInfo(String examNum,UserDTO user) throws ServiceException {
        String isprivateflag = IsPrivateCenterConfig.isprivateflag(user);//判断用户是否有隐私资源  Y 有 N无
        String sql = "select c.arch_num,e.exam_num,e.is_guide_back,e.id,e.wuxuzongjian,dbo.fun_CharToStar(e.exam_num,'name',c.user_name,'"+isprivateflag+"') as user_name,c.sex,e.age,"
                + " (case when e.exam_type = 'G' then '个检' else e.company end) as company,ct.type_name as customer_type,"
                + " dbo.GetExamSetNameByExamID(e.exam_num) as set_name,e.picture_path, "
                + " e.past_medical_history,convert(varchar(50),e.join_date,23) as join_date,e.status,d.data_code_children as exam_type_code,"
                + " d.data_name as exam_type,g.group_name,g.amount,dbo.fun_CharToStar(e.exam_num,'phone',e.phone,'"+isprivateflag+"') as phone,cev.vip_name as vipflag,e.getReportWay,e.reportAddress  "
                + ",(select count(*) as num from exam_info where customer_id=e.customer_id and is_Active='Y') exam_count"
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

        ChargingSummarySingleCharge chargingSummarySingle = new ChargingSummarySingleCharge();
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

//		//医保项目是否对照
//		if("103".equals(model.getChargeType())||"104".equals(model.getChargeType())){
//			String message = cardSaleService.verifyMedicalItem(model.getExamInfoCharingItems(), model.getInter_class());
//			if("error".equals(message.split(",")[0])){
//				result.setFlag("error");
//				result.setInfo(message.split(",")[1]);
//				return result;
//			}
//		}

        String charging_summary_num = GetNumContral.getInstance().getParamNum("rcpt_num",user.getCenter_num());
        ChargingInvoiceSingleCharge chargingInvoiceSingle = null;
        if("Y".equals(model.getIsPrintRecepit())){

            chargingInvoiceSingle = new ChargingInvoiceSingleCharge();

            chargingInvoiceSingle.setTitle_info(model.getTitle_info());
            chargingInvoiceSingle.setInvoice_num(model.getInvoice_num());
            chargingInvoiceSingle.setInvoice_type(model.getInvoice_type());
            if(("00").equals(model.getChargeType())||("01").equals(model.getChargeType())){
                chargingInvoiceSingle.setInvoice_status("N");
            }else{
                chargingInvoiceSingle.setInvoice_status("Y");
            }
            chargingInvoiceSingle.setBill_type(model.getBill_type());
            chargingInvoiceSingle.setTax_invoices_num(model.getTax_invoices_num());
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
            CardDealCharge cardDeal= new CardDealCharge();
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
        chargingSummarySingle.setOld_req_num(charging_summary_num);
        chargingSummarySingle.setCharging_status("Y");
        chargingSummarySingle.setAmount1(model.getAmount1());
        chargingSummarySingle.setAmount2(model.getAmount2());
        chargingSummarySingle.setDiscount(model.getDiscount());
        chargingSummarySingle.setCashier(user.getUserid());
        chargingSummarySingle.setCash_date(DateTimeUtil.parse());
        chargingSummarySingle.setIs_print_recepit(model.getIsPrintRecepit());
        chargingSummarySingle.setPrescription_name(model.getPrescription_name());
        chargingSummarySingle.setPrescription_num(model.getPrescription_num());
        chargingSummarySingle.setMedical_insurance_card(model.getPatNo());
        chargingSummarySingle.setHealth_type(model.getInter_class());
        String city_cycle_code ="";
        String prov_cycle_code ="";
        //获取省医保市医保pos凭证号
//		try {
//			List<InsuranceOpSignStatusDTO> list=findSignIn(user.getUserid());
//			if(list!=null&&list.size()>0){
//				city_cycle_code=list.get(0).getCity_cycle_code();
//				prov_cycle_code=list.get(0).getProv_cycle_code();
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
        if(("00").equals(model.getChargeType())||("01").equals(model.getChargeType())){
            chargingSummarySingle.setIs_active("C");
        }else{
            chargingSummarySingle.setIs_active("Y");
        }
//		chargingSummarySingle.setIs_active("Y");
//		chargingSummarySingle.setIs_active("N");
//		chargingSummarySingle.setCity_cycle_code(city_cycle_code);
//		chargingSummarySingle.setProv_cycle_code(prov_cycle_code);

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


        Date InsuranceFeeDetailDate = DateTimeUtil.parse(); //同流水下入库时间相同 不能改
        //2保存结算明细
        List<ExamResultChargingItemDTO> listItem = model.getExamInfoCharingItem();
        for(ExamResultChargingItemDTO item : listItem){
            ChargingDetailSingleCharge chargingDetailSingle = new ChargingDetailSingleCharge();

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
            if(("00").equals(model.getChargeType())||("01").equals(model.getChargeType())){

            }else{
                ec.setPay_status("Y");
            }
//			ec.setPay_status("Y");
            ec.setAmount(item.getAmount());
            ec.setDiscount(item.getDiscount());
            ec.setPersonal_pay(item.getPersonal_pay());
            ec.setUpdater(user.getUserid());
            ec.setUpdate_time(DateTimeUtil.parse());
            this.pm.update(ec);//修改项目状态


            List<InsuranceFeeDetailDTO> customerList=runningWater(item.getItem_code(),model.getInter_class());
            if(customerList!=null&&customerList.size()>0){
                for (int i = 0; i < customerList.size(); i++) {
                    InsuranceFeeDetailCharge insuranceFeeDetail =new InsuranceFeeDetailCharge();
                    insuranceFeeDetail.setReq_num(charging_summary_num);
                    insuranceFeeDetail.setCharging_item_code(customerList.get(i).getCharging_item_code());
                    insuranceFeeDetail.setCharging_item_amount(item.getItem_amount());
                    insuranceFeeDetail.setMedical_price_id(customerList.get(i).getMedical_price_id());
                    insuranceFeeDetail.setMedical_price_num(customerList.get(i).getMedical_price_num());
                    insuranceFeeDetail.setMedical_old_price(customerList.get(i).getMedical_old_price());
                    insuranceFeeDetail.setMedical_new_price(customerList.get(i).getMedical_old_price());
                    insuranceFeeDetail.setMedical_item_code(customerList.get(i).getMedical_item_code());
                    insuranceFeeDetail.setMedical_item_num(customerList.get(i).getMedical_item_num());
                    insuranceFeeDetail.setMedical_type(model.getInter_class());
                    insuranceFeeDetail.setBusiness_type("1");
                    insuranceFeeDetail.setCreater(user.getUserid());
                    insuranceFeeDetail.setCreate_date(InsuranceFeeDetailDate);
                    this.pm.save(insuranceFeeDetail);
                }
            }
        }

        boolean isCard = false;
        //4收费方式
        List<ChargingWayDTO> listway = model.getCharingWay();
        for(ChargingWayDTO chargingWay : listway){
            ChargingWaySingleCharge chargingWaySingle = new ChargingWaySingleCharge();

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
//		if("103".equals(model.getChargeType())||"104".equals(model.getChargeType())){
//			List<InsuranceFeeDetailDTO> customerList=runningWater(model.getExam_num(),charging_summary_num);
//			if(customerList!=null&&customerList.size()>0){
//				for (int i = 0; i < customerList.size(); i++) {
//				InsuranceFeeDetail insuranceFeeDetail =new InsuranceFeeDetail();
//				insuranceFeeDetail.setReq_num(customerList.get(i).getReq_num());
//				insuranceFeeDetail.setCharging_item_code(customerList.get(i).getCharging_item_code());
//				insuranceFeeDetail.setCharging_item_amount(item.getItem_amount());
//				insuranceFeeDetail.setMedical_price_id(customerList.get(i).getMedical_price_id());
//				insuranceFeeDetail.setMedical_price_num(customerList.get(i).getMedical_price_num());
//				insuranceFeeDetail.setMedical_old_price(customerList.get(i).getMedical_old_price());
//				insuranceFeeDetail.setMedical_new_price(customerList.get(i).getMedical_old_price());
//				insuranceFeeDetail.setMedical_item_code(customerList.get(i).getMedical_item_code());
//				insuranceFeeDetail.setMedical_item_num(customerList.get(i).getMedical_item_num());
//				insuranceFeeDetail.setMedical_type(model.getInter_class());
//				insuranceFeeDetail.setBusiness_type("1");
//				insuranceFeeDetail.setCreater(user.getUserid());
//				insuranceFeeDetail.setCreate_time(DateTimeUtil.parse());
//				}
//			}
//		}
//		if(isCard){//会员刷卡，保存卡消费信息
//			List<CardInfoDTO> listCard = model.getCardInfo();
//			for(CardInfoDTO cardInfo : listCard){
//				CardDeal cardDeal = new CardDeal();
//
//				cardDeal.setCard_num(cardInfo.getCard_num());
//				cardDeal.setAmount(cardInfo.getAmount());
//				cardDeal.setCard_count(cardInfo.getCard_count()+1);
//				cardDeal.setSummary_id(chargingSummarySingle.getId());
//				cardDeal.setExaminfo_id(model.getExam_id());
//				cardDeal.setDeal_type("6");
//				cardDeal.setCreater(user.getUserid());
//				cardDeal.setDeal_date(DateTimeUtil.getDate2());
//				cardDeal.setDeal_time(DateTimeUtil.parse());
//				cardDeal.setOld_amount(cardInfo.getFace_amount());
//				cardDeal.setTrancode("001");
//				cardDeal.setExam_num(model.getExam_num());
//				this.pm.save(cardDeal);
//
//				List<CardInfo> cardlist = this.qm.find("from CardInfo c where c.card_num = '"+cardInfo.getCard_num()+"'");
//				if(cardlist.size() > 0){
//					CardInfo cardifo = cardlist.get(0);
//					cardifo.setAmount(cardInfo.getFace_amount()-cardInfo.getAmount());
//					cardifo.setCard_count(cardInfo.getCard_count()+1);
//					this.pm.update(cardifo);
//				}
//			}
//		}

        if(chargingInvoiceSingle != null){
            result.setAccount_num(chargingInvoiceSingle.getAccount_num());
        }
        result.setReq_num(chargingSummarySingle.getReq_num());
        result.setUser_id(user.getUserid());
        result.setFlag("ok");
        result.setInfo("收费成功！");
        return result;
    }

//	@Override
//	@SuppressWarnings("unchecked")
//	public List<CardInfoDTO> getCardListByexamId(long exam_id) throws ServiceException {
//		String sql = "select c.id,c.card_num,c.card_type,c.status,c.amount,c.card_count,"
//				+ "convert(varchar(50),c.limit_card_count) as limit_card_count,"
//				+ "CONVERT(varchar(100),c.deadline,23) as deadline,d.data_name as card_level,"
//				+ "c.remark,c.company from exam_info e,customer_info ci,card_info c left join data_dictionary d on d.id = c.card_level "
//				+ "where c.member_id = ci.arch_num and ci.id = e.customer_id and e.id = "+exam_id;
//		List<CardInfoDTO> list = this.jqm.getList(sql, CardInfoDTO.class);
//		return list;
//	}
//
//	@Override
//	@SuppressWarnings("unchecked")
//	public CardInfoDTO getCardInfoByNum(String card_num) throws ServiceException {
//		String sql = "select c.id,c.card_num,c.card_pwd,c.card_type,c.status,c.amount,c.card_count,"
//				+ "CONVERT(varchar(50),c.limit_card_count) as limit_card_count,"
//				+ "CONVERT(varchar(100),c.deadline,23) as deadline,c.card_level,c.remark,c.company,"
//				+ "d.data_name as card_level_y,c.member_id,cm.user_name as member_name,convert(varchar(100), GETDATE(),23) as get_date ,c.hair_card_status from card_info c "
//				+ "left join customer_info cm on cm.arch_num=c.member_id left join data_dictionary d on c.card_level=d.id where "
//				+ "c.card_num = '"+card_num.trim()+"'";
//		List<CardInfoDTO> list = this.jqm.getList(sql, CardInfoDTO.class);
//		if(list.size() !=0){
//			return list.get(0);
//		}
//		return null;
//	}

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
                + "on a.charging_way = d.id where d.data_code = 'SFFSLX' and d.isActive = 'Y'  and a.amount!='0'";
        List<ChargingWayDTO> list = this.jqm.getList(sql, ChargingWayDTO.class);
        return list;
    }

    /**
     *
     * @Title: getChargingInvoiceSingleOfaccountnum
     * @Description:
     * @param: @param account_num
     * @param: @return
     * @param: @throws ServiceException
     * @return: ChargingInvoiceSingle
     * @throws
     */
    @Override
    @SuppressWarnings("unchecked")
    public ChargingInvoiceSingleCharge getChargingInvoiceSingleOfaccountnum(String account_num,String center_num) throws ServiceException{
        List<ChargingInvoiceSingleCharge> invoiceList = this.qm.find("from ChargingInvoiceSingleCharge where account_num = '"+account_num+"' and center_num='"+center_num+"'");
        ChargingInvoiceSingleCharge cis= new ChargingInvoiceSingleCharge();
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
        String sql ="select cd.card_num,sum(cd.amount) amount from card_deal cd where cd.examinfo_id = " + model.getExam_id()
                + " and cd.deal_type in (6,7) group by cd.card_num";
//		List<CardInfoDTO> listCard = this.jqm.getList(sql, CardInfoDTO.class);
        String charging_summary_num = GetNumContral.getInstance().getParamNum("rcpt_num",user.getCenter_num());
        if("Y".equals(IS_SINGLE_REFUND)){//表示单项退费

            if("Y".equals(model.getIsPrintRecepit())){//表示同时作废发票

                List<ChargingInvoiceSingleCharge> invoiceList = this.qm.find("from ChargingInvoiceSingleCharge where account_num = '"+model.getAccount_num()+"'");
                if(invoiceList.size() != 0){

                    ChargingInvoiceSingleCharge chargingInvoiceSingle = invoiceList.get(0);

                    List<NullifyInvoiceCharge> zuofeilist = this.qm.find("from NullifyInvoiceCharge where invoice_num = '"+chargingInvoiceSingle.getInvoice_num()+"' and invoice_class = '"+chargingInvoiceSingle.getInvoice_class()+"'");
                    if(zuofeilist.size() > 0){
                        return "error-该发票号（"+chargingInvoiceSingle.getInvoice_num()+"）已作废，不能再次作废，请先查询!";
                    }
                    chargingInvoiceSingle.setInvoice_status("N");
                    chargingInvoiceSingle.setCancel_time(DateTimeUtil.parse());
                    chargingInvoiceSingle.setCanceller(user.getUserid());

                    this.pm.update(chargingInvoiceSingle);

                    //保存发票记录表
                    CardDealCharge cardDeal= new CardDealCharge();
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
                    NullifyInvoiceCharge nullifyInvoice = new NullifyInvoiceCharge();
                    nullifyInvoice.setInvoice_num(chargingInvoiceSingle.getInvoice_num());
                    nullifyInvoice.setInvoice_status("N");
                    nullifyInvoice.setBill_type(chargingInvoiceSingle.getBill_type());
                    nullifyInvoice.setTax_invoices_num(chargingInvoiceSingle.getTax_invoices_num());
                    nullifyInvoice.setInvoice_class(chargingInvoiceSingle.getInvoice_class());
                    nullifyInvoice.setCreater(user.getUserid());
                    nullifyInvoice.setCreate_time(DateTimeUtil.parse());
                    nullifyInvoice.setDaily_status("0");
                    nullifyInvoice.setCenter_num(user.getCenter_num());
                    nullifyInvoice.setAccount_num(GetNumContral.getInstance().getParamNum("account_num",user.getCenter_num()));
                    this.pm.save(nullifyInvoice);

                    List<ChargingSummarySingleCharge> summarySingleList = this.qm.find("from ChargingSummarySingleCharge where invoice_id = "+chargingInvoiceSingle.getId());

                    for(ChargingSummarySingleCharge chargingSummarySingle : summarySingleList){

                        chargingSummarySingle.setInvoice_id(0);
//						chargingSummarySingle.setIs_print_recepit("N");

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
            ChargingSummarySingleCharge chargingSummarySingle = new ChargingSummarySingleCharge();
            String city_cycle_code ="";
            String prov_cycle_code ="";
            //获取省医保市医保pos凭证号
//			try {
//				List<InsuranceOpSignStatusDTO> list=findSignIn(user.getUserid());
//				if(list!=null&&list.size()>0){
//					city_cycle_code=list.get(0).getCity_cycle_code();
//					prov_cycle_code=list.get(0).getProv_cycle_code();
//				}
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
            chargingSummarySingle.setReq_num(charging_summary_num);
            chargingSummarySingle.setOld_req_num(model.getReq_nums());
            chargingSummarySingle.setCity_cycle_code(city_cycle_code);
            chargingSummarySingle.setProv_cycle_code(prov_cycle_code);
            chargingSummarySingle.setExam_id(model.getExam_id());
            chargingSummarySingle.setCharging_status("M");
            chargingSummarySingle.setAmount1(model.getAmount1());
            chargingSummarySingle.setAmount2(model.getAmount2());
            chargingSummarySingle.setDiscount(model.getDiscount());
            chargingSummarySingle.setCashier(user.getUserid());
            chargingSummarySingle.setCash_date(DateTimeUtil.parse());
            chargingSummarySingle.setIs_print_recepit("N");
//			if(("00").equals(model.getChargeType())||("01").equals(model.getChargeType())){
//				chargingSummarySingle.setIs_active("Y");
//			}else{
            chargingSummarySingle.setIs_active("N");
//			}
            chargingSummarySingle.setCreater(user.getUserid());
            chargingSummarySingle.setCreate_time(DateTimeUtil.parse());
            chargingSummarySingle.setUpdater(user.getUserid());
            chargingSummarySingle.setUpdate_time(DateTimeUtil.parse());
            chargingSummarySingle.setDaily_status("0");
            chargingSummarySingle.setExam_num(model.getExam_num());
            chargingSummarySingle.setCenter_num(user.getCenter_num());

            this.pm.save(chargingSummarySingle);  //保存收费总表


            for(ExamResultChargingItemDTO item : listItem){
                ChargingDetailSingleCharge chargingDetailSingle = new ChargingDetailSingleCharge();

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

                List<ExaminfoChargingItem> examinfoChargingItemList = this.qm.find("from ExaminfoChargingItem where id = "+item.getId());
                if(examinfoChargingItemList.size() != 0){
                    ExaminfoChargingItem examinfoChargingItem = examinfoChargingItemList.get(0);

                    examinfoChargingItem.setPay_status("M");
                    if(("00").equals(model.getChargeType())||("01").equals(model.getChargeType())){
//
                    }else{

                        examinfoChargingItem.setIsActive("N");
                    }

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
                ChargingWaySingleCharge chargingWaySingle = new ChargingWaySingleCharge();

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

//			if(isCard){//会员刷卡，保存卡消费信息
//				for(CardInfoDTO carddto : listCard){
//					List<CardInfo> cardlist = this.qm.find("from CardInfo c where c.card_num = '"+carddto.getCard_num()+"'");
//					if(cardlist.size() > 0 && carddto.getAmount() > 0 && amount > 0){
//						CardInfo cardInfo = cardlist.get(0);
//						CardDeal cardDeals = new CardDeal();
//						cardDeals.setCard_num(cardInfo.getCard_num());
//						cardDeals.setCard_count(cardInfo.getCard_count());
//						cardDeals.setOld_amount(cardInfo.getAmount());
//						cardDeals.setSummary_id(chargingSummarySingle.getId());
//						cardDeals.setExaminfo_id(model.getExam_id());
//						cardDeals.setDeal_type("7");
//						cardDeals.setCreater(user.getUserid());
//						cardDeals.setDeal_date(DateTimeUtil.getDate2());
//						cardDeals.setDeal_time(DateTimeUtil.parse());
//						cardDeals.setTrancode("001");
//						cardDeals.setExam_num(model.getExam_num());
//						if(carddto.getAmount() > amount){
//							cardDeals.setAmount(-amount);
//							cardInfo.setAmount(amount+cardInfo.getAmount());
//							amount = 0.0;
//						}else{
//							cardDeals.setAmount(-carddto.getAmount());
//							cardInfo.setAmount(carddto.getAmount()+cardInfo.getAmount());
//							cardInfo.setCard_count(cardInfo.getCard_count()-1);
//							amount = amount - carddto.getAmount();
//						}
//						this.pm.save(cardDeals);
//						this.pm.update(cardInfo);
//					}
//				}
//			}
            return "ok-退费成功！";
        }else{//一退全退

            boolean is_his_sq = false; //项目是否发送申请

            List<String> REQ_NOS= new ArrayList<String>();//申请单号
            if("Y".equals(model.getIsPrintRecepit())){//按收据单号退费

                List<ChargingInvoiceSingleCharge> invoiceList = this.qm.find("from ChargingInvoiceSingleCharge where account_num = '"+model.getAccount_num()+"'");
                if(invoiceList.size() != 0){
                    ChargingInvoiceSingleCharge chargingInvoiceSingle = invoiceList.get(0);

                    List<NullifyInvoiceCharge> zuofeilist = this.qm.find("from NullifyInvoiceCharge where invoice_num = '"+chargingInvoiceSingle.getInvoice_num()+"' and invoice_class = '"+chargingInvoiceSingle.getInvoice_class()+"'");
                    if(zuofeilist.size() > 0){
                        return "error-该发票号（"+chargingInvoiceSingle.getInvoice_num()+"）已作废，不能再次作废，请先查询!";
                    }
                    List<ChargingSummarySingleCharge> summarySingleList = this.qm.find("from ChargingSummarySingleCharge where invoice_id = "+chargingInvoiceSingle.getId());
                    for(ChargingSummarySingleCharge chargingSummary : summarySingleList){
                        if(chargingSummary.getIs_active().equals("N")){
                            return "error-该收费记录已作废，请查询加载最新数据!";
                        }
                    }
                    chargingInvoiceSingle.setInvoice_status("N");
                    chargingInvoiceSingle.setCancel_time(DateTimeUtil.parse());
                    chargingInvoiceSingle.setCanceller(user.getUserid());

                    this.pm.update(chargingInvoiceSingle);

                    //保存发票记录表
                    CardDealCharge cardDeal= new CardDealCharge();
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
                    NullifyInvoiceCharge nullifyInvoice = new NullifyInvoiceCharge();
                    nullifyInvoice.setInvoice_num(chargingInvoiceSingle.getInvoice_num());
                    nullifyInvoice.setInvoice_status("N");
                    nullifyInvoice.setBill_type(chargingInvoiceSingle.getBill_type());
                    nullifyInvoice.setTax_invoices_num(chargingInvoiceSingle.getTax_invoices_num());
                    nullifyInvoice.setInvoice_class(chargingInvoiceSingle.getInvoice_class());
                    nullifyInvoice.setCreater(user.getUserid());
                    nullifyInvoice.setCreate_time(DateTimeUtil.parse());
                    nullifyInvoice.setDaily_status("0");
                    nullifyInvoice.setAccount_num(GetNumContral.getInstance().getParamNum("account_num",user.getCenter_num()));
                    nullifyInvoice.setCenter_num(user.getCenter_num());
                    this.pm.save(nullifyInvoice);

                    for(ChargingSummarySingleCharge chargingSummary : summarySingleList){

                        REQ_NOS.add(charging_summary_num);
                        chargingSummary.setIs_active("N");
                        this.pm.update(chargingSummary);

                        ChargingSummarySingleCharge chargingSummarySingle = new ChargingSummarySingleCharge();
//						chargingSummarySingle.setReq_num(charging_summary_num);


                        String city_cycle_code ="";
                        String prov_cycle_code ="";
                        //获取省医保市医保pos凭证号
//						try {
//							List<InsuranceOpSignStatusDTO> list=findSignIn(user.getUserid());
//							if(list!=null&&list.size()>0){
//								city_cycle_code=list.get(0).getCity_cycle_code();
//								prov_cycle_code=list.get(0).getProv_cycle_code();
//							}
//						} catch (Exception e) {
//							e.printStackTrace();
//						}
                        chargingSummarySingle.setOld_req_num(chargingSummary.getReq_num());
                        chargingSummarySingle.setReq_num(charging_summary_num);
                        chargingSummarySingle.setCity_cycle_code(city_cycle_code);
                        chargingSummarySingle.setProv_cycle_code(prov_cycle_code);
                        chargingSummarySingle.setHealth_type(model.getInter_class());

                        chargingSummarySingle.setExam_id(chargingSummary.getExam_id());
                        chargingSummarySingle.setCharging_status("M");
                        chargingSummarySingle.setAmount1(chargingSummary.getAmount1());
                        chargingSummarySingle.setAmount2(chargingSummary.getAmount2());
                        chargingSummarySingle.setDiscount(chargingSummary.getDiscount());
                        chargingSummarySingle.setCashier(user.getUserid());
                        chargingSummarySingle.setCash_date(DateTimeUtil.parse());
                        chargingSummarySingle.setIs_print_recepit("Y");
//						chargingSummarySingle.setIs_active("N");
//						if(("00").equals(model.getChargeType())||("01").equals(model.getChargeType())){
//							chargingSummarySingle.setIs_active("Y");
//						}else{
                        chargingSummarySingle.setIs_active("N");
//						}
                        chargingSummarySingle.setCreater(user.getUserid());
                        chargingSummarySingle.setCreate_time(DateTimeUtil.parse());
                        chargingSummarySingle.setUpdater(user.getUserid());
                        chargingSummarySingle.setUpdate_time(DateTimeUtil.parse());
                        chargingSummarySingle.setInvoice_id(chargingInvoiceSingle.getId());
                        chargingSummarySingle.setDaily_status("0");
                        chargingSummarySingle.setExam_num(model.getExam_num());
                        chargingSummarySingle.setCenter_num(user.getCenter_num());

                        this.pm.save(chargingSummarySingle); //保存退费信息总表

                        List<ChargingDetailSingleCharge> detailSingleList = this.qm.find("from ChargingDetailSingleCharge where summary_id = "+chargingSummary.getId());

                        for(ChargingDetailSingleCharge detailSingle : detailSingleList){
                            ChargingDetailSingleCharge chargingDetailSingle = new ChargingDetailSingleCharge();

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

                        List<ChargingWaySingleCharge> waySingleList = this.qm.find("from ChargingWaySingleCharge where summary_id = " +chargingSummary.getId());
                        boolean isCard = false;
                        Double amount = 0.0;
                        for(ChargingWaySingleCharge WaySingle : waySingleList){
                            ChargingWaySingleCharge chargingWaySingle = new ChargingWaySingleCharge();
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

//						if(isCard){//会员刷卡，保存卡消费信息
//							for(CardInfoDTO carddto : listCard){
//								List<CardInfo> cardlist = this.qm.find("from CardInfo c where c.card_num = '"+carddto.getCard_num()+"'");
//								if(cardlist.size() > 0 && carddto.getAmount() > 0 && amount > 0){
//									CardInfo cardInfo = cardlist.get(0);
//									CardDeal cardDeals = new CardDeal();
//									cardDeals.setCard_num(cardInfo.getCard_num());
//									cardDeals.setCard_count(cardInfo.getCard_count());
//									cardDeals.setOld_amount(cardInfo.getAmount());
//									cardDeals.setSummary_id(chargingSummarySingle.getId());
//									cardDeals.setExaminfo_id(model.getExam_id());
//									cardDeals.setDeal_type("7");
//									cardDeals.setCreater(user.getUserid());
//									cardDeals.setDeal_date(DateTimeUtil.getDate2());
//									cardDeals.setDeal_time(DateTimeUtil.parse());
//									cardDeals.setTrancode("001");
//									if(carddto.getAmount() > amount){
//										cardDeals.setAmount(-amount);
//										cardInfo.setAmount(amount+cardInfo.getAmount());
//										amount = 0.0;
//									}else{
//										cardDeals.setAmount(-carddto.getAmount());
//										cardInfo.setAmount(carddto.getAmount()+cardInfo.getAmount());
//										cardInfo.setCard_count(cardInfo.getCard_count()-1);
//										amount = amount - carddto.getAmount();
//									}
//									cardDeal.setExam_num(model.getExam_num());
//									this.pm.save(cardDeals);
//									this.pm.update(cardInfo);
//								}
//							}
//						}
                    }

                    List<ExamResultChargingItemDTO> listItem = model.getExamInfoCharingItem();
                    for(ExamResultChargingItemDTO item : listItem){
                        List<ExaminfoChargingItem> examinfoChargingItemList = this.qm.find("from ExaminfoChargingItem where id = "+item.getId());
                        if(examinfoChargingItemList.size() != 0){
                            ExaminfoChargingItem examinfoChargingItem = examinfoChargingItemList.get(0);

                            examinfoChargingItem.setPay_status("M");
                            if(("00").equals(model.getChargeType())||("01").equals(model.getChargeType())){

                            }else{

                                examinfoChargingItem.setIsActive("N");
                            }

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

                List<ChargingSummarySingleCharge> sumarySingleList = this.qm.find("from ChargingSummarySingleCharge where req_num = '"+model.getReq_nums()+"'");
                for(ChargingSummarySingleCharge chargingSummary : sumarySingleList){
                    if(chargingSummary.getIs_active().equals("N")){
                        return "error-该收费记录已作废，请查询加载最新数据!";
                    }
                }
                if(sumarySingleList.size() != 0){
                    sumarySingleList.get(0).setIs_active("N");
                    this.pm.update(sumarySingleList.get(0)); //将原来的总表置为N

                    ChargingSummarySingleCharge chargingSummarySingle = new ChargingSummarySingleCharge();
                    chargingSummarySingle.setOld_req_num(sumarySingleList.get(0).getReq_num());


                    String city_cycle_code ="";
                    String prov_cycle_code ="";
                    //获取省医保市医保pos凭证号
//					try {
//						List<InsuranceOpSignStatusDTO> list=findSignIn(user.getUserid());
//						if(list!=null&&list.size()>0){
//							city_cycle_code=list.get(0).getCity_cycle_code();
//							prov_cycle_code=list.get(0).getProv_cycle_code();
//						}
//					} catch (Exception e) {
//						e.printStackTrace();
//					}
                    chargingSummarySingle.setReq_num(charging_summary_num);
                    chargingSummarySingle.setCity_cycle_code(city_cycle_code);
                    chargingSummarySingle.setProv_cycle_code(prov_cycle_code);

                    chargingSummarySingle.setHealth_type(model.getInter_class());
                    chargingSummarySingle.setExam_id(sumarySingleList.get(0).getExam_id());
                    chargingSummarySingle.setCharging_status("M");
                    chargingSummarySingle.setAmount1(sumarySingleList.get(0).getAmount1());
                    chargingSummarySingle.setAmount2(sumarySingleList.get(0).getAmount2());
                    chargingSummarySingle.setDiscount(sumarySingleList.get(0).getDiscount());
                    chargingSummarySingle.setCashier(user.getUserid());
                    chargingSummarySingle.setCash_date(DateTimeUtil.parse());
                    chargingSummarySingle.setIs_print_recepit("N");
//					if(("00").equals(model.getChargeType())||("01").equals(model.getChargeType())){
//						chargingSummarySingle.setIs_active("Y");
//					}else{
                    chargingSummarySingle.setIs_active("N");
//					}
//					chargingSummarySingle.setIs_active("N");
                    chargingSummarySingle.setCreater(user.getUserid());
                    chargingSummarySingle.setCreate_time(DateTimeUtil.parse());
                    chargingSummarySingle.setUpdater(user.getUserid());
                    chargingSummarySingle.setUpdate_time(DateTimeUtil.parse());
                    chargingSummarySingle.setDaily_status("0");
                    chargingSummarySingle.setExam_num(model.getExam_num());
                    chargingSummarySingle.setCenter_num(user.getCenter_num());

                    this.pm.save(chargingSummarySingle); //保存退费信息总表

                    List<ChargingDetailSingleCharge> detailSingleList = this.qm.find("from ChargingDetailSingleCharge where summary_id = "+sumarySingleList.get(0).getId());

                    for(ChargingDetailSingleCharge detailSingle : detailSingleList){
                        ChargingDetailSingleCharge chargingDetailSingle = new ChargingDetailSingleCharge();

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

                            if("Y".equals(examItem.getHis_req_status())){
                                is_his_sq = true;
                            }
                            examItem.setPay_status("N");
                            examItem.setHis_req_status("N");
                            this.pm.update(examItem);
                        }
                    }

                    List<ChargingWaySingleCharge> waySingleList = this.qm.find("from ChargingWaySingleCharge where summary_id = " +sumarySingleList.get(0).getId());
                    boolean isCard = false;
                    Double amount = 0.0;
                    for(ChargingWaySingleCharge WaySingle : waySingleList){
                        ChargingWaySingleCharge chargingWaySingle = new ChargingWaySingleCharge();
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
//					if(isCard){//会员刷卡，保存卡消费信息
//						for(CardInfoDTO carddto : listCard){
//							List<CardInfo> cardlist = this.qm.find("from CardInfo c where c.card_num = '"+carddto.getCard_num()+"'");
//							if(cardlist.size() > 0 && carddto.getAmount() > 0 && amount > 0){
//								CardInfo cardInfo = cardlist.get(0);
//								CardDeal cardDeals = new CardDeal();
//								cardDeals.setCard_num(cardInfo.getCard_num());
//								cardDeals.setCard_count(cardInfo.getCard_count());
//								cardDeals.setOld_amount(cardInfo.getAmount());
//								cardDeals.setSummary_id(chargingSummarySingle.getId());
//								cardDeals.setExaminfo_id(model.getExam_id());
//								cardDeals.setDeal_type("7");
//								cardDeals.setCreater(user.getUserid());
//								cardDeals.setDeal_date(DateTimeUtil.getDate2());
//								cardDeals.setDeal_time(DateTimeUtil.parse());
//								cardDeals.setTrancode("001");
//								if(carddto.getAmount() > amount){
//									cardDeals.setAmount(-amount);
//									cardInfo.setAmount(amount+cardInfo.getAmount());
//									amount = 0.0;
//								}else{
//									cardDeals.setAmount(-carddto.getAmount());
//									cardInfo.setAmount(carddto.getAmount()+cardInfo.getAmount());
//									cardInfo.setCard_count(cardInfo.getCard_count()-1);
//									amount = amount - carddto.getAmount();
//								}
//								cardDeals.setExam_num(model.getExam_num());
//								this.pm.save(cardDeals);
//								this.pm.update(cardInfo);
//							}
//						}
//					}
                }

                List<ExamResultChargingItemDTO> listItem = model.getExamInfoCharingItem();
                for(ExamResultChargingItemDTO item : listItem){
                    List<ExaminfoChargingItem> examinfoChargingItemList = this.qm.find("from ExaminfoChargingItem where id = "+item.getId());
                    if(examinfoChargingItemList.size() != 0){
                        ExaminfoChargingItem examinfoChargingItem = examinfoChargingItemList.get(0);
                        examinfoChargingItem.setPay_status("M");
                        if(("00").equals(model.getChargeType())||("01").equals(model.getChargeType())){

                        }else{
                            examinfoChargingItem.setIsActive("N");
                        }

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
    //单项退费，
    public String saveTuifeiDanXiang(CollectFeesModel model, UserDTO user) throws ServiceException {
        List<ExamInfo> listinfo = this.qm.find("from ExamInfo e where e.id = '"+model.getExam_id()+"'");
        ExamInfo examinfo = null;
        if(listinfo.size() == 0){
            return "error-该体检号不存在体检信息";
        }else{
            examinfo = listinfo.get(0);
        }
        String sql ="select cd.card_num,sum(cd.amount) amount from card_deal cd where cd.examinfo_id = " + model.getExam_id()
                + " and cd.deal_type in (6,7) group by cd.card_num";
//		List<CardInfoDTO> listCard = this.jqm.getList(sql, CardInfoDTO.class);
        String charging_summary_num = GetNumContral.getInstance().getParamNum("rcpt_num",user.getCenter_num());
        if("Y".equals(model.getIsPrintRecepit())){//表示同时作废发票

            List<ChargingInvoiceSingleCharge> invoiceList = this.qm.find("from ChargingInvoiceSingleCharge where account_num = '"+model.getAccount_num()+"'");
            if(invoiceList.size() != 0){

                ChargingInvoiceSingleCharge chargingInvoiceSingle = invoiceList.get(0);

                List<NullifyInvoiceCharge> zuofeilist = this.qm.find("from NullifyInvoiceCharge where invoice_num = '"+chargingInvoiceSingle.getInvoice_num()+"' and invoice_class = '"+chargingInvoiceSingle.getInvoice_class()+"'");
                if(zuofeilist.size() > 0){
                    return "error-该发票号（"+chargingInvoiceSingle.getInvoice_num()+"）已作废，不能再次作废，请先查询!";
                }
                chargingInvoiceSingle.setInvoice_status("N");
                chargingInvoiceSingle.setCancel_time(DateTimeUtil.parse());
                chargingInvoiceSingle.setCanceller(user.getUserid());

                this.pm.update(chargingInvoiceSingle);

                //保存发票记录表
                CardDealCharge cardDeal= new CardDealCharge();
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
                NullifyInvoiceCharge nullifyInvoice = new NullifyInvoiceCharge();
                nullifyInvoice.setInvoice_num(chargingInvoiceSingle.getInvoice_num());
                nullifyInvoice.setInvoice_status("N");
                nullifyInvoice.setBill_type(chargingInvoiceSingle.getBill_type());
                nullifyInvoice.setTax_invoices_num(chargingInvoiceSingle.getTax_invoices_num());
                nullifyInvoice.setInvoice_class(chargingInvoiceSingle.getInvoice_class());
                nullifyInvoice.setCreater(user.getUserid());
                nullifyInvoice.setCreate_time(DateTimeUtil.parse());
                nullifyInvoice.setDaily_status("0");
                nullifyInvoice.setAccount_num(GetNumContral.getInstance().getParamNum("account_num",user.getCenter_num()));
                nullifyInvoice.setCenter_num(user.getCenter_num());
                this.pm.save(nullifyInvoice);

                List<ChargingSummarySingleCharge> summarySingleList = this.qm.find("from ChargingSummarySingleCharge where invoice_id = "+chargingInvoiceSingle.getId());

                for(ChargingSummarySingleCharge chargingSummarySingle : summarySingleList){
                    chargingSummarySingle.setInvoice_id(0);
                    chargingSummarySingle.setIs_print_recepit("N");
                    this.pm.update(chargingSummarySingle);
                }
            }
        }

        List<ExamResultChargingItemDTO> listItem = model.getExamInfoCharingItem();
        for(ExamResultChargingItemDTO item : listItem){
            List<ExaminfoChargingItem> examinfoChargingItemList = this.qm.find("from ExaminfoChargingItem where id = "+item.getId());
            if(examinfoChargingItemList.size() != 0){
                if(examinfoChargingItemList.get(0).getPay_status().equals("M")){
                    return "error-存在已退费的收费项目，请查询加载最新数据!";
                }
            }
        }
        ChargingSummarySingleCharge chargingSummarySingle = new ChargingSummarySingleCharge();
        String city_cycle_code ="";
        String prov_cycle_code ="";
        //获取省医保市医保pos凭证号
//			try {
//				List<InsuranceOpSignStatusDTO> list=findSignIn(user.getUserid());
//				if(list!=null&&list.size()>0){
//					city_cycle_code=list.get(0).getCity_cycle_code();
//					prov_cycle_code=list.get(0).getProv_cycle_code();
//				}
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
        chargingSummarySingle.setReq_num(charging_summary_num);
        chargingSummarySingle.setOld_req_num(model.getReq_nums());
//			chargingSummarySingle.setCity_cycle_code(city_cycle_code);
//			chargingSummarySingle.setProv_cycle_code(prov_cycle_code);
        chargingSummarySingle.setExam_id(model.getExam_id());
        chargingSummarySingle.setCharging_status("M");
        chargingSummarySingle.setAmount1(model.getAmount1());
        chargingSummarySingle.setAmount2(model.getAmount2());
        chargingSummarySingle.setDiscount(model.getDiscount());
        chargingSummarySingle.setCashier(user.getUserid());
        chargingSummarySingle.setCash_date(DateTimeUtil.parse());
        chargingSummarySingle.setIs_print_recepit("N");
//			if(("00").equals(model.getChargeType())||("01").equals(model.getChargeType())){
//				chargingSummarySingle.setIs_active("Y");
//			}else{
        chargingSummarySingle.setIs_active("N");
//			}
        chargingSummarySingle.setCreater(user.getUserid());
        chargingSummarySingle.setCreate_time(DateTimeUtil.parse());
        chargingSummarySingle.setUpdater(user.getUserid());
        chargingSummarySingle.setUpdate_time(DateTimeUtil.parse());
        chargingSummarySingle.setDaily_status("0");
        chargingSummarySingle.setExam_num(model.getExam_num());
        chargingSummarySingle.setCenter_num(user.getCenter_num());
        this.pm.save(chargingSummarySingle);  //保存收费总表


        for(ExamResultChargingItemDTO item : listItem){
            ChargingDetailSingleCharge chargingDetailSingle = new ChargingDetailSingleCharge();

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

            List<ExaminfoChargingItem> examinfoChargingItemList = this.qm.find("from ExaminfoChargingItem where id = "+item.getId());
            if(examinfoChargingItemList.size() != 0){
                ExaminfoChargingItem examinfoChargingItem = examinfoChargingItemList.get(0);


                if(("00").equals(model.getChargeType())||("01").equals(model.getChargeType())){
//
                }else{
                    examinfoChargingItem.setPay_status("M");
                    examinfoChargingItem.setIsActive("N");
                }

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
            ChargingWaySingleCharge chargingWaySingle = new ChargingWaySingleCharge();

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

        return "ok-退费成功！";
    }

    public String deleteRefund(CollectFeesModel model, UserDTO user)throws ServiceException{
        //一退全退
//		if("Y".equals(model.getIsPrintRecepit())){//按收据单号退费
        List<ChargingInvoiceSingleCharge> invoiceList = this.qm.find("from ChargingInvoiceSingleCharge where account_num = '"+model.getAccount_num()+"'");
        if(invoiceList.size() != 0){
            ChargingInvoiceSingleCharge chargingInvoiceSingle = invoiceList.get(0);
            List<ChargingSummarySingleCharge> summarySingleList = this.qm.find("from ChargingSummarySingleCharge where invoice_id = "+chargingInvoiceSingle.getId());
            chargingInvoiceSingle.setInvoice_status("Y");
            chargingInvoiceSingle.setCancel_time(DateTimeUtil.parse());
            chargingInvoiceSingle.setCanceller(user.getUserid());
            this.pm.update(chargingInvoiceSingle);
            //删除发票记录表
            String sql1 = "delete from card_deal where card_num='"+chargingInvoiceSingle.getInvoice_num()+"'";
            this.jpm.executeSql(sql1); //
            //删除作废发票流水表
            String sql0 = "delete from nullify_invoice  where  invoice_num "+ chargingInvoiceSingle.getInvoice_num()+"'";// invoice_num='"+invoice_num+"' ";
            this.jpm.executeSql(sql0); //
            for(ChargingSummarySingleCharge chargingSummary : summarySingleList){
                chargingSummary.setIs_active("Y");
                this.pm.update(chargingSummary); //将原来的总表置为N
//					String sql4 = " delete from  charging_summary_single where old_req_num="+ chargingSummary.getReq_num()+"'";
//					this.jpm.executeSql(sql4); //
                String sql3 = " delete from  charging_detail_single where summary_id=(select top 1 id from  charging_summary_single where old_req_num='"+ model.getReq_nums()+"')";
                this.jpm.executeSql(sql3); //
                String sql2 = " delete from  charging_way_single where summary_id=(select top 1 id from  charging_summary_single where old_req_num='"+ model.getReq_nums()+"')";
                this.jpm.executeSql(sql2); //
                List<ExamResultChargingItemDTO> listItem = model.getExamInfoCharingItem();
                for(ExamResultChargingItemDTO item : listItem){
                    List<ExaminfoChargingItem> examinfoChargingItemList = this.qm.find("from ExaminfoChargingItem where id = "+item.getId());
                    if(examinfoChargingItemList.size() != 0){
                        ExaminfoChargingItem examinfoChargingItem = examinfoChargingItemList.get(0);
                        examinfoChargingItem.setPay_status("Y");
                        examinfoChargingItem.setIsActive("Y");
                        examinfoChargingItem.setUpdater(user.getUserid());
                        examinfoChargingItem.setUpdate_time(DateTimeUtil.parse());
                        this.pm.update(examinfoChargingItem);
                    }
                }

            }
        }else{//按申请单号退费

            List<ChargingSummarySingleCharge> sumarySingleList = this.qm.find("from ChargingSummarySingleCharge where req_num = '"+model.getReq_nums()+"'");
            if(sumarySingleList.size() != 0){
                sumarySingleList.get(0).setIs_active("Y");
                this.pm.update(sumarySingleList.get(0)); //将原来的总表置为N

                String sql4 = " delete from  charging_summary_single where old_req_num='"+ sumarySingleList.get(0).getReq_num()+"'";
                this.jpm.executeSql(sql4); //
                String sql3 = " delete from  charging_detail_single where summary_id=(select top 1 id from  charging_summary_single where old_req_num='"+ model.getReq_nums()+"')";
                this.jpm.executeSql(sql3); //
                String sql2 = " delete from  charging_way_single where summary_id=(select top 1 id from  charging_summary_single where old_req_num='"+ model.getReq_nums()+"')";
                this.jpm.executeSql(sql2); //
            }
            List<ExamResultChargingItemDTO> listItem = model.getExamInfoCharingItem();
            for(ExamResultChargingItemDTO item : listItem){
                List<ExaminfoChargingItem> examinfoChargingItemList = this.qm.find("from ExaminfoChargingItem where id = "+item.getId());
                if(examinfoChargingItemList.size() != 0){
                    ExaminfoChargingItem examinfoChargingItem = examinfoChargingItemList.get(0);
                    examinfoChargingItem.setPay_status("Y");
                    examinfoChargingItem.setIsActive("Y");
                    examinfoChargingItem.setUpdater(user.getUserid());
                    examinfoChargingItem.setUpdate_time(DateTimeUtil.parse());
                    this.pm.update(examinfoChargingItem);
                }
            }

        }
        return "ok-删除退费信息成功！";

    }
//	@SuppressWarnings("unchecked")
//	@Override
//	public List<CardInfoDTO> getCardInfo(long exam_id) throws ServiceException{
//		String sql =" select ci.card_num,cd.amount,ci.card_count,ci.card_type from card_deal cd,card_info ci "
//				+ "where ci.card_num = cd.card_num and cd.examinfo_id = "+exam_id+" and cd.deal_type = 6 order by ci.card_type";
//		List<CardInfoDTO> listCard = this.jqm.getList(sql, CardInfoDTO.class);
//		return listCard;
//	}

    @SuppressWarnings("unchecked")
    @Override
    public String getMaxFaPiaoHaoByUser(long userId) throws ServiceException {
        String sqlstr = "select invoice_num,id from [charging_invoice_group] "
                + "union select invoice_num,id from [charging_invoice_single] "
                + " where invoice_maker = "+userId+" order by id desc ";
        List<ChargingInvoiceSingleCharge> list = this.jqm.getList(sqlstr, ChargingInvoiceSingleCharge.class);
        if (list.size() != 0) {
            ChargingInvoiceSingleCharge map = list.get(0);
            return map.getInvoice_num();
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public String getChargingInvoiceSingleByNum(String invoiceNum,String invoice_class) throws ServiceException {
        String sqlstr = "select invoice_num from charging_invoice_single g where g.invoice_num = '"+invoiceNum.trim()+"' "
                + " and g.invoice_class =  '"+invoice_class+"'  and g.invoice_status='Y' ";
        List<ChargingInvoiceSingleCharge> list = this.jqm.getList(sqlstr, ChargingInvoiceSingleCharge.class);
        if (list.size() != 0) {
            ChargingInvoiceSingleCharge map = list.get(0);
            return map.getInvoice_num();
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public String getNullifyInvoice(String invoiceNum,String invoice_class,String center_num) throws ServiceException{
        String sql = "select n.invoice_num from nullify_invoice n where n.invoice_num = '"+invoiceNum.trim()+"' "
                + " and n.invoice_class = '"+invoice_class+"' and n.center_num = '"+center_num+"'";
        List<ChargingInvoiceSingleCharge> list = this.jqm.getList(sql, ChargingInvoiceSingleCharge.class);
        if (list.size() != 0) {
            ChargingInvoiceSingleCharge map = list.get(0);
            return map.getInvoice_num();
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<ChargingInvoiceSingleDTO> getsingleInvoice(String chargingIds, String examNum) throws ServiceException {
        String sql = "select distinct cv.id,cv.invoice_num,cv.title_info,cv.invoice_type,"
                + " cv.invoice_amount,u.chi_name as invoice_maker,CONVERT(varchar(50),cv.invoice_time,23) "
                + " as invoice_time from charging_detail_single c ,charging_summary_single sc"
                + " ,exam_info e, charging_invoice_single cv left join user_usr u "
                + " on u.id = cv.invoice_maker"
                + " where c.summary_id = sc.id and c.charging_item_id in ("+chargingIds+") "
                + " and sc.is_print_recepit = 'Y' and sc.exam_num = e.exam_num "
                + " and e.exam_num = '"+examNum+"' and sc.invoice_id = cv.id ";
        List<ChargingInvoiceSingleDTO> list = this.jqm.getList(sql, ChargingInvoiceSingleDTO.class);
        return list;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<ChargingSummarySingleDTO> getChargingSingleInvoickList(String examNum,String center_num) throws ServiceException {
//		String sql = "SELECT sum(case when s.charging_status = 'M' then -amount2 else amount2 end) as amount,s.req_num "
////				+" ,u.chi_name as cashier,CONVERT(varchar(100),s.cash_date,23) as cash_date "
//				+" FROM exam_info e,charging_summary_single s "
////				+ "left join user_usr u on u.id = s.cashier "
//				+" where s.exam_id = e.id and e.exam_num = '"+examNum.trim()+"' and s.is_print_recepit = 'N' "
//				+" and s.is_active = 'Y' group by s.req_num";
////				+ ",u.chi_name,CONVERT(varchar(100),s.cash_date,23)";
        String sql="select  sum(case when charging_status = 'M' then -amount2 else amount2 end ) as amount,"
                + "old_req_num as req_num  from   charging_summary_single   "
                + "where is_print_recepit = 'N' and exam_num = '"+examNum.trim()+"' and center_num='"+center_num+"'  group by old_req_num";
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
    public CollectFeesResult saveSingleInviockBu(CollectFeesModel model, UserDTO user,String invoice_class) throws ServiceException {
        CollectFeesResult result = new CollectFeesResult();
        String[] req_nums = model.getReq_nums().split(",");
        for (int i = 0; i < req_nums.length; i++) {
            String sql = "from ChargingSummarySingleCharge c where c.req_num = '"+req_nums[i]+"'";
            List<ChargingSummarySingleCharge> list = this.qm.find(sql);
            for (ChargingSummarySingleCharge chargingSummarySingle : list) {
                if("2".equals(model.getBill_type())){
                    String sql1 = "from ChargingWaySingleCharge c where c.summary_id = '"+chargingSummarySingle.getId()+"'";
                    List<ChargingWaySingleCharge> list1 = this.qm.find(sql1);
                    for (ChargingWaySingleCharge chargingWaySingle : list1) {
                        if("".equals(chargingWaySingle.getCharging_way())||"".equals(chargingWaySingle.getCharging_way())){
                            result.setFlag("error");
                            result.setInfo("存在医保收费，不能打印发票！");
                            return result;
                        }
                    }
                }
                if(chargingSummarySingle.getIs_print_recepit().equals("Y")){
                    result.setUser_id(user.getUserid());
                    result.setFlag("error");
                    result.setInfo("存在已开发票收费记录，请查询最新的收费记录！");
                    return result;
                }
            }
        }
        ChargingInvoiceSingleCharge chargingInvoiceSingle = new ChargingInvoiceSingleCharge();

        chargingInvoiceSingle.setTitle_info(model.getTitle_info());
        chargingInvoiceSingle.setInvoice_num(model.getInvoice_num());
        chargingInvoiceSingle.setBill_type(model.getBill_type());
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
        CardDealCharge cardDeal= new CardDealCharge();
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
            String sql = "from ChargingSummarySingleCharge c where c.req_num = '"+req_nums[i]+"'";
            List<ChargingSummarySingleCharge> list = this.qm.find(sql);
            for (ChargingSummarySingleCharge chargingSummarySingle : list) {
                chargingSummarySingle.setInvoice_id(chargingInvoiceSingle.getId());
                chargingSummarySingle.setIs_print_recepit("Y");
                chargingSummarySingle.setUpdater(user.getUserid());
                chargingSummarySingle.setUpdate_time(DateTimeUtil.parse());
                this.pm.update(chargingSummarySingle);

                String sql1 = "from ChargingWaySingleCharge c where c.summary_id ="+chargingSummarySingle.getId();
                List<ChargingWaySingleCharge> cList = this.qm.find(sql1);
                for(ChargingWaySingleCharge chargingWaySingle : cList){
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
        List<ChargingDetailSingleCharge> detailList = new ArrayList<ChargingDetailSingleCharge>();  //结算明细

        for(ExaminfoChargingItem item : itemList) {

            ChargingItem charitem = (ChargingItem) this.qm.load(ChargingItem.class,item.getCharge_item_id());

            amount += item.getPersonal_pay();
            //组装明细表
            ChargingDetailSingleCharge chargingDetailSingle = new ChargingDetailSingleCharge();
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
        ChargingSummarySingleCharge chargingSummarySingle = new ChargingSummarySingleCharge();

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

        for(ChargingDetailSingleCharge chargingDetailSingle : detailList){//遍历保存明细数据
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

    public List<ChargingSummarySingleDTO> getChargingSummary() throws ServiceException {
        String date = DateTimeUtil.getDate2();
        String sql = "select c.id,c.req_num from charging_summary_single c where c.charging_status = 'R' and c.is_active = 'Y' and c.create_time >='"+date+" 00:00:00.000' and c.create_time <'"+date+" 23:59:59.999'";
        List<ChargingSummarySingleDTO> list = this.jqm.getList(sql, ChargingSummarySingleDTO.class);
        return list;
    }
    public String  updateCharge(String req_num,String bill_type,long userid,String invoice_num ,String trade_no,String voucher_no) throws ServiceException {
        String merge_charge="2";
//		String pos_voucher_no="";
        try {
//			List<PosTransListDTO> posTransList= posTransListService.getPeisTradeCode(req_num);
//			if(posTransList.size()>0&&posTransList!=null){
//				pos_voucher_no=posTransList.get(0).getVoucher_no();
//			}
            String sql = "update charging_invoice_single  set Invoice_status  = 'Y' where charging_summary_num = '"+ req_num+"' ";
            this.jpm.executeSql(sql); //修改发票
//			String sql1 = "update charging_summary_single  set Is_active = 'Y',pos_voucher_no='"+pos_voucher_no+"' where req_num = '"+ req_num+"' ";
            String sql1 = "update charging_summary_single  set Is_active = 'Y' where req_num = '"+ req_num+"' ";
            this.jpm.executeSql(sql1); //修改个人结算  修改凭证号
            String sql2 = "update examinfo_charging_item   set pay_status = 'Y' FROM (select charging_summary_single.exam_num as exam_num,charging_detail_single.charging_item_id as charging_item_id from charging_detail_single   right join  charging_summary_single on charging_detail_single.summary_id  =charging_summary_single.id where   charging_summary_single.req_num ='"+req_num+"') as a"
                    + " where examinfo_charging_item.exam_num=a.exam_num and  examinfo_charging_item.charge_item_id=a.charging_item_id ";
            this.jpm.executeSql(sql2); //修改收费项目项目状态


//			UserInvoiceDTO userInvoice = userInvoiceService.getUserInvoiceByUserId(userid,bill_type);
//			String sql3 = "select * from user_invoice u where u.user_id = "+userInvoice.getUser_id()+" and u.invoice_class ='"+userInvoice.getInvoice_class()+"' and bill_type='"+bill_type+"' ";
//			List<UserInvoiceDTO> userList = this.jqm.getList(sql3, UserInvoiceDTO.class);
//			if (userList.size() > 0&&userList!=null) {
//				String sql4 = "update user_invoice   set invoice_num_used='"+invoice_num+"',bill_type='"+bill_type+"'  where id = "+userList.get(0).getId()+"  ";
//				this.jpm.executeSql(sql4);
//			}
//			userinvoiceused.setInvoice_num_used(invoice_num());
//			userinvoiceused.setBill_type(bill_type());
//			this.userInvoiceService.updateUserInvoice(userinvoiceused);


            //插入pos日结表
            String  sqlPos = "select  cws.*  from  charging_summary_single   css ,  charging_way_single cws ,data_dictionary dd where  dd.id = cws.charging_way and css.id = cws.summary_id and css.is_active = 'Y'  and dd.data_code_children in ('115','101','102') and css.req_num = '"+req_num+"'";
            List<ChargingWaySingleCharge> list = this.jqm.getList(sqlPos, ChargingWaySingleCharge.class);
            if (list.size() > 0&&list!=null) {
                ChargingWaySingleCharge chargingWaySingle = list.get(0);
                String addsql = "insert  into  pos_detail(pay_way , peis_trade_code , amount , pos_type ,daily_status,trans_code,trade_no,voucher_no) "
                        + "values('"+chargingWaySingle.getCharging_way()+"','"+req_num+"','"+chargingWaySingle.getAmount()+"','1','0','00','"+trade_no+"','"+voucher_no+"')";
                System.out.println("插入 pos_detail  "+addsql);
                this.jpm.execSql(addsql);
            }
            merge_charge="0";
        } catch (Exception e) {
            merge_charge="1";
            e.printStackTrace();
        }
        return merge_charge;
    }
//	@Override
//	public UserFeeDTO saveCollectFeesYiBao(String rcpt,String operator, Date visit_date, String invoice_num, String req_num,
//			Double amount,long summaryId) throws ServiceException {
//		UserFeeDTO uf=new UserFeeDTO();
//		String sql3 = "select d.id from data_dictionary d where "
//				+ "d.data_code = 'SFFSLX' and d.isActive = 'Y' and d.remark = 'his收费'";
//		List<DataDictionaryDTO> dicList = this.jqm.getList(sql3, DataDictionaryDTO.class);
//		if (dicList.size() == 0) {
//			uf.setFlags(false);
//			uf.setError("未匹配到His收费方式,请检查His收费方式编码是否对应");
//            return uf;
//		}
//		long sffslx = dicList.get(0).getId();
//
//		String sql1 = "select u.id,u.chi_name,u.work_num from user_usr u where u.work_num = '"+ operator + "'";
//		List<UserInfoDTO> userList = this.jqm.getList(sql1, UserInfoDTO.class);
//		if (userList.size() == 0) {
//			uf.setFlags(false);
//			uf.setError("未匹配到收费员信息!");
//            return uf;
//		}
//
//		/** 保存HIS收费方式表*/
////		HisChargingWaySingle hisChargingWaySingle = new HisChargingWaySingle();
////
////		hisChargingWaySingle.setAccount_num(req_num);
////		hisChargingWaySingle.setCharging_way(dicList.get(0).getId() + "");
////		hisChargingWaySingle.setAmount(amount);
////		hisChargingWaySingle.setCreater(Long.valueOf(userList.get(0).getId()));
////		hisChargingWaySingle.setCreate_time(visit_date);
////		hisChargingWaySingle.setUpdater(Long.valueOf(userList.get(0).getId()));
////		hisChargingWaySingle.setUpdate_time(visit_date);
////		this.pm.save(hisChargingWaySingle);
//
//		String sqlhisWay = "insert into his_charging_way_single (account_num,charging_way,amount,creater,create_time,updater,update_time) values('"+req_num+"','"+dicList.get(0).getId()+"','"+amount
//				+"','"+userList.get(0).getId()+"','"+visit_date+"','"+userList.get(0).getId()+"','"+visit_date+"')";
//
//		this.jpm.executeSql(sqlhisWay);
//
//
//		/**保存发票表**/
////		ChargingInvoiceSingle chargingInvoiceSingle = new ChargingInvoiceSingle();
////
////		chargingInvoiceSingle.setTitle_info("");
////		chargingInvoiceSingle.setInvoice_type("");
////		chargingInvoiceSingle.setInvoice_num(invoice_num);
////		chargingInvoiceSingle.setInvoice_amount(amount);
////		chargingInvoiceSingle.setInvoice_status("Y");
////		chargingInvoiceSingle.setInvoice_maker(Long.valueOf(userList.get(0).getId()));
////		chargingInvoiceSingle.setInvoice_time(visit_date);
////		chargingInvoiceSingle.setCreate_time(visit_date);
////		chargingInvoiceSingle.setCreater(Long.valueOf(userList.get(0).getId()));
////		chargingInvoiceSingle.setUpdate_time(visit_date);
////		chargingInvoiceSingle.setUpdater(Long.valueOf(userList.get(0).getId()));
////		chargingInvoiceSingle.setAccount_num(rcpt);
////		this.pm.save(chargingInvoiceSingle);
//
//		String charingInvoicesql = "insert into charging_invoice_single (title_info,invoice_type,invoice_num,invoice_amount,invoice_status,invoice_maker"
//				                 + ",invoice_time,creater,create_time,updater,update_time,account_num,exam_type) values('','','"+invoice_num+"','"+amount+"','Y','"+userList.get(0).getId()
//				                 + "','"+visit_date+"','"+userList.get(0).getId()+"','"+visit_date+"','"+userList.get(0).getId()+"','"+visit_date+"','"+rcpt+"','G')";
//		this.jpm.executeSql(charingInvoicesql);
//
//		/**保存结算总记录与发票关系表**/
//		String teamAmount = "insert into team_invoice_account (account_num,acc_num,auditor,audit_date) values('"+rcpt+"','"+req_num+"','"+userList.get(0).getId()+"','"+visit_date+"')";
//		this.jpm.executeSql(teamAmount);
//
//
//		String invoiceIdsql = "select c.id from charging_invoice_single c where c.account_num = '"+rcpt+"'";
//
//		List<ChargingInvoiceSingleDTO> invoiceidlist = this.jqm.getList(invoiceIdsql, ChargingInvoiceSingleDTO.class);
//
//		long invoiceid = 0;
//		if(invoiceidlist.size() > 0){
//			invoiceid = invoiceidlist.get(0).getId();
//		}
//
//		/**更新结算总记录信息**/
////		chargingSummarySingle.setCharging_status("Y");
////		chargingSummarySingle.setIs_print_recepit("Y");
////		chargingSummarySingle.setCashier(Long.valueOf(userList.get(0).getId()));
////		chargingSummarySingle.setCash_date(visit_date);
////		chargingSummarySingle.setUpdate_time(visit_date);
////		chargingSummarySingle.setUpdater(Long.valueOf(userList.get(0).getId()));
////
////		chargingSummarySingle.setInvoice_id(chargingInvoiceSingle.getId());
////		this.pm.update(chargingSummarySingle);
//
//		String summaryup = "update charging_summary_single set invoice_id = '"+invoiceid+"',charging_status = 'Y',"
//				+ "is_print_recepit = 'Y',cashier='"+userList.get(0).getId()+"',cash_date='"+visit_date+"',updater='"+userList.get(0).getId()+"',update_time='"+visit_date+"' where req_num = '"+req_num+"'";
//		this.jpm.executeSql(summaryup);
//
//		/**保存收费方式信息**/
////		ChargingWaySingle chargingWaySingle = new ChargingWaySingle();
////		chargingWaySingle.setSummary_id(chargingSummarySingle.getId());
////		chargingWaySingle.setCharging_way(sffslx + "");
////		chargingWaySingle.setAmount(chargingSummarySingle.getAmount2());
////		chargingWaySingle.setCreater(Long.valueOf(userList.get(0).getId()));
////		chargingWaySingle.setCreate_time(visit_date);
////		chargingWaySingle.setUpdater(Long.valueOf(userList.get(0).getId()));
////		chargingWaySingle.setUpdate_time(visit_date);
////		this.pm.save(chargingWaySingle);
//
//		String charingWaySql = "insert into charging_way_single (summary_id,charging_way,amount,creater,create_time,updater,update_time,account_num) values('"+summaryId+"','"+sffslx+"','"+amount
//							 + "','"+userList.get(0).getId()+"','"+visit_date+"','"+userList.get(0).getId()+"','"+visit_date+"','"+rcpt+"')";
//		this.jpm.executeSql(charingWaySql);
//
//
//
//		String sql4 = " select cs.exam_id, cd.charging_item_id" + " from ChargingSummarySingle cs,"
//				+ "ChargingDetailSingle cd where cd.summary_id=cs.id " + " and cs.req_num ='"+req_num+"'";
//		List iteList = new ArrayList();
//		iteList = this.qm.find(sql4);
//		for (int j = 0; j < iteList.size(); j++) {
//			Object[] object = (Object[]) iteList.get(j);
//			long examid = (long) object[0];
//			long ciid = (long) object[1];
//			String sql2 = "from ExaminfoChargingItem c where examinfo_id = '" + examid + "' and charge_item_id=" + ciid
//					+ " and pay_status='N' and isActive='Y'";
//			List<ExaminfoChargingItem> eciList = this.qm.find(sql2);
//			if (eciList.size() > 0) {
//				ExaminfoChargingItem ecinew = new ExaminfoChargingItem();
//				ecinew = eciList.get(0);
//				ecinew.setPay_status("Y");
//				this.pm.update(ecinew);
//			}
//		}
//
//		uf.setUsername(userList.get(0).getChi_Name());
//		uf.setWork_num(userList.get(0).getWork_num());
//		uf.setReq_nums(req_num);
//		uf.setFlags(true);
//		uf.setError("保存成功");
//		return uf;
//	}

    @Override
    public String invalidInvoiceGe(String invoice_nums,UserDTO user,String invoice_class) throws ServiceException {
        String account_num="";
        String bill_type="1";
        String sql = "from ChargingInvoiceSingle c where c.invoice_num in("+invoice_nums+") and c.invoice_class = '"+invoice_class+"'";
        List<ChargingInvoiceSingleCharge> list = this.qm.find(sql);
        List<NullifyInvoiceCharge> zuofeilist = this.qm.find("from NullifyInvoiceCharge where invoice_num in ("+invoice_nums+") and invoice_class = '"+invoice_class+"'");
        if(zuofeilist.size() > 0){
            return "error-存在已作废发票，不能再次作废，请查询最新发票信息!";
        }
        for(ChargingInvoiceSingleCharge chargingInvoiceSingle : list){
            chargingInvoiceSingle.setInvoice_status("N");
            chargingInvoiceSingle.setCancel_time(DateTimeUtil.parse());
            chargingInvoiceSingle.setCanceller(user.getUserid());
            account_num=chargingInvoiceSingle.getAccount_num();
            bill_type=chargingInvoiceSingle.getBill_type();
            this.pm.update(chargingInvoiceSingle);
            //保存发票记录表
            CardDealCharge cardDeal= new CardDealCharge();
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
            NullifyInvoiceCharge nullifyInvoice = new NullifyInvoiceCharge();
            nullifyInvoice.setInvoice_num(chargingInvoiceSingle.getInvoice_num());
            nullifyInvoice.setInvoice_status("N");
            nullifyInvoice.setBill_type(chargingInvoiceSingle.getBill_type());
            nullifyInvoice.setTax_invoices_num(chargingInvoiceSingle.getTax_invoices_num());
            nullifyInvoice.setInvoice_class(chargingInvoiceSingle.getInvoice_class());
            nullifyInvoice.setCreater(user.getUserid());
            nullifyInvoice.setCreate_time(DateTimeUtil.parse());
            nullifyInvoice.setDaily_status("0");
            nullifyInvoice.setAccount_num(GetNumContral.getInstance().getParamNum("account_num",user.getCenter_num()));
            nullifyInvoice.setCenter_num(user.getCenter_num());
            this.pm.save(nullifyInvoice);

            List<ChargingSummarySingleCharge> summarySingleList = this.qm.find("from ChargingSummarySingleCharge where invoice_id = "+chargingInvoiceSingle.getId());
            for(ChargingSummarySingleCharge chargingSummarySingle : summarySingleList){

                chargingSummarySingle.setInvoice_id(0);
                chargingSummarySingle.setIs_print_recepit("N");

                this.pm.update(chargingSummarySingle);
            }
        }

        return "ok-"+account_num+"-"+bill_type+"-作废发票成功!";
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
    private void delLisAndPacsDetail(ExamInfo examInfo,long charingid){
        List<ChargingItem> cList = this.qm.find("from ChargingItem c where c.id = "+charingid);
        ChargingItem chargingItem = null;
        if(cList.size() != 0){
            chargingItem = cList.get(0);
        }else{
            return;
        }
        List<SampleExamDetail> samList = this.qm.find("from SampleExamDetail s where s.exam_num = '"+examInfo.getExam_num() +"' and s.sample_id = "+chargingItem.getSam_demo_id());
        for(SampleExamDetail sampleExamDetail : samList){
            List<ExamResultChargingItem> examList = this.qm.find("from ExamResultChargingItem e where e.charging_id = "+charingid+" and e.bar_code = "+sampleExamDetail.getSample_barcode()+" and e.result_type = 'sample'");
            if(examList.size() != 0 ){
                this.pm.remove(examList.get(0));
            }
            List<ExamResultChargingItem> examrList = this.qm.find("from ExamResultChargingItem e where e.exam_id = "+sampleExamDetail.getId()+" and e.result_type = 'sample' and e.isActive = 'Y' ");
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

        List<ChargingInvoiceSingleCharge> invoiceList = this.qm.find("from ChargingInvoiceSingleCharge where bill_type ='"+model.getBill_type()+"' and invoice_num='"+model.getInvoice_num_used()+"'");
        String  tax_invoices_num="";
        if(invoiceList!=null && invoiceList.size()>0 && invoiceList.get(0).getId()>0){
            //链接第三方 作废发票
            tax_invoices_num=invoiceList.get(0).getTax_invoices_num();
        }

        List<UserInvoiceCharge> li = this.qm.find("from UserInvoice u where u.user_id = "+model.getUser_id()+" and u.invoice_class ='"+model.getInvoice_class()+"' and bill_type='"+model.getBill_type()+"' ");
        li.get(0).setInvoice_num_used(model.getInvoice_num_used());
        UserInvoiceCharge u = li.get(0);
        this.pm.update(u);

        NullifyInvoiceCharge nullifyInvoice = new NullifyInvoiceCharge();
        nullifyInvoice.setInvoice_num(model.getInvoice_num_used());
        nullifyInvoice.setInvoice_status("B");
        nullifyInvoice.setTax_invoices_num(tax_invoices_num);
        nullifyInvoice.setInvoice_class(model.getInvoice_class());
        nullifyInvoice.setCreater(user.getUserid());
        nullifyInvoice.setCreate_time(DateTimeUtil.parse());
        nullifyInvoice.setDaily_status("0");
        nullifyInvoice.setBill_type(model.getBill_type());
        nullifyInvoice.setTax_invoices_num(model.getTax_invoices_num());
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

    public CollectFeesResult saveMergeChargeInfo(CollectFeesModel model, UserDTO user, String invoice_class)
            throws ServiceException {
        CollectFeesResult result = new CollectFeesResult();

        ChargingInvoiceSingleCharge chargingInvoiceSingle = null;
        if("Y".equals(model.getIsPrintRecepit())){
            chargingInvoiceSingle = new ChargingInvoiceSingleCharge();

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
            CardDealCharge cardDeal= new CardDealCharge();
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
            ChargingSummarySingleCharge chargingSummarySingle = new ChargingSummarySingleCharge();
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

            List<ExaminfoChargingItem> listItem = this.qm.find("from ExaminfoChargingItem ec where ec.examinfo_id = "+itemdto.getId()+" and ec.isActive='Y' and ec.pay_status = 'N'");
            //2保存结算明细
            for(ExaminfoChargingItem item : listItem){
                ChargingDetailSingleCharge chargingDetailSingle = new ChargingDetailSingleCharge();
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
                ChargingWaySingleCharge chargingWaySingle = new ChargingWaySingleCharge();

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
                +" and his_charge_status = 'N'  and   pay_mode <> '0'  and eci.his_charge_amount > '0'   and  e.exam_num = '"+exam_num+"'"  ;
        List<ExaminfoChargingItem>  list = this.jqm.getList(sql, ExaminfoChargingItem.class);
        return list;
    }



    @Override
    public String saveDaily(CollectFeesModel model, UserDTO user) throws ServiceException {
        PosDetailCharge PosDetail = new PosDetailCharge();
        PosDetail.setAmount(model.getAmount());
        PosDetail.setDaily_status("0");
        PosDetail.setPay_way(model.getPay_way());
        PosDetail.setPeis_trade_code(model.getPeis_trade_code());
        PosDetail.setPos_type(1);
//		PosDetail.setDaily_time(new Date());
        PosDetail.setTrans_code(model.getTrans_code());
        PosDetail.setTrade_no(model.getTrade_no());
        PosDetail.setVoucher_no(model.getVoucher_no());
//		trans_code
        this.pm.save(PosDetail);
        return "ok";
    }

//	@Override
//	public CardExamSetItemDTO getCardExamSet(String card_num) throws ServiceException {
//		String sql = "select si.set_num,e.set_name,e.sex,e.set_discount,e.set_amount " +
//				"from card_exam_set si " +
//				"left join exam_set e on si.set_num = e.set_num " +
//				"where si.card_num = '"+card_num.trim()+"'";
//		List<CardExamSetItemDTO> list = this.jqm.getList(sql, CardExamSetItemDTO.class);
//		if(list.size() !=0){
//			return list.get(0);
//		}
//		return null;
//	}
//
//	@Override
//	public List<CardExamSetItemDTO> getCardExamCharing(String card_num) throws ServiceException {
//		String sql = "select si.card_num,h.item_code,h.item_name,h.item_category,h.sex,h.amount, " +
//				"h.item_type,si.itemnum,si.item_amount,si.discount,si.amount,si.set_num,e.set_name " +
//				"from card_exam_set_item si " +
//				"left join charging_item h on si.charging_item_code = h.item_code " +
//				"left join exam_set e on si.set_num = e.set_num " +
//				"where si.card_num = '"+card_num.trim()+"'";
//		List<CardExamSetItemDTO> list = this.jqm.getList(sql, CardExamSetItemDTO.class);
//		if(list.size() !=0){
//			return list;
//		}
//		return null;
//	}
//
//	@Override
//	public String updExamForCardnum(String card_num,long exam_id) throws ServiceException {
//		List<ExamInfo> examInfoLi = this.qm.find("from ExamInfo c where c.card_num = '"+card_num.trim()+"'");
//		if(examInfoLi.size()>0) {
//			return "error-该卡已使用，不能进行操作！";
//		}
//		List<ExamInfo> examInfoList = this.qm.find("from ExamInfo c where c.id = '"+exam_id+"'");
//		for (ExamInfo examInfo : examInfoList) {
//			if(examInfo.getCard_num()!=null&&!"".equals(examInfo.getCard_num())) {
//				return "error-体检信息已绑定卡，不能进行操作！";
//			}
//			examInfo.setCard_num(card_num);
//			this.pm.update(examInfo);
//		}
//		return "ok";
//	}
//
//	@Override
//	public List<CardInfo> getCardInfo(String card_num)throws ServiceException{
//		List<CardInfo> cardlist = this.qm.find("from CardInfo c where c.card_num = '"+card_num+"'");
//		return cardlist;
//	}
//

    public CollectFeesResult saveSettlements(CollectFeesModel model,UserDTO user,String invoice_class) throws ServiceException {

        ChargingSummarySingleCharge chargingSummarySingle = new ChargingSummarySingleCharge();
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
            ChargingDetailSingleCharge chargingDetailSingle = new ChargingDetailSingleCharge();

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
    /**
     * 团体划账返回
     * <p>Title: updateaDvance</p>
     * <p>Description: </p>
     * @param req_num
     * @param samount
     * @param userid
     * @param com_num
     * @return
     * @throws ServiceException
     * @see com.hjw.wst.service.CollectFeesService#updateaDvance(java.lang.String, double, long, java.lang.String)
     */
//	@Override
//	public String  updateaDvance(String req_num,double samount,long userid,String com_num) throws ServiceException {
//		String merge_charge="2";
//
//		try {
//			String sql = "select  * from  company_account  where com_num = '"+com_num+"'";
//			List<CompanyAccount>  list = this.jqm.getList(sql, CompanyAccount.class);
//			if (list.size() > 0) {
//				CompanyAccount companyAccount = list.get(0);
//				double amount = companyAccount.getBalance()+samount;
//				String accountUpdate ="update company_account_detail set  balance='"+amount+"' where Jnnumber ='"+req_num+"'";
//				this.jpm.execSql(accountUpdate);
//				String update = " update company_account  set balance = "+amount+", updater = '"+userid+"',update_date = '"+DateTimeUtil.getDateTime()+"' where  com_num = '"+com_num+"'";
//				this.jpm.execSql(update);
//				}
//			merge_charge="0";
//		} catch (Exception e) {
//			merge_charge="1";
//			e.printStackTrace();
//		}
//		return merge_charge;
//	}
    public List<ExamInfoDTO> getExamInfophone(String id_num) throws ServiceException {
        String sql = "select phone From customer_info where id_num ='"+id_num+"' and is_Active='Y' ";
        List<ExamInfoDTO> list  = this.jqm.getList(sql, ExamInfoDTO.class);
        return list;
    }
    /**
     * 1003 查询个人社保编号
     * @Title: saveInvoiceInfo
     * @Description: TODO(这里用一句话描述这个方法的作用)
     * @param: @return
     * @param: @throws WebException
     * @param: @throws SQLException
     * @return: String
     * @throws
     */
    public String findPatNo(String exam_num)throws ServiceException{
        String sql ="select a.medical_insurance_card,a.membership_card from customer_info a "
                + "left join exam_info e on a.id=e.customer_id where e.exam_num='"+exam_num+"';";
        List<CustomerInfoDTO> customerList = this.jqm.getList(sql, CustomerInfoDTO.class);
        String medical_insurance_card="";
        if(customerList!=null){
            medical_insurance_card=customerList.get(0).getMedical_insurance_card();
        }
        return medical_insurance_card;
    }
    /**
     * 1003 查询民族编号
     * @Title: saveInvoiceInfo
     * @Description: TODO(这里用一句话描述这个方法的作用)
     * @param: @return
     * @param: @throws WebException
     * @param: @throws SQLException
     * @return: String
     * @throws
     */
    public long findNation(String data_name)throws ServiceException{
        long id = 0;
        List<DataDictionary> cardlist = this.qm.find("from DataDictionary  where data_code='MZLX' and  data_name= '"+data_name+"'");
        if(cardlist!=null&&cardlist.size()>0){
            id=cardlist.get(0).getId();
        }
        return id;
    }
    /**
     * 1003 查询该工作人员是否签到
     * @Title: saveInvoiceInfo
     * @Description: TODO(这里用一句话描述这个方法的作用)
     * @param: @return
     * @param: @throws WebException
     * @param: @throws SQLException
     * @return: String
     * @throws
     */
    public List<InsuranceOpSignStatusDTO> findSignIn(long operatorId)throws ServiceException{
        String sql = "select op_work_num,prov_sign_status,city_sign_status,prov_cycle_code,city_cycle_code  from insurance_op_signStatus where operator_id ='"+operatorId+"' ";
        List<InsuranceOpSignStatusDTO> list  = this.jqm.getList(sql, InsuranceOpSignStatusDTO.class);
        return list;
    }
    @Override
    public String  updateChargeRefund(String req_num,List<ExamResultChargingItemDTO> listItem,String singleRefund) throws ServiceException {
        String merge_charge="2";
//		String pos_voucher_no="";
        try {
//			List<PosTransListDTO> posTransList= posTransListService.getPeisTradeCode(req_num);
//			if(posTransList.size()>0){
//				pos_voucher_no=posTransList.get(0).getVoucher_no();
//			}
//			String sql = "update charging_invoice_single  set Invoice_status  = 'Y' where charging_summary_num = '"+ req_num+"' ";
//			this.jpm.executeSql(sql); //修改发票
            String sql1 = "";
//			if("1".equals(singleRefund)){
//				sql1 = "update charging_summary_single  set pos_voucher_no='"+pos_voucher_no+"' where req_num = '"+ req_num+"' ";
//			}else{
//				sql1 = "update charging_summary_single  set Is_active = 'N',pos_voucher_no='"+pos_voucher_no+"' where req_num = '"+ req_num+"' ";
            sql1 = "update charging_summary_single  set Is_active = 'N' where req_num = '"+ req_num+"' ";
//			}

            this.jpm.executeSql(sql1); //修改个人结算  修改凭证号·
            for(ExamResultChargingItemDTO item : listItem){
                String sql2 = "update examinfo_charging_item   set isActive='N',pay_status='M' FROM (select charging_summary_single.exam_id as exam_id,charging_detail_single.charging_item_id as charging_item_id from charging_detail_single   right join  charging_summary_single on charging_detail_single.summary_id  =charging_summary_single.id where   charging_summary_single.req_num ='"+ req_num+"') as a"
                        + " where examinfo_charging_item.examinfo_id=a.exam_id and  examinfo_charging_item.charge_item_id=a.charging_item_id and examinfo_charging_item.charge_item_id='"+item.getItem_id()+"' ";
                this.jpm.executeSql(sql2); //修改收费项目项目状态
            }


            //插入pos日结表
//			String  sqlPos = "select  cws.*  from  charging_summary_single   css ,  charging_way_single cws where css.id = cws.summary_id and css.is_active = 'Y' and css.req_num = '"+req_num+"'";
//			List<ChargingWaySingle> list = this.jqm.getList(sqlPos, ChargingWaySingle.class);
//			if (list.size() > 0) {
//				ChargingWaySingle chargingWaySingle = list.get(0);
//				String addsql = "insert  into  pos_detail(pay_way , peis_trade_code , amount , pos_type ,daily_status,trans_code) "
//						    + "values('"+chargingWaySingle.getCharging_way()+"','"+req_num+"','"+chargingWaySingle.getAmount()+"','1','0','00')";
//				 this.jpm.execSql(addsql);
//			}
            merge_charge="0";
        } catch (Exception e) {
            merge_charge="1";
            e.printStackTrace();
        }
        return merge_charge;
    }
    public CollectFeesResult  healthCareAssociation(String peis_req_code,int pay_class,String nsurance_class,List<ExamResultChargingItemDTO> listitem  ) throws ServiceException {
        CollectFeesResult result = new CollectFeesResult();
        String sql = " select dbo.fun_insurance_unlink_items('"+nsurance_class+"', "+pay_class+", '"+peis_req_code+"') as unlink_items";
        List<CustomerInfoDTO> list = this.jqm.getList(sql, CustomerInfoDTO.class);
        if(list.size() !=0&&list!=null){
//            if(list.get(0).getUnlink_items()!=null&&!"".equals(list.get(0).getUnlink_items())){
                for (ExamResultChargingItemDTO chargingItem : listitem) {
                    String sql1 = " select ci.item_name, ci.amount as item_amount , ci.item_code as item_code, dp.item_num as itemnum,dp.item_price as amount from  charging_item ci , dict_city_clinic_item_peis   dp ,dict_Insurance_clinic_item  di "
                            +" where  dp.clinic_item_code = di.item_code and  dp.peis_item_code = ci.item_code and ci.isActive = 'Y'  and ci.item_code = '"+chargingItem.getItem_code()+"'" ;
                    List<ExaminfoChargingItemDTO> lclist = this.jqm.getList(sql1,ExaminfoChargingItemDTO.class);
                    if(lclist.size() < 1){
                        result.setFlag("error");
                        result.setInfo("所选择的项目 [ "+chargingItem.getItem_name()+" ]，尚未对照医保，请联系管理员。。");
                        return result;
                    }
                    for (int k = 0; k < lclist.size(); k++) {
                        String itemcode = lclist.get(k).getItem_code();
                        double amount = 0;
                        for (int i = 0; i < lclist.size(); i++) {
                            if(!"".equals(lclist.get(i).getItem_code()) && lclist.get(i).getItem_code().equals(itemcode)){
                                amount +=  (lclist.get(i).getAmount() * lclist.get(i).getItemnum());
                            }
                        }
                        if(amount != lclist.get(k).getItem_amount()){
                            result.setFlag("error");
                            result.setInfo("项目  "+lclist.get(k).getItem_name()+" 金额为："+lclist.get(k).getItem_amount()+" ,医保项目金额为："+amount +"总金额不对操作不能继续。");
                            return result;
                        }
                    }
                    result.setFlag("ok");
                    return result;
                }
//            }
//			return list.get(0).getUnlink_items();
        }else{
            result.setFlag("error");
            result.setInfo("尚未对照医保，请联系管理员。。");
            return result;
        }
        return null;
    }
    public List<JobDTO> getPrescription() throws ServiceException {
        String sql = "select  * from  user_usr   where is_active='Y' and is_prescription='Y' ";
        Connection connection = null;
        Statement statement = null;
        int count = 0;
        List<JobDTO> list = new ArrayList<JobDTO>();
        try {
            // 读取记录数
            connection = this.jqm.getConnection();
            statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                JobDTO jd =new JobDTO();
                jd.setId(rs.getString("prescription_num"));
                jd.setName(rs.getString("chi_name"));
                list.add(jd);
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
        return list;
    }
    public String  updateCredentials(String req_num,UserDTO user,String patNo, String inter_class ) throws ServiceException {
        String merge_charge="2";
        String city_cycle_code="";
        String prov_cycle_code="";
        try {
            //获取省医保市医保pos凭证号
//			try {
//				List<InsuranceOpSignStatusDTO> list=findSignIn(user.getUserid());
//				if(list!=null&&list.size()>0){
//					city_cycle_code=list.get(0).getCity_cycle_code();
//					prov_cycle_code=list.get(0).getProv_cycle_code();
//				}
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
            String sql2 = "update  charging_summary_single set city_cycle_code='"+city_cycle_code+"', prov_cycle_code='"+prov_cycle_code+"' "
                    + ",health_type='"+inter_class+"',medical_insurance_card='"+patNo+"' where  req_num='"+req_num+"' ";
            this.jpm.executeSql(sql2); //修改收费项目项目状态
            merge_charge="0";
        } catch (Exception e) {
            merge_charge="1";
            e.printStackTrace();
        }
        return merge_charge;
    }
    public String  deleteCharge(String invoice_num,String req_nums){
        String msg="ok";
        try {
            String sql = "delete from charging_invoice_single  where  charging_summary_num= '"+ req_nums+"'";// invoice_num='"+invoice_num+"' ";
            this.jpm.executeSql(sql); //
            String sql1 = "delete from card_deal where card_num='"+invoice_num+"'";
            this.jpm.executeSql(sql1); //
            String sql2 = " delete from  charging_way_single where summary_id=(select   id from  charging_summary_single where req_num='"+ req_nums+"')";
            this.jpm.executeSql(sql2); //
            String sql3 = " delete from  charging_detail_single where summary_id=(select   id from  charging_summary_single where req_num='"+ req_nums+"')";
            this.jpm.executeSql(sql3); //
            String sql4 = " delete from  charging_summary_single where req_num='"+req_nums+"'";
            this.jpm.executeSql(sql4); //
            String sql5 = " delete from   Insurance_fee_detail where  req_num='"+req_nums+"'";
            this.jpm.executeSql(sql5); //
        } catch (Exception e) {
            msg="error";
            e.printStackTrace();
        }
        return msg;
    }

    public List<ChargingSummarySingleDTO> findexamSummary(String exam_num)throws ServiceException{
        String sql ="select  c.req_num,s.invoice_num,c.* from  charging_summary_single c left join charging_invoice_single s on c.req_num=s.charging_summary_num "
                + " where  c.exam_num='"+exam_num+"' and c.is_active='N' and (c.health_type ='02' or c.health_type ='03')";
        List<ChargingSummarySingleDTO> customerList = this.jqm.getList(sql, ChargingSummarySingleDTO.class);
        return customerList;
    }
    public List<InsuranceFeeDetailDTO> runningWater(String item_code,String inter_class)throws ServiceException{

        String sql=	"select distinct ci.item_code as charging_item_code,cimp.medical_price_id,"
                + "cimp.item_num as medical_price_num,mp.price as medical_old_price, "
                + "mpmi.medical_item_code , mpmi.item_num as medical_item_num "
                + "from charging_item ci,  charging_item_medical_price cimp, medical_price_list mp,"
                + "medical_price_medical_item mpmi "
                + " where ci.item_code=cimp.charge_item_code  and cimp.medical_price_id=mp.id "
                + "and mp.id=mpmi.medical_price_id and ci.item_code='"+item_code+"' "
                + "and mpmi.medical_type='"+inter_class+"'";
        List<InsuranceFeeDetailDTO> customerList = this.jqm.getList(sql, InsuranceFeeDetailDTO.class);
        return customerList;
    }
    public List<ChargingSummarySingleDTO> selectPatno(String req_num) throws ServiceException {
        String sql = "select  medical_insurance_card,health_type from charging_summary_single  where req_num = '"+req_num+"'";
        List<ChargingSummarySingleDTO> customerList = this.jqm.getList(sql, ChargingSummarySingleDTO.class);
        return customerList;
    }
    public List<InsureAccountDTO> queryWater(String req_num) throws ServiceException {
        String sql = "select  * from insure_account where his_req_num='"+req_num+"'";
        List<InsureAccountDTO> list = this.jqm.getList(sql, InsureAccountDTO.class);
        return list;
    }

    /**
     * 更想发票表
     * @Title: updateCIS
     * @Description:
     * @param: @param req_num
     * @param: @param user
     * @param: @param patNo
     * @param: @param inter_class
     * @param: @return
     * @param: @throws ServiceException
     * @return: String
     * @throws
     */
    public String  updateCIS(long invoice_id,String invoice_num,String tax_invoices_num) throws ServiceException {
        String sql2 = "update  charging_invoice_single set invoice_num='"+invoice_num+"', tax_invoices_num='"+tax_invoices_num+"' where  id='"+invoice_id+"' ";
        this.jpm.executeSql(sql2); //更新发票表发票号
        return "";
    }

    /**
     *
     * @Title: getcardSaleSummary
     * @Description:
     * @param: @param account_num
     * @param: @return
     * @param: @throws ServiceException
     * @return: ChargingInvoiceSingle
     * @throws
     */
    public ChargingInvoiceSingleCharge getCISForAccountnum(String account_num)throws ServiceException {
        List<ChargingInvoiceSingleCharge> cardlist = this.qm.find("from ChargingInvoiceSingleCharge c where c.account_num = '"+account_num+"' ");
        if(cardlist.size() > 0){
            return cardlist.get(0);
        }else{
            return null;
        }
    }

    public ChargingSummarySingleCharge getChargingSummarySingle(String account_num)throws ServiceException{
        List<ChargingSummarySingleCharge> cardlist = this.qm.find("from ChargingSummarySingleCharge c where c.req_num = '"+account_num+"'");
        if(cardlist.size() > 0){
            return cardlist.get(0);
        }else{
            return null;
        }
    }
    public String saveInsurance(CollectFeesModel model, UserDTO user) throws ServiceException {
        List<IdentityAuthenticationCharge> insuranceList = this.qm.find("from IdentityAuthenticationCharge where pat_no = '"+model.getPat_no()+"'");
        if(insuranceList.size() != 0){
            IdentityAuthenticationCharge identityAuthentication = insuranceList.get(0);
            identityAuthentication.setFlag(model.getFalg());
            identityAuthentication.setCause(model.getCause());
            identityAuthentication.setPersonal_account_balance(model.getPersonal_account_balance());
            identityAuthentication.setCreate_time(DateTimeUtil.parse());
            identityAuthentication.setCreater(user.getUserid());
            this.pm.update(identityAuthentication);
        }else{
            IdentityAuthenticationCharge identityAuthentication =new IdentityAuthenticationCharge();
            identityAuthentication.setFlag(model.getFalg());
            identityAuthentication.setCause(model.getCause());
            identityAuthentication.setGinseng_administrative(model.getGinseng_administrative());
            identityAuthentication.setGinseng_administrative_name(model.getGinseng_administrative_name());
            identityAuthentication.setNam_entity(model.getNam_entity());
            identityAuthentication.setPat_no(model.getPat_no());
            identityAuthentication.setDocument_type(model.getDocument_type());
            identityAuthentication.setDocument_name(model.getDocument_name());
            identityAuthentication.setDocument_number(model.getDocument_number());
            identityAuthentication.setPersonal_code(model.getPersonal_code());;
            identityAuthentication.setName(model.getName());
            identityAuthentication.setSex(model.getSex());
            identityAuthentication.setSex_name(model.getSex_name());
            identityAuthentication.setDate_birth(model.getDate_birth());
            identityAuthentication.setMedical_identification(model.getMedical_identification());
            identityAuthentication.setMedical_identification_name(model.getMedical_identification_name());
            identityAuthentication.setPersonal_account_balance(model.getPersonal_account_balance());
            identityAuthentication.setCenter_num(user.getCenter_num());
            this.pm.save(identityAuthentication);
        }

        return "ok-医保卡信息保存成功！";
    }
    public String saveCompanyInformation(CollectFeesModel model, UserDTO user) throws ServiceException {
        InvoiceInformationCharge invoiceInformation=new InvoiceInformationCharge();
        String sql = "select  * from  invoice_information where id = '"+model.getId()+"'";
        List<InvoiceInformationDTO> list = this.jqm.getList(sql, InvoiceInformationDTO.class);
        if(list==null||list.size()==0){
            invoiceInformation.setInfoClientAddressPhone(model.getInfoClientAddressPhone());
            invoiceInformation.setInfoClientBankAccount(model.getInfoClientBankAccount());
            invoiceInformation.setInfoClientName(model.getInfoClientName());
            invoiceInformation.setInfoClientTaxCode(model.getInfoClientTaxCode());
            invoiceInformation.setInfoKind("2");
            invoiceInformation.setCreate_time(DateTimeUtil.parse());
            invoiceInformation.setCreater(user.getUserid());

            this.pm.save(invoiceInformation);
        }else{
            invoiceInformation.setId(list.get(0).getId());
            invoiceInformation.setInfoClientAddressPhone(model.getInfoClientAddressPhone());
            invoiceInformation.setInfoClientBankAccount(model.getInfoClientBankAccount());
            invoiceInformation.setInfoClientName(model.getInfoClientName());
            invoiceInformation.setInfoClientTaxCode(model.getInfoClientTaxCode());
            invoiceInformation.setInfoKind("2");
            invoiceInformation.setCreate_time(DateTimeUtil.parse());
            invoiceInformation.setCreater(user.getUserid());
            this.pm.update(invoiceInformation);
        }
        return "ok-发票信息保存成功！";
    }
    public List<InvoiceInformationDTO> getInformation(String id) throws ServiceException {
        String sql = "select  * from  invoice_information";
        if(id!=null&&!"".equals(id)&&!"0".equals(id)){
            sql=sql + "  where id = '"+id+"'";
        }
        List<InvoiceInformationDTO> list = this.jqm.getList(sql, InvoiceInformationDTO.class);
        return list;
    }

    public String verifyMedicalItem(String item_codes, String medical_type) throws ServiceException {
        String[] itemCode = item_codes.split(",");
        for (int i = 0; i < itemCode.length; i++) {
            String sql = "select ci.item_name,COUNT(c.id) as itemnum,sum(c.item_num * m.price) as item_amount,ci.amount "
                    + "from charging_item ci left join charging_item_medical_price c on ci.item_code = c.charge_item_code "
                    + "left join medical_price_list m on c.medical_price_id = m.id "
                    + "where ci.item_code = '"+itemCode[i]+"' group by ci.item_name,ci.amount";
            List<ChargingItemDTO> itemlist = this.jqm.getList(sql, ChargingItemDTO.class);
            if(itemlist.size() == 0){
                return "error-收费项目不存在，不能添加！";
            }
            ChargingItemDTO item = itemlist.get(0);
            if(item.getItemnum() <= 0){
                return "error-收费项目"+item.getItem_name()+"【"+itemCode[i]+"】没有绑定医保价表，不能添加！";
            }
            if((double)item.getAmount() != (double)item.getItem_amount()){
                return "error-收费项目"+item.getItem_name()+"【"+itemCode[i]+"】金额与绑定医保价表金额不一致，不能添加！";
            }

            if("02".equals(medical_type)){//市医保
                String sql1 = "select m.item_name,COUNT(mm.id) as itemnum,sum(mm.item_num * di.price) as item_amount,m.price amount from charging_item ci "
                        + "left join charging_item_medical_price c on ci.item_code = c.charge_item_code "
                        + "left join medical_price_list m on c.medical_price_id = m.id "
                        + "left join medical_price_medical_item mm on mm.medical_price_id = m.id and mm.medical_type = '02' "
                        + "left join dict_Insurance_clinic_item di on di.item_code = mm.medical_item_code "
                        + "where ci.item_code = '"+itemCode[i]+"' group by m.item_name,m.price";
                List<ChargingItemDTO> slist = this.jqm.getList(sql1, ChargingItemDTO.class);
                ChargingItemDTO sitem = slist.get(0);
                if(item.getItemnum() <= 0){
                    return "error-医保价表【"+sitem.getItem_name()+"】没有绑定市医保项目，不能添加！";
                }
                if((double)item.getAmount() != (double)item.getItem_amount()){
                    return "error-医保价表【"+sitem.getItem_name()+"】金额与绑定市医保项目金额不一致，不能添加！";
                }
            }else if("03".equals(medical_type)){//省医保
                String sql1 = "select distinct m.item_name,COUNT(mm.id) as num,sum(mm.item_num * dp.price) as amount1,m.price amount from charging_item ci "
                        + "left join charging_item_medical_price c on ci.item_code = c.charge_item_code "
                        + "left join medical_price_list m on c.medical_price_id = m.id "
                        + "left join medical_price_medical_item mm on mm.medical_price_id = m.id and mm.medical_type = '03' "
                        + "left join dict_prov_Insurance_clinic_item dp on dp.item_code = mm.medical_item_code "
                        + "where ci.item_code = '"+itemCode[i]+"'  group by m.item_name,m.price";
                List<ChargingItemDTO> slist = this.jqm.getList(sql1, ChargingItemDTO.class);
                ChargingItemDTO sitem = slist.get(0);
                if(item.getItemnum() <= 0){
                    return "error-医保价表【"+sitem.getItem_name()+"】没有绑定省医保项目，不能添加！";
                }
                if((double)item.getAmount() != (double)item.getItem_amount()){
                    return "error-医保价表【"+sitem.getItem_name()+"】金额与绑定省医保项目金额不一致，不能添加！";
                }
            }
        }
        return "ok-可以添加!";
    }
    public List<String> uploadDetail(String req_num,String name,String cardNum)throws ServiceException{
        String sql=	"select dici.item_code,dici.spec,dici.dosage_form,dici.price,dici.item_unit,dici.item_name,sum(mpmi.item_num)as itemNum, "
                + "(select  c_value from  health_care_coding where specifies=dici.dosage_form) as dosage_form,"
                + "(select  c_value from  health_care_coding where specifies=dici.invoice_item_name) as invoice_item_name"
                + " from charging_summary_single css,charging_detail_single cds,charging_item ci,"
                + "charging_item_medical_price cimp,medical_price_list mpl,medical_price_medical_item mpmi,"
                + "dict_Insurance_clinic_item dici "
                + "where css.id=cds.summary_id and cds.charging_item_id=ci.id and ci.item_code=cimp.charge_item_code"
                + " and cimp.medical_price_id=mpl.id and mpl.id=mpmi.medical_price_id "
                + "and mpmi.medical_item_code=dici.item_code and css.req_num='"+req_num+"'"
                + "group by dici.item_code,dici.spec,dosage_form,dici.price,dici.item_unit,dici.item_name,invoice_item_name";
        List<DictInsuranceClinicItemDTO> customerList = this.jqm.getList(sql, DictInsuranceClinicItemDTO.class);
        List<String> list=new ArrayList<String>();
        for (int i = 0; i < customerList.size(); i++) {
            JSONObject s=new JSONObject();
            SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");//设置日期格式
            SimpleDateFormat df1 = new SimpleDateFormat("ddHHmmss");//设置日期格式
            s.put("bke162", df.format(new Date()));
            s.put("bkea97", df.format(new Date())+req_num);
            s.put("bke045", customerList.get(i).getItem_code());
            s.put("bke046", customerList.get(i).getItem_name());
            s.put("aka063", customerList.get(i).getInvoice_item_name()!=null&&!"".equals(customerList.get(i).getInvoice_item_name())&&!"NULL".equals(customerList.get(i).getInvoice_item_name())? customerList.get(i).getInvoice_item_name():"08");//n
            s.put("aka074", customerList.get(i).getSpec());
            s.put("aka070", customerList.get(i).getDosage_form()!=null&&!"".equals(customerList.get(i).getDosage_form())&&!"NULL".equals(customerList.get(i).getDosage_form())?customerList.get(i).getDosage_form():"015");
            s.put("aka067", customerList.get(i).getItem_unit());
            s.put("akc225", customerList.get(i).getPrice()+"");
            s.put("akc226", customerList.get(i).getItemNum()+"");
            Double am=customerList.get(i).getPrice()*customerList.get(i).getItemNum();
            s.put("akc227", am+"");
            s.put("aka071", "");//n
            s.put("aka072", "");//n
            s.put("aka073", "");//n
            s.put("akc221", df1.format(new Date()));
            s.put("akc229", "");//n
            s.put("akf001", "");//n
            s.put("akf002", "");//n
            s.put("bkf237", "001");
            s.put("bkf238", "体检科");
            s.put("bkc006", name);
            s.put("bkc007", cardNum);
            s.put("bkea96", "Y");
            s.put("bka001", "Y");
            s.put("bkeaa0", "N");
            s.put("bkeaa1", "漳州市第三医院");
            s.put("bkeaa2", "");
            s.put("bkeaa3", df.format(new Date()));
            list.add(s.toString());
        }
        return list;
    }
    public String  saveInsureAccount(List<InsureAccountDmDTO> listitem,CollectFeesModel model, UserDTO user) throws ServiceException {
        String mes="ok";
        try {
            if(listitem.size()==0){
                mes="error";
            }else{
                InsureAccountCharge insureAccountCharge=new InsureAccountCharge();
                insureAccountCharge.setFlag(model.getFalg());
                insureAccountCharge.setCause(model.getCause());
                insureAccountCharge.setMedical_req_num(listitem.get(0).getAkc190());
                insureAccountCharge.setMedical_charge_req_num(listitem.get(0).getAae072());
                insureAccountCharge.setHis_req_num(listitem.get(0).getBke298());
                insureAccountCharge.setMedical_treatment_mode(listitem.get(0).getAka078());
                insureAccountCharge.setMedical_category(listitem.get(0).getAka130());
                insureAccountCharge.setPat_no(listitem.get(0).getAaz500());
                insureAccountCharge.setManagement_code(listitem.get(0).getAac999());
                insureAccountCharge.setName(listitem.get(0).getAac003());
                insureAccountCharge.setSex(listitem.get(0).getAac004());
                insureAccountCharge.setSex_name(listitem.get(0).getAac004_mc());
                insureAccountCharge.setDate_birth(listitem.get(0).getAac006());
                insureAccountCharge.setDiseases_coding(listitem.get(0).getAaz149());
                insureAccountCharge.setTreatment_personnel(listitem.get(0).getBkc001());
                insureAccountCharge.setTreatment_personnel_name(listitem.get(0).getBkc001_mc());
                insureAccountCharge.setTreatment_medicall(listitem.get(0).getBkc026());
                insureAccountCharge.setTreatment_medical_name(listitem.get(0).getBkc026_mc());
                insureAccountCharge.setSite_medical_type(listitem.get(0).getBke174());
                insureAccountCharge.setSite_medical_type_name(listitem.get(0).getBke174_mc());
                insureAccountCharge.setAdministrative_divisions(listitem.get(0).getAab301());
                insureAccountCharge.setOverall_payment_standard(listitem.get(0).getAka151());
                insureAccountCharge.setAmount_medical(listitem.get(0).getAkc227());
                insureAccountCharge.setPersonal_cash(listitem.get(0).getBkc040());
                insureAccountCharge.setIndividual_account(listitem.get(0).getBkc041());
                insureAccountCharge.setFund(listitem.get(0).getBkc102());
                insureAccountCharge.setWhole_fund(listitem.get(0).getBkc045());
                insureAccountCharge.setCommercial_fund(listitem.get(0).getBkc052());
                insureAccountCharge.setCivil_servants(listitem.get(0).getBkc059());
                insureAccountCharge.setPoverty_alleviation(listitem.get(0).getBkc062());
                insureAccountCharge.setBailout_fund(listitem.get(0).getBkc060());
                insureAccountCharge.setOther_fund(listitem.get(0).getAke173());
                insureAccountCharge.setEnterprise_complement(listitem.get(0).getAke026());
                insureAccountCharge.setFamily_account(listitem.get(0).getBkc075());
                insureAccountCharge.setDeductionser(listitem.get(0).getBke921());
                insureAccountCharge.setDeductions_amount(listitem.get(0).getBke922());
                insureAccountCharge.setGeneral_amount_compensation(listitem.get(0).getBkc166());
                insureAccountCharge.setGeneral_personal_amount(listitem.get(0).getBkc167());
                insureAccountCharge.setIndividual_pocket(listitem.get(0).getBkc011());
                insureAccountCharge.setPersonal_account_balance(listitem.get(0).getAkc087());
                insureAccountCharge.setFamily_balance(listitem.get(0).getBkc591());
                insureAccountCharge.setNumber_hospita(listitem.get(0).getAka150());
                insureAccountCharge.setActual_visit_date(listitem.get(0).getBkc014());
                insureAccountCharge.setActual_time_visit(listitem.get(0).getBkc171());
                insureAccountCharge.setSettlement_date(listitem.get(0).getAke007());
                insureAccountCharge.setClearing_time(listitem.get(0).getBae029());
                insureAccountCharge.setCollector(listitem.get(0).getAae011());
                insureAccountCharge.setSurgical_category(listitem.get(0).getAmc029());
                insureAccountCharge.setFertility_category(listitem.get(0).getAmc026());
                insureAccountCharge.setFetus_number(listitem.get(0).getAmc028());
                insureAccountCharge.setFamily_planning(listitem.get(0).getAmc020());
                insureAccountCharge.setDays_pregnancy(listitem.get(0).getBmc041());
                insureAccountCharge.setDisease_settlement(listitem.get(0).getBka188());
                insureAccountCharge.setMedical_type(model.getMedical_type());
                insureAccountCharge.setCenter_num(user.getCenter_num());
                insureAccountCharge.setSterilisation_req_num(listitem.get(0).getBae010());
                insureAccountCharge.setCreate_time(DateTimeUtil.parse());
                insureAccountCharge.setCreater(user.getUserid());
                this.pm.save(insureAccountCharge);
            }
        } catch (Exception e) {
            mes="error";
            e.printStackTrace();
        }
        return mes;
    }

}
