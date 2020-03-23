package com.hjw.wst.DTO;

public class ExamFlowDTO implements java.io.Serializable {
	 private static final long serialVersionUID = -97502163798576023L;

		private long id;

        private long exam_id;//--体检id	

		private long fromacc;//--发起人id	

		private String fromacc_date="";//--发起时间

		private long toacc;//--接收人id

		private String toacc_date="";//接收时间

		private String types="0";//--类型：0表示发起，标识接收

		private String remark="";//--说明1

		private String remark1="";//--说明2	
		
		private long center_num;//体检中心编码	
		private String exam_num;
	    
	    public String getExam_num() {
			return exam_num;
		}

		public void setExam_num(String exam_num) {
			this.exam_num = exam_num;
		}

		public long getCenter_num() {
			return center_num;
		}

		public void setCenter_num(long center_num) {
			this.center_num = center_num;
		}

		public long getId() {
			return id;
		}

		public void setId(long id) {
			this.id = id;
		}

		public long getExam_id() {
			return exam_id;
		}

		public void setExam_id(long exam_id) {
			this.exam_id = exam_id;
		}

		public long getFromacc() {
			return fromacc;
		}

		public void setFromacc(long fromacc) {
			this.fromacc = fromacc;
		}

		public String getFromacc_date() {
			return fromacc_date;
		}

		public void setFromacc_date(String fromacc_date) {
			this.fromacc_date = fromacc_date;
		}

		public long getToacc() {
			return toacc;
		}

		public void setToacc(long toacc) {
			this.toacc = toacc;
		}

		public String getToacc_date() {
			return toacc_date;
		}

		public void setToacc_date(String toacc_date) {
			this.toacc_date = toacc_date;
		}

		public String getTypes() {
			return types;
		}

		public void setTypes(String types) {
			this.types = types;
		}

		public String getRemark() {
			return remark;
		}

		public void setRemark(String remark) {
			this.remark = remark;
		}

		public String getRemark1() {
			return remark1;
		}

		public void setRemark1(String remark1) {
			this.remark1 = remark1;
		}
			
	}