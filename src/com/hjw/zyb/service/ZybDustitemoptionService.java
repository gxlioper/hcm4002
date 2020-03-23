package com.hjw.zyb.service;


import java.util.List;

import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.wst.DTO.UserDTO;
import com.hjw.zyb.DTO.ZybDustitemoptionDTO;
import com.hjw.zyb.domain.ZybDustitemoption;
import com.hjw.zyb.model.ZybdustitemoptionModel;
import com.synjones.framework.exception.ServiceException;



public interface ZybDustitemoptionService {
	

	/**
	 * 粉尘胸片参数添加
	     * @Title: adddustitemoption   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param o
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: ZybDustitemoption      
	     * @throws
	 */
	public ZybDustitemoption adddustitemoption(ZybDustitemoption o) throws ServiceException;
	
	/**
	 * 粉尘胸片参数删除
	     * @Title: deletedustitemoption   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param o
	     * @param: @throws ServiceException      
	     * @return: void      
	     * @throws
	 */
	public void deletedustitemoption(ZybDustitemoption o) throws ServiceException;
	
	/**
	 * 粉尘胸片参数 修改
	     * @Title: updatedustitemoption   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param o
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: ZybDustitemoption      
	     * @throws
	 */
	public ZybDustitemoption updatedustitemoption(ZybDustitemoption o) throws ServiceException;
	
	/**
	 * 粉尘胸片参数根据主键查询
	     * @Title: queryByID   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param id
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: ZybDustitemoption      
	     * @throws
	 */
	public ZybDustitemoption queryByID(String id) throws ServiceException;
	
	/**
	 * 粉尘胸片参数分页查询
	     * @Title: querydustitemoptionList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param model
	     * @param: @param pagesize
	     * @param: @param pageno
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: PageReturnDTO      
	     * @throws
	 */
	public PageReturnDTO querydustitemoptionList(ZybdustitemoptionModel model,int pagesize,int pageno,UserDTO user) throws ServiceException;
	
	/**
	 * 粉尘胸片类别查询全部
	     * @Title: getdustitemList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<ZybDustitemoptionDTO>      
	     * @throws
	 */
	public List<ZybDustitemoptionDTO> getdustitemList() throws ServiceException;
	
	/**
	 * 粉尘胸片参数id查询
	     * @Title: getone   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param id
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: ZybDustitemoptionDTO      
	     * @throws
	 */
	public ZybDustitemoptionDTO  getone(String id,UserDTO user) throws ServiceException;
	
	
	/**
	 * 粉尘胸片参数批量删除
	     * @Title: deletes   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param ids
	     * @param: @throws ServiceException      
	     * @return: void      
	     * @throws
	 */
	public void deletes(String ids)throws ServiceException;
	
}
