package com.hjw.zyb.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
/**
 * 因素体检类别禁忌症关系管理
     * @Title:  火箭蛙体检管理系统   
     * @Package com.hjw.zyb.domain   
     * @Description:  
     * @author: yangm     
     * @date:   2017年12月6日 下午3:22:02   
     * @version V2.0.0.0
 */
@Entity
@Table(name = "zyb_occuhazardfactors_occucontraindication")
public class ZyboccuhazardfactorsOccucontraindication  implements Serializable{
	
	/**   
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)   
	 */ 
	private static final long serialVersionUID = 358650175000506398L;

	@Id
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
	@Column(name = "RID", unique = true, nullable = false, length = 50)
	private String RID;
	
	@Column(name="occuphyexaclassID")
	private String  occuphyexaclassID;
	
	@Column(name="hazardfactorsID")
	private String hazardfactorsID;
	
	@Column(name="contraindicationID")
	private String contraindicationID;
	
	@Column(name="DISORDER")
	private int DISORDER;
	
	
	public String getRID() {
		return RID;
	}
	public void setRID(String rID) {
		RID = rID;
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
	public String getContraindicationID() {
		return contraindicationID;
	}
	public void setContraindicationID(String contraindicationID) {
		this.contraindicationID = contraindicationID;
	}
	public int getDISORDER() {
		return DISORDER;
	}
	public void setDISORDER(int dISORDER) {
		DISORDER = dISORDER;
	}
	
	
}
