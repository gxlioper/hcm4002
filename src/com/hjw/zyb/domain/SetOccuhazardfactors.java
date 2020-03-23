package com.hjw.zyb.domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

/**
 * zr
 * 套餐因素关系
 */
@Entity
@Table(name = "set_occuhazardfactors")
public class SetOccuhazardfactors implements java.io.Serializable{
    @Id
    @GenericGenerator(name = "idGenerator", strategy = "identity")
    @GeneratedValue(generator = "idGenerator")
    private long id;

    @Column(name = "set_id")
    private long set_id;
    @Column(name = "hazard_code")
    private String hazard_code;
    @Column(name = "occuphyexaclassID")
    private String occuphyexaclassID;
    @Column(name = "hazard_year")
    private int hazard_year;
    @Column(name = "creater")
    private long creater;
    @Column(name = "create_time")
    private Date create_time;

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

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }
}
