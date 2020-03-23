package com.hjw.wst.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "examResult_chargingItem")
public class ExamResultChargingItem implements java.io.Serializable {
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
	    
	    @Column(name = "charging_id")
	    private long charging_id;
	    
	    @Column(name = "exam_id")
	    private long exam_id;
	    
	    @Column(name = "result_type")
	    private String result_type;
	    
	    @Column(name = "isActive")
	    private String isActive;
	    
	    @Column(name = "charging_item_code")
	    private String charging_item_code;
	    
	    @Column(name = "bar_code")
	    private String bar_code;
	    

		public String getBar_code() {
			return bar_code;
		}

		public void setBar_code(String bar_code) {
			this.bar_code = bar_code;
		}

		public String getCharging_item_code() {
			return charging_item_code;
		}

		public void setCharging_item_code(String charging_item_code) {
			this.charging_item_code = charging_item_code;
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

		public long getCharging_id() {
			return charging_id;
		}

		public void setCharging_id(long charging_id) {
			this.charging_id = charging_id;
		}

		public long getExam_id() {
			return exam_id;
		}

		public void setExam_id(long exam_id) {
			this.exam_id = exam_id;
		}

		public String getResult_type() {
			return result_type;
		}

		public void setResult_type(String result_type) {
			this.result_type = result_type;
		}

		public String getIsActive() {
			return isActive;
		}

		public void setIsActive(String isActive) {
			this.isActive = isActive;
		}
	   

	}