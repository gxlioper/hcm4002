package com.hjw.wst.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;


@Entity
@Table(name = "quest_dict_title")
public class QuestDictTitle implements java.io.Serializable {
		private static final long serialVersionUID = 1L;

		@Id
		@GenericGenerator(name = "idGenerator", strategy = "identity")
		@GeneratedValue(generator = "idGenerator")
		private int titleid;

	    @Column(name = "titleName")
		private String titleName;
	    
	    @Column(name = "supID")
		private String supID;

	    @Column(name = "Level")
		private int Level;
	    
	    @Column(name = "seqNo")
		private int seqNo;
	    
	    @Column(name = "isVisable")
		private String isVisable;
	    
	    @Column(name = "quest_sub_code")
		private String quest_sub_code;
	    
	    @Column(name = "titleColumn")
	    private String titleColumn;
	    
		public String getTitleColumn() {
			return titleColumn;
		}

		public void setTitleColumn(String titleColumn) {
			this.titleColumn = titleColumn;
		}

		public int getTitleid() {
			return titleid;
		}

		public void setTitleid(int titleid) {
			this.titleid = titleid;
		}

		public String getTitleName() {
			return titleName;
		}

		public void setTitleName(String titleName) {
			this.titleName = titleName;
		}
		
		public String getSupID() {
			return supID;
		}

		public void setSupID(String supID) {
			this.supID = supID;
		}

		public int getLevel() {
			return Level;
		}

		public void setLevel(int level) {
			Level = level;
		}

		public int getSeqNo() {
			return seqNo;
		}

		public void setSeqNo(int seqNo) {
			this.seqNo = seqNo;
		}

		public String getIsVisable() {
			return isVisable;
		}

		public void setIsVisable(String isVisable) {
			this.isVisable = isVisable;
		}

		public String getQuest_sub_code() {
			return quest_sub_code;
		}

		public void setQuest_sub_code(String quest_sub_code) {
			this.quest_sub_code = quest_sub_code;
		}

	    
}