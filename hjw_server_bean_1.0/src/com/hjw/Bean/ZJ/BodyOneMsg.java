package com.hjw.Bean.ZJ;

public class BodyOneMsg extends BodyExamMsg {
	private String med_person_id = "";// 体检号
	private String name = "";// 姓名
	private String day_of_birth = "";// 出生日期 格式yyyy-mm-dd
	private String gender = "";// 性别 男/女
	private String Id_type = "1";// 证件类型 1:身份证 2：回乡证 3:护照 4:军官证 5:医保卡号 6：警察证
									// 20：其他 如果体检中心还有自己其他的证件类型，请提供慈云服务端
	private String id_no = "";// 证件号码
	private Object rpts = new Object();// 报告详细主体
	private String sex = "";

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getMed_person_id() {
		return med_person_id;
	}

	public void setMed_person_id(String med_person_id) {
		this.med_person_id = med_person_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDay_of_birth() {
		return day_of_birth;
	}

	public void setDay_of_birth(String day_of_birth) {
		this.day_of_birth = day_of_birth;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getId_type() {
		return Id_type;
	}

	public void setId_type(String id_type) {
		Id_type = id_type;
	}

	public String getId_no() {
		return id_no;
	}

	public void setId_no(String id_no) {
		this.id_no = id_no;
	}

	public Object getRpts() {
		return rpts;
	}

	public void setRpts(Object rpts) {
		this.rpts = rpts;
	}

}
