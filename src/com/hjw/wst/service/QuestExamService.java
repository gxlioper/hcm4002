package com.hjw.wst.service;

import java.util.List;

import com.hjw.wst.DTO.QuestChargingDTO;
import com.hjw.wst.DTO.QuestExamDTO;
import com.hjw.wst.DTO.QuestRecordDTO;
import com.hjw.wst.DTO.UserDTO;
import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.wst.domain.QuestDictItem;
import com.hjw.wst.domain.QuestExamRecord;
import com.hjw.wst.domain.QuestExamList;
import com.synjones.framework.exception.ServiceException;

public interface QuestExamService {
	
	
	/**
	 * 
	     * @Title: getMeunListFirst   查询菜单列表
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: PageReturnDTO      
	     * @throws
	 */
	public List<QuestExamDTO> getMeunListFirst(QuestExamDTO questExamDTO) throws ServiceException;
	
	/**
	 * 
	     * @Title: getQuestionList   查询问题信息
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     
	     * @param: @return
	     * @param: @throws ServiceException      
	           
	     * @throws
	 */
	public List<QuestExamDTO> getQuestionList(QuestExamDTO questExamDTO) throws ServiceException;
	
	
	/**
	 * 
	     * @Title: getQuestAllList    查询次布所有信息
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     
	     * @param: @return
	     * @param: @throws ServiceException      
	           
	     * @throws
	 */
	public List<QuestExamDTO> getQuestAllList(QuestExamDTO questExamDTO) throws ServiceException;

	public QuestExamDTO queryGetIdQuestDto(QuestExamDTO questExamDTO)throws ServiceException;

	
	/**
	 * 
	     * @Title: saveAnswerList   保存答案记录
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     Item
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: QuestDictItem      
	     * @throws
	 */
	public QuestExamList saveAnswerList(QuestExamList brainList) throws ServiceException;

	/**
	 * 
	     * @Title: editAnswerList  更新答案记录 
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     List
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: QuestExamList      
	     * @throws
	 */
	public QuestExamList editAnswerList(QuestExamList brainList) throws ServiceException;

	/**
	 * 
	     * @Title: queryIDAndUser   判断是否存在这条记录
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     List
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: boolean      
	     * @throws
	 */
	public int queryIDAndUser(QuestExamList brainList) throws ServiceException;

	/**
	 * 
	     * @Title: getQuestionHidden   显示隐藏
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     
	     * @param: @return
	     * @param: @throws ServiceException      
	           
	     * @throws
	 */
	public List<QuestExamDTO> getQuestionHidden(QuestExamDTO questExamDTO) throws ServiceException;
	
	
	/**
	 * 
	     * @Title: getQuestionHiddenAnswer   显示隐藏答案
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     
	     * @param: @return
	     * @param: @throws ServiceException      
	           
	     * @throws
	 */
	public List<QuestExamDTO> getHiddenAnswer(QuestExamDTO questExamDTO) throws ServiceException;
	
	
	
	/**
	 * 
	     * @Title: saveRecordMsg   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param record
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: QuestExamRecord      
	     * @throws
	 */
	public QuestExamRecord saveRecordMsg(QuestExamRecord record) throws ServiceException;


	/**
	 * 
	     * @Title: queryMegFromRecord   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     
	     * @param: @return
	     * @param: @throws ServiceException      
	           
	     * @throws
	 */
	public QuestRecordDTO queryMegFromRecord(QuestRecordDTO brain) throws ServiceException;

	
	/**
	 * 
	     * @Title: QueryResultMsg    查询答案
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     
	     * @param: @return
	     * @param: @throws ServiceException      
	           
	     * @throws
	 */
	public List<QuestRecordDTO> QueryResultMsg(QuestRecordDTO brain) throws ServiceException;
	
	/**
	 * 
	     * @Title: getQuestAllList_status   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param titleID
	     * @param: @param itemlevel
	     * @param: @param exam_num
	     * @param: @return
	     * @param: @throws ServiceException      
	           
	     * @throws
	 */
	public List<QuestExamDTO> getQuestAllList_status(int titleID, int itemlevel, String exam_num, String quest_sub_code) throws ServiceException ;

	/**
	 * 
	     * @Title: saveEndRecord   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param record
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: QuestExamRecord      
	     * @throws
	 */
	public QuestExamRecord saveEndRecord(QuestExamRecord record, UserDTO user,String exam_num,String querytitle) throws ServiceException;
	
	/**
	 * 
	     * @Title: queryIsPayCharing   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param charing
	     * @param: @return
	     * @param: @throws ServiceException      
	           
	     * @throws
	 */
	public List<QuestChargingDTO> queryIsPayCharing(QuestChargingDTO charing, String center_num)throws ServiceException;
	
	
	/**
	 * 
	     * @Title: queryIsPaychargingItemCode   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param charing
	     * @param: @return
	     * @param: @throws ServiceException      
	           
	     * @throws
	 */
	public List<QuestChargingDTO> queryIsPaychargingItemCode(QuestChargingDTO charing)throws ServiceException;

	/**
	 * 
	     * @Title: questIsdefaultValue   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param charing
	     * @param: @return
	     * @param: @throws ServiceException      
	           
	     * @throws
	 */
	public List<QuestExamDTO> questIsdefaultValue(QuestExamDTO charing)throws ServiceException;

	
	
	/**
	 * 
	     * @Title: queryTitleCont   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     
	     * @param: @return
	     * @param: @throws ServiceException      
	           
	     * @throws
	 */
	public QuestExamDTO queryTitleCont(QuestExamDTO questExamDTO) throws ServiceException;
	
	/**
	 * 
	     * @Title: queryQuestNameFixed    查询定位
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     
	     * @param: @return
	     * @param: @throws ServiceException      
	           
	     * @throws
	 */
	public QuestExamDTO queryQuestNameFixed(QuestExamDTO questExamDTO) throws ServiceException;

	
	/**
	 * 
	     * @Title: queryLikeTitleName   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     
	     * @param: @return
	     * @param: @throws ServiceException      
	           
	     * @throws
	 */
	public List<QuestExamDTO> queryLikeTitleName(QuestExamDTO questExamDTO) throws ServiceException;

	/**
	 * 
	     * @Title: addZhengZhuangById   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     
	     * @param: @return
	     * @param: @throws ServiceException      
	           
	     * @throws
	 */
	public List<QuestExamDTO> addZhengZhuangById(QuestExamDTO questExamDTO) throws ServiceException;
	
	/**
	 * 
	     * @Title: resultWenZhengMsg   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param recList
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<QuestExamList>      
	     * @throws
	 */
	public List<QuestExamDTO> resultWenZhengMsg(QuestExamList recList) throws ServiceException;
	

	/**
	 * 
	     * @Title: findByExamNum   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param bre
	     * @param: @return
	     * @param: @throws ServiceException      
	           
	     * @throws
	 */
	public List<QuestExamDTO> findByExamNum(QuestRecordDTO br) throws ServiceException;
	
	/**
	 * 
	     * @Title: findByExamNumScName   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param br
	     * @param: @return
	     * @param: @throws ServiceException      
	           
	     * @throws
	 */
	public QuestExamDTO findByExamNumScName(QuestRecordDTO br) throws ServiceException;
	
	
	public void delisMuslKey(String exam_num,String questResult_itemID)throws ServiceException ;
	
}
