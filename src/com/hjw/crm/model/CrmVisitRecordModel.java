package com.hjw.crm.model;

import java.util.Date;

public class CrmVisitRecordModel {

	private static final long serialVersionUID = 1L;
	//id
	private String id;
	//档案号
	private String arch_num;
	//客户反馈信息
	private String customer_feedback;
	//健康建议
	private String health_suggest;
	//回访类型
	private String visit_type;
	//回访医生id
	private Long visit_doctor_id;
	//回访时间
	private String visit_date;
	//健康计划编码
	private String visit_num;
	//医生姓名
	private String personname;
	//姓名
	private String name;
	//计划id
	private String planid;
	//开始时间
	private String startTime;
	//结束时间
	private String endTime;
	private String ids;
	private String username;
	private Long plan_doctor_id;
	private String exam_num;
	private String cvr_id;
	private String type;
	private String plan_visit_date;
	
	public Long getPlan_doctor_id() {
		return plan_doctor_id;
	}
	public void setPlan_doctor_id(Long plan_doctor_id) {
		this.plan_doctor_id = plan_doctor_id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getIds() {
		return ids;
	}
	public void setIds(String ids) {
		this.ids = ids;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getPlanid() {
		return planid;
	}
	public void setPlanid(String planid) {
		this.planid = planid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPersonname() {
		return personname;
	}
	public void setPersonname(String personname) {
		this.personname = personname;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getArch_num() {
		return arch_num;
	}
	public void setArch_num(String arch_num) {
		this.arch_num = arch_num;
	}
	public String getCustomer_feedback() {
		return customer_feedback;
	}
	public void setCustomer_feedback(String customer_feedback) {
		this.customer_feedback = customer_feedback;
	}
	public String getHealth_suggest() {
		return health_suggest;
	}
	public void setHealth_suggest(String health_suggest) {
		this.health_suggest = health_suggest;
	}
	public String getVisit_type() {
		return visit_type;
	}
	public void setVisit_type(String visit_type) {
		this.visit_type = visit_type;
	}
	public Long getVisit_doctor_id() {
		return visit_doctor_id;
	}
	public void setVisit_doctor_id(Long visit_doctor_id) {
		this.visit_doctor_id = visit_doctor_id;
	}
	public String getVisit_date() {
		return visit_date;
	}
	public void setVisit_date(String visit_date) {
		this.visit_date = visit_date;
	}
	public String getVisit_num() {
		return visit_num;
	}
	public void setVisit_num(String visit_num) {
		this.visit_num = visit_num;
	}
	public String getExam_num() {
		return exam_num;
	}
	public void setExam_num(String exam_num) {
		this.exam_num = exam_num;
	}
	public String getCvr_id() {
		return cvr_id;
	}
	public void setCvr_id(String cvr_id) {
		this.cvr_id = cvr_id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getPlan_visit_date() {
		return plan_visit_date;
	}
	public void setPlan_visit_date(String plan_visit_date) {
		this.plan_visit_date = plan_visit_date;
	}
}
