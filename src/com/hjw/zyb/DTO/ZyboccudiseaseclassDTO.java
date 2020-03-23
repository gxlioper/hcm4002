package com.hjw.zyb.DTO;

import java.io.Serializable;


/**
 * 职业病分类管理
     * @Title:  火箭蛙体检管理系统   
     * @Package com.hjw.wst.domain   
     * @Description:  
     * @author: zr     
     * @date:   2017年4月11日 下午3:36:13   
     * @version V2.0.0.0
 */
public class ZyboccudiseaseclassDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	private int diseaseclassID;
	private String diseaseclass_name;

	
	public int getDiseaseclassID() {
		return diseaseclassID;
	}


	public void setDiseaseclassID(int diseaseclassID) {
		this.diseaseclassID = diseaseclassID;
	}


	public String getDiseaseclass_name() {
		return diseaseclass_name;
	}


	public void setDiseaseclass_name(String diseaseclass_name) {
		this.diseaseclass_name = diseaseclass_name;
	}
}
