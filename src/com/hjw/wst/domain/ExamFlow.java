package com.hjw.wst.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "exam_flow")
public class ExamFlow implements java.io.Serializable {
	 private static final long serialVersionUID = -97502163798576023L;

		@Id
		@GenericGenerator(name = "idGenerator", strategy = "identity")
		@GeneratedValue(generator = "idGenerator")
		private long id;

		@Column(name = "exam_id")
        private long exam_id;//--体检id	
		
		@Column(name = "fromacc")
		private long fromacc;//--发起人id	
		
		@Column(name = "fromacc_date")
		private String fromacc_date="";//--发起时间
		
		@Column(name = "toacc")
		private long toacc;//--接收人id
		
		@Column(name = "toacc_date")
		private String toacc_date="";//接收时间
		
		@Column(name = "types")
		private String types="0";//--类型：0表示发起，标识接收
		
		@Column(name = "remark")
		private String remark="";//--说明1
		
		@Column(name = "remark1")
		private String remark1="";//--说明2	
		
		@Column(name = "center_num")
		private String center_num;//体检中心编码	
		
		@Column(name="exam_num")
	    private String exam_num;
	    
	    public String getExam_num() {
			return exam_num;
		}

		public void setExam_num(String exam_num) {
			this.exam_num = exam_num;
		}

		public String getCenter_num() {
			return center_num;
		}

		public void setCenter_num(String center_num) {
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