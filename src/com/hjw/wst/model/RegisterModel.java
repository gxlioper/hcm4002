package com.hjw.wst.model;

public class RegisterModel implements java.io.Serializable {
	 private static final long serialVersionUID = -97502163798576023L;

	    private long id;

		private long company_id;

		private long batch_id;
		
		private long set_id;

		private long contract_id;		

		private String arch_num="";

		private String id_num="";

		private String sex="";

		private String birthday="";

		private String custname="";

		private long age;

		private  String time5;
		private String sex_type;
		private String is_print;
		private String is_marriage="";

		private String position="";

		private String _level="";
		
		private String _level_name="";

		private String tel="";

		private String remark="";
		
		private String introducer = "";

		private String customer_type="";

		private String others="";

		private long flags;

		private String notices="";

	    private long creater;

	    private String create_time="";
	    
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
	    
	    private String time1="";
	    
	    private String time2="";  
	    
	    private String exam_type=""; 	   
	    
	    private String status="";
	    
	    private String nation="";	
	    
	    private long customer_id;
	    
	    private String chargingType="";
	    
	    private String address="";
	    
	    private String exam_num="";
	    
	    private String itementities="";    
	    
	    private String setentities="";  
	    
	    private String companybatch_id="";  
	    
	    private String chkItem="";
	    
	    private String company="";//单位中文
	    
	    private String past_medical_history="";//既往史
	    
	    private String picture_Path="";
	    
	    private String is_after_pay="";//是否后收费
	    
	    private String feeresourceflag;//是否拥有后付费 资源
	    
	    private double amount;
	    
	    private double discount;
	    
	    private double item_amount;
	    
	    private long dingweitype;
	    
	    private String register_date="";
	    
	    private String join_date="";
	    
	    private String customerType=""; 
	    
	    private String setimes="";    
	    
	    private String employeeID="";
	    
	    private String update_time="";
	    
        private String mc_no="";//就诊卡号

		private String visit_date="";//就诊日期

		private String visit_no="";//就诊号

		private String clinic_no="";//门诊号	
		
		private String exam_indicator="";//团体付费状态 T团体结算 G 自费结算		
		
		private long contralcustflag;//操作是否新增或者修改，团检使用	
		
		private String bar_code_url;//BAR_CODE_URL;
		
		private String djd_code_url;//DJD_CODE_URL;	
		
		private String report_url;//体检报告保存地址

		private String customer_type_special;//信息显示为特殊颜色的人员类别id，逗号隔开（登记台、科室体检、总检）
		
		private String customer_type_special_color;//特殊人员类别信息显示的颜色（登记台、科室体检、总检）
		
		private String freeze_color;//冻结人员信息显示的颜色（登记台、科室体检、总检）
		
		private String barcode_print_type;//调用打印程序类型
		private String zyb_barcode_print_type;
		private String report_print_type;//调用打印程序类型
		private String zyb_report_print_type;
		
        private String time3="";
	    
	    private String time4="";  
		
		private String receive_type;
		
		private String receive_name;
		
		private String receive_phone;
		
		private String receive_address;
		
		private String receive_postcode;
		
		private String receive_remark;		
		
		private String oldexam_num;
		private String reexam_type="N";//复检标志	
		
		private String joinDatetime;//进厂日期

		private int employeeage;//总工龄

		private int damage; //接害工龄

		private String occusector="";//行业

		private String occutypeofwork="";//工种	
		
		private String occusectorid="";
		private String occutypeofworkid="";	
		private String zywhlb="";
		private String zywhyslb="";
		private long examset_id;
		private String harmname="";//危害因素名称	
		private String concentrations="";//危害因素浓度							
		private String measure="";//防护措施							
		private String isradiation="";//是否放射工作史			
		private String zytjlb;//职业体检类别		
		private String app_type="1";
		private String bunk;//床位
		private int ren_type;
		private String typeofwork_name;
		
		private String budget_amount;//预算金额
		private String orderid;
		private String phone;
		private int zyb_set_source;
		
		private String yuyue_date1;
		private String yuyue_date2;
		
		private String djdstatuss;
		
		private String copy_status;
		
		private int idtype;
		
		private String idtypename;
		private String data_name;
		
		private String read_jiuzhenka;//就诊卡读取设备
		
		private String Read_jiuzhenka_ocx;
		
		private String data_source;
		private String bodao_checkebox;//报到  是否默认勾选  条码-档案号-导简单 配置
		
		private String levels="";
		
		private String degreeOfedu = "";
		
		private int political_status;
		
		private String membership_card;
		
		private String getReportWay="";//报告临取方式
		
		private String is_default_ten;
		
		private String ziqu_report_time="";//领取报告时间
		
		private String ti_name; 
		
		private String ti_sex;
		
		private int ti_age;
		
		private String ti_id_num;
		
		private String is_show_shenqingdan; //是否展示打申请单
		
		private String is_show_select_shenqingdan; //是否展示选择打申请单
		
		private String is_show_paiduihao;  //是否展示打排队号
		
		private String is_show_daojiandan_shengqingdan;  //是否展示打导检单申请单
		
		private String is_print_barcode_item_pay;//登记台打印条码是否判断项目是否付费
		
		private int djtfeedoflag;
		
		private String idnum_notnull;
		
		private String is_show_hh_pay;
		
		private int wuxuzongjian;

		private int is_show_discount;  //是否展示折扣率跟折扣后金额
		
		private String is_custom_identification;//特殊人员标识加★
		
		private String login_name;
		
		private String is_chargingway_zero;//0元收费项目配置
		
		private String card_num;
		
		private String djtlispacsflag;//是否自动发送lis/pacs申请
		
		private String zybxz_exam_num;

		private String disease_id;
		
		private String isphone;//有无电话号码
		
		private String sms_status;//短信发布状态
		
		private String isprivateflag;//是否有隐私权限
		
		private int ttog=0;
        private int gtot=0;
        private int gtjf=0;

        private int ttog_exam=0;
        private int gtot_exam=0;
        private int gtjf_exam=0;
        
        private int employeemonth=0;
        private int dammonth=0;

        private String  is_his_interface;
        private String  is_lis_interface;
        private String  is_pacs_interface;
        private String item_codes;
        private String charging_item_codes;


    public String getCharging_item_codes() {
        return charging_item_codes;
    }

    public void setCharging_item_codes(String charging_item_codes) {
        this.charging_item_codes = charging_item_codes;
    }

    public String getItem_codes() {
        return item_codes;
    }

    public void setItem_codes(String item_codes) {
        this.item_codes = item_codes;
    }

    public String getTime5() {
			return time5;
		}


		public void setTime5(String time5) {
			this.time5 = time5;
		}


		public String getSex_type() {
			return sex_type;
		}


		public void setSex_type(String sex_type) {
			this.sex_type = sex_type;
		}


		public String getIs_print() {
			return is_print;
		}


		public void setIs_print(String is_print) {
			this.is_print = is_print;
		}


		public String getIs_his_interface() {
		return is_his_interface;
	}

		
	public String getFeeresourceflag() {
			return feeresourceflag;
		}


		public void setFeeresourceflag(String feeresourceflag) {
			this.feeresourceflag = feeresourceflag;
		}


	public String getIsprivateflag() {
			return isprivateflag;
		}


		public void setIsprivateflag(String isprivateflag) {
			this.isprivateflag = isprivateflag;
		}


	public void setIs_his_interface(String is_his_interface) {
		this.is_his_interface = is_his_interface;
	}

	public String getIs_lis_interface() {
		return is_lis_interface;
	}

	public void setIs_lis_interface(String is_lis_interface) {
		this.is_lis_interface = is_lis_interface;
	}

	public String getIs_pacs_interface() {
		return is_pacs_interface;
	}

	public void setIs_pacs_interface(String is_pacs_interface) {
		this.is_pacs_interface = is_pacs_interface;
	}

	public String getDjtlispacsflag() {
		return djtlispacsflag;
	}

		public String getZybxz_exam_num() {
			return zybxz_exam_num;
		}

		public void setZybxz_exam_num(String zybxz_exam_num) {
			this.zybxz_exam_num = zybxz_exam_num;
		}

		public void setDjtlispacsflag(String djtlispacsflag) {
			this.djtlispacsflag = djtlispacsflag;
		}
		
		public String getLogin_name() {
			return login_name;
		}

		public void setLogin_name(String login_name) {
			this.login_name = login_name;
		}

		public int getIs_show_discount() {
			return is_show_discount;
		}

		public void setIs_show_discount(int is_show_discount) {
			this.is_show_discount = is_show_discount;
		}

		public String getIs_show_hh_pay() {
			return is_show_hh_pay;
		}

		public void setIs_show_hh_pay(String is_show_hh_pay) {
			this.is_show_hh_pay = is_show_hh_pay;
		}
		
		public String getIdnum_notnull() {
			return idnum_notnull;
		}

		public void setIdnum_notnull(String idnum_notnull) {
			this.idnum_notnull = idnum_notnull;
		}
		
		public int getDjtfeedoflag() {
			return djtfeedoflag;
		}

		public void setDjtfeedoflag(int djtfeedoflag) {
			this.djtfeedoflag = djtfeedoflag;
		}
		
		
		public String getIs_show_shenqingdan() {
			return is_show_shenqingdan;
		}

		public void setIs_show_shenqingdan(String is_show_shenqingdan) {
			this.is_show_shenqingdan = is_show_shenqingdan;
		}

		public String getIs_show_select_shenqingdan() {
			return is_show_select_shenqingdan;
		}

		public void setIs_show_select_shenqingdan(String is_show_select_shenqingdan) {
			this.is_show_select_shenqingdan = is_show_select_shenqingdan;
		}

		public String getIs_show_paiduihao() {
			return is_show_paiduihao;
		}

		public void setIs_show_paiduihao(String is_show_paiduihao) {
			this.is_show_paiduihao = is_show_paiduihao;
		}

		public String getIs_show_daojiandan_shengqingdan() {
			return is_show_daojiandan_shengqingdan;
		}

		public void setIs_show_daojiandan_shengqingdan(String is_show_daojiandan_shengqingdan) {
			this.is_show_daojiandan_shengqingdan = is_show_daojiandan_shengqingdan;
		}

		public String getIs_print_barcode_item_pay() {
			return is_print_barcode_item_pay;
		}

		public void setIs_print_barcode_item_pay(String is_print_barcode_item_pay) {
			this.is_print_barcode_item_pay = is_print_barcode_item_pay;
		}

		public String getTi_name() {
			return ti_name;
		}

		public void setTi_name(String ti_name) {
			this.ti_name = ti_name;
		}

		public String getTi_sex() {
			return ti_sex;
		}

		public void setTi_sex(String ti_sex) {
			this.ti_sex = ti_sex;
		}

		public int getTi_age() {
			return ti_age;
		}

		public void setTi_age(int ti_age) {
			this.ti_age = ti_age;
		}

		public String getTi_id_num() {
			return ti_id_num;
		}

		public void setTi_id_num(String ti_id_num) {
			this.ti_id_num = ti_id_num;
		}

		public String getZiqu_report_time() {
			return ziqu_report_time;
		}

		public void setZiqu_report_time(String ziqu_report_time) {
			this.ziqu_report_time = ziqu_report_time;
		}
		
		public String getIs_default_ten() {
			return is_default_ten;
		}

		public void setIs_default_ten(String is_default_ten) {
			this.is_default_ten = is_default_ten;
		}
		
		
		public String getMembership_card() {
			return membership_card;
		}

		public void setMembership_card(String membership_card) {
			this.membership_card = membership_card;
		}

		public String getGetReportWay() {
			return getReportWay;
		}

		public void setGetReportWay(String getReportWay) {
			this.getReportWay = getReportWay;
		}

		public String getDegreeOfedu() {
			return degreeOfedu;
		}

		public void setDegreeOfedu(String degreeOfedu) {
			this.degreeOfedu = degreeOfedu;
		}

		public int getPolitical_status() {
			return political_status;
		}

		public void setPolitical_status(int political_status) {
			this.political_status = political_status;
		}

		public String getLevels() {
			return levels;
		}

		public void setLevels(String levels) {
			this.levels = levels;
		}

		public String getBodao_checkebox() {
			return bodao_checkebox;
		}

		public void setBodao_checkebox(String bodao_checkebox) {
			this.bodao_checkebox = bodao_checkebox;
		}

		public String getData_source() {
			return data_source;
		}

		public void setData_source(String data_source) {
			this.data_source = data_source;
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

		public String getData_name() {
			return data_name;
		}

		public void setData_name(String data_name) {
			this.data_name = data_name;
		}

		public String getCopy_status() {
			return copy_status;
		}

		public void setCopy_status(String copy_status) {
			this.copy_status = copy_status;
		}

		public String getDjdstatuss() {
			return djdstatuss;
		}

		public void setDjdstatuss(String djdstatuss) {
			this.djdstatuss = djdstatuss;
		}

		public String getPhone() {
			return phone;
		}

		public void setPhone(String phone) {
			this.phone = phone;
		}

		public String getOrderid() {
			return orderid;
		}

		public void setOrderid(String orderid) {
			this.orderid = orderid;
		}

		public int getRen_type() {
			return ren_type;
		}

		public void setRen_type(int ren_type) {
			this.ren_type = ren_type;
		}

		public String getBunk() {
			return bunk;
		}

		public void setBunk(String bunk) {
			this.bunk = bunk;
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

		public String getZywhlb() {
			return zywhlb;
		}

		public void setZywhlb(String zywhlb) {
			this.zywhlb = zywhlb;
		}

		public String getZywhyslb() {
			return zywhyslb;
		}

		public void setZywhyslb(String zywhyslb) {
			this.zywhyslb = zywhyslb;
		}

		public long getExamset_id() {
			return examset_id;
		}

		public void setExamset_id(long examset_id) {
			this.examset_id = examset_id;
		}

		public String getHarmname() {
			return harmname;
		}

		public void setHarmname(String harmname) {
			this.harmname = harmname;
		}

		public String getConcentrations() {
			return concentrations;
		}

		public void setConcentrations(String concentrations) {
			this.concentrations = concentrations;
		}

		public String getMeasure() {
			return measure;
		}

		public void setMeasure(String measure) {
			this.measure = measure;
		}

		public String getIsradiation() {
			return isradiation;
		}

		public void setIsradiation(String isradiation) {
			this.isradiation = isradiation;
		}

		public String getZytjlb() {
			return zytjlb;
		}

		public void setZytjlb(String zytjlb) {
			this.zytjlb = zytjlb;
		}

		public String getApp_type() {
			return app_type;
		}

		public void setApp_type(String app_type) {
			this.app_type = app_type;
		}

		public String getReexam_type() {
			return reexam_type;
		}

		public void setReexam_type(String reexam_type) {
			this.reexam_type = reexam_type;
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

		public String getReceive_name() {
			return receive_name;
		}

		public String getReceive_phone() {
			return receive_phone;
		}

		public String getReceive_address() {
			return receive_address;
		}

		public String getReceive_postcode() {
			return receive_postcode;
		}

		public String getReceive_remark() {
			return receive_remark;
		}

		public void setReceive_type(String receive_type) {
			this.receive_type = receive_type;
		}

		public void setReceive_name(String receive_name) {
			this.receive_name = receive_name;
		}

		public void setReceive_phone(String receive_phone) {
			this.receive_phone = receive_phone;
		}

		public void setReceive_address(String receive_address) {
			this.receive_address = receive_address;
		}

		public void setReceive_postcode(String receive_postcode) {
			this.receive_postcode = receive_postcode;
		}

		public void setReceive_remark(String receive_remark) {
			this.receive_remark = receive_remark;
		}

		public String getReport_url() {
			return report_url;
		}

		public void setReport_url(String report_url) {
			this.report_url = report_url;
		}

		public String getUpdate_time() {
			return update_time;
		}

		public void setUpdate_time(String update_time) {
			this.update_time = update_time;
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

		public long getContralcustflag() {
			return contralcustflag;
		}

		public void setContralcustflag(long contralcustflag) {
			this.contralcustflag = contralcustflag;
		}

		public String getExam_indicator() {
			return exam_indicator;
		}

		public void setExam_indicator(String exam_indicator) {
			this.exam_indicator = exam_indicator;
		}

		private String patient_id="";
	    
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

		public String getSetimes() {
			return setimes;
		}

		public void setSetimes(String setimes) {
			this.setimes = setimes;
		}

		public String getCustomerType() {
			return customerType;
		}

		public void setCustomerType(String customerType) {
			this.customerType = customerType;
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

		public long getDingweitype() {
			return dingweitype;
		}

		public void setDingweitype(long dingweitype) {
			this.dingweitype = dingweitype;
		}

		public String getSetentities() {
			return setentities;
		}

		public void setSetentities(String setentities) {
			this.setentities = setentities;
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

		public String getChkItem() {
			return chkItem;
		}

		public void setChkItem(String chkItem) {
			this.chkItem = chkItem;
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

		public String getBarcode_print_type() {
			return barcode_print_type;
		}

		public void setBarcode_print_type(String barcode_print_type) {
			this.barcode_print_type = barcode_print_type;
		}

		public String getZyb_barcode_print_type() {
			return zyb_barcode_print_type;
		}

		public void setZyb_barcode_print_type(String zyb_barcode_print_type) {
			this.zyb_barcode_print_type = zyb_barcode_print_type;
		}

		public String getBudget_amount() {
			return budget_amount;
		}

		public void setBudget_amount(String budget_amount) {
			this.budget_amount = budget_amount;
		}	

		public String getIntroducer() {
			return introducer;
		}

		public void setIntroducer(String introducer) {
			this.introducer = introducer;
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

		public long getSet_id() {
			return set_id;
		}

		public void setSet_id(long set_id) {
			this.set_id = set_id;
		}

		public int getZyb_set_source() {
			return zyb_set_source;
		}

		public void setZyb_set_source(int zyb_set_source) {
			this.zyb_set_source = zyb_set_source;
		}

		public String getCustomer_type_special() {
			return customer_type_special;
		}

		public void setCustomer_type_special(String customer_type_special) {
			this.customer_type_special = customer_type_special;
		}

		public String getCustomer_type_special_color() {
			return customer_type_special_color;
		}

		public void setCustomer_type_special_color(String customer_type_special_color) {
			this.customer_type_special_color = customer_type_special_color;
		}

		public String getFreeze_color() {
			return freeze_color;
		}

		public void setFreeze_color(String freeze_color) {
			this.freeze_color = freeze_color;
		}

		public String get_level_name() {
			return _level_name;
		}

		public void set_level_name(String _level_name) {
			this._level_name = _level_name;
		}

		public int getWuxuzongjian() {
			return wuxuzongjian;
		}

		public void setWuxuzongjian(int wuxuzongjian) {
			this.wuxuzongjian = wuxuzongjian;
		}

		public String getIs_custom_identification() {
			return is_custom_identification;
		}

		public void setIs_custom_identification(String is_custom_identification) {
			this.is_custom_identification = is_custom_identification;
		}

		public String getIs_chargingway_zero() {
			return is_chargingway_zero;
		}

		public void setIs_chargingway_zero(String is_chargingway_zero) {
			this.is_chargingway_zero = is_chargingway_zero;
		}

		public String getCard_num() {
			return card_num;
		}

		public void setCard_num(String card_num) {
			this.card_num = card_num;
		}

		public String getDisease_id() {
			return disease_id;
		}

		public void setDisease_id(String disease_id) {
			this.disease_id = disease_id;
		}

		public String getIsphone() {
			return isphone;
		}

		public void setIsphone(String isphone) {
			this.isphone = isphone;
		}

		public String getSms_status() {
			return sms_status;
		}

		public void setSms_status(String sms_status) {
			this.sms_status = sms_status;
		}

		public String getOldexam_num() {
			return oldexam_num;
		}

		public void setOldexam_num(String oldexam_num) {
			this.oldexam_num = oldexam_num;
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

		public int getGtjf() {
			return gtjf;
		}

		public void setGtjf(int gtjf) {
			this.gtjf = gtjf;
		}

		public int getTtog_exam() {
			return ttog_exam;
		}

		public void setTtog_exam(int ttog_exam) {
			this.ttog_exam = ttog_exam;
		}

		public int getGtot_exam() {
			return gtot_exam;
		}

		public void setGtot_exam(int gtot_exam) {
			this.gtot_exam = gtot_exam;
		}

		public int getGtjf_exam() {
			return gtjf_exam;
		}

		public void setGtjf_exam(int gtjf_exam) {
			this.gtjf_exam = gtjf_exam;
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

		public String getReport_print_type() {
			return report_print_type;
		}

		public String getZyb_report_print_type() {
			return zyb_report_print_type;
		}

		public void setReport_print_type(String report_print_type) {
			this.report_print_type = report_print_type;
		}

		public void setZyb_report_print_type(String zyb_report_print_type) {
			this.zyb_report_print_type = zyb_report_print_type;
		}

		public String getTypeofwork_name() {
			return typeofwork_name;
		}

		public void setTypeofwork_name(String typeofwork_name) {
			this.typeofwork_name = typeofwork_name;
		}
}