package com.hjw.wst.service.impl;

import java.util.List;

import com.hjw.util.DateTimeUtil;
import com.hjw.wst.DTO.ExamPrintTmpDTO;
import com.hjw.wst.DTO.UserDTO;
import com.hjw.wst.config.GetNumContral;
import com.hjw.wst.domain.ExamPrintTmp;
import com.hjw.wst.model.ExamPrintTmpModel;
import com.hjw.wst.service.ExamPrintTmpService;
import com.synjones.framework.exception.ServiceException;
import com.synjones.framework.persistence.JdbcPersistenceManager;
import com.synjones.framework.persistence.JdbcQueryManager;
import com.synjones.framework.persistence.PersistenceManager;
import com.synjones.framework.persistence.QueryManager;

public class ExamPrintTmpServiceImpl implements ExamPrintTmpService{

	private QueryManager qm;
	private JdbcQueryManager jqm;
	private JdbcPersistenceManager jpm;
	private PersistenceManager pm;
	
	public void setPersistenceManager(PersistenceManager pm) {
		this.pm = pm;
	}

	public void setQueryManager(QueryManager qm) {
		this.qm = qm;
	}

	public void setJdbcQueryManager(JdbcQueryManager jqm) {
		this.jqm = jqm;
	}

	public void setJdbcPersistenceManager(JdbcPersistenceManager jpm) {
		this.jpm = jpm;
	}

	@Override
	public String saveExamPrintTmp(ExamPrintTmpModel model, UserDTO user) throws ServiceException {
		List<ExamPrintTmpDTO> list = model.getExamPrintTmpList();
		String print_num = GetNumContral.getInstance().getParamNum("print_task_no", user.getCenter_num());
		for (ExamPrintTmpDTO examPrintTmpDTO : list) {
			ExamPrintTmp examPrintTmp = new ExamPrintTmp();
			
			examPrintTmp.setSessionId(print_num);
			examPrintTmp.setUserId(user.getUserid());
			examPrintTmp.setExam_num(examPrintTmpDTO.getExam_num());
			examPrintTmp.setPrint_type(examPrintTmpDTO.getPrint_type());
			examPrintTmp.setPrint_flag("N");
			examPrintTmp.setCharging_item_codes(examPrintTmpDTO.getCharging_item_codes());
			examPrintTmp.setBar_class(examPrintTmpDTO.getBar_calss());
			examPrintTmp.setArch_bar_num(examPrintTmpDTO.getArch_bar_num());
			examPrintTmp.setCreate_time(DateTimeUtil.parse());
			
			this.pm.save(examPrintTmp);
		}
		return "ok-"+print_num;
	}
}
