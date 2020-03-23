package com.hjw.zyb.model;



public class ZybOccuModel implements java.io.Serializable{
	
	private static final long serialVersionUID = -97502163798576023L;
	
	
	
    //经济行业属性
	private String parentID;
    private String industryID;
    private String id;
    private String industry_code;
    private String industry_name;
    private String _parentId;
    private String industry_name_f;
    private String industry_name_i;
   
    //从业工种属性
    private String typeofworkID;
    private String typeofwork_code;
    private String typeofwork_name;
    private String ids;
    //行政区域属性
    private String areacode_codeID;
    private String areacode_code;
    private String areacode_name;
    // private String parentID;
    private String adminarea_code;
    
    //体检类别管理
    private String phyexaclassID;
	private String phyexaclass_code;
	private String phyexaclass_name;
	private String remark;
	private String isprintcard;
	private String center_num;
	private String isupload;
	private long showorder;
	
/*	//企业规模
	private String scaleID;
	private String scale_code;
	private String scale_name;
	
	*/
  
	public long getShoworder() {
		return showorder;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public String getParentID() {
		return parentID;
	}

	public void setParentID(String parentID) {
		this.parentID = parentID;
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

	public String getIsprintcard() {
		return isprintcard;
	}

	public void setIsprintcard(String isprintcard) {
		this.isprintcard = isprintcard;
	}

	public String getCenter_num() {
		return center_num;
	}

	public void setCenter_num(String center_num) {
		this.center_num = center_num;
	}

	public String getIsupload() {
		return isupload;
	}

	public void setIsupload(String isupload) {
		this.isupload = isupload;
	}

	public String getIndustry_name_i() {
		return industry_name_i;
	}

	public void setIndustry_name_i(String industry_name_i) {
		this.industry_name_i = industry_name_i;
	}

	public String getIndustry_name_f() {
		return industry_name_f;
	}

	public void setIndustry_name_f(String industry_name_f) {
		this.industry_name_f = industry_name_f;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String get_parentId() {
		return _parentId;
	}

	public void set_parentId(String _parentId) {
		this._parentId = _parentId;
	}

	
	public String getIndustryID() {
		return industryID;
	}

	public void setIndustryID(String industryID) {
		this.industryID = industryID;
	}

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

	public String getAreacode_codeID() {
		return areacode_codeID;
	}

	public void setAreacode_codeID(String areacode_codeID) {
		this.areacode_codeID = areacode_codeID;
	}

	public String getAreacode_code() {
		return areacode_code;
	}

	public void setAreacode_code(String areacode_code) {
		this.areacode_code = areacode_code;
	}

	public String getAreacode_name() {
		return areacode_name;
	}

	public void setAreacode_name(String areacode_name) {
		this.areacode_name = areacode_name;
	}

	public String getAdminarea_code() {
		return adminarea_code;
	}

	public void setAdminarea_code(String adminarea_code) {
		this.adminarea_code = adminarea_code;
	}

	public ZybOccuModel() {
		// TODO Auto-generated constructor stub
	}
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
