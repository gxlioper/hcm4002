package com.hjw.wst.domain;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="his_price_list")
public class HisPriceList implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	@GenericGenerator(name = "generator", strategy = "increment")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "item_code", unique = true, nullable = false)
	private String item_code="";
	
	@Column(name="item_class")
	private String item_class="";
	
	@Column(name="item_name")
	private String item_name="";
	
	@Column(name="item_spec")
	private String item_spec="";
	
	@Column(name="units")
	private String units="";
	
	@Column(name="price")
	private double price;
	
	@Column(name="prefer_price")
	private double prefer_price;
	
	@Column(name="performed_by")
	private String performed_by="";
	
	@Column(name="input_code")
	private String input_code="";
	
	@Column(name="class_on_inp_rcpt")
	private String class_on_inp_rcpt="";
	
	@Column(name="class_on_outp_rcpt")
	private String class_on_outp_rcpt="";
	
	@Column(name="class_on_reckoning")
	private String class_on_reckoning="";
	
	@Column(name="subj_code")
	private String subj_code="";
	
	@Column(name="memo")
	private String memo="";
	
	@Column(name="start_date")
	private Date start_date;
	
	@Column(name="stop_date")
	private Date stop_date;

	public String getItem_code() {
		return item_code;
	}

	public void setItem_code(String item_code) {
		this.item_code = item_code;
	}

	public String getItem_class() {
		return item_class;
	}

	public void setItem_class(String item_class) {
		this.item_class = item_class;
	}

	public String getItem_name() {
		return item_name;
	}

	public void setItem_name(String item_name) {
		this.item_name = item_name;
	}

	public String getItem_spec() {
		return item_spec;
	}

	public void setItem_spec(String item_spec) {
		this.item_spec = item_spec;
	}

	public String getUnits() {
		return units;
	}

	public void setUnits(String units) {
		this.units = units;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public double getPrefer_price() {
		return prefer_price;
	}

	public void setPrefer_price(double prefer_price) {
		this.prefer_price = prefer_price;
	}

	public String getPerformed_by() {
		return performed_by;
	}

	public void setPerformed_by(String performed_by) {
		this.performed_by = performed_by;
	}

	public String getInput_code() {
		return input_code;
	}

	public void setInput_code(String input_code) {
		this.input_code = input_code;
	}

	public String getClass_on_inp_rcpt() {
		return class_on_inp_rcpt;
	}

	public void setClass_on_inp_rcpt(String class_on_inp_rcpt) {
		this.class_on_inp_rcpt = class_on_inp_rcpt;
	}

	public String getClass_on_outp_rcpt() {
		return class_on_outp_rcpt;
	}

	public void setClass_on_outp_rcpt(String class_on_outp_rcpt) {
		this.class_on_outp_rcpt = class_on_outp_rcpt;
	}

	public String getClass_on_reckoning() {
		return class_on_reckoning;
	}

	public void setClass_on_reckoning(String class_on_reckoning) {
		this.class_on_reckoning = class_on_reckoning;
	}

	public String getSubj_code() {
		return subj_code;
	}

	public void setSubj_code(String subj_code) {
		this.subj_code = subj_code;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public Date getStart_date() {
		return start_date;
	}

	public void setStart_date(Date start_date) {
		this.start_date = start_date;
	}

	public Date getStop_date() {
		return stop_date;
	}

	public void setStop_date(Date stop_date) {
		this.stop_date = stop_date;
	}

}
