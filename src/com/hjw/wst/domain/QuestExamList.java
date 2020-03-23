package com.hjw.wst.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;


@Entity
@Table(name = "quest_exam_List")
public class QuestExamList implements java.io.Serializable {
		private static final long serialVersionUID = 1L;

		@Id
		@GenericGenerator(name = "idGenerator", strategy = "identity")
		@GeneratedValue(generator = "idGenerator")
		private int questID;
		
		@Column(name = "recordId")
		private String recordId;
		
		@Column(name = "peId")
		private String peId;
		
		@Column(name = "resultId")
		private String resultId;
		
		@Column(name = "questResult")
		private String questResult;
		
		@Column(name = "resultId_itemID")
		private String resultId_itemID;
		
		@Column(name = "resultId_itemID1")
		private String resultId_itemID1;
		
		@Column(name = "questResult_itemID")
		private String questResult_itemID;
		
		@Column(name = "itemTitleID")
		private String itemTitleID;
		
		@Column(name = "itemIsMulsel")
		private int itemIsMulsel;
		
		@Column(name = "itemtextUnit")
		private String itemtextUnit;
		
		@Column(name = "resultId_itemName")
		private String resultId_itemName;
		
		@Column(name = "resultId_itemName1")
		private String resultId_itemName1;
		
		@Column(name = "questResult_itemName")
		private String questResult_itemName;

		public int getQuestID() {
			return questID;
		}

		public void setQuestID(int questID) {
			this.questID = questID;
		}

		public String getRecordId() {
			return recordId;
		}

		public void setRecordId(String recordId) {
			this.recordId = recordId;
		}

		public String getPeId() {
			return peId;
		}

		public void setPeId(String peId) {
			this.peId = peId;
		}

		public String getResultId() {
			return resultId;
		}

		public void setResultId(String resultId) {
			this.resultId = resultId;
		}

		public String getQuestResult() {
			return questResult;
		}

		public void setQuestResult(String questResult) {
			this.questResult = questResult;
		}

		public String getResultId_itemID() {
			return resultId_itemID;
		}

		public void setResultId_itemID(String resultId_itemID) {
			this.resultId_itemID = resultId_itemID;
		}

		public String getResultId_itemID1() {
			return resultId_itemID1;
		}

		public void setResultId_itemID1(String resultId_itemID1) {
			this.resultId_itemID1 = resultId_itemID1;
		}

		public String getQuestResult_itemID() {
			return questResult_itemID;
		}

		public void setQuestResult_itemID(String questResult_itemID) {
			this.questResult_itemID = questResult_itemID;
		}

		public String getItemTitleID() {
			return itemTitleID;
		}

		public void setItemTitleID(String itemTitleID) {
			this.itemTitleID = itemTitleID;
		}

		public int getItemIsMulsel() {
			return itemIsMulsel;
		}

		public void setItemIsMulsel(int itemIsMulsel) {
			this.itemIsMulsel = itemIsMulsel;
		}

		public String getItemtextUnit() {
			return itemtextUnit;
		}

		public void setItemtextUnit(String itemtextUnit) {
			this.itemtextUnit = itemtextUnit;
		}

		public String getResultId_itemName() {
			return resultId_itemName;
		}

		public void setResultId_itemName(String resultId_itemName) {
			this.resultId_itemName = resultId_itemName;
		}

		public String getResultId_itemName1() {
			return resultId_itemName1;
		}

		public void setResultId_itemName1(String resultId_itemName1) {
			this.resultId_itemName1 = resultId_itemName1;
		}

		public String getQuestResult_itemName() {
			return questResult_itemName;
		}

		public void setQuestResult_itemName(String questResult_itemName) {
			this.questResult_itemName = questResult_itemName;
		}
		
		
		
}
