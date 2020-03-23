package com.hjw.wst.DTO;

import java.util.List;

public class ViewExamDetailDTO {

	private long id;
	private long exam_info_id;
	private long pacs_id;
	private String exam_result;
	private String exam_desc;
	private String report_picture_path;
	private String exam_doctor;
	private String exam_date;
	private long sample_id;
	private String item_name;
	private String item_code;
	private String pacs_req_code;
	
	private String dep_name;
	private String dep_num;
	private long dep_id;
	private String approver;
	private String approve_date;
	private long d_seq_code;
	private long c_seq_code;
	private String create_time;
	private long inputter;
	private String inputters;
	
	private String viewItems;
	private List<ViewExamItemDTO> viewItem;
	
	private List<ViewExamDetailDTO> lastExamResult;
	
	private String exam_num;
	
	private long exam_item_id;
    private String center_num="";
	private long creater;
	private long updater;
	private String update_time="";
	private String item_num="";
	private String item_category="";	
	private long charging_item_id;	
	private String charging_item_name="";
	private String image_path="";	
	private String charging_item_code="";
	
    public String getPacs_req_code() {
		return pacs_req_code;
	}
	public void setPacs_req_code(String pacs_req_code) {
		this.pacs_req_code = pacs_req_code;
	}
	public long getDep_id() {
		return dep_id;
	}
	public void setDep_id(long dep_id) {
		this.dep_id = dep_id;
	}
	public String getInputters() {
		return inputters;
	}
	public void setInputters(String inputters) {
		this.inputters = inputters;
	}
	public long getInputter() {
		return inputter;
	}
	public void setInputter(long inputter) {
		this.inputter = inputter;
	}
    public String getExam_num() {
		return exam_num;
	}

	public void setExam_num(String exam_num) {
		this.exam_num = exam_num;
	}
		
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getExam_info_id() {
		return exam_info_id;
	}
	public void setExam_info_id(long exam_info_id) {
		this.exam_info_id = exam_info_id;
	}
	public long getPacs_id() {
		return pacs_id;
	}
	public void setPacs_id(long pacs_id) {
		this.pacs_id = pacs_id;
	}
	public String getExam_result() {
		return exam_result;
	}
	public void setExam_result(String exam_result) {
		this.exam_result = exam_result;
	}
	public String getExam_desc() {
		return exam_desc;
	}
	public void setExam_desc(String exam_desc) {
		this.exam_desc = exam_desc;
	}
	public String getReport_picture_path() {
		return report_picture_path;
	}
	public void setReport_picture_path(String report_picture_path) {
		this.report_picture_path = report_picture_path;
	}
	public String getExam_doctor() {
		return exam_doctor;
	}
	public void setExam_doctor(String exam_doctor) {
		this.exam_doctor = exam_doctor;
	}
	public String getExam_date() {
		return exam_date;
	}
	public void setExam_date(String exam_date) {
		this.exam_date = exam_date;
	}
	public List<ViewExamItemDTO> getViewItem() {
		return viewItem;
	}
	public void setViewItem(List<ViewExamItemDTO> viewItem) {
		this.viewItem = viewItem;
	}
	public String getItem_name() {
		return item_name;
	}
	public void setItem_name(String item_name) {
		this.item_name = item_name;
	}
	public long getSample_id() {
		return sample_id;
	}
	public void setSample_id(long sample_id) {
		this.sample_id = sample_id;
	}
	public String getViewItems() {
		return viewItems;
	}
	public void setViewItems(String viewItems) {
		this.viewItems = viewItems;
	}
	public List<ViewExamDetailDTO> getLastExamResult() {
		return lastExamResult;
	}
	public void setLastExamResult(List<ViewExamDetailDTO> lastExamResult) {
		this.lastExamResult = lastExamResult;
	}
	public String getItem_code() {
		return item_code;
	}
	public void setItem_code(String item_code) {
		this.item_code = item_code;
	}
	public String getDep_name() {
		return dep_name;
	}
	public void setDep_name(String dep_name) {
		this.dep_name = dep_name;
	}
	public String getDep_num() {
		return dep_num;
	}
	public void setDep_num(String dep_num) {
		this.dep_num = dep_num;
	}
	public String getApprover() {
		return approver;
	}
	public void setApprover(String approver) {
		this.approver = approver;
	}
	public String getApprove_date() {
		return approve_date;
	}
	public void setApprove_date(String approve_date) {
		this.approve_date = approve_date;
	}
	public long getD_seq_code() {
		return d_seq_code;
	}
	public void setD_seq_code(long d_seq_code) {
		this.d_seq_code = d_seq_code;
	}
	public long getC_seq_code() {
		return c_seq_code;
	}
	public void setC_seq_code(long c_seq_code) {
		this.c_seq_code = c_seq_code;
	}
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	public long getExam_item_id() {
		return exam_item_id;
	}
	public String getCenter_num() {
		return center_num;
	}
	public long getCreater() {
		return creater;
	}
	public long getUpdater() {
		return updater;
	}
	public String getUpdate_time() {
		return update_time;
	}
	public String getItem_num() {
		return item_num;
	}
	public String getItem_category() {
		return item_category;
	}
	public long getCharging_item_id() {
		return charging_item_id;
	}
	public String getCharging_item_name() {
		return charging_item_name;
	}
	public String getImage_path() {
		return image_path;
	}
	public String getCharging_item_code() {
		return charging_item_code;
	}
	public void setExam_item_id(long exam_item_id) {
		this.exam_item_id = exam_item_id;
	}
	public void setCenter_num(String center_num) {
		this.center_num = center_num;
	}
	public void setCreater(long creater) {
		this.creater = creater;
	}
	public void setUpdater(long updater) {
		this.updater = updater;
	}
	public void setUpdate_time(String update_time) {
		this.update_time = update_time;
	}
	public void setItem_num(String item_num) {
		this.item_num = item_num;
	}
	public void setItem_category(String item_category) {
		this.item_category = item_category;
	}
	public void setCharging_item_id(long charging_item_id) {
		this.charging_item_id = charging_item_id;
	}
	public void setCharging_item_name(String charging_item_name) {
		this.charging_item_name = charging_item_name;
	}
	public void setImage_path(String image_path) {
		this.image_path = image_path;
	}
	public void setCharging_item_code(String charging_item_code) {
		this.charging_item_code = charging_item_code;
	}
}
