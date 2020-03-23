package com.hjw.crm.DTO;

import java.util.List;

import com.hjw.wst.DTO.ExamSetDTO;
import com.hjw.wst.DTO.GroupChargingItemDTO;

/**
 * 复制项目登记台啊加项
     * @Title:  火箭蛙体检管理系统   
     * @Package com.hjw.crm.DTO   
     * @Description:  
     * @author: yangm     
     * @date:   2017年10月25日 下午4:30:45   
     * @version V2.0.0.0
 */
public class CopyItemDTO {
	 List<GroupChargingItemDTO>  li;
	 List<ExamSetDTO> se;
	 
	public List<GroupChargingItemDTO> getLi() {
		return li;
	}
	public void setLi(List<GroupChargingItemDTO> li) {
		this.li = li;
	}
	public List<ExamSetDTO> getSe() {
		return se;
	}
	public void setSe(List<ExamSetDTO> se) {
		this.se = se;
	}
	 
	 
}
