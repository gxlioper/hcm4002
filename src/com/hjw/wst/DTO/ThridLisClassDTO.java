package com.hjw.wst.DTO;

import com.hjw.util.StringUtil;

public class ThridLisClassDTO{
	private String lisid = "";
	private String lisclassname = "";
	private String create_time = "";
	private String update_time = "";
	private String iSampleId="";//样本ID
    private String tubeID="";//试管ID
    private String testtubeName="";//试管名称
       
	public String getiSampleId() {
		return iSampleId;
	}

	public void setiSampleId(String iSampleId) {
		this.iSampleId = iSampleId;
	}

	public String getTubeID() {
		return tubeID;
	}

	public void setTubeID(String tubeID) {
		this.tubeID = tubeID;
	}

	public String getTesttubeName() {
		return testtubeName;
	}

	public void setTesttubeName(String testtubeName) {
		this.testtubeName = testtubeName;
	}

	public String getLisid() {
		return lisid;
	}

	public void setLisid(String lisid) {
		this.lisid = lisid;
	}

	public String getLisclassname() {
		return lisclassname;
	}

	public void setLisclassname(String lisclassname) {
		this.lisclassname = lisclassname;
		this.lisclassname=StringUtil.escapeExprSpecialWord(this.lisclassname);  
	}

	public String getCreate_time() {
		return create_time;
	}

	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}

	public String getUpdate_time() {
		return update_time;
	}

	public void setUpdate_time(String update_time) {
		this.update_time = update_time;
	}

}
