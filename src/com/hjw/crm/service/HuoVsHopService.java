package com.hjw.crm.service;

import java.sql.SQLException;

import com.hjw.crm.domain.HuoVsHop;
import com.hjw.crm.model.HuoVsHopModel;
import com.hjw.wst.DTO.PageReturnDTO;
import com.synjones.framework.exception.ServiceException;

public interface HuoVsHopService {
	public PageReturnDTO getHuoVsHopList(HuoVsHopModel model,int page,int rows) throws ServiceException,SQLException;
	public PageReturnDTO getHuoLisProject(HuoVsHopModel model,int page,int rows) throws ServiceException,SQLException;
	public PageReturnDTO getHuoPacsProject(HuoVsHopModel model,int page,int rows) throws ServiceException,SQLException;
	public PageReturnDTO getHuoCommonProject(HuoVsHopModel model,int page,int rows) throws ServiceException,SQLException;
	public HuoVsHop getHuoVsHop(String id)throws ServiceException,SQLException;
	public String updateHuoVsHop(HuoVsHop data)throws ServiceException,SQLException;
	public String saveHuoVsHop(HuoVsHop data)throws ServiceException,SQLException;
	public String deleteHuoVsHop(String id)throws ServiceException,SQLException;
}
