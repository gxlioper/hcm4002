package com.hjw.crm.DTO;

import java.util.Date;

public class CompanyInfoDTO {

	private long id;
	private Long _parentId;
	private String com_name;
	private String com_type;
	private String com_types;
	private String address;
	private String economiccode;
	private String economiccodes;
	private String industrycode;
	private String industrycodes;
	private String areacode;
	private String areacodes;
	private String comsizecode;
	private String comsizecodes;
	
	private Long parent_com_id;
	
	private String com_Num="";
	
	private String com_Name="";
	
	private String com_Type="";
	
	private long com_Level;
	
	private String contact_Name="";
	
	private String contact_Phone="";
	
	private String email="";
	
	private String remark="";
	
	private String is_Active="";
	
	private Long creater;
	
	private Date create_Time;
	
	private Long updater;

	private Date update_Time;
	
	private String name_pinyin="";
	
	private String com_zip="";//单位邮编
	
	private String com_phone="";//单位电话	

	private String keShi_Name="";//科室联系人
	
	private String com_fax="";//单位传真
	
	private String assigned_unit_code="";//客户合同单位代码	

	private String com_jianjie; //公司简介
    private String center_num;

    public String getCenter_num() {
        return center_num;
    }

    public void setCenter_num(String center_num) {
        this.center_num = center_num;
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
	public String getName_pinyin() {
		return name_pinyin;
	}
	public void setName_pinyin(String name_pinyin) {
		this.name_pinyin = name_pinyin;
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
	public String getCom_jianjie() {
		return com_jianjie;
	}
	public void setCom_jianjie(String com_jianjie) {
		this.com_jianjie = com_jianjie;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Long get_parentId() {
		return _parentId;
	}
	public void set_parentId(Long _parentId) {
		this._parentId = _parentId;
	}
	public String getCom_name() {
		return com_name;
	}
	public void setCom_name(String com_name) {
		this.com_name = com_name;
	}
	public String getCom_type() {
		return com_type;
	}
	public void setCom_type(String com_type) {
		this.com_type = com_type;
	}
	public String getCom_types() {
		return com_types;
	}
	public void setCom_types(String com_types) {
		this.com_types = com_types;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getEconomiccode() {
		return economiccode;
	}
	public void setEconomiccode(String economiccode) {
		this.economiccode = economiccode;
	}
	public String getEconomiccodes() {
		return economiccodes;
	}
	public void setEconomiccodes(String economiccodes) {
		this.economiccodes = economiccodes;
	}
	public String getIndustrycode() {
		return industrycode;
	}
	public void setIndustrycode(String industrycode) {
		this.industrycode = industrycode;
	}
	public String getIndustrycodes() {
		return industrycodes;
	}
	public void setIndustrycodes(String industrycodes) {
		this.industrycodes = industrycodes;
	}
	public String getAreacode() {
		return areacode;
	}
	public void setAreacode(String areacode) {
		this.areacode = areacode;
	}
	public String getAreacodes() {
		return areacodes;
	}
	public void setAreacodes(String areacodes) {
		this.areacodes = areacodes;
	}
	public String getComsizecode() {
		return comsizecode;
	}
	public void setComsizecode(String comsizecode) {
		this.comsizecode = comsizecode;
	}
	public String getComsizecodes() {
		return comsizecodes;
	}
	public void setComsizecodes(String comsizecodes) {
		this.comsizecodes = comsizecodes;
	}
}
