package com.hjw.wst.service;

import com.hjw.wst.DTO.ExamInfoDTO;
import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.wst.model.ExamInfoModel;
import com.synjones.framework.exception.ServiceException;

/**
 * 清除登记已检查状态
     * @Title:  火箭蛙体检管理系统   
     * @Package com.hjw.wst.service   
     * @Description:  
     * @author: zr     
     * @date:   2017年6月2日 上午9:12:55   
     * @version V2.0.0.0
 */
public interface UpdateExamStatusNService {
	/**
	 * 获取人员体检的项目
	     * @Title: getExamInfoItem   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param page
	     * @param: @param PageSize
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: ExamInfoDTO      
	     * @throws
	 */
	public PageReturnDTO getExamInfoItem(String examinf_id, String center_num,int page,int PageSize)  throws ServiceException;
	/**
	 * 修改体检项目状态未检
	     * @Title: saveItemexamStatus   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param ids
	     * @param: @throws ServiceException      
	     * @return: void      
	     * @throws
	 */
	public void saveItemexamStatus(String ids)throws ServiceException;
}
