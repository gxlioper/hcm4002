package com.hjw.wst.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.axis2.databinding.types.soapencoding.Array;
import org.hibernate.annotations.GenericGenerator;
/**
 * 危急值逻辑条件
     * @Title:  火箭蛙体检管理系统   
     * @Package com.hjw.wst.domain   
     * @Description:  
     * @author: yangm     
     * @date:   2019年7月16日 上午10:24:41   
     * @version V2.0.0.0
 */
@Entity
@Table(name = "exam_Critical_logic_item")
public class ExamCriticalLogicItem implements Serializable{
	/**   
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)   
	 */ 
	private static final long serialVersionUID = 1L;
	@Id
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
	@Column(name = "id", unique = true, nullable = false,length = 50)
	private String id;
	private String logic_id;
	private String logic_item_name;
	private long logic_index;
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getLogic_id() {
		return logic_id;
	}
	public void setLogic_id(String logic_id) {
		this.logic_id = logic_id;
	}
	public String getLogic_item_name() {
		return logic_item_name;
	}
	public void setLogic_item_name(String logic_item_name) {
		this.logic_item_name = logic_item_name;
	}
	public long getLogic_index() {
		return logic_index;
	}
	public void setLogic_index(long logic_index) {
		this.logic_index = logic_index;
	}
	
	
}
