package com.hjw.zyb.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
     * @Title:  火箭蛙体检管理系统   
     * @Package com.hjw.zyb.domain   
     * @Description:  职业体检类别
     * @author: zr     
     * @date:   2017年3月28日 上午10:13:19   
     * @version V2.0.0.0
 */
public class ZybOccuphyexaClassModel  implements java.io.Serializable {

	/**   
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)   
	 */ 
	private static final long serialVersionUID = 1L;
	
	  private   String  occuphyexaclassID;
	  
	  private   String  occuphyexaclass_name;
	  
	  private   long  order;
	  
	  private   String order_s;
	  
	  private   String  remark;
	  
	  
	public String getOrder_s() {
		return order_s;
	}
	public void setOrder_s(String order_s) {
		this.order_s = order_s;
	}
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
