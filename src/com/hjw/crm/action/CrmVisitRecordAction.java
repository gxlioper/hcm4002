package com.hjw.crm.action;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.hjw.crm.DTO.CrmVisitPlanDTO;
import com.hjw.crm.domain.CrmVisitRecord;
import com.hjw.crm.model.CrmVisitRecordModel;
import com.hjw.crm.service.CrmVisitPlanService;
import com.hjw.crm.service.CrmVisitRecordService;
import com.hjw.util.DateTimeUtil;
import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.wst.DTO.UserDTO;
import com.hjw.wst.domain.SysLog;
import com.hjw.wst.service.SyslogService;
import com.opensymphony.xwork2.ModelDriven;
import com.synjones.framework.exception.WebException;
import com.synjones.framework.web.action.BaseAction;

public class CrmVisitRecordAction extends BaseAction implements ModelDriven{
	/**   
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)   
	 */ 
	private static final long serialVersionUID = 1L;
	private CrmVisitRecordModel model=new CrmVisitRecordModel();
	private CrmVisitRecordService crmVisitRecordService;
	private int page=1;
	private int rows=15;
    private SyslogService syslogService;
    
    
	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public CrmVisitRecordService getCrmVisitRecordService() {
		return crmVisitRecordService;
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



	public SyslogService getSyslogService() {
		return syslogService;
	}

	public void setSyslogService(SyslogService syslogService) {
		this.syslogService = syslogService;
	}

	public void setModel(CrmVisitRecordModel model) {
		this.model = model;
	}

	@Override
	public Object getModel() {
		return model;
	}
	
	public String getCrmVisitRecordPage()throws  WebException,SQLException{
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
	 * crm101获取回访记录
	     * @Title: getCrmVisitRecordList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String getCrmVisitRecordList() throws  WebException,SQLException{
		UserDTO user = (UserDTO) session.get("username");
		model.setVisit_doctor_id(user.getUserid());
		PageReturnDTO  dto =  this.crmVisitRecordService.getCrmVisitRecordList(model, page, rows);
		this.outJsonResult( dto );
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("crm111");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return NONE;
	}
	/**
	 * 
	     * @Title: crm101getCrmVisitRecordListPage   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String getCrmVisitRecordListPage() throws WebException{
		model.setVisit_num(model.getVisit_num());
		UserDTO user = (UserDTO) session.get("username");
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("crm101");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return SUCCESS;
	}
	
	
	//新增页面
	/**
	 * crm110获取添加回访记录页面
	     * @Title: addCrmVisitRecordPage   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String addCrmVisitRecordPage()throws  WebException{
		UserDTO user = (UserDTO) session.get("username");
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("crm111");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return SUCCESS;
		
	}
	/**
	 * crm112添加回访记录
	     * @Title: saveCrmVisitRecord   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException
	     * @param: @throws ParseException      
	     * @return: String      
	     * @throws
	 */
	public String saveCrmVisitRecord() throws  WebException,SQLException, ParseException{
		UserDTO user = (UserDTO) session.get("username");
		
		CrmVisitRecord c = this.crmVisitRecordService.getCrmVisitRecord(this.model.getCvr_id());
		c.setCustomer_feedback(model.getCustomer_feedback());
		c.setHealth_suggest(model.getHealth_suggest());
		c.setVisit_type(model.getVisit_type());
		c.setActual_doctor_id(user.getUserid());
		c.setActual_date(DateTimeUtil.parse());
		c.setRecord_status("1");
		this.crmVisitRecordService.updateCrmVisitRecord(c);
		
		this.crmVisitRecordService.updateCrimVisitPlanStatus(model.getPlanid());
		this.message="添加记录成功，该计划状态已改变";
		
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("1");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("crm112");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		this.outJsonStrResult( this.message );
		return NONE;
	}
	public String getNewCrmVisitRecordListPage() throws WebException{
		model.setVisit_num(model.getVisit_num());
		if(model.getVisit_date()!=null&&model.getVisit_date().length()>0){
			model.setVisit_date(model.getVisit_date());	
		}
		model.setStartTime(this.model.getPlan_visit_date());
		model.setEndTime(this.model.getPlan_visit_date());
		UserDTO user = (UserDTO) session.get("username");
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("crm101");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return SUCCESS;
	}
}
