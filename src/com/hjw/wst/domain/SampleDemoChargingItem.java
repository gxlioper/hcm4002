package com.hjw.wst.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 检验样本和项目关系维护
 * 
 * @Title: 火箭蛙体检管理系统
 * @Package com.hjw.wst.domain
 * @Description:
 * @author: yangm
 * @date: 2017年11月10日 下午4:27:51
 * @version V2.0.0.0
 */
@Entity
@Table(name = "sample_demo_charging_item")
public class SampleDemoChargingItem implements java.io.Serializable {

	private static final long serialVersionUID = 4783399711232352000L;

	@Id
	@GenericGenerator(name = "idGenerator", strategy = "identity")
	@GeneratedValue(generator = "idGenerator")
	private long id;
	
	@Column(name="sample_demo_id")
	private long sample_demo_id;
	
	@Column(name="charging_id")
	private long charging_id;
	
	@Column(name="creater")
	private long creater;
	
	@Column(name="create_time")
	private Date create_time;
	
	@Column(name="charging_item_code")
	private String charging_item_code;

	public String getCharging_item_code() {
		return charging_item_code;
	}

	public void setCharging_item_code(String charging_item_code) {
		this.charging_item_code = charging_item_code;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getSample_demo_id() {
		return sample_demo_id;
	}

	public void setSample_demo_id(long sample_demo_id) {
		this.sample_demo_id = sample_demo_id;
	}

	public long getCharging_id() {
		return charging_id;
	}

	public void setCharging_id(long charging_id) {
		this.charging_id = charging_id;
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
