package com.hjw.zyb.DTO;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 职业禁忌证
 * 
 * @Title: 火箭蛙体检管理系统
 * @Package com.hjw.zyb.domain
 * @Description:
 * @author: yangm
 * @date: 2017年4月12日 下午3:45:17
 * @version V2.0.0.0
 */
public class ZyboccucontraindicationDTO implements Serializable {

	/**
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	 */
	private static final long serialVersionUID = 1L;

	private String contraindicationID;

	private String contraindication_name;

	private String tremexplain;
	
	private String hazardfactorsID;
	
	private int DISORDER;
	
	private String RID;
	
	private String hazard_name;
	private String occuphyexaclass_name;
	
	

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

	public String getContraindicationID() {
		return contraindicationID;
	}

	public void setContraindicationID(String contraindicationID) {
		this.contraindicationID = contraindicationID;
	}

	public String getContraindication_name() {
		return contraindication_name;
	}

	public void setContraindication_name(String contraindication_name) {
		this.contraindication_name = contraindication_name;
	}

	public String getTremexplain() {
		return tremexplain;
	}

	public void setTremexplain(String tremexplain) {
		this.tremexplain = tremexplain;
	}

}
