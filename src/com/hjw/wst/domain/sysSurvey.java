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
     * @Description:  调查问卷
     * @author: zr     
     * @date:   2017年2月17日 上午10:01:43   
     * @version V2.0.0.0
 */
@Entity
@Table(name="sys_survey")
public class sysSurvey implements Serializable{

	/**   
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)   
	 */ 
	private static final long serialVersionUID = 2171940682620442144L;
	
	
	  @Id
	  @GenericGenerator(name = "idGenerator", strategy = "identity")
	  @GeneratedValue(generator = "idGenerator")
	  @Column(name="objectId")
	  private  long   objectId;
	  
	  @Column(name="code")
	  private  String   code;
	  
	  @Column(name="name")
	  private  String   name;
	  
	  @Column(name="enable")
	  private  String   enable;
	  
	  @Column(name="delete_flg")
	  private  String   delete_flg;
	  
	  @Column(name="create_date")
	  private  Date   create_date;
	  
	  @Column(name="creater_id")
	  private  String   creater_id;
	  
	  @Column(name="update_date")
	  private  Date   update_date;
	  
	  @Column(name="updater_id")
	  private  String   updater_id;
	  
	  @Column(name="delete_date")
	  private  Date   delete_date;
	  
	  @Column(name="deleter_id")
	  private  String   deleter_id;
	  
	  @Column(name="exam_center_id")
	  private  String   exam_center_id;
	  
	  @Column(name="user_id")
	  private  String   user_id;
	  
	  @Column(name="person_name")
	  private  String   person_name;
	  
	  @Column(name="sex")
	  private  String   sex;
	  
	  @Column(name="age")
	  private  long   age;

	public long getObjectId() {
		return objectId;
	}

	public String getCode() {
		return code;
	}

	public String getName() {
		return name;
	}

	public String getEnable() {
		return enable;
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

	public Date getDelete_date() {
		return delete_date;
	}

	public String getDeleter_id() {
		return deleter_id;
	}

	public String getExam_center_id() {
		return exam_center_id;
	}

	public String getUser_id() {
		return user_id;
	}

	public String getPerson_name() {
		return person_name;
	}

	public String getSex() {
		return sex;
	}

	public long getAge() {
		return age;
	}

	public void setObjectId(long objectId) {
		this.objectId = objectId;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setEnable(String enable) {
		this.enable = enable;
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

	public void setDelete_date(Date delete_date) {
		this.delete_date = delete_date;
	}

	public void setDeleter_id(String deleter_id) {
		this.deleter_id = deleter_id;
	}

	public void setExam_center_id(String exam_center_id) {
		this.exam_center_id = exam_center_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public void setPerson_name(String person_name) {
		this.person_name = person_name;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public void setAge(long age) {
		this.age = age;
	}
	  
	  
}
