package com.hjw.wst.DTO;

import java.util.Date;
public class CompanyAccountDTO {
	
	private int id;//记录id
	
	private String center_num;//体检中心编号
	
	private String com_num;//单位编号
	
	private String com_code;//企业代码
	
	private Double balance;//账户余额
	
	private int com_type;//商户状态：0_正常 1_冻结 2_已销户
	
	private String creater;//创建者
	
	private String create_date;//创建时间
	
	private String updater;//修改者
	
	private String update_date;//修改时间
	
	private String digitalSign;//数字签名
	
	private String com_type_s;
	
	private Double amount;

	public int getId() {
		return id;
	}

	public String getCenter_num() {
		return center_num;
	}

	public String getCom_num() {
		return com_num;
	}

	public String getCom_code() {
		return com_code;
	}

	public Double getBalance() {
		return balance;
	}

	public int getCom_type() {
		return com_type;
	}

	public String getCreater() {
		return creater;
	}

	public String getCreate_date() {
		return create_date;
	}

	public String getUpdater() {
		return updater;
	}

	public String getUpdate_date() {
		return update_date;
	}

	public String getDigitalSign() {
		return digitalSign;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setCenter_num(String center_num) {
		this.center_num = center_num;
	}

	public void setCom_num(String com_num) {
		this.com_num = com_num;
	}

	public void setCom_code(String com_code) {
		this.com_code = com_code;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}

	public void setCom_type(int com_type) {//0_正常 1_冻结 2_已销户
		this.com_type = com_type;
		if(com_type == 0){
			this.setCom_type_s("正常");
		}else if(com_type == 1){
			this.setCom_type_s("冻结");
		}else if(com_type == 2){
			this.setCom_type_s("已销户");
		}
	}

	public void setCreater(String creater) {
		this.creater = creater;
	}

	public void setCreate_date(String create_date) {
		this.create_date = create_date;
	}

	public void setUpdater(String updater) {
		this.updater = updater;
	}

	public void setUpdate_date(String update_date) {
		this.update_date = update_date;
	}

	public void setDigitalSign(String digitalSign) {
		this.digitalSign = digitalSign;
	}

	public String getCom_type_s() {
		return com_type_s;
	}

	public void setCom_type_s(String com_type_s) {
		this.com_type_s = com_type_s;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}
	
}
