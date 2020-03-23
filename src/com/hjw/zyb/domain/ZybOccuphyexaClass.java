package com.hjw.zyb.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 *
     * @Title:  火箭蛙体检管理系统   
     * @Package com.hjw.zyb.domain   
     * @Description:  职业体检类别
     * @author: zr     
     * @date:   2017年3月28日 上午10:13:19   
     * @version V2.0.0.0
 */
@Entity
@Table(name = "zyb_occuphyexaclass")
public class ZybOccuphyexaClass  implements java.io.Serializable {

	/**   
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)   
	 */ 
	private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
	@Column(name = "occuphyexaclassID", unique = true, nullable = false,length = 50)
	  private   String  occuphyexaclassID;
	  
	  @Column(name="occuphyexaclass_name")
	  private   String  occuphyexaclass_name;
	  
	  @Column(name="[order]")
	  private   long  order;
	  
	  @Column(name="remark")
	  private   String  remark;
	  
	public String getOccuphyexaclassID() {
		return occuphyexaclassID;
	}
	
	public String getOccuphyexaclass_name() {
		return occuphyexaclass_name;
	}
	public long getOrder() {
		return order;
	}
	public String getRemark() {
		return remark;
	}
	public void setOccuphyexaclassID(String occuphyexaclassID) {
		this.occuphyexaclassID = occuphyexaclassID;
	}
	public void setOccuphyexaclass_name(String occuphyexaclass_name) {
		this.occuphyexaclass_name = occuphyexaclass_name;
	}
	public void setOrder(long order) {
		this.order = order;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	  
}
