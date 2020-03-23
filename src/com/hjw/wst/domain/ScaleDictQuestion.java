package com.hjw.wst.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;


@Entity
@Table(name = "scale_dict_question")
public class ScaleDictQuestion implements java.io.Serializable {
		private static final long serialVersionUID = 1L;

		@Id
		@GenericGenerator(name = "idGenerator", strategy = "identity")
		@GeneratedValue(generator = "idGenerator")
		private int id;

	    @Column(name = "name")
		private String name;

	    @Column(name = "seqNo")
		private int seqNo;
	    
	    @Column(name = "scale_code")
		private String scale_code;
	    
	    @Column(name = "imgName")
		private String imgName;

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

		public int getSeqNo() {
			return seqNo;
		}

		public void setSeqNo(int seqNo) {
			this.seqNo = seqNo;
		}

		public String getScale_code() {
			return scale_code;
		}

		public void setScale_code(String scale_code) {
			this.scale_code = scale_code;
		}

		public String getImgName() {
			return imgName;
		}

		public void setImgName(String imgName) {
			this.imgName = imgName;
		}

}