package com.hjw.wst.domain;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "system_informs_user")
public class SystemInforms_user implements java.io.Serializable{
	 private static final long serialVersionUID = -97502163798576023L;
		@Id
		@GenericGenerator(name = "idGenerator", strategy = "identity")
		@GeneratedValue(generator = "idGenerator")
		private long id;

	    @Column(name = "informs_id")
		private long informs_id;//站内表ID
	    
	    @Column(name = "user_id")
		private long user_id;//用户ID
	    
	    @Column(name = "reader_flag")
		private long reader_flag;//阅读标记  （预留） 
	    
	    @Column(name = "reader_time")
	    private Date reader_time;//阅读时间  （预留）
	    
	    @Column(name = "creater")
	  	private long creater; 
	  	 
	    @Column(name = "create_time")
	  	private Date create_time; 
	
	    @Column(name = "updater")
	  	private long updater; 
	  	 
	    @Column(name = "update_time")
	  	private Date update_time; 
	
		public SystemInforms_user() {
			// TODO Auto-generated constructor stub
		}
		public long getId() {
			return id;
		}
		public void setId(long id) {
			this.id = id;
		}
		public long getInforms_id() {
			return informs_id;
		}
		public void setInforms_id(long informs_id) {
			this.informs_id = informs_id;
		}
		public long getUser_id() {
			return user_id;
		}
		public void setUser_id(long user_id) {
			this.user_id = user_id;
		}
		public long getReader_flag() {
			return reader_flag;
		}
		public void setReader_flag(long reader_flag) {
			this.reader_flag = reader_flag;
		}
		public Date getReader_time() {
			return reader_time;
		}
		public void setReader_time(Date reader_time) {
			this.reader_time = reader_time;
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
