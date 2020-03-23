package com.hjw.zyb.domain;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;


@Entity
@Table(name = "zyb_economicindustry")
public class ZybEconomicIndustry implements java.io.Serializable {
	 private static final long serialVersionUID = -97502163798576023L;

	 
	/* @Id
	 @GenericGenerator(name = "idGenerator", strategy = "identity")
	 @GeneratedValue(generator = "idGenerator")*/
	 @Id
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
	@Column(name = "industryID", unique = true, nullable = false,length = 50)
	 private String industryID;
	 
	 @Column(name = "industry_code")
     private String industry_code;
	 
	 @Column(name = "industry_name")
     private String industry_name;
	 
	 @Column(name = "parentID")
     private String parentID;
	 
	 @Column(name = "industry_type")
	 private int industry_type;//0表示普通体检（健康证类行业） 1表示职业病体检
	 
	 public ZybEconomicIndustry() {
		// TODO Auto-generated constructor stub
	}

	public String getIndustryID() {
		return industryID;
	}

	public void setIndustryID(String industryID) {
		this.industryID = industryID;
	}

	public String getIndustry_code() {
		return industry_code;
	}

	public void setIndustry_code(String industry_code) {
		this.industry_code = industry_code;
	}

	public String getIndustry_name() {
		return industry_name;
	}

	public void setIndustry_name(String industry_name) {
		this.industry_name = industry_name;
	}

	public String getParentID() {
		return parentID;
	}

	public void setParentID(String parentID) {
		this.parentID = parentID;
	}

	public int getIndustry_type() {
		return industry_type;
	}

	public void setIndustry_type(int industry_type) {
		this.industry_type = industry_type;
	}
}