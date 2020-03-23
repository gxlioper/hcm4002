package com.hjw.wst.model;

/**
 * 
     * @Title:  火箭蛙体检管理系统   
     * @Package com.hjw.wst.domain   
     * @Description:  
     * @author: yangm     
     * @date:   2016年7月4日 上午11:18:07   
     * @version V2.0.0.0
 */
public class StatementsModel implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private long id;	
	
	private String center_num="";//体检中心编码		
	
	private String time1="";
	
	private String time2="";
	
	private String ids="";
	
	private long batchid;
	
	private long company_id;
	   
	private String comname="";  
	    
	private String batch_name="";  	
	
	private String batch_num="";
	
	private String acc_num="";	
	
	private String acc_date="";	
	
	private long group_id;	
	
	private String custname="";
	
	private String exam_num="";
	
	private String exam_status="";
	
	private String sex="";
	
	private String set_num="";	
	
	private String chkItem="";		
	
	private long set_id;
	
	private long  exam_id;	
	
	private String setentities="";	
	
	private String chargentities="";	
	
	private double prices;
	
	private double charges;
	
	private double deccharges;
	
	private String invoice_no="";
	
	private String invoice_name="";
	
	private String linker="";
 
	private String phone="";
 
	private long auditor;
	
	private String audit_date="";
	
	private String balance_status="";
	
	private long balancer;
	
	private String balance_date="";

	private String note="";	
	
	private String pay_way="";	
	
	private String customer_type_id="";	
	
	private String account_num="";	
	
	private String acc_name="";	
	
	private String levels="";	
	
	private String time3="";
	
	private String time4="";	
	
	private String isprint="";	
	
	private String invoiceprinttype="1";	
	
	private String teamadds;
	
	private double add_amount;//附加费
	
	private String tjlxs;	
	
    private long chargitemid;
    
    private Double hz_amount;//团体划账金额
    
    private Double yksp_amount;//已开发票金额
    
    private String invoiceRepeatType;
    
    private String  is_show_team_settlement_toolbar;

    private int  isovertype;
    private int  isunovertype;


	public long getChargitemid() {
		return chargitemid;
	}

	public void setChargitemid(long chargitemid) {
		this.chargitemid = chargitemid;
	}

	public String getTjlxs() {
		return tjlxs;
	}

	public void setTjlxs(String tjlxs) {
		this.tjlxs = tjlxs;
	}

	public double getAdd_amount() {
		return add_amount;
	}

	public void setAdd_amount(double add_amount) {
		this.add_amount = add_amount;
	}

	public String getInvoiceprinttype() {
		return invoiceprinttype;
	}

	public void setInvoiceprinttype(String invoiceprinttype) {
		this.invoiceprinttype = invoiceprinttype;
	}

	public String getIsprint() {
		return isprint;
	}

	public void setIsprint(String isprint) {
		this.isprint = isprint;
	}

	public String getTime3() {
		return time3;
	}

	public void setTime3(String time3) {
		this.time3 = time3;
	}

	public String getTime4() {
		return time4;
	}

	public void setTime4(String time4) {
		this.time4 = time4;
	}

	public String getLevels() {
		return levels;
	}

	public void setLevels(String levels) {
		this.levels = levels;
	}

	public String getAcc_name() {
		return acc_name;
	}

	public void setAcc_name(String acc_name) {
		this.acc_name = acc_name;
	}

	public String getAccount_num() {
		return account_num;
	}

	public void setAccount_num(String account_num) {
		this.account_num = account_num;
	}

	public String getChargentities() {
		return chargentities;
	}

	public void setChargentities(String chargentities) {
		this.chargentities = chargentities;
	}

	public String getCustomer_type_id() {
		return customer_type_id;
	}

	public void setCustomer_type_id(String customer_type_id) {
		this.customer_type_id = customer_type_id;
	}

	public String getPay_way() {
		return pay_way;
	}

	public void setPay_way(String pay_way) {
		this.pay_way = pay_way;
	}

	public String getInvoice_no() {
		return invoice_no;
	}

	public void setInvoice_no(String invoice_no) {
		this.invoice_no = invoice_no;
	}

	public String getInvoice_name() {
		return invoice_name;
	}

	public void setInvoice_name(String invoice_name) {
		this.invoice_name = invoice_name;
	}

	public String getLinker() {
		return linker;
	}

	public void setLinker(String linker) {
		this.linker = linker;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public long getAuditor() {
		return auditor;
	}

	public void setAuditor(long auditor) {
		this.auditor = auditor;
	}

	public String getAudit_date() {
		return audit_date;
	}

	public void setAudit_date(String audit_date) {
		this.audit_date = audit_date;
	}

	public String getBalance_status() {
		return balance_status;
	}

	public void setBalance_status(String balance_status) {
		this.balance_status = balance_status;
	}

	public long getBalancer() {
		return balancer;
	}

	public void setBalancer(long balancer) {
		this.balancer = balancer;
	}

	public String getBalance_date() {
		return balance_date;
	}

	public void setBalance_date(String balance_date) {
		this.balance_date = balance_date;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public double getPrices() {
		return prices;
	}

	public void setPrices(double prices) {
		this.prices = prices;
	}

	public double getCharges() {
		return charges;
	}

	public void setCharges(double charges) {
		this.charges = charges;
	}

	public double getDeccharges() {
		return deccharges;
	}

	public void setDeccharges(double deccharges) {
		this.deccharges = deccharges;
	}

	public String getSetentities() {
		return setentities;
	}

	public void setSetentities(String setentities) {
		this.setentities = setentities;
	}

	public long getExam_id() {
		return exam_id;
	}

	public void setExam_id(long exam_id) {
		this.exam_id = exam_id;
	}

	public long getSet_id() {
		return set_id;
	}

	public void setSet_id(long set_id) {
		this.set_id = set_id;
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

	public String getExam_num() {
		return exam_num;
	}

	public void setExam_num(String exam_num) {
		this.exam_num = exam_num;
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

	public long getGroup_id() {
		return group_id;
	}

	public void setGroup_id(long group_id) {
		this.group_id = group_id;
	}

	public String getAcc_date() {
		return acc_date;
	}

	public void setAcc_date(String acc_date) {
		this.acc_date = acc_date;
	}

	public String getAcc_num() {
		return acc_num;
	}

	public void setAcc_num(String acc_num) {
		this.acc_num = acc_num;
	}

	public String getBatch_num() {
		return batch_num;
	}

	public void setBatch_num(String batch_num) {
		this.batch_num = batch_num;
	}

	public long getCompany_id() {
		return company_id;
	}

	public void setCompany_id(long company_id) {
		this.company_id = company_id;
	}

	public String getComname() {
		return comname;
	}

	public void setComname(String comname) {
		this.comname = comname;
	}

	public String getBatch_name() {
		return batch_name;
	}

	public void setBatch_name(String batch_name) {
		this.batch_name = batch_name;
	}

	public long getBatchid() {
		return batchid;
	}

	public void setBatchid(long batchid) {
		this.batchid = batchid;
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

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCenter_num() {
		return center_num;
	}

	public void setCenter_num(String center_num) {
		this.center_num = center_num;
	}

	public String getTeamadds() {
		return teamadds;
	}

	public void setTeamadds(String teamadds) {
		this.teamadds = teamadds;
	}

	public Double getHz_amount() {
		return hz_amount;
	}

	public void setHz_amount(Double hz_amount) {
		this.hz_amount = hz_amount;
	}

	public Double getYksp_amount() {
		return yksp_amount;
	}

	public void setYksp_amount(Double yksp_amount) {
		this.yksp_amount = yksp_amount;
	}

	public String getInvoiceRepeatType() {
		return invoiceRepeatType;
	}

	public void setInvoiceRepeatType(String invoiceRepeatType) {
		this.invoiceRepeatType = invoiceRepeatType;
	}

	public String getIs_show_team_settlement_toolbar() {
		return is_show_team_settlement_toolbar;
	}

	public void setIs_show_team_settlement_toolbar(String is_show_team_settlement_toolbar) {
		this.is_show_team_settlement_toolbar = is_show_team_settlement_toolbar;
	}

	public int getIsovertype() {
		return isovertype;
	}

	public void setIsovertype(int isovertype) {
		this.isovertype = isovertype;
	}

	public int getIsunovertype() {
		return isunovertype;
	}

	public void setIsunovertype(int isunovertype) {
		this.isunovertype = isunovertype;
	}
}