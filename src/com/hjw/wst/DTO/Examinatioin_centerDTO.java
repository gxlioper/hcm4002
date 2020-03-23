package com.hjw.wst.DTO;


public class Examinatioin_centerDTO {
	private long id;
	private String center_num;
	private String center_name;
	private String is_active;
	private String is_active_y;
	private String creater;
	private String create_time;
	private String updater;
	private String update_time;
	private String photo_function_status;
	private String photo_function_status_s;
	private long limit_count;
	private long _parentId;
	
	public long get_parentId() {
		return _parentId;
	}
	public void set_parentId(long _parentId) {
		this._parentId = _parentId;
	}
	public Examinatioin_centerDTO() {
		// TODO Auto-generated constructor stub
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
		if("Y".equals(is_active)){
			this.setIs_active_y("是");
		}else{
			this.setIs_active_y("否");
		}
	}

	public String getIs_active_y() {
		return is_active_y;
	}

	public void setIs_active_y(String is_active_y) {
		this.is_active_y = is_active_y;
	}

	public String getPhoto_function_status_s() {
		return photo_function_status_s;
	}

	public void setPhoto_function_status_s(String photo_function_status_s) {
		this.photo_function_status_s = photo_function_status_s;
	}

	public String getCreater() {
		return creater;
	}

	public void setCreater(String creater) {
		this.creater = creater;
	}

	public String getCreate_time() {
		return create_time;
	}

	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}

	public String getUpdater() {
		return updater;
	}

	public void setUpdater(String updater) {
		this.updater = updater;
	}

	public String getUpdate_time() {
		return update_time;
	}

	public void setUpdate_time(String update_time) {
		this.update_time = update_time;
	}

	public String getPhoto_function_status() {
		return photo_function_status;
	}

	public void setPhoto_function_status(String photo_function_status) {
		this.photo_function_status = photo_function_status;
		if("1".equals(photo_function_status)){
			this.setPhoto_function_status_s("是");
		}else{
			this.setPhoto_function_status_s("否");
		}
	}


	
}