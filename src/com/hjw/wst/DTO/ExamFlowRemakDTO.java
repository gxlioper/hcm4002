package com.hjw.wst.DTO;


/**
 * 流程备注
     * @Title:  火箭蛙体检管理系统   
     * @Package com.hjw.wst.domain   
     * @Description:  
     * @author: yangm     
     * @date:   2018年3月22日 上午8:56:03   
     * @version V2.0.0.0
 */
public class ExamFlowRemakDTO{

	private long id;
	 
	 
	private String exam_num;
	 
	private long    dep_id;
	 
	private String    remark;
	 
	private long    remark_user;
	 
	private String   remark_time;
	private String   chi_name;
	private String dep_name;
	private long process;
	private String process_name;
	
	
	
	public long getProcess() {
		return process;
	}
	public void setProcess(long process) {
		this.process = process;
		if(process==1){
			this.setProcess_name("登记台");
		} else if(process==2){
			this.setProcess_name("导检单");
		} else if(process==3){
			this.setProcess_name("整单室");
		} else if(process==4){
			this.setProcess_name("报告室");
		} else if(process==5){
			this.setProcess_name("打印室");
		} else if(process==6){
			this.setProcess_name("收发室");
		} else if(process==7){
			this.setProcess_name("解读室");
		} else{
			
		}
	}
	public String getProcess_name() {
		return process_name;
	}
	public void setProcess_name(String process_name) {
		this.process_name = process_name;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getExam_num() {
		return exam_num;
	}
	public void setExam_num(String exam_num) {
		this.exam_num = exam_num;
	}
	public long getDep_id() {
		return dep_id;
	}
	public void setDep_id(long dep_id) {
		
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public long getRemark_user() {
		return remark_user;
	}
	public void setRemark_user(long remark_user) {
		this.remark_user = remark_user;
	}
	public String getRemark_time() {
		return remark_time;
	}
	public void setRemark_time(String remark_time) {
		this.remark_time = remark_time;
	}
	public String getChi_name() {
		return chi_name;
	}
	public void setChi_name(String chi_name) {
		this.chi_name = chi_name;
	}
	public String getDep_name() {
		return dep_name;
	}
	public void setDep_name(String dep_name) {
		this.dep_name = dep_name;
	}
	
	
	
	
	
	

}
