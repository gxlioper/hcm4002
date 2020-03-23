package com.hjw.crm.service;

import java.sql.SQLException;
import java.util.List;

import com.hjw.crm.DTO.CrmSalesCostTrDTO;
import com.hjw.crm.domain.CrmSalesCost;
import com.hjw.crm.model.CrmSalesCostModel;
import com.hjw.wst.DTO.PageReturnDTO;
import com.synjones.framework.exception.ServiceException;

public interface CrmSalesCostService {
		public PageReturnDTO getCrmSalesCostList(CrmSalesCostModel model,int page,int pageSize) throws ServiceException,SQLException;
	
		public CrmSalesCost getCrmSalesCost(String id) throws ServiceException;
		
		public String deleteCrmSalesCost(String ids)throws ServiceException;
	
		public void addCrmSalesCost(CrmSalesCost crmSalesCost)throws ServiceException;
		
		public void updateCrmSalesCost(CrmSalesCost crmSalesCost)throws ServiceException;
		public List<CrmSalesCostTrDTO> getCrmSalesCostTr(String center_num)throws ServiceException,SQLException;
		public List<CrmSalesCostTrDTO> getCrmSalesCostBX()throws ServiceException,SQLException;
}
