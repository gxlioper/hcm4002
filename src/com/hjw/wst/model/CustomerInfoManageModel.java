package com.hjw.wst.model;

import java.util.Date;


public class CustomerInfoManageModel implements java.io.Serializable{
	
	private static final long serialVersionUID = -97502163798576023L;
	private long id;
	private String arch_num;
	private String user_name;
	private String id_num;
	private String sex;
	private String birthday;
	private String nation;
	private String phone;
	private String address;
	private String email;
	private String is_Active;
	private Long creater;
	private Date create_time;
	private Long updater;
	private Date update_time;
	private String membership_card;
	private String medical_insurance_card;
	private String chkItem;
	private String time1;
	private String time2;
	private String exam_num;
	private long customer_id;
	
	public long getCustomer_id() {
		return customer_id;
	}

	public void setCustomer_id(long customer_id) {
		this.customer_id = customer_id;
	}

	public String getExam_num() {
		return exam_num;
	}

	public void setExam_num(String exam_num) {
		this.exam_num = exam_num;
	}

	public String getChkItem() {
		return chkItem;
	}

	public void setChkItem(String chkItem) {
		this.chkItem = chkItem;
	}

	public String getMembership_card() {
		return membership_card;
	}

	public void setMembership_card(String membership_card) {
		this.membership_card = membership_card;
	}

	public String getMedical_insurance_card() {
		return medical_insurance_card;
	}

	public void setMedical_insurance_card(String medical_insurance_card) {
		this.medical_insurance_card = medical_insurance_card;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getArch_num() {
		return arch_num;
	}

	public void setArch_num(String arch_num) {
		this.arch_num = arch_num;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getId_num() {
		return id_num;
	}

	public void setId_num(String id_num) {
		this.id_num = id_num;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getNation() {
		return nation;
	}

	public void setNation(String nation) {
		this.nation = nation;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getIs_Active() {
		return is_Active;
	}

	public void setIs_Active(String is_Active) {
		this.is_Active = is_Active;
	}

	public Long getCreater() {
		return creater;
	}

	public void setCreater(Long creater) {
		this.creater = creater;
	}

	public Date getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}

	public Long getUpdater() {
		return updater;
	}

	public void setUpdater(Long updater) {
		this.updater = updater;
	}

	public Date getUpdate_time() {
		return update_time;
	}

	public void setUpdate_time(Date update_time) {
		this.update_time = update_time;
	}

	public String getTime1() {
		return time1;
	}

	public void setTime1(String time1) {
		this.time1 = time1;
	}

	public String getTime2() {
		return time2;
	}

	public void setTime2(String time2) {
		this.time2 = time2;
	}

	public CustomerInfoManageModel() {
		// TODO Auto-generated constructor stub
	}

}
