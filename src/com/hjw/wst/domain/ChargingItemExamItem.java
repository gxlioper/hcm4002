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
     * @Description: 收费项目--收费项目---检查项目关系表 
     * @author: 张瑞     
     * @date:   2016年9月24日 上午9:01:21   
     * @version V2.0.0.0
 */
@Entity
@Table(name = "charging_item_exam_item")
public class ChargingItemExamItem  implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	

	@Id
	@GenericGenerator(name = "idGenerator", strategy = "identity")
	@GeneratedValue(generator = "idGenerator")
	private long	id;
	
	@Column(name="charging_item_id")
	private long	charging_item_id;
	
	@Column(name="exam_item_id")
	private long	exam_item_id;
	
	@Column(name="creater")
	private long	creater;
	
	@Column(name="create_time")
	private Date	create_time;
	
	@Column(name="updater")
	private long	updater;
	
	@Column(name="update_time")
	private Date	update_time;
	
	@Column(name="seq_code")
	private long	seq_code;
	
	@Column(name="charging_item_code")
	private String	charging_item_code;
	
	@Column(name="item_code")
	private String	item_code;

	public String getItem_code() {
		return item_code;
	}

	public void setItem_code(String item_code) {
		this.item_code = item_code;
	}

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

	public long getSeq_code() {
		return seq_code;
	}

	public void setSeq_code(long seq_code) {
		this.seq_code = seq_code;
	}
	
	
}
