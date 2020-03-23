package com.hjw.charge.DTO;

public class DictInsuranceClinicItemDTO {

	private String item_code;          //1.诊疗项目编码	
	private String  item_name;            //2.项目名称	
	private String  short_code;         //3.拼音助记码	
	private String  item_place;	       //5.产地(材料)
	private String  item_unit;           //16.单位	  
	private String  spec;                //19.规格
	private double  price;               //20.价格
	private String  invoice_item_name;    //发票项目名称
	private String  fee_class;           //14.收费类别
	private String  is_medical;		   //是否医保项目
	private String  dosage_form;          //剂型
	private String  note;                //23.备注
	private String  is_effective;         //是否有效
	private String  top_fee;
	private String  item_name_city;
	private String  item_code_city;
	private int  itemNum;
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
	public String getShort_code() {
		return short_code;
	}
	public void setShort_code(String short_code) {
		this.short_code = short_code;
	}
	public String getItem_place() {
		return item_place;
	}
	public void setItem_place(String item_place) {
		this.item_place = item_place;
	}
	public String getItem_unit() {
		return item_unit;
	}
	public void setItem_unit(String item_unit) {
		this.item_unit = item_unit;
	}
	public String getSpec() {
		return spec;
	}
	public void setSpec(String spec) {
		this.spec = spec;
	}
	
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public String getInvoice_item_name() {
		return invoice_item_name;
	}
	public void setInvoice_item_name(String invoice_item_name) {
		this.invoice_item_name = invoice_item_name;
	}
	public String getFee_class() {
		return fee_class;
	}
	public void setFee_class(String fee_class) {
		this.fee_class = fee_class;
	}
	public String getIs_medical() {
		return is_medical;
	}
	public void setIs_medical(String is_medical) {
		this.is_medical = is_medical;
	}
	public String getDosage_form() {
		return dosage_form;
	}
	public void setDosage_form(String dosage_form) {
		this.dosage_form = dosage_form;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getIs_effective() {
		return is_effective;
	}
	public void setIs_effective(String is_effective) {
		this.is_effective = is_effective;
	}
	public String getTop_fee() {
		return top_fee;
	}
	public void setTop_fee(String top_fee) {
		this.top_fee = top_fee;
	}
	public String getItem_name_city() {
		return item_name_city;
	}
	public void setItem_name_city(String item_name_city) {
		this.item_name_city = item_name_city;
	}
	public String getItem_code_city() {
		return item_code_city;
	}
	public void setItem_code_city(String item_code_city) {
		this.item_code_city = item_code_city;
	}
	public int getItemNum() {
		return itemNum;
	}
	public void setItemNum(int itemNum) {
		this.itemNum = itemNum;
	}
	
	
}
