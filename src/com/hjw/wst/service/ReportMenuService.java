package com.hjw.wst.service;


import java.util.List;

import com.hjw.wst.DTO.Customer_TypeDTO;
import com.hjw.wst.DTO.ExamFlowTotalDTO;
import com.hjw.wst.DTO.ExamInfoUserDTO;
import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.wst.DTO.ReportMenuDTO;
import com.hjw.wst.domain.Customer_Type;
import com.hjw.wst.domain.ExamFlow;
import com.hjw.wst.domain.ReportMenu;
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
public interface ReportMenuService {
	/**
	 * 
	     * @Title: saveReportMenu   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param ctms
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: ReportMenu      
	     * @throws
	 */
	public ReportMenu saveReportMenu(ReportMenu ctms) throws ServiceException;

	/**
	 * 
	     * @Title: loadReportMenu   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param id
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: ReportMenu      
	     * @throws
	 */
	public ReportMenu loadReportMenu(long id) throws ServiceException;
	
	
	/**
	 * 
	     * @Title: getReportMenuForFatherId   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param fatherid
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<ReportMenuDTO>      
	     * @throws
	 */
	public List<ReportMenuDTO> getReportMenuForFatherId(Integer fatherid) throws ServiceException;
	
	/**
	 * 
	     * @Title: getReportMenuForFatherIdCount   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param id
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: int      
	     * @throws
	 */
	public int getReportMenuForFatherIdCount(long id) throws ServiceException;
	
	/**
	 * 
	     * @Title: delReportMenu   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param ctms
	     * @param: @throws ServiceException      
	     * @return: void      
	     * @throws
	 */
	public void delReportMenu(ReportMenu ctms) throws ServiceException;
	
	/**
	 * 
	     * @Title: updateReportMenu   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param ctms
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: ReportMenu      
	     * @throws
	 */
	public ReportMenu updateReportMenu(ReportMenu ctms) throws ServiceException;
	
}
	
