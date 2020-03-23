package com.hjw.zyb.service.impl;

import java.util.List;

import org.hibernate.service.spi.ServiceException;

import com.hjw.zyb.DTO.ZybAskDiagnosisSampleDTO;
import com.hjw.zyb.domain.ZybAskDiagnosisSample;
import com.hjw.zyb.model.ZybAskDiagnosisSampleModel;
import com.hjw.zyb.service.ZybAskDiagnosisSampleService;
import com.synjones.framework.persistence.JdbcPersistenceManager;
import com.synjones.framework.persistence.JdbcQueryManager;
import com.synjones.framework.persistence.PersistenceManager;
import com.synjones.framework.persistence.QueryManager;

public class ZybAskDiagnosisSampleServiceImpl implements ZybAskDiagnosisSampleService {
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
	public List<ZybAskDiagnosisSampleDTO> getZybAskDiagnosisSampleList(ZybAskDiagnosisSampleModel model)
			throws ServiceException {
		String sql = "select z.sex,z.id,z.name,z.sub_name,z.seq_code,z.type,z.temp_content,z.is_active,u.chi_name updater,z.update_time"
				   + " from zyb_ask_diagnosis_sample z left join user_usr u on u.id = z.creater where z.is_active = 'Y'";
		if(!"".equals(model.getType())){
			sql += " and z.type = '"+model.getType()+"'";
		}
		sql += " order by z.type,z.seq_code";
		List<ZybAskDiagnosisSampleDTO> list = this.jqm.getList(sql, ZybAskDiagnosisSampleDTO.class);
		return list;
	}

	@Override
	public ZybAskDiagnosisSample getZybAskDiagnosisSampleById(long id) throws ServiceException {
		ZybAskDiagnosisSample zybAskDiagnosisSample = (ZybAskDiagnosisSample) this.qm.load(ZybAskDiagnosisSample.class, id);
		return zybAskDiagnosisSample;
	}

	@Override
	public ZybAskDiagnosisSample saveZybAskDiagnosisSample(ZybAskDiagnosisSample zybAskDiagnosisSample)
			throws ServiceException {
		this.pm.save(zybAskDiagnosisSample);
		return zybAskDiagnosisSample;
	}

	@Override
	public ZybAskDiagnosisSample updateZybAskDiagnosisSample(ZybAskDiagnosisSample zybAskDiagnosisSample)
			throws ServiceException {
		this.pm.update(zybAskDiagnosisSample);
		return zybAskDiagnosisSample;
	}
}
