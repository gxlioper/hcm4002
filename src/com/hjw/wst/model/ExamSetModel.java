package com.hjw.wst.model;

/**
 * @version V2.0.0.0
 * @Title: 火箭蛙体检管理系统
 * @Package com.hjw.wst.domain
 * @Description: 体检项目套餐
 * @author: zhangrui
 * @date: 2016年10月10日 上午10:12:10
 */
public class ExamSetModel implements java.io.Serializable {
    private static final long serialVersionUID = -97502163798576023L;

    private long id;

    private String ids;

    private long company_id;

    private String set_num;

    private String set_name = "";

    private String set_pinyin = "";

    private String sex;

    private double set_discount;

    private double set_amount;

    private double survey_minScore;

    private double survey_maxScore;

    private String disease_name;

    private String is_Active;

    private long creater;

    private String create_time;

    private String create_times;

    private long updater;

    private String update_time;

    private String update_times;

    private double set_amountq;

    private double price;//原价

    private String exam_set_type;//套餐类别

    private String webResource;

    private String app_type;

    private long set_seq;

    private String hazardfactorsid;//职业危害因素编码

    private String occuphyexaclassid;//职业体检类别编码

    private int isSynchro;

    private String startStop;//启/停

    private int is_show_discount;

    private long settreeid;

    private String exam_type;

    private String center_num;

    //套餐危害关系
    private long set_id;
    private String hazard_code;
    private String occuphyexaclassID;
    private int hazard_year;
    private String hazard_list;

    public String getHazard_list() {
        return hazard_list;
    }

    public void setHazard_list(String hazard_list) {
        this.hazard_list = hazard_list;
    }

    public long getSet_id() {
        return set_id;
    }

    public void setSet_id(long set_id) {
        this.set_id = set_id;
    }

    public String getHazard_code() {
        return hazard_code;
    }

    public void setHazard_code(String hazard_code) {
        this.hazard_code = hazard_code;
    }

    public String getOccuphyexaclassID() {
        return occuphyexaclassID;
    }

    public void setOccuphyexaclassID(String occuphyexaclassID) {
        this.occuphyexaclassID = occuphyexaclassID;
    }

    public int getHazard_year() {
        return hazard_year;
    }

    public void setHazard_year(int hazard_year) {
        this.hazard_year = hazard_year;
    }

    public long getSettreeid() {
        return settreeid;
    }

    public void setSettreeid(long settreeid) {
        this.settreeid = settreeid;
    }

    public String getExam_type() {
        return exam_type;
    }

    public void setExam_type(String exam_type) {
        this.exam_type = exam_type;
    }

    public int getIs_show_discount() {
        return is_show_discount;
    }

    public void setIs_show_discount(int is_show_discount) {
        this.is_show_discount = is_show_discount;
    }

    public int getIsSynchro() {
        return isSynchro;
    }

    public void setIsSynchro(int isSynchro) {
        this.isSynchro = isSynchro;
    }

    public String getHazardfactorsid() {
        return hazardfactorsid;
    }

    public void setHazardfactorsid(String hazardfactorsid) {
        this.hazardfactorsid = hazardfactorsid;
    }

    public String getOccuphyexaclassid() {
        return occuphyexaclassid;
    }

    public void setOccuphyexaclassid(String occuphyexaclassid) {
        this.occuphyexaclassid = occuphyexaclassid;
    }

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

    public long getSet_seq() {
        return set_seq;
    }

    public void setSet_seq(long set_seq) {
        this.set_seq = set_seq;
    }

    public String getApp_type() {
        return app_type;
    }

    public void setApp_type(String app_type) {
        this.app_type = app_type;
    }

    public String getWebResource() {
        return webResource;
    }

    public void setWebResource(String webResource) {
        this.webResource = webResource;
    }

    public String getExam_set_type() {
        return exam_set_type;
    }

    public void setExam_set_type(String exam_set_type) {
        this.exam_set_type = exam_set_type;
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

    public String getSet_num() {
        return set_num;
    }

    public void setSet_num(String set_num) {
        this.set_num = set_num;
    }

    public String getSet_name() {
        return set_name;
    }

    public void setSet_name(String set_name) {
        this.set_name = set_name;
    }

    public String getSet_pinyin() {
        return set_pinyin;
    }

    public void setSet_pinyin(String set_pinyin) {
        this.set_pinyin = set_pinyin;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public double getSet_discount() {
        return set_discount;
    }

    public void setSet_discount(double set_discount) {
        this.set_discount = set_discount;
    }

    public double getSet_amount() {
        return set_amount;
    }

    public void setSet_amount(double set_amount) {
        this.set_amount = set_amount;
    }

    public double getSurvey_minScore() {
        return survey_minScore;
    }

    public void setSurvey_minScore(double survey_minScore) {
        this.survey_minScore = survey_minScore;
    }

    public double getSurvey_maxScore() {
        return survey_maxScore;
    }

    public void setSurvey_maxScore(double survey_maxScore) {
        this.survey_maxScore = survey_maxScore;
    }

    public String getDisease_name() {
        return disease_name;
    }

    public void setDisease_name(String disease_name) {
        this.disease_name = disease_name;
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

    public long getUpdater() {
        return updater;
    }

    public void setUpdater(long updater) {
        this.updater = updater;
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

    public String getCreate_times() {
        return create_times;
    }

    public void setCreate_times(String create_times) {
        this.create_times = create_times;
    }

    public String getUpdate_times() {
        return update_times;
    }

    public void setUpdate_times(String update_times) {
        this.update_times = update_times;
    }

    public double getSet_amountq() {
        return set_amountq;
    }

    public void setSet_amountq(double set_amountq) {
        this.set_amountq = set_amountq;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getStartStop() {
        return startStop;
    }

    public void setStartStop(String startStop) {
        this.startStop = startStop;
    }

    public String getCenter_num() {
        return center_num;
    }

    public void setCenter_num(String center_num) {
        this.center_num = center_num;
    }

}