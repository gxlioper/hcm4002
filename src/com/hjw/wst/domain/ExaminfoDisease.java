package com.hjw.wst.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="examinfo_disease")
public class ExaminfoDisease implements java.io.Serializable{

	private static final long serialVersionUID = -97502163798576023L;
	
	@Id
	@GenericGenerator(name = "idGenerator", strategy = "identity")
	@GeneratedValue(generator = "idGenerator")
	private long id;
	
	@Column(name="exam_info_id")
	private long exam_info_id;
	
	@Column(name="disease_id")
	private long disease_id;
	
	@Column(name = "disease_name")
	private String disease_name;
	
	@Column(name="disease_index")
	private long disease_index;
	
	@Column(name="isActive")
	private String isActive;
	
	@Column(name="creater")
	private long creater;
	
	@Column(name="create_time")
	private Date create_time;
	
	@Column(name="updater")
	private long updater;
	
	@Column(name="update_time")
	private Date update_time; 
	
	@Column(name="disease_type")
	private String disease_type;
	
	@Column(name="icd_10")
	private String icd_10;
	
	@Column(name="final_doc_num")
	private String final_doc_num;
	
	@Column(name="suggest")
	private String suggest;
	
	@Column(name="disease_class")
	private String disease_class;
	
	@Column(name="app_type")
	private String app_type;
	
	@Column(name="diagnosis_source")
	private String diagnosis_source;//诊断来源
	
	@Column(name="item_source")
	private String item_source;//来源组合项目
	
	@Column(name="exam_result")
	private String exam_result;//个人体检结论

	@Column(name="career_hazards")
	private String career_hazards;//职业危害
	
	@Column(name="career_suggest")
	private String career_suggest;//职业建议
	
	@Column(name="resultID")
	private String resultID;//检查结果ID
	
	@Column(name="occudiseaseIDorcontraindicationID")
	private String occudiseaseIDorcontraindicationID;//职业病和禁忌症ID
	
	@Column(name="exam_num")
    private String exam_num;
	
	@Column(name="disease_description")
	private String disease_description;
	
	@Column(name="disease_num")
	private String disease_num;
	
	@Column(name="data_source")
	private long data_source;//数据来源 0表示自动生成、1表示手工添加、2表示手动合并
	
	@Column(name="data_source_json")
	private String data_source_json;//数据来源条件，data_source为0或2时保存json格式数据
    
    public String getDisease_num() {
		return disease_num;
	}

	public void setDisease_num(String disease_num) {
		this.disease_num = disease_num;
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

	public String getDisease_description() {
		return disease_description;
	}

	public void setDisease_description(String disease_description) {
		this.disease_description = disease_description;
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

	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	public long getCreater() {
		return creater;
	}

	public void setCreater(long creater) {
		this.creater = creater;
	}

	public Date getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}

	public long getUpdater() {
		return updater;
	}

	public void setUpdater(long updater) {
		this.updater = updater;
	}

	public Date getUpdate_time() {
		return update_time;
	}

	public void setUpdate_time(Date update_time) {
		this.update_time = update_time;
	}

	public String getDisease_type() {
		return disease_type;
	}

	public void setDisease_type(String disease_type) {
		this.disease_type = disease_type;
	}

	public String getIcd_10() {
		return icd_10;
	}

	public void setIcd_10(String icd_10) {
		this.icd_10 = icd_10;
	}

	public String getFinal_doc_num() {
		return final_doc_num;
	}

	public void setFinal_doc_num(String final_doc_num) {
		this.final_doc_num = final_doc_num;
	}

	public String getSuggest() {
		return suggest;
	}

	public void setSuggest(String suggest) {
		this.suggest = suggest;
	}

	public String getDisease_class() {
		return disease_class;
	}

	public void setDisease_class(String disease_class) {
		this.disease_class = disease_class;
	}

	public String getApp_type() {
		return app_type;
	}

	public void setApp_type(String app_type) {
		this.app_type = app_type;
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
}
