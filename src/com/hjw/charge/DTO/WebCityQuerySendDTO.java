package com.hjw.charge.DTO;


import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class WebCityQuerySendDTO implements java.io.Serializable{

	private static final long serialVersionUID = -97502163798576023L;
	private String  inter_class; //":"02",   //接口类型(01:银联POS通;02:市医保;03:省医保)
	private String  trade_code;   //":"1110",  //业务编码(1120:医疗费信息对帐)
	private long  operator_id;      //操作员ID
	private String  operator_code;    //操作员工号
	private String  operator_name;  //操作员姓名
	private String  center_cycle_code;  //对帐业务周期号
	private String  start_time;   ///20191027080000 //开始时间(日期格式：YYYYMMDDHH24MISS)
	private String  end_time;    //":"20191027172021//开始时间(日期格式：YYYYMMDDHH24MISS)
	public String getInter_class() {
		return inter_class;
	}
	public void setInter_class(String inter_class) {
		this.inter_class = inter_class;
	}
	public String getTrade_code() {
		return trade_code;
	}
	public void setTrade_code(String trade_code) {
		this.trade_code = trade_code;
	}
	public long getOperator_id() {
		return operator_id;
	}
	public void setOperator_id(long operator_id) {
		this.operator_id = operator_id;
	}
	public String getOperator_code() {
		return operator_code;
	}
	public void setOperator_code(String operator_code) {
		this.operator_code = operator_code;
	}
	public String getOperator_name() {
		return operator_name;
	}
	public void setOperator_name(String operator_name) {
		this.operator_name = operator_name;
	}
	public String getCenter_cycle_code() {
		return center_cycle_code;
	}
	public void setCenter_cycle_code(String center_cycle_code) {
		this.center_cycle_code = center_cycle_code;
	}
	public String getStart_time() {
		return start_time;
	}
	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}
	public String getEnd_time() {
		return end_time;
	}
	public void setEnd_time(String end_time) {
		this.end_time = end_time;
	}

}
	

