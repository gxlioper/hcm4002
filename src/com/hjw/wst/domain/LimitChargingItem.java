package com.hjw.wst.domain;

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
@Entity
@Table(name="limit_charging_item")
public class LimitChargingItem  implements  Serializable{
	private static final long serialVersionUID = -2446127075245762772L;
	@Id
	@GenericGenerator(name = "idGenerator", strategy = "identity")
	@GeneratedValue(generator = "idGenerator")
	  private  long   id;
	
	  @Column(name="charging_item_id")
	  private  long   charging_item_id;
	  
	  @Column(name="limit_count")
	  private  long   limit_count;
	  
	  @Column(name="center_num")
	  private  String   center_num;
	  
	  @Column(name="creater")
	  private  long   creater;
	  
	  @Column(name="create_time")
	  private  Date   create_time;
	  
	  @Column(name="updater")
	  private  long   updater;
	  
	  @Column(name="update_time")
	  private  Date   update_time;
	  
	  @Column(name="charging_item_code")
	  private  String   charging_item_code;
	  
	public String getCharging_item_code() {
		return charging_item_code;
	}
	public void setCharging_item_code(String charging_item_code) {
		this.charging_item_code = charging_item_code;
	}
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
	public long getCreater() {
		return creater;
	}
	public Date getCreate_time() {
		return create_time;
	}
	public long getUpdater() {
		return updater;
	}
	public Date getUpdate_time() {
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
	public void setCreater(long creater) {
		this.creater = creater;
	}
	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}
	public void setUpdater(long updater) {
		this.updater = updater;
	}
	public void setUpdate_time(Date update_time) {
		this.update_time = update_time;
	}
	  
	  
}
