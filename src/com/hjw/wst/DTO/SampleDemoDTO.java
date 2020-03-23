package com.hjw.wst.DTO;

/**
 * 
 * @Title: 火箭蛙体检管理系统
 * @Package com.hjw.wst.domain
 * @Description: 检验样本
 * @author: 张瑞
 * @date: 2016年9月5日 上午9:30:37
 * @version V2.0.0.0
 */
public class SampleDemoDTO implements  java.io.Serializable{

	private static final long serialVersionUID = -7143589775378909537L;

	private Long id;

	private String demo_num;

	private String demo_name;

	private String demo_category;
	
	private String demo_indicator;

	private String dataname;

	private String demo_color;

	private Long print_seq;

	private Long print_copy;

	private String remark;

	private String isActive;

	private String creatername;

	private String create_time;

	private String updataname;

	private String update_time;
	
	private long isPrint_BarCode;//打印条码1打印；0不打印
	
	private String isPrint_BarCodes;//要显示的打印条码
	
	private long BarCode_Class;//条码类型
	
	private String BarCode_Classs;//要显示的条码类型

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
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

	public String getDataname() {
		return dataname;
	}

	public void setDataname(String dataname) {
		this.dataname = dataname;
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

	public String getCreatername() {
		return creatername;
	}

	public void setCreatername(String creatername) {
		this.creatername = creatername;
	}

	public String getCreate_time() {
		return create_time;
	}

	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}

	public String getUpdataname() {
		return updataname;
	}

	public void setUpdataname(String updataname) {
		this.updataname = updataname;
	}

	public String getUpdate_time() {
		return update_time;
	}

	public void setUpdate_time(String update_time) {
		this.update_time = update_time;
	}

	public long getIsPrint_BarCode() {
		return isPrint_BarCode;
	}

	public void setIsPrint_BarCode(long isPrint_BarCode) {
		this.isPrint_BarCode = isPrint_BarCode;
		if(this.isPrint_BarCode==1){
			this.setIsPrint_BarCodes("是");
		}else{
			this.setIsPrint_BarCodes("否");
		}
	}

	public String getIsPrint_BarCodes() {
		return isPrint_BarCodes;
	}

	public void setIsPrint_BarCodes(String isPrint_BarCodes) {
		this.isPrint_BarCodes = isPrint_BarCodes;
	}

	public long getBarCode_Class() {
		return BarCode_Class;
	}

	public void setBarCode_Class(long barCode_Class) {
		BarCode_Class = barCode_Class;
		if(barCode_Class==1){
			this.setBarCode_Classs("预印条码");
		}else if(barCode_Class==2){
			this.setBarCode_Classs("其他条码");
		}else{
			this.setBarCode_Classs("系统条码");
		}
	}

	public String getBarCode_Classs() {
		return BarCode_Classs;
	}

	public void setBarCode_Classs(String barCode_Classs) {
		BarCode_Classs = barCode_Classs;
	}
	

}
