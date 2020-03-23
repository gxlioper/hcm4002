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
     * @Title:  火箭蛙体检管理系统   
     * @Package com.hjw.wst.domain   
     * @Description:  科室疾病项目关系表
     * @author: zr     
     * @date:   2016年12月13日 上午9:23:36   
     * @version V2.0.0.0
 */
@Entity
@Table(name = "dep_logic_exam_item")
public class DepLogicExamItem implements java.io.Serializable {

	@Id
	@GenericGenerator(name = "idGenerator", strategy = "identity")
	@GeneratedValue(generator = "idGenerator")
	private long id;

	@Column(name = "logic_id")
	private long logic_id;

	@Column(name = "exam_item_id")
	private long exam_item_id;

	@Column(name = "logic_index")
	private long logic_index;

	@Column(name = "condition_value")
	private String condition_value;

	@Column(name = "condition")
	private String condition;

	@Column(name = "andOrNo")
	private String andOrNo;

	@Column(name = "creater")
	private long creater;

	@Column(name = "create_time")
	private Date create_time;

	@Column(name = "updater")
	private long updater;

	@Column(name = "update_time")
	private Date update_time;
	
	@Column(name = "item_code")
	private String item_code;

	
	
	public String getItem_code() {
		return item_code;
	}

	public void setItem_code(String item_code) {
		this.item_code = item_code;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getLogic_id() {
		return logic_id;
	}

	public void setLogic_id(long logic_id) {
		this.logic_id = logic_id;
	}

	public long getExam_item_id() {
		return exam_item_id;
	}

	public void setExam_item_id(long exam_item_id) {
		this.exam_item_id = exam_item_id;
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

	public String getAndOrNo() {
		return andOrNo;
	}

	public void setAndOrNo(String andOrNo) {
		this.andOrNo = andOrNo;
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

	public long getUpdater() {
		return updater;
	}

	public void setUpdater(long updater) {
		this.updater = updater;
	}

	public Date getUpdate_time() {
		return update_time;
	}

	public void setUpdate_time(Date update_time) {
		this.update_time = update_time;
	}
}
