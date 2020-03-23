package com.hjw.wst.service;

import com.hjw.wst.model.ExamInfoModel;
import com.synjones.framework.exception.ServiceException;
/**
 * 评测报告
     * @Title:  火箭蛙体检管理系统   
     * @Package com.hjw.wst.service   
     * @Description:  
     * @author: yangm     
     * @date:   2017年9月16日 下午1:33:13   
     * @version V2.0.0.0
 */
public interface YxCardPdfService {
	/**
	 * 判断病人id是否为空
	     * @Title: getExamInfoPatient   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: String      
	     * @throws
	 */
	public String getExamInfoPatient(ExamInfoModel model) throws ServiceException;
}
