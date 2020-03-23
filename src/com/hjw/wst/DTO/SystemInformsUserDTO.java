package com.hjw.wst.DTO;

import java.util.Date;




public class SystemInformsUserDTO implements java.io.Serializable {
	 private static final long serialVersionUID = -97502163798576023L;

	    private int id;
		
		private int informs_id;//站内id
		 
		private int user_id;//用户id
		 
		private int reader_flag;

	  	private String  creater; 
	 	 
	  	private Date create_time; 

	  	private String  updater; 
	  	 
	  	private Date update_time;
	  	
	  	private String chi_name;
	  	
	  	private String selected;
	  	
	  	private long r_id;
	  	private long role_id;
	  	
	  	public long getRole_id() {
			return role_id;
		}

		public void setRole_id(long role_id) {
			this.role_id = role_id;
		}

		private String role_name;

		public long getR_id() {
			return r_id;
		}

		public void setR_id(long r_id) {
			this.r_id = r_id;
		}

		public String getRole_name() {
			return role_name;
		}

		public void setRole_name(String role_name) {
			this.role_name = role_name;
		}

		public String getSelected() {
			return selected;
		}

		public void setSelected(String selected) {
			this.selected = selected;
		}

		public String getChi_name() {
			return chi_name;
		}

		public void setChi_name(String chi_name) {
			this.chi_name = chi_name;
		}

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public int getInforms_id() {
			return informs_id;
		}

		public void setInforms_id(int informs_id) {
			this.informs_id = informs_id;
		}

		public int getUser_id() {
			return user_id;
		}

		public void setUser_id(int user_id) {
			this.user_id = user_id;
		}

		public int getReader_flag() {
			return reader_flag;
		}

		public void setReader_flag(int reader_flag) {
			this.reader_flag = reader_flag;
		}

		public String getCreater() {
			return creater;
		}

		public void setCreater(String creater) {
			this.creater = creater;
		}

		public Date getCreate_time() {
			return create_time;
		}

		public void setCreate_time(Date create_time) {
			this.create_time = create_time;
		}

		public String getUpdater() {
			return updater;
		}

		public void setUpdater(String updater) {
			this.updater = updater;
		}

		public Date getUpdate_time() {
			return update_time;
		}

		public void setUpdate_time(Date update_time) {
			this.update_time = update_time;
		} 
	
	}