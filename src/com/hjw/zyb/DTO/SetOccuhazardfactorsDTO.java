package com.hjw.zyb.DTO;


/**
 * zr
 * 套餐因素关系
 */
public class SetOccuhazardfactorsDTO {

    private long id;
    private long set_id;
    private String hazard_code;
    private String occuphyexaclassID;
    private int hazard_year;
    private long creater;
    private String create_time;
    private String occuphyexaclass_name;
    private String hazard_name;

    public String getOccuphyexaclass_name() {
        return occuphyexaclass_name;
    }

    public void setOccuphyexaclass_name(String occuphyexaclass_name) {
        this.occuphyexaclass_name = occuphyexaclass_name;
    }

    public String getHazard_name() {
        return hazard_name;
    }

    public void setHazard_name(String hazard_name) {
        this.hazard_name = hazard_name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
}