package com.hjw.zyb.DTO;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
/**
 * 疾病和因素关系
     * @Title:  火箭蛙体检管理系统   
     * @Package com.hjw.zyb.domain   
     * @Description:  
     * @author: yangm     
     * @date:   2017年12月27日 上午10:28:25   
     * @version V2.0.0.0
 */
public class ZyboccuhazardfactorsOccudiseaseDTO{

	/**   
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)   
	 */ 
	
	private String RID;
	private String hazardfactorsID;
	private String occuphyexaclassid;
	private String	occudiseaseID;
	private int	    DISORDER;
	private String occudisease_name;
	private String occuphyexaclass_name;
	private String hazard_name;
	
	
	
	public String getHazard_name() {
		return hazard_name;
	}
	public void setHazard_name(String hazard_name) {
		this.hazard_name = hazard_name;
	}
	public String getOccudisease_name() {
		return occudisease_name;
	}
	public void setOccudisease_name(String occudisease_name) {
		this.occudisease_name = occudisease_name;
	}
	public String getOccuphyexaclass_name() {
		return occuphyexaclass_name;
	}
	public void setOccuphyexaclass_name(String occuphyexaclass_name) {
		this.occuphyexaclass_name = occuphyexaclass_name;
	}
	public String getRID() {
		return RID;
	}
	public void setRID(String rID) {
		RID = rID;
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
	public String getOccudiseaseID() {
		return occudiseaseID;
	}
	public void setOccudiseaseID(String occudiseaseID) {
		this.occudiseaseID = occudiseaseID;
	}
	public int getDISORDER() {
		return DISORDER;
	}
	public void setDISORDER(int dISORDER) {
		DISORDER = dISORDER;
	}
	
	

}
