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
     * @Description: 项目知识库 
     * @author: zr     
     * @date:   2016年10月26日 上午10:45:52   
     * @version V2.0.0.0
 */
@Entity
@Table(name="item_result_lib")
public class ItemResultLib  implements java.io.Serializable{
	private static final long serialVersionUID = -2246054263316406307L;
	
	@Id
	@GenericGenerator(name = "idGenerator", strategy = "identity")
	@GeneratedValue(generator = "idGenerator")
	private   long      id;
	
	@Column(name="exam_item_id")
	private   long      exam_item_id;
	
	@Column(name="dep_id")
	private   long      dep_id;
	
	@Column(name="exam_result")
	private   String    exam_result;
	
	@Column(name="result_num")
	private   String    result_num;
	
	@Column(name="isActive")
	private   String    isActive;
	
	@Column(name="creater")
	private   long      creater;
	
	@Column(name="create_time")
	private   Date      create_time;
	
	@Column(name="updater")
	private   long      updater;
	
	@Column(name="update_time")
	private   Date      update_time;
	
	@Column(name="seq_code")
	private   long      seq_code;
	
	@Column(name="common_words")
	private   String   common_words;
	
	@Column(name="exam_conclusion")
	private   String   exam_conclusion;

	@Column(name="item_code")
	private   String   item_code;
	
	public String getItem_code() {
		return item_code;
	}

	public void setItem_code(String item_code) {
		this.item_code = item_code;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getExam_item_id() {
		return exam_item_id;
	}

	public void setExam_item_id(long exam_item_id) {
		this.exam_item_id = exam_item_id;
	}

	public long getDep_id() {
		return dep_id;
	}

	public void setDep_id(long dep_id) {
		this.dep_id = dep_id;
	}

	public String getExam_result() {
		return exam_result;
	}

	public void setExam_result(String exam_result) {
		this.exam_result = exam_result;
	}

	public String getResult_num() {
		return result_num;
	}

	public void setResult_num(String result_num) {
		this.result_num = result_num;
	}

	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
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

	public long getSeq_code() {
		return seq_code;
	}

	public void setSeq_code(long seq_code) {
		this.seq_code = seq_code;
	}

	public String getCommon_words() {
		return common_words;
	}

	public void setCommon_words(String common_words) {
		this.common_words = common_words;
	}

	public String getExam_conclusion() {
		return exam_conclusion;
	}

	public void setExam_conclusion(String exam_conclusion) {
		this.exam_conclusion = exam_conclusion;
	}
	
}
