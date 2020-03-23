package com.hjw.wst.DTO;




public class SystemInformsDTO implements java.io.Serializable {
	 private static final long serialVersionUID = -97502163798576023L;

	    private long id;
		
		private String inform_content;//通知内容
		 
		private String valid_date;//有效时间
		 
		private String is_active;//是否启用 

	  	private String  creater; 
	 	 
	  	private String create_time; 

	  	private String  updater; 
	  	 
	  	private String  update_time; 
	  	
	  	private String startDate;
	  	
	  	private String endDate;
	  	
	  	private String chi_name;
	  	private String selected;
	  	private long user_id;	
	  	

		public long getUser_id() {
			return user_id;
		}

		public void setUser_id(long user_id) {
			this.user_id = user_id;
		}

		public String getChi_name() {
			return chi_name;
		}

		public String getSelected() {
			return selected;
		}

		public void setSelected(String selected) {
			this.selected = selected;
		}

		public void setChi_name(String chi_name) {
			this.chi_name = chi_name;
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
		 
    
	}