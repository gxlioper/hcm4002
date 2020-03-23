package com.hjw.charge.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 
     * @Title:  火箭蛙体检管理系统   (交易流水表)
     * @Package com.hjw.wst.domain   
     * @Description:  
     * @author: dangqi     
     * @date:   2016年7月14日 下午3:39:36   
     * @version V2.0.0.0
 */
@Entity
@Table(name="identity_authentication")
public class InsurancePayerInfoCharge implements java.io.Serializable{

	private static final long serialVersionUID = -97502163798576023L;

	@Id
	@GenericGenerator(name = "idGenerator", strategy = "identity")
	@GeneratedValue(generator = "idGenerator")
	private long id;
	
	@Column(name="flag")
	private String  flag;//校验标志（0-未通过 1-已通过）	number(1)	y	
	@Column(name="cause")
	private String  cause;	//未通过原因说明	varchar2(500)	n	
	
	@Column(name="ginseng_administrative")
	private String  ginseng_administrative;//参保地行政区划	varchar2(6)	y
	
	@Column(name="ginseng_administrative_name")
	private String  ginseng_administrative_name;//参保地行政区划名称	varchar2(50)	y	
	
	@Column(name="nam_entity")
	private String  nam_entity;//单位名称	varchar2(100)	n	
	
	@Column(name="pat_no")
	private String  pat_no;//社会保障卡号	varchar2(20)	y	
	
	@Column(name="document_type")
	private String  document_type;//证件类型	varchar2(3)	y	参见编码附件
	
	@Column(name="document_name")
	private String  document_name;//证件类型名称	varchar2(50)	y	
	
	@Column(name="document_number")
	private String  document_number;//证件号码（社会保障号）	varchar2(18)	y	
	
	@Column(name="personal_code")
	private String  personal_code;//个人管理码	varchar2(20)	y	地市唯一标识码（id0000）
	
	@Column(name="name")
	private String  name;//姓名	varchar2(50)	y	
	
	@Column(name="sex")
	private String  sex;//性别	varchar2(3)	y	
	
	@Column(name="sex_name")
	private String  sex_name;//性别名称	varchar2(10)	y	
	
	@Column(name="date_birth")
	private String  date_birth;//出生日期	number(8)	y	
	
	@Column(name="medical_identification")
	private String  medical_identification;//医疗救助认定身份	varchar2(3)	n	
	
	@Column(name="medical_identification_name")
	private String  medical_identification_name;//医疗救助认定身份名称	varchar2(50)	n	
	
	@Column(name="personal_account_balance")
	private String  personal_account_balance;   //个人账户余额	number(16,2)	y	
	
	@Column(name="plant_type")
	private String  plant_type;//险种类型	varchar2(3)	y	参见编码附件
	
	@Column(name="plant_type_name")
	private String  plant_type_name;//险种类型名称	varchar2(50)	y	
	
	@Column(name="agency_code")
	private String  agency_code;//所属经办机构编码（分中心）	varchar2(8)	y	
	
	@Column(name="agency_code_name")
	private String  agency_code_name;//所属经办机构编码名称	varchar2(50)	y	
	
	@Column(name="treatment_personnel")
	private String  treatment_personnel;//人员待遇类别	varchar2(3)	y	
	
	@Column(name="treatment_personnel_name")
	private String  treatment_personnel_name;//人员待遇类别名称	varchar2(30)	y	
	
	@Column(name="treatment_medicall_name")
	private String  treatment_medicall_name;//医疗待遇状态名称	varchar2(30)	y	
	
	@Column(name="ginseng_protect_date")
	private String  ginseng_protect_date;//参保日期（享受待遇日期）	number(8)	y	
	
	@Column(name="status_indicators")
	private String  status_indicators;//身份指标	varchar2(10)	y	参见编码身份指标附件说明
	
	@Column(name="status_indicators_name")
	private String  status_indicators_name;//身份指标名称	varchar2(50)	y	
	
	@Column(name="identity")
	private String  identity;//身份标志	varchar2(3)	y	y 是，n 否

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

	public String getPlant_type() {
		return plant_type;
	}

	public void setPlant_type(String plant_type) {
		this.plant_type = plant_type;
	}

	public String getPlant_type_name() {
		return plant_type_name;
	}

	public void setPlant_type_name(String plant_type_name) {
		this.plant_type_name = plant_type_name;
	}

	public String getAgency_code() {
		return agency_code;
	}

	public void setAgency_code(String agency_code) {
		this.agency_code = agency_code;
	}

	public String getAgency_code_name() {
		return agency_code_name;
	}

	public void setAgency_code_name(String agency_code_name) {
		this.agency_code_name = agency_code_name;
	}

	public String getTreatment_personnel() {
		return treatment_personnel;
	}

	public void setTreatment_personnel(String treatment_personnel) {
		this.treatment_personnel = treatment_personnel;
	}

	public String getTreatment_personnel_name() {
		return treatment_personnel_name;
	}

	public void setTreatment_personnel_name(String treatment_personnel_name) {
		this.treatment_personnel_name = treatment_personnel_name;
	}

	public String getTreatment_medicall_name() {
		return treatment_medicall_name;
	}

	public void setTreatment_medicall_name(String treatment_medicall_name) {
		this.treatment_medicall_name = treatment_medicall_name;
	}

	public String getGinseng_protect_date() {
		return ginseng_protect_date;
	}

	public void setGinseng_protect_date(String ginseng_protect_date) {
		this.ginseng_protect_date = ginseng_protect_date;
	}

	public String getStatus_indicators() {
		return status_indicators;
	}

	public void setStatus_indicators(String status_indicators) {
		this.status_indicators = status_indicators;
	}

	public String getStatus_indicators_name() {
		return status_indicators_name;
	}

	public void setStatus_indicators_name(String status_indicators_name) {
		this.status_indicators_name = status_indicators_name;
	}

	public String getIdentity() {
		return identity;
	}

	public void setIdentity(String identity) {
		this.identity = identity;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
