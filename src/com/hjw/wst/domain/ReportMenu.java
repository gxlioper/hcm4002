package com.hjw.wst.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "Report_Menu")
public class ReportMenu implements java.io.Serializable {
	 private static final long serialVersionUID = -97502163798576023L;
		@Id
		@GenericGenerator(name = "idGenerator", strategy = "identity")
		@GeneratedValue(generator = "idGenerator")
		private long id;

		@Column(name = "parent_id")
		private Integer parent_id;
		
		@Column(name = "report_name")
		private String report_name;

		@Column(name = "report_address")
        private String report_address;

		@Column(name = "is_active")
        private String is_active;

		@Column(name = "creater")
        private long creater;

		@Column(name = "create_time")
        private Date create_time;
		
		@Column(name = "updater")
        private long updater;
		
		@Column(name = "update_time")
        private Date update_time;
		
		@Column(name = "seq_code")
        private long seq_code;
		
		@Column(name = "report_type")
        private String report_type;

		public long getId() {
			return id;
		}

		public void setId(long id) {
			this.id = id;
		}

		public Integer getParent_id() {
			return parent_id;
		}

		public void setParent_id(Integer parent_id) {
			this.parent_id = parent_id;
		}

		public String getReport_name() {
			return report_name;
		}

		public void setReport_name(String report_name) {
			this.report_name = report_name;
		}

		public String getReport_address() {
			return report_address;
		}

		public void setReport_address(String report_address) {
			this.report_address = report_address;
		}

		public String getIs_active() {
			return is_active;
		}

		public void setIs_active(String is_active) {
			this.is_active = is_active;
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

		public long getSeq_code() {
			return seq_code;
		}

		public void setSeq_code(long seq_code) {
			this.seq_code = seq_code;
		}

		public String getReport_type() {
			return report_type;
		}

		public void setReport_type(String report_type) {
			this.report_type = report_type;
		}  	   

	}