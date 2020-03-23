package com.hjw.zyb.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 疾病分类对应结论
     * @Title:  火箭蛙体检管理系统   
     * @Package com.hjw.zyb.DTO   
     * @Description:  
     * @author: zr     
     * @date:   2017年4月13日 下午3:19:02   
     * @version V2.0.0.0
 */
@Entity
@Table( name = "zyb_diseaseclassresult")
public class Zybdiseaseclassresult  implements  Serializable{

	/**   
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)   
	 */ 
	private static final long serialVersionUID = 1L;
	
	 @Id
	 @GeneratedValue(generator = "paymentableGenerator")
	 @GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
	 @Column(name = "diseaseclassresultID", unique = true, nullable = false,length = 50)
	private String  diseaseclassresultID;
	 
	 @Column( name = "diseaseclassID")
	private String diseaseclassID;
	 
	 @Column( name = "resultID")
	private String resultID;

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

	 
	 
}
