package com.hjw.wst.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "batch")
public class Batch implements java.io.Serializable {
	 private static final long serialVersionUID = -97502163798576023L;
		@Id
		@GenericGenerator(name = "idGenerator", strategy = "identity")
		@GeneratedValue(generator = "idGenerator")
		private long id;

	    @Column(name = "company_id")
		private long company_id;

	    @Column(name = "batch_num")
		private String batch_num;

	    @Column(name = "batch_name")
	    private String batch_name;
	    
	    @Column(name = "pay_way")
	    private String pay_way;
	    
	    @Column(name = "creater")
	    private long creater;
	    
	    @Column(name = "create_time")
	    private Date create_time;
	    
	    @Column(name = "updater")
	    private long updater;
	    
	    @Column(name = "update_time")
	    private Date update_time;
	    
	    @Column(name = "is_Active")
	    private String is_Active;
	    
	    @Column(name = "exam_item")
	    private String exam_item;
	    
	    @Column(name = "exam_number")
	    private long exam_number;
	    
	    @Column(name = "exam_date")
	    private String exam_date;
	    
	    @Column(name = "exam_date_end")
	    private String exam_date_end;
	    
	    @Column(name = "charge_type")
	    private String charge_type;
	    
	    @Column(name = "contact_name")
	    private String contact_name;
	    
	    @Column(name = "sales_name")
	    private String sales_name;
	    
	    @Column(name = "introducer_name")
	    private String introducer_name;
	    
	    @Column(name = "accommodation")
	    private String accommodation;
	    
	    @Column(name = "dine")
	    private String dine;
	    
	    @Column(name = "exam_fee")
	    private String exam_fee;
	    
	    @Column(name = "remark")
	    private String remark;
	    
	    @Column(name = "phone")
	    private String phone;
	    
	    @Column(name = "invoice_title")
	    private String invoice_title;
	    
	    @Column(name = "batch_address")
	    private String batch_address;
	    
	    @Column(name = "qian_remark")
	    private String qian_remark;
	    
	    @Column(name = "settlement")
	    private String settlement;
	    
	    @Column(name = "checktype")
	    private int checktype;
	    
	    @Column(name = "checkuser")
	    private Long checkuser;
	    
	    @Column(name = "checkdate")
	    private String checkdate;
	    
	    @Column(name = "checknotice")
	    private String checknotice;	  
	    
	    @Column(name = "overflag")
	    private String overflag="0"; //0表示未封帐，1表示已经封帐   
	    
	    @Column(name = "apptype")
	    private String apptype="1";	//对应应用  
	    @Column(name = "sign_num")
	    private String sign_num;
	    
	    @Column(name = "is_showamount")
	    private int is_showamount;//导检单是否显示金额 0 不显示，1显示
	    
	    @Column(name = "is_showseal")
	    private int is_showseal;//导检单是否显示印章0 不显示，1显示	
	    
	    @Column(name = "accountcreater")
	    private long accountcreater;//操作员id
	    
	    @Column(name = "accountdate")
	    private String accountdate;//操作时间
	    
	    @Column(name = "overcreater")
	    private long overcreater;//操作员id
	    
	    @Column(name = "overdate")
	    private String overdate;//操作时间
	    
	    @Column(name = "accountflag")
	    private int accountflag=0;//0未锁定 1锁定
	    
	    @Column(name = "report_sms_notice")
	    private int report_sms_notice;//批次领取报告短信通知标志， 0表示不通知， 1表示通知
	    
	    @Column(name = "center_num")
	    private String center_num;//操作时间
		public String getSign_num() {
			return sign_num;
		}

		public void setSign_num(String sign_num) {
			this.sign_num = sign_num;
		}

		public String getExam_date_end() {
			return exam_date_end;
		}

		public void setExam_date_end(String exam_date_end) {
			this.exam_date_end = exam_date_end;
		}

		public String getApptype() {
			return apptype;
		}

		public void setApptype(String apptype) {
			this.apptype = apptype;
		}

		public String getOverflag() {
			return overflag;
		}

		public void setOverflag(String overflag) {
			this.overflag = overflag;
		}

		public int getChecktype() {
			return checktype;
		}

		public void setChecktype(int checktype) {
			this.checktype = checktype;
		}

		public Long getCheckuser() {
			return checkuser;
		}

		public void setCheckuser(Long checkuser) {
			this.checkuser = checkuser;
		}

		public String getCheckdate() {
			return checkdate;
		}

		public void setCheckdate(String checkdate) {
			this.checkdate = checkdate;
		}

		public String getChecknotice() {
			return checknotice;
		}

		public void setChecknotice(String checknotice) {
			this.checknotice = checknotice;
		}

		public long getId() {
			return id;
		}

		public void setId(long id) {
			this.id = id;
		}

		public long getCompany_id() {
			return company_id;
		}

		public void setCompany_id(long company_id) {
			this.company_id = company_id;
		}

		public String getBatch_num() {
			return batch_num;
		}

		public void setBatch_num(String batch_num) {
			this.batch_num = batch_num;
		}

		public String getBatch_name() {
			return batch_name;
		}

		public void setBatch_name(String batch_name) {
			this.batch_name = batch_name;
		}

		public String getPay_way() {
			return pay_way;
		}

		public void setPay_way(String pay_way) {
			this.pay_way = pay_way;
		}

		public long getCreater() {
			return creater;
		}

		public void setCreater(long creater) {
			this.creater = creater;
		}

		public Date getCreate_time() {
			return create_time;
		}

		public void setCreate_time(Date create_time) {
			this.create_time = create_time;
		}

		public long getUpdater() {
			return updater;
		}

		public void setUpdater(long updater) {
			this.updater = updater;
		}

		public Date getUpdate_time() {
			return update_time;
		}

		public void setUpdate_time(Date update_time) {
			this.update_time = update_time;
		}

		public String getIs_Active() {
			return is_Active;
		}

		public void setIs_Active(String is_Active) {
			this.is_Active = is_Active;
		}

		public String getExam_item() {
			return exam_item;
		}

		public void setExam_item(String exam_item) {
			this.exam_item = exam_item;
		}

		public long getExam_number() {
			return exam_number;
		}

		public void setExam_number(long exam_number) {
			this.exam_number = exam_number;
		}

		public String getExam_date() {
			return exam_date;
		}

		public void setExam_date(String exam_date) {
			this.exam_date = exam_date;
		}

		public String getCharge_type() {
			return charge_type;
		}

		public void setCharge_type(String charge_type) {
			this.charge_type = charge_type;
		}

		public String getContact_name() {
			return contact_name;
		}

		public void setContact_name(String contact_name) {
			this.contact_name = contact_name;
		}

		public String getSales_name() {
			return sales_name;
		}

		public void setSales_name(String sales_name) {
			this.sales_name = sales_name;
		}

		public String getIntroducer_name() {
			return introducer_name;
		}

		public void setIntroducer_name(String introducer_name) {
			this.introducer_name = introducer_name;
		}

		public String getAccommodation() {
			return accommodation;
		}

		public void setAccommodation(String accommodation) {
			this.accommodation = accommodation;
		}

		public String getDine() {
			return dine;
		}

		public void setDine(String dine) {
			this.dine = dine;
		}

		public String getExam_fee() {
			return exam_fee;
		}

		public void setExam_fee(String exam_fee) {
			this.exam_fee = exam_fee;
		}

		public String getRemark() {
			return remark;
		}

		public void setRemark(String remark) {
			this.remark = remark;
		}

		public String getPhone() {
			return phone;
		}

		public void setPhone(String phone) {
			this.phone = phone;
		}

		public String getInvoice_title() {
			return invoice_title;
		}

		public void setInvoice_title(String invoice_title) {
			this.invoice_title = invoice_title;
		}

		public String getBatch_address() {
			return batch_address;
		}

		public void setBatch_address(String batch_address) {
			this.batch_address = batch_address;
		}

		public String getQian_remark() {
			return qian_remark;
		}

		public void setQian_remark(String qian_remark) {
			this.qian_remark = qian_remark;
		}

		public String getSettlement() {
			return settlement;
		}

		public void setSettlement(String settlement) {
			this.settlement = settlement;
		}

		public int getIs_showamount() {
			return is_showamount;
		}

		public int getIs_showseal() {
			return is_showseal;
		}

		public long getAccountcreater() {
			return accountcreater;
		}

		public String getAccountdate() {
			return accountdate;
		}

		public long getOvercreater() {
			return overcreater;
		}

		public String getOverdate() {
			return overdate;
		}

		public int getAccountflag() {
			return accountflag;
		}

		public int getReport_sms_notice() {
			return report_sms_notice;
		}

		public void setIs_showamount(int is_showamount) {
			this.is_showamount = is_showamount;
		}

		public void setIs_showseal(int is_showseal) {
			this.is_showseal = is_showseal;
		}

		public void setAccountcreater(long accountcreater) {
			this.accountcreater = accountcreater;
		}

		public void setAccountdate(String accountdate) {
			this.accountdate = accountdate;
		}

		public void setOvercreater(long overcreater) {
			this.overcreater = overcreater;
		}

		public void setOverdate(String overdate) {
			this.overdate = overdate;
		}

		public void setAccountflag(int accountflag) {
			this.accountflag = accountflag;
		}

		public void setReport_sms_notice(int report_sms_notice) {
			this.report_sms_notice = report_sms_notice;
		}

		public String getCenter_num() {
			return center_num;
		}

		public void setCenter_num(String center_num) {
			this.center_num = center_num;
		}

	}