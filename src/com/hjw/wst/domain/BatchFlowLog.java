package com.hjw.wst.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "batch_flow_log")
public class BatchFlowLog implements java.io.Serializable {
	
	private static final long serialVersionUID = 2754826261954191061L;
	 @Id
	 @GenericGenerator(name = "idGenerator", strategy = "identity")
	 @GeneratedValue(generator = "idGenerator")
	private long id;
	 @Column(name = "batch_id")
	 private long batch_id;
	 @Column(name = "group_id")
	private long group_id;
	 @Column(name = "creater")
	 private long creater;
	 @Column(name = "createdate")
	private String createdate;
	 @Column(name = "flow_type")
	 private int flow_type;//1 审核2取消审核，3封帐4取消封帐5 结帐
	 @Column(name = "remark")
	private String remark;
	 @Column(name = "notes")
	private String notes;
	 @Column(name = "center_num")
	private String center_num;
	 
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
		this.flow_type = flow_type;
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