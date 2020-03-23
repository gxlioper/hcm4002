package com.hjw.wst.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 打印程序临时表
     * @Title:  火箭蛙体检管理系统   
     * @Package com.hjw.wst.domain   
     * @Description:  
     * @author: yangm     
     * @date:   2018年1月8日 下午1:53:39   
     * @version V2.0.0.0
 */
@Entity
@Table(name = "exam_print_tmp")
public class ExamPrintTmp implements java.io.Serializable {
	private static final long serialVersionUID = -97502163798576023L;
	@Id
	@GenericGenerator(name = "idGenerator", strategy = "identity")
	@GeneratedValue(generator = "idGenerator")
	private long id;
	
	@Column(name="SessionId")
	private String sessionId;
	
	@Column(name="UserId")
	private long userId;
	
	@Column(name="exam_num")
	private String exam_num;
	
	@Column(name="print_type")
	private String print_type;//打印类型(G:导检单；B：条码; R:报告; T:团报; GB:导检单及条码打印)
	
	@Column(name="print_flag")
	private String print_flag;//打印状态(N:未打印；Y:已打印)
	
	@Column(name="charging_item_codes")
	private String charging_item_codes;//打印项目编码(用于导检单补打、打印指定条码)
	
	@Column(name="bar_class")
	private long bar_class;//打印条码时写入(1:只打印常用条码；2:只打印档案条码;3:以上两种都打印)
	
	@Column(name="arch_bar_num")
	private long arch_bar_num;//档案条码打印数量
	
	@Column(name="create_time")
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

	public long getBar_class() {
		return bar_class;
	}

	public void setBar_class(long bar_class) {
		this.bar_class = bar_class;
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
