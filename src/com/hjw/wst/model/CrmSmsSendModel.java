package com.hjw.wst.model;


/**
 * 短信发送记录表
     * @Title:  火箭蛙体检管理系统   
     * @Package com.hjw.wst.domain   
     * @Description:  
     * @author: zr     
     * @date:   2018年1月16日 上午10:41:20   
     * @version V2.0.0.0
 */
public class CrmSmsSendModel {
	
	private String id;
	 
    private String template_id;
    private String arch_num;
	 
    private String 	sms_note;
	 
    private String 	sms_phone;
	 
    private String sms_date;
	 
    private long   send_user;
	 
    private String	sms_status;
	 
    private Number	sms_amount;
	 
    private String   sms_type;
	 
    private long user_id;
	 
    private String sms_time;
	 
    private String user_type;
	 
    private String sms_batch;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTemplate_id() {
		return template_id;
	}

	public void setTemplate_id(String template_id) {
		this.template_id = template_id;
	}

	public String getArch_num() {
		return arch_num;
	}

	public void setArch_num(String arch_num) {
		this.arch_num = arch_num;
	}

	public String getSms_note() {
		return sms_note;
	}

	public void setSms_note(String sms_note) {
		this.sms_note = sms_note;
	}

	public String getSms_phone() {
		return sms_phone;
	}

	public void setSms_phone(String sms_phone) {
		this.sms_phone = sms_phone;
	}

	public String getSms_date() {
		return sms_date;
	}

	public void setSms_date(String sms_date) {
		this.sms_date = sms_date;
	}

	public long getSend_user() {
		return send_user;
	}

	public void setSend_user(long send_user) {
		this.send_user = send_user;
	}

	public String getSms_status() {
		return sms_status;
	}

	public void setSms_status(String sms_status) {
		this.sms_status = sms_status;
	}

	public Number getSms_amount() {
		return sms_amount;
	}

	public void setSms_amount(Number sms_amount) {
		this.sms_amount = sms_amount;
	}

	public String getSms_type() {
		return sms_type;
	}

	public void setSms_type(String sms_type) {
		this.sms_type = sms_type;
	}

	public long getUser_id() {
		return user_id;
	}

	public void setUser_id(long user_id) {
		this.user_id = user_id;
	}

	public String getSms_time() {
		return sms_time;
	}

	public void setSms_time(String sms_time) {
		this.sms_time = sms_time;
	}

	public String getUser_type() {
		return user_type;
	}

	public void setUser_type(String user_type) {
		this.user_type = user_type;
	}

	public String getSms_batch() {
		return sms_batch;
	}

	public void setSms_batch(String sms_batch) {
		this.sms_batch = sms_batch;
	}
    
    
	
}
