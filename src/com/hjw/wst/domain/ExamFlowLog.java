package com.hjw.wst.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "exam_flow_log")
public class ExamFlowLog implements java.io.Serializable {
	 private static final long serialVersionUID = -97502163798576023L;
	 
	 @Id
	 @GenericGenerator(name = "idGenerator", strategy = "identity")
	 @GeneratedValue(generator = "idGenerator")
	private long id;
	 @Column(name = "exam_num")
	 private String exam_num;
	 @Column(name = "flow_code")
	private String flow_code="";
	 @Column(name = "sendcreater")
	private long sendcreater;
	 @Column(name = "senddate")
	private Date senddate;
	 @Column(name = "acccreater")
	private long acccreater;
	 @Column(name = "flow_type")
	 private int flow_type;
	 @Column(name = "remark")
	private String remark;
	 @Column(name = "notes")
	private String notes;
	 @Column(name = "center_num")
	private String center_num;	 
	 
	public int getFlow_type() {
		return flow_type;
	}
	public void setFlow_type(int flow_type) {
		this.flow_type = flow_type;
	}
	public String getCenter_num() {
		return center_num;
	}
	public void setCenter_num(String center_num) {
		this.center_num = center_num;
	}
	public String getExam_num() {
		return exam_num;
	}
	public void setExam_num(String exam_num) {
		this.exam_num = exam_num;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getFlow_code() {
		return flow_code;
	}
	public void setFlow_code(String flow_code) {
		this.flow_code = flow_code;
	}
	public long getSendcreater() {
		return sendcreater;
	}
	public void setSendcreater(long sendcreater) {
		this.sendcreater = sendcreater;
	}
	public Date getSenddate() {
		return senddate;
	}
	public void setSenddate(Date senddate) {
		this.senddate = senddate;
	}
	public long getAcccreater() {
		return acccreater;
	}
	public void setAcccreater(long acccreater) {
		this.acccreater = acccreater;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	 
	
	}