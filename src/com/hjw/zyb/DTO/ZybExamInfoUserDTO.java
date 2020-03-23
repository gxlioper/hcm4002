package com.hjw.zyb.DTO;

import java.util.Date;

public class ZybExamInfoUserDTO implements java.io.Serializable {
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
		
		private String check_doctor="";
		
		private String exam_status;
		
		private String exam_type="";
		
		private String exam_types="";
		
		private String customer_type="";
		
		private String is_sampled_directly="";
		
		private String is_adjusted="";
		
		private String center_num="";
		
		private String getReportWay="";
		
		private String reportAddress="";
		
		private String chargingType="";
		
		private String customerType="";
		
		private String group_index="";
		
		private String is_Active="";
		
		private long creater;
		
		private String create_time="";
		
		private long updater;
		
		private Date update_time=null;
		
		private String is_guide_back="";
		
		private String is_guide_backs="";
		
		private String company_check_status="";
		
		private long customer_type_id;
		
		private String customer_type_name;
		
		private String is_marriage="";
		
		private long age;
		
		private String address="";
		
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
		
		private String counter_check="";
		private String counter_checks;
		
		private String guide_nurse="";
		
		private String appointment="";
		
		private String data_source="";
		
		private String others="";
		
		private String order_id="";
		
		private String user_name="";
		
		private String sex="";		
		
		private String arch_num="";	
		private String idtypename;
		private int idtype;
		private String id_num="";		
		
		private String birthday="";
		
		private String is_need_barcode="";
		
		private String is_need_guide="";
		
        private String is_need_barcodes="";
		
		private String is_need_guides="";
		
		private String dep_name="";
		
		private String group_name="";		
		
		private String set_name="";	
		
		private String remark1="";		
		
		private String statuss="";
		
		private String exam_times="";	
		
		private long wpacs;
		
		private long ypacs;
		
		private long wlis;
		
		private long ylis;
		
		private String pacs="";
		
		private String lis="";		
		
		private String nation="";
		
		private long batch_id;
		
		private String batch_name="";
		
		private long company_id;
	    
	    private String picture_Path="";
 
	    private String patient_id="";
	    
	    private String chi_name="";

		private String employeeID="";

		private String mc_no="";//就诊卡号

		private String visit_date="";//就诊日期

		private String visit_no="";//就诊号
		
		private String clinic_no="";//门诊号	
		
		private String exam_indicator="";//团体付费状态 T团体结算 G 自费结算	
		
		private String type_name="";//人员类型
		
	    private String actiontype="";
	    
	    private String actiontypes="";	 
	    
	    private int isnotpay;//是否包含弃检项目 0不包含1 包含
	    
	    private int isprepay;//是否预结算	   0不预结算 1 预结算 	  
	    
	    private long c_id;
		
	    private double personal_pay;
	    
	    private double team_pay;  
	    
	    private int examcount=0;	 
	    private String cygzname;
	    private String cyhyname;
	    private String occusectorid="";
	    private String occutypeofworkid;
	    private String joinDatetime;
	    private String occusector;
	    private String occutypeofwork;
	    private int employeeage;
	    private int damage;
	    
	    private int zyb_set_source;//职业病体检套餐来源，0表示按职业危害因素关联套餐，1表示关联自选套餐
	    
	    private int employeemonth=0;
        private int dammonth=0;
        
        
        
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

		public String getIdtypename() {
			return idtypename;
		}

		public void setIdtypename(String idtypename) {
			this.idtypename = idtypename;
		}

		public int getIdtype() {
			return idtype;
		}

		public void setIdtype(int idtype) {
			this.idtype = idtype;
		}

		public String getCygzname() {
			return cygzname;
		}

		public void setCygzname(String cygzname) {
			this.cygzname = cygzname;
		}

		public String getCyhyname() {
			return cyhyname;
		}

		public void setCyhyname(String cyhyname) {
			this.cyhyname = cyhyname;
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

		public String getJoinDatetime() {
			return joinDatetime;
		}

		public void setJoinDatetime(String joinDatetime) {
			this.joinDatetime = joinDatetime;
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

		public int getExamcount() {
			return examcount;
		}

		public void setExamcount(int examcount) {
			this.examcount = examcount;
		}

		public String getCustomer_type_name() {
			return customer_type_name;
		}

		public void setCustomer_type_name(String customer_type_name) {
			this.customer_type_name = customer_type_name;
		}

		public long getC_id() {
			return c_id;
		}

		public void setC_id(long c_id) {
			this.c_id = c_id;
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

		public int getIsnotpay() {
			return isnotpay;
		}

		public void setIsnotpay(int isnotpay) {
			this.isnotpay = isnotpay;
		}

		public int getIsprepay() {
			return isprepay;
		}

		public void setIsprepay(int isprepay) {
			this.isprepay = isprepay;
		}

		public String getExam_status() {
			return exam_status;
		}

		public void setExam_status(String exam_status) {
			this.exam_status = exam_status;
		}

		public String getActiontype() {
			return actiontype;
		}

		public void setActiontype(String actiontype) {
			this.actiontype = actiontype;
			if("0".equals(actiontype)){
				this.setActiontypes("分发");
			}else if("1".equals(actiontype)){
				this.setActiontypes("签收");
			}else {
				this.setActiontypes("未知");
			}
		}

		public String getActiontypes() {
			return actiontypes;
		}

		public void setActiontypes(String actiontypes) {
			this.actiontypes = actiontypes;
		}

		public String getType_name() {
			return type_name;
		}

		public void setType_name(String type_name) {
			this.type_name = type_name;
		}

		public String getChi_name() {
			return chi_name;
		}

		public void setChi_name(String chi_name) {
			this.chi_name = chi_name;
		}

		public String getExam_indicator() {
			return exam_indicator;
		}

		public void setExam_indicator(String exam_indicator) {
			this.exam_indicator = exam_indicator;
		}

		public String getPatient_id() {
			return patient_id;
		}

		public void setPatient_id(String patient_id) {
			this.patient_id = patient_id;
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

		public String getEmployeeID() {
			return employeeID;
		}

		public void setEmployeeID(String employeeID) {
			this.employeeID = employeeID;
		}

		public String getBatch_name() {
			return batch_name;
		}

		public void setBatch_name(String batch_name) {
			this.batch_name = batch_name;
		}

		public String getCheck_doctor() {
			return check_doctor;
		}

		public void setCheck_doctor(String check_doctor) {
			this.check_doctor = check_doctor;
		}

		public String getPicture_Path() {
			return picture_Path;
		}

		public void setPicture_Path(String picture_Path) {
			this.picture_Path = picture_Path;
		}

		public long getBatch_id() {
			return batch_id;
		}

		public void setBatch_id(long batch_id) {
			this.batch_id = batch_id;
		}

		public long getCompany_id() {
			return company_id;
		}

		public void setCompany_id(long company_id) {
			this.company_id = company_id;
		}

		public String getNation() {
			return nation;
		}

		public void setNation(String nation) {
			this.nation = nation;
		}

		public String getPacs() {
			return pacs;
		}

		public void setPacs(String pacs) {
			this.pacs = pacs;
		}

		public String getLis() {
			return lis;
		}

		public void setLis(String lis) {
			this.lis = lis;
		}

		public long getWpacs() {
			return wpacs;
		}

		public void setWpacs(long wpacs) {
			this.wpacs = wpacs;
		}

		public long getYpacs() {
			return ypacs;
		}

		public void setYpacs(long ypacs) {
			this.ypacs = ypacs;
		}

		public long getWlis() {
			return wlis;
		}

		public void setWlis(long wlis) {
			this.wlis = wlis;
		}

		public long getYlis() {
			return ylis;
		}

		public void setYlis(long ylis) {
			this.ylis = ylis;
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

		public String getIs_need_barcodes() {
			return is_need_barcodes;
		}

		public void setIs_need_barcodes(String is_need_barcodes) {
			this.is_need_barcodes = is_need_barcodes;
		}

		public String getIs_need_guides() {
			return is_need_guides;
		}

		public void setIs_need_guides(String is_need_guides) {
			this.is_need_guides = is_need_guides;
		}

		public String getRemark1() {
			return remark1;
		}

		public void setRemark1(String remark1) {
			this.remark1 = remark1;
		}

		public String getSet_name() {
			return set_name;
		}

		public void setSet_name(String set_name) {
			this.set_name = set_name;
		}

		public String getGroup_name() {
			return group_name;
		}

		public void setGroup_name(String group_name) {
			this.group_name = group_name;
		}

		public String getDep_name() {
			return dep_name;
		}

		public void setDep_name(String dep_name) {
			this.dep_name = dep_name;
		}

		public String getIs_need_barcode() {
			return is_need_barcode;
		}

		public void setIs_need_barcode(String is_need_barcode) {
			this.is_need_barcode = is_need_barcode;
			if("Y".equals(is_need_barcode)){
				this.setIs_need_barcodes("是");
			}else{
				this.setIs_need_barcodes("否");
			}
		}

		public String getIs_need_guide() {
			return is_need_guide;
		}

		public void setIs_need_guide(String is_need_guide) {
			this.is_need_guide = is_need_guide;
			if("Y".equals(is_need_guide)){
				this.setIs_need_guides("是");
			}else{
				this.setIs_need_guides("否");
			}
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
			this.status = status;
			if("Y".equals(status)){
				this.setStatuss("预约");
			}else if("D".equals(status)){
				this.setStatuss("登记");
			}else if("J".equals(status)){
				this.setStatuss("检查中");
			}else if("Z".equals(status)){
				this.setStatuss("已终检");
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
		
		public String getExam_types() {
			return exam_types;
		}

		public void setExam_types(String exam_types) {
			this.exam_types = exam_types;
		}

		public String getExam_type() {
			return exam_type;
		}

		public void setExam_type(String exam_type) {
			this.exam_type = exam_type;
			if("T".equals(exam_type)){
				this.setExam_types("团检");
			}else if("G".equals(exam_type)){
				this.setExam_types("个检");
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

		public String getIs_guide_backs() {
			return is_guide_backs;
		}

		public void setIs_guide_backs(String is_guide_backs) {
			this.is_guide_backs = is_guide_backs;
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
			if("N".equals(counter_check)){
				this.setCounter_checks("否");
			}else{
				this.setCounter_checks("是");
			}
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

		public Date getUpdate_time() {
			return update_time;
		}

		public void setUpdate_time(Date update_time) {
			this.update_time = update_time;
		}

		public String getCounter_checks() {
			return counter_checks;
		}

		public void setCounter_checks(String counter_checks) {
			this.counter_checks = counter_checks;
		}

		public int getZyb_set_source() {
			return zyb_set_source;
		}

		public void setZyb_set_source(int zyb_set_source) {
			this.zyb_set_source = zyb_set_source;
		}

		@Override
		public String toString() {
			return "ExamInfoUserDTO [id=" + id + ", group_id=" + group_id + ", customer_id=" + customer_id
					+ ", exam_num=" + exam_num + ", status=" + status + ", register_date=" + register_date
					+ ", join_date=" + join_date + ", final_date=" + final_date + ", final_doctor=" + final_doctor
					+ ", check_doctor=" + check_doctor + ", exam_type=" + exam_type + ", exam_types=" + exam_types
					+ ", customer_type=" + customer_type + ", is_sampled_directly=" + is_sampled_directly
					+ ", is_adjusted=" + is_adjusted + ", center_num=" + center_num + ", getReportWay=" + getReportWay
					+ ", reportAddress=" + reportAddress + ", chargingType=" + chargingType + ", customerType="
					+ customerType + ", group_index=" + group_index + ", is_Active=" + is_Active + ", creater="
					+ creater + ", create_time=" + create_time + ", updater=" + updater + ", update_time=" + update_time
					+ ", is_guide_back=" + is_guide_back + ", company_check_status=" + company_check_status
					+ ", customer_type_id=" + customer_type_id + ", is_marriage=" + is_marriage + ", age=" + age
					+ ", address=" + address + ", email=" + email + ", phone=" + phone + ", company=" + company
					+ ", position=" + position + ", _level=" + _level + ", picture_path=" + picture_path
					+ ", is_after_pay=" + is_after_pay + ", past_medical_history=" + past_medical_history + ", remarke="
					+ remarke + ", introducer=" + introducer + ", counter_check=" + counter_check + ", guide_nurse="
					+ guide_nurse + ", appointment=" + appointment + ", data_source=" + data_source + ", others="
					+ others + ", order_id=" + order_id + ", user_name=" + user_name + ", sex=" + sex + ", arch_num="
					+ arch_num + ", id_num=" + id_num + ", birthday=" + birthday + ", is_need_barcode="
					+ is_need_barcode + ", is_need_guide=" + is_need_guide + ", is_need_barcodes=" + is_need_barcodes
					+ ", is_need_guides=" + is_need_guides + ", dep_name=" + dep_name + ", group_name=" + group_name
					+ ", set_name=" + set_name + ", remark1=" + remark1 + ", statuss=" + statuss + ", exam_times="
					+ exam_times + ", wpacs=" + wpacs + ", ypacs=" + ypacs + ", wlis=" + wlis + ", ylis=" + ylis
					+ ", pacs=" + pacs + ", lis=" + lis + ", nation=" + nation + ", batch_id=" + batch_id
					+ ", batch_name=" + batch_name + ", company_id=" + company_id + ", picture_Path=" + picture_Path
					+ ", patient_id=" + patient_id + ", chi_name=" + chi_name + ", employeeID=" + employeeID
					+ ", mc_no=" + mc_no + ", visit_date=" + visit_date + ", visit_no=" + visit_no + ", clinic_no="
					+ clinic_no + ", exam_indicator=" + exam_indicator + "]";
		}		
		
		
	}