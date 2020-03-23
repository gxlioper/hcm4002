package com.hjw.wst.service;

import java.util.List;

import com.hjw.wst.DTO.ChargingItemDTO;
import com.hjw.wst.DTO.DiseaseKnowloedgeDTO;
import com.hjw.wst.DTO.DiseaseLogicDTO;
import com.hjw.wst.DTO.DiseaseLogicExamItemDTO;
import com.hjw.wst.DTO.ExaminationItemDTO;
import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.wst.DTO.UserDTO;
import com.hjw.wst.model.DiseaseLogicModel;
import com.synjones.framework.exception.ServiceException;

/**
 * 
     * @Title:  火箭蛙体检管理系统   
     * @Package com.hjw.wst.service   
     * @Description:  
     * @author: 疾病逻辑管理
     * @date:   2016年12月7日 上午9:50:05   
     * @version V2.0.0.0
 */
public interface DiseaseLogicService {
	/**
	 * 
	     * @Title: queryDiseaseLogic   
	     * @Description: TODO(疾病逻辑管理列表)   
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: PageReturnDTO      
	     * @throws
	 */
	public PageReturnDTO   queryDiseaseLogic(DiseaseLogicModel model,int page,int pagesize,UserDTO user)  throws ServiceException;
	
	public PageReturnDTO   queryDiseaseLogic_all(DiseaseLogicModel model,int page,int pagesize,UserDTO user)  throws ServiceException;
	/**
	 * 
	     * @Title: deletDiseaseLogic   
	     * @Description: TODO(疾病逻辑删除)   
	     * @param: @param dto
	     * @param: @throws ServiceException      
	     * @return: void      
	     * @throws
	 */
	public void deletDiseaseLogic(String ids) throws ServiceException;
	/**
	 * 
	     * @Title: getChargingItem   
	     * @Description: TODO(获取所有收费项目)   
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<ChargingItemDTO>      
	     * @throws
	 */
	public List<ChargingItemDTO> getChargingItem(DiseaseLogicModel model) throws ServiceException;
	/**
	 * 
	     * @Title: getExaminationItem   
	     * @Description: TODO(获取所有检查项目)   
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<ExaminationItemDTO>      
	     * @throws
	 */
	public List<ExaminationItemDTO> getExaminationItem(DiseaseLogicModel model) throws ServiceException;
	/**
	 * 
	     * @Title: getDiseaseKnowloedge   
	     * @Description: TODO(获取所有疾病)   
	     * @param: @param model
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<DiseaseKnowloedgeDTO>      
	     * @throws
	 */
	public List<DiseaseLogicDTO>  getDiseaseKnowloedge(DiseaseLogicModel model) throws ServiceException;
	/**
	 * 
	     * @Title: addDiseaseKnowloedge   
	     * @Description: TODO(新增疾病逻辑)   
	     * @param: @param model
	     * @param: @param dto
	     * @param: @throws ServiceException      
	     * @return: void      
	     * @throws
	 */
	public void addDiseaseKnowloedge(DiseaseLogicModel model,UserDTO dto) throws ServiceException;
	/**
	 * 
	     * @Title: getDiseaseLogic   
	     * @Description: TODO(获取疾病逻辑)   
	     * @param: @param id
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: DiseaseLogicDTO      
	     * @throws
	 */
	public DiseaseLogicDTO getDiseaseLogic(long id)  throws ServiceException;
	/**
	 * 
	     * @Title: getDiseaseKnowloedge   
	     * @Description: TODO(疾病逻辑获取检查项目)   
	     * @param: @param id
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<DiseaseLogicExamItemDTO>      
	     * @throws
	 */
	public List<DiseaseLogicExamItemDTO> getDiseaseKnowloedge(long id)  throws ServiceException;
	/**
	 * 
	     * @Title: updateDiseaseKnowloedge   
	     * @Description: TODO(修改疾病逻辑)   
	     * @param: @param model
	     * @param: @param dto
	     * @param: @throws ServiceException      
	     * @return: void      
	     * @throws
	 */
	public void   updateDiseaseKnowloedge(DiseaseLogicModel model,UserDTO dto)  throws ServiceException;
	
	public void update_diseaseLogic_off(String ids) throws ServiceException;
	
	public void update_diseaseLogic_on(String ids) throws ServiceException;
}
