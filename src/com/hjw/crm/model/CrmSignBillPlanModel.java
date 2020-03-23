package com.hjw.crm.model;

import java.util.List;

import com.hjw.crm.domain.CrmCompanyContacts;

public class CrmSignBillPlanModel implements java.io.Serializable{

	private static final long serialVersionUID = -97502163798576023L;
	private String type;
	private String q;
	private String flag;
	private String id;
	private String project_type;
	private long company_id;//单位ID
	private String com_name;
	private long com_Level;
	private String com_type;
	private String address;
	private String remark;
	private String name_pinyin;
	private String economiccode;//经济类型
	private String industrycode;//行业类型
	private String areacode;//行政区划
	private String comsizecode;//企业规模	
	private String project_reson;
	private String sign_num;//签单计划编号
	
	private String sign_name;//签单计划名称
	
	private String sign_pingying;//拼音助记码
	
	private String sign_year;//年度
	
	private String sign_type;//签单类型
	
	private String old_new_type;//新旧分类
	
	private String customer_type;//客户分类
	
	private long sign_count;//签单数量
	
	private String sign_date;//预计签单日期
	
	private Long sign_persion;//预计体检人数
	
	private Double sign_amount;//预计体检金额
	
	private String end_date;//预计体检结束日期
	
	private String track_progress;//跟踪进度
	
	private String track_time;//跟踪进度变化时间
	
	private String sign_status;//签单计划状态
	
	private String protect_date;//保护日期
	
	private String abort_date;//保护截止日期
	
	private String contacts;
	
    private String ids;
    private String sign_names;
    private String updatestatus;
    private String creater;
    private String project_name;
    private long userid;
    private String project_id;
    private String signstartTime;
    private String signendTime;
    private String tubiao;
    
    
	public String getTubiao() {
		return tubiao;
	}

	public void setTubiao(String tubiao) {
		this.tubiao = tubiao;
	}

	public String getSignstartTime() {
		return signstartTime;
	}

	public void setSignstartTime(String signstartTime) {
		this.signstartTime = signstartTime;
	}

	public String getSignendTime() {
		return signendTime;
	}

	public void setSignendTime(String signendTime) {
		this.signendTime = signendTime;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getProject_id() {
		return project_id;
	}

	public void setProject_id(String project_id) {
		this.project_id = project_id;
	}

	public String getProject_name() {
		return project_name;
	}

	public void setProject_name(String project_name) {
		this.project_name = project_name;
	}

	public String getProject_type() {
		return project_type;
	}

	public void setProject_type(String project_type) {
		this.project_type = project_type;
	}

	public String getProject_reson() {
		return project_reson;
	}

	public void setProject_reson(String project_reson) {
		this.project_reson = project_reson;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getCreater() {
		return creater;
	}

	public void setCreater(String creater) {
		this.creater = creater;
	}

	public String getUpdatestatus() {
		return updatestatus;
	}

	public void setUpdatestatus(String updatestatus) {
		this.updatestatus = updatestatus;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public String getSign_names() {
		return sign_names;
	}

	public void setSign_names(String sign_names) {
		this.sign_names = sign_names;
	}

	private List<CrmCompanyContacts> contactsList;

	public String getCom_name() {
		return com_name;
	}

	public void setCom_name(String com_name) {
		this.com_name = com_name;
	}

	public String getCom_type() {
		return com_type;
	}

	public void setCom_type(String com_type) {
		this.com_type = com_type;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public long getCompany_id() {
		return company_id;
	}

	public void setCompany_id(long company_id) {
		this.company_id = company_id;
	}

	public String getSign_num() {
		return sign_num;
	}

	public void setSign_num(String sign_num) {
		this.sign_num = sign_num;
	}

	public String getSign_name() {
		return sign_name;
	}

	public void setSign_name(String sign_name) {
		this.sign_name = sign_name;
	}

	public String getSign_year() {
		return sign_year;
	}

	public void setSign_year(String sign_year) {
		this.sign_year = sign_year;
	}

	public String getSign_type() {
		return sign_type;
	}

	public void setSign_type(String sign_type) {
		this.sign_type = sign_type;
	}

	public String getOld_new_type() {
		return old_new_type;
	}

	public void setOld_new_type(String old_new_type) {
		this.old_new_type = old_new_type;
	}

	public String getCustomer_type() {
		return customer_type;
	}

	public void setCustomer_type(String customer_type) {
		this.customer_type = customer_type;
	}

	public long getSign_count() {
		return sign_count;
	}

	public void setSign_count(long sign_count) {
		this.sign_count = sign_count;
	}

	public String getSign_date() {
		return sign_date;
	}

	public void setSign_date(String sign_date) {
		this.sign_date = sign_date;
	}

	public Long getSign_persion() {
		return sign_persion;
	}

	public void setSign_persion(Long sign_persion) {
		this.sign_persion = sign_persion;
	}

	public Double getSign_amount() {
		return sign_amount;
	}

	public void setSign_amount(Double sign_amount) {
		this.sign_amount = sign_amount;
	}

	public String getEnd_date() {
		return end_date;
	}

	public void setEnd_date(String end_date) {
		this.end_date = end_date;
	}

	public String getTrack_progress() {
		return track_progress;
	}

	public void setTrack_progress(String track_progress) {
		this.track_progress = track_progress;
	}

	public String getTrack_time() {
		return track_time;
	}

	public void setTrack_time(String track_time) {
		this.track_time = track_time;
	}

	public String getSign_status() {
		return sign_status;
	}

	public void setSign_status(String sign_status) {
		this.sign_status = sign_status;
	}

	public String getProtect_date() {
		return protect_date;
	}

	public void setProtect_date(String protect_date) {
		this.protect_date = protect_date;
	}

	public String getAbort_date() {
		return abort_date;
	}

	public void setAbort_date(String abort_date) {
		this.abort_date = abort_date;
	}

	public String getSign_pingying() {
		return sign_pingying;
	}

	public void setSign_pingying(String sign_pingying) {
		this.sign_pingying = sign_pingying;
	}

	public String getContacts() {
		return contacts;
	}

	public void setContacts(String contacts) {
		this.contacts = contacts;
	}

	public List<CrmCompanyContacts> getContactsList() {
		return contactsList;
	}

	public void setContactsList(List<CrmCompanyContacts> contactsList) {
		this.contactsList = contactsList;
	}

	public long getCom_Level() {
		return com_Level;
	}

	public void setCom_Level(long com_Level) {
		this.com_Level = com_Level;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getName_pinyin() {
		return name_pinyin;
	}

	public void setName_pinyin(String name_pinyin) {
		this.name_pinyin = name_pinyin;
	}

	public String getEconomiccode() {
		return economiccode;
	}

	public void setEconomiccode(String economiccode) {
		this.economiccode = economiccode;
	}

	public String getIndustrycode() {
		return industrycode;
	}

	public void setIndustrycode(String industrycode) {
		this.industrycode = industrycode;
	}

	public String getAreacode() {
		return areacode;
	}

	public void setAreacode(String areacode) {
		this.areacode = areacode;
	}

	public String getComsizecode() {
		return comsizecode;
	}

	public void setComsizecode(String comsizecode) {
		this.comsizecode = comsizecode;
	}

	public String getQ() {
		return q;
	}

	public void setQ(String q) {
		this.q = q;
	}

	public long getUserid() {
		return userid;
	}

	public void setUserid(long userid) {
		this.userid = userid;
	}
}
