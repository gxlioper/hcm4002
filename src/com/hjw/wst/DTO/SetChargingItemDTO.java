package com.hjw.wst.DTO;

public class SetChargingItemDTO implements java.io.Serializable {
	 private static final long serialVersionUID = -97502163798576023L;

		private long id;

		private long charging_item_id;//收费id

		private long exam_set_id;
		
		private String item_name="";//收费项目名称
		
		private String item_code="";//收费项目编码

		private double set_discountss;//折扣

		private double amount;//金额--改成折后金额
		
		private double amounts;//折后金额
		
		private double  item_amount;//金额
		
		private String d_name="";//科室名称

		private long creater;

		private String create_time="";

		private long updater;

		private String update_time="";	
		
		private String item_type="";	
		private int itemnum;
		
		private long item_seq;
		
		private String ischosen;
		private String set_num;
		
		private int item_discount;
		
		private double discount;
		
		public int getItem_discount() {
			return item_discount;
		}

		public void setItem_discount(int item_discount) {
			this.item_discount = item_discount;
		}

		public String getSet_num() {
			return set_num;
		}

		public double getDiscount() {
			return discount;
		}

		public void setDiscount(double discount) {
			this.discount = discount;
		}

		public void setSet_num(String set_num) {
			this.set_num = set_num;
		}
		
	    public String getIschosen() {
			return ischosen;
		}
		public void setIschosen(String ischosen) {
			this.ischosen = ischosen;
		}
		public long getItem_seq() {
			return item_seq;
		}
		public void setItem_seq(long item_seq) {
			this.item_seq = item_seq;
		}
		public int getItemnum() {
			return itemnum;
		}
		public void setItemnum(int itemnum) {
			this.itemnum = itemnum;
		}
	    public double getItem_amount() {
			return item_amount;
		}

		public void setItem_amount(double item_amount) {
			this.item_amount = item_amount;
		}

		public String getItem_type() {
			return item_type;
		}

		public void setItem_type(String item_type) {
			this.item_type = item_type;
		}

		public long getCharging_item_id() {
			return charging_item_id;
		}

		public void setCharging_item_id(long charging_item_id) {
			this.charging_item_id = charging_item_id;
		}

		public long getExam_set_id() {
			return exam_set_id;
		}

		public void setExam_set_id(long exam_set_id) {
			this.exam_set_id = exam_set_id;
		}

		public long getId() {
			return id;
		}

		public void setId(long id) {
			this.id = id;
		}


		public double getAmount() {
			return amount;
		}

		public void setAmount(double amount) {
			this.amount = amount;
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

		public String getItem_name() {
			return item_name;
		}

		public void setItem_name(String item_name) {
			this.item_name = item_name;
		}


		public String getD_name() {
			return d_name;
		}

		public void setD_name(String d_name) {
			this.d_name = d_name;
		}

		public double getSet_discountss() {
			return set_discountss;
		}

		public void setSet_discountss(double set_discountss) {
			this.set_discountss = set_discountss;
		}

		public double getAmounts() {
			return amounts;
		}

		public void setAmounts(double amounts) {
			this.amounts = amounts;
		}

		public String getItem_code() {
			return item_code;
		}

		public void setItem_code(String item_code) {
			this.item_code = item_code;
		}

		
	}