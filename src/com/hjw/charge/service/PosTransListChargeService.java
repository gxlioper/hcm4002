package com.hjw.charge.service;

import java.util.List;

import com.hjw.charge.DTO.PosTransListDTO;
import com.synjones.framework.exception.ServiceException;

/**
 * 影像科室检查结果功能
     * @Title:  火箭蛙体检管理系统   
     * @Package com.hjw.wst.service   
     * @Description:  
     * @author: dangqi     
     * @date:   2016年11月29日 上午10:05:53   
     * @version V2.0.0.0
 */
public interface PosTransListChargeService {

	/**
	 * 查询原凭证号原交易号
	     * @Title: getViewExamDetail   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param exam_num
	     * @param: @param depId
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<ViewExamDetailDTO>      
	     * @throws
	 */
	public List<PosTransListDTO> getPeisTradeCode(String peis_trade_code) throws ServiceException ;
	 

}
