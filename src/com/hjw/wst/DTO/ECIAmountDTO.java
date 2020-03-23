package com.hjw.wst.DTO;

public class ECIAmountDTO implements java.io.Serializable{
	/**
	* 注释内容
	*/
	private static final long serialVersionUID = 4456268124096194726L;

	private long exam_id;

	private String exam_num=""; //操作人员id
	
	private String username=""; //操作人员姓名

	private String sex="";  //系统功能表主功能id
	
	private int old;

	private double team_amt;
	
	private double pers_amt;

	public long getExam_id() {
		return exam_id;
	}

	public void setExam_id(long exam_id) {
		this.exam_id = exam_id;
	}

	public String getExam_num() {
		return exam_num;
	}

	public void setExam_num(String exam_num) {
		this.exam_num = exam_num;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public int getOld() {
		return old;
	}

	public void setOld(int old) {
		this.old = old;
	}

	public double getTeam_amt() {
		return team_amt;
	}

	public void setTeam_amt(double team_amt) {
		this.team_amt = team_amt;
	}

	public double getPers_amt() {
		return pers_amt;
	}

	public void setPers_amt(double pers_amt) {
		this.pers_amt = pers_amt;
	}

	
}
