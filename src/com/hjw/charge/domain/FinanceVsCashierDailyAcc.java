package com.hjw.charge.domain;

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
     * @Description:  
     * @author: yangm     
     * @date:   2017年11月7日 上午11:06:52   
     * @version V2.0.0.0
 */
@Entity
@Table(name = "Finance_vs_cashier_daily_acc")
public class FinanceVsCashierDailyAcc implements java.io.Serializable {
	private static final long serialVersionUID = -97502163798576023L;
	
	@Id
	@GenericGenerator(name = "idGenerator", strategy = "identity")
	@GeneratedValue(generator = "idGenerator")
	private long id;

	@Column(name="fd_acc_num")
	private String fd_acc_num;//财务部门日结号
	
	@Column(name="daily_acc_num")
	private String daily_acc_num;//收费员日结号
	
	@Column(name="is_active")
	private String is_active;

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

	public String getDaily_acc_num() {
		return daily_acc_num;
	}

	public void setDaily_acc_num(String daily_acc_num) {
		this.daily_acc_num = daily_acc_num;
	}

	public String getIs_active() {
		return is_active;
	}

	public void setIs_active(String is_active) {
		this.is_active = is_active;
	}
}
