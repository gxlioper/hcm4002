package com.hjw.wst.DTO;



public class HisPriceItemDTO implements java.io.Serializable{
	private static final long serialVersionUID = 1L;

	private String item_code="";

	private String item_name="";

	private String clinic_item_code="";

	private String clinic_item_class="";

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

	public String getClinic_item_code() {
		return clinic_item_code;
	}

	public void setClinic_item_code(String clinic_item_code) {
		this.clinic_item_code = clinic_item_code;
	}

	public String getClinic_item_class() {
		return clinic_item_class;
	}

	public void setClinic_item_class(String clinic_item_class) {
		this.clinic_item_class = clinic_item_class;
	}

	
}
