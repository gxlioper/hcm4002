package com.hjw.wst.DTO;

public class ExamInfoCountDTO implements java.io.Serializable{

	private static final long serialVersionUID = -97502163798576023L;

	private long num;
	private String exam_status;
	
	public long getNum() {
		return num;
	}
	public void setNum(long num) {
		this.num = num;
	}
	public String getExam_status() {
		return exam_status;
	}
	public void setExam_status(String exam_status) {
		this.exam_status = exam_status;
	}
}
