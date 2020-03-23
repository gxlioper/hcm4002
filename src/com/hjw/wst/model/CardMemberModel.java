package com.hjw.wst.model;

import java.util.Date;

public class CardMemberModel implements java.io.Serializable {
	
	private static final long serialVersionUID = -97502163798576023L;

	private String id;
	private long customer_id;
	private String arch_num;
	private String user_name;
	private String id_num;
	private String sex;
	private Date birthday;
	private String phone;
	private String photo;
	private String email;
	private String nation;
	private String address;
	private long level;
	private long integral;
	private String arch_nums;
	private String user_names;
	private String archnumber;
	private String level_name;
	
	
	public String getLevel_name() {
		return level_name;
	}
	public void setLevel_name(String level_name) {
		this.level_name = level_name;
	}
	public String getArch_nums() {
		return arch_nums;
	}
	public void setArch_nums(String arch_nums) {
		this.arch_nums = arch_nums;
	}
	public String getUser_names() {
		return user_names;
	}
	public void setUser_names(String user_names) {
		this.user_names = user_names;
	}
	public String getArchnumber() {
		return archnumber;
	}
	public void setArchnumber(String archnumber) {
		this.archnumber = archnumber;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public long getCustomer_id() {
		return customer_id;
	}
	public void setCustomer_id(long customer_id) {
		this.customer_id = customer_id;
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
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getNation() {
		return nation;
	}
	public void setNation(String nation) {
		this.nation = nation;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
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
	public long getLevel() {
		return level;
	}
	public void setLevel(long level) {
		this.level = level;
	}
	public long getIntegral() {
		return integral;
	}
	public void setIntegral(long integral) {
		this.integral = integral;
	}
}
