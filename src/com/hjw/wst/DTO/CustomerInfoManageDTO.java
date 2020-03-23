package com.hjw.wst.DTO;




public class CustomerInfoManageDTO implements java.io.Serializable {
	 private static final long serialVersionUID = -97502163798576023L;

	 private long id;
		private String arch_num;
		private String user_name;
		private String id_num;
		private String sex;
		private String birthday;
		private String nation;
		private String phone;
		private String address;
		private String email;
		private String is_Active;
		private String is_Active_y;
		private String creater;
		private String create_time;
		private String updater;
		private String update_time;
		private String membership_card;
		private String medical_insurance_card;
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
		public String getEmail() {
			return email;
		}
		public void setEmail(String email) {
			this.email = email;
		}
		public String getIs_Active() {
			return is_Active;
		}
		public void setIs_Active(String is_Active) {
			this.is_Active = is_Active;
			if("Y".equals(is_Active)){
				this.is_Active_y = "是";
			}else{
				this.is_Active_y = "否";
			}
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
		public String getIs_Active_y() {
			return is_Active_y;
		}
		public void setIs_Active_y(String is_Active_y) {
			this.is_Active_y = is_Active_y;
		}
	}