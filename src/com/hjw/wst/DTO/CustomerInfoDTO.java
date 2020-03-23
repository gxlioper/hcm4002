package com.hjw.wst.DTO;

/**
 * 档案表
 * @author Administrator
 *
 */
public class CustomerInfoDTO {
	private long id;
	private String arch_num="";//	档案编号
	private String user_name="";//	体检用户姓名
	private String id_num="";//	身份证号
	private String phone="";//电话
	private String address="";//地址
	private String sex="";//	性别
	private String birthday="";//	生日
	private String nation="";//	民族
	private String is_Active="";//	是否激活
	private long creater;//	记录创建者
	private String create_time="";//	创建时间
	private long updater;//	记录更新者
	private String update_time="";//	记录更新时间
	private String membership_card="";//	会员卡
	private String medical_insurance_card="";//	医保卡
    private String flag="0";
    private String visit_no;
    private String born_address;//出生地
	private int idtype;
	private String center_num;
	 private String unlink_items;
	public int getIdtype() {
		return idtype;
	}
	public void setIdtype(int idtype) {
		this.idtype = idtype;
	}
	public String getVisit_no() {
		return visit_no;
	}
	public void setVisit_no(String visit_no) {
		this.visit_no = visit_no;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getMembership_card() {
		return membership_card;
	}
	public void setMembership_card(String membership_card) {
		this.membership_card = membership_card;
	}
	public String getMedical_insurance_card() {
		return medical_insurance_card;
	}
	public void setMedical_insurance_card(String medical_insurance_card) {
		this.medical_insurance_card = medical_insurance_card;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getArch_num() {
		return arch_num;
	}
	public void setArch_num(String arch_num) {
		this.arch_num = arch_num;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public String getId_num() {
		return id_num;
	}
	public void setId_num(String id_num) {
		this.id_num = id_num;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	public String getNation() {
		return nation;
	}
	public void setNation(String nation) {
		this.nation = nation;
	}
	public String getIs_Active() {
		return is_Active;
	}
	public void setIs_Active(String is_Active) {
		this.is_Active = is_Active;
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
	public String getBorn_address() {
		return born_address;
	}
	public void setBorn_address(String born_address) {
		this.born_address = born_address;
	}
	public String getCenter_num() {
		return center_num;
	}
	public void setCenter_num(String center_num) {
		this.center_num = center_num;
	}
	public String getUnlink_items() {
		return unlink_items;
	}
	public void setUnlink_items(String unlink_items) {
		this.unlink_items = unlink_items;
	}
	
}
