package com.hjw.wst.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 
     * @Title:  火箭蛙体检管理系统   
     * @Package com.hjw.wst.domain   
     * @Description: 团体结算支付方式
     * @author: yangm     
     * @date:   2016年12月13日 下午4:54:02   
     * @version V2.0.0.0
 */
@Entity
@Table(name = "team_account_item_list")
public class TeamAccountItemList implements java.io.Serializable {
	 private static final long serialVersionUID = -97502163798576023L;
		@Id
		@GenericGenerator(name = "idGenerator", strategy = "identity")
		@GeneratedValue(generator = "idGenerator")
		private long id;

	    @Column(name = "exam_num")
		private String exam_num;
	    
	    @Column(name = "acc_num")
	    private String acc_num="";
	    
	    @Column(name = "charging_item_id")
	    private long charging_item_id;
	    
	    @Column(name = "price")
	    private double price;
	    
	    @Column(name = "acc_charge")
	    private double acc_charge;
	    
	    @Column(name = "dec_charges")
	    private double dec_charges;
	    
	    @Column(name = "personal_pay")
	    private double personal_pay;
	    
	    @Column(name = "PrePay")
	    private String PrePay="";
	    
	    @Column(name = "discard")
	    private String discard;
	    
	    @Column(name = "in_date")
	    private Date in_date;
	    
	    @Column(name = "creater")
	    private long creater;
	    
	    @Column(name = "rate")
	    private float rate;
	
	    @Column(name = "center_num")
	    private String center_num=""; 

		public double getPersonal_pay() {
			return personal_pay;
		}

		public void setPersonal_pay(double personal_pay) {
			this.personal_pay = personal_pay;
		}

		public String getCenter_num() {
			return center_num;
		}

		public void setCenter_num(String center_num) {
			this.center_num = center_num;
		}

		public long getId() {
			return id;
		}

		public void setId(long id) {
			this.id = id;
		}

		public String getAcc_num() {
			return acc_num;
		}

		public void setAcc_num(String acc_num) {
			this.acc_num = acc_num;
		}

		public String getExam_num() {
			return exam_num;
		}

		public void setExam_num(String exam_num) {
			this.exam_num = exam_num;
		}

		public long getCharging_item_id() {
			return charging_item_id;
		}

		public void setCharging_item_id(long charging_item_id) {
			this.charging_item_id = charging_item_id;
		}

		public double getPrice() {
			return price;
		}

		public void setPrice(double price) {
			this.price = price;
		}

		public double getAcc_charge() {
			return acc_charge;
		}

		public void setAcc_charge(double acc_charge) {
			this.acc_charge = acc_charge;
		}

		public String getPrePay() {
			return PrePay;
		}

		public void setPrePay(String prePay) {
			PrePay = prePay;
		}

		public String getDiscard() {
			return discard;
		}

		public void setDiscard(String discard) {
			this.discard = discard;
		}

		public long getCreater() {
			return creater;
		}

		public void setCreater(long creater) {
			this.creater = creater;
		}

		public float getRate() {
			return rate;
		}

		public void setRate(float rate) {
			this.rate = rate;
		}

		public Date getIn_date() {
			return in_date;
		}

		public void setIn_date(Date in_date) {
			this.in_date = in_date;
		}
      
	}