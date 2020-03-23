package com.hjw.charge.DTO;

import java.util.Date;

public class InsuranceOpSignStatusDTO implements java.io.Serializable {
		private static final long serialVersionUID = -97502163798576023L;
	 	private int operator_id;    //操作员ID
	 	private String   op_work_num;     //操作员工号
		private int   prov_sign_status;  //省医保签到签退状态(0:签退;1:签到)
		private int  city_sign_status;   //市医保签到签退状态(0:签退;1:签到)
		private String   prov_cycle_code;   //省医保当前业务周期号
		private String   city_cycle_code;   //市医保当前业务周期号
		private Date  prov_sign_date; //省医保最后签到签退时间
		private Date  city_sign_date; //市医保最后签到签退时间
		public int getOperator_id() {
			return operator_id;
		}
		public void setOperator_id(int operator_id) {
			this.operator_id = operator_id;
		}
		public String getOp_work_num() {
			return op_work_num;
		}
		public void setOp_work_num(String op_work_num) {
			this.op_work_num = op_work_num;
		}
		public int getProv_sign_status() {
			return prov_sign_status;
		}
		public void setProv_sign_status(int prov_sign_status) {
			this.prov_sign_status = prov_sign_status;
		}
		public int getCity_sign_status() {
			return city_sign_status;
		}
		public void setCity_sign_status(int city_sign_status) {
			this.city_sign_status = city_sign_status;
		}
		public String getProv_cycle_code() {
			return prov_cycle_code;
		}
		public void setProv_cycle_code(String prov_cycle_code) {
			this.prov_cycle_code = prov_cycle_code;
		}
		public String getCity_cycle_code() {
			return city_cycle_code;
		}
		public void setCity_cycle_code(String city_cycle_code) {
			this.city_cycle_code = city_cycle_code;
		}
		public Date getProv_sign_date() {
			return prov_sign_date;
		}
		public void setProv_sign_date(Date prov_sign_date) {
			this.prov_sign_date = prov_sign_date;
		}
		public Date getCity_sign_date() {
			return city_sign_date;
		}
		public void setCity_sign_date(Date city_sign_date) {
			this.city_sign_date = city_sign_date;
		}
		
	}