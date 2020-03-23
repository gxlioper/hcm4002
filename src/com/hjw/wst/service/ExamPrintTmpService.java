package com.hjw.wst.service;

import com.hjw.wst.DTO.UserDTO;
import com.hjw.wst.model.ExamPrintTmpModel;
import com.synjones.framework.exception.ServiceException;

public interface ExamPrintTmpService {

	/**
	 * 保存预览打印临时信息
	     * @Title: saveExamPrintTmp   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param model
	     * @param: @param user
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: String      
	     * @throws
	 */
	public String saveExamPrintTmp(ExamPrintTmpModel model,UserDTO user) throws ServiceException;
}
