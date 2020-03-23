package com.hjw.Bean;

public class ListsetBean {
	private String exam_set_num;//套餐id	
	private double charges;//原金额	charges
	private double discount;//折扣	discount
	private double amount;// 折后金额	amount
	private long set_id;
	private String set_name="";
		
	public String getSet_name() {
		return set_name;
	}
	public void setSet_name(String set_name) {
		this.set_name = set_name;
	}
	public long getSet_id() {
		return set_id;
	}
	public void setSet_id(long set_id) {
		this.set_id = set_id;
	}
	public String getExam_set_num() {
		return exam_set_num;
	}
	public void setExam_set_num(String exam_set_num) {
		this.exam_set_num = exam_set_num;
	}
	public double getCharges() {
		return charges;
	}
	public void setCharges(double charges) {
		this.charges = charges;
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

}
