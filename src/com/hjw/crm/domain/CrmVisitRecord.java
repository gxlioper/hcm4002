package com.hjw.crm.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="crm_visit_record")
public class CrmVisitRecord {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
	@Column(name = "id", unique = true, nullable = false,length = 50)
	//id
	private String id;
	@Column(name = "arch_num", nullable = false,length = 45)
	//档案号
	private String arch_num;
	@Column(name = "customer_feedback", nullable = false,length = 500)
	//客户反馈信息
	private String customer_feedback;
	@Column(name = "health_suggest", nullable = false,length = 500)
	//健康建议
	private String health_suggest;
	@Column(name = "visit_type", nullable = false)
	//回访类型
	private String visit_type;
	@Column(name = "visit_doctor_id", nullable = false)
	//回访医生id
	private Long visit_doctor_id;
	@Column(name = "visit_date", nullable = false)
	//回访时间
	private Date visit_date;
	@Column(name = "visit_num", nullable = false)
	//健康计划编码
	private String visit_num;
	@Column(name="visit_notices")
	private String visit_notices;//回访内容描述
	
	@Column(name="exam_num")
	private String exam_num;
	
	@Column(name="tactics_detail_id")
	private long tactics_detail_id;//对应策略细项id
	
	@Column(name="actual_doctor_id")//实际回访医生
	private long actual_doctor_id;
	
	@Column(name="actual_date")//实际回访时间
	private Date actual_date;
	
	@Column(name="record_status")//回访状态  0未完成 1 已完成 
	private String  record_status;
	
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
	public Date getVisit_date() {
		return visit_date;
	}
	public void setVisit_date(Date visit_date) {
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
	public String getVisit_notices() {
		return visit_notices;
	}
	public void setVisit_notices(String visit_notices) {
		this.visit_notices = visit_notices;
	}
	public long getTactics_detail_id() {
		return tactics_detail_id;
	}
	public void setTactics_detail_id(long tactics_detail_id) {
		this.tactics_detail_id = tactics_detail_id;
	}
	
	public long getActual_doctor_id() {
		return actual_doctor_id;
	}
	public Date getActual_date() {
		return actual_date;
	}
	public void setActual_doctor_id(long actual_doctor_id) {
		this.actual_doctor_id = actual_doctor_id;
	}
	public void setActual_date(Date actual_date) {
		this.actual_date = actual_date;
	}
	public String getRecord_status() {
		return record_status;
	}
	public void setRecord_status(String record_status) {
		this.record_status = record_status;
	}
	@Override
	public String toString() {
		return "CrmVisitRecord [id=" + id + ", arch_num=" + arch_num + ", customer_feedback=" + customer_feedback
				+ ", health_suggest=" + health_suggest + ", visit_type=" + visit_type + ", visit_doctor_id="
				+ visit_doctor_id + ", visit_date=" + visit_date + ", visit_num=" + visit_num + ", visit_notices="
				+ visit_notices + ", exam_num=" + exam_num + ", tactics_detail_id=" + tactics_detail_id + "]";
	}
	
}
