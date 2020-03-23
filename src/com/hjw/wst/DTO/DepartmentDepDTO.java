package com.hjw.wst.DTO;


public class DepartmentDepDTO{

	private static final long serialVersionUID = -97502163798576023L;
	private int id;
	private String dep_num;
	private String dep_name;
	private String dep_category;
	private String sex;
	private int seq_code;
	private String remark;
	private String dep_link;
	private String isActive;
	private long creater;
	private String create_time;
	private long updater;
	private String update_time;
	private int isPrint_Barcode;
	private String isPrint_Barcode_y;
	private String dep_inter_num;
	private int calculation_rate;//估算利润率
	private String centernames="";//保存对应体检中心名称
	
	private Integer  zdid;//字典id
	private String   data_name;//名称	
	
	private long dep_id;
	private String u_chi_name;
	private String c_chi_name;
	private String isPrint_Barcode_c;
	private String isPrint_Barcode_cs;
	private String remark1;
	private String dep_address;
	private String synchro;
	private int view_result_type;
	private String vs_view_result_type;
	private String synchrodate;
	private String creater_name;
	private String updater_name;
	private  long item_id;
	private String item_code;

	public String getItem_code() {
		return item_code;
	}

	public void setItem_code(String item_code) {
		this.item_code = item_code;
	}

	public long getItem_id() {
		return item_id;
	}

	public void setItem_id(long item_id) {
		this.item_id = item_id;
	}

	public String getCreater_name() {
		return creater_name;
	}

	public void setCreater_name(String creater_name) {
		this.creater_name = creater_name;
	}

	public String getUpdater_name() {
		return updater_name;
	}

	public void setUpdater_name(String updater_name) {
		this.updater_name = updater_name;
	}

	public String getSynchrodate() {
		return synchrodate;
	}

	public void setSynchrodate(String synchrodate) {
		this.synchrodate = synchrodate;
	}

	public String getVs_view_result_type() {
		return vs_view_result_type;
	}

	public void setVs_view_result_type(String vs_view_result_type) {
		this.vs_view_result_type = vs_view_result_type;
	}

	public int getView_result_type() {
		return view_result_type;
	}

	public void setView_result_type(int view_result_type) {
		this.view_result_type = view_result_type;
	}

	public String getSynchro() {
		return synchro;
	}

	public void setSynchro(String synchro) {
		this.synchro = synchro;
	}

	public String getDep_address() {
		return dep_address;
	}

	public void setDep_address(String dep_address) {
		this.dep_address = dep_address;
	}

	public String getRemark1() {
		return remark1;
	}

	public void setRemark1(String remark1) {
		this.remark1 = remark1;
	}

	public String getIsPrint_Barcode_cs() {
		return isPrint_Barcode_cs;
	}

	public void setIsPrint_Barcode_cs(String isPrint_Barcode_cs) {
		this.isPrint_Barcode_cs = isPrint_Barcode_cs;
	}

	public String getIsPrint_Barcode_c() {
		return isPrint_Barcode_c;
	}

	public void setIsPrint_Barcode_c(String isPrint_Barcode_c) {
		this.isPrint_Barcode_c = isPrint_Barcode_c;
		if("1".equals(this.isPrint_Barcode_c)){
			this.setIsPrint_Barcode_cs("否");
		} else {
			this.setIsPrint_Barcode_cs("是");
		}
	}

	public String getU_chi_name() {
		return u_chi_name;
	}

	public void setU_chi_name(String u_chi_name) {
		this.u_chi_name = u_chi_name;
	}

	public String getC_chi_name() {
		return c_chi_name;
	}

	public void setC_chi_name(String c_chi_name) {
		this.c_chi_name = c_chi_name;
	}

	public long getDep_id() {
		return dep_id;
	}

	public void setDep_id(long dep_id) {
		this.dep_id = dep_id;
	}

	public String getCenternames() {
		return centernames;
	}

	public void setCenternames(String centernames) {
		this.centernames = centernames;
	}

	public Integer getZdid() {
		return zdid;
	}

	public void setZdid(Integer zdid) {
		this.zdid = zdid;
	}

	public String getData_name() {
		return data_name;
	}

	public void setData_name(String data_name) {
		this.data_name = data_name;
	}

	public DepartmentDepDTO() {
		// TODO Auto-generated constructor stub
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDep_num() {
		return dep_num;
	}

	public void setDep_num(String dep_num) {
		this.dep_num = dep_num;
	}

	public String getDep_name() {
		return dep_name;
	}

	public void setDep_name(String dep_name) {
		this.dep_name = dep_name;
	}

	public String getDep_category() {
		return dep_category;
	}

	public void setDep_category(String dep_category) {
		this.dep_category = dep_category;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}


	public int getSeq_code() {
		return seq_code;
	}


	public void setSeq_code(int seq_code) {
		this.seq_code = seq_code;
	}


	public String getRemark() {
		return remark;
	}


	public void setRemark(String remark) {
		this.remark = remark;
	}


	public String getDep_link() {
		return dep_link;
	}


	public void setDep_link(String dep_link) {
		this.dep_link = dep_link;
	}


	public String getIsActive() {
		return isActive;
	}


	public void setIsActive(String isActive) {
		this.isActive = isActive;
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


	public int getIsPrint_Barcode() {
		return isPrint_Barcode;
	}


	public void setIsPrint_Barcode(int isPrint_Barcode) {
		this.isPrint_Barcode = isPrint_Barcode;
		if(1 == isPrint_Barcode){
			this.setIsPrint_Barcode_y("否");
		}else if(0 == isPrint_Barcode){
			this.setIsPrint_Barcode_y("是");
		}
	}


	public String getIsPrint_Barcode_y() {
		return isPrint_Barcode_y;
	}


	public void setIsPrint_Barcode_y(String isPrint_Barcode_y) {
		this.isPrint_Barcode_y = isPrint_Barcode_y;
	}


	public String getDep_inter_num() {
		return dep_inter_num;
	}


	public void setDep_inter_num(String dep_inter_num) {
		this.dep_inter_num = dep_inter_num;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public int getCalculation_rate() {
		return calculation_rate;
	}

	public void setCalculation_rate(int calculation_rate) {
		this.calculation_rate = calculation_rate;
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
	
	
}
