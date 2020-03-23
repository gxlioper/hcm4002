package com.hjw.wst.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "company_account_detail")
public class CompanyAccountDetail {
	@Id
	@GenericGenerator(name = "idGenerator", strategy = "identity")
	@GeneratedValue(generator = "idGenerator")
	private int id;//记录id

	@Column(name = "logicdate")
	private Date logicdate;//入帐日期
	
	@Column(name = "com_num")
	private String com_num;//商户单位编号
	
	@Column(name = "jnnumber")
	private String jnnumber;//交易流水号
	
	@Column(name = "prejnnumber")
	private String prejnnumber;//上笔流水号
	
	@Column(name = "account_num")
	private String account_num;//结帐号
	
	@Column(name = "creater")
	private int creater;//操作员
	
	@Column(name = "trancode")
	private String trancode;//交易类型代码
	
	@Column(name = "jnstatus")
	private int jnstatus;//流水状态
	
	@Column(name = "jndatetime")
	private Date jndatetime;//流水发生日期时间
	
	@Column(name = "balance")
	private Double balance;//账户余额
	
	@Column(name = "oldbalance")
	private Double oldbalance;//账户原余额
	
	@Column(name = "usednum")
	private int usednum;//累计使用次数
	
	@Column(name = "trantmt")
	private Double trantmt;//交易额
	
	@Column(name = "chargingway")
	private String chargingway;//支付方式
	
	@Column(name = "invaioce_type")
	private int invaioce_type;//开票状态
	
	@Column(name = "resume")
	private String resume;//摘要
	
	@Column(name = "digitalSign")
	private String digitalSign;//数字签名
	
	@Column(name = "invoice_id")
	private long invoice_id;//发票表id
	
	@Column(name = "center_num")
	private String center_num;   //体检中心编码
	
	public int getId() {
		return id;
	}

	public Date getLogicdate() {
		return logicdate;
	}

	public String getCom_num() {
		return com_num;
	}

	public String getJnnumber() {
		return jnnumber;
	}

	public String getPrejnnumber() {
		return prejnnumber;
	}

	public String getAccount_num() {
		return account_num;
	}

	public int getCreater() {
		return creater;
	}

	public String getTrancode() {
		return trancode;
	}

	public int getJnstatus() {
		return jnstatus;
	}

	public Date getJndatetime() {
		return jndatetime;
	}

	public Double getBalance() {
		return balance;
	}

	public Double getOldbalance() {
		return oldbalance;
	}

	public int getUsednum() {
		return usednum;
	}

	public Double getTrantmt() {
		return trantmt;
	}

	public String getChargingway() {
		return chargingway;
	}

	public int getInvaioce_type() {
		return invaioce_type;
	}

	public String getResume() {
		return resume;
	}

	public String getDigitalSign() {
		return digitalSign;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setLogicdate(Date logicdate) {
		this.logicdate = logicdate;
	}

	public void setCom_num(String com_num) {
		this.com_num = com_num;
	}

	public void setJnnumber(String jnnumber) {
		this.jnnumber = jnnumber;
	}

	public void setPrejnnumber(String prejnnumber) {
		this.prejnnumber = prejnnumber;
	}

	public void setAccount_num(String account_num) {
		this.account_num = account_num;
	}

	public void setCreater(int creater) {
		this.creater = creater;
	}

	public void setTrancode(String trancode) {
		this.trancode = trancode;
	}

	public void setJnstatus(int jnstatus) {
		this.jnstatus = jnstatus;
	}

	public void setJndatetime(Date jndatetime) {
		this.jndatetime = jndatetime;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}

	public void setOldbalance(Double oldbalance) {
		this.oldbalance = oldbalance;
	}

	public void setUsednum(int usednum) {
		this.usednum = usednum;
	}

	public void setTrantmt(Double trantmt) {
		this.trantmt = trantmt;
	}

	public void setChargingway(String chargingway) {
		this.chargingway = chargingway;
	}

	public void setInvaioce_type(int invaioce_type) {
		this.invaioce_type = invaioce_type;
	}

	public void setResume(String resume) {
		this.resume = resume;
	}

	public void setDigitalSign(String digitalSign) {
		this.digitalSign = digitalSign;
	}

	public long getInvoice_id() {
		return invoice_id;
	}

	public void setInvoice_id(long invoice_id) {
		this.invoice_id = invoice_id;
	}

	public String getCenter_num() {
		return center_num;
	}

	public void setCenter_num(String center_num) {
		this.center_num = center_num;
	}
	
}
