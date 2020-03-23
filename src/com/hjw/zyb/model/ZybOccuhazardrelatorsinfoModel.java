package com.hjw.zyb.model;


/***
 * 
 * @Title: 火箭蛙体检管理系统
 * @Package com.hjw.zyb.domain
 * @Description: 职业危害类别管理
 * @author: zr
 * @date: 2017年3月29日 下午4:45:32
 * @version V2.0.0.0
 */
public class ZybOccuhazardrelatorsinfoModel implements java.io.Serializable {

	/**
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	 */
	private static final long serialVersionUID = 1L;

	private String id;
	private String hazardfactorsID;//职业危害因素id
	private String occuphyexaclassID;//职业体检类别id
	private String hazardclassID;//职业危害类别
	private String diseaseandtaboo;//目标疾病/职业禁忌症
	private String checkcontent;//检查内容
	private String checkcriterion;//检查依据
	private String followdisease;//疾病跟踪
	private String examperiod;//体检周期
	private String remark;//备注
	private String occuphyexaclass_name;//职业体检类别名称
	private String hazard_name;//职业危害名称
	private String hazardclass_name;//职业危害类别
	private String ids;
	private String whlb;
	
	
	public String getWhlb() {
		return whlb;
	}
	public void setWhlb(String whlb) {
		this.whlb = whlb;
	}
	public String getIds() {
		return ids;
	}
	public void setIds(String ids) {
		this.ids = ids;
	}
	public String getHazardclassID() {
		return hazardclassID;
	}
	public void setHazardclassID(String hazardclassID) {
		this.hazardclassID = hazardclassID;
	}
	public String getHazardclass_name() {
		return hazardclass_name;
	}
	public void setHazardclass_name(String hazardclass_name) {
		this.hazardclass_name = hazardclass_name;
	}
	public String getOccuphyexaclassID() {
		return occuphyexaclassID;
	}
	public void setOccuphyexaclassID(String occuphyexaclassID) {
		this.occuphyexaclassID = occuphyexaclassID;
	}
	public ZybOccuhazardrelatorsinfoModel() {
		// TODO Auto-generated constructor stub
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getHazardfactorsID() {
		return hazardfactorsID;
	}
	public void setHazardfactorsID(String hazardfactorsID) {
		this.hazardfactorsID = hazardfactorsID;
	}
	public String getDiseaseandtaboo() {
		return diseaseandtaboo;
	}
	public void setDiseaseandtaboo(String diseaseandtaboo) {
		this.diseaseandtaboo = diseaseandtaboo;
	}
	public String getCheckcontent() {
		return checkcontent;
	}
	public void setCheckcontent(String checkcontent) {
		this.checkcontent = checkcontent;
	}
	public String getCheckcriterion() {
		return checkcriterion;
	}
	public void setCheckcriterion(String checkcriterion) {
		this.checkcriterion = checkcriterion;
	}
	public String getFollowdisease() {
		return followdisease;
	}
	public void setFollowdisease(String followdisease) {
		this.followdisease = followdisease;
	}
	public String getExamperiod() {
		return examperiod;
	}
	public void setExamperiod(String examperiod) {
		this.examperiod = examperiod;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getOccuphyexaclass_name() {
		return occuphyexaclass_name;
	}
	public void setOccuphyexaclass_name(String occuphyexaclass_name) {
		this.occuphyexaclass_name = occuphyexaclass_name;
	}
	public String getHazard_name() {
		return hazard_name;
	}
	public void setHazard_name(String hazard_name) {
		this.hazard_name = hazard_name;
	}
	
}
