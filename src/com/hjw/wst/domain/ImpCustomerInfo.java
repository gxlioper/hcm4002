package com.hjw.wst.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "impCustomerInfo")
public class ImpCustomerInfo implements java.io.Serializable {
	 private static final long serialVersionUID = -97502163798576023L;
		@Id
		@GenericGenerator(name = "idGenerator", strategy = "identity")
		@GeneratedValue(generator = "idGenerator")
		private long id;

		@Column(name = "company_id")
		private long company_id;
		
		@Column(name = "batch_id")
		private long batch_id;
		
		@Column(name = "contract_id")
		private long contract_id;		
		
		@Column(name = "arch_num")
		private String arch_num;
		
		@Column(name = "id_num")
		private String id_num;
		
		@Column(name = "sex")
		private String sex;
		
		@Column(name = "birthday")
		private String birthday;
		
		@Column(name = "custname")
		private String custname;
		
		@Column(name = "age")
		private long age;
		
		@Column(name = "is_marriage")
		private String is_marriage;
		
		@Column(name = "position")
		private String position;
		
		@Column(name = "_level")
		private String _level;
		
		@Column(name = "tel")
		private String tel;
		
		@Column(name = "remark")
		private String remark;
		
		@Column(name = "customer_type")
		private String customer_type;//人员类型
		
		@Column(name = "others")
		private String others;
		
		@Column(name = "flags")
		private long flags;
		
		@Column(name = "notices")
		private String notices;
	    
	    @Column(name = "creater")
	    private long creater;
	    
	    @Column(name = "create_time")
	    private String create_time;
	    
	    @Column(name = "exam_type")
	    private String exam_type;//体检类型  
	    
	    @Column(name = "employeeID")
	    private String employeeID;//员工编号
	    
	    @Column(name = "visit_no")
	    private String visit_no;//就诊卡
	    
	    @Column(name="introducer")
	    private String introducer;//介绍人
	    
	    @Column(name="idtype")
	    private int idtype;//证件类型
	    
	    @Column(name="idtypename")
	    private String idtypename;
		
	    @Column(name = "sets")
	    private String sets;//套餐名称，以小写的分号隔开
	    
	    @Column(name = "zywhys")
		private String zywhys;//危害因素类型，以小写的分号隔开，和套餐名称对应
	    
	    @Column(name = "joinDatetime")
		private String joinDatetime;//进厂日期
	    
	    @Column(name = "employeeage")
		private int employeeage;//总工龄
	    
	    @Column(name = "damage")
		private int damage; //接害工龄
	    
	    @Column(name = "employeemonth")
		private int employeemonth;//总工龄（月）
	    
	    @Column(name = "dammonth")
		private int dammonth; //接害工龄（月）
	    
	    @Column(name = "occusector")
		private String occusector;//行业
	    
	    @Column(name = "occutypeofwork")
		private String occutypeofwork;//工种
	    
	    @Column(name = "info_type")
	    private int info_type;//客户信息类型

	    @Column(name = "born_address")
		private String born_address;//工种
	    
	    @Column(name = "nation")
	    private String nation;//民族
	    
	    @Column(name = "address")
	    private String address;//家庭地址
	    
	    @Column(name = "zip")
	    private String zip;//家庭邮编
	    
	    @Column(name = "degreeOfedu")
	    private String degreeOfedu;//文化程度
	    
	    @Column(name = "sc_class")
	    private String sc_class;//放射源种类
	    
	    @Column(name = "exam_indicator")
	    private String exam_indicator;//职业病付费类别
	    
	    @Column(name = "exam_class")
	    private String exam_class;//体检类型：对应数据字典
	    
	    public enum InfoType {
	    	YBTJ(0, "一般体检"),
	    	ZYB(1, "职业健康检查"),
	    	FSXZYB(2, "放射健康检查");
	    	
	    	private int code;
	    	private String content;
	    	
	    	InfoType(int code, String content) {
	    		this.code = code;
	    		this.content = content;
	    	}
			public int getCode() {
				return code;
			}
			public void setCode(int code) {
				this.code = code;
			}
			public String getContent() {
				return content;
			}
			public void setContent(String content) {
				this.content = content;
			}
	    }

		public int getIdtype() {
			return idtype;
		}

		public void setIdtype(int idtype) {
			this.idtype = idtype;
		}

		public String getIdtypename() {
			return idtypename;
		}

		public void setIdtypename(String idtypename) {
			this.idtypename = idtypename;
		}

		public String getSets() {
			return sets;
		}

		public void setSets(String sets) {
			this.sets = sets;
		}

		public String getZywhys() {
			return zywhys;
		}

		public void setZywhys(String zywhys) {
			this.zywhys = zywhys;
		}

		public String getJoinDatetime() {
			return joinDatetime;
		}

		public void setJoinDatetime(String joinDatetime) {
			this.joinDatetime = joinDatetime;
		}

		public int getEmployeeage() {
			return employeeage;
		}

		public void setEmployeeage(int employeeage) {
			this.employeeage = employeeage;
		}

		public int getDamage() {
			return damage;
		}

		public void setDamage(int damage) {
			this.damage = damage;
		}

		public String getOccusector() {
			return occusector;
		}

		public void setOccusector(String occusector) {
			this.occusector = occusector;
		}

		public String getOccutypeofwork() {
			return occutypeofwork;
		}

		public void setOccutypeofwork(String occutypeofwork) {
			this.occutypeofwork = occutypeofwork;
		}

		public String getEmployeeID() {
			return employeeID;
		}

		public void setEmployeeID(String employeeID) {
			this.employeeID = employeeID;
		}

		public String getExam_type() {
			return exam_type;
		}

		public void setExam_type(String exam_type) {
			this.exam_type = exam_type;
		}

		public long getId() {
			return id;
		}

		public void setId(long id) {
			this.id = id;
		}

		public long getCompany_id() {
			return company_id;
		}

		public void setCompany_id(long company_id) {
			this.company_id = company_id;
		}

		public long getBatch_id() {
			return batch_id;
		}

		public void setBatch_id(long batch_id) {
			this.batch_id = batch_id;
		}

		public long getContract_id() {
			return contract_id;
		}

		public void setContract_id(long contract_id) {
			this.contract_id = contract_id;
		}

		public String getArch_num() {
			return arch_num;
		}

		public void setArch_num(String arch_num) {
			this.arch_num = arch_num;
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


		public String getCustname() {
			return custname;
		}

		public void setCustname(String custname) {
			this.custname = custname;
		}

		public long getAge() {
			return age;
		}

		public void setAge(long age) {
			this.age = age;
		}

		public String getIs_marriage() {
			return is_marriage;
		}

		public void setIs_marriage(String is_marriage) {
			this.is_marriage = is_marriage;
		}

		public String getPosition() {
			return position;
		}

		public void setPosition(String position) {
			this.position = position;
		}

		public String get_level() {
			return _level;
		}

		public void set_level(String _level) {
			this._level = _level;
		}

		public String getTel() {
			return tel;
		}

		public void setTel(String tel) {
			this.tel = tel;
		}

		public String getRemark() {
			return remark;
		}

		public void setRemark(String remark) {
			this.remark = remark;
		}

		public String getCustomer_type() {
			return customer_type;
		}

		public void setCustomer_type(String customer_type) {
			this.customer_type = customer_type;
		}

		public String getOthers() {
			return others;
		}

		public void setOthers(String others) {
			this.others = others;
		}

		public long getFlags() {
			return flags;
		}

		public void setFlags(long flags) {
			this.flags = flags;
		}

		public String getNotices() {
			return notices;
		}

		public void setNotices(String notices) {
			this.notices = notices;
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

		public String getVisit_no() {
			return visit_no;
		}

		public void setVisit_no(String visit_no) {
			this.visit_no = visit_no;
		}

		public String getIntroducer() {
			return introducer;
		}

		public void setIntroducer(String introducer) {
			this.introducer = introducer;
		}

		public int getInfo_type() {
			return info_type;
		}

		public void setInfo_type(int info_type) {
			this.info_type = info_type;
		}

		public String getBorn_address() {
			return born_address;
		}

		public void setBorn_address(String born_address) {
			this.born_address = born_address;
		}

		public String getNation() {
			return nation;
		}

		public void setNation(String nation) {
			this.nation = nation;
		}

		public String getAddress() {
			return address;
		}

		public void setAddress(String address) {
			this.address = address;
		}

		public String getZip() {
			return zip;
		}

		public void setZip(String zip) {
			this.zip = zip;
		}

		public String getDegreeOfedu() {
			return degreeOfedu;
		}

		public void setDegreeOfedu(String degreeOfedu) {
			this.degreeOfedu = degreeOfedu;
		}

		public String getSc_class() {
			return sc_class;
		}

		public void setSc_class(String sc_class) {
			this.sc_class = sc_class;
		}

		public String getExam_indicator() {
			return exam_indicator;
		}

		public void setExam_indicator(String exam_indicator) {
			this.exam_indicator = exam_indicator;
		}

		public String getExam_class() {
			return exam_class;
		}

		public void setExam_class(String exam_class) {
			this.exam_class = exam_class;
		}

		public int getEmployeemonth() {
			return employeemonth;
		}

		public void setEmployeemonth(int employeemonth) {
			this.employeemonth = employeemonth;
		}

		public int getDammonth() {
			return dammonth;
		}

		public void setDammonth(int dammonth) {
			this.dammonth = dammonth;
		}

	}