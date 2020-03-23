package com.hjw.wst.DTO;

public class ScaleDictOptionDTO implements java.io.Serializable {
		private static final long serialVersionUID = 1L;

		private int id;

		private String name;
		
		private int seqNo;
		
		private String scale_code;

		private int value;
		
		private int questionID;
		
		private int selected;

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getValue() {
			return value;
		}

		public void setValue(int value) {
			this.value = value;
		}

		public boolean isSelected() {
			return selected == 1;
		}

		public void setSelected(int selected) {
			this.selected = selected;
		}

		public int getSeqNo() {
			return seqNo;
		}

		public void setSeqNo(int seqNo) {
			this.seqNo = seqNo;
		}

		public int getQuestionID() {
			return questionID;
		}

		public void setQuestionID(int questionID) {
			this.questionID = questionID;
		}

		public String getScale_code() {
			return scale_code;
		}

		public void setScale_code(String scale_code) {
			this.scale_code = scale_code;
		}
}