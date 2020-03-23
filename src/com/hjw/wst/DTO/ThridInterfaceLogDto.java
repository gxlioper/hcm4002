package com.hjw.wst.DTO;

public class ThridInterfaceLogDto implements java.io.Serializable {
	
	/**   
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)   
	 */ 
	private static final long serialVersionUID = 1L;
	private String id;
	private String req_no;
	private String exam_no;
	private String message_id;
	private String message_name;
	private String message_date;
	private String message_type;
	private Integer flag;
	private String sender;
	private String receiver;
	private String message_request;
	private String message_response;
	
	private String sys_request;
	private String sys_respones;
	private String xtgnb_id;
	private Integer message_inout; //调用方式
	
	private String til_id;
	private String ldate;
	private String lmessage;
	
	private String config_key;
	private String config_url;
	private String config_method;
	private String config_remark;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getReq_no() {
		return req_no;
	}
	public void setReq_no(String req_no) {
		this.req_no = req_no;
	}
	public String getExam_no() {
		return exam_no;
	}
	public void setExam_no(String exam_no) {
		this.exam_no = exam_no;
	}
	public String getMessage_id() {
		return message_id;
	}
	public void setMessage_id(String message_id) {
		this.message_id = message_id;
	}
	public String getMessage_name() {
		return message_name;
	}
	public void setMessage_name(String message_name) {
		this.message_name = message_name;
	}
	public String getMessage_date() {
		return message_date;
	}
	public void setMessage_date(String message_date) {
		this.message_date = message_date;
	}
	public String getMessage_type() {
		return message_type;
	}
	public void setMessage_type(String message_type) {
		this.message_type = message_type;
	}
	public Integer getFlag() {
		return flag;
	}
	public void setFlag(Integer flag) {
		this.flag = flag;
	}
	public String getSender() {
		return sender;
	}
	public void setSender(String sender) {
		this.sender = sender;
	}
	public String getReceiver() {
		return receiver;
	}
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}
	public String getMessage_request() {
		return message_request;
	}
	public void setMessage_request(String message_request) {
		this.message_request = message_request;
	}
	public String getMessage_response() {
		return message_response;
	}
	public void setMessage_response(String message_response) {
		this.message_response = message_response;
	}
	public String getSys_request() {
		return sys_request;
	}
	public void setSys_request(String sys_request) {
		this.sys_request = sys_request;
	}
	public String getSys_respones() {
		return sys_respones;
	}
	public void setSys_respones(String sys_respones) {
		this.sys_respones = sys_respones;
	}
	public String getXtgnb_id() {
		return xtgnb_id;
	}
	public void setXtgnb_id(String xtgnb_id) {
		this.xtgnb_id = xtgnb_id;
	}
	public Integer getMessage_inout() {
		return message_inout;
	}
	public void setMessage_inout(Integer message_inout) {
		this.message_inout = message_inout;
	}
	public String getTil_id() {
		return til_id;
	}
	public void setTil_id(String til_id) {
		this.til_id = til_id;
	}
	public String getLdate() {
		return ldate;
	}
	public void setLdate(String ldate) {
		this.ldate = ldate;
	}
	public String getLmessage() {
		return lmessage;
	}
	public void setLmessage(String lmessage) {
		this.lmessage = lmessage;
	}
	public String getConfig_key() {
		return config_key;
	}
	public void setConfig_key(String config_key) {
		this.config_key = config_key;
	}
	public String getConfig_url() {
		return config_url;
	}
	public void setConfig_url(String config_url) {
		this.config_url = config_url;
	}
	public String getConfig_method() {
		return config_method;
	}
	public void setConfig_method(String config_method) {
		this.config_method = config_method;
	}
	public String getConfig_remark() {
		return config_remark;
	}
	public void setConfig_remark(String config_remark) {
		this.config_remark = config_remark;
	}
	
	

}
