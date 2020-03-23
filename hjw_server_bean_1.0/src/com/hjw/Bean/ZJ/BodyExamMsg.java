package com.hjw.Bean.ZJ;

public class BodyExamMsg {
	private long	med_age;//体检时年龄	integer	数字
	private String telephone="";//手机号	string	
	private String employee_no="";//员工号	string	
	private String department="";//客户所在部门	string	
	private String post="";//客户职务	string	
	private String med_exam_no="";//预约号	string	单一体检中心唯一
	private String med_exam_type="";//体检类型	string	普通体检、入职体检、VIP等，根据实际情况填写
	private String med_date="";//体检日期		string	实际体检日期，格式yyyy-mm-dd
	private String contract_id="";//合同编号
	private String contract_name="";//合同名称	string	企业团检的本次体检名称，如“2015北京国资委”，没有可以为空
	private String contract_date="";//合同日期	string	企业本次体检开始日期，格式yyyy-mm-dd，没有为空
    private long exam_id;//
    private String exam_type;//G或T
    private String address="";//地址
    private String register_date="";//预约日期
    private String marriage_state="";//是否已婚
    private String final_date="";//总检日期
    private String arch_num;//档案号
    private String company="";//公司名称
    private String corp_name="";//公司名称
    private long   company_id;//
    private long   batch_id;
    private String batch_name="";//
    private long group_id;//
    private String group_name="";//
	public long getMed_age() {
		return med_age;
	}
	public void setMed_age(long med_age) {
		this.med_age = med_age;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getEmployee_no() {
		return employee_no;
	}
	public void setEmployee_no(String employee_no) {
		this.employee_no = employee_no;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getPost() {
		return post;
	}
	public void setPost(String post) {
		this.post = post;
	}
	public String getMed_exam_no() {
		return med_exam_no;
	}
	public void setMed_exam_no(String med_exam_no) {
		this.med_exam_no = med_exam_no;
	}
	public String getMed_exam_type() {
		return med_exam_type;
	}
	public void setMed_exam_type(String med_exam_type) {
		this.med_exam_type = med_exam_type;
	}
	public String getMed_date() {
		return med_date;
	}
	public void setMed_date(String med_date) {
		this.med_date = med_date;
	}
	public String getContract_id() {
		return contract_id;
	}
	public void setContract_id(String contract_id) {
		this.contract_id = contract_id;
	}
	public String getContract_name() {
		return contract_name;
	}
	public void setContract_name(String contract_name) {
		this.contract_name = contract_name;
	}
	public String getContract_date() {
		return contract_date;
	}
	public void setContract_date(String contract_date) {
		this.contract_date = contract_date;
	}
	public long getExam_id() {
		return exam_id;
	}
	public void setExam_id(long exam_id) {
		this.exam_id = exam_id;
	}
	public String getExam_type() {
		return exam_type;
	}
	public void setExam_type(String exam_type) {
		this.exam_type = exam_type;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getRegister_date() {
		return register_date;
	}
	public void setRegister_date(String register_date) {
		this.register_date = register_date;
	}
	public String getMarriage_state() {
		return marriage_state;
	}
	public void setMarriage_state(String marriage_state) {
		this.marriage_state = marriage_state;
	}
	public String getFinal_date() {
		return final_date;
	}
	public void setFinal_date(String final_date) {
		this.final_date = final_date;
	}
	public String getArch_num() {
		return arch_num;
	}
	public void setArch_num(String arch_num) {
		this.arch_num = arch_num;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getCorp_name() {
		return corp_name;
	}
	public void setCorp_name(String corp_name) {
		this.corp_name = corp_name;
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
	public String getBatch_name() {
		return batch_name;
	}
	public void setBatch_name(String batch_name) {
		this.batch_name = batch_name;
	}
	public long getGroup_id() {
		return group_id;
	}
	public void setGroup_id(long group_id) {
		this.group_id = group_id;
	}
	public String getGroup_name() {
		return group_name;
	}
	public void setGroup_name(String group_name) {
		this.group_name = group_name;
	}
    
}
