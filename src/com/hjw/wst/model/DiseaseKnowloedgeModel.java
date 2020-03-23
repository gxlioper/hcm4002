package com.hjw.wst.model;

import java.util.Date;

public class DiseaseKnowloedgeModel implements java.io.Serializable{
	
	private static final long serialVersionUID = -97502163798576023L;
	 private long id;
     private String disease_type;
     private String disease_level;
     private String disease_name;
     private String disease_pinyin;
     private String disease_num;
     private String disease_description;
     private String disease_evendice;
     private String health_suggestion;
     private String icd_10;
	 private String isActive;
	 private long creater;
	 private Date create_time;
	 private long updater;
	 private Date update_time;
	 private long charging_item_id;
	 private long disease_classification;
	 
	 private long sug_id;
	 private long disease_id;
     private String disease_suggestion;
	 private String sex;
	 private long minAge;
	 private long maxAge;
	 private String default_value;
	 private String is_active;
	 private String disease_type_d;
     private String disease_level_d;
     private String disease_classification_d;
     private String disease_system;
     private String disease_systems;
     private String disease_statistics;
     private String disease_statisticss;
     private long disease_report;
     private String disease_reports;
     private long disease_team_show;
     private String disease_team_shows;
     
     //逻辑维护
     private long diskno_id;//疾病id
     private long ddepid;
     private long dep_id;//科室id
     private String dep_name;//科室名称
     private String charging_item_ids;//收费项目id
     private String item_name;//收费项目名称
     private String exam_item_ids;//检查项目id
     private String checkitem_name;//检查项目名称
     private long parentId;
     
     
     
     public long getParentId() {
		return parentId;
	}

	public void setParentId(long parentId) {
		this.parentId = parentId;
	}

	public long getDiskno_id() {
		return diskno_id;
	}


	public void setDiskno_id(long diskno_id) {
		this.diskno_id = diskno_id;
	}


	public long getDdepid() {
		return ddepid;
	}


	public void setDdepid(long ddepid) {
		this.ddepid = ddepid;
	}


	public long getDep_id() {
		return dep_id;
	}


	public String getCharging_item_ids() {
		return charging_item_ids;
	}


	public void setCharging_item_ids(String charging_item_ids) {
		this.charging_item_ids = charging_item_ids;
	}


	public String getExam_item_ids() {
		return exam_item_ids;
	}


	public void setExam_item_ids(String exam_item_ids) {
		this.exam_item_ids = exam_item_ids;
	}


	public void setDep_id(long dep_id) {
		this.dep_id = dep_id;
	}


	public String getDep_name() {
		return dep_name;
	}


	public void setDep_name(String dep_name) {
		this.dep_name = dep_name;
	}

	public String getItem_name() {
		return item_name;
	}


	public void setItem_name(String item_name) {
		this.item_name = item_name;
	}

	public String getCheckitem_name() {
		return checkitem_name;
	}


	public void setCheckitem_name(String checkitem_name) {
		this.checkitem_name = checkitem_name;
	}


	public DiseaseKnowloedgeModel() {
		// TODO Auto-generated constructor stub
	}

	
	public String getDisease_type_d() {
		return disease_type_d;
	}


	public void setDisease_type_d(String disease_type_d) {
		this.disease_type_d = disease_type_d;
	}


	public String getDisease_level_d() {
		return disease_level_d;
	}


	public void setDisease_level_d(String disease_level_d) {
		this.disease_level_d = disease_level_d;
	}


	public long getSug_id() {
		return sug_id;
	}

	public void setSug_id(long sug_id) {
		this.sug_id = sug_id;
	}

	public String getDefault_value() {
		return default_value;
	}

	public void setDefault_value(String default_value) {
		this.default_value = default_value;
	}

	public String getIs_active() {
		return is_active;
	}

	public void setIs_active(String is_active) {
		this.is_active = is_active;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getDisease_type() {
		return disease_type;
	}

	public void setDisease_type(String disease_type) {
		this.disease_type = disease_type;
	}

	public String getDisease_level() {
		return disease_level;
	}

	public void setDisease_level(String disease_level) {
		this.disease_level = disease_level;
	}

	public String getDisease_name() {
		return disease_name;
	}

	public void setDisease_name(String disease_name) {
		this.disease_name = disease_name;
	}

	public String getDisease_pinyin() {
		return disease_pinyin;
	}

	public void setDisease_pinyin(String disease_pinyin) {
		this.disease_pinyin = disease_pinyin;
	}

	public String getDisease_num() {
		return disease_num;
	}

	public void setDisease_num(String disease_num) {
		this.disease_num = disease_num;
	}

	public String getDisease_description() {
		return disease_description;
	}

	public void setDisease_description(String disease_description) {
		this.disease_description = disease_description;
	}

	public String getDisease_evendice() {
		return disease_evendice;
	}

	public void setDisease_evendice(String disease_evendice) {
		this.disease_evendice = disease_evendice;
	}

	public String getDisease_suggestion() {
		return disease_suggestion;
	}

	public void setDisease_suggestion(String disease_suggestion) {
		this.disease_suggestion = disease_suggestion;
	}

	public String getHealth_suggestion() {
		return health_suggestion;
	}

	public void setHealth_suggestion(String health_suggestion) {
		this.health_suggestion = health_suggestion;
	}

	public String getIcd_10() {
		return icd_10;
	}

	public void setIcd_10(String icd_10) {
		this.icd_10 = icd_10;
	}

	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	public long getCharging_item_id() {
		return charging_item_id;
	}

	public void setCharging_item_id(long charging_item_id) {
		this.charging_item_id = charging_item_id;
	}

	public long getDisease_classification() {
		return disease_classification;
	}

	public void setDisease_classification(long disease_classification) {
		this.disease_classification = disease_classification;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public long getMinAge() {
		return minAge;
	}

	public void setMinAge(long minAge) {
		this.minAge = minAge;
	}

	public long getMaxAge() {
		return maxAge;
	}

	public void setMaxAge(long maxAge) {
		this.maxAge = maxAge;
	}


	public String getDisease_classification_d() {
		return disease_classification_d;
	}

	public void setDisease_classification_d(String disease_classification_d) {
		this.disease_classification_d = disease_classification_d;
	}

	public long getDisease_id() {
		return disease_id;
	}

	public void setDisease_id(long disease_id) {
		this.disease_id = disease_id;
	}


	public String getDisease_system() {
		return disease_system;
	}


	public void setDisease_system(String disease_system) {
		this.disease_system = disease_system;
	}


	public String getDisease_systems() {
		return disease_systems;
	}


	public void setDisease_systems(String disease_systems) {
		this.disease_systems = disease_systems;
	}


	public String getDisease_statistics() {
		return disease_statistics;
	}


	public void setDisease_statistics(String disease_statistics) {
		this.disease_statistics = disease_statistics;
	}


	public String getDisease_statisticss() {
		return disease_statisticss;
	}


	public void setDisease_statisticss(String disease_statisticss) {
		this.disease_statisticss = disease_statisticss;
	}


	public long getDisease_report() {
		return disease_report;
	}


	public void setDisease_report(long disease_report) {
		this.disease_report = disease_report;
	}


	public String getDisease_reports() {
		return disease_reports;
	}


	public void setDisease_reports(String disease_reports) {
		this.disease_reports = disease_reports;
	}


	public long getDisease_team_show() {
		return disease_team_show;
	}


	public void setDisease_team_show(long disease_team_show) {
		this.disease_team_show = disease_team_show;
	}


	public String getDisease_team_shows() {
		return disease_team_shows;
	}


	public void setDisease_team_shows(String disease_team_shows) {
		this.disease_team_shows = disease_team_shows;
	}
}
