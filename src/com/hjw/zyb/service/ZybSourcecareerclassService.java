package com.hjw.zyb.service;


import java.util.List;

import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.zyb.DTO.ZybSourcecareerclassDTO;
import com.hjw.zyb.domain.ZybSourcecareerclass;
import com.synjones.framework.exception.ServiceException;



public interface ZybSourcecareerclassService {
	

	/**
	 * 照射源分类添加
	     * @Title: addSourcecareerclass   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param c
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: ZybSourcecareerclass      
	     * @throws
	 */
	public ZybSourcecareerclass addSourcecareerclass(ZybSourcecareerclass c) throws ServiceException; 
	
	
	/**照射源分类删除
	 * 
	     * @Title: deleteSourcecareerclass   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param c
	     * @param: @throws ServiceException      
	     * @return: void      
	     * @throws
	 */
	public void deleteSourcecareerclass(ZybSourcecareerclass c)throws ServiceException; 
	
	
	/**
	 * 照射源分类更新
	     * @Title: updateSourcecareerclass   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param c
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: ZybSourcecareerclass      
	     * @throws
	 */
	public ZybSourcecareerclass updateSourcecareerclass(ZybSourcecareerclass c) throws ServiceException;
	
	
	/**
	 * 照射源分类id查询
	     * @Title: queryByid   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param id
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: ZybSourcecareerclass      
	     * @throws
	 */
	public ZybSourcecareerclass queryByid(String id)throws ServiceException;
	
	public ZybSourcecareerclassDTO queryByID(String id) throws ServiceException;
	
	
	/**
	 * 照射源分类分页查询
	     * @Title: queryBySourcecareerclasspage   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param sc_classcode
	     * @param: @param sc_classname
	     * @param: @param pagesize
	     * @param: @param pageno
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: PageReturnDTO      
	     * @throws
	 */
	public PageReturnDTO queryBySourcecareerclasspage(String sc_classcode,String sc_classname,int pagesize,int pageno) throws ServiceException;
	
	
	/**
	 * 照射源分类编码查询
	     * @Title: queryBycode   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param sc_classcode
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: ZybSourcecareerclass      
	     * @throws
	 */
	public ZybSourcecareerclass queryBycode(String sc_classcode, String sc_classid) throws ServiceException;
	
	
	/**
	 * 照射源查询全部
	     * @Title: getS_name   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<ZybSourcecareerclassDTO>      
	     * @throws
	 */
	public List<ZybSourcecareerclassDTO> getS_name() throws ServiceException;
	
}
