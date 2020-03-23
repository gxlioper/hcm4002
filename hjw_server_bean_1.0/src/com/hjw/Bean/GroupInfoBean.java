package com.hjw.Bean;

import java.util.ArrayList;
import java.util.List;

public class GroupInfoBean implements java.io.Serializable {
	private static final long serialVersionUID = -97502163798576023L;
	private long id;

	private long batch_id;

	private String group_num;

	private String group_name;

	private String start_date;

	private String end_date;
	
	private String start_dates;

	private String end_dates;

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

	private long creater;

	private String create_time;
	
	private String create_times;

	private long updater;

	private String update_time;
	
	private String update_times;

	private String isActive;
	
	private String isActives;
	
    private String exam_indicator;//付费方式

    private String exam_indicators;   
    
    private String itemnames;
    
    private String type_name;
    
    private String cust_type_id="";
    
    private long exam_number;
    
    private double tamt;
    
    private double gamt;
    
    private List<GroupSetBean> listset = new ArrayList<GroupSetBean>();
    private List<GroupChargingItemBean> listitem = new ArrayList<GroupChargingItemBean>();   	
    
	public List<GroupSetBean> getListset() {
		return listset;
	}

	public void setListset(List<GroupSetBean> listset) {
		this.listset = listset;
	}

	public List<GroupChargingItemBean> getListitem() {
		return listitem;
	}

	public void setListitem(List<GroupChargingItemBean> listitem) {
		this.listitem = listitem;
	}

	public long getExam_number() {
		return exam_number;
	}

	public void setExam_number(long exam_number) {
		this.exam_number = exam_number;
	}

	public double getTamt() {
		return tamt;
	}

	public void setTamt(double tamt) {
		this.tamt = tamt;
	}

	public double getGamt() {
		return gamt;
	}

	public void setGamt(double gamt) {
		this.gamt = gamt;
	}

	public String getType_name() {
		return type_name;
	}

	public void setType_name(String type_name) {
		this.type_name = type_name;
	}

	public String getCust_type_id() {
		return cust_type_id;
	}

	public void setCust_type_id(String cust_type_id) {
		this.cust_type_id = cust_type_id;
	}

	public String getItemnames() {
		return itemnames;
	}

	public void setItemnames(String itemnames) {
		this.itemnames = itemnames;
	}

	public String getExam_indicators() {
		return exam_indicators;
	}

	public void setExam_indicators(String exam_indicators) {
		this.exam_indicators = exam_indicators;
	}

	public String getExam_indicator() {
		return exam_indicator;
	}

	public void setExam_indicator(String exam_indicator) {
		this.exam_indicator = exam_indicator;
		if("T".equals(exam_indicator)){
			this.setExam_indicators("团体结算");
		}else if("G".equals(exam_indicator)){
			this.setExam_indicators("个人结算");
		}else if("GT".equals(exam_indicator)){
			this.setExam_indicators("混合结算");
		}else if("TG".equals(exam_indicator)){
			this.setExam_indicators("混合结算");
		}else{
			this.setIsActives("无效");
		}
	}

	public long getBatch_id() {
		return batch_id;
	}

	public void setBatch_id(long batch_id) {
		this.batch_id = batch_id;
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
		if((this.start_date!=null)&&(this.start_date.trim().length()>=10)){
			this.setStart_dates(this.start_date.trim().substring(0, 10));
		}
	}

	public String getEnd_date() {
		return end_date;
	}

	public void setEnd_date(String end_date) {
		this.end_date = end_date;
		if((this.end_date!=null)&&(this.end_date.trim().length()>=10)){
			this.setEnd_dates(this.end_date.trim().substring(0, 10));
		}
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
		this.update_time = update_time;
		if((this.update_time!=null)&&(this.update_time.trim().length()>=10)){
			this.setUpdate_times(this.update_time.trim().substring(0, 10));
		}
	}

	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
		if("Y".equals(isActive)){
			this.setIsActives("有效");
		}else{
			this.setIsActives("无效");
		}
	}	

	public String getIsActives() {
		return isActives;
	}

	public void setIsActives(String isActives) {
		this.isActives = isActives;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getStart_dates() {
		return start_dates;
	}

	public void setStart_dates(String start_dates) {
		this.start_dates = start_dates;
	}

	public String getEnd_dates() {
		return end_dates;
	}

	public void setEnd_dates(String end_dates) {
		this.end_dates = end_dates;
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


}