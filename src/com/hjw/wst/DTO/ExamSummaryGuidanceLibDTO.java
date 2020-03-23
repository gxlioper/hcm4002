package com.hjw.wst.DTO;

public class ExamSummaryGuidanceLibDTO implements java.io.Serializable{
	private static final long serialVersionUID = -97502163798576023L;
	private long id;
	private String guidance_word;
	private String guidance_pinyin;
	private String guidance_content;
	private String is_active;
	private String updater;
	private String update_time;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getGuidance_word() {
		return guidance_word;
	}
	public void setGuidance_word(String guidance_word) {
		this.guidance_word = guidance_word;
	}
	public String getGuidance_pinyin() {
		return guidance_pinyin;
	}
	public void setGuidance_pinyin(String guidance_pinyin) {
		this.guidance_pinyin = guidance_pinyin;
	}
	public String getGuidance_content() {
		return guidance_content;
	}
	public void setGuidance_content(String guidance_content) {
		this.guidance_content = guidance_content;
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
