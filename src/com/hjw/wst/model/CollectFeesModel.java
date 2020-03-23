package com.hjw.wst.model;

import java.util.List;

import com.hjw.wst.DTO.CardInfoDTO;
import com.hjw.wst.DTO.ChargingWayDTO;
import com.hjw.wst.DTO.ExamResultChargingItemDTO;
import com.hjw.wst.domain.ExaminfoChargingItem;

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
	
	
} 
