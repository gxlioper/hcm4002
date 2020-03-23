package com.hjw.wst.DTO;

public class ProcPacsResult {
	private long pacs_id;
	private String pacs_req_code="";//   varchar(20),  --申请单编号（必填）
    private String exam_num="";//      varchar(20),  --体检号
    private String old_exam_num="";//      varchar(20),  --体检号
    private String check_date="";//    varchar(20),  --检查时间（必填）
    private String check_doct="";//    varchar(20),  --检查医生（必填）
    private String audit_date="";//    varchar(20),  --审核时间（必填）
    private String audit_doct="";//    varchar(20),  --审核医生（必填）
    private String exam_result="";//      varchar(1000), --诊断结果（必填）
    private String exam_desc="";//   varchar(2000),    --影像表现(可空)
    private String bodyPart="";//   varchar(200),     --检查部位(可空)
    private String examMethod="";// varchar(500),     --检查方法(可空)
    private String img_file="";//   varchar(2000)     --图像文件(多个用;隔开，文件格式：/pacs_img/ET/2016-10-27/T16A270010/T16A270010.jpg)*/
    private String pacs_name="";
	public String getOld_exam_num() {
		return old_exam_num;
	}
	public void setOld_exam_num(String old_exam_num) {
		this.old_exam_num = old_exam_num;
	}
	public long getPacs_id() {
		return pacs_id;
	}
	public void setPacs_id(long pacs_id) {
		this.pacs_id = pacs_id;
	}
	public String getPacs_req_code() {
		return pacs_req_code;
	}
	public void setPacs_req_code(String pacs_req_code) {
		this.pacs_req_code = pacs_req_code;
	}
	public String getExam_num() {
		return exam_num;
	}
	public void setExam_num(String exam_num) {
		this.exam_num = exam_num;
	}
	public String getCheck_date() {
		return check_date;
	}
	public void setCheck_date(String check_date) {
		this.check_date = check_date;
	}
	public String getCheck_doct() {
		return check_doct;
	}
	public void setCheck_doct(String check_doct) {
		this.check_doct = check_doct;
	}
	public String getAudit_date() {
		return audit_date;
	}
	public void setAudit_date(String audit_date) {
		this.audit_date = audit_date;
	}
	public String getAudit_doct() {
		return audit_doct;
	}
	public void setAudit_doct(String audit_doct) {
		this.audit_doct = audit_doct;
	}
	public String getExam_result() {
		return exam_result;
	}
	public void setExam_result(String exam_result) {
		this.exam_result = exam_result;
	}
	public String getExam_desc() {
		return exam_desc;
	}
	public void setExam_desc(String exam_desc) {
		this.exam_desc = exam_desc;
	}
	public String getBodyPart() {
		return bodyPart;
	}
	public void setBodyPart(String bodyPart) {
		bodyPart = bodyPart;
	}
	public String getExamMethod() {
		return examMethod;
	}
	public void setExamMethod(String examMethod) {
		this.examMethod = examMethod;
	}
	public String getImg_file() {
		return img_file;
	}
	public void setImg_file(String img_file) {
		this.img_file = img_file;
	}
	public String getPacs_name() {
		return pacs_name;
	}
	public void setPacs_name(String pacs_name) {
		this.pacs_name = pacs_name;
	}
	
}
