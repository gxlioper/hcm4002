package com.hjw.wst.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "batch_Per_plan")
public class BatchProPlan implements java.io.Serializable {
	 private static final long serialVersionUID = -97502163798576023L;
		@Id
		@GenericGenerator(name = "idGenerator", strategy = "identity")
		@GeneratedValue(generator = "idGenerator")
		private long id;

		 @Column(name = "plandate")
		private String plandate;
		
		 @Column(name = "batch_id")
		private long batch_id;
		
		 @Column(name = "per_num")
		private long per_num;
		
		 @Column(name = "creater")
		private long creater;
		
		 @Column(name = "creater_date")
		private String creater_date;
		
		 @Column(name = "remark")
		private String remark;
	    
	   
	    public long getId() {
			return id;
		}

		public void setId(long id) {
			this.id = id;
		}

		public String getPlandate() {
			return plandate;
		}

		public void setPlandate(String plandate) {
			this.plandate = plandate;
		}

		public long getBatch_id() {
			return batch_id;
		}

		public void setBatch_id(long batch_id) {
			this.batch_id = batch_id;
		}

		public long getPer_num() {
			return per_num;
		}

		public void setPer_num(long per_num) {
			this.per_num = per_num;
		}

		public long getCreater() {
			return creater;
		}

		public void setCreater(long creater) {
			this.creater = creater;
		}

		public String getCreater_date() {
			return creater_date;
		}

		public void setCreater_date(String creater_date) {
			this.creater_date = creater_date;
		}

		public String getRemark() {
			return remark;
		}

		public void setRemark(String remark) {
			this.remark = remark;
		}
		
	}