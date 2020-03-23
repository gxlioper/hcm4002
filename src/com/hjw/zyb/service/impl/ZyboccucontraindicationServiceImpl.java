package com.hjw.zyb.service.impl;

import java.sql.SQLException;
import java.util.List;

import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.zyb.DTO.ZyboccucontraindicationDTO;
import com.hjw.zyb.domain.Zyboccucontraindication;
import com.hjw.zyb.domain.ZyboccuhazardfactorsOccucontraindication;
import com.hjw.zyb.model.ZyboccucontraindicationModel;
import com.hjw.zyb.service.ZyboccucontraindicationService;
import com.synjones.framework.exception.ServiceException;
import com.synjones.framework.persistence.JdbcPersistenceManager;
import com.synjones.framework.persistence.JdbcQueryManager;
import com.synjones.framework.persistence.PersistenceManager;
import com.synjones.framework.persistence.QueryManager;
import com.synjones.framework.support.PageSupport;

public class ZyboccucontraindicationServiceImpl implements
		ZyboccucontraindicationService {

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
	public PageReturnDTO queryOccucontraindication(
			ZyboccucontraindicationModel model, int page, int pageSize)
			throws SQLException, ServiceException {
		String sql = "select z.contraindicationID,z.contraindication_name,z.tremexplain    from   zyb_occucontraindication  z  where   1=1";
		if (model.getTremexplain() != null
				&& !"".equals(model.getTremexplain())) {
			sql += "  and   z.contraindication_name like  '%"
					+ model.getContraindication_name().trim() + "%'";
		} 
		 sql+=" order by z.contraindication_name";
		PageSupport map = jqm.getList(sql, page, pageSize,
				ZyboccucontraindicationDTO.class);
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
	public void deleteOccucontraindication(String ids) throws SQLException,
			ServiceException {
		String hql = "FROM  Zyboccucontraindication  z  where   z.contraindicationID  in("+ ids + ")";
		List<Zyboccucontraindication> li = qm.find(hql);
		for (Zyboccucontraindication zyboccucontraindication : li) {
			pm.remove(zyboccucontraindication);
		}
		hql = " FROM   ZyboccuhazardfactorsOccucontraindication  oc   where   oc.contraindicationID  in("+ids+")";
		List<ZyboccuhazardfactorsOccucontraindication> lk = qm.find(hql);
		for (ZyboccuhazardfactorsOccucontraindication occ : lk) {
			pm.remove(occ);
		}
		
	}

	public void addOccucontraindication(Zyboccucontraindication occ,ZyboccucontraindicationModel model)throws SQLException, ServiceException {
		pm.save(occ);
	}

	@Override
	public void updateOccucontraindication(Zyboccucontraindication occ)
			throws SQLException, ServiceException {
		pm.update(occ);
	}

	@Override
	public Zyboccucontraindication getOccucontraindication(
			ZyboccucontraindicationModel model) throws SQLException,
			ServiceException {
		String hql = "FROM  Zyboccucontraindication  z  where   z.contraindicationID='"+ model.getContraindicationID()+"'";
		List<Zyboccucontraindication> li = qm.find(hql);
		return li.get(0);
	}

	public PageReturnDTO getLbOccucontraindicationList(
			ZyboccucontraindicationModel model,int page, int pageSize) throws SQLException,
			ServiceException {
		String sql = " SELECT o.RID,o.DISORDER,c.contraindicationID,c.contraindication_name,c.tremexplain,cl.occuphyexaclass_name,"
					+"dr.hazard_name  FROM   "
				   + "zyb_occuhazardfactors_occucontraindication  o,zyb_occucontraindication  c,"
				   + "zyb_occuhazardfactors  dr,zyb_occuphyexaclass cl"
				    +" WHERE  c.contraindicationID = o.contraindicationID    "
					+" AND  dr.hazardfactorsID = o.hazardfactorsID "
					+" AND   o.occuphyexaclassID = cl.occuphyexaclassID   AND "
				    +" o.hazardfactorsID  in("+model.getHazardfactorsID()+")  and 1=1 ";
					if(model.getContraindication_name()!=null && !"".equals(model.getContraindication_name())){
					sql+="   AND  c.contraindication_name  like   '%"+model.getContraindication_name().trim()+"%'";
					}
					if(model.getOccuphyexaclassID()!=null && !"".equals(model.getOccuphyexaclassID())){
					sql+="	 AND   o.occuphyexaclassID = '"+model.getOccuphyexaclassID()+"'  ";	
					}
		PageSupport map = jqm.getList(sql, page, pageSize,
				ZyboccucontraindicationDTO.class);
		PageReturnDTO webrole = new PageReturnDTO();
		webrole.setPage(page);
		webrole.setRp(pageSize);
		if ((map != null) && (map.getList() != null)) {
			webrole.setTotal(map.getRecTotal());
			webrole.setRows(map.getList());
		}
		return webrole;
	}

	public void addLbOccucontraindication(ZyboccucontraindicationModel model)
			throws SQLException, ServiceException {
		ZyboccuhazardfactorsOccucontraindication  zb = new ZyboccuhazardfactorsOccucontraindication();
		zb.setContraindicationID(model.getContraindicationID());
		zb.setOccuphyexaclassID(model.getOccuphyexaclassID());
		zb.setHazardfactorsID(model.getHazardfactorsID());
		zb.setDISORDER(model.getDISORDER());
		pm.save(zb);
	}

	@Override
	public List<ZyboccucontraindicationDTO> getLbcriterionManagerList(ZyboccucontraindicationModel model)
			throws SQLException, ServiceException {
		String sql = " SELECT z.contraindicationID,z.contraindication_name FROM  zyb_occucontraindication z ";
		List<ZyboccucontraindicationDTO>  li =  this.jqm.getList(sql,ZyboccucontraindicationDTO.class);
		return li;
	}

	@Override
	public void updateLbOccucontraindication(ZyboccucontraindicationModel model)
			throws SQLException, ServiceException {
		ZyboccuhazardfactorsOccucontraindication  z = (ZyboccuhazardfactorsOccucontraindication) this.qm.get(ZyboccuhazardfactorsOccucontraindication.class,model.getRID());
		z.setContraindicationID(model.getContraindicationID());
		z.setHazardfactorsID(model.getHazardfactorsID());
		z.setOccuphyexaclassID(model.getOccuphyexaclassID());
		z.setDISORDER(model.getDISORDER());
		this.pm.update(z);
		
	}

	@Override
	public ZyboccuhazardfactorsOccucontraindication getLbZyboccuhazardfactorsOccucontraindication(
			ZyboccucontraindicationModel model) throws SQLException,
			ServiceException {
		return 	(ZyboccuhazardfactorsOccucontraindication) this.qm.get(ZyboccuhazardfactorsOccucontraindication.class,model.getRID());
	}

	@Override
	public void deleteOccuhazardfactorsOccucontraindication(
			ZyboccucontraindicationModel model) throws SQLException,
			ServiceException {
		ZyboccuhazardfactorsOccucontraindication z = new ZyboccuhazardfactorsOccucontraindication();
		z.setRID(model.getRID());
		this.pm.remove(z);
	}
}
