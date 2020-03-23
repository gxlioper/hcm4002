package com.hjw.wst.DTO;

/**
 * 
     * @Title:  火箭蛙体检管理系统   
     * @Package com.hjw.wst.DTO   
     * @Description: 检查项目 
     * @author: zr    
     * @date:   2016年10月22日 下午5:48:04   
     * @version V2.0.0.0
 */
public class ExaminationItemDTO implements java.io.Serializable {

	private static final long serialVersionUID = 3432069517438013014L;

	private long   id;
	
	private long   cid;//收费项目id

	private String item_num;

	private String item_name;

	private String item_pinyin;

	private String item_eng_name;

	private String item_unit;

	private String exam_num;

	private String view_num;

	private String item_category;

	private String is_print;
	
	private String is_prints;
	
	private Long   seq_code;
	
	private String seq_codes;

	private String item_description;

	private String remark;

	private Double ref_Mmax;

	private Double ref_Mmin;

	private Double ref_Fmin;

	private Double ref_Fmax;

	private Double dang_Fmax;

	private Double dang_Fmin;

	private Double dang_Mmax;

	private Double dang_Mmin;

	private String is_Active;

	private String   creatername;

	private String   create_time;

	private String   updatername;

	private String   update_time;

	private Long   default_value;
	
	private String default_values;

	private String dataName;//项目类型
	
	private String reference;//拼接参考值
	
	private String risk;//拼接危险值
	
	private String 		 brief_mark;//小结标示，
	
	private String 		 brief;//记入小结
	
	private String exam_item_id;

	private String sex;
	
	private String dep_name;
	private String text;
	private long item_class_id;
	private String item_class_name;
	private long charging_item_id;
	private long exam_itemid;
	private long cteid;
	private long item_result_type;
	private int disease_count;

    public int getDisease_count() {
        return disease_count;
    }

    public void setDisease_count(int disease_count) {
        this.disease_count = disease_count;
    }

    public long getItem_result_type() {
		return item_result_type;
	}

	public void setItem_result_type(long item_result_type) {
		this.item_result_type = item_result_type;
	}

	public String getExam_item_id() {
		return exam_item_id;
	}

	public void setExam_item_id(String exam_item_id) {
		this.exam_item_id = exam_item_id;
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
		if(is_print==null){
			this.setIs_prints("否");
		}else if(is_print.equals("Y")){
			this.setIs_prints("是");
		}else{
			this.setIs_prints("否");
		}
	}

	public Long getSeq_code() {
		return seq_code;
	}

	public void setSeq_code(Long seq_code) {
		this.seq_code = seq_code;
		String fa=seq_code+"";
		this.setSeq_codes(fa);
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

	public String getCreatername() {
		return creatername;
	}

	public void setCreatername(String creatername) {
		this.creatername = creatername;
	}

	public String getCreate_time() {
		return create_time;
	}

	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}

	public String getUpdatername() {
		return updatername;
	}

	public void setUpdatername(String updatername) {
		this.updatername = updatername;
	}

	public String getUpdate_time() {
		return update_time;
	}

	public void setUpdate_time(String update_time) {
		this.update_time = update_time;
	}

	public Long getDefault_value() {
		return default_value;
	}

	public void setDefault_value(Long default_value) {
		this.default_value = default_value;
		String  ak=default_value+"";
		this.setDefault_values(ak);
	}

	public String getDataName() {
		return dataName;
	}

	public void setDataName(String dataName) {
		this.dataName = dataName;
	}

	public String getIs_prints() {
		return is_prints;
	}

	public void setIs_prints(String is_prints) {
		this.is_prints=is_prints;
	}
	//参考值
	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		 
		this.reference = reference;
	}
	//危机值
	public String getRisk() {
		return risk;
	}

	public void setRisk(String risk) {
		this.risk = risk;
	}

	public long getCid() {
		return cid;
	}

	public void setCid(long cid) {
		this.cid = cid;
	}

	public String getSeq_codes() {
		return seq_codes;
	}

	public void setSeq_codes(String seq_codes) {
		this.seq_codes = seq_codes;
	}

	public String getDefault_values() {
		return default_values;
	}

	public void setDefault_values(String default_values) {
		this.default_values = default_values;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getDep_name() {
		return dep_name;
	}

	public void setDep_name(String dep_name) {
		this.dep_name = dep_name;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public long getItem_class_id() {
		return item_class_id;
	}

	public void setItem_class_id(long item_class_id) {
		this.item_class_id = item_class_id;
	}

	public String getItem_class_name() {
		return item_class_name;
	}

	public void setItem_class_name(String item_class_name) {
		this.item_class_name = item_class_name;
	}

	public long getCharging_item_id() {
		return charging_item_id;
	}

	public void setCharging_item_id(long charging_item_id) {
		this.charging_item_id = charging_item_id;
	}

	public long getExam_itemid() {
		return exam_itemid;
	}

	public void setExam_itemid(long exam_itemid) {
		this.exam_itemid = exam_itemid;
	}

	public long getCteid() {
		return cteid;
	}

	public void setCteid(long cteid) {
		this.cteid = cteid;
	}
}
