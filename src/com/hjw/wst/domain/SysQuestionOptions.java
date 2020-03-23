package com.hjw.wst.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
@Entity
@Table( name ="sys_question_options" )
public class SysQuestionOptions implements java.io.Serializable {
	  /**   
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)   
	 */ 
	  private static final long serialVersionUID = 1L;
	  @Id
	  @GenericGenerator(name = "idGenerator", strategy = "identity")
	  @GeneratedValue(generator = "idGenerator")
	  private  long   objectId;
	  
	  @Column (name="quest_id")
      private  String   quest_id;
	  
	  @Column ( name="code" )
      private  String   code;
	  
	  @Column ( name="content" )
      private  String   content;
	  
	  @Column ( name="delete_flg" )
      private  String   delete_flg;
	  
	  @Column ( name="create_date" )
      private  Date   create_date;
	  
	  @Column ( name="creater_id" )
      private  String   creater_id;
	  
	  @Column ( name="update_date" )
      private  Date   update_date;
	  
	  @Column ( name="updater_id" )
      private  String   updater_id;
	  
	  @Column ( name="delete_date" )
      private  Date   delete_date;
	  
	  @Column ( name="deleter_id" )
      private  String   deleter_id;
	  
	  @Column ( name="next_quest_code" )
      private  String   next_quest_code;
	  
	  @Column ( name="sub_quest_id" )
      private  String   sub_quest_id;
      
	public long getObjectId() {
		return objectId;
	}
	public String getQuest_id() {
		return quest_id;
	}
	public String getCode() {
		return code;
	}
	public String getContent() {
		return content;
	}
	public String getDelete_flg() {
		return delete_flg;
	}
	public Date getCreate_date() {
		return create_date;
	}
	public String getCreater_id() {
		return creater_id;
	}
	public Date getUpdate_date() {
		return update_date;
	}
	public String getUpdater_id() {
		return updater_id;
	}
	public String getDeleter_id() {
		return deleter_id;
	}
	public String getNext_quest_code() {
		return next_quest_code;
	}
	public String getSub_quest_id() {
		return sub_quest_id;
	}
	public void setObjectId(long objectId) {
		this.objectId = objectId;
	}
	public void setQuest_id(String quest_id) {
		this.quest_id = quest_id;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public void setDelete_flg(String delete_flg) {
		this.delete_flg = delete_flg;
	}
	public void setCreate_date(Date create_date) {
		this.create_date = create_date;
	}
	public void setCreater_id(String creater_id) {
		this.creater_id = creater_id;
	}
	public void setUpdate_date(Date update_date) {
		this.update_date = update_date;
	}
	public void setUpdater_id(String updater_id) {
		this.updater_id = updater_id;
	}
	public void setDeleter_id(String deleter_id) {
		this.deleter_id = deleter_id;
	}
	public void setNext_quest_code(String next_quest_code) {
		this.next_quest_code = next_quest_code;
	}
	public void setSub_quest_id(String sub_quest_id) {
		this.sub_quest_id = sub_quest_id;
	}
	public Date getDelete_date() {
		return delete_date;
	}
	public void setDelete_date(Date delete_date) {
		this.delete_date = delete_date;
	}
      
      
	      
}
