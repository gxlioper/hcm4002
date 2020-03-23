package com.hjw.wst.action;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fr.fs.base.entity.Department;
import com.hjw.wst.domain.*;
import com.synjones.framework.exception.ServiceException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.hjw.wst.model.DepInspectModel;
import com.hjw.wst.DTO.BSRCorrectionValueDTO;
import com.hjw.wst.DTO.CenterConfigurationDTO;
import com.hjw.wst.DTO.CommonExamDetailDTO;
import com.hjw.wst.DTO.DepExamResultDTO;
import com.hjw.wst.DTO.DepInspectCharingItemDTO;
import com.hjw.wst.DTO.DepInspectExamIntionDTO;
import com.hjw.wst.DTO.DepLogicDTO;
import com.hjw.wst.DTO.ExamDepResultDTO;
import com.hjw.wst.DTO.ExamInfoDTO;
import com.hjw.wst.DTO.ExamInfoUserDTO;
import com.hjw.wst.DTO.ExpertSuggestionLibDTO;
import com.hjw.wst.DTO.ItemResultLibDTO;
import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.wst.DTO.UserDTO;
import com.hjw.wst.DTO.UserInfoDTO;
import com.google.gson.Gson;
import com.hjw.config.Logincheck;
import com.hjw.util.DateTimeUtil;
import com.hjw.webService.client.QueueNexSendMessage;
import com.hjw.webService.client.Bean.QueueNextBean;
import com.hjw.webService.client.body.QueueResBody;
import com.hjw.wst.service.CustomerInfoService;
import com.hjw.wst.service.DepInspectService;
import com.hjw.wst.service.NewDiseaseLogicService;
import com.hjw.wst.service.SyslogService;
import com.opensymphony.xwork2.ModelDriven;
import com.synjones.framework.aop.SystemType;
import com.synjones.framework.exception.WebException;
import com.synjones.framework.web.action.BaseAction;

import net.sf.json.JSONArray;



/**
 * 
     * @Title:  火箭蛙体检管理系统   
     * @Package com.hjw.wst.action   
     * @Description:  
     * @date:   2016年11月18日 下午4:01:37   
     * @version V2.0.0.0
 */
@SuppressWarnings("rawtypes")
public class DepInspectAction extends BaseAction implements ModelDriven {

	private static final long serialVersionUID = 1L;
	private Log log = LogFactory.getLog(this.getClass());
	private Logincheck logincheck;
	private DepInspectModel model = new DepInspectModel();
	private DepInspectService depInspectService;
	private SyslogService syslogService;
	private CustomerInfoService customerInfoService;
	private NewDiseaseLogicService newDiseaseLogicService;
	private int page=1; // 当前页
	private int rows=15; // easyui每页显示条数
	private String code="";//判断列表1/2
	private String li="";//接收list数据
	private String sort;
	private String order;
	
	public void setCustomerInfoService(CustomerInfoService customerInfoService) {
		this.customerInfoService = customerInfoService;
	}

	public void setNewDiseaseLogicService(NewDiseaseLogicService newDiseaseLogicService) {
		this.newDiseaseLogicService = newDiseaseLogicService;
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
	
	
	public String getLi() {
		return li;
	}
	public void setLi(String li) {
		this.li = li;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Log getLog() {
		return log;
	}
	public void setLog(Log log) {
		this.log = log;
	}
	public Logincheck getLogincheck() {
		return logincheck;
	}
	public void setLogincheck(Logincheck logincheck) {
		this.logincheck = logincheck;
	}
	public DepInspectModel getModel() {
		return model;
	}
	public void setModel(DepInspectModel model) {
		this.model = model;
	}
	public DepInspectService getDepInspectService() {
		return depInspectService;
	}
	public void setDepInspectService(DepInspectService depInspectService) {
		this.depInspectService = depInspectService;
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
	 * 科室检查首页600
	     * @Title: departInspec   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String departInspec() throws  WebException, SQLException{
		UserDTO user = (UserDTO) session.get("username");
		CenterConfigurationDTO config = this.customerInfoService.getCenterconfigByKey("IS_DEPINSPECT_CHECKED", user.getCenter_num());//
		if( config != null && config.getConfig_value() != null){
			model.setIs_depinspect_checked(config.getConfig_value().trim());
		}
		this.model.setReport_print_type(this.customerInfoService.getCenterconfigByKey("REPORT_PRINT_TYPE", user.getCenter_num()).getConfig_value().trim());
		this.model.setZyb_report_print_type(this.customerInfoService.getCenterconfigByKey("ZYB_REPORT_PRINT_TYPE", user.getCenter_num()).getConfig_value().trim());
		this.model.setCustomer_type_special(this.customerInfoService.getCenterconfigByKey("CUSTOMER_TYPE_SPECIAL", user.getCenter_num()).getConfig_value().trim());
		this.model.setCustomer_type_special_color(this.customerInfoService.getCenterconfigByKey("CUSTOMER_TYPE_SPECIAL_COLOR", user.getCenter_num()).getConfig_value().trim());
		this.model.setTime1(DateTimeUtil.getDate2());
		this.model.setTime2(DateTimeUtil.getDate2());
		this.model.setExam_date1(DateTimeUtil.getDate2());
		this.model.setExam_date2(DateTimeUtil.getDate2());
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
		sl.setXtgnid2("600");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return SUCCESS;
	}
	/**
	 * 
	     * @Title: inspectshow   
	     * @Description: TODO(科室检查编辑602)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String inspectshow() throws WebException,SQLException{
		UserDTO user = (UserDTO) session.get("username");
		this.defaultapp = (SystemType) session.get("defaultapp");
		this.model.setApp_type(this.defaultapp.getComid());
		this.model.setDep_num(user.getDeptCode());

		model.setIs_departinspect_summary_edit(this.customerInfoService.getCenterconfigByKey("IS_DEPARTINSPECT_SUMMARY_EDIT", user.getCenter_num()).getConfig_value());
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
		model.setId(eiudto.getId());
		model.setType_name(eiudto.getType_name());
		model.setC_id(eiudto.getC_id());
		model.setExam_type_code(eiudto.getExam_types());
		model.setPicture_path(eiudto.getPicture_path());
		model.setVipsigin(eiudto.getVipsigin());
	   	ExamdepResult	re = this.depInspectService.getExamdepresultResult(model.getId(), user,"1");
		if(re!=null){
			model.setExam_result_summary(re.getExam_result_summary());
			model.setSuggestion(re.getSuggestion());
		}
		
		String isCheckSensvitiveWord = this.customerInfoService.getCenterconfigByKey("IS_CHECK_SENSITIVE_WORD", user.getCenter_num()).getConfig_value().trim();
		model.setIsCheckSensvitiveWord(isCheckSensvitiveWord);
		String isDepResultShow = this.customerInfoService.getCenterconfigByKey("IS_DEPRESULT_SHOW", user.getCenter_num()).getConfig_value().trim();
		model.setExam_result(isDepResultShow);
		String is_dep_edit_questionnaire = this.customerInfoService.getCenterconfigByKey("IS_DEP_EDIT_QUESTIONNAIRE", user.getCenter_num()).getConfig_value().trim();
		model.setIs_dep_edit_questionnaire(is_dep_edit_questionnaire);
		model.setApp_type("1");
		
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
		String  isDeptType = "COMM";
		try{
			 isDeptType = this.customerInfoService.getCenterconfigByKey("IS_DEPT_TYPE",user.getCenter_num()).getConfig_value().trim();
		}catch (Exception e){
			System.out.println("缺少配置- IS_DEPT_TYPE");
		}
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("602");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		if("COMM_ALL".equals(isDeptType)){
			return "success_all";
		}else{
			return SUCCESS;
		}

	}
	public String inspectshow_BSR() throws WebException,SQLException{
		UserDTO user = (UserDTO) session.get("username");
		model.setIs_departinspect_summary_edit(this.customerInfoService.getCenterconfigByKey("IS_DEPARTINSPECT_SUMMARY_EDIT", user.getCenter_num()).getConfig_value());
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
		model.setId(eiudto.getId());
		model.setType_name(eiudto.getType_name());
		model.setC_id(eiudto.getC_id());
		model.setExam_type_code(eiudto.getExam_types());
		model.setPicture_path(eiudto.getPicture_path());
		model.setVipsigin(eiudto.getVipsigin());
		ExamdepResult	re = this.depInspectService.getExamdepresultResult(model.getId(), user, model.getApp_type());
		if(re!=null){
			model.setExam_result_summary(re.getExam_result_summary());
			model.setSuggestion(re.getSuggestion());
		}
		BSRCorrectionValueDTO correctionValue = depInspectService.getBSRCorrectionValueDTO(eiudto.getAge(), eiudto.getSex());
		model.setCorrectionValue(correctionValue);
		
		String isDepResultShow = this.customerInfoService.getCenterconfigByKey("IS_DEPRESULT_SHOW", user.getCenter_num()).getConfig_value().trim();
		model.setExam_result(isDepResultShow);
		String is_dep_edit_questionnaire = this.customerInfoService.getCenterconfigByKey("IS_DEP_EDIT_QUESTIONNAIRE", user.getCenter_num()).getConfig_value().trim();
		model.setIs_dep_edit_questionnaire(is_dep_edit_questionnaire);
		
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
		sl.setXtgnid2("602");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return SUCCESS;
	}
	/**
	 * 
	     * @Title: getDepExamInfoUserLis   
	     * @Description: TODO(科室体检人员列表)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String getDepExamInfoUserLis() throws WebException, SQLException{
		UserDTO user = (UserDTO) session.get("username");
		PageReturnDTO list = this.depInspectService.getDepExamInfoUserLis(model,user,this.page,this.rows,this.sort,this.order);
		this.outJsonResult(list);
		return NONE;
	}
	
	/**
	 *  查询人员基本信息
	     * @Title: getCustomerInfo   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String getExamSumarry() throws WebException,SQLException{
		UserDTO user = (UserDTO) session.get("username");
		List<ExamDepResultDTO> exam = this.depInspectService.getCustomerInfo(model.getId(),user);
		this.outJsonResult(exam);
		return NONE;
	}
	
	/**
	 * 获取检查历史结果
	     * @Title: getHistoryResult   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String getHistoryResult() throws WebException,SQLException{
		UserDTO user = (UserDTO) session.get("username");
		List<ExamDepResultDTO> exam = this.depInspectService.getHistoryResult(model.getExam_num(),user);
		this.outJsonResult(exam);
		return NONE;
	}
	/**
	 * 
	     * @Title:
	     * @Description: TODO(科室检查检查项目527)   
	     * @Title: getWeijizhi   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String getWeijizhi() throws WebException,SQLException{
		List<DepExamResultDTO> exam = this.depInspectService.queryweijizhi(model.getExam_num(),model.getApp_type());
		this.outJsonResult(exam);
		return NONE;
	}
	/**
	 * 
	     * @Title: getDepInspectExamIntion   
	     * @Description: TODO(科室检查获取所有检查项目)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String getDepInspectExamIntion() throws WebException,SQLException{
		UserDTO user = (UserDTO) session.get("username");
		long dep_id = user.getDep_id();
		if(model.getDep_id() > 0) {
			dep_id = model.getDep_id();
		}
		List<DepInspectExamIntionDTO>  li=this.depInspectService.getDepInspectExamIntion(model.getId(),dep_id,model.getApp_type(),model.getExam_num(), user.getCenter_num());
		Map<Long,DepInspectCharingItemDTO> map = new HashMap<Long,DepInspectCharingItemDTO>();
		for(DepInspectExamIntionDTO depItem : li){
			DepInspectCharingItemDTO dep_c = map.get(depItem.getS_id());
			if(dep_c != null){
				dep_c.getDepItemList().add(depItem);
				map.put(depItem.getS_id(), dep_c);
			}else{
				dep_c = new DepInspectCharingItemDTO();
					
				dep_c.setC_id(depItem.getS_id());
				dep_c.setC_name(depItem.getS_name());
				dep_c.setExam_status(depItem.getExam_status());
				
				dep_c.getDepItemList().add(depItem);
				map.put(depItem.getS_id(), dep_c);
			}
		}
		List<DepInspectCharingItemDTO> list = new ArrayList<>(map.values());
		this.outJsonResult(list);
		return NONE;
	}
	
	public String getDepInspectExamIntion_BSR() throws WebException,SQLException{
		UserDTO user = (UserDTO) session.get("username");
		long dep_id = user.getDep_id();
		if(model.getDep_id() > 0) {
			dep_id = model.getDep_id();
		}
		List<DepInspectExamIntionDTO>  li=this.depInspectService.getDepInspectExamIntion_BSR(model.getId(),dep_id,model.getExam_num(), user.getCenter_num());
		Map<Long,DepInspectCharingItemDTO> map = new HashMap<Long,DepInspectCharingItemDTO>();
		for(DepInspectExamIntionDTO depItem : li){
			DepInspectCharingItemDTO dep_c = map.get(depItem.getS_id());
			if(dep_c != null){
				dep_c.getDepItemList().add(depItem);
				map.put(depItem.getS_id(), dep_c);
			}else{
				dep_c = new DepInspectCharingItemDTO();
				
				dep_c.setC_id(depItem.getS_id());
				dep_c.setC_name(depItem.getS_name());
				dep_c.setExam_status(depItem.getExam_status());
				
				dep_c.getDepItemList().add(depItem);
				map.put(depItem.getS_id(), dep_c);
			}
		}
		List<DepInspectCharingItemDTO> list = new ArrayList<>(map.values());
		this.outJsonResult(list);
		return NONE;
	}
	/**
	 * 
	     * @Title: getfd   
	     * @Description: TODO(项目获取常用词528)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String getItemResultLibcyc()  throws WebException, SQLException{
		UserDTO user = (UserDTO) session.get("username");
		long dep_id = user.getDep_id();
		if(model.getDep_id() > 0) {
			dep_id = model.getDep_id();
		}
		List<ItemResultLibDTO> li=this.depInspectService.getItemResultLibcyc(model.getItem_code(),dep_id,model.getExam_status());
		this.outJsonResult(li);
		return NONE;
	}
	/**
	 * 
	     * @Title: getItemResultLibPage   
	     * @Description: TODO(项目常用词页面529)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String getItemResultLibPage() throws WebException, SQLException{
		model.setId(model.getId());
		return SUCCESS;
	}
	/**
	 * 
	     * @Title: getItemeditLibS   
	     * @Description: TODO(获取项目所有常用词530)   
	     * @param: @return      
	     * @return: String      
	     * @throws
	 */
	public String getItemeditLibS() throws WebException, SQLException{
		UserDTO user = (UserDTO) session.get("username");
		String IS_DEPINSPECT_WORD = this.customerInfoService.getCenterconfigByKey("IS_DEPINSPECT_WORD", user.getCenter_num()).getConfig_value();//
		List<ItemResultLibDTO> li=this.depInspectService.getItemeditLibS(model.getExam_result(),model.getItem_code(),user.getDep_id(),IS_DEPINSPECT_WORD);
		List<ItemResultLibDTO> lidto= new ArrayList<ItemResultLibDTO>();
		if(li.size()!=0){
			int count = 0;
			ItemResultLibDTO dto = null;
			for(int i=0;i<li.size();i++){
				if(count == 0){
					dto = new ItemResultLibDTO();
				}
				if(i%2==0){
					dto.setIds(li.get(i).getId());
					dto.setExam_results(li.get(i).getExam_result());
					dto.setExam_conclusions(li.get(i).getExam_conclusion());
					count ++;
				}else{
					dto.setId(li.get(i).getId());
					dto.setExam_result(li.get(i).getExam_result());
					dto.setExam_conclusion(li.get(i).getExam_conclusion());
					count ++;
				}
				if(count == 2||i==li.size()-1){
					lidto.add(dto);
					count = 0;
				}
				
			}
		}
		this.outJsonResult(lidto);
		return NONE;
	}
	/**
	 * 
	     * @Title: getItemintionReference   
	     * @Description: TODO(项目结果与参考值值比较531)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String getItemintionReference() throws WebException, SQLException{
		int f=this.depInspectService.getItemintionReference(model);
		if(f==1){//正常
			this.message="zc";
		}else if(f==2){//危机
			this.message="wj";
		}else{//异常
			this.message="yc";
		}
		this.outJsonStrResult(this.message);
		return NONE;
	}
	/**
	 * 
	     * @Title: addexamDepResult   
	     * @Description: TODO(保存普通科室结论 532)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String addexamDepResult() throws WebException, SQLException{
		//获取当前用户
		UserDTO user = (UserDTO) session.get("username");
		String userName = user.getName();
		long userId = user.getUserid();
		if("Y".equals(this.model.getExam_type())){ //多科室录入 检查医生收动选择
			user.setName(this.model.getExam_doctor());
			user.setUserid(this.model.getInputter());
			this.model.setInputter(userId);
			DepartmentDep dep = this.depInspectService.getDepBydepId(this.model.getDep_id());
			this.model.setDep_num(dep.getDep_num());
		}

		//保存细项每项结论
		@SuppressWarnings("unused")
		List<CommonExamDetailDTO> list = new ArrayList<CommonExamDetailDTO>(); 
		String[] strs=new String[2];
		if(this.li.length()>0){
			JSONArray liArry = JSONArray.fromObject(this.li);
			//josn转成List
			@SuppressWarnings("unchecked")
			List<CommonExamDetailDTO> lis = (List<CommonExamDetailDTO>) JSONArray.toCollection(liArry, CommonExamDetailDTO.class);
			this.model.setDetailList(lis);
			strs=checkExamDetail(lis);
		}
		if(strs[0].equals("AA")){
			String is_depitem_save = this.customerInfoService.getCenterconfigByKey("IS_DEPITEM_SAVE", user.getCenter_num()).getConfig_value().trim();
			this.message = this.depInspectService.saveDepResultJdbc(this.model, user,is_depitem_save);
			
			
			ExamInfoDTO examinfo = this.customerInfoService.getCustExamInfoForexamId(model.getExam_num());
			List<CommonExamDetailDTO> lis = model.getDetailList();
			for (CommonExamDetailDTO commonExamDetailDTO : lis) {
				DepExamResultDTO examResult = new DepExamResultDTO();
				examResult.setDep_id(model.getDep_id());
				examResult.setDep_num(model.getDep_num());
				examResult.setCharging_item_id(commonExamDetailDTO.getCharging_item_id());
				examResult.setItem_code(commonExamDetailDTO.getItem_code());
				examResult.setExam_item_id(commonExamDetailDTO.getExam_item_id());
				examResult.setItem_num(commonExamDetailDTO.getItem_num());
				examResult.setExam_result(commonExamDetailDTO.getExam_result());
				examResult.setResult_type(0);
				
				/*******************自动生成单项阳性词条*****************/
				this.newDiseaseLogicService.createDepLogicSingleDisease(examinfo, examResult,user.getUserid());
				/*******************自动生成危急值*****************/
				this.depInspectService.createExamCritical(examinfo.getExam_num(), commonExamDetailDTO.getItem_code(), commonExamDetailDTO.getItem_num());
			}

			//是否启用排队接口
			String is_queue_check = this.customerInfoService.getCenterconfigByKey("IS_QUEUE_CHECK", user.getCenter_num()).getConfig_value().trim();
			if("Y".equals(is_queue_check)){
				QueueNextBean bean = new QueueNextBean();
				bean.setCode(model.getExam_info_id()+"");
				bean.setCenter_num(user.getCenter_num());
				QueueNexSendMessage message = new QueueNexSendMessage();
				QueueResBody rb = message.Send(bean);
				if("AA".equals(rb.getRescode())){
					
				}else{
					this.message = rb.getRestext();
				}
			}
		}else if(strs[0].equals("AE")){
			this.message = strs[1];
		}

		user.setUserid(userId);
		user.setName(userName);
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("1");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("532");//子功能id 必须填写
		sl.setExplain("保存普通科室检查结果");//操作说明
		syslogService.saveSysLog(sl);

		this.outJsonStrResult(this.message);
		return NONE;
	}
	private String[] checkExamDetail(List<CommonExamDetailDTO> lis)throws WebException, SQLException{
		String[] strs=new String[2];
		strs[0]="AA";
		for(CommonExamDetailDTO exam:lis){
			ExaminationItem item=new ExaminationItem();
			item=this.depInspectService.getExamationItem(exam.getItem_num());
			if(item.getItem_category().equals("数字型")){
				if(exam.getExam_result().length()>0){
					if(item.getError_max()!=null){
						if(Double.valueOf(exam.getExam_result())>item.getError_max()){
							strs[0]="AE";
							strs[1]="保存失败，"+item.getItem_name()+"的结果超出上限"+item.getError_max();
							break;
						}
					}
					if(item.getError_min()!=null){
						if(Double.valueOf(exam.getExam_result())<item.getError_min()){
							strs[0]="AE";
							strs[1]="保存失败，"+item.getItem_name()+"的结果少于下限"+item.getError_min();
							break;
						}
					}
				}
			}
		}
		return strs;
	}
	
	/**
	 * 
	     * @Title: deleteResult   
	     * @Description: TODO(清除结果)   
	     * @param: @return      
	     * @return: String      
	     * @throws
	 */
	public String deleteResult(){
		UserDTO user = (UserDTO) session.get("username");
		 this.depInspectService.deleteResult(model,user);
		 this.message="清除成功！";
		 this.outJsonStrResult(message);
		return NONE;
	}
	public String getyichang() throws WebException,SQLException{
		UserDTO user = (UserDTO) session.get("username");
		List<DepExamResultDTO> exam = this.depInspectService.queryYichang(model.getExam_num(),model.getApp_type(), user.getCenter_num());
		this.outJsonResult(exam);
		return NONE;
	}
	
	public String getaLL() throws WebException,SQLException{
		UserDTO user = (UserDTO) session.get("username");
		List<DepExamResultDTO> exam = this.depInspectService.queryAll(model.getExam_num(),model.getApp_type(), user.getCenter_num());
		this.outJsonResult(exam);
		return NONE;
	}
	/**
	 * 
	     * @Title: getriskPage   
	     * @Description: TODO(设置危机534)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String getriskPage() throws WebException,SQLException{
		return SUCCESS;
	}
	/**
	 * 
	     * @Title: getExamDepStatus   
	     * @Description: TODO(判断是否总检，是否同一个人535)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String getExamDepStatus() throws WebException,SQLException{
			UserDTO user = (UserDTO) session.get("username");
			ExaminfoChargingItem	statuss = this.depInspectService.getExamdepresulStatus(model.getExam_num(), model.getCharing_ids(), user.getCenter_num());
				if(null != statuss){
					if(statuss.getExam_doctor_name() == null || statuss.getExam_doctor_name().equals(user.getName())){
						this.message = "OK";
					}else{
						this.message = "NO";
					}
				}else{
					this.message = "OK";
				}
				this.outJsonStrResult(message);
		return NONE;
	}
	
	public String getStatus()  throws WebException,SQLException{
			UserDTO user = (UserDTO) session.get("username");
			ExamInfo fo =  this.depInspectService.getStatuss(model.getExam_num(),user);
			String status = fo.getStatus();
			this.outJsonStrResult(status);
		return NONE;
	}
	
	/**
	 * 普通科室项目匹配科室逻辑  733
	     * @Title: getMateDepLogic   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String getMateDepLogic() throws WebException,SQLException{
		UserDTO user = (UserDTO) session.get("username");
//		JSONArray itemarray = JSONArray.fromObject(this.model.getResultLists());
//		List<DepExamResultDTO> listitem = (List<DepExamResultDTO>) JSONArray.toCollection(itemarray,DepExamResultDTO.class);
//		List<DepExamResultDTO> list = this.depInspectService.getMateDepLogic(listitem, user.getDep_id());
		long dep_id = user.getDep_id();
		if(model.getDep_id() > 0) {
			dep_id = model.getDep_id();
		}
		List<DepLogicDTO> list = this.depInspectService.getDepLpgic(dep_id,model.getSex());
		this.outJsonResult(list);
		return NONE;
	}
	
	/**
	 * 查询科室检查医生 734
	     * @Title: getDepuser   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String getDepuser() throws WebException,SQLException{
		UserDTO user = (UserDTO) session.get("username");
		this.defaultapp = (SystemType) session.get("defaultapp");
		List<UserInfoDTO> list = this.depInspectService.getDepuserBydepId(user,this.model.getType(),defaultapp.getComid());
		this.outJsonResult(list);
		return NONE;
	}
	
	/**
	 * 获取上一次细项检查结果 735
	     * @Title: getOldCommonDetailResult   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String getOldCommonDetailResult() throws WebException,SQLException{
		DepInspectExamIntionDTO dto = this.depInspectService.getOldCommonDetailResult(this.model.getExam_info_id(), this.model.getId());
		this.outJsonResult(dto);
		return NONE;
	}
	/**
	 * 
	     * @Title: getSurverPage   
	     * @Description: TODO(一般科室显示问卷页面586)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String getSurverPage() throws WebException,SQLException{
		model.setC_id(model.getC_id());
		model.setExam_num_x(model.getExam_num_x());
		return SUCCESS;
	}
	
	/**
	 * 获取VIP体检者提示信息777
	     * @Title: getCustomerVipPrompt   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String getCustomerVipPrompt() throws WebException,SQLException{
		UserDTO user = (UserDTO) session.get("username");
		if(user != null){
			List<CustomerVipPrompt> list = this.depInspectService.updateCustomerVipPrompt(user.getUserid());
			this.outJsonResult(list);
		}
		return NONE;
	}
	
	/**
	 * 根据建议名称获取专科建议内容778
	     * @Title: getDepSuggestionList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String getDepSuggestionList() throws WebException,SQLException{
		UserDTO user = (UserDTO) session.get("username");
		List<ExpertSuggestionLibDTO> list = this.depInspectService.getDepSuggestionList(this.model.getExam_result_summary(), user.getDep_id());
		this.outJsonResult(list);
		return NONE;
	}
	
	/**
	 * 1193 普通科室根据体检号查询是否存在项目，或者未检查
	     * @Title: checkDepExaminfoItem   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String checkDepExaminfoItem() throws WebException,SQLException{
		UserDTO user = (UserDTO) session.get("username");
		this.message = this.depInspectService.checkDepExaminfoItem(model.getExam_num(), user.getDep_id(), user.getCenter_num());
		this.outJsonStrResult(this.message);
		return NONE;
	}	
	
	/**
	 * 
	     * @Title: queryIsTiJianType   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: void      
	     * @throws
	 */
	public void queryIsTiJianType() throws WebException, SQLException{
		
		ExamInfoUserDTO eiudto = new ExamInfoUserDTO();
		//根据ID查询此条信息
		eiudto.setExam_num(model.getExam_num());;
		try {
			ExamInfoUserDTO exu = depInspectService.queryIsTiJianType(eiudto);
			this.message= exu.getData_code_children();
		} catch (Exception e) {
			this.message="加载信息失败";
			e.printStackTrace();
			return;
		}
		
		
		try {
			response.setContentType("text/html;charset=utf-8");
			response.getWriter().print(new Gson().toJson(this.message));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 
	     * @Title: queryCountTypeUser   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String queryCountTypeUser() throws WebException, SQLException{
		this.message="error-数据查询错误";
		UserDTO user = (UserDTO) session.get("username");
		DepInspectExamIntionDTO depDto = this.depInspectService.queryCountTypeUser(model,user);
		if(depDto!=null) {
			this.message = "ok-"+depDto.getYijian()+"-"+depDto.getWeijian()+"-"+depDto.getJiancha()+
					"-"+depDto.getQijian()+"-"+depDto.getYanqi();
		}
		this.outJsonStrResult(this.message);
		return NONE;
	}

	/**
	 * 录入全部科室结果首页 2030
	 * @Title: departInspectAllPage
	 * @Description: TODO
	 * @param: @return
	 * @param: @throws WebException
	 * @param: @throws SQLException
	 * @return: String
	 * @throws
	 */
	public String departInspectAllPage() throws WebException,SQLException{
		UserDTO user = (UserDTO) session.get("username");
		this.defaultapp = (SystemType) session.get("defaultapp");
		this.model.setApp_type(this.defaultapp.getComid());
		CenterConfigurationDTO config = this.customerInfoService.getCenterconfigByKey("IS_EXAM_ZHEER_SHOWBAOGAO",this.defaultapp.getComid());//
		if( config != null && !"".equals( config )){
			String carPdf_button = config.getConfig_value().trim();
			model.setCarPdf_button(carPdf_button);
		}
		model.setIs_departinspect_summary_edit(this.customerInfoService.getCenterconfigByKey("IS_DEPARTINSPECT_SUMMARY_EDIT",this.defaultapp.getComid()).getConfig_value());
		String isCheckSensvitiveWord = this.customerInfoService.getCenterconfigByKey("IS_CHECK_SENSITIVE_WORD",this.defaultapp.getComid()).getConfig_value().trim();
		model.setIsCheckSensvitiveWord(isCheckSensvitiveWord);
		String isDepResultShow = this.customerInfoService.getCenterconfigByKey("IS_DEPRESULT_SHOW",this.defaultapp.getComid()).getConfig_value().trim();
		model.setExam_result(isDepResultShow);
		String isDeptType = this.customerInfoService.getCenterconfigByKey("IS_DEPT_TYPE",this.defaultapp.getComid()).getConfig_value().trim();
		String is_dep_edit_questionnaire = this.customerInfoService.getCenterconfigByKey("IS_DEP_EDIT_QUESTIONNAIRE",this.defaultapp.getComid()).getConfig_value().trim();
		model.setIs_dep_edit_questionnaire(is_dep_edit_questionnaire);
		String IS_EXAMRESULTDETAIL_DOCTORPAGE_SHOW = "N";
		try {
			IS_EXAMRESULTDETAIL_DOCTORPAGE_SHOW = this.customerInfoService.getCenterconfigByKey("IS_EXAMRESULTDETAIL_DOCTORPAGE_SHOW",this.defaultapp.getComid()).getConfig_value().trim();
		} catch (Exception e) {
			System.out.println("缺少配置- IS_EXAMRESULTDETAIL_DOCTORPAGE_SHOW");
		}
		model.setIsExamResultDetailDoctorPageShow(IS_EXAMRESULTDETAIL_DOCTORPAGE_SHOW);
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("2030");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return SUCCESS;

	}

}

