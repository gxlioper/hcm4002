package com.hjw.wst.DTO;

import java.util.ArrayList;
import java.util.List;

public class CustomerExamDTO {

	private long id;
	private String arch_num;
	private String exam_num;
	private String user_name;
	private String sex;
	private long age;
	private String company;
	private String customer_type;
	private String set_name;
	private String is_guide_back;
	private String past_medical_history;
	private String item_code;
	private String item_name;
	private String exam_status;
	private String exam_date;
	private String exam_doctor_name;
	private String join_date;
	private String status;
	private String picture_path;
	private String is_guide_date; //回收日期
	private String phone;
	private String vipflag;
	private String exam_type;
	private String exam_types;
	
	private long wuxuzongjian;// 无需总检 状态
	private String exam_type_code;
	private String group_name;//分组名称
	private Double amount;//分组金额
	private long report_type;//报告类型
	private String getReportWay;
	private String getReportWays;
	private String reportAddress;
	private int exam_count;
	private String email;//邮箱
	private int ecd_id;//危急值id
	private int vipsigin=0;
	private int Customer_type_id;
	
	public int getCustomer_type_id() {
		return Customer_type_id;
	}
	public void setCustomer_type_id(int customer_type_id) {
		Customer_type_id = customer_type_id;
	}
	public int getVipsigin() {
		return vipsigin;
	}
	public void setVipsigin(int vipsigin) {
		this.vipsigin = vipsigin;
	}
	public String getExam_num() {
		return exam_num;
	}
	public void setExam_num(String exam_num) {
		this.exam_num = exam_num;
	}
	public String getGroup_name() {
		return group_name;
	}
	public void setGroup_name(String group_name) {
		this.group_name = group_name;
	}
	
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}

	private List<ExamDepResultDTO> exam_dep = new ArrayList<ExamDepResultDTO>(); 
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getExam_status() {
		return exam_status;
	}
	public void setExam_status(String exam_status) {
		//this.exam_status = exam_status;
		if(exam_status.equals("Y")){
			this.exam_status ="已检";
		}else if(exam_status.equals("N")){
			this.exam_status ="未检";
		}
	}
	public String getExam_date() {
		return exam_date;
	}
	public void setExam_date(String exam_date) {
		this.exam_date = exam_date;
	}
	public String getExam_doctor_name() {
		return exam_doctor_name;
	}
	public void setExam_doctor_name(String exam_doctor_name) {
		this.exam_doctor_name = exam_doctor_name;
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
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getArch_num() {
		return arch_num;
	}
	public void setArch_num(String arch_num) {
		this.arch_num = arch_num;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public long getAge() {
		return age;
	}
	public void setAge(long age) {
		this.age = age;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getCustomer_type() {
		return customer_type;
	}
	public void setCustomer_type(String customer_type) {
		this.customer_type = customer_type;
	}
	public String getSet_name() {
		return set_name;
	}
	public void setSet_name(String set_name) {
		this.set_name = set_name;
	}
	public String getIs_guide_back() {
		return is_guide_back;
	}
	public void setIs_guide_back(String is_guide_back) {
	  String a="";
		if(is_guide_back.equals("Y")){
		a+="已回收";	
		}else{
		a+="未回收";		
		}
		this.is_guide_back = a;
	}
	public String getPast_medical_history() {
		return past_medical_history;
	}
	public void setPast_medical_history(String past_medical_history) {
		this.past_medical_history = past_medical_history;
	}
	public String getJoin_date() {
		return join_date;
	}
	public void setJoin_date(String join_date) {
		this.join_date = join_date;
	}
	public List<ExamDepResultDTO> getExam_dep() {
		return exam_dep;
	}
	public void setExam_dep(List<ExamDepResultDTO> exam_dep) {
		this.exam_dep = exam_dep;
	}
	public String getPicture_path() {
		return picture_path;
	}
	public void setPicture_path(String picture_path) {
		this.picture_path = picture_path;
	}
	public String getExam_type_code() {
		return exam_type_code;
	}
	public void setExam_type_code(String exam_type_code) {
		this.exam_type_code = exam_type_code;
	}
	public long getReport_type() {
		return report_type;
	}
	public void setReport_type(long report_type) {
		this.report_type = report_type;
	}
	public String getIs_guide_date() {
		return is_guide_date;
	}
	public void setIs_guide_date(String is_guide_date) {
		this.is_guide_date = is_guide_date;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getVipflag() {
		return vipflag;
	}
	public void setVipflag(String vipflag) {
		this.vipflag = vipflag;
	}
	public String getExam_type() {
		return exam_type;
	}
	public void setExam_type(String exam_type) {
		this.exam_type = exam_type;
	}
	public String getExam_types() {
		return exam_types;
	}
	public void setExam_types(String exam_types) {
		this.exam_types = exam_types;
	}
	public long getWuxuzongjian() {
		return wuxuzongjian;
	}
	public void setWuxuzongjian(long wuxuzongjian) {
		this.wuxuzongjian = wuxuzongjian;
	}
	public String getGetReportWay() {
		return getReportWay;
	}
	public void setGetReportWay(String getReportWay) {
		this.getReportWay = getReportWay;
	}
	public String getGetReportWays() {
		return getReportWays;
	}
	public void setGetReportWays(String getReportWays) {
		this.getReportWays = getReportWays;
	}
	public String getReportAddress() {
		return reportAddress;
	}
	public void setReportAddress(String reportAddress) {
		this.reportAddress = reportAddress;
	}
	public int getExam_count() {
		return exam_count;
	}
	public void setExam_count(int exam_count) {
		this.exam_count = exam_count;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getEcd_id() {
		return ecd_id;
	}
	public void setEcd_id(int ecd_id) {
		this.ecd_id = ecd_id;
	}
	
}
