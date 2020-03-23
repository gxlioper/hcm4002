package com.hjw.wst.model;

import java.util.ArrayList;
import java.util.List;

import com.hjw.wst.DTO.BatchProPlanDTO;
import com.hjw.wst.DTO.GroupChargingItemDTO;
import com.hjw.wst.DTO.GroupInfoDTO;
import com.hjw.wst.DTO.GroupSetDTO;
import com.hjw.wst.DTO.YdExamInfoDTO;

public class BatchModel implements java.io.Serializable {
	 private static final long serialVersionUID = -97502163798576023L;

		private long id;
		
		private String ids="";

		private long company_id;

		private String batch_num;

	    private String batch_name;

	    private String pay_way;

	    private long creater;
	    
	    private String creaters;

	    private String create_time;

	    private long updater;
	    
	    private String updaters;

	    private String update_time;

	    private String is_Active;

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
	    
	    private String comname;
	    
	    private long batch_id;   
	    
	    private String group_num;

		private String group_name;

		private String start_date;

		private String end_date;
		
		private String sex;

		private int min_age;

		private int max_age;

		private String is_Marriage;

		private String posttion;

		private double discount;

		private double amount;

		private String group_index;

		private double person_team_amount;

		private String group_settlement_type;	
		
		private long group_id;		
		
		private String setname;	
		
		private long exam_set_id;	
		
		private String set_num;
		
		private double item_amount;//项目总金额 打折前	
		
		private String itementities;
		
		private String setentities;		
		
		private String exam_indicator;	
		
        private int checktype;
	    
	    private String checkuser;
	    
	    private String checkdate;
	    
	    private String checknotice; 
	    private String exam_num="";
	    
	    private String contract_num;//合同编号
	    private String apptype;
	    private String sign_num;
	    private String check_type;
	    private String check_status;
	    private String sign_nums;
	    private long company_ids;
	    private String sign_name;
	    private String tijiantype;
	    private String webResource;
	    private int is_show_discount;
	    private String status;
	    private int isaccounttype;
	    private int isunaccounttype;

	    private List<GroupSetDTO> groupSetList = new ArrayList<GroupSetDTO>();//套餐 中文名称
		
		private List<GroupInfoDTO>	groupItemList= new ArrayList<GroupInfoDTO>();
		
		private List<GroupChargingItemDTO> itemList= new ArrayList<GroupChargingItemDTO>();		
		
		private List<BatchProPlanDTO> bppList = new ArrayList<BatchProPlanDTO>();
		
		private List<YdExamInfoDTO> addItemList= new ArrayList<YdExamInfoDTO>();
		
		private List<YdExamInfoDTO> delItemList= new ArrayList<YdExamInfoDTO>();
		
		private List<YdExamInfoDTO> qjItemList= new ArrayList<YdExamInfoDTO>();		
		
		public String getExam_num() {
			return exam_num;
		}

		public void setExam_num(String exam_num) {
			this.exam_num = exam_num;
		}

		public int getIs_show_discount() {
			return is_show_discount;
		}

		public void setIs_show_discount(int is_show_discount) {
			this.is_show_discount = is_show_discount;
		}

		public String getWebResource() {
			return webResource;
		}

		public void setWebResource(String webResource) {
			this.webResource = webResource;
		}

		public String getTijiantype() {
			return tijiantype;
		}

		public void setTijiantype(String tijiantype) {
			this.tijiantype = tijiantype;
		}

		public String getSign_name() {
			return sign_name;
		}

		public void setSign_name(String sign_name) {
			this.sign_name = sign_name;
		}

		public long getCompany_ids() {
			return company_ids;
		}

		public void setCompany_ids(long company_ids) {
			this.company_ids = company_ids;
		}

		public String getSign_nums() {
			return sign_nums;
		}

		public void setSign_nums(String sign_nums) {
			this.sign_nums = sign_nums;
		}

		private String name_pinyin;	
		
		private long dep_id;
		
		private String cust_type_id;
		
		private String app_type="1";		

		private int is_showamount;//导检单是否显示金额 0 不显示，1显示	    
	    private int is_showseal;//导检单是否显示印章0 不显示，1显示	 
	    
	    private int group_order;//分组顺序
	    
	    private double maximum_amount; //分组最大金额限制
		
	    private int isgroupitemflag=0;//在修改分组时,是否对已分组的预约人员同步项目	    
	    
	    private String center_num;
		public int getIsgroupitemflag() {
			return isgroupitemflag;
		}

		public void setIsgroupitemflag(int isgroupitemflag) {
			this.isgroupitemflag = isgroupitemflag;
		}
		
		public String getCheck_type() {
			return check_type;
		}

		public void setCheck_type(String check_type) {
			this.check_type = check_type;
		}

		public String getCheck_status() {
			return check_status;
		}

		public void setCheck_status(String check_status) {
			this.check_status = check_status;
		}

		public String getSign_num() {
			return sign_num;
		}

		public void setSign_num(String sign_num) {
			this.sign_num = sign_num;
		}

		public String getApptype() {
			return apptype;
		}

		public void setApptype(String apptype) {
			this.apptype = apptype;
		}

		public String getApp_type() {
			return app_type;
		}

		public void setApp_type(String app_type) {
			this.app_type = app_type;
		}

		public String getCust_type_id() {
			return cust_type_id;
		}

		public void setCust_type_id(String cust_type_id) {
			this.cust_type_id = cust_type_id;
		}

		public String getExam_date_end() {
			return exam_date_end;
		}

		public void setExam_date_end(String exam_date_end) {
			this.exam_date_end = exam_date_end;
		}

		public String getIds() {
			return ids;
		}

		public void setIds(String ids) {
			this.ids = ids;
		}

		public long getDep_id() {
			return dep_id;
		}

		public void setDep_id(long dep_id) {
			this.dep_id = dep_id;
		}

		public String getName_pinyin() {
			return name_pinyin;
		}

		public void setName_pinyin(String name_pinyin) {
			this.name_pinyin = name_pinyin;
		}
		
		public String getContract_num() {
			return contract_num;
		}

		public void setContract_num(String contract_num) {
			this.contract_num = contract_num;
		}

		public int getChecktype() {
			return checktype;
		}

		public void setChecktype(int checktype) {
			this.checktype = checktype;
		}

		public String getCheckuser() {
			return checkuser;
		}

		public void setCheckuser(String checkuser) {
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

		public List<BatchProPlanDTO> getBppList() {
			return bppList;
		}

		public void setBppList(List<BatchProPlanDTO> bppList) {
			this.bppList = bppList;
		}

		public List<GroupSetDTO> getGroupSetList() {
			return groupSetList;
		}

		public void setGroupSetList(List<GroupSetDTO> groupSetList) {
			this.groupSetList = groupSetList;
		}

		public List<GroupInfoDTO> getGroupItemList() {
			return groupItemList;
		}

		public void setGroupItemList(List<GroupInfoDTO> groupItemList) {
			this.groupItemList = groupItemList;
		}

		public List<GroupChargingItemDTO> getItemList() {
			return itemList;
		}

		public void setItemList(List<GroupChargingItemDTO> itemList) {
			this.itemList = itemList;
		}

		public String getExam_indicator() {
			return exam_indicator;
		}

		public void setExam_indicator(String exam_indicator) {
			this.exam_indicator = exam_indicator;
		}

		public String getItementities() {
			return itementities;
		}

		public void setItementities(String itementities) {
			this.itementities = itementities;
		}

		public String getSetentities() {
			return setentities;
		}

		public void setSetentities(String setentities) {
			this.setentities = setentities;
		}

		public double getItem_amount() {
			return item_amount;
		}

		public void setItem_amount(double item_amount) {
			this.item_amount = item_amount;
		}

		public String getSet_num() {
			return set_num;
		}

		public void setSet_num(String set_num) {
			this.set_num = set_num;
		}

		public long getExam_set_id() {
			return exam_set_id;
		}

		public void setExam_set_id(long exam_set_id) {
			this.exam_set_id = exam_set_id;
		}

		public String getSetname() {
			return setname;
		}

		public void setSetname(String setname) {
			this.setname = setname;
		}

		public long getGroup_id() {
			return group_id;
		}

		public void setGroup_id(long group_id) {
			this.group_id = group_id;
		}

		public String getGroup_num() {
			return group_num;
		}

		public void setGroup_num(String group_num) {
			this.group_num = group_num;
		}

		public String getGroup_name() {
			return group_name;
		}

		public void setGroup_name(String group_name) {
			this.group_name = group_name;
		}

		public String getStart_date() {
			return start_date;
		}

		public void setStart_date(String start_date) {
			this.start_date = start_date;
		}

		public String getEnd_date() {
			return end_date;
		}

		public void setEnd_date(String end_date) {
			this.end_date = end_date;
		}

		public String getSex() {
			return sex;
		}

		public void setSex(String sex) {
			this.sex = sex;
		}

		public int getMin_age() {
			return min_age;
		}

		public void setMin_age(int min_age) {
			this.min_age = min_age;
		}

		public int getMax_age() {
			return max_age;
		}

		public void setMax_age(int max_age) {
			this.max_age = max_age;
		}

		public String getIs_Marriage() {
			return is_Marriage;
		}

		public void setIs_Marriage(String is_Marriage) {
			this.is_Marriage = is_Marriage;
		}

		public String getPosttion() {
			return posttion;
		}

		public void setPosttion(String posttion) {
			this.posttion = posttion;
		}

		public double getDiscount() {
			return discount;
		}

		public void setDiscount(double discount) {
			this.discount = discount;
		}

		public double getAmount() {
			return amount;
		}

		public void setAmount(double amount) {
			this.amount = amount;
		}

		public String getGroup_index() {
			return group_index;
		}

		public void setGroup_index(String group_index) {
			this.group_index = group_index;
		}

		public double getPerson_team_amount() {
			return person_team_amount;
		}

		public void setPerson_team_amount(double person_team_amount) {
			this.person_team_amount = person_team_amount;
		}

		public String getGroup_settlement_type() {
			return group_settlement_type;
		}

		public void setGroup_settlement_type(String group_settlement_type) {
			this.group_settlement_type = group_settlement_type;
		}

		public long getBatch_id() {
			return batch_id;
		}

		public void setBatch_id(long batch_id) {
			this.batch_id = batch_id;
		}

		public String getCreaters() {
			return creaters;
		}

		public void setCreaters(String creaters) {
			this.creaters = creaters;
		}

		public String getUpdaters() {
			return updaters;
		}

		public void setUpdaters(String updaters) {
			this.updaters = updaters;
		}

		public String getComname() {
			return comname;
		}

		public void setComname(String comname) {
			this.comname = comname;
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

		public List<YdExamInfoDTO> getAddItemList() {
			return addItemList;
		}

		public List<YdExamInfoDTO> getDelItemList() {
			return delItemList;
		}

		public List<YdExamInfoDTO> getQjItemList() {
			return qjItemList;
		}

		public int getIs_showamount() {
			return is_showamount;
		}

		public int getIs_showseal() {
			return is_showseal;
		}

		public int getGroup_order() {
			return group_order;
		}

		public double getMaximum_amount() {
			return maximum_amount;
		}

		public void setAddItemList(List<YdExamInfoDTO> addItemList) {
			this.addItemList = addItemList;
		}

		public void setDelItemList(List<YdExamInfoDTO> delItemList) {
			this.delItemList = delItemList;
		}

		public void setQjItemList(List<YdExamInfoDTO> qjItemList) {
			this.qjItemList = qjItemList;
		}

		public void setIs_showamount(int is_showamount) {
			this.is_showamount = is_showamount;
		}

		public void setIs_showseal(int is_showseal) {
			this.is_showseal = is_showseal;
		}

		public void setGroup_order(int group_order) {
			this.group_order = group_order;
		}

		public void setMaximum_amount(double maximum_amount) {
			this.maximum_amount = maximum_amount;
		}

		public String getStatus() {
			return status;
		}

		public void setStatus(String status) {
			this.status = status;
		}

		public String getCenter_num() {
			return center_num;
		}

		public void setCenter_num(String center_num) {
			this.center_num = center_num;
		}

	public int getIsaccounttype() {
		return isaccounttype;
	}

	public void setIsaccounttype(int isaccounttype) {
		this.isaccounttype = isaccounttype;
	}

	public int getIsunaccounttype() {
		return isunaccounttype;
	}

	public void setIsunaccounttype(int isunaccounttype) {
		this.isunaccounttype = isunaccounttype;
	}
}