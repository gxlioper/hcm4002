package com.hjw.wst.model;

import java.io.Serializable;

/**
 * 
     * @Title:  火箭蛙体检管理系统   
     * @Package com.hjw.wst.DTO   
     * @Description:  问卷调查用户信息
     * @author: zr     
     * @date:   2017年2月13日 下午3:07:30   
     * @version V2.0.0.0
 */
public class SurveyExamInfoModel  implements  Serializable{

	/**   
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)   
	 */ 
	private static final long serialVersionUID = 1L;
	
	private  String  user_name;
	private  String  sex;
	private  String  age;
	private  String  com_name;
	private  String  phone;
	private  String  data_name;
	private  String  set_name;
	
	public String getUser_name() {
		return user_name;
	}
	public String getSex() {
		return sex;
	}
	public String getAge() {
		return age;
	}
	public String getCom_name() {
		return com_name;
	}
	public String getPhone() {
		return phone;
	}
	public String getData_name() {
		return data_name;
	}
	public String getSet_name() {
		return set_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public void setAge(String age) {
		this.age = age;
	}
	public void setCom_name(String com_name) {
		this.com_name = com_name;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public void setData_name(String data_name) {
		this.data_name = data_name;
	}
	public void setSet_name(String set_name) {
		this.set_name = set_name;
	}
	
	
}
