package com.hjw.wst.service;

import java.util.List;

import com.hjw.wst.DTO.ExamResultDetailDTO;
import com.hjw.wst.DTO.ExaminationItemDTO;
import com.hjw.wst.DTO.HeaderDTO;
import com.hjw.wst.model.ExamResultDetailModel;
import com.synjones.framework.exception.ServiceException;

/**
 * 检查结果统计
     * @Title:  火箭蛙体检管理系统   
     * @Package com.hjw.wst.service   
     * @Description:  
     * @author: yangm     
     * @date:   2017年7月11日 下午2:47:58   
     * @version V2.0.0.0
 */
public interface CheckResultStatisticsService {
	/**
	 * 获取检验科项目
	     * @Title: getExaminationItemList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<ExaminationItemDTO>      
	     * @throws
	 */
	public  List<ExaminationItemDTO> getExaminationItemList() throws ServiceException;
	/**
	 * 获取检查结果列表
	     * @Title: getExamResultDetail   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<ExamResultDetailDTO>      
	     * @throws
	 */
	public  HeaderDTO getExamResultDetail(ExamResultDetailModel  model) throws ServiceException;
}
