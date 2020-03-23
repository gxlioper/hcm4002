package com.hjw.Bean;

import java.util.ArrayList;
import java.util.List;

public class BatchBean implements java.io.Serializable {
	 private static final long serialVersionUID = -97502163798576023L;

		private long id;

		private long synid;
		
		private long company_id;

		private String batch_num;

	    private String batch_name;

	    private String pay_way;

	    private long creater;

	    private String create_time;
	    
	    private String create_times;

		private long updater;
	    
	    private String update_times;

	    private String update_time;

	    private String is_Active;
	    
	    private String is_Actives;

	    private String exam_item;

	    private long exam_number;

	    private String exam_date;
	    
	    private String exam_date_end;

	    private String charge_type;

	    private String contact_name;

	    private String sales_name;

	    private String introducer_name;

	    private String accommodation;

	    private String dine;

	    private String exam_fee;

	    private String remark;

	    private String phone;

	    private String invoice_title;

	    private String batch_address;

	    private String qian_remark;
	    
	    private String settlement;
	    
	    private String data_name;  
	    
	    private int checktype;
	    
	    private String checktypes;
	    
	    private long checkuser;
	    
	    private String checkdate;
	    
	    private String checknotice; 
	    
	    private String overflag;
	    
	    private String overflags; 
	    
	    private String sign_num;
	    private String sign_name;
	    private String com_name;
	    private String name;
	    private String flag;
	    private String apptype;
	    private String apptypes;
	    
	    private List<GroupInfoBean> listgroup = new ArrayList<GroupInfoBean>();	    
	    
		public List<GroupInfoBean> getListgroup() {
			return listgroup;
		}

		public void setListgroup(List<GroupInfoBean> listgroup) {
			this.listgroup = listgroup;
		}

		public long getSynid() {
			return synid;
		}

		public void setSynid(long synid) {
			this.synid = synid;
		}

		public String getApptype() {
			return apptype;
		}

		public void setApptype(String apptype) {
			this.apptype = apptype;
			if("1".equals(apptype)){
				this.apptypes = "健康体检";
			}else if("2".equals(apptype)){
				this.apptypes = "职业病体检";
			}
		}

		public String getApptypes() {
			return apptypes;
		}

		public void setApptypes(String apptypes) {
			this.apptypes = apptypes;
		}

		public String getFlag() {
			return flag;
		}
	    private String cwcheckstatus;
	    private String cwcheckstatuss;
	    
	    private String sjcheckstatus;
	    private String sjcheckstatuss;
	    
	    private String yscheckstatus;
	    private String yscheckstatuss;

		public void setFlag(String flag) {
			this.flag = flag;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getCom_name() {
			return com_name;
		}

		public void setCom_name(String com_name) {
			this.com_name = com_name;
		}

		public String getSign_name() {
			return sign_name;
		}

		public void setSign_name(String sign_name) {
			this.sign_name = sign_name;
		}

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

		public String getOverflag() {
			return overflag;
		}

		public void setOverflag(String overflag) {
			this.overflag = overflag;
			if("1".equals(overflag)){
				this.setOverflags("已封帐");
			}else if("0".equals(overflag)){
				this.setOverflags("未封帐");
			}
		}

		public String getOverflags() {
			return overflags;
		}

		public void setOverflags(String overflags) {
			this.overflags = overflags;
		}

		public int getChecktype() {
			return checktype;
		}

		public void setChecktype(int checktype) {
			this.checktype = checktype;
			if(checktype==0){
				this.setChecktypes("未审核");
			}else if(checktype==1){
				this.setChecktypes("审核未通过");
			}else if(checktype==2){
				this.setChecktypes("审核通过");
			}else{
				this.setChecktypes("未审核");
			}
		}		

		public String getChecktypes() {
			return checktypes;
		}

		public void setChecktypes(String checktypes) {
			this.checktypes = checktypes;
		}

		public long getCheckuser() {
			return checkuser;
		}

		public void setCheckuser(long checkuser) {
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

		public String getIs_Actives() {
			return is_Actives;
		}

		public void setIs_Actives(String is_Actives) {
			this.is_Actives = is_Actives;
		}

		public String getData_name() {
			return data_name;
		}

		public void setData_name(String data_name) {
			this.data_name = data_name;
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

		public String getCreate_time() {
			return create_time;
		}

		public void setCreate_time(String create_time) {
			this.create_time = create_time;
			if((this.create_time!=null)&&(this.create_time.trim().length()>=10)){
				this.setCreate_times(this.create_time.trim().substring(0, 10));
			}
		}

		public long getUpdater() {
			return updater;
		}

		public void setUpdater(long updater) {
			this.updater = updater;
			
		}

		public String getUpdate_time() {
			return update_time;
			
		}

		public void setUpdate_time(String update_time) {
			this.update_time = update_time;
			if((this.update_time!=null)&&(this.update_time.trim().length()>=10)){
				this.setUpdate_times(this.update_time.trim().substring(0, 10));
			}
			
		}

		public String getCreate_times() {
			return create_times;
			
		}

		public void setCreate_times(String create_times) {
			this.create_times = create_times;
		}

		public String getUpdate_times() {
			return update_times;
		}

		public void setUpdate_times(String update_times) {
			this.update_times = update_times;
		}
		
		public String getIs_Active() {
			return is_Active;
		}

		public void setIs_Active(String is_Active) {
			this.is_Active = is_Active;
			if("Y".equals(is_Active)){
				this.setIs_Actives("已启用");
			}else if("C".equals(is_Active)){
				this.setIs_Actives("未提交审核");
			}else if("T".equals(is_Active)){
				this.setIs_Actives("已提交审核");
			}else{
				this.setIs_Actives("无效");
			}
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

		public String getCwcheckstatus() {
			return cwcheckstatus;
		}

		public void setCwcheckstatus(String cwcheckstatus) {
			this.cwcheckstatus = cwcheckstatus;
			if("0".equals(cwcheckstatus)){
				this.cwcheckstatuss = "未审核";
			}else if("1".equals(cwcheckstatus)){
				this.cwcheckstatuss = "审核未通过";
			}else if("2".equals(cwcheckstatus)){
				this.cwcheckstatuss = "审核通过";
			}
		}

		public String getCwcheckstatuss() {
			return cwcheckstatuss;
		}

		public void setCwcheckstatuss(String cwcheckstatuss) {
			this.cwcheckstatuss = cwcheckstatuss;
		}

		public String getSjcheckstatus() {
			return sjcheckstatus;
		}

		public void setSjcheckstatus(String sjcheckstatus) {
			this.sjcheckstatus = sjcheckstatus;
			if("0".equals(sjcheckstatus)){
				this.sjcheckstatuss = "未审核";
			}else if("1".equals(sjcheckstatus)){
				this.sjcheckstatuss = "审核未通过";
			}else if("2".equals(sjcheckstatus)){
				this.sjcheckstatuss = "审核通过";
			}
		}

		public String getSjcheckstatuss() {
			return sjcheckstatuss;
		}

		public void setSjcheckstatuss(String sjcheckstatuss) {
			this.sjcheckstatuss = sjcheckstatuss;
		}

		public String getYscheckstatus() {
			return yscheckstatus;
		}

		public void setYscheckstatus(String yscheckstatus) {
			this.yscheckstatus = yscheckstatus;
			if("0".equals(yscheckstatus)){
				this.yscheckstatuss = "未审核";
			}else if("1".equals(yscheckstatus)){
				this.yscheckstatuss = "审核未通过";
			}else if("2".equals(yscheckstatus)){
				this.yscheckstatuss = "审核通过";
			}
		}

		public String getYscheckstatuss() {
			return yscheckstatuss;
		}

		public void setYscheckstatuss(String yscheckstatuss) {
			this.yscheckstatuss = yscheckstatuss;
		}

	}