package com.hjw.zyb.model;

import com.hjw.zyb.DTO.ZybOccuHisDTO;


public class ZybImpCustomerInfoModel implements java.io.Serializable {
    private static final long serialVersionUID = -97502163798576023L;

    private String group_index = "";

    private long id;

    private long company_id;

    private long batch_id;

    private long contract_id;

    private String arch_num = "";

    private String id_num = "";

    private String sex = "";

    private String birthday = "";

    private String custname = "";

    private long age;

    private String is_marriage = "";

    private String position = "";

    private String _level = "";

    private String tel = "";

    private String remark = "";

    private String customer_type = "";

    private String others = "";

    private long flags;

    private String notices = "";

    private long creater;

    private String create_time = "";

    private String ids = "";

    private long exam_id;

    private long dept_id;

    private long group_id;

    private long customer_type_id;

    private String scustomer_type_id = "";

    private String comname = "";

    private String batch_name = "";

    private String Batch_num = "";

    private String email = "";

    private String mz = "";//民族

    private String person_shenfen = "";//身份类别

    private String sftype = "";//收费类型

    private String hansidjdflag = "";

    private String time1 = "";

    private String time2 = "";

    private String time3 = "";

    private String time4 = "";

    private String exam_type = "";

    private String status = "";

    private String nation = "";

    private long customer_id;

    private String chargingType = "";

    private String address = "";

    private String exam_num = "";

    private String itementities = "";

    private String setentities;

    private String companybatch_id = "";

    private String employeeID = "";

    private String sets = "";//套餐名称，以小写的分号隔开

    private String zywhys = "";//危害因素类型，以小写的分号隔开，和套餐名称对应

    private String joinDatetime = "";//进厂日期

    private String visit_no;

    private int idtype;

    private int employeeage;//总工龄

    private int damage; //接害工龄

    private int zyb_set_source; //职业病体检套餐来源，0表示按职业危害因素关联套餐，1表示关联自选套餐

    private String occusector = "";//行业

    private String occutypeofwork = "";//工种

    private String occusectorid = "";
    private String occutypeofworkid = "";
    private String zywhlb = "";
    private String zywhyslb = "";
    private long examset_id;
    private String harmname = "";//危害因素名称
    private String concentrations = "";//危害因素浓度
    private String measure = "";//防护措施
    private String isradiation = "";//是否放射工作史
    private String zytjlb;//职业体检类别
    private double amount;
    private double discount;
    private String app_type = "1";
    private String exam_indicator = "T";
    private String zyb_id;

    private String radiation;
    private String man_haur;
    private String cumulative_exposure;
    private String history_excessive;

    //既往史


    private String diseases;


    private String diagnosisdate;

    private String diagnosiscom;

    private String diagnosisnotice;

    private String diseasereturn;

    private String remark1;

    private String remark2;

    private String remark3;

    //职业史
    private String company;
    private String workshop;
    private String worktype;
    private String startdate;
    private String enddate;
    private String create_date;
    private ZybOccuHisDTO zybOccuHisDTO;

    private String djdstatuss = "";//导检单打印标志
    private String barcode_print_type;//调用打印程序类型
    private String zyb_barcode_print_type;
    private int is_show_discount;

    private String tiJianType;
    private String exam_nums;

    private String chkItem = "";
    private String yuyue_date1;
    private String yuyue_date2;
    private int ren_type;
    private long set_id;
    private String data_source;
    private String levels = "";
    private String hazard_list;

    public String getHazard_list() {
        return hazard_list;
    }

    public void setHazard_list(String hazard_list) {
        this.hazard_list = hazard_list;
    }

    public int getRen_type() {
        return ren_type;
    }

    public long getSet_id() {
        return set_id;
    }

    public String getData_source() {
        return data_source;
    }

    public String getLevels() {
        return levels;
    }

    public void setRen_type(int ren_type) {
        this.ren_type = ren_type;
    }

    public void setSet_id(long set_id) {
        this.set_id = set_id;
    }

    public void setData_source(String data_source) {
        this.data_source = data_source;
    }

    public void setLevels(String levels) {
        this.levels = levels;
    }

    public String getChkItem() {
        return chkItem;
    }

    public String getYuyue_date1() {
        return yuyue_date1;
    }

    public String getYuyue_date2() {
        return yuyue_date2;
    }

    public void setChkItem(String chkItem) {
        this.chkItem = chkItem;
    }

    public void setYuyue_date1(String yuyue_date1) {
        this.yuyue_date1 = yuyue_date1;
    }

    public void setYuyue_date2(String yuyue_date2) {
        this.yuyue_date2 = yuyue_date2;
    }

    public String getExam_nums() {
        return exam_nums;
    }

    public void setExam_nums(String exam_nums) {
        this.exam_nums = exam_nums;
    }

    public int getIs_show_discount() {
        return is_show_discount;
    }

    public void setIs_show_discount(int is_show_discount) {
        this.is_show_discount = is_show_discount;
    }

    public String getTiJianType() {
        return tiJianType;
    }

    public void setTiJianType(String tiJianType) {
        this.tiJianType = tiJianType;
    }

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

    public String getDjdstatuss() {
        return djdstatuss;
    }

    public void setDjdstatuss(String djdstatuss) {
        this.djdstatuss = djdstatuss;
    }

    public String getBarcode_print_type() {
        return barcode_print_type;
    }

    public void setBarcode_print_type(String barcode_print_type) {
        this.barcode_print_type = barcode_print_type;
    }

    public String getZyb_barcode_print_type() {
        return zyb_barcode_print_type;
    }

    public void setZyb_barcode_print_type(String zyb_barcode_print_type) {
        this.zyb_barcode_print_type = zyb_barcode_print_type;
    }

    public ZybOccuHisDTO getZybOccuHisDTO() {
        return zybOccuHisDTO;
    }

    public void setZybOccuHisDTO(ZybOccuHisDTO zybOccuHisDTO) {
        this.zybOccuHisDTO = zybOccuHisDTO;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getWorkshop() {
        return workshop;
    }

    public void setWorkshop(String workshop) {
        this.workshop = workshop;
    }

    public String getWorktype() {
        return worktype;
    }

    public void setWorktype(String worktype) {
        this.worktype = worktype;
    }

    public String getStartdate() {
        return startdate;
    }

    public void setStartdate(String startdate) {
        this.startdate = startdate;
    }

    public String getEnddate() {
        return enddate;
    }

    public void setEnddate(String enddate) {
        this.enddate = enddate;
    }

    public String getCreate_date() {
        return create_date;
    }

    public void setCreate_date(String create_date) {
        this.create_date = create_date;
    }

    public String getZyb_id() {
        return zyb_id;
    }

    public void setZyb_id(String zyb_id) {
        this.zyb_id = zyb_id;
    }

    public String getRadiation() {
        return radiation;
    }

    public void setRadiation(String radiation) {
        this.radiation = radiation;
    }

    public String getMan_haur() {
        return man_haur;
    }

    public void setMan_haur(String man_haur) {
        this.man_haur = man_haur;
    }

    public String getCumulative_exposure() {
        return cumulative_exposure;
    }

    public void setCumulative_exposure(String cumulative_exposure) {
        this.cumulative_exposure = cumulative_exposure;
    }

    public String getHistory_excessive() {
        return history_excessive;
    }

    public void setHistory_excessive(String history_excessive) {
        this.history_excessive = history_excessive;
    }

    public String getExam_indicator() {
        return exam_indicator;
    }

    public void setExam_indicator(String exam_indicator) {
        this.exam_indicator = exam_indicator;
    }

    public String getApp_type() {
        return app_type;
    }

    public void setApp_type(String app_type) {
        this.app_type = app_type;
    }

    public String getSetentities() {
        return setentities;
    }

    public void setSetentities(String setentities) {
        this.setentities = setentities;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public String getZytjlb() {
        return zytjlb;
    }

    public void setZytjlb(String zytjlb) {
        this.zytjlb = zytjlb;
    }

    public String getZywhlb() {
        return zywhlb;
    }

    public void setZywhlb(String zywhlb) {
        this.zywhlb = zywhlb;
    }

    public String getZywhyslb() {
        return zywhyslb;
    }

    public void setZywhyslb(String zywhyslb) {
        this.zywhyslb = zywhyslb;
    }

    public long getExamset_id() {
        return examset_id;
    }

    public void setExamset_id(long examset_id) {
        this.examset_id = examset_id;
    }

    public String getHarmname() {
        return harmname;
    }

    public void setHarmname(String harmname) {
        this.harmname = harmname;
    }

    public String getConcentrations() {
        return concentrations;
    }

    public void setConcentrations(String concentrations) {
        this.concentrations = concentrations;
    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public String getIsradiation() {
        return isradiation;
    }

    public void setIsradiation(String isradiation) {
        this.isradiation = isradiation;
    }

    public String getOccusectorid() {
        return occusectorid;
    }

    public void setOccusectorid(String occusectorid) {
        this.occusectorid = occusectorid;
    }

    public String getOccutypeofworkid() {
        return occutypeofworkid;
    }

    public void setOccutypeofworkid(String occutypeofworkid) {
        this.occutypeofworkid = occutypeofworkid;
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


    public String getGroup_index() {
        return group_index;
    }

    public void setGroup_index(String group_index) {
        this.group_index = group_index;
    }

    public String getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(String employeeID) {
        this.employeeID = employeeID;
    }

    public String getScustomer_type_id() {
        return scustomer_type_id;
    }

    public void setScustomer_type_id(String scustomer_type_id) {
        this.scustomer_type_id = scustomer_type_id;
    }

    public String getCompanybatch_id() {
        return companybatch_id;
    }

    public void setCompanybatch_id(String companybatch_id) {
        this.companybatch_id = companybatch_id;
    }

    public String getItementities() {
        return itementities;
    }

    public void setItementities(String itementities) {
        this.itementities = itementities;
    }

    public String getExam_num() {
        return exam_num;
    }

    public void setExam_num(String exam_num) {
        this.exam_num = exam_num;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getChargingType() {
        return chargingType;
    }

    public void setChargingType(String chargingType) {
        this.chargingType = chargingType;
    }

    public long getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(long customer_id) {
        this.customer_id = customer_id;
    }

    public long getCustomer_type_id() {
        return customer_type_id;
    }

    public void setCustomer_type_id(long customer_type_id) {
        this.customer_type_id = customer_type_id;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getExam_type() {
        return exam_type;
    }

    public void setExam_type(String exam_type) {
        this.exam_type = exam_type;
    }

    public String getTime1() {
        return time1;
    }

    public void setTime1(String time1) {
        this.time1 = time1;
    }

    public String getTime2() {
        return time2;
    }

    public void setTime2(String time2) {
        this.time2 = time2;
    }

    public String getHansidjdflag() {
        return hansidjdflag;
    }

    public void setHansidjdflag(String hansidjdflag) {
        this.hansidjdflag = hansidjdflag;
    }

    public String getSftype() {
        return sftype;
    }

    public void setSftype(String sftype) {
        this.sftype = sftype;
    }

    public String getPerson_shenfen() {
        return person_shenfen;
    }

    public void setPerson_shenfen(String person_shenfen) {
        this.person_shenfen = person_shenfen;
    }

    public String getMz() {
        return mz;
    }

    public void setMz(String mz) {
        this.mz = mz;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBatch_num() {
        return Batch_num;
    }

    public void setBatch_num(String batch_num) {
        Batch_num = batch_num;
    }

    public String getComname() {
        return comname;
    }

    public void setComname(String comname) {
        this.comname = comname;
    }

    public String getBatch_name() {
        return batch_name;
    }

    public void setBatch_name(String batch_name) {
        this.batch_name = batch_name;
    }

    public long getGroup_id() {
        return group_id;
    }

    public void setGroup_id(long group_id) {
        this.group_id = group_id;
    }

    public long getDept_id() {
        return dept_id;
    }

    public void setDept_id(long dept_id) {
        this.dept_id = dept_id;
    }

    public long getExam_id() {
        return exam_id;
    }

    public void setExam_id(long exam_id) {
        this.exam_id = exam_id;
    }

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
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

    public String getDiseases() {
        return diseases;
    }

    public void setDiseases(String diseases) {
        this.diseases = diseases;
    }

    public String getDiagnosisdate() {
        return diagnosisdate;
    }

    public void setDiagnosisdate(String diagnosisdate) {
        this.diagnosisdate = diagnosisdate;
    }

    public String getDiagnosiscom() {
        return diagnosiscom;
    }

    public void setDiagnosiscom(String diagnosiscom) {
        this.diagnosiscom = diagnosiscom;
    }

    public String getDiagnosisnotice() {
        return diagnosisnotice;
    }

    public void setDiagnosisnotice(String diagnosisnotice) {
        this.diagnosisnotice = diagnosisnotice;
    }

    public String getDiseasereturn() {
        return diseasereturn;
    }

    public void setDiseasereturn(String diseasereturn) {
        this.diseasereturn = diseasereturn;
    }

    public String getRemark1() {
        return remark1;
    }

    public void setRemark1(String remark1) {
        this.remark1 = remark1;
    }

    public String getRemark2() {
        return remark2;
    }

    public void setRemark2(String remark2) {
        this.remark2 = remark2;
    }

    public String getRemark3() {
        return remark3;
    }

    public void setRemark3(String remark3) {
        this.remark3 = remark3;
    }

    public int getZyb_set_source() {
        return zyb_set_source;
    }

    public void setZyb_set_source(int zyb_set_source) {
        this.zyb_set_source = zyb_set_source;
    }

    public String getTime3() {
        return time3;
    }

    public String getTime4() {
        return time4;
    }

    public void setTime3(String time3) {
        this.time3 = time3;
    }

    public void setTime4(String time4) {
        this.time4 = time4;
    }
}