package com.hjw.wst.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 
     * @Title:  火箭蛙体检管理系统   
     * @Package com.hjw.wst.domain   
     * @Description:  团体结算主表
     * @author: yangm     
     * @date:   2016年12月13日 下午4:54:02   
     * @version V2.0.0.0
 */
@Entity
@Table(name = "Team_Account")
public class TeamAccount implements java.io.Serializable {
	 private static final long serialVersionUID = -97502163798576023L;
		@Id
		@GenericGenerator(name = "idGenerator", strategy = "identity")
		@GeneratedValue(generator = "idGenerator")
		private long id;

	    @Column(name = "batchid")
		private long batchid;
	    
	    @Column(name = "acc_num")
	    private String acc_num=""; 
	    
	    @Column(name = "acc_date")    
	    private String acc_date=null;

	    @Column(name = "acc_operator")
	    private long acc_operator;
 
	    @Column(name = "prices")
	    private  double prices;
 
	    @Column(name = "charges")
	    private  double charges;
 
	    @Column(name = "dec_charges")
	    private  double dec_charges;
 
	    @Column(name = "acc_stauts")
	    private  String acc_stauts="";
 
	    @Column(name = "invoice_no")
	    private  String invoice_no="";
 
	    @Column(name = "invoice_name")
	    private  String invoice_name="";
 
	    @Column(name = "linker")
	    private  String linker="";
 
	    @Column(name = "phone")
	    private  String phone="";
 
	    @Column(name = "auditor")
	    private  long auditor;
 
	    @Column(name = "audit_date")
	    private  String audit_date=null;
 
	    @Column(name = "balance_status")
	    private  String balance_status="";
 
	    @Column(name = "balancer")
	    private  int balancer;
 
	    @Column(name = "balance_date")
	    private  String balance_date=null;
 
	    @Column(name = "note")
	    private  String note="";
	    
	    @Column(name = "center_num")
	    private String center_num="";  
	    
	    @Column(name = "acc_name")
	    private String acc_name;
	    
	    @Column(name = "remark")
	    private String remark;	    

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

		public String getCenter_num() {
			return center_num;
		}

		public void setCenter_num(String center_num) {
			this.center_num = center_num;
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

		public String getAcc_date() {
			return acc_date;
		}

		public void setAcc_date(String acc_date) {
			this.acc_date = acc_date;
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

		public int getBalancer() {
			return balancer;
		}

		public void setBalancer(int balancer) {
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
	}