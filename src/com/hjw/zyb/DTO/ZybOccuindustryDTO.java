package com.hjw.zyb.DTO;





public class ZybOccuindustryDTO {
	
	//从业行业属性
	private String industryID;
	private String industry_code;
	private String industry_name;
	private String center_num;
	private String scriptID;
	private int phyexeperiod;
	private int trainperiod;
	private int  packageID;
	private int showorder;
	private String exam_set_code;
	private String package_name;
	
	//从业工种属性
	private String typeofworkID;
	private String typeofwork_code;
	private String typeofwork_name;
	
	//检查依据属性
	private String criterionID;
	private String criterion_name;
	private int DISORDER;
	private String RID;
	
	private String hazard_name;
	private String occuphyexaclass_name;
	
	
	public String getHazard_name() {
		return hazard_name;
	}
	public void setHazard_name(String hazard_name) {
		this.hazard_name = hazard_name;
	}
	public String getOccuphyexaclass_name() {
		return occuphyexaclass_name;
	}
	public void setOccuphyexaclass_name(String occuphyexaclass_name) {
		this.occuphyexaclass_name = occuphyexaclass_name;
	}
	public String getRID() {
		return RID;
	}
	public void setRID(String rID) {
		RID = rID;
	}
	public int getDISORDER() {
		return DISORDER;
	}
	public void setDISORDER(int dISORDER) {
		DISORDER = dISORDER;
	}
	public String getCriterionID() {
		return criterionID;
	}
	public void setCriterionID(String criterionID) {
		this.criterionID = criterionID;
	}
	public String getCriterion_name() {
		return criterion_name;
	}
	public void setCriterion_name(String criterion_name) {
		this.criterion_name = criterion_name;
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
	public String getPackage_name() {
		return package_name;
	}
	public void setPackage_name(String package_name) {
		this.package_name = package_name;
	}
	private String set_name;
	private int  s_id;
	
	public String getSet_name() {
		return set_name;
	}
	public void setSet_name(String set_name) {
		this.set_name = set_name;
	}

	public int getS_id() {
		return s_id;
	}
	public void setS_id(int s_id) {
		this.s_id = s_id;
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
	public String getCenter_num() {
		return center_num;
	}
	public void setCenter_num(String center_num) {
		this.center_num = center_num;
	}
	public String getScriptID() {
		return scriptID;
	}
	public void setScriptID(String scriptID) {
		this.scriptID = scriptID;
	}
	public int getPhyexeperiod() {
		return phyexeperiod;
	}
	public void setPhyexeperiod(int phyexeperiod) {
		this.phyexeperiod = phyexeperiod;
	}
	public int getTrainperiod() {
		return trainperiod;
	}
	public void setTrainperiod(int trainperiod) {
		this.trainperiod = trainperiod;
	}
	public int getPackageID() {
		return packageID;
	}
	public void setPackageID(int packageID) {
		this.packageID = packageID;
	}
	public int getShoworder() {
		return showorder;
	}
	public void setShoworder(int showorder) {
		this.showorder = showorder;
	}
	public String getExam_set_code() {
		return exam_set_code;
	}
	public void setExam_set_code(String exam_set_code) {
		this.exam_set_code = exam_set_code;
	}
	
}
