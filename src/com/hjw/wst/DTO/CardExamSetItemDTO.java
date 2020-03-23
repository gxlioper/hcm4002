package com.hjw.wst.DTO;

public class CardExamSetItemDTO {

	private String id;   
	private String card_num;    //卡号
	private String set_num;    //套餐编号
	private String set_name;   //套餐名称
	private String item_code;    //收费项目编码
	private String item_name;    //收费项目名称
	private int itemnum;    //项目数量
	private double item_amount;  //原金额	
	private double discount;   //折扣
	private double amount;    //金额
	private String item_category;   //类型
	private String sex;
	private double set_discount;   //套餐折扣
	private double set_amount;   //套餐金额
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCard_num() {
		return card_num;
	}
	public void setCard_num(String card_num) {
		this.card_num = card_num;
	}
	public String getSet_num() {
		return set_num;
	}
	public void setSet_num(String set_num) {
		this.set_num = set_num;
	}
	public String getSet_name() {
		return set_name;
	}
	public void setSet_name(String set_name) {
		this.set_name = set_name;
	}
	public String getItem_code() {
		return item_code;
	}
	public void setItem_code(String item_code) {
		this.item_code = item_code;
	}
	public String getItem_name() {
		return item_name;
	}
	public void setItem_name(String item_name) {
		this.item_name = item_name;
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
	public String getItem_category() {
		return item_category;
	}
	public void setItem_category(String item_category) {
		this.item_category = item_category;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public double getSet_discount() {
		return set_discount;
	}
	public void setSet_discount(double set_discount) {
		this.set_discount = set_discount;
	}
	public double getSet_amount() {
		return set_amount;
	}
	public void setSet_amount(double set_amount) {
		this.set_amount = set_amount;
	}
	
	
}
