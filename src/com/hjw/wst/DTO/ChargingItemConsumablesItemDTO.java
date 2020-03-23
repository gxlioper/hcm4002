package com.hjw.wst.DTO;

public class ChargingItemConsumablesItemDTO implements java.io.Serializable {
	private static final long serialVersionUID = -97502163798576023L;
	private long id;
	private long item_id; //收费项目ID
	private String item_name;
	private String item_code;
	private long consumables_id;//耗材项目ID
	private String consumables_name;
	private String creater;
	private String create_time;
	private String dep_name;
	private double amount;
	private long item_seq;
	
	
	
	public long getItem_seq() {
		return item_seq;
	}
	public void setItem_seq(long item_seq) {
		this.item_seq = item_seq;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getItem_id() {
		return item_id;
	}
	public void setItem_id(long item_id) {
		this.item_id = item_id;
	}
	public String getItem_name() {
		return item_name;
	}
	public void setItem_name(String item_name) {
		this.item_name = item_name;
	}
	public long getConsumables_id() {
		return consumables_id;
	}
	public void setConsumables_id(long consumables_id) {
		this.consumables_id = consumables_id;
	}
	public String getConsumables_name() {
		return consumables_name;
	}
	public void setConsumables_name(String consumables_name) {
		this.consumables_name = consumables_name;
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
	public String getDep_name() {
		return dep_name;
	}
	public void setDep_name(String dep_name) {
		this.dep_name = dep_name;
	}
	public String getItem_code() {
		return item_code;
	}
	public void setItem_code(String item_code) {
		this.item_code = item_code;
	}
}
