package com.hjw.wst.model;

import java.util.Date;

public class SchedulingModel implements java.io.Serializable{
	
	private static final long serialVersionUID = -97502163798576023L;
	private int id;
	private int user_id;
	private String user_name;
	private String dep_name;
	private String working_date;
	private String working_type;
	private String user_id_s;
	private String notes_date;
	private String notes_content;
	private int creater;
	private Date create_time;
	private int updater;
	private Date update_time;
	private long m_id;
	private long y_id;
	private long wk_id;
	private long n_id;
	
	public long getY_id() {
		return y_id;
	}

	public void setY_id(long y_id) {
		this.y_id = y_id;
	}

	public long getN_id() {
		return n_id;
	}

	public void setN_id(long n_id) {
		this.n_id = n_id;
	}

	public long getWk_id() {
		return wk_id;
	}

	public void setWk_id(long wk_id) {
		this.wk_id = wk_id;
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

	public long getM_id() {
		return m_id;
	}

	public void setM_id(long m_id) {
		this.m_id = m_id;
	}

	public SchedulingModel() {
		// TODO Auto-generated constructor stub
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getDep_name() {
		return dep_name;
	}

	public void setDep_name(String dep_name) {
		this.dep_name = dep_name;
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

	public String getUser_id_s() {
		return user_id_s;
	}

	public void setUser_id_s(String user_id_s) {
		this.user_id_s = user_id_s;
	}

	public int getCreater() {
		return creater;
	}

	public void setCreater(int creater) {
		this.creater = creater;
	}

	public Date getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}

	public int getUpdater() {
		return updater;
	}

	public void setUpdater(int updater) {
		this.updater = updater;
	}

	public Date getUpdate_time() {
		return update_time;
	}

	public void setUpdate_time(Date update_time) {
		this.update_time = update_time;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	
}
