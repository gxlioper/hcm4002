package com.hjw.wst.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "pacs_summary")
public class PacsSummary implements java.io.Serializable {
	 private static final long serialVersionUID = -97502163798576023L;
	 @Id
		@GenericGenerator(name = "idGenerator", strategy = "identity")
		@GeneratedValue(generator = "idGenerator")
		private long id;
	 
	 @Column(name = "examinfo_num")
	    private String examinfo_num;
	 @Column(name = "examinfo_name")
	    private String examinfo_name;
	 @Column(name = "examinfo_sex")
	    private String examinfo_sex;	 
	 @Column(name = "examinfo_birthday")
	    private Date examinfo_birthday;
	 @Column(name = "apply_person")
	    private String apply_person;
	 @Column(name = "apply_date")
	    private Date apply_date;
	 @Column(name = "exam_status")
	    private String exam_status;
	 @Column(name = "examinfo_sampleId")
	    private long examinfo_sampleId;
	    
	    @Column(name = "creater")
	    private long creater;
	    
	    @Column(name = "create_time")
	    private Date create_time;
	    
	    @Column(name = "updater")
	    private long updater;
	    
	    @Column(name = "update_time")
	    private Date update_time;
	    
	    @Column(name = "pacs_req_code")
	    private String pacs_req_code;
	    
	    @Column(name = "sample_status")
	    private String sample_status;
	    
	    @Column(name = "sample_doc_id")
	    private long sample_doc_id;
	    
	    @Column(name = "sample_date")
	    private Date sample_date;

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

		public Date getExaminfo_birthday() {
			return examinfo_birthday;
		}

		public void setExaminfo_birthday(Date examinfo_birthday) {
			this.examinfo_birthday = examinfo_birthday;
		}

		public String getApply_person() {
			return apply_person;
		}

		public void setApply_person(String apply_person) {
			this.apply_person = apply_person;
		}

		public Date getApply_date() {
			return apply_date;
		}

		public void setApply_date(Date apply_date) {
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

		public Date getCreate_time() {
			return create_time;
		}

		public void setCreate_time(Date create_time) {
			this.create_time = create_time;
		}

		public long getUpdater() {
			return updater;
		}

		public void setUpdater(long updater) {
			this.updater = updater;
		}

		public Date getUpdate_time() {
			return update_time;
		}

		public void setUpdate_time(Date update_time) {
			this.update_time = update_time;
		}

		public String getPacs_req_code() {
			return pacs_req_code;
		}

		public void setPacs_req_code(String pacs_req_code) {
			this.pacs_req_code = pacs_req_code;
		}

		public String getSample_status() {
			return sample_status;
		}

		public void setSample_status(String sample_status) {
			this.sample_status = sample_status;
		}

		public long getSample_doc_id() {
			return sample_doc_id;
		}

		public void setSample_doc_id(long sample_doc_id) {
			this.sample_doc_id = sample_doc_id;
		}

		public Date getSample_date() {
			return sample_date;
		}

		public void setSample_date(Date sample_date) {
			this.sample_date = sample_date;
		}

	}