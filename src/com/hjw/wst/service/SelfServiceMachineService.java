package com.hjw.wst.service;

import com.synjones.framework.exception.ServiceException;

public interface SelfServiceMachineService {

	/**
	 * 自助机缴费
	     * @Title: selfServiceMachineFees   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param exam_num
	     * @param: @param amount
	     * @param: @param charging_way
	     * @param: @param card_num
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: String      
	     * @throws
	 */
	public String selfServiceMachineFees(String exam_num,Double amount,String charging_way,String card_num,String is_fees_qijian,long userid, String center_num)throws ServiceException;
}
