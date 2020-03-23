package com.hjw.wst.service;

import java.util.List;

import com.hjw.wst.DTO.DepExamResultDTO;
import com.hjw.wst.DTO.DiseaseLogicDTO;
import com.hjw.wst.DTO.ExamInfoDTO;
import com.hjw.wst.DTO.ExaminationItemDTO;
import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.wst.DTO.UserDTO;
import com.hjw.wst.model.DiseaseLogicModel;
import com.synjones.framework.exception.ServiceException;
/**
 * 新疾病逻辑
     * @Title:  火箭蛙体检管理系统   
     * @Package com.hjw.wst.service   
     * @Description:  
     * @author: dangqi     
     * @date:   2019年3月1日 上午10:35:27   
     * @version V2.0.0.0
 */
public interface NewDiseaseLogicService {

	/**
	 * 查询单项阳性逻辑列表
	     * @Title: getDiseaseLogicSingleList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<DiseaseLogicDTO>      
	     * @throws
	 */
	public PageReturnDTO getDiseaseLogicSingleList(String logic_name,String item_num, long depid,long logic_class, String isActive, int rows, int page, String center_num,String itemNum) throws ServiceException;
	
	/**
	 * 保存单项阳性逻辑
	     * @Title: saveDiseaseLogicSingle   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param model
	     * @param: @param user
	     * @param: @throws ServiceException      
	     * @return: void      
	     * @throws
	 */
	public void saveDiseaseLogicSingle(DiseaseLogicModel model, UserDTO user) throws ServiceException;
	
	/**
	 * 根据id查询单项阳性逻辑数据
	     * @Title: getDiseaseLogicSingleById   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param id
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: DiseaseLogicDTO      
	     * @throws
	 */
	public DiseaseLogicDTO getDiseaseLogicSingleById(String id, long logic_class, String center_num ) throws ServiceException;
	
	/**
	 * 单项阳性逻辑启用或停用
	     * @Title: diseaseLogicSingleStartOrEnd   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param ids
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: String      
	     * @throws
	 */
	public String diseaseLogicSingleStartOrEnd(String ids,String isActive,UserDTO user) throws ServiceException;
	
	/**
	 * 单项阳性逻辑删除
	     * @Title: delDiseaseLogicSingle   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param ids
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: String      
	     * @throws
	 */
	public String delDiseaseLogicSingle(String ids) throws ServiceException;
	
	/**
	 * 查询复合疾病逻辑列表
	     * @Title: getDiseaseLogicCompositeList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param logic_name
	     * @param: @param depid
	     * @param: @param isActive
	     * @param: @param rows
	     * @param: @param page
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: PageReturnDTO      
	     * @throws
	 */
	public PageReturnDTO getDiseaseLogicCompositeList(String logic_name, long depid, String isActive, int rows, int page) throws ServiceException;
	
	/**
	 * 保存复合疾病逻辑信息
	     * @Title: saveDiseaseLogicComposite   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param model
	     * @param: @param user
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: String      
	     * @throws
	 */
	public String saveDiseaseLogicComposite(DiseaseLogicModel model, UserDTO user) throws ServiceException;
	
	/**
	 *  查询单个复合疾病逻辑信息
	     * @Title: getDiseaseLogicCompositeById   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param id
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: DiseaseLogicDTO      
	     * @throws
	 */
	public DiseaseLogicDTO getDiseaseLogicCompositeById(String id) throws ServiceException;
	
	/**
	 * 复合疾病逻辑启用或停用
	     * @Title: diseaseLogicSingleStartOrEnd   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param ids
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: String      
	     * @throws
	 */
	public String diseaseLogicCompositeStartOrEnd(String ids,String isActive,UserDTO user) throws ServiceException;
	
	/**
	 * 复合疾病逻辑删除
	     * @Title: delDiseaseLogicSingle   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param ids
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: String      
	     * @throws
	 */
	public String delDiseaseLogicComposite(String ids) throws ServiceException;
	
	/**
	 * 获取科室类型为普通科室或影像科室的科室列表
	     * @Title: getDiseaseLogicSingleDepList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param dep_name
	     * @param: @param dep_id
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<DepartmentDepDTO>      
	     * @throws
	 */
	public List<ExaminationItemDTO> getDiseaseLogicSingleDepList(String dep_name,String dep_id, String center_num ) throws ServiceException;
	
	/**
	 * 通过单项阳性逻辑自动生成单项阳性和危机阳性
	     * @Title: createDepLogicSingleDisease   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param exam_num
	     * @param: @param item_code
	     * @param: @param item_num
	     * @param: @param exam_result
	     * @param: @return      
	     * @return: String      
	     * @throws
	 */
	public String createDepLogicSingleDisease(ExamInfoDTO examinfo,DepExamResultDTO examResult,long userid);
	
	/**
	 * 通过单项阳性科室小结逻辑自动生成单项阳性和危机阳性
	     * @Title: createDepLogicSingleDisease   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param exam_num
	     * @param: @param item_code
	     * @param: @param item_num
	     * @param: @param exam_result
	     * @param: @return      
	     * @return: String      
	     * @throws
	 */
	public String createDepLogicSingleDiseaseDep(ExamInfoDTO examinfo,DepExamResultDTO examResult,long userid);
}
