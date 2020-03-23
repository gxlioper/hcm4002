package com.hjw.wst.domain;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 套餐类别维护功能
     * @Title:  火箭蛙体检管理系统   
     * @Package com.hjw.crm.DTO   
     * @Description:  
     * @author: zr     
     * @date:   2017年10月27日 上午10:33:43   
     * @version V2.0.0.0
 */
@Entity
@Table(name = "exam_set_type")
public class ExamSetType implements java.io.Serializable  {
	/**   
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)   
	 */ 
	private static final long serialVersionUID = 1L;
	@Id
	@GenericGenerator(name = "idGenerator", strategy = "identity")
	@GeneratedValue(generator = "idGenerator")
	public long  id;
	
	@Column(name = "set_type_name")
	public String set_type_name;
	
	@Column(name = "is_Active")
	public String is_Active;
	
	@Column(name = "set_class")
	public long set_class;
	
	@Column(name = "creater")
	public long creater;
	
	@Column(name = "create_time")
	public Date create_time;
	
	@Column(name = "updater")
	public long updater;
	
	@Column(name = "update_time")
	public Date update_time;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getSet_type_name() {
		return set_type_name;
	}

	public void setSet_type_name(String set_type_name) {
		this.set_type_name = set_type_name;
	}

	public String getIs_Active() {
		return is_Active;
	}

	public void setIs_Active(String is_Active) {
		this.is_Active = is_Active;
	}

	public long getSet_class() {
		return set_class;
	}

	public void setSet_class(long set_class) {
		this.set_class = set_class;
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
