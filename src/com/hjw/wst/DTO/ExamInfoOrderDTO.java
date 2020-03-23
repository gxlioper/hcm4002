package com.hjw.wst.DTO;

public class ExamInfoOrderDTO implements java.io.Serializable {
	 private static final long serialVersionUID = -97502163798576023L;

		private long id;

        private long group_id;
        
		private long customer_id;
		
		private String exam_num="";
		
		private String status="";
		
		private String orderstatus="";//订单状态
		
		private String orderstatuss="";
		
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
		
		private String update_time=null;
		
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
	    
	    private long examcount;	  
	    
	    private String acc_num="";
	    
	    private String createdate="";
       
	    private String printflag="";
	    
	    private long printer;
	    
	    private String printdate="";
	    
	    private String flag="0";	
	    
	    private long freeze;
	    
	    private String freezename="";	
	    
	    private String occusectorid;
	    
	    private String read_status_str;
	    
	    private long read_status;//读取状态
	    private String bunk;//床位
	    private String allocationdate;//分配时间
	    
	    private String receive_type;//是否邮寄
	    private String disease_name;//
	    
	    private String c_name;
	    private String u_name;
	    
	    private String exam_desc;
	    private String exam_result;
	    private String item_name;
	    private String data_name;
	    private String marker;
	    private String mark_time;
	    private int item_number;
	    private String item_num_s;
	    private double budget_amount;//预算金额
	    
		public String getOrderstatus() {
			return orderstatus;
		}

		public void setOrderstatus(String orderstatus) {
			this.orderstatus = orderstatus;
			if("1".equals(orderstatus)){
				this.setOrderstatuss("预约订单");
			}else if("2".equals(orderstatus)){
				this.setOrderstatuss("订单取消");
			}else if("3".equals(orderstatus)){
				this.setOrderstatuss("订单支付");
			}else{
				this.setOrderstatuss("未知订单");
			}
		}

		public String getOrderstatuss() {
			return orderstatuss;
		}

		public void setOrderstatuss(String orderstatuss) {
			this.orderstatuss = orderstatuss;
		}

		public String getItem_num_s() {
			return item_num_s;
		}

		public void setItem_num_s(String item_num_s) {
			this.item_num_s = item_num_s;
		}

		public int getItem_number() {
			return item_number;
		}

		public void setItem_number(int item_number) {
			this.item_number = item_number;
			if(item_number>0){
				this.setItem_num_s(item_number+"");
			} 
		}

		public String getMarker() {
			return marker;
		}

		public void setMarker(String marker) {
			this.marker = marker;
		}

		public String getMark_time() {
			return mark_time;
		}

		public void setMark_time(String mark_time) {
			this.mark_time = mark_time;
		}

		public String getData_name() {
			return data_name;
		}

		public void setData_name(String data_name) {
			this.data_name = data_name;
		}

		public String getItem_name() {
			return item_name;
		}

		public void setItem_name(String item_name) {
			this.item_name = item_name;
		}

		public String getExam_desc() {
			return exam_desc;
		}

		public void setExam_desc(String exam_desc) {
			this.exam_desc = exam_desc;
		}

		public String getExam_result() {
			return exam_result;
		}

		public void setExam_result(String exam_result) {
			this.exam_result = exam_result;
		}

		public String getC_name() {
			return c_name;
		}

		public void setC_name(String c_name) {
			this.c_name = c_name;
		}

		public String getU_name() {
			return u_name;
		}

		public void setU_name(String u_name) {
			this.u_name = u_name;
		}

		public String getDisease_name() {
			return disease_name;
		}

		public void setDisease_name(String disease_name) {
			this.disease_name = disease_name;
			if(disease_name!=null && !"".equals(disease_name)){
				String di = disease_name.substring(0,1);
				if("★".equals(di)){
					this.disease_name = "★";
				} else {
					this.disease_name = "";
				}
			}
		}

		public String getReceive_type() {
			return receive_type;
		}

		public void setReceive_type(String receive_type) {
			if("1".equals(receive_type)){
				this.receive_type = "邮寄";
			} else if("0".equals(receive_type)) {
				this.receive_type = "自取";
			} else {
				this.receive_type = "";
			}
		}

		public String getCompany() {
			return company;
		}

		public void setCompany(String company) {
			this.company = company;
		}

		public String getAllocationdate() {
			return allocationdate;
		}

		public void setAllocationdate(String allocationdate) {
			this.allocationdate = allocationdate;
		}

		public String getBunk() {
			return bunk;
		}

		public void setBunk(String bunk) {
			this.bunk = bunk;
		}

		public long getRead_status() {
			return read_status;
		}

		public void setRead_status(long read_status) {
			this.read_status = read_status;
			if(read_status==0){
				this.read_status_str ="未读取";
			}else if(read_status==1){
				this.read_status_str ="已读取";
			}
		}

		public String getRead_status_str() {
			return read_status_str;
		}

		public void setRead_status_str(String read_status_str) {
			this.read_status_str = read_status_str;
		}

		public String getOccusectorid() {
			return occusectorid;
		}

		public void setOccusectorid(String occusectorid) {
			this.occusectorid = occusectorid;
		}

		public long getFreeze() {
			return freeze;
		}

		public void setFreeze(long freeze) {
			this.freeze = freeze;
			if(freeze==0){
				this.setFreezename("正常");
			}else if(freeze==1){
				this.setFreezename("冻结");
			}
		}

		public String getFreezename() {
			return freezename;
		}

		public void setFreezename(String freezename) {
			this.freezename = freezename;		
		}

		public String getFlag() {
			return flag;
		}

		public void setFlag(String flag) {
			this.flag = flag;
		}

		public String getAcc_num() {
			return acc_num;
		}

		public void setAcc_num(String acc_num) {
			this.acc_num = acc_num;
		}

		public String getCreatedate() {
			return createdate;
		}

		public void setCreatedate(String createdate) {
			this.createdate = createdate;
		}

		public String getPrintflag() {
			return printflag;
		}

		public void setPrintflag(String printflag) {
			this.printflag = printflag;
		}

		public long getPrinter() {
			return printer;
		}

		public void setPrinter(long printer) {
			this.printer = printer;
		}

		public String getPrintdate() {
			return printdate;
		}

		public void setPrintdate(String printdate) {
			this.printdate = printdate;
		}

		public long getExamcount() {
			return examcount;
		}

		public void setExamcount(long examcount) {
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

		public String getUpdate_time() {
			return update_time;
		}

		public void setUpdate_time(String update_time) {
			this.update_time = update_time;
		}

		public String getCounter_checks() {
			return counter_checks;
		}

		public void setCounter_checks(String counter_checks) {
			this.counter_checks = counter_checks;
		}

		public double getBudget_amount() {
			return budget_amount;
		}

		public void setBudget_amount(double budget_amount) {
			this.budget_amount = budget_amount;
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
					+ ", position=" + position + ", _level=" + _level + ", picture_path=" + picture_Path
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
					+ ", batch_name=" + batch_name + ", company_id=" + company_id + ", patient_id=" + patient_id + ", chi_name=" + chi_name + ", employeeID=" + employeeID
					+ ", mc_no=" + mc_no + ", visit_date=" + visit_date + ", visit_no=" + visit_no + ", clinic_no="
					+ clinic_no + ", exam_indicator=" + exam_indicator + "]";
		}		
		
		
	}