package com.hjw.wst.DTO;

public class SysQuestionItemsDTO  implements java.io.Serializable {
	  
	private static final long serialVersionUID = 1L;

	  private  long objectId;
	  
      private   String  quest_option_id;
	  
      private   String  charge_items_id;
	  
      private   String  weight_value;
      
      private   String  item_code;
      
      private   String  item_name;
      
      private   String  code;
      
      private   long  so_id;
      
      
	public long getSo_id() {
		return so_id;
	}
	public void setSo_id(long so_id) {
		this.so_id = so_id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getItem_code() {
		return item_code;
	}
	public String getItem_name() {
		return item_name;
	}
	public void setItem_code(String item_code) {
		this.item_code = item_code;
	}
	public void setItem_name(String item_name) {
		this.item_name = item_name;
	}
	public long getObjectId() {
		return objectId;
	}
	public String getQuest_option_id() {
		return quest_option_id;
	}
	public String getCharge_items_id() {
		return charge_items_id;
	}
	public String getWeight_value() {
		return weight_value;
	}
	public void setObjectId(long objectId) {
		this.objectId = objectId;
	}
	public void setQuest_option_id(String quest_option_id) {
		this.quest_option_id = quest_option_id;
	}
	public void setCharge_items_id(String charge_items_id) {
		this.charge_items_id = charge_items_id;
	}
	public void setWeight_value(String weight_value) {
		this.weight_value = weight_value;
	}
      
      
}
