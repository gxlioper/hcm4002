package com.hjw.wst.DTO;


import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

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
public class SampleDemoChargingItemDTO implements java.io.Serializable {

	private static final long serialVersionUID = 4783399711232352000L;

	private long id;
	
	private long sample_demo_id;
	
	private long charging_id;
	
	private long creater;
	
	private String create_time;
	
	private String item_code;
	private String item_name;

	
	public String getItem_code() {
		return item_code;
	}

	public void setItem_code(String item_code) {
		this.item_code = item_code;
	}

	public String getItem_name() {
		return item_name;
	}

	public void setItem_name(String item_name) {
		this.item_name = item_name;
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

	public String getCreate_time() {
		return create_time;
	}

	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}

	

}
