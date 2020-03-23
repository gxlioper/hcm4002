package com.hjw.crm.DTO;

public class HuoVsHopDTO {
	private String id;
	private String huo_data_code;
	private String huo_data_name;
	private String hos_data_code;
	private String hos_data_name;
	private String hos_piny;
	private String data_type;
	private String creater;
	private String creaters;
	private String create_time;
	private String updater;
	private String updaters;
	private String update_time;
	private String item_pinyin;
	
	public String getItem_pinyin() {
		return item_pinyin;
	}
	public void setItem_pinyin(String item_pinyin) {
		this.item_pinyin = item_pinyin;
	}
	public String getCreaters() {
		return creaters;
	}
	public void setCreaters(String creaters) {
		this.creaters = creaters;
	}
	public String getUpdaters() {
		return updaters;
	}
	public void setUpdaters(String updaters) {
		this.updaters = updaters;
	}
	public String getCreater() {
		return creater;
	}
	public void setCreater(String creater) {
		this.creater = creater;
	}
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	public String getUpdater() {
		return updater;
	}
	public void setUpdater(String updater) {
		this.updater = updater;
	}
	public String getUpdate_time() {
		return update_time;
	}
	public void setUpdate_time(String update_time) {
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
		if("1".equals(this.data_type)){
			this.setData_type("检验项目");
		}else if("2".equals(this.data_type)){
			this.setData_type("检查项目");
		}else if("3".equals(this.data_type)){
			this.setData_type("普通项目");
		}
	}
	
}
