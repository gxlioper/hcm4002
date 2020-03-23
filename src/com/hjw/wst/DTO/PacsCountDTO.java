package com.hjw.wst.DTO;

public class PacsCountDTO implements java.io.Serializable {
	 private static final long serialVersionUID = -97502163798576023L;

	 private long charging_id;
	 private String dep_name;
	 private String item_code;
	 private String item_name;
	 private String code;
	 private long examinfo_id;

    public long getExaminfo_id() {
        return examinfo_id;
    }

    public void setExaminfo_id(long examinfo_id) {
        this.examinfo_id = examinfo_id;
    }

    public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public long getCharging_id() {
		return charging_id;
	}
	public void setCharging_id(long charging_id) {
		this.charging_id = charging_id;
	}
	public String getDep_name() {
		return dep_name;
	}
	public void setDep_name(String dep_name) {
		this.dep_name = dep_name;
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
	
	}