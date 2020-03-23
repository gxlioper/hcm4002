package com.hjw.zyb.action;

import java.sql.SQLException;

import com.hjw.wst.DTO.ExamInfoUserDTO;
import com.hjw.wst.DTO.UserDTO;
import com.hjw.wst.domain.ExamdepResult;
import com.hjw.wst.domain.SysLog;
import com.hjw.wst.model.DepInspectModel;
import com.hjw.wst.service.CustomerInfoService;
import com.hjw.wst.service.DepInspectService;
import com.hjw.wst.service.SyslogService;
import com.hjw.zyb.DTO.ExamInfoExtDTO;
import com.hjw.zyb.service.ZybCustomerInfoService;
import com.opensymphony.xwork2.ModelDriven;
import com.synjones.framework.exception.WebException;
import com.synjones.framework.web.action.BaseAction;


public class ZybDepInspectAction extends BaseAction implements ModelDriven {
	private static final long serialVersionUID = 1L;
	private DepInspectModel model = new DepInspectModel();
	private DepInspectService depInspectService;
	private SyslogService syslogService;
	private CustomerInfoService customerInfoService;
	private ZybCustomerInfoService zybCustomerInfoService;
	private int page=1; // 当前页
	private int rows=15; // easyui每页显示条数
	
	public void setCustomerInfoService(CustomerInfoService customerInfoService) {
		this.customerInfoService = customerInfoService;
	}
	public DepInspectModel getModel() {
		return model;
	}
	public void setModel(DepInspectModel model) {
		this.model = model;
	}
	public void setDepInspectService(DepInspectService depInspectService) {
		this.depInspectService = depInspectService;
	}
	public void setZybCustomerInfoService(ZybCustomerInfoService zybCustomerInfoService) {
		this.zybCustomerInfoService = zybCustomerInfoService;
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
	public void setSyslogService(SyslogService syslogService) {
		this.syslogService = syslogService;
	}
	
	/**
	 * zyb400 zyb普通科室检查页面
	     * @Title: inspectshow   
	     * @Description: TODO 
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String zybInspectshow() throws WebException,SQLException{
		UserDTO user = (UserDTO) session.get("username");
		ExamInfoUserDTO eiudto = this.depInspectService.getExamInfoUserDTO(model.getExam_num(),user);
		model.setArch_num(eiudto.getArch_num());
		model.setUser_name(eiudto.getUser_name());
		model.setExam_num(eiudto.getExam_num());
		model.setSex(eiudto.getSex());
		model.setAge(eiudto.getAge());
		model.setCompany(eiudto.getCompany());
		model.setJoin_date(eiudto.getJoin_date());//检查日期
		model.setCustomer_type(eiudto.getCustomer_type());
			//model.setCustomer_type(eiudto.getCustomer_type());
		model.setSet_name(eiudto.getSet_name());//套餐
		model.setPast_medical_history(eiudto.getPast_medical_history());//既往史
		model.setId(eiudto.getId());
		model.setType_name(eiudto.getType_name());
		model.setC_id(eiudto.getC_id());
		model.setPicture_path(eiudto.getPicture_path());
	   	ExamdepResult	re = this.depInspectService.getExamdepresultResult(model.getId(), user,"2");
		if(re!=null){
			model.setExam_result_summary(re.getExam_result_summary());
			model.setSuggestion(re.getSuggestion());
		}
		
		String isDepResultShow = this.customerInfoService.getCenterconfigByKey("IS_DEPRESULT_SHOW", user.getCenter_num()).getConfig_value().trim();
		
		model.setExam_result(isDepResultShow);
		model.setApp_type("2");
		
		ExamInfoExtDTO ext = this.zybCustomerInfoService.getExamInfoExtForExamNum(eiudto.getExam_num());
		if(ext != null){
			model.setOccutypeofworkid(ext.getTypeofwork_name());
			model.setOccusectorid(ext.getIndustry_name());
			model.setOccutypeofwork(ext.getOccutypeofwork());
			model.setOccusector(ext.getOccusector());
			model.setJoinDatetime(ext.getJoinDatetime());
			model.setEmployeeage(ext.getEmployeeage());
			model.setDamage(ext.getDamage());
		}
		
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("zyb400");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return SUCCESS;
	}
	
	/**
	 * zyb401 zyb与健康体检普通科室检查综合页面
	     * @Title: inspectshowAll   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String inspectshowAll() throws WebException,SQLException{
		return SUCCESS;
	}
	
	/**
	 * zyb411查询体检者本科室项目的体检类型 
	     * @Title: getExamItemAppType   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String getExamItemAppType() throws WebException,SQLException{
		UserDTO user = (UserDTO) session.get("username");
		this.message = this.depInspectService.getExamItemAppType(this.model.getExam_num(),user.getDep_id(), user.getCenter_num());
		this.outJsonStrResult(this.message);
		return NONE;
	}

}
