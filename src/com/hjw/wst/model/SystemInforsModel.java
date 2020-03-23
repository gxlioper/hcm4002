package com.hjw.wst.model;

import java.util.Date;


public class SystemInforsModel implements java.io.Serializable {

	private static final long serialVersionUID = -97502163798576023L;
	
	private long id;//站内表ID
	
	private String inform_content;//通知内容
	 
	private String valid_date;//有效时间
	 
	private String is_active;//是否启用 

  	private long creater; 
 	 
  	private Date create_time; 

  	private long updater; 
  	 
  	private Date update_time; 
  	
  	private String startDate;
  	
  	private String endDate;
  	
	private String firstTime;
  	
  	private String lastTime;
  	
  	private String ids;
  	
  	private long user_id;
  	private long informs_id;
  	private long role_id;
  	private String r_ids;
  	
    public String getR_ids() {
		return r_ids;
	}
	public void setR_ids(String r_ids) {
		this.r_ids = r_ids;
	}
	
	public long getRole_id() {
		return role_id;
	}
	public void setRole_id(long role_id) {
		this.role_id = role_id;
	}
	public String getFirstTime() {
		return firstTime;
	}
	public void setFirstTime(String firstTime) {
		this.firstTime = firstTime;
	}
	public String getLastTime() {
		return lastTime;
	}
	public void setLastTime(String lastTime) {
		this.lastTime = lastTime;
	}
	public long getUser_id() {
		return user_id;
	}
	public void setUser_id(long user_id) {
		this.user_id = user_id;
	}
	public long getInforms_id() {
		return informs_id;
	}
	public void setInforms_id(long informs_id) {
		this.informs_id = informs_id;
	}
	public SystemInforsModel() {
		// TODO Auto-generated constructor stub
	}
	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public String getStartDate() {
		return startDate;
	}


	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}


	public String getEndDate() {
		return endDate;
	}


	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}


	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
	}


	public String getInform_content() {
		return inform_content;
	}


	public void setInform_content(String inform_content) {
		this.inform_content = inform_content;
	}


	public String getValid_date() {
		return valid_date;
	}


	public void setValid_date(String valid_date) {
		this.valid_date = valid_date;
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

}
