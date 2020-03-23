package com.hjw.wst.domain;

import java.io.Serializable;
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
     * @Description:  问题
     * @author: zr     
     * @date:   2017年1月17日 下午2:55:26   
     * @version V2.0.0.0
 */
@Entity
@Table(name="sys_survey_question")
public class SysSurveyQuestion  implements Serializable{
		  /**   
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)   
	 */ 
	private static final long serialVersionUID = -4742774374516365257L;
	
	
		@Id
	     @GenericGenerator(name = "idGenerator", strategy = "identity")
		 @GeneratedValue(generator = "idGenerator")
		  private    long   objectId;
		  
		  @Column(name = "code")
	      private   String  code;
		  
		  @Column(name = "qust_type_id")
	      private   String  qust_type_id;
		  
		  @Column(name = "sex")
	      private   String  sex;
		  
		  @Column(name = "age")
	      private   long     age;
		  
		  @Column(name = "marriageState")
	      private   String  marriageState;
		  
		  @Column(name = "answer_type")
	      private   String  answer_type;
		  
		  @Column(name = "delete_flg")
	      private   String  delete_flg;
		  
		  @Column(name = "create_date")
	      private   Date   create_date;
		  
		  @Column(name = "creater_id")
	      private   String  creater_id;
		  
		  @Column(name = "update_date")
	      private   Date   update_date;
		  
		  @Column(name = "updater_id")
	      private   String  updater_id;
		  
		  @Column(name = "delete_date")
	      private   Date   delete_date;
		  
		  @Column(name = "deleter_id")
	      private   String  deleter_id;
		  
		  @Column(name = "content")
	      private   String  content;
		  
		  @Column(name = "exam_center_id")
	      private   String  exam_center_id;
		  
		  @Column(name = "age_to")
	      private   long  age_to;
		  
		  @Column(name = "question_level")
	      private   String  question_level;
		  
		  @Column(name = "name")
		  private   String  name;
		  
		  @Column(name = "dep_homepage_show")
		  private String dep_homepage_show;
		  
		  
		  
		public String getDep_homepage_show() {
			return dep_homepage_show;
		}
		public void setDep_homepage_show(String dep_homepage_show) {
			this.dep_homepage_show = dep_homepage_show;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public long getObjectId() {
			return objectId;
		}
		public void setObjectId(long objectId) {
			this.objectId = objectId;
		}
		public String getCode() {
			return code;
		}
		public void setCode(String code) {
			this.code = code;
		}
		public String getQust_type_id() {
			return qust_type_id;
		}
		public void setQust_type_id(String qust_type_id) {
			this.qust_type_id = qust_type_id;
		}
		public String getSex() {
			return sex;
		}
		public void setSex(String sex) {
			this.sex = sex;
		}
		public long getAge() {
			return age;
		}
		public void setAge(long age) {
			this.age = age;
		}
		public String getMarriageState() {
			return marriageState;
		}
		public void setMarriageState(String marriageState) {
			this.marriageState = marriageState;
		}
		public String getAnswer_type() {
			return answer_type;
		}
		public void setAnswer_type(String answer_type) {
			this.answer_type = answer_type;
		}
		public String getDelete_flg() {
			return delete_flg;
		}
		public void setDelete_flg(String delete_flg) {
			this.delete_flg = delete_flg;
		}
		public Date getCreate_date() {
			return create_date;
		}
		public void setCreate_date(Date create_date) {
			this.create_date = create_date;
		}
		public String getCreater_id() {
			return creater_id;
		}
		public void setCreater_id(String creater_id) {
			this.creater_id = creater_id;
		}
		public Date getUpdate_date() {
			return update_date;
		}
		public void setUpdate_date(Date update_date) {
			this.update_date = update_date;
		}
		public String getUpdater_id() {
			return updater_id;
		}
		public void setUpdater_id(String updater_id) {
			this.updater_id = updater_id;
		}
		public Date getDelete_date() {
			return delete_date;
		}
		public void setDelete_date(Date delete_date) {
			this.delete_date = delete_date;
		}
		public String getDeleter_id() {
			return deleter_id;
		}
		public void setDeleter_id(String deleter_id) {
			this.deleter_id = deleter_id;
		}
		public String getContent() {
			return content;
		}
		public void setContent(String content) {
			this.content = content;
		}
		public String getExam_center_id() {
			return exam_center_id;
		}
		public void setExam_center_id(String exam_center_id) {
			this.exam_center_id = exam_center_id;
		}
		public long getAge_to() {
			return age_to;
		}
		public void setAge_to(long age_to) {
			this.age_to = age_to;
		}
		public String getQuestion_level() {
			return question_level;
		}
		public void setQuestion_level(String question_level) {
			this.question_level = question_level;
		}
	      
	      
}
