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
     * @Title:  火箭蛙体检管理系统   卡信息对应套餐项目信息表
     * @Package com.hjw.wst.domain   
     * @Description:  
     * @author: zhaocongshuo     
     * @date:   2019年5月7日 下午3:30:32   
     * @version V4.0.0.0
 */
@Entity
@Table(name="card_exam_set_item")
public class CardExamSetItem implements java.io.Serializable{

	private static final long serialVersionUID = -97502163798576023L;

	@Id
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
	@Column(name = "id", unique = true, nullable = false,length = 50)
	private String id;
	
	@Column(name="card_num")
	private String card_num;

	@Column(name="charging_item_code")
	private String charging_item_code;
	
	@Column(name="set_num")
	private String set_num;
	
	@Column(name="itemnum")
	private long itemnum;
	
	@Column(name="discount")
	private Double discount;
	
	@Column(name="amount")
	private Double amount;
	
	@Column(name="item_amount")
	private Double item_amount;
	
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

	public String getCharging_item_code() {
		return charging_item_code;
	}

	public void setCharging_item_code(String charging_item_code) {
		this.charging_item_code = charging_item_code;
	}

	public String getSet_num() {
		return set_num;
	}

	public void setSet_num(String set_num) {
		this.set_num = set_num;
	}

	public long getItemnum() {
		return itemnum;
	}

	public void setItemnum(long itemnum) {
		this.itemnum = itemnum;
	}

	public Double getDiscount() {
		return discount;
	}

	public void setDiscount(Double discount) {
		this.discount = discount;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Double getItem_amount() {
		return item_amount;
	}

	public void setItem_amount(Double item_amount) {
		this.item_amount = item_amount;
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
