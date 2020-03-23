package com.hjw.Bean.ZJ;

public class ReqDelMsg extends ReqMsgHeader{
	
	private String med_person_id="";//":"体检号", 
	private String med_exam_no="";//":"预约号"
	
	public String getMed_person_id() {
		return med_person_id;
	}
	public void setMed_person_id(String med_person_id) {
		this.med_person_id = med_person_id;
	}
	public String getMed_exam_no() {
		return med_exam_no;
	}
	public void setMed_exam_no(String med_exam_no) {
		this.med_exam_no = med_exam_no;
	}


}
