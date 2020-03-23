package com.hjw.wst.DTO;

public class ExamFlowLogDTO implements java.io.Serializable {
	 private static final long serialVersionUID = -97502163798576023L;
     private long id;
     private long acccreater;
     private long sendcreater;
     private String senddate;
     private int flow_type;
     private String flow_types;
     private String flow_name="";
     private String senduname="";
	 private String accuname="";
	 
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getAcccreater() {
		return acccreater;
	}
	public void setAcccreater(long acccreater) {
		this.acccreater = acccreater;
	}
	public long getSendcreater() {
		return sendcreater;
	}
	public void setSendcreater(long sendcreater) {
		this.sendcreater = sendcreater;
	}
	public String getSenddate() {
		return senddate;
	}
	public void setSenddate(String senddate) {
		this.senddate = senddate;
	}
	public int getFlow_type() {
		return flow_type;
	}
	public void setFlow_type(int flow_type) {
		this.flow_type = flow_type;
		if(this.flow_type==0){
			this.flow_types="删除";
		}else if(this.flow_type==1){
			this.flow_types="正常";
		}
	}
	public String getFlow_types() {
		return flow_types;
	}
	public void setFlow_types(String flow_types) {
		this.flow_types = flow_types;
	}
	public String getFlow_name() {
		return flow_name;
	}
	public void setFlow_name(String flow_name) {
		this.flow_name = flow_name;
	}
	public String getSenduname() {
		return senduname;
	}
	public void setSenduname(String senduname) {
		this.senduname = senduname;
	}
	public String getAccuname() {
		return accuname;
	}
	public void setAccuname(String accuname) {
		this.accuname = accuname;
	}
		
	 
	}