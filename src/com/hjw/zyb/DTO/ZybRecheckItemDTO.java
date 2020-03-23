package com.hjw.zyb.DTO;

import java.io.Serializable;


/**
 * 复查项目及要求管理
 * 
 * @Title: 火箭蛙体检管理系统
 * @Package com.hjw.zyb.domain
 * @Description:
 * @author: zr
 * @date: 2017年4月19日 下午4:21:56
 * @version V2.0.0.0
 */
public class ZybRecheckItemDTO implements Serializable {

	/**
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	 */
	private static final long serialVersionUID = 1L;
	private String check_item_id;
	
	private String check_item_ask;
	
	private long center_num;
	
	public String getCheck_item_id() {
		return check_item_id;
	}
	public void setCheck_item_id(String check_item_id) {
		this.check_item_id = check_item_id;
	}
	public String getCheck_item_ask() {
		return check_item_ask;
	}
	public void setCheck_item_ask(String check_item_ask) {
		this.check_item_ask = check_item_ask;
	}
	public long getCenter_num() {
		return center_num;
	}
	public void setCenter_num(long center_num) {
		this.center_num = center_num;
	}
	
}
