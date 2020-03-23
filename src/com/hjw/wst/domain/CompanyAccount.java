package com.hjw.wst.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "company_account")
public class CompanyAccount implements java.io.Serializable {
	

	private static final long serialVersionUID = -97502163798576123L;

	@Id
	@GenericGenerator(name = "idGenerator", strategy = "identity")
	@GeneratedValue(generator = "idGenerator")
	private int id;//记录id
	
	@Column(name = "center_num")
	private String center_num;//体检中心编号
	
	@Column(name = "com_num")
	private String com_num;//单位编号
	
	@Column(name = "com_code")
	private String com_code;//企业代码
	
	@Column(name = "balance")
	private Double balance;//账户余额
	
	@Column(name = "com_type")
	private int com_type;//商户状态：0_正常 1_冻结 2_已销户
	
	@Column(name = "creater")
	private int creater;//创建者
	
	@Column(name = "create_date")
	private Date create_date;//创建时间
	
	@Column(name = "updater")
	private int updater;//修改者
	
	@Column(name = "update_date")
	private Date update_date;//修改时间
	
	@Column(name = "digitalSign")
	private String digitalSign;//数字签名
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCenter_num() {
		return center_num;
	}

	public void setCenter_num(String center_num) {
		this.center_num = center_num;
	}

	public String getCom_num() {
		return com_num;
	}

	public void setCom_num(String com_num) {
		this.com_num = com_num;
	}

	public String getCom_code() {
		return com_code;
	}

	public void setCom_code(String com_code) {
		this.com_code = com_code;
	}

	public Double getBalance() {
		return balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}

	public int getCom_type() {
		return com_type;
	}

	public void setCom_type(int com_type) {
		this.com_type = com_type;
	}

	public int getCreater() {
		return creater;
	}

	public void setCreater(int creater) {
		this.creater = creater;
	}

	public Date getCreate_date() {
		return create_date;
	}

	public void setCreate_date(Date create_date) {
		this.create_date = create_date;
	}

	public int getUpdater() {
		return updater;
	}

	public void setUpdater(int updater) {
		this.updater = updater;
	}

	public Date getUpdate_date() {
		return update_date;
	}

	public void setUpdate_date(Date update_date) {
		this.update_date = update_date;
	}

	public String getDigitalSign() {
		return digitalSign;
	}

	public void setDigitalSign(String digitalSign) {
		this.digitalSign = digitalSign;
	}
}
