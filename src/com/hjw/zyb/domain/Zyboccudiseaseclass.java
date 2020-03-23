package com.hjw.zyb.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 职业病管理
 * 
 * @Title: 火箭蛙体检管理系统
 * @Package com.hjw.wst.domain
 * @Description:
 * @author: zr
 * @date: 2017年4月11日 下午3:36:13
 * @version V2.0.0.0
 */
@Entity
@Table(name = "zyb_occudiseaseclass")
public class Zyboccudiseaseclass implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
	@Column(name = "diseaseclassID", unique = true, nullable = false, length = 50)
	private int diseaseclassID;

	@Column(name = "diseaseclass_name")
	private String diseaseclass_name;

	public int getDiseaseclassID() {
		return diseaseclassID;
	}

	public String getDiseaseclass_name() {
		return diseaseclass_name;
	}

	public void setDiseaseclassID(int diseaseclassID) {
		this.diseaseclassID = diseaseclassID;
	}

	public void setDiseaseclass_name(String diseaseclass_name) {
		this.diseaseclass_name = diseaseclass_name;
	}
	
	
}
