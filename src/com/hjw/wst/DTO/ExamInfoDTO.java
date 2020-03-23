package com.hjw.wst.DTO;

import java.util.Date;

public class ExamInfoDTO implements java.io.Serializable {
	 private static final long serialVersionUID = -97502163798576023L;

		private long id;

        private long group_id;
        
		private long customer_id;
		
		private String exam_num="";
		
		private String status="";
		
		private String register_date="";
		
		private String join_date="";
		
		private String final_date="";
		
		private String final_doctor="";
		
		private String exam_type="";
		
		private String exam_type_y="";
		
		private String customer_type="";
		
		private String is_sampled_directly="";
		
		private String is_adjusted="";
		
		private String center_num="";
		
		private String getReportWay="";  //报告领取方式 1邮寄 2自取 3 电子报告
		
		private String reportAddress="";
		
		private String chargingType="";
		
		private String customerType="";
		
		private String group_index="";
		
		private String is_Active="";
		
		private long creater;
		
		private String create_time="";
		
		private long updater;
		
		private String update_time="";
		
		private String is_guide_back="";
		
		private String company_check_status="";
		
		private long customer_type_id;
		
		private String is_marriage="";
		
		private long age;
		
		private String address="";
		
		private String zip="";
		
		private String email="";
		
		private String phone="";
		
		private String company="";
		
		private String position="";
		
		private String _level="";
		
		private String picture_path="";
		
		private String is_after_pay="";
		
		private String past_medical_history="";
		
		private String remarke="";
		
		private String introducer="";
		
		private String counter_check="N";
		
		private String guide_nurse="";
		
		private String appointment="";
		
		private String data_source="";
		
		private String others="";
		
		private String order_id="";
		
		private String user_name="";
		
		private String sex="";		
		
		private String arch_num="";	
		
		private String id_num="";		
		
		private String birthday="";
		
		private String set_name="";
		
		private String is_report_print="";
		
		private String is_report_print_y="";
		
		private String is_report_tidy;
		private String is_report_tidy_y;
		
		private String print_doctor;
		private String print_time;
		
		private String receive_type="";
		
		private String receive_type_y="";
		
		private long company_id;
		
		private long batch_id;		
		
		private String statuss="";		
		
		private String exam_times="";
		
		private String app_doctor="";

		private String patient_id="";
		
		private String nation="";

		private String employeeID="";

		private String mc_no="";//就诊卡号

		private String visit_date="";//就诊日期

		private String visit_no="";//就诊号
		
		private String clinic_no="";//门诊号	
		
		private String exam_indicator="";//团体付费状态 T团体结算 G 自费结算	

		private String apptype="1";//1表示普通体检 2表示职业病体检

		private String is_need_guide;//是否打印导引单

		private String is_need_barcode;//是否打印条码		
		
		private String flag="0";	
		
        private long freeze;//0表示不冻结，1表示冻结		
        
        private String report_tidy_user;
        
        private String old_arch_num;
        private String up_arch_num_time;
        private long up_arch_num_person;
        private String u_name;
        private String c_name;
        private int ren_type;
        private String isPrePay;
        private String isnotPay;
        private String pay_status;
        private double totalamt;
        private Double budget_amount;//预算金额
        
        private String degreeOfedu;//文化程度
        
        private String data_code_children;
        
        private int zyb_set_source;
        
        //==================================就诊卡写卡============================
        private long aOutpatientNo;//卡号
        private String aPatName;//姓名
        private long aSex;//性别
        private Date aBirthday;//出生日期
        private String aAddres;//地址
        private String aIDCard;//身份证号
        private String aTel;//电话
        private long aOperator;//登记人
        private long aPrice;	//价格0
        
        private int comon_report_type;
        
        private String old_exam_num="";
		private long marriage_age;
		private int wuxuzongjian;
		
		private long join_operator;
		
		private int weijian;//获取体检信息综合查询页面未检查
        private int yijian;
        private String start_date;
        private String end_date;
        
        private double personal_pay;
        private double team_pay;
        
        private String fs_creater;//复审医生
        private String fs_date;//复审时间
        private String DJD_path;//导检单图片路径
        private String DJD_image_creater;//导检单图片上传者
        
        private String flow_name;//流程进度
        private String senddate;//流程进度时间
        private String  is_guide_backs;//是否回收导检单
        private String check_name;
        private String check_time;
        private String Report_Print_UserName;
        private String Report_Print_Date;
        private int print_count ;//记录打印次数
        private String ziqu_report_time;
        private String exam_center_num;
        private String receive_date;
        
		public String getReceive_date() {
			return receive_date;
		}

		public void setReceive_date(String receive_date) {
			this.receive_date = receive_date;
		}

		public long getJoin_operator() {
			return join_operator;
		}

		public void setJoin_operator(long join_operator) {
			this.join_operator = join_operator;
		}
		
	    public String getOld_exam_num() {
			return old_exam_num;
		}

		public void setOld_exam_num(String old_exam_num) {
			this.old_exam_num = old_exam_num;
		}

		public long getMarriage_age() {
			return marriage_age;
		}

		public void setMarriage_age(long marriage_age) {
			this.marriage_age = marriage_age;
		}

		public int getWuxuzongjian() {
			return wuxuzongjian;
		}

		public void setWuxuzongjian(int wuxuzongjian) {
			this.wuxuzongjian = wuxuzongjian;
		}

		public int getComon_report_type() {
			return comon_report_type;
		}

		public void setComon_report_type(int comon_report_type) {
			this.comon_report_type = comon_report_type;
		}

		public long getaOutpatientNo() {
			return aOutpatientNo;
		}

		public void setaOutpatientNo(long aOutpatientNo) {
			this.aOutpatientNo = aOutpatientNo;
		}

		public String getaPatName() {
			return aPatName;
		}

		public void setaPatName(String aPatName) {
			this.aPatName = aPatName;
		}

		public long getaSex() {
			return aSex;
		}

		public void setaSex(long aSex) {
			this.aSex = aSex;
		}

		public Date getaBirthday() {
			return aBirthday;
		}

		public void setaBirthday(Date aBirthday) {
			this.aBirthday = aBirthday;
		}

		public String getaAddres() {
			return aAddres;
		}

		public void setaAddres(String aAddres) {
			this.aAddres = aAddres;
		}

		public String getaIDCard() {
			return aIDCard;
		}

		public void setaIDCard(String aIDCard) {
			this.aIDCard = aIDCard;
		}

		public String getaTel() {
			return aTel;
		}

		public void setaTel(String aTel) {
			this.aTel = aTel;
		}

		public long getaOperator() {
			return aOperator;
		}

		public void setaOperator(long aOperator) {
			this.aOperator = aOperator;
		}

		public long getaPrice() {
			return aPrice;
		}

		public void setaPrice(long aPrice) {
			this.aPrice = aPrice;
		}

		public String getData_code_children() {
			return data_code_children;
		}

		public void setData_code_children(String data_code_children) {
			this.data_code_children = data_code_children;
		}

		public int getRen_type() {
			return ren_type;
		}

		public void setRen_type(int ren_type) {
			this.ren_type = ren_type;
		}

		public String getU_name() {
			return u_name;
		}

		public void setU_name(String u_name) {
			this.u_name = u_name;
		}

		public String getC_name() {
			return c_name;
		}

		public void setC_name(String c_name) {
			this.c_name = c_name;
		}

		public String getOld_arch_num() {
			return old_arch_num;
		}

		public void setOld_arch_num(String old_arch_num) {
			this.old_arch_num = old_arch_num;
		}

		public String getUp_arch_num_time() {
			return up_arch_num_time;
		}

		public void setUp_arch_num_time(String up_arch_num_time) {
			this.up_arch_num_time = up_arch_num_time;
		}

		public long getUp_arch_num_person() {
			return up_arch_num_person;
		}

		public void setUp_arch_num_person(long up_arch_num_person) {
			this.up_arch_num_person = up_arch_num_person;
		}

		public String getReport_tidy_user() {
			return report_tidy_user;
		}

		public void setReport_tidy_user(String report_tidy_user) {
			this.report_tidy_user = report_tidy_user;
		}

		public long getFreeze() {
			return freeze;
		}

		public void setFreeze(long freeze) {
			this.freeze = freeze;
		}

	    public String getFlag() {
			return flag;
		}

		public void setFlag(String flag) {
			this.flag = flag;
		}

		public String getApptype() {
			return apptype;
		}

		public void setApptype(String apptype) {
			this.apptype = apptype;
		}

		public String getIs_need_guide() {
			return is_need_guide;
		}

		public void setIs_need_guide(String is_need_guide) {
			this.is_need_guide = is_need_guide;
		}

		public String getIs_need_barcode() {
			return is_need_barcode;
		}

		public void setIs_need_barcode(String is_need_barcode) {
			this.is_need_barcode = is_need_barcode;
		}

		public String getExam_indicator() {
			return exam_indicator;
		}

		public void setExam_indicator(String exam_indicator) {
			this.exam_indicator = exam_indicator;
		}

		public String getNation() {
			return nation;
		}

		public void setNation(String nation) {
			this.nation = nation;
		}

		public String getClinic_no() {
			return clinic_no;
		}

		public void setClinic_no(String clinic_no) {
			this.clinic_no = clinic_no;
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

		public String getExam_times() {
			return exam_times;
		}

		public void setExam_times(String exam_times) {
			this.exam_times = exam_times;
		}

	    public String getStatuss() {
			return statuss;
		}

		public void setStatuss(String statuss) {
			this.statuss = statuss;
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

		public String getBirthday() {
			return birthday;
		}

		public void setBirthday(String birthday) {
			this.birthday = birthday;
		}

		public String getId_num() {
			return id_num;
		}

		public void setId_num(String id_num) {
			this.id_num = id_num;
		}

		public String getArch_num() {
			return arch_num;
		}

		public void setArch_num(String arch_num) {
			this.arch_num = arch_num;
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

		public long getId() {
			return id;
		}

		public void setId(long id) {
			this.id = id;
		}		

		public long getGroup_id() {
			return group_id;
		}

		public void setGroup_id(long group_id) {
			this.group_id = group_id;
		}

		public long getCustomer_id() {
			return customer_id;
		}

		public void setCustomer_id(long customer_id) {
			this.customer_id = customer_id;
		}

		public String getExam_num() {
			return exam_num;
		}

		public void setExam_num(String exam_num) {
			this.exam_num = exam_num;
		}

		public String getStatus() {
			return status;
		}

		public void setStatus(String status) {
			this.status = status;//y预约d登记j检查中z已终检
			if ("Y".equals(status)){
				this.setStatuss("Y预约");
			}else if ("D".equals(status)){
				this.setStatuss("D登记");
			}else if ("J".equals(status)){
				this.setStatuss("J检查中");
			}else if ("Z".equals(status)){
				this.setStatuss("Z已终检");
			}else{
				this.setStatuss("未知");
			}
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

		public String getFinal_date() {
			return final_date;
		}

		public void setFinal_date(String final_date) {
			this.final_date = final_date;
		}

		public String getFinal_doctor() {
			return final_doctor;
		}

		public void setFinal_doctor(String final_doctor) {
			this.final_doctor = final_doctor;
		}

		public String getExam_type() {
			return exam_type;
		}

		public void setExam_type(String exam_type) {
			this.exam_type = exam_type;
			if("G".equals(exam_type)){
				this.exam_type_y = "个检";
			}else if("T".equals(exam_type)){
				this.exam_type_y = "团检";
			}
		}

		public String getCustomer_type() {
			return customer_type;
		}

		public void setCustomer_type(String customer_type) {
			this.customer_type = customer_type;
		}

		public String getIs_sampled_directly() {
			return is_sampled_directly;
		}

		public void setIs_sampled_directly(String is_sampled_directly) {
			this.is_sampled_directly = is_sampled_directly;
		}

		public String getIs_adjusted() {
			return is_adjusted;
		}

		public void setIs_adjusted(String is_adjusted) {
			this.is_adjusted = is_adjusted;
		}

		public String getCenter_num() {
			return center_num;
		}

		public void setCenter_num(String center_num) {
			this.center_num = center_num;
		}

		public String getGetReportWay() {
			return getReportWay;
		}

		public void setGetReportWay(String getReportWay) {
			this.getReportWay = getReportWay;
		}

		public String getReportAddress() {
			return reportAddress;
		}

		public void setReportAddress(String reportAddress) {
			this.reportAddress = reportAddress;
		}

		public String getZip() {
			return zip;
		}

		public void setZip(String zip) {
			this.zip = zip;
		}

		public String getChargingType() {
			return chargingType;
		}

		public void setChargingType(String chargingType) {
			this.chargingType = chargingType;
		}

		public String getCustomerType() {
			return customerType;
		}

		public void setCustomerType(String customerType) {
			this.customerType = customerType;
		}

		public String getGroup_index() {
			return group_index;
		}

		public void setGroup_index(String group_index) {
			this.group_index = group_index;
		}

		public String getIs_guide_back() {
			return is_guide_back;
		}

		public void setIs_guide_back(String is_guide_back) {
			this.is_guide_back = is_guide_back;
			if("N".equals(this.is_guide_back)){
				this.setIs_guide_backs("未");
			}else if("Y".equals(this.is_guide_back)){
				this.setIs_guide_backs("已");
			}
		}

		public String getCompany_check_status() {
			return company_check_status;
		}

		public void setCompany_check_status(String company_check_status) {
			this.company_check_status = company_check_status;
		}

		public long getCustomer_type_id() {
			return customer_type_id;
		}

		public void setCustomer_type_id(long customer_type_id) {
			this.customer_type_id = customer_type_id;
		}

		public String getIs_marriage() {
			return is_marriage;
		}

		public void setIs_marriage(String is_marriage) {
			this.is_marriage = is_marriage;
		}

		public long getAge() {
			return age;
		}

		public void setAge(long age) {
			this.age = age;
		}

		public String getAddress() {
			return address;
		}

		public void setAddress(String address) {
			this.address = address;
		}

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}

		public String getPhone() {
			return phone;
		}

		public void setPhone(String phone) {
			this.phone = phone;
		}

		public String getCompany() {
			return company;
		}

		public void setCompany(String company) {
			this.company = company;
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

		public String getPicture_path() {
			return picture_path;
		}

		public void setPicture_path(String picture_path) {
			this.picture_path = picture_path;
		}

		public String getIs_after_pay() {
			return is_after_pay;
		}

		public void setIs_after_pay(String is_after_pay) {
			this.is_after_pay = is_after_pay;
		}

		public String getPast_medical_history() {
			return past_medical_history;
		}

		public void setPast_medical_history(String past_medical_history) {
			this.past_medical_history = past_medical_history;
		}

		public String getRemarke() {
			return remarke;
		}

		public void setRemarke(String remarke) {
			this.remarke = remarke;
		}

		public String getIntroducer() {
			return introducer;
		}

		public void setIntroducer(String introducer) {
			this.introducer = introducer;
		}

		public String getCounter_check() {
			return counter_check;
		}

		public void setCounter_check(String counter_check) {
			this.counter_check = counter_check;
		}

		public String getGuide_nurse() {
			return guide_nurse;
		}

		public void setGuide_nurse(String guide_nurse) {
			this.guide_nurse = guide_nurse;
		}

		public String getAppointment() {
			return appointment;
		}

		public void setAppointment(String appointment) {
			this.appointment = appointment;
		}

		public String getData_source() {
			return data_source;
		}

		public void setData_source(String data_source) {
			this.data_source = data_source;
		}

		public String getOthers() {
			return others;
		}

		public void setOthers(String others) {
			this.others = others;
		}

		public String getOrder_id() {
			return order_id;
		}

		public void setOrder_id(String order_id) {
			this.order_id = order_id;
		}

		public String getIs_Active() {
			return is_Active;
		}

		public void setIs_Active(String is_Active) {
			this.is_Active = is_Active;
		}

		public long getCreater() {
			return creater;
		}

		public void setCreater(long creater) {
			this.creater = creater;
		}

		public long getUpdater() {
			return updater;
		}

		public void setUpdater(long updater) {
			this.updater = updater;
		}

		public String getCreate_time() {
			return create_time;
		}

		public void setCreate_time(String create_time) {
			this.create_time = create_time;
		}

		public String getUpdate_time() {
			return update_time;
		}

		public void setUpdate_time(String update_time) {
			this.update_time = update_time;
		}

		public String getSet_name() {
			return set_name;
		}

		public void setSet_name(String set_name) {
			this.set_name = set_name;
		}

		public String getIs_report_print() {
			return is_report_print;
		}

		public void setIs_report_print(String is_report_print) {
			this.is_report_print = is_report_print;
			if("Y".equals(is_report_print)){
				this.setIs_report_print_y("是");
			}else{
				this.setIs_report_print_y("否");
			}
		}

		public String getIs_report_tidy() {
			return is_report_tidy;
		}

		public void setIs_report_tidy(String is_report_tidy) {
			this.is_report_tidy = is_report_tidy;
			if("Y".equals(is_report_tidy)){
				this.setIs_report_tidy_y("已整理");
			}else{
				this.setIs_report_tidy_y("未整理");
			}
		}

		public String getIs_report_tidy_y() {
			return is_report_tidy_y;
		}

		public void setIs_report_tidy_y(String is_report_tidy_y) {
			this.is_report_tidy_y = is_report_tidy_y;
		}

		public String getPrint_doctor() {
			return print_doctor;
		}

		public void setPrint_doctor(String print_doctor) {
			this.print_doctor = print_doctor;
		}

		public String getPrint_time() {
			return print_time;
		}

		public void setPrint_time(String print_time) {
			this.print_time = print_time;
		}

		public String getReceive_type() {
			return receive_type;
		}

		public void setReceive_type(String receive_type) {
			this.receive_type = receive_type;
			if("0".equals(receive_type)){
				this.setReceive_type_y("未邮寄,未自取");
			}else if("1".equals(receive_type)){
				this.setReceive_type_y("已邮寄");
			}else if("2".equals(receive_type)){
				this.setReceive_type_y("已自取");
			}
		}

		public String getIs_report_print_y() {
			return is_report_print_y;
		}

		public void setIs_report_print_y(String is_report_print_y) {
			this.is_report_print_y = is_report_print_y;
		}

		public String getReceive_type_y() {
			return receive_type_y;
		}

		public void setReceive_type_y(String receive_type_y) {
			this.receive_type_y = receive_type_y;
		}

		public String getApp_doctor() {
			return app_doctor;
		}

		public void setApp_doctor(String app_doctor) {
			this.app_doctor = app_doctor;
		}

		public String getExam_type_y() {
			return exam_type_y;
		}

		public void setExam_type_y(String exam_type_y) {
			this.exam_type_y = exam_type_y;
		}

		public String getIsPrePay() {
			return isPrePay;
		}

		public void setIsPrePay(String isPrePay) {
			this.isPrePay = isPrePay;
			if("Y".equals(isPrePay)){
				this.isPrePay = "预结";
			}else if("N".equals(isPrePay)){
				this.isPrePay = "非预结";
			}
		}

		public String getIsnotPay() {
			return isnotPay;
		}

		public void setIsnotPay(String isnotPay) {
			this.isnotPay = isnotPay;
			if("Y".equals(isnotPay)){
				this.isnotPay = "含弃检";
			}else if("N".equals(isnotPay)){
				this.isnotPay = "不含弃检";
			}
		}

		public String getPay_status() {
			return pay_status;
		}

		public void setPay_status(String pay_status) {
			this.pay_status = pay_status;
		}

		public double getTotalamt() {
			return totalamt;
		}

		public void setTotalamt(double totalamt) {
			this.totalamt = totalamt;
		}

		public Double getBudget_amount() {
			return budget_amount;
		}

		public void setBudget_amount(Double budget_amount) {
			this.budget_amount = budget_amount;
		}

		public String getDegreeOfedu() {
			return degreeOfedu;
		}

		public void setDegreeOfedu(String degreeOfedu) {
			this.degreeOfedu = degreeOfedu;
		}

		public int getZyb_set_source() {
			return zyb_set_source;
		}

		public void setZyb_set_source(int zyb_set_source) {
			this.zyb_set_source = zyb_set_source;
		}

		public int getWeijian() {
			return weijian;
		}

		public void setWeijian(int weijian) {
			this.weijian = weijian;
		}

		public int getYijian() {
			return yijian;
		}

		public void setYijian(int yijian) {
			this.yijian = yijian;
		}

		public String getStart_date() {
			return start_date;
		}

		public void setStart_date(String start_date) {
			this.start_date = start_date;
		}

		public String getEnd_date() {
			return end_date;
		}

		public void setEnd_date(String end_date) {
			this.end_date = end_date;
		}

		public double getPersonal_pay() {
			return personal_pay;
		}

		public void setPersonal_pay(double personal_pay) {
			this.personal_pay = personal_pay;
		}

		public double getTeam_pay() {
			return team_pay;
		}

		public void setTeam_pay(double team_pay) {
			this.team_pay = team_pay;
		}

		public String getFs_creater() {
			return fs_creater;
		}

		public void setFs_creater(String fs_creater) {
			this.fs_creater = fs_creater;
		}

		public String getFs_date() {
			return fs_date;
		}

		public void setFs_date(String fs_date) {
			this.fs_date = fs_date;
		}

		public String getDJD_path() {
			return DJD_path;
		}

		public void setDJD_path(String dJD_path) {
			DJD_path = dJD_path;
		}

		public String getDJD_image_creater() {
			return DJD_image_creater;
		}

		public void setDJD_image_creater(String dJD_image_creater) {
			DJD_image_creater = dJD_image_creater;
		}

		public String getFlow_name() {
			return flow_name;
		}

		public void setFlow_name(String flow_name) {
			this.flow_name = flow_name;
		}

		public String getSenddate() {
			return senddate;
		}

		public void setSenddate(String senddate) {
			this.senddate = senddate;
		}

		public String getIs_guide_backs() {
			return is_guide_backs;
		}

		public void setIs_guide_backs(String is_guide_backs) {
			this.is_guide_backs = is_guide_backs;
		}

		public String getCheck_name() {
			return check_name;
		}

		public void setCheck_name(String check_name) {
			this.check_name = check_name;
		}

		public String getCheck_time() {
			return check_time;
		}

		public void setCheck_time(String check_time) {
			this.check_time = check_time;
		}

		public String getReport_Print_UserName() {
			return Report_Print_UserName;
		}

		public void setReport_Print_UserName(String report_Print_UserName) {
			Report_Print_UserName = report_Print_UserName;
		}

		public String getReport_Print_Date() {
			return Report_Print_Date;
		}

		public void setReport_Print_Date(String report_Print_Date) {
			Report_Print_Date = report_Print_Date;
		}

		public int getPrint_count() {
			return print_count;
		}

		public void setPrint_count(int print_count) {
			this.print_count = print_count;
		}

		public String getZiqu_report_time() {
			return ziqu_report_time;
		}

		public void setZiqu_report_time(String ziqu_report_time) {
			this.ziqu_report_time = ziqu_report_time;
		}

		public String getExam_center_num() {
			return exam_center_num;
		}

		public void setExam_center_num(String exam_center_num) {
			this.exam_center_num = exam_center_num;
		}
		
	}