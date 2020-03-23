package com.hjw.wst.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "group_set")
public class GroupSet implements java.io.Serializable {
	 private static final long serialVersionUID = -97502163798576023L;
		@Id
		@GenericGenerator(name = "idGenerator", strategy = "identity")
		@GeneratedValue(generator = "idGenerator")
		private long id;

		@Column(name = "group_id")
        private long group_id;
		
		@Column(name = "exam_set_id")
		private long exam_set_id;
		
		@Column(name = "discount")
		private double discount;
		
		@Column(name = "amount")
		private double amount;
		
		@Column(name = "isActive")
		private String isActive;
		
		@Column(name = "final_exam_date")
		private String final_exam_date;
		
		@Column(name = "creater")
		private long creater;
		
		@Column(name = "create_time")
		private String create_time;
		
		@Column(name = "updater")
		private long updater;
		
		@Column(name = "update_time")
		private String update_time;	

	    public long getId() {
			return id;
		}

		public void setId(long id) {
			this.id = id;
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

		public String getCreate_time() {
			return create_time;
		}

		public void setCreate_time(String create_time) {
			this.create_time = create_time;
		}

		public String getUpdate_time() {
			return update_time;
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