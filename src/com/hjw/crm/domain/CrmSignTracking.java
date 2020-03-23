package com.hjw.crm.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
@Entity
@Table(name="crm_sign_tracking")
public class CrmSignTracking {
	@Id
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
	@Column(name = "id", unique = true, nullable = false,length = 50)
	private String id;
	@Column(name="sign_num",nullable=false)
	private String sign_num;
	@Column(name="tracking_date",nullable=false)
	private Date tracking_date;
	@Column(name="contact_name",nullable=false)
	private String contact_name;
	@Column(name="phone",nullable=false)
	private String phone;
	@Column(name="tracking_content",nullable=false)
	private String tracking_content;
	@Column(name="remark")
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
	public Date getTracking_date() {
		return tracking_date;
	}
	public void setTracking_date(Date tracking_date) {
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
