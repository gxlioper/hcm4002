package com.hjw.wst.DTO;

public class PacsSendDTO {

	private long id;
	private String pay_status;
	private String pacs_req_code;
	private String view_num;
	private String dept_code;
	private String dept_name;
	private String item_name;
	private String dep_num;
	private String item_code;
	private double item_amount;
	private double amount;
	
	private String his_num;
	private String hiscodeClass;
	private long itemId;
	
	public String getHis_num() {
		return his_num;
	}
	public void setHis_num(String his_num) {
		this.his_num = his_num;
	}
	public String getHiscodeClass() {
		return hiscodeClass;
	}
	public void setHiscodeClass(String hiscodeClass) {
		this.hiscodeClass = hiscodeClass;
	}
	public long getItemId() {
		return itemId;
	}
	public void setItemId(long itemId) {
		this.itemId = itemId;
	}
	public String getItem_code() {
		return item_code;
	}
	public void setItem_code(String item_code) {
		this.item_code = item_code;
	}
	public double getItem_amount() {
		return item_amount;
	}
	public void setItem_amount(double item_amount) {
		this.item_amount = item_amount;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public String getPacs_req_code() {
		return pacs_req_code;
	}
	public void setPacs_req_code(String pacs_req_code) {
		this.pacs_req_code = pacs_req_code;
	}
	public String getView_num() {
		return view_num;
	}
	public void setView_num(String view_num) {
		this.view_num = view_num;
	}
	public String getDept_code() {
		return dept_code;
	}
	public void setDept_code(String dept_code) {
		this.dept_code = dept_code;
	}
	public String getDept_name() {
		return dept_name;
	}
	public void setDept_name(String dept_name) {
		this.dept_name = dept_name;
	}
	public String getItem_name() {
		return item_name;
	}
	public void setItem_name(String item_name) {
		this.item_name = item_name;
	}
	public String getDep_num() {
		return dep_num;
	}
	public void setDep_num(String dep_num) {
		this.dep_num = dep_num;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getPay_status() {
		return pay_status;
	}
	public void setPay_status(String pay_status) {
		this.pay_status = pay_status;
	}
}
