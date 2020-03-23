package com.hjw.zyb.DTO;

import javax.persistence.Column;


public class ZybOccuHisDTO implements java.io.Serializable {
	 private static final long serialVersionUID = -97502163798576023L;	 

     private String id;

	 private String id_num;

	 private String arch_num;

	 private String exam_num;

	 private String company;

	 private String workshop;

	 private String worktype;

	 private String startdate;

	 private String enddate;

	 private String measure;

	 private String harmname;

	 private String concentrations;

	 private String isradiation;

	 private int creater;

	 private String create_date;

	 private String remark1;

	 private String remark2;

	 private String remark3;
	 
	 private String radiation;
	 
	 private String man_haur;
	 
	 private String cumulative_exposure;
	 
	 private String History_excessive;
	 
	 private String Remark;
	 
	 

	public String getRadiation() {
		return radiation;
	}

	public void setRadiation(String radiation) {
		this.radiation = radiation;
	}



	public String getMan_haur() {
		return man_haur;
	}

	public void setMan_haur(String man_haur) {
		this.man_haur = man_haur;
	}

	public String getCumulative_exposure() {
		return cumulative_exposure;
	}

	public void setCumulative_exposure(String cumulative_exposure) {
		this.cumulative_exposure = cumulative_exposure;
	}

	public String getHistory_excessive() {
		return History_excessive;
	}

	public void setHistory_excessive(String history_excessive) {
		History_excessive = history_excessive;
	}

	public String getRemark() {
		return Remark;
	}

	public void setRemark(String remark) {
		Remark = remark;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getId_num() {
		return id_num;
	}

	public void setId_num(String id_num) {
		this.id_num = id_num;
	}

	public String getArch_num() {
		return arch_num;
	}

	public void setArch_num(String arch_num) {
		this.arch_num = arch_num;
	}

	public String getExam_num() {
		return exam_num;
	}

	public void setExam_num(String exam_num) {
		this.exam_num = exam_num;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getWorkshop() {
		return workshop;
	}

	public void setWorkshop(String workshop) {
		this.workshop = workshop;
	}

	public String getWorktype() {
		return worktype;
	}

	public void setWorktype(String worktype) {
		this.worktype = worktype;
	}

	public String getStartdate() {
		return startdate;
	}

	public void setStartdate(String startdate) {
		this.startdate = startdate;
	}

	public String getEnddate() {
		return enddate;
	}

	public void setEnddate(String enddate) {
		this.enddate = enddate;
	}

	public String getMeasure() {
		return measure;
	}

	public void setMeasure(String measure) {
		this.measure = measure;
	}

	public String getHarmname() {
		return harmname;
	}

	public void setHarmname(String harmname) {
		this.harmname = harmname;
	}

	public String getConcentrations() {
		return concentrations;
	}

	public void setConcentrations(String concentrations) {
		this.concentrations = concentrations;
	}

	public String getIsradiation() {
		return isradiation;
	}

	public void setIsradiation(String isradiation) {
		//this.isradiation = isradiation;
		if("N".equals(isradiation)){
			this.isradiation="否";
		}else if("Y".equals(isradiation)){
			this.isradiation="是";
		}
	}

	public int getCreater() {
		return creater;
	}

	public void setCreater(int creater) {
		this.creater = creater;
	}

	public String getCreate_date() {
		return create_date;
	}

	public void setCreate_date(String create_date) {
		this.create_date = create_date;
	}

	public String getRemark1() {
		return remark1;
	}

	public void setRemark1(String remark1) {
		this.remark1 = remark1;
	}

	public String getRemark2() {
		return remark2;
	}

	public void setRemark2(String remark2) {
		this.remark2 = remark2;
	}

	public String getRemark3() {
		return remark3;
	}

	public void setRemark3(String remark3) {
		this.remark3 = remark3;
	}	 
	 
}