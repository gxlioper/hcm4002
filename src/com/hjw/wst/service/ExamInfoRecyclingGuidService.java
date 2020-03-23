package com.hjw.wst.service;

import java.util.List;

import com.hjw.wst.DTO.ExamDepResultDTO;
import com.hjw.wst.domain.ExamInfoRecyclingGuid;
import com.synjones.framework.exception.ServiceException;



/**
 * 
     * @Title:  火箭蛙体检管理系统   
     * @Package com.hjw.wst.domain   
     * @Description:  导简单回收人
     * @author:zr  
     * @date:   2016年10月19日 下午11:28:12   
     * @version V2.0.0.0
 */
public interface ExamInfoRecyclingGuidService{
	/**
	 * 
	     * @Title: addExamInfoRecyclingGuidService   
	     * @Description: TODO(引导单回收人)   
	     * @param: @throws ServiceException      
	     * @return: void      
	     * @throws
	 */
	public void addExamInfoRecyclingGuidService(ExamInfoRecyclingGuid  sexam) throws ServiceException;
	
	/**
	 * 查询检查结果
	     * @Title: getExamResultList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param examid
	     * @param: @param itemids
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<ExamDepResultDTO>      
	     * @throws
	 */
	public List<ExamDepResultDTO> getExamResultList(String examnum,String itemids, String center_num) throws ServiceException;
}
