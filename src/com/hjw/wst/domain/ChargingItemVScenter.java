package com.hjw.wst.domain;

import java.util.Date;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;



@Entity
@Table(name="charging_item_vs_center")
public class ChargingItemVScenter {
	/**   
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)   
	 */ 
	//private String center_num;	
	// 体检中心编码
	//private String charging_item_code;			// 收费项目编码
	ChargingItemVScenterPKID chargingItemVScenterPKID = new ChargingItemVScenterPKID();
	
	private double price;						// 统一价格即原价
	private double calculation_amount;	        // 核算金额
	private int calculation_rate;							// 利润率
	private int charging_item_number;						// 限制次数
	private int item_discount;								// 项目折扣率
	private int limit_num;									// 每日体检人数
	private String charge_inter_num;					// 系统外编码
	private String interface_flag;					// 接口标示
	private double center_price;						// 本体检中心价格
	private String his_num;								// 本体检中心his关联码
	private String exam_num;						// 本体检中心lis关联码
	private String view_num;							// 本体检中心pacs关联码
	private String perform_dept;						// 本体检中心执行科室编码
	private long creater;											// 创建者
	private Date cerate_time;									// 创建时间
	private long updater;											// 更新者	
	private Date update_time;									// 更新时间
	
	@EmbeddedId 
	public ChargingItemVScenterPKID getChargingItemVScenterPKID() {
		return chargingItemVScenterPKID;
	}
	public void setChargingItemVScenterPKID(ChargingItemVScenterPKID chargingItemVScenterPKID) {
		this.chargingItemVScenterPKID = chargingItemVScenterPKID;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public double getCalculation_amount() {
		return calculation_amount;
	}
	public void setCalculation_amount(double calculation_amount) {
		this.calculation_amount = calculation_amount;
	}
	public int getCalculation_rate() {
		return calculation_rate;
	}
	public void setCalculation_rate(int calculation_rate) {
		this.calculation_rate = calculation_rate;
	}
	public int getCharging_item_number() {
		return charging_item_number;
	}
	public void setCharging_item_number(int charging_item_number) {
		this.charging_item_number = charging_item_number;
	}
	public int getItem_discount() {
		return item_discount;
	}
	public void setItem_discount(int item_discount) {
		this.item_discount = item_discount;
	}
	public int getLimit_num() {
		return limit_num;
	}
	public void setLimit_num(int limit_num) {
		this.limit_num = limit_num;
	}
	public String getCharge_inter_num() {
		return charge_inter_num;
	}
	public void setCharge_inter_num(String charge_inter_num) {
		this.charge_inter_num = charge_inter_num;
	}
	public String getInterface_flag() {
		return interface_flag;
	}
	public void setInterface_flag(String interface_flag) {
		this.interface_flag = interface_flag;
	}
	public double getCenter_price() {
		return center_price;
	}
	public void setCenter_price(double center_price) {
		this.center_price = center_price;
	}
	public String getHis_num() {
		return his_num;
	}
	public void setHis_num(String his_num) {
		this.his_num = his_num;
	}
	public String getExam_num() {
		return exam_num;
	}
	public void setExam_num(String exam_num) {
		this.exam_num = exam_num;
	}
	public String getView_num() {
		return view_num;
	}
	public void setView_num(String view_num) {
		this.view_num = view_num;
	}
	public String getPerform_dept() {
		return perform_dept;
	}
	public void setPerform_dept(String perform_dept) {
		this.perform_dept = perform_dept;
	}
	
	public Date getCerate_time() {
		return cerate_time;
	}
	public void setCerate_time(Date cerate_time) {
		this.cerate_time = cerate_time;
	}
	public Date getUpdate_time() {
		return update_time;
	}
	public void setUpdate_time(Date update_time) {
		this.update_time = update_time;
	}
	public long getCreater() {
		return creater;
	}
	public void setCreater(long creater) {
		this.creater = creater;
	}
	public long getUpdater() {
		return updater;
	}
	public void setUpdater(long updater) {
		this.updater = updater;
	}
	
	
}
