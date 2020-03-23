package com.hjw.wst.service;

import java.util.List;

import javax.sql.rowset.serial.SerialException;

import com.hjw.wst.DTO.GroupSchedulingDTO;
import com.hjw.wst.DTO.UserDTO;
import com.hjw.wst.domain.GroupScheduling;
import com.hjw.wst.model.GroupSchedulingModel;
import com.synjones.framework.exception.ServiceException;

/**
 * 排期
     * @Title:  火箭蛙体检管理系统   
     * @Package com.hjw.wst.service   
     * @Description:  
     * @author: zr     
     * @date:   2017年7月6日 上午11:40:39   
     * @version V2.0.0.0
 */
public interface GroupSchedulingService {
	/**
	 * 获取排期
	     * @Title: deleteGroupSchedulingList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<GroupSchedulingDTO>      
	     * @throws
	 */
	public List<GroupSchedulingDTO>  getGroupSchedulingList(GroupSchedulingModel   model) throws  ServiceException;
	/**
	 * 修改
	     * @Title: getGroupSchedulingList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<GroupSchedulingDTO>      
	     * @throws
	 */
	public void  updateGroupScheduling(GroupSchedulingModel model,UserDTO  user) throws  ServiceException;
	/**
	 * 添加排期
	     * @Title: addGroupScheduling   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @throws ServiceException      
	     * @return: void      
	     * @throws
	 */
	public GroupScheduling addGroupScheduling(GroupScheduling  g) throws  ServiceException;
	/**
	 * 删除排期
	     * @Title: deleteGroupSchedulingList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<GroupSchedulingDTO>      
	     * @throws
	 */
	public void  deleteGroupSchedulingList(long id) throws  ServiceException;
}
