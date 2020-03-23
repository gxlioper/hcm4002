package com.hjw.wst.model;

public class SensitiveWordsLibModel implements java.io.Serializable {
	private static final long serialVersionUID = -97502163798576023L;
	
	private String id;
	private String sensitive_type;  //敏感词类型  1表示性别不一致敏感词  2表示特殊重要疾病敏感词
	private String sensitive_sex;  //适用性别
	private String sensitive_content;//敏感词内容
	
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
}
