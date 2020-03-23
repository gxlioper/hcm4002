package com.hjw.wst.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
/**
 * 总检驳回表
     * @Title:  火箭蛙体检管理系统   
     * @Package com.hjw.wst.domain   
     * @Description:  
     * @author: dq     
     * @date:   2018年1月18日 上午9:04:05   
     * @version V2.0.0.0
 */
@Entity
@Table(name="exam_summary_reject")
public class ExamSummaryReject implements java.io.Serializable {
	private static final long serialVersionUID = -97502163798576023L;
	@Id
	@GenericGenerator(name = "idGenerator", strategy = "identity")
	@GeneratedValue(generator = "idGenerator")
	private long id;

	@Column(name="examinfo_id")
	private long examinfo_id;//体检ID
	
	@Column(name="reject_doctor")
	private long reject_doctor;//驳回医生
	
	@Column(name="reject_time")
	private Date reject_time;//驳回时间
	
	@Column(name="reject_context")
	private String reject_context;//驳回原因
	
	@Column(name="done_status")
	private long done_status;//处理状态
	
	@Column(name="done_doctor")
	private long done_doctor;//处理医生
	
	@Column(name="done_time")
	private Date done_time;//处理时间

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getExaminfo_id() {
		return examinfo_id;
	}

	public void setExaminfo_id(long examinfo_id) {
		this.examinfo_id = examinfo_id;
	}

	public long getReject_doctor() {
		return reject_doctor;
	}

	public void setReject_doctor(long reject_doctor) {
		this.reject_doctor = reject_doctor;
	}

	public Date getReject_time() {
		return reject_time;
	}

	public void setReject_time(Date reject_time) {
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
	}

	public long getDone_doctor() {
		return done_doctor;
	}

	public void setDone_doctor(long done_doctor) {
		this.done_doctor = done_doctor;
	}

	public Date getDone_time() {
		return done_time;
	}

	public void setDone_time(Date done_time) {
		this.done_time = done_time;
	}
}
