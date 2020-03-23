package com.hjw.wst.DTO;
import java.util.Date;
public class ExamCriticalLogDTO {
	
	private long id;
	
	private long exam_Critical_id;
	
	private Date create_time;
	
	private long creater;
	
	private String note;
	
	private String isActive;

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
	
}
