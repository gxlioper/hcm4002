package com.hjw.wst.service;

import java.util.List;

import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.wst.DTO.SampleDemoDTO;
import com.hjw.wst.domain.ViewCommonWords;
import com.synjones.framework.exception.ServiceException;

/**
 * 影像科室常用词管理
     * @Title:  火箭蛙体检管理系统   
     * @Package com.hjw.wst.service   
     * @Description:  
     * @author: dangqi     
     * @date:   2016年10月31日 下午7:59:01   
     * @version V2.0.0.0
 */
public interface ViewCommonWordsService {

	/**
	 * 查询影像科室常用词列表
	     * @Title: getViewCommonWordsList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param simpleId
	     * @param: @param pagesize
	     * @param: @param pageno
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: PageReturnDTO      
	     * @throws
	 */
	public PageReturnDTO getViewCommonWordsList(long simpleId,int pagesize, int pageno) throws ServiceException;

	/**
	 * 保存常用词
	     * @Title: saveViewCommonWords   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param viewCommonWords
	     * @param: @return      
	     * @return: String      
	     * @throws
	 */
	public ViewCommonWords saveViewCommonWords(ViewCommonWords viewCommonWords)throws ServiceException;
	
	/**
	 * 修改常用词
	     * @Title: updateViewCommonWords   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param viewCommonWords
	     * @param: @return      
	     * @return: String      
	     * @throws
	 */
	public ViewCommonWords updateViewCommonWords(ViewCommonWords viewCommonWords)throws ServiceException;
	
	/**
	 * 根据ID查询常用词
	     * @Title: getViewCommonWords   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param id
	     * @param: @return      
	     * @return: ViewCommonWords      
	     * @throws
	 */
	public ViewCommonWords getViewCommonWords(long id)throws ServiceException;
	
	/**
	 * 查询影像科室样本
	     * @Title: getSampleList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param sample_name
	     * @param: @return      
	     * @return: List<SampleDemoDTO>      
	     * @throws
	 */
	public List<SampleDemoDTO> getSampleList()throws ServiceException;
	
	/**
	 * 查询默认结果词
	     * @Title: getDefaultViewCommonWords   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param sample_id
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: ViewCommonWords      
	     * @throws
	 */
	public ViewCommonWords getDefaultViewCommonWords(long sample_id) throws ServiceException;
}
