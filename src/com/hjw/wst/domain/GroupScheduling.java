package com.hjw.wst.domain;

import java.util.Date;

import javax.persistence.Column;
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
@Entity
@Table(name = "group_scheduling")
public class GroupScheduling  implements java.io.Serializable {
	
	@Id
	@GenericGenerator(name = "idGenerator", strategy = "identity")
	@GeneratedValue(generator = "idGenerator")
	 private long id;
	
	@Column(name = "scheduling_content")
     private String scheduling_content;
	
	@Column(name = "creater")
     private long creater;
	
	@Column(name = "create_time")
     private Date create_time;
	
	@Column(name = "updater")
     private long updater;
	
	@Column(name = "update_time")
     private Date update_time;
	
	@Column(name = "scheduling_time")
     private Date scheduling_time;
	
	@Column(name = "scheduling_end_time")
     private Date scheduling_end_time;
	
	@Column(name = "title")
	private String title;
	
	@Column(name = "significance")
	private String significance;
	
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
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

	public Date getScheduling_time() {
		return scheduling_time;
	}

	public void setScheduling_time(Date scheduling_time) {
		this.scheduling_time = scheduling_time;
	}

	public Date getScheduling_end_time() {
		return scheduling_end_time;
	}

	public void setScheduling_end_time(Date scheduling_end_time) {
		this.scheduling_end_time = scheduling_end_time;
	}

	public String getSignificance() {
		return significance;
	}

	public void setSignificance(String significance) {
		this.significance = significance;
	}
}
