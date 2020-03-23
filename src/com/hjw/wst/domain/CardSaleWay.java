package com.hjw.wst.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="card_sale_way")//售卡收费方式表
public class CardSaleWay implements java.io.Serializable {
	private static final long serialVersionUID = -97502163798576023L;
	
	@Id
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
    @Column(name = "id", unique = true, nullable = false,length = 50)
	private String id;
	
	@Column(name="sale_trade_num")
	private String sale_trade_num;//售卡交易主表流水号
	
	@Column(name="charging_way")
	private String charging_way;//收费方式ID
	
	@Column(name="amount")
	private Double amount;//金额
	
	@Column(name="creater")
	private long creater;
	
	@Column(name="create_time")
	private Date create_time;

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

	public String getCharging_way() {
		return charging_way;
	}

	public void setCharging_way(String charging_way) {
		this.charging_way = charging_way;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
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
