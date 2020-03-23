package com.hjw.wst.service.impl;

import java.util.List;

import com.hjw.wst.DTO.ExamInfoDTO;
import com.hjw.wst.model.ExamInfoModel;
import com.hjw.wst.service.YxCardPdfService;
import com.synjones.framework.exception.ServiceException;
import com.synjones.framework.persistence.JdbcQueryManager;

public class YxCardPdfServiceImpl implements YxCardPdfService {
	private JdbcQueryManager jqm;
	
	public void setJdbcQueryManager(JdbcQueryManager jqm) {
		this.jqm = jqm;
	}
	@Override
	public String getExamInfoPatient(ExamInfoModel model) throws ServiceException {
		String sql = "SELECT patient_id FROM exam_info where   exam_num='"+model.getExam_num().trim()+"'";
		List<ExamInfoDTO> info = this.jqm.getList(sql,ExamInfoDTO.class);
		if(info.size()>0){
			return info.get(0).getPatient_id();
		} else {
			return null;
		}
	}

}
