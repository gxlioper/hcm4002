package com.hjw.wst.DTO;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/** 
 * <b>Application name:</b>新中新第一事业部<br>
 * <b>Application describing:</b> <br>
 * <b>Copyright:</b>Copyright &copy; 2013 新中新第一事业部。<br>
 * <b>Company:</b>Neusoft<br>
 * <b>Date:</b>2013-4-15<br>
 * @author 路志友
 * @version $Revision$
 */


public class WebConfigDTO{
    
    private String code;// 编号
    private String name;//名称
    private String types;//类型
    private String remark;//标记
    private String memo;//1 表示字符型，2表示数字型（不带小数点），3表示带小数点
    
    
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
