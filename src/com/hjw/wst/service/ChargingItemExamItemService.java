package com.hjw.wst.service;

import com.hjw.wst.domain.ChargingItemExamItem;
import com.synjones.framework.exception.ServiceException;

/**
 * 
     * @Title:  火箭蛙体检管理系统   
     * @Package com.hjw.wst.service   
     * @Description:  收费项目检查项目关系表
     * @author: 张瑞    
     * @date:   2016年9月24日 上午9:18:11   
     * @version V2.0.0.0
 */
public interface  ChargingItemExamItemService {
	/**
	 * 
	     * @Title: getItemId   
	     * @Description: TODO(验证检查项目编码是否存在)   
	     * @param: @param id
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: ChargingItemExamItem      
	     * @throws
	 */
	public ChargingItemExamItem getItemId(long id)  throws ServiceException;
}
