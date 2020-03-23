package com.hjw.zyb.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 职业病/分类管理
     * @Title:  火箭蛙体检管理系统   
     * @Package com.hjw.wst.domain   
     * @Description:  
     * @author: zr     
     * @date:   2017年4月11日 下午3:36:13   
     * @version V2.0.0.0
 */
public class ZyboccudiseaseModel implements Serializable{
	private static final long serialVersionUID = 1L;
	
	//职业病分类
	private int diseaseclassID;
	private String diseaseclass_name;
	//职业病
	private  String  occudiseaseID;
	private  String  occudisease_name;
	
	//-----------职业病和因素关系
	private String hazardfactorsID;
	private String occuphyexaclassid;
	private int DISORDER;
	private String RID;
	private String tjlb;
	private String occuphyexaclass_name;
	private String hazard_name;
	
	
	public String getHazard_name() {
		return hazard_name;
	}
	public void setHazard_name(String hazard_name) {
		this.hazard_name = hazard_name;
	}
	public String getOccuphyexaclass_name() {
		return occuphyexaclass_name;
	}
	public void setOccuphyexaclass_name(String occuphyexaclass_name) {
		this.occuphyexaclass_name = occuphyexaclass_name;
	}
	public String getTjlb() {
		return tjlb;
	}
	public void setTjlb(String tjlb) {
		this.tjlb = tjlb;
	}
	public String getRID() {
		return RID;
	}
	public void setRID(String rID) {
		RID = rID;
	}
	public int getDISORDER() {
		return DISORDER;
	}
	public void setDISORDER(int dISORDER) {
		DISORDER = dISORDER;
	}
	public String getHazardfactorsID() {
		return hazardfactorsID;
	}
	public void setHazardfactorsID(String hazardfactorsID) {
		this.hazardfactorsID = hazardfactorsID;
	}
	public String getOccuphyexaclassid() {
		return occuphyexaclassid;
	}
	public void setOccuphyexaclassid(String occuphyexaclassid) {
		this.occuphyexaclassid = occuphyexaclassid;
	}
	public int getDiseaseclassID() {
		return diseaseclassID;
	}
	public String getDiseaseclass_name() {
		return diseaseclass_name;
	}
	public String getOccudiseaseID() {
		return occudiseaseID;
	}
	public String getOccudisease_name() {
		return occudisease_name;
	}
	public void setDiseaseclassID(int diseaseclassID) {
		this.diseaseclassID = diseaseclassID;
	}
	public void setDiseaseclass_name(String diseaseclass_name) {
		this.diseaseclass_name = diseaseclass_name;
	}
	public void setOccudiseaseID(String occudiseaseID) {
		this.occudiseaseID = occudiseaseID;
	}
	public void setOccudisease_name(String occudisease_name) {
		this.occudisease_name = occudisease_name;
	}
	
}
