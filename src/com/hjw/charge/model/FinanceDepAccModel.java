package com.hjw.charge.model;

import java.util.List;

import com.hjw.charge.DTO.FinanceDepAccPaywayDTO;
import com.hjw.charge.DTO.FinanceVsCashierDailyAccDTO;

public class FinanceDepAccModel {

	private String start_date;
	private String end_date;
	private long user_id;
	private String daily_status;
	private double fd_acc_amount;
	private String fd_acc_num;
	
	private String financecCashiers;
	private List<FinanceVsCashierDailyAccDTO> financecCashier;
	
	private String financePayways;
	private List<FinanceDepAccPaywayDTO> financePayway;
	
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
	public long getUser_id() {
		return user_id;
	}
	public void setUser_id(long user_id) {
		this.user_id = user_id;
	}
	public String getDaily_status() {
		return daily_status;
	}
	public void setDaily_status(String daily_status) {
		this.daily_status = daily_status;
	}
	public String getFinancecCashiers() {
		return financecCashiers;
	}
	public void setFinancecCashiers(String financecCashiers) {
		this.financecCashiers = financecCashiers;
	}
	public List<FinanceVsCashierDailyAccDTO> getFinancecCashier() {
		return financecCashier;
	}
	public void setFinancecCashier(List<FinanceVsCashierDailyAccDTO> financecCashier) {
		this.financecCashier = financecCashier;
	}
	public String getFinancePayways() {
		return financePayways;
	}
	public void setFinancePayways(String financePayways) {
		this.financePayways = financePayways;
	}
	public List<FinanceDepAccPaywayDTO> getFinancePayway() {
		return financePayway;
	}
	public void setFinancePayway(List<FinanceDepAccPaywayDTO> financePayway) {
		this.financePayway = financePayway;
	}
	public double getFd_acc_amount() {
		return fd_acc_amount;
	}
	public void setFd_acc_amount(double fd_acc_amount) {
		this.fd_acc_amount = fd_acc_amount;
	}
	public String getFd_acc_num() {
		return fd_acc_num;
	}
	public void setFd_acc_num(String fd_acc_num) {
		this.fd_acc_num = fd_acc_num;
	}
}
