package com.hjw.zyb.DTO;

import java.util.List;

public class ZybExamSummaryResultDTO implements java.io.Serializable {
	private static final long serialVersionUID = -97502163798576023L;
	 private long id;
	 private long exam_info_id;
	 private String resultID;
	 private String exam_result;
	 private String remark;
	 private String creater;
	 private String create_time;
	 private List<ZybExamSummaryResultOccidDTO> occidList;
	 
	 private String exam_num;
	    
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
	public String getResultID() {
		return resultID;
	}
	public void setResultID(String resultID) {
		this.resultID = resultID;
	}
	public String getExam_result() {
		return exam_result;
	}
	public void setExam_result(String exam_result) {
		this.exam_result = exam_result;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getCreater() {
		return creater;
	}
	public void setCreater(String creater) {
		this.creater = creater;
	}
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	public List<ZybExamSummaryResultOccidDTO> getOccidList() {
		return occidList;
	}
	public void setOccidList(List<ZybExamSummaryResultOccidDTO> occidList) {
		this.occidList = occidList;
	}
}
