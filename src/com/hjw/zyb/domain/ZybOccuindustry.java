package com.hjw.zyb.domain;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;


@Entity
@Table(name = "zyb_occuindustry")
public class ZybOccuindustry implements java.io.Serializable {
	 private static final long serialVersionUID = -97502163798576023L;

	 
	 /*@Id
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
	 
	 @Column(name = "center_num")
     private String center_num;
	 
	 @Column(name = "scriptID")
     private String scriptID;
	 
	 @Column(name = "packageID")
     private int packageID;
	 
	 @Column(name = "exam_set_code")
     private String exam_set_code;
	 
	 @Column(name = "phyexeperiod")
     private int phyexeperiod;
	 
	 @Column(name = "trainperiod")
     private int trainperiod;
	 
	 @Column(name = "showorder")
     private int showorder;
	 
	 public ZybOccuindustry() {
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

	public String getCenter_num() {
		return center_num;
	}

	public void setCenter_num(String center_num) {
		this.center_num = center_num;
	}

	public String getScriptID() {
		return scriptID;
	}

	public void setScriptID(String scriptID) {
		this.scriptID = scriptID;
	}

	public int getPackageID() {
		return packageID;
	}

	public void setPackageID(int packageID) {
		this.packageID = packageID;
	}

	public String getExam_set_code() {
		return exam_set_code;
	}

	public void setExam_set_code(String exam_set_code) {
		this.exam_set_code = exam_set_code;
	}

	public int getPhyexeperiod(){
		return phyexeperiod;
	}

	public void setPhyexeperiod(int phyexeperiod) {
		this.phyexeperiod = phyexeperiod;
	}

	public int  getTrainperiod() {
		return trainperiod;
	}

	public void setTrainperiod(int trainperiod) {
		this.trainperiod = trainperiod;
	}

	public int getShoworder() {
		return showorder;
	}

	public void setShoworder(int showorder) {
		this.showorder = showorder;
	}

			}