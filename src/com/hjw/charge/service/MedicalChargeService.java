package com.hjw.charge.service;
/**
 * 医保
     * @Title:  火箭蛙体检管理系统   
     * @Package com.hjw.wst.service   
     * @Description:  
     * @author: yangm     
     * @date:   2019年10月30日 上午10:59:06   
     * @version V2.0.0.0
 */

import java.util.List;

import com.hjw.charge.DTO.DicProvInsuranceClinicItemDTO;
import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.wst.DTO.UserDTO;
import com.hjw.charge.domain.MedicalPriceListCharge;
import com.hjw.charge.model.MedicalModel;
import com.synjones.framework.exception.ServiceException;

public interface MedicalChargeService {
	/**
	 * 价表列表
	     * @Title: queryMedical   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param model
	     * @param: @param user
	     * @param: @param rows
	     * @param: @param page
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: PageReturnDTO      
	     * @throws
	 */
	public PageReturnDTO queryMedical(MedicalModel model,UserDTO user,int rows, int page) throws ServiceException;
	/**
	 * 收费项目价表关系列表
	     * @Title: getChargingItemMedicalPrice   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param model
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List      
	     * @throws
	 */
	public List getChargingItemMedicalPrice(MedicalModel model) throws ServiceException;
	/**
	 * 保存项目和价表关系
	     * @Title: saveChargingItemMedicalPrice   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param model
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: String      
	     * @throws
	 */
	public String saveChargingItemMedicalPrice(MedicalModel model,UserDTO user) throws ServiceException;
	/**
	 * 通过id获取价表信息
	     * @Title: getMedicalPriceById   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param model
	     * @param: @param user
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: MedicalPriceList      
	     * @throws
	 */
	public MedicalPriceListCharge getMedicalPriceById(MedicalModel model, UserDTO user) throws ServiceException;
	/**
	 * 价表新增修改
	     * @Title: saveMedicalPrice   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param model
	     * @param: @param user
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: String      
	     * @throws
	 */
	public String saveMedicalPrice(MedicalModel model,UserDTO user) throws ServiceException;
	/**
	 * 删除价表
	     * @Title: deleteMedicalPrice   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param model
	     * @param: @param user
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: int      
	     * @throws
	 */
	public int deleteMedicalPrice(MedicalModel model,UserDTO user) throws ServiceException;
	/**
	 * 价表医保关系保存
	     * @Title: saveMedicalPriceMedicalItem   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param model
	     * @param: @param user
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: String      
	     * @throws
	 */
	public String saveMedicalPriceMedicalItem(MedicalModel model,UserDTO user)  throws ServiceException;
	/**
	 * 省医保分页列表
	     * @Title: queryDicProvInsuranceClinicItem   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param model
	     * @param: @param rows
	     * @param: @param page
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: PageReturnDTO      
	     * @throws
	 */
	public PageReturnDTO queryDicProvInsuranceClinicItem(MedicalModel model,int rows, int page)  throws ServiceException; 
	/**
	 * 根据价表id查询省医保
	     * @Title: getDicProvInsuranceClinicItemList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param model
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<DicProvInsuranceClinicItemDTO>      
	     * @throws
	 */
	public List<DicProvInsuranceClinicItemDTO> getDicProvInsuranceClinicItemList(MedicalModel model)  throws ServiceException; 
}
