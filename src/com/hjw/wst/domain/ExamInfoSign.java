package com.hjw.wst.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "exam_info_sign")
public class ExamInfoSign implements java.io.Serializable {
	 private static final long serialVersionUID = -97502163798576023L;
	    @Id
		@GenericGenerator(name = "idGenerator", strategy = "identity")
		@GeneratedValue(generator = "idGenerator")
	    private long id;
	    
	    @Column(name = "exam_num")
		private String exam_num;

	    @Column(name = "examsign")
		private String examsign;
    
	    @Column(name = "creater")
	    private long creater;
	    
	    @Column(name = "create_time")
	    private Date create_time;
	    
	    @Column(name = "updater")
	    private long updater;
	    
	    @Column(name = "update_time")
	    private Date update_time;
	    
	    @Column(name = "examsignpicpath")
	    private String examsignpicpath;	    

		public String getExamsignpicpath() {
			return examsignpicpath;
		}

		public void setExamsignpicpath(String examsignpicpath) {
			this.examsignpicpath = examsignpicpath;
		}

		public long getId() {
			return id;
		}

		public void setId(long id) {
			this.id = id;
		}

		public String getExam_num() {
			return exam_num;
		}

		public void setExam_num(String exam_num) {
			this.exam_num = exam_num;
		}

		public String getExamsign() {
			return examsign;
		}

		public void setExamsign(String examsign) {
			this.examsign = examsign;
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
	    
	   
	}