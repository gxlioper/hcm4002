package com.hjw.wst.DTO;

import java.util.Date;


public class QuestRecordDTO {
	
	//------------------record表----------------
	
	private int recId;//记录ID
	
	private String peId;//体检编号
	
	private Date inDate;//开始体检时间
	
	private Date tranDate;//上传时间
	
	private long traner;//上传人员
	
	private String tranFlag;//0：未上传；1：已上传
	
	private Date checkDate;//审核时间
	
	private long checker;//审核人员
	
	private String delFlag;//1：已删除
	
	private String custId;
	
	private int downFlag;//下载标识
	
	private String quest_sub_code;//量表代码
	
	private int score;//总分数
	
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

	public long getTraner() {
		return traner;
	}

	public void setTraner(long traner) {
		this.traner = traner;
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

	public long getChecker() {
		return checker;
	}

	public void setChecker(long checker) {
		this.checker = checker;
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

	//-------------List表-----------------------
	private int questID;
	
	private String recordId;
	
	//private String peId;
	
	private String resultId;
	
	private String questResult;
	
	private String resultId_itemID;
	
	private String resultId_itemID1;
	
	private String questResult_itemID;
	
	private String itemTitleID;
	
	private int itemIsMulsel;
	
	private String itemtextUnit;
	
	private String resultId_itemName;
	
	private String resultId_itemName1;
	
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
