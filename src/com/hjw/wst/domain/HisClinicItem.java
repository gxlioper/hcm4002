package com.hjw.wst.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="his_clinic_item")
public class HisClinicItem implements java.io.Serializable{
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
	
	@Column(name="input_code")
	private String input_code="";
	
	@Column(name="expand1")
	private String expand1="";
	
	@Column(name="expand2")
	private String expand2="";
	
	@Column(name="expand3")
	private String expand3="";
	
	@Column(name="item_status")
	private String item_status="";

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

	public String getInput_code() {
		return input_code;
	}

	public void setInput_code(String input_code) {
		this.input_code = input_code;
	}

	public String getExpand1() {
		return expand1;
	}

	public void setExpand1(String expand1) {
		this.expand1 = expand1;
	}

	public String getExpand2() {
		return expand2;
	}

	public void setExpand2(String expand2) {
		this.expand2 = expand2;
	}

	public String getExpand3() {
		return expand3;
	}

	public void setExpand3(String expand3) {
		this.expand3 = expand3;
	}

	public String getItem_status() {
		return item_status;
	}

	public void setItem_status(String item_status) {
		this.item_status = item_status;
	}

}
