package com.hjw.Bean;

import java.util.ArrayList;
import java.util.List;

public class ExamInfoBean {
	private long synid;
	private String exam_num="";//体检编号
	private String user_name="";//会员姓名	
	private String id_num="";//身份证号	id_num
	private String sex="";//性别	
	private long group_id;//分组id
	private long batch_id;//批次id
	private long company_id;//单位id
	private String birthday="";//生日	birthday
	private String nation="";//民族	nation
	private String phone="";//电话	phone
	private String address="";//地址	address
	private String email="";//Email	email
	private String register_date="";//预约日期	register_date
	private String join_date="";//体检日期	join_date
	private String status="";//体检类型	status
	private String exam_times="";//体检预约时间段	exam_times
	private String exam_type="";//个人（团体）类型	exam_type
	private String getReportWay="";//取体检报告方式	
	private String reportAddress="";//体检报告寄送地址	reportAddress
	private String is_marriage="";//婚否	is_marriage
	private long age;//年龄	age
	private String past_medical_history="";//既往史	past_medical_history
	private String create_time="";//创建时间	create_time
	private String update_time="";//更新时间	update_time
	private String remark="";//备注	remark
	private String is_Active="Y";
	private String arch_num="";
	private String batch_num="";
	private String batch_name="";
	private String group_num="";
	private String group_name="";
	private String company="";
	private List<ListsetBean> listset= new ArrayList<ListsetBean>();
	private List<ListitemBean> listitem= new ArrayList<ListitemBean>();
	private List<TrjnBean> trjnlist = new ArrayList<TrjnBean>();
	
	public List<TrjnBean> getTrjnlist() {
		return trjnlist;
	}
	public void setTrjnlist(List<TrjnBean> trjnlist) {
		this.trjnlist = trjnlist;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getBatch_num() {
		return batch_num;
	}
	public void setBatch_num(String batch_num) {
		this.batch_num = batch_num;
	}
	public String getBatch_name() {
		return batch_name;
	}
	public void setBatch_name(String batch_name) {
		this.batch_name = batch_name;
	}
	public String getGroup_num() {
		return group_num;
	}
	public void setGroup_num(String group_num) {
		this.group_num = group_num;
	}
	public String getGroup_name() {
		return group_name;
	}
	public void setGroup_name(String group_name) {
		this.group_name = group_name;
	}
	public long getCompany_id() {
		return company_id;
	}
	public void setCompany_id(long company_id) {
		this.company_id = company_id;
	}
	public long getGroup_id() {
		return group_id;
	}
	public void setGroup_id(long group_id) {
		this.group_id = group_id;
	}
	public long getBatch_id() {
		return batch_id;
	}
	public void setBatch_id(long batch_id) {
		this.batch_id = batch_id;
	}
	public String getArch_num() {
		return arch_num;
	}
	public void setArch_num(String arch_num) {
		this.arch_num = arch_num;
	}
	public String getIs_Active() {
		return is_Active;
	}
	public void setIs_Active(String is_Active) {
		this.is_Active = is_Active;
	}
	public long getSynid() {
		return synid;
	}
	public void setSynid(long synid) {
		this.synid = synid;
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
		if((register_date==null)||(register_date.trim().length()<=0)){
			this.register_date="";
		}else if(register_date.indexOf("1900-01-01")>=0)
		{
			this.register_date="";
		}else{
			this.register_date = register_date;
		}
	}
	public String getJoin_date() {
		return join_date;
	}
	public void setJoin_date(String join_date) {
		if((join_date==null)||(join_date.trim().length()<=0)){
			this.join_date="";
		}else if(join_date.indexOf("1900-01-01")>=0)
		{
			this.join_date="";
		}else{
			this.join_date = join_date;
		}
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
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
		if((update_time==null)||(update_time.trim().length()<=0)){
			this.update_time="";
		}else if(update_time.indexOf("1900-01-01")>=0)
		{
			this.update_time="";
		}else{
			this.update_time = update_time;
		}
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public List<ListsetBean> getListset() {
		return listset;
	}
	public void setListset(List<ListsetBean> listset) {
		this.listset = listset;
	}
	public List<ListitemBean> getListitem() {
		return listitem;
	}
	public void setListitem(List<ListitemBean> listitem) {
		this.listitem = listitem;
	}

}
