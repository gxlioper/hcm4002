package com.hjw.wst.DTO;

public class SysQuestionOptionsDTO implements java.io.Serializable {
	  /**   
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)   
	 */ 
	  private static final long serialVersionUID = 1L;
	  private  long   objectId;
	  
      private  String   quest_id;
	  
      private  String   code;
	  
      private  String   content;
	  
      private  String   delete_flg;
	  
      private  String   create_date;
	  
      private  String   creater_id;
	  
      private  String   update_date;
	  
      private  String   updater_id;
	  
      private  String   delete_date;
	  
      private  String   deleter_id;
	  
      private  String   next_quest_code;
	  
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

	public String getCreate_date() {
		return create_date;
	}

	public String getCreater_id() {
		return creater_id;
	}

	public String getUpdate_date() {
		return update_date;
	}

	public String getUpdater_id() {
		return updater_id;
	}

	public String getDelete_date() {
		return delete_date;
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

	public void setCreate_date(String create_date) {
		this.create_date = create_date;
	}

	public void setCreater_id(String creater_id) {
		this.creater_id = creater_id;
	}

	public void setUpdate_date(String update_date) {
		this.update_date = update_date;
	}

	public void setUpdater_id(String updater_id) {
		this.updater_id = updater_id;
	}

	public void setDelete_date(String delete_date) {
		this.delete_date = delete_date;
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
      
}
