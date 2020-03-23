package com.hjw.wst.model;

/**
 * 体检综述（健康指导建议）
     * @Title:  火箭蛙体检管理系统   
     * @Package com.hjw.wst.model   
     * @Description:  
     * @author: dq     
     * @date:   2017年7月13日 下午4:55:56   
     * @version V2.0.0.0
 */
public class ExamSummaryGuidanceLibModel {
	
	private long id;
	private String guidance_word;
	private String guidance_pinyin;
	private String guidance_content;
	
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
}
