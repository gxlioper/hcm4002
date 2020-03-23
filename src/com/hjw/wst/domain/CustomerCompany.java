package com.hjw.wst.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "customer_company")
public class CustomerCompany implements java.io.Serializable {
	 private static final long serialVersionUID = -97502163798576023L;
		@Id
		@GenericGenerator(name = "idGenerator", strategy = "identity")
		@GeneratedValue(generator = "idGenerator")
		private long id;

		@Column(name = "com_id")
		private long com_id;		
	    
	    @Column(name = "batchcom_id")
	    private long batchcom_id;
	    
	    @Column(name = "center_id")
	    private long center_id;
		
		@Column(name = "remark")
		private String remark;
		
		@Column(name = "remark2")
		private String remark2;
	    
	    @Column(name = "creater")
	    private long creater;
	    
	    @Column(name = "create_time")
	    private Date create_time;
	    
	    @Column(name = "updater")
	    private long updater;
	    
	    @Column(name = "update_time")
	    private Date update_time;	    

		public long getBatchcom_id() {
			return batchcom_id;
		}

		public void setBatchcom_id(long batchcom_id) {
			this.batchcom_id = batchcom_id;
		}

		public long getCenter_id() {
			return center_id;
		}

		public void setCenter_id(long center_id) {
			this.center_id = center_id;
		}

		public long getId() {
			return id;
		}

		public void setId(long id) {
			this.id = id;
		}

		public long getCom_id() {
			return com_id;
		}

		public void setCom_id(long com_id) {
			this.com_id = com_id;
		}

		public String getRemark() {
			return remark;
		}

		public void setRemark(String remark) {
			this.remark = remark;
		}

		public String getRemark2() {
			return remark2;
		}

		public void setRemark2(String remark2) {
			this.remark2 = remark2;
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