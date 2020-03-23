package com.hjw.crm.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "temporary_customer_info")
public class TemporaryCustomerInfo {
	@Id
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
	@Column(name = "id", unique = true, nullable = false,length = 50)
	private String id;
	@Column(name="user_name")
	private String user_name;
	@Column(name="id_num")
	private String id_num;
	@Column(name="sex")
	private String sex;
	@Column(name="birthday")
	private String birthday;
	@Column(name="nation")
	private String nation;
	@Column(name="phone")
	private String phone;
	@Column(name="address")
	private String address;
	@Column(name="email")
	private String email;
	@Column(name="flag")
	private long flag;
	@Column(name="level")
	private long level;
	@Column(name="integral")
	private long integral;
	@Column(name="notices")
	private String notices;
	
	public String getNotices() {
		return notices;
	}
	public void setNotices(String notices) {
		this.notices = notices;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
	public long getFlag() {
		return flag;
	}
	public void setFlag(long flag) {
		this.flag = flag;
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
