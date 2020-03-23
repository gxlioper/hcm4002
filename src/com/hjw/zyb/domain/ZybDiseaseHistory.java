package com.hjw.zyb.domain;

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
@SuppressWarnings("unused")
@Entity
@Table( name = "Zyb_disease_History")
public class ZybDiseaseHistory implements Serializable {
	/**   
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)   
	 */ 
	private static final long serialVersionUID = 8421337046368246204L;
	
	 @Id
	 @GeneratedValue(generator = "paymentableGenerator")
	 @GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
	 @Column(name = "Id", unique = true, nullable = false,length = 50)
	private String Id;
	 
	 @Column(name = "id_num")
	private String id_num;
	 
	 @Column(name = "exam_num")
	private String exam_num;
	 
	 @Column(name = "arch_num")
	private String arch_num;
	 
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
	 
	 @Column(name = "remark1")
	private String remark1;
	 
	 @Column(name = "remark2")
	private String remark2;
	 
	 @Column(name = "remark3")
	private String remark3;
	 
	 @Column(name = "creater")
	private long creater;
	 
	 @Column(name = "create_time")
	private Date create_time;

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

	public Date getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}

	 
}
