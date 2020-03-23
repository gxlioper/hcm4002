package com.hjw.wst.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "quest_dict_list_ext")
public class QuestDictListExt implements java.io.Serializable {
		private static final long serialVersionUID = 1L;

		@Id
		//@GeneratedValue(strategy = GenerationType.IDENTITY)
		@Column(name = "scale_code")
		private String scale_code;
		
	    @Column(name = "nameCn")
		private String nameCn;

	    @Column(name = "nameEn")
		private String nameEn;
	    
	    @Column(name = "full_score")
	    private int full_score;
	    
	    @Column(name = "content")
	    private String content;

		public String getScale_code() {
			return scale_code;
		}

		public void setScale_code(String scale_code) {
			this.scale_code = scale_code;
		}

		public String getNameCn() {
			return nameCn;
		}

		public void setNameCn(String nameCn) {
			this.nameCn = nameCn;
		}

		public String getNameEn() {
			return nameEn;
		}

		public void setNameEn(String nameEn) {
			this.nameEn = nameEn;
		}

		public String getContent() {
			return content;
		}

		public void setContent(String content) {
			this.content = content;
		}

		public int getFull_score() {
			return full_score;
		}

		public void setFull_score(int full_score) {
			this.full_score = full_score;
		}
}