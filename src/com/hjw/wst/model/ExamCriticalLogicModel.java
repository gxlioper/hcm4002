package com.hjw.wst.model;

import java.io.Serializable;

public class ExamCriticalLogicModel implements Serializable{
	
	private long id;
	private long critical_class_parent_id;
	private long critical_class_id;
	private long critical_class_level;
	private String isActive;
	private long creater;
	private String create_time;
	private long updater;
	private String update_time;
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
