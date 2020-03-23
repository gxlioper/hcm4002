package com.hjw.wst.DTO;

public class BatchFlowLogDTO implements java.io.Serializable {
	
	
	private static final long serialVersionUID = -3637072187991699258L;
	private long id;
	private long batch_id;
	private long group_id;
	private long creater;
	private String creaters;
	private String createdate;
	private int flow_type;//1 审核2取消审核，3封帐4取消封帐5 结帐
	private String flow_types;
	private String remark;
	private String notes;
	private String center_num;
	 
	public String getCreaters() {
		return creaters;
	}
	public void setCreaters(String creaters) {
		this.creaters = creaters;
	}
	public String getFlow_types() {
		return flow_types;
	}
	public void setFlow_types(String flow_types) {
		this.flow_types = flow_types;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getBatch_id() {
		return batch_id;
	}
	public void setBatch_id(long batch_id) {
		this.batch_id = batch_id;
	}
	public long getGroup_id() {
		return group_id;
	}
	public void setGroup_id(long group_id) {
		this.group_id = group_id;
	}
	public long getCreater() {
		return creater;
	}
	public void setCreater(long creater) {
		this.creater = creater;
	}
	public String getCreatedate() {
		return createdate;
	}
	public void setCreatedate(String createdate) {
		this.createdate = createdate;
	}
	public int getFlow_type() {
		return flow_type;
	}
	
	public void setFlow_type(int flow_type) {
		this.flow_type = flow_type;////1 审核2取消审核，3封帐4取消封帐5 结帐
		if(flow_type==1){
			this.setFlow_types("审核操作");
		}else if(flow_type==2){
			this.setFlow_types("取消操作");
		}else if(flow_type==3){
			this.setFlow_types("封帐操作");
		}else if(flow_type==4){
			this.setFlow_types("取消封帐");
		}else if(flow_type==5){
			this.setFlow_types("结帐操作");
		}else{
			this.setFlow_types("未知操作");
		}
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	public String getCenter_num() {
		return center_num;
	}
	public void setCenter_num(String center_num) {
		this.center_num = center_num;
	}	 
		 
	}