package com.hjw.zyb.service;

import java.sql.SQLException;
import java.util.List;

import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.zyb.DTO.ZyboccucontraindicationDTO;
import com.hjw.zyb.domain.Zyboccucontraindication;
import com.hjw.zyb.domain.ZyboccuhazardfactorsOccucontraindication;
import com.hjw.zyb.model.ZyboccucontraindicationModel;
import com.synjones.framework.exception.ServiceException;

/**
 * 职业禁忌症
     * @Title:  火箭蛙体检管理系统   
     * @Package com.hjw.zyb.service   
     * @Description:  
     * @author: yangm     
     * @date:   2017年4月12日 下午3:50:12   
     * @version V2.0.0.0
 */
public interface ZyboccucontraindicationService {
	/**
	 * 职业禁忌症列表
	     * @Title: queryOccucontraindication   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param model
	     * @param: @return
	     * @param: @throws SQLException
	     * @param: @throws ServiceException      
	     * @return: PageReturnDTO      
	     * @throws
	 */
	public PageReturnDTO  queryOccucontraindication(ZyboccucontraindicationModel model,int page,int pageSize) throws  SQLException,ServiceException;
	/**
	 * 删除职业禁忌症
	     * @Title: deleteOccucontraindication   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param ids
	     * @param: @return
	     * @param: @throws SQLException
	     * @param: @throws ServiceException      
	     * @return: PageReturnDTO      
	     * @throws
	 */
	public void  deleteOccucontraindication(String ids) throws  SQLException,ServiceException;
	/**
	 * 新增职业禁忌症
	     * @Title: addOccucontraindication   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param occ
	     * @param: @return
	     * @param: @throws SQLException
	     * @param: @throws ServiceException      
	     * @return: PageReturnDTO      
	     * @throws
	 */
	public void  addOccucontraindication(Zyboccucontraindication  occ,ZyboccucontraindicationModel model) throws  SQLException,ServiceException;
	/**
	 * 修改职业禁忌症
	     * @Title: queryOccucontraindication   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param model
	     * @param: @return
	     * @param: @throws SQLException
	     * @param: @throws ServiceException      
	     * @return: PageReturnDTO      
	     * @throws
	 */
	public void  updateOccucontraindication(Zyboccucontraindication  occ) throws  SQLException,ServiceException;
	/**
	 * 获取职业禁忌症
	     * @Title: queryOccucontraindication   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param model
	     * @param: @return
	     * @param: @throws SQLException
	     * @param: @throws ServiceException      
	     * @return: PageReturnDTO      
	     * @throws
	 */
	public Zyboccucontraindication  getOccucontraindication(ZyboccucontraindicationModel model) throws  SQLException,ServiceException;
	/**
	 * ------------------------------因素体检类别禁忌症关系---------------------------------------
	 */
	public PageReturnDTO getLbOccucontraindicationList(ZyboccucontraindicationModel model,int page, int pageSize) throws  SQLException,ServiceException;
	/**
	 * 保存关系
	     * @Title: getLbAddOccucontraindicationList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param occ
	     * @param: @param model
	     * @param: @return
	     * @param: @throws SQLException
	     * @param: @throws ServiceException      
	     * @return: PageReturnDTO      
	     * @throws
	 */
	public void addLbOccucontraindication(ZyboccucontraindicationModel model) throws  SQLException,ServiceException;
	/**
	 * 获取检查依据列表
	     * @Title: getLbcriterionManagerList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param model
	     * @param: @throws SQLException
	     * @param: @throws ServiceException      
	     * @return: void      
	     * @throws
	 */
	public List<ZyboccucontraindicationDTO> getLbcriterionManagerList(ZyboccucontraindicationModel model) throws  SQLException,ServiceException;
	/**
	 * 修改因素和禁忌症关系
	     * @Title: updateLbOccucontraindication   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param model
	     * @param: @throws SQLException
	     * @param: @throws ServiceException      
	     * @return: void      
	     * @throws
	 */
	public void updateLbOccucontraindication(ZyboccucontraindicationModel model) throws  SQLException,ServiceException;
	/**
	 * 获取因素和禁忌症关系
	     * @Title: updateLbOccucontraindication   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param model
	     * @param: @throws SQLException
	     * @param: @throws ServiceException      
	     * @return: void      
	     * @throws
	 */
	public ZyboccuhazardfactorsOccucontraindication getLbZyboccuhazardfactorsOccucontraindication(ZyboccucontraindicationModel model) throws  SQLException,ServiceException;
	/**
	 * 因素禁忌症关系删除
	     * @Title: getLbZyboccuhazardfactorsOccucontraindication   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param model
	     * @param: @return
	     * @param: @throws SQLException
	     * @param: @throws ServiceException      
	     * @return: ZyboccuhazardfactorsOccucontraindication      
	     * @throws
	 */
	public void deleteOccuhazardfactorsOccucontraindication(ZyboccucontraindicationModel model) throws  SQLException,ServiceException;
	
}
