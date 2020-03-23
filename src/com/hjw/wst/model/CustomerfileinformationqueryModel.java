package com.hjw.wst.model;
/**
 * 
     * @Title:  火箭蛙体检管理系统   
     * @Package com.hjw.wst.DTO   
     * @Description:  档案信息查询
     * @author: zr     
     * @date:   2016年11月14日 下午1:56:57   
     * @version V2.0.0.0
 */
public class CustomerfileinformationqueryModel implements  java.io.Serializable{
	    
		private static final long serialVersionUID = 6051802423558441770L;
		
		private long id;//档案id
		private String	arch_num;//档案号
		private String	exam_num;//体检号
		private String	status;//体检状态
		private String  register_date;//登记日期
		private String  id_num;//身份证号码
		private String	user_name;//参检用户姓名
		private String  sex;//性别
		private String  age;//年龄
		private String  company;//单位
		private String  address;//地址
		private String  phone;//手机
		
		
		//健康档案
		private  String  num;//体检次数
		private  String  tj_time;//体检时间
		private  String  tj_exam_set;//体检套餐
		private  double  examSet_amount;//套餐金额
		private  String yxfx;
		
		
		
		
		public String getTj_time() {
			return tj_time;
		}
		public void setTj_time(String tj_time) {
			this.tj_time = tj_time;
		}
		public String getTj_exam_set() {
			return tj_exam_set;
		}
		public void setTj_exam_set(String tj_exam_set) {
			this.tj_exam_set = tj_exam_set;
		}
		public double getExamSet_amount() {
			return examSet_amount;
		}
		public void setExamSet_amount(double examSet_amount) {
			this.examSet_amount = examSet_amount;
		}
		public String getYxfx() {
			return yxfx;
		}
		public void setYxfx(String yxfx) {
			this.yxfx = yxfx;
		}
		public String getNum() {
			return num;
		}
		public void setNum(String num) {
			this.num = num;
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
		public String getSex() {
			return sex;
		}
		public void setSex(String sex) {
			this.sex = sex;
		}
		public String getAge() {
			return age;
		}
		public void setAge(String age) {
			this.age = age;
		}
		public String getCompany() {
			return company;
		}
		public void setCompany(String company) {
			this.company = company;
		}
		public String getAddress() {
			return address;
		}
		public void setAddress(String address) {
			this.address = address;
		}
		public String getPhone() {
			return phone;
		}
		public void setPhone(String phone) {
			this.phone = phone;
		}
		
}
