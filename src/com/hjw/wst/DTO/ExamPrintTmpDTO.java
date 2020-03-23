package com.hjw.wst.DTO;

import java.util.Date;

public class ExamPrintTmpDTO implements java.io.Serializable {
	private static final long serialVersionUID = -97502163798576023L;
	
	private long id;
	private String sessionId;
	private long userId;
	private String exam_num;
	private String print_type;//打印类型(G:导检单；B：条码; R:报告; T:团报; GB:导检单及条码打印)
	private String print_flag;//打印状态(N:未打印；Y:已打印)
	private String charging_item_codes;//打印项目编码(用于导检单补打、打印指定条码)
	private long bar_calss;//打印条码时写入(1:只打印常用条码；2:只打印档案条码;3:以上两种都打印)
	private long arch_bar_num;//档案条码打印数量
	private Date create_time;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public String getExam_num() {
		return exam_num;
	}
	public void setExam_num(String exam_num) {
		this.exam_num = exam_num;
	}
	public String getPrint_type() {
		return print_type;
	}
	public void setPrint_type(String print_type) {
		this.print_type = print_type;
	}
	public String getPrint_flag() {
		return print_flag;
	}
	public void setPrint_flag(String print_flag) {
		this.print_flag = print_flag;
	}
	public String getCharging_item_codes() {
		return charging_item_codes;
	}
	public void setCharging_item_codes(String charging_item_codes) {
		this.charging_item_codes = charging_item_codes;
	}
	public long getBar_calss() {
		return bar_calss;
	}
	public void setBar_calss(long bar_calss) {
		this.bar_calss = bar_calss;
	}
	public long getArch_bar_num() {
		return arch_bar_num;
	}
	public void setArch_bar_num(long arch_bar_num) {
		this.arch_bar_num = arch_bar_num;
	}
	public Date getCreate_time() {
		return create_time;
	}
	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}
}
