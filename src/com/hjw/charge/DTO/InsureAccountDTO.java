package com.hjw.charge.DTO;
/* 医保收费记录表*/

public class InsureAccountDTO {
	private long  id;
	private String  flag;    //	 ----操作成功标志	number(1)	y	（0-失败 1-成功）
	private String  cause;    //  ----失败原因说明	varchar2(500)	n	
	private String  sterilisation_req_num;   //冲销单据号
	private String  medical_req_num;    //   ----医保流水号	varchar2(20)	y	医保门诊挂号流水号/住院登记流水号
	private String  medical_charge_req_num;    //	varchar;    //(20)  NOT NULL,   ----医保收费流水号	      varchar2(20)	y	
	private String  his_req_num;    //  ---- varchar2(50)	y	收费流水号
	private String  medical_treatment_mode;    //    ---- varchar2(3)	y	医疗就诊方式 参见编码附件
	private String  medical_category;    //  ----varchar2(3)	y 	医疗类别参见编码附件
	private String  pat_no;    //	  ---- 社会保障卡号	varchar2(20)	y	
	private String  management_code;    //	varchar;    //(20),      ----个人管理码	varchar2(20)	n	地市唯一标识码（id0000）
	private String  name;    //	varchar;    //(50)    NOT NULL,    ----姓名	varchar2(50)	y	 
	private String  sex;    //	varchar;    //(3)     NOT NULL,    ----性别	varchar2(3)	y	
	private String  sex_name;    //	  ---- 性别名称	varchar2(10)	y	
	private String  date_birth;    //     ----出生日期	number(8)	y	
	private String  diseases_coding;    //	varchar;    //(20),      ----病种编码	varchar2(16)	n	
	private String  treatment_personnel;    //	varchar;    //(3),   ----   人员待遇类别	varchar2(3)	n	
	private String  treatment_personnel_name;    //	varchar;    //(20) ,   ----  人员待遇类别名称	varchar2(30)	n	
	private String  treatment_medicall;    //	varchar;    //(3)    NOT NULL,    ---- 医疗待遇状态	varchar2(3)	y	参见编码附件
	private String  treatment_medical_name;    //	varchar;    //(30)    NOT NULL,    ---- 医疗待遇状态名称	varchar2(30)	y	
	private String  site_medical_type;    //	varchar;    //(3),   ----   异地就医类型	varchar2(3)	n	
	private String  site_medical_type_name;    //	varchar;    //(30),   ----   异地就医类型名称	varchar2(30)	n	
	private String  administrative_divisions;    //	varchar;    //(6),   ----   参保地行政区划	varchar2(6)	n	 
	private double  overall_payment_standard;    //	numeric;    //(12, 4),   ----统筹支付医保费用起付标准	number(12,4)	n	
	private double  amount_medical;    //	  ----医疗费总金额	number(12,4)	y	医疗费总金额=个人现金支付金额+个人账户支付金额+基金支付总额
	private double  personal_cash;    //	   ----个人现金支付金额（实付现金）	number(12,4)	y	发票显示金额
	private double  individual_account;    //	   ----个人账户支付金额	number(12,4)	y	发票显示金额
	private double  fund;    //		numeric;    //(12, 4),   ----基金支付总额	number(12,4)	y	发票显示金额 医保基金支付总额=统筹基金支付+商保基金支付+公务员医疗补助+精准扶贫医疗叠加+医疗救助基金+其他基金支付+企业补充
	private double  whole_fund;    //		 ----其中：统筹基金支付	number(12,4)	n	
	private double  commercial_fund;    //	  ----其中：商保基金支付（大额补充）	number(12,4)	n	大病基金
	private double  civil_servants;    //	   ----其中：公务员医疗补助	number(12,4)	n	明细见“补助明细”列表（以下类同）bkc059=bkc059_1+bkc059_2+bkc059_3
	private double  poverty_alleviation; //   ----其中：精准扶贫医疗叠加	number(12,4)	n	明细见“补助明细”列表
	private double  bailout_fund;    //		  ----其中：医疗救助基金	number(12,4)	n	明细见“补助明细”列表
	private double  other_fund;    //		   ----其中：其他基金支付	number(12,4)	n	
	private double  enterprise_complement;    //	   ----其中：企业补充	number(12,4)	n	
	private double  family_account;    //		numeric;     ----家庭共济账户支付	number(12,4)	y	通用
	private String  deductionser;    //	varchar;    //(40)      NOT NULL,   ----(家庭健康)共济账户扣款人	varchar2(40)	y	(例:张三;李四;)
	private String  deductions_amount;    //	 ----(家庭健康)共济账户扣款人金额	varchar2(40)	y	(例:100;200;)
	private double  general_amount_compensation;    //   ----（其中）一般诊疗费补偿金额	number(12,4)	y	
	private double  general_personal_amount;    //		 ----（其中）一般诊疗费个人支付金额	number(12,4)	y	
	private double  individual_pocket;    //----个人自费（非医保费用）	number(12,4)	y	
	private double  personal_account_balance;    //	 ----	个人账户余额	number(16,2)	y	通用
	private double  family_balance;    // ----家庭共济账户余额	number(16,2)	n	通用
	private String  number_hospita;    //	varchar;    //(3)   NOT NULL,   ----本年度住院次数	number(3)	y	
	private String  actual_visit_date;    //	  ---- 实际就诊日期	number(8)	y	门诊为实际就诊日期，住院为实际出院日期(或中途结算日期)
	private String  actual_time_visit;    //	----实际就诊时间	number(6)	n	住院指病人实际出院时间
	private String  settlement_date;    //  ----费用发生日期（结算日期）	number(8)	y	医保系统结算时间
	private String  clearing_time;    //	   ----费用发生时间（结算时间）	number(6)	y	医保系统结算时间
	private String  collector;    //	  ----  收费人	varchar2(50)	n	
	private String  surgical_category;    //   ----   计划生育手术类别	varchar2(3)	n	
	private String  fertility_category;    //  ----   生育类别	varchar2(3)	n	
	private String  fetus_number;    //	  ---- 胎儿数	number(3)	n	
	private String  family_planning;    //	---- 计划生育手术或生育日期	number(8)	n	
	private String  days_pregnancy;    //	 ---- 怀孕天数	number(3)	n	
	private String  disease_settlement;    //   NOT NULL,   ----是否进入单病种结算  	varchar（2）	y	Y-是  N-否
	private String  subsidies_indicators;    //	  NOT NULL,   ---- 补助指标	varchar2(8)	y	参见补助编码表
	private String  subsidies_indicators_name;    // ----补助指标名称	varchar2(50)	y	各地补助名称
	private double  subsidies_amount;    //	----补助金额	number(12,4)	y	
	private String  invoice_category;    //   ----发票项目类别	varchar2(3)	y	参见编码附件
	private String  invoice_category_name;    //   ---- 	发票项目类别名称	varchar2(50)	y	
	private double  invoice_cost;    //	numeric;    //(12, 4),   ---- 发票项目费用                                   	number(12,4)	y	
	private double  medical_invoice_cost;    //	  ----医保发票费用             	number(12,4)	y	(完全进入三段的费用)
	private double  medical_personal_cost;    //	 ----医保个人费用             	number(12,4)	y	(乙类中个人支付费用)
	private double  special_invoice_cost;    //  ----特殊项目发票费用	number(12,4)	y	(门诊三段费用或门诊基金费用)
	private double  non_invoice_cost;    //	  ----非医保发票费用            	number(12,4

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String getCause() {
		return cause;
	}
	public void setCause(String cause) {
		this.cause = cause;
	}
	public String getSterilisation_req_num() {
		return sterilisation_req_num;
	}
	public void setSterilisation_req_num(String sterilisation_req_num) {
		this.sterilisation_req_num = sterilisation_req_num;
	}
	public String getMedical_charge_req_num() {
		return medical_charge_req_num;
	}
	public void setMedical_charge_req_num(String medical_charge_req_num) {
		this.medical_charge_req_num = medical_charge_req_num;
	}
	public String getMedical_req_num() {
		return medical_req_num;
	}
	public void setMedical_req_num(String medical_req_num) {
		this.medical_req_num = medical_req_num;
	}
	public String getHis_req_num() {
		return his_req_num;
	}
	public void setHis_req_num(String his_req_num) {
		this.his_req_num = his_req_num;
	}
	public String getMedical_treatment_mode() {
		return medical_treatment_mode;
	}
	public void setMedical_treatment_mode(String medical_treatment_mode) {
		this.medical_treatment_mode = medical_treatment_mode;
	}
	public String getMedical_category() {
		return medical_category;
	}
	public void setMedical_category(String medical_category) {
		this.medical_category = medical_category;
	}
	public String getPat_no() {
		return pat_no;
	}
	public void setPat_no(String pat_no) {
		this.pat_no = pat_no;
	}
	public String getManagement_code() {
		return management_code;
	}
	public void setManagement_code(String management_code) {
		this.management_code = management_code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getSex_name() {
		return sex_name;
	}
	public void setSex_name(String sex_name) {
		this.sex_name = sex_name;
	}
	public String getDate_birth() {
		return date_birth;
	}
	public void setDate_birth(String date_birth) {
		this.date_birth = date_birth;
	}
	public String getDiseases_coding() {
		return diseases_coding;
	}
	public void setDiseases_coding(String diseases_coding) {
		this.diseases_coding = diseases_coding;
	}
	public String getTreatment_personnel() {
		return treatment_personnel;
	}
	public void setTreatment_personnel(String treatment_personnel) {
		this.treatment_personnel = treatment_personnel;
	}
	public String getTreatment_personnel_name() {
		return treatment_personnel_name;
	}
	public void setTreatment_personnel_name(String treatment_personnel_name) {
		this.treatment_personnel_name = treatment_personnel_name;
	}
	public String getTreatment_medicall() {
		return treatment_medicall;
	}
	public void setTreatment_medicall(String treatment_medicall) {
		this.treatment_medicall = treatment_medicall;
	}
	public String getTreatment_medical_name() {
		return treatment_medical_name;
	}
	public void setTreatment_medical_name(String treatment_medical_name) {
		this.treatment_medical_name = treatment_medical_name;
	}
	public String getSite_medical_type() {
		return site_medical_type;
	}
	public void setSite_medical_type(String site_medical_type) {
		this.site_medical_type = site_medical_type;
	}
	public String getSite_medical_type_name() {
		return site_medical_type_name;
	}
	public void setSite_medical_type_name(String site_medical_type_name) {
		this.site_medical_type_name = site_medical_type_name;
	}
	public String getAdministrative_divisions() {
		return administrative_divisions;
	}
	public void setAdministrative_divisions(String administrative_divisions) {
		this.administrative_divisions = administrative_divisions;
	}
	public double getOverall_payment_standard() {
		return overall_payment_standard;
	}
	public void setOverall_payment_standard(double overall_payment_standard) {
		this.overall_payment_standard = overall_payment_standard;
	}
	public double getAmount_medical() {
		return amount_medical;
	}
	public void setAmount_medical(double amount_medical) {
		this.amount_medical = amount_medical;
	}
	public double getPersonal_cash() {
		return personal_cash;
	}
	public void setPersonal_cash(double personal_cash) {
		this.personal_cash = personal_cash;
	}
	public double getIndividual_account() {
		return individual_account;
	}
	public void setIndividual_account(double individual_account) {
		this.individual_account = individual_account;
	}
	public double getFund() {
		return fund;
	}
	public void setFund(double fund) {
		this.fund = fund;
	}
	public double getWhole_fund() {
		return whole_fund;
	}
	public void setWhole_fund(double whole_fund) {
		this.whole_fund = whole_fund;
	}
	public double getCommercial_fund() {
		return commercial_fund;
	}
	public void setCommercial_fund(double commercial_fund) {
		this.commercial_fund = commercial_fund;
	}
	public double getCivil_servants() {
		return civil_servants;
	}
	public void setCivil_servants(double civil_servants) {
		this.civil_servants = civil_servants;
	}
	public double getPoverty_alleviation() {
		return poverty_alleviation;
	}
	public void setPoverty_alleviation(double poverty_alleviation) {
		this.poverty_alleviation = poverty_alleviation;
	}
	public double getBailout_fund() {
		return bailout_fund;
	}
	public void setBailout_fund(double bailout_fund) {
		this.bailout_fund = bailout_fund;
	}
	public double getOther_fund() {
		return other_fund;
	}
	public void setOther_fund(double other_fund) {
		this.other_fund = other_fund;
	}
	public double getEnterprise_complement() {
		return enterprise_complement;
	}
	public void setEnterprise_complement(double enterprise_complement) {
		this.enterprise_complement = enterprise_complement;
	}
	public double getFamily_account() {
		return family_account;
	}
	public void setFamily_account(double family_account) {
		this.family_account = family_account;
	}
	public String getDeductionser() {
		return deductionser;
	}
	public void setDeductionser(String deductionser) {
		this.deductionser = deductionser;
	}
	public String getDeductions_amount() {
		return deductions_amount;
	}
	public void setDeductions_amount(String deductions_amount) {
		this.deductions_amount = deductions_amount;
	}
	public double getGeneral_amount_compensation() {
		return general_amount_compensation;
	}
	public void setGeneral_amount_compensation(double general_amount_compensation) {
		this.general_amount_compensation = general_amount_compensation;
	}
	public double getGeneral_personal_amount() {
		return general_personal_amount;
	}
	public void setGeneral_personal_amount(double general_personal_amount) {
		this.general_personal_amount = general_personal_amount;
	}
	public double getIndividual_pocket() {
		return individual_pocket;
	}
	public void setIndividual_pocket(double individual_pocket) {
		this.individual_pocket = individual_pocket;
	}
	public double getPersonal_account_balance() {
		return personal_account_balance;
	}
	public void setPersonal_account_balance(double personal_account_balance) {
		this.personal_account_balance = personal_account_balance;
	}
	public double getFamily_balance() {
		return family_balance;
	}
	public void setFamily_balance(double family_balance) {
		this.family_balance = family_balance;
	}
	public String getNumber_hospita() {
		return number_hospita;
	}
	public void setNumber_hospita(String number_hospita) {
		this.number_hospita = number_hospita;
	}
	public String getActual_visit_date() {
		return actual_visit_date;
	}
	public void setActual_visit_date(String actual_visit_date) {
		this.actual_visit_date = actual_visit_date;
	}
	public String getActual_time_visit() {
		return actual_time_visit;
	}
	public void setActual_time_visit(String actual_time_visit) {
		this.actual_time_visit = actual_time_visit;
	}
	public String getSettlement_date() {
		return settlement_date;
	}
	public void setSettlement_date(String settlement_date) {
		this.settlement_date = settlement_date;
	}
	public String getClearing_time() {
		return clearing_time;
	}
	public void setClearing_time(String clearing_time) {
		this.clearing_time = clearing_time;
	}
	public String getCollector() {
		return collector;
	}
	public void setCollector(String collector) {
		this.collector = collector;
	}
	public String getSurgical_category() {
		return surgical_category;
	}
	public void setSurgical_category(String surgical_category) {
		this.surgical_category = surgical_category;
	}
	public String getFertility_category() {
		return fertility_category;
	}
	public void setFertility_category(String fertility_category) {
		this.fertility_category = fertility_category;
	}
	public String getFetus_number() {
		return fetus_number;
	}
	public void setFetus_number(String fetus_number) {
		this.fetus_number = fetus_number;
	}
	public String getFamily_planning() {
		return family_planning;
	}
	public void setFamily_planning(String family_planning) {
		this.family_planning = family_planning;
	}
	public String getDays_pregnancy() {
		return days_pregnancy;
	}
	public void setDays_pregnancy(String days_pregnancy) {
		this.days_pregnancy = days_pregnancy;
	}
	public String getDisease_settlement() {
		return disease_settlement;
	}
	public void setDisease_settlement(String disease_settlement) {
		this.disease_settlement = disease_settlement;
	}
	public String getSubsidies_indicators() {
		return subsidies_indicators;
	}
	public void setSubsidies_indicators(String subsidies_indicators) {
		this.subsidies_indicators = subsidies_indicators;
	}
	public String getSubsidies_indicators_name() {
		return subsidies_indicators_name;
	}
	public void setSubsidies_indicators_name(String subsidies_indicators_name) {
		this.subsidies_indicators_name = subsidies_indicators_name;
	}
	public double getSubsidies_amount() {
		return subsidies_amount;
	}
	public void setSubsidies_amount(double subsidies_amount) {
		this.subsidies_amount = subsidies_amount;
	}
	public String getInvoice_category() {
		return invoice_category;
	}
	public void setInvoice_category(String invoice_category) {
		this.invoice_category = invoice_category;
	}
	public String getInvoice_category_name() {
		return invoice_category_name;
	}
	public void setInvoice_category_name(String invoice_category_name) {
		this.invoice_category_name = invoice_category_name;
	}
	public double getInvoice_cost() {
		return invoice_cost;
	}
	public void setInvoice_cost(double invoice_cost) {
		this.invoice_cost = invoice_cost;
	}
	public double getMedical_invoice_cost() {
		return medical_invoice_cost;
	}
	public void setMedical_invoice_cost(double medical_invoice_cost) {
		this.medical_invoice_cost = medical_invoice_cost;
	}
	public double getMedical_personal_cost() {
		return medical_personal_cost;
	}
	public void setMedical_personal_cost(double medical_personal_cost) {
		this.medical_personal_cost = medical_personal_cost;
	}
	public double getSpecial_invoice_cost() {
		return special_invoice_cost;
	}
	public void setSpecial_invoice_cost(double special_invoice_cost) {
		this.special_invoice_cost = special_invoice_cost;
	}
	public double getNon_invoice_cost() {
		return non_invoice_cost;
	}
	public void setNon_invoice_cost(double non_invoice_cost) {
		this.non_invoice_cost = non_invoice_cost;
	}
	
}
