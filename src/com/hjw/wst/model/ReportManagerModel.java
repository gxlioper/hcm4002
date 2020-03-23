package com.hjw.wst.model;

import java.util.Date;

public class ReportManagerModel implements java.io.Serializable {
	private static final long serialVersionUID = -97502163798576023L;

	private long id;
	
	private String ids;
	
	private String receive_type;
	private String receive_name;
	private String receive_phone;
	private String receive_address;
	private String receive_postcode;
	private Date receive_date;
	private String receive_remark;
	
	private String arch_num;
	private String exam_num;
	private String user_name;
	private String id_num;
	private String phone;
	private String join_date;
	private String com_name;
	
	private String creater;
	private String create_time;
	private String updater;
	private String update_time;
	
	private String is_report_print;
	private String is_report_tidy;
	
    private String customer_type;
	
    private String exam_status="";
	
	private String sex="";
	
	private String set_num="";	
	
	private String chkItem="";
	
    private String custname="";
    
    private long company_id;
    
    private long group_id;	
    
    private long set_id;
    
    private String customer_type_id="";	
    
    private String levels="";
    
    private String tjlxs;	
    
    private long batchid; 
    
    private String zytjlx;//职业体检类型
    
    private String zywhys;//职业危害因素    
    
    private String barcode_print_type; 
    private String zyb_barcode_print_type;
    
    private String comon_report_type;
    private String join_date1;
    private String create_time1;
    private String report_tidy_time;
    private String report_tidy_time1;
    
	 
	public String getIds() {
		return ids;
	}
	public void setIds(String ids) {
		this.ids = ids;
	}
	public String getComon_report_type() {
		return comon_report_type;
	}
	public void setComon_report_type(String comon_report_type) {
		this.comon_report_type = comon_report_type;
	}
	public String getZyb_barcode_print_type() {
		return zyb_barcode_print_type;
	}
	public void setZyb_barcode_print_type(String zyb_barcode_print_type) {
		this.zyb_barcode_print_type = zyb_barcode_print_type;
	}
	public String getBarcode_print_type() {
		return barcode_print_type;
	}
	public void setBarcode_print_type(String barcode_print_type) {
		this.barcode_print_type = barcode_print_type;
	}
	public String getZytjlx() {
		return zytjlx;
	}
	public void setZytjlx(String zytjlx) {
		this.zytjlx = zytjlx;
	}
	public String getZywhys() {
		return zywhys;
	}
	public void setZywhys(String zywhys) {
		this.zywhys = zywhys;
	}
	public String getCustomer_type() {
		return customer_type;
	}
	public void setCustomer_type(String customer_type) {
		this.customer_type = customer_type;
	}
	public String getExam_status() {
		return exam_status;
	}
	public void setExam_status(String exam_status) {
		this.exam_status = exam_status;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getSet_num() {
		return set_num;
	}
	public void setSet_num(String set_num) {
		this.set_num = set_num;
	}
	public String getChkItem() {
		return chkItem;
	}
	public void setChkItem(String chkItem) {
		this.chkItem = chkItem;
	}
	public String getCustname() {
		return custname;
	}
	public void setCustname(String custname) {
		this.custname = custname;
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
	public long getSet_id() {
		return set_id;
	}
	public void setSet_id(long set_id) {
		this.set_id = set_id;
	}
	public String getCustomer_type_id() {
		return customer_type_id;
	}
	public void setCustomer_type_id(String customer_type_id) {
		this.customer_type_id = customer_type_id;
	}
	public String getLevels() {
		return levels;
	}
	public void setLevels(String levels) {
		this.levels = levels;
	}
	public String getTjlxs() {
		return tjlxs;
	}
	public void setTjlxs(String tjlxs) {
		this.tjlxs = tjlxs;
	}
	public long getBatchid() {
		return batchid;
	}
	public void setBatchid(long batchid) {
		this.batchid = batchid;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getReceive_type() {
		return receive_type;
	}
	public void setReceive_type(String receive_type) {
		this.receive_type = receive_type;
	}
	public String getReceive_name() {
		return receive_name;
	}
	public void setReceive_name(String receive_name) {
		this.receive_name = receive_name;
	}
	public String getReceive_phone() {
		return receive_phone;
	}
	public void setReceive_phone(String receive_phone) {
		this.receive_phone = receive_phone;
	}
	public String getReceive_address() {
		return receive_address;
	}
	public void setReceive_address(String receive_address) {
		this.receive_address = receive_address;
	}
	public String getReceive_postcode() {
		return receive_postcode;
	}
	public void setReceive_postcode(String receive_postcode) {
		this.receive_postcode = receive_postcode;
	}
	public Date getReceive_date() {
		return receive_date;
	}
	public void setReceive_date(Date receive_date) {
		this.receive_date = receive_date;
	}
	public String getReceive_remark() {
		return receive_remark;
	}
	public void setReceive_remark(String receive_remark) {
		this.receive_remark = receive_remark;
	}
	
	
	public String getCom_name() {
		return com_name;
	}
	public void setCom_name(String com_name) {
		this.com_name = com_name;
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
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getJoin_date() {
		return join_date;
	}
	public void setJoin_date(String join_date) {
		this.join_date = join_date;
	}
	public String getCreater() {
		return creater;
	}
	public void setCreater(String creater) {
		this.creater = creater;
	}
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	public String getUpdater() {
		return updater;
	}
	public void setUpdater(String updater) {
		this.updater = updater;
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
	public String getIs_report_tidy() {
		return is_report_tidy;
	}
	public void setIs_report_tidy(String is_report_tidy) {
		this.is_report_tidy = is_report_tidy;
	}
	public String getJoin_date1() {
		return join_date1;
	}
	public String getCreate_time1() {
		return create_time1;
	}
	public void setJoin_date1(String join_date1) {
		this.join_date1 = join_date1;
	}
	public void setCreate_time1(String create_time1) {
		this.create_time1 = create_time1;
	}
	public String getReport_tidy_time() {
		return report_tidy_time;
	}
	public String getReport_tidy_time1() {
		return report_tidy_time1;
	}
	public void setReport_tidy_time(String report_tidy_time) {
		this.report_tidy_time = report_tidy_time;
	}
	public void setReport_tidy_time1(String report_tidy_time1) {
		this.report_tidy_time1 = report_tidy_time1;
	}
}
