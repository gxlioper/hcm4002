package com.hjw.wst.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

/**
 * 
     * @Title:  火箭蛙体检管理系统   
     * @Package com.hjw.wst.domain   
     * @Description:  
     * @author: yangm     
     * @date:   2016年7月4日 上午11:13:46   
     * @version V2.0.0.0
 */

@Entity
@Table(name = "user_usr")

public class WebUserInfo implements java.io.Serializable {
	private static final long serialVersionUID = -97502163798576023L;
	
	@Id
	@GenericGenerator(name = "idGenerator", strategy = "identity")
	@GeneratedValue(generator = "idGenerator")
	private long id;
	
	@Column(name = "exam_center_id")
	private Long exam_center_id;
	
	@Column(name = "chi_Name")
	private String chi_Name;
	
	@Column(name = "log_Name")
	private String log_Name;
	
	@Column(name = "pwd_encrypted")
	private String pwd_encrypted;
	
	@Column(name = "is_active")
	private String is_active;
	
	@Column(name = "email")
	private String email;
	
	@Column(name = "phone_num")
	private String phone_num;
	
	@Column(name = "user_pic_path")
	private String user_pic_path;
	
	@Column(name = "user_signature")
	private String user_signature;
	
	@Column(name = "invoice_num_min")
	private Long invoice_num_min;
	
	@Column(name = "invoice_num_max")
	private Long invoice_num_max;
	
	@Column(name = "discount")
	private String discount;
	
	@Column(name = "creater")
	private long creater;
	
	@Column(name = "create_Time")
	private Date create_Time;
	
	@Column(name = "updater")
	private long updater;

	@Column(name = "update_Time")
	private Date update_Time;
	
	@Column(name = "work_num")
	private String work_num;
	
	@Column(name = "signpicpath")
	private String signpicpath;
	
	@Column(name = "user_notices")
	private String user_notices;
	
	public String getUser_notices() {
		return user_notices;
	}

	public void setUser_notices(String user_notices) {
		this.user_notices = user_notices;
	}

	public String getSignpicpath() {
		return signpicpath;
	}

	public void setSignpicpath(String signpicpath) {
		this.signpicpath = signpicpath;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Long getExam_center_id() {
		return exam_center_id;
	}

	public void setExam_center_id(Long exam_center_id) {
		this.exam_center_id = exam_center_id;
	}

	public String getChi_Name() {
		return chi_Name;
	}

	public void setChi_Name(String chi_Name) {
		this.chi_Name = chi_Name;
	}

	public String getLog_Name() {
		return log_Name;
	}

	public void setLog_Name(String log_Name) {
		this.log_Name = log_Name;
	}

	public String getPwd_encrypted() {
		return pwd_encrypted;
	}

	public void setPwd_encrypted(String pwd_encrypted) {
		this.pwd_encrypted = pwd_encrypted;
	}

	public String getIs_active() {
		return is_active;
	}

	public void setIs_active(String is_active) {
		this.is_active = is_active;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone_num() {
		return phone_num;
	}

	public void setPhone_num(String phone_num) {
		this.phone_num = phone_num;
	}

	public String getUser_pic_path() {
		return user_pic_path;
	}

	public void setUser_pic_path(String user_pic_path) {
		this.user_pic_path = user_pic_path;
	}

	public String getUser_signature() {
		return user_signature;
	}

	public void setUser_signature(String user_signature) {
		this.user_signature = user_signature;
	}

	public Long getInvoice_num_min() {
		return invoice_num_min;
	}

	public void setInvoice_num_min(Long invoice_num_min) {
		this.invoice_num_min = invoice_num_min;
	}

	public Long getInvoice_num_max() {
		return invoice_num_max;
	}

	public void setInvoice_num_max(Long invoice_num_max) {
		this.invoice_num_max = invoice_num_max;
	}

	public String getDiscount() {
		return discount;
	}

	public void setDiscount(String discount) {
		this.discount = discount;
	}

	public long getCreater() {
		return creater;
	}

	public void setCreater(long creater) {
		this.creater = creater;
	}

	public long getUpdater() {
		return updater;
	}

	public void setUpdater(long updater) {
		this.updater = updater;
	}

	public Date getCreate_Time() {
		return create_Time;
	}

	public void setCreate_Time(Date create_Time) {
		this.create_Time = create_Time;
	}

	public Date getUpdate_Time() {
		return update_Time;
	}

	public void setUpdate_Time(Date update_Time) {
		this.update_Time = update_Time;
	}

	public String getWork_num() {
		return work_num;
	}

	public void setWork_num(String work_num) {
		this.work_num = work_num;
	}
}

