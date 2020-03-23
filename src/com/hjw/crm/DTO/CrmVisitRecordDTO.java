package com.hjw.crm.DTO;

import java.util.Date;

public class CrmVisitRecordDTO {
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
	//姓名
	private String name;
	//医生姓名
	private String doctorname;
	
	private String actual_doctor_name;//实际回访医生
	
	private String actual_date;//实际回访时间
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDoctorname() {
		return doctorname;
	}
	public void setDoctorname(String doctorname) {
		this.doctorname = doctorname;
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
		this.visit_type=visit_type;
		if("1".equals(this.visit_type)){
			this.setVisit_type("医生主动与体检者电话沟通");
		}else if("2".equals(this.visit_type)){
			this.setVisit_type("体检者主动与医生电话沟通");
		}else if("3".equals(this.visit_type)){
			this.setVisit_type("通过app进行实时沟通");
		}else if("4".equals(this.visit_type)){
			this.setVisit_type("通过微信进行实时沟通");
		}else if("0".equals(this.visit_type)){
			this.setVisit_type("");
		}
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
	public String getActual_doctor_name() {
		return actual_doctor_name;
	}
	public String getActual_date() {
		return actual_date;
	}
	public void setActual_doctor_name(String actual_doctor_name) {
		this.actual_doctor_name = actual_doctor_name;
	}
	public void setActual_date(String actual_date) {
		this.actual_date = actual_date;
	}
	
}
