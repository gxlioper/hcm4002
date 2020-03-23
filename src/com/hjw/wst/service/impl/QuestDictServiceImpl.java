package com.hjw.wst.service.impl;

import java.util.List;

import com.hjw.wst.DTO.QuestExamDTO;
import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.wst.domain.QuestDictTitle;
import com.hjw.wst.domain.QuestDictItem;
import com.hjw.wst.service.QuestDictService;
import com.synjones.framework.exception.ServiceException;
import com.synjones.framework.persistence.JdbcPersistenceManager;
import com.synjones.framework.persistence.JdbcQueryManager;
import com.synjones.framework.persistence.PersistenceManager;
import com.synjones.framework.persistence.QueryManager;
import com.synjones.framework.support.PageSupport;

public class QuestDictServiceImpl implements QuestDictService{
	
	
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
	public PageReturnDTO queryQuestTitle(QuestExamDTO questExamDTO, int pageSize,
			int page) throws ServiceException {
		String sql="SELECT t.titleID, t.titleName,isnull(a.titleName,0) AS supID,t.Level,t.seqNo,t.isVisable,"
				+"t.quest_sub_code ,t.titleColumn FROM quest_dict_title t LEFT JOIN quest_dict_title a on a.titleID = t.supID "
				+ "where t.Level = '0'  and t.quest_sub_code = '"+questExamDTO.getQuest_sub_code()+"' ";
		
		if(questExamDTO.getTitleName()!=null && !questExamDTO.getTitleName().equals("")){
			sql+=" and  t.titleName like '%"+questExamDTO.getTitleName()+"%' ";		
		}
		
		sql+=" ORDER BY t.seqNo ";
		PageSupport map=jqm.getList(sql,pageSize,page,QuestExamDTO.class);
		PageReturnDTO title = new PageReturnDTO();
		title.setPage(page);
		title.setRp(pageSize);
		if ((map != null) && (map.getList() != null)) {
			title.setTotal(map.getRecTotal());
			title.setRows(map.getList());
		}
		return title;
	}

	@Override
	public PageReturnDTO queryModuleList(QuestExamDTO questExamDTO, int pageSize,
			int page) throws ServiceException {
		
		String sql="SELECT m.MenuId, m.MenuTitle, m.MenuTitleFather, m.seqNo, m.tablebodercolor, m.MenuPagequestNum, m.MeunquestNum "
				+ "FROM quest_dict_Menu m where 1=1";
		
		if(questExamDTO.getTitleName()!=null && !questExamDTO.getTitleName().equals("")){
			sql+=" and  m.MenuTitle like '%"+questExamDTO.getTitleName()+"%'";		
		}
		
		PageSupport map=jqm.getList(sql,pageSize,page,QuestExamDTO.class);
		PageReturnDTO module = new PageReturnDTO();
		module.setPage(page);
		module.setRp(pageSize);
		if ((map != null) && (map.getList() != null)) {
			module.setTotal(map.getRecTotal());
			module.setRows(map.getList());
		}
		return module;
		
	}

	@Override
	public PageReturnDTO queryStyleList(QuestExamDTO questExamDTO, int pageSize,
			int page) throws ServiceException {
		String sql="SELECT * from quest_dict_style where 1=1";
		
		if(questExamDTO.getTitleName()!=null && !questExamDTO.getTitleName().equals("")){
			sql+=" and  quest_dict_style.partStyleTitle like '%"+questExamDTO.getTitleName()+"%'";		
		}
		
		PageSupport map=jqm.getList(sql,pageSize,page,QuestExamDTO.class);
		PageReturnDTO style = new PageReturnDTO();
		style.setPage(page);
		style.setRp(pageSize);
		if ((map != null) && (map.getList() != null)) {
			style.setTotal(map.getRecTotal());
			style.setRows(map.getList());
		}
		return style;
	}

	@Override
	public PageReturnDTO queryProjectTwoGrid(QuestExamDTO questExamDTO,
			int pageSize, int page) throws ServiceException {
		String sql="SELECT t.titleID, t.titleName, a.titleName AS supID, t. LEVEL, t.seqNo, t.isVisable, t.quest_sub_code,t.titleColumn FROM "
				+ "quest_dict_title t LEFT JOIN quest_dict_title a ON a.titleID = t.supID"
				+ " WHERE 1 = 1 AND t.supID = '"+questExamDTO.getTitleID()+"' ";
		
		if(questExamDTO.getTitleName()!=null && !questExamDTO.getTitleName().equals("")){
			sql+=" and  t.titleName like '%"+questExamDTO.getTitleName()+"%' ";		
		}
		sql+=" ORDER BY t.seqNo ";
		PageSupport map=jqm.getList(sql,pageSize,page,QuestExamDTO.class);
		PageReturnDTO project = new PageReturnDTO();
		project.setPage(page);
		project.setRp(pageSize);
		if ((map != null) && (map.getList() != null)) {
			project.setTotal(map.getRecTotal());
			project.setRows(map.getList());
		}
		return project;
	}
	
	@Override
	public QuestDictTitle saveQuestTitle(QuestDictTitle questDictTitle) throws ServiceException{
		this.pm.save(questDictTitle);
		return questDictTitle;
	}

	@Override
	public QuestDictTitle editQuestTitle(QuestDictTitle questDictTitle) throws ServiceException {
		this.pm.update(questDictTitle);
		return questDictTitle;
	}

	@Override
	public void deleteQuestTitle(int titleID)
			throws ServiceException {
		String sql = "delete from quest_dict_title  where quest_dict_title.titleID = '" + titleID + "' ";
		this.jpm.execSql(sql);
	}

	@Override
	public PageReturnDTO getQuestionGrid(QuestExamDTO questExamDTO, int pageSize,
			int page) throws ServiceException {
		String sql = "SELECT q.ItemId, q.ItemName, isnull(a.ItemName, 0) AS supItemId, "
				+ "q.Itemlevel, q.seqNo, q.titleId, q.isVisable, q.isMulSel, q.linkNo, "
				+ "q.textUnit, q.linkItem, q.linksubItem, q.ItemCode, q.Sex, q.IsDefault, q.defaultResult "
				+ "FROM quest_dict_Item q LEFT JOIN quest_dict_Item a ON a.ItemId = q.supItemId "
				+ "WHERE q.titleId = '"+questExamDTO.getTitleID()+"' "
				+ "AND q.Itemlevel = '"+questExamDTO.getItemlevel()+"' ";
		
		if(questExamDTO.getSupItemId()!=null && !questExamDTO.getSupItemId().equals("")){
			sql+=" and  q.supItemId = '"+questExamDTO.getSupItemId()+"' ";		
		}
		
		if(questExamDTO.getTitleName()!=null && !questExamDTO.getTitleName().equals("")){
			sql+=" and  q.ItemName like '%"+questExamDTO.getTitleName()+"%' ";		
		}
		
		sql+= " ORDER BY q.seqNo ";
		
		PageSupport map=jqm.getList(sql,pageSize,page,QuestExamDTO.class);
		PageReturnDTO question = new PageReturnDTO();
		question.setPage(page);
		question.setRp(pageSize);
		if ((map != null) && (map.getList() != null)) {
			question.setTotal(map.getRecTotal());
			question.setRows(map.getList());
		}
		return question;
	}

	@Override
	public void deleteProjectQuest(int itemId) throws ServiceException {
		String sql = "delete from quest_dict_Item  where quest_dict_Item.ItemId = '" + itemId + "' ";
		this.jpm.execSql(sql);
	}

	@Override
	public QuestDictItem editQuestItem(QuestDictItem questItem)
			throws ServiceException {
		this.pm.update(questItem);
		return questItem;
	}

	@Override
	public QuestDictItem saveQuestItem(QuestDictItem questItem)
			throws ServiceException {
		this.pm.save(questItem);
		return questItem;
	}

	@Override
	public List<QuestExamDTO> querySelectType(QuestExamDTO questExamDTO) throws ServiceException {
		
		String sql="select q.quest_sub_code,q.quest_code,q.quest_sub_name from quest_dict_list q where isVisable = 1";
		List<QuestExamDTO> list = this.jqm.getList(sql,QuestExamDTO.class);
		return  list;
	};
	
	
}

