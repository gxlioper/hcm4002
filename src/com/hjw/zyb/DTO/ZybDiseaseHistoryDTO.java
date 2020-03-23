package com.hjw.zyb.DTO;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 既往史
 * 
 * @Title: 火箭蛙体检管理系统
 * @Package com.hjw.zyb.domain
 * @Description:
 * @author:
 * @date: 2017年12月22日 下午3:42:34
 * @version V2.0.0.0
 */
public class ZybDiseaseHistoryDTO implements Serializable {
	/**   
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)   
	 */ 
	private static final long serialVersionUID = 8421337046368246204L;
	private String Id;
	 
	private String id_num;
	 
	private String exam_num;
	 
	private String arch_num;
	 
	private String diseases;
	 
	 
	private String diagnosisdate;
	 
	private String diagnosiscom;
	 
	private String diagnosisnotice;
	 
	private String diseasereturn;
	 
	private String remark1;
	 
	private String remark2;
	 
	private String remark3;
	 
	private long creater;
	 
	private String create_time;

	public String getId() {
		return Id;
	}

	public void setId(String id) {
		Id = id;
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

	 
}
