package com.hjw.wst.service;

import com.hjw.wst.DTO.UserDTO;
import com.synjones.framework.exception.ServiceException;

/**
 * Lis 和 pcas 申请接口服务
     * @Title:  火箭蛙体检管理系统   
     * @Package com.hjw.wst.service   
     * @Description:  
     * @author: dangqi     
     * @date:   2016年10月24日 下午9:20:24   
     * @version V2.0.0.0
 */
public interface LisPacsApplicationService {

	/**
	 * pacs申请发送  (charingIds 可以为 null,isBufa 为true是,是否发送申请不过滤，需要发送的项目全部发送,为false 时，只发送未发送的项目)
	     * @Title: pacsSend   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: String      
	     * @throws
	 */
	public String pacsSend(String exam_num,String charingIds,UserDTO user,boolean isBufa,String IS_HIS_PACS_CHECK) throws ServiceException;
	
	/**
	 * pacs申请撤销 (charingIds 可以为 null)
	     * @Title: PacsDel   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param exam_num
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: String      
	     * @throws
	 */
	public String pacsDel(String exam_num,String charging_item_code_s,UserDTO user) throws ServiceException;
	
	/**
	 * lis申请发送
	     * @Title: lisSend   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param exam_num
	     * @param: @param charingIds
	     * @param: @param user
	     * @param: @param isBufa
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: String      
	     * @throws
	 */
	public String lisSend(String exam_num,String charingIds,UserDTO user,boolean isBufa,String bangding,String checkhisfee) throws ServiceException;
	
	/**
	 * lis申请撤销
	     * @Title: lisDel   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param exam_num
	     * @param: @param charingIds
	     * @param: @param user
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: String      
	     * @throws
	 */
	public String lisDel(String exam_num,String charging_item_code_s,UserDTO user) throws ServiceException;

	/**
	 * HIS减项申请
	     * @Title: delFeeSend   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param exam_num
	     * @param: @param charingIds
	     * @param: @param user
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: String      
	     * @throws
	 */
	public String delFeeSend(String exam_num,String charingIds,UserDTO user) throws ServiceException;

	/**
	 * 退费申请
	     * @Title: cancelFees   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param exam_num
	     * @param: @param charingIds
	     * @param: @param user
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: String      
	     * @throws
	 */
	public String cancelFees(String exam_num,String charingIds,UserDTO user) throws ServiceException;

}
