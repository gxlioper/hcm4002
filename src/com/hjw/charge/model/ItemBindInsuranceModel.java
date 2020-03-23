package com.hjw.charge.model;

import java.io.Serializable;

public class ItemBindInsuranceModel implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String item_code;   //收费项目编码
	private String item_name;   //收费项目名称
	private long dep_id;      //科室id
	private String is_bind_prov = "";     //是否关联省医保
	private String is_bind_city = "";     //是否关联市医保
	
	private String item_code_city;  //市医保项目编码
	private String item_name_city;  //市医保项目名称
	private String item_code_prov;  //省医保项目编码
	private String item_name_prov;  //省医保项目名称
	private String short_code;   //拼音助记码	
	private String dictCityClinicItemPeis;
	private String dictProvClinicItemPeis;
	private long id;
	
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
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
	public long getDep_id() {
		return dep_id;
	}
	public void setDep_id(long dep_id) {
		this.dep_id = dep_id;
	}
	public String getIs_bind_prov() {
		return is_bind_prov;
	}
	public void setIs_bind_prov(String is_bind_prov) {
		this.is_bind_prov = is_bind_prov;
	}
	public String getIs_bind_city() {
		return is_bind_city;
	}
	public void setIs_bind_city(String is_bind_city) {
		this.is_bind_city = is_bind_city;
	}
	public String getItem_code_city() {
		return item_code_city;
	}
	public void setItem_code_city(String item_code_city) {
		this.item_code_city = item_code_city;
	}
	public String getItem_name_city() {
		return item_name_city;
	}
	public void setItem_name_city(String item_name_city) {
		this.item_name_city = item_name_city;
	}
	public String getItem_code_prov() {
		return item_code_prov;
	}
	public void setItem_code_prov(String item_code_prov) {
		this.item_code_prov = item_code_prov;
	}
	public String getItem_name_prov() {
		return item_name_prov;
	}
	public void setItem_name_prov(String item_name_prov) {
		this.item_name_prov = item_name_prov;
	}
	public String getShort_code() {
		return short_code;
	}
	public void setShort_code(String short_code) {
		this.short_code = short_code;
	}
	public String getDictCityClinicItemPeis() {
		return dictCityClinicItemPeis;
	}
	public void setDictCityClinicItemPeis(String dictCityClinicItemPeis) {
		this.dictCityClinicItemPeis = dictCityClinicItemPeis;
	}
	public String getDictProvClinicItemPeis() {
		return dictProvClinicItemPeis;
	}
	public void setDictProvClinicItemPeis(String dictProvClinicItemPeis) {
		this.dictProvClinicItemPeis = dictProvClinicItemPeis;
	}
	
}
