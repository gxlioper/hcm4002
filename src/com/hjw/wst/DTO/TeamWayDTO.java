package com.hjw.wst.DTO;

public class TeamWayDTO implements java.io.Serializable {
	private static final long serialVersionUID = -97502163798576023L;

	private long wayid;//
	private String wayname;//
	private double amount;//
	private String charging_way;
	private String data_code_children;//子编码 商户划账必须为 372
	public long getWayid() {
		return wayid;
	}
	public void setWayid(long wayid) {
		this.wayid = wayid;
	}
	public String getWayname() {
		return wayname;
	}
	public void setWayname(String wayname) {
		this.wayname = wayname;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getCharging_way() {
		return charging_way;
	}
	public void setCharging_way(String charging_way) {
		this.charging_way = charging_way;
	}
	public String getData_code_children() {
		return data_code_children;
	}
	public void setData_code_children(String data_code_children) {
		this.data_code_children = data_code_children;
	}
}
