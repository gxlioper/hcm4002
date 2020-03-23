package com.hjw.zyb.action;

import java.sql.SQLException;
import java.util.List;

import com.hjw.util.DateTimeUtil;
import com.hjw.wst.DTO.CustomerExamDTO;
import com.hjw.wst.DTO.ExamSummaryDTO;
import com.hjw.wst.DTO.ExaminfoDiseaseDTO;
import com.hjw.wst.DTO.UserDTO;
import com.hjw.wst.domain.ExamInfo;
import com.hjw.wst.domain.ExamSummary;
import com.hjw.wst.domain.SysLog;
import com.hjw.wst.domain.WebResrelAtionship;
import com.hjw.wst.domain.WebUserInfo;
import com.hjw.wst.model.ExamSummaryModel;
import com.hjw.wst.service.CustomerInfoService;
import com.hjw.wst.service.ExamSummaryService;
import com.hjw.wst.service.SyslogService;
import com.hjw.wst.service.UserInfoService;
import com.hjw.zyb.DTO.ExamInfoExtDTO;
import com.hjw.zyb.DTO.ZybCheckcriterionDTO;
import com.hjw.zyb.DTO.ZybExamOccuhazardfactorsDTO;
import com.hjw.zyb.DTO.ZybExamSummaryResultDTO;
import com.hjw.zyb.DTO.ZybexaminationresultDTO;
import com.hjw.zyb.DTO.ZyboccucontraindicationDTO;
import com.hjw.zyb.DTO.ZyboccudiseaseDTO;
import com.hjw.zyb.service.ZybCustomerInfoService;
import com.hjw.zyb.service.ZybExamSummaryService;
import com.opensymphony.xwork2.ModelDriven;
import com.synjones.framework.exception.WebException;
import com.synjones.framework.web.action.BaseAction;

import net.sf.json.JSONArray;

public class ZybExamSummaryAction extends BaseAction implements ModelDriven{
	private static final long serialVersionUID = 8217814910220881058L;
	private ExamSummaryModel model = new ExamSummaryModel();
	private ExamSummaryService examSummaryService;
	private UserInfoService userInfoService;
	private ZybExamSummaryService zybExamSummaryService;
	private CustomerInfoService customerInfoService;
	private SyslogService syslogService;
	private ZybCustomerInfoService zybCustomerInfoService;
	
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
	public void setExamSummaryService(ExamSummaryService examSummaryService) {
		this.examSummaryService = examSummaryService;
	}

	public void setUserInfoService(UserInfoService userInfoService) {
		this.userInfoService = userInfoService;
	}
	public void setCustomerInfoService(CustomerInfoService customerInfoService) {
		this.customerInfoService = customerInfoService;
	}

	public void setSyslogService(SyslogService syslogService) {
		this.syslogService = syslogService;
	}
	public void setZybExamSummaryService(ZybExamSummaryService zybExamSummaryService) {
		this.zybExamSummaryService = zybExamSummaryService;
	}
	public void setZybCustomerInfoService(ZybCustomerInfoService zybCustomerInfoService) {
		this.zybCustomerInfoService = zybCustomerInfoService;
	}
	@Override
	public Object getModel() {
		return model;
	}

	public void setModel(ExamSummaryModel model) {
		this.model = model;
	}
	
	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	/**
	 * zyb406  获取职业病总检页面
	     * @Title: getZybExamSummaryPage   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	*/
	public String getZybExamSummaryPage() throws WebException{
		//资源
		UserDTO user =(UserDTO) session.get("username");
		List<WebResrelAtionship>  web = user.getWebResource();
		if(web!=null){
			for (int i = 0; i < web.size(); i++) {
				if(web.get(i).getRes_code().equals("RS001")){
					this.model.setWebResource(web.get(i).getDatavalue());
					break;
				}
			}
		}
		String isExamSummaryEdit = this.customerInfoService.getCenterconfigByKey("IS_EXAM_SUMARRY_EDIT", user.getCenter_num()).getConfig_value().trim();
		this.model.setIsExamSummaryEdit(isExamSummaryEdit);
		this.model.setApp_type("2");
		String isDiseaseMerge = this.customerInfoService.getCenterconfigByKey("IS_DISEASE_MERGE", user.getCenter_num()).getConfig_value();
		this.model.setIsDiseaseMerge(isDiseaseMerge);
		String examResultStyle = this.customerInfoService.getCenterconfigByKey("EXAM_RESULT_STYLE", user.getCenter_num()).getConfig_value().trim();
		this.model.setExamResultStyle(examResultStyle);
		String isExamSuggest = this.customerInfoService.getCenterconfigByKey("IS_EXAM_SUGGEST", user.getCenter_num()).getConfig_value().trim();
		this.model.setIsExamSuggest(isExamSuggest);
		String isExamSummaryNewDiseasePageShow = this.customerInfoService.getCenterconfigByKey("IS_EXAMSUMMARY_NEWDISEASEPAGE_SHOW", user.getCenter_num()).getConfig_value().trim();
		this.model.setIsExamSummaryNewDiseasePageShow(isExamSummaryNewDiseasePageShow);
		String isExamSummaryNew = this.customerInfoService.getCenterconfigByKey("IS_EXAM_SUMARRY_NEW", user.getCenter_num()).getConfig_value().trim();
		this.model.setIsExamSummaryNew(isExamSummaryNew);
		this.model.setReport_print_type(this.customerInfoService.getCenterconfigByKey("REPORT_PRINT_TYPE", user.getCenter_num()).getConfig_value().trim());
		this.model.setZyb_report_print_type(this.customerInfoService.getCenterconfigByKey("ZYB_REPORT_PRINT_TYPE", user.getCenter_num()).getConfig_value().trim());
		CustomerExamDTO customerInfo = this.zybExamSummaryService.getCustomerInfo(this.model.getExam_num(),user);
		this.model.setExam_info_id(customerInfo.getId());
		this.model.setArch_num(customerInfo.getArch_num());
//		this.model.setExam_num(customerInfo.getexa);
		this.model.setSex(customerInfo.getSex());
		this.model.setAge(customerInfo.getAge());
		this.model.setUser_name(customerInfo.getUser_name());
		this.model.setSet_name(customerInfo.getSet_name());
		this.model.setCompany(customerInfo.getCompany());
		
		ExamInfoExtDTO ext = this.zybCustomerInfoService.getExamInfoExtForExamNum(this.model.getExam_num());
		if(ext != null){
			model.setOccutypeofworkid(ext.getTypeofwork_name());
			model.setOccusectorid(ext.getIndustry_name());
			model.setOccutypeofwork(ext.getOccutypeofwork());
			model.setOccusector(ext.getOccusector());
			model.setJoinDatetime(ext.getJoinDatetime());
			model.setEmployeeage(ext.getEmployeeage());
			model.setDamage(ext.getDamage());
			model.setEmployeemonth(ext.getEmployeemonth());
			model.setDammonth(ext.getDammonth());
		}
		
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("zyb406");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return SUCCESS;
	}
	
	/**
	 * zyb407 获取总检室健康体检和职业病体检综合页面
	     * @Title: getExamSummaryPageAll   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String getExamSummaryPageAll() throws WebException{
		return SUCCESS;
	}
	
	/**
	 * zyb408 获取生成职业病的阳性发现列表
	     * @Title: getZybExamDiseaseList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String getZybExamDiseaseList() throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		String isDiseaseLogicDep = this.customerInfoService.getCenterconfigByKey("IS_DISEASE_LOGIC_DEP", user.getCenter_num()).getConfig_value();
		String isExamSuggest = this.customerInfoService.getCenterconfigByKey("IS_EXAM_SUGGEST", user.getCenter_num()).getConfig_value();
		String IS_DISEASE_SUG_CENTER = "N";
		try {
			IS_DISEASE_SUG_CENTER = this.customerInfoService.getCenterconfigByKey("IS_DISEASE_SUG_CENTER", user.getCenter_num()).getConfig_value().trim();
		} catch (Exception e) {
		}
		List<ExaminfoDiseaseDTO> list = null;
		ExamSummaryDTO exam_summary = this.examSummaryService.getFinalExamResult(this.model.getExam_num(),model.getApp_type(),user);
		if("Y".equals(this.model.getSug_flag())){//重新生成建议，匹配疾病逻辑
			if(isDiseaseLogicDep != null && "X".equals(isDiseaseLogicDep.trim())){//使用新疾病逻辑
				list = this.zybExamSummaryService.createNewZybExamInfoDiseaseSingle(this.model.getExam_num(), isExamSuggest, user.getCenter_num(),IS_DISEASE_SUG_CENTER);
			}else if(isDiseaseLogicDep != null && "Y".equals(isDiseaseLogicDep.trim())){//使用旧关联科室疾病逻辑
				list = this.zybExamSummaryService.createZybExamInfoDiseaseDep(this.model.getExam_num(),user.getCenter_num(),IS_DISEASE_SUG_CENTER);
			}else{
				list = this.zybExamSummaryService.createZybExamInfoDisease(this.model.getExam_num(),isExamSuggest, user.getCenter_num(),IS_DISEASE_SUG_CENTER);
			}
		}else{//先查询是否总检，总检过的取旧数据,未总检的在匹配疾病逻辑,生成阳性发现。
			list = this.examSummaryService.getExamInfoDisease(this.model.getExam_num(),this.model.getApp_type());
			if(!exam_summary.getZyb_final_status().equals("Z") && (list == null || list.size() == 0)){
				if(isDiseaseLogicDep != null && "X".equals(isDiseaseLogicDep.trim())){//使用新疾病逻辑
					list = this.zybExamSummaryService.createNewZybExamInfoDiseaseSingle(this.model.getExam_num(), isExamSuggest, user.getCenter_num(),IS_DISEASE_SUG_CENTER);
				}else if(isDiseaseLogicDep != null && "Y".equals(isDiseaseLogicDep.trim())){//使用旧关联科室疾病逻辑
					list = this.zybExamSummaryService.createZybExamInfoDiseaseDep(this.model.getExam_num(),user.getCenter_num(),IS_DISEASE_SUG_CENTER);
				}else{
					list = this.zybExamSummaryService.createZybExamInfoDisease(this.model.getExam_num(),isExamSuggest, user.getCenter_num(),IS_DISEASE_SUG_CENTER);
				}
			}
		}
		this.outJsonResult(list);
		return NONE;
	}
	
	/**
	 * zyb409 获取总检体检结论字典列表
	     * @Title: getZybExaminationResultList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String getZybExaminationResultList() throws WebException{
		List<ZybexaminationresultDTO> list = this.zybExamSummaryService.getZybExaminationResultList(this.model.getExam_result());
		this.outJsonResult(list);
		return NONE;
	}
	
	/**
	 * zyb410 获取职业病总检室新增阳性页面
	     * @Title: showNewZybExamDisease   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String showNewZybExamDisease() throws WebException{
		return SUCCESS;
	}
	
	/**
	 * zyb412查询体检者体检类型
	     * @Title: getExamInfoAppType   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String getExamInfoAppType() throws WebException,SQLException{
		UserDTO user = (UserDTO) session.get("username");
		this.message = this.examSummaryService.getExamInfoAppType(model.getExam_num(), user.getCenter_num());
		this.outJsonStrResult(this.message);
		return NONE;
	}

	/**
	 * zyb423 总检室获取选择检查结果页面
	     * @Title: getExamSummaryExaminationResultPage   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String getExamSummaryExaminationResultPage() throws WebException{
		return SUCCESS;
	}
	
	/**
	 * zyb424 根据职业危害因素查询职业禁忌症列表
	     * @Title: getExamSummaryOccucontraindicationList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String getExamSummaryOccucontraindicationList() throws WebException,SQLException{
		JSONArray occus = JSONArray.fromObject(this.model.getExamOccuhazardfactorsLists());
		List<ZybExamOccuhazardfactorsDTO> listoccus = (List<ZybExamOccuhazardfactorsDTO>) JSONArray.toCollection(occus,ZybExamOccuhazardfactorsDTO.class);
		model.setExamOccuhazardfactorsList(listoccus);
		List<ZyboccucontraindicationDTO> list = this.zybExamSummaryService.getExamSummaryOccucontraindicationList(model);
		this.outJsonResult(list);
		return NONE;
	}
	
	/**
	 * zyb425 根据职业危害因素查询职业病信息列表
	     * @Title: getExamSummaryOccudisease   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String getExamSummaryOccudiseaseList() throws WebException,SQLException{
		JSONArray occus = JSONArray.fromObject(this.model.getExamOccuhazardfactorsLists());
		List<ZybExamOccuhazardfactorsDTO> listoccus = (List<ZybExamOccuhazardfactorsDTO>) JSONArray.toCollection(occus,ZybExamOccuhazardfactorsDTO.class);
		model.setExamOccuhazardfactorsList(listoccus);
		List<ZyboccudiseaseDTO> list = this.zybExamSummaryService.getExamSummaryOccudisease(model);
		this.outJsonResult(list);
		return NONE;
	}
	
	/**
	 * zyb426 根据职业危害因素查询检查依据信息
	     * @Title: getExamSummaryCheckcriterionList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String getExamSummaryCheckcriterionList() throws WebException,SQLException{
		List<ZybCheckcriterionDTO> list = this.zybExamSummaryService.getExamSummaryCheckcriterionList(model);
		this.outJsonResult(list);
		return NONE;
	}
	
	/**
	 * zyb427 总检保存职业病检查结果
	     * @Title: saveZybExamSummaryResult   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String saveZybExamSummaryResult() throws WebException,SQLException{
		UserDTO user =(UserDTO) session.get("username");
		this.message = this.zybExamSummaryService.saveZybExamSummaryResult(model, user.getUserid());
		this.outJsonStrResult(this.message);
		return NONE;
	}
	
	/**
	 * zyb428  获取总检保存的职业病检查结果信息
	     * @Title: getZybExamSummaryResult   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String getZybExamSummaryResult() throws WebException,SQLException{
		ZybExamSummaryResultDTO resultdto = this.zybExamSummaryService.getZybExamSummaryResult(model.getExam_num());
		this.outJsonResult(resultdto);
		return NONE;
	}
	
	/**
	 * zyb429 保存职业病总检信息
	     * @Title: saveZybExamSummary   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String saveZybExamSummary() throws WebException,SQLException{
		UserDTO user = (UserDTO) session.get("username");
		if(user != null){
			JSONArray disease = JSONArray.fromObject(this.model.getExaminfoDiseases());
			List<ExaminfoDiseaseDTO> listdisease = (List<ExaminfoDiseaseDTO>) JSONArray.toCollection(disease,ExaminfoDiseaseDTO.class);
			this.model.setExaminfoDisease(listdisease);
			ExamSummary examSummary = this.examSummaryService.getExamSummaryById(model.getExam_num(),this.model.getApp_type());
			if(examSummary != null && examSummary.getExam_doctor_id() != 0 && examSummary.getExam_doctor_id() != user.getUserid()){
				WebUserInfo userinfo = this.userInfoService.loadUserInfo(examSummary.getExam_doctor_id());
				this.message = "error-对不起,您不是此体检人的终检医生,不能终检或保存!请联系(" + userinfo.getChi_Name() + ")终检或保存!";
			}else{
				String zongjian = this.zybExamSummaryService.saveZybExamSummary(model,examSummary, user,model.getExam_status());
				this.message=zongjian;//+huiyuan;
				SysLog sl =  new SysLog();
				sl.setCenter_num(user.getCenter_num());
				sl.setUserid(user.getUserid()+"");
				sl.setOper_type("1");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
				sl.setXtgnid("");//可不填写
				sl.setXtgnid2("702");//子功能id 必须填写
				sl.setExplain("");//操作说明
				syslogService.saveSysLog(sl);
			}
			
		}else{
			this.message = "error-请重新登录!";
		}
		this.outJsonStrResult(this.message);
		return NONE;
	}
	
	/**
	 * zyb430 职业病取消总检
	     * @Title: cancelZybExamSummary   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String cancelZybExamSummary() throws WebException,SQLException{
		UserDTO user = (UserDTO) session.get("username");
		ExamInfo examInfo = this.examSummaryService.getExamInfoByExamNum(this.model.getExam_num());
		if(examInfo == null){
			this.message = "error-该体检号体检信息";
		}else{
			if(!examInfo.getFinal_doctor().equals(user.getName())){
				this.message = "error-您不是该体检人的终检医生,请联系医生(" + examInfo.getFinal_doctor() + ")取消终检!";
			}else{
				ExamSummary examSummary = this.examSummaryService.getExamSummaryById(model.getExam_num(),this.model.getApp_type());
				examInfo.setStatus("J");// 将状态设置成检查中
				examInfo.setFinal_date(null);
				examInfo.setFinal_doctor(null);
				this.examSummaryService.updateExamInfo(examInfo);
					
				examSummary.setExam_doctor_id(0);
				examSummary.setFinal_status("J");
				examSummary.setFinal_time(null);
				this.examSummaryService.updateExamSummary(examSummary);
				this.message = "ok-取消职业病总检成功!";
					
				SysLog sl =  new SysLog();
				sl.setCenter_num(user.getCenter_num());
				sl.setUserid(user.getUserid()+"");
				sl.setOper_type("2");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
				sl.setXtgnid("");//可不填写
				sl.setXtgnid2("703");//子功能id 必须填写
				sl.setExplain(model.getExam_num());//操作说明
				syslogService.saveSysLog(sl);
			}
		}
		this.outJsonStrResult(this.message);
		return NONE;
	}
	
	/**
	 * zyb431 职业病审核与取消审核
	     * @Title: approveZybExamSummary   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String approveZybExamSummary() throws WebException,SQLException{
		UserDTO user = (UserDTO) session.get("username");
		ExamSummary examSummary = this.examSummaryService.getExamSummaryById(model.getExam_num(),this.model.getApp_type());
		if(examSummary == null){
			this.message = "error-总检信息不存在,请重新进入总检页面!";
		}else{
			
			if("B".equals(this.model.getApprove_status())){//审核 将B改为 A
				String is_apply_update = this.customerInfoService.getCenterconfigByKey("IS_APPLY_UPDATE", user.getCenter_num()).getConfig_value().trim();
				if("Y".equals(is_apply_update)){//审核保存总检信息
					JSONArray disease = JSONArray.fromObject(this.model.getExaminfoDiseases());
					List<ExaminfoDiseaseDTO> listdisease = (List<ExaminfoDiseaseDTO>) JSONArray.toCollection(disease,ExaminfoDiseaseDTO.class);
					this.examSummaryService.saveExamDiseaseList(model.getExam_num(),model.getApp_type(),listdisease, user);
				}
				examSummary.setApprove_status("A");
				examSummary.setCheck_doc(user.getUserid());
				examSummary.setCheck_time(DateTimeUtil.parse());
				examSummary.setUpdater(user.getUserid());
				examSummary.setRead_status(0);
				examSummary.setUpdate_time(DateTimeUtil.parse());
				this.examSummaryService.updateExamSummary(examSummary);
				this.message = "ok-职业病审核成功!";
					
				SysLog sl =  new SysLog();
				sl.setCenter_num(user.getCenter_num());
				sl.setUserid(user.getUserid()+"");
				sl.setOper_type("2");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
				sl.setXtgnid("");//可不填写
				sl.setXtgnid2("704");//子功能id 必须填写
				sl.setExplain("审核");//操作说明
				syslogService.saveSysLog(sl);
			}else if("A".equals(this.model.getApprove_status())){//取消审核
				if(user.getUserid() != examSummary.getCheck_doc()){
					WebUserInfo userinfo = this.userInfoService.loadUserInfo(examSummary.getCheck_doc());
					this.message = "error-对不起,您不是此总检信息的审核者,不能取消审核!请联系(" + userinfo.getChi_Name() + ")取消审核!";
				}else if("1".equals(examSummary.getCensoring_status())){
					this.message = "error-对不起,此总检信息已复审,不能取消审核!";
				}else{
					examSummary.setApprove_status("B");
					examSummary.setCheck_doc(0);
					examSummary.setCheck_time(null);
					examSummary.setUpdater(user.getUserid());
					examSummary.setUpdate_time(DateTimeUtil.parse());
					examSummary.setRead_status(0);
					this.examSummaryService.updateExamSummary(examSummary);
					this.message = "ok-取消职业病审核成功!";
						
					SysLog sl =  new SysLog();
					sl.setCenter_num(user.getCenter_num());
					sl.setUserid(user.getUserid()+"");
					sl.setOper_type("2");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
					sl.setXtgnid("");//可不填写
					sl.setXtgnid2("704");//子功能id 必须填写
					sl.setExplain("取消审核");//操作说明
					syslogService.saveSysLog(sl);
				}
			}
		}
		this.outJsonStrResult(this.message);
		return NONE;
	}
}
