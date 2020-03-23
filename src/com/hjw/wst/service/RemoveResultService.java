package com.hjw.wst.service;

import java.util.List;

import com.hjw.wst.DTO.ExaminfoChargingItemDTO;
import com.hjw.wst.domain.ExamInfo;
import com.hjw.wst.model.RemoveResulModel;
import com.synjones.framework.exception.ServiceException;

public interface RemoveResultService {

	/**
	 * 查询已检项目列表
	     * @Title: getCheckedExamChargingItem   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param exam_num
	     * @param: @return      
	     * @return: List<ExaminfoChargingItemDTO>      
	     * @throws
	 */
	public List<ExaminfoChargingItemDTO> getCheckedExamChargingItem(String exam_num, String center_num)throws ServiceException;
	
	/**
	 * 清除项目检查结果
	     * @Title: removeResult   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param mdeol
	     * @param: @return      
	     * @return: String      
	     * @throws
	 */
	public String removeResult(RemoveResulModel mdeol)throws ServiceException;
	
	/**
	 * 根据体检ID查询体检信息
	     * @Title: getExamInfo   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param id
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: ExamInfo      
	     * @throws
	 */
	public ExamInfo getExamInfo(long id) throws ServiceException;
}
