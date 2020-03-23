package com.hjw.wst.model;

import java.util.Date;
import java.util.List;

import com.hjw.wst.DTO.CardSaleDetailDTO;
import com.hjw.wst.DTO.CardSaleWayDTO;
import com.hjw.wst.domain.CardInfo;

public class CardInfoModel implements java.io.Serializable{

	private static final long serialVersionUID = -97502163798576023L;
	private String card_id;
	private String physical_num;
	private String card_num;
	private String member_id;
	private String user_name;
	private String arch_num;
	private String status;
	private String deadline;
	private String card_type;
	private Double amount;
	private Double face_amount;
	private String card_level;
	private Long card_count;
	private Long limit_card_count;
	private String card_pwd;
	private String card_remark;
	private String tijiantaocan_id;

	private Double c_amount;
	private long c_points;
	
	private String s_card_num;
	private String e_card_num;

	private String is_start_card_reader;
	
	private String star_date;
	private String end_date;
	
	private double discount;
	private String company;
	private CardInfo cardInfo;
	
	private String exam_num;
    private String isPrintRecepit;
	private String title_info;
	private String invoice_type;
	private String invoice_num;
	private String invoice_class;
	private String charingWays;
	private List<CardSaleWayDTO> charingWay;
	private String isCardUserChoose;
	private long hair_card_creater;
    
    public String getExam_num() {
		return exam_num;
	}

	public void setExam_num(String exam_num) {
		this.exam_num = exam_num;
	}
	private double sale_amount;
	private String time1;
	private String time2;
	private long hair_card_status;
	private String isShowInvoicePage;
	private String invoiceprinttype;
	private long is_set_status;
	private String itementities;
	private String setentities;
	private String is_charge_bearer_card;

	public String getIs_charge_bearer_card() {
		return is_charge_bearer_card;
	}

	public void setIs_charge_bearer_card(String is_charge_bearer_card) {
		this.is_charge_bearer_card = is_charge_bearer_card;
	}

	public long getHair_card_creater() {
		return hair_card_creater;
	}

	public void setHair_card_creater(long hair_card_creater) {
		this.hair_card_creater = hair_card_creater;
	}

	public String getIsCardUserChoose() {
		return isCardUserChoose;
	}

	public void setIsCardUserChoose(String isCardUserChoose) {
		this.isCardUserChoose = isCardUserChoose;
	}

	public long getIs_set_status() {
		return is_set_status;
	}

	public void setIs_set_status(long is_set_status) {
		this.is_set_status = is_set_status;
	}

	public CardInfo getCardInfo() {
		return cardInfo;
	}

	public void setCardInfo(CardInfo cardInfo) {
		this.cardInfo = cardInfo;
	}

	public double getDiscount() {
		return discount;
	}
	public String getTijiantaocan_id() {
		return tijiantaocan_id;
	}

	public void setTijiantaocan_id(String tijiantaocan_id) {
		this.tijiantaocan_id = tijiantaocan_id;
	}
	public void setDiscount(double discount) {
		this.discount = discount;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getStar_date() {
		return star_date;
	}

	public void setStar_date(String star_date) {
		this.star_date = star_date;
	}

	public String getEnd_date() {
		return end_date;
	}

	public void setEnd_date(String end_date) {
		this.end_date = end_date;
	}

	public String getIs_start_card_reader() {
		return is_start_card_reader;
	}

	public void setIs_start_card_reader(String is_start_card_reader) {
		this.is_start_card_reader = is_start_card_reader;
	}

	public String getCard_id() {
		return card_id;
	}

	public void setCard_id(String card_id) {
		this.card_id = card_id;
	}

	public String getPhysical_num() {
		return physical_num;
	}

	public void setPhysical_num(String physical_num) {
		this.physical_num = physical_num;
	}

	public String getCard_num() {
		return card_num;
	}

	public void setCard_num(String card_num) {
		this.card_num = card_num;
	}

	public String getMember_id() {
		return member_id;
	}

	public void setMember_id(String member_id) {
		this.member_id = member_id;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDeadline() {
		return deadline;
	}

	public void setDeadline(String deadline) {
		this.deadline = deadline;
	}

	public String getCard_type() {
		return card_type;
	}

	public void setCard_type(String card_type) {
		this.card_type = card_type;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Double getFace_amount() {
		return face_amount;
	}

	public void setFace_amount(Double face_amount) {
		this.face_amount = face_amount;
	}

	public String getCard_level() {
		return card_level;
	}

	public void setCard_level(String card_level) {
		this.card_level = card_level;
	}

	public Long getCard_count() {
		return card_count;
	}

	public void setCard_count(Long card_count) {
		this.card_count = card_count;
	}

	public Long getLimit_card_count() {
		return limit_card_count;
	}

	public void setLimit_card_count(Long limit_card_count) {
		this.limit_card_count = limit_card_count;
	}

	public String getCard_pwd() {
		return card_pwd;
	}

	public void setCard_pwd(String card_pwd) {
		this.card_pwd = card_pwd;
	}

	public String getCard_remark() {
		return card_remark;
	}

	public void setCard_remark(String card_remark) {
		this.card_remark = card_remark;
	}

	public Double getC_amount() {
		return c_amount;
	}

	public void setC_amount(Double c_amount) {
		this.c_amount = c_amount;
	}

	public long getC_points() {
		return c_points;
	}

	public void setC_points(long c_points) {
		this.c_points = c_points;
	}

	public String getS_card_num() {
		return s_card_num;
	}

	public void setS_card_num(String s_card_num) {
		this.s_card_num = s_card_num;
	}

	public String getE_card_num() {
		return e_card_num;
	}

	public void setE_card_num(String e_card_num) {
		this.e_card_num = e_card_num;
	}

	public String getArch_num() {
		return arch_num;
	}

	public void setArch_num(String arch_num) {
		this.arch_num = arch_num;
	}

	public double getSale_amount() {
		return sale_amount;
	}

	public void setSale_amount(double sale_amount) {
		this.sale_amount = sale_amount;
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

	public long getHair_card_status() {
		return hair_card_status;
	}

	public void setHair_card_status(long hair_card_status) {
		this.hair_card_status = hair_card_status;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	public String getIsPrintRecepit() {
		return isPrintRecepit;
	}

	public String getTitle_info() {
		return title_info;
	}

	public String getInvoice_type() {
		return invoice_type;
	}

	public String getInvoice_num() {
		return invoice_num;
	}

	public String getInvoice_class() {
		return invoice_class;
	}

	public String getCharingWays() {
		return charingWays;
	}

	public List<CardSaleWayDTO> getCharingWay() {
		return charingWay;
	}

	public void setIsPrintRecepit(String isPrintRecepit) {
		this.isPrintRecepit = isPrintRecepit;
	}

	public void setTitle_info(String title_info) {
		this.title_info = title_info;
	}

	public void setInvoice_type(String invoice_type) {
		this.invoice_type = invoice_type;
	}

	public void setInvoice_num(String invoice_num) {
		this.invoice_num = invoice_num;
	}

	public void setInvoice_class(String invoice_class) {
		this.invoice_class = invoice_class;
	}


	public void setCharingWays(String charingWays) {
		this.charingWays = charingWays;
	}

	public void setCharingWay(List<CardSaleWayDTO> charingWay) {
		this.charingWay = charingWay;
	}

	public String getIsShowInvoicePage() {
		return isShowInvoicePage;
	}

	public String getInvoiceprinttype() {
		return invoiceprinttype;
	}

	public void setIsShowInvoicePage(String isShowInvoicePage) {
		this.isShowInvoicePage = isShowInvoicePage;
	}

	public void setInvoiceprinttype(String invoiceprinttype) {
		this.invoiceprinttype = invoiceprinttype;
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
	
}

