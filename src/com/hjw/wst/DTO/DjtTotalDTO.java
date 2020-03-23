package com.hjw.wst.DTO;


public class DjtTotalDTO implements java.io.Serializable {
	 private static final long serialVersionUID = -97502163798576023L;

		private String exam_num;

        private int counts=0;

        private double totalAmt=0;

        private double personAmt=0;

        private double termAmt=0;
        
        private double personYfAmt=0;
        
        private double qfAmt=0;//欠费金额     
        
        private int totalcustume;//总人数
        
        private int totalexam;//总人次        

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

		public double getPersonYfAmt() {
			return personYfAmt;
		}

		public void setPersonYfAmt(double personYfAmt) {
			this.personYfAmt = personYfAmt;
		}

		public double getQfAmt() {
			return qfAmt;
		}

		public void setQfAmt(double qfAmt) {
			this.qfAmt = qfAmt;
		}

		public String getExam_num() {
			return exam_num;
		}

		public void setExam_num(String exam_num) {
			this.exam_num = exam_num;
		}

		public int getCounts() {
			return counts;
		}

		public void setCounts(int counts) {
			this.counts = counts;
		}

		public double getTotalAmt() {
			return totalAmt;
		}

		public void setTotalAmt(double totalAmt) {
			this.totalAmt = totalAmt;
		}

		public double getPersonAmt() {
			return personAmt;
		}

		public void setPersonAmt(double personAmt) {
			this.personAmt = personAmt;
		}

		public double getTermAmt() {
			return termAmt;
		}

		public void setTermAmt(double termAmt) {
			this.termAmt = termAmt;
		}
	
	}