package com.hjw.wst.domain;



import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;


@Entity
@Table(name="crm_sms_send")
//健康计划表
public class CrmSmsSend {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
	@Column(name = "id", unique = true, nullable = false,length = 50)
	//id
	private String id;
	
	@Column(name="template_id")
	private String template_id;

	@Column(name="arch_num")
	private String arch_num;

	@Column(name="sms_note")
	private String sms_note;
	
	@Column(name="sms_phone")
	private String sms_phone;
	
	@Column(name="sms_date")
	private Date sms_date;
	
	@Column(name="send_user")
	private long send_user;
	
	@Column(name="sms_status")
	private int sms_status;
	
	@Column(name="sms_amount")
	private double sms_amount;
	
	@Column(name="sms_type")
	private int sms_type;
	
	@Column(name="user_id")
	private long user_id;
	
	@Column(name="sms_time")
	private Date sms_time;
	
	@Column(name="user_type")
	private int user_type;
	
	@Column(name="sms_batch")
	private String sms_batch;
	
	@Column(name="sms_fal_notice")
	private String sms_fal_notice;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTemplate_id() {
		return template_id;
	}

	public void setTemplate_id(String template_id) {
		this.template_id = template_id;
	}

	public String getArch_num() {
		return arch_num;
	}

	public void setArch_num(String arch_num) {
		this.arch_num = arch_num;
	}

	public String getSms_note() {
		return sms_note;
	}

	public void setSms_note(String sms_note) {
		this.sms_note = sms_note;
	}

	public String getSms_phone() {
		return sms_phone;
	}

	public void setSms_phone(String sms_phone) {
		this.sms_phone = sms_phone;
	}

	public Date getSms_date() {
		return sms_date;
	}

	public void setSms_date(Date sms_date) {
		this.sms_date = sms_date;
	}

	public long getSend_user() {
		return send_user;
	}

	public void setSend_user(long send_user) {
		this.send_user = send_user;
	}

	public int getSms_status() {
		return sms_status;
	}

	public void setSms_status(int sms_status) {
		this.sms_status = sms_status;
	}

	public double getSms_amount() {
		return sms_amount;
	}

	public void setSms_amount(double sms_amount) {
		this.sms_amount = sms_amount;
	}

	public int getSms_type() {
		return sms_type;
	}

	public void setSms_type(int sms_type) {
		this.sms_type = sms_type;
	}

	public long getUser_id() {
		return user_id;
	}

	public void setUser_id(long user_id) {
		this.user_id = user_id;
	}

	public Date getSms_time() {
		return sms_time;
	}

	public void setSms_time(Date sms_time) {
		this.sms_time = sms_time;
	}

	public int getUser_type() {
		return user_type;
	}

	public void setUser_type(int user_type) {
		this.user_type = user_type;
	}

	public String getSms_batch() {
		return sms_batch;
	}

	public void setSms_batch(String sms_batch) {
		this.sms_batch = sms_batch;
	}

	public String getSms_fal_notice() {
		return sms_fal_notice;
	}

	public void setSms_fal_notice(String sms_fal_notice) {
		this.sms_fal_notice = sms_fal_notice;
	}
		    
}
