package com.hjw.wst.DTO;

public class ProcListResult {
	private String bar_code="";// --条码号
	private String exam_num="";//--体检ID
	private String old_exam_num="";//老体检号
	private String lis_item_code="";//S收费项目关联码        
	private String lis_rep_item_code="";// --LIS细项编码
	private String exam_doctor="";//        varchar(20),      --检验（报告）医生
	private String exam_date="";//          varchar(20),      --检验日期
	private String exam_result="";//        varchar(100),     --检验结果
	private String ref_value="";//            varchar(400),   --参考范围
	private String item_unit="";//          varchar(80),      --单位
	private String ref_indicator="";//      varchar(4),       --高低标识（0：正常；1：高；2：低；3：阳性；4：危急）
	private String approver="";//           varchar(20),      --审核医生
	private String approve_date="";//       varchar(20),      --审核日期
	
	public String getOld_exam_num() {
		return old_exam_num;
	}
	public void setOld_exam_num(String old_exam_num) {
		this.old_exam_num = old_exam_num;
	}
	public String getBar_code() {
		return bar_code;
	}
	public void setBar_code(String bar_code) {
		this.bar_code = bar_code;
	}
	
	public String getExam_num() {
		return exam_num;
	}
	public void setExam_num(String exam_num) {
		this.exam_num = exam_num;
	}
	public String getLis_item_code() {
		return lis_item_code;
	}
	public void setLis_item_code(String lis_item_code) {
		this.lis_item_code = lis_item_code;
	}
	public String getLis_rep_item_code() {
		return lis_rep_item_code;
	}
	public void setLis_rep_item_code(String lis_rep_item_code) {
		this.lis_rep_item_code = lis_rep_item_code;
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
	public String getExam_result() {
		return exam_result;
	}
	public void setExam_result(String exam_result) {
		this.exam_result = exam_result;
	}
	public String getRef_value() {
		return ref_value;
	}
	public void setRef_value(String ref_value) {
		this.ref_value = ref_value;
	}
	public String getItem_unit() {
		return item_unit;
	}
	public void setItem_unit(String item_unit) {
		this.item_unit = item_unit;
	}
	public String getRef_indicator() {
		return ref_indicator;
	}
	public void setRef_indicator(String ref_indicator) {
		this.ref_indicator = ref_indicator;
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
	@Override
	public String toString() {
		return "ProcListResult [bar_code=" + bar_code + ", exam_num="
				+ exam_num + ", lis_item_code=" + lis_item_code
				+ ", lis_rep_item_code=" + lis_rep_item_code + ", exam_doctor="
				+ exam_doctor + ", exam_date=" + exam_date + ", exam_result="
				+ exam_result + ", ref_value=" + ref_value + ", item_unit="
				+ item_unit + ", ref_indicator=" + ref_indicator
				+ ", approver=" + approver + ", approve_date=" + approve_date
				+ "]";
	}
	
	
}
