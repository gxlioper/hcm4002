package com.hjw.wst.service;

import java.util.List;

import com.hjw.wst.DTO.DepartmentDepDTO;
import com.hjw.wst.DTO.Examinatioin_centerDTO;
import com.hjw.wst.DTO.Examinatioin_center_dept;
import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.wst.domain.DepartmentDep;
import com.hjw.wst.domain.Exam_user;
import com.hjw.wst.domain.Examinatioin_center;
import com.hjw.wst.domain.Scheduling;
import com.synjones.framework.exception.ServiceException;
import com.synjones.framework.support.PageSupport;

/**
 * 
 * <菜单、功能、角色操作> <功能详细描述>
 * 
 * @author yangm
 * @version [3.0.0, Nov 4, 2011]
 * @see [相关类/方法]
 * @since [一卡通网站查询系统]
 */
public interface Examinatioin_centerService {
	/**
	 * 增加体检中心信息
	     * @Title: addExamination   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param examinatioin
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: Examinatioin_center      
	     * @throws
	 */
	public Examinatioin_center addExamination(Examinatioin_center examinatioin) throws ServiceException;
		/**
		 * 删除体检中心信息
		     * @Title: delExamination   
		     * @Description: TODO(这里用一句话描述这个方法的作用)   
		     * @param: @param examinatioin
		     * @param: @throws ServiceException      
		     * @return: void      
		     * @throws
		 */
	public void delExamination(Examinatioin_center examinatioin) throws ServiceException;;

	/**
	 * 更新体检中心信息
	     * @Title: updExamination   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param examinatioin
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: Examinatioin_center      
	     * @throws
	 */
	public Examinatioin_center updExamination(Examinatioin_center examinatioin) throws ServiceException;;

	
	
	/**
	 * 查询全部
	     * @Title: queryAllExaminatioin   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<Examinatioin_center>      
	     * @throws
	 */
	public List<Examinatioin_centerDTO> queryAllExaminatioin() throws ServiceException;;

	/**
	 * 根据编号查询
	     * @Title: loadExaminatioin   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param id
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: Examinatioin_center      
	     * @throws
	 */
	
	public Examinatioin_center loadExaminatioin(long id) throws ServiceException;

	/**
	 * 编码查询
	     * @Title: queryByNum   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param center_num
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: Examinatioin_center      
	     * @throws
	 */
	
	public  Examinatioin_center queryByNum(String center_num) throws ServiceException;
	
	/**
	 *  分页查询
	     * @Title: queryPageExaminatioin   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param pageno
	     * @param: @param pagesize
	     * @param: @return      
	     * @return: PageReturnDTO      
	     * @throws
	 */
	
	public PageReturnDTO queryPageExaminatioin(String center_num ,String center_name,int pageno, int pagesize);
	
	public List<Examinatioin_center_dept> getDept() throws ServiceException;
	
	public List<Examinatioin_center> getExaminatioin_centerList(String center_name,String center_num)throws ServiceException;
	public List<Examinatioin_center> getExaminatioin_centerListByParentId(String id)throws ServiceException;
	/**
	 * 获取体检中心列表
	     * @Title: getExaminatioin_centerList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param id
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<Examinatioin_center>      
	     * @throws
	 */
	public List<Examinatioin_center> getExaminatioin_centerList(Long id) throws ServiceException;
	public List<Examinatioin_center> getExaminatioin_centerListByParent(String parent_name) throws ServiceException;
	public List<Exam_user> getExam_userById(String id)throws ServiceException;
	public List<Examinatioin_center_dept> getExaminatioin_centerListDTO(long centerid,String userid)throws ServiceException;
	
}
	
