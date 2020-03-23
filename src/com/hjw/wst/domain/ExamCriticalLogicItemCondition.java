package com.hjw.wst.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="exam_Critical_logic_item_condition")
public class ExamCriticalLogicItemCondition implements Serializable {
	/**   
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)   
	 */ 
	private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
	@Column(name = "id", unique = true, nullable = false,length = 50)
	 
	private String id;
	private String logic_item_id;
	private String item_num;
	private String condition_value;
	private String condition;
	private long logic_index;
	private String charging_item_code;
	
	
	
	public String getCharging_item_code() {
		return charging_item_code;
	}
	public void setCharging_item_code(String charging_item_code) {
		this.charging_item_code = charging_item_code;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getLogic_item_id() {
		return logic_item_id;
	}
	public void setLogic_item_id(String logic_item_id) {
		this.logic_item_id = logic_item_id;
	}
	public String getItem_num() {
		return item_num;
	}
	public void setItem_num(String item_num) {
		this.item_num = item_num;
	}
	public String getCondition_value() {
		return condition_value;
	}
	public void setCondition_value(String condition_value) {
		this.condition_value = condition_value;
	}
	public String getCondition() {
		return condition;
	}
	public void setCondition(String condition) {
		this.condition = condition;
	}
	public long getLogic_index() {
		return logic_index;
	}
	public void setLogic_index(long logic_index) {
		this.logic_index = logic_index;
	}
	
	

}
