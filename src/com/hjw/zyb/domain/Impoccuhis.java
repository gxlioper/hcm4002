package com.hjw.zyb.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "impoccuhis")
public class Impoccuhis implements java.io.Serializable{
	
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
	@Column(name = "id", unique = true, nullable = false, length = 50)
	private String id;
	
	 @Column(name = "id_num")
	 private String id_num;
	 
	 @Column(name = "exam_num")
	 private String exam_num;
	 
	 @Column(name = "arch_num")
	 private String arch_num;
	 
	 @Column(name = "company")
	 private String company;
	 
	 @Column(name = "workshop")
	 private String workshop;
	 
	 @Column(name = "worktype")
	 private String worktype;
	 
	 @Column(name = "startdate")
	 private String startdate;
	 
	 @Column(name = "enddate")
	 private String enddate;
	 
	 @Column(name = "harmname")
	 private String harmname;
	 
	 @Column(name = "concentrations")
	 private String concentrations;
	 
	 @Column(name = "measure")
	 private String measure;
	 
	 @Column(name = "creater")
	 private long creater;
	 
	 @Column(name = "create_time")
	 private String create_time;
	 
	 @Column(name = "radiation")
	 private String radiation;
	 
	 @Column(name = "[man_haur]")
	 private String man_haur;
	 
	 @Column(name = "cumulative_exposure")
	 private String cumulative_exposure;
	 
	 @Column(name = "History_excessive")
	 private String History_excessive;
	 
	 @Column(name = "diseases")
	 private String diseases;
	 
	 @Column(name = "diagnosisdate")
	 private String diagnosisdate;
	 
	 @Column(name = "diagnosiscom")
	 private String diagnosiscom;
	 
	 @Column(name = "diagnosisnotice")
	 private String diagnosisnotice;
	 
	 @Column(name = "diseasereturn")
	 private String diseasereturn;
	 
	 @Column(name = "occuType")
	 private int occuType;
	 
	 @Column(name = "histype")
	 private int histype;

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

	public String getExam_num() {
		return exam_num;
	}

	public void setExam_num(String exam_num) {
		this.exam_num = exam_num;
	}

	public String getArch_num() {
		return arch_num;
	}

	public void setArch_num(String arch_num) {
		this.arch_num = arch_num;
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

	public String getMeasure() {
		return measure;
	}

	public void setMeasure(String measure) {
		this.measure = measure;
	}

	public long getCreater() {
		return creater;
	}

	public void setCreater(long creater) {
		this.creater = creater;
	}

	public String getCreate_time() {
		return create_time;
	}

	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}

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

	public String getDiseases() {
		return diseases;
	}

	public void setDiseases(String diseases) {
		this.diseases = diseases;
	}

	public String getDiagnosisdate() {
		return diagnosisdate;
	}

	public void setDiagnosisdate(String diagnosisdate) {
		this.diagnosisdate = diagnosisdate;
	}

	public String getDiagnosiscom() {
		return diagnosiscom;
	}

	public void setDiagnosiscom(String diagnosiscom) {
		this.diagnosiscom = diagnosiscom;
	}

	public String getDiagnosisnotice() {
		return diagnosisnotice;
	}

	public void setDiagnosisnotice(String diagnosisnotice) {
		this.diagnosisnotice = diagnosisnotice;
	}

	public String getDiseasereturn() {
		return diseasereturn;
	}

	public void setDiseasereturn(String diseasereturn) {
		this.diseasereturn = diseasereturn;
	}

	public int getOccuType() {
		return occuType;
	}

	public void setOccuType(int occuType) {
		this.occuType = occuType;
	}

	public int getHistype() {
		return histype;
	}

	public void setHistype(int histype) {
		this.histype = histype;
	}
	 
	 

}
