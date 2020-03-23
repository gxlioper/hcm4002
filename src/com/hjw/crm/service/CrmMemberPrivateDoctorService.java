package com.hjw.crm.service;

import java.sql.SQLException;

import com.hjw.crm.domain.CrmMemberPrivateDoctor;
import com.hjw.crm.model.CrmMemberPrivateDoctorModel;
import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.wst.DTO.UserDTO;
import com.synjones.framework.exception.ServiceException;

public interface CrmMemberPrivateDoctorService {
	//获取表
		public PageReturnDTO getCrmMemberPrivateDoctorList(CrmMemberPrivateDoctorModel model,int page,int pageSize, UserDTO user) throws ServiceException,SQLException;
		//获取单个
		public CrmMemberPrivateDoctor getCrmMemberPrivateDoctor(String id) throws ServiceException;
		public CrmMemberPrivateDoctor getCrmMemberPrivateDoctor(String exam_num,String arch_num,String userid) throws ServiceException ;
		//新增或更新
		public String mergeCrmMemberPrivateDoctor(CrmMemberPrivateDoctorModel model)throws ServiceException;
		//获取私人医生的体检者表
		public PageReturnDTO getCrmDoctorMemberList(CrmMemberPrivateDoctorModel model,int page,int pageSize) throws ServiceException,SQLException;
		public String updateCrmMemberPrivateDoctor(CrmMemberPrivateDoctor crmMemberPrivateDoctor)throws ServiceException;
		public String saveCrmMemberPrivateDoctor(CrmMemberPrivateDoctor crmMemberPrivateDoctor)throws ServiceException;
}
