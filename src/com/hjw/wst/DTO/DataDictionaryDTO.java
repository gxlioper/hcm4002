package com.hjw.wst.DTO;


public class DataDictionaryDTO implements java.io.Serializable {
	 private static final long serialVersionUID = -97502163798576023L;

		private long id;

        private String data_type;

        private String data_code;

        private String data_name;

        private String remark;

        private String isActive;
        
        private String isActive_i;

        private int seq_code;

	    private String creater;

	    private String create_time;

	    private String updater;

	    private String update_time;	 
	    
	    private String data_code_children;
	    
	    private String data_class;
	    private String data_classs;

	    public long getId() {
			return id;
		}

		public void setId(long id) {
			this.id = id;
		}		

		public String getData_type() {
			return data_type;
		}

		public void setData_type(String data_type) {
			this.data_type = data_type;
		}

		public String getData_code() {
			return data_code;
		}

		public void setData_code(String data_code) {
			this.data_code = data_code;
		}

		public String getData_name() {
			return data_name;
		}

		public void setData_name(String data_name) {
			this.data_name = data_name;
		}

		public String getRemark() {
			return remark;
		}

		public void setRemark(String remark) {
			this.remark = remark;
		}

		public String getIsActive() {
			return isActive;
		}

		public void setIsActive(String isActive) {
			this.isActive = isActive;
			if("Y".equals(isActive)){
				this.setIsActive_i("是");
			}else{
				this.setIsActive_i("否");
			}
		}

		public String getIsActive_i() {
			return isActive_i;
		}

		public void setIsActive_i(String isActive_i) {
			this.isActive_i = isActive_i;
		}

		public int getSeq_code() {
			return seq_code;
		}

		public void setSeq_code(int seq_code) {
			this.seq_code = seq_code;
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

		public String getData_code_children() {
			return data_code_children;
		}

		public void setData_code_children(String data_code_children) {
			this.data_code_children = data_code_children;
		}

		public String getData_class() {
			return data_class;
		}

		public void setData_class(String data_class) {
			this.data_class = data_class;
			if("0".equals(data_class)){
				this.data_classs = "共用体检系统";
			}else if("1".equals(data_class)){
				this.data_classs = "健康体检系统";
			}else if("2".equals(data_class)){
				this.data_classs = "职业病体检系统";
			}
		}

		public String getData_classs() {
			return data_classs;
		}

		public void setData_classs(String data_classs) {
			this.data_classs = data_classs;
		}

	}