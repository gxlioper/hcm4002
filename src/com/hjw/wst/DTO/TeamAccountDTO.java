package com.hjw.wst.DTO;

import javax.persistence.Column;

/**
 * 
     * @Title:  火箭蛙体检管理系统   
     * @Package com.hjw.wst.domain   
     * @Description:  团体结算主表
     * @author: yangm     
     * @date:   2016年12月13日 下午4:54:02   
     * @version V2.0.0.0
 */

public class TeamAccountDTO implements java.io.Serializable {
	 private static final long serialVersionUID = -97502163798576023L;

		private long id;

		private long batchid;
		
		private String contract_num;

	    private String acc_num=""; 

	    private String acc_date;

	    private long acc_operator;
	    
	    private String acc_user="";

	    private  double prices;

	    private  double charges;
	    
	    private  double trueamt;

	    private  double dec_charges;

	    private  String acc_stauts="";
	    
	    private  String acc_stautss="";

	    private  String invoice_no="";

	    private  String invoice_name="";

	    private  String linker="";

	    private  String phone="";

	    private  long auditor;
	    
	    private String auditer_user="";

	    private  String audit_date;

	    private  String balance_status="";
	    
	    private  String balance_statuss="";

	    private  int balancer;
	    
	    private String balance_user="";

	    private  String balance_date;

	    private  String note="";  

	    private String center_num=""; 	 
	    
        private String acc_name="";

	    private String remark="";	
	    
        private int totalcustume;//总人数
        
        private int totalexam;//总人次        
        
        private double additional;//附加费用

		public double getAdditional() {
			return additional;
		}

		public void setAdditional(double additional) {
			this.additional = additional;
		}

		public int getTotalcustume() {
			return totalcustume;
		}

		public void setTotalcustume(int totalcustume) {
			this.totalcustume = totalcustume;
		}

		public int getTotalexam() {
			return totalexam;
		}

		public void setTotalexam(int totalexam) {
			this.totalexam = totalexam;
		}

		public String getAcc_name() {
			return acc_name;
		}

		public void setAcc_name(String acc_name) {
			this.acc_name = acc_name;
		}

		public String getRemark() {
			return remark;
		}

		public void setRemark(String remark) {
			this.remark = remark;
		}


		public String getAcc_user() {
			return acc_user;
		}

		public void setAcc_user(String acc_user) {
			this.acc_user = acc_user;
		}

		public String getAuditer_user() {
			return auditer_user;
		}

		public void setAuditer_user(String auditer_user) {
			this.auditer_user = auditer_user;
		}

		public String getBalance_user() {
			return balance_user;
		}

		public void setBalance_user(String balance_user) {
			this.balance_user = balance_user;
		}

		public double getTrueamt() {
			return trueamt;
		}

		public void setTrueamt(double trueamt) {
			this.trueamt = trueamt;
		}

		public String getCenter_num() {
			return center_num;
		}

		public void setCenter_num(String center_num) {
			this.center_num = center_num;
		}
	    
		public void setBalance_date(String balance_date) {
			this.balance_date = balance_date;
		}

		public String getContract_num() {
			return contract_num;
		}

		public void setContract_num(String contract_num) {
			this.contract_num = contract_num;
		}

		public String getAcc_stautss() {
			return acc_stautss;
		}

		public void setAcc_stautss(String acc_stautss) {
			this.acc_stautss = acc_stautss;
		}

		public String getBalance_statuss() {
			return balance_statuss;
		}

		public void setBalance_statuss(String balance_statuss) {
			this.balance_statuss = balance_statuss;
		}

		public void setAcc_date(String acc_date) {
			this.acc_date = acc_date;
		}

		public void setAudit_date(String audit_date) {
			this.audit_date = audit_date;
		}

		public void setBalance_date1(String balance_date1) {
			this.balance_date = balance_date1;
		}

		public long getId() {
			return id;
		}

		public void setId(long id) {
			this.id = id;
		}

		public long getBatchid() {
			return batchid;
		}

		public void setBatchid(long batchid) {
			this.batchid = batchid;
		}

		public String getAcc_num() {
			return acc_num;
		}

		public void setAcc_num(String acc_num) {
			this.acc_num = acc_num;
		}

		public long getAcc_operator() {
			return acc_operator;
		}

		public void setAcc_operator(long acc_operator) {
			this.acc_operator = acc_operator;
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

		public double getDec_charges() {
			return dec_charges;
		}

		public void setDec_charges(double dec_charges) {
			this.dec_charges = dec_charges;
		}

		public String getAcc_stauts() {
			return acc_stauts;
		}

		public void setAcc_stauts(String acc_stauts) {
			this.acc_stauts = acc_stauts;
			if("Y".equals(acc_stauts)){
				this.setAcc_stautss("已审");
			}else if("N".equals(acc_stauts)){
				this.setAcc_stautss("未审");
			}
			
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

		public String getBalance_status() {
			return balance_status;
		}

		public void setBalance_status(String balance_status) {
			this.balance_status = balance_status;
			if("N".equals(balance_status)){
				this.setBalance_statuss("未结帐");
			}else if("Y".equals(balance_status)){
				this.setBalance_statuss("已结账");
			}
		}

		public int getBalancer() {
			return balancer;
		}

		public void setBalancer(int balancer) {
			this.balancer = balancer;
		}

		public String getNote() {
			return note;
		}

		public void setNote(String note) {
			this.note = note;
		}

		public String getAcc_date() {
			return acc_date;
		}

		public String getAudit_date() {
			return audit_date;
		}

		public String getBalance_date() {
			return balance_date;
		}       
		
		
	}