package com.hjw.wst.DTO;

import java.io.Serializable;

/**
 * 
 * @Title: 火箭蛙体检管理系统
 * @Package com.hjw.wst.domain
 * @Description: 问题
 * @author: zr
 * @String: 2017年1月17日 下午2:55:26
 * @version V2.0.0.0
 */
public class SysSurveyQuestionDTO implements Serializable {
	/**
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	 */
	private static final long serialVersionUID = -4742774374516365257L;

	private long objectId;

	private String code;

	private String qust_type_id;

	private String sex;

	private String sexs;

	private long age;

	private String marriageState;

	private String answer_type;

	private String delete_flg;

	private String create_String;

	private String creater_id;

	private String upString_String;

	private String upStringr_id;

	private String delete_String;

	private String deleter_id;

	private String content;

	private String exam_center_id;

	private long age_to;

	private String question_level;

	private String name;

	private String ages;

	private long agess;

	private String marriageStates;

	private String dep_homepage_show;

	public String getDep_homepage_show() {
		return dep_homepage_show;
	}

	public void setDep_homepage_show(String dep_homepage_show) {
		this.dep_homepage_show = dep_homepage_show;
	}

	public long getAgess() {
		return agess;
	}

	public void setAgess(long agess) {
		this.agess = agess;
	}

	public String getMarriageStates() {
		return marriageStates;
	}

	public void setMarriageStates(String marriageStates) {
		this.marriageStates = marriageStates;
	}

	public String getSexs() {
		return sexs;
	}

	public void setSexs(String sexs) {
		this.sexs = sexs;
	}

	public String getAges() {
		return ages;
	}

	public void setAges(String ages) {
		this.ages = ages;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getObjectId() {
		return objectId;
	}

	public void setObjectId(long objectId) {
		this.objectId = objectId;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getQust_type_id() {
		return qust_type_id;
	}

	public void setQust_type_id(String qust_type_id) {
		this.qust_type_id = qust_type_id;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
		if (sex.equals("M")) {
			this.setSexs("男");
		} else if (sex.equals("F")) {
			this.setSexs("女");
		} else {
			this.setSexs("全部");
		}
	}

	public long getAge() {
		return age;
	}

	public void setAge(long age) {
		this.age = age;
		this.setAges(age + "~" + this.getAge_to());
	}

	public String getMarriageState() {
		return marriageState;
	}

	public void setMarriageState(String marriageState) {
		this.marriageState = marriageState;
		if ("1".equals(marriageState)) {
			this.setMarriageStates("已婚");
		} else if ("2".equals(marriageState)) {
			this.setMarriageStates("未婚");
		} else {
			this.setMarriageStates("全部");
		}
	}

	public String getAnswer_type() {
		return answer_type;
	}

	public void setAnswer_type(String answer_type) {
		this.answer_type = answer_type;
	}

	public String getDelete_flg() {
		return delete_flg;
	}

	public void setDelete_flg(String delete_flg) {
		this.delete_flg = delete_flg;
	}

	public String getCreate_String() {
		return create_String;
	}

	public void setCreate_String(String create_String) {
		this.create_String = create_String;
	}

	public String getCreater_id() {
		return creater_id;
	}

	public void setCreater_id(String creater_id) {
		this.creater_id = creater_id;
	}

	public String getUpString_String() {
		return upString_String;
	}

	public void setUpString_String(String upString_String) {
		this.upString_String = upString_String;
	}

	public String getUpStringr_id() {
		return upStringr_id;
	}

	public void setUpStringr_id(String upStringr_id) {
		this.upStringr_id = upStringr_id;
	}

	public String getDelete_String() {
		return delete_String;
	}

	public void setDelete_String(String delete_String) {
		this.delete_String = delete_String;
	}

	public String getDeleter_id() {
		return deleter_id;
	}

	public void setDeleter_id(String deleter_id) {
		this.deleter_id = deleter_id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getExam_center_id() {
		return exam_center_id;
	}

	public void setExam_center_id(String exam_center_id) {
		this.exam_center_id = exam_center_id;
	}

	public long getAge_to() {
		return age_to;
	}

	public void setAge_to(long age_to) {
		this.age_to = age_to;
		this.setAges(this.getAge() + "~" + this.getAge_to());
	}

	public String getQuestion_level() {
		return question_level;
	}

	public void setQuestion_level(String question_level) {
		this.question_level = question_level;
	}

}
