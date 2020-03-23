package com.hjw.wst.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 
 * @Title: 火箭蛙体检管理系统
 * @Package com.hjw.wst.domain
 * @Description: 疾病逻辑
 * @author: zr
 * @date: 2016年12月7日 上午9:24:35
 * @version V2.0.0.0
 */
@Entity
@Table(name = "disease_logic")
public class DiseaseLogic implements java.io.Serializable {

	private static final long serialVersionUID = 6785996553946508095L;

	@Id
	@GenericGenerator(name = "idGenerator", strategy = "identity")
	@GeneratedValue(generator = "idGenerator")
	private long id;
	
	@Column(name = "disease_id")
	private long disease_id;

	@Column(name = "logic_num")
	private String logic_num;

	@Column(name = "logic_name")
	private String logic_name;

	@Column(name = "logic_type")
	private String logic_type;

	@Column(name = "sex")
	private String sex;

	@Column(name = "isActive")
	private String isActive;

	@Column(name = "creater")
	private long creater;

	@Column(name = "create_time")
	private Date create_time;

	@Column(name = "updater")
	private long updater;

	@Column(name = "update_time")
	private Date update_time;

	@Column(name = "critical_flag")
	private long critical_flag;
	
	@Column(name = "age_min")
	private String age_min;
	
	@Column(name = "age_max")
	private String age_max;

	
	
	public String getAge_min() {
		return age_min;
	}

	public void setAge_min(String age_min) {
		this.age_min = age_min;
	}

	public String getAge_max() {
		return age_max;
	}

	public void setAge_max(String age_max) {
		this.age_max = age_max;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getDisease_id() {
		return disease_id;
	}

	public void setDisease_id(long disease_id) {
		this.disease_id = disease_id;
	}

	public String getLogic_num() {
		return logic_num;
	}

	public void setLogic_num(String logic_num) {
		this.logic_num = logic_num;
	}

	public String getLogic_name() {
		return logic_name;
	}

	public void setLogic_name(String logic_name) {
		this.logic_name = logic_name;
	}

	public String getLogic_type() {
		return logic_type;
	}

	public void setLogic_type(String logic_type) {
		this.logic_type = logic_type;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
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

	public long getCritical_flag() {
		return critical_flag;
	}

	public void setCritical_flag(long critical_flag) {
		this.critical_flag = critical_flag;
	}

	
}
