package com.hjw.wst.action;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.axis.utils.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hsqldb.lib.StringUtil;

import com.hjw.wst.DTO.ExamFlowLogDTO;
import com.hjw.wst.DTO.ExamInfoDTO;
import com.hjw.wst.DTO.ExamInfoUserDTO;
import com.hjw.wst.DTO.ExamSummaryDTO;
import com.hjw.wst.DTO.ExaminfoChargingItemDTO;
import com.hjw.wst.DTO.JobDTO;
import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.wst.DTO.UserDTO;
import com.hjw.wst.DTO.WebserviceConfigurationDTO;
import com.hjw.wst.domain.ChargingItem;
import com.hjw.wst.domain.ExamFlow;
import com.hjw.wst.domain.ExamInfo;
import com.hjw.wst.domain.SysLog;
import com.hjw.wst.domain.WebUserInfo;
import com.hjw.wst.model.ExamFlowModel;
import com.hjw.wst.service.ChargingItemService;
import com.hjw.wst.service.CustomerInfoService;
import com.hjw.wst.service.ExamFlowService;
import com.hjw.wst.service.SyslogService;
import com.hjw.wst.service.WebserviceConfigurationService;
import com.hjw.wst.service.examInfoService;
import com.opensymphony.xwork2.ModelDriven;
import com.hjw.config.Logincheck;
import com.hjw.util.DateTimeUtil;
import com.hjw.webService.client.HealthResMessage;
import com.hjw.webService.client.LISResMessage;
import com.hjw.webService.client.LISResStatusMessage;
import com.hjw.webService.client.PACSResMessage;
import com.hjw.webService.client.body.HealthFileResBody;
import com.synjones.framework.exception.WebException;
import com.synjones.framework.web.action.BaseAction;

@SuppressWarnings("rawtypes")
public class ExamflowManagerAction extends BaseAction implements ModelDriven {

	private static final long serialVersionUID = 1L;
	@SuppressWarnings("unused")
	private Log log = LogFactory.getLog(this.getClass());
	@SuppressWarnings("unused")
	private Logincheck logincheck;
	private int rp = 15; // 每页显示的条数
	private int page = 1; // 当前页
	private int rows = 15; // easyui每页显示条数
	private String sort;
	private String order;
	private ExamFlowModel model = new ExamFlowModel();  
    private ExamFlowService examFlowService;    
    private SyslogService syslogService; 
    private CustomerInfoService customerInfoService;
    private ChargingItemService chargingItemService;
    private WebserviceConfigurationService webserviceConfigurationService;
	private examInfoService examInfoService;
    
    private String sfz_div_ocx;// 身份证读卡器ocx
   	private String sfz_div_code;// 身份证厂家代码

   	public void setSyslogService(SyslogService syslogService) {
   		this.syslogService = syslogService;
   	}
	public int getRp() {
		return rp;
	}

	public void setRp(int rp) {
		this.rp = rp;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public void setExamFlowService(ExamFlowService examFlowService) {
		this.examFlowService = examFlowService;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public Object getModel() {
		return model;
	}

	public void setModel(ExamFlowModel model) {
		this.model = model;
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
	public CustomerInfoService getCustomerInfoService() {
		return customerInfoService;
	}
	public void setCustomerInfoService(CustomerInfoService customerInfoService) {
		this.customerInfoService = customerInfoService;
	}
	public void setChargingItemService(ChargingItemService chargingItemService) {
		this.chargingItemService = chargingItemService;
	}
	public void setWebserviceConfigurationService(WebserviceConfigurationService webserviceConfigurationService) {
		this.webserviceConfigurationService = webserviceConfigurationService;
	}
	public String getSfz_div_ocx() {
		return sfz_div_ocx;
	}
	public void setSfz_div_ocx(String sfz_div_ocx) {
		this.sfz_div_ocx = sfz_div_ocx;
	}
	public String getSfz_div_code() {
		return sfz_div_code;
	}
	public void setSfz_div_code(String sfz_div_code) {
		this.sfz_div_code = sfz_div_code;
	}
	
	public void setExamInfoService(examInfoService examInfoService) {
		this.examInfoService = examInfoService;
	}
	/**
	 * 
	     * @Title: examflowManager   
	     * @Description: 7 体检单查询统计
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String examflowManager() throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("7");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return SUCCESS;
	}
	
	/**
	 * 
	     * @Title: djdexamflowin   
	     * @Description: 分发导检单   5
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String djdexamflowin() throws WebException{
		return SUCCESS;
	}
	
	/**
	 * 
	     * @Title: getDjtExamInfoUserList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   437
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String getexaminfoForNum() throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		if((this.model.getExam_num()!=null)||(this.model.getExam_num().trim().length()>0)){
			ExamInfoUserDTO eu = new ExamInfoUserDTO();
			eu=this.examFlowService.getExamFlowInfoForNum(this.model.getExam_num(),this.model.getTypes(),user.getUserid(),user);
			if(eu.getId()<=0){
				this.message="error";
			}else{
				this.outJsonResult(eu);
			}
		}else{
		    this.message="error";
		    this.outJsonStrResult(this.message);
		}
		return NONE;
	}
	
	/**
	 * 
	     * @Title: examflowinsave   
	     * @Description: 导检单分发保存   438
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String examflowinsave() throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		if(this.model.getIds().length()>0){
			int i=0;
			String[] idss=this.model.getIds().split(",");
			for(String exam_num:idss){
				ExamInfoDTO examInfoForexamId = customerInfoService.getExamInfoForexamNum(exam_num);
				if (examInfoForexamId != null) {
					ExamFlow ef= new ExamFlow();
					ef.setCenter_num(user.getCenter_num());
					ef.setFromacc(user.getUserid());
					ef.setExam_num(exam_num);
					ef.setFromacc_date(DateTimeUtil.getDateTime());
					ef.setRemark(this.model.getRemark());
					ef.setTypes("0");
					ef.setToacc(this.model.getToacc());
					ef.setToacc_date(null);
					ef.setExam_num(examInfoForexamId.getExam_num());
					this.examFlowService.saveExamFlow(ef);
					
					//UserDTO user = (UserDTO) session.get("username");
					SysLog sl =  new SysLog();
					sl.setCenter_num(user.getCenter_num());
					sl.setUserid(user.getUserid()+"");
					sl.setOper_type("1");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
					sl.setXtgnid("");//可不填写
					sl.setXtgnid2("438");//子功能id 必须填写
					sl.setExplain("导检单分发保存 "+exam_num);//操作说明
					syslogService.saveSysLog(sl);
					i++;
				}
			}
			this.message="ok-合计保存成功:【"+i+"】个导检单";
		}else{
			this.message="error-无效体检编号";
		}
		this.outJsonStrResult(this.message);
		return NONE;
	}
	
	/**
	 * 
	     * @Title: djdexamflowout   
	     * @Description: 接收导检单 6
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String djdexamflowout() throws WebException{
		return SUCCESS;
	}
	
	/**
	 * 	
	     * @Title: examflowoutsave   
	     * @Description: 导检单接受  439)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String examflowoutsave() throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		if(this.model.getIds().length()>0){
			int i=0;
			String[] idss=this.model.getIds().split(",");
			for(String exam_num:idss){
				ExamInfoDTO examInfoForexamId = customerInfoService.getExamInfoForexamNum(exam_num);
				if (examInfoForexamId != null) {
					ExamFlow ef= new ExamFlow();
					ef.setExam_num(exam_num);
					ef.setCenter_num(user.getCenter_num());
					ef.setFromacc(this.model.getFromacc());
					ef.setFromacc_date(null);
					ef.setRemark(this.model.getRemark());
					ef.setTypes("1");
					ef.setToacc(user.getUserid());
					ef.setToacc_date(DateTimeUtil.getDateTime());
					ef.setExam_num(examInfoForexamId.getExam_num());
					
					this.examFlowService.saveExamFlow(ef);
					//UserDTO user = (UserDTO) session.get("username");
					SysLog sl =  new SysLog();
					sl.setCenter_num(user.getCenter_num());
					sl.setUserid(user.getUserid()+"");
					sl.setOper_type("1");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
					sl.setXtgnid("");//可不填写
					sl.setXtgnid2("439");//子功能id 必须填写
					sl.setExplain("导检单接受保存 "+exam_num);//操作说明
					syslogService.saveSysLog(sl);
					i++;
				}
			}
			this.message="ok-合计保存成功:【"+i+"】个导检单";
		}else{
			this.message="error-无效体检编号";
		}
		this.outJsonStrResult(this.message);
		return NONE;
	}
	
	/**
	 * 
	     * @Title: djdexamflowshow   
	     * @Description: 导检单查询 434
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String djdexamflowshow() throws WebException{
		this.model.setTime1(DateTimeUtil.getDate2());
		this.model.setTime2(DateTimeUtil.getDate2());
		return SUCCESS;
	}	
	
	/**
	 * 
	     * @Title: examflowManagerall   
	     * @Description: 导检单查询统计   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String examflowManagerall() throws WebException{
		return SUCCESS;
	}	
	
	/**
	 * 
	     * @Title: djdexamflowtotalshow   
	     * @Description: 导检单个人统计   435
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String djdexamflowtotalshow() throws WebException{
		this.model.setTime1(DateTimeUtil.getDate2());
		this.model.setTime2(DateTimeUtil.getDate2());
		return SUCCESS;
	}
	
	/**
	 * 
	     * @Title: examflowListshow   
	     * @Description: 440 导检单查询   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String examflowListshow() throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		PageReturnDTO eu = new PageReturnDTO();
		if ((!StringUtil.isEmpty(this.model.getExam_num())) 
				|| (!StringUtil.isEmpty(this.model.getUsername()))
				|| ((!StringUtil.isEmpty(this.model.getTime1())) && (!StringUtil.isEmpty(this.model.getTime2())))) {		
			eu = this.examFlowService.getExamFlowForNum(this.model.getExam_num(),this.model.getUsername(),
					this.model.getTime1(), this.model.getTime2(),user.getUserid(),this.model.getTypes(), user.getCenter_num(),this.rows, this.getPage(),user);			
		}
		this.outJsonResult(eu);
		return NONE;
	}
	
	/**
	 * 
	     * @Title: examflowListtotal   
	     * @Description: 个人统计导检单 436   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String examflowListtotal() throws WebException{
		UserDTO user = (UserDTO) session.get("username");		
		PageReturnDTO contractlist = new PageReturnDTO();
		contractlist=this.examFlowService.getExamFlowForNumTotal(user.getUserid(),this.model.getTypes(), this.model.getTime1(), this.model.getTime2(),user);
		this.outJsonResult(contractlist);
		return NONE;
	}
	
	/**
	 * 
	     * @Title: djdexamflowshowsearchall   
	     * @Description: 导检单多人查询 454   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String djdexamflowshowsearchall()throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		PageReturnDTO eu = new PageReturnDTO();
		if ((!StringUtil.isEmpty(this.model.getExam_num())) 
				|| (!StringUtil.isEmpty(this.model.getUsername()))
				|| ((!StringUtil.isEmpty(this.model.getTime1())) && (!StringUtil.isEmpty(this.model.getTime2())))) {		
			eu = this.examFlowService.getExamFlowForNum(this.model.getExam_num(),this.model.getUsername(),
					this.model.getTime1(), this.model.getTime2(),this.model.getFromacc(),this.model.getTypes(), user.getCenter_num(),this.rows, this.getPage(),user);			
		}
		this.outJsonResult(eu);
		return NONE;
	}
	
	/**
	 * 
	     * @Title: examflowListtotalall   
	     * @Description: 个人合计导检单2   441 
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String examflowListtotalall() throws WebException{
		UserDTO user = (UserDTO) session.get("username");		
		int list = 0;
		list = this.examFlowService.getExamFlowForNumTotalall(user.getUserid(),this.model.getTypes(),this.model.getTime1(), this.model.getTime2(),user);
		this.outJsonStrResult(list+"");
		return NONE;
	}
	
	/**
	 * 
	     * @Title: examflowListtotal   
	     * @Description: 导检单多人按天统计 444   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String examflowListtotalcountall() throws WebException{	
		PageReturnDTO contractlist = new PageReturnDTO();
		UserDTO user = (UserDTO) session.get("username");
		if(this.model.getToacc()>0){
			contractlist=this.examFlowService.getExamFlowForNumTotal(this.model.getToacc(),this.model.getTypes(), this.model.getTime1(), this.model.getTime2(),user);
		}
		this.outJsonResult(contractlist);
		return NONE;
	}
	
	/**
	 * 
	     * @Title: examflowListtotalall   
	     * @Description: 导检单多人合计统计 445 
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String examflowListtotalallall() throws WebException{
		int countss = 0;
		UserDTO user = (UserDTO) session.get("username");
		if(this.model.getToacc()>0){
			countss = this.examFlowService.getExamFlowForNumTotalall(this.model.getToacc(),this.model.getTypes(),this.model.getTime1(), this.model.getTime2(),user);	
		}		
		this.outJsonStrResult(countss+"");
		return NONE;
	}

	/**
	 * 
	     * @Title: djdexamflowshowall   
	     * @Description: 导检单多人查询   442
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String djdexamflowshowall() throws WebException{
		this.model.setTime1(DateTimeUtil.getDate2());
		this.model.setTime2(DateTimeUtil.getDate2());
		return SUCCESS;
	}
	
	/**
	 * 
	     * @Title: djdexamflowtotalshow   
	     * @Description: 导检单多人统计 443
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String djdexamflowtotalshowall() throws WebException{
		this.model.setTime1(DateTimeUtil.getDate2());
		this.model.setTime2(DateTimeUtil.getDate2());
		return SUCCESS;
	}
	/**
	 * 
	     * @Title: ExamflowManagerAction   
	     * @Description: 流程-导检单核收 1200   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String flowEndRecovery()  throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		this.model.setTime1(DateTimeUtil.getDate2());
		this.model.setTime2(DateTimeUtil.getDate2());
		this.model.setReport_print_type(
				this.customerInfoService.getCenterconfigByKey("REPORT_PRINT_TYPE", user.getCenter_num()).getConfig_value().trim());
		this.model.setUpload_flow(
				this.customerInfoService.getCenterconfigByKey("UPLOAD_FLOW", user.getCenter_num()).getConfig_value().trim());
		String IS_FLOW_DJDRECOVERY_SHOW="Y";
		try{
			IS_FLOW_DJDRECOVERY_SHOW=this.customerInfoService.getCenterconfigByKey("IS_FLOW_DJDRECOVERY_SHOW", user.getCenter_num()).getConfig_value().trim();
		}catch(Exception ex){}
		this.model.setReportAddress(IS_FLOW_DJDRECOVERY_SHOW);
		String IS_SHOW_YANQITIME="N";
		try{
			IS_SHOW_YANQITIME=this.customerInfoService.getCenterconfigByKey("IS_SHOW_YANQITIME", user.getCenter_num()).getConfig_value().trim();
		}catch(Exception ex){}
		this.model.setIs_show_yanqitime(IS_SHOW_YANQITIME);
		return SUCCESS;
	}
	
	/**
	 * 
	     * @Title: flowEndRecoveryexam   
	     * @Description: 导检单核收-获取体检项目信息 441  
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String flowEndRecoveryexam()  throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		List<ExaminfoChargingItemDTO> list=this.examFlowService.queryExamInfo(this.model.getExam_num(),this.model.getTypes(), user.getCenter_num());
		this.outJsonResult(list);
		return NONE;
	}
	
	/**
	 * 
	     * @Title: updateyanqi   
	     * @Description: 导引单批量延期1207  
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String flowupdateyanqi() throws WebException{
		 UserDTO user = (UserDTO) session.get("username");
		 this.message=this.examFlowService.flowupdateplyq(this.model.getExam_num(),this.model.getId(),user);
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("2");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("1207");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		this.outJsonStrResult(this.message);
		return NONE;
	}
	
	/**
	 * 
	     * @Title: updateExam   
	     * @Description:流程-导检单核收  弃检/延期 --恢复443   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String flowupdateExam() throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		this.message=this.examFlowService.flowupdateExam(this.model.getExam_num(),this.model.getId(),user);
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("2");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("1204");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		this.outJsonStrResult(this.message);
		return NONE;
	}
	
	/**
	 * 
	     * @Title: deleteExam   
	     * @Description: 导检单核收-放弃1203 
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String flowdeleteExam() throws WebException{		
		UserDTO user = (UserDTO) session.get("username");
		this.message=this.examFlowService.flowdeleteExam(this.model.getExam_num(),this.model.getId(),user);
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("3");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("444");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		this.outJsonStrResult(message);
		return NONE;
	}
	
	/**
	 * 
	     * @Title: getZDSmessageShow   
	     * @Description: 站内通知编辑页面  
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String getZDSmessageShow()throws WebException{
		ExamInfoDTO ei=this.customerInfoService.getExamInfoForexamNum(this.model.getExam_num());
		ChargingItem ci =this.chargingItemService.findChargingItem(this.model.getSet_id());
		if(((ei!=null)&&(ei.getId()>0)&&(ci!=null)&&(ci.getId()>0))){
			this.message=ei.getUser_name()+"["+ei.getExam_num()+"] "+ci.getItem_name()+"["+ci.getItem_code()+"] 未录入";
		}
		return SUCCESS;
	}
	
	/**
	 * 
	     * @Title: getZDSDoctors   
	     * @Description: 获取每个科室对应的医生人员
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String getZDSDoctors()throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		List<WebUserInfo> doctors = examFlowService.getZDSDoctors(this.model.getId(), user.getCenter_num());
		this.outJsonResult(doctors);
		return NONE;
	}
	/**
	 * 
	     * @Title: zlxmessagesave   
	     * @Description: 给文本域发送消息
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String zlxmessagesave()throws WebException{
		if(this.model.getUser_id()<=0){
			this.message="error-无效接收者";
		}else if((this.model.getRemark()==null)||(this.model.getRemark().trim().length()<=0)){
			this.message="error-无效消息体";
		}else{
			UserDTO user = (UserDTO) session.get("username");
			this.message=this.examFlowService.saveMessage(user.getUserid(), this.model.getUser_id(), this.model.getRemark());
			this.message=this.examFlowService.flowupdatetx(this.model.getExam_num(),this.model.getId(),user);
		}
		this.outJsonStrResult(message);
		return NONE;
	}
	
	/**
     * 
         * @Title: 导检单核收  无需总检   
         * @Description: TODO(这里用一句话描述这个方法的作用)   
         * @param: @return
         * @param: @throws WebException
         * @param: @throws SQLException      
         * @return: String      
         * @throws
     */
    public String guidewuxuzongjian() throws WebException,SQLException{
    	UserDTO user = (UserDTO) session.get("username");
    	if(!StringUtils.isEmpty(model.getExam_num())){
			String wuxu = this.examFlowService.guidewuxuzongjian(model.getExam_num());
			//判断是否回访
			this.customerInfoService.updateExaminfoIsVisit(model.getExam_num(), user.getCenter_num());
			SysLog sl =  new SysLog();
			sl.setCenter_num(user.getCenter_num());
			sl.setUserid(user.getUserid()+"");
			sl.setOper_type("2");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
			sl.setXtgnid("");//可不填写
			sl.setXtgnid2("935");//子功能id 必须填写
			sl.setExplain("导引单核发 无需总检");//操作说明
			syslogService.saveSysLog(sl);
			this.message=wuxu+"操作成功";
			/*}*/
			this.outJsonStrResult(this.message);
		}else{
			this.message="error";
		    this.outJsonStrResult(this.message);
		}
		
		return NONE;
    }
    /**
     * 
     * @Title: 导检单核收  需要总检   
     * @Description: TODO(这里用一句话描述这个方法的作用)   
     * @param: @return
     * @param: @throws WebException
     * @param: @throws SQLException      
     * @return: String      
     * @throws
     */
    public String guidexuyaozongjian() throws WebException,SQLException{
    	UserDTO user = (UserDTO) session.get("username");
    	if(!StringUtils.isEmpty(model.getExam_num())){
    		String wuxu = this.examFlowService.guidexuyaozongjian(model.getExam_num());
    		//判断是否回访
			this.customerInfoService.updateExaminfoIsVisit(model.getExam_num(), user.getCenter_num());
    		SysLog sl =  new SysLog();
    		sl.setCenter_num(user.getCenter_num());
    		sl.setUserid(user.getUserid()+"");
    		sl.setOper_type("2");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
    		sl.setXtgnid("");//可不填写
    		sl.setXtgnid2("936");//子功能id 必须填写
    		sl.setExplain("导引单核发 需要总检");//操作说明
    		syslogService.saveSysLog(sl);
    		this.message=wuxu+"操作成功";
    		
    		this.outJsonStrResult(this.message);
    	}else{
    		this.message="error";
    		this.outJsonStrResult(this.message);
    	}
    	
    	return NONE;
    }
    /**
     * 
         * @Title: upd_getReportWay   
         * @Description: 更新报告领取方式  
         * @param: @return
         * @param: @throws WebException
         * @param: @throws SQLException      
         * @return: String      
         * @throws
     */
    public String upd_getReportWay() throws WebException,SQLException{
    	UserDTO user = (UserDTO) session.get("username");
    	if(!StringUtils.isEmpty(model.getExam_num())){
			this.examFlowService.upd_getReportWay(model, user);
			SysLog sl =  new SysLog();
			sl.setCenter_num(user.getCenter_num());
			sl.setUserid(user.getUserid()+"");
			sl.setOper_type("2");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
			sl.setXtgnid("");//可不填写
			sl.setXtgnid2("1353");//子功能id 必须填写
			sl.setExplain("");//操作说明
			syslogService.saveSysLog(sl);
			
			this.message="ok";
			this.outJsonStrResult(this.message);
		}else{
			this.message="error";
		    this.outJsonStrResult(this.message);
		}
		return NONE;
    }
    /**
     * 
         * @Title: upd_reportAddress   
         * @Description: 保存报告邮寄地址
         * @param: @return
         * @param: @throws WebException
         * @param: @throws SQLException      
         * @return: String      
         * @throws
     */
    public String upd_reportAddress() throws WebException,SQLException{
		UserDTO user = (UserDTO) session.get("username");
		if(!StringUtils.isEmpty(model.getExam_num())){
			this.examFlowService.upd_reportAddress(model, user);
			SysLog sl =  new SysLog();
			sl.setCenter_num(user.getCenter_num());
			sl.setUserid(user.getUserid()+"");
			sl.setOper_type("2");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
			sl.setXtgnid("");//可不填写
			sl.setXtgnid2("1354");//子功能id 必须填写
			sl.setExplain("");//操作说明
			syslogService.saveSysLog(sl);
			
			this.message="ok";
			this.outJsonStrResult(this.message);
		}else{
			this.message="error";
			this.outJsonStrResult(this.message);
		}
		return NONE;
	}
    /**
     * 
         * @Title: upd_emailAddress   
         * @Description: 保存电子邮箱   
         * @param: @return      
         * @return: String      
         * @throws
     */
    public  String upd_emailAddress(){
    	UserDTO user = (UserDTO) session.get("username");
		if(!StringUtils.isEmpty(model.getExam_num())){
			this.examFlowService.upd_emailAddress(this.model, user);
			SysLog sl =  new SysLog();
			sl.setCenter_num(user.getCenter_num());
			sl.setUserid(user.getUserid()+"");
			sl.setOper_type("2");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
			sl.setXtgnid("");//可不填写
			sl.setXtgnid2("1356");//子功能id 必须填写
			sl.setExplain("");//操作说明
			syslogService.saveSysLog(sl);
			
			this.message="ok";
			this.outJsonStrResult(this.message);
		}else{
			this.message="error";
			this.outJsonStrResult(this.message);
		}
    	return NONE;
    }
    /**
	 * 
	     * @Title: flowexamloglist 1216
	     * @Description: 获取流程日志   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String flowexamloglist()throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		List<ExamFlowLogDTO> list = this.examFlowService.flowexamLogForExamNum(this.model.getExam_num(), user);
		SysLog sl = new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid() + "");
		sl.setOper_type("3");// 0 查询，1插入 2修改 3 删除 4导入 9 其他
		sl.setXtgnid("");// 可不填写
		sl.setXtgnid2("1216");// 子功能id 必须填写
		sl.setExplain("");// 操作说明
		syslogService.saveSysLog(sl);
		this.outJsonResult(list);
		return NONE;
	}
	/**
	 * 
	     * @Title: flowreceiveExamInfo   
	     * @Description: 流程-导检单核收-导检单核收 1202   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String flowh0ExamInfo() throws WebException {
		UserDTO user = (UserDTO) session.get("username");		
		this.message=this.examFlowService.insertExamflowRecovery(this.model.getExam_num(), user);
		//判断是否回访
		this.customerInfoService.updateExaminfoIsVisit(this.model.getExam_num(), user.getCenter_num());//修改vip 回访标识
		//根据IsVisit添加回访策略
		ExamInfo examInfo = this.examInfoService.findExamInfo(this.model.getExam_num());
		if(examInfo != null && "1".equals(examInfo.getIsvisit()) && "ok".equals(this.message.split("-")[0])){
			try {
				String tactics_num = this.customerInfoService.getCenterconfigByKey("TACTICS_NUM", user.getCenter_num()).getConfig_value().trim();
				if(tactics_num != null && !"".equals(tactics_num)){
					String str = this.examFlowService.addTactics(examInfo,user,tactics_num);//添加策略回访计划
					this.message="ok-回收成功,"+str;
				}
				
			} catch (Exception e) {
				System.out.println("缺少TACTICS_NUM 配置。。。。");
				this.message="ok-回收成功,缺少TACTICS_NUM 配置。。。。";
			}
			
		}
			SysLog sl = new SysLog();
			sl.setCenter_num(user.getCenter_num());
			sl.setUserid(user.getUserid() + "");
			sl.setOper_type("9");// 0 查询，1插入 2修改 3 删除 4导入 5，登录 9 其他
			sl.setXtgnid("");// 可不填写
			sl.setXtgnid2("1202");// 子功能id 必须填写
			sl.setExplain("");// 操作说明
			syslogService.saveSysLog(sl);
			if(message.indexOf("ok")>=0){
				WebserviceConfigurationDTO wd = webserviceConfigurationService.getWebServiceConfig("LIS_STATUS", user.getCenter_num());
				if ((wd != null) && (!"0".equals(wd.getConfig_method()))) {
					LISResStatusMessage lis = new LISResStatusMessage();
					lis.lisSend(wd.getConfig_url(), wd.getConfig_method(), this.model.getExam_num(),this.model.getId());
				}
			}
		this.outJsonStrResult(this.message);
		return NONE;
	}
	/**
	 * 
	     * @Title: revocationDYD   
	     * @Description: 流程-导检单核收 撤销导引单1205 
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String flowrevocationDYD() throws WebException{
		    UserDTO user = (UserDTO) session.get("username");
			this.message = this.examFlowService.revocationDYD(this.model.getExam_num(),user);
   		    SysLog sl =  new SysLog();
			sl.setCenter_num(user.getCenter_num());
			sl.setUserid(user.getUserid()+"");
			sl.setOper_type("2");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
			sl.setXtgnid("");//可不填写
			sl.setXtgnid2("1205");//子功能id 必须填写
			sl.setExplain("");//操作说明
			syslogService.saveSysLog(sl);
			this.outJsonStrResult(this.message);
		return NONE;
	}
	/**
	 * 
	     * @Title: getlisStatusres   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String getlisStatusres() throws WebException {
		UserDTO user = (UserDTO) session.get("username");
		if (StringUtil.isEmpty(this.model.getExam_num())) {
			this.message = "errro-体检编号无效";
		} else {
			WebserviceConfigurationDTO wd = webserviceConfigurationService.getWebServiceConfig("LIS_STATUS", user.getCenter_num());
			if ((wd != null) && (!"0".equals(wd.getConfig_method()))) {
				LISResStatusMessage lis = new LISResStatusMessage();
				lis.lisSend(wd.getConfig_url(), wd.getConfig_method(), this.model.getExam_num(),0);
			}
			this.message="ok-请求处理完成";
		}
		this.outJsonStrResult(this.message);
		return NONE;
	}
	/**
	 * 
	     * @Title: flowexampList   
	     * @Description: 查询上传信息1208   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String flowexampList()throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		PageReturnDTO contractlist = new PageReturnDTO();
		contractlist = this.examFlowService.getFlowExamInfoUserList(this.model, user.getUserid(), user.getCenter_num(),
				this.rows, this.getPage(),user);
		SysLog sl = new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid() + "");
		sl.setOper_type("0");// 0 查询，1插入 2修改 3 删除 4导入 9 其他
		sl.setXtgnid("");// 可不填写
		sl.setXtgnid2("1208");// 子功能id 必须填写
		sl.setExplain("");// 操作说明
		syslogService.saveSysLog(sl);
		this.outJsonResult(contractlist);
		return NONE;
	}
	/**
	 * 
	     * @Title: flowexamh1insert   
	     * @Description: 上传信息1209   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String flowexamh1insert()throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		this.message = this.examFlowService.flowexamh1insert(this.model.getIds(), user);
		SysLog sl = new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid() + "");
		sl.setOper_type("1");// 0 查询，1插入 2修改 3 删除 4导入 9 其他
		sl.setXtgnid("");// 可不填写
		sl.setXtgnid2("1209");// 子功能id 必须填写
		sl.setExplain("");// 操作说明
		syslogService.saveSysLog(sl);
		this.outJsonStrResult(this.message);
		return NONE;
	}
	/**
	 * 
	     * @Title: flowexamh1delete   
	     * @Description: 取消上次信息1210  
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String flowexamh1delete()throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		this.message = this.examFlowService.flowexamh1delete(this.model.getIds(), user);
		SysLog sl = new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid() + "");
		sl.setOper_type("3");// 0 查询，1插入 2修改 3 删除 4导入 9 其他
		sl.setXtgnid("");// 可不填写
		sl.setXtgnid2("1210");// 子功能id 必须填写
		sl.setExplain("");// 操作说明
		syslogService.saveSysLog(sl);
		this.outJsonStrResult(this.message);
		return NONE;
	}
	/**
	 * 流程管理人员查询下拉框1445
	 */
	public String getflowUser()throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		List<UserDTO> li = this.examFlowService.getflowUser(model,user.getCenter_num());
		SysLog sl = new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid() + "");
		sl.setOper_type("0");// 0 查询，1插入 2修改 3 删除 4导入 9 其他
		sl.setXtgnid("");// 可不填写
		sl.setXtgnid2("1445");// 子功能id 必须填写
		sl.setExplain("");// 操作说明
		syslogService.saveSysLog(sl);
		this.outJsonResult(li);
		return NONE;
	}
	/**
	 * 
	     * @Title: flow_s_show   
	     * @Description:流程-整单室核收导检单主页面 1211  
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String flow_s_show()  throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		this.model.setTime1(DateTimeUtil.getDate2());
		this.model.setTime2(DateTimeUtil.getDate2());
		this.model.setReport_print_type(
				this.customerInfoService.getCenterconfigByKey("REPORT_PRINT_TYPE", user.getCenter_num()).getConfig_value().trim());
		this.model.setIs_report_room(
				this.customerInfoService.getCenterconfigByKey("IS_REPORT_ROOM", user.getCenter_num()).getConfig_value().trim());
		this.model.setWjItem_dep(this.customerInfoService.getCenterconfigByKey("IS_NEEDALERT_WJITEM", user.getCenter_num()).getConfig_value().trim());
		String IS_FLOW_DJDRECOVERY_SHOW="Y";
		try{
			IS_FLOW_DJDRECOVERY_SHOW=this.customerInfoService.getCenterconfigByKey("IS_FLOW_DJDRECOVERY_SHOW", user.getCenter_num()).getConfig_value().trim();
		}catch(Exception ex){}
		this.model.setReportAddress(IS_FLOW_DJDRECOVERY_SHOW);
		if (!StringUtil.isEmpty(this.model.getIs_report_room()) && "N".equals(this.model.getIs_report_room())) {
			return "success1";
		}else{
			return SUCCESS;
		}
	}
	
	/**
	 * 
	     * @Title: flowexampLists 1217
	     * @Description: 整单室   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String flowexampLists()throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		PageReturnDTO contractlist = new PageReturnDTO();
		contractlist = this.examFlowService.getFlowExamInfoUserLists(this.model, user.getUserid(), user.getCenter_num(),
				this.rows, this.getPage(),user);
		SysLog sl = new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid() + "");
		sl.setOper_type("0");// 0 查询，1插入 2修改 3 删除 4导入 9 其他
		sl.setXtgnid("");// 可不填写
		sl.setXtgnid2("1217");// 子功能id 必须填写
		sl.setExplain("");// 操作说明
		syslogService.saveSysLog(sl);
		this.outJsonResult(contractlist);
		return NONE;
	}
	
	/**
	 * 
	     * @Title: flows0ExamInfo   
	     * @Description:整单室 接受导检单1212  
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String flows0ExamInfo() throws WebException {
		UserDTO user = (UserDTO) session.get("username");
		this.message=this.examFlowService.insertExamflows0(this.model.getExam_num(), user);
			SysLog sl = new SysLog();
			sl.setCenter_num(user.getCenter_num());
			sl.setUserid(user.getUserid() + "");
			sl.setOper_type("9");// 0 查询，1插入 2修改 3 删除 4导入 5，登录 9 其他
			sl.setXtgnid("");// 可不填写
			sl.setXtgnid2("1212");// 子功能id 必须填写
			sl.setExplain("");// 操作说明
			syslogService.saveSysLog(sl);
			if(message.indexOf("ok")>=0){
				WebserviceConfigurationDTO wd = webserviceConfigurationService.getWebServiceConfig("LIS_STATUS", user.getCenter_num());
				if ((wd != null) && (!"0".equals(wd.getConfig_method()))) {
					LISResStatusMessage lis = new LISResStatusMessage();
					lis.lisSend(wd.getConfig_url(), wd.getConfig_method(), this.model.getExam_num(),this.model.getId());
				}
			}
		this.outJsonStrResult(this.message);
		return NONE;
	}
	/**
	 * 整单室查询未检项目
	 */
	public  String getWJitemList() {
    	UserDTO user = (UserDTO) session.get("username");
    	if (this.model.getExam_num() == null || "".equals(this.model.getExam_num()) ) {
			this.message =  "error-体检号无效";
		}else{
			this.message = this.examFlowService.getWJitemList(this.model.getWjItem_dep() ,this.model.getExam_num(), user.getCenter_num());
    		SysLog sl =  new SysLog();
    		sl.setCenter_num(user.getCenter_num());
    		sl.setUserid(user.getUserid()+"");
    		sl.setOper_type("2");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
    		sl.setXtgnid("");//可不填写
    		sl.setXtgnid2("1218");//子功能id 必须填写
    		sl.setExplain("取消整单上传");//操作说明
    		syslogService.saveSysLog(sl);
		}
    	this.outJsonStrResult(this.message);
    	return NONE;
    }
	/**
	 * 
	     * @Title: flows0ExamInfo   
	     * @Description:整单室 取消接受导检单1213   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String flows0ExamInfoun() throws WebException {
		UserDTO user = (UserDTO) session.get("username");
		if(this.model.getIds() != null && !"".equals(this.model.getIds())){
			String[] ids = this.model.getIds().split(",");
			for (int i = 0; i < ids.length; i++) {
				this.examFlowService.insertExamflows0un(ids[i], user);
			}
			this.message = "ok-取消核收成功!";
		}else{
			this.message=this.examFlowService.insertExamflows0un(this.model.getExam_num(), user);
		}
			SysLog sl = new SysLog();
			sl.setCenter_num(user.getCenter_num());
			sl.setUserid(user.getUserid() + "");
			sl.setOper_type("3");// 0 查询，1插入 2修改 3 删除 4导入 5，登录 9 其他
			sl.setXtgnid("");// 可不填写
			sl.setXtgnid2("1213");// 子功能id 必须填写
			sl.setExplain("");// 操作说明
			syslogService.saveSysLog(sl);
		this.outJsonStrResult(this.message);
		return NONE;
	}
    /**
     * 
         * @Title: uploadFlow   
         * @Description: 整单室上传 省略报告室流程 
         * @param: @return
         * @param: @throws WebException
         * @param: @throws SQLException      
         * @return: String      
         * @throws
     */
	public String  uploadFlow()throws WebException,SQLException{
    	UserDTO user = (UserDTO) session.get("username");
    	if (this.model.getIds() == null || "".equals(this.model.getIds()) ) {
			this.message =  "error-体检号无效";
		}else{
			this.message = this.examFlowService.uploadFlow( user ,this.model.getIds());
    		SysLog sl =  new SysLog();
    		sl.setCenter_num(user.getCenter_num());
    		sl.setUserid(user.getUserid()+"");
    		sl.setOper_type("2");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
    		sl.setXtgnid("");//可不填写
    		sl.setXtgnid2("1235");//子功能id 必须填写
    		sl.setExplain("整单上传");//操作说明
    		syslogService.saveSysLog(sl);
		}
    	this.outJsonStrResult(this.message);
    	return NONE;
    }
	/**
	 * 一键上传全部记录
	     * @Title: uploadFlowAll   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String uploadFlowAll() throws WebException,SQLException{
		UserDTO user = (UserDTO) session.get("username");
		PageReturnDTO contractlist = this.examFlowService.getFlowExamInfoUserLists(this.model, user.getUserid(), user.getCenter_num(),this.rows, this.getPage(),user);
		List<ExamInfoUserDTO> list = contractlist.getRows();
		String ids = "";
		for (int i = 0; i < list.size(); i++) {
			if( i == list.size() - 1){
				ids += list.get(i).getExam_num();
			}else{
				ids += list.get(i).getExam_num() + ",";
			}
		}
		this.message = this.examFlowService.uploadFlow( user ,ids);
		this.outJsonStrResult(this.message);
		return NONE;
	}
    //取消上传
    public  String examUploadFlowDel() {
    	UserDTO user = (UserDTO) session.get("username");
    	if (this.model.getIds() == null || "".equals(this.model.getIds()) ) {
			this.message =  "error-体检号无效";
		}else{
			this.message = this.examFlowService.examUploadFlowDel( user ,this.model.getIds());
    		SysLog sl =  new SysLog();
    		sl.setCenter_num(user.getCenter_num());
    		sl.setUserid(user.getUserid()+"");
    		sl.setOper_type("2");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
    		sl.setXtgnid("");//可不填写
    		sl.setXtgnid2("1218");//子功能id 必须填写
    		sl.setExplain("取消整单上传");//操作说明
    		syslogService.saveSysLog(sl);
		}
    	this.outJsonStrResult(this.message);
    	return NONE;
    }
    
    //整单室获取lis pacs 结果
    public String getlispacsres() throws WebException {
    	UserDTO user = (UserDTO) session.get("username");
		if (StringUtil.isEmpty(this.model.getExam_num())) {
			this.message = "errro-体检编号无效";
		} else {
			WebserviceConfigurationDTO wd = webserviceConfigurationService.getWebServiceConfig("LIS_READ", user.getCenter_num());
			if ((wd != null) && (!"0".equals(wd.getConfig_method()))) {
				LISResMessage lis = new LISResMessage();
				lis.lisSend(wd.getConfig_url(), wd.getConfig_method(), this.model.getExam_num(), user.getCenter_num());
			}

			wd = webserviceConfigurationService.getWebServiceConfig("PACS_READ", user.getCenter_num());
			if ((wd != null) && (!"0".equals(wd.getConfig_method()))) {
				PACSResMessage lis = new PACSResMessage();
				lis.pacsSend(wd.getConfig_url(), wd.getConfig_method(), this.model.getExam_num());
			}
			this.message="ok-请求处理完成";
		}
		this.outJsonStrResult(this.message);
		return NONE;
	}
  //===========================================整单室报告接收table  可上传===================================================================
    public String jieshoukeshangchuan()throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		if(!StringUtils.isEmpty(model.getExam_num())){
			ExamInfo ei = customerInfoService.getExamInfoForExamNum(this.model.getExam_num());
			String jieshou = this.examFlowService.jieshoukeshangchuan(this.model.getExam_num(), user.getUserid(), user.getCenter_num());
			SysLog sl = new SysLog();
			sl.setCenter_num(user.getCenter_num());
			sl.setUserid(user.getUserid() + "");
			sl.setOper_type("2");// 0 查询，1插入 2修改 3 删除 4导入 9 其他
			sl.setXtgnid("");// 可不填写
			sl.setXtgnid2("1550");// 子功能id 必须填写
			sl.setExplain("");// 操作说明
			syslogService.saveSysLog(sl);
			this.message=jieshou+"操作成功";
			/*}*/
			this.outJsonStrResult(this.message);
		}else{
			this.message="error";
		    this.outJsonStrResult(this.message);
		}
		
		return NONE;
		
	}
	//===========================================整单室报告接收table  取消上传===================================================================
	public String quxiaoshangchuan()throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		if(!StringUtils.isEmpty(model.getExam_num())){
			ExamInfo ei = customerInfoService.getExamInfoForExamNum(model.getExam_num());
			String exam_num = ei.getExam_num();
			String quxiao = this.examFlowService.quxiaoshangchuan(exam_num,user.getUserid(), user.getCenter_num());
			SysLog sl = new SysLog();
			sl.setCenter_num(user.getCenter_num());
			sl.setUserid(user.getUserid() + "");
			sl.setOper_type("0");// 0 查询，1插入 2修改 3 删除 4导入 9 其他
			sl.setXtgnid("");// 可不填写
			sl.setXtgnid2("1551");// 子功能id 必须填写
			sl.setExplain("");// 操作说明
			syslogService.saveSysLog(sl);
			this.message=quxiao+"操作成功";
			
			this.outJsonStrResult(this.message);
		}else{
			this.message="error";
		    this.outJsonStrResult(this.message);
		}
		return NONE;
	}
	/**
	 * 
	     * @Title: flowexampuploadingLists   
	     * @Description: 整单室 报告上传table  可上传
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String flowexampuploadingLists()throws WebException{
		
		UserDTO user = (UserDTO) session.get("username");
		PageReturnDTO contractlist = new PageReturnDTO();
		
		contractlist = this.examFlowService.flowexampuploadingLists(this.model, user.getUserid(), user.getCenter_num(),
				this.rows, this.getPage());
		
		SysLog sl = new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid() + "");
		sl.setOper_type("0");// 0 查询，1插入 2修改 3 删除 4导入 9 其他
		sl.setXtgnid("");// 可不填写
		sl.setXtgnid2("1552");// 子功能id 必须填写
		sl.setExplain("");// 操作说明
		syslogService.saveSysLog(sl);
		this.outJsonResult(contractlist);
		return NONE;
	}
	/**
	 * 
	     * @Title: flowexamh1insert   
	     * @Description: 整单上传信息1214   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String flowexams1insert()throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		this.message = this.examFlowService.flowexams1insert(this.model.getIds(), user);
		SysLog sl = new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid() + "");
		sl.setOper_type("1");// 0 查询，1插入 2修改 3 删除 4导入 9 其他
		sl.setXtgnid("");// 可不填写
		sl.setXtgnid2("1214");// 子功能id 必须填写
		sl.setExplain("");// 操作说明
		syslogService.saveSysLog(sl);
		this.outJsonStrResult(this.message);
		return NONE;
	}
	
	/**
	 * 
	     * @Title: flowexamh1delete   
	     * @Description: 整单取消上次信息1215 
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String flowexams1delete()throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		this.message = this.examFlowService.flowexams1delete(this.model.getIds(), user);
		SysLog sl = new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid() + "");
		sl.setOper_type("3");// 0 查询，1插入 2修改 3 删除 4导入 9 其他
		sl.setXtgnid("");// 可不填写
		sl.setXtgnid2("1215");// 子功能id 必须填写
		sl.setExplain("");// 操作说明
		syslogService.saveSysLog(sl);
		this.outJsonStrResult(this.message);
		return NONE;
	}
	/**
	 * 
	     * @Title: batch_transfer   
	     * @Description:批量转移页面 1322
	     * @param: @return
	     * @param: @throws WebException
	     * @return: String
	     * @throws
	 */
	public String batch_transfer() throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		this.model.setReport_print_type(
				this.customerInfoService.getCenterconfigByKey("REPORT_PRINT_TYPE", user.getCenter_num()).getConfig_value().trim());
		return SUCCESS;
	}
	
	/**
	 * 
	     * @Title: flowexamtransfer   
	     * @Description: 指引单转移1323 
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String flowexamtransfer() throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		this.message = this.examFlowService.flowexamtransfer(this.model.getIds(), this.model.getFlow_code(), user.getUserid(), this.model.getDoctor_id(), user.getCenter_num());
		SysLog sl = new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid() + "");
		sl.setOper_type("1");// 0 查询，1插入 2修改 3 删除 4导入 9 其他
		sl.setXtgnid("");// 可不填写
		sl.setXtgnid2("1323");// 子功能id 必须填写
		sl.setExplain("");// 操作说明
		syslogService.saveSysLog(sl);
		this.outJsonStrResult(this.message);
		return NONE;
	}
	/**
	 * 
	     * @Title: flow_z_show 1230 
	     * @Description:流程-总检主页面   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String flow_z_show()  throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		this.model.setTime1(DateTimeUtil.getDate2());
		this.model.setTime2(DateTimeUtil.getDate2());
		this.model.setReport_print_type(this.customerInfoService.getCenterconfigByKey("REPORT_PRINT_TYPE", user.getCenter_num()).getConfig_value().trim());
		String IS_REPORT_DEP_FLOW="1";//报告室流程:1表示180模式，2表示东北国际模式
		try{
			IS_REPORT_DEP_FLOW=this.customerInfoService.getCenterconfigByKey("IS_REPORT_DEP_FLOW", user.getCenter_num()).getConfig_value().trim();
		}catch(Exception ex){}
		if("1".equals(IS_REPORT_DEP_FLOW)){
			return SUCCESS;
		}else if("2".equals(IS_REPORT_DEP_FLOW)){
			return "success_dbgj";
		}else{
			return SUCCESS;
		}
		
	}
	/**
	 *总检室 获取待接受导检单列表1336
	     * @Title: getPre_receive_zongjian   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String getPre_receive_zongjian()throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		PageReturnDTO contractlist = new PageReturnDTO();
		contractlist = this.examFlowService.getPre_receive_zongjian(this.model, user.getUserid(), user.getCenter_num(),
				this.rows, this.getPage());
		SysLog sl = new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid() + "");
		sl.setOper_type("0");// 0 查询，1插入 2修改 3 删除 4导入 9 其他
		sl.setXtgnid("");// 可不填写
		sl.setXtgnid2("1336");// 子功能id 必须填写
		sl.setExplain("");// 操作说明
		syslogService.saveSysLog(sl);
		this.outJsonResult(contractlist);
		return NONE;
	}
	/**
	 * 
	     * @Title: getPre_receive_details_zongjian   
	     * @Description: 总检室 获取待接受导检单详细信息1337
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String getPre_receive_details_zongjian()throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		PageReturnDTO contractlist = new PageReturnDTO();
		contractlist = this.examFlowService.getPre_receive_details_zongjian(this.model, user.getUserid(), user.getCenter_num(),
				this.rows, this.getPage());
		SysLog sl = new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid() + "");
		sl.setOper_type("0");// 0 查询，1插入 2修改 3 删除 4导入 9 其他
		sl.setXtgnid("");// 可不填写
		sl.setXtgnid2("1337");// 子功能id 必须填写
		sl.setExplain("");// 操作说明
		syslogService.saveSysLog(sl);
		this.outJsonResult(contractlist);
		return NONE;
	}
	
	/**
	 * 
	     * @Title: flowz0ExamInfo_batch   
	     * @Description: 总检室 导检单批量核收1338
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String flowz0ExamInfo_batch() throws WebException {
		UserDTO user = (UserDTO) session.get("username");
		String ids = this.model.getIds();
		int all=0,ok=0;
		for(String exam_num : ids.trim().split(",")) {
			all++;
			this.message = this.examFlowService.insertExamflowz(exam_num, user);
			if(this.message.startsWith("ok")) {
				ok++;
			}
		}
		this.message="ok-共选中" +all+ "份，本次成功核收" + ok + "份";
		
		SysLog sl = new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid() + "");
		sl.setOper_type("9");// 0 查询，1插入 2修改 3 删除 4导入 5，登录 9 其他
		sl.setXtgnid("");// 可不填写
		sl.setXtgnid2("1338");// 子功能id 必须填写
		sl.setExplain("");// 操作说明
		syslogService.saveSysLog(sl);
		this.outJsonStrResult(this.message);
		return NONE;
	}
	/**
	 * 
	     * @Title: flowexameListz   
	     * @Description:流程-总检主页面 1231   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String flowexameListz()throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		PageReturnDTO contractlist = new PageReturnDTO();
		contractlist = this.examFlowService.getFlowExamInfoUserListz(this.model, user.getUserid(), user.getCenter_num(),
				this.rows, this.getPage(),user);
		SysLog sl = new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid() + "");
		sl.setOper_type("0");// 0 查询，1插入 2修改 3 删除 4导入 9 其他
		sl.setXtgnid("");// 可不填写
		sl.setXtgnid2("1231");// 子功能id 必须填写
		sl.setExplain("");// 操作说明
		syslogService.saveSysLog(sl);
		this.outJsonResult(contractlist);
		return NONE;
	}
	
	/**
	 * 
	     * @Title: flowexameListz   
	     * @Description:流程-总检主页面 1940   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String flowexameListz_dbgj()throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		PageReturnDTO contractlist = new PageReturnDTO();
		contractlist = this.examFlowService.getFlowExamInfoUserListz_dbgj(this.model, user.getUserid(), user.getCenter_num(),
				this.rows, this.getPage(),user);
		this.outJsonResult(contractlist);
		return NONE;
	}
	/**
	 * 
	     * @Title: flowp0ExamInfoun   1233
	     * @Description: 终检室-取消接收导检单 
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String flowzExamInfoun() throws WebException {
		UserDTO user = (UserDTO) session.get("username");		
		this.message=this.examFlowService.insertExamflowzun(this.model.getExam_num(), user);
			SysLog sl = new SysLog();
			sl.setCenter_num(user.getCenter_num());
			sl.setUserid(user.getUserid() + "");
			sl.setOper_type("3");// 0 查询，1插入 2修改 3 删除 4导入 5，登录 9 其他
			sl.setXtgnid("");// 可不填写
			sl.setXtgnid2("1233");// 子功能id 必须填写
			sl.setExplain("");// 操作说明
			syslogService.saveSysLog(sl);
		this.outJsonStrResult(this.message);
		return NONE;
	}
	/**
	 * 
	     * @Title: flowzExamInfo   
	     * @Description: 终检室-接收导检单1232
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String flowzExamInfo() throws WebException {
		UserDTO user = (UserDTO) session.get("username");		
		this.message=this.examFlowService.insertExamflowz(this.model.getExam_num(), user);
			SysLog sl = new SysLog();
			sl.setCenter_num(user.getCenter_num());
			sl.setUserid(user.getUserid() + "");
			sl.setOper_type("9");// 0 查询，1插入 2修改 3 删除 4导入 5，登录 9 其他
			sl.setXtgnid("");// 可不填写
			sl.setXtgnid2("1232");// 子功能id 必须填写
			sl.setExplain("");// 操作说明
			syslogService.saveSysLog(sl);
		this.outJsonStrResult(this.message);
		return NONE;
	}
	
	/**
	 * 
	     * @Title: batch_sendchk   
	     * @Description:批量发送审核页面 1326
	     * @param: @return
	     * @param: @throws WebException
	     * @return: String
	     * @throws
	 */
	public String batch_sendchk() throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		this.model.setReport_print_type(
				this.customerInfoService.getCenterconfigByKey("REPORT_PRINT_TYPE", user.getCenter_num()).getConfig_value().trim());
		return SUCCESS;
	}
	
	/**
	 * 
	     * @Title: flowexamsendchk   
	     * @Description: 报告批量发送审核1327 
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String flowexamsendchk() throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		this.message = this.examFlowService.flowexamsendchk(this.model.getIds(), user, this.model.getDoctor_id());
		SysLog sl = new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid() + "");
		sl.setOper_type("1");// 0 查询，1插入 2修改 3 删除 4导入 9 其他
		sl.setXtgnid("");// 可不填写
		sl.setXtgnid2("1327");// 子功能id 必须填写
		sl.setExplain("");// 操作说明
		syslogService.saveSysLog(sl);
		this.outJsonStrResult(this.message);
		return NONE;
	}
	
	/**
	 * 
	     * @Title: flow_p_show 1218
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String flow_p_show()  throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		this.model.setTime1(DateTimeUtil.getDate2());
		this.model.setTime2(DateTimeUtil.getDate2());
		this.model.setReport_print_type(
				this.customerInfoService.getCenterconfigByKey("REPORT_PRINT_TYPE", user.getCenter_num()).getConfig_value().trim());
		return SUCCESS;
	}
	
	/**
	 * 
	     * @Title: flowp0ExamInfo   
	     * @Description: 打印室-接收导检单 
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String getHealthExamInfo() throws WebException {
		UserDTO user = (UserDTO) session.get("username");
		ExamInfoDTO ei=this.customerInfoService.getExamInfoForexamNum(model.getExam_num());
		this.message="ok-ok";
		if((ei!=null)&&(ei.getId()>=0)){
			String HEALTH_GET_IS=this.customerInfoService.getCenterconfigByKey("HEALTH_GET_IS", user.getCenter_num()).getConfig_value().trim();
			String HEALTH_GET_DEP=this.customerInfoService.getCenterconfigByKey("HEALTH_GET_DEP", user.getCenter_num()).getConfig_value().trim();
			if("Y".equals(HEALTH_GET_IS.toUpperCase())){
				if(this.examFlowService.checkHealthDepId(ei.getId(),HEALTH_GET_DEP, user.getCenter_num())){
					HealthResMessage res = new HealthResMessage();
					HealthFileResBody body = res.getHealThMessage(ei.getExam_num(), user.getCenter_num(), 1);
					if(!"AA".equals(body.getRescode())){
					  this.message="error-"+body.getRestext();
					}
				}
			}			
		}
		this.outJsonStrResult(this.message);
		return NONE;
	}
	/**
	 * 
	     * @Title: flowp0ExamInfo   
	     * @Description: 打印室-接收导检单1219  
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String flowp0ExamInfo() throws WebException {
		UserDTO user = (UserDTO) session.get("username");
		if (this.model.getUpload_flow() != null && "1".equals(this.model.getUpload_flow())) {
			this.message=this.examFlowService.insertExamflowp(this.model.getExam_num(), user,this.model.getUpload_flow());
		}else{
			this.message=this.examFlowService.insertExamflowp0(this.model.getExam_num(), user);
		}
			SysLog sl = new SysLog();
			sl.setCenter_num(user.getCenter_num());
			sl.setUserid(user.getUserid() + "");
			sl.setOper_type("9");// 0 查询，1插入 2修改 3 删除 4导入 5，登录 9 其他
			sl.setXtgnid("");// 可不填写
			sl.setXtgnid2("1219");// 子功能id 必须填写
			sl.setExplain("");// 操作说明
			syslogService.saveSysLog(sl);
		this.outJsonStrResult(this.message);
		return NONE;
	}
	
	
	/**
	 * 
	     * @Title: check_print   
	     * @Description: 打印室-检查导检单是否已打印1360
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String check_print() throws WebException {
		UserDTO user = (UserDTO) session.get("username");		
		this.message=this.examFlowService.check_print(this.model.getExam_num());
			SysLog sl = new SysLog();
			sl.setCenter_num(user.getCenter_num());
			sl.setUserid(user.getUserid() + "");
			sl.setOper_type("0");// 0 查询，1插入 2修改 3 删除 4导入 5，登录 9 其他
			sl.setXtgnid("");// 可不填写
			sl.setXtgnid2("1360");// 子功能id 必须填写
			sl.setExplain("");// 操作说明
			syslogService.saveSysLog(sl);
		this.outJsonStrResult(this.message);
		return NONE;
	}
	
	/**
	 * 
	     * @Title: flowp0ExamInfoun   
	     * @Description: 打印室核收导检单主页面 1220  
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String flowp0ExamInfoun() throws WebException {
		UserDTO user = (UserDTO) session.get("username");		
		this.message=this.examFlowService.insertExamflowp0un(this.model.getExam_num(), user);
			SysLog sl = new SysLog();
			sl.setCenter_num(user.getCenter_num());
			sl.setUserid(user.getUserid() + "");
			sl.setOper_type("3");// 0 查询，1插入 2修改 3 删除 4导入 5，登录 9 其他
			sl.setXtgnid("");// 可不填写
			sl.setXtgnid2("1220");// 子功能id 必须填写
			sl.setExplain("");// 操作说明
			syslogService.saveSysLog(sl);
		this.outJsonStrResult(this.message);
		return NONE;
	}
	
	/**
	 * 
	     * @Title: flowexampListp   
	     * @Description: 打印室 取消接受导检单列表1221   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String flowexampListp()throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		PageReturnDTO contractlist = new PageReturnDTO();
		contractlist = this.examFlowService.getFlowExamInfoUserListp(this.model, user.getUserid(), user.getCenter_num(),this.rows, this.getPage(),this.sort,this.order,user);
		SysLog sl = new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid() + "");
		sl.setOper_type("0");// 0 查询，1插入 2修改 3 删除 4导入 9 其他
		sl.setXtgnid("");// 可不填写
		sl.setXtgnid2("1221");// 子功能id 必须填写
		sl.setExplain("");// 操作说明
		syslogService.saveSysLog(sl);
		this.outJsonResult(contractlist);
		return NONE;
	}
	/**
	 * 
	     * @Title: flowexamh1insert   
	     * @Description: 打印室-整单上传信息1222   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String flowexamp1insert()throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		this.message = this.examFlowService.flowexamp1insert(this.model.getIds(), user);
		SysLog sl = new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid() + "");
		sl.setOper_type("1");// 0 查询，1插入 2修改 3 删除 4导入 9 其他
		sl.setXtgnid("");// 可不填写
		sl.setXtgnid2("1222");// 子功能id 必须填写
		sl.setExplain("");// 操作说明
		syslogService.saveSysLog(sl);
		this.outJsonStrResult(this.message);
		return NONE;
	}
	
	/**
	 * 
	     * @Title: flowexamh1delete   
	     * @Description: 打印室-整单取消上次信息1223 
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String flowexamp1delete()throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		this.message = this.examFlowService.flowexamp1delete(this.model.getIds(), user);
		SysLog sl = new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid() + "");
		sl.setOper_type("3");// 0 查询，1插入 2修改 3 删除 4导入 9 其他
		sl.setXtgnid("");// 可不填写
		sl.setXtgnid2("1223");// 子功能id 必须填写
		sl.setExplain("");// 操作说明
		syslogService.saveSysLog(sl);
		this.outJsonStrResult(this.message);
		return NONE;
	}
	/**
	 * 
	     * @Title: flow_e_show   
	     * @Description: 流程-发送室核收导检单主页面 1224 
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String flow_e_show()  throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		this.model.setTime1(DateTimeUtil.getDate2());
		this.model.setTime2(DateTimeUtil.getDate2());
		this.model.setReport_print_type(this.customerInfoService.getCenterconfigByKey("REPORT_PRINT_TYPE", user.getCenter_num()).getConfig_value().trim());
		String IS_FLOW_DJDRECOVERY_SHOW="Y";
		try{
			IS_FLOW_DJDRECOVERY_SHOW=this.customerInfoService.getCenterconfigByKey("IS_FLOW_DJDRECOVERY_SHOW", user.getCenter_num()).getConfig_value().trim();
		}catch(Exception ex){}
		this.model.setReportAddress(IS_FLOW_DJDRECOVERY_SHOW);
		// 获取身份证厂家代码
		String sfz_div_code = this.customerInfoService.getCenterconfigByKey("SFZ_CARD", user.getCenter_num()).getConfig_value().trim();
		this.sfz_div_ocx = this.customerInfoService.getDiversforByCode(sfz_div_code).getCom_ocx_name();
		this.sfz_div_code = sfz_div_code;
		return SUCCESS;
	}
	/**
	 *发送室 获取待接受导检单列表1319
	     * @Title: getPre_receive   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String getPre_receive()throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		PageReturnDTO contractlist = new PageReturnDTO();
		contractlist = this.examFlowService.getPre_receive(this.model, user.getUserid(), user.getCenter_num(),
				this.rows, this.getPage());
		SysLog sl = new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid() + "");
		sl.setOper_type("0");// 0 查询，1插入 2修改 3 删除 4导入 9 其他
		sl.setXtgnid("");// 可不填写
		sl.setXtgnid2("1319");// 子功能id 必须填写
		sl.setExplain("");// 操作说明
		syslogService.saveSysLog(sl);
		this.outJsonResult(contractlist);
		return NONE;
	}
	
	/**
	 * 
	     * @Title: getPre_receive_details   
	     * @Description: 发送室 获取待接受导检单详细信息1320  
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String getPre_receive_details()throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		PageReturnDTO contractlist = new PageReturnDTO();
		contractlist = this.examFlowService.getPre_receive_details(this.model, user.getUserid(), user.getCenter_num(),
				this.rows, this.getPage());
		SysLog sl = new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid() + "");
		sl.setOper_type("0");// 0 查询，1插入 2修改 3 删除 4导入 9 其他
		sl.setXtgnid("");// 可不填写
		sl.setXtgnid2("1320");// 子功能id 必须填写
		sl.setExplain("");// 操作说明
		syslogService.saveSysLog(sl);
		this.outJsonResult(contractlist);
		return NONE;
	}
	
	/**
	 * 
	     * @Title: flowe0ExamInfo_batch   
	     * @Description: 发送室 导检单批量核收1321  
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String flowe0ExamInfo_batch() throws WebException {
		UserDTO user = (UserDTO) session.get("username");
		JobDTO mes = new JobDTO();
		String ids = this.model.getIds();
		String IS_REPORT_DEP_FLOW="1";//报告室流程:1表示180模式，1表示东北国际模式
		try{
			IS_REPORT_DEP_FLOW=this.customerInfoService.getCenterconfigByKey("IS_REPORT_DEP_FLOW", user.getCenter_num()).getConfig_value().trim();
		}catch(Exception ex){}
		
		int all=0,ok=0;
		for (String exam_num : ids.trim().split(",")) {
			all++;

			if ("1".equals(IS_REPORT_DEP_FLOW)) {
				mes = this.examFlowService.insertExamflowe0(exam_num, user);
			} else if ("2".equals(IS_REPORT_DEP_FLOW)) {

				mes = this.examFlowService.insertExamflowe0_dbgj(exam_num, user);
			}else{
				mes = this.examFlowService.insertExamflowe0(exam_num, user);
			}
			if ("ok".equals(mes.getId())) {
				ok++;
				if ("Y".equals(
						this.customerInfoService.getCenterconfigByKey("IS_PRINT_SMS", user.getCenter_num()).getConfig_value().trim())) {
					// 发短信
				}
			}
		}
		this.message="ok-共选中" +all+ "份，本次成功核收" + ok + "份";
		SysLog sl = new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid() + "");
		sl.setOper_type("9");// 0 查询，1插入 2修改 3 删除 4导入 5，登录 9 其他
		sl.setXtgnid("");// 可不填写
		sl.setXtgnid2("1321");// 子功能id 必须填写
		sl.setExplain("");// 操作说明
		syslogService.saveSysLog(sl);
		this.outJsonStrResult(this.message);
		return NONE;
	}
	
	/**
	 * 
	     * @Title: flowe0ExamInfo   
	     * @Description: 发送室-接收导检单1225  
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String flowe0ExamInfo() throws WebException {
		UserDTO user = (UserDTO) session.get("username");
		JobDTO mes = new JobDTO();
		String IS_REPORT_DEP_FLOW="1";//报告室流程:1表示180模式，1表示东北国际模式
		try{
			IS_REPORT_DEP_FLOW=this.customerInfoService.getCenterconfigByKey("IS_REPORT_DEP_FLOW", user.getCenter_num()).getConfig_value().trim();
		}catch(Exception ex){}
		if ("1".equals(IS_REPORT_DEP_FLOW)) {
			mes=this.examFlowService.insertExamflowe0(this.model.getExam_num(), user);
		}else if ("2".equals(IS_REPORT_DEP_FLOW)) {
			mes=this.examFlowService.insertExamflowe0_dbgj(this.model.getExam_num(), user);
		}else{
			mes=this.examFlowService.insertExamflowe0(this.model.getExam_num(), user);
		}
		this.message=mes.getName();
		if (("ok".equals(mes.getId()))&&("Y".equals(this.customerInfoService.getCenterconfigByKey("IS_PRINT_SMS", user.getCenter_num()).getConfig_value()
				.trim()))) {
			//发短信
		}
		
			SysLog sl = new SysLog();
			sl.setCenter_num(user.getCenter_num());
			sl.setUserid(user.getUserid() + "");
			sl.setOper_type("9");// 0 查询，1插入 2修改 3 删除 4导入 5，登录 9 其他
			sl.setXtgnid("");// 可不填写
			sl.setXtgnid2("1225");// 子功能id 必须填写
			sl.setExplain("");// 操作说明
			syslogService.saveSysLog(sl);
		this.outJsonStrResult(this.message);
		return NONE;
	}
	
	/**
	 * 
	     * @Title: flowp0ExamInfoun   
	     * @Description: 发送室-取消接收导检单1226  
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String flowe0ExamInfoun() throws WebException {
		UserDTO user = (UserDTO) session.get("username");		
		this.message=this.examFlowService.insertExamflowe0un(this.model.getExam_num(), user);
			SysLog sl = new SysLog();
			sl.setCenter_num(user.getCenter_num());
			sl.setUserid(user.getUserid() + "");
			sl.setOper_type("3");// 0 查询，1插入 2修改 3 删除 4导入 5，登录 9 其他
			sl.setXtgnid("");// 可不填写
			sl.setXtgnid2("1226");// 子功能id 必须填写
			sl.setExplain("");// 操作说明
			syslogService.saveSysLog(sl);
		this.outJsonStrResult(this.message);
		return NONE;
	}
	/**
	 * 
	     * @Title: flowexameListe 1227
	     * @Description: 发送室 取消接受导检单列表  
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String flowexameListe()throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		PageReturnDTO contractlist = new PageReturnDTO();

		contractlist = this.examFlowService.getFlowExamInfoUserListe(this.model, user.getUserid(), user.getCenter_num(),
				this.rows, this.getPage(),user);
		SysLog sl = new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid() + "");
		sl.setOper_type("0");// 0 查询，1插入 2修改 3 删除 4导入 9 其他
		sl.setXtgnid("");// 可不填写
		sl.setXtgnid2("1227");// 子功能id 必须填写
		sl.setExplain("");// 操作说明
		syslogService.saveSysLog(sl);
		this.outJsonResult(contractlist);
		return NONE;
	}
	
	/**
	 * 
	     * @Title: flowexameListe_allflow   
	     * @Description: 发送室 全流程查询列表1362
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String flowexameListe_allflow()throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		PageReturnDTO contractlist = new PageReturnDTO();

		contractlist = this.examFlowService.flowexameListe_allflow(this.model, user.getUserid(), user.getCenter_num(),
				this.rows, this.getPage(),user);
		SysLog sl = new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid() + "");
		sl.setOper_type("0");// 0 查询，1插入 2修改 3 删除 4导入 9 其他
		sl.setXtgnid("");// 可不填写
		sl.setXtgnid2("1362");// 子功能id 必须填写
		sl.setExplain("");// 操作说明
		syslogService.saveSysLog(sl);
		this.outJsonResult(contractlist);
		return NONE;
	}
	
	/**
	 * 
	     * @Title: checkCTMR   
	     * @Description: 批量检查是否有做CT/MR 1357
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String checkCTMR()throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		List<ExamInfoDTO> list = this.examFlowService.queryExamInfoHasCTMR(model.getExam_num(), user.getCenter_num());
		SysLog sl = new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid() + "");
		sl.setOper_type("0");// 0 查询，1插入 2修改 3 删除 4导入 9 其他
		sl.setXtgnid("");// 可不填写
		sl.setXtgnid2("1357");// 子功能id 必须填写
		sl.setExplain("");// 操作说明
		syslogService.saveSysLog(sl);
		this.outJsonResult(list);
		return NONE;
	}
	/**
	 * 保存收发室说明 1355
	     * @Title: saveEdesc   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String saveEdesc() throws WebException,SQLException{
		UserDTO user = (UserDTO) session.get("username");
		this.message = this.examFlowService.saveEdesc(this.model.getExam_num(), this.model.getEdesc(), user);
		this.outJsonStrResult(this.message);
		
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("1");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("1355");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return NONE;
	}
	
	/**
	 * 
	     * @Title: flow_m_show   
	     * @Description: 流程-解读室主页面 1234   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String flow_m_show()  throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		this.model.setTime1(DateTimeUtil.getDate2());
		this.model.setTime2(DateTimeUtil.getDate2());
		this.model.setReport_print_type(
				this.customerInfoService.getCenterconfigByKey("REPORT_PRINT_TYPE", user.getCenter_num()).getConfig_value().trim());
		return SUCCESS;
	}
	/**
	 * 
	     * @Title: flowexamListm   
	     * @Description:报告解读综合查询 1235 
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String flowexamListm()throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		PageReturnDTO contractlist = new PageReturnDTO();
		contractlist = this.examFlowService.getFlowExamInfoUserListm(this.model, user.getUserid(), user.getCenter_num(),
				this.rows, this.getPage());
		SysLog sl = new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid() + "");
		sl.setOper_type("0");// 0 查询，1插入 2修改 3 删除 4导入 9 其他
		sl.setXtgnid("");// 可不填写
		sl.setXtgnid2("1231");// 子功能id 必须填写
		sl.setExplain("");// 操作说明
		syslogService.saveSysLog(sl);
		this.outJsonResult(contractlist);
		return NONE;
	}
	
	/**
	 * 
	     * @Title: flowmmessageshow   
	     * @Description:报告解读获取内容 1236   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String flowmmessageshow()throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		message =this.examFlowService.flowmmessageshow(this.model.getExam_num(),"m", user);
		SysLog sl = new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid() + "");
		sl.setOper_type("0");// 0 查询，1插入 2修改 3 删除 4导入 9 其他
		sl.setXtgnid("");// 可不填写
		sl.setXtgnid2("1236");// 子功能id 必须填写
		sl.setExplain("");// 操作说明
		syslogService.saveSysLog(sl);
		this.outJsonStrResult(message);
		return NONE;
	}
	
	/**
	 * 
	     * @Title: flowmmessageUpdate   
	     * @Description: 报告解读保存内容 1237  
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String flowmmessageUpdate()throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		message =this.examFlowService.flowmmessageUpdate(this.model.getExam_num(),"m",this.model.getRemark(), user);
		SysLog sl = new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid() + "");
		sl.setOper_type("2");// 0 查询，1插入 2修改 3 删除 4导入 9 其他
		sl.setXtgnid("");// 可不填写
		sl.setXtgnid2("1237");// 子功能id 必须填写
		sl.setExplain("");// 操作说明
		syslogService.saveSysLog(sl);
		this.outJsonStrResult(message);
		return NONE;
	}
	
	/**
	 * 
	     * @Title: getFlowExamDoc   
	     * @Description: 流程-总检主页面获取主检查医生 1941 
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String getFlowExamDoc() throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		List<JobDTO> list = new ArrayList<JobDTO>();
		list =this.examFlowService.getFlowExamDoc(user);
		this.outJsonResult(list);
		return NONE;
	}

	/**
	 *     流程-总检主页面获取主检查体检信息 1942 
	     * @Title: flowexameListzjbg   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String flowexameListzjbg() throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		List<ExamInfoUserDTO> contractlist = this.examFlowService.flowexameListzjbg(this.model.getDoctor_id(),this.model.getExam_num(), user.getUserid(), user.getCenter_num(),user);
		this.outJsonResult(contractlist);
		return NONE;
	}
	
	/**
	 * 
	     * @Title: getAcceptanceItemDbgj   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String getAcceptanceItemDbgj() throws WebException{
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
	 * 核收与取消核收操作保存 381
	     * @Title: AcceptanceCheckExamInfo   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String flowacceptanceCheckExamInfo() throws WebException,SQLException{
		UserDTO user = (UserDTO) session.get("username");
		this.message = this.examFlowService.saveAcceptanceExamInfoFlow(this.model.getExam_num(),model.getFromacc(), user);
		this.outJsonStrResult(this.message);
		return NONE;
	}
	
	/**
	 * 1754报告取消发送审核
	     * @Title: flowexambackchk   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String flowexambackchk() throws WebException,SQLException{
		UserDTO user = (UserDTO) session.get("username");
		this.message = this.examFlowService.flowexambackchk(model.getExam_num(), user);
		this.outJsonStrResult(this.message);
		return NONE;
	}
	
	/**
	 * 1756预览组工作量查询
	     * @Title: getFlowExamPreviewCount   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String getFlowExamPreviewCount() throws WebException,SQLException{
		UserDTO user = (UserDTO) session.get("username");
		ExamSummaryDTO exam = this.examFlowService.getFlowExamPreviewCount(user);
		this.outJsonResult(exam);
		return NONE;
	}
}