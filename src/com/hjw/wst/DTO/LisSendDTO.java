package com.hjw.wst.DTO;

public class LisSendDTO {

	private long id;
	private String pay_status;
	private String item_name;
	private String exam_num;
	private String sample_barcode;
	private String remark;
	private String data_name;
	private String dept_code;
	private String dept_name;
	private long   chargingitemId; 
	private String item_code;
	private double item_amount;
	private double amount;
	
	private String his_num;	
	private String hiscodeClass;
	
	public String getHis_num() {
		return his_num;
	}
	public void setHis_num(String his_num) {
		this.his_num = his_num;
	}
	public String getHiscodeClass() {
		return hiscodeClass;
	}
	public void setHiscodeClass(String hiscodeClass) {
		this.hiscodeClass = hiscodeClass;
	}
	public long getChargingitemId() {
		return chargingitemId;
	}
	public void setChargingitemId(long chargingitemId) {
		this.chargingitemId = chargingitemId;
	}
	public String getItem_code() {
		return item_code;
	}
	public void setItem_code(String item_code) {
		this.item_code = item_code;
	}
	public double getItem_amount() {
		return item_amount;
	}
	public void setItem_amount(double item_amount) {
		this.item_amount = item_amount;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public String getItem_name() {
		return item_name;
	}
	public void setItem_name(String item_name) {
		this.item_name = item_name;
	}
	public String getExam_num() {
		return exam_num;
	}
	public void setExam_num(String exam_num) {
		this.exam_num = exam_num;
	}
	public String getSample_barcode() {
		return sample_barcode;
	}
	public void setSample_barcode(String sample_barcode) {
		this.sample_barcode = sample_barcode;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getData_name() {
		return data_name;
	}
	public void setData_name(String data_name) {
		this.data_name = data_name;
	}
	public String getDept_code() {
		return dept_code;
	}
	public void setDept_code(String dept_code) {
		this.dept_code = dept_code;
	}
	public String getDept_name() {
		return dept_name;
	}
	public void setDept_name(String dept_name) {
		this.dept_name = dept_name;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getPay_status() {
		return pay_status;
	}
	public void setPay_status(String pay_status) {
		this.pay_status = pay_status;
	}
}
