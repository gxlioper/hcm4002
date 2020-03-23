package com.hjw.wst.model;

public class ViewCommonWordsModel implements java.io.Serializable{

	private static final long serialVersionUID = -97502163798576023L;

	private long id;
	private long sample_id;
	private String exam_desc;
	private String exam_result;
	private Integer seq_code;
	
	private String sample_name;
	private long is_default;
	
	public long getIs_default() {
		return is_default;
	}
	public void setIs_default(long is_default) {
		this.is_default = is_default;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getSample_id() {
		return sample_id;
	}
	public void setSample_id(long sample_id) {
		this.sample_id = sample_id;
	}
	public String getExam_desc() {
		return exam_desc;
	}
	public void setExam_desc(String exam_desc) {
		this.exam_desc = exam_desc;
	}
	public String getExam_result() {
		return exam_result;
	}
	public void setExam_result(String exam_result) {
		this.exam_result = exam_result;
	}
	public Integer getSeq_code() {
		return seq_code;
	}
	public void setSeq_code(Integer seq_code) {
		this.seq_code = seq_code;
	}
	public String getSample_name() {
		return sample_name;
	}
	public void setSample_name(String sample_name) {
		this.sample_name = sample_name;
	}
}
