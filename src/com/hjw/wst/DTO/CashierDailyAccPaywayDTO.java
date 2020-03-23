package com.hjw.wst.DTO;

public class CashierDailyAccPaywayDTO {

	private long id;
	private String daily_acc_num;
	private long charging_way;
	private String charging_ways;
	private double amonut;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getDaily_acc_num() {
		return daily_acc_num;
	}
	public void setDaily_acc_num(String daily_acc_num) {
		this.daily_acc_num = daily_acc_num;
	}
	public long getCharging_way() {
		return charging_way;
	}
	public void setCharging_way(long charging_way) {
		this.charging_way = charging_way;
	}
	public double getAmonut() {
		return amonut;
	}
	public void setAmonut(double amonut) {
		this.amonut = amonut;
	}
	public String getCharging_ways() {
		return charging_ways;
	}
	public void setCharging_ways(String charging_ways) {
		this.charging_ways = charging_ways;
	}
}
