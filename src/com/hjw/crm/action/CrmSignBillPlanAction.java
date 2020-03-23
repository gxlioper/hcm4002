package com.hjw.crm.action;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.hjw.crm.DTO.CompanyInfoDTO;
import com.hjw.crm.DTO.CrmCompanyContactsDTO;
import com.hjw.crm.DTO.CrmSignBillPlanDTO;
import com.hjw.crm.domain.BatchPlanLog;
import com.hjw.crm.domain.CrmCompanyContacts;
import com.hjw.crm.domain.CrmSignBillPlan;
import com.hjw.crm.model.CrmSignBillPlanModel;
import com.hjw.crm.service.CrmSignBillPlanService;
import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.wst.DTO.UserDTO;
import com.hjw.wst.domain.Batch;
import com.hjw.wst.domain.Exam_user;
import com.hjw.wst.domain.Examinatioin_center;
import com.hjw.wst.domain.SysLog;
import com.hjw.wst.domain.WebUserInfo;
import com.hjw.wst.service.Examinatioin_centerService;
import com.hjw.wst.service.SyslogService;
import com.hjw.wst.service.UserInfoService;
import com.opensymphony.xwork2.ModelDriven;
import com.synjones.framework.exception.ServiceException;
import com.synjones.framework.exception.WebException;
import com.synjones.framework.web.action.BaseAction;

import net.sf.json.JSONArray;

/**
 * 签单计划功能
     * @Title:  火箭蛙体检管理系统   
     * @Package com.hjw.crm.action   
     * @Description:  
     * @author: dangqi     
     * @date:   2017年4月26日 上午10:26:47   
     * @version V2.0.0.0
 */
public class CrmSignBillPlanAction extends BaseAction implements ModelDriven{
	private SyslogService syslogService;
	private static final long serialVersionUID = 1L;

	private CrmSignBillPlanModel model = new CrmSignBillPlanModel();
	private CrmSignBillPlanService crmSignBillPlanService;
	private Examinatioin_centerService examinatioin_centerService;
	private int page=1;
	private int rows=15;
	private UserInfoService userInfoService;
	
	
	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public UserInfoService getUserInfoService() {
		return userInfoService;
	}

	public void setUserInfoService(UserInfoService userInfoService) {
		this.userInfoService = userInfoService;
	}

	public Examinatioin_centerService getExaminatioin_centerService() {
		return examinatioin_centerService;
	}

	public void setExaminatioin_centerService(Examinatioin_centerService examinatioin_centerService) {
		this.examinatioin_centerService = examinatioin_centerService;
	}

	public SyslogService getSyslogService() {
		return syslogService;
	}

	public void setSyslogService(SyslogService syslogService) {
		this.syslogService = syslogService;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public void setModel(CrmSignBillPlanModel model) {
		this.model = model;
	}

	@Override
	public Object getModel() {
		return this.model;
	}

	public CrmSignBillPlanService getCrmSignBillPlanService() {
		return crmSignBillPlanService;
	}

	public void setCrmSignBillPlanService(CrmSignBillPlanService crmSignBillPlanService) {
		this.crmSignBillPlanService = crmSignBillPlanService;
	}

	/**
	 * crm200 签单计划页面
	     * @Title: getCrmSignBillPlanPage   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return      
	     * @return: String      
	     * @throws
	 */
	public String getCrmSignBillPlanPage(){
		Calendar cal = Calendar.getInstance();
		this.model.setSign_year(cal.get(Calendar.YEAR)+"");
		UserDTO user = (UserDTO) session.get("username");
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("crm200");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return SUCCESS;
	}
	
	/**
	 * crm201 获取签单计划列表
	     * @Title: getCrmSignBillPlanList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return      
	     * @return: String      
	     * @throws
	 */
	public String getCrmSignBillPlanList() throws WebException, SQLException {
		UserDTO user = (UserDTO) session.get("username");
		long user_id = user.getUserid();
		if(model.getUserid() != 0){
			user_id = model.getUserid();
		}
		PageReturnDTO pageReturn = this.crmSignBillPlanService.getCrmSignBillPlanList(model,user_id, page, rows);
		this.outJsonResult(pageReturn);
		
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("crm201");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return NONE;
	}
	
	/**
	 * @throws SQLException 
	 * @throws ServiceException 
	 * crm202 申请新增签单计划页面
	     * @Title: getCrmSignBillPlanAddPage   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String getCrmSignBillPlanAddPage() throws WebException, ServiceException, SQLException{
		UserDTO user = (UserDTO) session.get("username");
		String userid=String.valueOf(user.getUserid());
		String str=this.crmSignBillPlanService.checkExamUser(userid);
		if(str.equals("1")){
			Calendar cal = Calendar.getInstance();
			this.model.setSign_year(cal.get(Calendar.YEAR)+"");
		}else{
			this.message="因未分配部门，无权限申请签单计划";
			return "commerror";
		}
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("crm201");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return SUCCESS;
	}
	
	/**
	 * crm203 获取单位联系人列表
	     * @Title: getCompanyContactsList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String getCompanyContactsList() throws WebException, SQLException {
		List<CrmCompanyContactsDTO> list = this.crmSignBillPlanService.getCompanyContactsList(this.model.getCompany_id());
		this.outJsonResult(list);
		return NONE;
	}
	
	/**
	 * crm204 获取联系人编辑页面
	     * @Title: getCompanyContactsEditPage   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String getCompanyContactsEditPage() throws WebException {
		return SUCCESS;
	}
	
	/**
	 * crm205  获取单位信息搜索页面
	     * @Title: getSerchCompanyInfoPage   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String getSerchCompanyInfoPage() throws WebException {
		return SUCCESS;
	}
	
	/**
	 * crm206 根据条件获取单位信息列表
	     * @Title: getSerchCompanyInfoList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String getSerchCompanyInfoList() throws WebException,SQLException{
		List<CompanyInfoDTO> list = this.crmSignBillPlanService.getCompanyInfolist(this.model.getCom_name());
		PageReturnDTO page = new PageReturnDTO();
		page.setRows(list);
		page.setTotal(list.size());
		this.outJsonResult(page);
		return NONE;
	}
	
	/**
	 * @throws ParseException 
	 * @throws ServiceException 
	 * crm207新增保存签单计划
	     * @Title: saveSignBillPlan   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String saveSignBillPlan() throws WebException,SQLException, ServiceException, ParseException{
		UserDTO user = (UserDTO) session.get("username");
		JSONArray contacts = JSONArray.fromObject(this.model.getContacts());
		@SuppressWarnings("unchecked")
		List<CrmCompanyContacts> contactslist = (List<CrmCompanyContacts>) JSONArray.toCollection(contacts,CrmCompanyContacts.class);
		this.model.setContactsList(contactslist);
		this.message = this.crmSignBillPlanService.saveSignBillPlan(this.model,user);
		
		this.outJsonStrResult(this.message);
		return NONE;
	}
	
	/**
	 * crm208 签单计划相信信息查看页面
	     * @Title: getSignBillPlanLookPage   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String getSignBillPlanLookPage() throws WebException,SQLException{
		if(this.model.getFlag()!=null&&model.getFlag().length()>0){
			this.model.setFlag(this.model.getFlag());
		}
		if(this.model.getId()!=null&&this.model.getId().length()>0){
			this.model.setId(this.model.getId());
		}
		CompanyInfoDTO company = this.crmSignBillPlanService.getCompanyInfoById(this.model.getCompany_id());
		if(company != null){
			this.model.setCom_name(company.getCom_name());
			this.model.setCom_type(company.getCom_types());
			this.model.setAddress(company.getAddress());
			this.model.setAreacode(company.getAreacodes());
			this.model.setIndustrycode(company.getIndustrycodes());
			this.model.setEconomiccode(company.getEconomiccodes());
			this.model.setComsizecode(company.getComsizecodes());
		}
		return SUCCESS;
	}
	
	/**
	 * crm209 通过名称检索签单计划信息
	     * @Title: getSignBillPlanByName   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String getSignBillPlanByName() throws WebException,SQLException {
		UserDTO user = (UserDTO) session.get("username");
		long userid=user.getUserid();
		if("all".equals(model.getSign_type())){
			userid=0;
		}
		List<CrmSignBillPlanDTO> list = this.crmSignBillPlanService.getSignBillPlanByName(this.model.getQ(), userid);
		this.outJsonResult(list);
		return NONE;
	}
	
	/**
	 * 
	     * @Title: crm157获取签单计划编码盒名称   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String getCrmSignBill()throws  WebException,SQLException{
		List list=this.crmSignBillPlanService.getCrmSignBill();
		this.outJsonResult( list );
		UserDTO user = (UserDTO) session.get("username");
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("crm157");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return NONE;
	}
	/**
	 * @throws UnsupportedEncodingException 
	 * 
	     * @Title: getCrmSignBillPlanEditCreaterPage   
	     * @Description: crm182签单计划修改负责人页面
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String getCrmSignBillPlanEditCreaterPage()throws  WebException,SQLException, UnsupportedEncodingException{
		UserDTO user = (UserDTO) session.get("username");
		List<Examinatioin_center> parlist=this.examinatioin_centerService.getExaminatioin_centerListByParentId("0");
		List<Exam_user> list=this.examinatioin_centerService.getExam_userById(String.valueOf(user.getUserid()));
		int index=0;
		if(list!=null&&list.size()>0){
			for(Exam_user exam:list){
				List<Examinatioin_center> examList=this.examinatioin_centerService.getExaminatioin_centerList(exam.getExam_center_id());
				if(examList!=null&&examList.size()>0){
					if(examList.get(0).getParent_id()==parlist.get(0).getId()||examList.get(0).getParent_id()==0){
						index=index+1;
					}
				}
			}
		}
		if(index>0){
			model.setId(model.getIds());
			model.setSign_name(new String(this.model.getSign_name().getBytes("ISO8859_1"), "UTF8"));
			model.setCom_name(new String(this.model.getCom_name().getBytes("ISO8859_1"), "UTF8"));
			List<CrmSignBillPlan> crmlist=this.crmSignBillPlanService.getCrmSignBill(model.getId());
			if(crmlist!=null&&crmlist.size()>0){
				Long createrid=crmlist.get(0).getCreater();
				WebUserInfo userinfo=userInfoService.loadUserInfo(createrid);
				model.setCreater(userinfo.getChi_Name());
			}
		}else{
			this.message="您没有权限修改负责人，需要上级部门操作";
			return "commerror";
		}
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("crm139");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return SUCCESS;
	}
	/**
	 * 
	     * @Title: editCrmSignBillPlanEditCreater   
	     * @Description: crm183修改负责人  
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String editCrmSignBillPlanEditCreater()throws  WebException,SQLException{
		UserDTO user = (UserDTO) session.get("username");
		List<CrmSignBillPlan> crm=this.crmSignBillPlanService.getCrmSignBill(model.getId());
		String yi = this.crmSignBillPlanService.editCreater(model.getId(), model.getCreater());
		WebUserInfo userInfo= userInfoService.loadUserInfo(Long.valueOf(model.getCreater()));
		if(yi.equals("1")){
			BatchPlanLog log=new BatchPlanLog();
			log.setCreater(String.valueOf(user.getUserid()));
			log.setCreater_time(new Date());
			log.setProject_id(crm.get(0).getSign_num());
			log.setProject_name(crm.get(0).getSign_name());
			log.setProject_status("该签单计划修改负责人为"+userInfo.getChi_Name());
			log.setProject_type("6");
			log.setType("1");
			this.crmSignBillPlanService.saveBatchPlanLog(log);
			this.message="修改成功";
		}else{
			this.message="修改失败";
		}
		this.outJsonStrResult( this.message );
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("crm139");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return NONE;
	}
	
	/**
	 *crm217 查询用户行政部门列表
	     * @Title: getCrmUserCenterList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String getCrmUserCenterList() throws WebException,SQLException{
		UserDTO user = (UserDTO) session.get("username");
		List<UserDTO> list = this.crmSignBillPlanService.getUserDepList(user.getUserid());
		this.outJsonResult(list);
		return NONE;
	}
	
	/**
	 * 获取日志跟踪列表
	     * @Title: getBatchPlanLogDTOList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String getBatchPlanLogDTOList()throws WebException,SQLException{
		UserDTO user = (UserDTO) session.get("username");
		PageReturnDTO  dto =  this.crmSignBillPlanService.getBatchPlanDTOList(model.getId(),model.getType(), page, rows);
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
	 * 保存日志跟踪
	     * @Title: saveBatchPlanLog   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String saveBatchPlanLog()throws  WebException,SQLException{
		UserDTO user = (UserDTO) session.get("username");
		BatchPlanLog batchPlanLog=new BatchPlanLog();
		batchPlanLog.setProject_id(model.getId());
		batchPlanLog.setProject_status(model.getSign_status());
		batchPlanLog.setProject_reson(model.getProject_reson());
		batchPlanLog.setCreater(String.valueOf(user.getUserid()));
		batchPlanLog.setCreater_time(new Date());
		batchPlanLog.setProject_type(this.model.getProject_type());
		batchPlanLog.setProject_name(this.model.getProject_name());
		batchPlanLog.setType("1");
		String yi = this.crmSignBillPlanService.saveBatchPlanLog(batchPlanLog);
		if(yi.equals("1")){
			this.message="添加成功";
		}else{
			this.message="添加失败";
		}
		this.outJsonStrResult( this.message );
		return NONE;
	}
	/**
	 * 获取日志跟踪页面
	     * @Title: getBatchPlanLogListPage   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String getBatchPlanLogListPage()throws  WebException,SQLException{
		this.model.setType(this.model.getType());
		this.model.setId(this.model.getIds());
		return SUCCESS;
	}
	/**
	 * 获取保存日志跟踪页面
	     * @Title: saveBatchPlanLogPage   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String saveBatchPlanLogPage()throws  WebException,SQLException{
		if(this.model.getProject_type().equals("2")){
			List<CrmSignBillPlan> crm=this.crmSignBillPlanService.getCrmSignBill(this.model.getId());
			this.model.setProject_name(crm.get(0).getSign_name());
			this.model.setProject_type("2");
			this.model.setProject_id(crm.get(0).getSign_num());
		}else if(this.model.getProject_type().equals("5")){
			List<CrmSignBillPlan> crm=this.crmSignBillPlanService.getCrmSignBill(this.model.getId());
			this.model.setProject_name(crm.get(0).getSign_name());
			this.model.setProject_type("5");
			this.model.setProject_id(crm.get(0).getSign_num());
		}
		return SUCCESS;
	}
	/**
	 * 删除签单计划
	     * @Title: deleteSignBillPlan   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String deleteSignBillPlan()throws WebException,SQLException{
		CrmSignBillPlan crmSignBillPlan=new CrmSignBillPlan();
		crmSignBillPlan=this.crmSignBillPlanService.getSignBillPlanByNum(model.getSign_num());
		if(crmSignBillPlan!=null){
			if(crmSignBillPlan.getSign_status().equals("1")){
				String str=this.crmSignBillPlanService.deleteCrmSignBillPlan(crmSignBillPlan);
				if(str.equals("1")){
					this.message="删除成功";
				}else{
					this.message="删除失败";
				}
			}else if(crmSignBillPlan.getSign_status().equals("2")){
				this.message="删除失败，该签单计划上级部门判断为撞单";
			}else if(crmSignBillPlan.getSign_status().equals("3")){
				this.message="删除失败，该签单计划已形成正式稿";
			}
		}
		this.outJsonStrResult( this.message );
		return NONE;
	}
	public String countSignBillPlanPage()throws WebException,SQLException{
		UserDTO user = (UserDTO) session.get("username");
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("crm154");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return SUCCESS;
	}
	public String countSignBillPlanDetailPage()throws WebException,SQLException{
		UserDTO user = (UserDTO) session.get("username");
		this.model.setCreater(this.model.getCreater());
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("crm154");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return SUCCESS;
	}
	public String tuSignBillPlanDetailPage()throws WebException,SQLException{
		UserDTO user = (UserDTO) session.get("username");
		this.model.setCreater(this.model.getCreater());
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("crm154");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return SUCCESS;
	}
	public String countSignBillPLanList()throws WebException,SQLException{
		UserDTO user = (UserDTO) session.get("username");
		PageReturnDTO  dto =  this.crmSignBillPlanService.getCountSignBillPlanList(model,user.getUserid(), page, rows);
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
	public String countSignBillPLanDetailList()throws WebException,SQLException{
		UserDTO user = (UserDTO) session.get("username");
		PageReturnDTO  dto =  this.crmSignBillPlanService.countSignBillPLanDetailList(model,user.getUserid(), page, rows);
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
	public String tuSignBillPLanList()throws WebException,SQLException{
		UserDTO user = (UserDTO) session.get("username");
		List<CrmSignBillPlanDTO>  dto =  this.crmSignBillPlanService.tuSignBillPLanList(model, page, rows);
		if(dto!=null&&dto.size()>0){
			this.outJsonResult( dto );	
		}
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
}
