package com.hjw.wst.DTO;

import java.io.Serializable;

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
     * @Description:  问卷问题
     * @author: zr     
     * @date:   2017年2月17日 上午11:17:57   
     * @version V2.0.0.0
 */
public class sysSurveyQuestionRelationDTO implements Serializable{

	/**   
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)   
	 */ 
	private static final long serialVersionUID = 4672376151990106829L;
	
	  private   long  objectId;
	  
	  private   String  survey_id;
	  
	  private   String  question_id;
	  
	  private   String  code;

	public long getObjectId() {
		return objectId;
	}

	public String getSurvey_id() {
		return survey_id;
	}

	public String getQuestion_id() {
		return question_id;
	}

	public String getCode() {
		return code;
	}

	public void setObjectId(long objectId) {
		this.objectId = objectId;
	}

	public void setSurvey_id(String survey_id) {
		this.survey_id = survey_id;
	}

	public void setQuestion_id(String question_id) {
		this.question_id = question_id;
	}

	public void setCode(String code) {
		this.code = code;
	}
	  
}
