package com.hjw.wst.DTO;

import java.util.ArrayList;
import java.util.List;

import com.hjw.wst.domain.WebResrelAtionship;

/*
 * Title:ϵͳ
 * Description: 
 *   Jun 8, 200710:49:43 AM
 *     webschoolcard
 *      com.synjones.wst.domain
 *
 * Copyright:   Copyright (c) 2011-2016
 * Company:     syntongs
 * author       yangm
 * version      3.0.0.0
 */

public class UserDTO implements Cloneable, java.io.Serializable {
	private static final long serialVersionUID = -3565567339406922273L;
	private boolean login;

	private String identityID;// 身份序号
	
	private String name;// 姓名
	
	private String rolestr;

	private String sessionId;

	private String deptCode;// 部门代码

	private String error;// 错误信息
	private long id;
	// 通过cas登录得到
	private long userid;
	private String Email;
	private String tel;
	private String Department;
	private List<JobDTO> Job = new ArrayList<JobDTO>();
	private String xtgn; //以逗点隔开
	private List<String> res = new ArrayList<String>();
	private String Identity;
	private String username;//登录名
	private String usertype;
	private String reportid;
	private long dep_id;
	private long center_id;
	private String center_num;
	private String center_name;
	private String uuid;	
	private String dep_url;	
	private String work_num;
	private long isPrint;
	private String work_other_num;
	private String filelist;
	
	private List<WebResrelAtionship> webResource=new ArrayList<WebResrelAtionship>();//资源

	public List<WebResrelAtionship> getWebResource() {
		return webResource;
	}

	public void setWebResource(List<WebResrelAtionship> webResource) {
		this.webResource = webResource;
	}

	public String getReportid() {
		return reportid;
	}

	public void setReportid(String reportid) {
		this.reportid = reportid;
	}

	public String getCenter_num() {
		return center_num;
	}

	public void setCenter_num(String center_num) {
		this.center_num = center_num;
	}

	public String getCenter_name() {
		return center_name;
	}

	public void setCenter_name(String center_name) {
		this.center_name = center_name;
	}

	public String getDep_url() {
		return dep_url;
	}

	public void setDep_url(String dep_url) {
		this.dep_url = dep_url;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getUsertype() {
		return usertype;
	}

	public void setUsertype(String usertype) {
		this.usertype = usertype;
	}

	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

	public String getDepartment() {
		return Department;
	}
	
	

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setDepartment(String department) {
		Department = department;
	}

	public String getEmail() {
		return Email;
	}

	public void setEmail(String email) {
		Email = email;
	}

	public String getIdentity() {
		return Identity;
	}

	public void setIdentity(String identity) {
		Identity = identity;
	}

	public long getUserid() {
		return userid;
	}

	public void setUserid(long userid) {
		this.userid = userid;
	}

	/**
	 * @return Returns the identityID.
	 */
	public String getIdentityID() {
		return identityID;
	}

	/**
	 * @param identityID
	 *            The identityID to set.
	 */
	public void setIdentityID(String identityID) {
		this.identityID = identityID;
	}

	/**
	 * @return Returns the login.
	 */
	public boolean isLogin() {
		return login;
	}

	/**
	 * @param login
	 *            The login to set.
	 */
	public void setLogin(boolean login) {
		this.login = login;
	}

	/**
	 * @return Returns the sessionId.
	 */
	public String getSessionId() {
		return sessionId;
	}

	/**
	 * @param sessionId
	 *            The sessionId to set.
	 */
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	/**
	 * @return the error
	 */
	public String getError() {
		return error;
	}

	/**
	 * @param error
	 *            the error to set
	 */
	public void setError(String error) {
		this.error = error;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	public String getDeptCode() {
		return deptCode;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	public List<JobDTO> getJob() {
		return Job;
	}

	public void setJob(List<JobDTO> job) {
		Job = job;
	}

	public String getXtgn() {
		return xtgn;
	}

	public void setXtgn(String xtgn) {
		this.xtgn = xtgn;
	}

	public String getJobstr(){
		StringBuffer sb = new StringBuffer();
		if (Job==null) return null;
		for (JobDTO jb  : Job) {
			sb.append("'"+jb.getId()+"'").append(",");
		}
		
		if(Job.size()>0)
			sb.delete(sb.length()-1, sb.length());
		
		return sb.toString();
	}

	public List<String> getRes() {
		return res;
	}
	
	public List<String> getRes(String prefix) {
		List<String> list = new ArrayList<String>();
		for (String str : res) {
			if(str.startsWith(prefix))
				list.add(str.toLowerCase().replaceAll(prefix, ""));
		}
		return list;
	}
   
	
	public void setRes(List<String> res) {
		this.res = res;
	}

	public String getRolestr() {
		return rolestr;
	}

	public void setRolestr(String rolestr) {
		this.rolestr = rolestr;
	}

	public long getDep_id() {
		return dep_id;
	}

	public void setDep_id(long dep_id) {
		this.dep_id = dep_id;
	}

	public long getCenter_id() {
		return center_id;
	}

	public void setCenter_id(long center_id) {
		this.center_id = center_id;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getWork_num() {
		return work_num;
	}

	public void setWork_num(String work_num) {
		this.work_num = work_num;
	}

	public long getIsPrint() {
		return isPrint;
	}

	public void setIsPrint(long isPrint) {
		this.isPrint = isPrint;
	}

	public String getFilelist() {
		return filelist;
	}

	public void setFilelist(String filelist) {
		this.filelist = filelist;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getWork_other_num() {
		return work_other_num;
	}

	public void setWork_other_num(String work_other_num) {
		this.work_other_num = work_other_num;
	}
	
}
