package com.hjw.wst.action;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.hjw.wst.DTO.*;
import org.hsqldb.lib.StringUtil;

import com.hjw.crm.service.CrmMemberManageService;
import com.hjw.util.DateTimeUtil;
import com.hjw.wst.domain.ExamFlowConfig;
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
import com.opensymphony.xwork2.ModelDriven;
import com.synjones.framework.aop.SystemType;
import com.synjones.framework.exception.ServiceException;
import com.synjones.framework.exception.WebException;
import com.synjones.framework.web.action.BaseAction;

import net.sf.json.JSONArray;

/**
 * 总检室功能
     * @Title:  火箭蛙体检管理系统   
     * @Package com.hjw.wst.action   
     * @Description:  
     * @author: dangqi     
     * @date:   2016年11月15日 上午9:40:36   
     * @version V2.0.0.0
 */
public class ExamSummaryAction extends BaseAction implements ModelDriven{

	private static final long serialVersionUID = 8217814910220881058L;
	private ExamSummaryModel model = new ExamSummaryModel();
	private ExamSummaryService examSummaryService;
	private CustomerInfoService customerInfoService;
	private UserInfoService userInfoService;
	private SyslogService syslogService;
	private String exam_type_s;//体检类型
	private String is_batch_examine_s;//批量审核按钮配置
	
	private CrmMemberManageService crmMemberManageService;
	
	
	public String getIs_batch_examine_s() {
		return is_batch_examine_s;
	}
	public void setIs_batch_examine_s(String is_batch_examine_s) {
		this.is_batch_examine_s = is_batch_examine_s;
	}
	public CrmMemberManageService getCrmMemberManageService() {
		return crmMemberManageService;
	}
	public void setCrmMemberManageService(CrmMemberManageService crmMemberManageService) {
		this.crmMemberManageService = crmMemberManageService;
	}

	private int rows = 15; // easyui每页显示条数
	private String sort;
	private String order;
	private String teamAmountViewFlag;
	public String getSort() {
		return sort;
	}
	
	public String getExam_type_s() {
		return exam_type_s;
	}
	public void setExam_type_s(String exam_type_s) {
		this.exam_type_s = exam_type_s;
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

	public void setCustomerInfoService(CustomerInfoService customerInfoService) {
		this.customerInfoService = customerInfoService;
	}

	public void setUserInfoService(UserInfoService userInfoService) {
		this.userInfoService = userInfoService;
	}

	public void setSyslogService(SyslogService syslogService) {
		this.syslogService = syslogService;
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

	public String getTeamAmountViewFlag() {
		return teamAmountViewFlag;
	}
	public void setTeamAmountViewFlag(String teamAmountViewFlag) {
		this.teamAmountViewFlag = teamAmountViewFlag;
	}
	/**
	 * 获取总检页面  392
	     * @Title: getExamSummary   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String getExamSummaryPage() throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		CenterConfigurationDTO config = this.customerInfoService.getCenterconfigByKey("IS_EXAM_ZHEER_SHOWBAOGAO", user.getCenter_num());//
		if( config != null && !"".equals( config )){
			String carPdf_button = config.getConfig_value().trim();
			model.setCarPdf_button(carPdf_button);
		}
		//资源
		List<WebResrelAtionship>  web = user.getWebResource();
		if(web!=null){
			for (int i = 0; i < web.size(); i++) {
				if(web.get(i).getRes_code().equals("RS001")){
					this.model.setWebResource(web.get(i).getDatavalue());
					continue;
				}else if(web.get(i).getRes_code().equals("RS022")){
					this.model.setT_wbeResource(web.get(i).getDatavalue());
					continue;
				}else if(web.get(i).getRes_code().equals("RS025")){
					this.model.setCancel_wbeResource(web.get(i).getDatavalue());
					continue;
				}else if(web.get(i).getRes_code().equals("RS054")){
					this.model.setIs_update_critical(web.get(i).getDatavalue());
					continue;
				}
			}
		}
		String isExamSummaryEdit = this.customerInfoService.getCenterconfigByKey("IS_EXAM_SUMARRY_EDIT", user.getCenter_num()).getConfig_value().trim();
		this.model.setIsExamSummaryEdit(isExamSummaryEdit);
		String isDiseaseMerge = this.customerInfoService.getCenterconfigByKey("IS_DISEASE_MERGE", user.getCenter_num()).getConfig_value();
		this.model.setIsDiseaseMerge(isDiseaseMerge);
		String examResultStyle = this.customerInfoService.getCenterconfigByKey("EXAM_RESULT_STYLE", user.getCenter_num()).getConfig_value().trim();
		this.model.setExamResultStyle(examResultStyle);
		String isExamSuggest = this.customerInfoService.getCenterconfigByKey("IS_EXAM_SUGGEST", user.getCenter_num()).getConfig_value().trim();
		this.model.setIsExamSuggest(isExamSuggest);
		String examSummaryPacsUrl = this.customerInfoService.getCenterconfigByKey("EXAM_SUMMARY_PACS_URL", user.getCenter_num()).getConfig_value().trim();
		this.model.setExamSummaryPacsUrl(examSummaryPacsUrl);
		String isExamSummaryNewDiseasePageShow = this.customerInfoService.getCenterconfigByKey("IS_EXAMSUMMARY_NEWDISEASEPAGE_SHOW", user.getCenter_num()).getConfig_value().trim();
		this.model.setIsExamSummaryNewDiseasePageShow(isExamSummaryNewDiseasePageShow);
		String isExamSummaryNew = this.customerInfoService.getCenterconfigByKey("IS_EXAM_SUMARRY_NEW", user.getCenter_num()).getConfig_value().trim();
		this.model.setIsExamSummaryNew(isExamSummaryNew);
		this.model.setReport_print_type(this.customerInfoService.getCenterconfigByKey("REPORT_PRINT_TYPE", user.getCenter_num()).getConfig_value().trim());
		this.model.setApp_type("1");
		String EXAM_SUMMARY_STYLE = this.customerInfoService.getCenterconfigByKey("EXAM_SUMMARY_STYLE", user.getCenter_num()).getConfig_value();
		String IS_EXAM_RESULT_CANFINAL = this.customerInfoService.getCenterconfigByKey("IS_EXAM_RESULT_CANFINAL", user.getCenter_num()).getConfig_value();
		this.model.setIs_exam_result_canfinal(IS_EXAM_RESULT_CANFINAL);
		
		String IS_FINAL_HISTORY_SHOW = "N";
		try {
			IS_FINAL_HISTORY_SHOW = this.customerInfoService.getCenterconfigByKey("IS_FINAL_HISTORY_SHOW", user.getCenter_num()).getConfig_value().trim();
		} catch (Exception e) {
		}
		this.model.setIs_final_history_show(IS_FINAL_HISTORY_SHOW);
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("392");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		if(EXAM_SUMMARY_STYLE != null && "3".equals(EXAM_SUMMARY_STYLE.trim())){
			return "success_new";
		}else if(EXAM_SUMMARY_STYLE != null && "2".equals(EXAM_SUMMARY_STYLE.trim())){
			return "success_180";
		}
		return SUCCESS;
	}
	
	/**
	 * 获取普通科室检查结果 393
	     * @Title: getPtResultList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String getPtDepResultList() throws WebException,SQLException{
		UserDTO user = (UserDTO) session.get("username");
		List<DepExamResultDTO> list = this.examSummaryService.getPtResultList(this.model.getExam_num(),this.model.getApp_type(), user.getCenter_num());
		this.outJsonResult(list);
		return NONE;
	}
	
	/**
	 * 获取检验科室检查结果 394
	     * @Title: getHyResultList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String getHyDepResultList() throws WebException,SQLException{
		UserDTO user = (UserDTO) session.get("username");
		String IS_EXAM_RESULT_CITEM = this.customerInfoService.getCenterconfigByKey("IS_EXAM_RESULT_CITEM", user.getCenter_num()).getConfig_value();
		List<DepExamResultDTO> list = this.examSummaryService.getHyResultList(this.model.getExam_num(),IS_EXAM_RESULT_CITEM,this.model.getApp_type(), user.getCenter_num());
		this.outJsonResult(list);
		return NONE;
	}
	
	/**
	 * 获取影像科室检查结果 395
	     * @Title: getYxResultList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String getYxDepResultList() throws WebException,SQLException{
		UserDTO user = (UserDTO) session.get("username");
		String isShowSamplename = this.customerInfoService.getCenterconfigByKey("IS_SHOW_SAMPLENAME", user.getCenter_num()).getConfig_value().trim();
		List<DepExamResultDTO> list = this.examSummaryService.getYxResultList(this.model.getExam_num(),this.model.getApp_type(),isShowSamplename, user.getCenter_num());
		this.outJsonResult(list);
		return NONE;
	}
	/**
	 * 获取体检报告图片地址400
	     * @Title: getYxResultList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String gettjbgList() throws WebException,SQLException{
		UserDTO user = (UserDTO) session.get("username");
		String isShowSamplename = this.customerInfoService.getCenterconfigByKey("IS_SHOW_SAMPLENAME", user.getCenter_num()).getConfig_value().trim();
		List<ExamInfoDTO> list = this.examSummaryService.gettjbgList(this.model.getExam_num(),this.model.getApp_type(),isShowSamplename);
		if(null!=list&&list.size()>0){
			this.outJsonResult(list.get(0));
		}
		return NONE;
	}
	
	/**
	 * 获取与汇总体检人员的阳性发现信息列表  396
	     * @Title: getExamDiseaseList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String getExamDiseaseList() throws WebException,SQLException{
		UserDTO user = (UserDTO) session.get("username");
		List<ExaminfoDiseaseDTO> list = null;
		ExamSummaryDTO exam_summary = this.examSummaryService.getFinalExamResult(this.model.getExam_num(),"1",user);
		String isDiseaseLogicDep = this.customerInfoService.getCenterconfigByKey("IS_DISEASE_LOGIC_DEP", user.getCenter_num()).getConfig_value();
		String siExamDiseaseSeq = this.customerInfoService.getCenterconfigByKey("IS_EXAM_DISEASE_SEQ", user.getCenter_num()).getConfig_value();
		String isExamDiseaseItem = this.customerInfoService.getCenterconfigByKey("IS_EXAM_DISEASE_ITEM", user.getCenter_num()).getConfig_value();
		String isExamSuggest = this.customerInfoService.getCenterconfigByKey("IS_EXAM_SUGGEST", user.getCenter_num()).getConfig_value();
		String defalutDiseaseId = this.customerInfoService.getCenterconfigByKey("EXAM_SUMMARY_DEFAULT_DISEASEIDS", user.getCenter_num()).getConfig_value();
		String isExamsummaryDiseaseDepLogic = this.customerInfoService.getCenterconfigByKey("IS_EXAMSUMMARY_DISEASE_DEP_LOGIC", user.getCenter_num()).getConfig_value();
		String isUseCompositeLogic = this.customerInfoService.getCenterconfigByKey("IS_USE_COMPOSITE_LOGIC", user.getCenter_num()).getConfig_value();
		String EXAM_SUMMARY_STYLE = this.customerInfoService.getCenterconfigByKey("EXAM_SUMMARY_STYLE", user.getCenter_num()).getConfig_value();
		String IS_DISEASE_SUG_CENTER = "N";
		try {
			IS_DISEASE_SUG_CENTER = this.customerInfoService.getCenterconfigByKey("IS_DISEASE_SUG_CENTER", user.getCenter_num()).getConfig_value().trim();
		} catch (Exception e) {
		}
		if("Y".equals(this.model.getSug_flag())){//重新生成建议，匹配疾病逻辑
			if(isDiseaseLogicDep != null && "X".equals(isDiseaseLogicDep.trim())){//使用新疾病逻辑
				if(EXAM_SUMMARY_STYLE != null && "3".equals(EXAM_SUMMARY_STYLE.trim())){//新疾病逻辑总检页面，直接查询单项阳性结果表
					list = this.examSummaryService.createNewExamInfoDisease(this.model.getExam_num(), isExamSuggest,isUseCompositeLogic, user.getCenter_num(),IS_DISEASE_SUG_CENTER);
				}else{
					list = this.examSummaryService.createNewExamInfoDiseaseSingle(this.model.getExam_num(), isExamSuggest, user.getCenter_num(),IS_DISEASE_SUG_CENTER);
				}
			}else if(isDiseaseLogicDep != null && "Y".equals(isDiseaseLogicDep.trim())){//使用旧关联科室疾病逻辑
				list = this.examSummaryService.createExamInfoDiseaseDep(this.model.getExam_num(), siExamDiseaseSeq,isExamDiseaseItem,isExamSuggest, user.getCenter_num(),IS_DISEASE_SUG_CENTER);
			}else{
				list = this.examSummaryService.createExamInfoDisease(this.model.getExam_num(),isExamSuggest, user.getCenter_num(),IS_DISEASE_SUG_CENTER);
			}
			if(list.size() <= 0){//返回默认疾病列表
				if(!"".equals(defalutDiseaseId)){
					list = this.examSummaryService.getDefalutDiseaseList(this.model.getExam_num(),isExamSuggest,defalutDiseaseId,user.getCenter_num(),IS_DISEASE_SUG_CENTER);
				}
			}
		}else{//先查询是否总检，总检过的取旧数据,未总检的在匹配疾病逻辑,生成阳性发现。
			list = this.examSummaryService.getExamInfoDisease(this.model.getExam_num(),"1");
			if(!exam_summary.getExam_status().equals("Z") && (list == null || list.size() == 0)){
				if(isDiseaseLogicDep != null && "X".equals(isDiseaseLogicDep.trim())){//使用新疾病逻辑
					if(EXAM_SUMMARY_STYLE != null && "3".equals(EXAM_SUMMARY_STYLE.trim())){//新疾病逻辑总检页面，直接查询单项阳性结果表
						list = this.examSummaryService.createNewExamInfoDisease(this.model.getExam_num(), isExamSuggest,isUseCompositeLogic, user.getCenter_num(),IS_DISEASE_SUG_CENTER);
					}else{
						list = this.examSummaryService.createNewExamInfoDiseaseSingle(this.model.getExam_num(), isExamSuggest, user.getCenter_num(),IS_DISEASE_SUG_CENTER);
					}
				}else if(isDiseaseLogicDep != null && "Y".equals(isDiseaseLogicDep.trim())){
					list = this.examSummaryService.createExamInfoDiseaseDep(this.model.getExam_num(), siExamDiseaseSeq,isExamDiseaseItem,isExamSuggest, user.getCenter_num(),IS_DISEASE_SUG_CENTER);
				}else{
					list = this.examSummaryService.createExamInfoDisease(this.model.getExam_num(),isExamSuggest, user.getCenter_num(),IS_DISEASE_SUG_CENTER);
				}
				if(list.size() <= 0){//返回默认疾病列表
					if(!"".equals(defalutDiseaseId)){
						list = this.examSummaryService.getDefalutDiseaseList(this.model.getExam_num(),isExamSuggest,defalutDiseaseId,user.getCenter_num(),IS_DISEASE_SUG_CENTER);
					}
				}
			}
		}
		this.outJsonResult(list);
		return NONE;
	}
	
	/**
	 * 获取总检结论 与总检状态  397
	     * @Title: getFinalExamResult   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String getFinalExamResult() throws WebException,SQLException{
		UserDTO user = (UserDTO) session.get("username");
		String isExamSummary = this.customerInfoService.getCenterconfigByKey("IS_EXAM_SUMMARY", user.getCenter_num()).getConfig_value().trim();
		String isShowRefvalue = this.customerInfoService.getCenterconfigByKey("IS_SHOW_REFVALUE", user.getCenter_num()).getConfig_value().trim();
		String isShowSamplename = this.customerInfoService.getCenterconfigByKey("IS_SHOW_SAMPLENAME", user.getCenter_num()).getConfig_value().trim();
		
		String examResultStyle = this.customerInfoService.getCenterconfigByKey("EXAM_RESULT_STYLE", user.getCenter_num()).getConfig_value().trim();
		String examResultShowItemId = this.customerInfoService.getCenterconfigByKey("EXAM_RESULT_SHOW_ITEMID", user.getCenter_num()).getConfig_value();
		String examSummaryResultIn = this.customerInfoService.getCenterconfigByKey("EXAMSUMMARY_RESULT_IN", user.getCenter_num()).getConfig_value();
		ExamSummaryDTO examSummary = this.examSummaryService.getFinalExamResult(this.model.getExam_num(),this.model.getApp_type(),user);
		if((examSummary == null)||(examSummary.getId()<=0)){
			if(examSummary == null){
				examSummary = new ExamSummaryDTO();
			}
			String final_result = "";
			if("1".equals(examResultStyle)){ //普通样式
				final_result = this.examSummaryService.createFinalExamResult(this.model.getExam_num(), isExamSummary,isShowRefvalue,isShowSamplename,examResultShowItemId,examSummaryResultIn,this.model.getApp_type(), user.getCenter_num());
			}else if("2".equals(examResultStyle)){//浙医二样式
				final_result = this.examSummaryService.createFinalExamResultZY2(this.model.getExam_num(), isExamSummary,isShowRefvalue,isShowSamplename,examResultShowItemId,examSummaryResultIn,this.model.getApp_type(), user.getCenter_num());
			}else if("3".equals(examResultStyle)){//江苏机关医院
				final_result = this.examSummaryService.createFinalExamResultJGYY(this.model.getExam_num(), isExamSummary,isShowRefvalue,isShowSamplename,examResultShowItemId,examSummaryResultIn,this.model.getApp_type(), user.getCenter_num());
			}else if("4".equals(examResultStyle)){//西苑医院
				final_result = this.examSummaryService.createFinalExamResultXYYY(this.model.getExam_num(), isExamSummary,isShowRefvalue,isShowSamplename,examResultShowItemId,examSummaryResultIn,this.model.getApp_type(), user.getCenter_num());
			}else if("5".equals(examResultStyle)){
				final_result = this.examSummaryService.createFinalExamResultNHFY(this.model.getExam_num(), isExamSummary,isShowRefvalue,isShowSamplename,examResultShowItemId,examSummaryResultIn,this.model.getApp_type(), user.getCenter_num());
			}else if("6".equals(examResultStyle)){//交通大学第二附属医院
				final_result = this.examSummaryService.createFinalExamResultERYUAN(this.model.getExam_num(), isExamSummary,isShowRefvalue,isShowSamplename,examResultShowItemId,examSummaryResultIn,this.model.getApp_type(), user.getCenter_num());
			}else{
				final_result = this.examSummaryService.createFinalExamResult(this.model.getExam_num(), isExamSummary,isShowRefvalue,isShowSamplename,examResultShowItemId,examSummaryResultIn,this.model.getApp_type(), user.getCenter_num());
			}
			examSummary.setFinal_exam_result(final_result);
			examSummary.setExam_status("J");
		}else{
			if("Y".equals(this.model.getSug_flag())){
				String final_result = "";
				if("1".equals(examResultStyle)){ //普通样式
					final_result = this.examSummaryService.createFinalExamResult(this.model.getExam_num(), isExamSummary,isShowRefvalue,isShowSamplename,examResultShowItemId,examSummaryResultIn,this.model.getApp_type(), user.getCenter_num());
				}else if("2".equals(examResultStyle)){//浙医二样式
					final_result = this.examSummaryService.createFinalExamResultZY2(this.model.getExam_num(), isExamSummary,isShowRefvalue,isShowSamplename,examResultShowItemId,examSummaryResultIn,this.model.getApp_type(), user.getCenter_num());
				}else if("3".equals(examResultStyle)){//江苏机关医院
					final_result = this.examSummaryService.createFinalExamResultJGYY(this.model.getExam_num(), isExamSummary,isShowRefvalue,isShowSamplename,examResultShowItemId,examSummaryResultIn,this.model.getApp_type(), user.getCenter_num());
				}else if("4".equals(examResultStyle)){//西苑医院
					final_result = this.examSummaryService.createFinalExamResultXYYY(this.model.getExam_num(), isExamSummary,isShowRefvalue,isShowSamplename,examResultShowItemId,examSummaryResultIn,this.model.getApp_type(), user.getCenter_num());
				}else if("5".equals(examResultStyle)){//南华附一
					final_result = this.examSummaryService.createFinalExamResultNHFY(this.model.getExam_num(), isExamSummary,isShowRefvalue,isShowSamplename,examResultShowItemId,examSummaryResultIn,this.model.getApp_type(), user.getCenter_num());
				}else if("6".equals(examResultStyle)){//交通大学第二附属医院
					final_result = this.examSummaryService.createFinalExamResultERYUAN(this.model.getExam_num(), isExamSummary,isShowRefvalue,isShowSamplename,examResultShowItemId,examSummaryResultIn,this.model.getApp_type(), user.getCenter_num());
				}else{
					final_result = this.examSummaryService.createFinalExamResult(this.model.getExam_num(), isExamSummary,isShowRefvalue,isShowSamplename,examResultShowItemId,examSummaryResultIn,this.model.getApp_type(), user.getCenter_num());
				}
				examSummary.setFinal_exam_result(final_result);
			}
		}
		this.outJsonResult(examSummary);
		return NONE;
	}
	
	/**
	 * 根据条件 查询 阳性发现列表    398
	     * @Title: serchDiseaseList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String serchDiseaseList() throws WebException,SQLException{
		List<DiseaseKnowloedgeDTO> list = this.examSummaryService.serchDiseaseList(this.model.getQ());
		this.outJsonResult(list);
		return NONE;
	}
	
	/**
	 * 根据阳性发现ID查询健康建议 399
	     * @Title: serDiseaseSuggestionList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String serchDiseaseSuggestionList() throws WebException,SQLException{
		UserDTO user = (UserDTO) session.get("username");
		String IS_DISEASE_SUG_CENTER = "N";
		try {
			IS_DISEASE_SUG_CENTER = this.customerInfoService.getCenterconfigByKey("IS_DISEASE_SUG_CENTER", user.getCenter_num()).getConfig_value().trim();
		} catch (Exception e) {
		}
		List<ExaminfoDiseaseDTO> list = this.examSummaryService.serchDiseaseSuggestionList(this.model.getDisease_id(), this.model.getAge(), this.model.getSex(),user.getCenter_num(),IS_DISEASE_SUG_CENTER);
		this.outJsonResult(list);
		return NONE;
	}
	
	/**
	 * 总检显示建议列表 700
	     * @Title: showExamDiseaseSugList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String showExamDiseaseSugList() throws WebException{
		return SUCCESS;
	}
	
	/**
	 * 总检显示新增阳性发现 701
	     * @Title: showNewExamDisease   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String showNewExamDisease() throws WebException{
		return SUCCESS;
	}
	
	/**
	 * 保存总检信息 702
	     * @Title: saveExamSummary   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	@SuppressWarnings("unchecked")
	public String saveExamSummary() throws WebException,SQLException{
		UserDTO user = (UserDTO) session.get("username");
		if(user != null){
			String  is_save_dx_disease  ="N";   //总检时是否保存单项阳性到examinfo_disease表  默认值是不保存
			try {
				is_save_dx_disease = this.customerInfoService.getCenterconfigByKey("IS_SAVE_DX_DISEASE", user.getCenter_num()).getConfig_value().trim();
			} catch (Exception e) {
				// TODO: handle exception
			}
			this.model.setIs_save_dx_disease(is_save_dx_disease);
			JSONArray dxdisease = JSONArray.fromObject(this.model.getDxExamDisease());
			List<ExaminfoDiseaseDTO> dxlistdisease = (List<ExaminfoDiseaseDTO>) JSONArray.toCollection(dxdisease,ExaminfoDiseaseDTO.class);
			this.model.setDxexaminfoDiseases(dxlistdisease);
			this.model.setIs_save_dx_disease(is_save_dx_disease);
			
			
			JSONArray disease = JSONArray.fromObject(this.model.getExaminfoDiseases());
			List<ExaminfoDiseaseDTO> listdisease = (List<ExaminfoDiseaseDTO>) JSONArray.toCollection(disease,ExaminfoDiseaseDTO.class);
			this.model.setExaminfoDisease(listdisease);
            this.defaultapp = (SystemType) session.get("defaultapp");
			ExamSummary examSummary = this.examSummaryService.getExamSummaryById(model.getExam_num(),
                    this.defaultapp.getComid());
			if(examSummary != null && examSummary.getExam_doctor_id() != 0 && examSummary.getExam_doctor_id() != user.getUserid()){
				WebUserInfo userinfo = this.userInfoService.loadUserInfo(examSummary.getExam_doctor_id());
				this.message = "error-对不起,您不是此体检人的终检医生,不能终检或保存!请联系(" + userinfo.getChi_Name() + ")终检或保存!";
			}else{
                this.defaultapp = (SystemType)session.get("defaultapp");
                model.setApp_type(this.defaultapp.getComid());
				String zongjian = this.examSummaryService.saveExamSummary(model,examSummary, user,model.getExam_status());
//				String huiyuan=this.crmMemberManageService.saveCrmMemberStore(model);
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
	 * 取消总检 703
	     * @Title: cancelExamSummary   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String cancelExamSummary() throws WebException,SQLException{
		UserDTO user = (UserDTO) session.get("username");
		String EXAM_SUMMARY_STYLE = this.customerInfoService.getCenterconfigByKey("EXAM_SUMMARY_STYLE", user.getCenter_num()).getConfig_value();
		ExamInfo examInfo = this.examSummaryService.getExamInfoByExamNum(this.model.getExam_num());
		if(examInfo == null){
			this.message = "error-该体检号体检信息";
		}else{
			if((examInfo.getApptype().equals("2") && !examInfo.getZyb_final_doctor().equals(user.getName())) || (examInfo.getApptype().equals("1") && !examInfo.getFinal_doctor().equals(user.getName()))){
				this.message = "error-您不是该体检人的终检医生,请联系医生(" + examInfo.getFinal_doctor() + ")取消终检!";
			}else{
				ExamSummary examSummary = this.examSummaryService.getExamSummaryById(model.getExam_num(),this.model.getApp_type());
				if(EXAM_SUMMARY_STYLE != null && "2".equals(EXAM_SUMMARY_STYLE.trim())){
					if("A".equals(examSummary.getApprove_status())){
						this.message = "error-体检人员已审核,不能取消总检!";
					}else{
						this.message = this.examSummaryService.cancelExamSummary(examInfo, examSummary,user);
						SysLog sl =  new SysLog();
						sl.setCenter_num(user.getCenter_num());
						sl.setUserid(user.getUserid()+"");
						sl.setOper_type("2");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
						sl.setXtgnid("");//可不填写
						sl.setXtgnid2("703");//子功能id 必须填写
						sl.setExplain("");//操作说明
						syslogService.saveSysLog(sl);
					}
				}else{
					if(examInfo.getApptype().equals("2")){//职业病健康总检取消总检
						examInfo.setZyb_final_status("N");// 
						examInfo.setZyb_final_doctor(null);
						examInfo.setZyb_final_time(null);
					}else { //健康体检取消总检
						examInfo.setStatus("J");// 将状态设置成检查中
						examInfo.setFinal_date(null);
						examInfo.setFinal_doctor(null);
					}
					this.examSummaryService.updateExamInfo(examInfo);
					
					examSummary.setExam_doctor_id(0);
					examSummary.setFinal_status("J");
					examSummary.setFinal_time(null);
					this.examSummaryService.updateExamSummary(examSummary);
					this.message = "ok-取消总检成功!";
					
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
		}
		this.outJsonStrResult(this.message);
		return NONE;
	}
	
	/**
	 * 审核与取消审核  704
	     * @Title: approveExamSummary   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	@SuppressWarnings("unchecked")
	public String approveExamSummary() throws WebException,SQLException{
		UserDTO user = (UserDTO) session.get("username");
		ExamSummary examSummary = this.examSummaryService.getExamSummaryById(model.getExam_num(),this.model.getApp_type());
		if(examSummary == null){
			this.message = "error-总检信息不存在,请重新进入总检页面!";
		}else{
			String EXAM_SUMMARY_STYLE = this.customerInfoService.getCenterconfigByKey("EXAM_SUMMARY_STYLE", user.getCenter_num()).getConfig_value();
			String  is_save_dx_disease  ="N";   //总检时是否保存单项阳性到examinfo_disease表  默认值是不保存
			try {
				is_save_dx_disease = this.customerInfoService.getCenterconfigByKey("IS_SAVE_DX_DISEASE", user.getCenter_num()).getConfig_value().trim();
			} catch (Exception e) {
				// TODO: handle exception
			}
			
			JSONArray dxdisease = JSONArray.fromObject(this.model.getDxExamDisease());
			List<ExaminfoDiseaseDTO> dxlistdisease = (List<ExaminfoDiseaseDTO>) JSONArray.toCollection(dxdisease,ExaminfoDiseaseDTO.class);
			this.model.setDxexaminfoDiseases(dxlistdisease);
			this.model.setIs_save_dx_disease(is_save_dx_disease);
			
			
			if(EXAM_SUMMARY_STYLE != null && "2".equals(EXAM_SUMMARY_STYLE.trim())){
				if("B".equals(this.model.getApprove_status())){//审核 将B改为 A
					//审核保存总检信息
					JSONArray disease = JSONArray.fromObject(this.model.getExaminfoDiseases());
					List<ExaminfoDiseaseDTO> listdisease = (List<ExaminfoDiseaseDTO>) JSONArray.toCollection(disease,ExaminfoDiseaseDTO.class);
					this.model.setExaminfoDisease(listdisease);
					this.message = this.examSummaryService.approveExamSummary(model, examSummary, user);
                    ExamInfoDTO ec = this.customerInfoService.getCustExamInfoForexamId(examSummary.getExam_num());

                    this.defaultapp = (SystemType) session.get("defaultapp");
                    ReportPdfDTO r = new ReportPdfDTO();
                    r.setExam_num(ec.getExam_num());
                    r.setArch_num(ec.getArch_num());
                    r.setReport_year(Integer.parseInt(ec.getJoin_date().substring(0,4)));
                    this.examSummaryService.saveReportPdf(r,user,this.defaultapp.getComid());

					SysLog sl =  new SysLog();
					sl.setCenter_num(user.getCenter_num());
					sl.setUserid(user.getUserid()+"");
					sl.setOper_type("2");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
					sl.setXtgnid("");//可不填写
					sl.setXtgnid2("704");//子功能id 必须填写
					sl.setExplain("审核");//操作说明
					syslogService.saveSysLog(sl);
				}else if("A".equals(this.model.getApprove_status())){//取消审核
					if("1".equals(examSummary.getCensoring_status())){
						this.message = "error-已终审,不能取消审核!";
					}else{
						if(user.getUserid() != examSummary.getCheck_doc()){
							WebUserInfo userinfo = this.userInfoService.loadUserInfo(examSummary.getCheck_doc());
							this.message = "error-对不起,您不是此总检信息的审核者,不能取消审核!请联系(" + userinfo.getChi_Name() + ")取消审核!";
						}else{
							this.message = this.examSummaryService.approveExamSummary(model, examSummary, user);
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
                    examSummary.setRead_status1(0);
                    examSummary.setRead_status2(0);
                    examSummary.setRead_status3(0);
					this.examSummaryService.updateExamSummary(examSummary);
					this.message = "ok-审核成功!";

                    ExamInfoDTO ec = this.customerInfoService.getCustExamInfoForexamId(examSummary.getExam_num());
                    this.defaultapp = (SystemType) session.get("defaultapp");
                    ReportPdfDTO r = new ReportPdfDTO();
                    r.setExam_num(ec.getExam_num());
                    r.setArch_num(ec.getArch_num());
                    r.setReport_year(Integer.parseInt(ec.getJoin_date().substring(0,4)));
                    this.examSummaryService.saveReportPdf(r,user,this.defaultapp.getComid());


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
						this.message = "ok-取消审核成功!";
						
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
		}
		this.outJsonStrResult(this.message);
		return NONE;
	}
	
	/**
	 * 总检室首页  705
	     * @Title: getExamSummaryIndex   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String getExamSummaryIndex() throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		this.defaultapp = (SystemType)session.get("defaultapp");
		this.model.setTime1(DateTimeUtil.getDate2());
		this.model.setTime2(DateTimeUtil.getDate2());
		this.model.setFinal_time1(DateTimeUtil.getDate2());
		this.model.setFinal_time2(DateTimeUtil.getDate2());
		this.model.setSeven_time(DateTimeUtil.getPastDate(7));
		this.model.setExamSummaryCheckDefault(this.customerInfoService.getCenterconfigByKey("EXAM_SUMMARY_CHECK_DEFAULT", user.getCenter_num()).getConfig_value());
		CenterConfigurationDTO cent = this.customerInfoService.getCenterconfigByKey("IS_BATCH_EXAMINE", user.getCenter_num());
		if(cent!=null){
			this.setIs_batch_examine_s(cent.getConfig_value());
		}
		this.model.setReport_print_type(this.customerInfoService.getCenterconfigByKey("REPORT_PRINT_TYPE", user.getCenter_num()).getConfig_value().trim());
		this.model.setZyb_report_print_type(this.customerInfoService.getCenterconfigByKey("ZYB_REPORT_PRINT_TYPE", user.getCenter_num()).getConfig_value().trim());
		this.model.setCustomer_type_special(this.customerInfoService.getCenterconfigByKey("CUSTOMER_TYPE_SPECIAL", user.getCenter_num()).getConfig_value().trim());
		this.model.setCustomer_type_special_color(this.customerInfoService.getCenterconfigByKey("CUSTOMER_TYPE_SPECIAL_COLOR", user.getCenter_num()).getConfig_value().trim());
		String EXAM_SUMMARY_STYLE = this.customerInfoService.getCenterconfigByKey("EXAM_SUMMARY_STYLE", user.getCenter_num()).getConfig_value();
		String IS_TEAM_AMOUNT_VIEW = this.customerInfoService.getCenterconfigByKey("IS_TEAM_AMOUNT_VIEW", user.getCenter_num()).getConfig_value();
		List<WebResrelAtionship> wrs=user.getWebResource();
		if (wrs != null && wrs.size() > 0) {
			if(StringUtil.isEmpty(IS_TEAM_AMOUNT_VIEW) || "N".equals(IS_TEAM_AMOUNT_VIEW)) {
				//没有此配置，或者配成不控制，都能看见团体金额
				this.teamAmountViewFlag = "1";
			} else if("Y".equals(IS_TEAM_AMOUNT_VIEW)) {
				//配成Y的话，仅有资源者能看见团体金额
				for(WebResrelAtionship wr :wrs){
					if("RS039".equals(wr.getRes_code())){
						this.teamAmountViewFlag = "1";
						break;
					}
				}
			}
		}
		
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("705");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		
		this.model.setApp_type("1");
		if(this.defaultapp.getComid().equals("1")){
			if(EXAM_SUMMARY_STYLE != null && "2".equals(EXAM_SUMMARY_STYLE.trim())){
				return "success_180";
			}
		}else{
			this.model.setApp_type("2");
			return "success_zyb";
		}
		return SUCCESS;
	}
	
	/**
	 * 总检室首页查询人员列表 706
	     * @Title: getExamSummaryIndexList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String getExamSummaryIndexList() throws WebException,SQLException{
		UserDTO user = (UserDTO) session.get("username");
		PageReturnDTO list = this.examSummaryService.getExamInfoList(model, user,model.getApp_type(), this.rows, this.getPage(),this.sort,this.order);
		this.outJsonResult(list);
		return NONE;
	}
	
	/**
	 * 查询可以总检的人员列表 762
	     * @Title: getMayExamSummaryList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String getMayExamSummaryList() throws WebException,SQLException{
		UserDTO user = (UserDTO) session.get("username");
		PageReturnDTO list = this.examSummaryService.getExamInfoList2(model,model.getApp_type(), user, this.rows, this.getPage(),this.sort,this.order);
		this.outJsonResult(list);
		return NONE;
	}
	
	/**
	 * 查询是否存在未检项目 707
	     * @Title: getNotExamCharingItemList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String getNotExamCharingItemList() throws WebException,SQLException{
		UserDTO user = (UserDTO) session.get("username");
		List<ChargingItemDTO> list = this.examSummaryService.getNotExamItem(this.model.getExam_num(), user.getCenter_num());
		this.outJsonResult(list);
		return NONE;
	}
	
	/**
	 * 获取往年历史结果 708
	     * @Title: getResultsHistoryList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String getResultsHistoryList() throws WebException,SQLException{
		this.defaultapp = (SystemType) session.get("defaultapp");
		UserDTO user = (UserDTO) session.get("username");
		List<ExamSummaryDTO> list = this.examSummaryService.getResultsHistoryList(this.model.getExam_num(),this.defaultapp.getComid(),user);
		this.outJsonResult(list);
		return NONE;
	}
	
	/**
	 * 获取往年普通科项目检查结果 709
	     * @Title: getPtItemResultsHistory   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String getPtItemResultsHistory() throws WebException,SQLException{
		List<DepExamResultDTO> list = this.examSummaryService.getPtItemResultsHistory(this.model.getExam_num(), this.model.getItem_id());
		this.outJsonResult(list);
		return NONE;
	}
	
	/**
	 * 获取往年检验科项目检查结果 710
	     * @Title: getHyItemResultsHistory   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String getHyItemResultsHistory() throws WebException,SQLException{
		List<DepExamResultDTO> list = this.examSummaryService.getHyItemResultsHistory(this.model.getExam_num(), this.model.getItem_id());
		this.outJsonResult(list);
		return NONE;
	}
	
	/**
	 * 获取往年影像科项目检查结果 711
	     * @Title: getYxItemResultsHistory   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String getYxItemResultsHistory() throws WebException,SQLException{
		List<DepExamResultDTO> list = this.examSummaryService.getYxItemResultsHistory(this.model.getExam_num(), this.model.getItem_id());
		this.outJsonResult(list);
		return NONE;
	}
	
	/**
	 * 获取历史结果对比页面  712
	     * @Title: resultsHistoryListpage   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String resultsHistoryListPage() throws WebException{
		return SUCCESS;
	}
	/**
	 * 批量审核921
	     * @Title: getBatchAudit   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String getBatchAudit() throws WebException{
		UserDTO user  = (UserDTO) session.get("username");
        this.defaultapp = (SystemType) session.get("defaultapp");
        model.setApptype(this.defaultapp.getComid());
        model.setApp_type(this.defaultapp.getComid());
		String mes = this.examSummaryService.getBatchAudit(model,user);
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("2");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("921");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		this.outJsonStrResult(mes);
		return NONE;
	}
	
	/**
	 * 保存总检阳性发现列表
	     * @Title: saveExamDiseaseList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String saveExamDiseaseList() throws WebException{
		UserDTO user  = (UserDTO) session.get("username");
		JSONArray disease = JSONArray.fromObject(this.model.getExaminfoDiseases());
		List<ExaminfoDiseaseDTO> listdisease = (List<ExaminfoDiseaseDTO>) JSONArray.toCollection(disease,ExaminfoDiseaseDTO.class);
		this.examSummaryService.saveExamDiseaseList(model.getApp_type(),model.getApp_type(),listdisease, user);
		this.outJsonStrResult("保存成功!");
		return NONE;
	}
	
	/**
	 * 查询问诊症状和既往史列表
	     * @Title: getSymptomsAndHistoryList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String getSymptomsAndHistoryList() throws WebException{
		List<DepExamResultDTO> list = this.examSummaryService.getSymptomsAndHistoryList(this.model.getExam_num());
		this.outJsonResult(list);
		return NONE;
	}
	/**
	 * 获取总检医生的被驳回列表
	     * @Title: getExamSummaryRejectList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String getExamSummaryRejectList() throws WebException{
		UserDTO user  = (UserDTO) session.get("username");
		PageReturnDTO list = this.examSummaryService.getExamSummaryRejectList(model, user, this.rows, this.getPage(),this.sort,this.order);
		this.outJsonResult(list);
		return NONE;
	}
	
	/**
	 *  获取总检、审核、复审医生列表
	     * @Title: getFinalDoctorList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String getFinalDoctorList() throws WebException,SQLException{
		UserDTO users = (UserDTO) session.get("username");
		List<UserDTO> list = this.examSummaryService.getFinalDoctorList(model.getOperation_type(),users.getCenter_num());
		List<UserDTO> list1 = new ArrayList<UserDTO>();
		UserDTO user = new UserDTO();
		user.setId(0);
		user.setName("全部");
		list1.add(user);
		list1.addAll(list);
		this.outJsonResult(list1);
		return NONE;
	}
	
	/**
	 *  查询总检医生已总检人员列表
	     * @Title: getHasFinalExamInfoList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String getHasFinalExamInfoList() throws WebException{
		UserDTO user  = (UserDTO) session.get("username");
		PageReturnDTO list = this.examSummaryService.getHasFinalExamInfoList(model, user, this.rows, this.getPage(),this.sort,this.order);
		this.outJsonResult(list);
		return NONE;
	}
	
	/**
	 *  已总检获取的体检人信息列表
	     * @Title: getFinalExamInfoList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String getFinalExamInfoList() throws WebException{
		UserDTO user  = (UserDTO) session.get("username");
		List<ExamInfoUserDTO> list = this.examSummaryService.getFinalExamInfoList(user);
		this.outJsonResult(list);
		return NONE;
	}
	/**
	 *  查询可以下总检并且未总检人数
	     * @Title: getNotFinalCount   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String getNotFinalCount() throws WebException,SQLException{
		UserDTO user = (UserDTO) session.get("username");
		String IS_EXAM_RESULT_CANFINAL = this.customerInfoService.getCenterconfigByKey("IS_EXAM_RESULT_CANFINAL", user.getCenter_num()).getConfig_value();
		ExamSummaryDTO examsumary = this.examSummaryService.getNotFinalCount(model.getOperation_type(),user,IS_EXAM_RESULT_CANFINAL);
		this.outJsonResult(examsumary);
		return NONE;
	}
	
	/**
	 *  总检获取体检人信息
	     * @Title: canFinalExamInfo
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String canFinalExamInfo() throws WebException{
		UserDTO user  = (UserDTO) session.get("username");
		String IS_EXAM_RESULT_CANFINAL = this.customerInfoService.getCenterconfigByKey("IS_EXAM_RESULT_CANFINAL", user.getCenter_num()).getConfig_value();
		String num = this.customerInfoService.getCenterconfigByKey("EXAM_SUMMARY_FINAL_NUM", user.getCenter_num()).getConfig_value();
		if(num == null || !com.hjw.util.StringUtil.isInt(num)){
			num = "5";
		}
		long count = this.examSummaryService.finalGainCount(user);
		long wzcount = Long.valueOf(num) - count;
		
		if("3".equals(IS_EXAM_RESULT_CANFINAL)){//东北国际个人主检医生不限制上限数量
			//获取总检贵宾客户权限资源
			String resource = null;
			List<WebResrelAtionship>  web = user.getWebResource();
			if(web!=null){
			for (int i = 0; i < web.size(); i++) {
				if(web.get(i).getRes_code().equals("RS050")){
						resource = web.get(i).getDatavalue();
						break;
					}
				}
			}
			if(resource != null && "3".equals(resource)){
				wzcount = 1;
			}
		}
		if(wzcount <= 0){
			this.message = "error-已达获取上限，不能继续获取总检人员!";
		}else{
			
			if("all".equals(model.getStatus())){
				if(IS_EXAM_RESULT_CANFINAL != null && "1".equals(IS_EXAM_RESULT_CANFINAL)){
					this.message = this.examSummaryService.canFinalExamInfo(wzcount+"", user);
				}else if(IS_EXAM_RESULT_CANFINAL != null && "2".equals(IS_EXAM_RESULT_CANFINAL)){
					this.message = this.examSummaryService.canFinalExamInfo2(wzcount+"", user);
				}else if(IS_EXAM_RESULT_CANFINAL != null && "3".equals(IS_EXAM_RESULT_CANFINAL)){
					this.message = this.examSummaryService.canFinalExamInfoDbgj(wzcount+"", user);
				}else{
					this.message = this.examSummaryService.canFinalExamInfo(wzcount+"", user);
				}
			}else{
				if(IS_EXAM_RESULT_CANFINAL != null && "1".equals(IS_EXAM_RESULT_CANFINAL)){
					this.message = this.examSummaryService.canFinalExamInfo("1", user);
				}else if(IS_EXAM_RESULT_CANFINAL != null && "2".equals(IS_EXAM_RESULT_CANFINAL)){
					this.message = this.examSummaryService.canFinalExamInfo2("1", user);
				}else if(IS_EXAM_RESULT_CANFINAL != null && "3".equals(IS_EXAM_RESULT_CANFINAL)){
					this.message = this.examSummaryService.canFinalExamInfoDbgj("1", user);
				}else{
					this.message = this.examSummaryService.canFinalExamInfo("1", user);
				}
			}
		}		
		this.outJsonStrResult(this.message);
		return NONE;
	}
	
	/**
	 * 1146 获取审核首页
	     * @Title: getExamSummaryApprovePage   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String getExamSummaryApprovePage() throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		this.defaultapp = (SystemType)session.get("defaultapp");
		this.model.setTime1(DateTimeUtil.getDate2());
		this.model.setTime2(DateTimeUtil.getDate2());
		this.model.setFinal_time1(DateTimeUtil.getDate2());
		this.model.setFinal_time2(DateTimeUtil.getDate2());
		this.model.setReport_print_type(this.customerInfoService.getCenterconfigByKey("REPORT_PRINT_TYPE", user.getCenter_num()).getConfig_value().trim());
		
		String IS_EXAM_RESULT_CANFINAL = this.customerInfoService.getCenterconfigByKey("IS_EXAM_RESULT_CANFINAL", user.getCenter_num()).getConfig_value();
		this.model.setIs_exam_result_canfinal(IS_EXAM_RESULT_CANFINAL);
		String EXAM_SUMMARY_STYLE = this.customerInfoService.getCenterconfigByKey("EXAM_SUMMARY_STYLE", user.getCenter_num()).getConfig_value();
		
		this.model.setApp_type("1");
		if(this.defaultapp.getComid().equals("1")){
			if(EXAM_SUMMARY_STYLE != null && "2".equals(EXAM_SUMMARY_STYLE.trim())){
				if("1".equals(IS_EXAM_RESULT_CANFINAL)){
					return "success_180";
				}else if("2".equals(IS_EXAM_RESULT_CANFINAL)){
					return "success_eryuan";
				}
			}
		}else{
			this.model.setApp_type("2");
			return "success_zyb";
		}
		return SUCCESS;
		
	}
	
	/**
	 * 1159 根据体检号或档案号获取总检信息
	     * @Title: canFinalExamInfoByExamNum   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String canFinalExamInfoByExamNum() throws WebException,SQLException{
		UserDTO user = (UserDTO) session.get("username");
		String IS_EXAM_RESULT_CANFINAL = this.customerInfoService.getCenterconfigByKey("IS_EXAM_RESULT_CANFINAL", user.getCenter_num()).getConfig_value();
		if(IS_EXAM_RESULT_CANFINAL != null && "3".equals(IS_EXAM_RESULT_CANFINAL)){//东北国际总检按体检号获取，不受整单室限制
			this.message = this.examSummaryService.canFinalExamInfoByExamNumDbgj(model, user);
		}else{
			this.message = this.examSummaryService.canFinalExamInfoByExamNum(model, user);
		}
		this.outJsonStrResult(this.message);
		return NONE;
	}
	/**
	 * 审核室随机获取人员信息
	     * @Title: canFinalExamInfo   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String canExaminedExamInfo() throws WebException{
		UserDTO user  = (UserDTO) session.get("username");
		String num = this.customerInfoService.getCenterconfigByKey("EXAM_SUMMARY_FINAL_NUM", user.getCenter_num()).getConfig_value().trim();
		if("all".equals(model.getStatus())){
			this.message = this.examSummaryService.canExaminedExamInfo(num, user);
		}else{
			if(model.getId() >= Long.valueOf(num)){
				this.message = "error-已达获取上限，不能继续获取总检人员!";
			}else{
				this.message = this.examSummaryService.canExaminedExamInfo("1", user);
			}
		}
		this.outJsonStrResult(this.message);
		return NONE;
	}
	/**
	 * 获取已经获取的审核人员信息
	     * @Title: getFinalExamInfoList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String getExaminedExamInfoList() throws WebException{
		UserDTO user  = (UserDTO) session.get("username");
		List<ExamInfoUserDTO> list = this.examSummaryService.getExaminedExamInfoList(model.getExam_num(),user,this.sort,this.order);
		this.outJsonResult(list);
		return NONE;
	}
	
	/**
	 * 体检号获取审核信息
	     * @Title: canExaminedExamInfoByExamNum   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String canExaminedExamInfoByExamNum() throws WebException,SQLException{
		UserDTO user = (UserDTO) session.get("username");
		String IS_EXAM_RESULT_CANFINAL = this.customerInfoService.getCenterconfigByKey("IS_EXAM_RESULT_CANFINAL", user.getCenter_num()).getConfig_value();
		if(IS_EXAM_RESULT_CANFINAL != null && "3".equals(IS_EXAM_RESULT_CANFINAL)){
			this.message = this.examSummaryService.canExaminedExamInfoByExamNumDbgj(model, user);
		}else{
			this.message = this.examSummaryService.canExaminedExamInfoByExamNum(model, user);
		}
		this.outJsonStrResult(this.message);
		return NONE;
	}
	
	/**
	 * 1148 查询已审核人员列表
	     * @Title: getExamSummaryApproveList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String getExamSummaryApproveList() throws WebException{
		UserDTO user  = (UserDTO) session.get("username");
		PageReturnDTO list = this.examSummaryService.getExamSummaryApproveList(model, user, this.rows, this.getPage(),this.sort,this.order);
		this.outJsonResult(list);
		return NONE;
	}
	
	/**
	 * 1193 保存需要复审的体检人员
	     * @Title: isCensoringExamSummary   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String isCensoringExamSummary() throws WebException,SQLException{
		UserDTO user  = (UserDTO) session.get("username");
		ExamFlowConfig config = this.examSummaryService.getExamFlogConfig(model.getExam_num());
		this.message = this.examSummaryService.isCensoringExamSummary(config, user);
		this.outJsonStrResult(this.message);
		return NONE;
	}
	
	/**
	 * 1173 审核保存、终审保存阳性发现列表
	     * @Title: saveExamInfoDiseaseList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String saveExamInfoDiseaseList() throws WebException,SQLException{
		UserDTO user = (UserDTO) session.get("username");
		if(user != null){
			String  is_save_dx_disease  ="N";   //总检时是否保存单项阳性到examinfo_disease表  默认值是不保存
			try {
				is_save_dx_disease = this.customerInfoService.getCenterconfigByKey("IS_SAVE_DX_DISEASE", user.getCenter_num()).getConfig_value().trim();
			} catch (Exception e) {
				// TODO: handle exception
			}
			
			JSONArray dxdisease = JSONArray.fromObject(this.model.getDxExamDisease());
			List<ExaminfoDiseaseDTO> dxlistdisease = (List<ExaminfoDiseaseDTO>) JSONArray.toCollection(dxdisease,ExaminfoDiseaseDTO.class);
			this.model.setDxexaminfoDiseases(dxlistdisease);
			this.model.setIs_save_dx_disease(is_save_dx_disease);
			
			JSONArray disease = JSONArray.fromObject(this.model.getExaminfoDiseases());
			List<ExaminfoDiseaseDTO> listdisease = (List<ExaminfoDiseaseDTO>) JSONArray.toCollection(disease,ExaminfoDiseaseDTO.class);
			this.model.setExaminfoDisease(listdisease);
			
			ExamSummary examSummary = this.examSummaryService.getExamSummaryById(this.model.getExam_num(),this.model.getApp_type());
			if("1".equals(this.model.getExam_status())){//审核保存
				if(examSummary.getAcceptance_doctor() != 0 && examSummary.getAcceptance_doctor() != user.getUserid()){
					WebUserInfo userinfo = this.userInfoService.loadUserInfo(examSummary.getAcceptance_doctor());
					this.message = "error-对不起,您不是此体检人的审核医生,不能保存!请联系(" + userinfo.getChi_Name() + ")保存!";
				}else{
					this.message = this.examSummaryService.saveExamInfoDiseaseList(model,examSummary, user);
				}
			}else if("2".equals(this.model.getExam_status())){
				if(examSummary.getCensoring_doc() != 0 && examSummary.getCensoring_doc() != user.getUserid()){
					WebUserInfo userinfo = this.userInfoService.loadUserInfo(examSummary.getCensoring_doc());
					this.message = "error-对不起,您不是此体检人的复审医生,不能保存!请联系(" + userinfo.getChi_Name() + ")保存!";
				}else{
					this.message = this.examSummaryService.saveExamInfoDiseaseList(model,examSummary, user);
				}
			}else{
				this.message = "error-未总检，不能保存!";
			}
		}else{
			this.message = "error-请重新登录!";
		}
		this.outJsonStrResult(this.message);
		return NONE;
	}
	/**
	 * 1194获取复审首页
	     * @Title: getExamSummaryCensoringPage   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String getExamSummaryCensoringPage() throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		this.model.setTime1(DateTimeUtil.getDate2());
		this.model.setTime2(DateTimeUtil.getDate2());
		this.model.setFinal_time1(DateTimeUtil.getDate2());
		this.model.setFinal_time2(DateTimeUtil.getDate2());
		this.model.setReport_print_type(this.customerInfoService.getCenterconfigByKey("REPORT_PRINT_TYPE", user.getCenter_num()).getConfig_value().trim());
		String IS_EXAM_RESULT_CANFINAL = this.customerInfoService.getCenterconfigByKey("IS_EXAM_RESULT_CANFINAL", user.getCenter_num()).getConfig_value();
		this.model.setIs_exam_result_canfinal(IS_EXAM_RESULT_CANFINAL);
		return SUCCESS;
	}
	/**
	 * 1192 查询未复审列表
	     * @Title: getExamSummaryCensoringWList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String getExamSummaryCensoringWList() throws WebException,SQLException{
		UserDTO user  = (UserDTO) session.get("username");
		String IS_EXAM_RESULT_CANFINAL = this.customerInfoService.getCenterconfigByKey("IS_EXAM_RESULT_CANFINAL", user.getCenter_num()).getConfig_value();
		PageReturnDTO list = this.examSummaryService.getExamSummaryCensoringWList(model, IS_EXAM_RESULT_CANFINAL, user, this.rows, this.getPage(),this.sort,this.order);
		this.outJsonResult(list);
		return NONE;
	}
	
	/**
	 * 1195查询复审列表
	     * @Title: getExamSummaryCensoringList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String getExamSummaryCensoringList() throws WebException{
		UserDTO user  = (UserDTO) session.get("username");
		PageReturnDTO list = this.examSummaryService.getExamSummaryCensoringList(model, user, this.rows, this.getPage(),this.sort,this.order);
		this.outJsonResult(list);
		return NONE;
	}
	/**
	 * 1196终审与取消终审保存
	     * @Title: censoringExamSummary   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	@SuppressWarnings("unchecked")
	public String censoringExamSummary() throws WebException,SQLException{
		UserDTO user = (UserDTO) session.get("username");
		ExamSummary examSummary = this.examSummaryService.getExamSummaryById(this.model.getExam_num(),this.model.getApp_type());
		if(examSummary == null){
			this.message = "error-总检信息不存在,请重新进入总检页面!";
		}else{
			if("0".equals(this.model.getCensoring_status())){//终审
				//审核保存总检信息
				
				String  is_save_dx_disease  ="N";   //总检时是否保存单项阳性到examinfo_disease表  默认值是不保存
				try {
					is_save_dx_disease = this.customerInfoService.getCenterconfigByKey("IS_SAVE_DX_DISEASE", user.getCenter_num()).getConfig_value().trim();
				} catch (Exception e) {
					// TODO: handle exception
				}
				
				JSONArray dxdisease = JSONArray.fromObject(this.model.getDxExamDisease());
				List<ExaminfoDiseaseDTO> dxlistdisease = (List<ExaminfoDiseaseDTO>) JSONArray.toCollection(dxdisease,ExaminfoDiseaseDTO.class);
				this.model.setDxexaminfoDiseases(dxlistdisease);
				this.model.setIs_save_dx_disease(is_save_dx_disease);
				
				JSONArray disease = JSONArray.fromObject(this.model.getExaminfoDiseases());
				List<ExaminfoDiseaseDTO> listdisease = (List<ExaminfoDiseaseDTO>) JSONArray.toCollection(disease,ExaminfoDiseaseDTO.class);
				this.model.setExaminfoDisease(listdisease);
				this.message = this.examSummaryService.censoringExamSummary(model, examSummary, user);
				SysLog sl =  new SysLog();
				sl.setCenter_num(user.getCenter_num());
				sl.setUserid(user.getUserid()+"");
				sl.setOper_type("2");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
				sl.setXtgnid("");//可不填写
				sl.setXtgnid2("1151");//子功能id 必须填写
				sl.setExplain("终审");//操作说明
				syslogService.saveSysLog(sl);
			}else if("1".equals(this.model.getCensoring_status())){//取消终审
				if(user.getUserid() != examSummary.getCensoring_doc()){
					WebUserInfo userinfo = this.userInfoService.loadUserInfo(examSummary.getCensoring_doc());
					this.message = "error-对不起,您不是此总检信息的审核者,不能取消审核!请联系(" + userinfo.getChi_Name() + ")取消审核!";
				}else{
					this.message = this.examSummaryService.censoringExamSummary(model, examSummary, user);
					SysLog sl =  new SysLog();
					sl.setCenter_num(user.getCenter_num());
					sl.setUserid(user.getUserid()+"");
					sl.setOper_type("2");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
					sl.setXtgnid("");//可不填写
					sl.setXtgnid2("1151");//子功能id 必须填写
					sl.setExplain("取消终审");//操作说明
					syslogService.saveSysLog(sl);
				}
			}
		}
		this.outJsonStrResult(this.message);
		return NONE;
	}
	
	/**
	 * 1147查询体检人是否可以审核或终审
	     * @Title: getExamSummaryApproveOrCensoring   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String getExamSummaryApproveOrCensoring() throws WebException{
		UserDTO user  = (UserDTO) session.get("username");
		ExamSummaryDTO dto = this.examSummaryService.getFinalExamResult(model.getExam_num(),"1",user);
		if(dto == null){
			this.message = "error-体检信息不存在!";
		}else{
			if("2".equals(model.getOperation_type())){//审核
				if("Z".equals(dto.getExam_status())){
					this.message = "ok-已总检,可以审核!";
				}else{
					this.message = "error-未总检,不能审核!";
				}
			}else if("3".equals(model.getOperation_type())){//终审
				if("A".equals(dto.getApprove_status())){
//					this.message = "ok-已审核,可以终审!";
					if(dto.getReport_class() == 3){
						this.message = "error-报告等级为3级,不需要再检索!";
					}else{
						ExamFlowConfig examFlowConfig = this.examSummaryService.getExamFlogConfig(model.getExam_num());
						if(examFlowConfig.getF0() == 1){
							this.message = "error-该体检信息已是需要复审,不需要再检索!";
						}else{
							this.message = this.examSummaryService.isCensoringExamSummary(examFlowConfig, user);
						}
					}
				}else{
					this.message = "error-未审核,不能终审!";
				}
			}
		}
		this.outJsonStrResult(this.message);
		return NONE;
	}
	
	/**
	 *1700 总检一键取消恢复
	     * @Title: saveExamSummaryCancel   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String saveExamSummaryCancel() throws WebException,SQLException{
		UserDTO user  = (UserDTO) session.get("username");
		if(this.model.getId() <= 0){
			this.message = "error-总检信息不存在，不能一键取消/恢复";
		}else{
			this.message = this.examSummaryService.saveExamSummaryCancel(model.getId(), model.getExam_num(), model.getCancel_type(), user);
		}
		this.outJsonStrResult(this.message);
		return NONE;
	}
	
	/**
	 * 1189 保存设定报告等级
	     * @Title: saveExamSummaryReportClass   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return      
	     * @return: String      
	     * @throws
	 */
	public String saveExamSummaryReportClass() throws WebException,SQLException{
		UserDTO user = (UserDTO) session.get("username");
		ExamSummary examSummary = null;
		if(this.model.getId() <= 0){
			this.message = "error-总检信息不存在,请先确定总检或保存!";
		}else{
			boolean flag = true;
			examSummary = this.examSummaryService.getExamSummaryById(this.model.getExam_num(),model.getApp_type());
			if(examSummary == null){
				this.message = "error-总检信息不存在,请先确定总检或保存!";
				flag = false;
			}
			if("0".equals(model.getOperation_type())){
				if(examSummary.getReport_class_type() > 0){
					this.message = "error-审核医生已设定报告等级，不能再设定!";
					flag = false;
				}
			}else if("1".equals(model.getOperation_type())){
				if(examSummary.getReport_class_type() > 1){
					this.message = "error-复审医生已设定报告等级，不能再设定!";
					flag = false;
				}
			}
			if(flag){
				examSummary.setReport_class(model.getReport_class());
				examSummary.setReport_class_user(user.getUserid());
				examSummary.setReport_class_date(DateTimeUtil.parse());
				examSummary.setReport_class_type(Long.valueOf(model.getOperation_type()));
				this.examSummaryService.updateExamSummary(examSummary);
				this.message = "ok-设置报告等级成功!";
			}
		}
		this.outJsonStrResult(this.message);
		return NONE;
	}
	
	/**
	 * 1190 获取报告等级
	     * @Title: getExamSummaryReportClass   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String getExamSummaryReportClass()throws WebException,SQLException{
		UserDTO user = (UserDTO) session.get("username");
		ExamSummaryDTO examSummary = this.examSummaryService.getFinalExamResult(this.model.getExam_num(),model.getApp_type(),user);
		if(examSummary.getReport_class_user() == null){
			examSummary.setReport_class_user(user.getName());
			examSummary.setReport_class_date(DateTimeUtil.getDate2());
		}
		this.outJsonResult(examSummary);
		return NONE;
	}
	
	/**
	 * 1191获取驳回填写驳回原因页面
	     * @Title: getExamSummaryRehectPage   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String getExamSummaryRehectPage() throws WebException{
		return SUCCESS;
	}
	/**
	 * 1192保存总检驳回信息
	     * @Title: saveExamSummaryRehectInfo   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String saveExamSummaryRehectInfo() throws WebException{
		UserDTO user  = (UserDTO) session.get("username");
		this.message = this.examSummaryService.saveExamSummaryRehectInfo(model, user);
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("1");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("1192");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		this.outJsonStrResult(this.message);
		return NONE;
	}
	
	/**
	 * 1755 获取已驳回总检列表
	     * @Title: getAlreadyRejectList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String getAlreadyRejectList() throws WebException{
		UserDTO user  = (UserDTO) session.get("username");
		PageReturnDTO list = this.examSummaryService.getAlreadyRejectList(model, user, this.rows, this.getPage(),this.sort,this.order);
		this.outJsonResult(list);
		return NONE;
	}
	
	/**
	 * 1757  总检获取模式主动分配页面
	     * @Title: getExamSummaryDistributionPage   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String getExamSummaryDistributionPage() throws WebException{
		return SUCCESS;
	}
	
	/**
	 * 1758查询未获取人员列表
	     * @Title: getExamSummaryDistributionList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String getExamSummaryDistributionList() throws WebException{
		UserDTO user  = (UserDTO) session.get("username");
		PageReturnDTO list = this.examSummaryService.getExamSummaryDistributionList(model, user, this.rows, this.getPage(),this.sort,this.order);
		this.outJsonResult(list);
		return NONE;
	}
	
	/**
	 * 1759保存主动分配信息
	     * @Title: saveExamSummaryDistribution   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String saveExamSummaryDistribution() throws WebException{
		UserDTO user  = (UserDTO) session.get("username");
		this.message = this.examSummaryService.saveExamSummaryDistribution(model.getExam_num(),model.getCensoring_doc(),user);
		this.outJsonStrResult(this.message);
		return NONE;
	}
	
	/**
	 * 2118查询驳回意见列表
	     * @Title: getRejectionlistshow   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String getRejectionlistshow() throws WebException{
		UserDTO user  = (UserDTO) session.get("username");
		List<FinalRejectionDTO> list = new ArrayList<FinalRejectionDTO>();
		list = this.examSummaryService.getRejectList();
		this.outJsonResult(list);
		
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("2118");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return NONE;
	}
	
	/**
	 * 1760按主检医生保存主动分配信息
	     * @Title: saveExamSummaryDistributionByDoctor   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String saveExamSummaryDistributionByDoctor() throws WebException{
		UserDTO user  = (UserDTO) session.get("username");
		this.message = this.examSummaryService.saveExamSummaryDistributionByDoctor(model.getExam_num(),model.getFinal_worknum(),user);
		this.outJsonStrResult(this.message);
		return NONE;
	}
	
	
	//单项疾病逻辑
		public String getExamDiseaseListNew() throws WebException,SQLException{
			UserDTO user = (UserDTO) session.get("username");
			String isExamSuggest = this.customerInfoService.getCenterconfigByKey("IS_EXAM_SUGGEST", user.getCenter_num()).getConfig_value();
			String isUseCompositeLogic = this.customerInfoService.getCenterconfigByKey("IS_USE_COMPOSITE_LOGIC", user.getCenter_num()).getConfig_value();
			String IS_DISEASE_SUG_CENTER = "N";
			try {
				IS_DISEASE_SUG_CENTER = this.customerInfoService.getCenterconfigByKey("IS_DISEASE_SUG_CENTER", user.getCenter_num()).getConfig_value().trim();
			} catch (Exception e) {
			}
			List<ExaminfoDiseaseDTO> list = null;
			list = this.examSummaryService.createNewExamInfoDisease(this.model.getExam_num(), isExamSuggest,isUseCompositeLogic, user.getCenter_num(),IS_DISEASE_SUG_CENTER);
			this.outJsonResult(list);
			return NONE;
		}
	//  复合逻辑疾病生成
		public String getExamCompositeDiseaseList() throws WebException,SQLException{
			List<ExaminfoDiseaseDTO> list = null;
			UserDTO user = (UserDTO) session.get("username");
			String IS_DISEASE_SUG_CENTER = "N";
			try {
				IS_DISEASE_SUG_CENTER = this.customerInfoService.getCenterconfigByKey("IS_DISEASE_SUG_CENTER", user.getCenter_num()).getConfig_value().trim();
			} catch (Exception e) {
			}
			ExamSummaryDTO exam_summary = this.examSummaryService.getFinalExamResult(this.model.getExam_num(),"1",user);
			if("Y".equals(this.model.getSug_flag())){
				list = this.examSummaryService.createExamInfoCompositeDisease(this.model.getExam_num(),user.getCenter_num(),IS_DISEASE_SUG_CENTER);
			}else{
				list = this.examSummaryService.getExamInfoDisease(this.model.getExam_num(),"1");
				if(exam_summary != null && !exam_summary.getExam_status().equals("Z") && (list == null || list.size() == 0)){
					list = this.examSummaryService.createExamInfoCompositeDisease(this.model.getExam_num(),user.getCenter_num(),IS_DISEASE_SUG_CENTER);
				}
			}
			this.outJsonResult(list);
			return NONE;
		}
		
		/**
		 * 主检室跟科室显示危急值列表 2301
		     * @Title: getExamCriticalList   
		     * @Description: TODO(这里用一句话描述这个方法的作用)   
		     * @param: @return
		     * @param: @throws WebException
		     * @param: @throws SQLException      
		     * @return: String      
		     * @throws
		 */
		public String getExamCriticalList() throws WebException,SQLException{
			UserDTO user = (UserDTO) session.get("username");
			List<CriticalDTO> list = null;
			list = this.examSummaryService.getExamCriticalList(this.model.getExam_num());

			this.outJsonResult(list);
			SysLog sl =  new SysLog();
			sl.setCenter_num(user.getCenter_num());
			sl.setUserid(user.getUserid()+"");
			sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
			sl.setXtgnid("");//可不填写
			sl.setXtgnid2("2301");//子功能id 必须填写
			sl.setExplain("");//操作说明
			syslogService.saveSysLog(sl);
			return NONE;
		}
		/**
		 * 批量复审922
		     * @Title: getBatchRetrial   
		     * @Description: TODO(这里用一句话描述这个方法的作用)   
		     * @param: @return
		     * @param: @throws WebException      
		     * @return: String      
		     * @throws
		 */
		public String getBatchRetrial() throws WebException{
			UserDTO user  = (UserDTO) session.get("username");
            this.defaultapp = (SystemType) session.get("defaultapp");
			String mes = this.examSummaryService.getBatchRetrial(this.model.getExam_num(),user,this.defaultapp.getComid());
			SysLog sl =  new SysLog();
			sl.setCenter_num(user.getCenter_num());
			sl.setUserid(user.getUserid()+"");
			sl.setOper_type("2");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
			sl.setXtgnid("");//可不填写
			sl.setXtgnid2("922");//子功能id 必须填写
			sl.setExplain("");//操作说明
			syslogService.saveSysLog(sl);
			this.outJsonStrResult(mes);
			return NONE;
		}
}
