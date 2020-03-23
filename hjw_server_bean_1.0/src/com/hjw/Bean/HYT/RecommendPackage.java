package com.hjw.Bean.HYT;

import java.util.ArrayList;
import java.util.List;

public class RecommendPackage {

	private String code = "";//推荐套餐代码
	private int gender = 0;//0：不限；1：男；2：女
	private List<ItemHYT> items = new ArrayList<>();	//套餐下的项目列表
	private int marital	= 0;//0：不限；1：未婚；2：已婚
//	private List<MedicalPackageItemLink> medicalPackageItemLinks = new ArrayList<>();	//套餐明细关系
	private String name = "";//推荐套餐名称
	private String price = "";//套餐价格
	
	public String getCode() {
		return code;
	}
	public int getGender() {
		return gender;
	}
	public List<ItemHYT> getItems() {
		return items;
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
	public void setItems(List<ItemHYT> items) {
		this.items = items;
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
}
