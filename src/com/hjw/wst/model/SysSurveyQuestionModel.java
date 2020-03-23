package com.hjw.wst.model;

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
public class SysSurveyQuestionModel implements Serializable {
	/**
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	 */
	private static final long serialVersionUID = -4742774374516365257L;

	private long objectId;

	private String name;

	private String code;

	private String qust_type_id;

	private String sex;

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

	private String wenti;

	private String shoufeixm;

	private String customer_id;

	private String dep_homepage_show;// 是否显示在科室首页


	private String s_user_name;
	private String s_sex;
	private long s_age;
	private String s_com_name;
	private String s_phone;
	private String s_data_name;
	private String s_set_name;
	private String s_exam_num;

	private String shujulist;

	private String user_id;// 用户id
	private String person_name;// 问卷人
	private String exam_num_x;// 体检编码

	public String getExam_num_x() {
		return exam_num_x;
	}

	public void setExam_num_x(String exam_num_x) {
		this.exam_num_x = exam_num_x;
	}

	public String getCustomer_id() {
		return customer_id;
	}

	public void setCustomer_id(String customer_id) {
		this.customer_id = customer_id;
	}

	public String getShujulist() {
		return shujulist;
	}

	public String getUser_id() {
		return user_id;
	}

	public String getPerson_name() {
		return person_name;
	}

	public void setShujulist(String shujulist) {
		this.shujulist = shujulist;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public void setPerson_name(String person_name) {
		this.person_name = person_name;
	}

	public String getShujuli() {
		return shujulist;
	}

	public String getS_exam_num() {
		return s_exam_num;
	}

	public void setS_exam_num(String s_exam_num) {
		this.s_exam_num = s_exam_num;
	}

	public String getS_user_name() {
		return s_user_name;
	}

	public String getS_sex() {
		return s_sex;
	}

	public long getS_age() {
		return s_age;
	}

	public String getS_com_name() {
		return s_com_name;
	}

	public String getS_phone() {
		return s_phone;
	}

	public String getS_data_name() {
		return s_data_name;
	}

	public String getS_set_name() {
		return s_set_name;
	}

	public void setS_user_name(String s_user_name) {
		this.s_user_name = s_user_name;
	}

	public void setS_sex(String s_sex) {
		this.s_sex = s_sex;
	}

	public void setS_age(long s_age) {
		this.s_age = s_age;
	}

	public void setS_com_name(String s_com_name) {
		this.s_com_name = s_com_name;
	}

	public void setS_phone(String s_phone) {
		this.s_phone = s_phone;
	}

	public void setS_data_name(String s_data_name) {
		this.s_data_name = s_data_name;
	}

	public void setS_set_name(String s_set_name) {
		this.s_set_name = s_set_name;
	}

	public String getShoufeixm() {
		return shoufeixm;
	}

	public void setShoufeixm(String shoufeixm) {
		this.shoufeixm = shoufeixm;
	}

	public String getWenti() {
		return wenti;
	}

	public void setWenti(String wenti) {
		this.wenti = wenti;
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
	}

	public long getAge() {
		return age;
	}

	public void setAge(long age) {
		this.age = age;
	}

	public String getMarriageState() {
		return marriageState;
	}

	public void setMarriageState(String marriageState) {
		this.marriageState = marriageState;
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
	}

	public String getQuestion_level() {
		return question_level;
	}

	public void setQuestion_level(String question_level) {
		this.question_level = question_level;
	}
	public String getDep_homepage_show() {
		return dep_homepage_show;
	}

	public void setDep_homepage_show(String dep_homepage_show) {
		this.dep_homepage_show = dep_homepage_show;
	}
}
