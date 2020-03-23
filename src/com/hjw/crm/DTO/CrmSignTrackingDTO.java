package com.hjw.crm.DTO;

public class CrmSignTrackingDTO {
	private String id;
	private String sign_num;
	private String sign_name;
	private String tracking_date;
	private String contact_name;
	private String phone;
	private String tracking_content;
	private String remark;
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
	public String getSign_name() {
		return sign_name;
	}
	public void setSign_name(String sign_name) {
		this.sign_name = sign_name;
	}
}
