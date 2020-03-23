package com.hjw.wst.service;




import java.util.List;

import com.hjw.wst.DTO.CriticalDTO;
import com.hjw.wst.DTO.DiseaseKnowloedgeDTO;
import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.wst.domain.DiseaseDep;
import com.hjw.wst.domain.DiseaseKnowloedge;
import com.hjw.wst.domain.DiseaseSuggestion;
import com.synjones.framework.exception.ServiceException;

/**
 * 
 * <菜单、功能、角色操作> <功能详细描述>
 * 
 * @author 张婷
 * @version [3.0.0, Nov 4, 2011]
 * @see [相关类/方法]
 * @since [一卡通网站查询系统]
 */
public interface DiseaseKnowloedgeService {
	/**
	 * 添加
	     * @Title: addDklodeg   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param dklg
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: DiseaseKnowloedge      
	     * @throws
	 */
	public DiseaseKnowloedge addDklodeg(DiseaseKnowloedge dklg) throws ServiceException;
	/**
	 * 建议表新增
	     * @Title: addSug   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param ds
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: DiseaseSuggestion      
	     * @throws
	 */
	public DiseaseSuggestion addSug(DiseaseSuggestion ds) throws ServiceException;
	/**
	 * 删除(建议表)
	     * @Title: deleteDSugge   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param dsuge
	     * @param: @throws ServiceException      
	     * @return: void      
	     * @throws
	 */
	public void deleteDSugge(DiseaseSuggestion dsuge) throws ServiceException;
	/**
		 * 批量删除
		     * @Title: deleteDklodeg   
		     * @Description: TODO(这里用一句话描述这个方法的作用)   
		     * @param: @param dklg
		     * @param: @throws ServiceException      
		     * @return: void      
		     * @throws
		 */
	public void deleteDklodeg(String id) throws ServiceException;;

	/**
	 * 更新
	     * @Title: updateDklodeg   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param dklg
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: DiseaseKnowloedge      
	     * @throws
	 */
	public DiseaseKnowloedge updateDklodeg(DiseaseKnowloedge dklg) throws ServiceException;

	/**
	 * 建议表更新
	     * @Title: updateSug   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param ds
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: DiseaseSuggestion      
	     * @throws
	 */
	public DiseaseSuggestion updateSug(DiseaseSuggestion ds) throws ServiceException;
	
	public List<DiseaseKnowloedgeDTO> getSuggtionByDiseaseid(long diseaseid,String center_num,String sug_center) throws ServiceException;
	/**
	 * 建议表列表
	     * @Title: queryallSuggestion   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param pageno
	     * @param: @param pagesize
	     * @param: @return      
	     * @return: PageReturnDTO      
	     * @throws
	 */
	public PageReturnDTO queryallSuggestion(long diseaseid,int pageno, int pagesize,String center_num,String sug_center);
	
	/**
	 * id查询
	     * @Title: loadDklodeg   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param id
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: DiseaseKnowloedge      
	     * @throws
	 */
	
	public DiseaseKnowloedge loadDklodeg(long id) throws ServiceException;

	/**
	 * 建议表id查询
	     * @Title: loadSuggestion   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param id
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: DiseaseSuggestion      
	     * @throws
	 */
	public DiseaseSuggestion loadSuggestion(long id) throws ServiceException;
	
	/**
	 * 根据疾病ID查询建议信息
	     * @Title: getSuggestionList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param disesId
	     * @param: @return      
	     * @return: List<DiseaseSuggestion>      
	     * @throws
	 */
	public List<DiseaseSuggestion> getSuggestionList(long disesId,String center_num,String sug_center);
	
	/**
	 * 编码查询
	     * @Title: queryByNum   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param disease_num
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: DiseaseKnowloedge      
	     * @throws
	 */
	
	public  DiseaseKnowloedge queryByNum(String disease_num) throws ServiceException;
	
	/**
	 * 查询科室名称
	     * @Title: getdepname   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<DiseaseKnowloedgeDTO>      
	     * @throws
	 */
	public List<DiseaseKnowloedgeDTO> getdepname( String center_num )throws ServiceException;
	
	
	/**
	 * 查询收费项目
	     * @Title: getitemname   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<DiseaseKnowloedgeDTO>      
	     * @throws
	 */
	public List<DiseaseKnowloedgeDTO> getitemname()throws ServiceException;
	
	/**
	 * 收费项目id查询
	     * @Title: getByitem_id   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param id
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<DiseaseKnowloedgeDTO>      
	     * @throws
	 */
	public List<DiseaseKnowloedgeDTO> getByitem_id(long id)throws ServiceException;
	
	/**
	 * 查询检查项目
	     * @Title: getitemname   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<DiseaseKnowloedgeDTO>      
	     * @throws
	 */
	public List<DiseaseKnowloedgeDTO> getcheckitemname(long id)throws ServiceException;
	
	
	/**
	 * 疾病科室关系id查询
	     * @Title: getByDiseaseDepId   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param id
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: DiseaseDep      
	     * @throws
	 */
	public DiseaseKnowloedgeDTO  getByDiseaseDepId(long id)throws ServiceException;
	
	/**
	 * 根据收费项目查询检查项目
	     * @Title: getcheckitemid   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param id
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<DiseaseKnowloedgeDTO>      
	     * @throws
	 */
	public List<DiseaseKnowloedgeDTO> getBycharginItemid(String id,long dep_id)throws ServiceException;
	
	
	/**
	 * 疾病科室项目关系维护插入
	     * @Title: insertDiseaseDep   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param dd
	     * @param: @throws ServiceException      
	     * @return: void      
	     * @throws
	 */
	public void insertDiseaseDep(DiseaseDep dd)throws ServiceException;

	
	/**
	 * 疾病科室项目关系维护更新
	     * @Title: updateDiseaseDep   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param dd
	     * @param: @throws ServiceException      
	     * @return: void      
	     * @throws
	 */
	public void updateDiseaseDep(DiseaseDep dd)throws ServiceException;
	
	public DiseaseDep getDiseaseDepByDisId(long dis_id) throws ServiceException;
	/**
	 * 
	     * @Title: queryPageDklodegDep   
	     * @Description: 按照疾病知识库表部门现实树 
	     * @param: @param dep_id
	     * @param: @param disease_type
	     * @param: @param disease_name
	     * @param: @param disease_level
	     * @param: @param pageno
	     * @param: @param pagesize
	     * @param: @return      
	     * @return: PageReturnDTO      
	     * @throws
	 */
	public PageReturnDTO queryPageDklodegDep(long dep_id,String disease_type,String disease_name,String disease_level,String isActive,int pageno, int pagesize, String center_num );
	/**
	 * 
	     * @Title: loadDklodeg   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param id
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: DiseaseKnowloedge      
	     * @throws
	 */
	
	public DiseaseKnowloedgeDTO loadDkloaddept(long id) throws ServiceException;
	
	/**
	 * 查询危急值大类名称
	     * @Title: getcriticalD   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<DiseaseKnowloedgeDTO>      
	     * @throws
	 */
	public List<CriticalDTO> getcriticalD()throws ServiceException;
	
	/**
	 * 查询危急值子类名称
	     * @Title: getcritical   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<DiseaseKnowloedgeDTO>      
	     * @throws
	 */
	public List<CriticalDTO> getcritical(long id)throws ServiceException;
	
	/**
	 * 查询危急值类别等级
	     * @Title: getcriticalLevel   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<DiseaseKnowloedgeDTO>      
	     * @throws
	 */
	public List<CriticalDTO> getcriticalLevel()throws ServiceException;
	
	/**
	 * 查询疾病
	     * @Title: getcriticalLevel   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<DiseaseKnowloedgeDTO>      
	     * @throws
	 */
	public List<CriticalDTO> getDisease()throws ServiceException;
	/**
	 * 根据编码查询
	     * @Title: getDiseaseKnowloedgeByDisaseNum   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param disaseNum
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: DiseaseKnowloedge      
	     * @throws
	 */
	public DiseaseKnowloedge getDiseaseKnowloedgeByDisaseNum(String disaseNum) throws ServiceException;
	/**
	 * @return 
	 * 查询疾病
	     * @Title: saveCrmSmsSendCHONGFA   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param model
	     * @param: @param user
	     * @param: @throws ServiceException      
	     * @return: void      
	     * @throws
	 */
	public List<DiseaseKnowloedgeDTO>  queryDiseaseList(String disease_name) throws ServiceException; 
}
	
