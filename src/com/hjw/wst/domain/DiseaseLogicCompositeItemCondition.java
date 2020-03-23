package com.hjw.wst.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="disease_logic_composite_item_condition")
public class DiseaseLogicCompositeItemCondition implements java.io.Serializable{
	private static final long serialVersionUID = -97502163798576023L;
	
	@Id
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
    @Column(name = "id", unique = true, nullable = false,length = 50)
	private String id;
	
	@Column(name="logic_composite_item_id")
	private String logic_composite_item_id;
	
	@Column(name="diseaseOrItem_num")
	private String diseaseOrItem_num;
	
	@Column(name="condition_type")
	private long condition_type;
	
	@Column(name="logic_index")
	private long logic_index;

	@Column(name="condition_value")
	private String condition_value;
	
	@Column(name="condition")
	private String condition;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLogic_composite_item_id() {
		return logic_composite_item_id;
	}

	public void setLogic_composite_item_id(String logic_composite_item_id) {
		this.logic_composite_item_id = logic_composite_item_id;
	}

	public String getDiseaseOrItem_num() {
		return diseaseOrItem_num;
	}

	public void setDiseaseOrItem_num(String diseaseOrItem_num) {
		this.diseaseOrItem_num = diseaseOrItem_num;
	}

	public long getCondition_type() {
		return condition_type;
	}

	public void setCondition_type(long condition_type) {
		this.condition_type = condition_type;
	}

	public long getLogic_index() {
		return logic_index;
	}

	public void setLogic_index(long logic_index) {
		this.logic_index = logic_index;
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
}
