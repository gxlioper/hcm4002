package com.hjw.wst.DTO;

import java.util.Date;

public class FinalRejectionDTO  implements java.io.Serializable  {
	
	private static final long serialVersionUID = -97502163798576023L;
	
	private    long  id;
	private    String  reject_context;
	private    String  is_Active;
	private    String  creater;
	private    String  create_time;
	private    String  updater;
	private    String  update_time;
	
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
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	public String getCreater() {
		return creater;
	}
	public void setCreater(String creater) {
		this.creater = creater;
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
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
