package com.hjw.wst.service;

import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.wst.DTO.UserDTO;
import com.hjw.wst.domain.CardDeal;
import com.synjones.framework.exception.ServiceException;

/**
 * 充值、消费记录
     * @Title:  火箭蛙体检管理系统   
     * @Package com.hjw.wst.service   
     * @Description:  
     * @author: dangqi     
     * @date:   2016年7月25日 下午12:17:32   
     * @version V2.0.0.0
 */
public interface CardDealService {

	/**
	 * 新增保存交易记录
	     * @Title: saveCardDeal   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return      
	     * @return: CardDeal      
	     * @throws
	 */
	public CardDeal saveCardDeal(CardDeal cardDeal) throws ServiceException;
	
	/**
	 * 查询交易记录列表
	     * @Title: getcardDealList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param pagesize
	     * @param: @param pageno
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: PageReturnDTO      
	     * @throws
	 */
	public PageReturnDTO getcardDealList(String card_num,String deal_type,String creater,String deal_date,int pagesize, int pageno ,String user_name,String exam_num,UserDTO user) throws ServiceException;
}
