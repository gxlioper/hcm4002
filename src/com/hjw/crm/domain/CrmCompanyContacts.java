package com.hjw.crm.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="crm_company_contacts")
public class CrmCompanyContacts implements java.io.Serializable{
	
	private static final long serialVersionUID = -97502163798576023L;

	@Id
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
	@Column(name = "id", unique = true, nullable = false,length = 50)
	private String id;
	
	@Column(name="company_id")
	private long company_id;//单位ID
	
	@Column(name="contacts_name")
	private String contacts_name;//联系人姓名

	@Column(name="position")
	private String position;//职务
	
	@Column(name="important_level")
	private String important_level;//重要级别
	
	@Column(name="phone")
	private String phone;//手机
	
	@Column(name="telephone")
	private String telephone;//办公电话
	
	@Column(name="email")
	private String email;//电子邮件
	
	@Column(name="id_num")
	private String id_num;//身份证号
	
	@Column(name="personal_interests")
	private String personal_interests;//个人爱好
	
	@Column(name="remarke")
	private String remarke;//备注

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public long getCompany_id() {
		return company_id;
	}

	public void setCompany_id(long company_id) {
		this.company_id = company_id;
	}

	public String getContacts_name() {
		return contacts_name;
	}

	public void setContacts_name(String contacts_name) {
		this.contacts_name = contacts_name;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getImportant_level() {
		return important_level;
	}

	public void setImportant_level(String important_level) {
		this.important_level = important_level;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getId_num() {
		return id_num;
	}

	public void setId_num(String id_num) {
		this.id_num = id_num;
	}

	public String getPersonal_interests() {
		return personal_interests;
	}

	public void setPersonal_interests(String personal_interests) {
		this.personal_interests = personal_interests;
	}

	public String getRemarke() {
		return remarke;
	}

	public void setRemarke(String remarke) {
		this.remarke = remarke;
	}
}
