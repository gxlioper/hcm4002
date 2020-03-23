package com.hjw.wst.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "charging_summary_group")
public class ChargingSummaryGroup implements java.io.Serializable{

	private static final long serialVersionUID = -97502163798576023L;

	@Id
	@GenericGenerator(name = "idGenerator", strategy = "identity")
	@GeneratedValue(generator = "idGenerator")
	private long id;
	
	@Column(name="batch_id")
	private long batch_id;
	
	@Column(name="invoice_id")
	private long invoice_id;
	
	@Column(name="charging_status")
	private String charging_status;
	
	@Column(name="amount1")
	private double amount1;
	
	@Column(name="amount2")
	private double amount2;
	
	@Column(name="additional")
	private double additional;
	
	@Column(name="is_print_recepit")
	private String is_print_recepit;
	
	@Column(name="cashier")
	private long cashier;
	
	@Column(name="cash_date")
	private Date cash_date;
	
	@Column(name="account_num")
	private String account_num;
	
	@Column(name="is_active")
	private String is_active;
	
	@Column(name="creater")
	private long creater;
	
	@Column(name="create_time")
	private Date create_time;
	
	@Column(name="updater")
	private long updater;
	
	@Column(name="update_time")
	private Date update_time;
	
	@Column(name="daily_status")
	private String daily_status;
	
	@Column(name="receiv_status")
	private int receiv_status;//确认收款 0表示未确认，1表示确认
	
	@Column(name="receiver")
	private long receiver;//
	
	@Column(name="receiv_date")
	private Date receiv_date;
	
	@Column(name="center_num")
	private String center_num;

	public String getCenter_num() {
		return center_num;
	}

	public void setCenter_num(String center_num) {
		this.center_num = center_num;
	}

	public int getReceiv_status() {
		return receiv_status;
	}

	public void setReceiv_status(int receiv_status) {
		this.receiv_status = receiv_status;
	}

	public long getReceiver() {
		return receiver;
	}

	public void setReceiver(long receiver) {
		this.receiver = receiver;
	}

	public Date getReceiv_date() {
		return receiv_date;
	}

	public void setReceiv_date(Date receiv_date) {
		this.receiv_date = receiv_date;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getInvoice_id() {
		return invoice_id;
	}

	public void setInvoice_id(long invoice_id) {
		this.invoice_id = invoice_id;
	}

	public String getCharging_status() {
		return charging_status;
	}

	public void setCharging_status(String charging_status) {
		this.charging_status = charging_status;
	}

	public double getAmount1() {
		return amount1;
	}

	public void setAmount1(double amount1) {
		this.amount1 = amount1;
	}

	public double getAmount2() {
		return amount2;
	}

	public void setAmount2(double amount2) {
		this.amount2 = amount2;
	}

	public String getIs_print_recepit() {
		return is_print_recepit;
	}

	public void setIs_print_recepit(String is_print_recepit) {
		this.is_print_recepit = is_print_recepit;
	}

	public long getCashier() {
		return cashier;
	}

	public void setCashier(long cashier) {
		this.cashier = cashier;
	}

	public Date getCash_date() {
		return cash_date;
	}

	public void setCash_date(Date cash_date) {
		this.cash_date = cash_date;
	}

	public long getCreater() {
		return creater;
	}

	public void setCreater(long creater) {
		this.creater = creater;
	}

	public Date getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}

	public long getUpdater() {
		return updater;
	}

	public void setUpdater(long updater) {
		this.updater = updater;
	}

	public Date getUpdate_time() {
		return update_time;
	}

	public void setUpdate_time(Date update_time) {
		this.update_time = update_time;
	}

	public String getIs_active() {
		return is_active;
	}

	public void setIs_active(String is_active) {
		this.is_active = is_active;
	}

	public long getBatch_id() {
		return batch_id;
	}

	public void setBatch_id(long batch_id) {
		this.batch_id = batch_id;
	}

	public double getAdditional() {
		return additional;
	}

	public void setAdditional(double additional) {
		this.additional = additional;
	}

	public String getAccount_num() {
		return account_num;
	}

	public void setAccount_num(String account_num) {
		this.account_num = account_num;
	}

	public String getDaily_status() {
		return daily_status;
	}

	public void setDaily_status(String daily_status) {
		this.daily_status = daily_status;
	}
	
	
}
