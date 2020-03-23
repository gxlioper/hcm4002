package com.hjw.wst.domain;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "customer_info")
public class CustomerInfo implements java.io.Serializable {
	
	private static final long serialVersionUID = -97502163798576023L;
	
	@Id
    @GenericGenerator(name = "idGenerator", strategy = "identity")
	@GeneratedValue(generator = "idGenerator")
	private long id;
	
	@Column(name = "arch_num")
	private String arch_num="";
	
	@Column(name = "user_name")
	private String user_name="";
	
	@Column(name = "id_num")
	private String id_num="";
	
	@Column(name = "sex")
	private String sex="";
	
	@Column(name = "birthday")
	private Date birthday=null;
	
	@Column(name = "nation")
	private String nation="";
	
	@Column(name = "phone")
	private String phone="";
	
	@Column(name = "address")
	private String address="";
	
	@Column(name = "email")
	private String email="";
	
	@Column(name = "is_Active")
	private String is_Active="";
	
	@Column(name = "creater")
	private Long creater;
	
	@Column(name = "create_time")
	private Date create_time=null;
	
	@Column(name = "updater")
	private Long updater;
	
	@Column(name = "update_time")
	private Date update_time=null;
	
	@Column(name = "membership_card")
	private String membership_card;
	
	@Column(name = "medical_insurance_card")
	private String medical_insurance_card;
	
	@Column(name = "flag")
	private String flag;
	
	@Column(name="center_num")
	private String center_num;

	
	public String getCenter_num() {
		return center_num;
	}

	public void setCenter_num(String center_num) {
		this.center_num = center_num;
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

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
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

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
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
}
