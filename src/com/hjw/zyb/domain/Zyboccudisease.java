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
     * @Title:  火箭蛙体检管理系统   
     * @Package com.hjw.wst.domain   
     * @Description:  
     * @author: yangm     
     * @date:   2017年4月11日 下午3:38:25   
     * @version V2.0.0.0
 */
@Entity
@Table( name = "zyb_occudisease" )
public class Zyboccudisease  implements   Serializable {
	/**   
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)   
	 */ 
	private static final long serialVersionUID = 1L;
	 @Id
	    @GeneratedValue(generator = "paymentableGenerator")
	    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
		@Column(name = "occudiseaseID", unique = true, nullable = false,length = 50)
		private String occudiseaseID;
	 
	 	@Column( name="diseaseclassID")
		private int diseaseclassID;
	 
	 	@Column( name="occudisease_name")
		private String occudisease_name;

		public String getOccudiseaseID() {
			return occudiseaseID;
		}

		public int getDiseaseclassID() {
			return diseaseclassID;
		}

		public String getOccudisease_name() {
			return occudisease_name;
		}

		public void setOccudiseaseID(String occudiseaseID) {
			this.occudiseaseID = occudiseaseID;
		}

		public void setDiseaseclassID(int diseaseclassID) {
			this.diseaseclassID = diseaseclassID;
		}

		public void setOccudisease_name(String occudisease_name) {
			this.occudisease_name = occudisease_name;
		}
	 	
	 	
}
