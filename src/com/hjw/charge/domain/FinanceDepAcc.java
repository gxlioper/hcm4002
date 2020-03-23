package com.hjw.charge.domain;

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
     * @Description:  财务部日结主表 
     * @author: dangqi     
     * @date:   2017年11月7日 上午10:43:38   
     * @version V2.0.0.0
 */
@Entity
@Table(name="Finance_Dep_acc")
public class FinanceDepAcc implements java.io.Serializable {
	private static final long serialVersionUID = -97502163798576023L;
	
	@Id
	@GenericGenerator(name = "idGenerator", strategy = "identity")
	@GeneratedValue(generator = "idGenerator")
	private long id;

	@Column(name="fd_acc_num")
	private String fd_acc_num; //日结号
	
	@Column(name="fd_acc_date")
	private Date fd_acc_date;  //日结日期
	
	@Column(name="userId")
	private long userId;  //日结人
	
	@Column(name="fd_acc_amount")
	private double fd_acc_amount; //日结金额
	
	@Column(name="is_active")
	private String is_active; //是否有效
	
	@Column(name="center_num")
	private String center_num;

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

	public Date getFd_acc_date() {
		return fd_acc_date;
	}

	public void setFd_acc_date(Date fd_acc_date) {
		this.fd_acc_date = fd_acc_date;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public double getFd_acc_amount() {
		return fd_acc_amount;
	}

	public void setFd_acc_amount(double fd_acc_amount) {
		this.fd_acc_amount = fd_acc_amount;
	}

	public String getIs_active() {
		return is_active;
	}

	public void setIs_active(String is_active) {
		this.is_active = is_active;
	}

	public String getCenter_num() {
		return center_num;
	}

	public void setCenter_num(String center_num) {
		this.center_num = center_num;
	}
}
