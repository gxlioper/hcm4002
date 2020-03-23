package  com.hjw.wst.service;

import com.hjw.wst.domain.CrmSmsBaseTemplate;
import com.hjw.wst.domain.CrmSmsSend;
import com.hjw.wst.model.CrmSmsBaseTemplateModel;

import java.util.List;

import com.hjw.wst.DTO.CrmSmsBaseTemplateDTO;
import com.hjw.wst.DTO.ExamInfoDTO;
import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.wst.DTO.UserDTO;
import com.synjones.framework.exception.ServiceException;

/**
 * 短信模板
     * @Title:  火箭蛙体检管理系统   
     * @Package src.com.hjw.wst.service   
     * @Description:  
     * @author: yangm     
     * @date:   2018年1月15日 上午10:10:37   
     * @version V2.0.0.0
 */
public interface CrmSmsBaseTemplateService {
	/**
	 * 短信模板列表
	     * @Title: queryCrmSmsBaseTemplate   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: PageReturnDTO      
	     * @throws
	 */
	public PageReturnDTO queryCrmSmsBaseTemplate(CrmSmsBaseTemplateModel model,int page, int pageSize) throws ServiceException;
	
	public List<CrmSmsBaseTemplateDTO> queryCrmSmsBaseTemplateAll() throws ServiceException;
	/*
	 * 模板删除
	 */
	public void deleteCrmSmsBaseTemplate(CrmSmsBaseTemplateModel model) throws ServiceException;
	/**
	 * 模板保存
	     * @Title: svaeCrmSmsBaseTemplate   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @throws ServiceException      
	     * @return: void      
	     * @throws
	 */
	public void svaeCrmSmsBaseTemplate(CrmSmsBaseTemplateModel model,UserDTO user) throws ServiceException;
	/**
	 * 模板修改
	     * @Title: updateCrmSmsBaseTemplate   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @throws ServiceException      
	     * @return: void      
	     * @throws
	 */
	public void updateCrmSmsBaseTemplate(CrmSmsBaseTemplateModel model,UserDTO user) throws ServiceException;
	
	public void updateCrmSmsBaseTemplate(CrmSmsBaseTemplate cmbt) throws ServiceException;
	
	/**
	 * 
	     * @Title: saveCrmSmsSend   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param crmSmsSend
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: CrmSmsSend      
	     * @throws
	 */
	public CrmSmsSend saveCrmSmsSend(CrmSmsSend crmSmsSend)throws ServiceException;
	
	/**
	 * 
	     * @Title: getCrmSmsBaseTemplate   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param temidl
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: CrmSmsBaseTemplate      
	     * @throws
	 */
	public CrmSmsBaseTemplate getCrmSmsBaseTemplate(String temidl) throws ServiceException;
	/*
	 * 
	 * 
	 * 
	 * 
	 * =========================================短信发送=======================================================
	 * 
	 * 
	 * 
	 * 
	 */
	public PageReturnDTO queryCrmSmsSend(CrmSmsBaseTemplateModel model, String center_num,int page, int pageSize) throws ServiceException;
	/**
	 * 获取人员信息
	     * @Title: queryUser   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param model
	     * @param: @param page
	     * @param: @param pageSize
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: ExamInfoDTO      
	     * @throws
	 */
	public PageReturnDTO queryUser(CrmSmsBaseTemplateModel model,int page, int pageSize) throws ServiceException;
	public String saveCrmSmsSend(CrmSmsBaseTemplateModel model,UserDTO user) throws ServiceException;
	/**
	 * @return 
	 * 重新发送
	     * @Title: saveCrmSmsSendCHONGFA   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param model
	     * @param: @param user
	     * @param: @throws ServiceException      
	     * @return: void      
	     * @throws
	 */
	public String saveCrmSmsSendCHONGFA(CrmSmsBaseTemplateModel model,UserDTO user) throws ServiceException;
}
