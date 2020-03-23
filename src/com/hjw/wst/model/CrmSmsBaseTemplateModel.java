package com.hjw.wst.model;

import com.hjw.wst.domain.CrmSmsBaseTemplate;


public class CrmSmsBaseTemplateModel   implements java.io.Serializable{
	
	/**   
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)   
	 */ 
	private static final long serialVersionUID = -6316887964764639885L;
	private String id;
	private String	   sms_category;
	private String	   sms_name;
	private String	   sms_note;
	private long	   creater;
	private String	   create_time;
	private long	   updater;
	private String	   update_time;
	private String sms_state;
	//==============短信记录===========
	private String template_id;
    private String arch_num;
    private String 	sms_phone;
    private String sms_date;
    private long   send_user;
    private String	sms_status;
    private Number	sms_amount;
    private int   sms_type;
    private long user_id;
    private String sms_time;
    private String user_type;
    private String sms_batch;
    
    private String name;
    private String exam_num;
    private String phone;
    private String sms_date_j;
    
    private String chkItem;
    
    private String time1;
    private String time2;
    private String custname;
    private String li;
    //=======================体检信息===============

	private long company_id;

	private long batch_id;

	private long contract_id;		


	private String id_num="";

	private String sex="";

	private String birthday="";


	private long age;

	private String is_marriage="";

	private String position="";

	private String _level="";

	private String tel="";

	private String remark="";
	
	private String introducer = "";

	private String customer_type="";

	private String others="";

	private long flags;

	private String notices="";


    
    private String ids="";;	  
    
    private long exam_id;
    
    private long dept_id;
    
    private long group_id;	
    
    private long customer_type_id;
    
    private String comname="";
    
    private String batch_name="";
    
    private String Batch_num="";
    
    private String email="";	    
    
    private String mz="";//民族    
    
    private String person_shenfen="";//身份类别
    
    private String sftype="";//收费类型
    
    private String hansidjdflag="";	
    
    
    
    private String exam_type=""; 	   
    
    private String status="";
    
    private String nation="";	
    
    private long customer_id;
    
    private String chargingType="";
    
    private String address="";
    
    
    private String itementities="";    
    
    private String setentities="";  
    
    private String companybatch_id="";  
    
    
    private String company="";//单位中文
    
    private String past_medical_history="";//既往史
    
    private String picture_Path="";
    
    private String is_after_pay="";//是否后收费
    
    private double amount;
    
    private double discount;
    
    private double item_amount;
    
    private long dingweitype;
    
    private String register_date="";
    
    private String join_date="";
    
    private String customerType=""; 
    
    private String setimes="";    
    
    private String employeeID="";
    
    
    private String mc_no="";//就诊卡号

	private String visit_date="";//就诊日期

	private String visit_no="";//就诊号

	private String clinic_no="";//门诊号	
	
	private String exam_indicator="";//团体付费状态 T团体结算 G 自费结算		
	
	private long contralcustflag;//操作是否新增或者修改，团检使用	
	
	private String bar_code_url;//BAR_CODE_URL;
	
	private String djd_code_url;//DJD_CODE_URL;	
	
	private String report_url;//体检报告保存地址；
	
	private String barcode_print_type;//调用打印程序类型
	
    private String time3="";
    
    private String time4="";  
    
    private String time5="";  
	
	private String receive_type;
	
	private String receive_name;
	
	private String receive_phone;
	
	private String receive_address;
	
	private String receive_postcode;
	
	private String receive_remark;
	
	private long oldexam_id;
	private String reexam_type="N";//复检标志	
	private String djdstatuss="";//导检单打印标志
	private String occusectorid="";//行业	
	
	private long read_status;//读取状态
	private String bunk;//床位
	private String allocationdate;//分配时间
	
	public String user_name;
	public String txtpswd;
	
	public int ren_type;
	private String budget_amount;//预算金额
	private String idnum_notnull;
	private String orderid="";	
	
	private String yuyue_date1;
	private String yuyue_date2;
	
	private String copy_status;//复制项目
	
	private int idtype;
	
	private String idtypename;
	
	private String visit_no_j;
	
	private String read_jiuzhenka;//就诊卡读取设备
	
	private String Read_jiuzhenka_ocx;
	
	private String bodao_checkebox;//报到  是否默认勾选  条码-档案号-导简单 配置
	
	private long marriage_age;//婚龄
	
	private String disease_id;
	private CrmSmsBaseTemplate m;
	private String sex_type;
	private String is_print;
	private String max_age;
	private String min_age;
	
	
	public String getMax_age() {
		return max_age;
	}
	public void setMax_age(String max_age) {
		this.max_age = max_age;
	}
	public String getMin_age() {
		return min_age;
	}
	public void setMin_age(String min_age) {
		this.min_age = min_age;
	}
	public String getIs_print() {
		return is_print;
	}
	public void setIs_print(String is_print) {
		this.is_print = is_print;
	}
	public String getSex_type() {
		return sex_type;
	}
	public void setSex_type(String sex_type) {
		this.sex_type = sex_type;
	}
	public String getTime5() {
		return time5;
	}
	public void setTime5(String time5) {
		this.time5 = time5;
	}
	public String getLi() {
		return li;
	}
	public void setLi(String li) {
		this.li = li;
	}
	public String getCustname() {
		return custname;
	}
	public void setCustname(String custname) {
		this.custname = custname;
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
	public String getChkItem() {
		return chkItem;
	}
	public void setChkItem(String chkItem) {
		this.chkItem = chkItem;
	}
	public CrmSmsBaseTemplate getM() {
		return m;
	}
	public void setM(CrmSmsBaseTemplate m) {
		this.m = m;
	}

	public String getTemplate_id() {
		return template_id;
	}
	public void setTemplate_id(String template_id) {
		this.template_id = template_id;
	}
	public String getArch_num() {
		return arch_num;
	}
	public void setArch_num(String arch_num) {
		this.arch_num = arch_num;
	}
	public String getSms_phone() {
		return sms_phone;
	}
	public void setSms_phone(String sms_phone) {
		this.sms_phone = sms_phone;
	}
	public String getSms_date() {
		return sms_date;
	}
	public void setSms_date(String sms_date) {
		this.sms_date = sms_date;
	}
	public long getSend_user() {
		return send_user;
	}
	public void setSend_user(long send_user) {
		this.send_user = send_user;
	}
	public String getSms_status() {
		return sms_status;
	}
	public void setSms_status(String sms_status) {
		this.sms_status = sms_status;
	}
	public Number getSms_amount() {
		return sms_amount;
	}
	public void setSms_amount(Number sms_amount) {
		this.sms_amount = sms_amount;
	}
	public int getSms_type() {
		return sms_type;
	}
	public void setSms_type(int sms_type) {
		this.sms_type = sms_type;
	}
	public long getUser_id() {
		return user_id;
	}
	public void setUser_id(long user_id) {
		this.user_id = user_id;
	}
	public String getSms_time() {
		return sms_time;
	}
	public void setSms_time(String sms_time) {
		this.sms_time = sms_time;
	}
	public String getUser_type() {
		return user_type;
	}
	public void setUser_type(String user_type) {
		this.user_type = user_type;
	}
	public String getSms_batch() {
		return sms_batch;
	}
	public void setSms_batch(String sms_batch) {
		this.sms_batch = sms_batch;
	}
	public String getSms_state() {
		return sms_state;
	}
	public void setSms_state(String sms_state) {
		this.sms_state = sms_state;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSms_category() {
		return sms_category;
	}
	public void setSms_category(String sms_category) {
		this.sms_category = sms_category;
	}
	public String getSms_name() {
		return sms_name;
	}
	public void setSms_name(String sms_name) {
		this.sms_name = sms_name;
	}
	public String getSms_note() {
		return sms_note;
	}
	public void setSms_note(String sms_note) {
		this.sms_note = sms_note;
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getExam_num() {
		return exam_num;
	}
	public void setExam_num(String exam_num) {
		this.exam_num = exam_num;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getSms_date_j() {
		return sms_date_j;
	}
	public void setSms_date_j(String sms_date_j) {
		this.sms_date_j = sms_date_j;
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
	public String getIntroducer() {
		return introducer;
	}
	public void setIntroducer(String introducer) {
		this.introducer = introducer;
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
	public String getIds() {
		return ids;
	}
	public void setIds(String ids) {
		this.ids = ids;
	}
	public long getExam_id() {
		return exam_id;
	}
	public void setExam_id(long exam_id) {
		this.exam_id = exam_id;
	}
	public long getDept_id() {
		return dept_id;
	}
	public void setDept_id(long dept_id) {
		this.dept_id = dept_id;
	}
	public long getGroup_id() {
		return group_id;
	}
	public void setGroup_id(long group_id) {
		this.group_id = group_id;
	}
	public long getCustomer_type_id() {
		return customer_type_id;
	}
	public void setCustomer_type_id(long customer_type_id) {
		this.customer_type_id = customer_type_id;
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
	public String getBatch_num() {
		return Batch_num;
	}
	public void setBatch_num(String batch_num) {
		Batch_num = batch_num;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getMz() {
		return mz;
	}
	public void setMz(String mz) {
		this.mz = mz;
	}
	public String getPerson_shenfen() {
		return person_shenfen;
	}
	public void setPerson_shenfen(String person_shenfen) {
		this.person_shenfen = person_shenfen;
	}
	public String getSftype() {
		return sftype;
	}
	public void setSftype(String sftype) {
		this.sftype = sftype;
	}
	public String getHansidjdflag() {
		return hansidjdflag;
	}
	public void setHansidjdflag(String hansidjdflag) {
		this.hansidjdflag = hansidjdflag;
	}
	public String getExam_type() {
		return exam_type;
	}
	public void setExam_type(String exam_type) {
		this.exam_type = exam_type;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getNation() {
		return nation;
	}
	public void setNation(String nation) {
		this.nation = nation;
	}
	public long getCustomer_id() {
		return customer_id;
	}
	public void setCustomer_id(long customer_id) {
		this.customer_id = customer_id;
	}
	public String getChargingType() {
		return chargingType;
	}
	public void setChargingType(String chargingType) {
		this.chargingType = chargingType;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getItementities() {
		return itementities;
	}
	public void setItementities(String itementities) {
		this.itementities = itementities;
	}
	public String getSetentities() {
		return setentities;
	}
	public void setSetentities(String setentities) {
		this.setentities = setentities;
	}
	public String getCompanybatch_id() {
		return companybatch_id;
	}
	public void setCompanybatch_id(String companybatch_id) {
		this.companybatch_id = companybatch_id;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getPast_medical_history() {
		return past_medical_history;
	}
	public void setPast_medical_history(String past_medical_history) {
		this.past_medical_history = past_medical_history;
	}
	public String getPicture_Path() {
		return picture_Path;
	}
	public void setPicture_Path(String picture_Path) {
		this.picture_Path = picture_Path;
	}
	public String getIs_after_pay() {
		return is_after_pay;
	}
	public void setIs_after_pay(String is_after_pay) {
		this.is_after_pay = is_after_pay;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public double getDiscount() {
		return discount;
	}
	public void setDiscount(double discount) {
		this.discount = discount;
	}
	public double getItem_amount() {
		return item_amount;
	}
	public void setItem_amount(double item_amount) {
		this.item_amount = item_amount;
	}
	public long getDingweitype() {
		return dingweitype;
	}
	public void setDingweitype(long dingweitype) {
		this.dingweitype = dingweitype;
	}
	public String getRegister_date() {
		return register_date;
	}
	public void setRegister_date(String register_date) {
		this.register_date = register_date;
	}
	public String getJoin_date() {
		return join_date;
	}
	public void setJoin_date(String join_date) {
		this.join_date = join_date;
	}
	public String getCustomerType() {
		return customerType;
	}
	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}
	public String getSetimes() {
		return setimes;
	}
	public void setSetimes(String setimes) {
		this.setimes = setimes;
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
	public String getClinic_no() {
		return clinic_no;
	}
	public void setClinic_no(String clinic_no) {
		this.clinic_no = clinic_no;
	}
	public String getExam_indicator() {
		return exam_indicator;
	}
	public void setExam_indicator(String exam_indicator) {
		this.exam_indicator = exam_indicator;
	}
	public long getContralcustflag() {
		return contralcustflag;
	}
	public void setContralcustflag(long contralcustflag) {
		this.contralcustflag = contralcustflag;
	}
	public String getBar_code_url() {
		return bar_code_url;
	}
	public void setBar_code_url(String bar_code_url) {
		this.bar_code_url = bar_code_url;
	}
	public String getDjd_code_url() {
		return djd_code_url;
	}
	public void setDjd_code_url(String djd_code_url) {
		this.djd_code_url = djd_code_url;
	}
	public String getReport_url() {
		return report_url;
	}
	public void setReport_url(String report_url) {
		this.report_url = report_url;
	}
	public String getBarcode_print_type() {
		return barcode_print_type;
	}
	public void setBarcode_print_type(String barcode_print_type) {
		this.barcode_print_type = barcode_print_type;
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
	public String getReceive_type() {
		return receive_type;
	}
	public void setReceive_type(String receive_type) {
		this.receive_type = receive_type;
	}
	public String getReceive_name() {
		return receive_name;
	}
	public void setReceive_name(String receive_name) {
		this.receive_name = receive_name;
	}
	public String getReceive_phone() {
		return receive_phone;
	}
	public void setReceive_phone(String receive_phone) {
		this.receive_phone = receive_phone;
	}
	public String getReceive_address() {
		return receive_address;
	}
	public void setReceive_address(String receive_address) {
		this.receive_address = receive_address;
	}
	public String getReceive_postcode() {
		return receive_postcode;
	}
	public void setReceive_postcode(String receive_postcode) {
		this.receive_postcode = receive_postcode;
	}
	public String getReceive_remark() {
		return receive_remark;
	}
	public void setReceive_remark(String receive_remark) {
		this.receive_remark = receive_remark;
	}
	public long getOldexam_id() {
		return oldexam_id;
	}
	public void setOldexam_id(long oldexam_id) {
		this.oldexam_id = oldexam_id;
	}
	public String getReexam_type() {
		return reexam_type;
	}
	public void setReexam_type(String reexam_type) {
		this.reexam_type = reexam_type;
	}
	public String getDjdstatuss() {
		return djdstatuss;
	}
	public void setDjdstatuss(String djdstatuss) {
		this.djdstatuss = djdstatuss;
	}
	public String getOccusectorid() {
		return occusectorid;
	}
	public void setOccusectorid(String occusectorid) {
		this.occusectorid = occusectorid;
	}
	public long getRead_status() {
		return read_status;
	}
	public void setRead_status(long read_status) {
		this.read_status = read_status;
	}
	public String getBunk() {
		return bunk;
	}
	public void setBunk(String bunk) {
		this.bunk = bunk;
	}
	public String getAllocationdate() {
		return allocationdate;
	}
	public void setAllocationdate(String allocationdate) {
		this.allocationdate = allocationdate;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public String getTxtpswd() {
		return txtpswd;
	}
	public void setTxtpswd(String txtpswd) {
		this.txtpswd = txtpswd;
	}
	public int getRen_type() {
		return ren_type;
	}
	public void setRen_type(int ren_type) {
		this.ren_type = ren_type;
	}
	public String getBudget_amount() {
		return budget_amount;
	}
	public void setBudget_amount(String budget_amount) {
		this.budget_amount = budget_amount;
	}
	public String getIdnum_notnull() {
		return idnum_notnull;
	}
	public void setIdnum_notnull(String idnum_notnull) {
		this.idnum_notnull = idnum_notnull;
	}
	public String getOrderid() {
		return orderid;
	}
	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}
	public String getYuyue_date1() {
		return yuyue_date1;
	}
	public void setYuyue_date1(String yuyue_date1) {
		this.yuyue_date1 = yuyue_date1;
	}
	public String getYuyue_date2() {
		return yuyue_date2;
	}
	public void setYuyue_date2(String yuyue_date2) {
		this.yuyue_date2 = yuyue_date2;
	}
	public String getCopy_status() {
		return copy_status;
	}
	public void setCopy_status(String copy_status) {
		this.copy_status = copy_status;
	}
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
	public String getVisit_no_j() {
		return visit_no_j;
	}
	public void setVisit_no_j(String visit_no_j) {
		this.visit_no_j = visit_no_j;
	}
	public String getRead_jiuzhenka() {
		return read_jiuzhenka;
	}
	public void setRead_jiuzhenka(String read_jiuzhenka) {
		this.read_jiuzhenka = read_jiuzhenka;
	}
	public String getRead_jiuzhenka_ocx() {
		return Read_jiuzhenka_ocx;
	}
	public void setRead_jiuzhenka_ocx(String read_jiuzhenka_ocx) {
		Read_jiuzhenka_ocx = read_jiuzhenka_ocx;
	}
	public String getBodao_checkebox() {
		return bodao_checkebox;
	}
	public void setBodao_checkebox(String bodao_checkebox) {
		this.bodao_checkebox = bodao_checkebox;
	}
	public long getMarriage_age() {
		return marriage_age;
	}
	public void setMarriage_age(long marriage_age) {
		this.marriage_age = marriage_age;
	}
	public String getDisease_id() {
		return disease_id;
	}
	public void setDisease_id(String disease_id) {
		this.disease_id = disease_id;
	}
	
	
	
	
}
