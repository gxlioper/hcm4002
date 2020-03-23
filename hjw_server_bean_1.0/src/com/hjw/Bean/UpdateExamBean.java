package com.hjw.Bean;

public class UpdateExamBean implements java.io.Serializable {
	 private static final long serialVersionUID = -97502163798576023L;

		private String idnum="";
		
		private String examnum="";
		
		private String sex="";
		
		private long old;
		
		private String phone="";
		
		private String birthday="";
		
		private String marriage="";
		
		private String address="";
		
		private String reportWay="";
		
		private String reportAddress="";

		private String picbase64="";	
		
		public String getBirthday() {
			return birthday;
		}

		public void setBirthday(String birthday) {
			this.birthday = birthday;
		}

		public String getReportWay() {
			return reportWay;
		}

		public void setReportWay(String reportWay) {
			this.reportWay = reportWay;
		}

		public String getReportAddress() {
			return reportAddress;
		}

		public void setReportAddress(String reportAddress) {
			this.reportAddress = reportAddress;
		}

		public String getExamnum() {
			return examnum;
		}

		public void setExamnum(String examnum) {
			this.examnum = examnum;
		}

		public String getSex() {
			return sex;
		}

		public void setSex(String sex) {
			this.sex = sex;
		}

		public long getOld() {
			return old;
		}

		public void setOld(long old) {
			this.old = old;
		}

		public String getPhone() {
			return phone;
		}

		public void setPhone(String phone) {
			this.phone = phone;
		}

		public String getMarriage() {
			return marriage;
		}

		public void setMarriage(String marriage) {
			this.marriage = marriage;
		}

		public String getAddress() {
			return address;
		}

		public void setAddress(String address) {
			this.address = address;
		}

		public String getIdnum() {
			return idnum;
		}

		public void setIdnum(String idnum) {
			this.idnum = idnum;
		}

		public String getPicbase64() {
			return picbase64;
		}

		public void setPicbase64(String picbase64) {
			this.picbase64 = picbase64;
		}
		
	}