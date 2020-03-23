package com.hjw.charge.DTO;
/**
 * 收费项目价表关系
     * @Title:  火箭蛙体检管理系统   
     * @Package com.hjw.wst.DTO   
     * @Description:  
     * @author: yangm     
     * @date:   2019年10月30日 下午2:22:10   
     * @version V2.0.0.0
 */
public class ChargingItemMedicalPriceDTO {
	private	 long id;
	private	 String charge_item_code;
	private	 int medical_price_id;
	private	 int creater;
	private	 String create_date;
	private	 int updater;
	private	String update_date;
	private int item_num;
	private double price;
	
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public int getItem_num() {
		return item_num;
	}
	public void setItem_num(int item_num) {
		this.item_num = item_num;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getCharge_item_code() {
		return charge_item_code;
	}
	public void setCharge_item_code(String charge_item_code) {
		this.charge_item_code = charge_item_code;
	}
	public int getMedical_price_id() {
		return medical_price_id;
	}
	public void setMedical_price_id(int medical_price_id) {
		this.medical_price_id = medical_price_id;
	}
	public int getCreater() {
		return creater;
	}
	public void setCreater(int creater) {
		this.creater = creater;
	}
	public String getCreate_date() {
		return create_date;
	}
	public void setCreate_date(String create_date) {
		this.create_date = create_date;
	}
	public int getUpdater() {
		return updater;
	}
	public void setUpdater(int updater) {
		this.updater = updater;
	}
	public String getUpdate_date() {
		return update_date;
	}
	public void setUpdate_date(String update_date) {
		this.update_date = update_date;
	}
	
	
	
}
