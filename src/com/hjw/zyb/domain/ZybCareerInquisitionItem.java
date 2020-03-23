package com.hjw.zyb.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

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
@Entity
@Table(name = "zyb_career_inquisition_item")
public class ZybCareerInquisitionItem implements Serializable {

	/**
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	 */
	private static final long serialVersionUID = 1L;
	 @Id
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
	@Column(name = "item_id", unique = true, nullable = false,length = 50)
	private String item_id;
	 
	 @Column(name = "item_code")
	private String item_code;
	 
	 @Column(name = "item_name")
	private String item_name;
	 
	 @Column(name = "sex")
	private String sex;
	 
	 @Column(name = "isshow")
	private String isshow;
	 
	 @Column(name = "[order]")
	private int order;
	 
	 
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
	}
	public void setIsshow(String isshow) {
		this.isshow = isshow;
	}
	public void setOrder(int order) {
		this.order = order;
	}
	
}
