package com.hjw.charge.DTO;

public class ItemBindInsuranceDTO {
	
	private long id;
	private long dep_id;
	private String dep_name;
	private String item_code;
	private String item_name;
	private double amount;
	private String is_bind_prov;
	private String is_bind_city;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getDep_id() {
		return dep_id;
	}
	public void setDep_id(long dep_id) {
		this.dep_id = dep_id;
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
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
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
	public String getDep_name() {
		return dep_name;
	}
	public void setDep_name(String dep_name) {
		this.dep_name = dep_name;
	}

}
