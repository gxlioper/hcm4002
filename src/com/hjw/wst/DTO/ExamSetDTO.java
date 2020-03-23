package com.hjw.wst.DTO;


/**
 * 
     * @Title:  火箭蛙体检管理系统   
     * @Package com.hjw.wst.DTO   
     * @Description:体检套餐  
     * @author: zhangrui    
     * @date:   2016年10月10日 上午10:13:13   
     * @version V2.0.0.0
 */
public class ExamSetDTO implements java.io.Serializable {
	 private static final long serialVersionUID = -97502163798576023L;

		private long id;

        private long company_id;

        private String set_num;

        private String  set_name;

        private String set_pinyin;

        private String sex;

        private double set_discount;

        private double set_amount;
        
        private double set_amountq;

        private double survey_minScore;

        private double survey_maxScore;

        private String disease_name;

        private String is_Active;

		private long creater;

		private long updater;

		private String creater_name;
		
		private String create_time;
		
		private String create_times;
		
		private String update_name;
		
		private String update_time;
		
		private String update_times;
		
		private String com_name;
		
		private double price;
		
		private int isSynchro; //是否同步
		
		private String startStop;
		
		private String exam_num;
		
		private String item_code;

		private String charging_item_code;
		private long itemnum;
		private double discount;
		private double amount;
		private double item_amount;
		private String exam_type;
		private long settreeid;
		private String center_num;
		private long app_type;
		private String exam_indicators;
		

		public long getApp_type() {
			return app_type;
		}

		public void setApp_type(long app_type) {
			this.app_type = app_type;
		}
	    public String getExam_type() {
			return exam_type;
		}

		public void setExam_type(String exam_type) {
			this.exam_type = exam_type;
		}

		public long getSettreeid() {
			return settreeid;
		}

		public void setSettreeid(long settreeid) {
			this.settreeid = settreeid;
		}

		public String getItem_code() {
			return item_code;
		}

		public void setItem_code(String item_code) {
			this.item_code = item_code;
		}

		public String getCharging_item_code() {
			return charging_item_code;
		}

		public void setCharging_item_code(String charging_item_code) {
			this.charging_item_code = charging_item_code;
		}

		public long getItemnum() {
			return itemnum;
		}

		public void setItemnum(long itemnum) {
			this.itemnum = itemnum;
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

		public double getItem_amount() {
			return item_amount;
		}

		public void setItem_amount(double item_amount) {
			this.item_amount = item_amount;
		}

		public String getExam_num() {
			return exam_num;
		}

		public void setExam_num(String exam_num) {
			this.exam_num = exam_num;
		}
		

		public int getIsSynchro() {
			return isSynchro;
		}

		public void setIsSynchro(int isSynchro) {
			this.isSynchro = isSynchro;
		}

		public long getId() {
			return id;
		}

		public void setId(long id) {
			this.id = id;
		}

		public long getCompany_id() {
			return company_id;
		}

		public void setCompany_id(long company_id) {
			this.company_id = company_id;
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

		public double getSet_amountq() {
			return set_amountq;
		}

		public void setSet_amountq(double set_amountq) {
			this.set_amountq = set_amountq;
		}

		public double getSurvey_minScore() {
			return survey_minScore;
		}

		public void setSurvey_minScore(double survey_minScore) {
			this.survey_minScore = survey_minScore;
		}

		public double getSurvey_maxScore() {
			return survey_maxScore;
		}

		public void setSurvey_maxScore(double survey_maxScore) {
			this.survey_maxScore = survey_maxScore;
		}

		public String getDisease_name() {
			return disease_name;
		}

		public void setDisease_name(String disease_name) {
			this.disease_name = disease_name;
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

		public long getUpdater() {
			return updater;
		}

		public void setUpdater(long updater) {
			this.updater = updater;
		}

		public String getCreater_name() {
			return creater_name;
		}

		public void setCreater_name(String creater_name) {
			this.creater_name = creater_name;
		}

		public String getCreate_time() {
			return create_time;
		}

		public void setCreate_time(String create_time) {
			this.create_time = create_time;
		}

		public String getCreate_times() {
			return create_times;
		}

		public void setCreate_times(String create_times) {
			this.create_times = create_times;
		}

		public String getUpdate_name() {
			return update_name;
		}

		public void setUpdate_name(String update_name) {
			this.update_name = update_name;
		}

		public String getUpdate_time() {
			return update_time;
		}

		public void setUpdate_time(String update_time) {
			this.update_time = update_time;
		}

		public String getUpdate_times() {
			return update_times;
		}

		public void setUpdate_times(String update_times) {
			this.update_times = update_times;
		}

		public String getCom_name() {
			return com_name;
		}

		public void setCom_name(String com_name) {
			this.com_name = com_name;
		}

		public double getPrice() {
			return price;
		}

		public void setPrice(double price) {
			this.price = price;
		}

		public String getStartStop() {
			return startStop;
		}

		public void setStartStop(String startStop) {
			this.startStop = startStop;
		}

		public String getCenter_num() {
			return center_num;
		}

		public void setCenter_num(String center_num) {
			this.center_num = center_num;
		}

		public String getExam_indicators() {
			return exam_indicators;
		}

		public void setExam_indicators(String exam_indicators) {
			this.exam_indicators = exam_indicators;
		}


		
		
}