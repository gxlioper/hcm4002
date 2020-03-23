package com.hjw.config;
/*
 * Title:Ȧ���ϵͳ
 * Description:
 * Dec 12, 2006
 * 
 * com.synjones.security.config
 *
 * Copyright: Copyright (c) 2005
 * Company: synjones
 * author yangm
 * version 2.0
 */

public class Logincheck {
   // private String fixtype;
    private String adminJob;
   // private String interfaceUrl;
    private int fixroleadmin;
    private int defaultrole;
    private String adminUser;
    private String jsoncontrol;
    private int adminUserId;
    private String version;    
    
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getJsoncontrol() {
		return jsoncontrol;
	}
	public void setJsoncontrol(String jsoncontrol) {
		this.jsoncontrol = jsoncontrol;
	}
	public String getAdminUser() {
		return adminUser;
	}
	public void setAdminUser(String adminUser) {
		this.adminUser = adminUser;
	}

	public String getAdminJob() {
		return adminJob;
	}
	public void setAdminJob(String adminJob) {
		this.adminJob = adminJob;
	}

	public int getFixroleadmin() {
		return fixroleadmin;
	}
	public void setFixroleadmin(int fixroleadmin) {
		this.fixroleadmin = fixroleadmin;
	}
	public int getDefaultrole() {
		return defaultrole;
	}
	public void setDefaultrole(int defaultrole) {
		this.defaultrole = defaultrole;
	}

	public int getAdminUserId() {
		return adminUserId;
	}
	public void setAdminUserId(int adminUserId) {
		this.adminUserId = adminUserId;
	}
	
	
}
