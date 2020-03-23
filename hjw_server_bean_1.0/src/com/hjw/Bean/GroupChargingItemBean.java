package com.hjw.Bean;

public class GroupChargingItemBean implements java.io.Serializable {
	 private static final long serialVersionUID = -97502163798576023L;

		private long id;

		private long charge_item_id;
		
		private long group_id;
		
		private double item_amount;

		private double discount;
		
		private double amount;
		
		private String isActive;
		
		private String final_exam_date;
		
		private long creater;
		
		private String create_time;
		
		private long updater;
		
		private String update_time;
		
        private long dep_id;
		
		private long sam_demo_id;
		
		private long sam_report_demo_id;
		
		private String item_code;
		
		private String item_name;
		
		private String item_pinyin;
		
		private String item_category;
		
		private String sex;
		
		private String dep_category;
		
		private String isOnlyApplyOrReport;
		
		private String item_seq;
		
		private String guide_category;
		
		private String his_num;
		
		private String exam_num;
		
		private String view_num;
		
		private double calculation_amount;
		
		
		private String interface_flag;
		
		private String item_type;
		
		private String charge_inter_num;
		
		private String item_abbreviation;

		private String dep_name;	
		
		private String set_num;
		
		private double team_pay;
		
		private double personal_pay;
		
		private String pay_status;
		
		private String team_pay_status;
		
		private String exam_indicator;
		
		private String exam_indicators;	
		
		private int calculation_rate;	 
		
		private int itemnum=1;//项目个数，1 表示1项，不能为0			

	    public int getCalculation_rate() {
			return calculation_rate;
		}

		public void setCalculation_rate(int calculation_rate) {
			this.calculation_rate = calculation_rate;
		}

		public int getItemnum() {
			return itemnum;
		}

		public void setItemnum(int itemnum) {
			this.itemnum = itemnum;
		}
		
		public String getPay_status() {
			return pay_status;
		}

		public void setPay_status(String pay_status) {
			this.pay_status = pay_status;
		}

		public String getTeam_pay_status() {
			return team_pay_status;
		}

		public void setTeam_pay_status(String team_pay_status) {
			this.team_pay_status = team_pay_status;
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
		}

		public double getTeam_pay() {
			return team_pay;
		}

		public void setTeam_pay(double team_pay) {
			this.team_pay = team_pay;
		}

		public double getPersonal_pay() {
			return personal_pay;
		}

		public void setPersonal_pay(double personal_pay) {
			this.personal_pay = personal_pay;
		}

		public String getSet_num() {
			return set_num;
		}

		public void setSet_num(String set_num) {
			this.set_num = set_num;
		}

		public String getDep_name() {
			return dep_name;
		}

		public void setDep_name(String dep_name) {
			this.dep_name = dep_name;
		}

		public long getDep_id() {
			return dep_id;
		}

		public void setDep_id(long dep_id) {
			this.dep_id = dep_id;
		}

		public long getSam_demo_id() {
			return sam_demo_id;
		}

		public void setSam_demo_id(long sam_demo_id) {
			this.sam_demo_id = sam_demo_id;
		}

		public long getSam_report_demo_id() {
			return sam_report_demo_id;
		}

		public void setSam_report_demo_id(long sam_report_demo_id) {
			this.sam_report_demo_id = sam_report_demo_id;
		}

		public String getItem_code() {
			return item_code;
		}

		public void setItem_code(String item_code) {
			this.item_code = item_code;
		}

		public String getItem_name() {
			return item_name;
		}

		public void setItem_name(String item_name) {
			this.item_name = item_name;
		}

		public String getItem_pinyin() {
			return item_pinyin;
		}

		public void setItem_pinyin(String item_pinyin) {
			this.item_pinyin = item_pinyin;
		}

		public String getItem_category() {
			return item_category;
		}

		public void setItem_category(String item_category) {
			this.item_category = item_category;
		}

		public String getSex() {
			return sex;
		}

		public void setSex(String sex) {
			this.sex = sex;
		}

		public String getDep_category() {
			return dep_category;
		}

		public void setDep_category(String dep_category) {
			this.dep_category = dep_category;
		}

		public String getIsOnlyApplyOrReport() {
			return isOnlyApplyOrReport;
		}

		public void setIsOnlyApplyOrReport(String isOnlyApplyOrReport) {
			this.isOnlyApplyOrReport = isOnlyApplyOrReport;
		}

		public String getItem_seq() {
			return item_seq;
		}

		public void setItem_seq(String item_seq) {
			this.item_seq = item_seq;
		}

		public String getGuide_category() {
			return guide_category;
		}

		public void setGuide_category(String guide_category) {
			this.guide_category = guide_category;
		}

		public String getHis_num() {
			return his_num;
		}

		public void setHis_num(String his_num) {
			this.his_num = his_num;
		}

		public String getExam_num() {
			return exam_num;
		}

		public void setExam_num(String exam_num) {
			this.exam_num = exam_num;
		}

		public String getView_num() {
			return view_num;
		}

		public void setView_num(String view_num) {
			this.view_num = view_num;
		}

		public double getCalculation_amount() {
			return calculation_amount;
		}

		public void setCalculation_amount(double calculation_amount) {
			this.calculation_amount = calculation_amount;
		}

		public String getInterface_flag() {
			return interface_flag;
		}

		public void setInterface_flag(String interface_flag) {
			this.interface_flag = interface_flag;
		}

		public String getItem_type() {
			return item_type;
		}

		public void setItem_type(String item_type) {
			this.item_type = item_type;
		}

		public String getCharge_inter_num() {
			return charge_inter_num;
		}

		public void setCharge_inter_num(String charge_inter_num) {
			this.charge_inter_num = charge_inter_num;
		}

		public String getItem_abbreviation() {
			return item_abbreviation;
		}

		public void setItem_abbreviation(String item_abbreviation) {
			this.item_abbreviation = item_abbreviation;
		}

		

	    public long getId() {
			return id;
		}

		public void setId(long id) {
			this.id = id;
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

		public String getIsActive() {
			return isActive;
		}

		public void setIsActive(String isActive) {
			this.isActive = isActive;
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

		public long getCharge_item_id() {
			return charge_item_id;
		}

		public void setCharge_item_id(long charge_item_id) {
			this.charge_item_id = charge_item_id;
		}

		public long getGroup_id() {
			return group_id;
		}

		public void setGroup_id(long group_id) {
			this.group_id = group_id;
		}

		public double getItem_amount() {
			return item_amount;
		}

		public void setItem_amount(double item_amount) {
			this.item_amount = item_amount;
		}

		public String getFinal_exam_date() {
			return final_exam_date;
		}

		public void setFinal_exam_date(String final_exam_date) {
			this.final_exam_date = final_exam_date;
		}

		public String getCreate_time() {
			return create_time;
		}

		public void setCreate_time(String create_time) {
			this.create_time = create_time;
		}

		public String getUpdate_time() {
			return update_time;
		}

		public void setUpdate_time(String update_time) {
			this.update_time = update_time;
		}		

	}