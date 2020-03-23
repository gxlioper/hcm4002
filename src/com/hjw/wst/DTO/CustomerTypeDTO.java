package com.hjw.wst.DTO;

public class CustomerTypeDTO implements java.io.Serializable {
	 private static final long serialVersionUID = -97502163798576023L;

	 private long id;
	 private String type_name;
	 private String type_code;
	 private String type_comment;
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
	public String getType_name() {
		return type_name;
	}
	public void setType_name(String type_name) {
		this.type_name = type_name;
	}
	public String getType_code() {
		return type_code;
	}
	public void setType_code(String type_code) {
		this.type_code = type_code;
	}
	public String getType_comment() {
		return type_comment;
	}
	public void setType_comment(String type_comment) {
		this.type_comment = type_comment;
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