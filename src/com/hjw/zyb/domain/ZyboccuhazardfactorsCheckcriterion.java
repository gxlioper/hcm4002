package com.hjw.zyb.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/***
 * 2.2.27	职业危害因素与检查依据关系表
     * @Title:  火箭蛙体检管理系统   
     * @Package com.hjw.zyb.domain   
     * @Description:  
     * @author: yangm     
     * @date:   2017年12月7日 下午3:48:22   
     * @version V2.0.0.0
 */
@Entity
@Table(name = "zyb_occuhazardfactors_checkcriterion")
public class ZyboccuhazardfactorsCheckcriterion  implements Serializable{

	/**   
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)   
	 */ 
	private static final long serialVersionUID = 1089785690875845687L;
	
	@Id
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
	@Column(name = "RID", unique = true, nullable = false, length = 50)
	private String RID;
	
	@Column(name="criterionID")
	private String criterionID;

	@Column(name="occuphyexaclassID")
	private String occuphyexaclassID;
	
	@Column(name="hazardfactorsID")
	private String hazardfactorsID;
	
	@Column(name="DISORDER")
	private int DISORDER;
	
	public String getRID() {
		return RID;
	}
	public void setRID(String rID) {
		RID = rID;
	}
	public String getCriterionID() {
		return criterionID;
	}
	public void setCriterionID(String criterionID) {
		this.criterionID = criterionID;
	}
	public String getOccuphyexaclassID() {
		return occuphyexaclassID;
	}
	public void setOccuphyexaclassID(String occuphyexaclassID) {
		this.occuphyexaclassID = occuphyexaclassID;
	}
	public String getHazardfactorsID() {
		return hazardfactorsID;
	}
	public void setHazardfactorsID(String hazardfactorsID) {
		this.hazardfactorsID = hazardfactorsID;
	}
	public int getDISORDER() {
		return DISORDER;
	}
	public void setDISORDER(int dISORDER) {
		DISORDER = dISORDER;
	}
	
	
}
