package com.hjw.wst.service;

import java.util.List;

import com.hjw.wst.DTO.ExaminationItemDTO;
import com.hjw.wst.DTO.ItemResultLibDTO;
import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.wst.domain.ExaminationItem;
import com.hjw.wst.domain.ItemResultLib;
import com.synjones.framework.exception.ServiceException;

/**
 * 
     * @Title:  火箭蛙体检管理系统   
     * @Package com.hjw.wst.service   
     * @Description:  项目结果知识库
     * @author: zr     
     * @date:   2016年10月26日 上午11:17:23   
     * @version V2.0.0.0
 */
public interface ItemResultLibService {
	/**
	 * 
	     * @Title: queryItemResultLib   
	     * @Description: TODO(获取项目结果知识库列表)   
	     * @param: @param dto
	     * @param: @param page
	     * @param: @param pageSize
	     * @param: @throws ServiceException      
	     * @return: void      
	     * @throws
	 */
	public PageReturnDTO queryItemResultLib(ItemResultLibDTO dto,int page,int pageSize,String center_num ) throws  ServiceException;
	/**
	 * 
	     * @Title: getExaminationItem   
	     * @Description: TODO(获取所有检查项目)   
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<ExaminationItem>      
	     * @throws
	 */
	public List<ExaminationItemDTO>  getExaminationItem(long dep_id) throws  ServiceException;
	/**
	 * @return 
	 * 
	     * @Title: addItemResultLib   
	     * @Description: TODO(添加项目结果知识)   
	     * @param: @throws ServiceException      
	     * @return: void      
	     * @throws
	 */
	public ItemResultLib addItemResultLib(ItemResultLib item) throws ServiceException;
	/***
	 * 
	     * @Title: findItemResultLib   
	     * @Description: TODO(根据Id项目结果知识库)   
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: ItemResultLib      
	     * @throws
	 */
	public  ItemResultLib   findItemResultLib(Long itme) throws  ServiceException;
	/**
	 * 
	     * @Title: updateItemResultLib   
	     * @Description: TODO(修改项目结果知识库)   
	     * @param: @param item
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: ItemResultLib      
	     * @throws
	 */
	public ItemResultLib updateItemResultLib(ItemResultLib item)   throws  ServiceException;
	/**
	 * 
	     * @Title: delItemResultLib   
	     * @Description: TODO(删除项目结果)   
	     * @param: @throws ServiceException      
	     * @return: void      
	     * @throws
	 */
	public void  delItemResultLib(String ids)  throws  ServiceException;
	/**
	 * 
	     * @Title: getDepItemResultLib   
	     * @Description: TODO(科室和检查项目联动)   
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<ItemResultLib>      
	     * @throws
	 */
	public List<ExaminationItemDTO>  getDepItemResultLib(ItemResultLibDTO dep_id,String center_num ) throws  ServiceException;
	/**
	 * 
	     * @Title: getExaminationItemResult   
	     * @Description: TODO(项目结果知识库获取项目)   
	     * @param: @param id
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: ExaminationItem      
	     * @throws
	 */
	public  ExaminationItem  getExaminationItemResult(String id) throws ServiceException;
	/**
	 * 
	     * @Title: updateExaminationItems   
	     * @Description: TODO(修改检查项目默认值)   
	     * @param: @param id
	     * @param: @param vlue
	     * @param: @throws ServiceException      
	     * @return: void      
	     * @throws
	 */
	public void updateExaminationItems_up(ExaminationItem	examinationI)throws ServiceException;
	/**
	 * 修改验证项目结果是否存在
	     * @Title: getExaminationItem_default   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: int      
	     * @throws
	 */
	public  int  getExaminationItem_default(long  id) throws ServiceException;
}
