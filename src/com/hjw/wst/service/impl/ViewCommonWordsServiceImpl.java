package com.hjw.wst.service.impl;

import java.util.List;

import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.wst.DTO.SampleDemoDTO;
import com.hjw.wst.DTO.ViewCommonWordsDTO;
import com.hjw.wst.domain.ViewCommonWords;
import com.hjw.wst.service.ViewCommonWordsService;
import com.synjones.framework.exception.ServiceException;
import com.synjones.framework.persistence.JdbcQueryManager;
import com.synjones.framework.persistence.PersistenceManager;
import com.synjones.framework.persistence.QueryManager;
import com.synjones.framework.support.PageSupport;


public class ViewCommonWordsServiceImpl implements ViewCommonWordsService {

	private QueryManager qm;
	private JdbcQueryManager jqm;
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
	
	@Override
	public PageReturnDTO getViewCommonWordsList(long simpleId, int pagesize, int pageno) throws ServiceException {
		
		String sql =" select v.id,v.sample_id,v.exam_desc,v.exam_result,s.demo_name as sample_name,CONVERT(varchar(20),v.seq_code) as seq_code,"
				   +" u.chi_name as updater,CONVERT(varchar(50),v.update_time,23) as update_time,v.is_default "
				   +" from sample_demo s,view_common_words v left join user_usr u on v.updater = u.id where "
				   +" v.sample_id = s.id and v.isActive = 'Y'";
		if(simpleId != 0){
			sql += " and v.sample_id = "+simpleId;
		}
		sql += " order by v.sample_id";
		PageSupport map = this.jqm.getList(sql, pageno, pagesize, ViewCommonWordsDTO.class);
		PageReturnDTO pageDto = new PageReturnDTO();
		pageDto.setPage(pageno);
		pageDto.setRp(pagesize);
		pageDto.setTotal(map.getRecTotal());
		pageDto.setRows(map.getList());
		return pageDto;
	}

	@Override
	public ViewCommonWords saveViewCommonWords(ViewCommonWords viewCommonWords) throws ServiceException {
		this.pm.save(viewCommonWords);
		return viewCommonWords;
	}

	@Override
	public ViewCommonWords updateViewCommonWords(ViewCommonWords viewCommonWords) throws ServiceException {
		this.pm.update(viewCommonWords);
		return viewCommonWords;
	}

	@Override
	public ViewCommonWords getViewCommonWords(long id) throws ServiceException {
		return (ViewCommonWords) this.qm.load(ViewCommonWords.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SampleDemoDTO> getSampleList() throws ServiceException {
		
		String sql ="select s.id,s.demo_name from sample_demo s "
				+ "where s.demo_category = '150' and s.isActive = 'Y'"
				+ "order by s.print_seq";
		
		List<SampleDemoDTO> list = this.jqm.getList(sql, SampleDemoDTO.class);
		return list;
	}

	@Override
	public ViewCommonWords getDefaultViewCommonWords(long sample_id) throws ServiceException {
		String sql = "from ViewCommonWords v where v.sample_id = '"+sample_id+"' and v.is_default = '1'";
		List<ViewCommonWords> list = this.qm.find(sql);
		if(list.size() > 0){
			return list.get(0);
		}
		return null;
	}

}
