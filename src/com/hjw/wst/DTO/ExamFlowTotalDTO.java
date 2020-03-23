package com.hjw.wst.DTO;


public class ExamFlowTotalDTO implements java.io.Serializable {
	 private static final long serialVersionUID = -97502163798576023L;

		private String datetimes="";

        private long counts=0;

		public String getDatetimes() {
			return datetimes;
		}

		public void setDatetimes(String datetimes) {
			this.datetimes = datetimes;
		}

		public long getCounts() {
			return counts;
		}

		public void setCounts(long counts) {
			this.counts = counts;
		}
	
	}