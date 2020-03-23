package com.hjw.zyb.DTO;


/**
 *
     * @Title:  火箭蛙体检管理系统   
     * @Package com.hjw.zyb.domain   
     * @Description:  职业体检类别
     * @author: zr     
     * @date:   2017年3月28日 上午10:13:19   
     * @version V2.0.0.0
 */
public class ZybOccuphyexaClassDTO  implements java.io.Serializable {

	/**   
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)   
	 */ 
	private static final long serialVersionUID = 1L;
	
	  private   String  occuphyexaclassID;
	  
	  private   String  occuphyexaclass_name;
	  
	  private   long  order;
	  
	  private   String order_s;
	  
	  private   String  remark;
	  
	  
	  private String id;
	  private String text;
	  private String lx="lx";

	  private String set_name;


    public String getSet_name() {
        return set_name;
    }

    public void setSet_name(String set_name) {
        this.set_name = set_name;
    }

    public String getLx() {
		return lx;
	}
	public void setLx(String lx) {
		this.lx = lx;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getOrder_s() {
		return order_s;
	}
	public void setOrder_s(String order_s) {
		this.setId(order_s);
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
		this.setId(occuphyexaclassID);
		this.occuphyexaclassID = occuphyexaclassID;
	}
	public void setOccuphyexaclass_name(String occuphyexaclass_name) {
		this.setText(occuphyexaclass_name);
		this.occuphyexaclass_name = occuphyexaclass_name;
	}
	public void setOrder(long order) {
		this.order = order;
		this.setOrder_s(order+"");
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	  
}
