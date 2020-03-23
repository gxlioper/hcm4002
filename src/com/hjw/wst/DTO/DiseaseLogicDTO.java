package com.hjw.wst.DTO;

import java.util.List;

/**
 * 
 * @Title: 火箭蛙体检管理系统
 * @Package com.hjw.wst.DTO
 * @Description: 疾病逻辑
 * @author: zr
 * @date: 2016年12月7日 上午9:33:24
 * @version V2.0.0.0
 */
public class DiseaseLogicDTO implements java.io.Serializable {
	private static final long serialVersionUID = 7233956517893154249L;
	private long id;
	private long disease_id;
	private String ids;
	private String logic_num;
	private String logic_name;
	private String logic_type;
	private String logic_type_s;//类型转
	private String sex;
	private String isActive;
	private long logic_class; //--单项阳性逻辑类型 0逻辑关联检查项目，1逻辑关联普通科室或影像检查按照科室保存的结果，2逻辑关联收费项目（影像类的）
	private long creater;
	private String create_time;
	private long updater;
	private String update_time;
	private long critical_flag;
	private String age_min;
	private String age_max;
	private String chi_name;
	//疾病知识
	private String disease_name;//疾病名称
	private String disease_type_s;//疾病类型
	private String disease_num;//疾病编码
	
	private String tiaojian;//条件
	private String condition_value;//条件值
	private String condition;//条件
	private String item_name;//检查项目名称
	private String item_num;
	//疾病和检查项目关系
	private String ageMinMax;//适用年龄范围
	private List<DiseaseLogicItemDTO> logicItem;
	
	public List<DiseaseLogicItemDTO> getLogicItem() {
		return logicItem;
	}
	public void setLogicItem(List<DiseaseLogicItemDTO> logicItem) {
		this.logicItem = logicItem;
	}
	public long getLogic_class() {
		return logic_class;
	}
	public void setLogic_class(long logic_class) {
		this.logic_class = logic_class;
	}
	public String getItem_num() {
		return item_num;
	}
	public void setItem_num(String item_num) {
		this.item_num = item_num;
	}
	public String getIds() {
		return ids;
	}
	public void setIds(String ids) {
		this.ids = ids;
	}
	public String getLogic_type_s() {
		return logic_type_s;
	}
	public String getChi_name() {
		return chi_name;
	}
	public void setChi_name(String chi_name) {
		this.chi_name = chi_name;
	}
	public String getAge_min() {
		return age_min;
	}
	public void setAge_min(String age_min) {
		this.age_min = age_min;
	}
	public String getAge_max() {
		return age_max;
	}
	public void setAge_max(String age_max) {
		this.age_max = age_max;
	}
	public String getDisease_name() {
		return disease_name;
	}
	public void setDisease_name(String disease_name) {
		this.disease_name = disease_name;
	}
	public String getDisease_type_s() {
		return disease_type_s;
	}
	public void setDisease_type_s(String disease_type_s) {
		this.disease_type_s = disease_type_s;
	}
	public void setLogic_type_s(String logic_type_s) {
		this.logic_type_s = logic_type_s;
	}
	public String getCondition_value() {
		return condition_value;
	}
	public void setCondition_value(String condition_value) {
		this.condition_value = condition_value;
	}
	public String getCondition() {
		return condition;
	}
	public void setCondition(String condition) {
		this.condition = condition;
	}
	public String getItem_name() {
		return item_name;
	}
	public void setItem_name(String item_name) {
		this.item_name = item_name;
	}
	public String getTiaojian() {
		return tiaojian;
	}
	public void setTiaojian(String tiaojian) {
		this.tiaojian = tiaojian;
	}
	public String getDisease_num() {
		return disease_num;
	}
	public void setDisease_num(String disease_num) {
		this.disease_num = disease_num;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getDisease_id() {
		return disease_id;
	}
	public void setDisease_id(long disease_id) {
		this.disease_id = disease_id;
	}
	public String getLogic_num() {
		return logic_num;
	}
	public void setLogic_num(String logic_num) {
		this.logic_num = logic_num;
	}
	public String getLogic_name() {
		return logic_name;
	}
	public void setLogic_name(String logic_name) {
		this.logic_name = logic_name;
	}
	public String getLogic_type() {
		return logic_type;
	}
	public void setLogic_type(String logic_type) {
		this.logic_type = logic_type;
		if("Y".equals(logic_type)){
			this.setLogic_type_s("阳性指标");
		}else{
			setLogic_type_s("疾病");
		}
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getIsActive() {
		return isActive;
	}
	public void setIsActive(String isActive) {
		this.isActive = isActive;
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
	public long getCritical_flag() {
		return critical_flag;
	}
	public void setCritical_flag(long critical_flag) {
		this.critical_flag = critical_flag;
	}
	public String getAgeMinMax() {
		return ageMinMax;
	}
	public void setAgeMinMax(String ageMinMax) {
		this.ageMinMax = ageMinMax;
	}

	
}
