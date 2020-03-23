package com.hjw.charge.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
@Entity
@Table(name="charging_invoice_single")
public class ChargingInvoiceSingleCharge implements java.io.Serializable{

	private static final long serialVersionUID = -97502163798576023L;

	@Id
	@GenericGenerator(name = "idGenerator", strategy = "identity")
	@GeneratedValue(generator = "idGenerator")
	private long id;
	
	@Column(name="title_info")
	private String title_info;
	
	@Column(name="invoice_type")
	private String invoice_type;
	
	@Column(name="invoice_num")
	private String invoice_num;
	
	@Column(name="invoice_amount")
	private Double invoice_amount;
	
	@Column(name="invoice_status")
	private String invoice_status;
	
	@Column(name="invoice_maker")
	private Long invoice_maker;
	
	@Column(name="invoice_time")
	private Date invoice_time;
	
	@Column(name="canceller")
	private Long canceller;
	
	@Column(name="cancel_time")
	private Date cancel_time;
	
	@Column(name="creater")
	private Long creater;
	
	@Column(name="create_time")
	private Date create_time;
	
	@Column(name="updater")
	private Long updater;
	
	@Column(name="update_time")
	private Date update_time;

	@Column(name="account_num")
	private String account_num;
	
	@Column(name="exam_type")
	private String exam_type;
	
	@Column(name="barchid")
	private long barchid;
	
	@Column(name="center_num")
	private String center_num="";
	
	@Column(name="invoice_class")
	private String invoice_class;
	
	@Column(name = "daily_status")
	private String daily_status;
	
	@Column(name = "charging_summary_num")
	private String charging_summary_num;//结帐单号	
	
	@Column(name = "tax_invoices_num")
	private String tax_invoices_num;//表示税务发票编号
	
	@Column(name = "bill_type")
	private String bill_type;//票据类型  1表示 收据 2表示发票	
	
	public String getTax_invoices_num() {
		return tax_invoices_num;
	}

	public void setTax_invoices_num(String tax_invoices_num) {
		this.tax_invoices_num = tax_invoices_num;
	}

	public String getBill_type() {
		return bill_type;
	}

	public void setBill_type(String bill_type) {
		this.bill_type = bill_type;
	}

	public String getCharging_summary_num() {
		return charging_summary_num;
	}

	public void setCharging_summary_num(String charging_summary_num) {
		this.charging_summary_num = charging_summary_num;
	}

	public String getCenter_num() {
		return center_num;
	}

	public void setCenter_num(String center_num) {
		this.center_num = center_num;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitle_info() {
		return title_info;
	}

	public void setTitle_info(String title_info) {
		this.title_info = title_info;
	}

	public String getInvoice_type() {
		return invoice_type;
	}

	public void setInvoice_type(String invoice_type) {
		this.invoice_type = invoice_type;
	}

	public String getInvoice_num() {
		return invoice_num;
	}

	public void setInvoice_num(String invoice_num) {
		this.invoice_num = invoice_num;
	}

	public Double getInvoice_amount() {
		return invoice_amount;
	}

	public void setInvoice_amount(Double invoice_amount) {
		this.invoice_amount = invoice_amount;
	}

	public String getInvoice_status() {
		return invoice_status;
	}

	public void setInvoice_status(String invoice_status) {
		this.invoice_status = invoice_status;
	}

	public Long getInvoice_maker() {
		return invoice_maker;
	}

	public void setInvoice_maker(Long invoice_maker) {
		this.invoice_maker = invoice_maker;
	}

	public Date getInvoice_time() {
		return invoice_time;
	}

	public void setInvoice_time(Date invoice_time) {
		this.invoice_time = invoice_time;
	}

	public Long getCanceller() {
		return canceller;
	}

	public void setCanceller(Long canceller) {
		this.canceller = canceller;
	}

	public Date getCancel_time() {
		return cancel_time;
	}

	public void setCancel_time(Date cancel_time) {
		this.cancel_time = cancel_time;
	}

	public Long getCreater() {
		return creater;
	}

	public void setCreater(Long creater) {
		this.creater = creater;
	}

	public Date getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}

	public Long getUpdater() {
		return updater;
	}

	public void setUpdater(Long updater) {
		this.updater = updater;
	}

	public Date getUpdate_time() {
		return update_time;
	}

	public void setUpdate_time(Date update_time) {
		this.update_time = update_time;
	}

	public String getAccount_num() {
		return account_num;
	}

	public void setAccount_num(String account_num) {
		this.account_num = account_num;
	}

	public String getExam_type() {
		return exam_type;
	}

	public void setExam_type(String exam_type) {
		this.exam_type = exam_type;
	}

	public long getBarchid() {
		return barchid;
	}

	public void setBarchid(long barchid) {
		this.barchid = barchid;
	}

	public String getInvoice_class() {
		return invoice_class;
	}

	public void setInvoice_class(String invoice_class) {
		this.invoice_class = invoice_class;
	}

	public String getDaily_status() {
		return daily_status;
	}

	public void setDaily_status(String daily_status) {
		this.daily_status = daily_status;
	}

	

}
