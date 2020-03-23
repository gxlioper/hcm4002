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
     * @Description: 检查项目文本 
     * @author: 张瑞     
     * @date:   2016年9月21日 上午9:16:27   
     * @version V2.0.0.0
 */
@Entity
@Table(name ="exam_item_RefandDang")
public class ExamItemRefandDang implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GenericGenerator(name = "idGenerator", strategy = "identity")
	@GeneratedValue(generator = "idGenerator")
	private long id;
	
	@Column(name="exam_item_id" )
	private long exam_item_id;
	
	@Column(name = "is_ReforDang")
	private String is_ReforDang;
	
	@Column(name = "val_info")
	private String val_info;
	
	@Column(name = "val_index")
	private long val_index;
	
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

	public long getExam_item_id() {
		return exam_item_id;
	}

	public void setExam_item_id(long exam_item_id) {
		this.exam_item_id = exam_item_id;
	}

	public String getIs_ReforDang() {
		return is_ReforDang;
	}

	public void setIs_ReforDang(String is_ReforDang) {
		this.is_ReforDang = is_ReforDang;
	}

	public String getVal_info() {
		return val_info;
	}

	public void setVal_info(String val_info) {
		this.val_info = val_info;
	}

	public long getVal_index() {
		return val_index;
	}

	public void setVal_index(long val_index) {
		this.val_index = val_index;
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
