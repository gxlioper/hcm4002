package com.hjw.crm.service;

import java.sql.SQLException;
import java.util.List;

import com.hjw.crm.DTO.CrmSalesCostTrDTO;
import com.hjw.wst.domain.CustomerMemberInfo;
import com.hjw.wst.model.ExamSummaryModel;
import com.synjones.framework.exception.ServiceException;

public interface CrmMemberManageService {
//	public String saveCrmMemberStore(ExamSummaryModel model);
	public CustomerMemberInfo getUpdatePageMmember(String arch_num);
	public String updateCustomerMemberInfo(String arch_num,String level);
	public String updateJiFenLing(String arch_num);
	public List<CrmSalesCostTrDTO> getLevel()throws ServiceException,SQLException;
	public String getLevelName(String level)throws ServiceException,SQLException;
}
