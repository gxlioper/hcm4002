package com.hjw.wst.service;

import java.util.List;

import com.hjw.wst.DTO.BSRCorrectionValueDTO;
import com.hjw.wst.DTO.DepExamResultDTO;
import com.hjw.wst.DTO.DepInspectExamIntionDTO;
import com.hjw.wst.DTO.DepLogicDTO;
import com.hjw.wst.DTO.ExamDepResultDTO;
import com.hjw.wst.DTO.ExamInfoUserDTO;
import com.hjw.wst.DTO.ExpertSuggestionLibDTO;
import com.hjw.wst.DTO.ItemResultLibDTO;
import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.wst.DTO.UserDTO;
import com.hjw.wst.DTO.UserInfoDTO;
import com.hjw.wst.domain.*;
import com.hjw.wst.model.DepInspectModel;
import com.synjones.framework.exception.ServiceException;
/**
 * 
     * @Title:  火箭蛙体检管理系统   
     * @Package com.hjw.wst.service   
     * @Description:  科室检查
     * @author: zr     
     * @date:   2016年11月22日 下午8:40:30   
     * @version V2.0.0.0
 */


public interface DepInspectService {
	
	/**
	 * 
	     * @Title: departmentList   
	     * @Description: TODO(获取体检人员列表)   
	     * @param: @param pageno
	     * @param: @param pagesize
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: PageReturnDTO      
	     * @throws
	 */
	public PageReturnDTO getDepExamInfoUserLis(DepInspectModel model,UserDTO user, int pageno, int pagesize,String sort,String order)throws ServiceException;
	
	/**
	 * 
	     * @Title: queryAll   
	     * @Description: 查询全部(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @throws
	 */
	public List<ExamInfoUserDTO> queryAllDepartmentDep() throws ServiceException;

	
	/**
	 * 查询人员体检结果
	     * @Title: getCustomerInfo   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param examNum
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: CustomerInfoDTO      
	     * @throws
	 */
	public List<ExamDepResultDTO> getCustomerInfo(long id,UserDTO user) throws ServiceException;

	/**
	 * 查询人员体检结果
	     * @Title: getCustomerInfo   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param examNum
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: CustomerInfoDTO      
	     * @throws
	 */
	public List<ExamDepResultDTO> getHistoryResult(String exam_num,UserDTO user) throws ServiceException;

	/**
	 * 
	     * @Title: getExamInfoUserDTO   
	     * @Description: TODO(根据体检编号获取会员信息)   
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: ExamInfoUserDTO      
	     * @throws
	 */
	public ExamInfoUserDTO  getExamInfoUserDTO(String exam_num, UserDTO user) throws ServiceException;
	/**
	 * 危急值查询
	     * @Title: queryweijizhi   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param id
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<ExamDepResultDTO>      
	     * @throws
	 */
	public List<DepExamResultDTO> queryweijizhi(String exam_num,String app_type) throws ServiceException;
	/**
	 * 异常查询
	     * @Title: queryYichang   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param id
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<ExamDepResultDTO>      
	     * @throws
	 */
	public List<DepExamResultDTO> queryYichang(String exam_num,String app_type, String center_num) throws ServiceException;
	/**
	 * 查询全部（全科会诊）
	     * @Title: queryAll   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param id
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<ExamDepResultDTO>      
	     * @throws
	 */
	public List<DepExamResultDTO> queryAll(String exam_num,String app_type, String center_num) throws ServiceException;


	/**
	 * 
	     * @Title: getItemResultLibcyc   
	     * @Description: TODO(获取常用词)   
	     * @param: @param id
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<ItemResultLibDTO>      
	     * @throws
	 */
	public List<ItemResultLibDTO> getItemResultLibcyc(String item_code,long dep_id,String exam_status) throws ServiceException;
	/**
	 * 
	     * @Title: getItemeditLibS   
	     * @Description: TODO(获取所有常用词)   
	     * @param: @param name
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<ItemResultLibDTO>      
	     * @throws
	 */
	public List<ItemResultLibDTO>  getItemeditLibS(String name, String item_code,long dep_id,String IS_DEPINSPECT_WORD) throws ServiceException;
	/**
	 * 
	     * @Title: getItemintionReference   
	     * @Description: TODO(结果与参考值比较)   
	     * @param: @param id
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: int      
	     * @throws
	 */
	public  int   getItemintionReference(DepInspectModel dto) throws ServiceException;
	/**
	 * 
	     * @Title: addDepInspect   
	     * @Description: TODO(科室检查结论保存)   
	     * @param: @param dto
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: int      
	     * @throws
	 */
	public  void   addDepInspect(ExamdepResult  dto) throws ServiceException;
	/**
	 * 
	     * @Title: CommonExamDetail   
	     * @Description: TODO(科室检查结论细项保存)   
	     * @param: @param dto
	     * @param: @throws ServiceException      
	     * @return: void      
	     * @throws
	 */
	public  void  addCommonExamDetail(CommonExamDetail  de) throws ServiceException;
	
	/**
	 * 
	     * @Title: deleteCommonExamDetail   
	     * @Description: TODO(科室检查结论细项删除)   
	     * @param: @param commonExamDetail
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: CommonExamDetail      
	     * @throws
	 */
	public CommonExamDetail deleteCommonExamDetail(CommonExamDetail commonExamDetail) throws ServiceException;
	/**
	 * 
	     * @Title: findExamDepResultDTO   
	     * @Description: TODO(查询普通科室检查结论)   
	     * @param: @param exam_info_id
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: ExamDepResultDTO      
	     * @throws
	 */
	public  List<ExamdepResult>  findExamDepResultDTO(String exam_num,Long dep_id,String app_type)  throws ServiceException;
	/**
	 * 
	     * @Title: updateExamDepResult   
	     * @Description: TODO(科室检查修改科室检查结论)   
	     * @param: @param dto
	     * @param: @throws ServiceException      
	     * @return: void      
	     * @throws
	 */
	public  void  updateExamDepResult(ExamdepResult  dto) throws ServiceException;
	
	/**
	 * 
	     * @Title: updateExamInfo   
	     * @Description: TODO(更新体检人体检中状态   J)   
	     * @param: @param examinfo_id
	     * @param: @throws ServiceException      
	     * @return: void      
	     * @throws
	 */
	public void updateExamInfo(long examinfo_id) throws ServiceException;
	/**
	 * 
	     * @Title: findCommonExamDetail   
	     * @Description: TODO(科室检查细项查询)   
	     * @param: @param co
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<CommonExamDetail>      
	     * @throws
	 */
	public  List<CommonExamDetail>  findCommonExamDetail(String exam_num,Long exam_info) throws ServiceException;
	/**
	 * 
	     * @Title: updateCommonExamDetail   
	     * @Description: TODO(修改科室检查细项)   
	     * @param: @param co
	     * @param: @throws ServiceException      
	     * @return: void      
	     * @throws
	 */
	public  void updateCommonExamDetail(CommonExamDetail co)  throws ServiceException;
	/**
	 * 
	     * @Title: getDepInspectExamIntion   
	     * @Description: TODO(获取检查科室所有项目)   
	     * @param: @param id
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<DepInspectExamIntionDTO>      
	     * @throws
	 */
	public List<DepInspectExamIntionDTO> getDepInspectExamIntion(long id,long dep_id,String app_type,String exam_num, String center_num) throws ServiceException;
	public List<DepInspectExamIntionDTO> getDepInspectExamIntion_BSR(long id,long dep_id,String exam_num, String center_num) throws ServiceException;
	/**
	 * 
	     * @Title: deleteResult   
	     * @Description: TODO(清除结果)   
	     * @param: @throws ServiceException      
	     * @return: void      
	     * @throws
	 */
	public void deleteResult(DepInspectModel model,UserDTO user) throws ServiceException;
	/**
	 * 
	     * @Title: updateExaminfoChargingItem   
	     * @Description: TODO(科室结论保存，修改检查状态)   
	     * @param: @param id
	     * @param: @param user
	     * @param: @throws ServiceException      
	     * @return: void      
	     * @throws
	 */
	public void updateExaminfoChargingItem(String exam_num,String ids,UserDTO user)  throws ServiceException;
	/**
	 * 
	     * @Title: getExamdepresultResult   
	     * @Description: TODO(获取结论和专家建议)   
	     * @param: @param id
	     * @param: @param user
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: ExamdepResult      
	     * @throws
	 */
	public ExamdepResult  getExamdepresultResult(long id,UserDTO user,String app_type)  throws ServiceException;
	/**
	 * 
	     * @Title: getExamdepresulStatus   
	     * @Description: TODO(获取状态，和操作人，限制操作)   
	     * @param: @param id
	     * @param: @param user
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: ExamdepResult      
	     * @throws
	 */
	public ExaminfoChargingItem  getExamdepresulStatus(String exam_num,String ids, String center_num) throws ServiceException;
	/**
	 * 
	     * @Title: getStatuss   
	     * @Description: TODO(获取体检状态)   
	     * @param: @param id
	     * @param: @param user
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: ExamInfo      
	     * @throws
	 */
	public 	ExamInfo	getStatuss(String exam_num,UserDTO user) throws ServiceException;
	
	/**
	 * 根据检查结果 匹配科室逻辑
	     * @Title: getMateDepLogic   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param depResultList
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<DepExamResultDTO>      
	     * @throws
	 */
	public List<DepExamResultDTO> getMateDepLogic(List<DepExamResultDTO> depResultList,long dep_id,String sex) throws ServiceException;
	
	/**
	 * 获取科室逻辑信息列表
	     * @Title: getDepLpgic   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param dep_id
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<DepLogicDTO>      
	     * @throws
	 */
	public List<DepLogicDTO> getDepLpgic(long dep_id,String sex) throws ServiceException;
	
	/**
	 * 查询检查结果
	     * @Title: getDepResult   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param id
	     * @param: @param dep_id
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<DepExamResultDTO>      
	     * @throws
	 */
	public List<DepExamResultDTO> getDepResult(long id,long dep_id) throws ServiceException;
	
	/**
	 * 查询科室检查医生
	     * @Title: getDepuserBydepId   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param depId
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<UserDTO>      
	     * @throws
	 */
	public List<UserInfoDTO> getDepuserBydepId(UserDTO user ,String type,String apptype) throws ServiceException;
	
	/**
	 * 获取历史检查项目检查结果
	     * @Title: getOldCommonDetailResult   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param id
	     * @param: @param item_id
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: DepInspectExamIntionDTO      
	     * @throws
	 */
	public DepInspectExamIntionDTO getOldCommonDetailResult(long id,long item_id) throws ServiceException;
	
	/**
	 * 保存普通科室检查结果
	     * @Title: saveDepResult   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param model
	     * @param: @param user
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: String      
	     * @throws
	 */
	public String saveDepResult(DepInspectModel model,UserDTO user,String is_depitem_save) throws ServiceException;
	
	/**
	 * 获取VIP体检者提示信息 
	     * @Title: getCustomerVipPrompt   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param userid
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<CustomerVipPrompt>      
	     * @throws
	 */
	public List<CustomerVipPrompt> updateCustomerVipPrompt(long userid) throws ServiceException;
	
	/**
	 * 查询专科建议列表
	     * @Title: getDepSuggestionList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param sugg_word
	     * @param: @param dep_id
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<ExpertSuggestionLibDTO>      
	     * @throws
	 */
	public List<ExpertSuggestionLibDTO> getDepSuggestionList(String sugg_word,long dep_id) throws ServiceException;
	/**
	 * 查下检查项目
	     * @Title: getExamationItem   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param exam_item_id
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: ExaminationItem      
	     * @throws
	 */
	public ExaminationItem getExamationItem(String item_num)throws ServiceException;
	
	/**
	 * 查看判断科室是否存在项目
	     * @Title: checkDepExaminfoItem   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param exam_num
	     * @param: @return      
	     * @return: String      
	     * @throws
	 */
	public String checkDepExaminfoItem(String exam_num,long dep_id, String center_num)throws ServiceException;
	
	/**
	 * 科室检查结果实现jdbc方式插入数据
	     * @Title: saveDepResultJdbc   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param model
	     * @param: @param user
	     * @param: @param is_depitem_save
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: String      
	     * @throws
	 */
	public String saveDepResultJdbc(DepInspectModel model, UserDTO user,String is_depitem_save) throws ServiceException;
	
	/**
	 * 查询体检者本科室项目的体检类型 
	     * @Title: getExamItemAppType   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param exam_num
	     * @param: @param dep_id
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: String      
	     * @throws
	 */
	public String getExamItemAppType(String exam_num,long dep_id, String center_num) throws ServiceException;
	
	
	/**
	 * 
	     * @Title: queryIsTiJianType   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: ExamInfoUserDTO      
	     * @throws
	 */
	public ExamInfoUserDTO queryIsTiJianType(ExamInfoUserDTO euo) throws ServiceException;

	/**
	 * 
	     * @Title: queryCountTypeUser   
	     * @Description: TODO(查询检查的人数)   
	     * @param: @param model
	     * @param: @param user
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: String      
	     * @throws
	 */
	public DepInspectExamIntionDTO queryCountTypeUser(DepInspectModel model, UserDTO user) throws ServiceException;
	
	/**
	 * 调用自动生成危机值存储过程
	     * @Title: createExamCritical   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param exam_num
	     * @param: @param charging_item_code
	     * @param: @param item_num
	     * @param: @throws ServiceException      
	     * @return: void      
	     * @throws
	 */
	public void createExamCritical(String exam_num,String charging_item_code, String item_num) throws ServiceException;
	
	public BSRCorrectionValueDTO getBSRCorrectionValueDTO(long age, String sex) throws ServiceException;

	public DepartmentDep getDepBydepId(long id) throws ServiceException;
}
	
