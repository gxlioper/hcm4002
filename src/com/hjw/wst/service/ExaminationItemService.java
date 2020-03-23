package com.hjw.wst.service;
import java.util.List;

import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.wst.DTO.ThridLisItemDTO;
import com.hjw.wst.domain.ExaminationItem;
import com.hjw.wst.model.ExaminationItemModel;
import com.synjones.framework.exception.ServiceException;

/**
 * 
     * @Title:  火箭蛙体检管理系统   
     * @Package com.hjw.wst.service   
     * @Description:  检查项目管理数字型
     * @author: 张瑞     
     * @date:   2016年9月12日 上午11:13:56   
     * @version V2.0.0.0
 */
public interface ExaminationItemService {
	/**
	 * 
	     * @Title: queryExaminationItem   
	     * @Description: TODO(检查项目管理列表&&检索)   
	     * @param: @param exa
	     * @param: @param page
	     * @param: @param pagesize
	     * @param: @return      
	     * @return: PageReturnDTO      
	     * @throws
	 */
	public PageReturnDTO queryExaminationItem(ExaminationItem exa,int page,int pagesize,String rt,long charging_item_id,String startStop, String center_num) throws ServiceException;
	/**
	 * 
	     * @Title: deleteSampleDemo   
	     * @Description: TODO(删除检查项目)   
	     * @param: @param id
	     * @param: @throws ServiceException      
	     * @return: void      
	     * @throws
	 */
	public void deleteExaminationItem(String id) throws ServiceException;
	/**
	 * 
	     * @Title: findClass   
	     * @Description: TODO(查找实体类)   
	     * @param: @param id
	     * @param: @return      
	     * @return: ExaminationItem      
	     * @throws
	 */
	public ExaminationItem findClass(long i, String center_num) throws ServiceException;
	/**
	 * 
	     * @Title: updateExaminationItem   
	     * @Description: TODO(修改检查项目管理)   
	     * @param: @param exa
	     * @param: @throws ServiceException      
	     * @return: void      
	     * @throws
	 */
	public void updateExaminationItem(ExaminationItem exa, String center_num) throws ServiceException;
	/**
	 * @return 
	 * 
	     * @Title: addExaminationItem   
	     * @Description: TODO(新增检查项目)   
	     * @param: @param exa
	     * @param: @throws ServiceException      
	     * @return: void      
	     * @throws
	 */
	public ExaminationItem addExaminationItem(ExaminationItem exa, String center_num) throws ServiceException;
	/**
	 * 
	     * @Title: getexam_num   
	     * @Description: TODO(验证检验编码唯一)   
	     * @param: @param exam_num
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: ExaminationItem      
	     * @throws
	 */
	public ExaminationItem getexam_num(String exam_num) throws ServiceException;
    public ExaminationItem getExaminationItemByItem_num(String item_num) throws ServiceException;
	/**
	 * 
	     * @Title: getview_num   
	     * @Description: TODO(关联影项编码验证)   
	     * @param: @param view_num
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: ExaminationItem      
	     * @throws
	 */
	public ExaminationItem getviewnum(String view_num) throws ServiceException;
	/**
	 * 
	     * @Title: queryDefaultid   
	     * @Description: TODO(项目结果知识验证是否有默认值)   
	     * @param: @param defaultid
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: ExaminationItem      
	     * @throws
	 */
	public int queryDefaultid(Long defaultid)  throws ServiceException;
	/**
	 * Lis检验明细列表
	     * @Title: getThridLisItemList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param model
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<ThridLisItemDTO>      
	     * @throws
	 */
	public List<ThridLisItemDTO> getThridLisItemList(ExaminationItemModel model) throws ServiceException;
	/**
	 * Lis检验列表带分页
	     * @Title: getThridLisItemTable   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param model
	     * @param: @param page
	     * @param: @param pagesize
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: PageReturnDTO      
	     * @throws
	 */
	public PageReturnDTO getThridLisItemTable(ExaminationItemModel model,int page,int pagesize) throws ServiceException;
	/**
	 * 
	     * @Title: queryExaminationItemByConfig   
	     * @Description: TODO(数据库带配置的项目列表)   
	     * @param: @param exa
	     * @param: @param page
	     * @param: @param pagesize
	     * @param: @param rt
	     * @param: @param charging_item_id
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: PageReturnDTO      
	     * @throws
	 */
	public PageReturnDTO queryExaminationItemByConfig(ExaminationItem exa,int page,int pagesize,String rt,long charging_item_id, String center_num) throws ServiceException;
	/**
	 * 
	     * @Title: queryExamItemByOwnDept   
	     * @Description: TODO(新增页面  细项表)   
	     * @param: @param exa
	     * @param: @param page
	     * @param: @param pagesize
	     * @param: @param rt
	     * @param: @param charging_item_id
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: PageReturnDTO      
	     * @throws
	 */
	public PageReturnDTO queryExamItemByOwnDept(ExaminationItem exa,int page,int pagesize,String rt,long charging_item_id, String center_num) throws ServiceException;
	
	
	/**
	 * 根据id获取code
	     * @Title: getExamCodeById   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param id
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: String      
	     * @throws
	 */
	public String getExamCodeById(long id) throws ServiceException;
	
//	public boolean saveOrUpdateExaminationItemVSLis(ExaminationItemVSLisDTO evsl)  throws ServiceException;
}
