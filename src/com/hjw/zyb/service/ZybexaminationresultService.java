package com.hjw.zyb.service;

import java.sql.SQLException;
import java.util.List;

import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.zyb.domain.ZybExaminationresult;
import com.synjones.framework.exception.ServiceException;


/**
 * 体检结论
     * @Title:  火箭蛙体检管理系统   
     * @Package com.hjw.zyb.service   
     * @Description:  
     * @author: zt     
     * @date:   2017年4月21日 上午10:09:01   
     * @version V2.0.0.0
 */
public interface ZybexaminationresultService {
	
	/**
	 * 体检结论分页查询
	     * @Title: queryZybexaminationresultList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param result_name
	     * @param: @param page
	     * @param: @param pageSize
	     * @param: @return
	     * @param: @throws SQLException
	     * @param: @throws ServiceException      
	     * @return: PageReturnDTO      
	     * @throws
	 */
	public PageReturnDTO   queryZybexaminationresultList(String result_name,int pageno,int pageSize) throws  SQLException,ServiceException;
	
	/**
	 * 体检结论添加
	     * @Title: addZybexamresul   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param r
	     * @param: @throws SQLException
	     * @param: @throws ServiceException      
	     * @return: void      
	     * @throws
	 */
	public void  addZybexamresul(ZybExaminationresult r) throws  SQLException,ServiceException;
	
	/**
	 * 体检结论批量删除
	     * @Title: deleteZybexamresul   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param ids
	     * @param: @throws SQLException
	     * @param: @throws ServiceException      
	     * @return: void      
	     * @throws
	 */
	public void  deleteZybexamresul(String ids) throws  SQLException,ServiceException;
	
	
	/**
	 * 删除
	     * @Title: deleteZybexamresulone   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param id
	     * @param: @throws SQLException
	     * @param: @throws ServiceException      
	     * @return: void      
	     * @throws
	 */
	public void  deleteZybexamresulone(ZybExaminationresult r) throws  SQLException,ServiceException;
	
	/**
	 * 体检结论更新
	     * @Title: updateZybexamresul   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param r
	     * @param: @throws SQLException
	     * @param: @throws ServiceException      
	     * @return: void      
	     * @throws
	 */
	public void   updateZybexamresul(ZybExaminationresult r) throws  SQLException,ServiceException;
	
	
	/**
	 * 体检结论id查询
	     * @Title: queryByID   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param id
	     * @param: @return
	     * @param: @throws SQLException
	     * @param: @throws ServiceException      
	     * @return: ZybExaminationresult      
	     * @throws
	 */
	public ZybExaminationresult   queryByID(String id) throws  SQLException,ServiceException;
	
	/**
	 * 体检结论查询全部
	     * @Title: queryByAll   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws SQLException
	     * @param: @throws ServiceException      
	     * @return: List<ZybExaminationresult>      
	     * @throws
	 */
	public List<ZybExaminationresult> queryByAll()throws  SQLException,ServiceException;
}
