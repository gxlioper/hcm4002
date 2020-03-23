package com.hjw.wst.DTO;

public class ExaminationItemVSLisDTO {

	private long exam_item_id;
	private String lis_item_id;
	private String item_code;
	private String center_num;
	public long getExam_item_id() {
		return exam_item_id;
	}
	public String getLis_item_id() {
		return lis_item_id;
	}
	public String getItem_code() {
		return item_code;
	}
	public String getCenter_num() {
		return center_num;
	}
	public void setExam_item_id(long exam_item_id) {
		this.exam_item_id = exam_item_id;
	}
	public void setLis_item_id(String lis_item_id) {
		this.lis_item_id = lis_item_id;
	}
	public void setItem_code(String item_code) {
		this.item_code = item_code;
	}
	public void setCenter_num(String center_num) {
		this.center_num = center_num;
	}
}
