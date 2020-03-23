package com.hjw.wst.service;

import java.util.List;

import com.hjw.wst.DTO.QuestExamDTO;
import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.wst.domain.QuestDictTitle;
import com.hjw.wst.domain.QuestDictItem;
import com.synjones.framework.exception.ServiceException;

public interface QuestDictService {
	
	/**
	 * 
	     * @Title: queryBrainTitle   查询脑健康title信息
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param pageSize
	     * @param: @param page
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: PageReturnDTO      
	     * @throws
	 */
	public PageReturnDTO queryQuestTitle(QuestExamDTO questExamDTO,int pageSize,int page) throws ServiceException;
	
	
	/**
	 * 
	     * @Title: queryModuleList   查询脑健康module信息 
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     
	     * @param: @param pageSize
	     * @param: @param page
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: PageReturnDTO      
	     * @throws
	 */
	public PageReturnDTO queryModuleList(QuestExamDTO questExamDTO,int pageSize,int page) throws ServiceException;
	
	/**
	 * 
	     * @Title: queryStyleList 查询style信息
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     
	     * @param: @param pageSize
	     * @param: @param page
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: PageReturnDTO      
	     * @throws
	 */
	public PageReturnDTO queryStyleList(QuestExamDTO questExamDTO,int pageSize,int page) throws ServiceException;
	
	
	/**
	 * 
	     * @Title: queryProjectTwoGrid   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     
	     * @param: @param pageSize
	     * @param: @param page
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: PageReturnDTO      
	     * @throws
	 */
	public PageReturnDTO queryProjectTwoGrid(QuestExamDTO questExamDTO,int pageSize,int page) throws ServiceException;
	
	
	/**
	 * 
	     * @Title: saveBrainTitleAdd   新增方法
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: QuestDictTitle      
	     * @throws
	 */
	public QuestDictTitle saveQuestTitle(QuestDictTitle brain) throws ServiceException;
	
	/**
	 * 
	     * @Title: editBrainTitleAdd   修改方法
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: QuestDictTitle      
	     * @throws
	 */
	public QuestDictTitle editQuestTitle(QuestDictTitle brain) throws ServiceException;
	
	
	/**
	 * 
	     * @Title: deleteBrainTitleAdd  删除方法 
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: QuestDictTitle      
	     * @throws
	 */
	public void deleteQuestTitle(int titleID) throws ServiceException;
	
	
	/**
	 * 
	     * @Title: getQuestionGrid   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     
	     * @param: @param pageSize
	     * @param: @param page
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: PageReturnDTO      
	     * @throws
	 */
	public PageReturnDTO getQuestionGrid(QuestExamDTO questExamDTO,int pageSize,int page) throws ServiceException;
	
	/**
	 * 
	     * @Title: deleteProjectQuest   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param titleID
	     * @param: @throws ServiceException      
	     * @return: void      
	     * @throws
	 */
	public void deleteProjectQuest(int itemId) throws ServiceException;
	
	
	/**
	 * 
	      
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: QuestDictItem      
	     * @throws
	 */
	public QuestDictItem editQuestItem(QuestDictItem brainItem) throws ServiceException;
	
	/**
	 * 
	        
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: QuestDictItem      
	     * @throws
	 */
	public QuestDictItem saveQuestItem(QuestDictItem brainItem) throws ServiceException;
	
	
	/**
	 * 
	     * @Title: querySelectType   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<BrainExaminDto>      
	     * @throws
	 */
	public List<QuestExamDTO> querySelectType(QuestExamDTO questExamDTO)throws ServiceException;
	
}
