package com.hjw.crm.service;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import com.hjw.crm.DTO.TemporaryCustomerInfoDTO;
import com.hjw.crm.domain.TemporaryCustomerInfo;
import com.hjw.crm.model.TemporaryCustomerInfoModel;
import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.wst.domain.CustomerInfo;
import com.hjw.wst.domain.CustomerMemberInfo;
import com.synjones.framework.exception.ServiceException;


public interface TemporaryCustomerInfoService {
	public PageReturnDTO getTemporaryCustomerInfoList(TemporaryCustomerInfoModel model,int page,int rows) throws ServiceException,SQLException;
	public String deleteTemporaryCustomerInfo(String ids)throws ServiceException;
	public TemporaryCustomerInfo getTemporaryCustomerInfo(String id) throws ServiceException;
	public void updateTemporaryCustomerInfo(TemporaryCustomerInfo temporaryCustomerInfo)throws ServiceException;
	public void saveTemporaryCustomerInfo(TemporaryCustomerInfo temporaryCustomerInfo)throws ServiceException;
	public void saveTemporaryCustomerInfoForList(List<TemporaryCustomerInfo> list)throws ServiceException;
	public String getDataId(String data_name)throws ServiceException;
	public String saveCustomerInfo(CustomerInfo customerInfo)throws ServiceException;
	public String updateCustomerInfo(CustomerInfo customerInfo)throws ServiceException;
	public String saveCustomerMemberInfo(CustomerMemberInfo customerMemberInfo)throws ServiceException;
	public CustomerInfo getCustomerInfoTemporary(String id_num)throws ServiceException;
	public List<TemporaryCustomerInfo> getTemporaryCustomerInfoList()throws ServiceException;
	public List<TemporaryCustomerInfo> getTemporaryCustomerInfoByIdBum(String id_num)throws ServiceException;
	public List<CustomerInfo> getCustomerInfoByIdBum(String id_num)throws ServiceException;
	public List<CustomerMemberInfo> getCustomerMemberInfoByIdBum(String arch_num)throws ServiceException;
	public List<TemporaryCustomerInfoDTO> getNation()throws  ServiceException;
	public List<TemporaryCustomerInfoDTO> getLevelName()throws ServiceException;
	public String getNationByName(String name)throws ServiceException;
}
