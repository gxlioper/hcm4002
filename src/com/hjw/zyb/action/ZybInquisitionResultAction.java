package com.hjw.zyb.action;

import java.util.List;

import com.hjw.util.DateTimeUtil;
import com.hjw.wst.DTO.ExamInfoDTO;
import com.hjw.wst.DTO.ExamInfoUserDTO;
import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.wst.DTO.UserDTO;
import com.hjw.wst.domain.SysLog;
import com.hjw.wst.service.CustomerInfoService;
import com.hjw.wst.service.SyslogService;
import com.hjw.zyb.DTO.ExamInfoExtDTO;
import com.hjw.zyb.DTO.ZybAskDiagnosisSampleDTO;
import com.hjw.zyb.DTO.ZybInquisitionResultDTO;
import com.hjw.zyb.model.ZybInquisitionResultModel;
import com.hjw.zyb.service.ZybCustomerInfoService;
import com.hjw.zyb.service.ZybInquisitionResultService;
import com.opensymphony.xwork2.ModelDriven;
import com.synjones.framework.exception.WebException;
import com.synjones.framework.web.action.BaseAction;

import net.sf.json.JSONArray;
/**
 * 问诊项目结果功能类
     * @Title:  火箭蛙体检管理系统   
     * @Package com.hjw.zyb.action   
     * @Description:  
     * @author: dangqi     
     * @date:   2017年12月12日 下午2:59:44   
     * @version V2.0.0.0
 */
public class ZybInquisitionResultAction extends BaseAction implements ModelDriven{

	private static final long serialVersionUID = 1L;
	private ZybInquisitionResultModel model = new ZybInquisitionResultModel();
	private ZybInquisitionResultService zybInquisitionResultService;
	private ZybCustomerInfoService zybCustomerInfoService;
	private SyslogService syslogService;
	private CustomerInfoService customerInfoService;
	
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
	
	public ZybInquisitionResultModel getModel() {
		return model;
	}
	public void setModel(ZybInquisitionResultModel model) {
		this.model = model;
	}
	public ZybInquisitionResultService getZybInquisitionResultService() {
		return zybInquisitionResultService;
	}
	public void setZybInquisitionResultService(ZybInquisitionResultService zybInquisitionResultService) {
		this.zybInquisitionResultService = zybInquisitionResultService;
	}
	public SyslogService getSyslogService() {
		return syslogService;
	}
	public void setSyslogService(SyslogService syslogService) {
		this.syslogService = syslogService;
	}
	
	public void setZybCustomerInfoService(ZybCustomerInfoService zybCustomerInfoService) {
		this.zybCustomerInfoService = zybCustomerInfoService;
	}
	
	public void setCustomerInfoService(CustomerInfoService customerInfoService) {
		this.customerInfoService = customerInfoService;
	}
	/**
	 * zyb418 获取问诊功能首页
	     * @Title: zybInquisitionIndexpage   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return      
	     * @return: String      
	     * @throws
	 */
	public String zybInquisitionIndexpage() throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		this.model.setTime1(DateTimeUtil.getDate2());
		this.model.setTime2(DateTimeUtil.getDate2());
		this.model.setExam_date1(DateTimeUtil.getDate2());
		this.model.setExam_date2(DateTimeUtil.getDate2());
		this.model.setBarcode_print_type(this.customerInfoService.getCenterconfigByKey("BARCODE_PRINT_TYPE", user.getCenter_num()).getConfig_value().trim());
		this.model.setZyb_barcode_print_type(this.customerInfoService.getCenterconfigByKey("ZYB_BARCODE_PRINT_TYPE", user.getCenter_num()).getConfig_value().trim());
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("zyb418");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return SUCCESS;
	}
	
	/**
	 * zyb419 获取问诊人员列表
	     * @Title: zybInquisitionIndexList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return      
	     * @return: String      
	     * @throws
	 */
	public String zybInquisitionIndexList() throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		PageReturnDTO list = this.zybInquisitionResultService.getExamInfoList(model, user, rows, page, sort, order);
		this.outJsonResult(list);
		return NONE;
	}
	
	/**
	 * zyb420 获取问诊项目录入页面
	     * @Title: zybInquisitionPage   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String zybInquisitionPage() throws WebException{
		ExamInfoUserDTO eiudto = this.zybInquisitionResultService.getExaminfoById(model.getExaminfo_id());
		if(eiudto != null){
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
		}
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
		return SUCCESS;
	}
	
	/**
	 * zyb421 获取问诊项目列表
	     * @Title: zybInquisitionList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String zybInquisitionList() throws WebException{
		List<ZybAskDiagnosisSampleDTO> list = this.zybInquisitionResultService.getZybInquisitionList(this.model.getExaminfo_id());
		this.outJsonResult(list);
		return NONE;
	}
	
	/**
	 * zyb422 保存问诊项目结果
	     * @Title: saveZybInquisitionList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String saveZybInquisitionList() throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		
		JSONArray inquisitionResults = JSONArray.fromObject(this.model.getInquisitionResults());
		List<ZybInquisitionResultDTO> list = (List<ZybInquisitionResultDTO>) JSONArray.toCollection(inquisitionResults,ZybInquisitionResultDTO.class);
		model.setInquisitionResultList(list);
		this.message = this.zybInquisitionResultService.saveZybInquisitionList(model, user);
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("1");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("zyb422");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		this.outJsonStrResult(this.message);
		return NONE;
	}
	/**
	 * zyb604职业史页面
	     * @Title: zhiyeshiPage   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String getzhiyeshiPage(){
		ExamInfoDTO examinfo = zybCustomerInfoService.getExamInfoFindExamNum(model);
		model.setId_num(examinfo.getId_num());
		model.setArch_num(examinfo.getArch_num());
		model.setExam_num(examinfo.getExam_num());
		model.setData_code_children(examinfo.getData_code_children());
		return SUCCESS;
	}
}
