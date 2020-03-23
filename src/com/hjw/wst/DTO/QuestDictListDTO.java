package com.hjw.wst.DTO;

public class QuestDictListDTO implements java.io.Serializable {
	private static final long serialVersionUID = -97502163798576023L;

	private String quest_sub_code;

	private String quest_code;
	
	private String quest_sub_name;
	
	private int seq_no;
	
	private String note;
	
	private int isVisable;

	public String getQuest_sub_code() {
		return quest_sub_code;
	}

	public void setQuest_sub_code(String quest_sub_code) {
		this.quest_sub_code = quest_sub_code;
	}

	public String getQuest_code() {
		return quest_code;
	}

	public void setQuest_code(String quest_code) {
		this.quest_code = quest_code;
	}

	public String getQuest_sub_name() {
		return quest_sub_name;
	}

	public void setQuest_sub_name(String quest_sub_name) {
		this.quest_sub_name = quest_sub_name;
	}

	public int getSeq_no() {
		return seq_no;
	}

	public void setSeq_no(int seq_no) {
		this.seq_no = seq_no;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public int getIsVisable() {
		return isVisable;
	}

	public void setIsVisable(int isVisable) {
		this.isVisable = isVisable;
	}
}