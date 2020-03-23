package com.hjw.zyb.service;
import java.sql.SQLException;






import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.zyb.domain.ZybRecheckItem;
import com.hjw.zyb.domain.ZybrecheckExclDisease;
import com.hjw.zyb.model.ZybrecheckExclDiseaseModel;
import com.synjones.framework.exception.ServiceException;


/**
 * 复查项目/及要求/排除目标疾病
     * @Title:  火箭蛙体检管理系统   
     * @Package com.hjw.zyb.service   
     * @Description:  
     * @author: zr     
     * @date:   2017年4月19日 下午5:34:56   
     * @version V2.0.0.0
 */
public interface ZybcheckItemDiseaseService {
	/**
	 * @throws org.apache.avalon.framework.service.ServiceException 
	 * 获取复查项目及要求List
	     * @Title: queryCheckItem   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws SQLException
	     * @param: @throws ServerException      
	     * @return: PageReturnDTO      
	     * @throws
	 */
	public PageReturnDTO  queryCheckItem(ZybrecheckExclDiseaseModel  model,int page,int pageSize) throws  SQLException,ServiceException;
	/**
	 * 删除复查项目及要求
	     * @Title: deleteCheckItem   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param ids
	     * @param: @throws SQLException
	     * @param: @throws ServerException      
	     * @return: void      
	     * @throws
	 */
	public void deleteCheckItem(String ids) throws  SQLException,ServiceException;
	/**
	 * 获取复查项目及要求
	     * @Title: getCheckItem   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param model
	     * @param: @return
	     * @param: @throws SQLException
	     * @param: @throws ServerException      
	     * @return: ZybRecheckItem      
	     * @throws
	 */
	public ZybRecheckItem  getCheckItem(ZybrecheckExclDiseaseModel  model)   throws  SQLException,ServiceException;
	/**
	 * 新增复查项目及要求
	     * @Title: addcheckItem   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param item
	     * @param: @throws SQLException
	     * @param: @throws ServerException      
	     * @return: void      
	     * @throws
	 */
	public void addcheckItem(ZybRecheckItem  item)  throws  SQLException,ServiceException;
	/**
	 *修改复查项目及要求
	     * @Title: updatecheckItem   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param item
	     * @param: @throws SQLException
	     * @param: @throws ServerException      
	     * @return: void      
	     * @throws
	 */
	public void updatecheckItem(ZybRecheckItem  item)  throws  SQLException,ServiceException;
	
	/**-----------------------------------复查项目排除疾病-----------------------------------
	 * 获取复查项目排除病List
	 * @Title: queryCheckItem   
	 * @Description: TODO(这里用一句话描述这个方法的作用)   
	 * @param: @return
	 * @param: @throws SQLException
	 * @param: @throws ServerException      
	 * @return: PageReturnDTO      
	 * @throws
	 */
	public PageReturnDTO  queryRecheckExclDisease(ZybrecheckExclDiseaseModel  model,int page,int pageSize) throws  SQLException,ServiceException;
	/**
	 * 删除复查项目排除疾
	 * @Title: deleteCheckItem   
	 * @Description: TODO(这里用一句话描述这个方法的作用)   
	 * @param: @param ids
	 * @param: @throws SQLException
	 * @param: @throws ServerException      
	 * @return: void      
	 * @throws
	 */
	public void deleteRecheckExclDisease(String ids) throws  SQLException,ServiceException;
	/**
	 * 获取复查项目排除疾
	 * @Title: getCheckItem   
	 * @Description: TODO(这里用一句话描述这个方法的作用)   
	 * @param: @param model
	 * @param: @return
	 * @param: @throws SQLException
	 * @param: @throws ServerException      
	 * @return: ZybRecheckItem      
	 * @throws
	 */
	public ZybrecheckExclDisease  getRecheckExclDisease(ZybrecheckExclDiseaseModel  model)   throws  SQLException,ServiceException;
	/**
	 * 新增复查项目排除疾
	 * @Title: addcheckItem   
	 * @Description: TODO(这里用一句话描述这个方法的作用)   
	 * @param: @param item
	 * @param: @throws SQLException
	 * @param: @throws ServerException      
	 * @return: void      
	 * @throws
	 */
	public void addRecheckExclDisease(ZybrecheckExclDisease  item)  throws  SQLException,ServiceException;
	/**
	 *修改复查项目排除疾
	 * @Title: updatecheckItem   
	 * @Description: TODO(这里用一句话描述这个方法的作用)   
	 * @param: @param item
	 * @param: @throws SQLException
	 * @param: @throws ServerException      
	 * @return: void      
	 * @throws
	 */
	public void updateRecheckExclDisease(ZybrecheckExclDisease  item)  throws  SQLException,ServiceException;
	
}
