package com.hjw.wst.service;

import java.util.List;

import com.hjw.wst.DTO.*;
import com.hjw.wst.domain.DepartmentDep;
import com.hjw.wst.domain.NoteData;
import com.hjw.wst.domain.Scheduling;
import com.hjw.wst.model.DepartmentModel;
import com.synjones.framework.exception.ServiceException;

/**
 * 
 * <菜单、功能、角色操作> <功能详细描述>
 * 
 * @author zt
 * @version [3.0.0, Nov 4, 2011]
 * @see [相关类/方法]
 * @since [一卡通网站查询系统]
 */
public interface DepartmentService {
	/**
	 * 
	     * @Title: addDepart   
	     * @Description: 增加科室(这里用一句话描述这个方法的作用)   
	     * @param: @param dept
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: boolean      
	     * @throws
	 */
	public DepartmentDep addDepart(DepartmentDep dep) throws ServiceException;
		/**
		 * 
		     * @Title: deleteDept   
		     * @Description: 删除科室(这里用一句话描述这个方法的作用)   
		     * @param: @param deptid
		     * @param: @return
		     * @param: @throws ServiceException      
		     * @return: boolean      
		     * @throws
		 */
	public void deleteDept(DepartmentDep dep) throws ServiceException;

	/**
	 * 
	     * @Title: updateDept   
	     * @Description: 科室更新(这里用一句话描述这个方法的作用)   
	     * @param: @param dep
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: boolean      
	     * @throws
	 */
	public DepartmentDep updateDept(DepartmentDep dep) throws ServiceException;

	
	
	/**
	 * 
	     * @Title: queryAll   
	     * @Description: 查询全部(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<department_dep>      
	     * @throws
	 */
	public List<DepartmentDepDTO> queryAllDepartmentDep( String center_num ) throws ServiceException;

	/**
	 * 
	     * @Title: loadDepartmentDep   
	     * @Description: 根据科室编号查询(这里用一句话描述这个方法的作用)   
	     * @param: @param deptid
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: DepartmentDep      
	     * @throws
	 */
	
	public DepartmentDep loadDepartmentDep(long depid) throws ServiceException;

	/**
 * 编码查询
     * @Title: queryByNum   
     * @Description: TODO(这里用一句话描述这个方法的作用)   
     * @param: @param dep_num
     * @param: @return
     * @param: @throws ServiceException      
     * @return: DepartmentDep      
     * @throws
 */
	
	public  DepartmentDep queryByNum(String dep_num) throws ServiceException;
	
	/**
	 * 
	     * @Title: queryPage   
	     * @Description: 分页查询(这里用一句话描述这个方法的作用)   
	     * @param: @param page
	     * @param: @param pageSize
	     * @param: @return      
	     * @return: PageSupport      
	     * @throws
	 */
	
	public PageReturnDTO queryPageDepartmentDep(int pageno, int pagesize,String center_num);
	
	/**
	 * 科室管理分页查询
	     * @Title: departmentDeByPage   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param pageno
	     * @param: @param pagesize
	     * @param: @return      
	     * @return: PageReturnDTO      
	     * @throws
	 */
	
	public PageReturnDTO departmentList(String dep_name,String dep_num,String dep_category,String sex, String centerNum, int pageno, int pagesize);
	
	/**
	 * 科室管理分页查询
	     * @Title: departmentDeByPage   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param pageno
	     * @param: @param pagesize
	     * @param: @return      
	     * @return: PageReturnDTO      
	     * @throws
	 */
	
	public PageReturnDTO departmentListAll(String dep_name,String dep_num,String dep_category,String sex,  int pageno, int pagesize,DepartmentModel model);
	
	/**
	 * 
	     * @Title: schedulinglist   
	     * @Description: 排班列表(这里用一句话描述这个方法的作用)   
	     * @param: @param page
	     * @param: @param pageSize
	     * @param: @return      
	     * @return: PageSupport      
	     * @throws
	 */
	public PageReturnDTO schedulinglist(String user_name,int page, int pageSize);
	/**
	 * 保存排班信息
	     * @Title: saveschedu   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return      
	     * @return: Scheduling      
	     * @throws
	 */
	public Scheduling saveschedu(Scheduling scheduling);
	/**
	 * 获取排班信息
	     * @Title: getScheduledetails   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param user_id
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<SchedulingDTO>      
	     * @throws
	 */
	public List<SchedulingDTO> getScheduledetails(long user_id) throws ServiceException;
	/**
	 * 首页排班信息
	     * @Title: querypaibanList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param userid
	     * @param: @param user
	     * @param: @param m_id
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<SchedulingDTO>      
	     * @throws
	 */
	public List<SchedulingDTO> querypaibanList(long userid,UserDTO user,long m_id,long y_id) throws ServiceException;
	/**
	 * 添加备忘录
	     * @Title: addNoteData   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param n
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: NoteData      
	     * @throws
	 */
	public NoteData addNoteData(NoteData n) throws ServiceException;
	/**
	 * 一周备忘录
	     * @Title: querybeiwangList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param userid
	     * @param: @param user
	     * @param: @param wk_id
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<SchedulingDTO>      
	     * @throws
	 */
	public List<SchedulingDTO> querybeiwangList(long userid,UserDTO user,long wk_id) throws ServiceException;
	/**
	 * 备忘录更新
	     * @Title: updateNote   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param n
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: NoteData      
	     * @throws
	 */
	public NoteData updateNote(NoteData n) throws ServiceException;
	/**
	 * 排班按日期查询
	     * @Title: queryDate   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param d
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: SchedulingDTO      
	     * @throws
	 */
	public NoteData queryDate(String d) throws ServiceException;
	/**
	 * 备忘录删除
	     * @Title: deleteNote   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param n
	     * @param: @throws ServiceException      
	     * @return: void      
	     * @throws
	 */
	public void deleteNote(NoteData n) throws ServiceException;
	/**
	 * 备忘录id查询
	     * @Title: loadNote   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param n_id
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: NoteData      
	     * @throws
	 */
	public NoteData loadNote(long n_id) throws ServiceException;
	/**
	 * 排班删除
	     * @Title: deleteSched   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param u_id
	     * @param: @param d_m
	     * @param: @throws ServiceException      
	     * @return: void      
	     * @throws
	 */
	public void deleteSched(String u_id,String d_m,String d_y) throws ServiceException;
	
	public List<SchedulingDTO> chakanpaiabn(long userid,long m_id,long y_id) throws ServiceException;
	
	/**
	 * 
	     * @Title: updateCIRate   
	     * @Description: 更新收费项目 利润率和利润金额   
	     * @param: @param deptid
	     * @param: @param rate
	     * @param: @throws ServiceException      
	     * @return: void      
	     * @throws
	 */
	public void updateCIRate(long deptid,long rate) throws ServiceException;
	public void centerupdateCIRate(long deptid,long rate,UserDTO user,DepartmentModel model) throws ServiceException;
	/**
	 * 获取当前体检中心科室
	     * @Title: getCenterDepartmentList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<DepartmentDepDTO>      
	     * @throws
	 */
	public List<DepartmentDepDTO> getCenterDepartmentList(UserDTO user) throws ServiceException;
	
	/**
	 * 
	     * @Title: getCenterforDepid   
	     * @Description:部门编号和体检编号，判断是否在部门体检中心关系表里面存在
	     * @param: @param depid
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: String      
	     * @throws
	 */
	public String getCenterforDepid(String depid,String centerNum)throws ServiceException;
	
	/**
	 * 
	     * @Title: saveDepartmentCenter   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param depid
	     * @param: @param centers
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: String      
	     * @throws
	 */
	public String saveDepartmentCenter(String depid,String centers,long userid)throws ServiceException;
	
	public String saveDepartmentBachCenter(String dep_ids,String centers,long userid)throws ServiceException;
	/**
	 * 批量设置当前体检中心科室
	     * @Title: updateBatcahDepCenter   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param model
	     * @param: @param user
	     * @param: @return      
	     * @return: String      
	     * @throws
	 */
	public String updateBatcahDepCenter(DepartmentModel model,UserDTO user)throws ServiceException;;
	public String deleteCenterDep(DepartmentModel model,UserDTO user)throws ServiceException;
	/**
	 * 多体检中心-获取科室信息
	     * @Title: getCenterDep   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param model
	     * @param: @param user
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: String      
	     * @throws
	 */
	public  DepartmentDepDTO getCenterDep(DepartmentModel model,UserDTO user)throws ServiceException;
	/**
	 * 多体检中心-修改科室信息
	     * @Title: updateCenterDep   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param model
	     * @param: @param user
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: int      
	     * @throws
	 */
	public  int updateCenterDep(DepartmentModel model,UserDTO user)throws ServiceException;

	/**
	 * 按照医生和体检号获取科室和收费项目 树
	 * @Title: getDepartmentDepListByExamnum
	 * @Description: TODO(科室部门启用/停用)
	 * @param: @return
	 * @param: @throws WebException
	 * @return: String
	 * @throws
	 */
	public List<DepartmentDepDTO> getDepartmentDepListByExamnum(DepartmentModel model, UserDTO user)throws ServiceException;

	/**
	 * 查询科室结论 建议。
	 * @Title: getDepartmentResultById
	 * @Description: TODO()
	 * @param: @return
	 * @param: @throws WebException
	 * @return: String
	 * @throws
	 */
	public ExamDepResultDTO getDepartmentResultById(DepartmentModel departmentModel, UserDTO user)throws ServiceException;

    /**
     * 同部数据到多体检中心
     * @param departmentModel
     * @return
     * @throws ServiceException
     */
    public String saveDepCenter(DepartmentModel departmentModel,UserDTO user)throws ServiceException;

}
	
