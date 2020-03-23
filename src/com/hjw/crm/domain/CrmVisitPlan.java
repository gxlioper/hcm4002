package com.hjw.crm.domain;



import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;


@Entity
@Table(name="crm_visit_plan")
//健康计划表
public class CrmVisitPlan {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
	@Column(name = "id", unique = true, nullable = false,length = 50)
	//id
	private String id;
	@Column(name="arch_num",nullable=false,length=45)
	//档案号
	private String arch_num;
	@Column(name="plan_doctor_id",nullable=false)
	//计划回访医生id
	private Long plan_doctor_id;
	@Column(name="plan_visit_date",nullable=false)
	//计划回访时间
	private Date plan_visit_date;
	@Column(name="visit_content",nullable=false,length=500)
	//回访内容
	private String visit_content;
	@Column(name="visit_num",nullable=false,length=50)
	//健康计划编码
	private String visit_num;
	@Column(name="visit_status",nullable=false)
	//回访状态：1-计划回访，2-开始回访，3-结束回访
	private String visit_status;
	@Column(name="creater")
	//创建人
	private Long creater;
	@Column(name="create_time")
	//创建时间
	private Date create_time;
	
	@Column(name="exam_num")
	private String exam_num;
	
	@Column(name="visit_important")
	private String visit_important;
	@Column(name="fujianflag")
	private String fujianflag;
	
	@Column(name="tactics_num")
	private String tactics_num ;//对应计划策略编码
	
	public String getFujianflag() {
		return fujianflag;
	}
	public void setFujianflag(String fujianflag) {
		this.fujianflag = fujianflag;
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
	public Long getPlan_doctor_id() {
		return plan_doctor_id;
	}
	public void setPlan_doctor_id(Long plan_doctor_id) {
		this.plan_doctor_id = plan_doctor_id;
	}
	public Date getPlan_visit_date() {
		return plan_visit_date;
	}
	public void setPlan_visit_date(Date plan_visit_date) {
		this.plan_visit_date = plan_visit_date;
	}
	public String getVisit_content() {
		return visit_content;
	}
	public void setVisit_content(String visit_content) {
		this.visit_content = visit_content;
	}
	public String getVisit_num() {
		return visit_num;
	}
	public void setVisit_num(String visit_num) {
		this.visit_num = visit_num;
	}
	public String getVisit_status() {
		return visit_status;
	}
	public void setVisit_status(String visit_status) {
		this.visit_status = visit_status;
	}
	public Long getCreater() {
		return creater;
	}
	public void setCreater(Long creater) {
		this.creater = creater;
	}
	public Date getCreate_time() {
		return create_time;
	}
	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}
	public String getExam_num() {
		return exam_num;
	}
	public void setExam_num(String exam_num) {
		this.exam_num = exam_num;
	}
	public String getVisit_important() {
		return visit_important;
	}
	public void setVisit_important(String visit_important) {
		this.visit_important = visit_important;
	}
	public String getTactics_num() {
		return tactics_num;
	}
	public void setTactics_num(String tactics_num) {
		this.tactics_num = tactics_num;
	}
}
