package com.hjw.wst.domain;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "disease_suggestion_lib")
public class DiseaseSuggestion implements java.io.Serializable {
	 private static final long serialVersionUID = -97502163798576023L;
	 @Id
		@GenericGenerator(name = "idGenerator", strategy = "identity")
		@GeneratedValue(generator = "idGenerator")
		private long id;
	 
	    @Column(name = "disease_id")
	    private long disease_id;
	    
	    @Column(name="disease_num")
	    private String disease_num;
	 
	    @Column(name = "sex")
        private String sex;
	 
	    @Column(name = "minAge")
        private long minAge;
	 
	    @Column(name = "maxAge")
        private long maxAge;
	 
	    @Column(name = "disease_suggestion")
	    private String disease_suggestion;
	    
	    @Column(name = "default_value")
	    private String default_value;
	    
	    @Column(name = "is_active")
	    private String is_active;
	    
	    @Column(name = "center_num")
	    private String center_num;

		public String getDisease_num() {
			return disease_num;
		}

		public void setDisease_num(String disease_num) {
			this.disease_num = disease_num;
		}

		public long getId() {
			return id;
		}

		public void setId(long id) {
			this.id = id;
		}

		public long getDisease_id() {
			return disease_id;
		}

		public void setDisease_id(long disease_id) {
			this.disease_id = disease_id;
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

		public String getDisease_suggestion() {
			return disease_suggestion;
		}

		public void setDisease_suggestion(String disease_suggestion) {
			this.disease_suggestion = disease_suggestion;
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

		public String getCenter_num() {
			return center_num;
		}

		public void setCenter_num(String center_num) {
			this.center_num = center_num;
		}
	}