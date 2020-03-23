package com.hjw.crm.service;

import java.sql.SQLException;
import java.util.List;

import com.hjw.crm.domain.CrmVisitRecord;
import com.hjw.crm.model.CrmVisitRecordModel;
import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.wst.DTO.UserDTO;
import com.hjw.wst.domain.CustomerInfo;
import com.hjw.wst.domain.WebUserInfo;
import com.synjones.framework.exception.ServiceException;


public interface CrmVisitRecordService {
	
		public PageReturnDTO getCrmVisitRecordList(CrmVisitRecordModel model,int page,int pageSize) throws ServiceException,SQLException;
		
		public CrmVisitRecord getCrmVisitRecord(String id) throws ServiceException;

		
		public String deleteCrmVisitRecord(String id)throws ServiceException;
		
		public void addCrmVisitRecord(CrmVisitRecord crmVisitRecord)throws ServiceException;
		
		public void updateCrmVisitRecord(CrmVisitRecord crmVisitRecord)throws ServiceException;
		
		public String updateCrimVisitPlanStatus(String id)throws ServiceException;

		public List<CrmVisitRecord> getCrmVisitRecordListByVisitNum(String visit_num)throws ServiceException;


}
