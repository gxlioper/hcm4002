package com.hjw.wst.model;

import com.opensymphony.util.Data;

/**
 * 
 * @Title: 火箭蛙体检管理系统
 * @Package com.hjw.wst.domain
 * @Description: 检验报告样本
 * @author: 张瑞
 * @date: 2016年9月5日 上午9:30:37
 * @version V2.0.0.0
 */
public class SampleDemoModel implements java.io.Serializable {
	
	private static final long serialVersionUID = 4408307705811554013L;

	private long id;

	private String demo_num;

	private String demo_name;

	private String demo_category;

	private String demo_indicator;

	private String demo_color;

	private Long print_seq;

	private Long print_copy;

	private String remark;

	private String isActive;

	private long creater;

	private Data create_time;

	private long updater;

	private Data update_time;
	
	private Long isPrint_BarCode;
	
	private long barCode_Class;
	
	private long charging_id;
	
	private String item_name_s;
	
	private long Sample_demo_id;
	
	private String charging_id_s;
	
	private long print_dep;
	
	private int demo_type;
	
	private String startStop;
	
	private int req_print_num;
	private int isPrint_req;
	
	private String ids;

	public long getPrint_dep() {
		return print_dep;
	}

	public void setPrint_dep(long print_dep) {
		this.print_dep = print_dep;
	}

	public String getCharging_id_s() {
		return charging_id_s;
	}

	public void setCharging_id_s(String charging_id_s) {
		this.charging_id_s = charging_id_s;
	}

	public long getSample_demo_id() {
		return Sample_demo_id;
	}

	public void setSample_demo_id(long sample_demo_id) {
		Sample_demo_id = sample_demo_id;
	}

	public String getItem_name_s() {
		return item_name_s;
	}

	public void setItem_name_s(String item_name_s) {
		this.item_name_s = item_name_s;
	}

	public long getCharging_id() {
		return charging_id;
	}

	public void setCharging_id(long charging_id) {
		this.charging_id = charging_id;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getDemo_num() {
		return demo_num;
	}

	public void setDemo_num(String demo_num) {
		this.demo_num = demo_num;
	}

	public String getDemo_name() {
		return demo_name;
	}

	public void setDemo_name(String demo_name) {
		this.demo_name = demo_name;
	}

	public String getDemo_category() {
		return demo_category;
	}

	public void setDemo_category(String demo_category) {
		this.demo_category = demo_category;
	}

	public String getDemo_indicator() {
		return demo_indicator;
	}

	public void setDemo_indicator(String demo_indicator) {
		this.demo_indicator = demo_indicator;
	}

	public String getDemo_color() {
		return demo_color;
	}

	public void setDemo_color(String demo_color) {
		this.demo_color = demo_color;
	}

	public Long getPrint_seq() {
		return print_seq;
	}

	public void setPrint_seq(Long print_seq) {
		this.print_seq = print_seq;
	}

	public Long getPrint_copy() {
		return print_copy;
	}

	public void setPrint_copy(Long print_copy) {
		this.print_copy = print_copy;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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

	public long getUpdater() {
		return updater;
	}

	public void setUpdater(long updater) {
		this.updater = updater;
	}

	public Data getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Data create_time) {
		this.create_time = create_time;
	}

	public Data getUpdate_time() {
		return update_time;
	}

	public void setUpdate_time(Data update_time) {
		this.update_time = update_time;
	}

	public Long getIsPrint_BarCode() {
		return isPrint_BarCode;
	}

	public void setIsPrint_BarCode(Long isPrint_BarCode) {
		this.isPrint_BarCode = isPrint_BarCode;
	}

	public long getBarCode() {
		return barCode_Class;
	}

	public void setBarCode(long barCode_Class) {
		this.barCode_Class = barCode_Class;
	}

	public int getDemo_type() {
		return demo_type;
	}

	public void setDemo_type(int demo_type) {
		this.demo_type = demo_type;
	}

	public String getStartStop() {
		return startStop;
	}

	public void setStartStop(String startStop) {
		this.startStop = startStop;
	}

	public int getReq_print_num() {
		return req_print_num;
	}

	public void setReq_print_num(int req_print_num) {
		this.req_print_num = req_print_num;
	}

	public int getIsPrint_req() {
		return isPrint_req;
	}

	public void setIsPrint_req(int isPrint_req) {
		this.isPrint_req = isPrint_req;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}
	
}
