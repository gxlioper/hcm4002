package com.hjw.zyb.DTO;

import java.io.Serializable;

/**
 * 结论
     * @Title:  火箭蛙体检管理系统   
     * @Package com.hjw.zyb.DTO   
     * @Description:  
     * @author: zr     
     * @date:   2017年4月13日 下午3:19:02   
     * @version V2.0.0.0
 */
public class ZybdiseaseclassresultDTO  implements  Serializable{

	/**   
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)   
	 */ 
	private static final long serialVersionUID = 1L;
	
	private String  diseaseclassresultID;
	private String diseaseclassID;
	private String resultID;
	
	private String  diseaseclass_name;
	private String  result_name;
	public String getDiseaseclassresultID() {
		return diseaseclassresultID;
	}
	public void setDiseaseclassresultID(String diseaseclassresultID) {
		this.diseaseclassresultID = diseaseclassresultID;
	}
	public String getDiseaseclassID() {
		return diseaseclassID;
	}
	public void setDiseaseclassID(String diseaseclassID) {
		this.diseaseclassID = diseaseclassID;
	}
	public String getResultID() {
		return resultID;
	}
	public void setResultID(String resultID) {
		this.resultID = resultID;
	}
	public String getDiseaseclass_name() {
		return diseaseclass_name;
	}
	public void setDiseaseclass_name(String diseaseclass_name) {
		this.diseaseclass_name = diseaseclass_name;
	}
	public String getResult_name() {
		return result_name;
	}
	public void setResult_name(String result_name) {
		this.result_name = result_name;
	}
	
	
}
