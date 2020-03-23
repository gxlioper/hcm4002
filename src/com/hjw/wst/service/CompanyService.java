package com.hjw.wst.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.hjw.crm.DTO.CompanyInfoDTO;
import com.hjw.wst.DTO.JobDTO;
import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.wst.DTO.TreeDTO;
import com.hjw.wst.DTO.UserDTO;
import com.hjw.wst.DTO.WebXtgncdDTO;
import com.hjw.wst.domain.CompanyDepartment;
import com.hjw.wst.domain.CompanyInfo;
import com.hjw.wst.domain.DataDictionary;
import com.hjw.wst.domain.WebRole;
import com.hjw.wst.domain.WebRoleMenu;
import com.hjw.wst.domain.WebUserjsb;
import com.hjw.wst.domain.WebXtgnb;
import com.hjw.wst.domain.WebXtgncd;
import com.hjw.wst.model.CompanyInfoModel;
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
public interface CompanyService {
	
	/**
	 * 
	     * @Title: GetCreateID   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param com_level 单位级别
	     * @param: @param parent_com_id 父级单位编码(一级单位传000)
	     * @param: @param param_com_num
	     * @param: @return      
	     * @return: String      
	     * @throws
	 */
public String GetCreateID(long com_level,long parent_com_id,String param_com_num);

	/**
	 * 
	     * <p>Title: getComByFatherID</p>   
	     * <p>通过father获取单位 </p>   
	     * @param father
	     * @return
	     * @throws ServiceException   
	     * @see com.hjw.wst.service.CompanyService#getComByFatherID(java.lang.String)
	 */
	public List<CompanyInfo> getComByFatherID(long father) throws ServiceException;
	
	/**
	 * 
	     * @Title: saveCompanyInfo   
	     * @Description: 增加单位   
	     * @param: @param companyInfo
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: CompanyInfo      
	     * @throws
	 */
	public CompanyInfo saveCompanyInfo(CompanyInfo companyInfo,UserDTO user) throws ServiceException;
	
	/**
	 * 
	     * @Title: saveCompanyInfo   
	     * @Description: 修改单位   
	     * @param: @param companyInfo
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: CompanyInfo    CompanyInfo    
	     * @throws
	 */
	public CompanyInfo updateCompanyInfo(CompanyInfo companyInfo,UserDTO user) throws ServiceException;

    /**
     * 逻辑删除
     * @param companyInfo
     * @param user
     * @throws ServiceException
     */
    public void removeCompanyInfo(CompanyInfo companyInfo) throws ServiceException;
	
	public int updateCompanyInfoIsActive(CompanyInfo companyInfo,UserDTO user) throws ServiceException;
	/**
	 * 
	     * <p>Title: getComByFatherID</p>   
	     * <p>通过id获取单位 </p>   
	     * @param father
	     * @return
	     * @throws ServiceException   
	     * @see com.hjw.wst.service.CompanyService#getComByFatherID(java.lang.String)
	 */
	public CompanyInfo getComByID(long id) throws ServiceException;
	public CompanyInfoDTO getCenterComByID(long id,String center_num)  throws ServiceException;
	
	/**
	 * 
	     * <p>Title: getComByFatherID</p>   
	     * <p>通过id获取单位 </p>   
	     * @param father
	     * @return
	     * @throws ServiceException   
	     * @see com.hjw.wst.service.CompanyService#getComByFatherID(java.lang.String)
	 */
	public CompanyInfo findComByID(long id) throws ServiceException;
	
	public CompanyInfo findComBySignNum(String sign_num) throws ServiceException;
	/**
	 * 
	     * @Title: findComByName   
	     * @Description: 通过部门名称获取编号   
	     * @param: @param comname
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: CompanyInfo      
	     * @throws
	 */
	public CompanyInfo findComByName(String comname) throws ServiceException;
	
	/**
	 * 
	     * @Title: saveCompanyDepartment   
	     * @Description: 保存部门  
	     * @param: @param cdt
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: CompanyDepartment      
	     * @throws
	 */
	public CompanyDepartment saveCompanyDepartment(CompanyDepartment cdt) throws ServiceException;
	

	/**
	 * 
	     * @Title: saveCompanyInfo   
	     * @Description: 单位级别修改   
	     * @param: @param companyInfo
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: CompanyInfo      
	     * @throws
	 */
	public boolean chargeCompanyInfo(CompanyInfo cif) throws ServiceException;
	
	/**
	 * 
	     * @Title: saveCompanyDepartment   
	     * @Description: 修改部门  
	     * @param: @param cdt
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: CompanyDepartment      
	     * @throws
	 */
	public CompanyDepartment updateCompanyDepartment(CompanyDepartment cdt) throws ServiceException;
	
	/**
	 * 
	     * @Title: saveCompanyDepartment   
	     * @Description: 删除部门  
	     * @param: @param cdt
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: CompanyDepartment      
	     * @throws
	 */
	public void delCompanyDepartment(CompanyDepartment cdt) throws ServiceException;
	/**
	 * 
	     * @Title: getComByLevel   
	     * @Description: TODO通过层级获取单位 
	     * @param: @param levels
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<CompanyInfo>      
	     * @throws
	 */
	public List<CompanyInfo> getComByLevel(String levels) throws ServiceException;
	
	/**
	 * 
	     * @Title: getCompanyDepartmentByid   
	     * @Description: 通过单位id获取部门
	     * @param: @param com_id
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<CompanyDepartment>      
	     * @throws
	 */
	public CompanyDepartment getCompanyDepartmentByid(long com_id) throws ServiceException;

	/**
	 * 
	     * @Title: getDatadis   
	     * @Description: 获取数据字典  
	     * @param: @param data_code
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<JobDTO>      
	     * @throws
	 */
	public List<JobDTO> getDatadis(String data_code,String data_class)throws ServiceException;
	
	/**
	 * 
	     * @Title: getDatadis   
	     * @Description: 获取数据字典  
	     * @param: @param data_code
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<JobDTO>      
	     * @throws
	 */
	public DataDictionary getDatadisForId(long id)throws ServiceException;
	
	/**
	 * 
	     * @Title: getDwList   
	     * @Description: 获取单位对于部门   
	     * @param: @param company_Id
	     * @param: @param pagesize
	     * @param: @param pageno
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: PageReturnDTO      
	     * @throws
	 */
	public PageReturnDTO getDwList(long company_Id,int pagesize, int pageno) throws ServiceException;
	
	/**
	 * 
	     * @Title: getCompanyDepartmentByFatherid   
	     * @Description: 获取部门下的单位   
	     * @param: @param company_Id
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<CompanyDepartment>      
	     * @throws
	 */
	@SuppressWarnings("unchecked")
	public List<CompanyDepartment> getCompanyDepartmentByComid(long company_Id) throws ServiceException;
	
	/**
	 * 
	     * @Title: getCompanyForName   
	     * @Description: 获取所有单位名称为name的模糊查询
	     * @param: @param name
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<TreeDTO>      
	     * @throws
	 */
	public List<TreeDTO> getCompanyForName(String name)throws ServiceException;
	
	/**
	 * 
	     * @Title: getCompanyForName   
	     * @Description: 获isactive状态的单位
	     * @param: @param name
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<TreeDTO>      
	     * @throws
	 */
	public List<TreeDTO> getCompanyForNameIsactive(String isactive)throws ServiceException;
	
	/**
	 * 
	     * @Title: getCompanyDepartmentByid   
	     * @Description: 获取部门  
	     * @param: @param com_id
	     * @param: @param dep_Name
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: CompanyDepartment      
	     * @throws
	 */
	public CompanyDepartment getCompanyDepartmentByid(long com_id,String dep_Name) throws ServiceException;

	/**
	 * 
	     * @Title: getDatadisForName   
	     * @Description: 通过姓名获取数据字典  
	     * @param: @param dataname
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: JobDTO      
	     * @throws
	 */
	public JobDTO getDatadisForName(String types,String dataname)throws ServiceException;
	
	/**
	 * 
	     * @Title: getDatadisForName   
	     * @Description: 通过子编码获取数据字典  
	     * @param: @param dataname
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: JobDTO      
	     * @throws
	 */
	public JobDTO getDatadisForChildrenCode(String types,String dataname)throws ServiceException;
	
	/**
	 * 
	     * @Title: getComByName   
	     * @Description:根据单位名称查询单位
	     * @param: @param comname
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: CompanyInfo      
	     * @throws
	 */
	public CompanyInfo getComByName(long id,String comname,UserDTO user) throws ServiceException;
	
	/**
	 * 
	     * @Title: getCompanyDepartmentById   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param id
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: CompanyDepartment      
	     * @throws
	 */
	public CompanyDepartment getCompanyDepartmentById(long id) throws ServiceException;
	
	/**
	 * 
	     * @Title: getDataDictionaryByid   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param id
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: DataDictionary      
	     * @throws
	 */
	public DataDictionary getDataDictionaryByid(String id) throws ServiceException;
	
	/**
	 * 获取移动的层的最大值
	     * @Title: getcompanyByNum   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param paramString
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: int      
	     * @throws
	 */
	public int getcompanyById(long comid)throws ServiceException;
	
	/**
	 * 
	     * @Title: getcompanySubNodeById   
	     * @Description:   判断目标结点是否源结点的子结点，如果是 返回true，否则返回false
	     * @param: @param oldid
	     * @param: @param newid
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: boolean      
	     * @throws
	 */
	public boolean getcompanySubNodeById(long oldid,long newid)throws ServiceException;
	
	/**
	 * 
	     * @Title: getCompanyPersonForId   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param 通过id获取子菜单
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<TreeDTO>      
	     * @throws
	 */
	public List<TreeDTO> getCompanyPersonForId(long id)throws ServiceException;
	
	/**
	 * 
	     * @Title: getcompanyParsentNodeById   
	     * @Description: 在customer_company保存要导入的部门的父级部门  并返回当前 selfcomid 在customer_company部门的id
	     * @param: @param selfcomid
	     * @param: @param rescomid
	     * @param: @param userid
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: long      
	     * @throws
	 */
	public void getcompanyParsentNodeById(long selfcomid,long rescomid,long userid,long centerid)throws ServiceException;
	
	
	/**
	 * 
	     * @Title: getCompanyForBatchComId   
	     * @Description: 通过体检计划获取计划内部门   
	     * @param: @param id
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<TreeDTO>      
	     * @throws
	 */
	public List<TreeDTO> getCompanyForBatchComId(long id)throws ServiceException;
	
	/**
	 * 
	     * @Title: findComByComNum   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param comnum
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: int      
	     * @throws
	 */
	public int findComByComNum(long id,String comnum,int edittype) throws ServiceException;
	
	/**
	 * 
	     * @Title: getUsers   
	     * @Description: 获取用户数据  
	     * @param: @param data_code
	     * @param: @param exam_center_id
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<JobDTO>      
	     * @throws
	 */
	public List<JobDTO> getUsers(long exam_center_id)throws ServiceException;
	
	/**
	 * 
	     * @Title: getReportMenu   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param exam_center_id
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<JobDTO>      
	     * @throws
	 */
	public List<JobDTO> getReportMenu()throws ServiceException;
	
	/**
	 * 
	     * @Title: getCompanyForAll   
	     * @Description: 获取所有节点对应的包括子节点 
	     * @param: @param comid
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<TreeDTO>      
	     * @throws
	 */
	public List<TreeDTO> getCompanyForAll(long comid)throws ServiceException;
	
	/**
	 * 
	     * @Title: getDatadis   
	     * @Description: 获取经济类型
	     * @param: @param data_code
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<JobDTO>      
	     * @throws
	 */
	public List<JobDTO> getDatadisjjlx()throws ServiceException;
	
	/**
	 * 
	     * @Title: getDatadishylx   
	     * @Description: 行业类型  
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<JobDTO>      
	     * @throws
	 */
	public List<JobDTO> getDatadishylx()throws ServiceException;
	
	/**
	 * 
	     * @Title: getDatadisdanwgm   
	     * @Description:单位规模   
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<JobDTO>      
	     * @throws
	 */
	public List<JobDTO> getDatadisdanwgm()throws ServiceException;
	
	/**
	 * 
	     * @Title: getDatadis   
	     * @Description: 获取数据字典  
	     * @param: @param data_code
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<JobDTO>      
	     * @throws
	 */
	public List<JobDTO> getDatadis(String data_code)throws ServiceException;
	
	/**
	 * 
	     * @Title: getDatadixzqh   
	     * @Description: 行政区划   
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<JobDTO>      
	     * @throws
	 */
	public List<JobDTO> getDatadixzqh()throws ServiceException;
	
	public List<JobDTO> getDegreeOfedu()throws ServiceException;
	
	public List<TreeDTO> getCompanyForNameIsactive(String name,String isactive,String ispower,long userid,UserDTO user)throws ServiceException;
	public List<TreeDTO> getZybCompanyForNameIsactive(String name,String isactive,String ispower,long userid,UserDTO user)throws ServiceException;
	
	/**
	 * 
	     * @Title: getBatchForComId   
	     * @Description: 根据单位ID获取批次      
	     * @param: @param id
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<TreeDTO>      
	     * @throws
	 */
	public List<TreeDTO> getBatchForComId(long id,String apptype,String center_num)throws ServiceException;
	/**
	 * 
	     * @Title: getCompanyInfoList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<TreeDTO>      
	     * @throws
	 */
	public PageReturnDTO getCompanyInfoList(CompanyInfoModel model,int page,int pageSize,UserDTO user)  throws ServiceException;
	/**
	 * 提取单位
	     * @Title: saveCompanInfo   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param model
	     * @param: @param user
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<CompanyInfoDTO>      
	     * @throws
	 */
	public String saveCompanInfo(CompanyInfoModel model,UserDTO user)  throws ServiceException;
	
}
