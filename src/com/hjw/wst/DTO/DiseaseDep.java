package com.hjw.wst.DTO;

/**
 * 科室疾病维护
 * 
 * @Title: 火箭蛙体检管理系统
 * @Package com.hjw.wst.DTO
 * @Description:
 * @author: zr
 * @date: 2017年5月12日 上午10:54:23
 * @version V2.0.0.0
 */
public class DiseaseDep {
	private long id;
	private long disease_id;
	private long dep_id;
	private String charging_item_ids;
	private String exam_item_ids;
	private long creater;
	private String create_time;
	private long updater;
	private String update_time;

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

	public long getDep_id() {
		return dep_id;
	}

	public void setDep_id(long dep_id) {
		this.dep_id = dep_id;
	}

	public String getCharging_item_ids() {
		return charging_item_ids;
	}

	public void setCharging_item_ids(String charging_item_ids) {
		this.charging_item_ids = charging_item_ids;
	}

	public String getExam_item_ids() {
		return exam_item_ids;
	}

	public void setExam_item_ids(String exam_item_ids) {
		this.exam_item_ids = exam_item_ids;
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

}
