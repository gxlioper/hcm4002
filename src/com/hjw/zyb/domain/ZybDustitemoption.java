package com.hjw.zyb.domain;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;


@Entity
@Table(name = "zyb_dustitemoption")
public class ZybDustitemoption implements java.io.Serializable {
	 private static final long serialVersionUID = -97502163798576023L;

	 
	 
	 @Id
	 @GeneratedValue(generator = "paymentableGenerator")
	 @GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
	 @Column(name = "optionID", unique = true, nullable = false,length = 50)
     private String optionID;
	 
	 @Column(name = "dustID")
     private String dustID;
	 
	 @Column(name = "option_value")
     private String option_value;
	
	 @Column(name = "center_num")
	 private String center_num;

	 @Column(name = "showorder")
	 private int showorder;
	
	public String getOptionID() {
		return optionID;
	}

	public void setOptionID(String optionID) {
		this.optionID = optionID;
	}

	public String getDustID() {
		return dustID;
	}

	public void setDustID(String dustID) {
		this.dustID = dustID;
	}

	public String getOption_value() {
		return option_value;
	}

	public void setOption_value(String option_value) {
		this.option_value = option_value;
	}

	public String getCenter_num() {
		return center_num;
	}

	public void setCenter_num(String center_num) {
		this.center_num = center_num;
	}

	public int getShoworder() {
		return showorder;
	}

	public void setShoworder(int showorder) {
		this.showorder = showorder;
	}

	public ZybDustitemoption() {
		// TODO Auto-generated constructor stub
	}

			}