package com.hjw.wst.DTO;

import java.util.List;

/**
 * 
 * @Title: 火箭蛙体检管理系统
 * @Package com.hjw.wst.domain
 * @Description: 科室逻辑
 * @author: zr
 * @date: 2016年12月13日 上午9:13:11
 * @version V2.0.0.0
 */
public class DepLogicDTO implements java.io.Serializable {

	/**   
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)   
	 */ 
	private static final long serialVersionUID = -4621063889365927871L;

	private long id;

	private long dep_id;

	private String conclusion_word;

	private long creater;

	private String create_time;

	private long updater;

	private String update_time;
	
	private String tiaojian;
	
	private String dep_name;
	private String sex;
	
	private List<DepLogicExamItemDTO> logic_item;

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getDep_id() {
		return dep_id;
	}

	public void setDep_id(long dep_id) {
		this.dep_id = dep_id;
	}

	public String getConclusion_word() {
		return conclusion_word;
	}

	public void setConclusion_word(String conclusion_word) {
		this.conclusion_word = conclusion_word;
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

	public String getTiaojian() {
		return tiaojian;
	}

	public void setTiaojian(String tiaojian) {
		this.tiaojian = tiaojian;
	}

	public String getDep_name() {
		return dep_name;
	}

	public void setDep_name(String dep_name) {
		this.dep_name = dep_name;
	}

	public List<DepLogicExamItemDTO> getLogic_item() {
		return logic_item;
	}

	public void setLogic_item(List<DepLogicExamItemDTO> logic_item) {
		this.logic_item = logic_item;
	}
}
