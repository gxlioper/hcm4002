package com.hjw.wst.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "set_charging_item")
public class SetChargingItem implements java.io.Serializable {
	 private static final long serialVersionUID = -97502163798576023L;

		@Id
		@GenericGenerator(name = "idGenerator", strategy = "identity")
		@GeneratedValue(generator = "idGenerator")
		private long id;

		@Column(name = "charging_item_id")
		private long charging_item_id;
		
		@Column(name = "exam_set_id")
		private long exam_set_id;
		
		@Column(name = "discount")
		private double discount;
		
		@Column(name = "amount")
		private double amount;
		
		@Column(name = "creater")
		private long creater;
		
		@Column(name = "item_amount")
		private double item_amount;
		
		@Column(name = "create_time")
		private Date create_time;
		
		@Column(name = "updater")
		private long updater;
		
		@Column(name = "update_time")
		private Date update_time;	
		
		@Column(name = "itemnum")
		private int itemnum=1;//项目个数，1 表示1项，不能为0	
		
		@Column(name = "ischosen")
		private String ischosen;//1必选2可选
		
		@Column(name = "charging_item_code")
		private String charging_item_code;
		
		@Column(name = "apptype")
		private String apptype;

		
		
		public String getApptype() {
			return apptype;
		}

		public void setApptype(String apptype) {
			this.apptype = apptype;
		}

		public String getCharging_item_code() {
			return charging_item_code;
		}

		public void setCharging_item_code(String charging_item_code) {
			this.charging_item_code = charging_item_code;
		}

		public String getIschosen() {
			return ischosen;
		}

		public void setIschosen(String ischosen) {
			this.ischosen = ischosen;
		}

		public int getItemnum() {
			return itemnum;
		}

		public void setItemnum(int itemnum) {
			this.itemnum = itemnum;
		}

	    public double getItem_amount() {
			return item_amount;
		}

		public void setItem_amount(double item_amount) {
			this.item_amount = item_amount;
		}

		public long getCharging_item_id() {
			return charging_item_id;
		}

		public void setCharging_item_id(long charging_item_id) {
			this.charging_item_id = charging_item_id;
		}

		public long getExam_set_id() {
			return exam_set_id;
		}

		public void setExam_set_id(long exam_set_id) {
			this.exam_set_id = exam_set_id;
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