package com.hjw.crm.DTO;

public class CrmSalesCostTrDTO {
	private String batch_num;
	private String batch;
	private String payment_type;
	private String payment_type_name;
	private String level_num;
	private String level_name;
	
	


	public String getLevel_num() {
		return level_num;
	}
	public void setLevel_num(String level_num) {
		this.level_num = level_num;
	}
	public String getLevel_name() {
		return level_name;
	}
	public void setLevel_name(String level_name) {
		this.level_name = level_name;
	}
	public String getPayment_type() {
		return payment_type;
	}
	public void setPayment_type(String payment_type) {
		this.payment_type = payment_type;
	}
	public String getPayment_type_name() {
		return payment_type_name;
	}
	public void setPayment_type_name(String payment_type_name) {
		this.payment_type_name = payment_type_name;
	}
	public String getBatch_num() {
		return batch_num;
	}
	public void setBatch_num(String batch_num) {
		this.batch_num = batch_num;
	}
	public String getBatch() {
		return batch;
	}
	public void setBatch(String batch) {
		this.batch = batch;
	}
	

}
