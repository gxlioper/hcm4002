package com.hjw.wst.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;


@Entity
@Table(name = "scale_dict_appraise")
public class ScaleDictAppraise implements java.io.Serializable {
		private static final long serialVersionUID = 1L;

		@Id
		@GenericGenerator(name = "idGenerator", strategy = "identity")
		@GeneratedValue(generator = "idGenerator")
		private int id;

	    @Column(name = "fromPoint")
		private int fromPoint;
	    
	    @Column(name = "toPoint")
	    private int toPoint;
	    
	    @Column(name = "content")
	    private String content;
	    
	    @Column(name = "scale_code")
	    private String scale_code;

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public int getFromPoint() {
			return fromPoint;
		}

		public void setFromPoint(int fromPoint) {
			this.fromPoint = fromPoint;
		}

		public String getContent() {
			return content;
		}

		public void setContent(String content) {
			this.content = content;
		}

		public String getScale_code() {
			return scale_code;
		}

		public void setScale_code(String scale_code) {
			this.scale_code = scale_code;
		}

		public int getToPoint() {
			return toPoint;
		}

		public void setToPoint(int toPoint) {
			this.toPoint = toPoint;
		}
}