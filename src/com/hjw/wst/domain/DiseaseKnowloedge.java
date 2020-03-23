package com.hjw.wst.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "disease_knowloedge_lib")
public class DiseaseKnowloedge implements java.io.Serializable {
	 private static final long serialVersionUID = -97502163798576023L;
	 @Id
		@GenericGenerator(name = "idGenerator", strategy = "identity")
		@GeneratedValue(generator = "idGenerator")
		private long id;
	 
	    @Column(name = "disease_type")
        private String disease_type;
	 
	    @Column(name = "disease_level")
        private String disease_level;
	 
	    @Column(name = "disease_name")
        private String disease_name;
	    
	    @Column(name = "disease_pinyin")
        private String disease_pinyin;
	 
	    @Column(name = "disease_num")
        private String disease_num;
	    
	    @Column(name = "disease_description")
        private String disease_description;
	 
	    @Column(name = "disease_evendice")
        private String disease_evendice;
	    
	    @Column(name = "disease_suggestion")
        private String disease_suggestion;
	 
	    @Column(name = "health_suggestion")
        private String health_suggestion;
	  
	    @Column(name = "icd_10")
	    private String icd_10; 
	    
	    @Column(name = "isActive")
	    private String isActive;
	 
	    @Column(name = "creater")
	    private long creater;
	    
	    @Column(name = "create_time")
	    private Date create_time;
	    
	    @Column(name = "updater")
	    private long updater;
	    
	    @Column(name = "update_time")
	    private Date update_time;
	    
	    @Column(name = "disease_classification")
	    private long disease_classification;
	    
	    @Column(name = "dep_id")
	    private long dep_id;
	    
	    @Column(name="disease_system")
	    private String disease_system;
	    
	    @Column(name="disease_statistics")
	    private String disease_statistics;
	    
	    @Column(name="disease_report")
	    private long disease_report;
	    
	    @Column(name="disease_team_show")
	    private long disease_team_show;
	    
	    @Column(name="charging_item_code")
	    private long charging_item_code;
	    
		public long getCharging_item_code() {
			return charging_item_code;
		}

		public void setCharging_item_code(long charging_item_code) {
			this.charging_item_code = charging_item_code;
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

		public long getDisease_classification() {
			return disease_classification;
		}

		public void setDisease_classification(long disease_classification) {
			this.disease_classification = disease_classification;
		}

		public static long getSerialversionuid() {
			return serialVersionUID;
		}

		public String getIcd_10() {
			return icd_10;
		}

		public void setIcd_10(String icd_10) {
			this.icd_10 = icd_10;
		}

		public long getDep_id() {
			return dep_id;
		}

		public void setDep_id(long dep_id) {
			this.dep_id = dep_id;
		}

		public String getDisease_system() {
			return disease_system;
		}

		public void setDisease_system(String disease_system) {
			this.disease_system = disease_system;
		}

		public String getDisease_statistics() {
			return disease_statistics;
		}

		public void setDisease_statistics(String disease_statistics) {
			this.disease_statistics = disease_statistics;
		}

		public long getDisease_report() {
			return disease_report;
		}

		public void setDisease_report(long disease_report) {
			this.disease_report = disease_report;
		}

		public long getDisease_team_show() {
			return disease_team_show;
		}

		public void setDisease_team_show(long disease_team_show) {
			this.disease_team_show = disease_team_show;
		}
	}