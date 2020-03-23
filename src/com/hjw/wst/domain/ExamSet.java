package com.hjw.wst.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
/**
 * 
     * @Title:  火箭蛙体检管理系统   
     * @Package com.hjw.wst.domain   
     * @Description:  体检项目套餐
     * @author: zhangrui     
     * @date:   2016年10月10日 上午10:12:10   
     * @version V2.0.0.0
 */
@Entity
@Table(name = "exam_set")
public class ExamSet implements java.io.Serializable {
	 private static final long serialVersionUID = -97502163798576023L;

		@Id
		@GenericGenerator(name = "idGenerator", strategy = "identity")
		@GeneratedValue(generator = "idGenerator")
		private long id;

		@Column(name = "company_id")
        private long company_id;
        
        @Column(name = "set_num")
        private String set_num;
        
        @Column(name = "set_name")
        private String  set_name;
        
        @Column(name = "set_pinyin")
        private String set_pinyin;
        
        @Column(name = "sex")
        private String sex;
        
        @Column(name = "set_discount")
        private double set_discount;
        
        @Column(name = "set_amount")
        private double set_amount;
        
        @Column(name = "survey_minScore")
        private double survey_minScore;
        
        @Column(name = "survey_maxScore")
        private double survey_maxScore;
        
        @Column(name = "disease_name")
        private String disease_name;
        
        @Column(name = "is_Active")
        private String is_Active;
		
		@Column(name = "creater")
		private long creater;
		
		@Column(name = "create_time")
		private Date create_time;
		
		@Column(name = "updater")
		private long updater;
		
		@Column(name = "update_time")
		private Date update_time;
		
		@Column(name = "price")
		private double price;
		
		@Column(name = "exam_set_type")
		private String exam_set_type;
		
		@Column(name= "app_type")
		private int  app_type;
		
		@Column(name= "set_seq")
		private long  set_seq;
		
		@Column(name= "occuphyexaclassid")
		private String occuphyexaclassid;//职业体检类别
		
		@Column(name= "hazardfactorsid")
		private String hazardfactorsid;//职业危害因素 
		
		@Column(name= "isSynchro")
		private int isSynchro;//是否微信同步
		
		@Column(name= "center_num")
		private String center_num;//体检中心编码
		
		public int getIsSynchro() {
			return isSynchro;
		}

		public void setIsSynchro(int isSynchro) {
			this.isSynchro = isSynchro;
		}

		public String getOccuphyexaclassid() {
			return occuphyexaclassid;
		}

		public void setOccuphyexaclassid(String occuphyexaclassid) {
			this.occuphyexaclassid = occuphyexaclassid;
		}

		public String getHazardfactorsid() {
			return hazardfactorsid;
		}

		public void setHazardfactorsid(String hazardfactorsid) {
			this.hazardfactorsid = hazardfactorsid;
		}

		public long getSet_seq() {
			return set_seq;
		}

		public void setSet_seq(long set_seq) {
			this.set_seq = set_seq;
		}

		public int getApp_type() {
			return app_type;
		}

		public void setApp_type(int app_type) {
			this.app_type = app_type;
		}

		public String getExam_set_type() {
			return exam_set_type;
		}

		public void setExam_set_type(String exam_set_type) {
			this.exam_set_type = exam_set_type;
		}

		public long getId() {
			return id;
		}

		public void setId(long id) {
			this.id = id;
		}

		
		
		public long getCompany_id() {
			return company_id;
		}

		public void setCompany_id(long company_id) {
			this.company_id = company_id;
		}

		public String getSet_num() {
			return set_num;
		}

		public void setSet_num(String set_num) {
			this.set_num = set_num;
		}

		public String getSet_name() {
			return set_name;
		}

		public void setSet_name(String set_name) {
			this.set_name = set_name;
		}

		public String getSet_pinyin() {
			return set_pinyin;
		}

		public void setSet_pinyin(String set_pinyin) {
			this.set_pinyin = set_pinyin;
		}

		public String getSex() {
			return sex;
		}

		public void setSex(String sex) {
			this.sex = sex;
		}

		public double getSet_discount() {
			return set_discount;
		}

		public void setSet_discount(double set_discount) {
			this.set_discount = set_discount;
		}

		public double getSet_amount() {
			return set_amount;
		}

		public void setSet_amount(double set_amount) {
			this.set_amount = set_amount;
		}

		public double getSurvey_minScore() {
			return survey_minScore;
		}

		public void setSurvey_minScore(double survey_minScore) {
			this.survey_minScore = survey_minScore;
		}

		public double getSurvey_maxScore() {
			return survey_maxScore;
		}

		public void setSurvey_maxScore(double survey_maxScore) {
			this.survey_maxScore = survey_maxScore;
		}

		public String getDisease_name() {
			return disease_name;
		}

		public void setDisease_name(String disease_name) {
			this.disease_name = disease_name;
		}

		public String getIs_Active() {
			return is_Active;
		}

		public void setIs_Active(String is_Active) {
			this.is_Active = is_Active;
		}

		public long getCreater() {
			return creater;
		}

		public void setCreater(long creater) {
			this.creater = creater;
		}

		public long getUpdater() {
			return updater;
		}

		public void setUpdater(long updater) {
			this.updater = updater;
		}

		public Date getCreate_time() {
			return create_time;
		}

		public void setCreate_time(Date create_time) {
			this.create_time = create_time;
		}

		public Date getUpdate_time() {
			return update_time;
		}

		public void setUpdate_time(Date update_time) {
			this.update_time = update_time;
		}

		public double getPrice() {
			return price;
		}

		public void setPrice(double price) {
			this.price = price;
		}

		public String getCenter_num() {
			return center_num;
		}

		public void setCenter_num(String center_num) {
			this.center_num = center_num;
		}		
		
	}