package com.hjw.wst.service;

import java.util.List;

import com.hjw.wst.DTO.DepartmentDepDTO;
import com.hjw.wst.DTO.DiseaseLogicDTO;
import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.wst.DTO.UserDTO;
import com.hjw.wst.domain.ChargingItem;
import com.hjw.wst.model.DiseaseLogicModel;
import com.synjones.framework.exception.ServiceException;

/**
 * 科室疾病
     * @Title:  火箭蛙体检管理系统   
     * @Package com.hjw.wst.service   
     * @Description:  
     * @author: zr     
     * @date:   2017年5月12日 上午10:57:29   
     * @version V2.0.0.0
 */
public interface DiseaseDepService {
	/**
	 * 获取所有科室
	     * @Title: getDepartmentDepDTO   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param model
	     * @param: @param user
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<DepartmentDepDTO>      
	     * @throws
	 */
	public  List<DepartmentDepDTO>  getDepartmentDepDTO(DiseaseLogicModel model,UserDTO user,String app_type) throws  ServiceException;
	/**
	 * 根据科室过滤疾病
	     * @Title: getDepartmentDepDTO   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param model
	     * @param: @param user
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<DepartmentDepDTO>      
	     * @throws
	 */
	public List<DiseaseLogicDTO> getDepDiseaseKnowloedge(DiseaseLogicModel model, String center_num ) throws ServiceException;
	/**
	 * 获取科室疾病列表
	     * @Title: queryDiseaseDep   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param model
	     * @param: @param user
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: PageReturnDTO      
	     * @throws
	 */
	public PageReturnDTO  queryDiseaseDep(DiseaseLogicModel model) throws  ServiceException;
	/**
	 * 
	     * @Title: updateItemList   
	     * @Description: TODO(修改项目细项)   
	     * @param: @param itemList
	     * @param: @throws ServiceException      
	     * @return: void      
	     * @throws
	 */
	public ChargingItem updateItemList(ChargingItem item,String itemList,long limitCount,UserDTO user)throws  ServiceException;
}
