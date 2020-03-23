package com.hjw.wst.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

	
@Entity
@Table(name = "quest_exam_Record")
public class QuestExamRecord implements java.io.Serializable {
		private static final long serialVersionUID = 1L;

		@Id
		@GenericGenerator(name = "idGenerator", strategy = "identity")
		@GeneratedValue(generator = "idGenerator")
		private int recId;//记录ID
		
		@Column(name = "peId")
		private String peId;//体检编号
		
		@Column(name = "inDate")
		private Date inDate;//创建时间
		
		@Column(name = "tranDate")
		private Date tranDate;//上传时间
		
		@Column(name = "traner")
		private long traner;//上传人员
		
		@Column(name = "tranFlag")
		private String tranFlag;//0：未上传；1：已上传
		
		@Column(name = "checkDate")
		private Date checkDate;//审核时间
		
		@Column(name = "checker")
		private long checker;//审核人员
		
		@Column(name = "delFlag")
		private String delFlag;//1：已删除
		
		@Column(name = "custId")
		private String custId;
		
		@Column(name = "downFlag")
		private int downFlag;//下载标识
		
		@Column(name = "quest_sub_code")
		private String quest_sub_code;//量表代码
		
		@Column(name = "score")
		private int score;//总分数
		
		@Column(name = "appraise")
		private String appraise;//评估内容

		public int getRecId() {
			return recId;
		}

		public void setRecId(int recId) {
			this.recId = recId;
		}

		public String getPeId() {
			return peId;
		}

		public void setPeId(String peId) {
			this.peId = peId;
		}

		public Date getInDate() {
			return inDate;
		}

		public void setInDate(Date inDate) {
			this.inDate = inDate;
		}

		public Date getTranDate() {
			return tranDate;
		}

		public void setTranDate(Date tranDate) {
			this.tranDate = tranDate;
		}

		public String getTranFlag() {
			return tranFlag;
		}

		public void setTranFlag(String tranFlag) {
			this.tranFlag = tranFlag;
		}

		public Date getCheckDate() {
			return checkDate;
		}

		public void setCheckDate(Date checkDate) {
			this.checkDate = checkDate;
		}

		public String getDelFlag() {
			return delFlag;
		}

		public void setDelFlag(String delFlag) {
			this.delFlag = delFlag;
		}

		public String getCustId() {
			return custId;
		}

		public void setCustId(String custId) {
			this.custId = custId;
		}

		public int getDownFlag() {
			return downFlag;
		}

		public void setDownFlag(int downFlag) {
			this.downFlag = downFlag;
		}

		public String getQuest_sub_code() {
			return quest_sub_code;
		}

		public void setQuest_sub_code(String quest_sub_code) {
			this.quest_sub_code = quest_sub_code;
		}

		public int getScore() {
			return score;
		}

		public void setScore(int score) {
			this.score = score;
		}

		public String getAppraise() {
			return appraise;
		}

		public void setAppraise(String appraise) {
			this.appraise = appraise;
		}

		public long getTraner() {
			return traner;
		}

		public void setTraner(long traner) {
			this.traner = traner;
		}

		public long getChecker() {
			return checker;
		}

		public void setChecker(long checker) {
			this.checker = checker;
		}
}
