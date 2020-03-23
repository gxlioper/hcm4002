package com.hjw.Bean.ZJ;

public class RawDataMsgt {
	private String raw_data="";//":"BASE64编码的原始数据",
	private String datatype="";//":数据类型,1 jpg图像,2   wav声音,3  dicom文件,4 ecg心电
	private String purpose="";//":数据用途 1 B超,2 DR,3 CT ,4 核磁,5 心电,6 检验科  
	
	public String getRaw_data() {
		return raw_data;
	}
	public void setRaw_data(String raw_data) {
		this.raw_data = raw_data;
	}
	public String getDatatype() {
		return datatype;
	}
	public void setDatatype(String datatype) {
		this.datatype = datatype;
	}
	public String getPurpose() {
		return purpose;
	}
	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}
	
	

}
