package com.hjw.wst.DTO;

import java.util.ArrayList;
import java.util.List;

public class DepInspectCharingItemDTO {

	private long c_id;
	private String c_name;
	private String exam_status;
	private List<DepInspectExamIntionDTO> depItemList = new ArrayList<DepInspectExamIntionDTO>();
	
	public String getExam_status() {
		return exam_status;
	}
	public void setExam_status(String exam_status) {
		this.exam_status = exam_status;
	}
	public long getC_id() {
		return c_id;
	}
	public void setC_id(long c_id) {
		this.c_id = c_id;
	}
	public String getC_name() {
		return c_name;
	}
	public void setC_name(String c_name) {
		this.c_name = c_name;
	}
	public List<DepInspectExamIntionDTO> getDepItemList() {
		return depItemList;
	}
	public void setDepItemList(List<DepInspectExamIntionDTO> depItemList) {
		this.depItemList = depItemList;
	}
}
