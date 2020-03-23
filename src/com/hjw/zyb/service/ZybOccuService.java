package com.hjw.zyb.service;


import java.sql.SQLException;
import java.util.List;

import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.wst.DTO.TreeDTO;
import com.hjw.zyb.DTO.ZybEconomicTreeDTO;
import com.hjw.zyb.domain.ZybAreaCode;
import com.hjw.zyb.domain.ZybEconomicIndustry;
import com.hjw.zyb.domain.ZybPhyexaclass;
import com.hjw.zyb.domain.ZybTypeOfWork;
import com.synjones.framework.exception.ServiceException;
import com.synjones.framework.exception.WebException;

/**
 * 
 * <菜单、功能、角色操作> <功能详细描述>
 * 
 * @author zt
 * @version [3.0.0, Nov 4, 2011]
 * @see [相关类/方法]
 * @since [一卡通网站查询系统]
 */
public interface ZybOccuService {
	
	
//---------------------------------------经济行业------------------------------------------------------------
	
	
	
	/**
	 * 行业_增
	     * @Title: addEconomic   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param ei
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: ZYB_EconomicIndustry      
	     * @throws
	 */
	public ZybEconomicIndustry addIndustry(ZybEconomicIndustry ei) throws ServiceException;
	/**
	 * 行业_删
	     * @Title: deleteEconomic   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param ei
	     * @param: @throws ServiceException      
	     * @return: void      
	     * @throws
	 */
	public void deleteIndustryc(ZybEconomicIndustry ei) throws ServiceException;
	/**
	 * 行业_改
	     * @Title: updateEconomic   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param ei
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: ZYB_EconomicClass      
	     * @throws
	 */
	public ZybEconomicIndustry updateIndustry(ZybEconomicIndustry ei) throws ServiceException;
	/**
	 * 行业_查
	     * @Title: queryByAll_EI   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List      
	     * @throws
	 */
	//public PageReturnDTO queryByIndustry(String industry_code,String industry_name,int pagesize, int pageno) throws ServiceException;
	public PageReturnDTO queryByIndustry(String industry_code,String industry_name,int industry_type, int pagesize, int pageno) throws ServiceException;
	/**
	 * 行业_ID查
	     * @Title: queryById_EI   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param id
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: ZYB_EconomicIndustry      
	     * @throws
	 */
	public ZybEconomicIndustry  queryById_EI(String id) throws ServiceException;

	
	
	
	/**
	 * 经济行业查询全部
	     * @Title: getIndustryAll   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<ZybEconomicTreeDTO>      
	     * @throws
	 */
	 //public List<ZybEconomicTreeDTO> getIndustryAll() throws ServiceException;
	public List<ZybEconomicTreeDTO> getIndustryAllByType(int industry_type) throws ServiceException;
	 
	 
	 
	 /**
	  * 经济行业id查询返回多个对象
	      * @Title: getind   
	      * @Description: TODO(这里用一句话描述这个方法的作用)   
	      * @param: @param id
	      * @param: @return
	      * @param: @throws ServiceException      
	      * @return: List<ZybEconomicTreeDTO>      
	      * @throws
	  */
	 public List<ZybEconomicTreeDTO> getind(String id) throws ServiceException;
	 
	 
	 
	 /**
	  * 经济行业父级单位名称查询
	      * @Title: getp_name   
	      * @Description: TODO(这里用一句话描述这个方法的作用)   
	      * @param: @param id
	      * @param: @return
	      * @param: @throws ServiceException      
	      * @return: ZybEconomicIndustry      
	      * @throws
	  */
	 public ZybEconomicIndustry getp_name(String id) throws ServiceException;
	 
	 
	 /**
	  * 编码验证
	      * @Title: getIndustrycode   
	      * @Description: TODO(这里用一句话描述这个方法的作用)   
	      * @param: @param code
	      * @param: @return
	      * @param: @throws ServiceException      
	      * @return: ZybEconomicIndustry      
	      * @throws
	  */
	 public ZybEconomicIndustry getIndustrycode(String code)throws ServiceException;
	
	
	
//---------------------------------------工种------------------------------------------------------------
	

	
	
	
	/**
	 * 工种_增
	     * @Title: addEconomic   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param tow
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: ZYB_TypeOfWork      
	     * @throws
	 */
	public ZybTypeOfWork addTypeOfWork(ZybTypeOfWork tow) throws ServiceException;
	/**
	 * 工种_删
	     * @Title: deleteEconomic   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param tow
	     * @param: @throws ServiceException      
	     * @return: void      
	     * @throws
	 */
	public void deleteTypeOfWork(ZybTypeOfWork tow) throws ServiceException;
	/**
	 * 工种_改
	     * @Title: updateEconomic   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param tow
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: ZYB_EconomicClass      
	     * @throws
	 */
	public ZybTypeOfWork updateTypeOfWork(ZybTypeOfWork tow) throws ServiceException;
	/**
	 * 工种_查
	     * @Title: queryByAllTypeOfWork   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List      
	     * @throws
	 */
	public PageReturnDTO queryByAllTypeOfWork(String typeofwork_name,String typeofwork_code,int pagesize, int pageno) throws ServiceException;
	/**
	 * 工种_ID查
	     * @Title: queryByIdTypeOfWork   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param id
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: ZYB_TypeOfWork      
	     * @throws
	 */
	public ZybTypeOfWork queryByIdTypeOfWork(String id) throws ServiceException;
	
	
	
	
	/**
	 * 工种管理批量删除
	     * @Title: deletes   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param ids
	     * @param: @throws ServiceException      
	     * @return: void      
	     * @throws
	 */
	public void deletes(String ids)throws ServiceException;
	
	
	
	/**
	 * 工种管理编码查询
	     * @Title: getBycode   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param code
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: ZybTypeOfWork      
	     * @throws
	 */
	public ZybTypeOfWork getBycode(String code)throws ServiceException;
	

	
	

	
	//--------------------------------------------------体检类别管理----------------------------------------------
	
	
	
	/**
	 * 体检类别添加
	     * @Title: addPhyexaclass   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param p
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: ZybPhyexaclass      
	     * @throws
	 */
	public  ZybPhyexaclass  addPhyexaclass(ZybPhyexaclass p) throws  ServiceException;
	
	
	
	/**
	 * 体检类别删除
	     * @Title: deletePhyexaclass   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param p
	     * @param: @throws ServiceException      
	     * @return: void      
	     * @throws
	 */
	public void  deletePhyexaclass(ZybPhyexaclass p) throws ServiceException;
	
	
	/***
	 * 体检类别更新
	     * @Title: updatePhyexaclass   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param p
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: ZybPhyexaclass      
	     * @throws
	 */
	public ZybPhyexaclass updatePhyexaclass(ZybPhyexaclass p) throws ServiceException;
	
	
	/**
	 * 体检类别查询全部
	     * @Title: queryAllPhyexaclass   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<ZybEconomicTreeDTO>      
	     * @throws
	 */
	public List<ZybEconomicTreeDTO> queryAllPhyexaclass() throws ServiceException;
	
	
	
	/**
	 * 体检类别id查询
	     * @Title: queryByPhyexaclassid   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param id
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: ZybPhyexaclass      
	     * @throws
	 */
	public ZybPhyexaclass queryByPhyexaclassid(String id) throws ServiceException;
	
	
	/**
	 * 体检类别分页查询
	     * @Title: queryByPagePhyexaclass   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param phyexaclass_code
	     * @param: @param phyexaclass_name
	     * @param: @param pagesize
	     * @param: @param pageno
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: PageReturnDTO      
	     * @throws
	 */
	public PageReturnDTO queryByPagePhyexaclass(String phyexaclass_code,String phyexaclass_name,int pagesize,int pageno) throws ServiceException;
	
	/**
	 * 
	     * @Title: queryByAll   
	     * @Description: 获取从业工种列表  
	     * @param: @param typeofwork_name
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<ZybEconomicTreeDTO>      
	     * @throws
	 */
	public List<TreeDTO> queryByAllTypOfWork(String typeofwork_name) throws ServiceException;
	
	
	
	
	/**
	 * 体检类别编码查询
	     * @Title: getByphyexacode   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param code
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: ZybPhyexaclass      
	     * @throws
	 */
	public ZybPhyexaclass getByphyexacode(String code)throws ServiceException;
	/**
	 * 
	     * @Title: getCyhyList   
	     * @Description: 获取从业行业   
	     * @param: @param name
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<TreeDTO>      
	     * @throws
	 */
	//public List<TreeDTO> getCyhyList(String name)throws ServiceException;
	public List<TreeDTO> getCyhyList(String name, int industry_type)throws ServiceException;
	

	/**
	 * 
	     * @Title: findZybEconomicIndustryById   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param id
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: ZybEconomicIndustry      
	     * @throws
	 */
	public ZybEconomicIndustry findZybEconomicIndustryById(String id) throws ServiceException;
	public List<ZybEconomicTreeDTO> getIndustryAll()throws ServiceException;
}
	
