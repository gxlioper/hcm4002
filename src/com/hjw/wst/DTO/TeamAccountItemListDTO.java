package com.hjw.wst.DTO;

/**
 * 
     * @Title:  火箭蛙体检管理系统   
     * @Package com.hjw.wst.domain   
     * @Description: 团体结算支付方式
     * @author: yangm     
     * @date:   2016年12月13日 下午4:54:02   
     * @version V2.0.0.0
 */

public class TeamAccountItemListDTO implements java.io.Serializable {
	 private static final long serialVersionUID = -97502163798576023L;

		private long id;

		private String exam_num;

	    private String acc_num="";

	    private long charging_item_id;

	    private double price;

	    private double acc_charge;

	    private String PrePay="";

	    private String discard;

	    private String in_date;

	    private long creater;

	    private float rate;

	    private String center_num="";
	    
	    private double personal_pay;    

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

		public String getIn_date() {
			return in_date;
		}

		public void setIn_date(String in_date) {
			this.in_date = in_date;
		}
      
	}