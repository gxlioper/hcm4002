package com.hjw.wst.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "examinfo_set")
public class ExaminfoSet implements java.io.Serializable {
	 private static final long serialVersionUID = -97502163798576023L;

		@Id
		@GenericGenerator(name = "idGenerator", strategy = "identity")
		@GeneratedValue(generator = "idGenerator")
		private long id;

		@Column(name = "examinfo_id")
		private long examinfo_id;
		
		@Column(name = "exam_set_id")
		private long exam_set_id;
		
		@Column(name = "exam_indicator")
		private String exam_indicator;
		
		@Column(name = "discount")
		private double discount;
		
		@Column(name = "amount")
		private double amount;
		
		@Column(name = "isActive")
		private String isActive;
		
		@Column(name = "final_exam_date")
		private Date final_exam_date;
		
		@Column(name = "is_new_added")
		private long is_new_added;
		
		@Column(name = "creater")
		private long creater;
		
		@Column(name = "create_time")
		private Date create_time;
		
		@Column(name = "updater")
		private long updater;
		
		@Column(name = "update_time")
		private Date update_time;
		
		@Column(name = "app_type")
		private String app_type="1";//1 表示普通体检，2表示职业病体检	
		
		@Column(name="exam_num")
	    private String exam_num;
	    
	    public String getExam_num() {
			return exam_num;
		}

		public void setExam_num(String exam_num) {
			this.exam_num = exam_num;
		}
	    
		public String getApp_type() {
			return app_type;
		}

		public void setApp_type(String app_type) {
			this.app_type = app_type;
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

		public Date getFinal_exam_date() {
			return final_exam_date;
		}

		public void setFinal_exam_date(Date final_exam_date) {
			this.final_exam_date = final_exam_date;
		}

		public long getIs_new_added() {
			return is_new_added;
		}

		public void setIs_new_added(long is_new_added) {
			this.is_new_added = is_new_added;
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

		public Date getCreate_time() {
			return create_time;
		}

		public void setCreate_time(Date create_time) {
			this.create_time = create_time;
		}

		public Date getUpdate_time() {
			return update_time;
		}

		public void setUpdate_time(Date update_time) {
			this.update_time = update_time;
		}

		
	}