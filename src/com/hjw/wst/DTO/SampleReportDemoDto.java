package com.hjw.wst.DTO;

/**
 * 
     * @Title:  火箭蛙体检管理系统   
     * @Package com.hjw.wst.DTO   
     * @Description:  报告样本 
     * @author: 张瑞     
     * @date:   2016年8月25日 上午10:59:30   
     * @version V2.0.0.0
 */
public class SampleReportDemoDto implements java.io.Serializable{
	private static final long serialVersionUID = -1485855914726265709L;

	private long id;
	
	private String demo_name;
	
	private String demo_num;
	
	private Long print_seq;
	
	private String remark;
	
	private String is_Active;
	
	private String chi_name;
	
	private String create_time;
	
	private String updatename;
	
	private String update_time;
	
	private Long isPrint_BarCode;//是否打印条码0是/1否
	
	private String isPrint_BarCodes;//转换后的打印条码
	
	private long BarCode_Class;//条码类型   默认：0）、预印条码（1）、其他条码（2）
	
	private String barCodes;//要显示的打印类型

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getDemo_name() {
		return demo_name;
	}

	public void setDemo_name(String demo_name) {
		this.demo_name = demo_name;
	}

	public String getDemo_num() {
		return demo_num;
	}

	public void setDemo_num(String demo_num) {
		this.demo_num = demo_num;
	}

	public Long getPrint_seq() {
		return print_seq;
	}

	public void setPrint_seq(Long print_seq) {
		this.print_seq = print_seq;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getIs_Active() {
		return is_Active;
	}

	public void setIs_Active(String is_Active) {
		this.is_Active = is_Active;
	}

	public String getChi_name() {
		return chi_name;
	}

	public void setChi_name(String chi_name) {
		this.chi_name = chi_name;
	}

	public String getCreate_time() {
		return create_time;
	}

	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}

	public String getUpdatename() {
		return updatename;
	}

	public void setUpdatename(String updatename) {
		this.updatename = updatename;
	}

	public String getUpdate_time() {
		return update_time;
	}

	public void setUpdate_time(String update_time) {
		this.update_time = update_time;
	}

	public Long getIsPrint_BarCode() {
		return isPrint_BarCode;
	}

	public void setIsPrint_BarCode(Long isPrint_BarCode) {
		this.isPrint_BarCode=isPrint_BarCode;
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

	public long getBarCode() {
		return BarCode_Class;
	}

	public void setBarCode(long BarCode_Class) {
		this.BarCode_Class = BarCode_Class;
		if(this.BarCode_Class==0){
			this.setBarCodes("系统条码");
		}else if(this.BarCode_Class==1){
			this.setBarCodes("预印条码");
		}else{
			this.setBarCodes("其他条码");
		}
	}

	public String getBarCodes() {
		return this.barCodes;
	}

	public void setBarCodes(String barCodes) {
		this.barCodes = barCodes;
	}
	
}
