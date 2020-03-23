package com.hjw.zyb.model;


public class FsxzybImpCustomerInfoModel implements java.io.Serializable {
	 private static final long serialVersionUID = -97502163798576023L;
	 
	 private String group_index="";

	    private long id;

		private long company_id;

		private long batch_id;

		private long contract_id;		

		private String arch_num="";

		private String id_num="";

		private String sex="";

		private String birthday="";

		private String custname="";

		private long age;

		private String is_marriage="";

		private String position="";

		private String _level="";

		private String tel="";

		private String remark="";

		private String customer_type="";

		private String others="";

		private long flags;

		private String notices="";

	    private long creater;

	    private String create_time="";
	    
	    private String ids="";	  
	    
	    private long exam_id;
	    
	    private long dept_id;
	    
	    private long group_id;	
	    
	    private long customer_type_id;
	    
	    private String scustomer_type_id="";
	    
	    private String comname="";
	    
	    private String batch_name="";  
	    
	    private String Batch_num="";
	    
	    private String email="";	    
	    
	    private String mz="";//民族    
	    
	    private String person_shenfen="";//身份类别
	    
	    private String sftype="";//收费类型
	    
	    private String hansidjdflag="";	
	    
	    private String time1="";
	    
	    private String time2="";  
	    
	    private String exam_type=""; 	   
	    
	    private String status="";
	    
	    private long customer_id;
	    
	    private String chargingType="";
	    
	    private String exam_num="";
	    
	    private String itementities="";  
	    
	    private String setentities;
	    
	    private String companybatch_id="";  
	    
	    private String employeeID="";

		private String occusector="";//行业

		private String occutypeofwork="";//工种	
		
		private String occusectorid="";
		private String occutypeofworkid="";
	
		private String zytjlb;//职业体检类别		

		private String app_type="1";	
		private String exam_indicator="T";		
		
		private String born_address;//工种
	    private String nation;//民族
	    private String address;//家庭地址
	    private String zip;//家庭邮编
	    private String degreeOfedu;//文化程度
	    private String sc_class;//放射源种类
	    private String sets;//套餐
		
		public String getExam_indicator() {
			return exam_indicator;
		}

		public void setExam_indicator(String exam_indicator) {
			this.exam_indicator = exam_indicator;
		}

		public String getApp_type() {
			return app_type;
		}

		public void setApp_type(String app_type) {
			this.app_type = app_type;
		}

		public String getSetentities() {
			return setentities;
		}

		public void setSetentities(String setentities) {
			this.setentities = setentities;
		}

		public String getZytjlb() {
			return zytjlb;
		}

		public void setZytjlb(String zytjlb) {
			this.zytjlb = zytjlb;
		}

		public String getOccusectorid() {
			return occusectorid;
		}

		public void setOccusectorid(String occusectorid) {
			this.occusectorid = occusectorid;
		}

		public String getOccutypeofworkid() {
			return occutypeofworkid;
		}

		public void setOccutypeofworkid(String occutypeofworkid) {
			this.occutypeofworkid = occutypeofworkid;
		}

		public String getOccusector() {
			return occusector;
		}

		public void setOccusector(String occusector) {
			this.occusector = occusector;
		}

		public String getOccutypeofwork() {
			return occutypeofwork;
		}

		public void setOccutypeofwork(String occutypeofwork) {
			this.occutypeofwork = occutypeofwork;
		}

	    
		public String getGroup_index() {
			return group_index;
		}

		public void setGroup_index(String group_index) {
			this.group_index = group_index;
		}

		public String getEmployeeID() {
			return employeeID;
		}

		public void setEmployeeID(String employeeID) {
			this.employeeID = employeeID;
		}

		public String getScustomer_type_id() {
			return scustomer_type_id;
		}

		public void setScustomer_type_id(String scustomer_type_id) {
			this.scustomer_type_id = scustomer_type_id;
		}

		public String getCompanybatch_id() {
			return companybatch_id;
		}

		public void setCompanybatch_id(String companybatch_id) {
			this.companybatch_id = companybatch_id;
		}

		public String getItementities() {
			return itementities;
		}

		public void setItementities(String itementities) {
			this.itementities = itementities;
		}
	    
		public String getExam_num() {
			return exam_num;
		}

		public void setExam_num(String exam_num) {
			this.exam_num = exam_num;
		}

		public String getAddress() {
			return address;
		}

		public void setAddress(String address) {
			this.address = address;
		}

		public String getChargingType() {
			return chargingType;
		}

		public void setChargingType(String chargingType) {
			this.chargingType = chargingType;
		}

		public long getCustomer_id() {
			return customer_id;
		}

		public void setCustomer_id(long customer_id) {
			this.customer_id = customer_id;
		}

		public long getCustomer_type_id() {
			return customer_type_id;
		}

		public void setCustomer_type_id(long customer_type_id) {
			this.customer_type_id = customer_type_id;
		}

		public String getNation() {
			return nation;
		}

		public void setNation(String nation) {
			this.nation = nation;
		}

		public String getStatus() {
			return status;
		}

		public void setStatus(String status) {
			this.status = status;
		}

		public String getExam_type() {
			return exam_type;
		}

		public void setExam_type(String exam_type) {
			this.exam_type = exam_type;
		}

		public String getTime1() {
			return time1;
		}

		public void setTime1(String time1) {
			this.time1 = time1;
		}

		public String getTime2() {
			return time2;
		}

		public void setTime2(String time2) {
			this.time2 = time2;
		}

		public String getHansidjdflag() {
			return hansidjdflag;
		}

		public void setHansidjdflag(String hansidjdflag) {
			this.hansidjdflag = hansidjdflag;
		}

		public String getSftype() {
			return sftype;
		}

		public void setSftype(String sftype) {
			this.sftype = sftype;
		}

		public String getPerson_shenfen() {
			return person_shenfen;
		}

		public void setPerson_shenfen(String person_shenfen) {
			this.person_shenfen = person_shenfen;
		}

		public String getMz() {
			return mz;
		}

		public void setMz(String mz) {
			this.mz = mz;
		}

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}

		public String getBatch_num() {
			return Batch_num;
		}

		public void setBatch_num(String batch_num) {
			Batch_num = batch_num;
		}

		public String getComname() {
			return comname;
		}

		public void setComname(String comname) {
			this.comname = comname;
		}

		public String getBatch_name() {
			return batch_name;
		}

		public void setBatch_name(String batch_name) {
			this.batch_name = batch_name;
		}

		public long getGroup_id() {
			return group_id;
		}

		public void setGroup_id(long group_id) {
			this.group_id = group_id;
		}

		public long getDept_id() {
			return dept_id;
		}

		public void setDept_id(long dept_id) {
			this.dept_id = dept_id;
		}

		public long getExam_id() {
			return exam_id;
		}

		public void setExam_id(long exam_id) {
			this.exam_id = exam_id;
		}

		public String getIds() {
			return ids;
		}

		public void setIds(String ids) {
			this.ids = ids;
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

		public long getBatch_id() {
			return batch_id;
		}

		public void setBatch_id(long batch_id) {
			this.batch_id = batch_id;
		}

		public long getContract_id() {
			return contract_id;
		}

		public void setContract_id(long contract_id) {
			this.contract_id = contract_id;
		}

		public String getArch_num() {
			return arch_num;
		}

		public void setArch_num(String arch_num) {
			this.arch_num = arch_num;
		}

		public String getId_num() {
			return id_num;
		}

		public void setId_num(String id_num) {
			this.id_num = id_num;
		}

		public String getSex() {
			return sex;
		}

		public void setSex(String sex) {
			this.sex = sex;
		}

		public String getBirthday() {
			return birthday;
		}

		public void setBirthday(String birthday) {
			this.birthday = birthday;
		}

		
		public String getCustname() {
			return custname;
		}

		public void setCustname(String custname) {
			this.custname = custname;
		}

		public long getAge() {
			return age;
		}

		public void setAge(long age) {
			this.age = age;
		}

		public String getIs_marriage() {
			return is_marriage;
		}

		public void setIs_marriage(String is_marriage) {
			this.is_marriage = is_marriage;
		}

		public String getPosition() {
			return position;
		}

		public void setPosition(String position) {
			this.position = position;
		}

		public String get_level() {
			return _level;
		}

		public void set_level(String _level) {
			this._level = _level;
		}

		public String getTel() {
			return tel;
		}

		public void setTel(String tel) {
			this.tel = tel;
		}

		public String getRemark() {
			return remark;
		}

		public void setRemark(String remark) {
			this.remark = remark;
		}

		public String getCustomer_type() {
			return customer_type;
		}

		public void setCustomer_type(String customer_type) {
			this.customer_type = customer_type;
		}

		public String getOthers() {
			return others;
		}

		public void setOthers(String others) {
			this.others = others;
		}

		public long getFlags() {
			return flags;
		}

		public void setFlags(long flags) {
			this.flags = flags;
		}

		public String getNotices() {
			return notices;
		}

		public void setNotices(String notices) {
			this.notices = notices;
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

		public String getBorn_address() {
			return born_address;
		}

		public void setBorn_address(String born_address) {
			this.born_address = born_address;
		}

		public String getZip() {
			return zip;
		}

		public void setZip(String zip) {
			this.zip = zip;
		}

		public String getDegreeOfedu() {
			return degreeOfedu;
		}

		public void setDegreeOfedu(String degreeOfedu) {
			this.degreeOfedu = degreeOfedu;
		}

		public String getSc_class() {
			return sc_class;
		}

		public void setSc_class(String sc_class) {
			this.sc_class = sc_class;
		}

		public String getSets() {
			return sets;
		}

		public void setSets(String sets) {
			this.sets = sets;
		}	
	    
	}