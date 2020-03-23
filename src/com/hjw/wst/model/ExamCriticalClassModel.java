package com.hjw.wst.model;

import java.io.Serializable;

public class ExamCriticalClassModel implements Serializable{

	/**   
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)   
	 */ 
	private static final long serialVersionUID = 1L;
	
	private long id;
	private String critical_class_name;
	private long critical_class_level = 2L;
	private long parent_id;
	private long creater;
	private String create_time;
	private long seq_code;
	private String remark;
	//危急值子类id
	private long critical_class_id;
	private long critical_class_parent_id;
	private String li;
	
	private String ecl_id;
	private String ids;
	private long critical_class_level_g;
	private String isActive;
	private String disease_num;//疾病编码
	private String critical_suggestion;//危急值建议
	private String disease_name;
	private String disease_type;
	private String disease_type_s;
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

	public String getDisease_type() {
		return disease_type;
	}

	public void setDisease_type(String disease_type) {
		this.disease_type = disease_type;
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

	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	public long getCritical_class_level_g() {
		return critical_class_level_g;
	}

	public void setCritical_class_level_g(long critical_class_level_g) {
		this.critical_class_level_g = critical_class_level_g;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCritical_class_name() {
		return critical_class_name;
	}

	public void setCritical_class_name(String critical_class_name) {
		this.critical_class_name = critical_class_name;
	}

	public long getCritical_class_level() {
		return critical_class_level;
	}

	public void setCritical_class_level(long critical_class_level) {
		this.critical_class_level = critical_class_level;
	}

	public long getParent_id() {
		return parent_id;
	}

	public void setParent_id(long parent_id) {
		this.parent_id = parent_id;
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

	public long getSeq_code() {
		return seq_code;
	}

	public void setSeq_code(long seq_code) {
		this.seq_code = seq_code;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public long getCritical_class_id() {
		return critical_class_id;
	}

	public void setCritical_class_id(long critical_class_id) {
		this.critical_class_id = critical_class_id;
	}

	public long getCritical_class_parent_id() {
		return critical_class_parent_id;
	}

	public void setCritical_class_parent_id(long critical_class_parent_id) {
		this.critical_class_parent_id = critical_class_parent_id;
	}

	public String getLi() {
		return li;
	}

	public void setLi(String li) {
		this.li = li;
	}

	public String getEcl_id() {
		return ecl_id;
	}

	public void setEcl_id(String ecl_id) {
		this.ecl_id = ecl_id;
	}
}
