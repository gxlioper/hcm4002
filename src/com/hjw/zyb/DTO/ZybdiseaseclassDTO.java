package com.hjw.zyb.DTO;

import java.io.Serializable;

/**
 * 疾病分类
 * 
 * @Title: 火箭蛙体检管理系统
 * @Package com.hjw.zyb.DTO
 * @Description:
 * @author: yangm
 * @date: 2017年4月13日 下午3:14:45
 * @version V2.0.0.0
 */
public class ZybdiseaseclassDTO implements Serializable {

	/**
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	 */
	private static final long serialVersionUID = 1L;
	private String diseaseclassID;
	private String diseaseclass_name;

	public String getDiseaseclassID() {
		return diseaseclassID;
	}

	public void setDiseaseclassID(String diseaseclassID) {
		this.diseaseclassID = diseaseclassID;
	}

	public String getDiseaseclass_name() {
		return diseaseclass_name;
	}

	public void setDiseaseclass_name(String diseaseclass_name) {
		this.diseaseclass_name = diseaseclass_name;
	}

}
