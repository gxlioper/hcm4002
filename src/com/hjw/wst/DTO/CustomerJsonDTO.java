package com.hjw.wst.DTO;

public class CustomerJsonDTO {
	
	public String user_name;
	public String arch_num;//档案号
	public String id_num;//身份证
	public String phone;//电话
	public String address;//地址
	public String birthday;//出生日期
	public String exam_num;//体检 编号
	public int age;//年龄
	public String sex;
	public String is_marriage;//婚否
	public String email;
	public String company;//单位
	public String exam_type;//个检团检
	public String txtpswd;//签名
    public Double budget;
	
	public Double getBudget() {
		return budget;
	}
	public void setBudget(Double budget) {
		this.budget = budget;
	}
	
	
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
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
	public String getId_num() {
		return id_num;
	}
	public void setId_num(String id_num) {
		this.id_num = id_num;
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
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	public String getExam_num() {
		return exam_num;
	}
	public void setExam_num(String exam_num) {
		this.exam_num = exam_num;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getIs_marriage() {
		return is_marriage;
	}
	public void setIs_marriage(String is_marriage) {
		this.is_marriage = is_marriage;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getExam_type() {
		return exam_type;
	}
	public void setExam_type(String exam_type) {
		this.exam_type = exam_type;
	}
	public String getTxtpswd() {
		return txtpswd;
	}
	public void setTxtpswd(String txtpswd) {
		this.txtpswd = txtpswd;
	}
	
	
}
