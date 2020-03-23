package com.hjw.charge.DTO;

public class InsureVerifyAccountDetailDTD {  // 医保对帐明细  返回数据
	
	  private String rec_id ;             
	  private String  trade_code ;        //业务交易码        
	  private String  center_cycle_code;   //中心业务周期号
	  private String  PatNo  ;             //个人医保编号
	  private String  peis_req_code;       //体检缴费申请单号
	  private String  peis_trade_code ;     //体检交易流水号
	  private String  center_trade_code;   //中心交易流水号
	  private double  total_amount;         //医疗费用总金额
	  private double  account_pay;			//本次账户支付
	  private double  cash_pay;				//本次现金支付
	  private double  plan_pay;				//统筹支付金额
	  private double  bailout_fund;			//救助基金支付
	  private double  person_fund_pay;		//个人补充基金支付
	  private double  min_living_pay;		//低保救助金额支付
	  private double  Enterprise_fund;		//企业补充基金支付
	  private double  civil_worker_pay;		//公务员补助支出金额
	  private double  disabled_pay;			//伤残人员医疗保障金
	  private String  acc_date;				//结算日期
	  private String  soft_house;			//开发商标识
	  private String  pat_class;			//医疗类型
	  private String  medical_treatment;	//医疗待遇类型
	  private String  solving_flag;			//问题处方标识
	  private String   create_time;			//入库时间
	  
	  
	public String getRec_id() {
		return rec_id;
	}
	public String getTrade_code() {
		return trade_code;
	}
	public String getCenter_cycle_code() {
		return center_cycle_code;
	}
	public String getPatNo() {
		return PatNo;
	}
	public String getPeis_req_code() {
		return peis_req_code;
	}
	public String getPeis_trade_code() {
		return peis_trade_code;
	}
	public String getCenter_trade_code() {
		return center_trade_code;
	}
	public double getTotal_amount() {
		return total_amount;
	}
	public double getAccount_pay() {
		return account_pay;
	}
	public double getCash_pay() {
		return cash_pay;
	}
	public double getPlan_pay() {
		return plan_pay;
	}
	public double getBailout_fund() {
		return bailout_fund;
	}
	public double getPerson_fund_pay() {
		return person_fund_pay;
	}
	public double getMin_living_pay() {
		return min_living_pay;
	}
	public double getEnterprise_fund() {
		return Enterprise_fund;
	}
	public double getCivil_worker_pay() {
		return civil_worker_pay;
	}
	public double getDisabled_pay() {
		return disabled_pay;
	}
	public String getAcc_date() {
		return acc_date;
	}
	public String getSoft_house() {
		return soft_house;
	}
	public String getPat_class() {
		return pat_class;
	}
	public String getMedical_treatment() {
		return medical_treatment;
	}
	public String getSolving_flag() {
		return solving_flag;
	}
	public String getCreate_time() {
		return create_time;
	}
	public void setRec_id(String rec_id) {
		this.rec_id = rec_id;
	}
	public void setTrade_code(String trade_code) {
		this.trade_code = trade_code;
	}
	public void setCenter_cycle_code(String center_cycle_code) {
		this.center_cycle_code = center_cycle_code;
	}
	public void setPatNo(String patNo) {
		PatNo = patNo;
	}
	public void setPeis_req_code(String peis_req_code) {
		this.peis_req_code = peis_req_code;
	}
	public void setPeis_trade_code(String peis_trade_code) {
		this.peis_trade_code = peis_trade_code;
	}
	public void setCenter_trade_code(String center_trade_code) {
		this.center_trade_code = center_trade_code;
	}
	public void setTotal_amount(double total_amount) {
		this.total_amount = total_amount;
	}
	public void setAccount_pay(double account_pay) {
		this.account_pay = account_pay;
	}
	public void setCash_pay(double cash_pay) {
		this.cash_pay = cash_pay;
	}
	public void setPlan_pay(double plan_pay) {
		this.plan_pay = plan_pay;
	}
	public void setBailout_fund(double bailout_fund) {
		this.bailout_fund = bailout_fund;
	}
	public void setPerson_fund_pay(double person_fund_pay) {
		this.person_fund_pay = person_fund_pay;
	}
	public void setMin_living_pay(double min_living_pay) {
		this.min_living_pay = min_living_pay;
	}
	public void setEnterprise_fund(double enterprise_fund) {
		Enterprise_fund = enterprise_fund;
	}
	public void setCivil_worker_pay(double civil_worker_pay) {
		this.civil_worker_pay = civil_worker_pay;
	}
	public void setDisabled_pay(double disabled_pay) {
		this.disabled_pay = disabled_pay;
	}
	public void setAcc_date(String acc_date) {
		this.acc_date = acc_date;
	}
	public void setSoft_house(String soft_house) {
		this.soft_house = soft_house;
	}
	public void setPat_class(String pat_class) {
		this.pat_class = pat_class;
	}
	public void setMedical_treatment(String medical_treatment) {
		this.medical_treatment = medical_treatment;
	}
	public void setSolving_flag(String solving_flag) {
		this.solving_flag = solving_flag;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}

}
