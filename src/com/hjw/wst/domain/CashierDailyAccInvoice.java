package com.hjw.wst.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
/**
 * 
     * @Title:  火箭蛙体检管理系统   
     * @Package com.hjw.wst.domain   
     * @Description:  收费员日结发票明细
     * @author: dangqi     
     * @date:   2017年11月2日 上午11:57:44   
     * @version V2.0.0.0
 */
@Entity
@Table(name = "cashier_daily_acc_invoice")
public class CashierDailyAccInvoice implements java.io.Serializable {
	private static final long serialVersionUID = -97502163798576023L;
	
	@Id
	@GenericGenerator(name = "idGenerator", strategy = "identity")
	@GeneratedValue(generator = "idGenerator")
	private long id;
	
	@Column(name = "daily_acc_num")
	private String daily_acc_num;
	
	@Column(name = "charging_way_id")
	private long charging_way_id;//发票类型
	
	@Column(name = "exam_num")
	private String exam_num;
	
	@Column(name = "invoice_num")
	private String invoice_num;
	
	@Column(name = "invoice_status")
	private String invoice_status;
	
	@Column(name = "create_date")
	private Date create_date;
	
	@Column(name = "userId")
	private long userId;
	
	@Column(name = "invoice_amount")
	private double invoice_amount;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getDaily_acc_num() {
		return daily_acc_num;
	}

	public void setDaily_acc_num(String daily_acc_num) {
		this.daily_acc_num = daily_acc_num;
	}

	public long getCharging_way_id() {
		return charging_way_id;
	}

	public void setCharging_way_id(long charging_way_id) {
		this.charging_way_id = charging_way_id;
	}

	public String getExam_num() {
		return exam_num;
	}

	public void setExam_num(String exam_num) {
		this.exam_num = exam_num;
	}

	public String getInvoice_num() {
		return invoice_num;
	}

	public void setInvoice_num(String invoice_num) {
		this.invoice_num = invoice_num;
	}

	public String getInvoice_status() {
		return invoice_status;
	}

	public void setInvoice_status(String invoice_status) {
		this.invoice_status = invoice_status;
	}

	public Date getCreate_date() {
		return create_date;
	}

	public void setCreate_date(Date create_date) {
		this.create_date = create_date;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public double getInvoice_amount() {
		return invoice_amount;
	}

	public void setInvoice_amount(double invoice_amount) {
		this.invoice_amount = invoice_amount;
	}
}
