package com.hjw.crm.service;

import java.sql.SQLException;
import java.util.List;

import com.hjw.crm.domain.CrmSignTracking;
import com.hjw.crm.model.CrmSignTrackingModel;
import com.hjw.wst.DTO.PageReturnDTO;
import com.synjones.framework.exception.ServiceException;


public interface CrmSignTrackingService {
	public PageReturnDTO getCrmSignTrackingList(CrmSignTrackingModel model,long userid,int page,int pageSize) throws ServiceException,SQLException;
	public List<String> getCrmSignTrackingTime();
	public void addCrmSignTracking(CrmSignTracking crmSignTracking);
}
