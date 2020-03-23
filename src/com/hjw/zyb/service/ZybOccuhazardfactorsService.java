package com.hjw.zyb.service;

import java.util.List;

import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.zyb.DTO.ZybOccuhazardfactorsDTO;
import com.hjw.zyb.domain.ZybOccuhazardfactors;
import com.hjw.zyb.model.ZybOccuhazardfactorsModel;
import com.synjones.framework.exception.ServiceException;

/**
 * 职业因素管理
     * @Title:  火箭蛙体检管理系统   
     * @Package com.hjw.zyb.service   
     * @Description:  
     * @author: zr     
     * @date:   2017年4月1日 上午11:32:23   
     * @version V2.0.0.0
 */
public interface ZybOccuhazardfactorsService {
	/**
	 * 获取职业危害类别
	     * @Title: getOccuhazardfactorsList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws ServerException
	     * @param: @throws SQLServerException      
	     * @return: List<ZYB_OccuhazardfactorsDTO>      
	     * @throws
	 */
	public List<ZybOccuhazardfactorsDTO> getOccuhazardfactorsList(ZybOccuhazardfactorsModel  model) throws  ServiceException;
	/**
	 * 职业危害因素列表
	     * @Title: getOccuHazardFactorsList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List      
	     * @throws
	 */
	public PageReturnDTO  getOccuHazardFactorsList(ZybOccuhazardfactorsModel model,int page,int pageSize) throws  ServiceException;
	/**
	 * 删除职业危害因素
	     * @Title: deledtOccuHazardFactors   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param model
	     * @param: @throws ServiceException      
	     * @return: void      
	     * @throws
	 */
	public void deledtOccuHazardFactors (String ids) throws  ServiceException;
	/**
	 * 获取职业危害因素
	     * @Title: deledtOccuHazardFactors   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param model
	     * @param: @throws ServiceException      
	     * @return: void      
	     * @throws
	 */
	public ZybOccuhazardfactors getOccuHazardFactors (String id) throws  ServiceException;
	/**
	 * 职业因素验证编码唯一
	     * @Title: getOccuHazardFactors   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param id
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: ZYB_Occuhazardfactors      
	     * @throws
	 */
	public int getOccuHazardFactorsCode (ZybOccuhazardfactorsModel model) throws  ServiceException;
	/**
	 * 保存职业危害因素
	     * @Title: addOccuHazardFactorsCode   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param model
	     * @param: @throws ServiceException      
	     * @return: void      
	     * @throws
	 */
	public void addOccuHazardFactors(ZybOccuhazardfactors to) throws  ServiceException;
	/**
	 * 修改职业危害因素
	     * @Title: updateOccuHazardFactors   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param model
	     * @param: @throws ServiceException      
	     * @return: void      
	     * @throws
	 */
	public void updateOccuHazardFactors(ZybOccuhazardfactorsModel model) throws  ServiceException;
	/**
	 * 职业危害因素获取显示顺序最大值
	     * @Title: getOrderMax   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: int      
	     * @throws
	 */
	public int getOrderMax() throws  ServiceException;
}
