package com.hjw.charge.model;

import java.util.Date;

public class PosDetailModel implements java.io.Serializable {
	 /**   
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)   
	 */ 
	private static final long serialVersionUID = 1L;

	private long id;
	 
	 private int pay_way;  //收费方式
	 
	 private String peis_trade_code; //体检交易流水号
	 
	 private double amount;  // 交易金额
	 
	 private  String daily_status;  // 日结状态 0 未日结 、1以日结 
	 
	 private Date daily_time;   //日结日期
	 
	 private  int  pos_type;   // pos类型 1 银联支付、 2 社保支付

	public long getId() {
		return id;
	}

	public int getPay_way() {
		return pay_way;
	}

	public String getPeis_trade_code() {
		return peis_trade_code;
	}

	public double getAmount() {
		return amount;
	}

	public String getDaily_status() {
		return daily_status;
	}

	public int getPos_type() {
		return pos_type;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setPay_way(int pay_way) {
		this.pay_way = pay_way;
	}

	public void setPeis_trade_code(String peis_trade_code) {
		this.peis_trade_code = peis_trade_code;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public Date getDaily_time() {
		return daily_time;
	}

	public void setDaily_time(Date daily_time) {
		this.daily_time = daily_time;
	}

	public void setDaily_status(String daily_status) {
		this.daily_status = daily_status;
	}

	public void setPos_type(int pos_type) {
		this.pos_type = pos_type;
	}
	 
	
}
