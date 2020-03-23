package com.hjw.wst.DTO;

public class ImpCustomerInfoDTO implements java.io.Serializable {
	 private static final long serialVersionUID = -97502163798576023L;

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
	    
	    private String sflags="";
	    
	    private long customerid; 
	    
	    private String exam_type="";//体检类型    
	    
	    private long customer_type_id;

		private String patient_id="";

		private String employeeID="";

		private String mc_no="";//就诊卡号

		private String visit_date="";//就诊日期

		private String visit_no="";//就诊号		
		
		private String introducer="";//介绍人	
		
		private String sets="";//套餐名称，以小写的分号隔开

		private String zywhys="";//危害因素类型，以小写的分号隔开，和套餐名称对应

		private String joinDatetime="";//进厂日期

		private int employeeage;//总工龄

		private int damage; //接害工龄

		private String occusector="";//行业

		private String occutypeofwork="";//工种		
        private String idtypename;
		
		private int idtype;

		private int info_type;//人员信息类型
		private String born_address;//出生地
	    private String nation;//民族
	    private String address;//家庭地址
	    private String zip;//家庭邮编
	    private String degreeOfedu;//文化程度
	    private String sc_class;//放射源种类
	    private String exam_indicator;//职业病付费类别 exam_indicator：个费，团费
	    private String exam_class;//体检类型：对应数据字典
	    
	    private int employeemonth;//工龄 月
	    private int dammonth;//接害工龄 月

		public int getIdtype() {
			return idtype;
		}

		public void setIdtype(int idtype) {
			this.idtype = idtype;
		}

		public String getIdtypename() {
			return idtypename;
		}

		public void setIdtypename(String idtypename) {
			this.idtypename = idtypename;
		}

		public String getSets() {
			return sets;
		}

		public void setSets(String sets) {
			this.sets = sets;
		}

		public String getZywhys() {
			return zywhys;
		}

		public void setZywhys(String zywhys) {
			this.zywhys = zywhys;
		}

		public String getJoinDatetime() {
			return joinDatetime;
		}

		public void setJoinDatetime(String joinDatetime) {
			this.joinDatetime = joinDatetime;
		}

		public int getEmployeeage() {
			return employeeage;
		}

		public void setEmployeeage(int employeeage) {
			this.employeeage = employeeage;
		}

		public int getDamage() {
			return damage;
		}

		public void setDamage(int damage) {
			this.damage = damage;
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

		public String getPatient_id() {
			return patient_id;
		}

		public void setPatient_id(String patient_id) {
			this.patient_id = patient_id;
		}

		public String getEmployeeID() {
			return employeeID;
		}

		public void setEmployeeID(String employeeID) {
			this.employeeID = employeeID;
		}

		public String getMc_no() {
			return mc_no;
		}

		public void setMc_no(String mc_no) {
			this.mc_no = mc_no;
		}

		public String getVisit_date() {
			return visit_date;
		}

		public void setVisit_date(String visit_date) {
			this.visit_date = visit_date;
		}

		public String getVisit_no() {
			return visit_no;
		}

		public void setVisit_no(String visit_no) {
			this.visit_no = visit_no;
		}

		public long getCustomer_type_id() {
			return customer_type_id;
		}

		public void setCustomer_type_id(long customer_type_id) {
			this.customer_type_id = customer_type_id;
		}

		public String getExam_type() {
			return exam_type;
		}

		public void setExam_type(String exam_type) {
			this.exam_type = exam_type;
		}

		public long getCustomerid() {
			return customerid;
		}

		public void setCustomerid(long customerid) {
			this.customerid = customerid;
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
			if(flags==0){
				this.setSflags("未导入");
			}else if(flags==1){
				this.setSflags("导入错误");
			} else if(flags==9){
				this.setSflags("导入成功");
			}else {
				this.setSflags("未知");
			}
		}	

		public String getSflags() {
			return sflags;
		}

		public void setSflags(String sflags) {
			this.sflags = sflags;
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

		public String getIntroducer() {
			return introducer;
		}

		public void setIntroducer(String introducer) {
			this.introducer = introducer;
		}

		public int getInfo_type() {
			return info_type;
		}

		public void setInfo_type(int info_type) {
			this.info_type = info_type;
		}

		public String getBorn_address() {
			return born_address;
		}

		public void setBorn_address(String born_address) {
			this.born_address = born_address;
		}

		public String getNation() {
			return nation;
		}

		public void setNation(String nation) {
			this.nation = nation;
		}

		public String getAddress() {
			return address;
		}

		public void setAddress(String address) {
			this.address = address;
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

		public String getExam_indicator() {
			return exam_indicator;
		}

		public void setExam_indicator(String exam_indicator) {
			this.exam_indicator = exam_indicator;
		}

		public String getExam_class() {
			return exam_class;
		}

		public void setExam_class(String exam_class) {
			this.exam_class = exam_class;
		}

		public int getEmployeemonth() {
			return employeemonth;
		}

		public void setEmployeemonth(int employeemonth) {
			this.employeemonth = employeemonth;
		}

		public int getDammonth() {
			return dammonth;
		}

		public void setDammonth(int dammonth) {
			this.dammonth = dammonth;
		}

	}