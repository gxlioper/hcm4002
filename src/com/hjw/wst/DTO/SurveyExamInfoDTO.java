package com.hjw.wst.DTO;

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
public class SurveyExamInfoDTO  implements  Serializable{

	/**   
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)   
	 */ 
	private static final long serialVersionUID = 1L;
	
	private  String  user_name;
	private  String  sex;
	private  long  age;
	private  String  com_name;
	private  String  phone;
	private  String  data_name;
	private  String  set_name;
	private  String  exam_num;
	private  long    id;
	private  String  is_marriage;
	private long c_id;
	public long getC_id() {
		return c_id;
	}
	public void setC_id(long c_id) {
		this.c_id = c_id;
	}
	public String getIs_marriage() {
		return is_marriage;
	}
	public void setIs_marriage(String is_marriage) {
		this.is_marriage = is_marriage;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getExam_num() {
		return exam_num;
	}
	public void setExam_num(String exam_num) {
		this.exam_num = exam_num;
	}
	public String getUser_name() {
		return user_name;
	}
	public String getSex() {
		return sex;
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
	public long getAge() {
		return age;
	}
	public void setAge(long age) {
		this.age = age;
	}
	
	
}
