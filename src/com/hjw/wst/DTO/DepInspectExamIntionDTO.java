package com.hjw.wst.DTO;

import java.util.List;

/**
 * 
     * @Title:  火箭蛙体检管理系统   
     * @Package com.hjw.wst.DTO   
     * @Description:  
     * @author: 检查科室--检查项目     
     * @date:   2016年11月22日 下午8:42:24   
     * @version V2.0.0.0
 */
public class DepInspectExamIntionDTO  implements java.io.Serializable{

	/**   
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)   
	 */ 
	private static final long serialVersionUID = -6197402036553071175L;
	private long id;//检查项目id
	private String item_category;//检查项目类型
	private String	item_name;//名称
	private  String  default_value;//默认值
	private  String  exam_result;//结论
	private String  join_date;//参检日期
	private String health_level;//健康状态
	private long c_id;
	private String s_name;
	private long s_id;
	private String exam_doctor_name;
	private String exam_date;
	private String brief_mark;
	private String brief;
	private String item_num;
	private String item_code;
	private String conclusion_word;
	
	private String ref_Mmax;
	private String ref_Mmin;
	private String ref_Fmin;
	private String ref_Fmax;
	private String dang_Fmax;
	private String dang_Fmin;
	private String dang_Mmax;
	private String dang_Mmin;
	
	
	private int yijian;    //已检人数
	private int weijian;  //未检人数
	private int jiancha; //检查中人数
	private int qijian; //弃检人数
	private int yanqi; //延期人数
	private String exam_status;
	private long inputter;
	
	public String getItem_code() {
		return item_code;
	}
	public void setItem_code(String item_code) {
		this.item_code = item_code;
	}
	public long getInputter() {
		return inputter;
	}
	public void setInputter(long inputter) {
		this.inputter = inputter;
	}
	public int getYijian() {
		return yijian;
	}
	public void setYijian(int yijian) {
		this.yijian = yijian;
	}
	public int getWeijian() {
		return weijian;
	}
	public void setWeijian(int weijian) {
		this.weijian = weijian;
	}
	public int getJiancha() {
		return jiancha;
	}
	public void setJiancha(int jiancha) {
		this.jiancha = jiancha;
	}
	public int getQijian() {
		return qijian;
	}
	public void setQijian(int qijian) {
		this.qijian = qijian;
	}
	public int getYanqi() {
		return yanqi;
	}
	public void setYanqi(int yanqi) {
		this.yanqi = yanqi;
	}
	public String getExam_status() {
		return exam_status;
	}
	public void setExam_status(String exam_status) {
		this.exam_status = exam_status;
	}
	private List<examItemRefandDangDTO> itemRefDang;
	
	public String getHealth_level() {
		return health_level;
	}
	public void setHealth_level(String health_level) {
		this.health_level = health_level;
	}
	public String getJoin_date() {
		return join_date;
	}
	public void setJoin_date(String join_date) {
		this.join_date = join_date;
	}
	public String getItem_category() {
		return item_category;
	}
	public void setItem_category(String item_category) {
		this.item_category = item_category;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getItem_name() {
		return item_name;
	}
	public void setItem_name(String item_name) {
		this.item_name = item_name;
	}
	public String getDefault_value() {
		return default_value;
	}
	public void setDefault_value(String default_value) {
		this.default_value = default_value;
	}
	public String getExam_result() {
		return exam_result;
	}
	public void setExam_result(String exam_result) {
		this.exam_result = exam_result;
	}
	public long getC_id() {
		return c_id;
	}
	public void setC_id(long c_id) {
		this.c_id = c_id;
	}
	public String getS_name() {
		return s_name;
	}
	public void setS_name(String s_name) {
		this.s_name = s_name;
	}
	public long getS_id() {
		return s_id;
	}
	public void setS_id(long s_id) {
		this.s_id = s_id;
	}
	public String getExam_doctor_name() {
		return exam_doctor_name;
	}
	public void setExam_doctor_name(String exam_doctor_name) {
		this.exam_doctor_name = exam_doctor_name;
	}
	public String getExam_date() {
		return exam_date;
	}
	public void setExam_date(String exam_date) {
		this.exam_date = exam_date;
	}
	public String getBrief_mark() {
		return brief_mark;
	}
	public void setBrief_mark(String brief_mark) {
		this.brief_mark = brief_mark;
	}
	public String getBrief() {
		return brief;
	}
	public void setBrief(String brief) {
		this.brief = brief;
	}
	public String getItem_num() {
		return item_num;
	}
	public void setItem_num(String item_num) {
		this.item_num = item_num;
	}
	public String getConclusion_word() {
		return conclusion_word;
	}
	public void setConclusion_word(String conclusion_word) {
		this.conclusion_word = conclusion_word;
	}
	public String getRef_Mmax() {
		return ref_Mmax;
	}
	public void setRef_Mmax(String ref_Mmax) {
		this.ref_Mmax = ref_Mmax;
	}
	public String getRef_Mmin() {
		return ref_Mmin;
	}
	public void setRef_Mmin(String ref_Mmin) {
		this.ref_Mmin = ref_Mmin;
	}
	public String getRef_Fmin() {
		return ref_Fmin;
	}
	public void setRef_Fmin(String ref_Fmin) {
		this.ref_Fmin = ref_Fmin;
	}
	public String getRef_Fmax() {
		return ref_Fmax;
	}
	public void setRef_Fmax(String ref_Fmax) {
		this.ref_Fmax = ref_Fmax;
	}
	public String getDang_Fmax() {
		return dang_Fmax;
	}
	public void setDang_Fmax(String dang_Fmax) {
		this.dang_Fmax = dang_Fmax;
	}
	public String getDang_Fmin() {
		return dang_Fmin;
	}
	public void setDang_Fmin(String dang_Fmin) {
		this.dang_Fmin = dang_Fmin;
	}
	public String getDang_Mmax() {
		return dang_Mmax;
	}
	public void setDang_Mmax(String dang_Mmax) {
		this.dang_Mmax = dang_Mmax;
	}
	public String getDang_Mmin() {
		return dang_Mmin;
	}
	public void setDang_Mmin(String dang_Mmin) {
		this.dang_Mmin = dang_Mmin;
	}
	public List<examItemRefandDangDTO> getItemRefDang() {
		return itemRefDang;
	}
	public void setItemRefDang(List<examItemRefandDangDTO> itemRefDang) {
		this.itemRefDang = itemRefDang;
	}
}
