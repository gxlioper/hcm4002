package com.hjw.crm.DTO;

import java.util.Date;

public class CrmSalesCostDTO {
	private String id;

	private Long sales_id;
	private String username;

	private Double cost_amount;

	private String cost_date;

	private String cost_type;
	private String cost_type_code;
	
	private String batch_num;
	
	private String payment_type;
	
	private String remark;
	
	private String batch_id;
	private String batch;
	

	public String getBatch() {
		return batch;
	}

	public void setBatch(String batch) {
		this.batch = batch;
	}


	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getBatch_id() {
		return batch_id;
	}

	public void setBatch_id(String batch_id) {
		this.batch_id = batch_id;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Long getSales_id() {
		return sales_id;
	}

	public void setSales_id(Long sales_id) {
		this.sales_id = sales_id;
	}

	public Double getCost_amount() {
		return cost_amount;
	}

	public void setCost_amount(Double cost_amount) {
		this.cost_amount = cost_amount;
	}

	public String getCost_date() {
		return cost_date;
	}

	public void setCost_date(String cost_date) {
		this.cost_date = cost_date;
	}

	public String getCost_type() {
		return cost_type;
	}

	public String getCost_type_code() {
		return cost_type_code;
	}

	public void setCost_type_code(String cost_type_code) {
		this.cost_type_code = cost_type_code;
	}

	public void setCost_type(String cost_type) {
		this.cost_type = cost_type;
		if("1".equals(this.cost_type)){
			this.setCost_type("借款类型");
		}else if("2".equals(this.cost_type)){
			this.setCost_type("还款类型");
		}
	}

	public String getBatch_num() {
		return batch_num;
	}

	public void setBatch_num(String batch_num) {
		this.batch_num = batch_num;
	}

	public String getPayment_type() {
		return payment_type;
	}

	public void setPayment_type(String payment_type) {
		this.payment_type = payment_type;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
}
