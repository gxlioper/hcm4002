package com.hjw.charge.model;


public class MedicalModel {
	//价表
	private	long		id;
	private	String		item_class;
	private	String		item_name;
	private	String		item_spec;
	private	String		units;
	private	double		price;
	private	double		prefer_price;
	private	String		performed_by;
	private	String		input_code;
	private	String		class_on_inp_rcpt;
	private	String		class_on_outp_rcpt;
	private	String		class_on_reckoning;
	private	String		subj_code;
	private	String		memo;
	private	String		start_date;
	private	String		stop_date;
	private	long		creater;
	private	String		create_date;
	private	long		updater;
	private	String		update_date;
	private	String		is_active;
	private	String		expand1;
	//收费项目和价表关系
	private	 long c_id;
	private	 String c_charge_item_code;
	private	 int c_medical_price_id;
	private	 int c_creater;
	private	 String c_create_date;
	private	 int c_updater;
	private	String c_update_date;
	
	//价表医保关系
	private	long	m_id;
	private	long	medical_price_id;
	private	String	medical_item_code;
	private	int	item_num;
	private	String	medical_type;
	//省医保
	private String item_code;
	//伪列
	private String item_list;
	private String ids;
	
	
	public String getItem_code() {
		return item_code;
	}
	public void setItem_code(String item_code) {
		this.item_code = item_code;
	}
	public long getM_id() {
		return m_id;
	}
	public void setM_id(long m_id) {
		this.m_id = m_id;
	}
	public long getMedical_price_id() {
		return medical_price_id;
	}
	public void setMedical_price_id(long medical_price_id) {
		this.medical_price_id = medical_price_id;
	}
	public String getMedical_item_code() {
		return medical_item_code;
	}
	public void setMedical_item_code(String medical_item_code) {
		this.medical_item_code = medical_item_code;
	}
	public int getItem_num() {
		return item_num;
	}
	public void setItem_num(int item_num) {
		this.item_num = item_num;
	}
	public String getMedical_type() {
		return medical_type;
	}
	public void setMedical_type(String medical_type) {
		this.medical_type = medical_type;
	}
	public String getIds() {
		return ids;
	}
	public void setIds(String ids) {
		this.ids = ids;
	}
	public String getItem_list() {
		return item_list;
	}
	public void setItem_list(String item_list) {
		this.item_list = item_list;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getItem_class() {
		return item_class;
	}
	public void setItem_class(String item_class) {
		this.item_class = item_class;
	}
	public String getItem_name() {
		return item_name;
	}
	public void setItem_name(String item_name) {
		this.item_name = item_name;
	}
	public String getItem_spec() {
		return item_spec;
	}
	public void setItem_spec(String item_spec) {
		this.item_spec = item_spec;
	}
	public String getUnits() {
		return units;
	}
	public void setUnits(String units) {
		this.units = units;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public double getPrefer_price() {
		return prefer_price;
	}
	public void setPrefer_price(double prefer_price) {
		this.prefer_price = prefer_price;
	}
	public String getPerformed_by() {
		return performed_by;
	}
	public void setPerformed_by(String performed_by) {
		this.performed_by = performed_by;
	}
	public String getInput_code() {
		return input_code;
	}
	public void setInput_code(String input_code) {
		this.input_code = input_code;
	}
	public String getClass_on_inp_rcpt() {
		return class_on_inp_rcpt;
	}
	public void setClass_on_inp_rcpt(String class_on_inp_rcpt) {
		this.class_on_inp_rcpt = class_on_inp_rcpt;
	}
	public String getClass_on_outp_rcpt() {
		return class_on_outp_rcpt;
	}
	public void setClass_on_outp_rcpt(String class_on_outp_rcpt) {
		this.class_on_outp_rcpt = class_on_outp_rcpt;
	}
	public String getClass_on_reckoning() {
		return class_on_reckoning;
	}
	public void setClass_on_reckoning(String class_on_reckoning) {
		this.class_on_reckoning = class_on_reckoning;
	}
	public String getSubj_code() {
		return subj_code;
	}
	public void setSubj_code(String subj_code) {
		this.subj_code = subj_code;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public String getStart_date() {
		return start_date;
	}
	public void setStart_date(String start_date) {
		this.start_date = start_date;
	}
	public String getStop_date() {
		return stop_date;
	}
	public void setStop_date(String stop_date) {
		this.stop_date = stop_date;
	}
	public long getCreater() {
		return creater;
	}
	public void setCreater(long creater) {
		this.creater = creater;
	}
	public String getCreate_date() {
		return create_date;
	}
	public void setCreate_date(String create_date) {
		this.create_date = create_date;
	}
	public long getUpdater() {
		return updater;
	}
	public void setUpdater(long updater) {
		this.updater = updater;
	}
	public String getUpdate_date() {
		return update_date;
	}
	public void setUpdate_date(String update_date) {
		this.update_date = update_date;
	}
	public String getIs_active() {
		return is_active;
	}
	public void setIs_active(String is_active) {
		this.is_active = is_active;
	}
	public String getExpand1() {
		return expand1;
	}
	public void setExpand1(String expand1) {
		this.expand1 = expand1;
	}
	public long getC_id() {
		return c_id;
	}
	public void setC_id(long c_id) {
		this.c_id = c_id;
	}
	public String getC_charge_item_code() {
		return c_charge_item_code;
	}
	public void setC_charge_item_code(String c_charge_item_code) {
		this.c_charge_item_code = c_charge_item_code;
	}
	public int getC_medical_price_id() {
		return c_medical_price_id;
	}
	public void setC_medical_price_id(int c_medical_price_id) {
		this.c_medical_price_id = c_medical_price_id;
	}
	public int getC_creater() {
		return c_creater;
	}
	public void setC_creater(int c_creater) {
		this.c_creater = c_creater;
	}
	public String getC_create_date() {
		return c_create_date;
	}
	public void setC_create_date(String c_create_date) {
		this.c_create_date = c_create_date;
	}
	public int getC_updater() {
		return c_updater;
	}
	public void setC_updater(int c_updater) {
		this.c_updater = c_updater;
	}
	public String getC_update_date() {
		return c_update_date;
	}
	public void setC_update_date(String c_update_date) {
		this.c_update_date = c_update_date;
	}
	

}
