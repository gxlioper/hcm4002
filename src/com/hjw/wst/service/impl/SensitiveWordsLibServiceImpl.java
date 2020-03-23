package com.hjw.wst.service.impl;

import java.util.List;

import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.wst.DTO.SensitiveWordsLibDTO;
import com.hjw.wst.domain.SensitiveWordsLib;
import com.hjw.wst.model.SensitiveWordsLibModel;
import com.hjw.wst.service.SensitiveWordsLibService;
import com.synjones.framework.exception.ServiceException;
import com.synjones.framework.persistence.JdbcQueryManager;
import com.synjones.framework.persistence.PersistenceManager;
import com.synjones.framework.persistence.QueryManager;
import com.synjones.framework.support.PageSupport;

public class SensitiveWordsLibServiceImpl implements SensitiveWordsLibService {

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
	public PageReturnDTO getSensitiveWordsLibList(SensitiveWordsLibModel model, int rows, int page)
			throws ServiceException {
		String sql = " select s.id,s.sensitive_type,s.sensitive_sex,s.sensitive_content,s.update_time,u.chi_name as updater "
				   + " from sensitive_words_lib s left join user_usr u on s.updater = u.id where s.is_active = 'Y'";
		if(!"".equals(model.getSensitive_type())){
			sql += " and s.sensitive_type = '"+model.getSensitive_type()+"'";
		}
		PageReturnDTO webrole = new PageReturnDTO();
		webrole.setPage(rows);
		webrole.setRp(page);
		PageSupport map = this.jqm.getList(sql, page, rows, SensitiveWordsLibDTO.class);
		if ((map != null) && (map.getList() != null)) {
			webrole.setTotal(map.getRecTotal());
			webrole.setRows(map.getList());
		}
		return webrole;
	}

	@Override
	public SensitiveWordsLib getSensitiveWordsLib(String id) throws ServiceException {
		SensitiveWordsLib lib = (SensitiveWordsLib) this.qm.load(SensitiveWordsLib.class, id);
		return lib;
	}

	@Override
	public SensitiveWordsLib saveSensitiveWordsLib(SensitiveWordsLib sensitiveWordsLib) throws ServiceException {
		this.pm.save(sensitiveWordsLib);
		return sensitiveWordsLib;
	}

	@Override
	public SensitiveWordsLib updateSensitiveWordsLib(SensitiveWordsLib sensitiveWordsLib) throws ServiceException {
		this.pm.update(sensitiveWordsLib);
		return sensitiveWordsLib;
	}

	@Override
	public String checkSensitiveWordsSex(SensitiveWordsLibModel model) throws ServiceException {
		String sql = "select s.sensitive_content from sensitive_words_lib s where s.is_active = 'Y' and s.sensitive_type = '1' "
					+"and s.sensitive_sex = '"+model.getSensitive_sex()+"' and '"+model.getSensitive_content()+"' like '%'+s.sensitive_content+'%'";
		List<SensitiveWordsLibDTO> list = this.jqm.getList(sql, SensitiveWordsLibDTO.class);
		if(list.size() != 0){
			String str = "";
			for(int i=0;i<list.size();i++){
				if(i == list.size()-1){
					str += list.get(i).getSensitive_content();
				}else{
					str += list.get(i).getSensitive_content()+",";
				}
			}
			return "error-"+str;
		}
		return "ok-未发现!";
	}

	@Override
	public String checkSensitiveWord(SensitiveWordsLibModel model) throws ServiceException {
		String sql = "select s.sensitive_content from sensitive_words_lib s where s.is_active = 'Y' and s.sensitive_type = '2'"
				   + " and '"+model.getSensitive_content()+"' like '%'+s.sensitive_content+'%'";
		List<SensitiveWordsLibDTO> list = this.jqm.getList(sql, SensitiveWordsLibDTO.class);
		if(list.size() != 0){
			String str = "";
			for(int i=0;i<list.size();i++){
				if(i == list.size()-1){
					str += list.get(i).getSensitive_content();
				}else{
					str += list.get(i).getSensitive_content()+",";
				}
			}
			return "error-"+str;
		}
		return "ok-未发现!";
	}
}
