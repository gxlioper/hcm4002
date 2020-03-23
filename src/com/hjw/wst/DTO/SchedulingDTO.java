package com.hjw.wst.DTO;


public class SchedulingDTO implements java.io.Serializable {

	private static final long serialVersionUID = -97502163798576023L;
	private long id;
	private long user_id;
	private String chi_name;
	private String working_date;
	private String working_type;
	private long creater;
	private String dep_name;
	private String create_time;
	private long updater;
	private String update_time;
	private String notes_date;
	private String notes_content;
	private long n_id;
	
	
	public long getN_id() {
		return n_id;
	}
	public void setN_id(long n_id) {
		this.n_id = n_id;
	}
	public String getNotes_date() {
		return notes_date;
	}
	public void setNotes_date(String notes_date) {
		this.notes_date = notes_date;
	}
	public String getNotes_content() {
		return notes_content;
	}
	public void setNotes_content(String notes_content) {
		this.notes_content = notes_content;
	}
	public SchedulingDTO() {
		// TODO Auto-generated constructor stub
	}
	public String getDep_name() {
		return dep_name;
	}
	public void setDep_name(String dep_name) {
		this.dep_name = dep_name;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getUser_id() {
		return user_id;
	}
	public String getChi_name() {
		return chi_name;
	}
	public void setChi_name(String chi_name) {
		this.chi_name = chi_name;
	}
	public void setUser_id(long user_id) {
		this.user_id = user_id;
	}
	
	public String getWorking_date() {
		return working_date;
	}
	public void setWorking_date(String working_date) {
		this.working_date = working_date;
	}
	public String getWorking_type() {
		return working_type;
	}
	public void setWorking_type(String working_type) {
		this.working_type = working_type;
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
