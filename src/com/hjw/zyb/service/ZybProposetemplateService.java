package com.hjw.zyb.service;

import java.sql.SQLException;
import java.util.List;

import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.wst.DTO.UserDTO;
import com.hjw.zyb.domain.ZybProposetemplate;
import com.hjw.zyb.model.ZybProposetemplateModel;
import com.synjones.framework.exception.ServiceException;

/**
 * 职业建议词库管理
 * 
 * @Title: 火箭蛙体检管理系统
 * @Package com.hjw.zyb.service
 * @Description:
 * @author: zr
 * @date: 2017年4月25日 上午10:05:06
 * @version V2.0.0.0
 */
public interface ZybProposetemplateService {
	/**
	 * 获取所有体检结论
	 * 
	 * @Title: getExaminationresult
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param: @return
	 * @param: @throws SQLException
	 * @param: @throws ServiceException  
	 * @return: List
	 * @throws
	 */
	public List getExaminationresult(ZybProposetemplateModel model) throws SQLException, ServiceException;

	/**
	 * 获取所有建议词List
	 * @Title: queryProposetemplate
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param: @param model
	 * @param: @return
	 * @param: @throws SQLException
	 * @param: @throws ServiceException
	 * @return: PageReturnDTO
	 * @throws
	 */
	public PageReturnDTO queryProposetemplate(ZybProposetemplateModel model,int page,int pageSize)throws SQLException, ServiceException;
	/**
	 * 删除建议词
	     * @Title: queryProposetemplate   
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
	public void deleteProposetemplate(ZybProposetemplateModel model)throws SQLException, ServiceException;
	/**
	 * 获取建议词
	     * @Title: queryProposetemplate   
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
	public ZybProposetemplate getProposetemplate(ZybProposetemplateModel model)throws SQLException, ServiceException;
	/**
	 * 新增建议词
	     * @Title: queryProposetemplate   
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
	public void addProposetemplate(ZybProposetemplate  pro)throws SQLException, ServiceException;
	/**
	 * 修改建议词
	     * @Title: queryProposetemplate   
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
	public void updateProposetemplate(ZybProposetemplateModel model,UserDTO  user)throws SQLException, ServiceException;
	/**
	 * 建议词表格记录修改是否默认值/是否有效
	     * @Title: updateProposetemplateListchebox   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param model
	     * @param: @throws SQLException
	     * @param: @throws ServiceException      
	     * @return: void      
	     * @throws
	 */
	public void updateProposetemplateListchebox(ZybProposetemplateModel model)throws SQLException, ServiceException;

}
