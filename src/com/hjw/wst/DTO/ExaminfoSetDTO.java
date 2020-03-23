package com.hjw.wst.DTO;

public class ExaminfoSetDTO implements java.io.Serializable {
	 private static final long serialVersionUID = -97502163798576023L;

		private long id;

		private long examinfo_id;

		private long exam_set_id;

		private String exam_indicator;

		private double discount;

		private double amount;

		private String isActive;

		private String final_exam_date;

		private long is_new_added;

		private long creater;

		private String create_time;

		private long updater;

		private String update_time;
		
		private String set_num;
		
		private String set_name;
		
		private String set_pinyin;
		
		private String sex;
		
		private double set_discount;
		
		private double set_amount;	
		
		private String app_type="1";//1 表示普通体检，2表示职业病体检	
	    private String app_typename="";
	    
	    private String exam_num;
	    private String exam_indicators;
	    
	    public String getExam_num() {
			return exam_num;
		}

    public String getExam_indicators() {
        return exam_indicators;
    }

    public void setExam_indicators(String exam_indicators) {
        this.exam_indicators = exam_indicators;
    }

    public void setExam_num(String exam_num) {
			this.exam_num = exam_num;
		}
		public String getApp_type() {
			return app_type;
		}

		public void setApp_type(String app_type) {
			this.app_type = app_type;
			if("1".equals(this.app_type)){
				this.setApp_typename("普通");
			}else if("2".equals(this.app_type)){
				this.setApp_typename("职业病");
			}else{
				this.setApp_typename("普通");
			}		
		}
		public String getApp_typename() {
			return app_typename;
		}

		public void setApp_typename(String app_typename) {
			this.app_typename = app_typename;
		}

	    public String getSet_num() {
			return set_num;
		}

		public void setSet_num(String set_num) {
			this.set_num = set_num;
		}

		public String getSet_name() {
			return set_name;
		}

		public void setSet_name(String set_name) {
			this.set_name = set_name;
		}

		public String getSet_pinyin() {
			return set_pinyin;
		}

		public void setSet_pinyin(String set_pinyin) {
			this.set_pinyin = set_pinyin;
		}

		public String getSex() {
			return sex;
		}

		public void setSex(String sex) {
			this.sex = sex;
		}

		public double getSet_discount() {
			return set_discount;
		}

		public void setSet_discount(double set_discount) {
			this.set_discount = set_discount;
		}

		public double getSet_amount() {
			return set_amount;
		}

		public void setSet_amount(double set_amount) {
			this.set_amount = set_amount;
		}

		public String getCreate_time() {
			return create_time;
		}

		public String getUpdate_time() {
			return update_time;
		}

		public long getId() {
			return id;
		}

		public void setId(long id) {
			this.id = id;
		}


		public long getExaminfo_id() {
			return examinfo_id;
		}

		public void setExaminfo_id(long examinfo_id) {
			this.examinfo_id = examinfo_id;
		}

		public long getExam_set_id() {
			return exam_set_id;
		}

		public void setExam_set_id(long exam_set_id) {
			this.exam_set_id = exam_set_id;
		}

		public String getExam_indicator() {
			return exam_indicator;
		}

		public void setExam_indicator(String exam_indicator) {
			this.exam_indicator = exam_indicator;
		}

		public double getDiscount() {
			return discount;
		}

		public void setDiscount(double discount) {
			this.discount = discount;
		}

		public double getAmount() {
			return amount;
		}

		public void setAmount(double amount) {
			this.amount = amount;
		}

		public String getIsActive() {
			return isActive;
		}

		public void setIsActive(String isActive) {
			this.isActive = isActive;
		}

		public String getFinal_exam_date() {
			return final_exam_date;
		}

		public void setFinal_exam_date(String final_exam_date) {
			this.final_exam_date = final_exam_date;
		}

		public long getIs_new_added() {
			return is_new_added;
		}

		public void setIs_new_added(long is_new_added) {
			this.is_new_added = is_new_added;
		}

		public void setCreate_time(String create_time) {
			this.create_time = create_time;
		}

		public void setUpdate_time(String update_time) {
			this.update_time = update_time;
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
	
	}