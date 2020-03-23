package com.hjw.wst.model;

public class ImpCustomerInfoModel implements java.io.Serializable {
	private static final long serialVersionUID = -97502163798576023L;

	private String group_index = "";
	
	private String set_num = "";
	
	private String item_code = "";

	private long id;

	private long company_id;

	private long batch_id;

	private long contract_id;

	private String arch_num = "";

	private String id_num = "";

	private String sex = "";

	private String birthday = "";

	private String custname = "";

	private long age;

	private String is_marriage = "";

	private String position = "";

	private String _level = "";

	private String tel = "";

	private String remark = "";

	private String customer_type = "";

	private String others = "";

	private long flags;

	private String notices = "";

	private long creater;

	private String create_time = "";

	private String ids = "";

	private long exam_id;

	private long dept_id;

	private long group_id;

	private long customer_type_id;

	private String scustomer_type_id = "";

	private String comname = "";

	private String batch_name = "";

	private String Batch_num = "";

	private String email = "";

	private String mz = "";// 民族

	private String person_shenfen = "";// 身份类别

	private String sftype = "";// 收费类型

	private String hansidjdflag = "";

	private String time1 = "";

	private String time2 = "";

	private String exam_type = "";

	private String status = "";

	private String nation = "";

	private long customer_id;

	private String chargingType = "";

	private String address = "";

	private String exam_num = "";

	private String itementities = "";

	private String companybatch_id = "";

	private String employeeID = "";

	private String sets = "";// 套餐名称，以小写的分号隔开

	private String zywhys = "";// 危害因素类型，以小写的分号隔开，和套餐名称对应

	private String joinDatetime = "";// 进厂日期

	private int employeeage;// 总工龄

	private int damage; // 接害工龄

	private String occusector = "";// 行业

	private String occutypeofwork = "";// 工种

	private String group_name;

	private String exam_indicator = "T";

	private long freeze;// 0表示不冻结，1表示冻结

	private String chkItem = "";

	private String time3 = "";

	private String time4 = "";

	private long set_id;

	private String exam_status = "";

	private String levels = "";

	private long batchid;	
	
	
	private String idnum_notnull;
	private String introducer;
	private String visit_no;
	private String idtypename;
	
	private String startDate;
	
	private String endDate;
	
	private int is_upload;
	
	private int is_report_upload;
	
	private String degreeOfedu="";
	
	private int political_status;
	
	private String political_statuss;

    private String join_date="";
    
    private String djdstatuss="";//导检单打印标志
    
    private String barcode_print_type;//调用打印程序类型
    
    private int ischecktype = 0;// 是否有审核的资源权限
    private int isaccounttype=0;//是否有锁定的资源权限
    private int isunaccounttype=0;//是否有解除锁定的资源权限

	private String jiuzhenka;
	private int min_age;
	private int max_age;
	
	private String dept_ids;
	private int dep_id;
	private int dep_c;
	private String item_name;
	private String lc_remark;
	
	private int isovertype = 0;// 是否有结帐的资源权限
    private int isSMS=0;//是否发送短信通知
    private int ttog=0;
    private int gtot=0;
    private int is_show_discount;
    private int grouptot=0;
    private String exam_nums;
    private String exam_class;//体检类型：对应数据字典
    
	public String getExam_nums() {
		return exam_nums;
	}

	public void setExam_nums(String exam_nums) {
		this.exam_nums = exam_nums;
	}

	public int getIs_show_discount() {
		return is_show_discount;
	}

	public void setIs_show_discount(int is_show_discount) {
		this.is_show_discount = is_show_discount;
	}

	public int getIsovertype() {
		return isovertype;
	}

	public void setIsovertype(int isovertype) {
		this.isovertype = isovertype;
	}

	public int getIsSMS() {
		return isSMS;
	}

	public void setIsSMS(int isSMS) {
		this.isSMS = isSMS;
	}

	public int getTtog() {
		return ttog;
	}

	public void setTtog(int ttog) {
		this.ttog = ttog;
	}

	public int getGtot() {
		return gtot;
	}

	public void setGtot(int gtot) {
		this.gtot = gtot;
	}

	public int getIs_report_upload() {
		return is_report_upload;
	}

	public void setIs_report_upload(int is_report_upload) {
		this.is_report_upload = is_report_upload;
	}
	
	
	public int getIs_upload() {
		return is_upload;
	}

	public void setIs_upload(int is_upload) {
		this.is_upload = is_upload;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

        
		public String getJiuzhenka() {
			return jiuzhenka;
		}

		public void setJiuzhenka(String jiuzhenka) {
			this.jiuzhenka = jiuzhenka;
		}

		public String getIdtypename() {
			return idtypename;
		}

	public void setIdtypename(String idtypename) {
		this.idtypename = idtypename;
	}

	public String getIdnum_notnull() {
		return idnum_notnull;
	}

	public void setIdnum_notnull(String idnum_notnull) {
		this.idnum_notnull = idnum_notnull;
	}

     private String zyb_barcode_print_type;
		
	public String getZyb_barcode_print_type() {
		return zyb_barcode_print_type;
	}

	public void setZyb_barcode_print_type(String zyb_barcode_print_type) {
		this.zyb_barcode_print_type = zyb_barcode_print_type;
	}

		public String getBarcode_print_type() {
			return barcode_print_type;
		}

		public void setBarcode_print_type(String barcode_print_type) {
			this.barcode_print_type = barcode_print_type;
		}

		public String getDjdstatuss() {
			return djdstatuss;
		}

		public void setDjdstatuss(String djdstatuss) {
			this.djdstatuss = djdstatuss;
		}

	public long getFreeze() {
		return freeze;
	}

	public void setFreeze(long freeze) {
		this.freeze = freeze;
	}

	public String getChkItem() {
		return chkItem;
	}

	public void setChkItem(String chkItem) {
		this.chkItem = chkItem;
	}

	public String getTime3() {
		return time3;
	}

	public void setTime3(String time3) {
		this.time3 = time3;
	}

	public String getTime4() {
		return time4;
	}

	public void setTime4(String time4) {
		this.time4 = time4;
	}

	public long getSet_id() {
		return set_id;
	}

	public void setSet_id(long set_id) {
		this.set_id = set_id;
	}

	public String getExam_status() {
		return exam_status;
	}

	public void setExam_status(String exam_status) {
		this.exam_status = exam_status;
	}

	public String getLevels() {
		return levels;
	}

	public void setLevels(String levels) {
		this.levels = levels;
	}

	public long getBatchid() {
		return batchid;
	}

	public void setBatchid(long batchid) {
		this.batchid = batchid;
	}

	public String getExam_indicator() {
		return exam_indicator;
	}

	public void setExam_indicator(String exam_indicator) {
		this.exam_indicator = exam_indicator;
	}

	public String getGroup_name() {
		return group_name;
	}

	public void setGroup_name(String group_name) {
		this.group_name = group_name;
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

	public String getIntroducer() {
		return introducer;
	}

	public void setIntroducer(String introducer) {
		this.introducer = introducer;
	}

	public String getVisit_no() {
		return visit_no;
	}

	public void setVisit_no(String visit_no) {
		this.visit_no = visit_no;
	}

	public String getDegreeOfedu() {
		return degreeOfedu;
	}

	public int getPolitical_status() {
		return political_status;
	}

	public String getPolitical_statuss() {
		return political_statuss;
	}

	public String getJoin_date() {
		return join_date;
	}

	public int getIschecktype() {
		return ischecktype;
	}

	public int getIsaccounttype() {
		return isaccounttype;
	}

	public int getMin_age() {
		return min_age;
	}

	public int getMax_age() {
		return max_age;
	}

	public String getDept_ids() {
		return dept_ids;
	}

	public int getDep_id() {
		return dep_id;
	}

	public int getDep_c() {
		return dep_c;
	}

	public String getItem_name() {
		return item_name;
	}

	public String getLc_remark() {
		return lc_remark;
	}

	public void setDegreeOfedu(String degreeOfedu) {
		this.degreeOfedu = degreeOfedu;
	}

	public void setPolitical_status(int political_status) {
		this.political_status = political_status;
	}

	public void setPolitical_statuss(String political_statuss) {
		this.political_statuss = political_statuss;
	}

	public void setJoin_date(String join_date) {
		this.join_date = join_date;
	}

	public void setIschecktype(int ischecktype) {
		this.ischecktype = ischecktype;
	}

	public void setIsaccounttype(int isaccounttype) {
		this.isaccounttype = isaccounttype;
	}

	public void setMin_age(int min_age) {
		this.min_age = min_age;
	}

	public void setMax_age(int max_age) {
		this.max_age = max_age;
	}

	public void setDept_ids(String dept_ids) {
		this.dept_ids = dept_ids;
	}

	public void setDep_id(int dep_id) {
		this.dep_id = dep_id;
	}

	public void setDep_c(int dep_c) {
		this.dep_c = dep_c;
	}

	public void setItem_name(String item_name) {
		this.item_name = item_name;
	}

	public void setLc_remark(String lc_remark) {
		this.lc_remark = lc_remark;
	}

	public String getSet_num() {
		return set_num;
	}

	public String getItem_code() {
		return item_code;
	}

	public void setSet_num(String set_num) {
		this.set_num = set_num;
	}

	public void setItem_code(String item_code) {
		this.item_code = item_code;
	}

	public int getGrouptot() {
		return grouptot;
	}

	public void setGrouptot(int grouptot) {
		this.grouptot = grouptot;
	}

	public String getExam_class() {
		return exam_class;
	}

	public void setExam_class(String exam_class) {
		this.exam_class = exam_class;
	}

	public int getIsunaccounttype() {
		return isunaccounttype;
	}

	public void setIsunaccounttype(int isunaccounttype) {
		this.isunaccounttype = isunaccounttype;
	}
}