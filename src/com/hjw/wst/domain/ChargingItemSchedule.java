package com.hjw.wst.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 项目排期实体类
 * 
 * @Title: 火箭蛙体检管理系统
 * @Package com.hjw.wst.domain
 * @Description:
 * @author: yangm
 * @date: 2017年11月7日 下午5:05:07
 * @version V2.0.0.0
 */
@Entity
@Table(name = "charging_item_schedule")
public class ChargingItemSchedule {
	
	@Id
	@GenericGenerator(name = "idGenerator", strategy = "identity")
	@GeneratedValue(generator = "idGenerator")
	private long id;


	@Column(name="charging_item_id")
	private long charging_item_id;
	
	@Column(name="schedule_time")
	private Date schedule_time;
	
	@Column(name="schedule_number")
	private long schedule_number;
	
	@Column(name="creater")
	private long creater;
	
	@Column(name="create_time")
	private Date create_time;
	
	@Column(name="updater")
	private long updater;
	
	@Column(name="update_time")
	
	private Date update_time;
	
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
	public long getCharging_item_id() {
		return charging_item_id;
	}
	public void setCharging_item_id(long charging_item_id) {
		this.charging_item_id = charging_item_id;
	}
	public Date getSchedule_time() {
		return schedule_time;
	}
	public void setSchedule_time(Date schedule_time) {
		this.schedule_time = schedule_time;
	}
	public long getSchedule_number() {
		return schedule_number;
	}
	public void setSchedule_number(long schedule_number) {
		this.schedule_number = schedule_number;
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
