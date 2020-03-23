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
 * 职业建议词库管理
 * 
 * @Title: 火箭蛙体检管理系统
 * @Package com.hjw.zyb.domain
 * @Description:
 * @author: zr
 * @date: 2017年4月24日 下午5:04:26
 * @version V2.0.0.0
 */
@Entity
@Table(name = "zyb_proposetemplate")
public class ZybProposetemplate   implements  Serializable{
	
	/**   
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)   
	 */ 
	private static final long serialVersionUID = 1L;

	@Id
	 @GeneratedValue(generator = "paymentableGenerator")
	 @GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
	 @Column(name = "TEMPLATEID", unique = true, nullable = false,length = 50)
	private String TEMPLATEID;
	
	@Column(name="OPTIONID")
	private String OPTIONID;
	
	@Column(name="TEMPLATENAME")
	private String TEMPLATENAME;
	
	@Column(name="CONTEXT")
	private String CONTEXT;
	
	@Column(name="ISDEFAULT")
	private boolean ISDEFAULT;
	
	@Column(name="ENABLE")
	private boolean ENABLE;
	
	@Column(name="UPDATER")
	private long UPDATER;
	
	@Column(name="UPDATETIME")
	private Date UPDATETIME;
	
	@Column(name="ORGID")
	private long ORGID;
	
	@Column(name="OCCUPECLASSID")
	private String OCCUPECLASSID;

	public String getTEMPLATEID() {
		return TEMPLATEID;
	}

	public void setTEMPLATEID(String tEMPLATEID) {
		TEMPLATEID = tEMPLATEID;
	}

	public String getOPTIONID() {
		return OPTIONID;
	}
	public void setOPTIONID(String oPTIONID) {
		OPTIONID = oPTIONID;
	}

	public String getTEMPLATENAME() {
		return TEMPLATENAME;
	}

	public void setTEMPLATENAME(String tEMPLATENAME) {
		TEMPLATENAME = tEMPLATENAME;
	}

	public String getCONTEXT() {
		return CONTEXT;
	}

	public void setCONTEXT(String cONTEXT) {
		CONTEXT = cONTEXT;
	}

	public boolean isISDEFAULT() {
		return ISDEFAULT;
	}

	public void setISDEFAULT(boolean iSDEFAULT) {
		ISDEFAULT = iSDEFAULT;
	}

	public boolean isENABLE() {
		return ENABLE;
	}

	public void setENABLE(boolean eNABLE) {
		ENABLE = eNABLE;
	}

	

	public long getUPDATER() {
		return UPDATER;
	}

	public void setUPDATER(long uPDATER) {
		UPDATER = uPDATER;
	}

	public Date getUPDATETIME() {
		return UPDATETIME;
	}

	public void setUPDATETIME(Date uPDATETIME) {
		UPDATETIME = uPDATETIME;
	}

	public long getORGID() {
		return ORGID;
	}

	public void setORGID(long oRGID) {
		ORGID = oRGID;
	}

	public String getOCCUPECLASSID() {
		return OCCUPECLASSID;
	}

	public void setOCCUPECLASSID(String oCCUPECLASSID) {
		OCCUPECLASSID = oCCUPECLASSID;
	}
	
}
