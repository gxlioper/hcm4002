package com.hjw.zyb.service;

import java.sql.SQLException;

import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.zyb.domain.ZybOccuhazardClass;
import com.hjw.zyb.model.ZybOccuhazardClassModel;
import com.synjones.framework.exception.ServiceException;

/**
 * 职业危害类别
     * @Title:  火箭蛙体检管理系统   
     * @Package com.hjw.zyb.service   
     * @Description:  
     * @author: zr     
     * @date:   2017年3月29日 下午4:54:41   
     * @version V2.0.0.0
 */
public interface ZybOccuhazardClassService {
	/**
	 * 职业危害类别列表
	     * @Title: queryOccuhazardClassList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param model
	     * @param: @param page
	     * @param: @param pageSize
	     * @param: @return
	     * @param: @throws SQLException
	     * @param: @throws ServiceException      
	     * @return: PageReturnDTO      
	     * @throws
	 */
	public PageReturnDTO  queryOccuhazardClassList(ZybOccuhazardClassModel model,int page,int pageSize) throws SQLException,ServiceException;
	/**
	 * 删除职业危害类别
	     * @Title: deleteOccuhazardClass   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param ids
	     * @param: @throws SQLException
	     * @param: @throws ServiceException      
	     * @return: void      
	     * @throws
	 */
	public void deleteOccuhazardClass(String ids) throws SQLException,ServiceException;
	/**
	 * 保存职业危害类别
	     * @Title: saveOccuhazardClass   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param occClass
	     * @param: @throws SQLException
	     * @param: @throws ServiceException      
	     * @return: void      
	     * @throws
	 */
	public void saveOccuhazardClass(ZybOccuhazardClass   occClass) throws SQLException,ServiceException;
	/**
	 * 修改职业危害类别
	     * @Title: updateOccuhazardClass   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @throws SQLException
	     * @param: @throws ServiceException      
	     * @return: void      
	     * @throws
	 */
	public void updateOccuhazardClass(ZybOccuhazardClassModel  occClass) throws SQLException,ServiceException;
	/**
	 * 获取职业危害类别
	     * @Title: getZYB_OccuhazardClass   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws SQLException
	     * @param: @throws ServiceException      
	     * @return: ZYB_OccuhazardClass      
	     * @throws
	 */
	public ZybOccuhazardClass  getZYB_OccuhazardClass(String id) throws SQLException,ServiceException;
	/**
	 * 
	     * @Title: getVerificationHazardclassCode   
	     * @Description: TODO(危害类别验证编码唯一性)   
	     * @param: @param Hazardclass_Code
	     * @param: @return
	     * @param: @throws SQLException
	     * @param: @throws ServiceException      
	     * @return: boolean      
	     * @throws
	 */
	public int  getVerificationHazardclassCode(String Hazardclass_Code)  throws SQLException,ServiceException;
}
