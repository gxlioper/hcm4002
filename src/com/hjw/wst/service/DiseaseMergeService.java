package com.hjw.wst.service;

import java.sql.SQLException;
import java.util.List;

import com.hjw.wst.DTO.DiseaseKnowloedgeDTO;
import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.wst.DTO.UserDTO;
import com.hjw.wst.domain.DiseaseKnowloedge;
import com.hjw.wst.domain.DiseaseMerge;
import com.hjw.wst.model.DiseaseMergeModel;
import com.synjones.framework.exception.ServiceException;

/**
 * 疾病合并
     * @Title:  火箭蛙体检管理系统   
     * @Package com.hjw.wst.service   
     * @Description:  
     * @author: yangm     
     * @date:   2017年5月5日 下午2:31:38   
     * @version V2.0.0.0
 */
public interface DiseaseMergeService {
	/**
	 * 合并疾病列表
	     * @Title: queryDiseaseMerge   
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
	public PageReturnDTO   queryDiseaseMerge(DiseaseMergeModel model,int page,int pageSize) throws ServiceException,SQLException;
	/**
	 * 删除合并的疾病
	     * @Title: deleteDiseaseMerge   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param ids
	     * @param: @throws ServiceException
	     * @param: @throws SQLException      
	     * @return: void      
	     * @throws
	 */
	public void   deleteDiseaseMerge(String ids) throws ServiceException,SQLException;
	/**
	 * 获取合并疾病
	     * @Title: getDiseaseMerge   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param id
	     * @param: @return
	     * @param: @throws ServiceException
	     * @param: @throws SQLException      
	     * @return: DiseaseMerge      
	     * @throws
	 */
	public DiseaseMerge   getDiseaseMerge(long id) throws ServiceException,SQLException;
	/**
	 * 新增合并疾病
	     * @Title: queryDiseaseMerge   
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
	public void   addDiseaseMerge(DiseaseMerge me) throws ServiceException,SQLException;
	/**
	 * 修改合并疾病
	     * @Title: queryDiseaseMerge   
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
	public void  updateDiseaseMerge(DiseaseMergeModel model,UserDTO user) throws ServiceException,SQLException;
	/**
	 * 获取所有疾病
	     * @Title: getDiseaseKnowloedge   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws ServiceException
	     * @param: @throws SQLException      
	     * @return: DiseaseKnowloedge      
	     * @throws
	 */
	public List<DiseaseKnowloedge>   getDiseaseKnowloedge(String name) throws ServiceException,SQLException;
	/**
	 * 合并疾病之获取所有疾病
	     * @Title: getqueryPageDklodeg   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param model
	     * @param: @return
	     * @param: @throws ServiceException
	     * @param: @throws SQLException      
	     * @return: List      
	     * @throws
	 */
	public  List<DiseaseKnowloedgeDTO> getqueryPageDklodeg(DiseaseMergeModel model) throws ServiceException,SQLException;
	/**
	 * 获取疾病下拉框
	     * @Title: getPageDklodegcombobox   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws ServiceException
	     * @param: @throws SQLException      
	     * @return: List<DiseaseKnowloedgeDTO>      
	     * @throws
	 */
	public List<DiseaseKnowloedgeDTO>  getPageDklodegcombobox(String name)  throws ServiceException,SQLException;
}
