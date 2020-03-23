package com.hjw.crm.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="huo_vs_hop")
public class HuoVsHop {
	@Id
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
	@Column(name = "id", unique = true, nullable = false,length = 50)
	private String id;
	@Column(name="huo_data_code")
	private String huo_data_code;
	@Column(name="huo_data_name")
	private String huo_data_name;
	@Column(name="hos_data_code")
	private String hos_data_code;
	@Column(name="hos_data_name")
	private String hos_data_name;
	@Column(name="hos_piny")
	private String hos_piny;
	@Column(name="data_type")
	private String data_type;
	@Column(name="creater")
	private long creater;
	@Column(name="create_time")
	private Date create_time;
	@Column(name="updater")
	private long updater;
	@Column(name="update_time")
	private Date update_time;
	
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
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getHuo_data_code() {
		return huo_data_code;
	}
	public void setHuo_data_code(String huo_data_code) {
		this.huo_data_code = huo_data_code;
	}
	public String getHuo_data_name() {
		return huo_data_name;
	}
	public void setHuo_data_name(String huo_data_name) {
		this.huo_data_name = huo_data_name;
	}
	public String getHos_data_code() {
		return hos_data_code;
	}
	public void setHos_data_code(String hos_data_code) {
		this.hos_data_code = hos_data_code;
	}
	public String getHos_data_name() {
		return hos_data_name;
	}
	public void setHos_data_name(String hos_data_name) {
		this.hos_data_name = hos_data_name;
	}
	public String getHos_piny() {
		return hos_piny;
	}
	public void setHos_piny(String hos_piny) {
		this.hos_piny = hos_piny;
	}
	public String getData_type() {
		return data_type;
	}
	public void setData_type(String data_type) {
		this.data_type = data_type;
	}
	

}
