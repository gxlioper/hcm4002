package com.hjw.wst.domain;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "exam_Critical_detail")
public class Critical implements java.io.Serializable{
	 private static final long serialVersionUID = -97502163798576023L;
		@Id
		@GenericGenerator(name = "idGenerator", strategy = "identity")
		@GeneratedValue(generator = "idGenerator")
		private long id;

	    @Column(name = "exam_info_id")
		private long exam_info_id;
	    
	    @Column(name = "dept_id")
		private long dept_id; 
	    
	    @Column(name = "charging_item_id")
		private String charging_item_id;
	    
	    @Column(name = "exam_item_id")
		private long exam_item_id; 
	    
	    @Column(name = "exam_result")
	  	private String exam_result;
	    

	    @Column(name = "disease_id")
		private long disease_id;
	    
	    @Column(name = "userid")
		private long userid; 
	    
	    @Column(name = "done_date")
		private Date done_date; 
	    
	    @Column(name = "done_flag")
		private long done_flag; 
	    
	    @Column(name = "note")
	  	private String note;
	    
	    @Column(name = "check_date")
		private Date check_date; 
	    
	    @Column(name = "is_active")
	  	private String is_active;
	    
	    @Column(name = "data_source")
	    private int data_source;
	    
	    @Column(name = "creater")
	    private long creater;
	    @Column(name = "create_time")
	    private Date create_time;
	    
	    @Column(name="exam_num")
	    private String exam_num;
	    
	    @Column(name="item_code")
	    private String item_code;
	    
	    @Column(name="charging_item_code")
	    private String charging_item_code;
	    
	    @Column(name="disease_num")
	    private String disease_num;
	    
	    @Column(name="critical_type")
	    private String critical_type;//类型 0 重大阳性 1 危机值
	    
	    @Column(name = "critical_class_parent_id")
	    private long critical_class_parent_id;
	    
	    @Column(name = "critical_class_id")
	    private long critical_class_id;
	    
	    @Column(name = "critical_class_level")
	    private int critical_class_level;
	    
	    @Column(name = "critical_suggestion")
	    private String critical_suggestion;
	    
		//通知方式  1电话   2短信
	    @Column(name = "give_notice_type")
	    private long give_notice_type;
	    
	    public long getGive_notice_type() {
			return give_notice_type;
		}

		public void setGive_notice_type(long give_notice_type) {
			this.give_notice_type = give_notice_type;
		}

		public long getCritical_class_parent_id() {
			return critical_class_parent_id;
		}

		public void setCritical_class_parent_id(long critical_class_parent_id) {
			this.critical_class_parent_id = critical_class_parent_id;
		}

		public long getCritical_class_id() {
			return critical_class_id;
		}

		public void setCritical_class_id(long critical_class_id) {
			this.critical_class_id = critical_class_id;
		}

		public int getCritical_class_level() {
			return critical_class_level;
		}

		public void setCritical_class_level(int critical_class_level) {
			this.critical_class_level = critical_class_level;
		}

		public String getCritical_suggestion() {
			return critical_suggestion;
		}

		public void setCritical_suggestion(String critical_suggestion) {
			this.critical_suggestion = critical_suggestion;
		}

		public String getDisease_num() {
			return disease_num;
		}

		public void setDisease_num(String disease_num) {
			this.disease_num = disease_num;
		}

		public String getCharging_item_code() {
			return charging_item_code;
		}

		public void setCharging_item_code(String charging_item_code) {
			this.charging_item_code = charging_item_code;
		}

		public String getItem_code() {
			return item_code;
		}

		public void setItem_code(String item_code) {
			this.item_code = item_code;
		}

		public String getExam_num() {
			return exam_num;
		}

		public void setExam_num(String exam_num) {
			this.exam_num = exam_num;
		}
	    
		public Critical() {
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

		public long getDept_id() {
			return dept_id;
		}

		public void setDept_id(long dept_id) {
			this.dept_id = dept_id;
		}

		public String getCharging_item_id() {
			return charging_item_id;
		}

		public void setCharging_item_id(String charging_item_id) {
			this.charging_item_id = charging_item_id;
		}

		public long getExam_item_id() {
			return exam_item_id;
		}

		public void setExam_item_id(long exam_item_id) {
			this.exam_item_id = exam_item_id;
		}

		public String getExam_result() {
			return exam_result;
		}

		public void setExam_result(String exam_result) {
			this.exam_result = exam_result;
		}

		public long getDisease_id() {
			return disease_id;
		}

		public void setDisease_id(long disease_id) {
			this.disease_id = disease_id;
		}

		public long getUserid() {
			return userid;
		}

		public void setUserid(long userid) {
			this.userid = userid;
		}

		public Date getDone_date() {
			return done_date;
		}

		public void setDone_date(Date done_date) {
			this.done_date = done_date;
		}

		public long getDone_flag() {
			return done_flag;
		}

		public void setDone_flag(long done_flag) {
			this.done_flag = done_flag;
		}

		public String getNote() {
			return note;
		}

		public void setNote(String note) {
			this.note = note;
		}

		public Date getCheck_date() {
			return check_date;
		}

		public void setCheck_date(Date check_date) {
			this.check_date = check_date;
		}

		public String getIs_active() {
			return is_active;
		}

		public void setIs_active(String is_active) {
			this.is_active = is_active;
		}

		public int getData_source() {
			return data_source;
		}

		public void setData_source(int data_source) {
			this.data_source = data_source;
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

		public String getCritical_type() {
			return critical_type;
		}

		public void setCritical_type(String critical_type) {
			this.critical_type = critical_type;
		}
	    
}
