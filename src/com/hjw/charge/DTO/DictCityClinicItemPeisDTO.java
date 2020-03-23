package com.hjw.charge.DTO;

public class DictCityClinicItemPeisDTO {

	private String peis_item_code;   //体检收费项目编码
	private String clinic_item_code; //市医保诊疗项目编码
	private String item_name_city;   //市医保诊疗项目名称
	private int item_num;   		 //项目数量
	private double item_price;       //项目单价
	public String getPeis_item_code() {
		return peis_item_code;
	}
	public void setPeis_item_code(String peis_item_code) {
		this.peis_item_code = peis_item_code;
	}
	public String getClinic_item_code() {
		return clinic_item_code;
	}
	public void setClinic_item_code(String clinic_item_code) {
		this.clinic_item_code = clinic_item_code;
	}
	public int getItem_num() {
		return item_num;
	}
	public void setItem_num(int item_num) {
		this.item_num = item_num;
	}
	public String getItem_name_city() {
		return item_name_city;
	}
	public void setItem_name_city(String item_name_city) {
		this.item_name_city = item_name_city;
	}
	public double getItem_price() {
		return item_price;
	}
	public void setItem_price(double item_price) {
		this.item_price = item_price;
	}

	
}
