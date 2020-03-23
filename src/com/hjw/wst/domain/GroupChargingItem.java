package com.hjw.wst.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "group_charging_item")
public class GroupChargingItem implements java.io.Serializable {
	 private static final long serialVersionUID = -97502163798576023L;

		@Id
		@GenericGenerator(name = "idGenerator", strategy = "identity")
		@GeneratedValue(generator = "idGenerator")
		private long id;

		@Column(name = "charge_item_id")
		private long charge_item_id;		
		
		@Column(name = "group_id")
		private long group_id;
		
		@Column(name = "item_amount")
		private double item_amount;
		
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
		
		@Column(name = "itemnum")
		private int itemnum=1;//项目个数，1 表示1项，不能为0		

	    public int getItemnum() {
			return itemnum;
		}

		public void setItemnum(int itemnum) {
			this.itemnum = itemnum;
		}

	    public long getId() {
			return id;
		}

		public void setId(long id) {
			this.id = id;
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

		public long getCharge_item_id() {
			return charge_item_id;
		}

		public void setCharge_item_id(long charge_item_id) {
			this.charge_item_id = charge_item_id;
		}

		public long getGroup_id() {
			return group_id;
		}

		public void setGroup_id(long group_id) {
			this.group_id = group_id;
		}

		public double getItem_amount() {
			return item_amount;
		}

		public void setItem_amount(double item_amount) {
			this.item_amount = item_amount;
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
	}