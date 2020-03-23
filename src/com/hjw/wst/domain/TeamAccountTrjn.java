package com.hjw.wst.domain;

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
@Table(name = "Team_Account_trjn")
public class TeamAccountTrjn implements java.io.Serializable {
	 private static final long serialVersionUID = -97502163798576023L;
		@Id
		@GenericGenerator(name = "idGenerator", strategy = "identity")
		@GeneratedValue(generator = "idGenerator")
		private long id;

	    @Column(name = "batchid")
		private long batchid;
	    
	    @Column(name = "acc_num")
	    private String acc_num=""; 
	    
	    @Column(name = "exam_num")
	    private String exam_num="";
	    
	    @Column(name = "arch_num")
	    private String arch_num="";
	    
	    @Column(name = "is_active")
	    private String is_active="";
	    
	    @Column(name = "creater")
	    private long creater;
	    
	    @Column(name = "createdate")	    
	    private String createdate="";
	    
	    @Column(name = "updater")
	    private long updater;
	    
	    @Column(name = "updatedate")	    
	    private String updatedate="";
	    
	    @Column(name = "printflag")	    
	    private String printflag="";
	    
	    @Column(name = "printer")
	    private long printer;
	    
	    @Column(name = "printdate")
	    private String printdate="";
 
	    @Column(name = "prices")
	    private  double prices;
 
	    @Column(name = "charges")
	    private  double charges; 
	    
	    @Column(name = "center_num")
	    private String center_num="";  
	   
	    @Column(name = "remark")
	    private String remark;
	    
	    

		public long getUpdater() {
			return updater;
		}

		public void setUpdater(long updater) {
			this.updater = updater;
		}

		public String getUpdatedate() {
			return updatedate;
		}

		public void setUpdatedate(String updatedate) {
			this.updatedate = updatedate;
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

		public String getExam_num() {
			return exam_num;
		}

		public void setExam_num(String exam_num) {
			this.exam_num = exam_num;
		}

		public String getArch_num() {
			return arch_num;
		}

		public void setArch_num(String arch_num) {
			this.arch_num = arch_num;
		}

		public String getIs_active() {
			return is_active;
		}

		public void setIs_active(String is_active) {
			this.is_active = is_active;
		}

		public long getCreater() {
			return creater;
		}

		public void setCreater(long creater) {
			this.creater = creater;
		}

		public String getCreatedate() {
			return createdate;
		}

		public void setCreatedate(String createdate) {
			this.createdate = createdate;
		}

		public String getPrintflag() {
			return printflag;
		}

		public void setPrintflag(String printflag) {
			this.printflag = printflag;
		}

		public long getPrinter() {
			return printer;
		}

		public void setPrinter(long printer) {
			this.printer = printer;
		}

		public String getPrintdate() {
			return printdate;
		}

		public void setPrintdate(String printdate) {
			this.printdate = printdate;
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

		public String getCenter_num() {
			return center_num;
		}

		public void setCenter_num(String center_num) {
			this.center_num = center_num;
		}

		public String getRemark() {
			return remark;
		}

		public void setRemark(String remark) {
			this.remark = remark;
		}	           
	}