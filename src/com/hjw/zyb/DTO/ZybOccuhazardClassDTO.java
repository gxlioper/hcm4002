package com.hjw.zyb.DTO;

import java.util.List;


/***
 * 
 * @Title: 火箭蛙体检管理系统
 * @Package com.hjw.zyb.domain
 * @Description: 职业危害类别管理
 * @author: zr
 * @date: 2017年3月29日 下午4:45:32
 * @version V2.0.0.0
 */
public class ZybOccuhazardClassDTO implements java.io.Serializable {

	/**
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	 */
	private static final long serialVersionUID = 1L;

	private String hazardclassID;

	private String hazardclass_code;

	private String hazardclass_name;

	private long order;
	
	private String remark;
	
	
	
	private String id;
	private String text;
	private String state = "closed";
	private List<ZybOccuhazardfactorsDTO> children;
	private String whlb = "whlb";
	

	
	public String getWhlb() {
		return whlb;
	}

	public void setWhlb(String whlb) {
		this.whlb = whlb;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public List<ZybOccuhazardfactorsDTO> getChildren() {
		return children;
	}

	public void setChildren(List<ZybOccuhazardfactorsDTO> children) {
		this.children = children;
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

	public String getHazardclassID() {
		return hazardclassID;
	}

	public String getHazardclass_code() {
		return hazardclass_code;
	}

	public String getHazardclass_name() {
		return hazardclass_name;
	}

	public long getOrder() {
		return order;
	}

	public String getRemark() {
		return remark;
	}

	public void setHazardclassID(String hazardclassID) {
		this.setId(hazardclassID);
		this.hazardclassID = hazardclassID;
	}

	public void setHazardclass_code(String hazardclass_code) {
		this.hazardclass_code = hazardclass_code;
	}

	public void setHazardclass_name(String hazardclass_name) {
		this.setText(hazardclass_name);
		this.hazardclass_name = hazardclass_name;
	}

	public void setOrder(long order) {
		this.order = order;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
