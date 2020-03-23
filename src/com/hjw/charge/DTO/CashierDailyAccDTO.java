package com.hjw.charge.DTO;

public class CashierDailyAccDTO implements java.io.Serializable {
	private static final long serialVersionUID = -97502163798576023L;

	private String daily_acc_num;
	private double daily_acc_amount;
	private String daily_acc_date;
	private long userId;
	private String user_name;
	private long charging_way;
	private String data_name;
	private double amount;
	private double invoice_amount;
	private String daily_status;
	private String daily_statuss;
	private String daily_acc_class_num;
	private double daily_acc_class_amount;
	private String daily_acc_class;
	private String daily_acc_classs;
	private String tran_code;
	private String tran_codes;
	private double persion_amount;//个人支付金额
	private String create_time;
	private int persion_count;//个人收费总笔数
	
	private Double settlement_amount;//结算总金额
	private int settlement_count;   
	
	private Double revoke_amount;//撤销总金额
	private int revoke_count;
	
	private Double correct_amount;//冲正总金额
	private int correct_count;
	public String getDaily_acc_class() {
		return daily_acc_class;
	}
	public void setDaily_acc_class(String daily_acc_class) {
		this.daily_acc_class = daily_acc_class;
		if(this.daily_acc_class.equals("001")){
			this.daily_acc_classs = "个人收费";
		}else if(this.daily_acc_class.equals("002")){
			this.daily_acc_classs = "团体结账";
		}else if(this.daily_acc_class.equals("003")){
			this.daily_acc_classs = "会员卡储值";
		}else if(this.daily_acc_class.equals("004")){
			this.daily_acc_classs = "单位储值";
		}
	}
	public String getDaily_acc_classs() {
		return daily_acc_classs;
	}
	public void setDaily_acc_classs(String daily_acc_classs) {
		this.daily_acc_classs = daily_acc_classs;
	}
	public String getTran_code() {
		return tran_code;
	}
	public void setTran_code(String tran_code) {
		this.tran_code = tran_code;
		if(this.tran_code.equals("101")){
			this.tran_codes = "收费";
		}else if(this.tran_code.equals("102")){
			this.tran_codes = "消费";
		}
	}
	public String getTran_codes() {
		return tran_codes;
	}
	public void setTran_codes(String tran_codes) {
		this.tran_codes = tran_codes;
	}
	public String getDaily_acc_class_num() {
		return daily_acc_class_num;
	}
	public void setDaily_acc_class_num(String daily_acc_class_num) {
		this.daily_acc_class_num = daily_acc_class_num;
	}
	public double getDaily_acc_class_amount() {
		return daily_acc_class_amount;
	}
	public void setDaily_acc_class_amount(double daily_acc_class_amount) {
		this.daily_acc_class_amount = daily_acc_class_amount;
	}
	public String getDaily_status() {
		return daily_status;
	}
	public void setDaily_status(String daily_status) {
		this.daily_status = daily_status;
		if(daily_status.equals("1")){
			this.daily_statuss = "已日结";
		}else{
			this.daily_statuss = "未日结";
		}
	}
	public String getDaily_statuss() {
		return daily_statuss;
	}
	public void setDaily_statuss(String daily_statuss) {
		this.daily_statuss = daily_statuss;
	}
	public String getDaily_acc_num() {
		return daily_acc_num;
	}
	public void setDaily_acc_num(String daily_acc_num) {
		this.daily_acc_num = daily_acc_num;
	}
	public double getDaily_acc_amount() {
		return daily_acc_amount;
	}
	public void setDaily_acc_amount(double daily_acc_amount) {
		this.daily_acc_amount = daily_acc_amount;
	}
	public String getDaily_acc_date() {
		return daily_acc_date;
	}
	public void setDaily_acc_date(String daily_acc_date) {
		this.daily_acc_date = daily_acc_date;
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public long getCharging_way() {
		return charging_way;
	}
	public void setCharging_way(long charging_way) {
		this.charging_way = charging_way;
	}
	public String getData_name() {
		return data_name;
	}
	public void setData_name(String data_name) {
		this.data_name = data_name;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public double getInvoice_amount() {
		return invoice_amount;
	}
	public void setInvoice_amount(double invoice_amount) {
		this.invoice_amount = invoice_amount;
	}
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	public int getPersion_count() {
		return persion_count;
	}
	public void setPersion_count(int persion_count) {
		this.persion_count = persion_count;
	}
	public Double getSettlement_amount() {
		return settlement_amount;
	}
	public void setSettlement_amount(Double settlement_amount) {
		this.settlement_amount = settlement_amount;
	}
	public int getSettlement_count() {
		return settlement_count;
	}
	public void setSettlement_count(int settlement_count) {
		this.settlement_count = settlement_count;
	}
	public Double getRevoke_amount() {
		return revoke_amount;
	}
	public void setRevoke_amount(Double revoke_amount) {
		this.revoke_amount = revoke_amount;
	}
	public int getRevoke_count() {
		return revoke_count;
	}
	public void setRevoke_count(int revoke_count) {
		this.revoke_count = revoke_count;
	}
	public Double getCorrect_amount() {
		return correct_amount;
	}
	public void setCorrect_amount(Double correct_amount) {
		this.correct_amount = correct_amount;
	}
	public int getCorrect_count() {
		return correct_count;
	}
	public void setCorrect_count(int correct_count) {
		this.correct_count = correct_count;
	}

	public double getPersion_amount() {
		return persion_amount;
	}

	public void setPersion_amount(double persion_amount) {
		this.persion_amount = persion_amount;
	}

    @Override
    public String toString() {
        return "CashierDailyAccDTO{" +
                "daily_acc_num='" + daily_acc_num + '\'' +
                ", daily_acc_amount=" + daily_acc_amount +
                ", daily_acc_date='" + daily_acc_date + '\'' +
                ", userId=" + userId +
                ", user_name='" + user_name + '\'' +
                ", charging_way=" + charging_way +
                ", data_name='" + data_name + '\'' +
                ", amount=" + amount +
                ", invoice_amount=" + invoice_amount +
                ", daily_status='" + daily_status + '\'' +
                ", daily_statuss='" + daily_statuss + '\'' +
                ", daily_acc_class_num='" + daily_acc_class_num + '\'' +
                ", daily_acc_class_amount=" + daily_acc_class_amount +
                ", daily_acc_class='" + daily_acc_class + '\'' +
                ", daily_acc_classs='" + daily_acc_classs + '\'' +
                ", tran_code='" + tran_code + '\'' +
                ", tran_codes='" + tran_codes + '\'' +
                ", persion_amount=" + persion_amount +
                ", create_time='" + create_time + '\'' +
                ", persion_count=" + persion_count +
                ", settlement_amount=" + settlement_amount +
                ", settlement_count=" + settlement_count +
                ", revoke_amount=" + revoke_amount +
                ", revoke_count=" + revoke_count +
                ", correct_amount=" + correct_amount +
                ", correct_count=" + correct_count +
                '}';
    }
}
