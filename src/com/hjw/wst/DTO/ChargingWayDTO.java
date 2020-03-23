package com.hjw.wst.DTO;

public class ChargingWayDTO implements java.io.Serializable{

	private static final long serialVersionUID = -97502163798576023L;
	
	private long id;
	private long summary_id;
	private String charging_way;
	private Double amount;
	private String creater;
	private String create_time;
	private String updater;
	private String update_time;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getSummary_id() {
		return summary_id;
	}
	public void setSummary_id(long summary_id) {
		this.summary_id = summary_id;
	}
	public String getCharging_way() {
		return charging_way;
	}
	public void setCharging_way(String charging_way) {
		this.charging_way = charging_way;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public String getCreater() {
		return creater;
	}
	public void setCreater(String creater) {
		this.creater = creater;
	}
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	public String getUpdater() {
		return updater;
	}
	public void setUpdater(String updater) {
		this.updater = updater;
	}
	public String getUpdate_time() {
		return update_time;
	}
	public void setUpdate_time(String update_time) {
		this.update_time = update_time;
	}
}
