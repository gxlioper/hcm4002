package com.hjw.zyb.service;

import java.sql.SQLException;
import org.hibernate.service.spi.ServiceException;

import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.zyb.domain.ZybCareerInquisitionItem;
import com.hjw.zyb.model.ZybCareerInquisitionItemModel;
/**
 * 职业病问诊项目管理
     * @Title:  火箭蛙体检管理系统   
     * @Package com.hjw.zyb.service   
     * @Description:  
     * @author: yangm     
     * @date:   2017年4月10日 上午10:43:14   
     * @version V2.0.0.0
 */
public interface ZybCareerInquisitionItemService {
	/**
	 * zyb问诊项目列表
	     * @Title: queryZybCareerInquisitionItemList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param model
	     * @param: @param page
	     * @param: @param pageSize
	     * @param: @return
	     * @param: @throws ServiceException
	     * @param: @throws SQLException      
	     * @return: PageReturnDTO      
	     * @throws
	 */
	public PageReturnDTO queryZybCareerInquisitionItemList(ZybCareerInquisitionItemModel model,int page,int pageSize) throws ServiceException,SQLException;
	/**
	 * 删除zyb问诊项目
	     * @Title: deletequeryZybCareerInquisitionItem   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param ids
	     * @param: @throws ServiceException
	     * @param: @throws SQLException      
	     * @return: void      
	     * @throws
	 */
	public void deletequeryZybCareerInquisitionItem(String ids) throws ServiceException,SQLException;
	/**
	 * 获取zyb问诊项目
	     * @Title: getZybCareerInquisitionItem   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param id
	     * @param: @return
	     * @param: @throws ServiceException
	     * @param: @throws SQLException      
	     * @return: List<ZybCareerInquisitionItem>      
	     * @throws
	 */
	public ZybCareerInquisitionItem getZybCareerInquisitionItem(String id) throws ServiceException,SQLException;
	/**
	 * 添加zyb问诊项目
	     * @Title: addZybCareerInquisitionItem   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param item
	     * @param: @throws ServiceException
	     * @param: @throws SQLException      
	     * @return: void      
	     * @throws
	 */
	public void addZybCareerInquisitionItem( ZybCareerInquisitionItem  item) throws ServiceException,SQLException;
	/**
	 * 修改zyb问诊项目
	     * @Title: updateZybCareerInquisitionItem   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @throws ServiceException
	     * @param: @throws SQLException      
	     * @return: void      
	     * @throws
	 */
	public void updateZybCareerInquisitionItem( ZybCareerInquisitionItem  item)  throws ServiceException,SQLException;
	/**
	 * zyb问诊项目验证编码唯一
	     * @Title: getZybCareerInquisitionItemCode   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param id
	     * @param: @return
	     * @param: @throws ServiceException
	     * @param: @throws SQLException      
	     * @return: int      
	     * @throws
	 */
	public int getZybCareerInquisitionItemCode(String id) throws ServiceException,SQLException;
	/**
	 * 获取顺序码最大值
	     * @Title: getZybCareerInquisitionItemCodeOrder   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws ServiceException
	     * @param: @throws SQLException      
	     * @return: int      
	     * @throws
	 */
	public int getZybCareerInquisitionItemCodeOrder()  throws ServiceException,SQLException;
}
