package com.hjw.wst.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "customer_type")
public class Customer_Type implements java.io.Serializable {
	 private static final long serialVersionUID = -97502163798576023L;
	 @Id
		@GenericGenerator(name = "idGenerator", strategy = "identity")
		@GeneratedValue(generator = "idGenerator")
		private long id;
	 
	    @Column(name = "type_name")
        private String type_name;
	 
	    @Column(name = "type_code")
        private String type_code;
	 
	    @Column(name = "type_comment")
        private String type_comment;
	 
	    @Column(name = "creater")
	    private long creater;
	    
	    @Column(name = "create_time")
	    private Date create_time;
	    
	    @Column(name = "updater")
	    private long updater;
	    
	    @Column(name = "update_time")
	    private Date update_time;	
	    
	    public Customer_Type() {
			// TODO Auto-generated constructor stub
		}

		public long getId() {
			return id;
		}

		public void setId(long id) {
			this.id = id;
		}

		public String getType_name() {
			return type_name;
		}

		public void setType_name(String type_name) {
			this.type_name = type_name;
		}

		public String getType_code() {
			return type_code;
		}

		public void setType_code(String type_code) {
			this.type_code = type_code;
		}

		public String getType_comment() {
			return type_comment;
		}

		public void setType_comment(String type_comment) {
			this.type_comment = type_comment;
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

		public static long getSerialversionuid() {
			return serialVersionUID;
		}
	    

	}