package com.hjw.wst.service.impl;

import java.util.List;

import org.hibernate.service.spi.ServiceException;

import com.hjw.util.DateTimeUtil;
import com.hjw.wst.DTO.ExamSetTypeDTO;
import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.wst.DTO.UserDTO;
import com.hjw.wst.domain.ExamSetType;
import com.hjw.wst.model.ExamSetTypeModel;
import com.hjw.wst.service.ExamSetTypeService;
import com.synjones.framework.persistence.JdbcPersistenceManager;
import com.synjones.framework.persistence.JdbcQueryManager;
import com.synjones.framework.persistence.PersistenceManager;
import com.synjones.framework.persistence.QueryManager;
import com.synjones.framework.support.PageSupport;

public class ExamSetTypeServiceImpl implements ExamSetTypeService {

	private QueryManager qm;
	private JdbcQueryManager jqm;
	private JdbcPersistenceManager jpm;
	private PersistenceManager pm;


	public void setQueryManager(QueryManager qm) {
		this.qm = qm;
	}


	public void setJdbcQueryManager(JdbcQueryManager jqm) {
		this.jqm = jqm;
	}


	public void setJdbcPersistenceManager(JdbcPersistenceManager jpm) {
		this.jpm = jpm;
	}


	public void setPersistenceManager(PersistenceManager pm) {
		this.pm = pm;
	}

	@Override
	public PageReturnDTO getqueryExamSetTypeList(ExamSetTypeModel model,int page,int pageSize) throws ServiceException {
		String sql = "SELECT set_type_name,is_Active,set_class,id FROM  exam_set_type   where  is_Active = 'Y'";
		if(model.getSet_class()>0){
			sql +="  and  set_class = '"+model.getSet_class()+"'";
		}
		if(model.getSet_type_name() != null && !"".equals(model.getSet_type_name())){
			sql +=" and set_type_name like '%"+model.getSet_type_name().trim()+"%'";
		}
		PageSupport map=jqm.getList(sql,pageSize,page,ExamSetTypeDTO.class);
		PageReturnDTO webrole = new PageReturnDTO();
		webrole.setPage(page);
		webrole.setRp(pageSize);
		if ((map != null) && (map.getList() != null)) {
			webrole.setTotal(map.getRecTotal());
			webrole.setRows(map.getList());
		}
		return webrole;
	}

	@Override
	public void saveExamSetTypeList(ExamSetTypeModel model,UserDTO user) throws ServiceException {
		ExamSetType ex = new ExamSetType();
		ex.setSet_class(model.getSet_class());
		ex.setSet_type_name(model.getSet_type_name());
		ex.setId(model.getId());
		ex.setIs_Active("Y");
		ex.setCreater(user.getUserid());
		ex.setCreate_time(DateTimeUtil.parse());
		ex.setUpdate_time(DateTimeUtil.parse());
		ex.setUpdater(user.getUserid());
		this.pm.save(ex);
		
	}

	@Override
	public void updateSetTypeList(ExamSetTypeModel model,UserDTO user) throws ServiceException {
		ExamSetType ex = (ExamSetType) this.qm.get(ExamSetType.class, model.getId());
		ex.setSet_class(model.getSet_class());
		ex.setSet_type_name(model.getSet_type_name());
		ex.setId(model.getId());
		ex.setIs_Active("Y");
		ex.setUpdate_time(DateTimeUtil.parse());
		ex.setUpdater(user.getUserid());
		this.pm.update(ex);
		
	}

	@Override
	public void deleteExamSetTypeList(ExamSetTypeModel model) throws ServiceException {
		ExamSetType ex = (ExamSetType) this.qm.get(ExamSetType.class, model.getId());
		ex.setIs_Active("N");
		this.pm.update(ex);
	}


	@Override
	public ExamSetTypeDTO getExamSetTypeId(ExamSetTypeModel model) throws ServiceException {
		String sql = " SELECT * FROM exam_set_type where id = '"+model.getId()+"'";
		List<ExamSetTypeDTO> dto = this.jqm.getList(sql, ExamSetTypeDTO.class);
		return dto.get(0);
	}


	@Override
	public List<ExamSetTypeDTO> getExamSet_typeList() throws ServiceException {
		String sql = " SELECT * FROM exam_set_type  where  is_Active = 'Y' ";
		List<ExamSetTypeDTO> li = this.jqm.getList(sql, ExamSetTypeDTO.class);
		return li;
	}
}
