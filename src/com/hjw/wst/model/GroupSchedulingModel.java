package com.hjw.wst.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 排期
     * @Title:  火箭蛙体检管理系统   
     * @Package com.hjw.wst.domain   
     * @Description:  
     * @author: zr     
     * @date:   2017年7月6日 上午11:33:18   
     * @version V2.0.0.0
 */
public class GroupSchedulingModel {
	
	 private long id;
	
     private String scheduling_content;
	
     private long creater;
	
     private String create_time;
	
     private long updater;
	
     private String update_time;
	
     private String scheduling_time;
	
     private String scheduling_end_time;
     
     private String title;
     
     private String significance;
     
     
     
     
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSignificance() {
		return significance;
	}
	public void setSignificance(String significance) {
		this.significance = significance;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getScheduling_content() {
		return scheduling_content;
	}
	public void setScheduling_content(String scheduling_content) {
		this.scheduling_content = scheduling_content;
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
	public String getScheduling_time() {
		return scheduling_time;
	}
	public void setScheduling_time(String scheduling_time) {
		this.scheduling_time = scheduling_time;
	}
	public String getScheduling_end_time() {
		return scheduling_end_time;
	}
	public void setScheduling_end_time(String scheduling_end_time) {
		this.scheduling_end_time = scheduling_end_time;
	}
     
     
}
