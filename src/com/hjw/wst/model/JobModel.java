package com.hjw.wst.model;


public class JobModel  implements java.io.Serializable {
	private static final long serialVersionUID = 6736394712820610270L;
	 private String id;
	 private String gwbm;
     private String gwmc;
     private String bmbh;
     private String gwjsid;
     private String roleshid;
     private String rolezyid;
     private String roleid;
     private String root;
     private int rp;
     private int page;
     
     
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getRp() {
		return rp;
	}
	public void setRp(int rp) {
		this.rp = rp;
	}
	public String getBmbh() {
		return bmbh;
	}
	public void setBmbh(String bmbh) {
		this.bmbh = bmbh;
	}
	public String getGwbm() {
		return gwbm;
	}
	public void setGwbm(String gwbm) {
		this.gwbm = gwbm;
	}
	public String getGwjsid() {
		return gwjsid;
	}
	public void setGwjsid(String gwjsid) {
		this.gwjsid = gwjsid;
	}
	public String getGwmc() {
		return gwmc;
	}
	public void setGwmc(String gwmc) {
		this.gwmc = gwmc;
	}
	
	public String getRoleshid() {
		return roleshid;
	}
	public void setRoleshid(String roleshid) {
		this.roleshid = roleshid;
	}
	public String getRolezyid() {
		return rolezyid;
	}
	public void setRolezyid(String rolezyid) {
		this.rolezyid = rolezyid;
	}
	public String getRoleid() {
		return roleid;
	}
	public void setRoleid(String roleid) {
		this.roleid = roleid;
	}
	public String getRoot() {
		return root;
	}
	public void setRoot(String root) {
		this.root = root;
	}
     

}



