package com.hjw.zyb.service;

import java.util.List;

import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.zyb.DTO.ZybOccuhazardrelatorsinfoDTO;
import com.hjw.zyb.domain.ZybOccuhazardrelatorsinfo;
import com.hjw.zyb.model.ZybOccuhazardrelatorsinfoModel;
import com.synjones.framework.exception.ServiceException;



public interface ZybOccuhazardrelatorsinfoService {
	
	
	/**
	 * 职业危害相关性添加
	     * @Title: addOccuwhxg   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param r
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: ZybOccuhazardrelatorsinfo      
	     * @throws
	 */
	public ZybOccuhazardrelatorsinfo addOccuwhxg(ZybOccuhazardrelatorsinfo r) throws ServiceException;
	
	
	/**
	 * 职业危害相关信息删除
	     * @Title: deleteOccuwhxg   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param r
	     * @param: @throws ServiceException      
	     * @return: void      
	     * @throws
	 */
	public void deleteOccuwhxg(ZybOccuhazardrelatorsinfo r) throws ServiceException;
	
	
	
	/**
	 * 职业危害相关信息更新
	     * @Title: updateOccuwhxg   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param r
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: ZybOccuhazardrelatorsinfo      
	     * @throws
	 */
	public ZybOccuhazardrelatorsinfo updateOccuwhxg(ZybOccuhazardrelatorsinfo r) throws ServiceException;
	
	
	
	/**
	 * 职业危害相关信心id查询
	     * @Title: queryById   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param id
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: ZybOccuhazardrelatorsinfo      
	     * @throws
	 */
	public ZybOccuhazardrelatorsinfo queryById(String id) throws ServiceException;
	
	
	
	/**
	 * 职业危害相关信息分页查询
	     * @Title: queryByOccuwhxg   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param model
	     * @param: @param pagesize
	     * @param: @param pageno
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: PageReturnDTO      
	     * @throws
	 */
	public PageReturnDTO queryByOccuwhxg(ZybOccuhazardrelatorsinfoModel model,int pagesize,int pageno) throws ServiceException;
	
	
	/**
	 * 职业危害相关信息id查询
	     * @Title: getByid   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param id
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: ZybOccuhazardrelatorsinfoDTO      
	     * @throws
	 */
	public ZybOccuhazardrelatorsinfoDTO getByid(String id)throws ServiceException;
	
	
	
	/**
	 * 职业危害类别树加载
	     * @Title: getOccuwhlbtree   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<ZybOccuhazardrelatorsinfoDTO>      
	     * @throws
	 */
	public List<ZybOccuhazardrelatorsinfoDTO> getOccuwhlbtree() throws ServiceException;
	
	
	/**
	 * 职业危害类别数据加载
	     * @Title: getOccuwhlbList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<ZybOccuhazardrelatorsinfoDTO>      
	     * @throws
	 */
	public List<ZybOccuhazardrelatorsinfoDTO> getOccuwhlbList() throws ServiceException;
	
	
	
	/**
	 * 职业危害因素数据加载
	     * @Title: getOccuwhys   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<ZybOccuhazardrelatorsinfoDTO>      
	     * @throws
	 */
	public List<ZybOccuhazardrelatorsinfoDTO> getOccuwhys() throws ServiceException;
	
	
	/**
	 * 体检类别数据加载
	     * @Title: getOccuphyclass   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<ZybOccuhazardrelatorsinfoDTO>      
	     * @throws
	 */
	public List<ZybOccuhazardrelatorsinfoDTO> getOccuphyclass() throws ServiceException;
	
	
	/**
	 * 体检类别批量删除
	     * @Title: deletes   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param ids
	     * @param: @throws ServiceException      
	     * @return: void      
	     * @throws
	 */
	public void deletes(String ids) throws ServiceException;
	
}
