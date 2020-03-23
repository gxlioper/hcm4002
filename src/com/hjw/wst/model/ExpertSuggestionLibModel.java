package com.hjw.wst.model;
/**
 * 
     * @Title:  火箭蛙体检管理系统   
     * @Package com.hjw.wst.model   
     * @Description: 专家建议知识库 
     * @author: zr    
     * @date:   2016年11月2日 下午2:39:23   
     * @version V2.0.0.0
 */

public class ExpertSuggestionLibModel  implements java.io.Serializable{
	
	private static final long serialVersionUID = -7472045454210508663L;

	private    long  id;
	
	private    long  dep_id;
	
	private    String  sugg_word;
	
	private    String  sugg_content;
	
	private    String  sugg_num;
	
	private    String  is_Active;
	
	private    Long  creater;
	
	private    String  create_time;
	
	private    Long  updater;
	
	private    String  update_time;
	
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
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	public Long getUpdater() {
		return updater;
	}
	public void setUpdater(Long updater) {
		this.updater = updater;
	}
	public String getUpdate_time() {
		return update_time;
	}
	public void setUpdate_time(String update_time) {
		this.update_time = update_time;
	}
	
}
