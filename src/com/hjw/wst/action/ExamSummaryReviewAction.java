package com.hjw.wst.action;

import com.hjw.util.DateTimeUtil;
import com.hjw.webService.client.Bean.MSGSendBean;
import com.hjw.webService.client.MSGSendMessage;
import com.hjw.webService.client.body.MSGResBody;
import com.hjw.wst.DTO.ExamSummaryReviewDTO;
import com.hjw.wst.DTO.ExaminfoChargingItemDTO;
import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.wst.DTO.UserDTO;
import com.hjw.wst.domain.SysLog;
import com.hjw.wst.model.ExamSummaryReviewModel;
import com.hjw.wst.service.ExamSummaryReviewService;
import com.hjw.wst.service.SyslogService;
import com.opensymphony.xwork2.ModelDriven;
import com.synjones.framework.aop.SystemType;
import com.synjones.framework.exception.WebException;
import com.synjones.framework.web.action.BaseAction;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.text.ParseException;
import java.util.List;

public class ExamSummaryReviewAction extends BaseAction implements ModelDriven {
	
	private static final long serialVersionUID = 8217814910220881058L;

	private ExamSummaryReviewModel model = new ExamSummaryReviewModel();
	private ExamSummaryReviewService examSummaryReviewService;
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

	public void setSyslogService(SyslogService syslogService) {
		this.syslogService = syslogService;
	}

	public ExamSummaryReviewModel getModel() {
		return model;
	}

	public void setModel(ExamSummaryReviewModel model) {
		this.model = model;
	}

	public void setExamSummaryReviewService(ExamSummaryReviewService examSummaryReviewService) {
		this.examSummaryReviewService = examSummaryReviewService;
	}
	
	/**
	 * 1160 总检室复查设定按钮弹出页面
	     * @Title: getExamSummaryReviewAddPage   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return      
	     * @return: String      
	     * @throws
	 */
	public String getExamSummaryReviewAddPage()throws WebException {
		UserDTO user = (UserDTO) session.get("username");
		SystemType defaultapp = (SystemType) session.get("defaultapp");
		ExamSummaryReviewDTO examSummaryReviewDTO = this.examSummaryReviewService.getExamSummaryReview(model.getExam_num(),defaultapp.getComid());
		if(examSummaryReviewDTO != null){
			model.setId(examSummaryReviewDTO.getId());
			model.setReview_date(examSummaryReviewDTO.getReview_date());
			model.setReview_title(examSummaryReviewDTO.getReview_title());
			model.setReview_status(examSummaryReviewDTO.getReview_statuss());
			model.setReview_user(examSummaryReviewDTO.getReview_user());
			model.setNotice_time(examSummaryReviewDTO.getNotice_time());
			model.setNotice_type(examSummaryReviewDTO.getNotice_types());
			model.setNotice_user(examSummaryReviewDTO.getNotice_user());
		}else{
			model.setReview_status("未通知");
			model.setReview_date(DateTimeUtil.getDate2());
			model.setReview_user(user.getUsername());
		}
		return SUCCESS;
	}
	
	/**
	 * 1161 复查设定信息保存
	     * @Title: saveExamSummaryReview   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String saveExamSummaryReview() throws WebException {
		UserDTO user = (UserDTO) session.get("username");
		SystemType defaultapp = (SystemType) session.get("defaultapp");
		this.message = this.examSummaryReviewService.saveExamSummaryReview(model, user,defaultapp.getComid());
		this.outJsonStrResult(this.message);
		return NONE;
	}
	
	/**
	 * 1162 通知复查管理页面
	     * @Title: getExamSummaryReviewListPage   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String getExamSummaryReviewListPage() throws WebException, ParseException{
		this.model.setTime1(DateTimeUtil.getDate2());
		this.model.setTime2(DateTimeUtil.getDate2());
		this.model.setTime3(DateTimeUtil.getDate2());
		this.model.setTime4(DateTimeUtil.getdateAddDay(30, DateTimeUtil.getDate2()));
		UserDTO user = (UserDTO) session.get("username");
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("1162");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return SUCCESS;
	}
	
	/**
	 * 1163 查询总检设定复查列表
	     * @Title: getExamSummaryReviewList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String getExamSummaryReviewList() throws WebException {
		PageReturnDTO list = this.examSummaryReviewService.getExamSummaryReviewList(model, this.rows, this.getPage(),this.sort,this.order);
		this.outJsonResult(list);
		return NONE;
	}
	
	/**
	 * 1164 总检设定的复查信息通知或作废
	     * @Title: updateExamSummaryReview   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
//	public String updateExamSummaryReview() throws WebException{
//		UserDTO user = (UserDTO) session.get("username");
//		this.message = this.examSummaryReviewService.updateExamSummaryReview(model, user);
//		if("2".equals(model.getReview_status())){//通知复查信息
//			if("1".equals(model.getNotice_type())){
//				MSGSendMessage mSGSendMessage = new MSGSendMessage();
//				MSGSendBean eu = new  MSGSendBean();
//				eu.setBatchCode(this.message);
//				MSGResBody mb = mSGSendMessage.Send(eu);
//				this.message = "ok-通知复查成功!";
//			}
//		}
//		this.outJsonStrResult(this.message);
//		return NONE;
//	}
	public String updateExamSummaryReview() throws WebException {
		UserDTO user = (UserDTO) session.get("username");
		
		JSONArray ja = JSONArray.fromObject(model.getLi());
		for(int i=0;i<ja.size();i++) {
			ExamSummaryReviewDTO esr = (ExamSummaryReviewDTO) JSONObject.toBean(ja.optJSONObject(i),ExamSummaryReviewDTO.class);
			String msg = this.examSummaryReviewService.updateExamSummaryReview(model.getReview_status(), model.getNotice_type(), model.getSms_note(), esr, user);
			if("2".equals(model.getReview_status())){//通知复查信息
				if("1".equals(model.getNotice_type())){
					MSGSendMessage mSGSendMessage = new MSGSendMessage();
					MSGSendBean eu = new MSGSendBean();
					eu.setBatchCode(msg);
					MSGResBody mb = mSGSendMessage.Send(eu);
				}
			}
		}
		this.message = "ok-本次成功处理"+ja.size()+"条";
		this.outJsonStrResult(this.message);
		return NONE;
	}
	
	/**
	 *  
	 * 1165 日期加上天数获得新的日期
	     * @Title: dateAddNum   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String getdateAddDay() throws WebException {
		String date = DateTimeUtil.getdateAddDay(this.model.getNum(),this.model.getReview_date());
		if("".equals(date)){
			this.message = "error,复查日期请输入合法的日期!";
		}else{
			this.message = "ok,"+date;
		}
		this.outJsonStrResult(this.message);
		return NONE;
	}
	
	/**
	 * 1701获取已设定的复查项目列表
	     * @Title: getExamSummaryReviewItemList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String getExamSummaryReviewItemList() throws WebException {
		List<ExaminfoChargingItemDTO> list = this.examSummaryReviewService.getExamSummaryReviewItemList(model.getExam_num());
		this.outJsonResult(list);
		return NONE;
	}
	
	/**
	 *1716  取消或恢复复查
	     * @Title: saveCancelReview   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String saveCancelReview() throws WebException {
		UserDTO user = (UserDTO) session.get("username");
		this.message = this.examSummaryReviewService.saveCancelReview(model, user);
		this.outJsonStrResult(this.message);
		return NONE;
	}
	
	/**
	 * 1717 查询设定复查信息
	     * @Title: getExamSummaryReview   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String getExamSummaryReview() throws WebException {
		SystemType defaultapp = (SystemType) session.get("defaultapp");
		ExamSummaryReviewDTO examSummaryReviewDTO = this.examSummaryReviewService.getExamSummaryReview(model.getExam_num(),defaultapp.getComid());
		this.outJsonResult(examSummaryReviewDTO);
		return NONE;
	}
}
