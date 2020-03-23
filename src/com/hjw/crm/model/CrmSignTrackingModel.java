package com.hjw.crm.model;

public class CrmSignTrackingModel {
	private String id;
	private String sign_num;
	private String tracking_date;
	private String contact_name;
	private String phone;
	private String tracking_content;
	private String remark;
	private String start_date;
	private String end_date;
	
	public String getStart_date() {
		return start_date;
	}
	public void setStart_date(String start_date) {
		this.start_date = start_date;
	}
	public String getEnd_date() {
		return end_date;
	}
	public void setEnd_date(String end_date) {
		this.end_date = end_date;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSign_num() {
		return sign_num;
	}
	public void setSign_num(String sign_num) {
		this.sign_num = sign_num;
	}
	public String getTracking_date() {
		return tracking_date;
	}
	public void setTracking_date(String tracking_date) {
		this.tracking_date = tracking_date;
	}
	public String getContact_name() {
		return contact_name;
	}
	public void setContact_name(String contact_name) {
		this.contact_name = contact_name;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getTracking_content() {
		return tracking_content;
	}
	public void setTracking_content(String tracking_content) {
		this.tracking_content = tracking_content;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
}
