package com.hjw.wst.service;

import java.util.List;

import com.hjw.wst.DTO.DepLogicDTO;
import com.hjw.wst.DTO.DepLogicExamItemDTO;
import com.hjw.wst.DTO.DepartmentDepDTO;
import com.hjw.wst.DTO.ExaminationItemDTO;
import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.wst.DTO.UserDTO;
import com.hjw.wst.domain.DepLogic;
import com.hjw.wst.model.DepInspectModel;
import com.hjw.wst.model.DepLogicModel;
import com.hjw.wst.model.DiseaseLogicModel;
import com.synjones.framework.exception.ServiceException;

/**
 * 
     * @Title:  火箭蛙体检管理系统   
     * @Package com.hjw.wst.service   
     * @Description:  科室逻辑
     * @author: zr     
     * @date:   2016年12月13日 上午9:32:33   
     * @version V2.0.0.0
 */
public interface DepLogicService {
	/**
	 * 
	     * @Title: getDepLogic   
	     * @Description: TODO(获取科室逻辑列表)   
	     * @param: @param model
	     * @param: @param user
	     * @param: @param pageno
	     * @param: @param pagesize
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: PageReturnDTO      
	     * @throws
	 */
	public PageReturnDTO getDepLogic(DepLogicModel model,UserDTO user, int pageno, int pagesize)throws ServiceException;
	/**
	 * 
	     * @Title: deleteDepLogic   
	     * @Description: TODO(删除科室疾病逻辑)   
	     * @param: @param model
	     * @param: @throws ServiceException      
	     * @return: void      
	     * @throws
	 */
	public void deleteDepLogic(DepLogicModel model) throws ServiceException;
	/**
	 * 
	     * @Title: getExaminationItem   
	     * @Description: TODO(获取所有检查项目)   
	     * @param: @param model
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<ExaminationItemDTO>      
	     * @throws
	 */
	public List<DepLogicExamItemDTO> getDepLogicExamItem(DepLogicModel model) throws ServiceException;
	/**
	 * 
	     * @Title: addDepLogic   
	     * @Description: TODO(保存科室逻辑)   
	     * @param: @param model
	     * @param: @throws ServiceException      
	     * @return: void      
	     * @throws
	 */
	public void addDepLogic(DepLogicModel model,UserDTO  user) throws ServiceException;
	/**
	 * 
	     * @Title: getDepLogicExamItemShow   
	     * @Description: TODO(科室逻辑修改页面获取检查细项)   
	     * @param: @throws ServiceException      
	     * @return: void      
	     * @throws
	 */
	public List<DepLogicExamItemDTO> getDepLogicExamItemShow(DepLogicModel model)throws ServiceException;
	/**
	 * 
	     * @Title: getDepLogicShou   
	     * @Description: TODO(科室逻辑修改页面获取科室逻辑)   
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: DepLogicDTO      
	     * @throws
	 */
	public DepLogic  getDepLogicShou(DepLogicModel model) throws ServiceException;
	/**
	 * 
	     * @Title: updateDepLogic   
	     * @Description: TODO(修改科室逻辑)   
	     * @param: @param model
	     * @param: @throws ServiceException      
	     * @return: void      
	     * @throws
	 */
	public void updateDepLogic(DepLogicModel model,UserDTO  user) throws ServiceException;
	/**
	 * 
	     * @Title: getDepartmentDep   
	     * @Description: TODO(获取所有科室)   
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<DepartmentDepDTO>      
	     * @throws
	 */
	public List<DepartmentDepDTO>  getDepartmentDep( String center_num) throws ServiceException;
}
