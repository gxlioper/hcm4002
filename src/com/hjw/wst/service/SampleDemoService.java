package com.hjw.wst.service;

import java.util.List;
import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.wst.DTO.SampleDemoChargingItemDTO;
import com.hjw.wst.DTO.SampleDemoDTO;
import com.hjw.wst.DTO.UserDTO;
import com.hjw.wst.domain.SampleDemo;
import com.hjw.wst.domain.SampleReportDemo;
import com.hjw.wst.model.SampleDemoModel;
import com.synjones.framework.exception.ServiceException;

/**
 * 
     * @Title:  火箭蛙体检管理系统   
     * @Package com.hjw.wst.service   
     * @Description:  检查样本
     * @author: 张瑞     
     * @date:   2016年9月5日 上午9:45:06   
     * @version V2.0.0.0
 */

public interface SampleDemoService{
	
	/**
	 * 
	     * @Title: querySampleReportDemo   
	     * @Description: TODO(获取报告样本列表&&检索)   
	     * @param: @param sample
	     * @param: @param page
	     * @param: @param pageSize
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: PageReturnDTO      
	     * @throws
	 */
	public PageReturnDTO querySampleDemo(SampleDemo demo,int pageSize,int page,String  startStop);
	/**
	 * 删除检查样本
	     * @Title: deleteSampleDemo   
	     * @Description: TODO(删除检查样本啥)   
	     * @param: @param id
	     * @param: @throws ServiceException      
	     * @return: void      
	     * @throws
	 */
	public void deleteSampleDemo(String id) throws ServiceException;
	/**
	 * 
	     * @Title: getSampleDemoBynum   
	     * @Description: TODO(验证检验样本编号)   
	     * @param: @param demo_num
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: SampleDemo      
	     * @throws
	 */
	public SampleDemo getSampleDemoBynum(String demo_num) throws ServiceException;
	/**
	 * 
	     * @Title: addSampleDemo   
	     * @Description: TODO(添加检验样本)   
	     * @param: @param sample
	     * @param: @throws ServiceException      
	     * @return: void      
	     * @throws
	 */
	public void addSampleDemo(SampleDemo  sample) throws ServiceException;
	/**
	 * 
	     * @Title: findClass   
	     * @Description: TODO(查找实体类)   
	     * @param: @param id
	     * @param: @return      
	     * @return: SampleDemo      
	     * @throws
	 */
	public SampleDemo findClass(long id);
	/**
	 * 
	     * @Title: updateSampleDemo   
	     * @Description: TODO(修改报告样本)   
	     * @param: @param sa
	     * @param: @throws ServiceException      
	     * @return: void      
	     * @throws
	 */
	public void updateSampleDemo(SampleDemo sa) throws ServiceException;
	/**
	 * 查询样本和项目关系
	     * @Title: getSampleDemoChargingItemList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param sa
	     * @param: @throws ServiceException      
	     * @return: void      
	     * @throws
	 */
	public List<SampleDemoChargingItemDTO> getSampleDemoChargingItemList(SampleDemoModel model) throws ServiceException;
	/**
	 * 删除样本和项目关系
	     * @Title: deleteSampleDemoChargingItem   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param sa
	     * @param: @throws ServiceException      
	     * @return: void      
	     * @throws
	 */
	public void deleteSampleDemoChargingItem(SampleDemoModel model) throws ServiceException;
	/**
	 * 增加项目和样本关系
	     * @Title: addSampleDemoChargingItem   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param sa
	     * @param: @throws ServiceException      
	     * @return: void      
	     * @throws
	 */
	public void addSampleDemoChargingItem(SampleDemoModel model,UserDTO user) throws ServiceException;
	/**
	 * 获取收费项目列表
	     * @Title: addSampleDemoChargingItem   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param model
	     * @param: @param user
	     * @param: @throws ServiceException      
	     * @return: void      
	     * @throws
	 */
	public PageReturnDTO getChagingitem(SampleDemoModel model, int page, int pageSize) throws ServiceException;
	/**
	 * 根据收费项目查询样本
	     * @Title: getItemSampleDemoPage   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param model
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<SampleDemoDTO>      
	     * @throws
	 */
	public List<SampleDemoDTO> getItemSampleDemoList(SampleDemoModel model) throws ServiceException;
	public String saveDemoTypePage(String ids, int demo_type)throws ServiceException;
	
}