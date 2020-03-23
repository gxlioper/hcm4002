package com.hjw.wst.DTO;

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
public class examItemRefandDangDTO implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	
	private long id;
	
	private long exam_item_id;
	
	private String is_ReforDang;
	
	private String val_info;
	
	private Long val_index;
	
	private long creater;
	
	private String create_time;
	
	private long updater;
	
	private String update_time;

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

	public Long getVal_index() {
		return val_index;
	}

	public void setVal_index(Long val_index) {
		this.val_index = val_index;
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
	
}
