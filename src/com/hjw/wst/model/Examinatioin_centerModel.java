package com.hjw.wst.model;

import java.util.Date;

public class Examinatioin_centerModel {
	private long id;
	private String center_num;
	private String center_name;
	private String is_active;
	private long creater;
	private Date create_time;
	private long updater;
	private Date update_time;
	private String photo_function_status;
	private long limit_count;
	private String limit_count_s;
	private String parent_id;
	
	public String getParent_id() {
		return parent_id;
	}

	public void setParent_id(String parent_id) {
		this.parent_id = parent_id;
	}

	public String getLimit_count_s() {
		return limit_count_s;
	}

	public void setLimit_count_s(String limit_count_s) {
		this.limit_count_s = limit_count_s;
	}

	public long getLimit_count() {
		return limit_count;
	}

	public void setLimit_count(long limit_count) {
		this.limit_count = limit_count;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCenter_num() {
		return center_num;
	}

	public void setCenter_num(String center_num) {
		this.center_num = center_num;
	}

	public String getCenter_name() {
		return center_name;
	}

	public void setCenter_name(String center_name) {
		this.center_name = center_name;
	}

	public String getIs_active() {
		return is_active;
	}

	public void setIs_active(String is_active) {
		this.is_active = is_active;
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

	public String getPhoto_function_status() {
		return photo_function_status;
	}

	public void setPhoto_function_status(String photo_function_status) {
		this.photo_function_status = photo_function_status;
	}

}