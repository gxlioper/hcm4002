package com.hjw.wst.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="exam_summary_cancel")
public class ExamSummaryCancel implements java.io.Serializable{
	private static final long serialVersionUID = -97502163798576023L;
	@Id
	@GenericGenerator(name = "idGenerator", strategy = "identity")
	@GeneratedValue(generator = "idGenerator")
	private long id;

	@Column(name="exam_num")
	private String exam_num;//体检号
	
	@Column(name="final_status")
	private String final_status;//取消时总检状态
	
	@Column(name="approve_status")
	private String approve_status;//取消时审核状态
	
	@Column(name="censoring_status")
	private String censoring_status;//取消时复审状态
	
	@Column(name="creater")
	private long creater; //操作人
	
	@Column(name="create_time")
	private Date create_time;//操作时间
	
	@Column(name="cancel_type")
	private long cancel_type; //操作类型 1 一键取消，0 一键恢复

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getExam_num() {
		return exam_num;
	}

	public void setExam_num(String exam_num) {
		this.exam_num = exam_num;
	}

	public String getFinal_status() {
		return final_status;
	}

	public void setFinal_status(String final_status) {
		this.final_status = final_status;
	}

	public String getApprove_status() {
		return approve_status;
	}

	public void setApprove_status(String approve_status) {
		this.approve_status = approve_status;
	}

	public String getCensoring_status() {
		return censoring_status;
	}

	public void setCensoring_status(String censoring_status) {
		this.censoring_status = censoring_status;
	}

	public long getCreater() {
		return creater;
	}

	public void setCreater(long creater) {
		this.creater = creater;
	}

	public Date getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}

	public long getCancel_type() {
		return cancel_type;
	}

	public void setCancel_type(long cancel_type) {
		this.cancel_type = cancel_type;
	}
}
