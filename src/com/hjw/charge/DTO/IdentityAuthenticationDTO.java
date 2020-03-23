package com.hjw.charge.DTO;

public class IdentityAuthenticationDTO {
	private long id;
	
	private String  flag;//校验标志（0-未通过 1-已通过）	number(1)	y	
	private String  cause;	//未通过原因说明	varchar2(500)	n	
	
	private String  ginseng_administrative;//参保地行政区划	varchar2(6)	y
	
	private String  ginseng_administrative_name;//参保地行政区划名称	varchar2(50)	y	
	
	private String  nam_entity;//单位名称	varchar2(100)	n	
	
	private String  pat_no;//社会保障卡号	varchar2(20)	y	
	
	private String  document_type;//证件类型	varchar2(3)	y	参见编码附件
	
	private String  document_name;//证件类型名称	varchar2(50)	y	
	
	private String  document_number;//证件号码（社会保障号）	varchar2(18)	y	
	
	private String  personal_code;//个人管理码	varchar2(20)	y	地市唯一标识码（id0000）
	
	private String  name;//姓名	varchar2(50)	y	
	
	private String  sex;//性别	varchar2(3)	y	
	
	private String  sex_name;//性别名称	varchar2(10)	y	
	
	private String  date_birth;//出生日期	number(8)	y	
	
	private String  medical_identification;//医疗救助认定身份	varchar2(3)	n	
	
	private String  medical_identification_name;//医疗救助认定身份名称	varchar2(50)	n	
	
	private String  personal_account_balance;   //个人账户余额	number(16,2)	y	

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getCause() {
		return cause;
	}

	public void setCause(String cause) {
		this.cause = cause;
	}

	public String getGinseng_administrative() {
		return ginseng_administrative;
	}

	public void setGinseng_administrative(String ginseng_administrative) {
		this.ginseng_administrative = ginseng_administrative;
	}

	public String getGinseng_administrative_name() {
		return ginseng_administrative_name;
	}

	public void setGinseng_administrative_name(String ginseng_administrative_name) {
		this.ginseng_administrative_name = ginseng_administrative_name;
	}

	public String getNam_entity() {
		return nam_entity;
	}

	public void setNam_entity(String nam_entity) {
		this.nam_entity = nam_entity;
	}

	public String getPat_no() {
		return pat_no;
	}

	public void setPat_no(String pat_no) {
		this.pat_no = pat_no;
	}

	public String getDocument_type() {
		return document_type;
	}

	public void setDocument_type(String document_type) {
		this.document_type = document_type;
	}

	public String getDocument_name() {
		return document_name;
	}

	public void setDocument_name(String document_name) {
		this.document_name = document_name;
	}

	public String getDocument_number() {
		return document_number;
	}

	public void setDocument_number(String document_number) {
		this.document_number = document_number;
	}

	public String getPersonal_code() {
		return personal_code;
	}

	public void setPersonal_code(String personal_code) {
		this.personal_code = personal_code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getSex_name() {
		return sex_name;
	}

	public void setSex_name(String sex_name) {
		this.sex_name = sex_name;
	}

	public String getDate_birth() {
		return date_birth;
	}

	public void setDate_birth(String date_birth) {
		this.date_birth = date_birth;
	}

	public String getMedical_identification() {
		return medical_identification;
	}

	public void setMedical_identification(String medical_identification) {
		this.medical_identification = medical_identification;
	}

	public String getMedical_identification_name() {
		return medical_identification_name;
	}

	public void setMedical_identification_name(String medical_identification_name) {
		this.medical_identification_name = medical_identification_name;
	}

	public String getPersonal_account_balance() {
		return personal_account_balance;
	}

	public void setPersonal_account_balance(String personal_account_balance) {
		this.personal_account_balance = personal_account_balance;
	}
	
	
	
}
