package com.hjw.wst.DTO;

public class FinanceDepAccPaywayDTO {

	private long charging_way_id;
	private String charging_way;
	private double amount;
	public long getCharging_way_id() {
		return charging_way_id;
	}
	public void setCharging_way_id(long charging_way_id) {
		this.charging_way_id = charging_way_id;
	}
	public String getCharging_way() {
		return charging_way;
	}
	public void setCharging_way(String charging_way) {
		this.charging_way = charging_way;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
}
