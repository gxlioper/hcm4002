package com.hjw.zyb.model;

import java.io.Serializable;


/**
 * 
     * @Title:  火箭蛙体检管理系统   
     * @Package com.hjw.zyb.model   
     * @Description:  
     * @author: zt     
     * @date:   2017年4月21日 上午9:52:44   
     * @version V2.0.0.0
 */
public class ZybExaminationresultModel implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String  resultID;
	private String  result_name;
	private String ids;
	private Long seq_code;
	
	
	public Long getSeq_code() {
		return seq_code;
	}
	public void setSeq_code(Long seq_code) {
		this.seq_code = seq_code;
	}
	public String getIds() {
		return ids;
	}
	public void setIds(String ids) {
		this.ids = ids;
	}
	public String getResultID() {
		return resultID;
	}
	public void setResultID(String resultID) {
		this.resultID = resultID;
	}
	public String getResult_name() {
		return result_name;
	}
	public void setResult_name(String result_name) {
		this.result_name = result_name;
	}
	public ZybExaminationresultModel() {
		// TODO Auto-generated constructor stub
	}
	
	
}
