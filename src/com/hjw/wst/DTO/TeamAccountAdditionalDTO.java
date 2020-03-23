package com.hjw.wst.DTO;

public class TeamAccountAdditionalDTO implements java.io.Serializable {
	private static final long serialVersionUID = -97502163798576023L;
	
	private String acc_num;//结算单号
	private long item_id;//附加项目ID
	private String item_name;//附加项目名称
	private double amount;//附加项目金额
	
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
}
