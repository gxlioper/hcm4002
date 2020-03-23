package com.hjw.wst.model;

import java.util.Date;
/**
 * 
     * @Title:  火箭蛙体检管理系统   
     * @Package com.hjw.wst.model   
     * @Description: 报告样本 
     * @author: 张瑞     
     * @date:   2016年8月25日 上午10:58:57   
     * @version V2.0.0.0
 */
public class SampleReportDemoModel implements java.io.Serializable{
	private static final long serialVersionUID = 6491512652670118064L;
	private long id;
	
	private String demo_name;
	
	private String demo_num;
	
	private Long print_seq;
	
	private String remark;
	
	private String is_Active;
	
	private long creater;
	
	private Date create_time;
	
	private long updater;
	
	private Date update_time;
	
	private Long isPrint_BarCode;
	
	private  String startStop;
	

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getDemo_name() {
		return demo_name;
	}

	public void setDemo_name(String demo_name) {
		this.demo_name = demo_name;
	}

	public String getDemo_num() {
		return demo_num;
	}

	public void setDemo_num(String demo_num) {
		this.demo_num = demo_num;
	}

	public Long getPrint_seq() {
		return print_seq;
	}

	public void setPrint_seq(Long print_seq) {
		this.print_seq = print_seq;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getIs_Active() {
		return is_Active;
	}

	public void setIs_Active(String is_Active) {
		this.is_Active = is_Active;
	}

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

	public Long getIsPrint_BarCode() {
		return isPrint_BarCode;
	}

	public void setIsPrint_BarCode(Long isPrint_BarCode) {
		this.isPrint_BarCode = isPrint_BarCode;
	}

	public String getStartStop() {
		return startStop;
	}

	public void setStartStop(String startStop) {
		this.startStop = startStop;
	}

	
}
