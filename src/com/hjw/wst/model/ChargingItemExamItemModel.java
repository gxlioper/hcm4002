package com.hjw.wst.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

/**
 * 
     * @Title:  火箭蛙体检管理系统   
     * @Package com.hjw.wst.domain   
     * @Description: 收费项目--检查项目关系表 
     * @author: 张瑞     
     * @date:   2016年9月24日 上午9:01:21   
     * @version V2.0.0.0
 */
public class ChargingItemExamItemModel  implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	
	private long	id;
	
	private long	charging_item_id;
	
	private long	exam_item_id;
	
	private long	creater;
	
	private Date	create_time;
	
	private long	updater;
	
	private Date	update_time;

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

	public long getExam_item_id() {
		return exam_item_id;
	}

	public void setExam_item_id(long exam_item_id) {
		this.exam_item_id = exam_item_id;
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
