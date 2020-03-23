package com.hjw.wst.service;

import java.util.List;

import com.hjw.wst.DTO.CustomerfileinformationqueryDTO;
import com.hjw.wst.DTO.PageReturnDTO;
import com.synjones.framework.exception.ServiceException;

/**
 * 
     * @Title:  火箭蛙体检管理系统   
     * @Package com.hjw.wst.service   
     * @Description:  档案对比--参检用户查询
     * @author: zr     
     * @date:   2016年11月14日 下午4:18:15   
     * @version V2.0.0.0
 */
public interface FilecontrastService {
	/**
	 * 
	     * @Title: queryCustomerInfo   
	     * @Description: TODO(检索用户参检用户信息)   
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: PageReturnDTO      
	     * @throws
	 */
	public List<CustomerfileinformationqueryDTO> queryCustomerInfo(CustomerfileinformationqueryDTO dto, String center_num) throws  ServiceException;
	/**
	 * 
	     * @Title: getCustomerInfo   
	     * @Description: TODO(获取健康档案)   
	     * @param: @param dto
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: CustomerfileinformationqueryDTO      
	     * @throws
	 */
	public  CustomerfileinformationqueryDTO  getCustomerInfo(CustomerfileinformationqueryDTO dto,String center_num) throws  ServiceException;
	

}
