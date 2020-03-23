package com.hjw.wst.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;


@Entity
@Table(name = "scale_question_option")
public class ScaleQuestionOption implements java.io.Serializable {
		private static final long serialVersionUID = 1L;

		@Id
		@GenericGenerator(name = "idGenerator", strategy = "identity")
		@GeneratedValue(generator = "idGenerator")
		private int id;

	    @Column(name = "questionID")
		private int questionID;

	    @Column(name = "optionID")
		private int optionID;

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public int getQuestionID() {
			return questionID;
		}

		public void setQuestionID(int questionID) {
			this.questionID = questionID;
		}

		public int getOptionID() {
			return optionID;
		}

		public void setOptionID(int optionID) {
			this.optionID = optionID;
		}
}