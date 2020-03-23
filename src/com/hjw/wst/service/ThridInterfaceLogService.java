package com.hjw.wst.service;

import java.util.List;

import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.wst.DTO.ThridInterfaceLogDto;
import com.hjw.wst.DTO.ThridInterfaceLogLisResultDto;
import com.hjw.wst.DTO.ThridInterfaceLogPaceResultDto;
import com.hjw.wst.model.ThridInterfaceLogModel;
import com.synjones.framework.exception.ServiceException;

public interface ThridInterfaceLogService {
	
	/**
	 * 
	     * @Title: getTeamAccountForBatch   
	     * @Description: TODO(分页查询日志信息)   
	     * @param: @param req_no
	     * @param: @param exam_no
	     * @param: @param pageno
	     * @param: @param pagesize
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: PageReturnDTO      
	     * @throws
	 */ 
	public PageReturnDTO queryThirdInterfaceLog(ThridInterfaceLogModel model, String center_num,int pageno, int pagesize) throws ServiceException;
	
	/**
	 * 
	     * @Title: getLogDetailDrid   
	     * @Description: TODO(日志详情列表)   
	     * @param: @param tilId
	     * @param: @param pageno
	     * @param: @param pagesize
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: PageReturnDTO      
	     * @throws
	 */
	public PageReturnDTO getLogDetailDrid(String tilId,int pageno, int pagesize) throws ServiceException;


	/**
	 * 
	     * @Title: viewLogDetailPage   
	     * @Description: TODO(单条日志详情)   
	     * @param: @param id
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: ThridInterfaceLogDto      
	     * @throws
	 */
	public ThridInterfaceLogDto viewLogDetailPage(String id)throws ServiceException;
	
	
	/**
	 * 
	     * @Title: viewLogDetailPage   
	     * @Description: TODO(单条日志详情)   
	     * @param: @param id
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: ThridInterfaceLogDto      
	     * @throws
	 */
	public List<ThridInterfaceLogDto> queryLogDetailList(String id)throws ServiceException;
	
	
	
	/**
	 * 
	     * @Title: viewLogDetailPage   
	     * @Description: TODO(日志详情 单条日志查看)   
	     * @param: @param id
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: ThridInterfaceLogDto      
	     * @throws
	 */
	public ThridInterfaceLogDto viewDetailLogQuery(String id)throws ServiceException;
	
	
	/**
	 * 
	     * @Title: viewDetailLogList   
	     * @Description: TODO(日志多条导出)   
	     * @param: @param id
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<ThridInterfaceLogDto>      
	     * @throws
	 */
	public List<ThridInterfaceLogDto> viewDetailLogList(String id)throws ServiceException;
	
	
	/**
	 * 
	     * @Title: deleteDetailLogMsg   
	     * @Description: TODO(批量删除详情日志信息)   
	     * @param: @param ids
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: String      
	     * @throws
	 */
	public String deleteDetailLogMsg(String ids) throws ServiceException;
	
	
	/**
	 * 
	     * @Title: deleteLogMaterAndDetail   
	     * @Description: TODO(批量删除主表日志和附表日志)   
	     * @param: @param ids
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: String      
	     * @throws
	 */
	public String deleteLogMaterAndDetail(String ids) throws ServiceException;
	
	
	/**
	 * 
	     * @Title: queryAllMessageName   
	     * @Description: TODO(查询接口类型)   
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<ThridInterfaceLogDto>      
	     * @throws
	 */
	public List<ThridInterfaceLogDto> queryAllMessageName() throws ServiceException;
	
	
	/**
	 * 
	     * @Title: viewLogDetailLis   
	     * @Description: TODO(lis结果)   
	     * @param: @param til_id
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: ThridInterfaceLogDto      
	     * @throws
	 */
	public ThridInterfaceLogLisResultDto viewLogDetailLis(String til_id) throws ServiceException;
	
	/**
	 * 
	     * @Title: viewLogDetailPacs   
	     * @Description: TODO(pacs结果)   
	     * @param: @param til_id
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: ThridInterfaceLogDto      
	     * @throws
	 */
	public ThridInterfaceLogPaceResultDto viewLogDetailPacs(String til_id) throws ServiceException;
}
