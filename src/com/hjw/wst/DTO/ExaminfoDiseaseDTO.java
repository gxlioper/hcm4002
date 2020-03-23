package com.hjw.wst.DTO;

public class ExaminfoDiseaseDTO {

	private long id;
	private long exam_info_id;
	private long disease_id;
	private String disease_name;
	private long disease_index;
	private String disease_type;
	private String disease_types;
	private String icd_10;
	private String suggest;
	private String disease_class;
	private String disease_classs;
	private String diagnosis_source;//诊断来源
	private String item_source;//来源组合项目
	private String exam_result;//个人体检结论
	private String career_hazards;//职业危害
	private String career_suggest;//职业建议
	private String resultID;
	private String occudiseaseIDorcontraindicationID;//职业病和禁忌症ID
	private String disease_description;
	private String ds;
	
	private String item_name;
	private String eitem_name;
	private String dep_category;
	private long cid;
	private long eid; 
	private String exam_num;
	private String disease_num;
	private long data_source;//数据来源 0表示自动生成、1表示手工添加、2表示手动合并 
	private String data_source_json;//数据来源条件，data_source为0或2时保存json格式数据
	private String charging_item_name;
	private String dep_num;
	private String item_code;
	private String charging_item_code;
    
    public String getDs() {
		return ds;
	}

	public void setDs(String ds) {
		this.ds = ds;
	}

	public long getData_source() {
		return data_source;
	}

	public void setData_source(long data_source) {
		this.data_source = data_source;
	}

	public String getData_source_json() {
		return data_source_json;
	}

	public void setData_source_json(String data_source_json) {
		this.data_source_json = data_source_json;
	}

	public String getDisease_num() {
		return disease_num;
	}

	public void setDisease_num(String disease_num) {
		this.disease_num = disease_num;
	}

	public String getExam_num() {
		return exam_num;
	}

	public void setExam_num(String exam_num) {
		this.exam_num = exam_num;
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getExam_info_id() {
		return exam_info_id;
	}
	public void setExam_info_id(long exam_info_id) {
		this.exam_info_id = exam_info_id;
	}
	public long getDisease_id() {
		return disease_id;
	}
	public void setDisease_id(long disease_id) {
		this.disease_id = disease_id;
	}
	public String getDisease_name() {
		return disease_name;
	}
	public void setDisease_name(String disease_name) {
		this.disease_name = disease_name;
	}
	public long getDisease_index() {
		return disease_index;
	}
	public void setDisease_index(long disease_index) {
		this.disease_index = disease_index;
	}
	public String getDisease_type() {
		return disease_type;
	}
	public void setDisease_type(String disease_type) {
		this.disease_type = disease_type;
		if("Y".equals(disease_type)){
			this.disease_types = "阳性发现";
		}else{
			this.disease_types = "疾病";
		}
	}
	public String getSuggest() {
		return suggest;
	}
	public void setSuggest(String suggest) {
		this.suggest = suggest;
	}
	public String getDisease_types() {
		return disease_types;
	}
	public void setDisease_types(String disease_types) {
		this.disease_types = disease_types;
	}
	public String getIcd_10() {
		return icd_10;
	}
	public void setIcd_10(String icd_10) {
		this.icd_10 = icd_10;
	}
	public String getDisease_class() {
		return disease_class;
	}
	public void setDisease_class(String disease_class) {
		this.disease_class = disease_class;
	}
	public String getDisease_classs() {
		return disease_classs;
	}
	public void setDisease_classs(String disease_classs) {
		this.disease_classs = disease_classs;
	}
	public String getItem_name() {
		return item_name;
	}
	public void setItem_name(String item_name) {
		this.item_name = item_name;
	}
	public String getEitem_name() {
		return eitem_name;
	}
	public void setEitem_name(String eitem_name) {
		this.eitem_name = eitem_name;
	}
	public String getDep_category() {
		return dep_category;
	}
	public void setDep_category(String dep_category) {
		this.dep_category = dep_category;
	}
	public long getCid() {
		return cid;
	}
	public void setCid(long cid) {
		this.cid = cid;
	}
	public long getEid() {
		return eid;
	}
	public void setEid(long eid) {
		this.eid = eid;
	}
	public String getDiagnosis_source() {
		return diagnosis_source;
	}
	public void setDiagnosis_source(String diagnosis_source) {
		this.diagnosis_source = diagnosis_source;
	}
	public String getItem_source() {
		return item_source;
	}
	public void setItem_source(String item_source) {
		this.item_source = item_source;
	}
	public String getExam_result() {
		return exam_result;
	}
	public void setExam_result(String exam_result) {
		this.exam_result = exam_result;
	}
	public String getCareer_hazards() {
		return career_hazards;
	}
	public void setCareer_hazards(String career_hazards) {
		this.career_hazards = career_hazards;
	}
	public String getCareer_suggest() {
		return career_suggest;
	}
	public void setCareer_suggest(String career_suggest) {
		this.career_suggest = career_suggest;
	}
	public String getResultID() {
		return resultID;
	}
	public void setResultID(String resultID) {
		this.resultID = resultID;
	}
	public String getOccudiseaseIDorcontraindicationID() {
		return occudiseaseIDorcontraindicationID;
	}
	public void setOccudiseaseIDorcontraindicationID(String occudiseaseIDorcontraindicationID) {
		this.occudiseaseIDorcontraindicationID = occudiseaseIDorcontraindicationID;
	}
	public String getDisease_description() {
		return disease_description;
	}
	public void setDisease_description(String disease_description) {
		this.disease_description = disease_description;
	}

	public String getCharging_item_name() {
		return charging_item_name;
	}

	public void setCharging_item_name(String charging_item_name) {
		this.charging_item_name = charging_item_name;
	}

	public String getDep_num() {
		return dep_num;
	}

	public String getItem_code() {
		return item_code;
	}

	public String getCharging_item_code() {
		return charging_item_code;
	}

	public void setDep_num(String dep_num) {
		this.dep_num = dep_num;
	}

	public void setItem_code(String item_code) {
		this.item_code = item_code;
	}

	public void setCharging_item_code(String charging_item_code) {
		this.charging_item_code = charging_item_code;
	}
	
}
