package com.hjw.wst.DTO;

import java.util.Date;

public class HisClinicItemPriceListDTO {
	private long id;
	private double damount;
	//诊疗表
	private String item_id_c;  //项目编号
	private String item_class_c;  //项目分类
	private String item_class_cs;//诊疗项目分类sss
	private String item_code_c; //诊疗项目代码
	private String item_name_c; //诊疗项目名称
	private String input_code_c; //输入码 
	private String expand1;    //检验标本
	private String expand2;    //检验类别
	private String expand3;	   //检验科室
	private String item_status;    //项目状态
	//价表
	private String item_class_p; 	//项目分类
	private String item_code_p;	   //项目代码
	private String item_name_p;	    //项目名称
	private String item_spec;	    //项目规格
	private String units;	      //单位
	private Double price;	        //价格
	private Double prefer_price; 	//优惠价格
	private String performed_by;	//执行科室
	private String input_code_p;     //输入码
	private String class_on_inp_rcpt; //对应的住院收据费用分类
	private String class_on_outp_rcpt; //对应的门诊收据费用分类
	private String class_on_reckoning;  //对应的核算项目分类
	private String subj_code;	  //对应的会计科目
	private String memo;	 //备注
	private Date start_date; 	//起用日期
	private Date stop_date; 	//停用日期
	private String is_active;
	private String hisCode_class="";
	private String item_class_name;
	private String body_part="";
	private String method="";
	
	public String getItem_class_name() {
		return item_class_name;
	}
	public void setItem_class_name(String item_class_name) {
		this.item_class_name = item_class_name;
	}
	public String getItem_id_c() {
		return item_id_c;
	}
	public void setItem_id_c(String item_id_c) {
		this.item_id_c = item_id_c;
	}
	public String getHisCode_class() {
		return hisCode_class;
	}
	public void setHisCode_class(String hisCode_class) {
		this.hisCode_class = hisCode_class;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public double getDamount() {
		return damount;
	}
	public void setDamount(double damount) {
		this.damount = damount;
	}
	public String getIs_active() {
		return is_active;
	}
	public void setIs_active(String is_active) {
		this.is_active = is_active;
	}
	//诊疗项目与价表项目对应关系
	private String clinic_item_class;	//临床诊疗项目类别
	private String clinic_item_code;//临床诊疗项目代码
	private Integer charge_item_no;//对应价表项目序号
	private String charge_item_class;//对应价表项目分类
	private String charge_item_code;//对应价表项目代码
	private String charge_item_spec;//对应价表项目规格
	private Integer amount;//对应价表项目数量
	private String backbill_rule;//对应的计价规则
	
	private String systemdate;//系统时间
	
	
	public String getItem_class_cs() {
		return item_class_cs;
	}
	public void setItem_class_cs(String item_class_cs) {
		this.item_class_cs = item_class_cs;
	}
	public String getSystemdate() {
		return systemdate;
	}
	public void setSystemdate(String systemdate) {
		this.systemdate = systemdate;
	}
	public String getItem_spec() {
		return item_spec;
	}
	public void setItem_spec(String item_spec) {
		this.item_spec = item_spec;
	}
	public String getInput_code_p() {
		return input_code_p;
	}
	public void setInput_code_p(String input_code_p) {
		this.input_code_p = input_code_p;
	}
	public String getItem_class_c() {
		return item_class_c;
	}
	public void setItem_class_c(String item_class_c) {
		this.item_class_c = item_class_c;
		if(item_class_c.equals("A")){
			this.setItem_class_cs("西药");
		}else if(item_class_c.equals("B")){
			this.setItem_class_cs("中药");
		}else if(item_class_c.equals("C")){
			this.setItem_class_cs("检验");
		}else if(item_class_c.equals("D")){
			this.setItem_class_cs("检查");
		}else if(item_class_c.equals("E")){
			this.setItem_class_cs("治疗");
		}else if(item_class_c.equals("F")){
			this.setItem_class_cs("手术");
		}else if(item_class_c.equals("G")){
			this.setItem_class_cs("麻醉");
		}else if(item_class_c.equals("H")){
			this.setItem_class_cs("护理");
		}else if(item_class_c.equals("I")){
			this.setItem_class_cs("膳食");
		}else{
			this.setItem_class_cs("其他");
		}
	}
	public String getItem_code_c() {
		return item_code_c;
	}
	public void setItem_code_c(String item_code_c) {
		this.item_code_c = item_code_c;
	}
	public String getItem_name_c() {
		return item_name_c;
	}
	public void setItem_name_c(String item_name_c) {
		this.item_name_c = item_name_c;
	}
	public String getInput_code_c() {
		return input_code_c;
	}
	public void setInput_code_c(String input_code_c) {
		this.input_code_c = input_code_c;
	}
	public String getExpand1() {
		return expand1;
	}
	public void setExpand1(String expand1) {
		this.expand1 = expand1;
	}
	public String getExpand2() {
		return expand2;
	}
	public void setExpand2(String expand2) {
		this.expand2 = expand2;
	}
	public String getExpand3() {
		return expand3;
	}
	public void setExpand3(String expand3) {
		this.expand3 = expand3;
	}
	public String getItem_status() {
		return item_status;
	}
	public void setItem_status(String item_status) {
		this.item_status = item_status;
	}
	public String getItem_class_p() {
		return item_class_p;
	}
	public void setItem_class_p(String item_class_p) {
		this.item_class_p = item_class_p;
	}
	public String getItem_code_p() {
		return item_code_p;
	}
	public void setItem_code_p(String item_code_p) {
		this.item_code_p = item_code_p;
	}
	public String getItem_name_p() {
		return item_name_p;
	}
	public void setItem_name_p(String item_name_p) {
		this.item_name_p = item_name_p;
	}
	public String getUnits() {
		return units;
	}
	public void setUnits(String units) {
		this.units = units;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public Double getPrefer_price() {
		return prefer_price;
	}
	public void setPrefer_price(Double prefer_price) {
		this.prefer_price = prefer_price;
	}
	public String getPerformed_by() {
		return performed_by;
	}
	public void setPerformed_by(String performed_by) {
		this.performed_by = performed_by;
	}
	public String getClass_on_inp_rcpt() {
		return class_on_inp_rcpt;
	}
	public void setClass_on_inp_rcpt(String class_on_inp_rcpt) {
		this.class_on_inp_rcpt = class_on_inp_rcpt;
	}
	public String getClass_on_outp_rcpt() {
		return class_on_outp_rcpt;
	}
	public void setClass_on_outp_rcpt(String class_on_outp_rcpt) {
		this.class_on_outp_rcpt = class_on_outp_rcpt;
	}
	public String getClass_on_reckoning() {
		return class_on_reckoning;
	}
	public void setClass_on_reckoning(String class_on_reckoning) {
		this.class_on_reckoning = class_on_reckoning;
	}
	public String getSubj_code() {
		return subj_code;
	}
	public void setSubj_code(String subj_code) {
		this.subj_code = subj_code;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public Date getStart_date() {
		return start_date;
	}
	public void setStart_date(Date start_date) {
		this.start_date = start_date;
	}
	public Date getStop_date() {
		return stop_date;
	}
	public void setStop_date(Date stop_date) {
		this.stop_date = stop_date;
	}
	public String getClinic_item_class() {
		return clinic_item_class;
	}
	public void setClinic_item_class(String clinic_item_class) {
		this.clinic_item_class = clinic_item_class;
	}
	public String getClinic_item_code() {
		return clinic_item_code;
	}
	public void setClinic_item_code(String clinic_item_code) {
		this.clinic_item_code = clinic_item_code;
	}
	public Integer getCharge_item_no() {
		return charge_item_no;
	}
	public void setCharge_item_no(Integer charge_item_no) {
		this.charge_item_no = charge_item_no;
	}
	public String getCharge_item_class() {
		return charge_item_class;
	}
	public void setCharge_item_class(String charge_item_class) {
		this.charge_item_class = charge_item_class;
	}
	public String getCharge_item_code() {
		return charge_item_code;
	}
	public void setCharge_item_code(String charge_item_code) {
		this.charge_item_code = charge_item_code;
	}
	public String getCharge_item_spec() {
		return charge_item_spec;
	}
	public void setCharge_item_spec(String charge_item_spec) {
		this.charge_item_spec = charge_item_spec;
	}
	public Integer getAmount() {
		return amount;
	}
	public void setAmount(Integer amount) {
		this.amount = amount;
	}
	public String getBackbill_rule() {
		return backbill_rule;
	}
	public void setBackbill_rule(String backbill_rule) {
		this.backbill_rule = backbill_rule;
	}
	public String getBody_part() {
		return body_part;
	}
	public String getMethod() {
		return method;
	}
	public void setBody_part(String body_part) {
		this.body_part = body_part;
	}
	public void setMethod(String method) {
		this.method = method;
	}
}
