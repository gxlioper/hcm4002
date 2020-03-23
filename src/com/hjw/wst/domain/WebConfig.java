package com.hjw.wst.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;


@Entity 
@Table(name= "web_config")  
public class WebConfig implements java.io.Serializable{
    private static final long serialVersionUID = -97502163798576023L;
    
    @Id
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
    @Column(name = "CODE", unique = true, nullable = false,length = 50)
    private String code;// 编号
    @Column(name = "NAME", length = 50)
    private String name;//名称
    @Column(name = "TYPES", length = 150)
    private String types;//类型
    @Column(name = "REMARK", length = 100)
    private String remark;//标记
    @Column(name = "MEMO", length = 50)
    private String memo;//标记
    
    public String getMemo()
	{
		return memo;
	}
	public void setMemo(String memo)
	{
		this.memo = memo;
	}
	public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getTypes() {
        return types;
    }
    public void setTypes(String types) {
        this.types = types;
    }
    public String getRemark() {
        return remark;
    }
    public void setRemark(String remark) {
        this.remark = remark;
    }
    
    
    
    
}
