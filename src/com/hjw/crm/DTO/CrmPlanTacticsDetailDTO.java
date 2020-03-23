package com.hjw.crm.DTO;


public class CrmPlanTacticsDetailDTO {
	
	private long id;
	
	private String tactics_num;//策略编码   对应策略表crm_plan_tactics 的tactics_num  
	
	private String notices;//回访内容描述
	
	private String plan_doctor;//回访医生
	
	private int distancedate;//距离体检几天回访
	
	private String creater;
	
	private String create_date;
	
 	private String updater;
	    
    private String update_date;
    
    private long plan_doctor_id;

	public long getId() {
		return id;
	}

	public String getTactics_num() {
		return tactics_num;
	}

	public String getNotices() {
		return notices;
	}

	public String getPlan_doctor() {
		return plan_doctor;
	}

	public int getDistancedate() {
		return distancedate;
	}

	public String getCreater() {
		return creater;
	}

	public String getCreate_date() {
		return create_date;
	}

	public String getUpdater() {
		return updater;
	}

	public String getUpdate_date() {
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

	public void setPlan_doctor(String plan_doctor) {
		this.plan_doctor = plan_doctor;
	}

	public void setDistancedate(int distancedate) {
		this.distancedate = distancedate;
	}

	public void setCreater(String creater) {
		this.creater = creater;
	}

	public void setCreate_date(String create_date) {
		this.create_date = create_date;
	}

	public void setUpdater(String updater) {
		this.updater = updater;
	}

	public void setUpdate_date(String update_date) {
		this.update_date = update_date;
	}

	public long getPlan_doctor_id() {
		return plan_doctor_id;
	}

	public void setPlan_doctor_id(long plan_doctor_id) {
		this.plan_doctor_id = plan_doctor_id;
	}
    
    

}
