package com.hjw.zyb.DTO;

import java.io.Serializable;

/**
 * 体检结论
     * @Title:  火箭蛙体检管理系统   
     * @Package com.hjw.zyb.DTO   
     * @Description:  
     * @author: zr     
     * @date:   2017年4月13日 下午3:17:06   
     * @version V2.0.0.0
 */
public class ZybexaminationresultDTO  implements  Serializable{

	/**   
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)   
	 */ 
	private static final long serialVersionUID = 1L;
	private String  resultID;
	private String  result_name;
	private String seq_code;

	
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
	public String getSeq_code() {
		return seq_code;
	}
	public void setSeq_code(String seq_code) {
		this.seq_code = seq_code;
	}
	
	

}
