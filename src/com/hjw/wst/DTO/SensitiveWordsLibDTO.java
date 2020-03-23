package com.hjw.wst.DTO;


public class SensitiveWordsLibDTO implements java.io.Serializable {
	private static final long serialVersionUID = -97502163798576023L;
	
	private String id;
	private String sensitive_type;  //敏感词类型  1表示性别不一致敏感词  2表示特殊重要疾病敏感词
	private String sensitive_types;
	private String sensitive_sex;  //适用性别
	private String sensitive_content;//敏感词内容
	private String is_active;
	private String updater;
	private String update_time;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSensitive_type() {
		return sensitive_type;
	}
	public void setSensitive_type(String sensitive_type) {
		this.sensitive_type = sensitive_type;
		if("1".equals(sensitive_type)){
			this.sensitive_types = "性别敏感";
		}else if("2".equals(sensitive_type)){
			this.sensitive_types = "特殊词敏感";
		}
	}
	public String getSensitive_types() {
		return sensitive_types;
	}
	public void setSensitive_types(String sensitive_types) {
		this.sensitive_types = sensitive_types;
	}
	public String getSensitive_sex() {
		return sensitive_sex;
	}
	public void setSensitive_sex(String sensitive_sex) {
		this.sensitive_sex = sensitive_sex;
	}
	public String getSensitive_content() {
		return sensitive_content;
	}
	public void setSensitive_content(String sensitive_content) {
		this.sensitive_content = sensitive_content;
	}
	public String getIs_active() {
		return is_active;
	}
	public void setIs_active(String is_active) {
		this.is_active = is_active;
	}
	public String getUpdater() {
		return updater;
	}
	public void setUpdater(String updater) {
		this.updater = updater;
	}
	public String getUpdate_time() {
		return update_time;
	}
	public void setUpdate_time(String update_time) {
		this.update_time = update_time;
	}
}
