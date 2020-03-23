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
@Table(name = "team_invoice_account")
public class TeamInvoiceAccount implements java.io.Serializable {
	 private static final long serialVersionUID = -97502163798576023L;
		@Id
		@GenericGenerator(name = "idGenerator", strategy = "identity")
		@GeneratedValue(generator = "idGenerator")
		private long id;

	    @Column(name = "account_num")
		private String account_num;
	    
	    @Column(name = "acc_num")
	    private String acc_num=""; 
	    
	    @Column(name = "batchid")    
	    private long batchid;
	    
	    @Column(name = "auditor")
	    private  long auditor;
 
	    @Column(name = "audit_date")
	    private  Date audit_date=null;
 
	    @Column(name = "center_num")
	    private String center_num="";

		public long getId() {
			return id;
		}

		public void setId(long id) {
			this.id = id;
		}

		public String getAccount_num() {
			return account_num;
		}

		public void setAccount_num(String account_num) {
			this.account_num = account_num;
		}

		public String getAcc_num() {
			return acc_num;
		}

		public void setAcc_num(String acc_num) {
			this.acc_num = acc_num;
		}

		public long getBatchid() {
			return batchid;
		}

		public void setBatchid(long batchid) {
			this.batchid = batchid;
		}

		public long getAuditor() {
			return auditor;
		}

		public void setAuditor(long auditor) {
			this.auditor = auditor;
		}

		public Date getAudit_date() {
			return audit_date;
		}

		public void setAudit_date(Date audit_date) {
			this.audit_date = audit_date;
		}

		public String getCenter_num() {
			return center_num;
		}

		public void setCenter_num(String center_num) {
			this.center_num = center_num;
		}    

       
	}