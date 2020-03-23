package com.hjw.wst.DTO;


public class ContractDTO implements java.io.Serializable {
	 private static final long serialVersionUID = -97502163798576023L;

		private long id;

		private long company_id;

		private String company_name;

		private long batch_id;

		private String batch_name;

		private int types;//类型0：合同未审核，1合同审核未通过，2：合同审核通过  9合同无效
		
		private String stypes;//

		private String contract_num;

		private String validity_date;

		private String remark;

		private String linkman;

		private String tel;	    

	    private long creater;

	    private String  create_time;

	    private long updater;

	    private String update_time;   
	    	    
        private String checknotice;
	    
	    private long checkuser;
	    
	    private String checkdate;
	    
	    private String overflag;
	    
	    private String overflags; 

	    public String getOverflag() {
			return overflag;
		}

		public void setOverflag(String overflag) {
			this.overflag = overflag;
			if("1".equals(overflag)){
				this.setOverflags("已封帐");
			}else if("0".equals(overflag)){
				this.setOverflags("未封帐");
			}
		}

		public String getOverflags() {
			return overflags;
		}

		public void setOverflags(String overflags) {
			this.overflags = overflags;
		}

		public String getChecknotice() {
			return checknotice;
		}

		public void setChecknotice(String checknotice) {
			this.checknotice = checknotice;
		}

		public long getCheckuser() {
			return checkuser;
		}

		public void setCheckuser(long checkuser) {
			this.checkuser = checkuser;
		}

		public String getCheckdate() {
			return checkdate;
		}

		public void setCheckdate(String checkdate) {
			this.checkdate = checkdate;
		}


		public String getCompany_name() {
			return company_name;
		}

		public void setCompany_name(String company_name) {
			this.company_name = company_name;
		}

		public long getBatch_id() {
			return batch_id;
		}

		public void setBatch_id(long batch_id) {
			this.batch_id = batch_id;
		}

		public int getTypes() {
			return types;
		}

		//类型1：审核未通过；2 审核通过；0合同未审核
		public void setTypes(int types) {
			this.types = types;
			if(this.types==0){
				this.setStypes("合同未审核");
			}else if(this.types==1){
				this.setStypes("合同审核未通过");
			}else if(this.types==2){
				this.setStypes("合同审核通过");
			}else if(this.types==9){
				this.setStypes("无效合同");
			}else{
				this.setStypes("未审核");
			}
		}		
		
		public String getStypes() {
			return stypes;
		}

		public void setStypes(String stypes) {
			this.stypes = stypes;
		}

		public String getContract_num() {
			return contract_num;
		}

		public void setContract_num(String contract_num) {
			this.contract_num = contract_num;
		}

		public String getValidity_date() {
			return validity_date;
		}

		public void setValidity_date(String validity_date) {
			this.validity_date = validity_date;
		}

		public String getRemark() {
			return remark;
		}

		public void setRemark(String remark) {
			this.remark = remark;
		}

		public String getLinkman() {
			return linkman;
		}

		public void setLinkman(String linkman) {
			this.linkman = linkman;
		}

		public String getTel() {
			return tel;
		}

		public void setTel(String tel) {
			this.tel = tel;
		}

		public long getId() {
			return id;
		}

		public void setId(long id) {
			this.id = id;
		}

		public long getCompany_id() {
			return company_id;
		}

		public void setCompany_id(long company_id) {
			this.company_id = company_id;
		}

		public String getBatch_name() {
			return batch_name;
		}

		public void setBatch_name(String batch_name) {
			this.batch_name = batch_name;
		}


		public long getCreater() {
			return creater;
		}

		public void setCreater(long creater) {
			this.creater = creater;
		}

		public String getCreate_time() {
			return create_time;
		}

		public void setCreate_time(String create_time) {
			this.create_time = create_time;
		}

		public long getUpdater() {
			return updater;
		}

		public void setUpdater(long updater) {
			this.updater = updater;
		}

		public String getUpdate_time() {
			return update_time;
		}

		public void setUpdate_time(String update_time) {
			this.update_time = update_time;
		}


	}