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
     * @Description:  资源管理
     * @author: zr     
     * @date:   2016年12月15日 下午4:28:08   
     * @version V2.0.0.0
 */
@Entity
@Table(name="WEB_RESRELATIONSHIP")
public class WebResrelAtionship implements java.io.Serializable{

	private static final long serialVersionUID = -1825778688215944470L;
	@Id
	@GenericGenerator(name = "idGenerator", strategy = "identity")
	@GeneratedValue(generator = "idGenerator")
	private 	long id;
	
	@Column(name="res_code")
	private 	String  res_code;
	
	@Column(name="res_type")
	private 	String res_type;
	
	@Column(name="datavalue")
	private 	String datavalue;
	
	@Column(name="remark")
	private 	String remark;
	
	@Column(name="create_time")
	private 	Date create_time;
	
	@Column(name="creater")
	private 	long creater;
	
	@Column(name="updater")
	private 	long updater;
	
	@Column(name="update_time")
	private 	Date update_time;
	
	@Column(name="userorroleid")
	private 	String userorroleid;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getRes_code() {
		return res_code;
	}
	public void setRes_code(String res_code) {
		this.res_code = res_code;
	}
	public String getRes_type() {
		return res_type;
	}
	public void setRes_type(String res_type) {
		this.res_type = res_type;
	}
	public String getDatavalue() {
		return datavalue;
	}
	public void setDatavalue(String datavalue) {
		this.datavalue = datavalue;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Date getCreate_time() {
		return create_time;
	}
	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}
	public long getCreater() {
		return creater;
	}
	public void setCreater(long creater) {
		this.creater = creater;
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
	public String getUserorroleid() {
		return userorroleid;
	}
	public void setUserorroleid(String userorroleid) {
		this.userorroleid = userorroleid;
	}
	
	
}
