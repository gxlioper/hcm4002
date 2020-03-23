package com.hjw.wst.DTO;

public class ExamSummaryRejectDTO {

	private String arch_num;
	private String exam_num;
	private long examinfo_id;
	private String user_name;
	private String sex;
	private long age;
	private String join_date;
	private String final_date;
	private String final_doctor;
	private String exam_type;
	private String exam_types;
	private String set_name;
	private String company;
	private long freeze;
	
	private String reject_doctor;
	private String reject_time;
	private String reject_context;
	private long done_status;
	private String done_statuss;
	private String done_time;
	public String getArch_num() {
		return arch_num;
	}
	public void setArch_num(String arch_num) {
		this.arch_num = arch_num;
	}
	public String getExam_num() {
		return exam_num;
	}
	public void setExam_num(String exam_num) {
		this.exam_num = exam_num;
	}
	public long getExaminfo_id() {
		return examinfo_id;
	}
	public void setExaminfo_id(long examinfo_id) {
		this.examinfo_id = examinfo_id;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public long getAge() {
		return age;
	}
	public void setAge(long age) {
		this.age = age;
	}
	public String getJoin_date() {
		return join_date;
	}
	public void setJoin_date(String join_date) {
		this.join_date = join_date;
	}
	public String getFinal_date() {
		return final_date;
	}
	public void setFinal_date(String final_date) {
		this.final_date = final_date;
	}
	public String getFinal_doctor() {
		return final_doctor;
	}
	public void setFinal_doctor(String final_doctor) {
		this.final_doctor = final_doctor;
	}
	public String getExam_type() {
		return exam_type;
	}
	public void setExam_type(String exam_type) {
		this.exam_type = exam_type;
		if("G".equals(exam_type)){
			this.exam_types = "个检";
		}else{
			this.exam_types = "团检";
		}
	}
	public String getExam_types() {
		return exam_types;
	}
	public void setExam_types(String exam_types) {
		this.exam_types = exam_types;
	}
	public String getSet_name() {
		return set_name;
	}
	public void setSet_name(String set_name) {
		this.set_name = set_name;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getReject_doctor() {
		return reject_doctor;
	}
	public void setReject_doctor(String reject_doctor) {
		this.reject_doctor = reject_doctor;
	}
	public String getReject_time() {
		return reject_time;
	}
	public void setReject_time(String reject_time) {
		this.reject_time = reject_time;
	}
	public String getReject_context() {
		return reject_context;
	}
	public void setReject_context(String reject_context) {
		this.reject_context = reject_context;
	}
	public long getDone_status() {
		return done_status;
	}
	public void setDone_status(long done_status) {
		this.done_status = done_status;
		if(1 == done_status){
			this.done_statuss = "已处理";
		}else{
			this.done_statuss = "未处理";
		}
	}
	public String getDone_statuss() {
		return done_statuss;
	}
	public void setDone_statuss(String done_statuss) {
		this.done_statuss = done_statuss;
	}
	public String getDone_time() {
		return done_time;
	}
	public void setDone_time(String done_time) {
		this.done_time = done_time;
	}
	public long getFreeze() {
		return freeze;
	}
	public void setFreeze(long freeze) {
		this.freeze = freeze;
	}
}
