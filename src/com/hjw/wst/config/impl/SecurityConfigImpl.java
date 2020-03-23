package com.hjw.wst.config.impl;

import java.util.Set;

import com.hjw.config.SecurityConfig;



public class SecurityConfigImpl implements SecurityConfig {
  
    private String reSql1;
    private String reSql2;
    private Set noVelify;
    
    public String getReSql1() {
		return reSql1;
	}
	public void setReSql1(String reSql1) {
		this.reSql1 = reSql1;
	}
	public String getReSql2() {
		return reSql2;
	}
	public void setReSql2(String reSql2) {
		this.reSql2 = reSql2;
	}
	public Set getNoVelify() {
		return noVelify;
	}
	public void setNoVelify(Set noVelify) {
		this.noVelify = noVelify;
	}
}
