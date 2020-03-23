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
     * @Description: 收费员日结主表 
     * @author: dangqi     
     * @date:   2017年11月2日 上午11:55:59   
     * @version V2.0.0.0
 */
@Entity
@Table(name = "cashier_daily_acc")
public class CashierDailyAcc implements java.io.Serializable {
	private static final long serialVersionUID = -97502163798576023L;
	
	@Id
	@GenericGenerator(name = "idGenerator", strategy = "identity")
	@GeneratedValue(generator = "idGenerator")
	private long id;

	@Column(name = "daily_acc_num")
	private String daily_acc_num; //日结单号
	
	@Column(name = "userId")
	private long userId; //日结人
	
	@Column(name = "daily_acc_date")
	private Date daily_acc_date; //日结时间
	
	@Column(name = "daily_acc_amount")
	private double daily_acc_amount; // 日结总金额
	
	@Column(name = "is_Active")
	private String is_Active; //日结状态
	
	@Column(name="daily_status")
	private String daily_status;//财务部门日结状态 0未日结 1已日结

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

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public Date getDaily_acc_date() {
		return daily_acc_date;
	}

	public void setDaily_acc_date(Date daily_acc_date) {
		this.daily_acc_date = daily_acc_date;
	}

	public double getDaily_acc_amount() {
		return daily_acc_amount;
	}

	public void setDaily_acc_amount(double daily_acc_amount) {
		this.daily_acc_amount = daily_acc_amount;
	}

	public String getIs_Active() {
		return is_Active;
	}

	public void setIs_Active(String is_Active) {
		this.is_Active = is_Active;
	}

	public String getDaily_status() {
		return daily_status;
	}

	public void setDaily_status(String daily_status) {
		this.daily_status = daily_status;
	}
}
