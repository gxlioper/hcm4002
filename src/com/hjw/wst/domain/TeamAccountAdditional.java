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
     * @Description:  团体结算附加金额表
     * @author: dq     
     * @date:   2017年9月27日 下午2:31:24   
     * @version V2.0.0.0
 */
@Entity
@Table(name = "team_account_additional")
public class TeamAccountAdditional implements java.io.Serializable {
	private static final long serialVersionUID = -97502163798576023L;
	@Id
	@GenericGenerator(name = "idGenerator", strategy = "identity")
	@GeneratedValue(generator = "idGenerator")
	private long id;

	@Column(name = "acc_num")
	private String acc_num;//结算单号
	
	@Column(name = "item_id")
	private long item_id;//附加项目ID
	
	@Column(name = "item_name")
	private String item_name;//附加项目名称
	
	@Column(name = "amount")
	private double amount;//附加项目金额
	
	@Column(name = "creater")
	private long creater;
	
	@Column(name = "create_time")
	private Date create_time;
	
	@Column(name = "updater")
	private long updater;
	
	@Column(name = "update_time")
	private Date update_time;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getAcc_num() {
		return acc_num;
	}

	public void setAcc_num(String acc_num) {
		this.acc_num = acc_num;
	}

	public long getItem_id() {
		return item_id;
	}

	public void setItem_id(long item_id) {
		this.item_id = item_id;
	}

	public String getItem_name() {
		return item_name;
	}

	public void setItem_name(String item_name) {
		this.item_name = item_name;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
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

	public long getUpdater() {
		return updater;
	}

	public void setUpdater(long updater) {
		this.updater = updater;
	}

	public Date getUpdate_time() {
		return update_time;
	}

	public void setUpdate_time(Date update_time) {
		this.update_time = update_time;
	}
}
