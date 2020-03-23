package com.hjw.wst.action;

import java.sql.SQLException;
import java.util.List;

import com.hjw.util.DateTimeUtil;
import com.hjw.webService.client.LISResMessage;
import com.hjw.webService.client.body.ResultLisBody;
import com.hjw.wst.DTO.CommonExamDetailDTO;
import com.hjw.wst.DTO.DepExamResultDTO;
import com.hjw.wst.DTO.DepartmentDepDTO;
import com.hjw.wst.DTO.ExamInfoDTO;
import com.hjw.wst.DTO.ExamResultDetailDTO;
import com.hjw.wst.DTO.ExaminfoChargingItemDTO;
import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.wst.DTO.UserDTO;
import com.hjw.wst.DTO.WebserviceConfigurationDTO;
import com.hjw.wst.domain.SysLog;
import com.hjw.wst.domain.ViewExamDetail;
import com.hjw.wst.domain.WebResrelAtionship;
import com.hjw.wst.model.ExamResultDetailModel;
import com.hjw.wst.service.CustomerInfoService;
import com.hjw.wst.service.DepInspectService;
import com.hjw.wst.service.ExamResultDetailService;
import com.hjw.wst.service.NewDiseaseLogicService;
import com.hjw.wst.service.SyslogService;
import com.hjw.wst.service.ViewExamService;
import com.hjw.wst.service.WebserviceConfigurationService;
import com.opensymphony.xwork2.ModelDriven;
import com.synjones.framework.exception.WebException;
import com.synjones.framework.web.action.BaseAction;

import net.sf.json.JSONArray;

/**
 * 检验科 功能
     * @Title:  火箭蛙体检管理系统   
     * @Package com.hjw.wst.action   
     * @Description:  
     * @author: dangqi     
     * @date:   2016年12月2日 上午10:52:17   
     * @version V2.0.0.0
 */
public class ExamResultDetailAction extends BaseAction implements ModelDriven{
	private static final long serialVersionUID = 8217814910220881058L;
	
	private ExamResultDetailModel model = new ExamResultDetailModel();
	private ExamResultDetailService examResultDetailService;
	private SyslogService syslogService;
	private ViewExamService viewExamService;
	private WebserviceConfigurationService webserviceConfigurationService;
	private CustomerInfoService customerInfoService;
	private NewDiseaseLogicService newDiseaseLogicService;
	private DepInspectService depInspectService;
	
	public void setDepInspectService(DepInspectService depInspectService) {
		this.depInspectService = depInspectService;
	}
	public void setNewDiseaseLogicService(NewDiseaseLogicService newDiseaseLogicService) {
		this.newDiseaseLogicService = newDiseaseLogicService;
	}
	public void setCustomerInfoService(CustomerInfoService customerInfoService) {
		this.customerInfoService = customerInfoService;
	}
	private int rows = 15; // easyui每页显示条数
	private String sort;
	private String order;
	public ViewExamService getViewExamService() {
		return viewExamService;
	}
	public void setViewExamService(ViewExamService viewExamService) {
		this.viewExamService = viewExamService;
	}
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
	
	public void setExamResultDetailService(ExamResultDetailService examResultDetailService) {
		this.examResultDetailService = examResultDetailService;
	}
	public void setSyslogService(SyslogService syslogService) {
		this.syslogService = syslogService;
	}
	public void setWebserviceConfigurationService(WebserviceConfigurationService webserviceConfigurationService) {
		this.webserviceConfigurationService = webserviceConfigurationService;
	}
	public void setModel(ExamResultDetailModel model) {
		this.model = model;
	}
	
	@Override
	public Object getModel() {
		return model;
	}
	
	/**
	 * 检验科室首页 721
	     * @Title: getExamResultDetailIndex   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String getExamResultDetailIndex() throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		this.model.setTime1(DateTimeUtil.getDate2());
		this.model.setTime2(DateTimeUtil.getDate2());
		this.model.setExam_date1(DateTimeUtil.getDate2());
		this.model.setExam_date2(DateTimeUtil.getDate2());
		this.model.setReport_print_type(this.customerInfoService.getCenterconfigByKey("REPORT_PRINT_TYPE", user.getCenter_num()).getConfig_value().trim());
		this.model.setZyb_report_print_type(this.customerInfoService.getCenterconfigByKey("ZYB_REPORT_PRINT_TYPE", user.getCenter_num()).getConfig_value().trim());
		String is_custom_identification = "";
		try {
			is_custom_identification = this.customerInfoService.getCenterconfigByKey("IS_CUSTOM_IDENTIFICATION", user.getCenter_num()).getConfig_value();
		} catch (Exception e) {
			System.out.println("缺少IS_CUSTOM_IDENTIFICATION 配置");
		}
		this.model.setIs_custom_identification(is_custom_identification);
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("721");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return SUCCESS;
	}
	
	/**
	 * 检验科室首页查询人员信息列表 722
	     * @Title: getExamResultDetailList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String getExamResultDetailList() throws WebException,SQLException{
		UserDTO user = (UserDTO) session.get("username");
		PageReturnDTO list = this.examResultDetailService.getExamInfoList(model, user, this.rows, this.getPage(),this.sort,this.order);
		this.outJsonResult(list);
		return NONE;
	}
	
	/**
	 * 检验科室结果录入页面 723
	     * @Title: getExamResultDetail   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String getExamResultDetail() throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		this.model.setIsExamResultDetailDoctorPageShow(this.customerInfoService.getCenterconfigByKey("IS_EXAMRESULTDETAIL_DOCTORPAGE_SHOW", user.getCenter_num()).getConfig_value().trim());
		this.model.setApp_type("1");
		//资源
		List<WebResrelAtionship>  web = user.getWebResource();
		if(web!=null){
			for (int i = 0; i < web.size(); i++) {
				if(web.get(i).getRes_code().equals("RS054")){
					this.model.setIs_update_critical(web.get(i).getDatavalue());
					continue;
				}
			}
		}
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("723");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return SUCCESS;
	}
	
	/**
	 * 查询检验科收费项目也样本 列表 724
	     * @Title: getExamResultCharingItem   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String getExamResultCharingItem() throws WebException,SQLException{
		UserDTO user = (UserDTO) session.get("username");
		long dep_id = user.getDep_id();
		if(model.getDep_id() > 0){
			dep_id = model.getDep_id();
		}
		List<ExamResultDetailDTO> list = this.examResultDetailService.getCharingItemByExamNum(this.model.getExam_num(), dep_id,this.model.getApp_type(), user.getCenter_num());
		this.outJsonResult(list);
		return NONE;
	}
	
	/**
	 * 查询检验科收费项目中的检查项目与检查结果 725
	     * @Title: getExamResultExaminitionItem   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String getExamResultExaminitionItem() throws WebException,SQLException{
		UserDTO user = (UserDTO) session.get("username");
		List<ExamResultDetailDTO> list = this.examResultDetailService.getExamResultDetail(this.model.getExam_num(),this.model.getCharging_item_code());
		this.outJsonResult(list);
		return NONE;
	}
	
	/**
	 * 保存检验结果 726
	     * @Title: saveExamResultDetail   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	@SuppressWarnings("unchecked")
	public String saveExamResultDetail() throws WebException,SQLException{
		UserDTO user = (UserDTO) session.get("username");
		JSONArray itemList = JSONArray.fromObject(this.model.getExamResulLists());
		List<ExamResultDetailDTO> listviewExam = (List<ExamResultDetailDTO>) JSONArray.toCollection(itemList,ExamResultDetailDTO.class);
		this.model.setExamResulList(listviewExam);
		if(user != null){
			this.message = this.examResultDetailService.saveExamResultDetail(model, user);
			ExamInfoDTO examinfo = this.customerInfoService.getCustExamInfoForexamId(model.getExam_num());
			List<ExamResultDetailDTO> lis = model.getExamResulList();
			for (ExamResultDetailDTO examResultDetailDTO : lis) {
				DepExamResultDTO examResult = new DepExamResultDTO();
				examResult.setDep_id(model.getDep_id());
				examResult.setDep_num(model.getDep_num());
				examResult.setCharging_item_id(examResultDetailDTO.getCharging_item_id());
				examResult.setItem_code(examResultDetailDTO.getItem_code());
				examResult.setExam_item_id(examResultDetailDTO.getExam_item_id());
				examResult.setItem_num(examResultDetailDTO.getItem_num());
				examResult.setExam_result(examResultDetailDTO.getExam_result());
				examResult.setResult_type(0);
				
				/*******************自动生成单项阳性词条*****************/
				this.newDiseaseLogicService.createDepLogicSingleDisease(examinfo, examResult,user.getUserid());
				/*******************自动生成危急值*****************/
				this.depInspectService.createExamCritical(examinfo.getExam_num(), examResultDetailDTO.getItem_code(), examResultDetailDTO.getItem_num());
			}
			SysLog sl =  new SysLog();
			sl.setCenter_num(user.getCenter_num());
			sl.setUserid(user.getUserid()+"");
			sl.setOper_type("1");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
			sl.setXtgnid("");//可不填写
			sl.setXtgnid2("726");//子功能id 必须填写
			sl.setExplain("");//操作说明
			syslogService.saveSysLog(sl);
		}else{
			this.message = "error-请重新登录!";
		}
		this.outJsonStrResult(this.message);
		return NONE;
	}
	
	/**
	 * 769 根据体检信息ID查询本科室的收费项目
	     * @Title: getInfoItemByIdAndStatus   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String getInfoItemByIdAndStatus() throws WebException,SQLException {
		UserDTO user = (UserDTO) session.get("username");
		List<ExaminfoChargingItemDTO> list = this.examResultDetailService.getInfoItemByIdAndStatus(this.model.getExam_num(), user.getDep_id(), this.model.getExam_status(), user.getCenter_num());
		this.outJsonResult(list);
		return NONE;
	}
	
	/**
	 * 784 读取LIS检验结果首页
	     * @Title: getExamResultDetailIndexReader   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String getExamResultDetailIndexReader() throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		DepartmentDepDTO dep = this.examResultDetailService.getDepId(user.getCenter_num());
		if(dep != null){
			this.model.setDep_id(dep.getId());
		}
		return SUCCESS;
	}
	
	/**
	 * 785 读取LIS检验结果明细页
	     * @Title: getExamResultDetailReader   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String getExamResultDetailReaderPage() throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		DepartmentDepDTO dep = this.examResultDetailService.getDepId(user.getCenter_num());
		if(dep != null){
			this.model.setDep_id(dep.getId());
		}
		return SUCCESS;
	}
	
	/**
	 * 786 读取LIS检验结果
	     * @Title: getExamResultDetailReader   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String getExamResultDetailReader() throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		//查询webservice地址
		WebserviceConfigurationDTO wcf=new WebserviceConfigurationDTO();
		wcf=this.webserviceConfigurationService.getWebServiceConfig("LIS_SEND", user.getCenter_num());
		String web_url = wcf.getConfig_url().trim();
		String web_meth= wcf.getConfig_method().trim();
		String[] exam_nums = this.model.getExam_num().split(",");
		
		LISResMessage lisRes = new LISResMessage();
		String str = "";
		for (int i = 0; i < exam_nums.length; i++) {
			ResultLisBody rb = lisRes.lisSend(web_url, web_meth, exam_nums[i], user.getCenter_num());
			if("AE".equals(rb.getResultHeader().getTypeCode())){
				str += exam_nums[i]+",";
			}
		}
		if(!"".equals(str)){
			this.message = "error-体检号为"+str.substring(0, str.length()-1)+",读取失败!";
		}else{
			this.message = "ok-检验结果读取完成!";
		}
		this.outJsonStrResult(this.message);
		return NONE;
	}
	
	/**
	 * 影像科室项目查询
	     * @Title: getViewExamCharingItem   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String getViewExamCharingItem() throws WebException,SQLException{
		UserDTO user = (UserDTO) session.get("username");
		List<ExamResultDetailDTO> list = this.examResultDetailService.getViewExamCharingItem(this.model.getExam_num(), user.getCenter_num());
		this.outJsonResult(list);
		return NONE;
	}
	
	/**
	 * 影像科室检查项目结果查询
	     * @Title: getViewExamResult   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String getViewExamResult()throws WebException,SQLException{
		List<ExamResultDetailDTO> list = this.examResultDetailService.getViewExamResult(this.model.getPace_id(), this.model.getCharging_id());
		this.outJsonResult(list);
		return NONE;
	}
	
	/**
	 * 阳性标记查询
	     * @Title: getPositiveFindList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String getPositiveFindList()throws WebException{
		List<ExamResultDetailDTO> list=this.examResultDetailService.getPositiveFindList();
		this.outJsonResult(list);
		return NONE;
	}
	
	/**
	 * 影像科室检查结果阳性标记结果保存
	     * @Title: savePositivefind   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String savePositivefind()throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		ViewExamDetail v=this.examResultDetailService.getPositiveById(this.model.getId());
		v.setMarker(user.getUserid());
		v.setMark_time(DateTimeUtil.parse());
		v.setPositive_find(this.model.getPositive_find());
		this.viewExamService.updateViewexamdetail(v);
		return NONE;
	}
	
	/**
	 * 检验科室设置医生页面919
	 */
	public String getSetdoctor() throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("919");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return SUCCESS;
	}
	/**
	 * 获取检验科医生920
	     * @Title: getDoctor   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String getDoctor() throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		this.outJsonResult(this.examResultDetailService.getDoctor(user.getCenter_num()));
//		UserDTO user = (UserDTO) session.get("username");
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("920");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return NONE;
	}
}
