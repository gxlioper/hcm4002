package com.hjw.zyb.domain;

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
@Entity()
@Table(name ="zyb_occuhazardfactors_occudisease")
public class ZyboccuhazardfactorsOccudisease implements  Serializable{

	/**   
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)   
	 */ 
	private static final long serialVersionUID = -7745489650954426369L;
	
	@Id
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
	@Column(name = "RID", unique = true, nullable = false, length = 50)
	private String RID;
	
	@Column(name="hazardfactorsID")
	private String hazardfactorsID;
	@Column(name="occuphyexaclassid")
	private String occuphyexaclassid;
	@Column(name="occudiseaseID")
	private String	occudiseaseID;
	@Column(name="DISORDER")
	private int	    DISORDER;
	
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
