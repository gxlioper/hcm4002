package com.hjw.wst.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="card_sale_summary")//售卡交易主表
public class CardSaleSummary implements java.io.Serializable {
	private static final long serialVersionUID = -97502163798576023L;
	
	@Id
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
    @Column(name = "id", unique = true, nullable = false,length = 50)
	private String id;
	
	@Column(name="sale_trade_num")
	private String sale_trade_num;//售卡交易流水号
	
	@Column(name="sale_status")
	private long sale_status;//交易状态 1售卡、0预售卡 注：预售卡在操作售卡后修改此状态为1
	
	@Column(name="invoice_id")
	private long invoice_id;//所开发票ID
	
	@Column(name="is_print_recepit")
	private String is_print_recepit;//是否开发票 N未开、Y已开
	
	@Column(name="amount")
	private Double amount;//卡内总金额
	
	@Column(name="sale_amount")
	private Double sale_amount;//售卡总金额
	
	@Column(name="sale_type")
	private long sale_type;//交易类型 1售卡、0预售卡 注：预售卡在操作售卡后不修改此状态
	
	@Column(name="sale_user")
	private long sale_user;//售卡人
	
	@Column(name="sale_time")
	private Date sale_time;//售卡时间
	
	@Column(name="advance_sale_user")
	private long advance_sale_user;//预售卡人  直接售卡操作不需要填写
	
	@Column(name="advance_sale_time")
	private Date advance_sale_time;//预售卡时间
	
	@Column(name="daily_status")
	private long daily_status;//日结状态 0未日结、1已日结
	
	
	@Column(name = "center_num")
	private String center_num;  // 体检中心编码

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSale_trade_num() {
		return sale_trade_num;
	}

	public void setSale_trade_num(String sale_trade_num) {
		this.sale_trade_num = sale_trade_num;
	}

	public long getSale_status() {
		return sale_status;
	}

	public void setSale_status(long sale_status) {
		this.sale_status = sale_status;
	}

	public long getInvoice_id() {
		return invoice_id;
	}

	public void setInvoice_id(long invoice_id) {
		this.invoice_id = invoice_id;
	}

	public String getIs_print_recepit() {
		return is_print_recepit;
	}

	public void setIs_print_recepit(String is_print_recepit) {
		this.is_print_recepit = is_print_recepit;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Double getSale_amount() {
		return sale_amount;
	}

	public void setSale_amount(Double sale_amount) {
		this.sale_amount = sale_amount;
	}

	public long getSale_type() {
		return sale_type;
	}

	public void setSale_type(long sale_type) {
		this.sale_type = sale_type;
	}

	public long getSale_user() {
		return sale_user;
	}

	public void setSale_user(long sale_user) {
		this.sale_user = sale_user;
	}

	public Date getSale_time() {
		return sale_time;
	}

	public void setSale_time(Date sale_time) {
		this.sale_time = sale_time;
	}

	public long getAdvance_sale_user() {
		return advance_sale_user;
	}

	public void setAdvance_sale_user(long advance_sale_user) {
		this.advance_sale_user = advance_sale_user;
	}

	public Date getAdvance_sale_time() {
		return advance_sale_time;
	}

	public void setAdvance_sale_time(Date advance_sale_time) {
		this.advance_sale_time = advance_sale_time;
	}

	public long getDaily_status() {
		return daily_status;
	}

	public void setDaily_status(long daily_status) {
		this.daily_status = daily_status;
	}

	public String getCenter_num() {
		return center_num;
	}

	public void setCenter_num(String center_num) {
		this.center_num = center_num;
	}
	
}
