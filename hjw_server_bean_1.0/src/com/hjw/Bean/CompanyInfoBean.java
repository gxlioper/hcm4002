package com.hjw.Bean;

/**
 * 
     * @Title:  火箭蛙体检管理系统   
     * @Package com.hjw.wst.domain   
     * @Description:  
     * @author: yangm     
     * @date:   2016年7月4日 上午11:18:07   
     * @version V2.0.0.0
 */

public class CompanyInfoBean implements java.io.Serializable {

	/**   
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)   
	 */ 
	private static final long serialVersionUID = 1L;
	
	private long synid;

	private long id;

	private Long parent_com_id;

	private String com_Num="";

	private String com_Name="";

	private String com_Type="";

	private long com_Level;

	private String contact_Name="";

	private String contact_Phone="";

	private String email="";

	private String address="";

	private String remark="";

	private String is_Active="";

	private long creater;

	private String create_Time;

	private long updater;
	
	private long readFlag;

	private String update_Time;

	private String name_pinyin="";

	private String economiccode="";//经济类型

	private String industrycode="";//行业类型

	private String areacode="";//行政区划

	private String comsizecode="";//企业规模	

	public long getSynid() {
		return synid;
	}

	public void setSynid(long synid) {
		this.synid = synid;
	}

	public void setEconomiccode(String economiccode) {
		this.economiccode = economiccode;
	}

	public String getEconomiccode() {
		return economiccode;
	}

	public void setEconomicidcode(String economiccode) {
		this.economiccode = economiccode;
	}

	public String getIndustrycode() {
		return industrycode;
	}

	public void setIndustrycode(String industrycode) {
		this.industrycode = industrycode;
	}

	public String getAreacode() {
		return areacode;
	}

	public void setAreacode(String areacode) {
		this.areacode = areacode;
	}

	public String getComsizecode() {
		return comsizecode;
	}

	public void setComsizecode(String comsizecode) {
		this.comsizecode = comsizecode;
	}

	public String getName_pinyin() {
		return name_pinyin;
	}

	public void setName_pinyin(String name_pinyin) {
		this.name_pinyin = name_pinyin;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Long getParent_com_id() {
		return parent_com_id;
	}

	public void setParent_com_id(Long parent_com_id) {
		this.parent_com_id = parent_com_id;
	}

	public String getCom_Num() {
		return com_Num;
	}

	public void setCom_Num(String com_Num) {
		this.com_Num = com_Num;
	}

	public String getCom_Name() {
		return com_Name;
	}

	public void setCom_Name(String com_Name) {
		this.com_Name = com_Name;
	}

	public String getCom_Type() {
		return com_Type;
	}

	public void setCom_Type(String com_Type) {
		this.com_Type = com_Type;
	}

	public long getCom_Level() {
		return com_Level;
	}

	public void setCom_Level(long com_Level) {
		this.com_Level = com_Level;
	}

	public String getContact_Name() {
		return contact_Name;
	}

	public void setContact_Name(String contact_Name) {
		this.contact_Name = contact_Name;
	}

	public String getContact_Phone() {
		return contact_Phone;
	}

	public void setContact_Phone(String contact_Phone) {
		this.contact_Phone = contact_Phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getIs_Active() {
		return is_Active;
	}

	public void setIs_Active(String is_Active) {
		this.is_Active = is_Active;
	}

	public long getCreater() {
		return creater;
	}

	public void setCreater(long creater) {
		this.creater = creater;
	}

	public String getCreate_Time() {
		return create_Time;
	}

	public void setCreate_Time(String create_Time) {
		this.create_Time = create_Time;
	}

	public long getUpdater() {
		return updater;
	}

	public void setUpdater(long updater) {
		this.updater = updater;
	}

	public String getUpdate_Time() {
		return update_Time;
	}

	public void setUpdate_Time(String update_Time) {
		this.update_Time = update_Time;
	}

	public long getReadFlag() {
		return readFlag;
	}

	public void setReadFlag(long readFlag) {
		this.readFlag = readFlag;
	}

}