package com.hjw.wst.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="examination_item")
public class ExaminationItem implements java.io.Serializable{
	private static final Long serialVersionUID = -5559290153225610170L;
	
	@Id
	@GenericGenerator(name = "idGenerator", strategy = "identity")
	@GeneratedValue(generator = "idGenerator")
	private	 Long	id;
	
	@Column(name="item_num")
	private	 String	    item_num;
	
	@Column(name="item_name")
	private	String		item_name;
	
	@Column(name="item_pinyin")
	private	String		item_pinyin;
	
	@Column(name="item_eng_name")
	private	String		item_eng_name;
	
	@Column(name="item_unit")
	private	String		item_unit;//单位
	
	@Column(name="exam_num")
	private	String		exam_num;
	
	@Column(name="view_num")
	private	String		view_num;
	
	@Column(name="item_category")
	private	String		item_category;
	
	@Column(name="is_print")
	private	String		is_print;
	
	@Column(name="seq_code")
	private	 Long		seq_code;
	
	@Column(name="item_description")
	private	String  	item_description;
	
	@Column(name="remark")
	private	String		remark;
	
	@Column(name="ref_Mmax")
	private	Double 		ref_Mmax;
	
	@Column(name="ref_Mmin")
    private	Double 		ref_Mmin;
	
	@Column(name="ref_Fmin")
    private	Double		ref_Fmin;
	
	@Column(name="ref_Fmax")
	private	Double		ref_Fmax;
	
	@Column(name="dang_Fmax")
	private	Double		dang_Fmax;
	
	@Column(name="dang_Fmin")
	private	Double		dang_Fmin;
	
	@Column(name="dang_Mmax")
	private	Double		dang_Mmax;
	
	@Column(name="dang_Mmin")
	private	Double		dang_Mmin;
	
	@Column(name="is_Active")
	private	String		is_Active;
	
	@Column(name="creater")
	private	Long		creater;
	
	@Column(name="create_time")
	private	Date		create_time;
	
	@Column(name="updater")
	private	Long		updater;
	
	@Column(name="update_time")
	private	Date	    update_time;
	
	@Column(name="default_value")
	private	Long		default_value;
	
	@Column(name="item_type")
	private	String		item_type;
	
	@Column(name="brief_mark")
	private String 		 brief_mark;//小结标示，
	
	@Column(name="brief")
	private String 		 brief;//记入小结
	@Column(name="limite_max")
	private Double error_max;//纠错上限
	@Column(name="limite_min")
	private Double error_min;//纠错下限
	@Column(name="sex")
	private String sex;
	@Column(name="dep_id") //科室ID
	private long dep_id;
	@Column(name="item_class_id")
	private long item_class_id;
	
	@Column(name="item_result_type")
	private long item_result_type;
	
	public long getItem_result_type() {
		return item_result_type;
	}

	public void setItem_result_type(long item_result_type) {
		this.item_result_type = item_result_type;
	}

	public Double getError_max() {
		return error_max;
	}

	public void setError_max(Double error_max) {
		this.error_max = error_max;
	}

	public Double getError_min() {
		return error_min;
	}

	public void setError_min(Double error_min) {
		this.error_min = error_min;
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
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

	public String getItem_eng_name() {
		return item_eng_name;
	}

	public void setItem_eng_name(String item_eng_name) {
		this.item_eng_name = item_eng_name;
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

	public String getIs_print() {
		return is_print;
	}

	public void setIs_print(String is_print) {
		this.is_print = is_print;
	}

	public Long getSeq_code() {
		return seq_code;
	}

	public void setSeq_code(Long seq_code) {
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

	public Double getRef_Mmax() {
		return ref_Mmax;
	}

	public void setRef_Mmax(Double ref_Mmax) {
		this.ref_Mmax = ref_Mmax;
	}

	public Double getRef_Mmin() {
		return ref_Mmin;
	}

	public void setRef_Mmin(Double ref_Mmin) {
		this.ref_Mmin = ref_Mmin;
	}

	public Double getRef_Fmin() {
		return ref_Fmin;
	}

	public void setRef_Fmin(Double ref_Fmin) {
		this.ref_Fmin = ref_Fmin;
	}

	public Double getRef_Fmax() {
		return ref_Fmax;
	}

	public void setRef_Fmax(Double ref_Fmax) {
		this.ref_Fmax = ref_Fmax;
	}

	public Double getDang_Fmax() {
		return dang_Fmax;
	}

	public void setDang_Fmax(Double dang_Fmax) {
		this.dang_Fmax = dang_Fmax;
	}

	public Double getDang_Fmin() {
		return dang_Fmin;
	}

	public void setDang_Fmin(Double dang_Fmin) {
		this.dang_Fmin = dang_Fmin;
	}

	public Double getDang_Mmax() {
		return dang_Mmax;
	}

	public void setDang_Mmax(Double dang_Mmax) {
		this.dang_Mmax = dang_Mmax;
	}

	public Double getDang_Mmin() {
		return dang_Mmin;
	}

	public void setDang_Mmin(Double dang_Mmin) {
		this.dang_Mmin = dang_Mmin;
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

	public Date getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}

	public Long getUpdater() {
		return updater;
	}

	public void setUpdater(Long updater) {
		this.updater = updater;
	}

	public Date getUpdate_time() {
		return update_time;
	}

	public void setUpdate_time(Date update_time) {
		this.update_time = update_time;
	}

	public Long getDefault_value() {
		return default_value;
	}

	public void setDefault_value(Long default_value) {
		this.default_value = default_value;
	}

	public String getItem_type() {
		return item_type;
	}

	public void setItem_type(String item_type) {
		this.item_type = item_type;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public long getDep_id() {
		return dep_id;
	}

	public void setDep_id(long dep_id) {
		this.dep_id = dep_id;
	}

	public long getItem_class_id() {
		return item_class_id;
	}

	public void setItem_class_id(long item_class_id) {
		this.item_class_id = item_class_id;
	}
	
}
