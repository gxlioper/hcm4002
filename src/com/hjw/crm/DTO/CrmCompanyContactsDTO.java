package com.hjw.crm.DTO;

public class CrmCompanyContactsDTO implements java.io.Serializable{
	
	private static final long serialVersionUID = -97502163798576023L;
	private String id;
	private long company_id;//单位ID
	private String contacts_name;//联系人姓名
	private String position;//职务
	private String positions;
	private String important_level;//重要级别
	private String important_levels;
	private String phone;//手机
	private String telephone;//办公电话
	private String email;//电子邮件
	private String id_num;//身份证号
	private String personal_interests;//个人爱好
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
	public String getPositions() {
		return positions;
	}
	public void setPositions(String positions) {
		this.positions = positions;
	}
	public String getImportant_level() {
		return important_level;
	}
	public void setImportant_level(String important_level) {
		this.important_level = important_level;
	}
	public String getImportant_levels() {
		return important_levels;
	}
	public void setImportant_levels(String important_levels) {
		this.important_levels = important_levels;
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
