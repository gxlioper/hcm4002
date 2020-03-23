package com.hjw.wst.domain;

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
     * @Description:  收费员日结支付方式明细
     * @author: dangqi     
     * @date:   2017年11月2日 下午12:06:28   
     * @version V2.0.0.0
 */
@Entity
@Table(name = "cashier_daily_acc_payway")
public class CashierDailyAccPayway implements java.io.Serializable {
	private static final long serialVersionUID = -97502163798576023L;
	
	@Id
	@GenericGenerator(name = "idGenerator", strategy = "identity")
	@GeneratedValue(generator = "idGenerator")
	private long id;

	@Column(name = "daily_acc_num")
	private String daily_acc_num;
	
	@Column(name = "charging_way")
	private long charging_way;
	
	@Column(name = "amonut")
	private double amonut;

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

	public long getCharging_way() {
		return charging_way;
	}

	public void setCharging_way(long charging_way) {
		this.charging_way = charging_way;
	}

	public double getAmonut() {
		return amonut;
	}

	public void setAmonut(double amonut) {
		this.amonut = amonut;
	}
}
