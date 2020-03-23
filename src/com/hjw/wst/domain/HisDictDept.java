package com.hjw.wst.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 
     * @Title:  火箭蛙体检管理系统   
     * @Package com.hjw.wst.domain   
     * @Description:  his执行科室字典
     * @author: 张瑞    
     * @date:   2016年9月28日 上午8:45:28   
     * @version V2.0.0.0
 */
@Entity
@Table(name="his_dict_dept")
public class HisDictDept implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	
	@GenericGenerator(name = "generator", strategy = "increment")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "dept_code", unique = true, nullable = false)
	private String dept_code;//编号
	
	@Column(name="dept_name")
	private String dept_name;//名称
	
	@Column(name="dept_class")
	private String dept_class;//类别
	
	@Column(name="input_code")
	private String input_code;//注计符--拼音

	public String getDept_code() {
		return dept_code;
	}

	public void setDept_code(String dept_code) {
		this.dept_code = dept_code;
	}

	public String getDept_name() {
		return dept_name;
	}

	public void setDept_name(String dept_name) {
		this.dept_name = dept_name;
	}

	public String getDept_class() {
		return dept_class;
	}

	public void setDept_class(String dept_class) {
		this.dept_class = dept_class;
	}

	public String getInput_code() {
		return input_code;
	}

	public void setInput_code(String input_code) {
		this.input_code = input_code;
	}
	
	
}
