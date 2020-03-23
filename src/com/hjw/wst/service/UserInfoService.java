package com.hjw.wst.service;

import java.util.List;

import com.hjw.wst.DTO.DepUserDTO;
import com.hjw.wst.DTO.DepartmentDepDTO;
import com.hjw.wst.DTO.ECenterDTO;
import com.hjw.wst.DTO.ExamInfoCountDTO;
import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.wst.DTO.SysVersionDTO;
import com.hjw.wst.DTO.SystemInformsDTO;
import com.hjw.wst.DTO.UserCenterDepListDTO;
import com.hjw.wst.DTO.UserInfoDTO;
import com.hjw.wst.domain.Exam_user;
import com.hjw.wst.domain.WebResrelAtionship;
import com.hjw.wst.domain.WebUserInfo;
import com.synjones.framework.exception.ServiceException;

public interface UserInfoService {
	 /**
	  * <分页加载用户列表>
	 * @param pagesize
	 * @param pageno
	 * @return
	 * @throws ServiceException
	 * @see [类、类#方法、类#成员]
	  */
	public PageReturnDTO getUserInfoList(String chi_name, String log_name, String phone_num,String dep_name,String role_name,  int pagesize, int pageno, String startStop,String center_num)throws ServiceException;

	public WebUserInfo loadUserInfo(long id) throws ServiceException;
	
	public Exam_user saveExamUser(Exam_user eu) throws ServiceException;
	
	public WebUserInfo saveUserInfo(WebUserInfo wr) throws ServiceException;

	public WebUserInfo updateUserInfo(WebUserInfo wr) throws ServiceException;
	
	public WebUserInfo delUserInfo(WebUserInfo wr) throws ServiceException;
	
	public WebUserInfo getUserInfoByname(String name) throws ServiceException;

	/**
	 * 查询所有科室信息
	     * @Title: getAllDepartmenterDep   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<DepUserDTO>      
	     * @throws
	 */
	public List<DepUserDTO> getAllDepartmenterDep(String center_num) throws ServiceException;
	
	/**
	 * 
	     * @Title: getDepartmenterDepForUserId   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param userid
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<DepUserDTO>      
	     * @throws
	 */
	public List<DepUserDTO> getDepartmenterDepForUserId(long userid,String centerNum) throws ServiceException;
	/**
	 * 科室打印
	     * @Title: getDepUserPrint   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param user_id
	     * @param: @param dep_id
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: DepUserDTO      
	     * @throws
	 */
	public DepUserDTO getDepUserPrint(String user_id,long dep_id,String centerNum) throws ServiceException;
	
	/**
	 * 查询用户科室信息
	     * @Title: getDepUser   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param user_id
	     * @param: @param dep_id
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: DepUserDTO      
	     * @throws
	 */
	public DepUserDTO getDepUser(String user_id,long dep_id,String apptype) throws ServiceException;
	
	/**
	 * 查询用户科室信息
	     * @Title: getDepUser   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param user_id
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<DepUserDTO>      
	     * @throws
	 */
	public List<DepUserDTO> getDepUser(String userName,String apptype,String centerNum,String sort) throws ServiceException;
	
	/**
	 * 查询用户体检中心信息
	     * @Title: getCenterByUserName   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param userName
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: UserCenterDepListDTO      
	     * @throws
	 */
	public List<UserCenterDepListDTO> getCenterByUserName(String userName) throws ServiceException;
	
	/**
	 * 
	     * @Title: getCenterByUserId   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param userid
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<UserCenterDepListDTO>      
	     * @throws
	 */
	public List<UserCenterDepListDTO> getCenterByUserId(String userid) throws ServiceException;
	
	/**
	 * 
	     * @Title: getCenterByCenterNum   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param centerNum
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<DepartmentDepDTO>      
	     * @throws
	 */
	public List<DepartmentDepDTO> getCenterByCenterNum(String centerNum) throws ServiceException;
	
	/**
	 * 
	     * @Title: getCenterByCenterNum   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param userid
	     * @param: @param apptype
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<DepartmentDepDTO>      
	     * @throws
	 */
	public List<DepartmentDepDTO> getCenterByCenterNum(String userid,String centernum,String apptype) throws ServiceException;
	
	/**
	 * 
	     * @Title: getCenterByCenId   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param centerid
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: UserCenterDepListDTO      
	     * @throws
	 */
	public UserCenterDepListDTO getCenterByCenId(String centerid) throws ServiceException;
	
	/**
	 *   获取所以体检中心信息
	     * @Title: getCenterAll   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: UserCenterDepListDTO      
	     * @throws
	 */
	public List<UserCenterDepListDTO> getCenterAll() throws ServiceException;
	
	/**
	 *   获取所有体检中心信息
	     * @Title: getCenterAll   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws ServiceException   
	     * @return: UserCenterDepListDTO      
	     * @throws
	 */
	public List<ECenterDTO> getCheckCenterAll() throws ServiceException;
	/**
	 * 
	     * @Title: getUsername   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param userName
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<UserInfoDTO>      
	     * @throws
	 */
	public UserInfoDTO getUsername(String userName) throws ServiceException;
	
	/**
	 * 模糊查询用户名
	     * @Title: autoGetUsername   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param userName
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<String>      
	     * @throws
	 */
	public List<UserInfoDTO> autoGetUsername(String userName,String center_num) throws ServiceException;
	
	/**
	 * 查询用户科室信息
	     * @Title: getDepUser   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param user_id
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<DepUserDTO>      
	     * @throws
	 */
	public List<DepUserDTO> getDepUserByID(long user_id,String apptype, String center_num , String sort) throws ServiceException;
	
	/**
	 * 查询用户通知信息
	     * @Title: getSystemInfomsDTO   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<SystemInformsDTO>      
	     * @throws
	 */
	public SystemInformsDTO getSystemInfomsDTO(long user_id) throws ServiceException;
	/**
	 * 标记 系统通知
	     * @Title: updateUserSystemInfroms   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param messid
	     * @param: @param user_id
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: String      
	     * @throws
	 */
	
	public String updateUserSystemInfroms(long messid,long user_id) throws ServiceException;
	/**
	 * 查询用户名，科室名
	     * @Title: getCenterByUserName   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param userName
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: UserCenterDepListDTO      
	     * @throws
	 */
	public UserCenterDepListDTO getCenterByUserIdAndDepId(long userId,long depId) throws ServiceException;
	
	/**
	 * 查询本科室当天体检人数
	     * @Title: getCheckedCustomerCount   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param dep_id
	     * @param: @param date
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<ExamInfoCountDTO>      
	     * @throws
	 */
	public List<ExamInfoCountDTO> getCheckedCustomerCount(long dep_id, String center_num,String date) throws ServiceException;
	/**
	 * 
	     * @Title: getUserInfoLike   
	     * @Description: 用户模糊查询(这里用一句话描述这个方法的作用)   
	     * @param: @param name
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<WebUserInfo>      
	     * @throws
	 */
	public List<WebUserInfo> getUserInfoLike(String name) throws ServiceException;
	/**
	 * 员工编码查询
	     * @Title: queryByNum   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param work_num
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: WebUserInfo      
	     * @throws
	 */
	public  WebUserInfo queryByNum(String work_num) throws ServiceException;
	/**
	 * 获取资源
	     * @Title: getWebResource   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param user_id
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<WebResrelAtionship>      
	     * @throws
	 */
	public  List<WebResrelAtionship>  getWebResource(long user_id) throws ServiceException;
	
	public void saveUserExamDept(String user_id,String exam_center_parent_id) throws ServiceException;
	
//	public PageReturnDTO getLisPrint(String  exam_num) throws ServiceException;
	

	/**
	 * 
	     * @Title: getSysVersion   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: String      
	     * @throws
	 */
	public String getSysVersion() throws ServiceException;
	
	/**
	 * 
	     * @Title: getSysVersionList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: String      
	     * @throws
	 */
	public List<SysVersionDTO> getSysVersionList() throws ServiceException;


	public WebUserInfo getUserInfo(long id) throws ServiceException;
	
	/**
	 * 
	     * @Title: getUserInfoByWorkNum   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param name
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: WebUserInfo      
	     * @throws
	 */
	public WebUserInfo getUserInfoByWorkNum(String name) throws ServiceException;
	
	public PageReturnDTO getLisPrint(String  exam_num) throws ServiceException;
}
