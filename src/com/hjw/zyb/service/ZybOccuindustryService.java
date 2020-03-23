package com.hjw.zyb.service;



import java.util.List;

import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.wst.DTO.UserDTO;
import com.hjw.zyb.DTO.ZybOccuindustryDTO;
import com.hjw.zyb.domain.ZybCheckcriterion;
import com.hjw.zyb.domain.ZybOccuindustry;
import com.hjw.zyb.domain.ZybOccutypeofwork;
import com.hjw.zyb.domain.ZyboccuhazardfactorsCheckcriterion;
import com.hjw.zyb.model.ZybOccuindustryModel;
import com.synjones.framework.exception.ServiceException;

/**
 * 
 * <菜单、功能、角色操作> <功能详细描述>
 * 
 * @author zt
 * @version [3.0.0, Nov 4, 2011]
 * 
 * @see [相关类/方法]
 * @since [一卡通网站查询系统]
 */
public interface ZybOccuindustryService {
	
	
	
	//---------------------------------从业行业--------------------------------------------------
	
	
	
	/**
	 * 从业行业添加
	     * @Title: addOccuindustry   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param e
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: ZybOccuindustry      
	     * @throws
	 */
	public ZybOccuindustry addOccuindustry(ZybOccuindustry e) throws ServiceException;
	
	
	/**
	 * 从业行业删除
	     * @Title: deleteOccuindustry   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param e
	     * @param: @throws ServiceException      
	     * @return: void      
	     * @throws
	 */
	public void deleteOccuindustry(ZybOccuindustry e) throws ServiceException;
	
	
	/**
	 * 从业行业更新
	     * @Title: updateOccuindustry   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param e
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: ZybOccuindustry      
	     * @throws
	 */
	public ZybOccuindustry updateOccuindustry(ZybOccuindustry e) throws ServiceException;
	
	
	/**
	 * 从业行业id查询
	     * @Title: queryByID   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param id
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: ZybOccuindustry      
	     * @throws
	 */
	public ZybOccuindustry queryByID(String id) throws ServiceException;
	
	
	/**
	 * id查询返回DTO
	     * @Title: queryByOccuindustryID   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param id
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: ZybOccuindustryDTO      
	     * @throws
	 */
	public ZybOccuindustryDTO queryByOccuindustryID(String id,String center_num) throws ServiceException;
	
	
	/**
	 * 从业行业分页查询
	     * @Title: queryByOccuIndustry   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param industry_code
	     * @param: @param industry_name
	     * @param: @param pagesize
	     * @param: @param pageno
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: PageReturnDTO      
	     * @throws
	 */
	public PageReturnDTO queryByOccuIndustry(String industry_code,String industry_name,int pagesize,int pageno,String center_num) throws ServiceException;
	
	
	/**
	 * 从业行业获取套餐名称
	     * @Title: getAllExam_set   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<ZybOccuindustryDTO>      
	     * @throws
	 */
	public List<ZybOccuindustryDTO> getAllExam_set(String center_num) throws ServiceException;
	
	
	/**
	 *从业工种编码验证
	     * @Title: getByOcuindustrycode   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param code
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: ZybOccuindustry      
	     * @throws
	 */
	public ZybOccuindustry getByOcuindustrycode(String code,UserDTO user)throws ServiceException;
	
	
	
	
	
	
	//----------------------------------------------从业工种-----------------------------------------------------
	
	
	
	
	
	/**
	 * 从业工种添加
	     * @Title: addOccugzh   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param z
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: ZybOccutypeofwork      
	     * @throws
	 */
	public ZybOccutypeofwork addOccugzh(ZybOccutypeofwork z) throws ServiceException;
	
	
	/**
	 * 从业工种删除
	     * @Title: deleteOccugzh   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param z
	     * @param: @throws ServiceException      
	     * @return: void      
	     * @throws
	 */
	public void deleteOccugzh(ZybOccutypeofwork z)throws ServiceException;
	
	
	/**
	 * 从业工种更新
	     * @Title: updateOccugzh   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param z
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: ZybOccutypeofwork      
	     * @throws
	 */
	public ZybOccutypeofwork updateOccugzh(ZybOccutypeofwork z) throws ServiceException;
	
	
	/**
	 * 
	 * 从业工种id查询
	     * @Title: queryByOccugzhid   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param id
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: ZybOccutypeofwork      
	     * @throws
	 */
	public ZybOccutypeofwork queryByOccugzhid(String id) throws ServiceException;
	
	
	/**
	 * 从业工种分页查询
	     * @Title: queryByOccugzh   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param typeofwork_code
	     * @param: @param typeofwork_name
	     * @param: @param pagesize
	     * @param: @param pageno
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: PageReturnDTO      
	     * @throws
	 */
	public PageReturnDTO queryByOccugzh(String typeofwork_code,String typeofwork_name,int pagesize,int pageno) throws ServiceException;
	
	
	/**
	 * 从业工种编码查询
	     * @Title: queryBycode   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param code
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: ZybOccutypeofwork      
	     * @throws
	 */
	public ZybOccutypeofwork queryBycode(String code) throws ServiceException ;
	
	
	
	
	
	
	//------------------------------------------------检查依据-------------------------------------------------------------
	
	
	
	
	
	/**
	 * 检查依据添加
	     * @Title: addCheckyj   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param c
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: ZybCheckcriterion      
	     * @throws
	 */
	public ZybCheckcriterion addCheckyj(ZybOccuindustryModel model) throws ServiceException;
	
	
	/**
	 * 检查依据删除
	     * @Title: deleteCheckyj   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param c
	     * @param: @throws ServiceException      
	     * @return: void      
	     * @throws
	 */
	public void deleteCheckyj(ZybCheckcriterion c) throws ServiceException;
	
	
	
	/**
	 * 检查依据批量删除
	     * @Title: deletejcyjs   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param ids
	     * @param: @throws ServiceException      
	     * @return: void      
	     * @throws
	 */
	public void deletejcyjs(String ids)throws ServiceException;
	
	/**
	 * 检查依据更新
	     * @Title: updateCheckyj   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param c
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: ZybCheckcriterion      
	     * @throws
	 */
	public ZybCheckcriterion updateCheckyj(ZybOccuindustryModel model) throws ServiceException;
	
	
	/**
	 * 检查依据id查询
	     * @Title: queryBycheckyjID   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param id
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: ZybCheckcriterion      
	     * @throws
	 */
	public ZybCheckcriterion queryBycheckyjID(String id) throws ServiceException;
	
	
	/**
	 * 检查依据分页查询
	     * @Title: queryBycheckyjPage   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param criterion_name
	     * @param: @param pagesize
	     * @param: @param pageno
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: PageReturnDTO      
	     * @throws
	 */
	public PageReturnDTO queryBycheckyjPage(ZybOccuindustryModel model,int pagesize,int pageno)throws ServiceException;
	/**
	 * ------------------------------检查依据和管理-----------
	 */
	public List<ZybOccuindustryDTO> getLbcriterionManagerComxobox(ZybOccuindustryModel model)throws ServiceException;
	/*
	 * 保存
	 */
	public void saveLbcriterionManager(ZybOccuindustryModel model)throws ServiceException;
	/**
	 * 修改
	     * @Title: saveLbcriterionManager   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param model
	     * @param: @throws ServiceException      
	     * @return: void      
	     * @throws
	 */
	public void updateLbcriterionManager(ZybOccuindustryModel model)throws ServiceException;
	/*
	 * 删除
	 */
	public void deleteLbcriterionManager(ZybOccuindustryModel model)throws ServiceException;
	/**
	 * 列表
	     * @Title: queryLbcriterionManager   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param model
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: PageReturnDTO      
	     * @throws
	 */
	public PageReturnDTO queryLbcriterionManager(ZybOccuindustryModel model,int pagesize, int pageno)throws ServiceException;
	public ZyboccuhazardfactorsCheckcriterion getLBZybOccuindustryDTO(ZybOccuindustryModel model)throws ServiceException;
}
	
