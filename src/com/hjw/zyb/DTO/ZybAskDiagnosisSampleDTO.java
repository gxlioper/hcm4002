package com.hjw.zyb.DTO;

import java.util.List;

public class ZybAskDiagnosisSampleDTO {
	private long id;
	private String name;
	private String sub_name;
	private long seq_code; 
	private String type;
	private String types;
	private String temp_content;
	private String is_active;
	private String updater;
	private String update_time;
	private String sex;
	private String sex_name;
	
	private List<ZybCareerInquisitionItemDTO> careerItemList;
	
	
	
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
		if("1".equals(sex)){
			this.setSex_name("男");
		}else if("2".equals(sex)){
			this.setSex_name("女");
		} else {
			this.setSex_name("全部");
		}
	}
	public String getSex_name() {
		return sex_name;
	}
	public void setSex_name(String sex_name) {
		this.sex_name = sex_name;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSub_name() {
		return sub_name;
	}
	public void setSub_name(String sub_name) {
		this.sub_name = sub_name;
	}
	public long getSeq_code() {
		return seq_code;
	}
	public void setSeq_code(long seq_code) {
		this.seq_code = seq_code;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
		if("1".equals(type)){
			this.types = "职业健康检查";
		}else if("2".equals(type)){
			this.types = "放射健康检查";
		}
	}
	public String getTypes() {
		return types;
	}
	public void setTypes(String types) {
		this.types = types;
	}
	public String getTemp_content() {
		return temp_content;
	}
	public void setTemp_content(String temp_content) {
		this.temp_content = temp_content;
	}
	public String getIs_active() {
		return is_active;
	}
	public void setIs_active(String is_active) {
		this.is_active = is_active;
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
	public List<ZybCareerInquisitionItemDTO> getCareerItemList() {
		return careerItemList;
	}
	public void setCareerItemList(List<ZybCareerInquisitionItemDTO> careerItemList) {
		this.careerItemList = careerItemList;
	}
}
