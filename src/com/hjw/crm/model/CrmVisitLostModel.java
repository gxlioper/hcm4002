package com.hjw.crm.model;

import java.util.Date;
import java.util.List;

import com.hjw.crm.domain.CrmPlanTacticsDetail;


public class CrmVisitLostModel {

	private String id;

	private String arch_num;

	private Long doctor_id;

	private String create_time;
	private String exam_num;
	private Long flag;
	private String user_name;
	private String tactics_num;
	private String notices;
	private int tactics_type;
	private String rmark;
	private long tactics_id;
	private Long plan_doctor_id;
	private int distancedate; 
	private long tactics_detail_id;
	private String tactics_detail_ids;
	private String tactics_nums;
	private String tactics_notices;
	private String visit_status;
	private String tacticsDetailList;
	private String visit_important;
	private String cvr_id;
	public String getExam_num() {
		return exam_num;
	}

	public void setExam_num(String exam_num) {
		this.exam_num = exam_num;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getArch_num() {
		return arch_num;
	}

	public void setArch_num(String arch_num) {
		this.arch_num = arch_num;
	}

	public Long getDoctor_id() {
		return doctor_id;
	}

	public void setDoctor_id(Long doctor_id) {
		this.doctor_id = doctor_id;
	}

	public String getCreate_time() {
		return create_time;
	}

	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}

	public Long getFlag() {
		return flag;
	}

	public void setFlag(Long flag) {
		this.flag = flag;
	}

	public String getTactics_num() {
		return tactics_num;
	}

	public String getNotices() {
		return notices;
	}

	public int getTactics_type() {
		return tactics_type;
	}

	public String getRmark() {
		return rmark;
	}

	public void setTactics_num(String tactics_num) {
		this.tactics_num = tactics_num;
	}

	public void setNotices(String notices) {
		this.notices = notices;
	}

	public void setTactics_type(int tactics_type) {
		this.tactics_type = tactics_type;
	}

	public void setRmark(String rmark) {
		this.rmark = rmark;
	}

	public long getTactics_id() {
		return tactics_id;
	}

	public void setTactics_id(long tactics_id) {
		this.tactics_id = tactics_id;
	}

	public Long getPlan_doctor_id() {
		return plan_doctor_id;
	}

	public void setPlan_doctor_id(Long plan_doctor_id) {
		this.plan_doctor_id = plan_doctor_id;
	}

	public int getDistancedate() {
		return distancedate;
	}

	public void setDistancedate(int distancedate) {
		this.distancedate = distancedate;
	}

	public long getTactics_detail_id() {
		return tactics_detail_id;
	}

	public void setTactics_detail_id(long tactics_detail_id) {
		this.tactics_detail_id = tactics_detail_id;
	}

	public String getTactics_detail_ids() {
		return tactics_detail_ids;
	}

	public void setTactics_detail_ids(String tactics_detail_ids) {
		this.tactics_detail_ids = tactics_detail_ids;
	}

	public String getTactics_nums() {
		return tactics_nums;
	}

	public void setTactics_nums(String tactics_nums) {
		this.tactics_nums = tactics_nums;
	}

	public String getTactics_notices() {
		return tactics_notices;
	}

	public String getVisit_status() {
		return visit_status;
	}

	public String getTacticsDetailList() {
		return tacticsDetailList;
	}

	public void setTactics_notices(String tactics_notices) {
		this.tactics_notices = tactics_notices;
	}

	public void setVisit_status(String visit_status) {
		this.visit_status = visit_status;
	}

	public void setTacticsDetailList(String tacticsDetailList) {
		this.tacticsDetailList = tacticsDetailList;
	}

	public String getVisit_important() {
		return visit_important;
	}

	public void setVisit_important(String visit_important) {
		this.visit_important = visit_important;
	}

	public String getCvr_id() {
		return cvr_id;
	}

	public void setCvr_id(String cvr_id) {
		this.cvr_id = cvr_id;
	}

	
}
