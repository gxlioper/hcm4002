package com.hjw.wst.DTO;

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
public class ChargingItemExamItemDTO  implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	
	private long	id;
	
	private long	charging_item_id;
	
	private long	exam_item_id;//检查项目id
	
	private String exam_item_name;//检查项目名称
	
	private String exam_item_num;//检查项目编码
	
	private long	creater;
	
	private String	create_time;
	
	private long	updater;
	
	private String	update_time;
	
	private long seq_code;
	
	private String	item_code;
	
	private String	item_num;
	
	public String getItem_num() {
		return item_num;
	}

	public void setItem_num(String item_num) {
		this.item_num = item_num;
	}

	public long getSeq_code() {
		return seq_code;
	}

	public String getItem_code() {
		return item_code;
	}

	public void setItem_code(String item_code) {
		this.item_code = item_code;
	}

	public void setSeq_code(long seq_code) {
		this.seq_code = seq_code;
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

	public String getCreate_time() {
		return create_time;
	}

	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}

	public long getUpdater() {
		return updater;
	}

	public void setUpdater(long updater) {
		this.updater = updater;
	}

	public String getUpdate_time() {
		return update_time;
	}

	public void setUpdate_time(String update_time) {
		this.update_time = update_time;
	}

	public String getExam_item_name() {
		return exam_item_name;
	}

	public void setExam_item_name(String exam_item_name) {
		this.exam_item_name = exam_item_name;
	}

	public String getExam_item_num() {
		return exam_item_num;
	}

	public void setExam_item_num(String exam_item_num) {
		this.exam_item_num = exam_item_num;
	}
	
	
}
