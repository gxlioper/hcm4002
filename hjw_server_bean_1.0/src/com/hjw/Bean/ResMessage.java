package com.hjw.Bean;

public class ResMessage {
	private int rescode;//":"结果类型", 0或其他	int类型，0表示成功，其他数字表示失败,不能为空
	private String restext="";//处理结果说明"
	private String resbody="";//返回结构体
	
	public int getRescode() {
		return rescode;
	}
	public void setRescode(int rescode) {
		this.rescode = rescode;
	}
	public String getRestext() {
		return restext;
	}
	public void setRestext(String restext) {
		this.restext = restext;
	}
	public String getResbody() {
		return resbody;
	}
	public void setResbody(String resbody) {
		this.resbody = resbody;
	}	
}
