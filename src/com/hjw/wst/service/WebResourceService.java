package com.hjw.wst.service;

import java.util.List;

import com.hjw.wst.DTO.UserDTO;
import com.hjw.wst.DTO.WebResourceDTO;
import com.hjw.wst.domain.WebResrelAtionship;
import com.hjw.wst.model.WebResourceModel;
import com.synjones.framework.exception.ServiceException;

public interface WebResourceService {
	/**
	 * 
	     * @Title: getWebResourceTable   
	     * @Description: TODO(资源授权列表)   
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<WebResourceDTO>      
	     * @throws
	 */
	public List<WebResourceDTO>  getWebResourceTable(WebResourceModel model) throws ServiceException;
	/**
	 * 
	     * @Title: addWebResource   
	     * @Description: TODO(资源授权保存)   
	     * @param: @param model
	     * @param: @throws ServiceException      
	     * @return: void      
	     * @throws
	 */
	public void  addWebResource(WebResourceModel model,UserDTO user) throws ServiceException;
	/**
	 * 
	     * @Title: updataWebResource   
	     * @Description: TODO(资源下拉框)   
	     * @param: @param model
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<WebResourceDTO>      
	     * @throws
	 */
	public List<WebResourceDTO> updataWebResourceSelect(WebResourceModel model)throws ServiceException;
	/**
	 * 
	     * @Title: deleteWebResourceSelect   
	     * @Description: TODO(删除资源)   
	     * @param: @param model
	     * @param: @throws ServiceException      
	     * @return: void      
	     * @throws
	 */
	public void deleteWebResourceSelect(WebResourceModel model)throws ServiceException;
	/**
	 * 
	     * @Title: getWebResource   
	     * @Description: TODO(id获取资源)   
	     * @param: @param model
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: WebResourceDTO      
	     * @throws
	 */
	public WebResrelAtionship  getWebResource(WebResourceModel model) throws ServiceException;
	/**
	 * 
	     * @Title: updateWebResource   
	     * @Description: TODO(修改资源)   
	     * @param: @param model
	     * @param: @throws ServiceException      
	     * @return: void      
	     * @throws
	 */
	public void  updateWebResource(WebResourceModel model,UserDTO user) throws ServiceException;
	/**
	 * 
	     * @Title: getWebResourcePD   
	     * @Description: TODO(判断资源是否存在)   
	     * @param: @param model
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: int      
	     * @throws
	 */
	public int  getWebResourcePD(WebResourceModel model) throws ServiceException;
	/**
	 * 
	     * @Title: getWebResourceDTO   
	     * @Description: TODO(获取资源描述)   
	     * @param: @param model
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<WebResourceDTO>      
	     * @throws
	 */
	public WebResourceDTO  getWebResourceDTO(WebResourceModel model) throws ServiceException;

}
