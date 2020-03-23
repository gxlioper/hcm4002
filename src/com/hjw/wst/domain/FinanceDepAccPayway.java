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
     * @Description:  财务部日结支付方式明细
     * @author: dangqi     
     * @date:   2017年11月7日 上午10:48:48   
     * @version V2.0.0.0
 */
@Entity
@Table(name="Finance_Dep_acc_payway")
public class FinanceDepAccPayway implements java.io.Serializable {
	private static final long serialVersionUID = -97502163798576023L;
	
	@Id
	@GenericGenerator(name = "idGenerator", strategy = "identity")
	@GeneratedValue(generator = "idGenerator")
	private long id;

	@Column(name="fd_acc_num")
	private String fd_acc_num;
	
	@Column(name="charging_way_id")
	private long charging_way_id;
	
	@Column(name="amount")
	private double amount;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getFd_acc_num() {
		return fd_acc_num;
	}

	public void setFd_acc_num(String fd_acc_num) {
		this.fd_acc_num = fd_acc_num;
	}

	public long getCharging_way_id() {
		return charging_way_id;
	}

	public void setCharging_way_id(long charging_way_id) {
		this.charging_way_id = charging_way_id;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}
}
