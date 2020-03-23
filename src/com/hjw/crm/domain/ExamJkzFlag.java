package com.hjw.crm.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
@Entity
@Table(name="exam_jiankangzheng")
public class ExamJkzFlag {
	@Id
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
	@Column(name = "id", unique = true, nullable = false,length = 50)
	private String id;
	@Column(name="exam_num")//体检好
	private String exam_num;
	@Column(name="examflag")//是否上传
	private String examflag;
	@Column(name="create_time")//创建日期
	private Date create_time;
	@Column(name="sffz")//是否发证
	private String sffz;
	@Column(name="fzrq")//发证日期
	private Date fzrq;
	@Column(name="yxq")//有效期
	private Date yxq;
	@Column(name="djczy")//等级操作员
	private String djczy;
	@Column(name="dyrq")//打印日期
	private Date dyrq;
	@Column(name="sfdy")//是否打印
	private String sfdy;
	@Column(name="scrq")//上传日期
	private Date scrq;
	@Column(name="dyczy")//打印操作员
	private String dyczy;
	@Column(name="health_no")//健康证号
	private String health_no;
	
	public String getSffz() {
		return sffz;
	}
	public void setSffz(String sffz) {
		this.sffz = sffz;
	}
	public Date getFzrq() {
		return fzrq;
	}
	public void setFzrq(Date fzrq) {
		this.fzrq = fzrq;
	}
	public Date getYxq() {
		return yxq;
	}
	public void setYxq(Date yxq) {
		this.yxq = yxq;
	}
	public String getDjczy() {
		return djczy;
	}
	public void setDjczy(String djczy) {
		this.djczy = djczy;
	}
	public Date getDyrq() {
		return dyrq;
	}
	public void setDyrq(Date dyrq) {
		this.dyrq = dyrq;
	}
	public String getSfdy() {
		return sfdy;
	}
	public void setSfdy(String sfdy) {
		this.sfdy = sfdy;
	}
	public Date getScrq() {
		return scrq;
	}
	public void setScrq(Date scrq) {
		this.scrq = scrq;
	}
	public String getDyczy() {
		return dyczy;
	}
	public void setDyczy(String dyczy) {
		this.dyczy = dyczy;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getExam_num() {
		return exam_num;
	}
	public void setExam_num(String exam_num) {
		this.exam_num = exam_num;
	}
	public String getExamflag() {
		return examflag;
	}
	public void setExamflag(String examflag) {
		this.examflag = examflag;
	}
	public Date getCreate_time() {
		return create_time;
	}
	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}
	public String getHealth_no() {
		return health_no;
	}
	public void setHealth_no(String health_no) {
		this.health_no = health_no;
	}
}
