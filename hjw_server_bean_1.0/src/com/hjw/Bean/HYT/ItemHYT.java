package com.hjw.Bean.HYT;

public class ItemHYT {

	private String code = "";//项目代码
	private int gender = 0;//0：不限；1：男；2：女
	private int marital = 0;//0：不限；1：未婚；2：已婚
	private String name = "";//项目名称
	private String price = "";//项目价格
	private String description = "";//项目描述
	
	public String getCode() {
		return code;
	}
	public int getGender() {
		return gender;
	}
	public int getMarital() {
		return marital;
	}
	public String getName() {
		return name;
	}
	public String getPrice() {
		return price;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public void setGender(int gender) {
		this.gender = gender;
	}
	public void setMarital(int marital) {
		this.marital = marital;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
}
