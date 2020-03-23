package com.hjw.charge.service;

import com.hjw.charge.DTO.DictCityClinicItemPeisDTO;
import com.hjw.charge.DTO.DictInsuranceClinicItemDTO;
import com.hjw.charge.DTO.DictProvClinicItemPeisDTO;
import com.hjw.charge.DTO.DictProvInsuranceClinicItemDTO;
import com.hjw.wst.DTO.PageReturnDTO;

import java.util.List;

import com.hjw.charge.model.ItemBindInsuranceModel;
import com.synjones.framework.exception.ServiceException;

public interface ItemBindInsuranceChargeService {
	
	/**
	 * 
	     * @Title: getChargingItemListForProv   
	     * @Description: 获取收费项目列表（省医保）   
	     * @param: @param model
	     * @param: @param pagesize
	     * @param: @param page
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: PageReturnDTO      
	     * @throws
	 */
	public PageReturnDTO getChargingItemListForProv(ItemBindInsuranceModel model, int pagesize, int page) throws ServiceException;
	
	/**
	 * 
	     * @Title: getChargingItemListForCity   
	     * @Description: 获取收费项目列表（市医保）   
	     * @param: @param model
	     * @param: @param pagesize
	     * @param: @param page
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: PageReturnDTO      
	     * @throws
	 */
	public PageReturnDTO getChargingItemListForCity(ItemBindInsuranceModel model, int pagesize, int page) throws ServiceException;
	
	/**
	 * 
	     * @Title: getCityInsuranceItemList   
	     * @Description: 获取市医保诊疗项目列表   
	     * @param: @param dto
	     * @param: @param page
	     * @param: @param pageSize
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: PageReturnDTO      
	     * @throws
	 */
	public PageReturnDTO getCityInsuranceItemList(DictInsuranceClinicItemDTO dto,int page, int pageSize)throws ServiceException;
	
	/**
	 * 
	     * @Title: getClinicedItemCityList   
	     * @Description: 根据收费项目获取已关联的市医保项目   
	     * @param: @param clinic_item_code
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: DictCityClinicItemPeisDTO      
	     * @throws
	 */
	public List<DictCityClinicItemPeisDTO> getCityItemList(ItemBindInsuranceModel model)throws ServiceException;
	
	/**
	 * 
	     * @Title: saveDictCityClinicItemPeis   
	     * @Description: 保存市医保关联项目
	     * @param: @param item_code
	     * @param: @param list
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<DictCityClinicItemPeis>      
	     * @throws
	 */
	public List<DictCityClinicItemPeisDTO> saveDictCityClinicItemPeis(String item_code,List<DictCityClinicItemPeisDTO> list)throws ServiceException;
	
	/**
	 * 
	     * @Title: getProvInsuranceItemList   
	     * @Description: 获取省医保诊疗项目列表   
	     * @param: @param dto
	     * @param: @param page
	     * @param: @param pageSize
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: PageReturnDTO      
	     * @throws
	 */
	public PageReturnDTO getProvInsuranceItemList(DictProvInsuranceClinicItemDTO dto,int page, int pageSize)throws ServiceException;
	
	/**
	 * 
	     * @Title: getProvItemList   
	     * @Description: 根据收费项目获取已关联的省医保项目   
	     * @param: @param clinic_item_code
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: DictCityClinicItemPeisDTO      
	     * @throws
	 */
	public List<DictProvClinicItemPeisDTO> getProvItemList(String peis_item_code)throws ServiceException;
	
	/**
	 * 
	     * @Title: saveDictProvClinicItemPeis   
	     * @Description: 保存省医保关联项目
	     * @param: @param item_code
	     * @param: @param list
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<DictCityClinicItemPeis>      
	     * @throws
	 */
	public List<DictProvClinicItemPeisDTO> saveDictProvClinicItemPeis(String item_code,List<DictProvClinicItemPeisDTO> list)throws ServiceException;
	
}
