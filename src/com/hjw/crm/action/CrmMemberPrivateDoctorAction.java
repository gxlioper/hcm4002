package com.hjw.crm.action;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;

import com.hjw.crm.model.CrmMemberPrivateDoctorModel;
import com.hjw.crm.service.CrmMemberPrivateDoctorService;
import com.hjw.util.DateTimeUtil;
import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.wst.DTO.UserDTO;
import com.hjw.wst.domain.SysLog;
import com.hjw.wst.service.CustomerInfoService;
import com.hjw.wst.service.SyslogService;
import com.opensymphony.xwork2.ModelDriven;
import com.synjones.framework.exception.WebException;
import com.synjones.framework.web.action.BaseAction;

public class CrmMemberPrivateDoctorAction extends BaseAction implements ModelDriven{

	private static final long serialVersionUID = 1L;
	private CrmMemberPrivateDoctorModel model=new CrmMemberPrivateDoctorModel();
	private CrmMemberPrivateDoctorService crmMemberPrivateDoctorService;
	private CustomerInfoService customerInfoService;
	private int page=1;
	private int rows=15;
    private SyslogService syslogService;
    
    
	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	@Override
	public Object getModel() {
		return model;
	}

	public CrmMemberPrivateDoctorService getCrmMemberPrivateDoctorService() {
		return crmMemberPrivateDoctorService;
	}

	public void setCrmMemberPrivateDoctorService(CrmMemberPrivateDoctorService crmMemberPrivateDoctorService) {
		this.crmMemberPrivateDoctorService = crmMemberPrivateDoctorService;
	}


	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
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

	public void setModel(CrmMemberPrivateDoctorModel model) {
		this.model = model;
	}
	/**
	 * 
	     * @Title: crm119给体检者分配私人医生   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String mergeCrmMemberPrivateDoctor()throws  WebException,SQLException{
		UserDTO user = (UserDTO) session.get("username");
		model.setAllot_person(user.getUserid());
		this.message = this.crmMemberPrivateDoctorService.mergeCrmMemberPrivateDoctor(model);
		this.outJsonStrResult( this.message );
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("crm119");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return NONE;
	}
	/**
	 * 
	     * @Title: crm116分配私人医生页面   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException
	     * @param: @throws UnsupportedEncodingException      
	     * @return: String      
	     * @throws
	 */
	public String getMergeCrmMemberPrivateDoctorPage()throws  WebException,SQLException, UnsupportedEncodingException{
		UserDTO user = (UserDTO) session.get("username");
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("crm116");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return SUCCESS;
	}
	//这个action暂时没有用
	public String getCrmMemberPrivateDoctorPage()throws  WebException,SQLException{
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
	 * 
	     * @Title: crm118获取需要分配私人医生的体检者   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String getCrmMemberPrivateDoctorList() throws  WebException,SQLException{
		UserDTO user = (UserDTO) session.get("username");
		this.model.setReport_print_type(this.customerInfoService.getCenterconfigByKey("REPORT_PRINT_TYPE", user.getCenter_num()).getConfig_value().trim());
		this.model.setZyb_report_print_type(this.customerInfoService.getCenterconfigByKey("ZYB_REPORT_PRINT_TYPE", user.getCenter_num()).getConfig_value().trim());
		PageReturnDTO  dto =  this.crmMemberPrivateDoctorService.getCrmMemberPrivateDoctorList(model, page, rows,user);
		this.outJsonResult( dto );
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("crm118");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return NONE;
	}
	/**
	 * 
	     * @Title: crm109   分配私人医生
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String getCrmMemberPrivateDoctorListPage() throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		this.model.setReport_print_type(this.customerInfoService.getCenterconfigByKey("REPORT_PRINT_TYPE", user.getCenter_num()).getConfig_value().trim());
		this.model.setZyb_report_print_type(this.customerInfoService.getCenterconfigByKey("ZYB_REPORT_PRINT_TYPE", user.getCenter_num()).getConfig_value().trim());
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("crm109");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		this.model.setS_join_date(DateTimeUtil.getDate2());
		this.model.setE_join_date(DateTimeUtil.getDate2());
		return SUCCESS;
		
	}

	//私人医生获取自己的体检者列表
	/**
	 * 
	     * @Title: crm114我的客户   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String getCrmDoctorMemberPage()throws  WebException,SQLException{
		UserDTO user = (UserDTO) session.get("username");
		if(this.model.getAllot_date()!=null&&this.model.getAllot_date().length()>0){
			this.model.setAllot_date(this.model.getAllot_date());
		}
		if(this.model.getFlag()!=null&&this.model.getFlag().length()>0){
			this.model.setFlag(this.model.getFlag());
		}
		this.model.setReport_print_type(this.customerInfoService.getCenterconfigByKey("REPORT_PRINT_TYPE", user.getCenter_num()).getConfig_value().trim());
		this.model.setZyb_report_print_type(this.customerInfoService.getCenterconfigByKey("ZYB_REPORT_PRINT_TYPE", user.getCenter_num()).getConfig_value().trim());
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("crm114");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return SUCCESS;
	}
	//查询私人医生的体检者列表
	/**
	 * 
	     * @Title: crm115获取私人医生体检者列表   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String getCrmDoctorMemberList()throws WebException,SQLException{
		UserDTO user = (UserDTO) session.get("username");
		model.setDoctor_id(user.getUserid());
		PageReturnDTO  dto =  this.crmMemberPrivateDoctorService.getCrmDoctorMemberList(model, page, rows);
		this.outJsonResult( dto );
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("crm115");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return NONE;
	}
}
