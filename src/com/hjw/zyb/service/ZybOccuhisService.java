package com.hjw.zyb.service;

import java.sql.SQLException;
import java.util.List;

import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.zyb.domain.ZybDiseaseHistory;
import com.hjw.zyb.domain.ZybOccuHis;
import com.hjw.zyb.model.ZybDiseaseHistoryModel;
import com.hjw.zyb.model.ZybImpCustomerInfoModel;
import com.synjones.framework.exception.ServiceException;

/**
 * 职业史维护
     * @Title:  火箭蛙体检管理系统   
     * @Package com.hjw.zyb.service   
     * @Description:  
     * @author: yangm     
     * @date:   2017年7月19日 下午5:15:15   
     * @version V2.0.0.0
 */
public interface ZybOccuhisService {
	/**
	 * 登记台导入职业史
	     * @Title: saveOccuhis   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: int      
	     * @throws
	 */
	public String  saveOccuhis(List<ZybOccuHis> li,long userid)   throws ServiceException;
	//----------------------------------------既往史--------------------------------------
	/**
	 * 登记台导入既往史
	     * @Title: saveDiseaseHistory   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: String      
	     * @throws
	 */
	public String  saveDiseaseHistory(List<ZybDiseaseHistory> li , long userid)  throws ServiceException;
	/**
	 * 既往史列表
	     * @Title: getDiseaseHistoryTable   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param model
	     * @param: @param page
	     * @param: @param pageSize
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: PageReturnDTO      
	     * @throws
	 */
	public PageReturnDTO  getDiseaseHistoryTable(ZybImpCustomerInfoModel model,int page,int pageSize)  throws ServiceException;
	/**
	 * 保存既往史
	     * @Title: saveDiseaseHistoryTable   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param di
	     * @param: @throws ServiceException      
	     * @return: void      
	     * @throws
	 */
	public void saveDiseaseHistoryadd(ZybDiseaseHistory di)  throws ServiceException;
	/**
	 * 删除既往史
	     * @Title: saveDiseaseHistoryTable   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param di
	     * @param: @throws ServiceException      
	     * @return: void      
	     * @throws
	 */
	public void deleteDiseaseHistory(String  ids)  throws ServiceException;
	
}
