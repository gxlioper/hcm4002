package com.hjw.zyb.DTO;

import java.io.Serializable;

/**
 * 职业建议词库管理
     * @Title:  火箭蛙体检管理系统   
     * @Package com.hjw.zyb.DTO   
     * @Description:  
     * @author: zr     
     * @date:   2017年4月24日 下午5:27:32   
     * @version V2.0.0.0
 */
public class ZybProposetemplateDTO   implements  Serializable{
	
	private String TEMPLATEID;
	
	private String OPTIONID;
	
	private String TEMPLATENAME;
	
	private String CONTEXT;
	
	private boolean ISDEFAULT;
	
	private boolean ENABLE;
	
	private long UPDATER;
	
	private String user_name;
	
	private String UPDATETIME;
	
	private long ORGID;
	
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

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getUPDATETIME() {
		return UPDATETIME;
	}

	public void setUPDATETIME(String uPDATETIME) {
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
