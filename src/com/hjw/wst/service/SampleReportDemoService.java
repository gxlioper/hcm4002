package com.hjw.wst.service;

import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.wst.domain.SampleReportDemo;
import com.synjones.framework.exception.ServiceException;
/**
 * 
     * @Title:  火箭蛙体检管理系统   
     * @Package com.hjw.wst.service   
     * @Description:  报告样本
     * @author: 张瑞    
     * @date:   2016年8月25日 上午11:03:11   
     * @version V2.0.0.0
 */
public interface SampleReportDemoService {
	/**
	 * 
	     * @Title: querySampleReportDemo   
	     * @Description: TODO(报告样本分页)   
	     * @param: @param page
	     * @param: @param pageSize
	     * @param: @return      
	     * @return: PageReturnDTO      
	     * @throws
	 */
	public PageReturnDTO querySampleReportDemo(SampleReportDemo sample,int page, int pageSize,String startStop) throws ServiceException;
	
	/**
	 * 
	     * @Title: findSampleReportDemoForId   
	     * @Description: id查询样本   
	     * @param: @param id
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: SampleReportDemo      
	     * @throws
	 */
	public SampleReportDemo findSampleReportDemoForId(long id)  throws ServiceException;
	
	/**
	 * 
	     * @Title: updateSampleReportDemo   
	     * @Description: 修改样本  
	     * @param: @param sd
	     * @param: @throws ServiceException      
	     * @return: void      
	     * @throws
	 */
	public void updateSampleReportDemo(SampleReportDemo sd)  throws ServiceException;
	/**
	 * 
	     * @Title: deleteDept   
	     * @Description: TODO(删除报告样本/修改)   
	     * @param: @param dep
	     * @param: @throws ServiceException      
	     * @return: void      
	     * @throws
	 */
	public void deleteSam(SampleReportDemo sa) throws ServiceException;
	/**
	 * 
	     * @Title: deleteSampleReportDemo   
	     * @Description: TODO(批量删除报告样本)   
	     * @param: @param sa
	     * @param: @throws ServiceException      
	     * @return: void      
	     * @throws
	 */
	public void deleteSampleReportDemo(String id) throws ServiceException;
	/**
	 * 
	     * @Title: findClass   
	     * @Description: TODO(查找实体类)   
	     * @param: @param id
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: SampleReportDemo      
	     * @throws
	 */
	public SampleReportDemo  findClass(long id) throws ServiceException;
	/**
	 * 
	     * @Title: getSampleReportDemoBynum   
	     * @Description: TODO(验证报告编号是否存在)   
	     * @param: @param demo_num
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: SampleReportDemo      
	     * @throws
	 */
	public SampleReportDemo getSampleReportDemoBynum(String demo_num) throws ServiceException;
	/**
	 * 
	     * @Title: getSampleReportDemoBynum   
	     * @Description: TODO(验证样本名称是否存在)   
	     * @param: @param demo_num
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: SampleReportDemo      
	     * @throws
	 */
	public SampleReportDemo getSampleReportDemoByname(String demo_name) throws ServiceException;
	/**
	 * 
	     * @Title: addSampleReportDemo   
	     * @Description: TODO(添加报告样本)   
	     * @param: @param sam
	     * @param: @throws ServiceException      
	     * @return: void      
	     * @throws
	 */
	public void addSampleReportDemo(SampleReportDemo sam) throws ServiceException;
	
}
