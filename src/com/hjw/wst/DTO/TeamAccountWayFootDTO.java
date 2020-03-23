package com.hjw.wst.DTO;

/**
 * 
     * @Title:  火箭蛙体检管理系统   
     * @Package com.hjw.wst.domain   
     * @Description: 团体结算支付方式
     * @author: yangm     
     * @date:   2016年12月13日 下午4:54:02   
     * @version V2.0.0.0
 */

public class TeamAccountWayFootDTO implements java.io.Serializable {
	 private static final long serialVersionUID = -97502163798576023L;

	    private double amount;

	    private String account_num="";
	    
	    private String ck="";		

		public double getAmount() {
			return amount;
		}

		public void setAmount(double amount) {
			this.amount = amount;
		}

		public String getAccount_num() {
			return account_num;
		}

		public void setAccount_num(String account_num) {
			this.account_num = account_num;
		}

		public String getCk() {
			return ck;
		}

		public void setCk(String ck) {
			this.ck = ck;
		}

	
	    
	}