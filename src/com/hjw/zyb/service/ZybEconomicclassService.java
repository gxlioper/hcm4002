package com.hjw.zyb.service;

import java.util.List;

import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.zyb.domain.ZybEconomicClass;
import com.hjw.zyb.model.ZybEconomicclassModel;
import com.synjones.framework.exception.ServiceException;

public interface ZybEconomicclassService {

	/**
	 * 经济类型_增
	     * @Title: addEconomic   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param ec
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: ZYB_EconomicClass      
	     * @throws
	 */
	public void addEconomic(ZybEconomicClass c) throws ServiceException;
		/**
		 * 经济类型_删
		     * @Title: deleteEconomic   
		     * @Description: TODO(这里用一句话描述这个方法的作用)   
		     * @param: @param ec
		     * @param: @throws ServiceException      
		     * @return: void      
		     * @throws
		 */
	public void deleteEconomic(ZybEconomicClass c) throws ServiceException;

	/**
	 * 经济类型_改
	     * @Title: updateEconomic   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param ec
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: ZYB_EconomicClass      
	     * @throws
	 */
	public void updateEconomic(ZybEconomicClass c) throws ServiceException;

	/**
	 *查询全部
	     * @Title: queryByAll   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param ddt
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List      
	     * @throws
	 */
	@SuppressWarnings("rawtypes")
	public List queryByAll() throws ServiceException;
	
	/**
	 * 经济类型_ID查
	     * @Title: queryById   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param id
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: ZYB_EconomicClass      
	     * @throws
	 */
	public ZybEconomicClass queryById(String id) throws ServiceException;

	/**
	 * 编码查询
	     * @Title: queryByNum   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param data_code
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: ZybEconomicClass      
	     * @throws
	 */
	public  ZybEconomicClass queryByNum(String code) throws ServiceException;
	
	
	/**
	 * 分页查询
	     * @Title: queryPageDaDt   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param model
	     * @param: @param pageno
	     * @param: @param pagesize
	     * @param: @return      
	     * @return: PageReturnDTO      
	     * @throws
	 */
	public PageReturnDTO queryPageEconomicclass(ZybEconomicclassModel model,int pageno, int pagesize);
	
	
	/**
	 * 批量删除
	     * @Title: deletes   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param id
	     * @param: @throws ServiceException      
	     * @return: void      
	     * @throws
	 */
	public void deletes(String ids)throws ServiceException;
	
	
	/**
	 * id 查询
	     * @Title: getByidList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param id
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<ZybEconomicClass>      
	     * @throws
	 */
	public List<ZybEconomicClass> getByidList(String id)throws ServiceException;
	
	
	/**
	 * 获取父级单位名称
	     * @Title: getfujidanwei   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: ZybEconomicClass      
	     * @throws
	 */
	public  ZybEconomicClass getfujidanwei(String id)throws ServiceException;
	
	
}
