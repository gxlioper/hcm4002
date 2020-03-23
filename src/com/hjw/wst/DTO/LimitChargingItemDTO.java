package com.hjw.wst.DTO;

import java.io.Serializable;
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
     * @Description:  体检中心上限
     * @author: zr     
     * @date:   2017年2月20日 下午4:27:48   
     * @version V2.0.0.0
 */
public class LimitChargingItemDTO  implements  Serializable{
	  /**   
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)   
	 */ 
	private static final long serialVersionUID = -3027007364755137613L;

	private  long   id;
	
	  private  long   charging_item_id;
	  
	  private  long   limit_count;
	  
	  private  String   center_num;
	  
	  private  String   creater;
	  
	  private  Date   create_time;
	  
	  private  long   updater;
	  
	  private  String   update_time;

	public long getId() {
		return id;
	}

	public long getCharging_item_id() {
		return charging_item_id;
	}

	public long getLimit_count() {
		return limit_count;
	}

	public String getCenter_num() {
		return center_num;
	}

	public String getCreater() {
		return creater;
	}

	public Date getCreate_time() {
		return create_time;
	}

	public long getUpdater() {
		return updater;
	}

	public String getUpdate_time() {
		return update_time;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setCharging_item_id(long charging_item_id) {
		this.charging_item_id = charging_item_id;
	}

	public void setLimit_count(long limit_count) {
		this.limit_count = limit_count;
	}

	public void setCenter_num(String center_num) {
		this.center_num = center_num;
	}

	public void setCreater(String creater) {
		this.creater = creater;
	}

	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}

	public void setUpdater(long updater) {
		this.updater = updater;
	}

	public void setUpdate_time(String update_time) {
		this.update_time = update_time;
	}
	  
	  
}
