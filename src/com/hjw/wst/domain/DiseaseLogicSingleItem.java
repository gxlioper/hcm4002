package com.hjw.wst.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="disease_logic_single_item")
public class DiseaseLogicSingleItem implements java.io.Serializable{
	private static final long serialVersionUID = -97502163798576023L;
	
	@Id
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
    @Column(name = "id", unique = true, nullable = false,length = 50)
	private String id;
	
	@Column(name="logic_single_id")
	private String logic_single_id;
	
	@Column(name="logic_item_name")
	private String logic_item_name;
	
	@Column(name="logic_index")
	private long logic_index;
	
	@Column(name="critical_flag")
	private long critical_flag; 
	
	@Column(name="isActive")
	private String isActive;
	
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

	public String getLogic_single_id() {
		return logic_single_id;
	}

	public void setLogic_single_id(String logic_single_id) {
		this.logic_single_id = logic_single_id;
	}

	public String getLogic_item_name() {
		return logic_item_name;
	}

	public void setLogic_item_name(String logic_item_name) {
		this.logic_item_name = logic_item_name;
	}

	public long getLogic_index() {
		return logic_index;
	}

	public void setLogic_index(long logic_index) {
		this.logic_index = logic_index;
	}

	public long getCritical_flag() {
		return critical_flag;
	}

	public void setCritical_flag(long critical_flag) {
		this.critical_flag = critical_flag;
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
}
