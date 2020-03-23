package com.hjw.wst.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "charging_summary_single")
public class ChargingSummarySingle implements java.io.Serializable{

	private static final long serialVersionUID = -97502163798576023L;

	@Id
	@GenericGenerator(name = "idGenerator", strategy = "identity")
	@GeneratedValue(generator = "idGenerator")
	private long id;
	
	@Column(name="exam_id")
	private long exam_id;
	
	@Column(name="invoice_id")
	private long invoice_id;
	
	@Column(name="charging_status")
	private String charging_status;
	
	@Column(name="amount1")
	private Double amount1;
	
	@Column(name="amount2")
	private Double amount2;
	
	@Column(name="discount")
	private Double discount;
	
	@Column(name="is_print_recepit")
	private String is_print_recepit;
	
	@Column(name="cashier")
	private long cashier;
	
	@Column(name="cash_date")
	private Date cash_date;
	
	@Column(name="req_num")
	private String req_num;
	
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
	
	@Column(name = "daily_status")
	private String daily_status;
	
	@Column(name="merge_charge")
	private long merge_charge = 0;
	
	@Column(name="exam_num")
    private String exam_num;
	
	@Column(name = "pay_mode")
	private String pay_mode;
	
	@Column(name="center_num")
	private String center_num;
    
    public String getCenter_num() {
		return center_num;
	}

	public void setCenter_num(String center_num) {
		this.center_num = center_num;
	}

	public String getExam_num() {
		return exam_num;
	}

	public void setExam_num(String exam_num) {
		this.exam_num = exam_num;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getExam_id() {
		return exam_id;
	}

	public void setExam_id(long exam_id) {
		this.exam_id = exam_id;
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

	public Double getAmount1() {
		return amount1;
	}

	public void setAmount1(Double amount1) {
		this.amount1 = amount1;
	}

	public Double getAmount2() {
		return amount2;
	}

	public void setAmount2(Double amount2) {
		this.amount2 = amount2;
	}

	public Double getDiscount() {
		return discount;
	}

	public void setDiscount(Double discount) {
		this.discount = discount;
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

	public String getReq_num() {
		return req_num;
	}

	public void setReq_num(String req_num) {
		this.req_num = req_num;
	}

	public String getIs_active() {
		return is_active;
	}

	public void setIs_active(String is_active) {
		this.is_active = is_active;
	}

	public String getDaily_status() {
		return daily_status;
	}

	public void setDaily_status(String daily_status) {
		this.daily_status = daily_status;
	}

	public String getPay_mode() {
		return pay_mode;
	}

	public void setPay_mode(String pay_mode) {
		this.pay_mode = pay_mode;
	}

	public long getMerge_charge() {
		return merge_charge;
	}

	public void setMerge_charge(long merge_charge) {
		this.merge_charge = merge_charge;
	}
}
