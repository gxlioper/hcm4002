package com.hjw.zyb.domain;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;


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
@Entity
@Table( name="zyb_recheck_item")
public class ZybRecheckItem implements java.io.Serializable {

	 /**   
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)   
	 */ 
	private static final long serialVersionUID = 1L;

	/**   
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)   
	 */ 

	@Id
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
	@Column(name ="check_item_id", unique = true, nullable = false,length = 50)
	private String check_item_id;
	
	@Column(name="check_item_ask")
	private String check_item_ask;
	
	@Column(name="center_num")
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
