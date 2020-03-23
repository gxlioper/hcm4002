package com.hjw.zyb.service;

import java.sql.SQLException;
import java.util.List;

import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.zyb.DTO.ZybdiseaseclassDTO;
import com.hjw.zyb.DTO.ZybdiseaseclassresultDTO;
import com.hjw.zyb.DTO.ZybexaminationresultDTO;
import com.hjw.zyb.domain.Zybdiseaseclassresult;
import com.hjw.zyb.model.ZybdiseaseclassresultModel;
import com.synjones.framework.exception.ServiceException;

/**
 * 疾病分类对应结论
     * @Title:  火箭蛙体检管理系统   
     * @Package com.hjw.zyb.service   
     * @Description:  
     * @author: yangm     
     * @date:   2017年4月13日 下午3:29:07   
     * @version V2.0.0.0
 */
public interface ZybdiseaseclassresultService {
	/**
	 * 疾病分类对应结论列表
	     * @Title: queryDiseaseclassresulList   
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
	public PageReturnDTO  queryDiseaseclassresulList(ZybdiseaseclassresultModel model,int page,int pageSize) throws  SQLException,ServiceException;
	/**
	 * 删除疾病分类对应结论列表
	     * @Title: deleteDiseaseclassresulList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param ids
	     * @param: @throws SQLException
	     * @param: @throws ServiceException      
	     * @return: void      
	     * @throws
	 */
	public void  deleteDiseaseclassresulList(String ids) throws  SQLException,ServiceException;
	/**
	 * 获取疾病分类对应结论
	     * @Title: queryDiseaseclassresulList   
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
	public Zybdiseaseclassresult getDiseaseclassresulList(ZybdiseaseclassresultModel  model) throws  SQLException,ServiceException;
	/**
	 * 新增类别结论关系
	     * @Title: queryDiseaseclassresulList   
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
	public void  addDiseaseclassresulList(Zybdiseaseclassresult   zy) throws  SQLException,ServiceException;
	/**
	 * 修改疾病分类对应结论关系
	     * @Title: updateDiseaseclassresulList   
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
	public void  updateDiseaseclassresulList(Zybdiseaseclassresult   zy) throws  SQLException,ServiceException;
	/**
	 * 获取疾病分类
	     * @Title: getZybdiseaseclassSelect   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws SQLException
	     * @param: @throws ServiceException      
	     * @return: List<ZybdiseaseclassDTO>      
	     * @throws
	 */
	public List<ZybdiseaseclassDTO>  getZybdiseaseclassSelect() throws  SQLException,ServiceException;
	/**
	 * 获取结论
	     * @Title: getZybdiseaseclassresultSelect   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws SQLException
	     * @param: @throws ServiceException      
	     * @return: List<ZybdiseaseclassresultDTO>      
	     * @throws
	 */
	public List<ZybexaminationresultDTO>  getexaminationresultSelect() throws  SQLException,ServiceException;
}
