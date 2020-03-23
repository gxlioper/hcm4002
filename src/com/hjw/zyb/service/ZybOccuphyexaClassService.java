package com.hjw.zyb.service;


import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.zyb.DTO.ZybOccuphyexaClassDTO;
import com.hjw.zyb.domain.ZybOccuphyexaClass;
import com.hjw.zyb.model.ZybOccuphyexaClassModel;
import com.synjones.framework.exception.ServiceException;

/**
 * 职业体检类别
     * @Title:  火箭蛙体检管理系统   
     * @Package com.hjw.zyb.service   
     * @Description:  
     * @author: zr     
     * @date:   2017年3月28日 上午10:27:36   
     * @version V2.0.0.0
 */
public interface ZybOccuphyexaClassService {
	public PageReturnDTO  queryOccuphyexaClass(ZybOccuphyexaClassModel	model,int  Page,int  PageSize) throws  ServiceException;
	/**
	 * 添加职业体检类别
	     * @Title: saveOccuphyexaClass   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param model
	     * @param: @param user
	     * @param: @throws ServiceException      
	     * @return: void      
	     * @throws
	 */
	public void saveOccuphyexaClass(ZybOccuphyexaClass	occuphyexaClass) throws  ServiceException;
	/**
	 * 修改职业体检类别
	     * @Title: updateOccuphyexaClass   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param model
	     * @param: @throws ServiceException      
	     * @return: void      
	     * @throws
	 */
	public void updateOccuphyexaClass(ZybOccuphyexaClass	occuphyexaClass) throws ServiceException;
	/**
	 * 删除职业体检类别
	     * @Title: deleteOccuphyexaClass   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param model
	     * @param: @throws ServiceException      
	     * @return: void      
	     * @throws
	 */
	public void deleteOccuphyexaClass(String	occuphyexaClass) throws ServiceException;
	/**
	 * 获取职业体检类别
	     * @Title: getOccuphyexaClass   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param id
	     * @param: @throws ServiceException      
	     * @return: void      
	     * @throws
	 */
	public ZybOccuphyexaClassDTO getOccuphyexaClass(String	idOrName) throws ServiceException;
	
}
