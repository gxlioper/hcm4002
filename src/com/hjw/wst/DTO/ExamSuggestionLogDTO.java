package com.hjw.wst.DTO;

import java.util.Date;

import javax.persistence.Column;

public class ExamSuggestionLogDTO  implements java.io.Serializable{

	private static final long serialVersionUID = -97502163798576023L;
	private long id;
	
    private String exam_num;
	
    private String notices;
	
    private String resource;
	
    private long apptype;
	
    private long creater;
	
	private Date create_date;
	
    private long updater;
	
	private Date update_date;
	
	private String chi_name;
	
	private String persiondate;

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

	public String getNotices() {
		return notices;
	}

	public void setNotices(String notices) {
		this.notices = notices;
	}

	public String getResource() {
		return resource;
	}

	public void setResource(String resource) {
		this.resource = resource;
	}

	public long getApptype() {
		return apptype;
	}

	public void setApptype(long apptype) {
		this.apptype = apptype;
	}

	public long getCreater() {
		return creater;
	}

	public void setCreater(long creater) {
		this.creater = creater;
	}

	public Date getCreate_date() {
		return create_date;
	}

	public void setCreate_date(Date create_date) {
		this.create_date = create_date;
	}

	public long getUpdater() {
		return updater;
	}

	public void setUpdater(long updater) {
		this.updater = updater;
	}

	public Date getUpdate_date() {
		return update_date;
	}

	public void setUpdate_date(Date update_date) {
		this.update_date = update_date;
	}

	public String getChi_name() {
		return chi_name;
	}

	public void setChi_name(String chi_name) {
		this.chi_name = chi_name;
	}

	public String getPersiondate() {
		return persiondate;
	}

	public void setPersiondate(String persiondate) {
		this.persiondate = persiondate;
	}
	
	
}
