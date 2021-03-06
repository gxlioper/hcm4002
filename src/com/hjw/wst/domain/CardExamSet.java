package com.hjw.wst.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 
     * @Title:  火箭蛙体检管理系统   卡信息对应套餐信息表
     * @Package com.hjw.wst.domain   
     * @Description:  
     * @author: zhaocongshuo     
     * @date:   2019年5月7日 下午3:30:32   
     * @version V4.0.0.0
 */
@Entity
@Table(name="card_exam_set")
public class CardExamSet implements java.io.Serializable{

	private static final long serialVersionUID = -97502163798576023L;

	@Id
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
	@Column(name = "id", unique = true, nullable = false,length = 50)
	private String id;
	
	@Column(name="card_num")
	private String card_num;

	@Column(name="set_num")
	private String set_num;
	
	@Column(name="set_amount")
	private Double set_amount;
	
	@Column(name="set_name")
	private String set_name;
	
	@Column(name="set_discount")
	private Double set_discount;
	
	@Column(name="center_num")
	private String center_num;
	
	@Column(name="creater")
	private long creater;
	
	@Column(name="create_time")
	private Date create_time;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCard_num() {
		return card_num;
	}

	public void setCard_num(String card_num) {
		this.card_num = card_num;
	}

	public String getSet_num() {
		return set_num;
	}

	public void setSet_num(String set_num) {
		this.set_num = set_num;
	}

	public Double getSet_amount() {
		return set_amount;
	}

	public void setSet_amount(Double set_amount) {
		this.set_amount = set_amount;
	}

	public String getSet_name() {
		return set_name;
	}

	public void setSet_name(String set_name) {
		this.set_name = set_name;
	}



	public Double getSet_discount() {
		return set_discount;
	}

	public void setSet_discount(Double set_discount) {
		this.set_discount = set_discount;
	}

	public String getCenter_num() {
		return center_num;
	}

	public void setCenter_num(String center_num) {
		this.center_num = center_num;
	}

	public long getCreater() {
		return creater;
	}

	public void setCreater(long creater) {
		this.creater = creater;
	}

	public Date getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	
	
	
}
