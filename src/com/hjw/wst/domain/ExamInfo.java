package com.hjw.wst.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "exam_info")
public class ExamInfo implements java.io.Serializable {
	 private static final long serialVersionUID = -97502163798576023L;

		@Id
		@GenericGenerator(name = "idGenerator", strategy = "identity")
		@GeneratedValue(generator = "idGenerator")
		private long id;

		@Column(name = "group_id")
        private long group_id;
        
		@Column(name = "customer_id")
		private long customer_id;
		
		@Column(name = "exam_num")
		private String exam_num="";
		
		@Column(name = "status")
		private String status="";
		
		@Column(name = "register_date")
		private String register_date=null;
		
		@Column(name = "join_date")
		private String join_date=null;
		
		@Column(name = "final_date")
		private String final_date=null;
		
		@Column(name = "final_doctor")
		private String final_doctor="";
		
		@Column(name = "exam_type")
		private String exam_type="";
		
		@Column(name = "customer_type")
		private String customer_type="";
		
		@Column(name = "is_sampled_directly")
		private String is_sampled_directly="";
		
		@Column(name = "is_adjusted")
		private String is_adjusted="";
		
		@Column(name = "center_num")
		private String center_num="";
		
		@Column(name = "getReportWay")
		private String getReportWay="";
		
		@Column(name = "reportAddress")
		private String reportAddress="";
		
		@Column(name = "chargingType")
		private String chargingType="";
		
		@Column(name = "customerType")
		private String customerType="";
		
		@Column(name = "group_index")
		private String group_index="";
		
		@Column(name = "is_Active")
		private String is_Active="";
		
		@Column(name = "creater")
		private long creater;
		
		@Column(name = "create_time")
		private String create_time=null;
		
		@Column(name = "updater")
		private long updater;
		
		@Column(name = "update_time")
		private String update_time=null;
		
		@Column(name = "is_guide_back")
		private String is_guide_back="";
		
		@Column(name = "company_check_status")
		private String company_check_status="";
		
		@Column(name = "customer_type_id")
		private long customer_type_id;
		
		@Column(name = "is_marriage")
		private String is_marriage="";
		
		@Column(name = "age")
		private long age;
		
		@Column(name = "address")
		private String address="";
		
		@Column(name = "email")
		private String email="";
		
		@Column(name = "phone")
		private String phone="";
		
		@Column(name = "company")
		private String company="";
		
		@Column(name = "position")
		private String position="";
		
		@Column(name = "_level")
		private String _level="";
		
		@Column(name = "picture_path")
		private String picture_path="";
		
		@Column(name = "is_after_pay")
		private String is_after_pay="";
		
		@Column(name = "past_medical_history")
		private String past_medical_history="";
		
		@Column(name = "remarke")
		private String remarke="";
		
		@Column(name = "introducer")
		private String introducer="";
		
		@Column(name = "counter_check")
		private String counter_check="";
		
		@Column(name = "guide_nurse")
		private String guide_nurse="";
		
		@Column(name = "appointment")
		private String appointment="";
		
		@Column(name = "data_source")
		private String data_source="";
		
		@Column(name = "others")
		private String others="";
		
		@Column(name = "order_id")
		private String order_id="";
		
		@Column(name="is_report_print")
		private String is_report_print="";
		
		@Column(name="company_id")
        private long company_id;
		
		@Column(name="batch_id")
		private long batch_id;		
		
		@Column(name="exam_times")
		private String exam_times="";
		
		@Column(name="patient_id")
		private String patient_id="";
		
		@Column(name="employeeID")
		private String employeeID="";
		
		@Column(name="mc_no")
		private String mc_no="";//就诊卡号
		
		@Column(name="visit_date")
		private String visit_date="";//就诊日期
		
		@Column(name="visit_no")
		private String visit_no="";//就诊号
		
		@Column(name="clinic_no")
		private String clinic_no="";//门诊号		
		
		@Column(name="exam_indicator")
		private String exam_indicator="";//团体付费状态 T团体结算 G 自费结算	
		
		@Column(name="apptype")
		private String apptype="1";//1表示普通体检 2表示职业病体检
		
		@Column(name="is_need_guide")
		private String is_need_guide;//是否打印导引单
		
		@Column(name="is_need_barcode")
		private String is_need_barcode;//是否打印条码
		
		@Column(name="is_report_tidy")
		private String is_report_tidy;//确认整理报告
		
		@Column(name="report_tidy_user")
		private String report_tidy_user;//报告整理人
		
		@Column(name="report_tidy_time")
		private Date report_tidy_time;//报告整理时间
		
		@Column(name="freeze")
		private long freeze;//0表示不冻结，1表示冻结		
		
		@Column(name="budget_amount")
		private Double budget_amount;//预算金额
		
		@Column(name="zyb_set_source")
		private int zyb_set_source;//职业病体检套餐来源，0表示按职业危害因素关联套餐，1表示关联自选套餐
		
		@Column(name = "marriage_age")
		private long marriage_age;//婚龄
		
		@Column(name = "wuxuzongjian")
		private int wuxuzongjian;//0：总检，1：无需总检	 
		
		@Column(name = "old_exam_num") //旧的体检号
		private String old_exam_num;

		@Column(name = "is_upload") //
		private int is_upload;
		
		@Column(name = "is_guide_date") //回收时间
		private String is_guide_date=null;
		
		@Column(name = "is_guide_user") //回收人
		private long is_guide_user;
		
		@Column(name="ziqu_report_time")
		private String ziqu_report_time="";//领取报告时间
		
		@Column(name="zyb_final_status")
		private String zyb_final_status="N";
		
		@Column(name="zyb_final_doctor")
		private String zyb_final_doctor;
		
		@Column(name="zyb_final_time")
		private Date zyb_final_time;
		
		@Column(name="isvisit")
		private long isvisit;
		
		@Column(name="card_num")
		private String card_num;
		
		@Column(name="exam_center_num")
		private String exam_center_num;//报到体检中心编码
		
		public String getExam_center_num() {
			return exam_center_num;
		}

		public void setExam_center_num(String exam_center_num) {
			this.exam_center_num = exam_center_num;
		}

		public String getZyb_final_status() {
			return zyb_final_status;
		}

		public void setZyb_final_status(String zyb_final_status) {
			this.zyb_final_status = zyb_final_status;
		}

		public String getZyb_final_doctor() {
			return zyb_final_doctor;
		}

		public void setZyb_final_doctor(String zyb_final_doctor) {
			this.zyb_final_doctor = zyb_final_doctor;
		}

		public Date getZyb_final_time() {
			return zyb_final_time;
		}

		public void setZyb_final_time(Date zyb_final_time) {
			this.zyb_final_time = zyb_final_time;
		}

		public String getZiqu_report_time() {
			return ziqu_report_time;
		}

		public void setZiqu_report_time(String ziqu_report_time) {
			this.ziqu_report_time = ziqu_report_time;
		}
		
	    public long getMarriage_age() {
			return marriage_age;
		}

		public void setMarriage_age(long marriage_age) {
			this.marriage_age = marriage_age;
		}

		public int getWuxuzongjian() {
			return wuxuzongjian;
		}

		public void setWuxuzongjian(int wuxuzongjian) {
			this.wuxuzongjian = wuxuzongjian;
		}

		public String getOld_exam_num() {
			return old_exam_num;
		}

		public void setOld_exam_num(String old_exam_num) {
			this.old_exam_num = old_exam_num;
		}

		public String getIs_report_tidy() {
			return is_report_tidy;
		}

		public void setIs_report_tidy(String is_report_tidy) {
			this.is_report_tidy = is_report_tidy;
		}

		public long getFreeze() {
			return freeze;
		}

		public void setFreeze(long freeze) {
			this.freeze = freeze;
		}
		
	    public String getApptype() {
			return apptype;
		}

		public void setApptype(String apptype) {
			this.apptype = apptype;
		}

		public String getExam_indicator() {
			return exam_indicator;
		}

		public void setExam_indicator(String exam_indicator) {
			this.exam_indicator = exam_indicator;
		}

		public String getClinic_no() {
			return clinic_no;
		}

		public void setClinic_no(String clinic_no) {
			this.clinic_no = clinic_no;
		}

		public String getMc_no() {
			return mc_no;
		}

		public void setMc_no(String mc_no) {
			this.mc_no = mc_no;
		}

		public String getVisit_date() {
			return visit_date;
		}

		public void setVisit_date(String visit_date) {
			this.visit_date = visit_date;
		}

		public String getVisit_no() {
			return visit_no;
		}

		public void setVisit_no(String visit_no) {
			this.visit_no = visit_no;
		}

		public String getEmployeeID() {
			return employeeID;
		}

		public void setEmployeeID(String employeeID) {
			this.employeeID = employeeID;
		}

		public String getPatient_id() {
			return patient_id;
		}

		public void setPatient_id(String patient_id) {
			this.patient_id = patient_id;
		}

		public String getExam_times() {
			return exam_times;
		}

		public void setExam_times(String exam_times) {
			this.exam_times = exam_times;
		}

		public long getCompany_id() {
			return company_id;
		}

		public void setCompany_id(long company_id) {
			this.company_id = company_id;
		}

		public long getBatch_id() {
			return batch_id;
		}

		public void setBatch_id(long batch_id) {
			this.batch_id = batch_id;
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

		public String getRegister_date() {
			return register_date;
		}

		public void setRegister_date(String register_date) {
			this.register_date = register_date;
		}

		public String getJoin_date() {
			return join_date;
		}

		public void setJoin_date(String join_date) {
			this.join_date = join_date;
		}

		public String getFinal_date() {
			return final_date;
		}

		public void setFinal_date(String final_date) {
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

		public long getAge() {
			return age;
		}

		public void setAge(long age) {
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

		public long getUpdater() {
			return updater;
		}

		public void setUpdater(long updater) {
			this.updater = updater;
		}

		public String getCreate_time() {
			return create_time;
		}

		public void setCreate_time(String create_time) {
			this.create_time = create_time;
		}

		public String getUpdate_time() {
			return update_time;
		}

		public void setUpdate_time(String update_time) {
			this.update_time = update_time;
		}

		public String getIs_report_print() {
			return is_report_print;
		}

		public void setIs_report_print(String is_report_print) {
			this.is_report_print = is_report_print;
		}

		public String getIs_need_guide() {
			return is_need_guide;
		}

		public void setIs_need_guide(String is_need_guide) {
			this.is_need_guide = is_need_guide;
		}

		public String getIs_need_barcode() {
			return is_need_barcode;
		}

		public void setIs_need_barcode(String is_need_barcode) {
			this.is_need_barcode = is_need_barcode;
		}

		public String getReport_tidy_user() {
			return report_tidy_user;
		}

		public void setReport_tidy_user(String report_tidy_user) {
			this.report_tidy_user = report_tidy_user;
		}

		public Date getReport_tidy_time() {
			return report_tidy_time;
		}

		public void setReport_tidy_time(Date report_tidy_time) {
			this.report_tidy_time = report_tidy_time;
		}

		public Double getBudget_amount() {
			return budget_amount;
		}

		public void setBudget_amount(Double budget_amount) {
			this.budget_amount = budget_amount;
		}

		public int getZyb_set_source() {
			return zyb_set_source;
		}

		public void setZyb_set_source(int zyb_set_source) {
			this.zyb_set_source = zyb_set_source;
		}

		public int getIs_upload() {
			return is_upload;
		}

		public void setIs_upload(int is_upload) {
			this.is_upload = is_upload;
		}

		public String getIs_guide_date() {
			return is_guide_date;
		}

		public void setIs_guide_date(String is_guide_date) {
			this.is_guide_date = is_guide_date;
		}

		public long getIs_guide_user() {
			return is_guide_user;
		}

		public void setIs_guide_user(long is_guide_user) {
			this.is_guide_user = is_guide_user;
		}

		public long getIsvisit() {
			return isvisit;
		}

		public void setIsvisit(long isvisit) {
			this.isvisit = isvisit;
		}

		public String getCard_num() {
			return card_num;
		}

		public void setCard_num(String card_num) {
			this.card_num = card_num;
		}		
		
	}