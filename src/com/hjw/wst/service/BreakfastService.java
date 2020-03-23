package com.hjw.wst.service;


import java.util.List;

import com.hjw.wst.DTO.CustomerExamDTO;
import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.wst.DTO.UserDTO;
import com.hjw.wst.domain.ExaminfoChargingItem;
import com.hjw.wst.model.ExaminfoChargingItemModel;
import com.synjones.framework.exception.ServiceException;

/**
 * 
 * <菜单、功能、角色操作> <功能详细描述>
 * 
 * @author yangm
 * @version [3.0.0, Nov 4, 2011]
 * @see [相关类/方法]
 * @since [一卡通网站查询系统]
 */
public interface BreakfastService {

	/**
	 * 批量更新
	     * @Title: updateBreakStatus   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param ids
	     * @param: @throws ServiceException      
	     * @return: void      
	     * @throws
	 */
	//public ExaminfoChargingItem updateBreakStatus(ExaminfoChargingItem e) throws ServiceException;
	public void updateBreakStatus(String ids,UserDTO user) throws  ServiceException;
	/**
	 * 检查项目list
	     * @Title: queryItemList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param exam_num
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<CustomerExamDTO>      
	     * @throws
	 */
	public List<CustomerExamDTO> queryItemList(String exam_num,UserDTO user) throws ServiceException;
	
	/**
	 * 
	     * @Title: queryById   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param id
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: ExamInfo      
	     * @throws
	 */
	public ExaminfoChargingItem queryById(long  id) throws ServiceException;
	
	/**
	 * 查询影像科室人员列表
	     * @Title: getExamInfoList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param model
	     * @param: @param user
	     * @param: @param rows
	     * @param: @param page
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: PageReturnDTO      
	     * @throws
	 */
	public PageReturnDTO getExamInfoList(ExaminfoChargingItemModel model,UserDTO user, int rows, int page) throws ServiceException ;
}
	
