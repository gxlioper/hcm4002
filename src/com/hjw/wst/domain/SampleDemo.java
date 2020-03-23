package com.hjw.wst.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;


/**
 * 
     * @Title:  火箭蛙体检管理系统   
     * @Package com.hjw.wst.domain   
     * @Description: 检验样本 
     * @author: 张瑞    
     * @date:   2016年9月5日 上午9:30:37   
     * @version V2.0.0.0
 */
@Entity
@Table(name="sample_demo")
public class SampleDemo  implements java.io.Serializable{

	private static final long serialVersionUID = 4783399711232352000L;

	@Id
	@GenericGenerator(name = "idGenerator", strategy = "identity")
	@GeneratedValue(generator = "idGenerator")
	private long id;
	
	@Column(name = "demo_num")
	private String demo_num;
	
	@Column(name = "demo_name")
	private String demo_name;
	
	@Column(name = "demo_category")
	private String demo_category;
	
	@Column(name = "demo_indicator")
	private String demo_indicator;
	
	@Column(name = "demo_color")
	private String demo_color;
	
	@Column(name = "print_seq")
	private Long print_seq;
	
	@Column(name = "print_copy")
	private Long print_copy;
	
	@Column(name = "remark")
	private String remark;
	
	@Column(name = "isActive")
	private String isActive;
	
	@Column(name = "creater")
	private long creater;
	
	@Column(name = "create_time")
	private Date create_time;

	@Column(name = "updater")
	private Long updater;

	@Column(name = "update_time")
	private Date update_time;
	
	@Column(name = "isPrint_BarCode")
	private long isPrint_BarCode;//是否打印条码

	@Column(name = "BarCode_Class")
	private long BarCode_Class;//条码类型
	
	@Column(name = "print_dep")
	private long print_dep;//打印科室
	
	@Column(name = "demo_type")
	private int demo_type;
	
	@Column(name = "req_print_num")
	private int req_print_num;//打印申请单次数
	
	@Column(name = "isPrint_req")
	private int isPrint_req;//是否打印申请单
	
	public long getPrint_dep() {
		return print_dep;
	}

	public void setPrint_dep(long print_dep) {
		this.print_dep = print_dep;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getDemo_num() {
		return demo_num;
	}

	public void setDemo_num(String demo_num) {
		this.demo_num = demo_num;
	}

	public String getDemo_name() {
		return demo_name;
	}

	public void setDemo_name(String demo_name) {
		this.demo_name = demo_name;
	}

	public String getDemo_category() {
		return demo_category;
	}

	public void setDemo_category(String demo_category) {
		this.demo_category = demo_category;
	}

	public String getDemo_indicator() {
		return demo_indicator;
	}

	public void setDemo_indicator(String demo_indicator) {
		this.demo_indicator = demo_indicator;
	}

	public String getDemo_color() {
		return demo_color;
	}

	public void setDemo_color(String demo_color) {
		this.demo_color = demo_color;
	}

	public Long getPrint_seq() {
		return print_seq;
	}

	public void setPrint_seq(Long print_seq) {
		this.print_seq = print_seq;
	}

	public Long getPrint_copy() {
		return print_copy;
	}

	public void setPrint_copy(Long print_copy) {
		this.print_copy = print_copy;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}


	public Date getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}


	public Date getUpdate_time() {
		return update_time;
	}

	public void setUpdate_time(Date update_time) {
		this.update_time = update_time;
	}

	public long getCreater() {
		return creater;
	}

	public void setCreater(long creater) {
		this.creater = creater;
	}

	public long getUpdater() {
		return updater;
	}

	public void setUpdater(long updater) {
		this.updater = updater;
	}

	public long getIsPrint_BarCode() {
		return isPrint_BarCode;
	}

	public void setIsPrint_BarCode(long isPrint_BarCode) {
		this.isPrint_BarCode = isPrint_BarCode;
	}

	public long getBarCode() {
		return BarCode_Class;
	}

	public void setBarCode(long BarCode_Class) {
		this.BarCode_Class = BarCode_Class;
	}

	public int getDemo_type() {
		return demo_type;
	}

	public void setDemo_type(int demo_type) {
		this.demo_type = demo_type;
	}

	public int getReq_print_num() {
		return req_print_num;
	}

	public void setReq_print_num(int req_print_num) {
		this.req_print_num = req_print_num;
	}

	public int getIsPrint_req() {
		return isPrint_req;
	}

	public void setIsPrint_req(int isPrint_req) {
		this.isPrint_req = isPrint_req;
	}

	
}
