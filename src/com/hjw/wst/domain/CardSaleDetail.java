package com.hjw.wst.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="card_sale_detail")//售卡交易明细表
public class CardSaleDetail implements java.io.Serializable {
	private static final long serialVersionUID = -97502163798576023L;
	
	@Id
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
    @Column(name = "id", unique = true, nullable = false,length = 50)
	private String id;
	
	@Column(name="sale_trade_num")
	private String sale_trade_num;//售卡交易主表流水号
	
	@Column(name="card_num")
	private String card_num;//卡号
	
	@Column(name="amount")
	private Double amount;//卡内金额
	
	@Column(name="sale_amount")
	private Double sale_amount;//售卡金额
	
	@Column(name="creater")
	private long creater;//创建人
	
	@Column(name="create_time")
	private Date create_time;//创建时间

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

	public String getCard_num() {
		return card_num;
	}

	public void setCard_num(String card_num) {
		this.card_num = card_num;
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
}
