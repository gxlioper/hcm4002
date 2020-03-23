package com.hjw.wst.service;

import java.util.List;

import com.hjw.wst.DTO.BatchDTO;
import com.hjw.wst.DTO.CardInfoDTO;
import com.hjw.wst.DTO.CollectFeesResult;
import com.hjw.wst.DTO.ExamSetDTO;
import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.wst.DTO.UserDTO;
import com.hjw.wst.domain.CardExamSet;
import com.hjw.wst.domain.CardExamSetItem;
import com.hjw.wst.domain.CardInfo;
import com.hjw.wst.model.CardInfoModel;
import com.synjones.framework.exception.ServiceException;

/**
 * 卡管理
     * @Title:  火箭蛙体检管理系统   
     * @Package com.hjw.wst.service   
     * @Description:  
     * @author: dangqi     
     * @date:   2016年7月19日 下午9:43:40   
     * @version V2.0.0.0
 */
public interface CardInfoService {
	/**
	 *查询科室下的所有体检套餐
	     * @Title: getCardInfoByCardNum   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param cardNum
	     * @param: @return      
	     * @return: CardInfo      
	     * @throws
	 */
	public List<ExamSetDTO> getijiantaocanlist(String center_num) throws ServiceException;
	/**
	 *根据套餐查询科室下的所有体检套餐
	     * @Title: getCardInfoByCardNum   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param cardNum
	     * @param: @return      
	     * @return: CardInfo      
	     * @throws
	 */
	public List<ExamSetDTO> getijiantaocanlists(String setName,String center_num) throws ServiceException;
	/**
	 *根据套餐查询卡信息对应套餐项目信息表
	     * @Title: getCardInfoByCardNum   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param cardNum
	     * @param: @return      
	     * @return: CardInfo      
	     * @throws
	 */
	public List<ExamSetDTO> getijiantaocanlistss(String setName,String center_num) throws ServiceException;
	/**
	 * 根据卡号查询卡信息
	     * @Title: getCardInfoByCardNum   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param cardNum
	     * @param: @return      
	     * @return: CardInfo      
	     * @throws
	 */
	public CardInfoDTO getCardInfoByCardNum(UserDTO user,String cardNum) throws ServiceException;
	
	/**
	 * 根据ID查询卡信息
	     * @Title: getCardInfoById   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param id
	     * @param: @return      
	     * @return: CardInfo      
	     * @throws
	 */
	public CardInfo getCardInfoById(String id) throws ServiceException;
	
	/**
	 * 新增保存卡信息
	     * @Title: saveCardInfo   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param cardInfo
	     * @param: @return      
	     * @return: CardInfo      
	     * @throws
	 */
	public CardInfo saveCardInfo(CardInfo cardInfo) throws ServiceException;
	/**
	 * 新增保存卡信息
	     * @Title: saveCardInfo   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param cardInfo
	     * @param: @return      
	     * @return: CardInfo      
	     * @throws
	 */
	public CardExamSet saveCardExamSet(CardExamSet cardExamSet) throws ServiceException;
	
	/**
	 * 新增保存卡信息
	     * @Title: saveCardInfo   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param cardInfo
	     * @param: @return      
	     * @return: CardInfo      
	     * @throws
	 */
	public CardExamSetItem saveCardExamSetItem(CardExamSetItem cardExamSetItem) throws ServiceException;
	
	/**
	 * 移除原有卡套餐信息
	     * @Title: saveCardInfo   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param cardInfo
	     * @param: @return      
	     * @return: CardInfo      
	     * @throws
	 */
	public CardExamSet delCardExamSet(CardExamSet cardExamSetDel) throws ServiceException;
	
	/**
	 * 移除原有卡套餐项目信息
	     * @Title: saveCardInfo   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param cardInfo
	     * @param: @return      
	     * @return: CardInfo      
	     * @throws
	 */
	public CardExamSetItem delCardExamSetItem(CardExamSetItem cardExamSetItemdel) throws ServiceException;
	
	/**
	 * 查询卡套餐信息
	     * @Title: saveCardInfo   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param cardInfo
	     * @param: @return      
	     * @return: CardInfo      
	     * @throws
	 */
	public List<CardExamSet> selCardExamSetList(String card_num) throws ServiceException;
	
	/**
	 * 查询卡套餐项目信息
	     * @Title: saveCardInfo   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param cardInfo
	     * @param: @return      
	     * @return: CardInfo      
	     * @throws
	 */
	public List<CardExamSetItem> selCardExamSetItemList(String card_num) throws ServiceException;
	
	
	
	/**
	 * 修改卡信息
	     * @Title: updateCardInfo   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param cardInfo
	     * @param: @return      
	     * @return: CardInfo      
	     * @throws
	 */
	public CardInfo updateCardInfo(CardInfo cardInfo) throws ServiceException;
	
	/**
	 * 查询卡信息列表
	     * @Title: cardInfoList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param pagesize
	     * @param: @param pageno
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: PageReturnDTO      
	     * @throws
	 */
	public PageReturnDTO cardInfoList(UserDTO user,String card_num,String status,String card_type,String card_level,String time1,String time2,String company,long hair_card_status,long hair_card_creater,int pagesize, int pageno) throws ServiceException;
	
	/**
	 * 查询删除的卡信息
	     * @Title: getDeleteCardList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param starDate
	     * @param: @param endDate
	     * @param: @param pagesize
	     * @param: @param pageno
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: PageReturnDTO      
	     * @throws
	 */
	public PageReturnDTO getDeleteCardList(UserDTO user,String starDate,String endDate,int pagesize, int pageno) throws ServiceException;
	/**
	 * 保存批量制卡信息
	     * @Title: saveCardBatch   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param model
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: String      
	     * @throws
	 */
	public String saveCardBatch(CardInfoModel model,UserDTO user,String center_num) throws ServiceException;
	
	/**
	 * 根据物理卡号查询卡信息
	     * @Title: getCardInfoByPhyNum   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param phyNum
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: CardInfo      
	     * @throws
	 */
	public CardInfo getCardInfoByPhyNum(String phyNum) throws ServiceException;
	
	/**
	 * 根据卡号查询卡信息
	     * @Title: getCardInfoByCarNum   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param cardNum
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: CardInfo      
	     * @throws
	 */
	public CardInfo getCardInfoByCarNum(String cardNum) throws ServiceException;
	
	public void updateCard(CardInfoModel model) throws ServiceException;
	/**
	 * 發卡
	     * @Title: saveHairCardInfo   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param card_num
	     * @param: @param userid
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: String      
	     * @throws
	 */
	public String saveHairCardInfo(String card_num, long userid) throws ServiceException;
	/**
	 * 撤銷發卡
	     * @Title: delHairCardInfo   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param card_num
	     * @param: @param userid
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: String      
	     * @throws
	 */
	public String delHairCardInfo(String card_num, long userid)throws ServiceException;

	public String getCardInfoPREFIX(String card_level)throws ServiceException;
	//会员卡充值
	public CollectFeesResult saveRechargeInfo(CardInfoModel model, UserDTO user)throws ServiceException;
	/**
	 * 按照卡号查询批次任务
	     * @Title: saveCardInfo   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param cardInfo
	     * @param: @return      
	     * @return: CardInfo      
	     * @throws
	 */
	public List<BatchDTO> getCardBatchlists(String card_num,String center_num) throws ServiceException;

}
