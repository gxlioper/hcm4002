package com.hjw.Bean;

public class TrjnBean implements java.io.Serializable {
	 private static final long serialVersionUID = -97502163798576023L;

		private String status="";

		private int pay_way;
		
		private double jnamount;

		private String jntime;

	    private String orderid;

		public String getStatus() {
			return status;
		}

		public void setStatus(String status) {
			this.status = status;
		}

		public int getPay_way() {
			return pay_way;
		}

		public void setPay_way(int pay_way) {
			this.pay_way = pay_way;
		}

		public double getJnamount() {
			return jnamount;
		}

		public void setJnamount(double jnamount) {
			this.jnamount = jnamount;
		}

		public String getJntime() {
			return jntime;
		}

		public void setJntime(String jntime) {
			this.jntime = jntime;
		}

		public String getOrderid() {
			return orderid;
		}

		public void setOrderid(String orderid) {
			this.orderid = orderid;
		}   

	}