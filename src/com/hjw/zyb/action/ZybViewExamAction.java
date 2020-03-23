package com.hjw.zyb.action;

import com.hjw.wst.DTO.ExamInfoUserDTO;
import com.hjw.wst.DTO.UserDTO;
import com.hjw.wst.domain.SysLog;
import com.hjw.wst.model.ViewExamModel;
import com.hjw.wst.service.DepInspectService;
import com.hjw.wst.service.SyslogService;
import com.hjw.wst.service.ViewExamService;
import com.hjw.zyb.DTO.ExamInfoExtDTO;
import com.hjw.zyb.service.ZybCustomerInfoService;
import com.opensymphony.xwork2.ModelDriven;
import com.synjones.framework.exception.WebException;
import com.synjones.framework.web.action.BaseAction;

public class ZybViewExamAction extends BaseAction implements ModelDriven{
private static final long serialVersionUID = 8217814910220881058L;
	
	private ViewExamModel model = new ViewExamModel();
	private ViewExamService viewExamService;
	private DepInspectService depInspectService;
	private ZybCustomerInfoService zybCustomerInfoService;
	private SyslogService syslogService;
	
	private int rows = 15; // easyui每页显示条数
	private String sort;
	private String order;
	public String getSort() {
		return sort;
	}
	public void setSort(String sort) {
		this.sort = sort;
	}
	public String getOrder() {
		return order;
	}
	public void setOrder(String order) {
		this.order = order;
	}
	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public void setViewExamService(ViewExamService viewExamService) {
		this.viewExamService = viewExamService;
	}
	public void setDepInspectService(DepInspectService depInspectService) {
		this.depInspectService = depInspectService;
	}
	public void setZybCustomerInfoService(ZybCustomerInfoService zybCustomerInfoService) {
		this.zybCustomerInfoService = zybCustomerInfoService;
	}
	public void setModel(ViewExamModel model) {
		this.model = model;
	}

	@Override
	public Object getModel() {
		return model;
	}
	
	public void setSyslogService(SyslogService syslogService) {
		this.syslogService = syslogService;
	}

	/**
	 *zyb404 zyb影像科室结果录入页面
	     * @Title: getViewExamPage   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String getZybViewExamPage() throws WebException{
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
		model.setSet_name(eiudto.getSet_name());//套餐
		model.setPast_medical_history(eiudto.getPast_medical_history());//既往史
		model.setExaminfo_id(eiudto.getId());
		model.setType_name(eiudto.getType_name());
		model.setPicture_path(eiudto.getPicture_path());
		model.setExam_type_code(eiudto.getExam_types());
		
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
		this.model.setApp_type("2");
		
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("713");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return SUCCESS;
	}
	
	/**
	 * zyb405 zyb与健康体检影像科室检查综合页面
	     * @Title: getViewExamAllPage   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String getViewExamAllPage() throws WebException{
		return SUCCESS;
	}
}
