package com.hjw.charge.DTO;

public class DictProvInsuranceClinicItemDTO {

	private String item_code;   //诊疗项目编码
	private String item_name;   //项目名称
	private String item_code_prov;   //诊疗项目编码(省医保)
	private String item_name_prov;   //项目名称(省医保)
	private String short_code;   //拼音助记码
	private String five_code;   //五笔助记码
	private String fee_class;   //收费类别
	private String fee_level;   //收费项目等级
	private String price;   	//价格
	private String date_start;   //开始日期
	private String date_end;   //终止日期
	private String valid_flag;   //有效标志
	private String operator;   //经办人
	private String op_date;   //经办日期
	private String top_fee;   //最高限价
	
	
	public String getItem_code() {
		return item_code;
	}
	public void setItem_code(String item_code) {
		this.item_code = item_code;
	}
	public String getItem_name() {
		return item_name;
	}
	public void setItem_name(String item_name) {
		this.item_name = item_name;
	}
	public String getShort_code() {
		return short_code;
	}
	public void setShort_code(String short_code) {
		this.short_code = short_code;
	}
	public String getFive_code() {
		return five_code;
	}
	public void setFive_code(String five_code) {
		this.five_code = five_code;
	}
	public String getFee_class() {
		return fee_class;
	}
	public void setFee_class(String fee_class) {
		this.fee_class = fee_class;
	}
	public String getFee_level() {
		return fee_level;
	}
	public void setFee_level(String fee_level) {
		this.fee_level = fee_level;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getDate_start() {
		return date_start;
	}
	public void setDate_start(String date_start) {
		this.date_start = date_start;
	}
	public String getDate_end() {
		return date_end;
	}
	public void setDate_end(String date_end) {
		this.date_end = date_end;
	}
	public String getValid_flag() {
		return valid_flag;
	}
	public void setValid_flag(String valid_flag) {
		this.valid_flag = valid_flag;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public String getOp_date() {
		return op_date;
	}
	public void setOp_date(String op_date) {
		this.op_date = op_date;
	}
	public String getTop_fee() {
		return top_fee;
	}
	public void setTop_fee(String top_fee) {
		this.top_fee = top_fee;
	}
	public String getItem_code_prov() {
		return item_code_prov;
	}
	public void setItem_code_prov(String item_code_prov) {
		this.item_code_prov = item_code_prov;
	}
	public String getItem_name_prov() {
		return item_name_prov;
	}
	public void setItem_name_prov(String item_name_prov) {
		this.item_name_prov = item_name_prov;
	}
	
	
}
