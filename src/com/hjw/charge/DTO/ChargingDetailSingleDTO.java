package com.hjw.charge.DTO;

public class ChargingDetailSingleDTO implements java.io.Serializable{

	private static final long serialVersionUID = -97502163798576023L;

	private long id;
	private long summary_id;
	private long charging_item_id;
	private String item_name;
	private String item_code;
	private String dep_name;
	private Double item_amount;
	private Double amount;
	private Double discount;
	private String creater;
	private String create_time;
	private String updater;
	private String update_time;
	private String user_name;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getSummary_id() {
		return summary_id;
	}
	public void setSummary_id(long summary_id) {
		this.summary_id = summary_id;
	}
	public long getCharging_item_id() {
		return charging_item_id;
	}
	public void setCharging_item_id(long charging_item_id) {
		this.charging_item_id = charging_item_id;
	}
	public String getItem_name() {
		return item_name;
	}
	public void setItem_name(String item_name) {
		this.item_name = item_name;
	}
	public String getItem_code() {
		return item_code;
	}
	public void setItem_code(String item_code) {
		this.item_code = item_code;
	}
	public String getDep_name() {
		return dep_name;
	}
	public void setDep_name(String dep_name) {
		this.dep_name = dep_name;
	}
	public Double getItem_amount() {
		return item_amount;
	}
	public void setItem_amount(Double item_amount) {
		this.item_amount = item_amount;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public Double getDiscount() {
		return discount;
	}
	public void setDiscount(Double discount) {
		this.discount = discount;
	}
	public String getCreater() {
		return creater;
	}
	public void setCreater(String creater) {
		this.creater = creater;
	}
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	public String getUpdater() {
		return updater;
	}
	public void setUpdater(String updater) {
		this.updater = updater;
	}
	public String getUpdate_time() {
		return update_time;
	}
	public void setUpdate_time(String update_time) {
		this.update_time = update_time;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	
}