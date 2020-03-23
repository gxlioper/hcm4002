package com.hjw.wst.service;

import java.sql.SQLException;

import com.hjw.wst.DTO.ExamInfoDTO;
import com.hjw.wst.DTO.UserDTO;
import com.hjw.wst.domain.ReportReceive;
import com.hjw.wst.model.RegisterModel;
import com.hjw.wst.model.ReportReceiveModel;
import com.synjones.framework.exception.ServiceException;

/**
 * 
     * @Title:  火箭蛙体检管理系统   
     * @Package com.hjw.wst.service   
     * @Description:  报告领取方式
     * @author: zr     
     * @date:   2017年2月22日 下午3:42:13   
     * @version V2.0.0.0
 */
public interface ReportReceiveService  {
	/**
	 * 
	     * @Title: getReportReceive   
	     * @Description: TODO(获取报告领取方式)   
	     * @param: @param e_num
	     * @param: @return
	     * @param: @throws SQLException      
	     * @return: ReportReceive      
	     * @throws
	 */
	public  ExamInfoDTO getReportReceive(String e_num) throws   ServiceException;
	/**
	 * 
	     * @Title: saveReportReceive   
	     * @Description: TODO(保存报告领取方式)   
	     * @param: @throws ServiceException      
	     * @return: void      
	     * @throws
	 */
	public  void  saveReportReceive(RegisterModel  model,UserDTO  user) throws   ServiceException;
}
