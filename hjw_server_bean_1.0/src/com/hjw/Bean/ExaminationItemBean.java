package com.hjw.Bean;

/**
 * 
     * @Title:  火箭蛙体检管理系统   
     * @Package com.hjw.wst.DTO   
     * @Description: 检查项目 
     * @author: zr    
     * @date:   2016年10月22日 下午5:48:04   
     * @version V2.0.0.0
 */
public class ExaminationItemBean implements java.io.Serializable {

	private static final long serialVersionUID = 3432069517438013014L;
	private long   id;
	private long synid;
	private String item_num;
	private String item_name;
	private String item_pinyin;
	private String item_unit;
	private String exam_num;
	private String view_num;
	private String item_category;
	private long   seq_code;
	private String item_description;
	private String remark;
	private double ref_Mmax;
	private double ref_Mmin;
	private double ref_Fmin;
	private double ref_Fmax;
	private double dang_Fmax;
	private double dang_Fmin;
	private double dang_Mmax;
	private double dang_Mmin;
	private String is_Active;	
	private String   create_time;
	private String   update_time;
	private String   default_value;	
	
	public long getSynid() {
		return synid;
	}
	public void setSynid(long synid) {
		this.synid = synid;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getItem_num() {
		return item_num;
	}
	public void setItem_num(String item_num) {
		this.item_num = item_num;
	}
	public String getItem_name() {
		return item_name;
	}
	public void setItem_name(String item_name) {
		this.item_name = item_name;
	}
	public String getItem_pinyin() {
		return item_pinyin;
	}
	public void setItem_pinyin(String item_pinyin) {
		this.item_pinyin = item_pinyin;
	}
	
	public String getItem_unit() {
		return item_unit;
	}
	public void setItem_unit(String item_unit) {
		this.item_unit = item_unit;
	}
	public String getExam_num() {
		return exam_num;
	}
	public void setExam_num(String exam_num) {
		this.exam_num = exam_num;
	}
	public String getView_num() {
		return view_num;
	}
	public void setView_num(String view_num) {
		this.view_num = view_num;
	}
	public String getItem_category() {
		return item_category;
	}
	public void setItem_category(String item_category) {
		this.item_category = item_category;
	}
	public long getSeq_code() {
		return seq_code;
	}
	public void setSeq_code(long seq_code) {
		this.seq_code = seq_code;
	}
	public String getItem_description() {
		return item_description;
	}
	public void setItem_description(String item_description) {
		this.item_description = item_description;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public double getRef_Mmax() {
		return ref_Mmax;
	}
	public void setRef_Mmax(double ref_Mmax) {
		this.ref_Mmax = ref_Mmax;
	}
	public double getRef_Mmin() {
		return ref_Mmin;
	}
	public void setRef_Mmin(double ref_Mmin) {
		this.ref_Mmin = ref_Mmin;
	}
	public double getRef_Fmin() {
		return ref_Fmin;
	}
	public void setRef_Fmin(double ref_Fmin) {
		this.ref_Fmin = ref_Fmin;
	}
	public double getRef_Fmax() {
		return ref_Fmax;
	}
	public void setRef_Fmax(double ref_Fmax) {
		this.ref_Fmax = ref_Fmax;
	}
	public double getDang_Fmax() {
		return dang_Fmax;
	}
	public void setDang_Fmax(double dang_Fmax) {
		this.dang_Fmax = dang_Fmax;
	}
	public double getDang_Fmin() {
		return dang_Fmin;
	}
	public void setDang_Fmin(double dang_Fmin) {
		this.dang_Fmin = dang_Fmin;
	}
	public double getDang_Mmax() {
		return dang_Mmax;
	}
	public void setDang_Mmax(double dang_Mmax) {
		this.dang_Mmax = dang_Mmax;
	}
	public double getDang_Mmin() {
		return dang_Mmin;
	}
	public void setDang_Mmin(double dang_Mmin) {
		this.dang_Mmin = dang_Mmin;
	}
	public String getIs_Active() {
		return is_Active;
	}
	public void setIs_Active(String is_Active) {
		this.is_Active = is_Active;
	}
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	public String getUpdate_time() {
		return update_time;
	}
	public void setUpdate_time(String update_time) {
		this.update_time = update_time;
	}
	public String getDefault_value() {
		return default_value;
	}
	public void setDefault_value(String default_value) {
		this.default_value = default_value;
	}
}
