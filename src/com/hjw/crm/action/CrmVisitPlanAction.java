package com.hjw.crm.action;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.hjw.crm.domain.CrmMemberPrivateDoctor;
import com.hjw.crm.domain.CrmVisitPlan;
import com.hjw.crm.domain.CrmVisitRecord;
import com.hjw.crm.model.CrmVisitPlanModel;
import com.hjw.crm.service.CrmMemberPrivateDoctorService;
import com.hjw.crm.service.CrmVisitPlanService;
import com.hjw.crm.service.CrmVisitRecordService;
import com.hjw.util.DateTimeUtil;
import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.wst.DTO.UserDTO;
import com.hjw.wst.config.GetNumContral;
import com.hjw.wst.domain.ExamInfo;
import com.hjw.wst.domain.SysLog;
import com.hjw.wst.service.CustomerInfoService;
import com.hjw.wst.service.SyslogService;
import com.opensymphony.util.DateUtil;
import com.opensymphony.xwork2.ModelDriven;
import com.synjones.framework.exception.ServiceException;
import com.synjones.framework.exception.WebException;
import com.synjones.framework.web.action.BaseAction;

public class CrmVisitPlanAction extends BaseAction implements ModelDriven{
	/**   
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)   
	 */ 
	private static final long serialVersionUID = 1L;
	private CrmVisitPlanModel model=new CrmVisitPlanModel();
	private CrmVisitPlanService crmVisitPlanService;
	private CrmMemberPrivateDoctorService crmMemberPrivateDoctorService;
	private CustomerInfoService customerInfoService;
	private CrmVisitRecordService crmVisitRecordService;
	private int page=1;
	private int rows=15;
    private SyslogService syslogService;


	public CrmMemberPrivateDoctorService getCrmMemberPrivateDoctorService() {
		return crmMemberPrivateDoctorService;
	}

	public void setCrmMemberPrivateDoctorService(CrmMemberPrivateDoctorService crmMemberPrivateDoctorService) {
		this.crmMemberPrivateDoctorService = crmMemberPrivateDoctorService;
	}

	public CrmVisitPlanService getCrmVisitPlanService() {
		return crmVisitPlanService;
	}

	public void setCrmVisitPlanService(CrmVisitPlanService crmVisitPlanService) {
		this.crmVisitPlanService = crmVisitPlanService;
	}

	public void setCrmVisitRecordService(CrmVisitRecordService crmVisitRecordService) {
		this.crmVisitRecordService = crmVisitRecordService;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public void setModel(CrmVisitPlanModel model) {
		this.model = model;
	}

	@Override
	public Object getModel() {
		return model;
	}

	public SyslogService getSyslogService() {
		return syslogService;
	}

	public void setSyslogService(SyslogService syslogService) {
		this.syslogService = syslogService;
	}
	
	public void setCustomerInfoService(CustomerInfoService customerInfoService) {
		this.customerInfoService = customerInfoService;
	}

	/**
	 * 
	     * @Title: crm108获取医生姓名   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String getCrmVisitPlanPerson()throws  WebException,SQLException{
		List list=this.crmVisitPlanService.getCrmVisitPlanUserList();
		this.outJsonResult( list );
		UserDTO user = (UserDTO) session.get("username");
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("crm108");//子功能id 必须填写
		sl.setExplain("");//操作说明
		return NONE;
	}
	/**
	 * 
	     * @Title: getCrmVisitPlanPage   暂时不用
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String getCrmVisitPlanPage()throws  WebException,SQLException{
		UserDTO user = (UserDTO) session.get("username");
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("crm001");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return SUCCESS;
	}
	/**
	 * crm154获取全部健康计划
	     * @Title: getCrmAllVisitPlanList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String getCrmAllVisitPlanList() throws  WebException,SQLException{
		UserDTO user = (UserDTO) session.get("username");
		model.setCreater(user.getUserid());
		PageReturnDTO  dto =  this.crmVisitPlanService.getCrmAllVisitPlanList(model, page, rows,user);
		this.outJsonResult( dto );
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("crm154");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return NONE;
	}
	/**
	 * 获取健康计划列表crm104
	     * @Title: getCrmVisitPlanList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String getCrmVisitPlanList() throws  WebException,SQLException{
		UserDTO user = (UserDTO) session.get("username");
		model.setCreater(user.getUserid());
		PageReturnDTO  dto =  this.crmVisitPlanService.getCrmVisitPlanList(model, page, rows);
		this.outJsonResult( dto );
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("crm104");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return NONE;
	}
	/**
	 * @throws SQLException 
	 * @throws ServiceException 
	 * crm100健康计划表
	     * @Title: getCrmVisitPlanListPage   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String getCrmVisitPlanListPage() throws WebException, ServiceException, SQLException{
		UserDTO user = (UserDTO) session.get("username");
		model.setFlag("1");
		if(this.model.getPlan_visit_date()!=null&&this.model.getPlan_visit_date().length()>0){
			model.setPlan_visit_date(this.model.getPlan_visit_date());	
		}
		if(this.model.getVisit_important()!=null&&this.model.getVisit_important().length()>0){
			String visit=this.model.getVisit_important();
			String visit_important="";
			if(visit.equals("1")){
				 visit_important=this.crmVisitPlanService.getDataDict("普通");
			}else if(visit.equals("2")){
				 visit_important=this.crmVisitPlanService.getDataDict("一般");
			}else if(visit.equals("3")){
				visit_important=this.crmVisitPlanService.getDataDict("重要");
			}
			model.setVisit_important(visit_important);	
		}
		if(this.model.getFujianflag()!=null&&this.model.getFujianflag().length()>0){
			model.setFujianflag(this.model.getFujianflag());
		}
		if(this.model.getVisit_status()!=null&&this.model.getVisit_status().length()>0){
			model.setVisit_status(model.getVisit_status());
		}
		this.model.setReport_print_type(this.customerInfoService.getCenterconfigByKey("REPORT_PRINT_TYPE", user.getCenter_num()).getConfig_value().trim());
		this.model.setZyb_report_print_type(this.customerInfoService.getCenterconfigByKey("ZYB_REPORT_PRINT_TYPE", user.getCenter_num()).getConfig_value().trim());
		this.model.setStartTime(this.model.getPlan_visit_date());
		this.model.setEndTime(this.model.getPlan_visit_date());
		this.model.setUser_id(user.getUserid());
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("crm100");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return SUCCESS;	
	}
	
	/**
	 * crm132健康回访总记录
	     * @Title: getCrmAllVisitPlanListPage   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String getCrmAllVisitPlanListPage()throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		this.model.setReport_print_type(this.customerInfoService.getCenterconfigByKey("REPORT_PRINT_TYPE", user.getCenter_num()).getConfig_value().trim());
		this.model.setZyb_report_print_type(this.customerInfoService.getCenterconfigByKey("ZYB_REPORT_PRINT_TYPE", user.getCenter_num()).getConfig_value().trim());
		model.setFlag("0");
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("crm132");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return SUCCESS;	
	}
	/**
	 * crm121获取健康计划表和我的客户页面
	     * @Title: getCrmVisitPlanIndexPage   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String getCrmVisitPlanIndexPage() throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("crm121");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return SUCCESS;	
	}
	//删除
	/**
	 * crm106删除健康计划
	     * @Title: deleteCrmVisitPlan   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String deleteCrmVisitPlan() throws  WebException,SQLException{
		String yi=this.crmVisitPlanService.deleteCrmVisitPlan(model.getIds());
		if(yi=="1"){
			this.outJsonStrResult(this.message="删除成功");	
		}else{
			this.outJsonStrResult(this.message="删除失败");
		}
		UserDTO user = (UserDTO) session.get("username");
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("3");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("crm106");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return NONE;
	}
	//结束回访
	/**
	 * crm107更新结束健康计划状态
	     * @Title: updateCrmVisitPlanEndStatus   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
		public String updateCrmVisitPlanEndStatus() throws  WebException,SQLException{
			this.message = this.crmVisitPlanService.updateCrmVisitPlanEndStatus(model.getIds(),model.getVisit_status());
			this.outJsonStrResult(this.message);
			
			UserDTO user = (UserDTO) session.get("username");
			SysLog sl =  new SysLog();
			sl.setCenter_num(user.getCenter_num());
			sl.setUserid(user.getUserid()+"");
			sl.setOper_type("3");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
			sl.setXtgnid("");//可不填写
			sl.setXtgnid2("crm107");//子功能id 必须填写
			sl.setExplain("");//操作说明
			syslogService.saveSysLog(sl);
			return NONE;
		}
	//更新
		/**
		 * crm103获取更新健康计划页面
		     * @Title: getUpdateCrmVisitPlanPage   
		     * @Description: TODO(这里用一句话描述这个方法的作用)   
		     * @param: @return
		     * @param: @throws WebException
		     * @param: @throws SQLException      
		     * @return: String      
		     * @throws
		 */
	public String getUpdateCrmVisitPlanPage() throws  WebException,SQLException{
		CrmVisitPlan crm = this.crmVisitPlanService.getCrmVisitPlan(model.getId());
		CrmVisitRecord crmVisitRecord = this.crmVisitRecordService.getCrmVisitRecord(this.model.getCvr_id());
		if(crmVisitRecord != null){
			model.setVisit_content(crmVisitRecord.getVisit_notices());
			model.setPlan_doctor_id(crmVisitRecord.getVisit_doctor_id());
			model.setPlan_visit_date(DateTimeUtil.shortFmt2(crmVisitRecord.getVisit_date()));
			model.setCvr_id(crmVisitRecord.getId());
		}
		model.setId(crm.getId());
		model.setArch_num(crm.getArch_num());
		model.setExam_num(crm.getExam_num());
		model.setVisit_important(crm.getVisit_important());
		model.setVisit_num(crm.getVisit_num());
		model.setVisit_status(crm.getVisit_status());
		model.setCreater(crm.getCreater());
		model.setCreate_time(crm.getCreate_time());
		model.setFujianflag(crm.getFujianflag());
		
		UserDTO user = (UserDTO) session.get("username");
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("3");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("crm103");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return SUCCESS;
	}
	//新增页面
	/**
	 * crm102获取添加健康计划页面
	     * @Title: addCrmVisitPlanPage   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String addCrmVisitPlanPage()throws  WebException{
		UserDTO user = (UserDTO) session.get("username");
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("3");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("crm102");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return SUCCESS;
	}
	
	/**
	 * crm105保存健康计划
	     * @Title: saveCrmVisitPlan   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException
	     * @param: @throws ParseException      
	     * @return: String      
	     * @throws
	 */
	public String saveCrmVisitPlan() throws  WebException,SQLException, ParseException{
		UserDTO user = (UserDTO) session.get("username");
		if ( model.getId()!=null && !"".equals(model.getId()) && model.getCvr_id()!=null && !"".equals(model.getCvr_id())){
			CrmVisitPlan z = this.crmVisitPlanService.getCrmVisitPlan(model.getId());
			CrmVisitRecord crmVisitRecord = this.crmVisitRecordService.getCrmVisitRecord(model.getCvr_id());
//			z.setArch_num(model.getArch_num());
//			z.setPlan_doctor_id(user.getUserid());
//			z.setPlan_visit_date(DateTimeUtil.parse2(model.getPlan_visit_date()));
//			z.setVisit_content(model.getVisit_content());
//			z.setVisit_num(model.getVisit_num());
//			z.setVisit_status(model.getVisit_status());
//			z.setExam_num(model.getExam_num());
//			z.setCreater(user.getUserid());
//			z.setCreate_time(DateTimeUtil.parse());
			crmVisitRecord.setVisit_date(DateTimeUtil.parse2(this.model.getPlan_visit_date()));
			crmVisitRecord.setVisit_notices(this.model.getVisit_content());
			z.setVisit_important(model.getVisit_important());
			z.setFujianflag(model.getFujianflag());
			this.crmVisitPlanService.updateCrmVisitPlan(z);
			this.crmVisitRecordService.updateCrmVisitRecord(crmVisitRecord);
			this.message = "修改成功";
			SysLog sl =  new SysLog();
			sl.setCenter_num(user.getCenter_num());
			sl.setUserid(user.getUserid()+"");
			sl.setOper_type("2");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
			sl.setXtgnid("");//可不填写
			sl.setXtgnid2("crm105");//子功能id 必须填写
			sl.setExplain("");//操作说明
			syslogService.saveSysLog(sl);
		} else {
			long doctor_id = user.getUserid();
			if(model.getPlan_doctor_id() > 0){
				doctor_id = model.getPlan_doctor_id();
			}
			CrmVisitPlan c = new CrmVisitPlan();
			c.setArch_num(model.getArch_num());
			c.setPlan_doctor_id(doctor_id);
			c.setPlan_visit_date(DateTimeUtil.parse2(model.getPlan_visit_date()));
			c.setVisit_content(model.getVisit_content());
			c.setVisit_num(GetNumContral.getInstance().getParamNum("visit_num", user.getCenter_num()));
			c.setVisit_status("1");
			c.setExam_num(model.getExam_num());
			c.setCreater(user.getUserid());
			c.setCreate_time(DateTimeUtil.parse());
			c.setVisit_important(model.getVisit_important());
			c.setFujianflag(model.getFujianflag());
			this.crmVisitPlanService.addCrmVisitPlan(c);
			CrmMemberPrivateDoctor crm=this.crmMemberPrivateDoctorService.getCrmMemberPrivateDoctor(model.getExam_num(), model.getArch_num(), doctor_id+"");
			if(crm!=null){
				crm.setFlag("1");
				this.crmMemberPrivateDoctorService.updateCrmMemberPrivateDoctor(crm);
			}else{
				CrmMemberPrivateDoctor crmnew = new CrmMemberPrivateDoctor();
				crmnew.setArch_num(model.getArch_num());
				crmnew.setExam_num(model.getExam_num());
				crmnew.setDoctor_id(doctor_id);
				crmnew.setAllot_person(user.getUserid());
				crmnew.setAllot_date(new Date());
				crmnew.setFlag("1");
				this.crmMemberPrivateDoctorService.saveCrmMemberPrivateDoctor(crmnew);
			}
			this.message = "添加成功";
			SysLog sl =  new SysLog();
			sl.setCenter_num(user.getCenter_num());
			sl.setUserid(user.getUserid()+"");
			sl.setOper_type("1");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
			sl.setXtgnid("");//可不填写
			sl.setXtgnid2("crm105");//子功能id 必须填写
			sl.setExplain("");//操作说明
			syslogService.saveSysLog(sl);
		}
		
		this.outJsonStrResult( this.message );
		return NONE;
	}
	
	/**
	 *总检健康计划批量增加 （东北国际1次增加四个计划）
	     * @Title: saveCrmVisitPlanList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException
	     * @param: @throws ParseException      
	     * @return: String      
	     * @throws
	 */
	public String saveCrmVisitPlanList() throws  WebException,SQLException, ParseException{
		UserDTO user = (UserDTO) session.get("username");
			
		CrmVisitPlan c = new CrmVisitPlan();
		c.setArch_num(model.getArch_num());
		c.setPlan_doctor_id(model.getPlan_doctor_id1());
		c.setPlan_visit_date(DateTimeUtil.parse2(model.getPlan_visit_date1()));
		c.setVisit_content(model.getVisit_content());
		c.setVisit_num(GetNumContral.getInstance().getParamNum("visit_num", user.getCenter_num()));
		c.setVisit_status(model.getVisit_status());
		c.setExam_num(model.getExam_num());
		c.setCreater(user.getUserid());
		c.setCreate_time(DateTimeUtil.parse());
		c.setVisit_important(model.getVisit_important());
		c.setFujianflag(model.getFujianflag());
		this.crmVisitPlanService.addCrmVisitPlan(c);
		
		if(!"".equals(model.getPlan_visit_date2())){
			c = new CrmVisitPlan();
			c.setArch_num(model.getArch_num());
			c.setPlan_doctor_id(model.getPlan_doctor_id2());
			c.setPlan_visit_date(DateTimeUtil.parse2(model.getPlan_visit_date2()));
			c.setVisit_content(model.getVisit_content());
			c.setVisit_num(GetNumContral.getInstance().getParamNum("visit_num", user.getCenter_num()));
			c.setVisit_status(model.getVisit_status());
			c.setExam_num(model.getExam_num());
			c.setCreater(user.getUserid());
			c.setCreate_time(DateTimeUtil.parse());
			c.setVisit_important(model.getVisit_important());
			c.setFujianflag(model.getFujianflag());
			this.crmVisitPlanService.addCrmVisitPlan(c);
		}
		if(!"".equals(model.getPlan_visit_date3())){
			c = new CrmVisitPlan();
			c.setArch_num(model.getArch_num());
			c.setPlan_doctor_id(model.getPlan_doctor_id3());
			c.setPlan_visit_date(DateTimeUtil.parse2(model.getPlan_visit_date3()));
			c.setVisit_content(model.getVisit_content());
			c.setVisit_num(GetNumContral.getInstance().getParamNum("visit_num", user.getCenter_num()));
			c.setVisit_status(model.getVisit_status());
			c.setExam_num(model.getExam_num());
			c.setCreater(user.getUserid());
			c.setCreate_time(DateTimeUtil.parse());
			c.setVisit_important(model.getVisit_important());
			c.setFujianflag(model.getFujianflag());
			this.crmVisitPlanService.addCrmVisitPlan(c);
		}
		if(!"".equals(model.getPlan_visit_date4())){
			c = new CrmVisitPlan();
			c.setArch_num(model.getArch_num());
			c.setPlan_doctor_id(model.getPlan_doctor_id4());
			c.setPlan_visit_date(DateTimeUtil.parse2(model.getPlan_visit_date4()));
			c.setVisit_content(model.getVisit_content());
			c.setVisit_num(GetNumContral.getInstance().getParamNum("visit_num", user.getCenter_num()));
			c.setVisit_status(model.getVisit_status());
			c.setExam_num(model.getExam_num());
			c.setCreater(user.getUserid());
			c.setCreate_time(DateTimeUtil.parse());
			c.setVisit_important(model.getVisit_important());
			c.setFujianflag(model.getFujianflag());
			this.crmVisitPlanService.addCrmVisitPlan(c);
		}
		CrmMemberPrivateDoctor crm=this.crmMemberPrivateDoctorService.getCrmMemberPrivateDoctor(model.getExam_num(), model.getArch_num(), user.getUserid()+"");
		if(crm!=null){
			crm.setFlag("1");
			this.crmMemberPrivateDoctorService.updateCrmMemberPrivateDoctor(crm);
		}else{
			CrmMemberPrivateDoctor crmnew = new CrmMemberPrivateDoctor();
			crmnew.setArch_num(model.getArch_num());
			crmnew.setExam_num(model.getExam_num());
			crmnew.setDoctor_id(user.getUserid());
			crmnew.setAllot_person(user.getUserid());
			crmnew.setAllot_date(new Date());
			crmnew.setFlag("1");
			this.crmMemberPrivateDoctorService.saveCrmMemberPrivateDoctor(crmnew);
		}
		this.message = "添加成功";
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("1");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("crm105");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		
		this.outJsonStrResult( this.message );
		return NONE;
	}
	
	//获取新建健康计划页面
	/**
	 * @throws UnsupportedEncodingException 
	 * crm117从私人医生处建立健康计划
	     * @Title: getCrmVisitPlanDoctorPage   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String getCrmVisitPlanDoctorPage()throws Exception,SQLException{
		UserDTO user = (UserDTO) session.get("username");
		this.model.setFlag(model.getFlag());
		this.model.setDoctorName(user.getName());
		this.model.setDoctor_id(user.getUserid());
		this.model.setPersionName(new String(this.model.getPersionName()));
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("3");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("crm117");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		String isdjttype = this.customerInfoService.getCenterconfigByKey("IS_DJT_TYPE", user.getCenter_num()).getConfig_value().trim();
		if("3".equals(model.getFlag()) && "DBGJ".equals(isdjttype)){
			return "success_dbgj";
		}else if("4".equals(model.getFlag())){//按照策略回访
			return "success_new";
		}else{
			return SUCCESS;
		}
	}
	
	/**
	 * 获取健康计划首页
	     * @Title: getNewVisitPlanPage   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String getNewVisitPlanPage()throws WebException,SQLException{
		UserDTO user = (UserDTO) session.get("username");//今日任务列表
		String jinrirenwucount=this.crmVisitPlanService.getCrmVisitPlanCount(String.valueOf(user.getUserid()),DateTimeUtil.shortFmt3(new Date()));
		this.model.setJinrirenwucount(jinrirenwucount);
		String putong =this.crmVisitPlanService.getDataDict("普通");
		String putongrenwucount=this.crmVisitPlanService.getCrmVisitPlanCount(String.valueOf(user.getUserid()),DateTimeUtil.shortFmt3(new Date()),putong);
		this.model.setPutongrenwucount(putongrenwucount);
		String yiban =this.crmVisitPlanService.getDataDict("一般");
		String yibanrenwucount=this.crmVisitPlanService.getCrmVisitPlanCount(String.valueOf(user.getUserid()),DateTimeUtil.shortFmt3(new Date()),yiban);
		this.model.setYibanrenwucount(yibanrenwucount);
		String zhongyao=this.crmVisitPlanService.getDataDict("重要");
		String zhongyaorenwucount=this.crmVisitPlanService.getCrmVisitPlanCount(String.valueOf(user.getUserid()),DateTimeUtil.shortFmt3(new Date()),zhongyao);
		this.model.setZhongyaorenwucount(zhongyaorenwucount);//重要任务
//		String dafuzixun=this.crmVisitPlanService.getCrmVisitPlanCount(String.valueOf(user.getUserid()),2);
		String dafuzixun=this.crmVisitPlanService.getCrmVisitRecordCountByDate(user.getUserid(), DateTimeUtil.shortFmt3(new Date()));
		this.model.setDafuzixun(dafuzixun);//答复咨询
		String huifanggenzong=this.crmVisitPlanService.getCrmVisitRecordCount(String.valueOf(user.getUserid()),DateTimeUtil.shortFmt3(new Date()));
		this.model.setHuifanggenzong(huifanggenzong);//回访跟踪
		String kehucount=this.crmVisitPlanService.getKeHuCount(String.valueOf(user.getUserid()),DateTimeUtil.shortFmt3(new Date()));
		this.model.setKehucount(kehucount);//客户
		String yidafukehucount=this.crmVisitPlanService.getKeHuCount(String.valueOf(user.getUserid()),DateTimeUtil.shortFmt3(new Date()),"1");
		this.model.setYidafukehucount(yidafukehucount);//已答复客户
		String weidafukehucount=this.crmVisitPlanService.getKeHuCount(String.valueOf(user.getUserid()),DateTimeUtil.shortFmt3(new Date()),"0");
		this.model.setWeidafukehucount(weidafukehucount);// 未答复客户
		String rewuzongshu=this.crmVisitPlanService.getCrmVisitPlanCount(String.valueOf(user.getUserid()), "");
		this.model.setRewuzongshu(rewuzongshu);//任务条数
		String kehuzongshu=this.crmVisitPlanService.getKeHuCount(String.valueOf(user.getUserid()), "");
		this.model.setKehuzongshu(kehuzongshu);//客户条数
		String putongrenwuzongshu=this.crmVisitPlanService.getCrmVisitPlanCount(String.valueOf(user.getUserid()),"",putong);
		this.model.setPutongrenwuzongshu(putongrenwuzongshu);
		String yibanrenwuzongshu=this.crmVisitPlanService.getCrmVisitPlanCount(String.valueOf(user.getUserid()),"",yiban);
		this.model.setYibanrenwuzongshu(yibanrenwuzongshu);
		String zhongyaorenwuzongshu=this.crmVisitPlanService.getCrmVisitPlanCount(String.valueOf(user.getUserid()),"",zhongyao);
		this.model.setZhongyaorenwuzongshu(zhongyaorenwuzongshu);
		String yidafukehuzongshu=this.crmVisitPlanService.getKeHuCount(String.valueOf(user.getUserid()),"","1");
		this.model.setYidafukehuzongshu(yidafukehuzongshu);
		String weidafukehuzongshu=this.crmVisitPlanService.getKeHuCount(String.valueOf(user.getUserid()),"","0");
		this.model.setWeidafukehuzongshu(weidafukehuzongshu);
		String shifangjilu=this.crmVisitPlanService.getLostCrm(String.valueOf(user.getUserid()),DateTimeUtil.shortFmt3(new Date()));
		this.model.setShifangjilu(shifangjilu);
		String fujinflag=this.crmVisitPlanService.getCrmVisitPlanFujianCount(String.valueOf(user.getUserid()),DateTimeUtil.shortFmt3(new Date()));
		this.model.setFujianflag(fujinflag);
		this.model.setPlan_visit_date(DateTimeUtil.shortFmt3(new Date()));
		String shengrikehushu = this.crmVisitPlanService.getShengrikehuCount(DateTimeUtil.getDate2());
		this.model.setShengrikehushu(shengrikehushu);
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("3");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("crm117");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return SUCCESS;
	}
	
	public String getNewVisitPlanPageResult()throws WebException,SQLException{
		UserDTO user = (UserDTO) session.get("username");
		this.model.setExam_num(this.model.getExam_num());
		this.model.setArch_num(this.model.getArch_num());
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("3");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("crm117");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return SUCCESS;
	}
	public String getIdByExamInfoExamNum()throws WebException,SQLException{
		UserDTO user = (UserDTO) session.get("username");
		ExamInfo id=this.crmVisitPlanService.getIdByExamInfoExamNum(this.model.getExam_num());
		this.outJsonResult(id);
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("3");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("crm117");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return NONE;
	}
	public String getExamInfoByArchNum()throws WebException,SQLException{
		UserDTO user = (UserDTO) session.get("username");
		List<ExamInfo> list=this.crmVisitPlanService.getExamInfoByArchNum(this.model.getArch_num());
		this.outJsonResult(list);
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("3");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("crm117");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return NONE;
	}
	public String getExamInfoByArchNumAndJoinDate()throws WebException,SQLException{
		UserDTO user = (UserDTO) session.get("username");
		ExamInfo list=this.crmVisitPlanService.getExamInfoByArchNumAndJoinDate(this.model.getArch_num(),this.model.getJoin_date());
		this.outJsonResult(list);
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("3");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("crm117");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return NONE;
	}
	public String getNewCrmCount()throws WebException,SQLException{
		String putong =this.crmVisitPlanService.getDataDict("普通");
		String yiban =this.crmVisitPlanService.getDataDict("一般");
		String zhongyao=this.crmVisitPlanService.getDataDict("重要");
		UserDTO user = (UserDTO) session.get("username");
		String jinrirenwucount=this.crmVisitPlanService.getCrmVisitPlanCount(String.valueOf(user.getUserid()),this.model.getPlan_visit_date());
		this.model.setJinrirenwucount(jinrirenwucount);
		String putongrenwucount=this.crmVisitPlanService.getCrmVisitPlanCount(String.valueOf(user.getUserid()),this.model.getPlan_visit_date(),putong);
		this.model.setPutongrenwucount(putongrenwucount);
		String yibanrenwucount=this.crmVisitPlanService.getCrmVisitPlanCount(String.valueOf(user.getUserid()),this.model.getPlan_visit_date(),yiban);
		this.model.setYibanrenwucount(yibanrenwucount);
		String zhongyaorenwucount=this.crmVisitPlanService.getCrmVisitPlanCount(String.valueOf(user.getUserid()),this.model.getPlan_visit_date(),zhongyao);
		this.model.setZhongyaorenwucount(zhongyaorenwucount);
//		String dafuzixun=this.crmVisitPlanService.getCrmVisitPlanCount(String.valueOf(user.getUserid()),2);
		String dafuzixun=this.crmVisitPlanService.getCrmVisitRecordCountByDate(user.getUserid(), this.model.getPlan_visit_date());
		this.model.setDafuzixun(dafuzixun);
		String huifanggenzong=this.crmVisitPlanService.getCrmVisitRecordCount(String.valueOf(user.getUserid()),this.model.getPlan_visit_date());
		this.model.setHuifanggenzong(huifanggenzong);
		String kehucount=this.crmVisitPlanService.getKeHuCount(String.valueOf(user.getUserid()),this.model.getPlan_visit_date());
		this.model.setKehucount(kehucount);
		String yidafukehucount=this.crmVisitPlanService.getKeHuCount(String.valueOf(user.getUserid()),this.model.getPlan_visit_date(),"1");
		this.model.setYidafukehucount(yidafukehucount);
		String weidafukehucount=this.crmVisitPlanService.getKeHuCount(String.valueOf(user.getUserid()),this.model.getPlan_visit_date(),"0");
		this.model.setWeidafukehucount(weidafukehucount);
		String rewuzongshu=this.crmVisitPlanService.getCrmVisitPlanCount(String.valueOf(user.getUserid()), "");
		this.model.setRewuzongshu(rewuzongshu);
		String kehuzongshu=this.crmVisitPlanService.getKeHuCount(String.valueOf(user.getUserid()), "");
		this.model.setKehuzongshu(kehuzongshu);
		String putongrenwuzongshu=this.crmVisitPlanService.getCrmVisitPlanCount(String.valueOf(user.getUserid()),"",putong);
		this.model.setPutongrenwuzongshu(putongrenwuzongshu);
		String yibanrenwuzongshu=this.crmVisitPlanService.getCrmVisitPlanCount(String.valueOf(user.getUserid()),"",yiban);
		this.model.setYibanrenwuzongshu(yibanrenwuzongshu);
		String zhongyaorenwuzongshu=this.crmVisitPlanService.getCrmVisitPlanCount(String.valueOf(user.getUserid()),"",zhongyao);
		this.model.setZhongyaorenwuzongshu(zhongyaorenwuzongshu);
		String yidafukehuzongshu=this.crmVisitPlanService.getKeHuCount(String.valueOf(user.getUserid()),"","1");
		this.model.setYidafukehuzongshu(yidafukehuzongshu);
		String weidafukehuzongshu=this.crmVisitPlanService.getKeHuCount(String.valueOf(user.getUserid()),"","0");
		this.model.setWeidafukehuzongshu(weidafukehuzongshu);
		String shifangjilu=this.crmVisitPlanService.getLostCrm(String.valueOf(user.getUserid()),this.model.getPlan_visit_date());
		this.model.setShifangjilu(shifangjilu);
		String fujinflag=this.crmVisitPlanService.getCrmVisitPlanFujianCount(String.valueOf(user.getUserid()),this.model.getPlan_visit_date());
		this.model.setFujianflag(fujinflag);
		String shengrikehushu = this.crmVisitPlanService.getShengrikehuCount(this.model.getPlan_visit_date());
		this.model.setShengrikehushu(shengrikehushu);
		this.outJsonResult(model);
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("3");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("crm117");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return NONE;
	}
	// crm310 健康回访计划策略维护
	public String  getCrmPlanTactics(){
		return SUCCESS;
	}
	
	//crm 生日客户页面
	public String getShengrikehuPage()throws WebException{
		this.model.setPlan_visit_date(this.model.getPlan_visit_date());
		return SUCCESS;
	}
	
	/**
	 * 
	     * @Title: getShengrikehuList   
	     * @Description: 获取生日客户   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws ServiceException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String getShengrikehuList()throws WebException, ServiceException, SQLException{
		
		PageReturnDTO dto = this.crmVisitPlanService.getShengrikehuList(model, page, rows);
		this.outJsonResult(dto);
		return NONE;
	}
	
}
