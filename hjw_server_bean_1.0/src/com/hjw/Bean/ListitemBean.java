package com.hjw.Bean;

public class ListitemBean {
	private String item_code;//收费项目	
	private String exam_indicator="";//付费类别	exam_indicator
	private double item_amount;//收费项目原始金额	item_amount
	private double discount;//折扣	discount
	private int itemnum;//数量	itemnum
	private double amount;//金额	amount
	private long item_id;
	private double team_pay;
	private double personal_pay;
	private String item_name="";
	
	public String getItem_name() {
		return item_name;
	}
	public void setItem_name(String item_name) {
		this.item_name = item_name;
	}
	public double getTeam_pay() {
		return team_pay;
	}
	public void setTeam_pay(double team_pay) {
		this.team_pay = team_pay;
	}
	public double getPersonal_pay() {
		return personal_pay;
	}
	public void setPersonal_pay(double personal_pay) {
		this.personal_pay = personal_pay;
	}
	public long getItem_id() {
		return item_id;
	}
	public void setItem_id(long item_id) {
		this.item_id = item_id;
	}
	public String getItem_code() {
		return item_code;
	}
	public void setItem_code(String item_code) {
		this.item_code = item_code;
	}
	public String getExam_indicator() {
		return exam_indicator;
	}
	public void setExam_indicator(String exam_indicator) {
		this.exam_indicator = exam_indicator;
	}
	public double getItem_amount() {
		return item_amount;
	}
	public void setItem_amount(double item_amount) {
		this.item_amount = item_amount;
	}
	public double getDiscount() {
		return discount;
	}
	public void setDiscount(double discount) {
		this.discount = discount;
	}
	public int getItemnum() {
		return itemnum;
	}
	public void setItemnum(int itemnum) {
		this.itemnum = itemnum;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}

}
