package com.hjw.Bean;

public class OrderformBean {
	private String orderid;//订单编号	
	private String payway;//支付方式	payway 
	                      //        2表示微信支付
	                      //        3支付宝支付
	private double payamount;//订单金额	payamount
	private String jnnumber="";//
	
	public String getJnnumber() {
		return jnnumber;
	}
	public void setJnnumber(String jnnumber) {
		this.jnnumber = jnnumber;
	}
	public String getOrderid() {
		return orderid;
	}
	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}
	public String getPayway() {
		return payway;
	}
	public void setPayway(String payway) {
		this.payway = payway;
	}
	
	public double getPayamount() {
		return payamount;
	}
	public void setPayamount(double payamount) {
		this.payamount = payamount;
	}

	
}
