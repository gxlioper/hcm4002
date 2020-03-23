package com.hjw.zyb.service;

import java.sql.SQLException;
import java.util.List;

import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.zyb.DTO.ZyboccudiseaseDTO;
import com.hjw.zyb.DTO.ZyboccudiseaseclassDTO;
import com.hjw.zyb.DTO.ZyboccuhazardfactorsOccudiseaseDTO;
import com.hjw.zyb.domain.Zyboccudisease;
import com.hjw.zyb.model.ZyboccudiseaseModel;
import com.synjones.framework.exception.ServiceException;

/**
 * 职业病/分类管理
     * @Title:  火箭蛙体检管理系统   
     * @Package com.hjw.zyb.service   
     * @Description:  
     * @author: yangm     
     * @date:   2017年4月11日 下午4:01:43   
     * @version V2.0.0.0
 */
public interface ZyboccudiseaseService {
	/**
	 * 获取职业病列表
	     * @Title: queryZyboccudiseaseList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param mode
	     * @param: @param page
	     * @param: @param pageSize
	     * @param: @return
	     * @param: @throws SQLException
	     * @param: @throws ServiceException      
	     * @return: PageReturnDTO      
	     * @throws
	 */
	public PageReturnDTO   queryZyboccudiseaseList(ZyboccudiseaseModel mode,int page,int pageSize) throws   SQLException,ServiceException;
	/**
	 * 删除职业病
	     * @Title: deleteZyboccudisease   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param ids
	     * @param: @throws SQLException
	     * @param: @throws ServiceException      
	     * @return: void      
	     * @throws
	 */
	public void  deleteZyboccudisease(String ids) throws   SQLException,ServiceException;
	/**
	 * 获取职业病
	     * @Title: getZyboccudisease   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param mode
	     * @param: @return
	     * @param: @throws SQLException
	     * @param: @throws ServiceException      
	     * @return: ZyboccudiseaseDTO      
	     * @throws
	 */
	public Zyboccudisease   getZyboccudisease(ZyboccudiseaseModel model) throws   SQLException,ServiceException;
	/**
	 * 新增职业病
	     * @Title: addZyboccudiseaseList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param zy
	     * @param: @throws SQLException
	     * @param: @throws ServiceException      
	     * @return: void      
	     * @throws
	 */
	public void   addZyboccudiseaseList(Zyboccudisease zy) throws   SQLException,ServiceException;
	/**
	 * 修改职业病
	     * @Title: updateZyboccudisease   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param zy
	     * @param: @throws SQLException
	     * @param: @throws ServiceException      
	     * @return: void      
	     * @throws
	 */
	public void  updateZyboccudisease(Zyboccudisease zy) throws   SQLException,ServiceException;
	/**
	 * 获取职业病分类列表
	     * @Title: queryZyboccudiseaseList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param mode
	     * @param: @param page
	     * @param: @param pageSize
	     * @param: @return
	     * @param: @throws SQLException
	     * @param: @throws ServiceException      
	     * @return: PageReturnDTO      
	     * @throws
	 */
	public List<ZyboccudiseaseclassDTO>   queryZyboccudiseaseListclass(ZyboccudiseaseModel mode) throws   SQLException,ServiceException;
	/***
	 * 
	 * 
	 *、、、、、、、、、、、、、、、、、、、、、、、、、、因素和职业病关系维护、、、、、、、、、、、、、、、、、、、、、、、、
	 * 
	 * 
	 * 
	 */
	/**
	 * 因素和职业病获取列表
	     * @Title: getZyboccudiseaseYinsuTable   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param mode
	     * @param: @return
	     * @param: @throws SQLException
	     * @param: @throws ServiceException      
	     * @return: PageReturnDTO      
	     * @throws
	 */
	public PageReturnDTO getZyboccudiseaseYinsuTable(ZyboccudiseaseModel mode,int page,int pageSize) throws   SQLException,ServiceException;
	/**
	 *因素职业病关系保存
	     * @Title: saveZyboccudiseaseYinsu   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param mode
	     * @param: @throws SQLException
	     * @param: @throws ServiceException      
	     * @return: void      
	     * @throws
	 */
	public void saveZyboccudiseaseYinsu(ZyboccudiseaseModel mode) throws   SQLException,ServiceException;
	/**
	 * 因素职业病关系修改
	     * @Title: updateZyboccudiseaseYinsuList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param mode
	     * @param: @throws SQLException
	     * @param: @throws ServiceException      
	     * @return: void      
	     * @throws
	 */
	public void updateZyboccudiseaseYinsuList(ZyboccudiseaseModel mode) throws   SQLException,ServiceException;
	/**
	 * 删除关系因素职业病
	     * @Title: deleteZyboccudiseaseYinsuList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param mode
	     * @param: @throws SQLException
	     * @param: @throws ServiceException      
	     * @return: void      
	     * @throws
	 */
	public void deleteZyboccudiseaseYinsu(String ids) throws   SQLException,ServiceException;
	/**
	 * 疾病列表
	     * @Title: getZyboccudiseaseYinsuList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param mode
	     * @param: @return
	     * @param: @throws SQLException
	     * @param: @throws ServiceException      
	     * @return: List<ZyboccudiseaseDTO>      
	     * @throws
	 */
	public List<ZyboccudiseaseDTO> getZyboccudiseaseYinsuList(ZyboccudiseaseModel mode) throws   SQLException,ServiceException;
	/**
	 * 通过id获取因素职业病关系
	     * @Title: getZyboccudiseaseYinsuList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param mode
	     * @param: @return
	     * @param: @throws SQLException
	     * @param: @throws ServiceException      
	     * @return: List<ZyboccudiseaseDTO>      
	     * @throws
	 */
	public ZyboccuhazardfactorsOccudiseaseDTO getFindIDZyboccudiseaseYinsuList(ZyboccudiseaseModel mode) throws   SQLException,ServiceException;
}
