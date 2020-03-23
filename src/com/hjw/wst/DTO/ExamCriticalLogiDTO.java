package com.hjw.wst.DTO;

public class ExamCriticalLogiDTO {
	private String id;
	private String isActive;
	private long critical_class_id;
	private long critical_class_level;
	private long critical_class_parent_id;
	private String critical_class_d_name;
	private String critical_class_z_name;
	private String critical_class_level_name;
	private String info;
	private String disease_name;
	private String disease_type;
	private String disease_type_s;
	private String sex;
	private String age_min;
	
	
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getAge_min() {
		return age_min;
	}
	public void setAge_min(String age_min) {
		this.age_min = age_min;
	}
	public String getDisease_type() {
		return disease_type;
	}
	public void setDisease_type(String disease_type) {
		this.disease_type = disease_type;
		if("Y".equals(disease_type)) {
			disease_type_s = "阳性发现";
		} else {
			disease_type_s = "疾病";
		}
	}
	public String getDisease_type_s() {
		return disease_type_s;
	}
	public void setDisease_type_s(String disease_type_s) {
		this.disease_type_s = disease_type_s;
	}
	public String getDisease_name() {
		return disease_name;
	}
	public void setDisease_name(String disease_name) {
		this.disease_name = disease_name;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getIsActive() {
		return isActive;
	}
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}
	public long getCritical_class_id() {
		return critical_class_id;
	}
	public void setCritical_class_id(long critical_class_id) {
		this.critical_class_id = critical_class_id;
	}
	public long getCritical_class_level() {
		return critical_class_level;
	}
	public void setCritical_class_level(long critical_class_level) {
		this.critical_class_level = critical_class_level;
	}
	public long getCritical_class_parent_id() {
		return critical_class_parent_id;
	}
	public void setCritical_class_parent_id(long critical_class_parent_id) {
		this.critical_class_parent_id = critical_class_parent_id;
	}
	public String getCritical_class_d_name() {
		return critical_class_d_name;
	}
	public void setCritical_class_d_name(String critical_class_d_name) {
		this.critical_class_d_name = critical_class_d_name;
	}
	public String getCritical_class_z_name() {
		return critical_class_z_name;
	}
	public void setCritical_class_z_name(String critical_class_z_name) {
		this.critical_class_z_name = critical_class_z_name;
	}
	public String getCritical_class_level_name() {
		return critical_class_level_name;
	}
	public void setCritical_class_level_name(String critical_class_level_name) {
		this.critical_class_level_name = critical_class_level_name;
	}
	

}
