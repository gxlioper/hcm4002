package com.hjw.wst.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "data_dictionary")
public class DataDictionary implements java.io.Serializable {
	 private static final long serialVersionUID = -97502163798576023L;
	 @Id
		@GenericGenerator(name = "idGenerator", strategy = "identity")
		@GeneratedValue(generator = "idGenerator")
		private long id;
	 
	 @Column(name = "data_type")
        private String data_type;
	 
	 @Column(name = "data_code")
        private String data_code;
	 
	 @Column(name = "data_name")
        private String data_name;
	 
	 @Column(name = "remark")
        private String remark;
	 
	 @Column(name = "isActive")
        private String isActive;
	 
	 @Column(name = "seq_code")
        private int seq_code;
	    
	    @Column(name = "creater")
	    private long creater;
	    
	    @Column(name = "create_time")
	    private Date create_time;
	    
	    @Column(name = "updater")
	    private long updater;
	    
	    @Column(name = "update_time")
	    private Date update_time;	  
	    
	    @Column(name="data_code_children")
	    private String data_code_children;
	    
	    @Column(name="data_class")
	    private String data_class;

	    public DataDictionary() {
			// TODO Auto-generated constructor stub
		}
	    public long getId() {
			return id;
		}

		public void setId(long id) {
			this.id = id;
		}		

		public String getData_type() {
			return data_type;
		}

		public void setData_type(String data_type) {
			this.data_type = data_type;
		}

		public String getData_code() {
			return data_code;
		}

		public void setData_code(String data_code) {
			this.data_code = data_code;
		}

		public String getData_name() {
			return data_name;
		}

		public void setData_name(String data_name) {
			this.data_name = data_name;
		}

		public String getRemark() {
			return remark;
		}

		public void setRemark(String remark) {
			this.remark = remark;
		}

		public String getIsActive() {
			return isActive;
		}

		public void setIsActive(String isActive) {
			this.isActive = isActive;
		}

		public int getSeq_code() {
			return seq_code;
		}

		public void setSeq_code(int seq_code) {
			this.seq_code = seq_code;
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
		public String getData_code_children() {
			return data_code_children;
		}
		public void setData_code_children(String data_code_children) {
			this.data_code_children = data_code_children;
		}
		public String getData_class() {
			return data_class;
		}
		public void setData_class(String data_class) {
			this.data_class = data_class;
		}
	}