package com.hjw.zyb.DTO;

import java.io.Serializable;
/**
 * 职业病管理
     * @Title:  火箭蛙体检管理系统   
     * @Package com.hjw.zyb.DTO   
     * @Description:  
     * @author: yangm     
     * @date:   2017年4月11日 下午3:51:53   
     * @version V2.0.0.0
 */
public class ZyboccudiseaseDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	private int diseaseclassID;
	private String diseaseclass_name;
	private String occudiseaseID;
	private String occudisease_name;
	
	public int getDiseaseclassID() {
		return diseaseclassID;
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
	public void setOccudiseaseID(String occudiseaseID) {
		this.occudiseaseID = occudiseaseID;
	}
	public void setOccudisease_name(String occudisease_name) {
		this.occudisease_name = occudisease_name;
	}
	public String getDiseaseclass_name() {
		return diseaseclass_name;
	}
	public void setDiseaseclass_name(String diseaseclass_name) {
		this.diseaseclass_name = diseaseclass_name;
	}
}
