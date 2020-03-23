package com.hjw.wst.service;

import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.wst.domain.ExamSummaryGuidanceLib;
import com.hjw.wst.model.ExamSummaryGuidanceLibModel;
import com.synjones.framework.exception.ServiceException;

/**
 * 体检综述（健康指导建议） service
     * @Title:  火箭蛙体检管理系统   
     * @Package com.hjw.wst.service   
     * @Description:  
     * @author: dq     
     * @date:   2017年7月13日 下午4:54:46   
     * @version V2.0.0.0
 */
public interface ExamSummaryGuidanceLibService {

	/**
	 * 查询体检综述（健康指导建议）信息列表
	     * @Title: getExamSummaryGuidanceLibList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param model
	     * @param: @param pageno
	     * @param: @param pagesize
	     * @param: @return      
	     * @return: PageReturnDTO      
	     * @throws
	 */
	public PageReturnDTO getExamSummaryGuidanceLibList(ExamSummaryGuidanceLibModel model,int pageno, int pagesize) throws ServiceException;
	
	public ExamSummaryGuidanceLib getExamSummaryGuidanceLib(long id) throws ServiceException;
	
	public ExamSummaryGuidanceLib saveExamSummaryGuidanceLib(ExamSummaryGuidanceLib examSummaryGuidanceLib) throws ServiceException;
	
	public ExamSummaryGuidanceLib updateExamSummaryGuidanceLib(ExamSummaryGuidanceLib examSummaryGuidanceLib) throws ServiceException;
}
