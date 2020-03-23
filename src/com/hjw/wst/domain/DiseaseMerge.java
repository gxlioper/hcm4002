package com.hjw.wst.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 疾病合并
 * 
 * @Title: 火箭蛙体检管理系统
 * @Package com.hjw.wst.domain
 * @Description:
 * @author: zr
 * @date: 2017年5月5日 下午2:22:14
 * @version V2.0.0.0
 */
@Entity
@Table(name = "disease_merge")
public class DiseaseMerge implements Serializable {

	/**
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	 */
	
	private static final long serialVersionUID = 1L;
	@Id
	@GenericGenerator(name = "idGenerator", strategy = "identity")
	@GeneratedValue(generator = "idGenerator")
	private long id;
	
	@Column(name="before_disease_id")
	private String before_disease_id;
	
	@Column(name="later_disease_id")
	private long later_disease_id;
	
	@Column(name="name")
	private String name;
	
	@Column(name="creater")
	private long creater;
	
	@Column(name="create_time")
	private Date create_time;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getBefore_disease_id() {
		return before_disease_id;
	}

	public void setBefore_disease_id(String before_disease_id) {
		this.before_disease_id = before_disease_id;
	}

	public long getLater_disease_id() {
		return later_disease_id;
	}

	public void setLater_disease_id(long later_disease_id) {
		this.later_disease_id = later_disease_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

}
