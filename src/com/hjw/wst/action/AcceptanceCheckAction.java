package com.hjw.wst.action;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.hjw.util.DateTimeUtil;
import com.hjw.wst.DTO.CommonExamDetailDTO;
import com.hjw.wst.DTO.DepExamResultDTO;
import com.hjw.wst.DTO.ExamDepResultDTO;
import com.hjw.wst.DTO.ExamInfoDTO;
import com.hjw.wst.DTO.ExamResultDetailDTO;
import com.hjw.wst.DTO.ExamSuggestionLogDTO;
import com.hjw.wst.DTO.ExamSummaryDTO;
import com.hjw.wst.DTO.ExaminfoChargingItemDTO;
import com.hjw.wst.DTO.ExaminfoDiseaseDTO;
import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.wst.DTO.TreeDTO;
import com.hjw.wst.DTO.UserDTO;
import com.hjw.wst.DTO.ViewExamDetailDTO;
import com.hjw.wst.domain.ExamInfo;
import com.hjw.wst.domain.ExamSuggestionLog;
import com.hjw.wst.domain.SysLog;
import com.hjw.wst.model.AcceptanceCheckModel;
import com.hjw.wst.service.AcceptanceCheckService;
import com.hjw.wst.service.CustomerInfoService;
import com.hjw.wst.service.SyslogService;
import com.opensymphony.xwork2.ModelDriven;
import com.synjones.framework.exception.ServiceException;
import com.synjones.framework.exception.WebException;
import com.synjones.framework.web.action.BaseAction;

/**
 * 花名册  (核收) 功能 
     * @Title:  火箭蛙体检管理系统   
     * @Package com.hjw.wst.action   
     * @Description:  
     * @author: dangqi     
     * @date:   2016年10月18日 下午10:16:07   
     * @version V2.0.0.0
 */
public class AcceptanceCheckAction extends BaseAction implements ModelDriven{
	
	private static final long serialVersionUID = 8217814910220881058L;
	
	private AcceptanceCheckModel model = new AcceptanceCheckModel();
	private AcceptanceCheckService acceptanceCheckService;
	private CustomerInfoService customerInfoService;
	private SyslogService syslogService;    
	
	private int rows = 15; // easyui每页显示条数
	private String sort;
	private String order;
	private long id;
	
	//private String arch_com_ids;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
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
	@Override
	public Object getModel() {
		return model;
	}
	
	public void setModel(AcceptanceCheckModel model) {
		this.model = model;
	}
	
	public void setAcceptanceCheckService(AcceptanceCheckService acceptanceCheckService) {
		this.acceptanceCheckService = acceptanceCheckService;
	}

	public void setCustomerInfoService(CustomerInfoService customerInfoService) {
		this.customerInfoService = customerInfoService;
	}
	
	public void setSyslogService(SyslogService syslogService) {
   		this.syslogService = syslogService;
   	}

	/**
	 * 体检花名册页面  369
	     * @Title: getExamRoster   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String getAcceptanceCheck() throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("369");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return SUCCESS;
	}
	
	/**
	 * 根据条件查询体检人员信息列表 370
	     * @Title: getExamRosterList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String getExamRosterList() throws WebException,SQLException{
		UserDTO user = (UserDTO) session.get("username");
		PageReturnDTO pageDto = this.acceptanceCheckService.getExamInfoList(model, this.rows, this.getPage(),this.sort,this.order,user);
		this.outJsonResult(pageDto);
		return NONE;
	}
	
	/**
	 * 根据体检号查询检查结果页面 371
	     * @Title: getExamItemResultByExamNum   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String getExamItemResultByExamNum() throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		String isAcceptanceCheck = this.customerInfoService.getCenterconfigByKey("IS_ACCEPTANCECHECK", user.getCenter_num()).getConfig_value().trim();
		this.model.setIsAcceptanceCheck(isAcceptanceCheck);
		return SUCCESS;
	}
	
	/**
	 * 获取体检人的体检科室与项目 372
	     * @Title: getDepAndItemTree   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String getDepAndItemTree() throws WebException,SQLException{
		UserDTO user = (UserDTO) session.get("username");
		List<TreeDTO> list = this.acceptanceCheckService.getDepAndItemTree(this.model.getExam_num(), user.getCenter_num());
		this.outJsonResult(list);
		return NONE;
	}
	
	/**
	 * 获取普通科室检查项目的检查结果 373
	     * @Title: getPtResultList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String getPtResultList() throws WebException,SQLException{
		List<CommonExamDetailDTO> list = this.acceptanceCheckService
				.getPtResultList(this.model.getExam_num(), this.model.getCharging_item_ids());
		this.outJsonResult(list);
		return NONE;
	}
	
	/**
	 * 获取检验科检查项目的检查结果 374
	     * @Title: getHyResultList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String getHyResultList() throws WebException,SQLException{
		List<ExamResultDetailDTO> list = this.acceptanceCheckService
				.getHyResultList(this.model.getExam_num(), this.model.getCharging_item_ids());
		this.outJsonResult(list);
		return NONE;
	}
	
	/**
	 * 获取影像科室检查项目的检查结果 375
	     * @Title: getYxResultList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String getYxResultList() throws WebException,SQLException{
		List<ViewExamDetailDTO> list = this.acceptanceCheckService
				.getYxResultList(this.model.getExam_num(), this.model.getCharging_item_ids());
		this.outJsonResult(list);
		return NONE;
	}
	
	/**
	 * 获取科室项目检查医生和科室结论  376
	     * @Title: getExamDoctorAndDepResult   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String getExamDoctorAndDepResult() throws WebException,SQLException{
		ExamDepResultDTO examDepResultDTO = this.acceptanceCheckService
				.getExamDoctorAndDepResult(this.model.getExam_num(), this.model.getCharging_item_ids());
		this.outJsonResult(examDepResultDTO);
		return NONE;
	}
	
	/**
	 * 获取总检结论 377
	     * @Title: getFinalSummaryResult   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String getFinalSummaryResult() throws WebException,SQLException{
		UserDTO user = (UserDTO) session.get("username");
		String isAcceptanceCheck = this.customerInfoService.getCenterconfigByKey("IS_ACCEPTANCECHECK", user.getCenter_num()).getConfig_value().trim();
		ExamSummaryDTO examSummaryDTO = this.acceptanceCheckService.getFinalSummaryResult(this.model.getExam_num(),user);
		
		if(examSummaryDTO == null){
			examSummaryDTO = new ExamSummaryDTO();
		}
		examSummaryDTO.setApprove_status(isAcceptanceCheck);
		ExamInfo examinfo = this.acceptanceCheckService.getExamInfoByid(this.model.getExam_num()); 
		if(examinfo != null){
			if("Y".equals(examinfo.getStatus())){
				examSummaryDTO.setExam_status("预约");
			}else if("D".equals(examinfo.getStatus())){
				examSummaryDTO.setExam_status("登记");
			}else if("J".equals(examinfo.getStatus())){
				examSummaryDTO.setExam_status("检查中");
			}else{
				if("Z".equals(examSummaryDTO.getFinal_status())){
					examSummaryDTO.setExam_status("总检");
					examSummaryDTO.setFinal_doctor(examSummaryDTO.getUser_name());
					
					if("Y".equals(isAcceptanceCheck)){
						if(1 == examSummaryDTO.getAcceptance_check()){
							examSummaryDTO.setExam_status("核收");
						}
					}
					
					if("A".equals(examSummaryDTO.getApprove_status())){
						examSummaryDTO.setExam_status("审核");
					}
				}
			}
		}
		this.outJsonResult(examSummaryDTO);
		return NONE;
	}
	
	/**
	 * 获取总检疾病与建议 378
	     * @Title: getExamDiseaseResult   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String getExamDiseaseResult() throws WebException,SQLException{
		List<ExaminfoDiseaseDTO> list = this.acceptanceCheckService.getExamDiseaseResult(this.model.getExam_num());
		this.outJsonResult(list);
		return NONE;
	}
	
	/**
	 * 图片显示页面 379
	     * @Title: showViewExamImage   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String showViewExamImage() throws WebException{
		return SUCCESS;
	}
	
	/**
	 * 获取影像科室图片路径 380
	     * @Title: getViewExamImagePath   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String getViewExamImagePath() throws WebException,SQLException{
		List<ViewExamDetailDTO> list = this.acceptanceCheckService.getViewExamImagePath(this.model.getPacs_req_code(),this.model.getExam_num());
		this.outJsonResult(list);
		return NONE;
	}
	
	/**
	 * 核收与取消核收操作保存 381
	     * @Title: AcceptanceCheckExamInfo   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String acceptanceCheckExamInfo() throws WebException,SQLException{
		UserDTO user = (UserDTO) session.get("username");
		this.message = this.acceptanceCheckService.saveAcceptanceExamInfo(model, user);
		
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("2");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("381");//子功能id 必须填写
		if("0".equals(model.getAcceptance_check())){
			sl.setExplain("取消核收");//操作说明
		}else{
			sl.setExplain("核收");//操作说明
		}
		syslogService.saveSysLog(sl);
		
		this.outJsonStrResult(this.message);
		return NONE;
	}
	
	/**
	 * 核收界面报告预览意见
	     * @Title: examSuggestionInfo   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String examSuggestionInfo() throws WebException,SQLException{
		UserDTO user = (UserDTO) session.get("username");
		ExamSuggestionLog examSuggestionLog = this.acceptanceCheckService.serchExamSuggestionInfo(this.model.getExam_num(),user.getUserid());
		
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("2100");//子功能id 必须填写
		syslogService.saveSysLog(sl);
		
		this.outJsonResult(examSuggestionLog);
		return NONE;
	}
	
	/**
	 * 核收界面报告预览意见
	     * @Title: getExamSuggestionList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String getExamSuggestionList() throws WebException,SQLException{
		UserDTO user = (UserDTO) session.get("username");
		List<ExamSuggestionLogDTO> list = new ArrayList<ExamSuggestionLogDTO>();
		list = this.acceptanceCheckService.serchExamSuggestionInfo(this.model.getExam_num());
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("2102");//子功能id 必须填写
		syslogService.saveSysLog(sl);
		
		this.outJsonResult(list);
		return NONE;
	}
	
	/**
	 * 核收界面报告预览意见保存
	     * @Title: examSuggestionInfo   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String saveExamSuggestionInfo() throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		//String type=request.getParameter("type");
		ExamSuggestionLog examSuggestionLog = new ExamSuggestionLog();
		//if(type.equals("1")){
			examSuggestionLog.setExam_num(this.model.getExam_num());
			examSuggestionLog.setNotices(this.model.getNotices());
			examSuggestionLog.setCreater(user.getUserid());
			examSuggestionLog.setCreate_date(DateTimeUtil.parse());
			examSuggestionLog.setUpdater(user.getUserid());
			examSuggestionLog.setUpdate_date(DateTimeUtil.parse());
			examSuggestionLog.setResource("001");
			examSuggestionLog.setApptype(1);
			this.acceptanceCheckService.saveExamSuggestionInfo(examSuggestionLog);
		/*}else if(type.equals("2")){
			long id=Long.parseLong(request.getParameter("id"));
			examSuggestionLog.setId(id);
			examSuggestionLog.setNotices(this.model.getNotices());
			examSuggestionLog.setCreater(user.getUserid());
			examSuggestionLog.setCreate_date(DateTimeUtil.parse());
			examSuggestionLog.setUpdater(user.getUserid());
			examSuggestionLog.setUpdate_date(DateTimeUtil.parse());
			this.acceptanceCheckService.editExamSuggestionInfo(examSuggestionLog,id);
		}*/
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("2101");//子功能id 必须填写
		syslogService.saveSysLog(sl);
		this.message = "保存成功!";
		this.outJsonStrResult(this.message);
		return NONE;
	}
	
	/**
	 * 核收界面报告预览意见删除
	     * @Title: getExamSuggestionList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String delExamSuggestion() throws WebException,SQLException{
		UserDTO user = (UserDTO) session.get("username");
		this.acceptanceCheckService.delExamSuggestion(id);
		this.message="删除成功";
		this.outJsonStrResult(this.message);
		
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("3");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("2104");//子功能id 必须填写
		sl.setExplain("报告预览建议删除");//操作说明
		syslogService.saveSysLog(sl);
		
		return NONE;
	}
	
	/**
	 * 核收页面  390
	     * @Title: acceptanceCheck   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String getAcceptanceItem() throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		this.model.setReport_print_type(this.customerInfoService.getCenterconfigByKey("REPORT_PRINT_TYPE", user.getCenter_num()).getConfig_value().trim());
		this.model.setZyb_report_print_type(this.customerInfoService.getCenterconfigByKey("ZYB_REPORT_PRINT_TYPE", user.getCenter_num()).getConfig_value().trim());
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("390");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return SUCCESS;
	}
	
	/**
	 * 报告预览意见
	     * @Title: acceptanceCheck   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String getExamSuggestionEditPage() throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		this.model.setExam_num(this.model.getExam_num());
		this.model.setUserId(user.getUserid());
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("2103");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return SUCCESS;
	}
	
	/**
	 * 核收界面报告预览意见驳回判断是否总检 2108
	     * @Title: getExamSuggestionList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String validateZJ() throws WebException,SQLException{
		UserDTO user = (UserDTO) session.get("username");
		String states = this.acceptanceCheckService.validateZJ(this.model.getExam_num());
		if(states == "Z" ||states.equals("Z")){
			this.message="已总检";
		}else{
			this.message="未总检";
		}
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("2108");//子功能id 必须填写
		syslogService.saveSysLog(sl);
		
		this.outJsonStrResult(this.message);
		return NONE;
	}
	
	/**
	 * 报告驳回
	     * @Title: acceptanceCheck   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String getReportRejectEditPage() throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		this.model.setExam_id(this.model.getExam_id());
		this.model.setExam_num(this.model.getExam_num());
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("2105");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return SUCCESS;
	}
	
	/**
	 * 查询各科室检查结果 391
	     * @Title: getAcceptanceItemResult   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String getAcceptanceItemResult() throws WebException,SQLException{
		UserDTO user = (UserDTO) session.get("username");
		String IS_EXAM_RESULT_CITEM = this.customerInfoService.getCenterconfigByKey("IS_EXAM_RESULT_CITEM", user.getCenter_num()).getConfig_value();
		List<DepExamResultDTO> list = this.acceptanceCheckService.getAcceptanceItemResult(this.model.getExam_num(),IS_EXAM_RESULT_CITEM);
		this.outJsonResult(list);
		return NONE;
	}
	
	
	/**
	 * 合并档案
	     * @Title: combineArch   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String combineArch()throws WebException,SQLException{
		
		return SUCCESS;
	}
	
	public String getselectedarch()throws WebException,SQLException{
		UserDTO user = (UserDTO) session.get("username");
		List<ExamInfoDTO> list=this.acceptanceCheckService.getselectedarch(this.model.getArch_com_ids(),user.getCenter_num());
		this.outJsonResult(list);
		return NONE;
	}
	/**
	 * @throws Exception 
	 * @throws ServiceException 
	 * 自动合并档案
	     * @Title: autocombinearch   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String autocombinearch()throws ServiceException, Exception{
		UserDTO user = (UserDTO) session.get("username");
		this.acceptanceCheckService.editExam_ext_info(this.model.getArch_com_ids(),this.model.getStr_arch_num(),user);
		this.outJsonStrResult(this.message="自动合并档案成功");
		return NONE;
	}
	
	public String updateArchCombine()throws WebException,SQLException{
		UserDTO user = (UserDTO) session.get("username");
		this.acceptanceCheckService.updateArchCombine(this.model.getArch_com_ids(), this.model.getArch_num(),this.model.getCustomer_id(), user);
		this.outJsonStrResult(this.message="档案合并成功");
		return NONE;
	}
}
