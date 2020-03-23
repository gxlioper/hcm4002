package com.hjw.wst.domain;

import javax.persistence.Embeddable;

@Embeddable
public class ChargingItemVScenterPKID implements java.io.Serializable{
	
	private String center_num;	
	private String charging_item_code;			// 收费项目编码
	public String getCenter_num() {
		return center_num;
	}
	public void setCenter_num(String center_num) {
		this.center_num = center_num;
	}
	public String getCharging_item_code() {
		return charging_item_code;
	}
	public void setCharging_item_code(String charging_item_code) {
		this.charging_item_code = charging_item_code;
	}
	
	@Override
    public boolean equals(Object other) {
        if(this == other)
            return true;
        if(other == null)
            return false;
        if(!(other instanceof ChargingItemVScenterPKID))
            return false;
         
        ChargingItemVScenterPKID otherStudentId = (ChargingItemVScenterPKID) other;
         
        return this.getCenter_num() == otherStudentId.center_num &&
               this.getCharging_item_code() == otherStudentId.charging_item_code;
    }
     
    @Override
    public int hashCode() {
        return this.center_num.hashCode() + this.charging_item_code.hashCode();
    }
}
