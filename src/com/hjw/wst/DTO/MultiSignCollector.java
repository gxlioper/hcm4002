package com.hjw.wst.DTO;

public class MultiSignCollector {
	private String bgRemark;
	///////////////////////////公共信息/////////////////////////////////
	private String dn="";//DN00401******", 设备号
	private String tag="";//被测者标识 对应exam_num "123456",   
	private String tag2="";//测量者标识
	private Long timestamp;//测试时间戳 1442406526615, 
	private String signType="";//测量体征类型": 血压=bp,血糖 bg，血氧 spo2 体温 temp
		
    ///////////////////////////血压/////////////////////////////////
	private Integer map;//设备号 90,  
	private Integer heartrate;//心率": 93,  和血氧公用一个字段 
	private Integer diastolic;//舒张压 76,   
	private Integer systolic;//收缩压": 105,  
	private String pulserate;//收缩压": 105,  
	
    ///////////////////////////血糖/////////////////////////////////
	private Float glucose;//血糖测量值": 3.4, 
	private String glucosePeriod;//血糖测量时间 随机，无用
	private Integer glucoseCode;//血糖矫正码": 200,   
	
///////////////////////////血氧/////////////////////////////////
	private Float oximetryPI;//血氧关注指数 2.125,   
	private Float oximetry;//血氧测量值": 98.2,   
	
///////////////////////////体温/////////////////////////////////
    private Float temperature;//体温": 35.6,     
    
    private String id;
       
    private String usercode;//": null,       
    private String temperatureCW;//无用 
    private String temperatureMP;//无用": null,    
    private String refuse;//无用": null,   
    private String specifyTime;//": 1442484000000 
    
    private String insertstamp;
    private String returnMsg;
    private String spo2Remark;   
    private String stoolMsg;
    private String tempRemark;    
    
	public String getPulserate() {
		return pulserate;
	}
	public void setPulserate(String pulserate) {
		this.pulserate = pulserate;
	}
	public String getTempRemark() {
		return tempRemark;
	}
	public void setTempRemark(String tempRemark) {
		this.tempRemark = tempRemark;
	}
	public String getStoolMsg() {
		return stoolMsg;
	}
	public void setStoolMsg(String stoolMsg) {
		this.stoolMsg = stoolMsg;
	}
	public String getSpo2Remark() {
		return spo2Remark;
	}
	public void setSpo2Remark(String spo2Remark) {
		this.spo2Remark = spo2Remark;
	}
	public String getReturnMsg() {
		return returnMsg;
	}
	public void setReturnMsg(String returnMsg) {
		this.returnMsg = returnMsg;
	}
	public String getInsertstamp() {
		return insertstamp;
	}
	public void setInsertstamp(String insertstamp) {
		this.insertstamp = insertstamp;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getBgRemark() {
		return bgRemark;
	}
	public void setBgRemark(String bgRemark) {
		this.bgRemark = bgRemark;
	}
	public String getDn() {
		return dn;
	}
	public void setDn(String dn) {
		this.dn = dn;
	}
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	public String getTag2() {
		return tag2;
	}
	public void setTag2(String tag2) {
		this.tag2 = tag2;
	}
	public Long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}
	public String getSignType() {
		return signType;
	}
	public void setSignType(String signType) {
		this.signType = signType;
	}
	public Integer getMap() {
		return map;
	}
	public void setMap(Integer map) {
		this.map = map;
	}
	public Integer getHeartrate() {
		return heartrate;
	}
	public void setHeartrate(Integer heartrate) {
		this.heartrate = heartrate;
	}
	public Integer getDiastolic() {
		return diastolic;
	}
	public void setDiastolic(Integer diastolic) {
		this.diastolic = diastolic;
	}
	public Integer getSystolic() {
		return systolic;
	}
	public void setSystolic(Integer systolic) {
		this.systolic = systolic;
	}
	public Float getGlucose() {
		return glucose;
	}
	public void setGlucose(Float glucose) {
		this.glucose = glucose;
	}
	public String getGlucosePeriod() {
		return glucosePeriod;
	}
	public void setGlucosePeriod(String glucosePeriod) {
		this.glucosePeriod = glucosePeriod;
	}
	public Integer getGlucoseCode() {
		return glucoseCode;
	}
	public void setGlucoseCode(Integer glucoseCode) {
		this.glucoseCode = glucoseCode;
	}
	public Float getOximetryPI() {
		return oximetryPI;
	}
	public void setOximetryPI(Float oximetryPI) {
		this.oximetryPI = oximetryPI;
	}
	public Float getOximetry() {
		return oximetry;
	}
	public void setOximetry(Float oximetry) {
		this.oximetry = oximetry;
	}
	public Float getTemperature() {
		return temperature;
	}
	public void setTemperature(Float temperature) {
		this.temperature = temperature;
	}
	public String getUsercode() {
		return usercode;
	}
	public void setUsercode(String usercode) {
		this.usercode = usercode;
	}
	public String getTemperatureCW() {
		return temperatureCW;
	}
	public void setTemperatureCW(String temperatureCW) {
		this.temperatureCW = temperatureCW;
	}
	public String getTemperatureMP() {
		return temperatureMP;
	}
	public void setTemperatureMP(String temperatureMP) {
		this.temperatureMP = temperatureMP;
	}
	public String getRefuse() {
		return refuse;
	}
	public void setRefuse(String refuse) {
		this.refuse = refuse;
	}
	public String getSpecifyTime() {
		return specifyTime;
	}
	public void setSpecifyTime(String specifyTime) {
		this.specifyTime = specifyTime;
	}
    
    
}
