package com.hjw.wst.DTO;

public class QueryInterfaceResultDTO {
	private String exam_num;
	private String user_name;
	private String interface_type;
	private String exam_type;
	private String company_name;
	private String apply_id;
	private String item_name;
	private String send_flag;
	private String send_flags;
	private String dep_name;
	private String exam_types;
	private String charge_item_id;
	private String sample_applyid;
	private String sample_type;
	private String sample_creater;
	private String sample_createdate;
	private String sample_reportdate;
	
	
	public String getSample_applyid() {
		return sample_applyid;
	}
	public void setSample_applyid(String sample_applyid) {
		this.sample_applyid = sample_applyid;
	}
	public String getSample_type() {
		return sample_type;
	}
	public void setSample_type(String sample_type) {
		this.sample_type = sample_type;
	}
	public String getSample_creater() {
		return sample_creater;
	}
	public void setSample_creater(String sample_creater) {
		this.sample_creater = sample_creater;
	}
	public String getSample_createdate() {
		return sample_createdate;
	}
	public void setSample_createdate(String sample_createdate) {
		this.sample_createdate = sample_createdate;
	}
	public String getSample_reportdate() {
		return sample_reportdate;
	}
	public void setSample_reportdate(String sample_reportdate) {
		this.sample_reportdate = sample_reportdate;
	}
	public String getCharge_item_id() {
		return charge_item_id;
	}
	public void setCharge_item_id(String charge_item_id) {
		this.charge_item_id = charge_item_id;
	}
	public String getSend_flags() {
		return send_flags;
	}
	public void setSend_flags(String send_flags) {
		this.send_flags = send_flags;
	}
	public String getExam_types() {
		return exam_types;
	}
	public void setExam_types(String exam_types) {
		this.exam_types = exam_types;
	}
	public String getDep_name() {
		return dep_name;
	}
	public void setDep_name(String dep_name) {
		this.dep_name = dep_name;
	}
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
	

	public String getInterface_type() {
		return interface_type;
	}
	public void setInterface_type(String interface_type) {
		this.interface_type = interface_type;
	}
	public String getExam_type() {
		return exam_type;
	}
	public void setExam_type(String exam_type) {
		this.exam_type = exam_type;
		if("G".equals(exam_type)){
			this.exam_types = "个检";
		}else if("T".equals(exam_type)){
			this.exam_types = "团检";
		}
	}
	public String getCompany_name() {
		return company_name;
	}
	public void setCompany_name(String company_name) {
		this.company_name = company_name;
	}
	public String getApply_id() {
		return apply_id;
	}
	public void setApply_id(String apply_id) {
		this.apply_id = apply_id;
	}
	public String getItem_name() {
		return item_name;
	}
	public void setItem_name(String item_name) {
		this.item_name = item_name;
	}
	public String getSend_flag() {
		return send_flag;
	}
	public void setSend_flag(String send_flag) {
		this.send_flag = send_flag;
		if(send_flag!=null){
			if("Y".equals(send_flag)){
				this.send_flags = "成功";
			}else if("N".equals(send_flag)){
				this.send_flags = "失败";
			}else if(send_flag.length()==0){
				this.send_flags = "未发送";
			}
		}else{
			this.send_flags = "未发送";
		}
	}
	

}
