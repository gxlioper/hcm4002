package com.hjw.wst.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "disease_dep")
public class DiseaseDep implements java.io.Serializable {
	 private static final long serialVersionUID = -97502163798576023L;
		@Id
		@GenericGenerator(name = "idGenerator", strategy = "identity")
		@GeneratedValue(generator = "idGenerator")
		private long id;

	    @Column(name = "disease_id")
		private long disease_id;

	    @Column(name = "dep_id")
		private long dep_id;

	    @Column(name = "charging_item_ids")
	    private String charging_item_ids;
	    
	    @Column(name = "exam_item_ids")
	    private String exam_item_ids;
	    
	    @Column(name = "creater")
	    private long creater;
	    
	    @Column(name = "create_time")
	    private Date create_time;
	    
	    @Column(name = "updater")
	    private long updater;
	    
	    @Column(name = "update_time")
	    private Date update_time;

		public long getId() {
			return id;
		}

		public void setId(long id) {
			this.id = id;
		}

		public long getDisease_id() {
			return disease_id;
		}

		public void setDisease_id(long disease_id) {
			this.disease_id = disease_id;
		}

		public long getDep_id() {
			return dep_id;
		}

		public void setDep_id(long dep_id) {
			this.dep_id = dep_id;
		}

		public String getCharging_item_ids() {
			return charging_item_ids;
		}

		public void setCharging_item_ids(String charging_item_ids) {
			this.charging_item_ids = charging_item_ids;
		}

		public String getExam_item_ids() {
			return exam_item_ids;
		}

		public void setExam_item_ids(String exam_item_ids) {
			this.exam_item_ids = exam_item_ids;
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