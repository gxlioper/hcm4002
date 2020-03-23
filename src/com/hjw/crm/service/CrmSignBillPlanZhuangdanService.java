package com.hjw.crm.service;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.List;

import com.hjw.crm.DTO.CrmSignBillPlanDTO;
import com.hjw.crm.model.CrmSignBillPlanModel;
import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.wst.domain.CompanyInfo;
import com.synjones.framework.exception.ServiceException;

public interface CrmSignBillPlanZhuangdanService {
	public PageReturnDTO getAllSignBillPlanZhuangDanList(CrmSignBillPlanModel model,int page,int rows) throws ServiceException,SQLException;
	public PageReturnDTO getSignBillPlanZhuangDanList(CrmSignBillPlanModel model,int page,int rows) throws ServiceException,SQLException, UnsupportedEncodingException;
	public String updateSignBillPlanZhuangDan(String id,String status)throws ServiceException;
	public String updateSignBillPlanZhuangDanProcess(String id,String status)throws ServiceException;
	public String updateSignBillPlanZhuangDanProcessBySignNum(String sign_num,String status)throws ServiceException;
	public List<CompanyInfo> getCompanyInfoFromZhuangdan(String id)throws ServiceException;
	public List<CrmSignBillPlanDTO> getCrmSignBillPlanFromZhuangdan(String id)throws ServiceException;
	public String updateAbortDate(String ids,String abort_date)throws ServiceException;
}
