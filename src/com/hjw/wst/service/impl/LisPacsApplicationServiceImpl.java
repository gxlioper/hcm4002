package com.hjw.wst.service.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.fr.third.org.hsqldb.lib.StringUtil;
import com.hjw.util.DateTimeUtil;
import com.hjw.util.TranLogTxt;
import com.hjw.webService.client.DELFEESendMessage;
import com.hjw.webService.client.LISDELSendMessage;
import com.hjw.webService.client.LISSendMessage;
import com.hjw.webService.client.PACSDELSendMessage;
import com.hjw.webService.client.PACSSendMessage;
import com.hjw.webService.client.UNFEESendMessage;
import com.hjw.webService.client.body.DelFeeMessage;
import com.hjw.webService.client.body.FeeReqBody;
import com.hjw.webService.client.body.LisMessageBody;
import com.hjw.webService.client.body.PacsMessageBody;
import com.hjw.webService.client.body.ResultLisBody;
import com.hjw.webService.client.body.ResultPacsBody;
import com.hjw.webService.client.body.UnFeeMessage;
import com.hjw.webService.client.Bean.ApplyNOBean;
import com.hjw.webService.client.Bean.LisComponent;
import com.hjw.webService.client.Bean.LisComponents;
import com.hjw.webService.client.Bean.PacsComponent;
import com.hjw.webService.client.Bean.PacsComponents;
import com.hjw.webService.client.Bean.ReqNo;
import com.hjw.webService.client.Bean.ReqUnNo;
import com.hjw.wst.DTO.CenterConfigurationDTO;
import com.hjw.wst.DTO.ChargingDetailSingleDTO;
import com.hjw.wst.DTO.ExamInfoDTO;
import com.hjw.wst.DTO.ExaminfoChargingItemDTO;
import com.hjw.wst.DTO.JobDTO;
import com.hjw.wst.DTO.LisSendDTO;
import com.hjw.wst.DTO.PacsSendDTO;
import com.hjw.wst.DTO.UserDTO;
import com.hjw.wst.DTO.WebserviceConfigurationDTO;
import com.hjw.wst.config.GetNumContral;
import com.hjw.wst.domain.ChargingInvoiceSingle;
import com.hjw.wst.domain.ChargingSummarySingle;
import com.hjw.wst.domain.CustomerInfo;
import com.hjw.wst.domain.ExamInfo;
import com.hjw.wst.domain.ExaminfoChargingItem;
import com.hjw.wst.domain.WebUserInfo;
import com.hjw.wst.service.BatchService;
import com.hjw.wst.service.CompanyService;
import com.hjw.wst.service.CustomerInfoService;
import com.hjw.wst.service.LisPacsApplicationService;
import com.hjw.wst.service.WebserviceConfigurationService;
import com.synjones.framework.exception.ServiceException;
import com.synjones.framework.persistence.JdbcPersistenceManager;
import com.synjones.framework.persistence.JdbcQueryManager;
import com.synjones.framework.persistence.QueryManager;

public class LisPacsApplicationServiceImpl implements LisPacsApplicationService {

	private QueryManager qm;
	private JdbcQueryManager jqm;
	private JdbcPersistenceManager jpm; 
	private WebserviceConfigurationService webserviceConfigurationService;
	private CompanyService companyService;
	private CustomerInfoService customerInfoService;
	private BatchService batchService;
	
	public void setCustomerInfoService(CustomerInfoService customerInfoService) {
		this.customerInfoService = customerInfoService;
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

	public void setCompanyService(CompanyService companyService) {
		this.companyService = companyService;
	}

	public void setBatchService(BatchService batchService) {
		this.batchService = batchService;
	}

	/**
	 * 
	     * @Title: checkVip   
	     * @Description: 判断是否为vip   
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: int      
	     * @throws
	 */
	@SuppressWarnings("unchecked")
	private int checkVip(String exam_num, String center_num)throws ServiceException{
		String examsql = "select customer_type_id from exam_info a where exam_num='" + exam_num + "'";
		List<ExamInfoDTO> examList = new ArrayList<ExamInfoDTO>();
		examList = this.jqm.getList(examsql, ExamInfoDTO.class);
		boolean md = false;
		if (examList.size() > 0) {

			String viptype = "";
			CenterConfigurationDTO cc = new CenterConfigurationDTO();
			cc = this.customerInfoService.getCenterconfigByKey("VIP_CUST_TYPE", center_num);

			if ((cc != null) && (!StringUtil.isEmpty(cc.getConfig_value()))) {
				viptype = cc.getConfig_value();
			}

			ExamInfoDTO exam = examList.get(0);
			String custypeid=exam.getCustomer_type_id()+"";					
			if (viptype.equals(custypeid)) {
				md = true;
			}
		}

		if (md) {
			return 1;
		} else {
			String sql = "select a.examinfo_id,SUM(a.team_pay+a.personal_pay) as amount "
					+ "from  examinfo_charging_item a,exam_info b " + "where b.exam_num=a.exam_num and b.exam_num='"
					+ exam_num + "' " + " and a.isActive='Y' and a.pay_status<>'M' and a.exam_status='N' "
					+ "and a.is_new_added=0 and a.is_application='N' and a.center_num = '"+center_num+"' group by a.examinfo_id";
			List<ExaminfoChargingItemDTO> itemList = null;
			itemList = this.jqm.getList(sql, ExaminfoChargingItemDTO.class);
			if (itemList.size() == 0) {
				return 0;
			} else {
				double amt = 1800;
				try {
					amt = Double.valueOf(
							this.customerInfoService.getCenterconfigByKey("IS_VIP_AMOUNT", center_num).getConfig_value().trim());
				} catch (Exception e) {

				}
				if (itemList.get(0).getAmount() >= amt) {
					return 1;
				} else {
					return 0;
				}
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override 
	public String pacsSend(String exam_num, String charingIds, UserDTO user, boolean isBufa, String IS_HIS_PACS_CHECK) 
			throws ServiceException {
	    WebserviceConfigurationDTO wcf = new WebserviceConfigurationDTO();
	    wcf = this.webserviceConfigurationService.getWebServiceConfig("PACS_SEND", user.getCenter_num());
	    String web_url = wcf.getConfig_url().trim();
	    String web_meth = wcf.getConfig_method().trim();
	    
	    List<ExamInfo> examList = this.qm.find("from ExamInfo e where e.exam_num = '" + exam_num + "'");
	    if (examList.size() == 0) {
	      return "error-该体检信息不存在!";
	    }
	    ExamInfo examInfo = (ExamInfo)examList.get(0);
	    

	    List<CustomerInfo> cusList = this.qm.find("from CustomerInfo c where c.id = " + examInfo.getCustomer_id());
	    CustomerInfo customerInfo = (CustomerInfo)cusList.get(0);
	    StringBuffer sb = new StringBuffer();
	    
	    PacsMessageBody pb = new PacsMessageBody();
	    pb.setCenter_num(user.getCenter_num());
	    pb.setId_extension("TI001");
	    pb.setMessageid("");
	    pb.setPart_name("东北国际医院");
	    pb.setCreationTime_value(DateTimeUtil.getDateTimes());
	    pb.getCustom().setArch_num(customerInfo.getArch_num());
	    pb.getCustom().setPersonid(examInfo.getPatient_id());
	    pb.getCustom().setPersonno(examInfo.getVisit_no());
	    pb.getCustom().setExam_num(examInfo.getExam_num());
	    pb.getCustom().setPersonidnum(customerInfo.getId_num());
	    pb.getCustom().setPersioncode("");
	    pb.getCustom().setMc_no(examInfo.getMc_no());
	    pb.getCustom().setName(customerInfo.getUser_name());
	    pb.getCustom().setSexcode(customerInfo.getSex());
	    pb.getCustom().setBirthtime(DateTimeUtil.shortFmt4(customerInfo.getBirthday()));
	    pb.getCustom().setComname(examInfo.getCompany());
	    pb.getCustom().setContact_name("");
	    pb.getCustom().setContact_tel("");
	    pb.getCustom().setEthnicGroupCode("");
	    pb.getCustom().setMeritalcode(examInfo.getIs_marriage());
	    pb.getCustom().setOld(new Long(examInfo.getAge()).intValue());
	    pb.getCustom().setTel(examInfo.getPhone());
	    pb.getCustom().setAddress(examInfo.getAddress());
	    pb.getCustom().setVipflag(checkVip(exam_num, user.getCenter_num()) + "");
	    if ("男".equals(customerInfo.getSex()))
	    {
	      pb.getCustom().setSexcode("1");
	      pb.getCustom().setSexname("男");
	    }
	    else if ("女".equals(customerInfo.getSex()))
	    {
	      pb.getCustom().setSexcode("2");
	      pb.getCustom().setSexname("女");
	    }
	    else
	    {
	      pb.getCustom().setSexcode("3");
	      pb.getCustom().setSexname("未知");
	    }
	    List<JobDTO> dataList = this.companyService.getDatadis("SQKS","1");
	    if (dataList.size() == 0) {
	      return "error-请在数据字典中维护申请科室(data_code = SQKS)";
	    }
	    pb.getDoctor().setDept_code(((JobDTO)dataList.get(0)).getRemark());
	    pb.getDoctor().setDept_name(((JobDTO)dataList.get(0)).getName());
	    pb.getDoctor().setDoctorCode(user.getWork_num());
	    pb.getDoctor().setDoctorName(user.getName());
	    pb.getDoctor().setTime(DateTimeUtil.getDateTimes());
	    
	    sb.setLength(0);
	    sb.append("select p.pacs_req_code,c.view_num,c.item_code,ec.item_amount,hd.dept_code,hd.dept_name,c.his_num,c.item_name,"
	    		+ "dd.dep_num,ec.id,ec.pay_status,ec.amount,c.hiscodeClass,c.id as itemId  from examinfo_charging_item ec,"
	    		+ "pacs_summary p,pacs_detail d,department_dep dd,charging_item c left join his_dict_dept hd "
	    		+ "on c.perform_dept = hd.dept_code where ec.charging_item_code = c.item_code and ec.exam_num = p.examinfo_num and p.id = d.summary_id and "
	    		+ "d.chargingItem_num = c.item_code and c.dep_id = dd.id and ec.isActive = 'Y' and c.interface_flag = '2' "
	    		+ "and ec.change_item != 'C' and ec.pay_status != 'M' and ec.exam_status in ('N','D') and ec.center_num = '"+user.getCenter_num()+"' "
	    		+ " and p.examinfo_num = '" + examInfo.getExam_num() + "'");
	    if ((charingIds != null) && (!"".equals(charingIds))) {
	      sb.append(" and c.id in (" + charingIds + ")");
	    }
	    if (!isBufa) {
	      sb.append(" and ec.is_application = 'N'");
	    }
	    TranLogTxt.liswriteEror_to_txt("reqPacs", "根据体检id和体检编号查询需要发送影像申请的项目:"+sb.toString());
	    List<PacsSendDTO> pacsSendList = this.jqm.getList(sb.toString(), PacsSendDTO.class);
	    
	    String noPayItems = "";
	    if ("N".equals(examInfo.getIs_after_pay())) {
	      for (int i = 0; i < pacsSendList.size(); i++) {
	        if (("N".equals(((PacsSendDTO)pacsSendList.get(i)).getPay_status())) && ("Y".equals(IS_HIS_PACS_CHECK)))
	        {
	          if (i == pacsSendList.size() - 1) {
	            noPayItems = noPayItems + ((PacsSendDTO)pacsSendList.get(i)).getItem_name();
	          } else {
	            noPayItems = noPayItems + ((PacsSendDTO)pacsSendList.get(i)).getItem_name() + ",";
	          }
	          pacsSendList.remove(i);
	        }
	      }
	    }
	    if(!"2.2".equals(wcf.getConfig_method())){
		    if ((pacsSendList.size() == 0) && ("".equals(noPayItems))) {
		      return "error-没有需要发送申请的影像科室项目!";
		    }
	    
		    if ((pacsSendList.size() == 0) && (!"".equals(noPayItems))) {
		      return "error-项目(" + noPayItems + ")未付费,未发送申请!";
		    }
	    }
	    Map<String, PacsComponents> map = new HashMap<String, PacsComponents>();
	    for (PacsSendDTO pacssend : pacsSendList)
	    {
	      PacsComponents pacsCompoents = (PacsComponents)map.get(pacssend.getPacs_req_code());
	      if (pacsCompoents == null)
	      {
	        pacsCompoents = new PacsComponents();
	        
	        pacsCompoents.setReq_no(pacssend.getPacs_req_code());
	        pacsCompoents.setDatetime(DateTimeUtil.getDate());
	      }
	      PacsComponent pc = new PacsComponent();
	      pc.setExam_class(pacssend.getDep_num());
	      pc.setItemDate(DateTimeUtil.getDate());
	      pc.setItemName(pacssend.getItem_name());
	      pc.setHis_num(pacssend.getHis_num());
	      pc.setItemCode(pacssend.getItem_code());
	      pc.setItemprice(pacssend.getItem_amount());
	      pc.setItemamount(pacssend.getAmount());
	      pc.setItemId(pacssend.getItemId());
	      pc.setItemtime(DateTimeUtil.getDateTimes());
	      pc.setPacs_num(pacssend.getView_num());
	      pc.setServiceDeliveryLocation_code(pacssend.getDept_code());
	      pc.setServiceDeliveryLocation_name(pacssend.getDept_name());
	      pc.setCode_class(pacssend.getHiscodeClass());
	      
	      pacsCompoents.setCosts(pacssend.getAmount() + pacsCompoents.getCosts());
	      pacsCompoents.getPacsComponent().add(pc);
	      
	      map.put(pacsCompoents.getReq_no(), pacsCompoents);
	    }
	    List<PacsComponents> mapValuesList = new ArrayList(map.values());
	    
	    pb.setComponents(mapValuesList);
	    
	    PACSSendMessage pm = new PACSSendMessage(pb);
	    ResultPacsBody rb = pm.pacsSend(web_url, web_meth, true);
	    TranLogTxt.liswriteEror_to_txt("reqPacs", "接口返回结果:"+rb.getResultHeader().getTypeCode()+"--boday:"+rb.getControlActProcess().getList());
	    if ("AA".equals(rb.getResultHeader().getTypeCode()))
	    {//申请发送成功
			List<ApplyNOBean> reqList = rb.getControlActProcess().getList();
			for(PacsSendDTO pacssend : pacsSendList){
				for(ApplyNOBean appBean : reqList){
					if(pacssend.getPacs_req_code().equals(appBean.getApplyNO())){
						String sql = "update examinfo_charging_item set is_application = 'Y' where id = "+ pacssend.getId();
						this.jpm.executeSql(sql); //修改项目状态
//						ExaminfoChargingItem item = (ExaminfoChargingItem) this.qm.load(ExaminfoChargingItem.class, pacssend.getId());
//						item.setIs_application("Y");
//						this.pm.update(item);
					}
				}
			}
			if("".equals(noPayItems)){
				return "ok-PACS申请发送成功!";
			}else{
				return "error-项目("+noPayItems+")未付费,未发送申请!";
			}
		}else{
			throw new ServiceException(rb.getResultHeader().getText());
		}
	  }

	@SuppressWarnings("unchecked")
	@Override
	public String pacsDel(String exam_num,String charging_item_code_s,UserDTO user) throws ServiceException {
		
		//查询webservice地址
		WebserviceConfigurationDTO wcf=new WebserviceConfigurationDTO();
		wcf=this.webserviceConfigurationService.getWebServiceConfig("PACS_DEL", user.getCenter_num());
		String web_url = wcf.getConfig_url().trim();
		String web_meth= wcf.getConfig_method().trim();
		//根据体检号查询体检信息
		List<ExamInfo> examList = this.qm.find("from ExamInfo e where e.exam_num = '"+exam_num+"'");
		if(examList.size() == 0){
			return "error-该体检信息不存在!";
		}
		ExamInfo examInfo = examList.get(0);
		//查询人员信息
		List<CustomerInfo> cusList = this.qm.find("from CustomerInfo c where c.id = "+examInfo.getCustomer_id());
		CustomerInfo customerInfo = cusList.get(0);
				
//		List<ExaminfoChargingItem> itemList = null;
		StringBuffer sb = new StringBuffer();
//		sb.append("select ec.* from examinfo_charging_item ec where ec.isActive = 'Y' and ec.change_item != 'C' "
//				+ " and ec.pay_status != 'M' and ec.is_application = 'Y' and "
//				+ " ec.exam_num = "+examInfo.getExam_num()+" and ec.charge_item_id in "
//				+ " (select c.id from charging_item c where c.dep_category = '21'");
//		
//		if(charingIds != null && !"".equals(charingIds)){//未传收费项目ID，这个人全部的PCAS收费项目
//			sb.append(" and c.id in ("+charingIds+")");
//		}
//		sb.append(")");
//		itemList = this.jqm.getList(sb.toString(),ExaminfoChargingItem.class);
//				
//		if(itemList.size() == 0){
//			return "error-没有需要撤销申请的影像科室项目!";
//		}
		
		PacsMessageBody pb = new PacsMessageBody();
		pb.setCenter_num(user.getCenter_num());
		pb.setId_extension("TI004");//写死
		pb.setMessageid("");//交互流水号
		pb.setPart_name("东北国际医院");
		pb.setCreationTime_value(DateTimeUtil.getDateTimes());
		pb.getCustom().setPersonid(examInfo.getPatient_id());// 患者ID
		pb.getCustom().setPersonno(examInfo.getVisit_no());//就诊号
		pb.getCustom().setExam_num(examInfo.getExam_num());
		pb.getCustom().setPersonidnum(customerInfo.getId_num());//身份证号
		pb.getCustom().setPersioncode("");// 医保卡号
		pb.getCustom().setMc_no(examInfo.getMc_no());//就诊卡号
		pb.getCustom().setName(customerInfo.getUser_name());
		pb.getCustom().setSexcode(customerInfo.getSex());
		pb.getCustom().setBirthtime(DateTimeUtil.shortFmt4(customerInfo.getBirthday()));
		pb.getCustom().setComname(examInfo.getCompany());
		pb.getCustom().setContact_name("");
		pb.getCustom().setContact_tel("");
		pb.getCustom().setEthnicGroupCode("");//民族编码
		pb.getCustom().setMeritalcode(examInfo.getIs_marriage());//婚否
		pb.getCustom().setOld(new Long(examInfo.getAge()).intValue());
		pb.getCustom().setTel(examInfo.getPhone());
		pb.getCustom().setAddress(examInfo.getAddress());
		if("男".equals(customerInfo.getSex())){
			pb.getCustom().setSexcode("1");
			pb.getCustom().setSexname("男");
		}else if("女".equals(customerInfo.getSex())){
			pb.getCustom().setSexcode("2");
			pb.getCustom().setSexname("女");
		}else{
			pb.getCustom().setSexcode("3");
			pb.getCustom().setSexname("未知");
		}
		sb.setLength(0);
		sb.append("select distinct p.pacs_req_code,ec.id,c.item_name,c.item_code,ec.item_amount,ec.amount,c.view_num from examinfo_charging_item ec,pacs_summary p,pacs_detail d,charging_item c"
				+" where ec.charging_item_code = c.item_code and p.id = d.summary_id and d.chargingItem_num = c.item_code and ec.isActive = 'Y' "
				+" and ec.change_item != 'C' and ec.pay_status != 'M' and ec.is_application = 'Y' and ec.center_num = '"+user.getCenter_num()+"'"
				+" and ec.exam_num = '"+examInfo.getExam_num()+"' and p.examinfo_num = '"+exam_num+"'");
		//收费项目编码
		if(charging_item_code_s != null && !"".equals(charging_item_code_s)){//未传收费项目ID，这个人全部的PACS收费项目
			if(charging_item_code_s.contains(",") && !charging_item_code_s.contains("'")) {
				charging_item_code_s = charging_item_code_s.replace(",","','");
				charging_item_code_s = "'"+charging_item_code_s+"'";
			} else if(!charging_item_code_s.contains("'")){
				charging_item_code_s = "'" + charging_item_code_s + "'";
			}
			sb.append(" and c.item_code in ("+charging_item_code_s+")");
		}
		
		TranLogTxt.liswriteEror_to_txt("reqPacsDel", "根据体检item_code查询需要撤销检验申请的项目:"+sb.toString());
		List<PacsSendDTO> sumList = this.jqm.getList(sb.toString(), PacsSendDTO.class);
		
		if(sumList.size() == 0){
			return "error-没有需要撤销申请的影像科室项目!";
		}
		
		Map<String,PacsComponents> map = new HashMap<String,PacsComponents>();
		
		for(PacsSendDTO pacssend : sumList){
			PacsComponents pacsCompoents = map.get(pacssend.getPacs_req_code());
			if(pacsCompoents == null){
				pacsCompoents = new PacsComponents();
				
				pacsCompoents.setReq_no(pacssend.getPacs_req_code());
				pacsCompoents.setDatetime(DateTimeUtil.getDate());
			}
			
			PacsComponent pc= new PacsComponent();
//			pc.setExam_class(pacssend.getDep_num());
	        pc.setItemDate(DateTimeUtil.getDate());
	        pc.setItemName(pacssend.getItem_name());
	        pc.setItemCode(pacssend.getItem_code());
	        pc.setItemprice(pacssend.getItem_amount());
	        pc.setItemamount(pacssend.getAmount());
	        pc.setItemtime(DateTimeUtil.getDateTimes());
	        pc.setPacs_num(pacssend.getView_num());
//	        pc.setServiceDeliveryLocation_code(pacssend.getDept_code());
//	        pc.setServiceDeliveryLocation_name(pacssend.getDept_name());
//	        pacsCompoents.setCosts(pacssend.getAmount()+pacsCompoents.getCosts());
	        pacsCompoents.getPacsComponent().add(pc);
	        map.put(pacsCompoents.getReq_no(), pacsCompoents);
		}
		
		List<PacsComponents> mapValuesList = new ArrayList<PacsComponents>(map.values());
		
		pb.setComponents(mapValuesList);
		
		PACSDELSendMessage pm = new PACSDELSendMessage(pb);
		ResultPacsBody rb = pm.pacsSend(web_url,web_meth, true);
		
		TranLogTxt.liswriteEror_to_txt("reqPacsDel", "接口返回结果:"+rb.getResultHeader().getTypeCode()+"--boday:"+rb.getControlActProcess().getList());
		if("AA".equals(rb.getResultHeader().getTypeCode())){//申请发送成功
			List<ApplyNOBean> reqList = rb.getControlActProcess().getList();
			for(PacsSendDTO pacssend : sumList){
				for(ApplyNOBean appBean : reqList){
					if(pacssend.getPacs_req_code().equals(appBean.getApplyNO())){
						String sql = "update examinfo_charging_item set is_application = 'N' where id = "+ pacssend.getId();
						TranLogTxt.liswriteEror_to_txt("reqPacsDel", "修改项目状态:"+sql);
						this.jpm.executeSql(sql); //修改项目状态
						String barcode = this.batchService.GetCreateID("pacs_req_num", user.getCenter_num());
						String sqlpacs = "update pacs_summary set pacs_req_code='"+barcode+"' where pacs_req_code='"+pacssend.getPacs_req_code()+"'";
						TranLogTxt.liswriteEror_to_txt("reqPacsDel", "更新pacs申请主表:"+sqlpacs);
						this.jpm.executeSql(sqlpacs);
						sqlpacs = "update pacs_detail set pacs_req_code = '"+barcode+"' where pacs_req_code = '"+pacssend.getPacs_req_code()+"'";
						TranLogTxt.liswriteEror_to_txt("reqPacsDel", "更新pacs申请明细表:"+sqlpacs);
						this.jpm.executeSql(sqlpacs);
					}
				}
			}
			return "ok-PACS撤销申请发送成功!";
		}else{
			throw new ServiceException(rb.getResultHeader().getText());
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public String lisSend(String exam_num, String charingIds, UserDTO user, boolean isBufa, String bangding, String checkhisfee) throws ServiceException {
	    WebserviceConfigurationDTO wcf = new WebserviceConfigurationDTO();
	    wcf = this.webserviceConfigurationService.getWebServiceConfig("LIS_SEND", user.getCenter_num());
	    String web_url = wcf.getConfig_url().trim();
	    String web_meth = wcf.getConfig_method().trim();
	    
	    List<ExamInfo> examList = this.qm.find("from ExamInfo e where e.exam_num = '" + exam_num + "'");
	    if (examList.size() == 0) {
	      return "error-该体检信息不存在!";
	    }
	    ExamInfo examInfo = (ExamInfo)examList.get(0);
	    
	    List<CustomerInfo> cusList = this.qm.find("from CustomerInfo c where c.id = " + examInfo.getCustomer_id());
	    CustomerInfo customerInfo = (CustomerInfo)cusList.get(0);
	    
	    StringBuffer sb = new StringBuffer();
	    
	    LisMessageBody pb = new LisMessageBody();
	    pb.setCenter_num(user.getCenter_num());
	    pb.setId_extension("TI021");
	    pb.setMessageid("");
	    pb.setPart_name("东北国际医院");
	    pb.setCreationTime_value(DateTimeUtil.getDateTimes());
	    pb.getCustom().setPersonid(examInfo.getPatient_id());
	    pb.getCustom().setPersonno(examInfo.getVisit_no());
	    pb.getCustom().setExam_num(examInfo.getExam_num());
	    pb.getCustom().setPersonidnum(customerInfo.getId_num());
	    pb.getCustom().setPersioncode("");
	    pb.getCustom().setMc_no(examInfo.getMc_no());
	    pb.getCustom().setName(customerInfo.getUser_name());
	    pb.getCustom().setSexcode(customerInfo.getSex());
	    pb.getCustom().setBirthtime(DateTimeUtil.shortFmt4(customerInfo.getBirthday()));
	    pb.getCustom().setComname(examInfo.getCompany());
	    pb.getCustom().setContact_name("");
	    pb.getCustom().setContact_tel("");
	    pb.getCustom().setEthnicGroupCode("");
	    pb.getCustom().setMeritalcode(examInfo.getIs_marriage());
	    pb.getCustom().setOld(new Long(examInfo.getAge()).intValue());
	    pb.getCustom().setTel(examInfo.getPhone());
	    pb.getCustom().setAddress(examInfo.getAddress());
	    if ("男".equals(customerInfo.getSex()))
	    {
	      pb.getCustom().setSexcode("1");
	      pb.getCustom().setSexname("男");
	    }
	    else if ("女".equals(customerInfo.getSex()))
	    {
	      pb.getCustom().setSexcode("2");
	      pb.getCustom().setSexname("女");
	    }
	    else
	    {
	      pb.getCustom().setSexcode("3");
	      pb.getCustom().setSexname("未知");
	    }
	    List<JobDTO> dataList = this.companyService.getDatadis("SQKS","1");
	    if (dataList.size() == 0) {
	      return "error-请在数据字典中维护申请科室(data_code = SQKS)";
	    }
	    pb.getDoctor().setDept_code(((JobDTO)dataList.get(0)).getRemark());
	    pb.getDoctor().setDept_name(((JobDTO)dataList.get(0)).getName());
	    pb.getDoctor().setDoctorCode(user.getWork_num());
	    pb.getDoctor().setDoctorName(user.getName());
	    pb.getDoctor().setTime(DateTimeUtil.getDateTimes());
	    
	    sb.setLength(0);
	    
	    sb.append(" select c.Id as chargingitemId,c.item_name,c.exam_num,d.remark,d.data_name,ec.amount,c.his_num,c.item_code,ec.item_amount, hd.dept_code,hd.dept_name,ec.id,ec.pay_status,c.hiscodeClass");
	    if (("1".equals(web_meth)) || ("2".equals(web_meth))) {
	      sb.append(",s.sample_barcode ");
	    } else if ("3".equals(web_meth)) {
	      sb.append(",convert(varchar(50),s.id) as sample_barcode ");
	    } else {
	      sb.append(",s.sample_barcode ");
	    }
	    sb.append("from examinfo_charging_item ec,sample_exam_detail s,examResult_chargingItem er,sample_demo sd,data_dictionary d,"
	    		+ "charging_item  c "
	    		+ "left join his_dict_dept hd on c.perform_dept = hd.dept_code "
	    		+ "where ec.exam_num = s.exam_num and ec.charging_item_code = c.item_code "
	    		+ "and s.sample_id = c.sam_demo_id and s.sample_id = sd.id and sd.demo_category = d.id and ec.isActive = 'Y' "
	    		+ "and c.interface_flag = '2' and ec.exam_status in ('N','D')  and  s.sample_barcode = er.bar_code "
	    		+ "and er.charging_item_code = ec.charging_item_code and er.result_type = 'sample' "
	    		+ "and ec.change_item != 'C' and ec.pay_status != 'M' and ec.center_num = '"+user.getCenter_num()+"' "
	    				+ "and ec.exam_num ='"+examInfo.getExam_num()+"'"  );
	    if ((charingIds != null) && (!"".equals(charingIds))) {
	      sb.append(" and c.id in (" + charingIds + ")");
	    }
	    if (!isBufa) {
	      sb.append(" and ec.is_application = 'N'");
	    }
	    if ("Y".equals(bangding)) {
	      sb.append(" and (sd.BarCode_Class = 0 or (sd.BarCode_Class = 1 and s.is_binding = 1))");
	    }
	    TranLogTxt.liswriteEror_to_txt("reqLis", "根据体检id查询需要发送检验申请的项目:"+sb.toString());
	    List<LisSendDTO> sendList = this.jqm.getList(sb.toString(), LisSendDTO.class);
	    
	    String noPayItems = "";
	    if ("N".equals(examInfo.getIs_after_pay())) {
	      for (int i = 0; i < sendList.size(); i++) {
	        if (("N".equals(((LisSendDTO)sendList.get(i)).getPay_status())) && ("Y".equals(checkhisfee)))
	        {
	          if (i == sendList.size() - 1) {
	            noPayItems = noPayItems + ((LisSendDTO)sendList.get(i)).getItem_name();
	          } else {
	            noPayItems = noPayItems + ((LisSendDTO)sendList.get(i)).getItem_name() + ",";
	          }
	          sendList.remove(i);
	        }
	      }
	    }
	    if ((sendList.size() == 0) && ("".equals(noPayItems))) {
	      return "error-没有需要发送申请的检验科室项目!";
	    }
	    if ((sendList.size() == 0) && (!"".equals(noPayItems))) {
	      return "error-项目(" + noPayItems + ")未付费,未发送申请!";
	    }
	    Map<String, LisComponents> map = new HashMap();
	    for (LisSendDTO lissend : sendList)
	    {
	      LisComponents lisComponents = (LisComponents)map.get(lissend.getSample_barcode());
	      if (lisComponents == null)
	      {
	        lisComponents = new LisComponents();
	        lisComponents.setReq_no(lissend.getSample_barcode());
	        lisComponents.setDatetime(DateTimeUtil.getDate());
	        lisComponents.setCsampleName(lissend.getData_name());
	      }
	      LisComponent pc = new LisComponent();
	      pc.setItemName(lissend.getItem_name());
	      pc.setChargingItemid(lissend.getChargingitemId() + "");
	      pc.setItemCode(lissend.getExam_num());
	      pc.setHis_num(lissend.getHis_num());
	      pc.setItemtime(DateTimeUtil.getDateTimes());
	      pc.setItemprice(lissend.getItem_amount());
	      pc.setItemamount(lissend.getAmount());
	      pc.setExtension(lissend.getSample_barcode());
	      pc.setSpecimenNatural(lissend.getRemark());
	      pc.setSpecimenNaturalname(lissend.getData_name());
	      pc.setServiceDeliveryLocation_code(lissend.getDept_code());
	      pc.setServiceDeliveryLocation_name(lissend.getDept_name());
	      pc.setCode_class(lissend.getHiscodeClass());
	      lisComponents.setCosts(lisComponents.getCosts() + lissend.getAmount());
	      lisComponents.getItemList().add(pc);
	      
	      map.put(lisComponents.getReq_no(), lisComponents);
	    }
	    
	    String IS_LIS_INTERFACE_TYPE = "0";//1表示按照申请单号发送申请，2表示按照一次体检发生申请，写第三方申请号,3表示按照一次体检发送申请，不写已经采样状态
		try {
			IS_LIS_INTERFACE_TYPE = this.customerInfoService.getCenterconfigByKey("IS_LIS_INTERFACE_TYPE", user.getCenter_num()).getConfig_value().trim();
		} catch (ServiceException e) {
			System.out.println("缺少配置IS_LIS_INTERFACE_TYPE 配置无效配置");
			e.printStackTrace();
		}
		
	    
	    List<LisComponents> mapValuesList = new ArrayList<LisComponents>(map.values());
		if("1".equals(web_meth) || "2".equals(web_meth)|| "4".equals(web_meth)||"1".equals(IS_LIS_INTERFACE_TYPE)){
			for(LisComponents listCompone : mapValuesList){
				List<LisComponents> commpone = new ArrayList<LisComponents>();
				commpone.add(listCompone);
				pb.setComponents(commpone);				
				LISSendMessage pm = new LISSendMessage(pb);
				ResultLisBody rb = pm.lisSend(web_url,web_meth,true);
				if("AA".equals(rb.getResultHeader().getTypeCode())){//申请发送成功
					List<ApplyNOBean> reqList = rb.getControlActProcess().getList();
						for(LisSendDTO lissend : sendList){
							for(ApplyNOBean appBean : reqList){
								if(lissend.getSample_barcode().equals(appBean.getApplyNO())){
									String sql = "update examinfo_charging_item set is_application = 'Y' where id = "+ lissend.getId();
									TranLogTxt.liswriteEror_to_txt("reqLis", "修改项目状态:"+sql);
									this.jpm.executeSql(sql); //修改项目状态
								}
							}
						}
				}else{
					throw new ServiceException(rb.getResultHeader().getText());
				}
			}
		}else if("3".equals(web_meth)||"2".equals(IS_LIS_INTERFACE_TYPE)){
			pb.setComponents(mapValuesList);
			LISSendMessage pm = new LISSendMessage(pb);
			ResultLisBody rb = pm.lisSend(web_url,web_meth,true);
			if("AA".equals(rb.getResultHeader().getTypeCode())){//申请发送成功
				List<ApplyNOBean> reqList = rb.getControlActProcess().getList();
					for(LisSendDTO lissend : sendList){
						for(ApplyNOBean appBean : reqList){
							if(lissend.getSample_barcode().equals(appBean.getApplyNO())){
								String sql = "update examinfo_charging_item set is_application = 'Y' where id = "+ lissend.getId();
								TranLogTxt.liswriteEror_to_txt("reqLis", "修改项目状态:"+sql);
								this.jpm.executeSql(sql); //修改项目状态
								
								String sql1 ="update sample_exam_detail set sample_barcode = '"+appBean.getBarcode()+"' where id= "+ lissend.getSample_barcode();
								TranLogTxt.liswriteEror_to_txt("reqLis", "更新返回条码号:"+sql1);
								this.jpm.executeSql(sql1); //更新返回条码号
							}
						}
					}
			}else{
				throw new ServiceException(rb.getResultHeader().getText());
			}
			
		}else if("10".equals(web_meth)||"3".equals(IS_LIS_INTERFACE_TYPE)){
			pb.setComponents(mapValuesList);
			LISSendMessage pm = new LISSendMessage(pb);
			ResultLisBody rb = pm.lisSend(web_url,web_meth,true);
			if("AA".equals(rb.getResultHeader().getTypeCode())){//申请发送成功
				List<ApplyNOBean> reqList = rb.getControlActProcess().getList();
					for(LisSendDTO lissend : sendList){
						for(ApplyNOBean appBean : reqList){
							if(lissend.getSample_barcode().equals(appBean.getApplyNO())){
								String sql = "update examinfo_charging_item set is_application = 'Y' where id = "+ lissend.getId();
								this.jpm.executeSql(sql); //修改项目状态
								
								/*String sql1 ="update sample_exam_detail set sample_barcode = '"+appBean.getBarcode()+"' where id= "+ lissend.getSample_barcode();
								this.jpm.executeSql(sql1); //更新返回条码号
	*/							}
						}
					}
			}else{
				throw new ServiceException(rb.getResultHeader().getText());
			}
		}else{
			for(LisComponents listCompone : mapValuesList){
				List<LisComponents> commpone = new ArrayList<LisComponents>();
				commpone.add(listCompone);
				pb.setComponents(commpone);
				
				LISSendMessage pm = new LISSendMessage(pb);
				ResultLisBody rb = pm.lisSend(web_url,web_meth,true);
				if("AA".equals(rb.getResultHeader().getTypeCode())){//申请发送成功
					List<ApplyNOBean> reqList = rb.getControlActProcess().getList();
						for(LisSendDTO lissend : sendList){
							for(ApplyNOBean appBean : reqList){
								if(lissend.getSample_barcode().equals(appBean.getApplyNO())){
									String sql = "update examinfo_charging_item set is_application = 'Y' where id = "+ lissend.getId();
									this.jpm.executeSql(sql); //修改项目状态
								}
							}
						}
				}else{
					throw new ServiceException(rb.getResultHeader().getText());
				}
			}
		}
		if("".equals(noPayItems)){
			return "ok-LIS申请发送成功!";
		}else{
			return "error-项目("+noPayItems+")未付费,未发送申请!";
		}
	  }

	@SuppressWarnings("unchecked")
	@Override
	public String lisDel(String exam_num, String charging_item_code_s, UserDTO user) throws ServiceException {
		  WebserviceConfigurationDTO wcf = new WebserviceConfigurationDTO();
		    wcf = this.webserviceConfigurationService.getWebServiceConfig("LIS_DEL", user.getCenter_num());
		    String web_url = wcf.getConfig_url().trim();
		    String web_meth = wcf.getConfig_method().trim();
		    
		    List<ExamInfo> examList = this.qm.find("from ExamInfo e where e.exam_num = '" + exam_num + "'");
		    if (examList.size() == 0) {
		      return "error-该体检信息不存在!";
		    }
		    ExamInfo examInfo = (ExamInfo)examList.get(0);
		    
		    List<CustomerInfo> cusList = this.qm.find("from CustomerInfo c where c.id = " + examInfo.getCustomer_id());
		    CustomerInfo customerInfo = (CustomerInfo)cusList.get(0);
		    StringBuffer sb = new StringBuffer();
		    LisMessageBody pb = new LisMessageBody();
		    pb.setCenter_num(user.getCenter_num());
		    pb.setId_extension("TI024");
		    pb.setMessageid("");
		    pb.setPart_name("东北国际医院");
		    pb.setCreationTime_value(DateTimeUtil.getDateTimes());
		    pb.getCustom().setPersonid(examInfo.getPatient_id());
		    pb.getCustom().setPersonno(examInfo.getVisit_no());
		    pb.getCustom().setExam_num(examInfo.getExam_num());
		    pb.getCustom().setPersonidnum(customerInfo.getId_num());
		    pb.getCustom().setPersioncode("");
		    pb.getCustom().setMc_no(examInfo.getMc_no());
		    pb.getCustom().setName(customerInfo.getUser_name());
		    pb.getCustom().setSexcode(customerInfo.getSex());
		    pb.getCustom().setBirthtime(DateTimeUtil.shortFmt4(customerInfo.getBirthday()));
		    pb.getCustom().setComname(examInfo.getCompany());
		    pb.getCustom().setContact_name("");
		    pb.getCustom().setContact_tel("");
		    pb.getCustom().setEthnicGroupCode("");
		    pb.getCustom().setMeritalcode(examInfo.getIs_marriage());
		    pb.getCustom().setOld(new Long(examInfo.getAge()).intValue());
		    pb.getCustom().setTel(examInfo.getPhone());
		    pb.getCustom().setAddress(examInfo.getAddress());
		    if ("男".equals(customerInfo.getSex()))
		    {
		      pb.getCustom().setSexcode("1");
		      pb.getCustom().setSexname("男");
		    }
		    else if ("女".equals(customerInfo.getSex()))
		    {
		      pb.getCustom().setSexcode("2");
		      pb.getCustom().setSexname("女");
		    }
		    else
		    {
		      pb.getCustom().setSexcode("3");
		      pb.getCustom().setSexname("未知");
		    }
		    List<JobDTO> dataList = this.companyService.getDatadis("SQKS");
		    if (dataList.size() == 0) {
		      return "error-请在数据字典中维护申请科室(data_code = SQKS)";
		    }
		    pb.getDoctor().setDept_code(((JobDTO)dataList.get(0)).getRemark());
		    pb.getDoctor().setDept_name(((JobDTO)dataList.get(0)).getName());
		    pb.getDoctor().setDoctorCode(user.getWork_num());
		    pb.getDoctor().setDoctorName(user.getName());
		    pb.getDoctor().setTime(DateTimeUtil.getDateTimes());
		    sb.setLength(0);
		    




		    sb.append(" select c.Id as chargingitemId,c.item_name,c.exam_num,d.remark,d.data_name,ec.amount,c.his_num,c.item_code,ec.item_amount,hd.dept_code,hd.dept_name,ec.id,ec.pay_status,c.hiscodeClass,s.sample_barcode ");
		    
		    sb.append("from examinfo_charging_item ec,sample_exam_detail s,examResult_chargingItem er,sample_demo sd,data_dictionary d,charging_item  c "
		    		+ "left join his_dict_dept hd on c.perform_dept = hd.dept_code "
		    		+ "where ec.exam_num = s.exam_num and ec.charging_item_code = c.item_code and s.sample_id = c.sam_demo_id and s.sample_id = sd.id "
		    		+ "and sd.demo_category = d.id and ec.isActive = 'Y' and c.interface_flag = '2' and ec.is_application = 'Y'  "
		    		+ "and s.sample_barcode = er.bar_code and er.charging_item_code = ec.charging_item_code and er.result_type = 'sample' "
		    		+ "and ec.change_item != 'C' and ec.pay_status != 'M' and ec.center_num = '"+user.getCenter_num()+"' "
		    				+ "and ec.exam_num ='"+examInfo.getExam_num()+"'");
		  //收费项目编码
		if (charging_item_code_s != null && !"".equals(charging_item_code_s)) {// 未传收费项目ID，这个人全部的PACS收费项目
			if (charging_item_code_s.contains(",") && !charging_item_code_s.contains("'")) {
				charging_item_code_s = charging_item_code_s.replace(",", "','");
				charging_item_code_s = "'" + charging_item_code_s + "'";
			}else if(!charging_item_code_s.contains("'")){
				charging_item_code_s = "'" + charging_item_code_s + "'";
			}
			sb.append(" and c.item_code in (" + charging_item_code_s + ")");
		}
		    TranLogTxt.liswriteEror_to_txt("reqLisDel", "根据体检item_code查询需要撤销检验申请的项目:"+sb.toString());
		    
		    List<LisSendDTO> sendList = this.jqm.getList(sb.toString(), LisSendDTO.class);
		    if (sendList.size() == 0) {
		      return "error-没有需要撤销申请的检验科室项目!";
		    }
		    Map<String, LisComponents> map = new HashMap();
		    for (LisSendDTO lissend : sendList)
		    {
		      LisComponents lisComponents = (LisComponents)map.get(lissend.getSample_barcode());
		      if (lisComponents == null)
		      {
		        lisComponents = new LisComponents();
		        lisComponents.setReq_no(lissend.getSample_barcode());
		        lisComponents.setDatetime(DateTimeUtil.getDate());
		        lisComponents.setCsampleName(lissend.getData_name());
		      }
		      LisComponent pc = new LisComponent();
		      pc.setItemName(lissend.getItem_name());
		      pc.setChargingItemid(lissend.getChargingitemId() + "");
		      pc.setItemCode(lissend.getExam_num());
		      pc.setHis_num(lissend.getHis_num());
		      pc.setItemtime(DateTimeUtil.getDateTimes());
		      pc.setItemprice(lissend.getItem_amount());
		      pc.setItemamount(lissend.getAmount());
		      pc.setExtension(lissend.getSample_barcode());
		      pc.setSpecimenNatural(lissend.getRemark());
		      pc.setSpecimenNaturalname(lissend.getData_name());
		      pc.setServiceDeliveryLocation_code(lissend.getDept_code());
		      pc.setServiceDeliveryLocation_name(lissend.getDept_name());
		      pc.setCode_class(lissend.getHiscodeClass());
		      lisComponents.setCosts(lisComponents.getCosts() + lissend.getAmount());
		      lisComponents.getItemList().add(pc);
		      
		      map.put(lisComponents.getReq_no(), lisComponents);
		    }
		    List<LisComponents> mapValuesList = new ArrayList(map.values());
		    pb.setComponents(mapValuesList);
		    
		    LISDELSendMessage pm = new LISDELSendMessage(pb);
		    ResultLisBody rb = pm.lisSend(web_url, web_meth, false);
		    TranLogTxt.liswriteEror_to_txt("reqLisDel", "接口返回结果:"+rb.getResultHeader().getTypeCode()+"--boday:"+rb.getControlActProcess().getList());
		    if("AA".equals(rb.getResultHeader().getTypeCode())){//申请发送成功
				List<ApplyNOBean> reqList = rb.getControlActProcess().getList();
				for(LisSendDTO lissend : sendList){
					for(ApplyNOBean appBean : reqList){
						if(lissend.getSample_barcode().equals(appBean.getApplyNO())){
							String sql = "update examinfo_charging_item set is_application = 'N' where id = "+ lissend.getId();
							TranLogTxt.liswriteEror_to_txt("reqLisDel", "修改项目状态:"+sql);
							this.jpm.executeSql(sql); //修改项目状态
							String barcode = this.batchService.GetCreateID("barcode", user.getCenter_num());
							sql = "update sample_exam_detail set sample_barcode = '"+barcode+"' where sample_barcode = '"+lissend.getSample_barcode()+"'";
							TranLogTxt.liswriteEror_to_txt("reqLisDel", "取消采样打印SQL:"+sql);
							this.jpm.executeSql(sql); 
							sql = "update examResult_chargingItem set bar_code = '"+barcode+"' where bar_code='" + lissend.getSample_barcode()
								+ "' and result_type='sample' and isActive='Y'";
							this.jpm.executeSql(sql); 
							TranLogTxt.liswriteEror_to_txt("reqLisDel", "取消采样打印SQL:"+sql);
						}
					}
				}
				return "ok-LIS撤销申请发送成功!";
			}else{
				throw new ServiceException(rb.getResultHeader().getText());
			}
	}
	@SuppressWarnings("unchecked")
	@Override
	public String delFeeSend(String exam_num, String charingItemCodeS, UserDTO user) throws ServiceException {
		//查询webservice地址
		WebserviceConfigurationDTO wcf=new WebserviceConfigurationDTO();
		wcf=this.webserviceConfigurationService.getWebServiceConfig("DEL_ITEM_APPLICATION", user.getCenter_num());
		String web_url = wcf.getConfig_url().trim();
		String web_meth= wcf.getConfig_method().trim();
		//根据体检号查询体检信息
		List<ExamInfoDTO> examList = this.jqm.getList("select e.id,e.exam_num,e.visit_date,e.visit_no,e.patient_id from exam_info e where e.exam_num = '"+exam_num+"'",ExamInfoDTO.class);
		if(examList.size() == 0){
			return "error-该体检信息不存在!";
		}
		ExamInfoDTO examInfo = examList.get(0);
		//查询人员信息
//		List<CustomerInfo> cusList = this.qm.find("from CustomerInfo c where c.id = "+examInfo.getCustomer_id());
//		CustomerInfo customerInfo = cusList.get(0);
		
		List<ExaminfoChargingItemDTO> itemList = null;
		StringBuffer sb = new StringBuffer();
		sb.append("select ec.id,ec.examinfo_id,ec.charge_item_id,c.item_code,c.his_num,ec.item_amount,ec.amount from examinfo_charging_item ec,charging_item c "
				+ "where c.item_code = ec.charging_item_code and ec.his_req_status = 'Y' and ec.pay_status in ('N','R') and ec.isActive = 'Y' and ec.center_num = '"+user.getCenter_num()+"'"
				+ " and ec.exam_num = '"+examInfo.getExam_num()+"'");
		
		
		
		if (charingItemCodeS != null && !"".equals(charingItemCodeS)) {// 处理chaing_item_codes 逗号 单引号 
			if (charingItemCodeS.contains(",") && !charingItemCodeS.contains("'")) {
				charingItemCodeS = charingItemCodeS.replace(",", "','");
				charingItemCodeS = "'" + charingItemCodeS + "'";
			}else if(!charingItemCodeS.contains("'")){
				charingItemCodeS = "'" + charingItemCodeS + "'";
			}
			sb.append(" and c.item_code in (" + charingItemCodeS + ")");
		}
		
		
		itemList = this.jqm.getList(sb.toString(),ExaminfoChargingItemDTO.class);
		if(itemList.size() == 0){
			return "ok-没有需要撤销HIS申请的项目!";
		}
		
		sb.setLength(0);
		sb.append("select distinct cs.* from examinfo_charging_item ec,charging_summary_single cs,"
				+ "charging_detail_single cd where "
				//+ "ec.charging_item_code = cd.charging_item_code "//4.0.0.2版本  charging_detail_single表charging_item_code未存数据
				+ "ec.charge_item_id = cd.charging_item_id "
				+ "and cs.exam_num = ec.exam_num "
				+ "and cs.id = cd.summary_id and cs.is_active = 'Y' and cs.charging_status = 'R' "
				+ "and ec.his_req_status = 'Y' and ec.center_num = '"+user.getCenter_num()+"' and ec.exam_num = "+examInfo.getExam_num());
		

		if (charingItemCodeS != null && !"".equals(charingItemCodeS)) {// 处理chaing_item_codes 逗号 单引号 
			if (charingItemCodeS.contains(",") && !charingItemCodeS.contains("'")) {
				charingItemCodeS = charingItemCodeS.replace(",", "','");
				charingItemCodeS = "'" + charingItemCodeS + "'";
			}else if(!charingItemCodeS.contains("'")){
				charingItemCodeS = "'" + charingItemCodeS + "'";
			}
			sb.append(" and c.item_code in (" + charingItemCodeS + ")");
		}
		
		List<ChargingSummarySingle> list = this.jqm.getList(sb.toString(), ChargingSummarySingle.class);
		
//		if(list.size() != 1){
//			return "error-只能同时减掉同一个申请单号中的项目!";
//		}
		
			for(ChargingSummarySingle charsummarySingle : list){
				DelFeeMessage fm = new DelFeeMessage();
				
				List<ChargingDetailSingleDTO> detaillist = this.jqm.getList("select cs.charging_item_id,cs.id,cs.amount from charging_detail_single cs where cs.summary_id = '"+charsummarySingle.getId()+"'", ChargingDetailSingleDTO.class);
				
				fm.setExam_num(user.getCenter_num());
				fm.setREQ_NO(charsummarySingle.getReq_num());
				fm.setPATIENT_ID(examInfo.getPatient_id());//
				fm.setVISIT_DATE(examInfo.getVisit_date());
				fm.setVISIT_NO(examInfo.getVisit_no());
				fm.setExam_num(exam_num);
				List<ExaminfoChargingItemDTO> reqlist = new ArrayList<ExaminfoChargingItemDTO>();
				for(int i=0;i<detaillist.size();i++){
					for(int j=0;j<itemList.size();j++){
						if(detaillist.get(i).getCharging_item_id() == itemList.get(j).getCharge_item_id()){
							reqlist.add(itemList.get(j));
						}
					}
				}
				fm.setItemCodeList(reqlist);
				
				DELFEESendMessage fsm= new DELFEESendMessage(fm);		
				FeeReqBody frb= new FeeReqBody();
				frb = fsm.feeSend(web_url,web_meth, true);
				if("AA".equals(frb.getResultHeader().getTypeCode())){//申请发送成功
					String IS_SINGLE_REFUND = this.customerInfoService.getCenterconfigByKey("IS_SINGLE_REFUND", user.getCenter_num()).getConfig_value().trim();
					if("Y".equals(IS_SINGLE_REFUND)){//单项减项
						
					}else{
						String sql1 = "update charging_summary_single set is_active='N' where id="+charsummarySingle.getId();
						this.jpm.executeSql(sql1); //修改项目状态				
						String sql =" select ec.* from examinfo_charging_item ec ,charging_summary_single cs,charging_detail_single cd"
								   +" where ec.charging_item_code = cd.charging_item_code and cs.exam_num = ec.exam_num "
								   + "and cs.id = cd.summary_id"
								   +" and ec.isActive = 'Y' and ec.pay_status <> 'M' and ec.center_num = '"+user.getCenter_num()+"' "
								   		+ "and ec.exam_num = "+examInfo.getExam_num()+" and cs.id = " + charsummarySingle.getId();
						List<ExaminfoChargingItem> itemLists = this.jqm.getList(sql, ExaminfoChargingItem.class);
						for(ExaminfoChargingItem examitems : itemLists){
							String sql2="update examinfo_charging_item set his_req_status = 'N' where id = "+examitems.getId();
							this.jpm.executeSql(sql2); //修改项目状态
						}
					}
				}else{
					throw new ServiceException(frb.getResultHeader().getText());
				}
			}
		
		return "ok-HIS撤销申请撤销成功!";
	}

	@Override
	public String cancelFees(String exam_num, String charingIds, UserDTO user) throws ServiceException {
	    
	    WebserviceConfigurationDTO wcf = new WebserviceConfigurationDTO();
	    wcf = this.webserviceConfigurationService.getWebServiceConfig("REFUND_APPLICATION", user.getCenter_num());
	    String IS_SINGLE_REFUND = this.customerInfoService.getCenterconfigByKey("IS_SINGLE_REFUND", user.getCenter_num()).getConfig_value().trim();
	    
	    List<ExamInfoDTO> examList = this.jqm.getList("select e.id from exam_info e where e.exam_num = '" + exam_num + "'", ExamInfoDTO.class);
	    if (examList.size() == 0) {
	      return "error-该体检信息不存在!";
	    }
	    ExamInfoDTO examInfo = (ExamInfoDTO)examList.get(0);
	    
	    StringBuffer sb = new StringBuffer();
	    sb.append("select c.item_code,ec.id,ec.personal_pay,ec.item_amount,ec.charge_item_id,ec.discount from examinfo_charging_item ec,charging_item c where "
	    		+ "c.item_code = ec.charging_item_code and ec.his_req_status = 'Y' and ec.pay_status = 'Y' and ec.exam_num = '" + exam_num +"'");
	    if ((charingIds != null) && (!"".equals(charingIds))) {
	      sb.append(" and ec.charge_item_id in (" + charingIds + ")");
	    }
	    List<ExaminfoChargingItemDTO> itemList = this.jqm.getList(sb.toString(), ExaminfoChargingItemDTO.class);
	    if (itemList.size() == 0) {
	      return "error-请选择需要退费的项目!";
	    }
	    List<String> list = new ArrayList();
	    
	    double amount = 0.0;
	    double item_amount = 0.0;
	    for (ExaminfoChargingItemDTO dto : itemList) {
	      list.add(dto.getItem_code());
	      amount += dto.getPersonal_pay();
	      amount += dto.getItem_amount();
	    }
	    sb.setLength(0);
	    sb.append("select distinct cs.* from examinfo_charging_item ec,charging_summary_single cs,charging_detail_single cd where "
	    		+ "ec.charging_item_code = cd.charging_item_code and cs.exam_num = ec.exam_num and cs.id = cd.summary_id and cs.is_active = 'Y' "
	    		+ "and ec.his_req_status = 'Y'  and cs.charging_status='Y'  and ec.exam_num = '" + exam_num +"'");
	    if ((charingIds != null) && (!"".equals(charingIds))) {
	      sb.append(" and ec.charge_item_id in (" + charingIds + ")");
	    }
	    List<ChargingSummarySingle> listsumary = this.jqm.getList(sb.toString(), ChargingSummarySingle.class);
	    List<String> reqlist = new ArrayList();
	    for (ChargingSummarySingle chargingSummarySingle : listsumary) {
	    	if("Y".equals(chargingSummarySingle.getIs_print_recepit()) && "N".equals(IS_SINGLE_REFUND)){
	    		List<ChargingSummarySingle> invoiceList = this.jqm.getList("select * from charging_summary_single cs where cs.is_active = 'Y' and cs.invoice_id = "+ chargingSummarySingle.getInvoice_id(), ChargingSummarySingle.class);
	    		for (ChargingSummarySingle chargingSummarySingle2 : invoiceList) {
	    			reqlist.add(chargingSummarySingle2.getReq_num());
				}
	    	}else{
	    		reqlist.add(chargingSummarySingle.getReq_num());
	    	}
	    }
	    //去除重复申请单
	    HashSet<String> h = new HashSet<String>(reqlist);   
	    reqlist.clear();   
	    reqlist.addAll(h);  
	    
	    UnFeeMessage fm = new UnFeeMessage();
	    ReqUnNo reqUnno = new ReqUnNo();
	    reqUnno.setREQ_NO(reqlist);
	    fm.setEXAM_NUM(exam_num);
	    fm.setItemCodeList(list);
	    fm.setREQ_NOS(reqUnno);
	    
	    UNFEESendMessage fsm = new UNFEESendMessage(fm);
	    FeeReqBody frb = new FeeReqBody();
	    frb = fsm.feeSend(wcf.getConfig_url().trim(), wcf.getConfig_method().trim(), true);
		if ("AA".equals(frb.getResultHeader().getTypeCode())) {
			Connection connection = null;
			// 插入数据库
			try {
				// 读取记录数
				connection = this.jqm.getConnection();
				connection.setAutoCommit(false);
				List<ReqNo> req = frb.getControlActProcess().getList();
				if ("Y".equals(IS_SINGLE_REFUND)) {//单项退费
					for(ReqNo reqno : req) {
						String selsql1 = "select cs.id,cs.is_print_recepit,cs.invoice_id from charging_summary_single cs where and cs.req_num = '"+ reqno.getREQ_NO()+"'";
						ResultSet rs = connection.createStatement().executeQuery(selsql1);
						if(rs.next()){
							if("Y".equals(rs.getString("is_print_recepit"))){ //已开发票
								String selsql2 = "select * from charging_invoice_single in where in.id = "+rs.getInt("invoice_id");
								ResultSet rsinvoice = connection.createStatement().executeQuery(selsql2);
								
								if(rsinvoice.next()){//作废发票
									String upsql1 = "update charging_invoice_single set canceller='"+user.getUserid()+"',cancel_time='"+DateTimeUtil.getDate3()+"' where id = "+ rsinvoice.getInt("id");
									connection.createStatement().executeUpdate(upsql1);
									
									String insersql1 = "insert into card_deal(amount,card_num,trancode,creater,deal_type,deal_date,deal_time,old_amount,invoice_class,exam_num) "
											+ "values('"+rsinvoice.getDouble("invoice_amount")+"','"+rsinvoice.getString("invoice_num")+"','002','"+user.getUserid()+"','1',"
											+ "'"+DateTimeUtil.getDate2()+"','"+DateTimeUtil.getDate3()+"','"+rsinvoice.getDouble("invoice_amount")+"','"+rsinvoice.getDouble("invoice_class")+"','"+exam_num+"')";
									connection.createStatement().executeUpdate(insersql1);
									
									String insersql2 = "insert into nullify_invoice(invoice_num,invoice_status,invoice_class,creater,create_time,daily_status,account_num,center_num) "
											+ "values('"+rsinvoice.getString("invoice_num")+"','N','"+rsinvoice.getDouble("invoice_class")+"','"+user.getUserid()+"','"+DateTimeUtil.getDate3()+"','0',"
											+ "'"+GetNumContral.getInstance().getParamNum("account_num",user.getCenter_num())+"','"+user.getCenter_num()+"')";
									connection.createStatement().executeUpdate(insersql2);
									
									String selsql3 = "select cs.id from charging_summary_single cs where and cs.invoice_id = '"+ rs.getInt("invoice_id")+"'";
									ResultSet rssum = connection.createStatement().executeQuery(selsql3);
									
									if(rssum.next()){
										String upsql2 = "update charging_summary_single set is_print_recepit='N',invoice_id=0 where id="+rssum.getInt("id");
										connection.createStatement().executeUpdate(upsql2);
									}
								}
							}
							
							String insersql = "insert into charging_summary_single(req_num,exam_id,charging_status,amount1,amount2,discount,cashier,cash_date,is_print_recepit,is_active,creater,create_time,updater,update_time,daily_status,exam_num,center_num)"
									+ "values('"+reqno.getREQ_NO()+"','"+examInfo.getId()+"','M','"+item_amount+"','"+amount+"','10','"+user.getUserid()+"','"+DateTimeUtil.getDate3()+"','N','Y','"+user.getUserid()+"','"+DateTimeUtil.getDate3()+"',"
									+ "'"+user.getUserid()+"','"+DateTimeUtil.getDate3()+"','0','"+exam_num+"','"+user.getCenter_num()+"')";
							int summary_id = 0;
							connection.createStatement().executeUpdate(insersql);
							ResultSet executeQuery = connection.createStatement().executeQuery("select SCOPE_IDENTITY() as id ");//主键id
							if (executeQuery.next()) {
								summary_id = executeQuery.getInt("id");
							}
							for (ExaminfoChargingItemDTO dto : itemList) {
								String insersql1 = "insert into charging_detail_single(summary_id,amount,amount1,charging_item_id,discount,creater,create_time,updater,update_time,charging_item_code) "
										+ "values('"+summary_id+"','"+dto.getItem_amount()+"','"+dto.getPersonal_pay()+"','"+dto.getCharge_item_id()+"','"+dto.getDiscount()+"','"+user.getUserid()+"',"
										+ "'"+DateTimeUtil.getDate3()+"','"+user.getUserid()+"','"+DateTimeUtil.getDate3()+"','"+dto.getCharging_item_code()+"')";
								connection.createStatement().executeUpdate(insersql1);
								
								//修改项目状态
								String sql = "update examinfo_charging_item set pay_status = 'M',isActive = 'N' where id = " + dto.getId();
								connection.createStatement().executeUpdate(sql);
							}
							String selsql3 = "select * from charging_way_single w where w.summary_id = " + rs.getInt("id");
							ResultSet rsway = connection.createStatement().executeQuery(selsql3);
							String charging_way = "";
							if(rsway.next()){
								charging_way = rsway.getString("charging_way");
							}
							String insersql2 = "insert into charging_way_single(summary_id,amount,charging_way,creater,create_time,updater,update_time)"
									+ "values('"+summary_id+"','"+amount+"','"+charging_way+"','"+user.getUserid()+"','"+DateTimeUtil.getDate3()+"','"+user.getUserid()+"','"+DateTimeUtil.getDate3()+"')";
							connection.createStatement().executeUpdate(insersql2);
						}
					}
					
				} else {//一退全退
					for (ReqNo reqno : req) {
						String selsql1 = "select * from charging_summary_single cs where  cs.req_num = '"+ reqno.getREQ_NO()+"'";
						ResultSet rs = connection.createStatement().executeQuery(selsql1);
						if(rs.next()){
							if("Y".equals(rs.getString("is_print_recepit"))){ //已开发票
								String selsql2 = "select * from charging_invoice_single invo where invo.id = "+rs.getInt("invoice_id")+"";
								ResultSet rsinvoice = connection.createStatement().executeQuery(selsql2);
								
								if(rsinvoice.next()){//作废发票
									String upsql1 = "update charging_invoice_single set canceller='"+user.getUserid()+"',cancel_time='"+DateTimeUtil.getDate3()+"' where id = "+ rsinvoice.getInt("id");
									connection.createStatement().executeUpdate(upsql1);
									
									String insersql1 = "insert into card_deal(amount,card_num,trancode,creater,deal_type,deal_date,deal_time,old_amount,invoice_class,exam_num) "
											+ "values('"+rsinvoice.getDouble("invoice_amount")+"','"+rsinvoice.getString("invoice_num")+"','002','"+user.getUserid()+"','1',"
											+ "'"+DateTimeUtil.getDate2()+"','"+DateTimeUtil.getDate3()+"','"+rsinvoice.getDouble("invoice_amount")+"','"+rsinvoice.getDouble("invoice_class")+"','"+exam_num+"')";
									connection.createStatement().executeUpdate(insersql1);
									
									String insersql2 = "insert into nullify_invoice(invoice_num,invoice_status,invoice_class,creater,create_time,daily_status,account_num,center_num) "
											+ "values('"+rsinvoice.getString("invoice_num")+"','N','"+rsinvoice.getDouble("invoice_class")+"','"+user.getUserid()+"','"+DateTimeUtil.getDate3()+"','0',"
											+ "'"+GetNumContral.getInstance().getParamNum("account_num",user.getCenter_num())+"','"+user.getCenter_num()+"')";
									connection.createStatement().executeUpdate(insersql2);
									
									String selsql3 = "select cs.id from charging_summary_single cs where  cs.invoice_id = '"+ rs.getInt("invoice_id")+"'";
									ResultSet rssum = connection.createStatement().executeQuery(selsql3);
									
									if(rssum.next()){
										String upsql2 = "update charging_summary_single set is_print_recepit='N',invoice_id=0 where id="+rssum.getInt("id");
										connection.createStatement().executeUpdate(upsql2);
									}
								}
							}
							String sql1 = "update charging_summary_single set is_active='N' where req_num='" + reqno.getREQ_NO()+ "'";
							connection.createStatement().executeUpdate(sql1);
							
							String insersql = "insert into charging_summary_single(req_num,exam_id,charging_status,amount1,amount2,discount,cashier,cash_date,is_print_recepit,is_active,creater,create_time,updater,update_time,daily_status,exam_num,center_num)"
									+ "values('"+reqno.getREQ_NO()+"','"+examInfo.getId()+"','M','"+rs.getDouble("amount1")+"','"+rs.getDouble("amount2")+"','"+rs.getDouble("discount")+"','"+user.getUserid()+"','"+DateTimeUtil.getDate3()+"','N','Y','"+user.getUserid()+"','"+DateTimeUtil.getDate3()+"',"
									+ "'"+user.getUserid()+"','"+DateTimeUtil.getDate3()+"','0','"+exam_num+"','"+user.getCenter_num()+"')";
							int summary_id = 0;
							connection.createStatement().executeUpdate(insersql);
							ResultSet executeQuery = connection.createStatement().executeQuery("select SCOPE_IDENTITY() as id ");//主键id
							if (executeQuery.next()) {
								summary_id = executeQuery.getInt("id");
							}
							
							String selsql2 = "select * from charging_detail_single c where c.summary_id = " + rs.getInt("id"); 
							ResultSet rsdetail = connection.createStatement().executeQuery(selsql2);
							while(rsdetail.next()){
								String insersql1 = "insert into charging_detail_single(summary_id,amount,amount1,charging_item_id,discount,creater,create_time,updater,update_time,charging_item_code) "
										+ "values('"+summary_id+"','"+rsdetail.getDouble("amount")+"','"+rsdetail.getDouble("amount1")+"','"+rsdetail.getInt("charging_item_id")+"','"+rsdetail.getDouble("discount")+"','"+user.getUserid()+"',"
										+ "'"+DateTimeUtil.getDate3()+"','"+user.getUserid()+"','"+DateTimeUtil.getDate3()+"','"+rsdetail.getString("charging_item_code")+"')";
								connection.createStatement().executeUpdate(insersql1);

								String upsql1 = "update examinfo_charging_item set his_req_status = 'N', pay_status='N' where exam_num = '"+exam_num+"' and charging_item_code = '"+rsdetail.getString("charging_item_code")+"'";
								connection.createStatement().executeUpdate(upsql1);
							}
							
							String selsql3 = "select * from charging_way_single w where w.summary_id = " + rs.getInt("id");
							ResultSet rsway = connection.createStatement().executeQuery(selsql3);
							if(rsway.next()){
								String insersql2 = "insert into charging_way_single(summary_id,amount,charging_way,creater,create_time,updater,update_time)"
										+ "values('"+summary_id+"','"+rsway.getString("amount")+"','"+rsway.getString("charging_way")+"','"+user.getUserid()+"','"+DateTimeUtil.getDate3()+"','"+user.getUserid()+"','"+DateTimeUtil.getDate3()+"')";
								connection.createStatement().executeUpdate(insersql2);
							}
							for (ExaminfoChargingItemDTO dto : itemList) {
								//修改项目状态
								String sql = "update examinfo_charging_item set pay_status = 'M',isActive = 'N' where id = " + dto.getId();
								connection.createStatement().executeUpdate(sql);
							}
//							
//							String sql = " select ec.* from examinfo_charging_item ec ,charging_summary_single cs,charging_detail_single cd where ec.charge_item_id = cd.charging_item_id and cs.exam_id = ec.examinfo_id and cs.id = cd.summary_id and ec.isActive = 'Y' and ec.pay_status <> 'M' and ec.examinfo_id = "
//									+ examInfo.getId() + " and cs.req_num = '" + reqno.getREQ_NO() + "'";
//		
//							List<ExaminfoChargingItem> itemLists = this.jqm.getList(sql, ExaminfoChargingItem.class);
//							for (ExaminfoChargingItem examitem : itemLists) {
//								if ((charingIds != null) && (Arrays.asList(charingIds.split(",")).contains("" + examitem.getCharge_item_id()))) {
//									String sql2 = "update examinfo_charging_item set pay_status='M',isActive = 'N' where id = " + examitem.getId();
//									connection.createStatement().executeUpdate(sql2);
//								} else {
//									String sql2 = "update examinfo_charging_item set his_req_status = 'N', pay_status='N' where id = " + examitem.getId();
//									connection.createStatement().executeUpdate(sql2);
//								}
//							}
						}
					}
				}
				connection.commit();
			} catch (Exception ex) {
				try {
					connection.rollback();

				} catch (SQLException e) {

				}
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
			return "ok-退费申请发送成功！";
		}
		throw new ServiceException(frb.getResultHeader().getText());
	}
}
