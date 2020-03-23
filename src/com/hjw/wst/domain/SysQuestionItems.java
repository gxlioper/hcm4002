package com.hjw.wst.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table( name = "sys_question_items")
public class SysQuestionItems  implements java.io.Serializable {
	  
	  /**   
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)   
	 */ 
	private static final long serialVersionUID = 1L;

	  @Id
	  @GenericGenerator(name = "idGenerator", strategy = "identity")
	  @GeneratedValue(generator = "idGenerator")
	  private  long objectId;
	  
	  @Column( name = "quest_option_id")
      private   String  quest_option_id;
	  
	  @Column( name = "charge_items_id")
      private   String  charge_items_id;
	  
	  @Column( name = "weight_value")
      private   String  weight_value;
      
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
