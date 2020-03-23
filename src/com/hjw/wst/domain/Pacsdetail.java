package com.hjw.wst.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "Pacs_detail")
public class Pacsdetail implements java.io.Serializable {
	 private static final long serialVersionUID = -97502163798576023L;

		@Id
		@GenericGenerator(name = "idGenerator", strategy = "identity")
		@GeneratedValue(generator = "idGenerator")
		private long id;
		@Column(name = "summary_id")
		private long summary_id;
		@Column(name = "examinfo_num")
		private String examinfo_num;
		@Column(name = "chargingItem_num")
		private String chargingItem_num;
		@Column(name = "chargingItem_name")
		private String chargingItem_name;
		@Column(name = "dep_num")
		private String dep_num;
		@Column(name = "dep_name")
		private String dep_name;
		@Column(name = "examinfo_sampleId")
		private long examinfo_sampleId;
		@Column(name = "is_need_return")
		private String is_need_return;
		
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
		

	    public String getPacs_req_code() {
			return pacs_req_code;
		}

		public void setPacs_req_code(String pacs_req_code) {
			this.pacs_req_code = pacs_req_code;
		}

		public long getId() {
			return id;
		}

		public void setId(long id) {
			this.id = id;
		}

		

		public long getSummary_id() {
			return summary_id;
		}

		public void setSummary_id(long summary_id) {
			this.summary_id = summary_id;
		}

		public String getExaminfo_num() {
			return examinfo_num;
		}

		public void setExaminfo_num(String examinfo_num) {
			this.examinfo_num = examinfo_num;
		}

		public String getChargingItem_num() {
			return chargingItem_num;
		}

		public void setChargingItem_num(String chargingItem_num) {
			this.chargingItem_num = chargingItem_num;
		}

		public String getChargingItem_name() {
			return chargingItem_name;
		}

		public void setChargingItem_name(String chargingItem_name) {
			this.chargingItem_name = chargingItem_name;
		}

		public String getDep_num() {
			return dep_num;
		}

		public void setDep_num(String dep_num) {
			this.dep_num = dep_num;
		}

		public String getDep_name() {
			return dep_name;
		}

		public void setDep_name(String dep_name) {
			this.dep_name = dep_name;
		}

		public long getExaminfo_sampleId() {
			return examinfo_sampleId;
		}

		public void setExaminfo_sampleId(long examinfo_sampleId) {
			this.examinfo_sampleId = examinfo_sampleId;
		}

		public String getIs_need_return() {
			return is_need_return;
		}

		public void setIs_need_return(String is_need_return) {
			this.is_need_return = is_need_return;
		}

		public long getCreater() {
			return creater;
		}

		public void setCreater(long creater) {
			this.creater = creater;
		}

		public long getUpdater() {
			return updater;
		}

		public void setUpdater(long updater) {
			this.updater = updater;
		}

		public Date getCreate_time() {
			return create_time;
		}

		public void setCreate_time(Date create_time) {
			this.create_time = create_time;
		}

		public Date getUpdate_time() {
			return update_time;
		}

		public void setUpdate_time(Date update_time) {
			this.update_time = update_time;
		}		
	}