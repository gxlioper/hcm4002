package com.hjw.wst.action;

import java.sql.SQLException;
import java.util.List;

import com.hjw.util.ExportTxtFile;
import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.wst.DTO.ThridInterfaceLogDto;
import com.hjw.wst.DTO.ThridInterfaceLogLisResultDto;
import com.hjw.wst.DTO.ThridInterfaceLogPaceResultDto;
import com.hjw.wst.DTO.UserDTO;
import com.hjw.wst.domain.SysLog;
import com.hjw.wst.model.ThridInterfaceLogModel;
import com.hjw.wst.service.SyslogService;
import com.hjw.wst.service.ThridInterfaceLogService;
import com.opensymphony.xwork2.ModelDriven;
import com.synjones.framework.exception.WebException;
import com.synjones.framework.web.action.BaseAction;

public class ThridInterfaceLogAction extends BaseAction implements ModelDriven{
	
	/**   
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)   
	 */ 
	private static final long serialVersionUID = 1L;
	private ThridInterfaceLogService thridInterfaceLogService;
	private ThridInterfaceLogModel model = new ThridInterfaceLogModel();
	private int page = 1; // 当前页
	private int rows = 15; // easyui每页显示条数
	private SyslogService syslogService;

	public ThridInterfaceLogModel getModel() {
		return model;
	}
	public void setModel(ThridInterfaceLogModel model) {
		this.model = model;
	}
	
	public void setSyslogService(SyslogService syslogService) {
		this.syslogService = syslogService;
	}
	public ThridInterfaceLogService getThridInterfaceLogService() {
		return thridInterfaceLogService;
	}
	public void setThridInterfaceLogService(ThridInterfaceLogService thridInterfaceLogService) {
		this.thridInterfaceLogService = thridInterfaceLogService;
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

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}
	
	
	
	/**
	 * 
	     * @Title: queryAllMessageName   
	     * @Description: TODO(查询接口名称)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String queryAllMessageName() throws WebException, SQLException {
		List<ThridInterfaceLogDto> list = this.thridInterfaceLogService.queryAllMessageName();
		this.outJsonResult(list);
		return NONE;
	}
	
	/**
	 * 
	     * @Title: thirdInterLogView   
	     * @Description: TODO(跳转页面)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String thirdInterLogView() throws WebException, SQLException {
		UserDTO user = (UserDTO) session.get("username");
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("1560");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return SUCCESS;
	}
	
	/**
	 * 
	     * @Title: queryThirdInterfaceLog   
	     * @Description: TODO(查询日志信息)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String queryThirdInterfaceLog() throws WebException, SQLException {

		UserDTO user = (UserDTO) session.get("username");
		PageReturnDTO logList = new PageReturnDTO();
		
		if ((this.model.getChkItem() != null) && (this.model.getChkItem().trim().length() > 2)) {
			if (this.model.getChkItem().trim().indexOf("req_no") < 0) {
				this.model.setReq_no("");
			}
			if (this.model.getChkItem().trim().indexOf("exam_no") < 0) {
				this.model.setExam_no("");
			}
			if (this.model.getChkItem().trim().indexOf("message_name") < 0) {
				this.model.setMessage_name("");
			}
			if (this.model.getChkItem().trim().indexOf("message_type") < 0) {
				this.model.setMessage_type("");
			}
			if (this.model.getChkItem().trim().indexOf("flag") < 0) {
				this.model.setFlag(null);
			}
			
			if (this.model.getChkItem().indexOf("message_date") < 0) {
				this.model.setMessage_startDate("");
				this.model.setMessage_endDate("");
			}

			if (this.model.getChkItem().trim().indexOf("sender") < 0) {
				this.model.setSender("");
			}

			if (this.model.getChkItem().trim().indexOf("receiver") < 0) {
				this.model.setReceiver("");
			}

			logList = this.thridInterfaceLogService.queryThirdInterfaceLog(this.model, user.getCenter_num(), this.getPage(), this.rows);

		}
		
		SysLog sl = new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid() + "");
		sl.setOper_type("0");// 0 查询，1插入 2修改 3 删除 4导入 9 其他
		sl.setXtgnid("");// 可不填写
		sl.setXtgnid2("1561");// 子功能id 必须填写
		sl.setExplain("");// 操作说明
		syslogService.saveSysLog(sl);
		
		this.outJsonResult(logList);
		return NONE;
	}
	
	
	/**
	 * 
	     * @Title: getLogDetailDrid   
	     * @Description: TODO(日志详情查询 )   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String getLogDetailDrid() throws WebException, SQLException {

		UserDTO user = (UserDTO) session.get("username");
		PageReturnDTO logListDetail = new PageReturnDTO();
		logListDetail = this.thridInterfaceLogService.getLogDetailDrid(this.model.getTil_id(),this.getPage(), this.rows);
		SysLog sl = new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid() + "");
		sl.setOper_type("0");// 0 查询，1插入 2修改 3 删除 4导入 9 其他
		sl.setXtgnid("");// 可不填写
		sl.setXtgnid2("1562");// 子功能id 必须填写
		sl.setExplain("");// 操作说明
		syslogService.saveSysLog(sl);
		
		this.outJsonResult(logListDetail);
		return NONE;
	}
	
	
	/**
	 * 
	     * @Title: viewLogDetailPage   
	     * @Description: TODO(查看详情详情页面)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String viewLogDetailPage() throws WebException, SQLException {
		
		ThridInterfaceLogDto thirdDto = this.thridInterfaceLogService.viewLogDetailPage(this.model.getId());
		this.model.setReq_no(thirdDto.getReq_no());
		this.model.setExam_no(thirdDto.getExam_no());
		this.model.setMessage_id(thirdDto.getMessage_id());
		this.model.setMessage_name(thirdDto.getMessage_name());
		this.model.setMessage_startDate(thirdDto.getMessage_date());
		this.model.setMessage_type(thirdDto.getMessage_type());
		this.model.setSender(thirdDto.getSender());
		this.model.setReceiver(thirdDto.getReceiver());
		this.model.setMessage_request(thirdDto.getMessage_request());
		this.model.setMessage_response(thirdDto.getMessage_response());
		this.model.setFlag(thirdDto.getFlag());
		this.model.setSys_request(thirdDto.getSys_request());
		this.model.setSys_respones(thirdDto.getSys_respones());
		this.model.setXtgnb_id(thirdDto.getXtgnb_id());
		this.model.setMessage_inout(thirdDto.getMessage_inout());
		//查询lis结果
		ThridInterfaceLogLisResultDto thirdLis = this.thridInterfaceLogService.viewLogDetailLis(this.model.getId());
		if(thirdLis!=null) {
			this.model.setExam_num(thirdLis.getExam_num());
			this.model.setSample_barcode(thirdLis.getSample_barcode());
			this.model.setLis_item_code(thirdLis.getLis_item_code());
			this.model.setLis_item_name(thirdLis.getLis_item_name());
			this.model.setReport_item_code(thirdLis.getReport_item_code());
			this.model.setReport_item_name(thirdLis.getReport_item_name());
			this.model.setExam_date(thirdLis.getExam_date());
			this.model.setItem_result(thirdLis.getItem_result());
			this.model.setItem_unit(thirdLis.getItem_unit());
			this.model.setLis_flag(thirdLis.getLis_flag());
			this.model.setRef(thirdLis.getRef());
			this.model.setSeq_code(thirdLis.getSeq_code());
			this.model.setDoctor(thirdLis.getDoctor());
			this.model.setSh_doctor(thirdLis.getSh_doctor());
			this.model.setNote(thirdLis.getNote());
			this.model.setRead_flag(thirdLis.getRead_flag());
			this.model.setCreate_time(thirdLis.getCreate_time());
		}
		//查询pace结果
		ThridInterfaceLogPaceResultDto thirdPacs = this.thridInterfaceLogService.viewLogDetailPacs(this.model.getId());
		if(thirdPacs!=null) {
			this.model.setReq_no(thirdPacs.getReq_no());
			this.model.setPacs_checkno(thirdPacs.getPacs_checkno());
			this.model.setExam_num(thirdPacs.getExam_num());
			this.model.setItem_name(thirdPacs.getItem_name());
			this.model.setPacs_item_code(thirdPacs.getPacs_item_code());
			this.model.setStudy_type(thirdPacs.getStudy_type());
			this.model.setStudy_body_part(thirdPacs.getStudy_body_part());
			this.model.setClinic_diagnose(thirdPacs.getClinic_diagnose());
			this.model.setClinic_symptom(thirdPacs.getClinic_symptom());
			this.model.setClinic_advice(thirdPacs.getClinic_advice());
			this.model.setIs_abnormal(thirdPacs.getIs_abnormal());
			this.model.setReport_img_path(thirdPacs.getReport_img_path());
			this.model.setImg_path(thirdPacs.getImg_path());
			this.model.setStudy_state(thirdPacs.getStudy_state());
			this.model.setReg_doc(thirdPacs.getReg_doc());
			this.model.setCheck_doc(thirdPacs.getCheck_doc());
			this.model.setCheck_date(thirdPacs.getCheck_date());
			this.model.setReport_doc(thirdPacs.getReport_doc());
			this.model.setReport_date(thirdPacs.getReport_date());
			this.model.setAudit_doc(thirdPacs.getAudit_doc());
			this.model.setAudit_date(thirdPacs.getAudit_date());
			this.model.setNote(thirdPacs.getNote());
			this.model.setStatus(thirdPacs.getStatus());
			this.model.setTrans_date(thirdPacs.getTrans_date());
			this.model.setIs_tran_image(thirdPacs.getIs_tran_image());
			this.model.setIs_report_image(thirdPacs.getIs_report_image());
			this.model.setCreate_time(thirdPacs.getCreate_time());
		}
		UserDTO user = (UserDTO) session.get("username");
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("1563");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return SUCCESS;
	}
	
	/**
	 * 
	     * @Title: expTxtContentFile   
	     * @Description: TODO(导出txt)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String expTxtContentFile() throws WebException, SQLException {
		
		ThridInterfaceLogDto thirdDto = this.thridInterfaceLogService.viewLogDetailPage(this.model.getId());
		
		String fileName = ("时间-"+thirdDto.getMessage_date()+"-"+thirdDto.getMessage_name()+"日志").replaceAll(" ", "_");
		// 2018-08-01_09_13_12
		StringBuffer buffer = new StringBuffer();
		
		buffer.append("--------------"+thirdDto.getMessage_date()+thirdDto.getMessage_name()+"日志详情------------------\r\n\r\n");
		buffer.append("接口名称："+thirdDto.getMessage_name()+"\r\n\r\n");
		if(thirdDto.getFlag()==0) {
			buffer.append("消息处理状态：正常 \r\n\r\n");
		}else if(thirdDto.getFlag()==1) {
			buffer.append("消息处理状态：拒绝 \r\n\r\n");
		}else if(thirdDto.getFlag()==2) {
			buffer.append("消息处理状态：错误 \r\n\r\n");
		}
		if(0==thirdDto.getMessage_inout()) {
			buffer.append("调用方式：主动请求 \r\n\r\n");
		}else if(1==thirdDto.getMessage_inout()) {
			buffer.append("调用方式：被动调用 \r\n\r\n");
		}
		
		buffer.append("申请号："+thirdDto.getReq_no()+"\r\n\r\n");
		buffer.append("体检编号："+thirdDto.getExam_no()+"\r\n\r\n");
		buffer.append("消息标识："+thirdDto.getMessage_id()+"\r\n\r\n");
		buffer.append("消息时间："+thirdDto.getMessage_date()+"\r\n\r\n");
		buffer.append("消息类型："+thirdDto.getMessage_type()+"\r\n\r\n");
		buffer.append("消息发送者："+thirdDto.getSender()+"\r\n\r\n");
		buffer.append("消息接收者："+thirdDto.getReceiver()+"\r\n\r\n");
		buffer.append("消息请求原文：\r\n"+thirdDto.getMessage_request()+"\r\n\r\n");
		buffer.append("消息应答原文：\r\n"+thirdDto.getMessage_response()+"\r\n\r\n");
		buffer.append("系统请求原文：\r\n"+thirdDto.getSys_request()+"\r\n\r\n");
		buffer.append("系统应答原文：\r\n"+thirdDto.getSys_respones()+"\r\n\r\n");
		
		buffer.append("================以下是接口时间明细日志信息=====================\r\n\r\n");
		List<ThridInterfaceLogDto> thirdDetailLog = this.thridInterfaceLogService.queryLogDetailList(this.model.getId());
		if(thirdDetailLog.size()>0) {
			for(int i=0;i<thirdDetailLog.size();i++) {
				buffer.append("日志时间："+thirdDetailLog.get(i).getLdate()+"\r\n\r\n");
				buffer.append("\r\n"+thirdDetailLog.get(i).getLmessage()+"\r\n\r\n");
			}
		}
		
		ExportTxtFile.writeToTxt(response,buffer.toString(),fileName);
		
		UserDTO user = (UserDTO) session.get("username");
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("4");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("1564");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return NONE;
	}
	
	
	/**
	 * 
	     * @Title: viewDetailLogQuery   
	     * @Description: TODO(日志明细查看详情)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String viewDetailLogQuery() throws WebException, SQLException {
		
		ThridInterfaceLogDto thirdDetailDto = this.thridInterfaceLogService.viewDetailLogQuery(this.model.getId());
		this.model.setId(thirdDetailDto.getId());
		this.model.setTil_id(thirdDetailDto.getTil_id());
		this.model.setLdate(thirdDetailDto.getLdate());
		this.model.setLmessage(thirdDetailDto.getLmessage());
		UserDTO user = (UserDTO) session.get("username");
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("1565");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return SUCCESS;
	}
	
	
	
	/**
	 * 
	     * @Title: expTxtDetailContentFile   
	     * @Description: TODO(打出明细表日志)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String expTxtDetailContentFile() throws WebException, SQLException {
		
		StringBuffer buffer = new StringBuffer();
		String fileName = ("日志明细导出").replaceAll(" ", "_");
		buffer.append("================以下是接口时间明细日志信息=====================\r\n\r\n");
		List<ThridInterfaceLogDto> thirdList = this.thridInterfaceLogService.viewDetailLogList(this.model.getId());
		if(thirdList.size()>0) {
			for(int i=0;i<thirdList.size();i++) {
				buffer.append("日志时间："+thirdList.get(i).getLdate()+"\r\n\r\n");
				buffer.append("日志信息：\r\n"+thirdList.get(i).getLmessage()+"\r\n\r\n");
			}
		}
		
		ExportTxtFile.writeToTxt(response,buffer.toString(),fileName);
		
		UserDTO user = (UserDTO) session.get("username");
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("4");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("1566");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return NONE;
	}
	
	
	/**
	 * 
	     * @Title: deleteDetailLogMsg   
	     * @Description: TODO(批量删除详情日志信息)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String deleteDetailLogMsg() throws WebException, SQLException {
		this.message = this.thridInterfaceLogService.deleteDetailLogMsg(this.model.getId());
		this.outJsonStrResult(this.message);
		return NONE;
	}
	
	
	/**
	 * 
	     * @Title: deleteDetailLogMsg   
	     * @Description: TODO(批量删除主表日志 和附表日志)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String deleteLogMaterAndDetail() throws WebException, SQLException {
		this.message = this.thridInterfaceLogService.deleteLogMaterAndDetail(this.model.getId());
		this.outJsonStrResult(this.message);
		return NONE;
	}
	
	/**
	 * 
	     * @Title: queryViewDetailListLog   
	     * @Description: TODO(查看明细日志详情)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String queryViewDetailListLog() throws WebException, SQLException {
		List<ThridInterfaceLogDto> thirdList = this.thridInterfaceLogService.queryLogDetailList(this.model.getId());
		this.outJsonResult(thirdList);
		return NONE;
	}
	
	

}
