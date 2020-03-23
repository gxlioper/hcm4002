package com.hjw.charge.DTO;

import java.util.Date;

import javax.persistence.Column;

public class InsuranceFeeDetailDTO {
	private long id;
	
	private String req_num;//对应体检申请单号
	
	private String charging_item_code;//收费项目编号
	
	private Double charging_item_amount;//收费项目金额 保存eci表的实收金额
	
	private long medical_price_id;//火箭蛙价格项目id 保存medical_price_list表id
	
	private int medical_price_num;//火箭蛙价格项目个数 
	
	private Double medical_old_price;//价表单价
	
	private Double medical_new_price;//项目实际单价
	
	private String medical_item_code;//医保项目编码
	
	private int medical_item_num;//医保项目个数
	
	private String medical_type;//医保类型  02市医保 03 省医保
	
	private String business_type;//业务类型  1表示个人收费 2表示售卡
	
	private long creater;
	
	private Date create_time;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getReq_num() {
		return req_num;
	}

	public void setReq_num(String req_num) {
		this.req_num = req_num;
	}

	public String getCharging_item_code() {
		return charging_item_code;
	}

	public void setCharging_item_code(String charging_item_code) {
		this.charging_item_code = charging_item_code;
	}

	public Double getCharging_item_amount() {
		return charging_item_amount;
	}

	public void setCharging_item_amount(Double charging_item_amount) {
		this.charging_item_amount = charging_item_amount;
	}

	public long getMedical_price_id() {
		return medical_price_id;
	}

	public void setMedical_price_id(long medical_price_id) {
		this.medical_price_id = medical_price_id;
	}

	public int getMedical_price_num() {
		return medical_price_num;
	}

	public void setMedical_price_num(int medical_price_num) {
		this.medical_price_num = medical_price_num;
	}

	public Double getMedical_old_price() {
		return medical_old_price;
	}

	public void setMedical_old_price(Double medical_old_price) {
		this.medical_old_price = medical_old_price;
	}

	public Double getMedical_new_price() {
		return medical_new_price;
	}

	public void setMedical_new_price(Double medical_new_price) {
		this.medical_new_price = medical_new_price;
	}

	public String getMedical_item_code() {
		return medical_item_code;
	}

	public void setMedical_item_code(String medical_item_code) {
		this.medical_item_code = medical_item_code;
	}

	public int getMedical_item_num() {
		return medical_item_num;
	}

	public void setMedical_item_num(int medical_item_num) {
		this.medical_item_num = medical_item_num;
	}

	public String getMedical_type() {
		return medical_type;
	}

	public void setMedical_type(String medical_type) {
		this.medical_type = medical_type;
	}

	public String getBusiness_type() {
		return business_type;
	}

	public void setBusiness_type(String business_type) {
		this.business_type = business_type;
	}

	public long getCreater() {
		return creater;
	}

	public void setCreater(long creater) {
		this.creater = creater;
	}

	public Date getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}

	

}
