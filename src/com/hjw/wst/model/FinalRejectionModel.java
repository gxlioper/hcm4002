package com.hjw.wst.model;

import java.util.Date;

public class FinalRejectionModel  implements java.io.Serializable {
	private static final long serialVersionUID = -97502163798576023L;
	private    long  id;
	private    String  reject_context;
	private    String  is_Active;
	private    long  creater;
	private    String  create_time;
	private    long  updater;
	private    String  update_time;
	
	public FinalRejectionModel() {
		// TODO Auto-generated constructor stub
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getReject_context() {
		return reject_context;
	}

	public void setReject_context(String reject_context) {
		this.reject_context = reject_context;
	}

	public String getIs_Active() {
		return is_Active;
	}

	public void setIs_Active(String is_Active) {
		this.is_Active = is_Active;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
