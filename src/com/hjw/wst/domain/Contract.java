package com.hjw.wst.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "contract")
public class Contract implements java.io.Serializable {
	 private static final long serialVersionUID = -97502163798576023L;
		@Id
		@GenericGenerator(name = "idGenerator", strategy = "identity")
		@GeneratedValue(generator = "idGenerator")
		private long id;

		@Column(name = "company_id")
		private long company_id;
		
		@Column(name = "company_name")
		private String company_name;
		
		@Column(name = "batch_id")
		private long batch_id;
		
		@Column(name = "batch_name")
		private String batch_name;
		
		@Column(name = "types")
		private int types;//类型1：审核未通过；2 审核通过；0合同未审核 9合同无效
		
		@Column(name = "contract_num")
		private String contract_num;
		
		@Column(name = "validity_date")
		private String validity_date;
		
		@Column(name = "remark")
		private String remark;
		
		@Column(name = "linkman")
		private String linkman;
		
		@Column(name = "tel")
		private String tel;	    
	    
	    @Column(name = "creater")
	    private long creater;
	    
	    @Column(name = "create_time")
	    private Date create_time;
	    
	    @Column(name = "updater")
	    private long updater;
	    
	    @Column(name = "update_time")
	    private Date update_time; 
	    
	    @Column(name = "checknotice")
	    private String checknotice;
	    
	    @Column(name = "checkuser")
	    private long checkuser;
	    
	    @Column(name = "checkdate")
	    private String checkdate;	    
	    
	    public String getChecknotice() {
			return checknotice;
		}

		public void setChecknotice(String checknotice) {
			this.checknotice = checknotice;
		}

		public long getCheckuser() {
			return checkuser;
		}

		public void setCheckuser(long checkuser) {
			this.checkuser = checkuser;
		}

		public String getCheckdate() {
			return checkdate;
		}

		public void setCheckdate(String checkdate) {
			this.checkdate = checkdate;
		}

		public String getCompany_name() {
			return company_name;
		}

		public void setCompany_name(String company_name) {
			this.company_name = company_name;
		}

		public long getBatch_id() {
			return batch_id;
		}

		public void setBatch_id(long batch_id) {
			this.batch_id = batch_id;
		}

		public int getTypes() {
			return types;
		}

		public void setTypes(int types) {
			this.types = types;
		}

		public String getContract_num() {
			return contract_num;
		}

		public void setContract_num(String contract_num) {
			this.contract_num = contract_num;
		}

		public String getValidity_date() {
			return validity_date;
		}

		public void setValidity_date(String validity_date) {
			this.validity_date = validity_date;
		}

		public String getRemark() {
			return remark;
		}

		public void setRemark(String remark) {
			this.remark = remark;
		}

		public String getLinkman() {
			return linkman;
		}

		public void setLinkman(String linkman) {
			this.linkman = linkman;
		}

		public String getTel() {
			return tel;
		}

		public void setTel(String tel) {
			this.tel = tel;
		}

		public long getId() {
			return id;
		}

		public void setId(long id) {
			this.id = id;
		}

		public long getCompany_id() {
			return company_id;
		}

		public void setCompany_id(long company_id) {
			this.company_id = company_id;
		}

		public String getBatch_name() {
			return batch_name;
		}

		public void setBatch_name(String batch_name) {
			this.batch_name = batch_name;
		}


		public long getCreater() {
			return creater;
		}

		public void setCreater(long creater) {
			this.creater = creater;
		}

		public Date getCreate_time() {
			return create_time;
		}

		public void setCreate_time(Date create_time) {
			this.create_time = create_time;
		}

		public long getUpdater() {
			return updater;
		}

		public void setUpdater(long updater) {
			this.updater = updater;
		}

		public Date getUpdate_time() {
			return update_time;
		}

		public void setUpdate_time(Date update_time) {
			this.update_time = update_time;
		}


	}