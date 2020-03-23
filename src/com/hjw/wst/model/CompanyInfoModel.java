package com.hjw.wst.model;

import java.util.Date;

import javax.persistence.Column;

/**
 * 
     * @Title:  火箭蛙体检管理系统   
     * @Package com.hjw.wst.domain   
     * @Description:  
     * @author: yangm     
     * @date:   2016年7月4日 上午11:18:07   
     * @version V2.0.0.0
 */
public class CompanyInfoModel implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private long id;

	private Long parent_com_id=new Long(0);
	
    private long company_Id;

	private String dep_Name="";
	
	private String father_con_num="";

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

	private Long creater=new Long(0);

	private Date create_Time=null;

	private Long updater=new Long(0);

	private Date update_Time=null;
	
	private int edittype;
		
	private String name_pinyin="";
	
    private String economiccode;//经济类型

	private String industrycode;//行业类型

	private String areacode;//行政区划

	private String comsizecode;//企业规模
	
	private String com_zip;//单位邮编
	
	private String com_phone;//单位电话
	
	private String keShi_Name; //科室联系人

	private String com_fax; //单位传真
	
	private String assigned_unit_code; //客户合同单位代码
	
    private long fromUserId;
	
	private String flow_code;
	
	private String com_jianjie; //公司简介
	
	public String getCom_jianjie() {
		return com_jianjie;
	}

	public void setCom_jianjie(String com_jianjie) {
		this.com_jianjie = com_jianjie;
	}

	public String getKeShi_Name() {
		return keShi_Name;
	}

	public void setKeShi_Name(String keShi_Name) {
		this.keShi_Name = keShi_Name;
	}

	public String getCom_fax() {
		return com_fax;
	}

	public void setCom_fax(String com_fax) {
		this.com_fax = com_fax;
	}

	public String getAssigned_unit_code() {
		return assigned_unit_code;
	}

	public void setAssigned_unit_code(String assigned_unit_code) {
		this.assigned_unit_code = assigned_unit_code;
	}

	public String getEconomiccode() {
		return economiccode;
	}

	public void setEconomiccode(String economiccode) {
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

	public String getFather_con_num() {
		return father_con_num;
	}

	public void setFather_con_num(String father_con_num) {
		this.father_con_num = father_con_num;
	}

	public int getEdittype() {
		return edittype;
	}

	public void setEdittype(int edittype) {
		this.edittype = edittype;
	}

	public long getCompany_Id() {
		return company_Id;
	}

	public void setCompany_Id(long company_Id) {
		this.company_Id = company_Id;
	}

	public String getDep_Name() {
		return dep_Name;
	}

	public void setDep_Name(String dep_Name) {
		this.dep_Name = dep_Name;
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

	public Long getCreater() {
		return creater;
	}

	public void setCreater(Long creater) {
		this.creater = creater;
	}

	public Date getCreate_Time() {
		return create_Time;
	}

	public void setCreate_Time(Date create_Time) {
		this.create_Time = create_Time;
	}

	public Long getUpdater() {
		return updater;
	}

	public void setUpdater(Long updater) {
		this.updater = updater;
	}

	public Date getUpdate_Time() {
		return update_Time;
	}

	public void setUpdate_Time(Date update_Time) {
		this.update_Time = update_Time;
	}

	public String getCom_zip() {
		return com_zip;
	}

	public void setCom_zip(String com_zip) {
		this.com_zip = com_zip;
	}

	public String getCom_phone() {
		return com_phone;
	}

	public void setCom_phone(String com_phone) {
		this.com_phone = com_phone;
	}

	public long getFromUserId() {
		return fromUserId;
	}

	public void setFromUserId(long fromUserId) {
		this.fromUserId = fromUserId;
	}

	public String getFlow_code() {
		return flow_code;
	}

	public void setFlow_code(String flow_code) {
		this.flow_code = flow_code;
	}

}