package com.hjw.crm.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="crm_sales_cost")
public class CrmSalesCost {
	@Id
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
	@Column(name = "id", unique = true, nullable = false,length = 50)
	private String id;
	@Column(name="sales_id",nullable=false)
	private Long sales_id;
	@Column(name="cost_amount",nullable=false)
	private Double cost_amount;
	@Column(name="cost_date",nullable=false)
	private Date cost_date;
	@Column(name="cost_type",nullable=false)
	private String cost_type;
	@Column(name="batch_num")
	private String batch_num;
	@Column(name="payment_type")
	private String payment_type;
	@Column(name="remark")
	private String remark;
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
	public Date getCost_date() {
		return cost_date;
	}
	public void setCost_date(Date cost_date) {
		this.cost_date = cost_date;
	}
	public String getCost_type() {
		return cost_type;
	}
	public void setCost_type(String cost_type) {
		this.cost_type = cost_type;
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
