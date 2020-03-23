package com.hjw.zyb.DTO;

public class ZybExamSummaryResultOccidDTO implements java.io.Serializable {
	private static final long serialVersionUID = -97502163798576023L;
	private long id;
	private long result_id;
	private String occudiseaseIDorcontraindicationID;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getResult_id() {
		return result_id;
	}
	public void setResult_id(long result_id) {
		this.result_id = result_id;
	}
	public String getOccudiseaseIDorcontraindicationID() {
		return occudiseaseIDorcontraindicationID;
	}
	public void setOccudiseaseIDorcontraindicationID(String occudiseaseIDorcontraindicationID) {
		this.occudiseaseIDorcontraindicationID = occudiseaseIDorcontraindicationID;
	}
}
