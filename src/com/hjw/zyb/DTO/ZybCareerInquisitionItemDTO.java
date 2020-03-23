package com.hjw.zyb.DTO;

import java.io.Serializable;

import javax.persistence.Entity;

/**
 * 职业问诊项目管理
 * 
 * @Title: 火箭蛙体检管理系统
 * @Package com.hjw.zyb.domain
 * @Description:
 * @author: zr
 * @date: 2017年4月10日 上午10:08:06
 * @version V2.0.0.0
 */
public class ZybCareerInquisitionItemDTO implements Serializable {

	/**
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	 */
	private static final long serialVersionUID = 1L;
	private String item_id;
	private String item_code;
	private String item_name;
	private String sex;
	private String sex_s;
	private String isshow;
	private int order;
	private String result;
	
	public String getItem_id() {
		return item_id;
	}
	public String getItem_code() {
		return item_code;
	}
	public String getItem_name() {
		return item_name;
	}
	public String getSex() {
		return sex;
	}
	public String getIsshow() {
		return isshow;
	}
	public int getOrder() {
		return order;
	}
	public void setItem_id(String item_id) {
		this.item_id = item_id;
	}
	public void setItem_code(String item_code) {
		this.item_code = item_code;
	}
	public void setItem_name(String item_name) {
		this.item_name = item_name;
	}
	public void setSex(String sex) {
		this.sex = sex;
		if( "0".equals(sex)){
			this.setSex_s("通用");
		} else if("1".equals(sex)){
			this.setSex_s("男");
		} else{
			this.setSex_s("女");
		}
	}
	public void setIsshow(String isshow) {
		this.isshow = isshow;
	}
	public void setOrder(int order) {
		this.order = order;
	}
	public String getSex_s() {
		return sex_s;
	}
	public void setSex_s(String sex_s) {
		this.sex_s = sex_s;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
}
