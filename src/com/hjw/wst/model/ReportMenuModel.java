package com.hjw.wst.model;

public class ReportMenuModel implements java.io.Serializable {
	 private static final long serialVersionUID = -97502163798576023L;

		private long id;

		private Integer parent_id;

		private String report_name;

        private String report_address;

        private String is_active;

        private long creater;
        
        private String creaters;

        private String create_time;

        private long updater;
        
        private String updaters;

        private String update_time;

        private long seq_code;

        private String report_type;        
        

		public String getCreate_time() {
			return create_time;
		}

		public String getUpdate_time() {
			return update_time;
		}

		public String getCreaters() {
			return creaters;
		}

		public void setCreaters(String creaters) {
			this.creaters = creaters;
		}

		public String getUpdaters() {
			return updaters;
		}

		public void setUpdaters(String updaters) {
			this.updaters = updaters;
		}

		public void setCreate_time(String create_time) {
			this.create_time = create_time;
		}

		public void setUpdate_time(String update_time) {
			this.update_time = update_time;
		}

		public long getId() {
			return id;
		}

		public void setId(long id) {
			this.id = id;
		}

		public Integer getParent_id() {
			return parent_id;
		}

		public void setParent_id(Integer parent_id) {
			this.parent_id = parent_id;
		}

		public String getReport_name() {
			return report_name;
		}

		public void setReport_name(String report_name) {
			this.report_name = report_name;
		}

		public String getReport_address() {
			return report_address;
		}

		public void setReport_address(String report_address) {
			this.report_address = report_address;
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

		public long getUpdater() {
			return updater;
		}

		public void setUpdater(long updater) {
			this.updater = updater;
		}

		public long getSeq_code() {
			return seq_code;
		}

		public void setSeq_code(long seq_code) {
			this.seq_code = seq_code;
		}

		public String getReport_type() {
			return report_type;
		}

		public void setReport_type(String report_type) {
			this.report_type = report_type;
		}  	   

	}