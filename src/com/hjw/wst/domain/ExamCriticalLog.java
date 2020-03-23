package com.hjw.wst.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
@Entity
@Table(name = "exam_Critical_log")
public class ExamCriticalLog {
	@Id
	@GenericGenerator(name = "idGenerator", strategy = "identity")
	@GeneratedValue(generator = "idGenerator")
	private long id;
	
	@Column(name = "exam_Critical_id")
	private long exam_Critical_id;
	
	@Column(name = "create_time")
	private Date create_time;
	
	@Column(name = "creater")
	private long creater;
	
	@Column(name = "note")
	private String note;
	
	@Column(name = "isActive")
	private String isActive;
	
	@Column(name = "status")
	private int status;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getExam_Critical_id() {
		return exam_Critical_id;
	}

	public void setExam_Critical_id(long exam_Critical_id) {
		this.exam_Critical_id = exam_Critical_id;
	}

	public Date getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}

	public long getCreater() {
		return creater;
	}

	public void setCreater(long creater) {
		this.creater = creater;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	
	
}
