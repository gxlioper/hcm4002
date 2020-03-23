package com.hjw.charge.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "pos_daily_summary")
public class PosDailySummaryCharge {
	 @Id
	 @GenericGenerator(name = "idGenerator", strategy = "identity")
	 @GeneratedValue(generator = "idGenerator")
	 private long id;
	 
	 @Column(name = "amount")
	 private double amount;
	 
	 @Column(name = "creater")
	 private long  creater;
	 
	 @Column(name = "creater_time")
	 private Date creater_time;
	 
	 @Column(name = "trade_count")
	 private int trade_count;

	public long getId() {
		return id;
	}

	public double getAmount() {
		return amount;
	}

	public long getCreater() {
		return creater;
	}

	public Date getCreater_time() {
		return creater_time;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public void setCreater(long creater) {
		this.creater = creater;
	}

	public void setCreater_time(Date creater_time) {
		this.creater_time = creater_time;
	}

	public int getTrade_count() {
		return trade_count;
	}

	public void setTrade_count(int trade_count) {
		this.trade_count = trade_count;
	}
	

}
