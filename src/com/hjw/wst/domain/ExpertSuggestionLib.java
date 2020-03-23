package com.hjw.wst.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
@Entity
@Table(name="expert_suggestion_lib")
public class ExpertSuggestionLib  implements java.io.Serializable{
	private static final long serialVersionUID = 5817368272190984692L;
	
	@Id
	@GenericGenerator(name = "idGenerator", strategy = "identity")
	@GeneratedValue(generator = "idGenerator")
	private    long  id;
	
	@Column(name="dep_id")
	private    long  dep_id;
	
	@Column(name="sugg_word")
	private    String  sugg_word;
	
	@Column(name="sugg_content")
	private    String  sugg_content;
	
	@Column(name="sugg_num")
	private    String  sugg_num;
	
	@Column(name="is_Active")
	private    String  is_Active;
	
	@Column(name="creater")
	private    Long  creater;
	
	@Column(name="create_time")
	private    Date  create_time;
	
	@Column(name="updater")
	private    Long  updater;
	
	@Column(name="update_time")
	private    Date  update_time;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getDep_id() {
		return dep_id;
	}
	public void setDep_id(long dep_id) {
		this.dep_id = dep_id;
	}
	public String getSugg_word() {
		return sugg_word;
	}
	public void setSugg_word(String sugg_word) {
		this.sugg_word = sugg_word;
	}
	public String getSugg_content() {
		return sugg_content;
	}
	public void setSugg_content(String sugg_content) {
		this.sugg_content = sugg_content;
	}
	public String getSugg_num() {
		return sugg_num;
	}
	public void setSugg_num(String sugg_num) {
		this.sugg_num = sugg_num;
	}
	public String getIs_Active() {
		return is_Active;
	}
	public void setIs_Active(String is_Active) {
		this.is_Active = is_Active;
	}
	public Long getCreater() {
		return creater;
	}
	public void setCreater(Long creater) {
		this.creater = creater;
	}
	public Date getCreate_time() {
		return create_time;
	}
	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}
	public Long getUpdater() {
		return updater;
	}
	public void setUpdater(Long updater) {
		this.updater = updater;
	}
	public Date getUpdate_time() {
		return update_time;
	}
	public void setUpdate_time(Date update_time) {
		this.update_time = update_time;
	}
	
}
