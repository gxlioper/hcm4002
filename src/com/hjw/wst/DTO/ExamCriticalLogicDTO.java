package com.hjw.wst.DTO;



public class ExamCriticalLogicDTO{
	
	private long id;
	private long critical_class_parent_id;
	private long critical_class_id;
	private long critical_class_level;
	private String isActive;
	private long creater;
	private String create_time;
	private long updater;
	private String update_time;
	private String disease_num;//疾病编码
	private String critical_suggestion;//危急值建议
	private String sex;
	private	int age_min;
	private	int age_max;
	
	
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	
	public int getAge_min() {
		return age_min;
	}
	public void setAge_min(int age_min) {
		this.age_min = age_min;
	}
	public int getAge_max() {
		return age_max;
	}
	public void setAge_max(int age_max) {
		this.age_max = age_max;
	}
	public String getDisease_num() {
		return disease_num;
	}
	public void setDisease_num(String disease_num) {
		this.disease_num = disease_num;
	}
	public String getCritical_suggestion() {
		return critical_suggestion;
	}
	public void setCritical_suggestion(String critical_suggestion) {
		this.critical_suggestion = critical_suggestion;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getCritical_class_parent_id() {
		return critical_class_parent_id;
	}
	public void setCritical_class_parent_id(long critical_class_parent_id) {
		this.critical_class_parent_id = critical_class_parent_id;
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
	public String getIsActive() {
		return isActive;
	}
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}
	public long getCreater() {
		return creater;
	}
	public void setCreater(long creater) {
		this.creater = creater;
	}
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	public long getUpdater() {
		return updater;
	}
	public void setUpdater(long updater) {
		this.updater = updater;
	}
	public String getUpdate_time() {
		return update_time;
	}
	public void setUpdate_time(String update_time) {
		this.update_time = update_time;
	}
	
	
	
}
