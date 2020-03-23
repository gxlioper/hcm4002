package com.hjw.wst.DTO;

public class PacsSummaryDTO implements java.io.Serializable {
	 private static final long serialVersionUID = -97502163798576023L;
		private long id;
	    private String examinfo_num;
	    private String examinfo_name;
	    private String examinfo_sex;
	    private String examinfo_birthday;
	    private String apply_person;
	    private String apply_date;
	    private String exam_status;
	    private long examinfo_sampleId;
	    private long creater;
	    private String create_time;
	    private long updater;
	    private String update_time;
	    private String pacs_req_code;

	    public long getId() {
			return id;
		}

		public void setId(long id) {
			this.id = id;
		}		

		public String getExaminfo_num() {
			return examinfo_num;
		}

		public void setExaminfo_num(String examinfo_num) {
			this.examinfo_num = examinfo_num;
		}

		public String getExaminfo_name() {
			return examinfo_name;
		}

		public void setExaminfo_name(String examinfo_name) {
			this.examinfo_name = examinfo_name;
		}

		public String getExaminfo_sex() {
			return examinfo_sex;
		}

		public void setExaminfo_sex(String examinfo_sex) {
			this.examinfo_sex = examinfo_sex;
		}

		public String getExaminfo_birthday() {
			return examinfo_birthday;
		}

		public void setExaminfo_birthday(String examinfo_birthday) {
			this.examinfo_birthday = examinfo_birthday;
		}

		public String getApply_person() {
			return apply_person;
		}

		public void setApply_person(String apply_person) {
			this.apply_person = apply_person;
		}

		public String getApply_date() {
			return apply_date;
		}

		public void setApply_date(String apply_date) {
			this.apply_date = apply_date;
		}

		public String getExam_status() {
			return exam_status;
		}

		public void setExam_status(String exam_status) {
			this.exam_status = exam_status;
		}

		public long getExaminfo_sampleId() {
			return examinfo_sampleId;
		}

		public void setExaminfo_sampleId(long examinfo_sampleId) {
			this.examinfo_sampleId = examinfo_sampleId;
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

		public String getPacs_req_code() {
			return pacs_req_code;
		}

		public void setPacs_req_code(String pacs_req_code) {
			this.pacs_req_code = pacs_req_code;
		}
	}