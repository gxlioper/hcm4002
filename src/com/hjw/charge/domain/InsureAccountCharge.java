package com.hjw.charge.domain;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "insure_account")
public class InsureAccountCharge implements java.io.Serializable{

	private static final long serialVersionUID = -97502163798576023L;

	@Id
	@GenericGenerator(name = "idGenerator", strategy = "identity")
	@GeneratedValue(generator = "idGenerator")
	private long id;
	@Column(name="flag")
	private String  flag;    //	 ----操作成功标志	number(1)	y	（0-失败 1-成功）
	@Column(name="cause")
	private String  cause;    //  ----失败原因说明	varchar2(500)	n	
	@Column(name="medical_type")
	private String  medical_type;    //  ----收费状态
	@Column(name="center_num")
	private String  center_num;    //  ----收费状态
	@Column(name="sterilisation_req_num")
	private String  sterilisation_req_num;   //冲销单据号
	@Column(name="medical_req_num")
	private String  medical_req_num;    //   ----医保流水号	varchar2(20)	y	医保门诊挂号流水号/住院登记流水号
	@Column(name="medical_charge_req_num")
	private String  medical_charge_req_num;    //	varchar;    //(20)  NOT NULL,   ----医保收费流水号	      varchar2(20)	y	
	@Column(name="his_req_num")
	private String  his_req_num;    //  ---- varchar2(50)	y	收费流水号
	@Column(name="medical_treatment_mode")
	private String  medical_treatment_mode;    //    ---- varchar2(3)	y	医疗就诊方式 参见编码附件
	@Column(name="medical_category")
	private String  medical_category;    //  ----varchar2(3)	y 	医疗类别参见编码附件
	@Column(name="pat_no")
	private String  pat_no;    //	  ---- 社会保障卡号	varchar2(20)	y	
	@Column(name="management_code")
	private String  management_code;    //	varchar;    //(20),      ----个人管理码	varchar2(20)	n	地市唯一标识码（id0000）
	@Column(name="name")
	private String  name;    //	varchar;    //(50)    NOT NULL,    ----姓名	varchar2(50)	y	 
	@Column(name="sex")
	private String  sex;    //	varchar;    //(3)     NOT NULL,    ----性别	varchar2(3)	y	
	@Column(name="sex_name")
	private String  sex_name;    //	  ---- 性别名称	varchar2(10)	y	
	@Column(name="date_birth")
	private String  date_birth;    //     ----出生日期	number(8)	y	
	@Column(name="diseases_coding")
	private String  diseases_coding;    //	varchar;    //(20),      ----病种编码	varchar2(16)	n	
	@Column(name="treatment_personnel")
	private String  treatment_personnel;    //	varchar;    //(3),   ----   人员待遇类别	varchar2(3)	n	
	@Column(name="treatment_personnel_name")
	private String  treatment_personnel_name;    //	varchar;    //(20) ,   ----  人员待遇类别名称	varchar2(30)	n	
	@Column(name="treatment_medicall")
	private String  treatment_medicall;    //	varchar;    //(3)    NOT NULL,    ---- 医疗待遇状态	varchar2(3)	y	参见编码附件
	@Column(name="treatment_medical_name")
	private String  treatment_medical_name;    //	varchar;    //(30)    NOT NULL,    ---- 医疗待遇状态名称	varchar2(30)	y	
	@Column(name="site_medical_type")
	private String  site_medical_type;    //	varchar;    //(3),   ----   异地就医类型	varchar2(3)	n	
	@Column(name="site_medical_type_name")
	private String  site_medical_type_name;    //	varchar;    //(30),   ----   异地就医类型名称	varchar2(30)	n	
	@Column(name="administrative_divisions")
	private String  administrative_divisions;    //	varchar;    //(6),   ----   参保地行政区划	varchar2(6)	n	 
	@Column(name="overall_payment_standard")
	private double  overall_payment_standard;    //	numeric;    //(12, 4),   ----统筹支付医保费用起付标准	number(12,4)	n	
	@Column(name="amount_medical")
	private double  amount_medical;    //	  ----医疗费总金额	number(12,4)	y	医疗费总金额=个人现金支付金额+个人账户支付金额+基金支付总额
	@Column(name="personal_cash")
	private double  personal_cash;    //	   ----个人现金支付金额（实付现金）	number(12,4)	y	发票显示金额
	@Column(name="individual_account")
	private double  individual_account;    //	   ----个人账户支付金额	number(12,4)	y	发票显示金额
	@Column(name="fund")
	private double  fund;    //		numeric;    //(12, 4),   ----基金支付总额	number(12,4)	y	发票显示金额 医保基金支付总额=统筹基金支付+商保基金支付+公务员医疗补助+精准扶贫医疗叠加+医疗救助基金+其他基金支付+企业补充
	@Column(name="whole_fund")
	private double  whole_fund;    //		 ----其中：统筹基金支付	number(12,4)	n	
	@Column(name="commercial_fund")
	private double  commercial_fund;    //	  ----其中：商保基金支付（大额补充）	number(12,4)	n	大病基金
	@Column(name="civil_servants")
	private double  civil_servants;    //	   ----其中：公务员医疗补助	number(12,4)	n	明细见“补助明细”列表（以下类同）bkc059=bkc059_1+bkc059_2+bkc059_3
	@Column(name="poverty_alleviation")
	private double  poverty_alleviation; //   ----其中：精准扶贫医疗叠加	number(12,4)	n	明细见“补助明细”列表
	@Column(name="bailout_fund")
	private double  bailout_fund;    //		  ----其中：医疗救助基金	number(12,4)	n	明细见“补助明细”列表
	@Column(name="other_fund")
	private double  other_fund;    //		   ----其中：其他基金支付	number(12,4)	n	
	@Column(name="enterprise_complement")
	private double  enterprise_complement;    //	   ----其中：企业补充	number(12,4)	n	
	@Column(name="family_account")
	private double  family_account;    //		numeric;     ----家庭共济账户支付	number(12,4)	y	通用
	@Column(name="deductionser")
	private String  deductionser;    //	varchar;    //(40)      NOT NULL,   ----(家庭健康)共济账户扣款人	varchar2(40)	y	(例:张三;李四;)
	@Column(name="deductions_amount")
	private String  deductions_amount;    //	 ----(家庭健康)共济账户扣款人金额	varchar2(40)	y	(例:100;200;)
	@Column(name="general_amount_compensation")
	private double  general_amount_compensation;    //   ----（其中）一般诊疗费补偿金额	number(12,4)	y	
	@Column(name="general_personal_amount")
	private double  general_personal_amount;    //		 ----（其中）一般诊疗费个人支付金额	number(12,4)	y	
	@Column(name="individual_pocket")
	private double  individual_pocket;    //----个人自费（非医保费用）	number(12,4)	y	
	@Column(name="personal_account_balance")
	private double  personal_account_balance;    //	 ----	个人账户余额	number(16,2)	y	通用
	@Column(name="family_balance")
	private double  family_balance;    // ----家庭共济账户余额	number(16,2)	n	通用
	@Column(name="number_hospita")
	private String  number_hospita;    //	varchar;    //(3)   NOT NULL,   ----本年度住院次数	number(3)	y	
	@Column(name="actual_visit_date")
	private String  actual_visit_date;    //	  ---- 实际就诊日期	number(8)	y	门诊为实际就诊日期，住院为实际出院日期(或中途结算日期)
	@Column(name="actual_time_visit")
	private String  actual_time_visit;    //	----实际就诊时间	number(6)	n	住院指病人实际出院时间
	@Column(name="settlement_date")
	private String  settlement_date;    //  ----费用发生日期（结算日期）	number(8)	y	医保系统结算时间
	@Column(name="clearing_time")
	private String  clearing_time;    //	   ----费用发生时间（结算时间）	number(6)	y	医保系统结算时间
	@Column(name="collector")
	private String  collector;    //	  ----  收费人	varchar2(50)	n	
	@Column(name="surgical_category")
	private String  surgical_category;    //   ----   计划生育手术类别	varchar2(3)	n	
	@Column(name="fertility_category")
	private String  fertility_category;    //  ----   生育类别	varchar2(3)	n	
	@Column(name="fetus_number")
	private String  fetus_number;    //	  ---- 胎儿数	number(3)	n	
	@Column(name="family_planning")
	private String  family_planning;    //	---- 计划生育手术或生育日期	number(8)	n	
	@Column(name="days_pregnancy")
	private String  days_pregnancy;    //	 ---- 怀孕天数	number(3)	n	
	@Column(name="disease_settlement")
	private String  disease_settlement;    //   NOT NULL,   ----是否进入单病种结算  	varchar（2）	y	Y-是  N-否
	@Column(name="creater")
	private long creater;
	@Column(name="create_time")
	private Date create_time;
//	@Column(name="subsidies_indicators")
//	private String  subsidies_indicators;    //	  NOT NULL,   ---- 补助指标	varchar2(8)	y	参见补助编码表
//	@Column(name="subsidies_indicators_name")
//	private String  subsidies_indicators_name;    // ----补助指标名称	varchar2(50)	y	各地补助名称
//	@Column(name="subsidies_amount")
//	private double  subsidies_amount;    //	----补助金额	number(12,4)	y	
//	@Column(name="invoice_category")
//	private String  invoice_category;    //   ----发票项目类别	varchar2(3)	y	参见编码附件
//	@Column(name="invoice_category_name")
//	private String  invoice_category_name;    //   ---- 	发票项目类别名称	varchar2(50)	y	
//	@Column(name="invoice_cost")
//	private double  invoice_cost;    //	numeric;    //(12, 4),   ---- 发票项目费用                                   	number(12,4)	y	
//	@Column(name="medical_invoice_cost")
//	private double  medical_invoice_cost;    //	  ----医保发票费用             	number(12,4)	y	(完全进入三段的费用)
//	@Column(name="medical_personal_cost")
//	private double  medical_personal_cost;    //	 ----医保个人费用             	number(12,4)	y	(乙类中个人支付费用)
//	@Column(name="special_invoice_cost")
//	private double  special_invoice_cost;    //  ----特殊项目发票费用	number(12,4)	y	(门诊三段费用或门诊基金费用)
//	@Column(name="non_invoice_cost")
//	private double  non_invoice_cost;    //	  ----非医保发票费用            	number(12,4
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
	public String getMedical_type() {
		return medical_type;
	}
	public void setMedical_type(String medical_type) {
		this.medical_type = medical_type;
	}
	public String getCenter_num() {
		return center_num;
	}
	public void setCenter_num(String center_num) {
		this.center_num = center_num;
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
	public long getCreater() {
		return creater;
	}
	public void setCreater(long creater) {
		this.creater = creater;
	}
	public Date getCreate_time() {
		return create_time;
	}
	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}
	
}
