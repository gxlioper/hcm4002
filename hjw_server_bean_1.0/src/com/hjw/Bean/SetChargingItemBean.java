package com.hjw.Bean;

/**
 * 
     * @Title:  火箭蛙体检管理系统   
     * @Package com.hjw.wst.domain   
     * @Description:  
     * @author: 收费项目    
     * @date:   2016年9月25日 下午4:08:22   
     * @version V2.0.0.0
 */

public class SetChargingItemBean implements java.io.Serializable {
	 private static final long serialVersionUID = -97502163798576023L;
	 private long id;
	 private long charging_item_id;
	 private String item_code;
	 private double discount;
	 private int itemnum=1;
	 private double amount;
	 private String create_time="";
	 private String update_time="";
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getCharging_item_id() {
		return charging_item_id;
	}
	public void setCharging_item_id(long charging_item_id) {
		this.charging_item_id = charging_item_id;
	}
	
	public String getItem_code() {
		return item_code;
	}
	public void setItem_code(String item_code) {
		this.item_code = item_code;
	}
	public double getDiscount() {
		return discount;
	}
	public void setDiscount(double discount) {
		this.discount = discount;
	}
	public int getItemnum() {
		return itemnum;
	}
	public void setItemnum(int itemnum) {
		this.itemnum = itemnum;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	public String getUpdate_time() {
		return update_time;
	}
	public void setUpdate_time(String update_time) {
		this.update_time = update_time;
	}

	}