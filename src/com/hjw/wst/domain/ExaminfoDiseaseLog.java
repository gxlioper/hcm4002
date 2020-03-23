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
     * @Description:  总检审核复审一键恢复阳性发现日志
     * @author: dq     
     * @date:   2018年6月7日 下午5:59:21   
     * @version V2.0.0.0
 */
@Entity
@Table(name="examinfo_disease_log")
public class ExaminfoDiseaseLog implements java.io.Serializable{

	private static final long serialVersionUID = -97502163798576023L;
	
	@Id
	@GenericGenerator(name = "idGenerator", strategy = "identity")
	@GeneratedValue(generator = "idGenerator")
	private long id;
	
	@Column(name="summary_id")
	private long summary_id;
	
	@Column(name="disease_id")
	private long disease_id;
	
	@Column(name = "disease_name")
	private String disease_name;
	
	@Column(name="disease_index")
	private long disease_index;
	
	@Column(name="creater")
	private long creater;
	
	@Column(name="create_time")
	private Date create_time;
	
	@Column(name="disease_type")
	private String disease_type;
	
	@Column(name="icd_10")
	private String icd_10;
	
	@Column(name="suggest")
	private String suggest;
	
	@Column(name="disease_class")
	private String disease_class;
	
	@Column(name="remarke")
	private String remarke;

	@Column(name="disease_num")
	private String disease_num;
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

	public long getDisease_id() {
		return disease_id;
	}

	public void setDisease_id(long disease_id) {
		this.disease_id = disease_id;
	}

	public String getDisease_name() {
		return disease_name;
	}

	public void setDisease_name(String disease_name) {
		this.disease_name = disease_name;
	}

	public long getDisease_index() {
		return disease_index;
	}

	public void setDisease_index(long disease_index) {
		this.disease_index = disease_index;
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

	public String getDisease_type() {
		return disease_type;
	}

	public void setDisease_type(String disease_type) {
		this.disease_type = disease_type;
	}

	public String getIcd_10() {
		return icd_10;
	}

	public void setIcd_10(String icd_10) {
		this.icd_10 = icd_10;
	}

	public String getSuggest() {
		return suggest;
	}

	public void setSuggest(String suggest) {
		this.suggest = suggest;
	}

	public String getDisease_class() {
		return disease_class;
	}

	public void setDisease_class(String disease_class) {
		this.disease_class = disease_class;
	}

	public String getRemarke() {
		return remarke;
	}

	public void setRemarke(String remarke) {
		this.remarke = remarke;
	}

	public String getDisease_num() {
		return disease_num;
	}

	public void setDisease_num(String disease_num) {
		this.disease_num = disease_num;
	}
}
