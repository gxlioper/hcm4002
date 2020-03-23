package com.hjw.wst.DTO;

public class ExaminfoBatchDTO {
	private long id;
	private long examinfo_id;
	private long batch_id;
	private String is_need_guide;
	private String is_need_barcode;
	private String department;
	private String position;
	private String staff_id;
	private String import_date;
	private long creater;
	private String create_time;
	private long updater;
	private String update_time;
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
	public long getExaminfo_id() {
		return examinfo_id;
	}
	public void setExaminfo_id(long examinfo_id) {
		this.examinfo_id = examinfo_id;
	}
	public long getBatch_id() {
		return batch_id;
	}
	public void setBatch_id(long batch_id) {
		this.batch_id = batch_id;
	}
	public String getIs_need_guide() {
		return is_need_guide;
	}
	public void setIs_need_guide(String is_need_guide) {
		this.is_need_guide = is_need_guide;
	}
	public String getIs_need_barcode() {
		return is_need_barcode;
	}
	public void setIs_need_barcode(String is_need_barcode) {
		this.is_need_barcode = is_need_barcode;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public String getStaff_id() {
		return staff_id;
	}
	public void setStaff_id(String staff_id) {
		this.staff_id = staff_id;
	}
	public String getImport_date() {
		return import_date;
	}
	public void setImport_date(String import_date) {
		this.import_date = import_date;
	}
	public long getCreater() {
		return creater;
	}
	public void setCreater(long creater) {
		this.creater = creater;
	}
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	public long getUpdater() {
		return updater;
	}
	public void setUpdater(long updater) {
		this.updater = updater;
	}
	public String getUpdate_time() {
		return update_time;
	}
	public void setUpdate_time(String update_time) {
		this.update_time = update_time;
	}
	
	
}
