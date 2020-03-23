package com.hjw.wst.DTO;

import java.util.Date;

public class ExamSummaryDTO {

	private long id;
	private long exam_info_id;
	private String final_exam_result;
	private String final_doctor;
	private String final_date;
	private String check_time;
	private String exam_status;
	private long acceptance_check;
	private String acceptance_doctor;
	private String acceptance_date;
	private String approve_status;
	private String exam_guidance;
	private String final_status;
	private String user_name;
	private String exam_num;
	private String censoring_status;
	
	private String status;
	
	private String exam_doctor;
	private String app_doctor;
	private long censoring_doc;//终审医生
	private Date censoring_time;//终审时间
	private long final_worknum;//总检工作量
	private long approve_worknum;//审核工作量
	private long censoring_worknum;//终审工作量
	private long cancel_type;//操作类型 1 一键取消，0 一键恢复
	private long report_class;
	private String report_class_user;
	private String report_class_date;
	private String zyb_final_status;
	
	private long wz_count;
	private long yz_count;
	private long zj_count;
	private long t;
	
	public String getZyb_final_status() {
		return zyb_final_status;
	}
	public void setZyb_final_status(String zyb_final_status) {
		this.zyb_final_status = zyb_final_status;
	}
	public String getExam_doctor() {
		return exam_doctor;
	}
	public void setExam_doctor(String exam_doctor) {
		this.exam_doctor = exam_doctor;
	}
	public String getApp_doctor() {
		return app_doctor;
	}
	public void setApp_doctor(String app_doctor) {
		this.app_doctor = app_doctor;
	}
	public long getCensoring_doc() {
		return censoring_doc;
	}
	public void setCensoring_doc(long censoring_doc) {
		this.censoring_doc = censoring_doc;
	}
	public Date getCensoring_time() {
		return censoring_time;
	}
	public void setCensoring_time(Date censoring_time) {
		this.censoring_time = censoring_time;
	}
	public long getFinal_worknum() {
		return final_worknum;
	}
	public void setFinal_worknum(long final_worknum) {
		this.final_worknum = final_worknum;
	}
	public long getApprove_worknum() {
		return approve_worknum;
	}
	public void setApprove_worknum(long approve_worknum) {
		this.approve_worknum = approve_worknum;
	}
	public long getCensoring_worknum() {
		return censoring_worknum;
	}
	public void setCensoring_worknum(long censoring_worknum) {
		this.censoring_worknum = censoring_worknum;
	}
	public long getCancel_type() {
		return cancel_type;
	}
	public void setCancel_type(long cancel_type) {
		this.cancel_type = cancel_type;
	}
	public long getReport_class() {
		return report_class;
	}
	public void setReport_class(long report_class) {
		this.report_class = report_class;
	}
	public String getReport_class_user() {
		return report_class_user;
	}
	public void setReport_class_user(String report_class_user) {
		this.report_class_user = report_class_user;
	}
	public String getReport_class_date() {
		return report_class_date;
	}
	public void setReport_class_date(String report_class_date) {
		this.report_class_date = report_class_date;
	}
	public long getWz_count() {
		return wz_count;
	}
	public void setWz_count(long wz_count) {
		this.wz_count = wz_count;
	}
	public long getYz_count() {
		return yz_count;
	}
	public void setYz_count(long yz_count) {
		this.yz_count = yz_count;
	}
	public long getZj_count() {
		return zj_count;
	}
	public void setZj_count(long zj_count) {
		this.zj_count = zj_count;
	}
	public long getT() {
		return t;
	}
	public void setT(long t) {
		this.t = t;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public String getExam_num() {
		return exam_num;
	}
	public void setExam_num(String exam_num) {
		this.exam_num = exam_num;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getExam_info_id() {
		return exam_info_id;
	}
	public void setExam_info_id(long exam_info_id) {
		this.exam_info_id = exam_info_id;
	}
	public String getFinal_exam_result() {
		return final_exam_result;
	}
	public void setFinal_exam_result(String final_exam_result) {
		this.final_exam_result = final_exam_result;
	}
	public long getAcceptance_check() {
		return acceptance_check;
	}
	public void setAcceptance_check(long acceptance_check) {
		this.acceptance_check = acceptance_check;
	}
	public String getAcceptance_doctor() {
		return acceptance_doctor;
	}
	public void setAcceptance_doctor(String acceptance_doctor) {
		this.acceptance_doctor = acceptance_doctor;
	}
	public String getAcceptance_date() {
		return acceptance_date;
	}
	public void setAcceptance_date(String acceptance_date) {
		this.acceptance_date = acceptance_date;
	}
	public String getFinal_doctor() {
		return final_doctor;
	}
	public void setFinal_doctor(String final_doctor) {
		this.final_doctor = final_doctor;
	}
	public String getExam_status() {
		return exam_status;
	}
	public void setExam_status(String exam_status) {
		this.exam_status = exam_status;
	}
	public String getApprove_status() {
		return approve_status;
	}
	public void setApprove_status(String approve_status) {
		this.approve_status = approve_status;
	}
	public String getCheck_time() {
		return check_time;
	}
	public void setCheck_time(String check_time) {
		this.check_time = check_time;
	}
	public String getExam_guidance() {
		return exam_guidance;
	}
	public void setExam_guidance(String exam_guidance) {
		this.exam_guidance = exam_guidance;
	}
	public String getFinal_status() {
		return final_status;
	}
	public void setFinal_status(String final_status) {
		this.final_status = final_status;
	}
	public String getFinal_date() {
		return final_date;
	}
	public void setFinal_date(String final_date) {
		this.final_date = final_date;
	}
	public String getCensoring_status() {
		return censoring_status;
	}
	public void setCensoring_status(String censoring_status) {
		this.censoring_status = censoring_status;
	}
	
}
