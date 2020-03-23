package com.hjw.wst.DTO;

public class CardInfoDTO implements java.io.Serializable{

	private static final long serialVersionUID = -97502163798576023L;
	private String id;
	private String physical_num;
	private String card_num;
	private String member_id;
	private String member_name;
	private String status;
	private String status_y;
	private String deadline;
	private String card_type;
	private String card_type_y;
	private Double amount;
	private Double face_amount;
	private String card_level;
	private String card_level_y;
	private Long card_count;
	private String limit_card_count;
	private String card_pwd;
	private String remark;
	private String creater;
	private String create_time;
	private int is_set_status;
	
	private String get_date;
	
	private String config_value;
	
	private double discount;
	private String company;
	private double sale_amount;
	private String updater;
	private String update_time;
	private long hair_card_status;
	private String hair_card_statuss;
	private String hair_card_creater;
	private String hair_card_create_time;
	private String sale_trade_num;   //售卡交易流水号
	private int company_id;   //单位id
	private int sale_status;   //售卡状态
	private String saleStatus_CH;  //售卡状态中文名
	private String center_num;
	
	public String getCenter_num() {
		return center_num;
	}

	public void setCenter_num(String center_num) {
		this.center_num = center_num;
	}

	public double getDiscount() {
		return discount;
	}

	public void setDiscount(double discount) {
		this.discount = discount;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getCreater() {
		return creater;
	}

	public void setCreater(String creater) {
		this.creater = creater;
	}

	public String getCreate_time() {
		return create_time;
	}

	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}

	public String getConfig_value() {
		return config_value;
	}

	public void setConfig_value(String config_value) {
		this.config_value = config_value;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPhysical_num() {
		return physical_num;
	}

	public void setPhysical_num(String physical_num) {
		this.physical_num = physical_num;
	}

	public String getCard_num() {
		return card_num;
	}

	public void setCard_num(String card_num) {
		this.card_num = card_num;
	}

	public String getMember_id() {
		return member_id;
	}

	public void setMember_id(String member_id) {
		this.member_id = member_id;
	}

	public String getMember_name() {
		return member_name;
	}

	public void setMember_name(String member_name) {
		this.member_name = member_name;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
		if(status.equals("1")){
			this.setStatus_y("正常");
		}else if(status.equals("2")){
			this.setStatus_y("锁定");
		}else if(status.equals("3")){
			this.setStatus_y("挂失");
		}else if(status.equals("4")){
			this.setStatus_y("作废");
		}
	}

	public String getStatus_y() {
		return status_y;
	}

	public void setStatus_y(String status_y) {
		this.status_y = status_y;
	}

	public String getDeadline() {
		return deadline;
	}

	public void setDeadline(String deadline) {
		this.deadline = deadline;
	}

	public String getCard_type() {
		return card_type;
	}

	public void setCard_type(String card_type) {
		this.card_type = card_type;
		if(card_type.equals("1")){
			this.setCard_type_y("记名");
		}else if(card_type.equals("2")){
			this.setCard_type_y("不记名");
		}
	}

	public String getCard_type_y() {
		return card_type_y;
	}

	public void setCard_type_y(String card_type_y) {
		this.card_type_y = card_type_y;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Double getFace_amount() {
		return face_amount;
	}

	public void setFace_amount(Double face_amount) {
		this.face_amount = face_amount;
	}

	public String getCard_level() {
		return card_level;
	}

	public void setCard_level(String card_level) {
		this.card_level = card_level;
	}

	public String getCard_level_y() {
		return card_level_y;
	}

	public void setCard_level_y(String card_level_y) {
		this.card_level_y = card_level_y;
	}

	public Long getCard_count() {
		return card_count;
	}

	public void setCard_count(Long card_count) {
		this.card_count = card_count;
	}

	public String getLimit_card_count() {
		return limit_card_count;
	}

	public void setLimit_card_count(String limit_card_count) {
		this.limit_card_count = limit_card_count;
	}

	public String getCard_pwd() {
		return card_pwd;
	}

	public void setCard_pwd(String card_pwd) {
		this.card_pwd = card_pwd;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getGet_date() {
		return get_date;
	}

	public void setGet_date(String get_date) {
		this.get_date = get_date;
	}

	public String getUpdater() {
		return updater;
	}

	public void setUpdater(String updater) {
		this.updater = updater;
	}

	public String getUpdate_time() {
		return update_time;
	}

	public void setUpdate_time(String update_time) {
		this.update_time = update_time;
	}

	public long getHair_card_status() {
		return hair_card_status;
	}

	public void setHair_card_status(long hair_card_status) {
		this.hair_card_status = hair_card_status;
		if(hair_card_status == 0){
			this.hair_card_statuss = "未发卡";
		}else if(hair_card_status == 1){
			this.hair_card_statuss = "已发卡";
		}else{
			this.hair_card_statuss = "未发卡";
		}
	}

	public String getHair_card_statuss() {
		return hair_card_statuss;
	}

	public void setHair_card_statuss(String hair_card_statuss) {
		this.hair_card_statuss = hair_card_statuss;
	}

	public String getHair_card_creater() {
		return hair_card_creater;
	}

	public void setHair_card_creater(String hair_card_creater) {
		this.hair_card_creater = hair_card_creater;
	}

	public String getHair_card_create_time() {
		return hair_card_create_time;
	}

	public void setHair_card_create_time(String hair_card_create_time) {
		this.hair_card_create_time = hair_card_create_time;
	}

	public double getSale_amount() {
		return sale_amount;
	}

	public void setSale_amount(double sale_amount) {
		this.sale_amount = sale_amount;
	}

	public int getIs_set_status() {
		return is_set_status;
	}

	public void setIs_set_status(int is_set_status) {
		this.is_set_status = is_set_status;
	}

	public String getSale_trade_num() {
		return sale_trade_num;
	}

	public void setSale_trade_num(String sale_trade_num) {
		this.sale_trade_num = sale_trade_num;
	}

	public int getCompany_id() {
		return company_id;
	}

	public void setCompany_id(int company_id) {
		this.company_id = company_id;
	}

	public int getSale_status() {
		return sale_status;
	}

	public void setSale_status(int sale_status) {
		this.sale_status = sale_status;
		switch(sale_status) {
		case 0:
			this.saleStatus_CH = "未售";
			break;
		case 1:
			this.saleStatus_CH = "已售";
			break;
		case 2:
			this.saleStatus_CH = "预售";
			break;
		default:
			this.saleStatus_CH = "未售";
		}
	}

	public String getSaleStatus_CH() {
		return saleStatus_CH;
	}

	public void setSaleStatus_CH(String saleStatus_CH) {
		this.saleStatus_CH = saleStatus_CH;
	}
	
}
