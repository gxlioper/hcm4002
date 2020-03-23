package com.hjw.wst.model;

import java.util.Date;

public class ExamInfoModel implements java.io.Serializable {

	private static final long serialVersionUID = -8329309504856348803L;
    private long id;
    private long group_id;
    private long customer_id;
    private String exam_num="";
    private String status="";
    private Date register_date=null;
    private Date join_date=null;
    private Date final_date=null;
    private String final_doctor="";
    private String exam_type="";
    private String customer_type="";
    private String is_sampled_directly="";
    private String is_adjusted="";
    private String center_num="";
    private String getReportWay="";
    private String reportAddress="";
    private String chargingType="";
    private String customerType="";
    private String group_index="";
    private String is_Active="";
    private long creater;
    private Date create_time=null;
    private long updater;
    private Date update_time=null;
    private String is_guide_back="";
    private String company_check_status="";
    private long customer_type_id;
    private String is_marriage="";
    private int age;
    private String address="";
    private String email="";
    private String phone="";
    private String company="";
    private String position="";
    private String _level="";
    private String picture_path="";
    private String is_after_pay="";
    private String past_medical_history="";
    private String remarke="";
    private String introducer="";
    private String counter_check="";
    private String guide_nurse="";
    private String appointment="";
    private String data_source="";
    private String others="";
    private String order_id="";
    private String exam_times="";
    private String is_report_print="";
    private String id_num="";
    private String user_name="";
    private String arch_num="";
    private long freeze;//0表示不冻结，1表示冻结	
    private int ren_type;
    private long reprot_type;
    private String isDjdAutoRecover;
    
    private String DJD_path;
    private String start_date;
    private String end_date;
	
    public int getRen_type() {
		return ren_type;
	}

	public void setRen_type(int ren_type) {
		this.ren_type = ren_type;
	}

	public long getFreeze() {
		return freeze;
	}

	public void setFreeze(long freeze) {
		this.freeze = freeze;
	}    
    
    
    
    public ExamInfoModel() {
		// TODO Auto-generated constructor stub
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getGroup_id() {
		return group_id;
	}

	public void setGroup_id(long group_id) {
		this.group_id = group_id;
	}

	public long getCustomer_id() {
		return customer_id;
	}

	public void setCustomer_id(long customer_id) {
		this.customer_id = customer_id;
	}

	public String getExam_num() {
		return exam_num;
	}

	public void setExam_num(String exam_num) {
		this.exam_num = exam_num;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getRegister_date() {
		return register_date;
	}

	public void setRegister_date(Date register_date) {
		this.register_date = register_date;
	}

	public Date getJoin_date() {
		return join_date;
	}

	public void setJoin_date(Date join_date) {
		this.join_date = join_date;
	}

	public Date getFinal_date() {
		return final_date;
	}

	public void setFinal_date(Date final_date) {
		this.final_date = final_date;
	}

	public String getFinal_doctor() {
		return final_doctor;
	}

	public void setFinal_doctor(String final_doctor) {
		this.final_doctor = final_doctor;
	}

	public String getExam_type() {
		return exam_type;
	}

	public void setExam_type(String exam_type) {
		this.exam_type = exam_type;
	}

	public String getCustomer_type() {
		return customer_type;
	}

	public void setCustomer_type(String customer_type) {
		this.customer_type = customer_type;
	}

	public String getIs_sampled_directly() {
		return is_sampled_directly;
	}

	public void setIs_sampled_directly(String is_sampled_directly) {
		this.is_sampled_directly = is_sampled_directly;
	}

	public String getIs_adjusted() {
		return is_adjusted;
	}

	public void setIs_adjusted(String is_adjusted) {
		this.is_adjusted = is_adjusted;
	}

	public String getCenter_num() {
		return center_num;
	}

	public void setCenter_num(String center_num) {
		this.center_num = center_num;
	}

	public String getGetReportWay() {
		return getReportWay;
	}

	public void setGetReportWay(String getReportWay) {
		this.getReportWay = getReportWay;
	}

	public String getReportAddress() {
		return reportAddress;
	}

	public void setReportAddress(String reportAddress) {
		this.reportAddress = reportAddress;
	}

	public String getChargingType() {
		return chargingType;
	}

	public void setChargingType(String chargingType) {
		this.chargingType = chargingType;
	}

	public String getCustomerType() {
		return customerType;
	}

	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}

	public String getGroup_index() {
		return group_index;
	}

	public void setGroup_index(String group_index) {
		this.group_index = group_index;
	}

	public String getIs_Active() {
		return is_Active;
	}

	public void setIs_Active(String is_Active) {
		this.is_Active = is_Active;
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

	public long getUpdater() {
		return updater;
	}

	public void setUpdater(long updater) {
		this.updater = updater;
	}

	public Date getUpdate_time() {
		return update_time;
	}

	public void setUpdate_time(Date update_time) {
		this.update_time = update_time;
	}

	public String getIs_guide_back() {
		return is_guide_back;
	}

	public void setIs_guide_back(String is_guide_back) {
		this.is_guide_back = is_guide_back;
	}

	public String getCompany_check_status() {
		return company_check_status;
	}

	public void setCompany_check_status(String company_check_status) {
		this.company_check_status = company_check_status;
	}

	public long getCustomer_type_id() {
		return customer_type_id;
	}

	public void setCustomer_type_id(long customer_type_id) {
		this.customer_type_id = customer_type_id;
	}

	public String getIs_marriage() {
		return is_marriage;
	}

	public void setIs_marriage(String is_marriage) {
		this.is_marriage = is_marriage;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String get_level() {
		return _level;
	}

	public void set_level(String _level) {
		this._level = _level;
	}

	public String getPicture_path() {
		return picture_path;
	}

	public void setPicture_path(String picture_path) {
		this.picture_path = picture_path;
	}

	public String getIs_after_pay() {
		return is_after_pay;
	}

	public void setIs_after_pay(String is_after_pay) {
		this.is_after_pay = is_after_pay;
	}

	public String getPast_medical_history() {
		return past_medical_history;
	}

	public void setPast_medical_history(String past_medical_history) {
		this.past_medical_history = past_medical_history;
	}

	public String getRemarke() {
		return remarke;
	}

	public void setRemarke(String remarke) {
		this.remarke = remarke;
	}

	public String getIntroducer() {
		return introducer;
	}

	public void setIntroducer(String introducer) {
		this.introducer = introducer;
	}

	public String getCounter_check() {
		return counter_check;
	}

	public void setCounter_check(String counter_check) {
		this.counter_check = counter_check;
	}

	public String getGuide_nurse() {
		return guide_nurse;
	}

	public void setGuide_nurse(String guide_nurse) {
		this.guide_nurse = guide_nurse;
	}

	public String getAppointment() {
		return appointment;
	}

	public void setAppointment(String appointment) {
		this.appointment = appointment;
	}

	public String getData_source() {
		return data_source;
	}

	public void setData_source(String data_source) {
		this.data_source = data_source;
	}

	public String getOthers() {
		return others;
	}

	public void setOthers(String others) {
		this.others = others;
	}

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public String getExam_times() {
		return exam_times;
	}

	public void setExam_times(String exam_times) {
		this.exam_times = exam_times;
	}

	public String getIs_report_print() {
		return is_report_print;
	}

	public void setIs_report_print(String is_report_print) {
		this.is_report_print = is_report_print;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getId_num() {
		return id_num;
	}

	public void setId_num(String id_num) {
		this.id_num = id_num;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getArch_num() {
		return arch_num;
	}

	public void setArch_num(String arch_num) {
		this.arch_num = arch_num;
	}

	public long getReprot_type() {
		return reprot_type;
	}

	public void setReprot_type(long reprot_type) {
		this.reprot_type = reprot_type;
	}

	public String getDJD_path() {
		return DJD_path;
	}

	public void setDJD_path(String dJD_path) {
		DJD_path = dJD_path;
	}

	public String getIsDjdAutoRecover() {
		return isDjdAutoRecover;
	}

	public void setIsDjdAutoRecover(String isDjdAutoRecover) {
		this.isDjdAutoRecover = isDjdAutoRecover;
	}

	public String getStart_date() {
		return start_date;
	}

	public void setStart_date(String start_date) {
		this.start_date = start_date;
	}

	public String getEnd_date() {
		return end_date;
	}

	public void setEnd_date(String end_date) {
		this.end_date = end_date;
	}
    
}
