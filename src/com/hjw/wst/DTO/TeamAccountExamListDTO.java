package com.hjw.wst.DTO;


/**
 * 
     * @Title:  火箭蛙体检管理系统   
     * @Package com.hjw.wst.domain   
     * @Description:  团体结算体检人员明细表
     * @author: yangm     
     * @date:   2016年12月13日 下午4:54:02   
     * @version V2.0.0.0
 */
public class TeamAccountExamListDTO implements java.io.Serializable {
	 private static final long serialVersionUID = -97502163798576023L;

		private long id;

		private long batchid;

	    private String acc_num="";

	    private String exam_num="";

	    private String isPrePay="";
	    
	    private String isPrePays="";

	    private String isnotPay="";
	    
	    private String isnotPays="";

	    private String createtime;
        private String dep_name;
	    private String center_num=""; 
	    private String status="";
	    private String id_num="";
	    private String user_name="";
	    private String sex="";
	    private long age;
        private String phone="";   
        private String join_date="";
        private double totalamt;
        private String tjlx="";
        
		public String getTjlx() {
			return tjlx;
		}

		public void setTjlx(String tjlx) {
			this.tjlx = tjlx;
		}

		public String getDep_name() {
			return dep_name;
		}

		public void setDep_name(String dep_name) {
			this.dep_name = dep_name;
		}

		public String getStatus() {
			return status;
		}

		public void setStatus(String status) {
			if ("Y".equals(status)){
				this.status=("Y预约");
			}else if ("D".equals(status)){
				this.status=("D登记");
			}else if ("J".equals(status)){
				this.status=("J检查中");
			}else if ("Z".equals(status)){
				this.status=("Z已终检");
			}else{
				this.status=("未知");
			}
		}

		
		public String getJoin_date() {
			return join_date;
		}

		public void setJoin_date(String join_date) {
			this.join_date = join_date;
		}

		public double getTotalamt() {
			return totalamt;
		}

		public void setTotalamt(double totalamt) {
			this.totalamt = totalamt;
		}

		public String getId_num() {
			return id_num;
		}

		public void setId_num(String id_num) {
			this.id_num = id_num;
		}

		public String getUser_name() {
			return user_name;
		}

		public void setUser_name(String user_name) {
			this.user_name = user_name;
		}

		public String getSex() {
			return sex;
		}

		public void setSex(String sex) {
			this.sex = sex;
		}

		public long getAge() {
			return age;
		}

		public void setAge(long age) {
			this.age = age;
		}

		public String getPhone() {
			return phone;
		}

		public void setPhone(String phone) {
			this.phone = phone;
		}

		public String getIsPrePays() {
			return isPrePays;
		}

		public void setIsPrePays(String isPrePays) {
			this.isPrePays = isPrePays;
		}

		public String getIsnotPays() {
			return isnotPays;
		}

		public void setIsnotPays(String isnotPays) {
			this.isnotPays = isnotPays;
		}

		public String getCenter_num() {
			return center_num;
		}

		public void setCenter_num(String center_num) {
			this.center_num = center_num;
		}

		public long getId() {
			return id;
		}

		public void setId(long id) {
			this.id = id;
		}

		public long getBatchid() {
			return batchid;
		}

		public void setBatchid(long batchid) {
			this.batchid = batchid;
		}

		public String getAcc_num() {
			return acc_num;
		}

		public void setAcc_num(String acc_num) {
			this.acc_num = acc_num;
		}

		public String getExam_num() {
			return exam_num;
		}

		public void setExam_num(String exam_num) {
			this.exam_num = exam_num;
		}

		public String getIsPrePay() {
			return isPrePay;
		}

		public void setIsPrePay(String isPrePay) {
			this.isPrePay = isPrePay;
			if("Y".equals(isPrePay)){
				this.setIsPrePays("预结");
			}else if("N".equals(isPrePay)){
				this.setIsPrePays("非预结");
			}
		}

		public String getIsnotPay() {
			return isnotPay;
		}

		public void setIsnotPay(String isnotPay) {
			this.isnotPay = isnotPay;
			if("Y".equals(isnotPay)){
				this.setIsnotPays("含弃检");
			}else if("N".equals(isnotPay)){
				this.setIsnotPays("不含弃检");
			}
		}

		public String getCreatetime() {
			return createtime;
		}

		public void setCreatetime(String createtime) {
			this.createtime = createtime;
		}
      
	}