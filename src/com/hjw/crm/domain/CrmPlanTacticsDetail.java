package com.hjw.crm.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "crm_plan_tactics_detail")//	健康计划策略表明细crm_plan_tactics_detail
public class CrmPlanTacticsDetail {
	@Id
	@GenericGenerator(name = "idGenerator", strategy = "identity")
	@GeneratedValue(generator = "idGenerator")
	private long id;
	
	@Column(name = "")
	private String tactics_num;//策略编码   对应策略表crm_plan_tactics 的tactics_num  
	
	@Column(name = "notices")
	private String notices;//回访内容描述
	
	@Column(name = "plan_doctor_id")
	private long plan_doctor_id;//回访医生
	
	@Column(name = "distancedate")
	private int distancedate;//距离体检几天回访
	
	@Column(name = "creater")
	private long creater;
	
	@Column(name = "create_date")
	private Date create_date;
	
 	@Column(name = "updater")
 	private long updater;
	    
    @Column(name = "update_date")
    private Date update_date;

	public long getId() {
		return id;
	}

	public String getTactics_num() {
		return tactics_num;
	}

	public String getNotices() {
		return notices;
	}

	public long getPlan_doctor_id() {
		return plan_doctor_id;
	}

	public int getDistancedate() {
		return distancedate;
	}

	public long getCreater() {
		return creater;
	}

	public Date getCreate_date() {
		return create_date;
	}

	public long getUpdater() {
		return updater;
	}

	public Date getUpdate_date() {
		return update_date;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setTactics_num(String tactics_num) {
		this.tactics_num = tactics_num;
	}

	public void setNotices(String notices) {
		this.notices = notices;
	}

	public void setPlan_doctor_id(long plan_doctor_id) {
		this.plan_doctor_id = plan_doctor_id;
	}

	public void setDistancedate(int distancedate) {
		this.distancedate = distancedate;
	}

	public void setCreater(long creater) {
		this.creater = creater;
	}

	public void setCreate_date(Date create_date) {
		this.create_date = create_date;
	}

	public void setUpdater(long updater) {
		this.updater = updater;
	}

	public void setUpdate_date(Date update_date) {
		this.update_date = update_date;
	}


}
