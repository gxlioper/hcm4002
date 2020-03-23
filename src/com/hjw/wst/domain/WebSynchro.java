package com.hjw.wst.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 
     * @Title:  火箭蛙体检管理系统   
     * @Package com.hjw.wst.domain   
     * @Description:  
     * @author: zt     
     * @date:   2016年7月4日 上午11:13:46   
     * @version V2.0.0.0
 */

@Entity
@Table(name = "web_synchro")

public class WebSynchro implements java.io.Serializable {
	private static final long serialVersionUID = -97502163798576023L;
	
	@Id
	@GenericGenerator(name = "idGenerator", strategy = "identity")
	@GeneratedValue(generator = "idGenerator")
	private long id;
	
	@Column(name = "datatype")
	private char datatype;
	
	@Column(name = "dataid")
	private String dataid;
	
	@Column(name = "create_time")
	private Date create_time;
	
	@Column(name = "synchro")
	private char synchro;
	
	@Column(name = "synchrodate")
	private Date synchrodate;
	
	public WebSynchro() {
		// TODO Auto-generated constructor stub
	}
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public char getDatatype() {
		return datatype;
	}

	public void setDatatype(char datatype) {
		this.datatype = datatype;
	}

	public Date getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}

	public char getSynchro() {
		return synchro;
	}

	public void setSynchro(char synchro) {
		this.synchro = synchro;
	}

	public Date getSynchrodate() {
		return synchrodate;
	}

	public void setSynchrodate(Date synchrodate) {
		this.synchrodate = synchrodate;
	}
	
	public String getDataid() {
		return dataid;
	}
	
	public void setDataid(String dataid) {
		this.dataid = dataid;
	}
	
}
