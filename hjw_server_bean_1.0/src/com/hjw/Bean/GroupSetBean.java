package com.hjw.Bean;

import java.util.ArrayList;
import java.util.List;

public class GroupSetBean implements java.io.Serializable {
	 private static final long serialVersionUID = -97502163798576023L;

		private long id;

		private long group_id;
		
		private long exam_set_id;
		
		private double discount;
		
		private double amount;
		
		private String isActive;
		
		private String final_exam_date;
		
		private long creater;
		
		private String create_time;
		
		private long updater;
		
		private String update_time;	

        private long company_id;

        private String set_num;

        private String  set_name;

        private String set_pinyin;

        private String sex;
 
        private double set_discount;

        private double set_amount;

        private String survey_minScore;

        private String survey_maxScore;

        private String disease_name;

        private String is_Active;
        private List<SetChargingItemBean>  chargingList= new ArrayList<SetChargingItemBean>();  
	    

		public List<SetChargingItemBean> getChargingList() {
			return chargingList;
		}

		public void setChargingList(List<SetChargingItemBean> chargingList) {
			this.chargingList = chargingList;
		}

		public long getGroup_id() {
			return group_id;
		}

		public void setGroup_id(long group_id) {
			this.group_id = group_id;
		}

		public long getExam_set_id() {
			return exam_set_id;
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

		public String getSurvey_minScore() {
			return survey_minScore;
		}

		public void setSurvey_minScore(String survey_minScore) {
			this.survey_minScore = survey_minScore;
		}

		public String getSurvey_maxScore() {
			return survey_maxScore;
		}

		public void setSurvey_maxScore(String survey_maxScore) {
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

		public void setExam_set_id(long exam_set_id) {
			this.exam_set_id = exam_set_id;
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

		public long getId() {
			return id;
		}

		public void setId(long id) {
			this.id = id;
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

	}