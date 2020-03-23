package com.hjw.wst.model;

import java.util.List;

import com.hjw.wst.DTO.CardSaleDetailDTO;
import com.hjw.wst.DTO.CardSaleWayDTO;

public class CardSaleModel {
	private String card_num;
	private String card_num_s;
	private String card_num_e;
	private String start_date;
	private String end_date;
	private String company;
	private String isShowInvoicePage;
	private String invoiceprinttype;
	private String invoiceRepeatType;
	
	private String sale_trade_num;
	private long sale_status;
	private double amount;
	private double sale_amount;
	private String isPrintRecepit;
	private long sale_type;
	private String title_info;
	private String invoice_type;
	private String invoice_num;
	private String invoice_class;
	private String cardSaleDetails;
	private List<CardSaleDetailDTO> cardSaleDetail;
	private String charingWays;
	private List<CardSaleWayDTO> charingWay;
	private String company_id;
	
	public String getSale_trade_num() {
		return sale_trade_num;
	}
	public void setSale_trade_num(String sale_trade_num) {
		this.sale_trade_num = sale_trade_num;
	}
	public String getInvoice_class() {
		return invoice_class;
	}
	public void setInvoice_class(String invoice_class) {
		this.invoice_class = invoice_class;
	}
	public long getSale_status() {
		return sale_status;
	}
	public void setSale_status(long sale_status) {
		this.sale_status = sale_status;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public double getSale_amount() {
		return sale_amount;
	}
	public void setSale_amount(double sale_amount) {
		this.sale_amount = sale_amount;
	}
	public String getIsPrintRecepit() {
		return isPrintRecepit;
	}
	public void setIsPrintRecepit(String isPrintRecepit) {
		this.isPrintRecepit = isPrintRecepit;
	}
	public long getSale_type() {
		return sale_type;
	}
	public void setSale_type(long sale_type) {
		this.sale_type = sale_type;
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
	public String getCardSaleDetails() {
		return cardSaleDetails;
	}
	public void setCardSaleDetails(String cardSaleDetails) {
		this.cardSaleDetails = cardSaleDetails;
	}
	public List<CardSaleDetailDTO> getCardSaleDetail() {
		return cardSaleDetail;
	}
	public void setCardSaleDetail(List<CardSaleDetailDTO> cardSaleDetail) {
		this.cardSaleDetail = cardSaleDetail;
	}
	public String getCharingWays() {
		return charingWays;
	}
	public void setCharingWays(String charingWays) {
		this.charingWays = charingWays;
	}
	public List<CardSaleWayDTO> getCharingWay() {
		return charingWay;
	}
	public void setCharingWay(List<CardSaleWayDTO> charingWay) {
		this.charingWay = charingWay;
	}
	public String getIsShowInvoicePage() {
		return isShowInvoicePage;
	}
	public void setIsShowInvoicePage(String isShowInvoicePage) {
		this.isShowInvoicePage = isShowInvoicePage;
	}
	public String getInvoiceprinttype() {
		return invoiceprinttype;
	}
	public void setInvoiceprinttype(String invoiceprinttype) {
		this.invoiceprinttype = invoiceprinttype;
	}
	public String getCard_num() {
		return card_num;
	}
	public void setCard_num(String card_num) {
		this.card_num = card_num;
	}
	public String getCard_num_s() {
		return card_num_s;
	}
	public void setCard_num_s(String card_num_s) {
		this.card_num_s = card_num_s;
	}
	public String getCard_num_e() {
		return card_num_e;
	}
	public void setCard_num_e(String card_num_e) {
		this.card_num_e = card_num_e;
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
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getInvoiceRepeatType() {
		return invoiceRepeatType;
	}
	public void setInvoiceRepeatType(String invoiceRepeatType) {
		this.invoiceRepeatType = invoiceRepeatType;
	}
	public String getCompany_id() {
		return company_id;
	}
	public void setCompany_id(String company_id) {
		this.company_id = company_id;
	}
	
}
