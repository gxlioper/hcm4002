package com.hjw.Bean;

import java.util.ArrayList;
import java.util.List;

public class OrderBean {
	private String orderid;//订单编号
	private String user_name;//姓名	
	private String id_num="";//身份证号	id_num
	private String sex;//性别	sex
	private String birthday;//生日	birthday
	private String nation="";//民族	nation
	private String phone="";//电话	phone
	private String address="";//地址	address
	private String email="";//Email	email
	private String register_date;//预约日期	
	private String exam_times;//体检预约时间段	
	private String exam_type;//个人（团体）类型	exam_type
	private String reportWay;//取体检报告方式	reportWay 114单位统一取
	                            //                           115自取
	                            //                           116邮寄
	private String reportAddress="";//体检报告寄送地址	reportAddress
	private String is_marriage;//婚否	is_marriage   	已婚
	                           //                       未婚
	private long age;//年龄	age
	private String past_medical_history;//既往史	past_medical_history
	private String remark="";//备注	remark

	private List<ListitemBean> listitem= new ArrayList<ListitemBean>();
	private List<ListsetBean> listset= new ArrayList<ListsetBean>();	
	
	public String getOrderid() {
		return orderid;
	}
	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public String getId_num() {
		return id_num;
	}
	public void setId_num(String id_num) {
		this.id_num = id_num;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	public String getNation() {
		return nation;
	}
	public void setNation(String nation) {
		this.nation = nation;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
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
	public String getRegister_date() {
		return register_date;
	}
	public void setRegister_date(String register_date) {
		this.register_date = register_date;
	}
	public String getExam_times() {
		return exam_times;
	}
	public void setExam_times(String exam_times) {
		this.exam_times = exam_times;
	}
	public String getExam_type() {
		return exam_type;
	}
	public void setExam_type(String exam_type) {
		this.exam_type = exam_type;
	}
	public String getReportWay() {
		return reportWay;
	}
	public void setReportWay(String reportWay) {
		this.reportWay = reportWay;
	}
	public String getReportAddress() {
		return reportAddress;
	}
	public void setReportAddress(String reportAddress) {
		this.reportAddress = reportAddress;
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
	public String getPast_medical_history() {
		return past_medical_history;
	}
	public void setPast_medical_history(String past_medical_history) {
		this.past_medical_history = past_medical_history;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public List<ListitemBean> getListitem() {
		return listitem;
	}
	public void setListitem(List<ListitemBean> listitem) {
		this.listitem = listitem;
	}
	public List<ListsetBean> getListset() {
		return listset;
	}
	public void setListset(List<ListsetBean> listset) {
		this.listset = listset;
	}
	
}
