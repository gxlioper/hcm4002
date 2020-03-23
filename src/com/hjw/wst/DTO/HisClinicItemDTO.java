package com.hjw.wst.DTO;

public class HisClinicItemDTO implements java.io.Serializable{
	private static final long serialVersionUID = 1L;

	private String item_code="";

	private String item_class="";

	private String item_name="";

	private String item_spec="";
	
	private String units="";
	
	private double price=0;
	
	private String start_date="";
	
	private String stop_date="";
	
	private String types="";
	
	private String typess="";
	
	private String memo="";
	
	private String create_date="";
	
	private int id=0;

	public String getItem_code() {
		return item_code;
	}

	public void setItem_code(String item_code) {
		this.item_code = item_code;
	}

	public String getItem_class() {
		return item_class;
	}

	public void setItem_class(String item_class) {
		this.item_class = item_class;
	}

	public String getItem_name() {
		return item_name;
	}

	public void setItem_name(String item_name) {
		this.item_name = item_name;
	}

	public String getItem_spec() {
		return item_spec;
	}

	public void setItem_spec(String item_spec) {
		this.item_spec = item_spec;
	}

	public String getUnits() {
		return units;
	}

	public void setUnits(String units) {
		this.units = units;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getStart_date() {
		return start_date;
	}

	public void setStart_date(String start_date) {
		this.start_date = start_date;
	}

	public String getStop_date() {
		return stop_date;
	}

	public void setStop_date(String stop_date) {
		this.stop_date = stop_date;
	}

	public String getTypes() {
		return types;
	}

	public void setTypes(String types) {
		this.types = types;
		if("1".equals(types)){
			this.setTypess("项目禁用");
		}else if("2".equals(types)){
			this.setTypess("项目启用");
		}else if("3".equals(types)){
			this.setTypess("价格变化");
		}else{
			this.setTypess("未知");
		}
	}	

	public String getTypess() {
		return typess;
	}

	public void setTypess(String typess) {
		this.typess = typess;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getCreate_date() {
		return create_date;
	}

	public void setCreate_date(String create_date) {
		this.create_date = create_date;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	

}
