package com.hjw.wst.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "disease_logic_single")
public class DiseaseLogicSingle implements java.io.Serializable {
	private static final long serialVersionUID = -97502163798576023L;
	
	@Id
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
    @Column(name = "id", unique = true, nullable = false,length = 50)
	private String id;
	
	@Column(name="disease_num")
	private String disease_num;
	
	@Column(name="logic_name")
	private String logic_name;
	
	@Column(name="item_code")
	private String item_code;
	
	@Column(name="isActive")
	private String isActive;
	
	@Column(name="sex")
	private String sex;
	
	@Column(name="age_max")
	private long age_max;
	
	@Column(name="age_min")
	private long age_min;
	
	@Column(name="logic_class")
	private long logic_class;
	
	@Column(name="creater")
	private long creater;
	
	@Column(name="create_time")
	private Date create_time;
	
	@Column(name="updater")
	private long updater;
	
	@Column(name="update_time")
	private Date update_time;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDisease_num() {
		return disease_num;
	}

	public void setDisease_num(String disease_num) {
		this.disease_num = disease_num;
	}

	public String getLogic_name() {
		return logic_name;
	}

	public void setLogic_name(String logic_name) {
		this.logic_name = logic_name;
	}

	public String getItem_code() {
		return item_code;
	}

	public void setItem_code(String item_code) {
		this.item_code = item_code;
	}

	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public long getAge_max() {
		return age_max;
	}

	public void setAge_max(long age_max) {
		this.age_max = age_max;
	}

	public long getAge_min() {
		return age_min;
	}

	public void setAge_min(long age_min) {
		this.age_min = age_min;
	}

	public long getLogic_class() {
		return logic_class;
	}

	public void setLogic_class(long logic_class) {
		this.logic_class = logic_class;
	}

	public long getCreater() {
		return creater;
	}

	public void setCreater(long creater) {
		this.creater = creater;
	}

	public Date getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}

	public long getUpdater() {
		return updater;
	}

	public void setUpdater(long updater) {
		this.updater = updater;
	}

	public Date getUpdate_time() {
		return update_time;
	}

	public void setUpdate_time(Date update_time) {
		this.update_time = update_time;
	}
}
