package com.hjw.wst.service.impl;

import com.hjw.wst.DTO.ExamSummaryGuidanceLibDTO;
import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.wst.domain.ExamSummaryGuidanceLib;
import com.hjw.wst.model.ExamSummaryGuidanceLibModel;
import com.hjw.wst.service.ExamSummaryGuidanceLibService;
import com.synjones.framework.exception.ServiceException;
import com.synjones.framework.persistence.JdbcPersistenceManager;
import com.synjones.framework.persistence.JdbcQueryManager;
import com.synjones.framework.persistence.PersistenceManager;
import com.synjones.framework.persistence.QueryManager;
import com.synjones.framework.support.PageSupport;

/**
 * 体检综述（健康指导建议） service实现
     * @Title:  火箭蛙体检管理系统   
     * @Package com.hjw.wst.service.impl   
     * @Description:  
     * @author: dq     
     * @date:   2017年7月13日 下午4:54:55   
     * @version V2.0.0.0
 */
public class ExamSummaryGuidanceLibServiceImpl implements ExamSummaryGuidanceLibService{
	private QueryManager qm;
	private JdbcQueryManager jqm;
	private JdbcPersistenceManager jpm;
	private PersistenceManager pm;

	public QueryManager getQueryManager() {
		return qm;
	}

	public void setQueryManager(QueryManager qm) {
		this.qm = qm;
	}

	public JdbcQueryManager getJdbcQueryManager() {
		return jqm;
	}

	public void setJdbcQueryManager(JdbcQueryManager jqm) {
		this.jqm = jqm;
	}

	public JdbcPersistenceManager getJdbcPersistenceManager() {
		return jpm;
	}

	public void setJdbcPersistenceManager(JdbcPersistenceManager jpm) {
		this.jpm = jpm;
	}

	public PersistenceManager getPersistenceManager() {
		return pm;
	}

	public void setPersistenceManager(PersistenceManager pm) {
		this.pm = pm;
	}
	
	@Override
	public PageReturnDTO getExamSummaryGuidanceLibList(ExamSummaryGuidanceLibModel model, int page, int pagesize) throws ServiceException{
		
		String sql =" select e.id,e.guidance_word,e.guidance_pinyin,e.guidance_content,u.chi_name as updater,CONVERT(varchar(100),e.update_time,23) as update_time "
				   +" from exam_summary_guidance_lib e left join user_usr u on e.updater = u.id where e.is_active = 'Y' "
				   +" and (e.guidance_word like '%"+model.getGuidance_word()+"%' or e.guidance_pinyin like '%"+model.getGuidance_word()+"%')";
		PageReturnDTO webrole = new PageReturnDTO();
		webrole.setPage(page);
		webrole.setRp(pagesize);
		PageSupport map = (PageSupport) this.jqm.getList(sql,page,pagesize,ExamSummaryGuidanceLibDTO.class);
		if ((map != null) && (map.getList() != null)) {
			webrole.setTotal(map.getRecTotal());
			webrole.setRows(map.getList());
		}
		return webrole;
	}

	@Override
	public ExamSummaryGuidanceLib saveExamSummaryGuidanceLib(ExamSummaryGuidanceLib examSummaryGuidanceLib) throws ServiceException {
		this.pm.save(examSummaryGuidanceLib);
		return examSummaryGuidanceLib;
	}

	@Override
	public ExamSummaryGuidanceLib getExamSummaryGuidanceLib(long id) throws ServiceException {
		ExamSummaryGuidanceLib examSummaryGuidanceLib = (ExamSummaryGuidanceLib) this.qm.load(ExamSummaryGuidanceLib.class, id);
		return examSummaryGuidanceLib;
	}

	@Override
	public ExamSummaryGuidanceLib updateExamSummaryGuidanceLib(ExamSummaryGuidanceLib examSummaryGuidanceLib)
			throws ServiceException {
		this.pm.update(examSummaryGuidanceLib);
		return examSummaryGuidanceLib;
	}

}
