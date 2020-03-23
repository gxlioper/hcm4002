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

public class TeamAccountWayDTO implements java.io.Serializable {
	 private static final long serialVersionUID = -97502163798576023L;

		private long id;

	    private String account_num="";

	    private double amount;

	    private String charging_way="";
	    
	    private String chargingwayname;

	    private String update_time;

	    private String center_num="";
	    private String datacodechildren="";

		public long getId() {
			return id;
		}

		public void setId(long id) {
			this.id = id;
		}

		public String getAccount_num() {
			return account_num;
		}

		public void setAccount_num(String account_num) {
			this.account_num = account_num;
		}

		public double getAmount() {
			return amount;
		}

		public void setAmount(double amount) {
			this.amount = amount;
		}

		public String getCharging_way() {
			return charging_way;
		}

		public void setCharging_way(String charging_way) {
			this.charging_way = charging_way;
		}

		public String getChargingwayname() {
			return chargingwayname;
		}

		public void setChargingwayname(String chargingwayname) {
			this.chargingwayname = chargingwayname;
		}

		public String getUpdate_time() {
			return update_time;
		}

		public void setUpdate_time(String update_time) {
			this.update_time = update_time;
		}

		public String getCenter_num() {
			return center_num;
		}

		public void setCenter_num(String center_num) {
			this.center_num = center_num;
		}

		public String getDatacodechildren() {
			return datacodechildren;
		}

		public void setDatacodechildren(String datacodechildren) {
			this.datacodechildren = datacodechildren;
		}  
		
		

	    
	}