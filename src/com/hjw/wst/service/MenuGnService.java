package com.hjw.wst.service;

import java.util.List;

import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.wst.DTO.WebRoleDTO;
import com.hjw.wst.DTO.WebXtgncdDTO;
import com.hjw.wst.domain.DepUser;
import com.hjw.wst.domain.DepUserPrint;
import com.hjw.wst.domain.Exam_user;
import com.hjw.wst.domain.WebRole;
import com.hjw.wst.domain.WebRoleMenu;
import com.hjw.wst.domain.WebRoleReport;
import com.hjw.wst.domain.WebUserjsb;
import com.hjw.wst.domain.WebXtgnb;
import com.hjw.wst.domain.WebXtgncd;
import com.synjones.framework.exception.ServiceException;

/**
 * 
 * <菜单、功能、角色操作> <功能详细描述>
 * 
 * @author yangm
 * @version [3.0.0, Nov 4, 2011]
 * @see [相关类/方法]
 * @since [一卡通网站查询系统]
 */
public interface MenuGnService {
	public WebXtgnb getXtgnByUrl(String url) throws ServiceException;

	/**
	 * 保存岗位角色表
	* @param wg
	* @return
	* @throws ServiceException
	* @see [类、类#方法、类#成员]
	 */
	public WebUserjsb saveWebRyjsb(WebUserjsb wg) throws ServiceException;
	
	/**
	 * 
	     * @Title: findWebXtgnbById   
	     * @Description: 获取功能实体类
	     * @param: @param id
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: WebXtgnb      
	     * @throws
	 */
	public WebXtgnb findWebXtgnbById(String id) throws ServiceException;
		
	/**
	 * 
	     * @Title: findWebUserjsByUserid   
	     * @Description: 获取人员的角色 
	     * @param: @param userid
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List      
	     * @throws
	 */
	public List<WebUserjsb> findWebUserjsByUserid(String userid)throws ServiceException;
	/**
	 * @param lAccount
	 * @return
	 * @throws ServiceException
	 */
	public List getMenuGn(String father) throws ServiceException;

	/**
	 * <一句话功能简述> <保存系统功能菜单>
	 * 
	 * @param ws
	 * @return
	 * @throws ServiceException
	 * @see [类、类#方法、类#成员]
	 */
	public WebXtgncd saveWebXtgncd(WebXtgncd ws) throws ServiceException;
	public WebXtgncd updateWebXtgncd(WebXtgncd ws) throws ServiceException;
	/**
	 * 查询全部
	     * @Title: queryAllWebRole   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<WebRoleDTO>      
	     * @throws
	 */
	public List<WebRoleDTO> queryAllWebRole() throws ServiceException;

	/**
	* <加载一条功能菜单>
	* @param id
	* @return
	* @throws ServiceException
	* @see [类、类#方法、类#成员]
	 */
	public WebXtgncd loadWebXtgncd(String id) throws ServiceException;

	/**
	 * <加载一条功能信息>
	* @param id
	* @return
	* @throws ServiceException
	* @see [类、类#方法、类#成员]
	 */
	public WebXtgnb loadWebXtgnb(String id) throws ServiceException;

	/**
	 * <获取某一类型的系统功能>
	* @param types
	* @return
	* @throws ServiceException
	* @see [类、类#方法、类#成员]
	 */
	public List getXtgn(String types,String apps) throws ServiceException;

	/**
	 * <按照使用类型获取系统功能菜单>
	* @param usertype
	* @return
	* @throws ServiceException
	* @see [类、类#方法、类#成员]
	 */
	public List<WebXtgncd> findWebXtgncdByUsertype(String usertype)
			throws ServiceException;

	/**
	 * <删除一条系统功能菜单>
	* @param id
	* @throws ServiceException
	* @see [类、类#方法、类#成员]
	 */
	public void delWebXtgncd(String id) throws ServiceException;

	 /**
	  * <分页加载角色列表>
	 * @param pagesize
	 * @param pageno
	 * @return
	 * @throws ServiceException
	 * @see [类、类#方法、类#成员]
	  */
	public PageReturnDTO getRoleList(int pagesize, int pageno,String apptype)
			throws ServiceException;

	/**
	 * <加载一条角色>
	* @param id
	* @return
	* @throws ServiceException
	* @see [类、类#方法、类#成员]
	 */
	public WebRole loadWebRole(String id) throws ServiceException;

	/**
	 * <删除角色信息>
	* @param wr
	* @throws ServiceException
	* @see [类、类#方法、类#成员]
	 */
	public void delWebRole(WebRole wr) throws ServiceException;

	/**
	 * <保存角色信息>
	* @param wr
	* @return
	* @throws ServiceException
	* @see [类、类#方法、类#成员]
	 */
	public WebRole saveWebRole(WebRole wr) throws ServiceException;

	/**
	 * <修改角色信息>
	* @param wr
	* @return
	* @throws ServiceException
	* @see [类、类#方法、类#成员]
	 */
	public WebRole updateWebRole(WebRole wr) throws ServiceException;

	/**
	 * <查询对应角色的功能菜单>
	* @param role_id
	* @return
	* @throws ServiceException
	* @see [类、类#方法、类#成员]
	 */
	public List<WebRoleMenu> fingWebRoleMenu2RoleId(String role_id)
			throws ServiceException;

	/**
	 * <保存角色功能菜单>
	* @param wrm
	* @return
	* @throws ServiceException
	* @see [类、类#方法、类#成员]
	 */
	public WebRoleMenu saveWebRoleMenu(WebRoleMenu wrm) throws ServiceException;

	/**
	 * <删除角色菜单>
	* @param rule_id
	* @throws ServiceException
	* @see [类、类#方法、类#成员]
	 */
	public void delWebRoleMenu(String rule_id,String apptype) throws ServiceException;


	/**
	 * 查询角色信息
	* @return
	* @throws ServiceException
	* @see [类、类#方法、类#成员]
	 */
	public List<WebRole> getWebRule(String apptype) throws ServiceException;


	/**
	 * 
	     * @Title: findWebgnList   
	     * @Description: 获取系统菜单
	     * @param: @param rules
	     * @param: @param xtgn_ids
	     * @param: @param path
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<WebXtgncd>      
	     * @throws
	 */
	public List<WebXtgncdDTO> findWebgnList(String rules,String xtgn_ids,String path,String app) throws ServiceException;
	public List<WebXtgncd> findUserWebgnList(boolean flag,String jobs,String userType,String path) throws ServiceException;
	public List<WebXtgncd> findCardWebgnList(String userType,String path) throws ServiceException;
	public List<WebXtgncd> findMercWebgnList(String userType,String path) throws ServiceException;
    /**
	 * 得到角色和功能对应的下级菜单数
	* @param role_id
	* @param gn_id
	* @return
	* @throws ServiceException
	* @see [类、类#方法、类#成员]
	 */
	public int fingWebRoleMenu2RoleId(String role_id, String gn_id)	throws ServiceException;
	
	/**
	 * 
	     * @Title: findWebUserjsbByUserid   
	     * @Description:获取用户角色
	     * @param: @param userid
	     * @param: @param rule_id
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List      
	     * @throws
	 */
	public List<WebUserjsb> findWebUserjsbByUserid(String userid, String rule_id,String apptype)throws ServiceException;
	/**
	 * 得到功能对应的下级菜单数
	* @param gn_id
	* @return
	* @throws ServiceException
	* @see [类、类#方法、类#成员]
	 */
	public int fingWebXtgncdfaId(String gn_id)throws ServiceException;

	public void delUserRole(String userid,String apptype) throws ServiceException;
	
	public void delUserDep(String apptype,String userid,String center_num) throws ServiceException;

	public DepUser saveDepUser(DepUser depUser) throws ServiceException;
	
	/**
	 * 
	     * @Title: fingWebReportMenu2RoleId   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param role_id
	     * @param: @param repmid
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: int      
	     * @throws
	 */
	public int fingWebReportMenu2RoleId(String role_id, long repmid) throws ServiceException;
	
	public List<WebRoleReport> fingWebRoleReport2RoleId(String role_id) throws ServiceException;
	
	/**
	 * 
	     * @Title: delWebRoleReport   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param rule_id
	     * @param: @throws ServiceException      
	     * @return: void      
	     * @throws
	 */
	public void delWebRoleReport(String rule_id) throws ServiceException;
	
	/**
	 * 
	     * @Title: saveWebRoleReport   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param wrm
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: WebRoleReport      
	     * @throws
	 */
	public WebRoleReport saveWebRoleReport(WebRoleReport wrm) throws ServiceException;
	
	/**
	 * 
	     * @Title: findWebReportList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param rolestr
	     * @param: @param reprotmen_ids
	     * @param: @param path
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<WebXtgncdDTO>      
	     * @throws
	 */
	public List<WebXtgncdDTO> findWebReportList(String rolestr, String reprotmen_ids, String path) throws ServiceException;
	public void delCenter(long user_id,String center_num) throws ServiceException;
	public Exam_user saveCenter(Exam_user exam_user) throws ServiceException;
	
	
	
	/**
	 * 删除科室打印
	     * @Title: delPrintDep   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param userid
	     * @param: @throws ServiceException      
	     * @return: void      
	     * @throws
	 */
	public void delPrintDep(String userid) throws ServiceException;
	
	public DepUserPrint saveDepUserPrint(DepUserPrint depUser) throws ServiceException;
}
