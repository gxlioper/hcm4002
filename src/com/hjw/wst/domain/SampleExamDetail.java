package com.hjw.wst.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "sample_exam_detail")
public class SampleExamDetail implements java.io.Serializable {
	 private static final long serialVersionUID = -97502163798576023L;
		@Id
		@GenericGenerator(name = "idGenerator", strategy = "identity")
		@GeneratedValue(generator = "idGenerator")
		private long id;
 
		@Column(name = "creater")
	    private long creater;
	    
	    @Column(name = "create_time")
	    private Date create_time;
	    
	    @Column(name = "updater")
	    private long updater;
	    
	    @Column(name = "update_time")
	    private Date update_time;
	    
	    @Column(name = "exam_info_id")
	    private long exam_info_id;
	    
	    @Column(name = "sample_id")
	    private long sample_id;
	    
	    @Column(name = "sample_barcode")
	    private String sample_barcode;
	    
	    @Column(name = "status")
	    private String status;
	    
	    @Column(name = "pic_path")
	    private String pic_path;
	    
	    @Column(name = "center_num")
	    private String center_num;
	    
	    @Column(name = "approver")
	    private String approver;
	    
	    @Column(name = "approve_date")
	    private Date approve_date;
	    
	    @Column(name="is_binding")
	    private long is_binding;
	    
	    @Column(name="check_id")
	    private long check_id;
	    
	    @Column(name="check_doctor")
	    private String check_doctor;
	    
	    @Column(name="check_date")
	    private Date check_date;
	    
	    @Column(name="exam_num")
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

		public long getExam_info_id() {
			return exam_info_id;
		}

		public void setExam_info_id(long exam_info_id) {
			this.exam_info_id = exam_info_id;
		}

		public long getSample_id() {
			return sample_id;
		}

		public void setSample_id(long sample_id) {
			this.sample_id = sample_id;
		}

		public String getSample_barcode() {
			return sample_barcode;
		}

		public void setSample_barcode(String sample_barcode) {
			this.sample_barcode = sample_barcode;
		}

		public String getStatus() {
			return status;
		}

		public void setStatus(String status) {
			this.status = status;
		}

		public String getPic_path() {
			return pic_path;
		}

		public void setPic_path(String pic_path) {
			this.pic_path = pic_path;
		}

		public String getCenter_num() {
			return center_num;
		}

		public void setCenter_num(String center_num) {
			this.center_num = center_num;
		}

		public String getApprover() {
			return approver;
		}

		public void setApprover(String approver) {
			this.approver = approver;
		}

		public Date getApprove_date() {
			return approve_date;
		}

		public void setApprove_date(Date approve_date) {
			this.approve_date = approve_date;
		}

		public long getIs_binding() {
			return is_binding;
		}

		public void setIs_binding(long is_binding) {
			this.is_binding = is_binding;
		}

		public long getCheck_id() {
			return check_id;
		}

		public void setCheck_id(long check_id) {
			this.check_id = check_id;
		}

		public String getCheck_doctor() {
			return check_doctor;
		}

		public void setCheck_doctor(String check_doctor) {
			this.check_doctor = check_doctor;
		}

		public Date getCheck_date() {
			return check_date;
		}

		public void setCheck_date(Date check_date) {
			this.check_date = check_date;
		}
	}