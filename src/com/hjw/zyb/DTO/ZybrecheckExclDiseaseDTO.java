package com.hjw.zyb.DTO;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

/**
 * 复查需要排除目标疾病管理
     * @Title:  火箭蛙体检管理系统   
     * @Package com.hjw.zyb.domain   
     * @Description:  
     * @author: yangm     
     * @date:   2017年4月19日 下午4:30:20   
     * @version V2.0.0.0
 */
public class ZybrecheckExclDiseaseDTO  implements  Serializable{

	/**   
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)   
	 */ 
	private static final long serialVersionUID = 1L;
	private String  check_disease_id;
    private String  check_disease_name;
    
	public String getCheck_disease_id() {
		return check_disease_id;
	}
	public void setCheck_disease_id(String check_disease_id) {
		this.check_disease_id = check_disease_id;
	}
	public String getCheck_disease_name() {
		return check_disease_name;
	}
	public void setCheck_disease_name(String check_disease_name) {
		this.check_disease_name = check_disease_name;
	}
    
    
}
