package com.hjw.zyb.domain;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;


@Entity
@Table(name = "zyb_occuhazardrelatorsinfo")
public class ZybOccuhazardrelatorsinfo implements java.io.Serializable {
	 private static final long serialVersionUID = -97502163798576023L;

	 
	 
	 @Id
	 @GeneratedValue(generator = "paymentableGenerator")
	 @GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
	 @Column(name = "id", unique = true, nullable = false,length = 50)
     private String id;
	 
	 @Column(name = "hazardfactorsID")
     private String hazardfactorsID;//职业危害因素
	 
	 @Column(name = "occuphyexaclassID")
     private String occuphyexaclassID;//职业体检类别
	
	 @Column(name = "hazardclassID")
	 private String hazardclassID;//职业危害类别
	 
	 @Column(name = "diseaseandtaboo")
     private String diseaseandtaboo;
	 
	 @Column(name = "checkcontent")
     private String checkcontent;
	 
	 @Column(name = "checkcriterion")
     private String checkcriterion;
	 
	 @Column(name = "followdisease")
     private String followdisease;
	 
	 @Column(name = "examperiod")
     private String examperiod;
	 
	 @Column(name = "remark")
     private String remark;
	 
	
	 
	 public String getHazardclassID() {
		return hazardclassID;
	}

	public void setHazardclassID(String hazardclassID) {
		this.hazardclassID = hazardclassID;
	}

	public String getOccuphyexaclassID() {
		return occuphyexaclassID;
	}

	public void setOccuphyexaclassID(String occuphyexaclassID) {
		this.occuphyexaclassID = occuphyexaclassID;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getHazardfactorsID() {
		return hazardfactorsID;
	}

	public void setHazardfactorsID(String hazardfactorsID) {
		this.hazardfactorsID = hazardfactorsID;
	}
	public String getDiseaseandtaboo() {
		return diseaseandtaboo;
	}

	public void setDiseaseandtaboo(String diseaseandtaboo) {
		this.diseaseandtaboo = diseaseandtaboo;
	}

	public String getCheckcontent() {
		return checkcontent;
	}

	public void setCheckcontent(String checkcontent) {
		this.checkcontent = checkcontent;
	}

	public String getCheckcriterion() {
		return checkcriterion;
	}

	public void setCheckcriterion(String checkcriterion) {
		this.checkcriterion = checkcriterion;
	}

	public String getFollowdisease() {
		return followdisease;
	}

	public void setFollowdisease(String followdisease) {
		this.followdisease = followdisease;
	}

	public String getExamperiod() {
		return examperiod;
	}

	public void setExamperiod(String examperiod) {
		this.examperiod = examperiod;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public ZybOccuhazardrelatorsinfo() {
		// TODO Auto-generated constructor stub
	}

			}