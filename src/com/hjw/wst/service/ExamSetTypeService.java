package com.hjw.wst.service;

import java.util.List;

import org.hibernate.service.spi.ServiceException;

import com.hjw.wst.DTO.ExamSetTypeDTO;
import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.wst.DTO.UserDTO;
import com.hjw.wst.model.ExamSetTypeModel;

/**
 * 套餐类别维护功能
     * @Title:  火箭蛙体检管理系统   
     * @Package com.hjw.wst.service   
     * @Description:  
     * @author: yangm     
     * @date:   2017年10月27日 上午10:41:15   
     * @version V2.0.0.0
 */
public interface ExamSetTypeService {
	/**
	 * 类别列表
	     * @Title: getqueryExamSetTypeList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws SQLException
	     * @param: @throws ServiceException      
	     * @return: List      
	     * @throws
	 */
	public PageReturnDTO getqueryExamSetTypeList(ExamSetTypeModel model,int page,int pageSize) throws  ServiceException;
	/**
	 * 添加类别
	     * @Title: getqueryExamSetTypeList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param model
	     * @param: @return
	     * @param: @throws SQLException
	     * @param: @throws ServiceException      
	     * @return: List      
	     * @throws
	 */
	public void saveExamSetTypeList(ExamSetTypeModel model,UserDTO user) throws  ServiceException;
	/**
	 * 修改类别
	     * @Title: updateSetTypeList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param model
	     * @param: @throws SQLException
	     * @param: @throws ServiceException      
	     * @return: void      
	     * @throws
	 */
	public void updateSetTypeList(ExamSetTypeModel model,UserDTO user) throws  ServiceException;
	/**
	 * 删除类别
	     * @Title: deleteExamSetTypeList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param model
	     * @param: @throws SQLException
	     * @param: @throws ServiceException      
	     * @return: void      
	     * @throws
	 */
	public void deleteExamSetTypeList(ExamSetTypeModel model) throws   ServiceException;
	/**
	 * 根据id查询类别
	     * @Title: getExamSetTypeId   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param model
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: ExamSetTypeDTO      
	     * @throws
	 */
	public ExamSetTypeDTO getExamSetTypeId(ExamSetTypeModel model) throws   ServiceException;
	/**
	 * 套餐获取类别
	     * @Title: getExamSetTypeId   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param model
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: ExamSetTypeDTO      
	     * @throws
	 */
	public List<ExamSetTypeDTO> getExamSet_typeList() throws   ServiceException;

}
