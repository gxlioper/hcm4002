package com.hjw.wst.service;

import java.util.List;

import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.wst.DTO.SystemInformsUserDTO;
import com.hjw.wst.DTO.UserDTO;
import com.hjw.wst.DTO.WebRoleDTO;
import com.hjw.wst.domain.Customer_Type;
import com.hjw.wst.domain.SystemInforms;
import com.hjw.wst.domain.SystemInforms_user;
import com.hjw.wst.domain.UserRole;
import com.hjw.wst.model.SystemInforsModel;
import com.synjones.framework.exception.ServiceException;

/**
 * 
     * @Title:  火箭蛙体检管理系统   
     * @Package com.hjw.wst.service   
     * @Description:  
     * @author: zt     
     * @date:   2016年12月8日 下午2:23:21   
     * @version V2.0.0.0
 */
public interface SystemInformsService {
	
	/**
	 * 添加
	     * @Title: addSystemInfo   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param s
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: SystemInforms      
	     * @throws
	 */
	public SystemInforms addSystemInfo(SystemInforms s) throws ServiceException;
	
	/**
	 * 更新
	     * @Title: updateSysInfo   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param s
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: SystemInforms      
	     * @throws
	 */
	public SystemInforms updateSysInfo(SystemInforms s) throws ServiceException;
	
	/**
	 * id查询
	     * @Title: queryById   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param id
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: SystemInforms      
	     * @throws
	 */
	public SystemInforms queryById(long id) throws ServiceException;

	/**
	 * 删除
	     * @Title: deleteInforms   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param s
	     * @param: @throws ServiceException      
	     * @return: void      
	     * @throws
	 */
	public String deleteInforms(SystemInforms s) throws ServiceException;
	/**
	 * 分页list
	     * @Title: getSystemInformsLis   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param model
	     * @param: @param user
	     * @param: @param pageno
	     * @param: @param pagesize
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: PageReturnDTO      
	     * @throws
	 */
	public PageReturnDTO getSystemInformsLis(SystemInforsModel model,UserDTO user, int pageno, int pagesize)throws ServiceException;
	
	/**
	 * 用户查询
	     * @Title: queryAlluser   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<SystemInformsDTO>      
	     * @throws
	 */
	public List<SystemInformsUserDTO> queryAlluser()throws ServiceException;
	
	/**
	 * 授权保存
	     * @Title: saveEmpower   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param us
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: SystemInforms_user      
	     * @throws
	 */
	public SystemInforms_user saveEmpower(SystemInforms_user us) throws ServiceException;
	
	/**
	 * 删除
	     * @Title: deleteSysinformUser   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param userid
	     * @param: @throws ServiceException      
	     * @return: void      
	     * @throws
	 */
	public void deleteSysinformUser(long informs_id) throws ServiceException;
	
	/**
	 * 关系表 id查询
	     * @Title: findWebUserjsbByUserid   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param userid
	     * @param: @param rule_id
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<SystemInforms_user>      
	     * @throws
	 */
	public List<SystemInforms_user> findSysteminforms_user(long user_id, long informs_id) throws ServiceException ;
	
	/**
	 * 角色查询全部
	     * @Title: getWebRole   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<SystemInformsUserDTO>      
	     * @throws
	 */
	public List<WebRoleDTO> getAllRole() throws ServiceException ;
	
	/**
	 * 角色id查询用户
	     * @Title: queryByRid   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param id
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<WebUserInfo>      
	     * @throws
	 */
	public List<SystemInformsUserDTO> queryByRid(String role_id )throws ServiceException;
	/**
	 * 
	     * @Title: sendSysInfo   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param inform_content
	     * @param: @param fromuserid
	     * @param: @param touserids
	     * @param: @throws ServiceException      
	     * @return: void      
	     * @throws
	 */
	public void sendSysInfo(String inform_content, long fromuserid, String touserids) throws ServiceException;
}
	
