package com.hjw.wst.DTO;

public class ExamResultChargingItemDTO implements java.io.Serializable{

	private static final long serialVersionUID = -97502163798576023L;
	
	private long id;
	private long item_id;
	private String item_code;
	private String item_name;
	private String dep_name;
	private String exam_status;
	private String exam_status_y;
	private Double item_amount;
	private Double discount;
	private Double amount;
	private Double personal_pay;
	private Double team_pay;
	private String creater;
	private String create_time;
	private String his_req_status;
	private String his_req_status_y;
	
	private String invoice_num;
	private String invoice_class;
	private Double invoice_amount;
	private String req_num;
	private String is_application;
	private String is_application_y;
	private String account_num;
	private String exam_num;
	private String charging_status;
	private double calculation_amount;
	private int calculation_rate;
	private Double all_amount;
	private long itemnum;
	private String bar_code;
	
	private Double tj_charge_amount;    
	private Double his_charge_amount;
	private String pay_mode;
	private String pay_mode_y;
	private String his_charge_status;
	private String tj_charge_status;
	private String pay_status;
	private String is_print_recepit;//是否打印发票
	private int item_discount;
	public String getExam_num() {
		return exam_num;
	}
	public void setExam_num(String exam_num) {
		this.exam_num = exam_num;
	}
	public String getInvoice_class() {
		return invoice_class;
	}
	public void setInvoice_class(String invoice_class) {
		this.invoice_class = invoice_class;
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
	public String getBar_code() {
		return bar_code;
	}
	public void setBar_code(String bar_code) {
		this.bar_code = bar_code;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getItem_id() {
		return item_id;
	}
	public void setItem_id(long item_id) {
		this.item_id = item_id;
	}
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
	public String getDep_name() {
		return dep_name;
	}
	public void setDep_name(String dep_name) {
		this.dep_name = dep_name;
	}
	public String getExam_status() {
		return exam_status;
	}
	public void setExam_status(String exam_status) {
		this.exam_status = exam_status;
		if("N".equals(exam_status)){
			this.setExam_status_y("未检");
		}else if("Y".equals(exam_status)){
			this.setExam_status_y("已检");
		}else if("G".equals(exam_status)){
			this.setExam_status_y("弃检");
		}else if("D".equals(exam_status)){
			this.setExam_status_y("延期");
		}else if("C".equals(exam_status)){
			this.setExam_status_y("已登记");
		}
	}
	public Double getItem_amount() {
		return item_amount;
	}
	public void setItem_amount(Double item_amount) {
		this.item_amount = item_amount;
	}
	public Double getDiscount() {
		return discount;
	}
	public void setDiscount(Double discount) {
		this.discount = discount;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public Double getPersonal_pay() {
		return personal_pay;
	}
	public void setPersonal_pay(Double personal_pay) {
		this.personal_pay = personal_pay;
	}
	public Double getTeam_pay() {
		return team_pay;
	}
	public void setTeam_pay(Double team_pay) {
		this.team_pay = team_pay;
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
	public String getHis_req_status() {
		return his_req_status;
	}
	public void setHis_req_status(String his_req_status) {
		this.his_req_status = his_req_status;
		if("Y".equals(his_req_status)){
			this.setHis_req_status_y("已申请");
		}else{
			this.setHis_req_status_y("未申请");
		}
	}
	public String getHis_req_status_y() {
		return his_req_status_y;
	}
	public void setHis_req_status_y(String his_req_status_y) {
		this.his_req_status_y = his_req_status_y;
	}
	public String getInvoice_num() {
		return invoice_num;
	}
	public void setInvoice_num(String invoice_num) {
		this.invoice_num = invoice_num;
	}
	public String getReq_num() {
		return req_num;
	}
	public void setReq_num(String req_num) {
		this.req_num = req_num;
	}
	public String getIs_application() {
		return is_application;
	}
	public void setIs_application(String is_application) {
		this.is_application = is_application;
		if("Y".equals(is_application)){
			this.setIs_application_y("已申请");
		}else{
			this.setIs_application_y("未申请");
		}
	}
	public String getExam_status_y() {
		return exam_status_y;
	}
	public void setExam_status_y(String exam_status_y) {
		this.exam_status_y = exam_status_y;
	}
	public String getIs_application_y() {
		return is_application_y;
	}
	public void setIs_application_y(String is_application_y) {
		this.is_application_y = is_application_y;
	}
	public String getAccount_num() {
		return account_num;
	}
	public void setAccount_num(String account_num) {
		this.account_num = account_num;
	}
	public String getCharging_status() {
		return charging_status;
	}
	public void setCharging_status(String charging_status) {
		this.charging_status = charging_status;
	}
	public Double getAll_amount() {
		return all_amount;
	}
	public void setAll_amount(Double all_amount) {
		this.all_amount = all_amount;
	}
	public Double getInvoice_amount() {
		return invoice_amount;
	}
	public void setInvoice_amount(Double invoice_amount) {
		this.invoice_amount = invoice_amount;
	}
	public long getItemnum() {
		return itemnum;
	}
	public void setItemnum(long itemnum) {
		this.itemnum = itemnum;
	}
	public Double getTj_charge_amount() {
		return tj_charge_amount;
	}
	public void setTj_charge_amount(Double tj_charge_amount) {
		this.tj_charge_amount = tj_charge_amount;
	}
	public Double getHis_charge_amount() {
		return his_charge_amount;
	}
	public void setHis_charge_amount(Double his_charge_amount) {
		this.his_charge_amount = his_charge_amount;
	}
	public String getPay_mode() {
		return pay_mode;
	}
	public void setPay_mode(String pay_mode) {
		this.pay_mode = pay_mode;
		if(pay_mode.equals("0")){
			this.setPay_mode_y("体检收费");
		}else if(pay_mode.equals("1")){
			this.setPay_mode_y("his收费");
		}else if(pay_mode.equals("2")){
			this.setPay_mode_y("混合收费");
		}
	}
	public String getPay_mode_y() {
		return pay_mode_y;
	}
	public void setPay_mode_y(String pay_mode_y) {
		this.pay_mode_y = pay_mode_y;
	}
	public String getHis_charge_status() {
		return his_charge_status;
	}
	public void setHis_charge_status(String his_charge_status) {
		this.his_charge_status = his_charge_status;
	}
	public String getTj_charge_status() {
		return tj_charge_status;
	}
	public void setTj_charge_status(String tj_charge_status) {
		this.tj_charge_status = tj_charge_status;
	}
	public String getPay_status() {
		return pay_status;
	}
	public void setPay_status(String pay_status) {
		this.pay_status = pay_status;
	}
	public String getIs_print_recepit() {
		return is_print_recepit;
	}
	public void setIs_print_recepit(String is_print_recepit) {
		this.is_print_recepit = is_print_recepit;
	}
	public int getItem_discount() {
		return item_discount;
	}
	public void setItem_discount(int item_discount) {
		this.item_discount = item_discount;
	}
	
}
