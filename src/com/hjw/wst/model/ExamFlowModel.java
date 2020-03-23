package com.hjw.wst.model;

import java.util.Date;

/**
 * 
     * @Title:  火箭蛙体检管理系统   
     * @Package com.hjw.wst.domain   
     * @Description:  
     * @author: yangm     
     * @date:   2016年7月4日 上午11:18:07   
     * @version V2.0.0.0
 */
public class ExamFlowModel implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private long id;
	
	private String username;
	
	private long exam_id;//--体检id	
	
	private String exam_num;

	private long fromacc;//--发起人id	

	private String fromacc_date="";//--发起时间

	private long toacc;//--接收人id

	private String toacc_date="";//接收时间

	private String types="0";//--类型：0表示发起，标识接收

	private String remark="";//--说明1

	private String remark1="";//--说明2	
	
	private String center_num="";//体检中心编码		
	
	private String time1="";
	
	private String time2="";
	
	private String ids="";
	
	private String report_print_type="";
	private String zyb_report_print_type;
	private String upload_flow;//二院上传流程
	
	private long set_id;
	private long user_id;
	private String getReportWay;
	private String email;//邮箱
	private String reportAddress;
	private long process;//1登记台  2导简单核收
	
    private String sex;
	
	private String isVip;
	
	private int age;
	
	private String company;
	
	private long batch_id;
	
	private long group_id;
	
	private int ptype;	
	
	private String exam_type;
	
	private String creater_state;
	
	private String is_report_room;
	
	private String wjItem_dep;//未检查科室ids
	
	private String flow_code;
	
	private long doctor_id;
	
	private String printTime1;//打印时间
	
	private String printTime2;
	
	private int printFlag;//打印标识
	
	private  long  printer;//打印人
	
	private String health_report;
	
	private String receive_type;
	
	private String receive_date;
	
	private String report_class="";
	
	private long dep_id;
	
	private String vipflag="";
	
	private String receive_name;
	
	private String edesc;
	
	private String is_show_yanqitime;
	
	public String getIs_show_yanqitime() {
		return is_show_yanqitime;
	}

	public void setIs_show_yanqitime(String is_show_yanqitime) {
		this.is_show_yanqitime = is_show_yanqitime;
	}

	public String getTime1() {
		return time1;
	}

	public void setTime1(String time1) {
		this.time1 = time1;
	}

	public String getTime2() {
		return time2;
	}

	public void setTime2(String time2) {
		this.time2 = time2;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public String getExam_num() {
		return exam_num;
	}

	public void setExam_num(String exam_num) {
		this.exam_num = exam_num;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public long getExam_id() {
		return exam_id;
	}

	public void setExam_id(long exam_id) {
		this.exam_id = exam_id;
	}

	public long getFromacc() {
		return fromacc;
	}

	public void setFromacc(long fromacc) {
		this.fromacc = fromacc;
	}

	public String getFromacc_date() {
		return fromacc_date;
	}

	public void setFromacc_date(String fromacc_date) {
		this.fromacc_date = fromacc_date;
	}

	public long getToacc() {
		return toacc;
	}

	public void setToacc(long toacc) {
		this.toacc = toacc;
	}

	public String getToacc_date() {
		return toacc_date;
	}

	public void setToacc_date(String toacc_date) {
		this.toacc_date = toacc_date;
	}

	public String getTypes() {
		return types;
	}

	public void setTypes(String types) {
		this.types = types;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getRemark1() {
		return remark1;
	}

	public void setRemark1(String remark1) {
		this.remark1 = remark1;
	}

	public String getCenter_num() {
		return center_num;
	}

	public void setCenter_num(String center_num) {
		this.center_num = center_num;
	}

	public String getUpload_flow() {
		return upload_flow;
	}

	public void setUpload_flow(String upload_flow) {
		this.upload_flow = upload_flow;
	}

	public long getSet_id() {
		return set_id;
	}

	public void setSet_id(long set_id) {
		this.set_id = set_id;
	}

	public long getUser_id() {
		return user_id;
	}

	public void setUser_id(long user_id) {
		this.user_id = user_id;
	}

	public String getGetReportWay() {
		return getReportWay;
	}

	public void setGetReportWay(String getReportWay) {
		this.getReportWay = getReportWay;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getReportAddress() {
		return reportAddress;
	}

	public void setReportAddress(String reportAddress) {
		this.reportAddress = reportAddress;
	}

	public long getProcess() {
		return process;
	}

	public void setProcess(long process) {
		this.process = process;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getIsVip() {
		return isVip;
	}

	public void setIsVip(String isVip) {
		this.isVip = isVip;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public long getBatch_id() {
		return batch_id;
	}

	public void setBatch_id(long batch_id) {
		this.batch_id = batch_id;
	}

	public long getGroup_id() {
		return group_id;
	}

	public void setGroup_id(long group_id) {
		this.group_id = group_id;
	}

	public int getPtype() {
		return ptype;
	}

	public void setPtype(int ptype) {
		this.ptype = ptype;
	}

	public String getExam_type() {
		return exam_type;
	}

	public void setExam_type(String exam_type) {
		this.exam_type = exam_type;
	}

	public String getCreater_state() {
		return creater_state;
	}

	public void setCreater_state(String creater_state) {
		this.creater_state = creater_state;
	}

	public String getIs_report_room() {
		return is_report_room;
	}

	public void setIs_report_room(String is_report_room) {
		this.is_report_room = is_report_room;
	}

	public String getWjItem_dep() {
		return wjItem_dep;
	}

	public void setWjItem_dep(String wjItem_dep) {
		this.wjItem_dep = wjItem_dep;
	}

	public String getFlow_code() {
		return flow_code;
	}

	public void setFlow_code(String flow_code) {
		this.flow_code = flow_code;
	}

	public long getDoctor_id() {
		return doctor_id;
	}

	public void setDoctor_id(long doctor_id) {
		this.doctor_id = doctor_id;
	}

	public String getPrintTime1() {
		return printTime1;
	}

	public void setPrintTime1(String printTime1) {
		this.printTime1 = printTime1;
	}

	public String getPrintTime2() {
		return printTime2;
	}

	public void setPrintTime2(String printTime2) {
		this.printTime2 = printTime2;
	}

	public int getPrintFlag() {
		return printFlag;
	}

	public void setPrintFlag(int printFlag) {
		this.printFlag = printFlag;
	}

	public long getPrinter() {
		return printer;
	}

	public void setPrinter(long printer) {
		this.printer = printer;
	}

	public String getHealth_report() {
		return health_report;
	}

	public void setHealth_report(String health_report) {
		this.health_report = health_report;
	}

	public String getReceive_type() {
		return receive_type;
	}

	public void setReceive_type(String receive_type) {
		this.receive_type = receive_type;
	}

	public String getReceive_date() {
		return receive_date;
	}

	public void setReceive_date(String receive_date) {
		this.receive_date = receive_date;
	}

	public String getReport_class() {
		return report_class;
	}

	public void setReport_class(String report_class) {
		this.report_class = report_class;
	}

	public long getDep_id() {
		return dep_id;
	}

	public void setDep_id(long dep_id) {
		this.dep_id = dep_id;
	}

	public String getReceive_name() {
		return receive_name;
	}

	public void setReceive_name(String receive_name) {
		this.receive_name = receive_name;
	}

	public String getVipflag() {
		return vipflag;
	}

	public void setVipflag(String vipflag) {
		this.vipflag = vipflag;
	}

	public String getEdesc() {
		return edesc;
	}

	public void setEdesc(String edesc) {
		this.edesc = edesc;
	}

	public String getReport_print_type() {
		return report_print_type;
	}

	public String getZyb_report_print_type() {
		return zyb_report_print_type;
	}

	public void setReport_print_type(String report_print_type) {
		this.report_print_type = report_print_type;
	}

	public void setZyb_report_print_type(String zyb_report_print_type) {
		this.zyb_report_print_type = zyb_report_print_type;
	}
	

}