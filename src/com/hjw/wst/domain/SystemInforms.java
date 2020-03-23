package com.hjw.wst.domain;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "system_informs")
public class SystemInforms implements java.io.Serializable{
	 private static final long serialVersionUID = -97502163798576023L;
		@Id
		@GenericGenerator(name = "idGenerator", strategy = "identity")
		@GeneratedValue(generator = "idGenerator")
		private long id;

	    @Column(name = "inform_content")
		private String inform_content;//通知内容
	    
	    @Column(name = "valid_date")
		private Date valid_date;//有效时间
	    
	    @Column(name = "is_active")
		private String is_active;//是否启用 
	    
	    @Column(name = "creater")
	  	private long creater; 
	  	 
	    @Column(name = "create_time")
	  	private Date create_time; 
	
	    @Column(name = "updater")
	  	private long updater; 
	  	 
	    @Column(name = "update_time")
	  	private Date update_time; 
	
		public SystemInforms() {
			// TODO Auto-generated constructor stub
		}
		
		public long getId() {
			return id;
		}

		public void setId(long id) {
			this.id = id;
		}

		public String getInform_content() {
			return inform_content;
		}

		public void setInform_content(String inform_content) {
			this.inform_content = inform_content;
		}

		public Date getValid_date() {
			return valid_date;
		}

		public void setValid_date(Date valid_date) {
			this.valid_date = valid_date;
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

	    
}
