package com.hjw.wst.DTO;

import java.util.List;
/**
 * 登记台加项3项目样式数据
     * @Title:  火箭蛙体检管理系统   
     * @Package com.hjw.wst.DTO   
     * @Description:  
     * @author: yangm     
     * @date:   2018年3月6日 上午10:04:24   
     * @version V2.0.0.0
 */
public class DJTItemDTO {
	
	public String dep_name;
	public String dep_id;
	public List<ChargingItemDTO> li;
	
	public String getDep_name() {
		return dep_name;
	}
	public void setDep_name(String dep_name) {
		this.dep_name = dep_name;
	}
	public String getDep_id() {
		return dep_id;
	}
	public void setDep_id(String dep_id) {
		this.dep_id = dep_id;
	}
	public List<ChargingItemDTO> getLi() {
		return li;
	}
	public void setLi(List<ChargingItemDTO> li) {
		this.li = li;
	}
	
	
}
