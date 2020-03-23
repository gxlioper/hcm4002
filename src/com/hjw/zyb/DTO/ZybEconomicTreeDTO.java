package com.hjw.zyb.DTO;

import java.util.ArrayList;
import java.util.List;




public class ZybEconomicTreeDTO {
	/**
	 * 树结构信息
	 */
	
	//经济行业管理属性
	private String id;
	private String industryID;
	private String industry_code;
	private String industry_name;
	private String _parentId;
	private String industry_name_f;
	private String industry_name_i;
	//工种管理
	private String typeofworkID;
	private String typeofwork_code;
	private String typeofwork_name;
	
	//体检类别管理
	private String phyexaclassID;
	private String phyexaclass_code;
	private String phyexaclass_name;
	private String remark;
	private String isprintcard;
	private String isprintcard_s;
	private String center_num;
	private String isupload;
	private String isupload_s;
	private long showorder;
	
	/*//企业规模
	private String scaleID;
	private String scale_code;
	private String scale_name;*/
	

	public String getIsprintcard_s() {
		return isprintcard_s;
	}
	public void setIsprintcard_s(String isprintcard_s) {
		this.isprintcard_s = isprintcard_s;
	}
	public String getIsupload_s() {
		return isupload_s;
	}
	public void setIsupload_s(String isupload_s) {
		this.isupload_s = isupload_s;
	}
	public String getIsprintcard() {
		return isprintcard;
	}
	public void setIsprintcard(String isprintcard) {
		this.isprintcard = isprintcard;
		if("0".equals(this.isprintcard)){
			this.setIsprintcard_s("是");
		}else if("1".equals(this.isprintcard)){
			this.setIsprintcard_s("否");
		}
	}
	public String getIsupload() {
		return isupload;
	}
	public void setIsupload(String isupload) {
		this.isupload = isupload;
		if("0".equals(this.isupload)){
			this.setIsupload_s("是");
		}else if("1".equals(this.isupload)){
			this.setIsupload_s("否");
		}
		
	}
	public long  getShoworder() {
		return showorder;
	}
	public void setShoworder(long showorder) {
		this.showorder = showorder;
	}
	public String getPhyexaclassID() {
		return phyexaclassID;
	}
	public void setPhyexaclassID(String phyexaclassID) {
		this.phyexaclassID = phyexaclassID;
	}
	public String getPhyexaclass_code() {
		return phyexaclass_code;
	}
	public void setPhyexaclass_code(String phyexaclass_code) {
		this.phyexaclass_code = phyexaclass_code;
	}
	public String getPhyexaclass_name() {
		return phyexaclass_name;
	}
	public void setPhyexaclass_name(String phyexaclass_name) {
		this.phyexaclass_name = phyexaclass_name;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getCenter_num() {
		return center_num;
	}
	public void setCenter_num(String center_num) {
		this.center_num = center_num;
	}
	public String getTypeofworkID() {
		return typeofworkID;
	}
	public void setTypeofworkID(String typeofworkID) {
		this.typeofworkID = typeofworkID;
	}
	public String getTypeofwork_code() {
		return typeofwork_code;
	}
	public void setTypeofwork_code(String typeofwork_code) {
		this.typeofwork_code = typeofwork_code;
	}
	public String getTypeofwork_name() {
		return typeofwork_name;
	}
	public void setTypeofwork_name(String typeofwork_name) {
		this.typeofwork_name = typeofwork_name;
	}
	public String getIndustry_name_i() {
		return industry_name_i;
	}
	public void setIndustry_name_i(String industry_name_i) {
		this.industry_name_i = industry_name_i;
	}
	public String getIndustryID() {
		return industryID;
	}
	public void setIndustryID(String industryID) {
		this.industryID = industryID;
	}
	public String getIndustry_name_f() {
		return industry_name_f;
	}
	public void setIndustry_name_f(String industry_name_f) {
		this.industry_name_f = industry_name_f;
	}
	public String get_parentId() {
		return _parentId;
	}
	public void set_parentId(String _parentId) {
		this._parentId = _parentId;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	private List<ZybEconomicTreeDTO> children=new ArrayList<ZybEconomicTreeDTO>();
	
	public String getIndustry_code() {
		return industry_code;
	}
	public void setIndustry_code(String industry_code) {
		this.industry_code = industry_code;
	}
	public String getIndustry_name() {
		return industry_name;
	}
	public void setIndustry_name(String industry_name) {
		this.industry_name = industry_name;
	}
	
	public List<ZybEconomicTreeDTO> getChildren() {
		return children;
	}
	public void setChildren(List<ZybEconomicTreeDTO> children) {
		this.children = children;
	}	

}
