package com.hjw.wst.domain;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name=" his_clinic_item_v_price_list")
public class HisClinicItemPrice implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	@GenericGenerator(name = "generator", strategy = "increment")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "clinic_item_code", unique = true, nullable = false)
	private String clinic_item_code="";
	
	@Column(name="clinic_item_class")
	private String clinic_item_class="";
	
	@Column(name="charge_item_no")
	private int charge_item_no;
	
	@Column(name="charge_item_classs")
	private String charge_item_classs="";
	
	@Column(name="charge_item_code")
	private String charge_item_code="";
	
	@Column(name="charge_item_spec")
	private String charge_item_spec="";
	
	@Column(name="amount")
	private long amount;
	
	@Column(name="unitss")
	private String unitss="";
	
	@Column(name="backbill_rules")
	private String backbill_rules="";
	
	public String getClinic_item_code() {
		return clinic_item_code;
	}

	public void setClinic_item_code(String clinic_item_code) {
		this.clinic_item_code = clinic_item_code;
	}

	public String getClinic_item_class() {
		return clinic_item_class;
	}

	public void setClinic_item_class(String clinic_item_class) {
		this.clinic_item_class = clinic_item_class;
	}

	public int getCharge_item_no() {
		return charge_item_no;
	}

	public void setCharge_item_no(int charge_item_no) {
		this.charge_item_no = charge_item_no;
	}

	public String getCharge_item_classs() {
		return charge_item_classs;
	}

	public void setCharge_item_classs(String charge_item_classs) {
		this.charge_item_classs = charge_item_classs;
	}

	public String getCharge_item_code() {
		return charge_item_code;
	}

	public void setCharge_item_code(String charge_item_code) {
		this.charge_item_code = charge_item_code;
	}

	public String getCharge_item_spec() {
		return charge_item_spec;
	}

	public void setCharge_item_spec(String charge_item_spec) {
		this.charge_item_spec = charge_item_spec;
	}

	public long getAmount() {
		return amount;
	}

	public void setAmount(long amount) {
		this.amount = amount;
	}

	public String getUnitss() {
		return unitss;
	}

	public void setUnitss(String unitss) {
		this.unitss = unitss;
	}

	public String getBackbill_rules() {
		return backbill_rules;
	}

	public void setBackbill_rules(String backbill_rules) {
		this.backbill_rules = backbill_rules;
	}
}
