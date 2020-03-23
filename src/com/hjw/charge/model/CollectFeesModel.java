package com.hjw.charge.model;

import java.util.Date;
import java.util.List;


import com.hjw.wst.DTO.CardInfoDTO;
import com.hjw.charge.DTO.ChargingWayDTO;
import com.hjw.charge.DTO.IdentityAuthenticationDTO;
import com.hjw.wst.DTO.ExamResultChargingItemDTO;

public class CollectFeesModel implements java.io.Serializable{
	private static final long serialVersionUID = -97502163798576023L;
	
	private long id;
	private String exam_num;
	private long exam_id;
	private Double amount1;
	private Double amount2;
	private Double discount;
	
	private String card_num;
	
	private String examInfoCharingItems;
	private String charingWays;
	private String cardInfos;
	private List<ExamResultChargingItemDTO> examInfoCharingItem;
	private List<ChargingWayDTO> charingWay;
	private List<CardInfoDTO> cardInfo;
	
	private String chargingIds;
	
	private String isPrintRecepit;
	private String title_info;
	private String invoice_type;
	private String invoice_num;
	private String invoice_infoNumber;//发票代码
	private String account_num;
	
	private String req_nums;
	private long invoice_id;
	private long summary_id;
	
	private long user_id;
	private String chi_name;
	private String invoice_num_min;//最小发票号
	private String invoice_num_max;//最大发票号
	private String invoice_num_used;//已使用最大发票号
	private String invoice_class;//发票类型 数据字典 FPLX
	private String invoice_classs;
	private String is_active;//启用那种发票类型号段 Y表示启用，N表示未启用
	private String is_use_all;//是否启用共用号段 Y表示启用，N表示未启用
	private long batchid;
	private String invoiceprinttype;
	
	private String isShowInvoicePage;
	private String isFeesBaodao;
	private String fees_mx_point = "1";
	private String is_fees_mx_point_checked = "N";
	private String collect_fees_whole="1";
	private String invoice_head;
	private String invoiceRepeatType;
	
	private String pay_mode;
	private String eci_ids;
	private Double tj_amount;
	private Double his_amount;
	private String item_ids;
	private long  com_id;  
	private String com_num;
	private Double amount;
	private String charging_way_id;
	private String jnnumbers;
	private Double invoice_amount;
	private String invoice_name;
	private String ids;
	private String rechargeResources;//充值资源
	private String frozenResources;//冻结资源
	private String type;
	private String is_repeat_invoice;//是否有重打 1表示没有，2表示有
	private String  chargeType;//收费类型
	private String  center_id;
	private String  work_other_num;
	private String  name;
	
	 private int pay_way;  //收费方式
	 
	 private String peis_trade_code; //体检交易流水号
	 
	 private  String daily_status;  // 日结状态 0 未日结 、1以日结 
	 
	 private Date daily_time;   //日结日期
	 
	 private  int  pos_type;   // pos类型 1 银联支付、 2 社保支付
	 
	 private String trans_code;   //00 收费 02 退费
	 
	 private int pay_class;
	 
	 private String nsurance_class ;
	 
	 private String prescription_name ;
	 
	 private String prescription_num ;
	 
	 private String inter_class;
	 
	 private String PatNo;
	 
	 private String trade_no;
	 
	 private String voucher_no;
	 private String  trade_date;
	 private String bill_type;//1票据 2 发票	 
	 private String tax_invoices_num;//第三方发票系统的编号。	 

		private String patNoDK;
		
		private String id_num;
		
		private String sex;
		
		private String birthday;
		
		private String nation;
		
		
		private Double acc_balance;
		
		private String insurance_status;
		
		private String ic;
		
		private String medical_type;
		
		private String business_type;

		private Long creater;
		
		private Date create_date;

		private long rcpt_print_flag;
		private String  singleRefund;
		
		
		
		/**购方名称  **/
		private String infoClientName;
		
		/**购方税号   **/
		private String infoClientTaxCode;
		
		/**购方开户行及账号  **/
		private String infoClientBankAccount;
		
		/**购方地址电话  **/
		private String infoClientAddressPhone;
		
		/**发票类型(0:增值税发票;2:增值税普通发票)  **/
		private String infoKind;
		
		private String logFileName="";
		private String logData="";	
		
		
		
		
		private String falg;
		private String identityAuthentications;
		
		private String cause;
		
		private String  ginseng_administrative;//参保地行政区划	varchar2(6)	y
		
		private String  ginseng_administrative_name;//参保地行政区划名称	varchar2(50)	y	
		
		private String  nam_entity;//单位名称	varchar2(100)	n	
		
		private String  pat_no;//社会保障卡号	varchar2(20)	y	
		
		private String  document_type;//证件类型	varchar2(3)	y	参见编码附件
		
		private String  document_name;//证件类型名称	varchar2(50)	y	
		
		private String  document_number;//证件号码（社会保障号）	varchar2(18)	y	
		
		private String  personal_code;//个人管理码	varchar2(20)	y	地市唯一标识码（id0000）
		
		private String  sex_name;//性别名称	varchar2(10)	y	
		
		private String  date_birth;//出生日期	number(8)	y	
		
		private String  medical_identification;//医疗救助认定身份	varchar2(3)	n	
		
		private String  medical_identification_name;//医疗救助认定身份名称	varchar2(50)	n	
		
		private String  personal_account_balance;   //个人账户余额	number(16,2)	y
		
		private String  medical_req_num;       
		private String  medical_charge_req_num;
		
		private String insureAccount;
		public String getSingleRefund() {
			return singleRefund;
		}
		public void setSingleRefund(String singleRefund) {
			this.singleRefund = singleRefund;
		}
	public long getRcpt_print_flag() {
			return rcpt_print_flag;
		}
		public void setRcpt_print_flag(long rcpt_print_flag) {
			this.rcpt_print_flag = rcpt_print_flag;
		}
	public String getInvoice_infoNumber() {
		return invoice_infoNumber;
	}
	public void setInvoice_infoNumber(String invoice_infoNumber) {
		this.invoice_infoNumber = invoice_infoNumber;
	}
	public String getTax_invoices_num() {
		return tax_invoices_num;
	}
	public void setTax_invoices_num(String tax_invoices_num) {
		this.tax_invoices_num = tax_invoices_num;
	}
	public String getBill_type() {
		return bill_type;
	}
	public void setBill_type(String bill_type) {
		this.bill_type = bill_type;
	}
	public String getIs_repeat_invoice() {
		return is_repeat_invoice;
	}
	public void setIs_repeat_invoice(String is_repeat_invoice) {
		this.is_repeat_invoice = is_repeat_invoice;
	}
	public String getInvoice_name() {
		return invoice_name;
	}
	public void setInvoice_name(String invoice_name) {
		this.invoice_name = invoice_name;
	}
	public String getInvoice_head() {
		return invoice_head;
	}
	public void setInvoice_head(String invoice_head) {
		this.invoice_head = invoice_head;
	}
	
	public String getCollect_fees_whole() {
		return collect_fees_whole;
	}
	public void setCollect_fees_whole(String collect_fees_whole) {
		this.collect_fees_whole = collect_fees_whole;
	}
	public long getInvoice_id() {
		return invoice_id;
	}
	public void setInvoice_id(long invoice_id) {
		this.invoice_id = invoice_id;
	}
	public String getIsFeesBaodao() {
		return isFeesBaodao;
	}
	public void setIsFeesBaodao(String isFeesBaodao) {
		this.isFeesBaodao = isFeesBaodao;
	}
	public String getInvoiceprinttype() {
		return invoiceprinttype;
	}
	public void setInvoiceprinttype(String invoiceprinttype) {
		this.invoiceprinttype = invoiceprinttype;
	}
	public String getIsShowInvoicePage() {
		return isShowInvoicePage;
	}
	public void setIsShowInvoicePage(String isShowInvoicePage) {
		this.isShowInvoicePage = isShowInvoicePage;
	}
	public long getBatchid() {
		return batchid;
	}
	public void setBatchid(long batchid) {
		this.batchid = batchid;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getExam_num() {
		return exam_num;
	}
	public void setExam_num(String exam_num) {
		this.exam_num = exam_num;
	}
	public long getExam_id() {
		return exam_id;
	}
	public void setExam_id(long exam_id) {
		this.exam_id = exam_id;
	}
	public Double getAmount1() {
		return amount1;
	}
	public void setAmount1(Double amount1) {
		this.amount1 = amount1;
	}
	public Double getAmount2() {
		return amount2;
	}
	public void setAmount2(Double amount2) {
		this.amount2 = amount2;
	}
	public Double getDiscount() {
		return discount;
	}
	public void setDiscount(Double discount) {
		this.discount = discount;
	}
	public String getCard_num() {
		return card_num;
	}
	public void setCard_num(String card_num) {
		this.card_num = card_num;
	}
	public String getExamInfoCharingItems() {
		return examInfoCharingItems;
	}
	public void setExamInfoCharingItems(String examInfoCharingItems) {
		this.examInfoCharingItems = examInfoCharingItems;
	}
	public String getCharingWays() {
		return charingWays;
	}
	public void setCharingWays(String charingWays) {
		this.charingWays = charingWays;
	}
	public List<ExamResultChargingItemDTO> getExamInfoCharingItem() {
		return examInfoCharingItem;
	}
	public void setExamInfoCharingItem(List<ExamResultChargingItemDTO> examInfoCharingItem) {
		this.examInfoCharingItem = examInfoCharingItem;
	}
	public List<ChargingWayDTO> getCharingWay() {
		return charingWay;
	}
	public void setCharingWay(List<ChargingWayDTO> charingWay) {
		this.charingWay = charingWay;
	}
	public String getCardInfos() {
		return cardInfos;
	}
	public void setCardInfos(String cardInfos) {
		this.cardInfos = cardInfos;
	}
	public List<CardInfoDTO> getCardInfo() {
		return cardInfo;
	}
	public void setCardInfo(List<CardInfoDTO> cardInfo) {
		this.cardInfo = cardInfo;
	}
	public long getSummary_id() {
		return summary_id;
	}
	public void setSummary_id(long summary_id) {
		this.summary_id = summary_id;
	}
	public String getIsPrintRecepit() {
		return isPrintRecepit;
	}
	public void setIsPrintRecepit(String isPrintRecepit) {
		this.isPrintRecepit = isPrintRecepit;
	}
	public String getTitle_info() {
		return title_info;
	}
	public void setTitle_info(String title_info) {
		this.title_info = title_info;
	}
	public String getInvoice_type() {
		return invoice_type;
	}
	public void setInvoice_type(String invoice_type) {
		this.invoice_type = invoice_type;
	}
	public String getInvoice_num() {
		return invoice_num;
	}
	public void setInvoice_num(String invoice_num) {
		this.invoice_num = invoice_num;
	}
	public String getReq_nums() {
		return req_nums;
	}
	public void setReq_nums(String req_nums) {
		this.req_nums = req_nums;
	}
	public String getAccount_num() {
		return account_num;
	}
	public void setAccount_num(String account_num) {
		this.account_num = account_num;
	}
	public String getChargingIds() {
		return chargingIds;
	}
	public void setChargingIds(String chargingIds) {
		this.chargingIds = chargingIds;
	}
	public long getUser_id() {
		return user_id;
	}
	public void setUser_id(long user_id) {
		this.user_id = user_id;
	}
	public String getChi_name() {
		return chi_name;
	}
	public void setChi_name(String chi_name) {
		this.chi_name = chi_name;
	}
	public String getInvoice_num_min() {
		return invoice_num_min;
	}
	public void setInvoice_num_min(String invoice_num_min) {
		this.invoice_num_min = invoice_num_min;
	}
	public String getInvoice_num_max() {
		return invoice_num_max;
	}
	public void setInvoice_num_max(String invoice_num_max) {
		this.invoice_num_max = invoice_num_max;
	}
	public String getInvoice_num_used() {
		return invoice_num_used;
	}
	public void setInvoice_num_used(String invoice_num_used) {
		this.invoice_num_used = invoice_num_used;
	}
	public String getInvoice_class() {
		return invoice_class;
	}
	public void setInvoice_class(String invoice_class) {
		this.invoice_class = invoice_class;
	}
	public String getInvoice_classs() {
		return invoice_classs;
	}
	public void setInvoice_classs(String invoice_classs) {
		this.invoice_classs = invoice_classs;
	}
	public String getIs_active() {
		return is_active;
	}
	public void setIs_active(String is_active) {
		this.is_active = is_active;
	}
	public String getIs_use_all() {
		return is_use_all;
	}
	public void setIs_use_all(String is_use_all) {
		this.is_use_all = is_use_all;
	}
	public String getPay_mode() {
		return pay_mode;
	}
	public void setPay_mode(String pay_mode) {
		this.pay_mode = pay_mode;
	}
	public String getEci_ids() {
		return eci_ids;
	}
	public void setEci_ids(String eci_ids) {
		this.eci_ids = eci_ids;
	}
	public Double getTj_amount() {
		return tj_amount;
	}
	public void setTj_amount(Double tj_amount) {
		this.tj_amount = tj_amount;
	}
	public Double getHis_amount() {
		return his_amount;
	}
	public void setHis_amount(Double his_amount) {
		this.his_amount = his_amount;
	}
	public String getItem_ids() {
		return item_ids;
	}
	public void setItem_ids(String item_ids) {
		this.item_ids = item_ids;
	}
	 
	public String getFees_mx_point() {
		return fees_mx_point;
	}
	public void setFees_mx_point(String fees_mx_point) {
		this.fees_mx_point = fees_mx_point;
	}
	public String getIs_fees_mx_point_checked() {
		return is_fees_mx_point_checked;
	}
	public void setIs_fees_mx_point_checked(String is_fees_mx_point_checked) {
		this.is_fees_mx_point_checked = is_fees_mx_point_checked;
	}
	public long getCom_id() {
		return com_id;
	}
	public void setCom_id(long com_id) {
		this.com_id = com_id;
	}
	public String getCom_num() {
		return com_num;
	}
	public Double getAmount() {
		return amount;
	}
	public String getCharging_way_id() {
		return charging_way_id;
	}
	public void setCom_num(String com_num) {
		this.com_num = com_num;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public void setCharging_way_id(String charging_way_id) {
		this.charging_way_id = charging_way_id;
	}
	public String getJnnumbers() {
		return jnnumbers;
	}
	public void setJnnumbers(String jnnumbers) {
		this.jnnumbers = jnnumbers;
	}
	public Double getInvoice_amount() {
		return invoice_amount;
	}
	public void setInvoice_amount(Double invoice_amount) {
		this.invoice_amount = invoice_amount;
	}
	public String getIds() {
		return ids;
	}
	public void setIds(String ids) {
		this.ids = ids;
	}
	public String getRechargeResources() {
		return rechargeResources;
	}
	public String getFrozenResources() {
		return frozenResources;
	}
	public void setRechargeResources(String rechargeResources) {
		this.rechargeResources = rechargeResources;
	}
	public void setFrozenResources(String frozenResources) {
		this.frozenResources = frozenResources;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getInvoiceRepeatType() {
		return invoiceRepeatType;
	}
	public void setInvoiceRepeatType(String invoiceRepeatType) {
		this.invoiceRepeatType = invoiceRepeatType;
	}
	public String getChargeType() {
		return chargeType;
	}
	public void setChargeType(String chargeType) {
		this.chargeType = chargeType;
	}
	public int getPay_way() {
		return pay_way;
	}
	public void setPay_way(int pay_way) {
		this.pay_way = pay_way;
	}
	public String getPeis_trade_code() {
		return peis_trade_code;
	}
	public void setPeis_trade_code(String peis_trade_code) {
		this.peis_trade_code = peis_trade_code;
	}
	public String getDaily_status() {
		return daily_status;
	}
	public void setDaily_status(String daily_status) {
		this.daily_status = daily_status;
	}
	public Date getDaily_time() {
		return daily_time;
	}
	public void setDaily_time(Date daily_time) {
		this.daily_time = daily_time;
	}
	public int getPos_type() {
		return pos_type;
	}
	public void setPos_type(int pos_type) {
		this.pos_type = pos_type;
	}
	public String getTrans_code() {
		return trans_code;
	}
	public void setTrans_code(String trans_code) {
		this.trans_code = trans_code;
	}
	public int getPay_class() {
		return pay_class;
	}
	public void setPay_class(int pay_class) {
		this.pay_class = pay_class;
	}
	public String getNsurance_class() {
		return nsurance_class;
	}
	public void setNsurance_class(String nsurance_class) {
		this.nsurance_class = nsurance_class;
	}
	public String getPrescription_name() {
		return prescription_name;
	}
	public void setPrescription_name(String prescription_name) {
		this.prescription_name = prescription_name;
	}
	public String getPrescription_num() {
		return prescription_num;
	}
	public void setPrescription_num(String prescription_num) {
		this.prescription_num = prescription_num;
	}
	public String getInter_class() {
		return inter_class;
	}
	public void setInter_class(String inter_class) {
		this.inter_class = inter_class;
	}
	public String getPatNo() {
		return PatNo;
	}
	public void setPatNo(String patNo) {
		PatNo = patNo;
	}
	public String getCenter_id() {
		return center_id;
	}
	public void setCenter_id(String center_id) {
		this.center_id = center_id;
	}
	public String getWork_other_num() {
		return work_other_num;
	}
	public void setWork_other_num(String work_other_num) {
		this.work_other_num = work_other_num;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTrade_no() {
		return trade_no;
	}
	public void setTrade_no(String trade_no) {
		this.trade_no = trade_no;
	}
	public String getVoucher_no() {
		return voucher_no;
	}
	public void setVoucher_no(String voucher_no) {
		this.voucher_no = voucher_no;
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
	public String getNation() {
		return nation;
	}
	public void setNation(String nation) {
		this.nation = nation;
	}
	public Double getAcc_balance() {
		return acc_balance;
	}
	public void setAcc_balance(Double acc_balance) {
		this.acc_balance = acc_balance;
	}
	public String getInsurance_status() {
		return insurance_status;
	}
	public void setInsurance_status(String insurance_status) {
		this.insurance_status = insurance_status;
	}
	public String getIc() {
		return ic;
	}
	public void setIc(String ic) {
		this.ic = ic;
	}
	public String getMedical_type() {
		return medical_type;
	}
	public void setMedical_type(String medical_type) {
		this.medical_type = medical_type;
	}
	public String getBusiness_type() {
		return business_type;
	}
	public void setBusiness_type(String business_type) {
		this.business_type = business_type;
	}
	public Long getCreater() {
		return creater;
	}
	public void setCreater(Long creater) {
		this.creater = creater;
	}
	public Date getCreate_date() {
		return create_date;
	}
	public void setCreate_date(Date create_date) {
		this.create_date = create_date;
	}
	public String getPatNoDK() {
		return patNoDK;
	}
	public void setPatNoDK(String patNoDK) {
		this.patNoDK = patNoDK;
	}
	public String getInfoClientName() {
		return infoClientName;
	}
	public void setInfoClientName(String infoClientName) {
		this.infoClientName = infoClientName;
	}
	public String getInfoClientTaxCode() {
		return infoClientTaxCode;
	}
	public void setInfoClientTaxCode(String infoClientTaxCode) {
		this.infoClientTaxCode = infoClientTaxCode;
	}
	public String getInfoClientBankAccount() {
		return infoClientBankAccount;
	}
	public void setInfoClientBankAccount(String infoClientBankAccount) {
		this.infoClientBankAccount = infoClientBankAccount;
	}
	public String getInfoClientAddressPhone() {
		return infoClientAddressPhone;
	}
	public void setInfoClientAddressPhone(String infoClientAddressPhone) {
		this.infoClientAddressPhone = infoClientAddressPhone;
	}
	public String getInfoKind() {
		return infoKind;
	}
	public void setInfoKind(String infoKind) {
		this.infoKind = infoKind;
	}
	public String getTrade_date() {
		return trade_date;
	}
	public void setTrade_date(String trade_date) {
		this.trade_date = trade_date;
	}
	public String getLogFileName() {
		return logFileName;
	}
	public void setLogFileName(String logFileName) {
		this.logFileName = logFileName;
	}
	public String getLogData() {
		return logData;
	}
	public void setLogData(String logData) {
		this.logData = logData;
	}
	public String getFalg() {
		return falg;
	}
	public void setFalg(String falg) {
		this.falg = falg;
	}
	public String getIdentityAuthentications() {
		return identityAuthentications;
	}
	public void setIdentityAuthentications(String identityAuthentications) {
		this.identityAuthentications = identityAuthentications;
	}
	public String getCause() {
		return cause;
	}
	public void setCause(String cause) {
		this.cause = cause;
	}
	public String getGinseng_administrative() {
		return ginseng_administrative;
	}
	public void setGinseng_administrative(String ginseng_administrative) {
		this.ginseng_administrative = ginseng_administrative;
	}
	public String getGinseng_administrative_name() {
		return ginseng_administrative_name;
	}
	public void setGinseng_administrative_name(String ginseng_administrative_name) {
		this.ginseng_administrative_name = ginseng_administrative_name;
	}
	public String getNam_entity() {
		return nam_entity;
	}
	public void setNam_entity(String nam_entity) {
		this.nam_entity = nam_entity;
	}
	public String getPat_no() {
		return pat_no;
	}
	public void setPat_no(String pat_no) {
		this.pat_no = pat_no;
	}
	public String getDocument_type() {
		return document_type;
	}
	public void setDocument_type(String document_type) {
		this.document_type = document_type;
	}
	public String getDocument_name() {
		return document_name;
	}
	public void setDocument_name(String document_name) {
		this.document_name = document_name;
	}
	public String getDocument_number() {
		return document_number;
	}
	public void setDocument_number(String document_number) {
		this.document_number = document_number;
	}
	public String getPersonal_code() {
		return personal_code;
	}
	public void setPersonal_code(String personal_code) {
		this.personal_code = personal_code;
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
	public String getMedical_identification() {
		return medical_identification;
	}
	public void setMedical_identification(String medical_identification) {
		this.medical_identification = medical_identification;
	}
	public String getMedical_identification_name() {
		return medical_identification_name;
	}
	public void setMedical_identification_name(String medical_identification_name) {
		this.medical_identification_name = medical_identification_name;
	}
	public String getPersonal_account_balance() {
		return personal_account_balance;
	}
	public void setPersonal_account_balance(String personal_account_balance) {
		this.personal_account_balance = personal_account_balance;
	}
	public String getMedical_req_num() {
		return medical_req_num;
	}
	public void setMedical_req_num(String medical_req_num) {
		this.medical_req_num = medical_req_num;
	}
	public String getMedical_charge_req_num() {
		return medical_charge_req_num;
	}
	public void setMedical_charge_req_num(String medical_charge_req_num) {
		this.medical_charge_req_num = medical_charge_req_num;
	}
	public String getInsureAccount() {
		return insureAccount;
	}
	public void setInsureAccount(String insureAccount) {
		this.insureAccount = insureAccount;
	}
	
	
} 
