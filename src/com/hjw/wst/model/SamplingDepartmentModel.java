package com.hjw.wst.model;

import java.util.List;

import com.hjw.wst.DTO.SampleExamDetailDTO;

public class SamplingDepartmentModel implements java.io.Serializable{
	private static final long serialVersionUID = -97502163798576023L;
	
	private String exam_num;
	
	private String sample_barcode;
	
	private long sampleId;
	
	private String charingids;
	
	private String sampleExamDetails;
	
	private List<SampleExamDetailDTO> sampleExamDetailList;
	
	private String bar_code_url;
	
	private String barcode_print_type;//调用打印程序类型
	private String zyb_barcode_print_type;
	
	private String old_sampleIds;//被合并ID
	
	private String user_name;//用户名
	private String key_word;//密码
	
	private long id;
	private long examinfo_id;
	private String examinfo_ids;

	public String getZyb_barcode_print_type() {
		return zyb_barcode_print_type;
	}

	public void setZyb_barcode_print_type(String zyb_barcode_print_type) {
		this.zyb_barcode_print_type = zyb_barcode_print_type;
	}

	public String getBarcode_print_type() {
		return barcode_print_type;
	}

	public void setBarcode_print_type(String barcode_print_type) {
		this.barcode_print_type = barcode_print_type;
	}

	public String getExam_num() {
		return exam_num;
	}

	public void setExam_num(String exam_num) {
		this.exam_num = exam_num;
	}

	public String getSample_barcode() {
		return sample_barcode;
	}

	public void setSample_barcode(String sample_barcode) {
		this.sample_barcode = sample_barcode;
	}

	public String getSampleExamDetails() {
		return sampleExamDetails;
	}

	public void setSampleExamDetails(String sampleExamDetails) {
		this.sampleExamDetails = sampleExamDetails;
	}

	public List<SampleExamDetailDTO> getSampleExamDetailList() {
		return sampleExamDetailList;
	}

	public void setSampleExamDetailList(List<SampleExamDetailDTO> sampleExamDetailList) {
		this.sampleExamDetailList = sampleExamDetailList;
	}

	public long getSampleId() {
		return sampleId;
	}

	public void setSampleId(long sampleId) {
		this.sampleId = sampleId;
	}

	public String getCharingids() {
		return charingids;
	}

	public void setCharingids(String charingids) {
		this.charingids = charingids;
	}

	public String getBar_code_url() {
		return bar_code_url;
	}

	public void setBar_code_url(String bar_code_url) {
		this.bar_code_url = bar_code_url;
	}

	public String getOld_sampleIds() {
		return old_sampleIds;
	}

	public void setOld_sampleIds(String old_sampleIds) {
		this.old_sampleIds = old_sampleIds;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getKey_word() {
		return key_word;
	}

	public void setKey_word(String key_word) {
		this.key_word = key_word;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getExaminfo_id() {
		return examinfo_id;
	}

	public void setExaminfo_id(long examinfo_id) {
		this.examinfo_id = examinfo_id;
	}

	public String getExaminfo_ids() {
		return examinfo_ids;
	}

	public void setExaminfo_ids(String examinfo_ids) {
		this.examinfo_ids = examinfo_ids;
	}
}
