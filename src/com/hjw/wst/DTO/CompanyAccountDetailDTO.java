package com.hjw.wst.DTO;

import java.util.Date;

public class CompanyAccountDetailDTO {
	private int id;//记录id

	private String logicdate;//入帐日期
	
	private String com_num;//商户单位编号
	
	private String jnnumber;//交易流水号
	
	private String prejnnumber;//上笔流水号
	
	private String account_num;//结帐号
	
	private String creater;//操作员
	
	private String trancode;//交易类型代码
	
	private int jnstatus;//流水状态
	
	private String jndatetime;//流水发生日期时间
	
	private Double balance;//账户余额
	
	private Double oldbalance;//账户原余额
	
	private int usednum;//累计使用次数
	
	private Double trantmt;//交易额
	
	private String chargingway;//支付方式
	
	private int invaioce_type;//开票状态
	
	private String resume;//摘要
	
	private String digitalSign;//数字签名
	
	private String transactionType;//交易类型  1充值  2消费  
	
	private  String status;//1 有效、2作废，3撤销   流水状态 
	
	private String chargingwaycode;//支付方式子id

	public int getId() {
		return id;
	}

	public String getLogicdate() {
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

	public String getCreater() {
		return creater;
	}

	public String getTrancode() {
		return trancode;
	}

	public int getJnstatus() {
		return jnstatus;
	}

	public String getJndatetime() {
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

	public void setLogicdate(String logicdate) {
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

	public void setCreater(String creater) {
		this.creater = creater;
	}

	public void setTrancode(String trancode) {
		this.trancode = trancode;
		if (trancode.equals("001")) {
			this.setTransactionType("充值");
		}else if(trancode.equals("002")){
			this.setTransactionType("消费");
		}else if(trancode.equals("003")){
			this.setTransactionType("撤销充值");
		}else if(trancode.equals("004")){
			this.setTransactionType("撤销消费");
		}
	}

	public void setJnstatus(int jnstatus) {
		this.jnstatus = jnstatus;
		if (jnstatus == 1) {
			this.setStatus("有效");
		}else if (jnstatus == 2) {
			this.setStatus("作废");
		}else if (jnstatus == 3) {
			this.setStatus("撤销");
		}
	}

	public void setJndatetime(String jndatetime) {
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

	public String getTransactionType() {
		return transactionType;
	}

	public String getStatus() {
		return status;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getChargingwaycode() {
		return chargingwaycode;
	}

	public void setChargingwaycode(String chargingwaycode) {
		this.chargingwaycode = chargingwaycode;
	}
	
	

}
