package com.hjw.wst.model;

import java.util.Date;
													
public class DataDictionaryModel implements java.io.Serializable{
	
	private static final long serialVersionUID = -97502163798576023L;
	private long id;
	private String data_type;
	private String data_code;
	private String data_name;
	private String remark;
	private String isActive;
	private long creater;
	private Date create_time;
	private long updater;
	private Date update_time;
	private int seq_code;
	private String data_code_children;
	private String data_class;
	private long data_type_id;
	private String center_common;
	
	private String center_name;
	private String config_key; 
	private String config_value; 
	private String is_active; 
	private String common; 
	private String center_type; 
	
	
	
	public String getCenter_name() {
		return center_name;
	}
	public void setCenter_name(String center_name) {
		this.center_name = center_name;
	}
	public String getConfig_key() {
		return config_key;
	}
	public void setConfig_key(String config_key) {
		this.config_key = config_key;
	}
	public String getConfig_value() {
		return config_value;
	}
	public void setConfig_value(String config_value) {
		this.config_value = config_value;
	}
	public String getIs_active() {
		return is_active;
	}
	public void setIs_active(String is_active) {
		this.is_active = is_active;
	}
	public String getCommon() {
		return common;
	}
	public void setCommon(String common) {
		this.common = common;
	}
	public String getCenter_type() {
		return center_type;
	}
	public void setCenter_type(String center_type) {
		this.center_type = center_type;
	}
	
	
	public long getData_type_id() {
		return data_type_id;
	}
	public void setData_type_id(long data_type_id) {
		this.data_type_id = data_type_id;
	}
	public String getCenter_common() {
		return center_common;
	}
	public void setCenter_common(String center_common) {
		this.center_common = center_common;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getData_type() {
		return data_type;
	}
	public void setData_type(String data_type) {
		this.data_type = data_type;
	}
	public String getData_code() {
		return data_code;
	}
	public void setData_code(String data_code) {
		this.data_code = data_code;
	}
	public String getData_name() {
		return data_name;
	}
	public void setData_name(String data_name) {
		this.data_name = data_name;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
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
	public int getSeq_code() {
		return seq_code;
	}
	public void setSeq_code(int seq_code) {
		this.seq_code = seq_code;
	}
	public String getData_code_children() {
		return data_code_children;
	}
	public void setData_code_children(String data_code_children) {
		this.data_code_children = data_code_children;
	}
	public String getData_class() {
		return data_class;
	}
	public void setData_class(String data_class) {
		this.data_class = data_class;
	}
	
}
