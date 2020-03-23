package com.hjw.wst.action;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.gson.Gson;
import com.hjw.config.Logincheck;
import com.hjw.util.DateTimeUtil;
import com.hjw.util.Timeutils;
import com.hjw.wst.DTO.JobDTO;
import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.wst.DTO.ReportReceiveDTO;
import com.hjw.wst.DTO.UserDTO;
import com.hjw.wst.domain.QuestExamList;
import com.hjw.wst.domain.ExamInfo;
import com.hjw.wst.domain.ReportReceive;
import com.hjw.wst.domain.SysLog;
import com.hjw.wst.domain.WebUserInfo;
import com.hjw.wst.model.ReportManagerModel;
import com.hjw.wst.service.CustomerInfoService;
import com.hjw.wst.service.ExamFlowService;
import com.hjw.wst.service.ReportManagerService;
import com.hjw.wst.service.SyslogService;
import com.hjw.wst.service.UserInfoService;
import com.opensymphony.xwork2.ModelDriven;
import com.synjones.framework.exception.WebException;
import com.synjones.framework.web.action.BaseAction;
/**
 * 检后报告管理类
     * @Title:  火箭蛙体检管理系统   
     * @Package com.hjw.wst.action   
     * @Description:  
     * @author: dangqi     
     * @date:   2016年8月31日 上午9:01:46   
     * @version V2.0.0.0
 */
@SuppressWarnings("rawtypes")
public class ReportManagerAction extends BaseAction implements ModelDriven{

	private static final long serialVersionUID = 1L;
	@SuppressWarnings("unused")
	private Log log = LogFactory.getLog(this.getClass());
	private ReportManagerModel model = new ReportManagerModel();
	@SuppressWarnings("unused")
	private Logincheck logincheck;
	private ReportManagerService reportManagerService;
	private UserInfoService userInfoService;
	private SyslogService syslogService;
	private ExamFlowService examFlowService;
//	private int rp = 15; // 每页显示的条数
//	private int page = 1; // 当前页
	private int rows = 15; // easyui每页显示条数
	private String sort;
	private String order;
    private CustomerInfoService customerInfoService;
	
	public void setCustomerInfoService(CustomerInfoService customerInfoService) {
		this.customerInfoService = customerInfoService;
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

	public void setReportManagerService(ReportManagerService reportManagerService) {
		this.reportManagerService = reportManagerService;
	}

	public void setUserInfoService(UserInfoService userInfoService) {
		this.userInfoService = userInfoService;
	}

	public void setSyslogService(SyslogService syslogService) {
		this.syslogService = syslogService;
	}
	
	public void setExamFlowService(ExamFlowService examFlowService) {
		this.examFlowService = examFlowService;
	}
	public void setLogincheck(Logincheck logincheck) {
		this.logincheck = logincheck;
	}
	@Override
	public Object getModel() {
		return this.model;
	}
	public void setModel(ReportManagerModel model) {
		this.model = model;
	}

	/**
	 * 检后 报告管理页面  346
	     * @Title: reportManager   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String reportManager() throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("346");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return SUCCESS;
	}

	/**
	 * 多条件查询体检人员信息 347
	     * @Title: getReportExamInfoList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String getReportExamInfoList() throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		PageReturnDTO page = this.reportManagerService.getExamInfoList(this.model, this.rows, this.getPage(),this.sort,this.order,user);
		this.outJsonResult(page);
		return NONE;
	}
	
	/**
	 * 确认报告已打印 348
	     * @Title: confirmReportPrint   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String confirmReportPrint() throws WebException,SQLException{
		UserDTO user = (UserDTO) session.get("username");
		ExamInfo examinfo = this.reportManagerService.getExamInfoByExamNum(this.model.getExam_num());
		if(examinfo == null){
			this.message = "体检信息不存在!";
		}else{
			if(!"Z".equals(examinfo.getStatus())){
				this.message = "未做总检,不能确定整理报告!";
			}else{
				examinfo.setIs_report_tidy("Y");
				examinfo.setReport_tidy_user(user.getName());
				examinfo.setReport_tidy_time(DateTimeUtil.parse());
				this.reportManagerService.updateExamInfo(examinfo);
				this.message = "确定整理报告成功!";
				
				SysLog sl =  new SysLog();
				sl.setCenter_num(user.getCenter_num());
				sl.setUserid(user.getUserid()+"");
				sl.setOper_type("2");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
				sl.setXtgnid("");//可不填写
				sl.setXtgnid2("348");//子功能id 必须填写
				sl.setExplain("");//操作说明
				syslogService.saveSysLog(sl);
			}
		}
		this.outJsonStrResult(this.message);
		return NONE;
	}
	
	/**
	 * 报告邮寄页面 349
	     * @Title: getReportMail   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String getReportMail() throws WebException,SQLException{
		ReportReceive rr = this.reportManagerService.getInitializeReport(this.model.getExam_num());
		
		this.model.setJoin_date(DateTimeUtil.getDate2());
		this.model.setReceive_type("1");
		if(rr != null){
			this.model.setExam_num(rr.getExam_num());
			this.model.setUser_name(rr.getReceive_name());
			this.model.setReceive_name(rr.getReceive_name());
			this.model.setReceive_address(rr.getReceive_address());
			this.model.setReceive_phone(rr.getReceive_phone());
		}
		return SUCCESS;
	}
	
	/**
	 * 保存报告发送方式 350
	     * @Title: saveReportReceive   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String saveManagerReportReceive() throws WebException,SQLException{
		UserDTO user = (UserDTO) session.get("username");
		this.message = this.examFlowService.flowexame1insert(this.model.getExam_num(), user);
		if(this.message.startsWith("error")) {
			this.outJsonStrResult(this.message);
			return NONE;
		}
		
		String[] examNum = this.model.getExam_num().split(",");
		
		for (int i = 0; i < examNum.length; i++) {
			ReportReceive reportReceive = new ReportReceive();
			
			reportReceive.setExam_num(examNum[i]);
			reportReceive.setReceive_name(this.model.getReceive_name());
			reportReceive.setReceive_phone(this.model.getReceive_phone());
			reportReceive.setReceive_postcode(this.model.getReceive_postcode());
			reportReceive.setReceive_type(this.model.getReceive_type());
			reportReceive.setReceive_date(this.model.getReceive_date());
			reportReceive.setReceive_remark(this.model.getReceive_remark());
			reportReceive.setReceive_address(this.model.getReceive_address());
			reportReceive.setCreater(user.getUserid());
			reportReceive.setCreate_time(DateTimeUtil.parse());
			reportReceive.setUpdater(user.getUserid());
			reportReceive.setUpdate_time(DateTimeUtil.parse());
			
			this.reportManagerService.saveReportRecive(reportReceive);
		}
		
		this.message = "保存成功!";
		this.outJsonStrResult(this.message);
		
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("1");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("350");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		
		return NONE;
	}
	
	/**
	 *  报告自取页面 351
	     * @Title: getReportThems   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String getReportThems() throws WebException,SQLException{
		ReportReceive rr = this.reportManagerService.getInitializeReport(this.model.getExam_num());
		
		this.model.setJoin_date(DateTimeUtil.getDate2());
		this.model.setReceive_type("2");
		if(rr != null){
			this.model.setExam_num(rr.getExam_num());
			this.model.setUser_name(rr.getReceive_name());
			this.model.setReceive_name(rr.getReceive_name());
			this.model.setReceive_phone(rr.getReceive_phone());
		}
		return SUCCESS;
	}
	
	/**
	 * 显示报告邮寄信息 352
	     * @Title: showReportMail   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String showReportMail() throws WebException,SQLException{
		ReportReceiveDTO rr = this.reportManagerService.getReportReceive(this.model.getExam_num());
		if(rr != null){
			this.model.setId(rr.getId());
			this.model.setExam_num(rr.getExam_num());
			this.model.setUser_name(rr.getUser_name());
			this.model.setReceive_name(rr.getReceive_name());
			this.model.setReceive_address(rr.getReceive_address());
			this.model.setReceive_phone(rr.getReceive_phone());
			this.model.setReceive_postcode(rr.getReceive_postcode());
			this.model.setReceive_remark(rr.getReceive_remark());
			this.model.setReceive_type(rr.getReceive_type());
			this.model.setJoin_date(Timeutils.dateToStr(rr.getReceive_date()));
			WebUserInfo wb = null;
			if(rr.getCreater() != 0){
				wb = userInfoService.loadUserInfo(rr.getCreater());
				model.setCreater(wb.getLog_Name());
			}
			if(rr.getUpdater() != 0){
				wb = userInfoService.loadUserInfo(rr.getUpdater());
				model.setUpdater(wb.getLog_Name());
			}
			if (rr.getCreate_time() != null) {
				model.setCreate_time(Timeutils.dateToStr(rr.getCreate_time()));
			}
			if (rr.getUpdate_time() != null) {
				model.setUpdate_time(Timeutils.dateToStr(rr.getUpdate_time()));
			}
		}
		return SUCCESS;
	}
	
	/**
	 * 显示报告自己取信息 353
	     * @Title: showReportThems   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String showReportThems() throws WebException,SQLException{
		ReportReceiveDTO rr = this.reportManagerService.getReportReceive(this.model.getExam_num());
		if(rr != null){
			this.model.setId(rr.getId());
			this.model.setExam_num(rr.getExam_num());
			this.model.setUser_name(rr.getUser_name());
			this.model.setReceive_name(rr.getReceive_name());
			this.model.setReceive_address(rr.getReceive_address());
			this.model.setReceive_phone(rr.getReceive_phone());
			this.model.setReceive_postcode(rr.getReceive_postcode());
			this.model.setReceive_remark(rr.getReceive_remark());
			this.model.setReceive_type(rr.getReceive_type());
			this.model.setJoin_date(Timeutils.dateToStr(rr.getReceive_date()));
			WebUserInfo wb = null;
			if(rr.getCreater() != 0){
				wb = userInfoService.loadUserInfo(rr.getCreater());
				model.setCreater(wb.getLog_Name());
			}
			if(rr.getUpdater() != 0){
				wb = userInfoService.loadUserInfo(rr.getUpdater());
				model.setUpdater(wb.getLog_Name());
			}
			if (rr.getCreate_time() != null) {
				model.setCreate_time(Timeutils.dateToStr(rr.getCreate_time()));
			}
			if (rr.getUpdate_time() != null) {
				model.setUpdate_time(Timeutils.dateToStr(rr.getUpdate_time()));
			}
		}
		return SUCCESS;
	}
	
	/**
	 * 取消报告邮寄或自取 354
	     * @Title: cancelReportMail   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String cancelReportMail() throws WebException,SQLException{
		UserDTO user = (UserDTO) session.get("username");
		if(!"".equals(this.model.getExam_num())){
//			this.message = this.examFlowService.flowexame1delete(this.model.getExam_num(), user);
//			if(this.message.startsWith("error")) {
//				this.outJsonStrResult(this.message);
//				return this.message;
//			}
			this.reportManagerService.deleteReportReceive(this.model.getExam_num());
			this.message = "删除成功!";
			SysLog sl =  new SysLog();
			sl.setCenter_num(user.getCenter_num());
			sl.setUserid(user.getUserid()+"");
			sl.setOper_type("3");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
			sl.setXtgnid("");//可不填写
			sl.setXtgnid2("354");//子功能id 必须填写
			sl.setExplain("");//操作说明
			syslogService.saveSysLog(sl);
		}else{
			this.message = "删除失败!";
		}
		this.outJsonStrResult(this.message);
		return NONE;
	}
	
	/**
	 *      团体报告管理 1238
	     * @Title: reportManager   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String termreportManager() throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		this.model.setBarcode_print_type(
				this.customerInfoService.getCenterconfigByKey("BARCODE_PRINT_TYPE", user.getCenter_num()).getConfig_value().trim());
		this.model.setZyb_barcode_print_type(this.customerInfoService.getCenterconfigByKey("ZYB_BARCODE_PRINT_TYPE", user.getCenter_num()).getConfig_value().trim());
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("1238");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return SUCCESS;
	}
	
	/**
	 * 
	     * @Title: termGetzocforcomBatch   
	     * @Description: 通过单位批次获取职业体检类型     
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String termGetzocforcomBatch()throws WebException{		
		List<JobDTO> jb = this.reportManagerService.GetzocforcomBatch(this.model.getCompany_id(), this.model.getBatchid());
		this.outJsonResult(jb);
		return NONE;
	}
	
	/**
	 * 
	     * @Title: termGetzocforcomBatch   
	     * @Description: 通过单位批次获取职业危害因素类型
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String termGetzofforcomBatch()throws WebException{		
		List<JobDTO> jb = this.reportManagerService.GetzofforcomBatch(this.model.getCompany_id(), this.model.getBatchid());
		this.outJsonResult(jb);
		return NONE;
	}
	
	/**
	 * 
	     * @Title: termExamInfoUserList   
	     * @Description: 团体报告获取体检人员信息 1239
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String termReportUserList() throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		String centernum = user.getCenter_num();
		PageReturnDTO contractlist = new PageReturnDTO();
			if (this.model.getChkItem().trim().indexOf("exam_num") < 0) {
				this.model.setExam_num("");
			}
			if (this.model.getChkItem().trim().indexOf("custname") < 0) {
				this.model.setCustname("");
			}
			if (this.model.getChkItem().trim().indexOf("sex") < 0) {
				this.model.setSex("");
			}
			
			if (this.model.getChkItem().trim().indexOf("group_id") < 0) {
				this.model.setGroup_id(0);
			}
			
			if (this.model.getChkItem().trim().indexOf("set_id") < 0) {
				this.model.setSet_id(0);
			}
			
			if (this.model.getChkItem().trim().indexOf("exam_status") < 0) {
				this.model.setExam_status("");
			}
						
			if (this.model.getChkItem().trim().indexOf("rylb") < 0) {
				this.model.setCustomer_type_id("");
			}
			
			if (this.model.getChkItem().trim().indexOf("levels") < 0) {
				this.model.setLevels("");
			}
			
			if (this.model.getChkItem().trim().indexOf("tjlx") < 0) {
				this.model.setTjlxs("");
			}			
			
			contractlist = this.reportManagerService.getTermReportUserList(this.model.getCompany_id(), this.model.getBatchid(), this.model.getExam_num(), this.model.getGroup_id(), this.model.getExam_status(), this.model.getSex(), this.model.getCustname(), this.model.getSet_id(),this.model.getCustomer_type_id(),this.model.getLevels(),this.model.getTjlxs(), user.getUserid(), centernum, 
					this.model.getChkItem(),this.rows,this.getPage(),this.sort,this.order,user,user.getCenter_num());
			
		this.outJsonResult(contractlist);
		return NONE;
	}
	
	/**
	 * 
	     * @Title: termExamInfoUserList   
	     * @Description: 团体报告获取体检人员信息职业病
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String termReportUserZybList() throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		String centernum = user.getCenter_num();
		PageReturnDTO contractlist = new PageReturnDTO();
			if (this.model.getChkItem().trim().indexOf("zybexam_num") < 0) {
				this.model.setExam_num("");
			}
			if (this.model.getChkItem().trim().indexOf("zybcustname") < 0) {
				this.model.setCustname("");
			}
						
			if (this.model.getChkItem().trim().indexOf("zytjlx") < 0) {
				this.model.setZytjlx("");
			}
			
			if (this.model.getChkItem().trim().indexOf("zywhys") < 0) {
				this.model.setZywhys("");
			}
			
			if (this.model.getChkItem().trim().indexOf("zybexam_status") < 0) {
				this.model.setExam_status("");
			}
			
			if (this.model.getChkItem().trim().indexOf("zybis_report_print") < 0) {
				this.model.setIs_report_print("");
			}
			
			if (this.model.getChkItem().trim().indexOf("zyblevels") < 0) {
				this.model.setLevels("");
			}
			
			if (this.model.getChkItem().trim().indexOf("zybtjlx") < 0) {
				this.model.setTjlxs("");
			}			
			
			contractlist = this.reportManagerService.getTermReportUserZybList(this.model.getCompany_id(), this.model.getBatchid(), this.model.getComon_report_type(), this.model.getExam_num(), this.model.getZytjlx(), this.model.getExam_status(), this.model.getIs_report_print(), this.model.getCustname(),this.model.getZywhys(),this.model.getLevels(),this.model.getTjlxs(), user.getUserid(), centernum, 
					this.model.getChkItem(),this.rows,this.getPage(),this.sort,this.order,user,user.getCenter_num());
			
		this.outJsonResult(contractlist);
		return NONE;
	}
	
	
	
	/**
	 * 
	     * @Title: queryCommonReportTypeList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String queryCommonReportTypeList() throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		String centernum = user.getCenter_num();
		PageReturnDTO contractlist = new PageReturnDTO();
			if (this.model.getChkItem().trim().indexOf("company_id") < 0 &&  this.model.getChkItem().trim().indexOf("batchid") < 0) {
				//不包含
				this.model.setCompany_id(-1);
				this.model.setBatchid(-1);
				
			}
			
			if (this.model.getChkItem().trim().indexOf("comon_report_type") < 0) {
				this.model.setComon_report_type("");
			}
			
			if (this.model.getChkItem().trim().indexOf("zybexam_num") < 0) {
				this.model.setExam_num("");
			}
			if (this.model.getChkItem().trim().indexOf("zybcustname") < 0) {
				this.model.setCustname("");
			}
						
			if (this.model.getChkItem().trim().indexOf("zytjlx") < 0) {
				this.model.setZytjlx("");
			}
			
			if (this.model.getChkItem().trim().indexOf("zywhys") < 0) {
				this.model.setZywhys("");
			}
			
			if (this.model.getChkItem().trim().indexOf("zybexam_status") < 0) {
				this.model.setExam_status("");
			}
			
			if (this.model.getChkItem().trim().indexOf("zybis_report_print") < 0) {
				this.model.setIs_report_print("");
			}
			
			if (this.model.getChkItem().trim().indexOf("zyblevels") < 0) {
				this.model.setLevels("");
			}
			
			if (this.model.getChkItem().trim().indexOf("zybtjlx") < 0) {
				this.model.setTjlxs("");
			}			
			
			contractlist = this.reportManagerService.getTermReportUserZybList( this.model.getCompany_id(), this.model.getBatchid(), this.model.getComon_report_type(), this.model.getExam_num(), this.model.getZytjlx(), this.model.getExam_status(), this.model.getIs_report_print(), this.model.getCustname(),this.model.getZywhys(),this.model.getLevels(),this.model.getTjlxs(), user.getUserid(), centernum, 
					this.model.getChkItem(),this.rows,this.getPage(),this.sort,this.order,user,user.getCenter_num());
			
		this.outJsonResult(contractlist);
		return NONE;
	}
	
	
	
	/**
	 * 修改报告类型
	     * @Title: updateReportEdit   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: void      
	     * @throws
	 */
	public void updateReportTypeEdit()throws WebException, SQLException {
		String ids = this.model.getIds();
		
		try {
			this.reportManagerService.updateReportEdit(ids,this.model.getComon_report_type());
			this.message= "true";
		} catch (Exception e) {
			this.message="false";
			e.printStackTrace();
		}
		
		try {
			response.setContentType("text/html;charset=utf-8");
			response.getWriter().print(new Gson().toJson(this.message));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
		/**
	 * 报告邮寄/自取修改备注
	     * @Title: updateReportRemarke   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String updateReportRemarke() throws WebException, SQLException {
		UserDTO user = (UserDTO) session.get("username");
		this.message = this.reportManagerService.updateReportRemarke(this.model.getExam_num(), this.model.getReceive_remark(), user);
		this.outJsonStrResult(this.message);
		return NONE;
	}
	
}
