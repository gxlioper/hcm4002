package com.hjw.wst.DTO;


public class CrmSmsBaseTemplateDTO   implements java.io.Serializable{
	
	/**   
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)   
	 */ 
	private static final long serialVersionUID = -6316887964764639885L;
	private String id;
	private String	   sms_category;
	private String	   sms_name;
	private String	   sms_note;
	private long	   creater;
	private String	   create_time;
	private long	   updater;
	private String	   update_time;
	private String chi_name_x;
	private String chi_name_c;
	private String sms_state_s;
	private String sms_state;
	
	
	public String getSms_state() {
		return sms_state;
	}
	public void setSms_state(String sms_state) {
		this.sms_state = sms_state;
		if("Y".equals(sms_state)){
			this.setSms_state_s("是");
		} else {
			this.setSms_state_s("否");
		}
	}
	public String getSms_state_s() {
		return sms_state_s;
	}
	public void setSms_state_s(String sms_state_s) {
		this.sms_state_s = sms_state_s;
	}
	public String getChi_name_x() {
		return chi_name_x;
	}
	public void setChi_name_x(String chi_name_x) {
		this.chi_name_x = chi_name_x;
	}
	public String getChi_name_c() {
		return chi_name_c;
	}
	public void setChi_name_c(String chi_name_c) {
		this.chi_name_c = chi_name_c;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSms_category() {
		return sms_category;
	}
	public void setSms_category(String sms_category) {
		this.sms_category = sms_category;
	}
	public String getSms_name() {
		return sms_name;
	}
	public void setSms_name(String sms_name) {
		this.sms_name = sms_name;
	}
	public String getSms_note() {
		return sms_note;
	}
	public void setSms_note(String sms_note) {
		this.sms_note = sms_note;
	}
	public long getCreater() {
		return creater;
	}
	public void setCreater(long creater) {
		this.creater = creater;
	}
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	public long getUpdater() {
		return updater;
	}
	public void setUpdater(long updater) {
		this.updater = updater;
	}
	public String getUpdate_time() {
		return update_time;
	}
	public void setUpdate_time(String update_time) {
		this.update_time = update_time;
	}
	
	
	
	
}
