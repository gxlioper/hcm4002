package com.hjw.wst.DTO;


public class QuestChargingDTO {
	
	 private String exam_num; //体检编号
	 
	 private String item_name; //收费项目名称
	 
	 private String exam_type;//团体/个人
	    
	 private String is_after_pay; //是否后付费
	    
	 private String exam_indicator; //个人/团体付费
	    
	 private String pay_status; //付费状态
	 
	 private String item_code; //收费项目编码
	 
	 private String quest_code; //脑健康
	 
	 private String charging_item_code; //收费项目code
	 

	public String getCharging_item_code() {
		return charging_item_code;
	}

	public void setCharging_item_code(String charging_item_code) {
		this.charging_item_code = charging_item_code;
	}

	public String getQuest_code() {
		return quest_code;
	}

	public void setQuest_code(String quest_code) {
		this.quest_code = quest_code;
	}

	public String getItem_name() {
		return item_name;
	}

	public void setItem_name(String item_name) {
		this.item_name = item_name;
	}

	public String getItem_code() {
		return item_code;
	}

	public void setItem_code(String item_code) {
		this.item_code = item_code;
	}

	public String getExam_num() {
		return exam_num;
	}

	public void setExam_num(String exam_num) {
		this.exam_num = exam_num;
	}

	public String getExam_type() {
		return exam_type;
	}

	public void setExam_type(String exam_type) {
		this.exam_type = exam_type;
	}

	public String getIs_after_pay() {
		return is_after_pay;
	}

	public void setIs_after_pay(String is_after_pay) {
		this.is_after_pay = is_after_pay;
	}

	public String getExam_indicator() {
		return exam_indicator;
	}

	public void setExam_indicator(String exam_indicator) {
		this.exam_indicator = exam_indicator;
	}

	public String getPay_status() {
		return pay_status;
	}

	public void setPay_status(String pay_status) {
		this.pay_status = pay_status;
	}
	 
	 
}
