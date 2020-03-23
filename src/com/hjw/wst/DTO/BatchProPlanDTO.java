package com.hjw.wst.DTO;

public class BatchProPlanDTO implements java.io.Serializable {
	 private static final long serialVersionUID = -97502163798576023L;

		private long id;

		private String plandate;

		private long batch_id;

		private long per_num;

		private long creater;

		private String creater_date;

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