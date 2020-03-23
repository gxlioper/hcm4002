package com.hjw.wst.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "exam_dep_result")
public class ExamdepResult implements java.io.Serializable {
	 private static final long serialVersionUID = -97502163798576023L;
	 @Id
		@GenericGenerator(name = "idGenerator", strategy = "identity")
		@GeneratedValue(generator = "idGenerator")
		private long id;
	 
	    @Column(name = "exam_info_id")
        private long exam_info_id;
	 
	    @Column(name = "exam_doctor")
        private String exam_doctor;
	 
	    @Column(name = "dep_id")
        private long dep_id;
	 
	    @Column(name = "exam_result_summary")
	    private String exam_result_summary;
	    
	    @Column(name = "suggestion")
	    private String suggestion;
	    
	    @Column(name = "center_num")
	    private String center_num;
	    
	    @Column(name = "approver")
	    private long approver;	
	    
	    @Column(name = "approve_date")
	    private Date approve_date;
	    
	    @Column(name = "creater")
	    private long creater;
	    
	    @Column(name = "create_time")
	    private Date create_time;
	    
	    @Column(name = "updater")
	    private long updater;
	    
	    @Column(name = "update_time")
	    private Date update_time;
	    
	    @Column(name = "Special_setup")
	    private String Special_setup;
	    
	    @Column(name = "app_type")
	    private String app_type;
	    
	    @Column(name="exam_num")
	    private String exam_num;
	    
	    public String getExam_num() {
			return exam_num;
		}

		public void setExam_num(String exam_num) {
			this.exam_num = exam_num;
		}
	    
	    public ExamdepResult() {
			// TODO Auto-generated constructor stub
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

		public String getExam_doctor() {
			return exam_doctor;
		}

		public void setExam_doctor(String exam_doctor) {
			this.exam_doctor = exam_doctor;
		}

		public long getDep_id() {
			return dep_id;
		}

		public void setDep_id(long dep_id) {
			this.dep_id = dep_id;
		}

		public String getExam_result_summary() {
			return exam_result_summary;
		}

		public void setExam_result_summary(String exam_result_summary) {
			this.exam_result_summary = exam_result_summary;
		}

		public String getSuggestion() {
			return suggestion;
		}

		public void setSuggestion(String suggestion) {
			this.suggestion = suggestion;
		}

		public String getCenter_num() {
			return center_num;
		}

		public void setCenter_num(String center_num) {
			this.center_num = center_num;
		}

		public long getApprover() {
			return approver;
		}

		public void setApprover(long approver) {
			this.approver = approver;
		}

		public Date getApprove_date() {
			return approve_date;
		}

		public void setApprove_date(Date approve_date) {
			this.approve_date = approve_date;
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

		public String getSpecial_setup() {
			return Special_setup;
		}

		public void setSpecial_setup(String special_setup) {
			Special_setup = special_setup;
		}

		public String getApp_type() {
			return app_type;
		}

		public void setApp_type(String app_type) {
			this.app_type = app_type;
		}
	}