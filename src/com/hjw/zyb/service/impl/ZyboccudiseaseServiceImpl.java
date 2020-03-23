package com.hjw.zyb.service.impl;

import java.sql.SQLException;
import java.util.List;

import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.zyb.DTO.ZybOccuhazardClassDTO;
import com.hjw.zyb.DTO.ZyboccudiseaseDTO;
import com.hjw.zyb.DTO.ZyboccudiseaseclassDTO;
import com.hjw.zyb.DTO.ZyboccuhazardfactorsOccudiseaseDTO;
import com.hjw.zyb.domain.Zyboccudisease;
import com.hjw.zyb.domain.Zyboccudiseaseclass;
import com.hjw.zyb.domain.ZyboccuhazardfactorsOccudisease;
import com.hjw.zyb.model.ZyboccudiseaseModel;
import com.hjw.zyb.service.ZyboccudiseaseService;
import com.synjones.framework.exception.ServiceException;
import com.synjones.framework.persistence.JdbcPersistenceManager;
import com.synjones.framework.persistence.JdbcQueryManager;
import com.synjones.framework.persistence.PersistenceManager;
import com.synjones.framework.persistence.QueryManager;
import com.synjones.framework.support.PageSupport;
/***
 * 职业病/分类管理
     * @Title:  火箭蛙体检管理系统   
     * @Package com.hjw.zyb.service.impl   
     * @Description:  
     * @author: yangm     
     * @date:   2017年4月11日 下午4:15:27   
     * @version V2.0.0.0
 */
public class ZyboccudiseaseServiceImpl implements ZyboccudiseaseService {

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
	public PageReturnDTO queryZyboccudiseaseList(ZyboccudiseaseModel model, int page, int pageSize)
			throws SQLException, ServiceException {
		String sql = "SELECT z.occudiseaseID,z.occudisease_name FROM zyb_occudisease z  where  1=1";
					if(model.getDiseaseclassID()>0){
				sql +=" and   z.diseaseclassID='"+model.getDiseaseclassID()+"'";	
					}
			    sql +="  order by  z.occudisease_name";
		PageSupport map = jqm.getList(sql, page, pageSize, ZyboccudiseaseDTO.class);
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
	public void deleteZyboccudisease(String ids) throws SQLException, ServiceException {
		if(ids!=null && !"".equals(ids)){
			String sql = "FROM  Zyboccudisease z where   z.occudiseaseID  in("+ids+")";
			List<Zyboccudisease> li = qm.find(sql);
			for (Zyboccudisease zyboccudisease : li) {
				pm.remove(zyboccudisease);
			}
		}
	}

	@Override
	public Zyboccudisease  getZyboccudisease(ZyboccudiseaseModel mode) throws SQLException, ServiceException {
		String sql = " FROM  Zyboccudisease z where   z.occudiseaseID='"+mode.getOccudiseaseID()+"'";
		List<Zyboccudisease> li = qm.find(sql);
		return li.get(0);
	}

	@Override
	public void addZyboccudiseaseList(Zyboccudisease zy) throws SQLException, ServiceException {
		this.pm.save(zy);
	}

	@Override
	public void updateZyboccudisease(Zyboccudisease zy) throws SQLException, ServiceException {
		this.pm.update(zy);
	}

	@Override
	public List<ZyboccudiseaseclassDTO> queryZyboccudiseaseListclass(ZyboccudiseaseModel mode)
			throws SQLException, ServiceException {
		String sql = "SELECT * FROM  zyb_occudiseaseclass z where  1=1 ";
				if(mode.getDiseaseclass_name()!=null && !"".equals(mode.getDiseaseclass_name())){
			   sql	+="	 and	z.diseaseclass_name  like  '%"+mode.getDiseaseclass_name().trim()+"%'";
				}
		List<ZyboccudiseaseclassDTO>  li = jqm.getList(sql,ZyboccudiseaseclassDTO.class);
		return li;
	}

	@Override
	public PageReturnDTO getZyboccudiseaseYinsuTable(ZyboccudiseaseModel model,int page,int pageSize)
			throws SQLException, ServiceException {
	String sql =" SELECT oo.RID,ds.occudisease_name,ds.occudiseaseID,os.hazardfactorsID,os.hazard_name,oo.RID,oo.DISORDER,cl.occuphyexaclass_name"
			   +",cl.occuphyexaclassid FROM   "
			   +"zyb_occudisease ds,zyb_occuphyexaclass   cl,zyb_occuhazardfactors_occudisease oo,zyb_occuhazardfactors os"
			   +"  where   oo.occudiseaseID = ds.occudiseaseID  AND   oo.occuphyexaclassid = cl.occuphyexaclassID  "
			   +"  AND  oo.hazardfactorsID=os.hazardfactorsID   "
					+ " AND   oo.hazardfactorsID in("+model.getHazardfactorsID()+")    ";
					if(model.getTjlb()!=null&& !"".equals(model.getTjlb())){
						sql+="  AND cl.occuphyexaclassid='"+model.getTjlb()+"'  ";
					}
					if(model.getOccudisease_name()!=null && !"".equals(model.getOccudisease_name())){
						sql+="  AND   ds.occudisease_name  like '%"+model.getOccudisease_name().trim()+"%'";
					}
					sql += " order  by  oo.DISORDER  desc";
		PageSupport map = jqm.getList(sql, page, pageSize, ZyboccuhazardfactorsOccudiseaseDTO.class);
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
	public void saveZyboccudiseaseYinsu(ZyboccudiseaseModel model)
			throws SQLException, ServiceException {
		ZyboccuhazardfactorsOccudisease s = new ZyboccuhazardfactorsOccudisease();
		s.setDISORDER(model.getDISORDER());
		s.setHazardfactorsID(model.getHazardfactorsID());
		s.setOccudiseaseID(model.getOccudiseaseID());
		s.setOccuphyexaclassid(model.getTjlb());
		this.pm.save(s);
	}

	@Override
	public void updateZyboccudiseaseYinsuList(ZyboccudiseaseModel model)
			throws SQLException, ServiceException {
		ZyboccuhazardfactorsOccudisease s= (ZyboccuhazardfactorsOccudisease) this.qm.get(ZyboccuhazardfactorsOccudisease.class,model.getRID());
		s.setDISORDER(model.getDISORDER());
		s.setHazardfactorsID(model.getHazardfactorsID());
		s.setOccudiseaseID(model.getOccudiseaseID());
		s.setOccuphyexaclassid(model.getTjlb());
		this.pm.update(s);
	}

	public void deleteZyboccudiseaseYinsu(String ids)
			throws SQLException, ServiceException {
		String hql = " FROM ZyboccuhazardfactorsOccudisease z  where  z.RID  in  ("+ids+")";
		List<ZyboccuhazardfactorsOccudisease> li = this.qm.find(hql);
		for (ZyboccuhazardfactorsOccudisease zyboccuhazardfactorsOccudisease : li) {
			this.pm.remove(zyboccuhazardfactorsOccudisease);
		}
	}

	@Override
	public List<ZyboccudiseaseDTO> getZyboccudiseaseYinsuList(
			ZyboccudiseaseModel mode) throws SQLException, ServiceException {
		String sql = " SELECT di.occudiseaseID,di.occudisease_name FROM zyb_occudisease di ";
		List<ZyboccudiseaseDTO>  li = this.jqm.getList(sql, ZyboccudiseaseDTO.class);
		return li;
	}

	@Override
	public ZyboccuhazardfactorsOccudiseaseDTO getFindIDZyboccudiseaseYinsuList(
			ZyboccudiseaseModel mode) throws SQLException, ServiceException {
			String sql =" SELECT oo.RID,ds.occudisease_name,ds.occudiseaseID,os.hazardfactorsID,os.hazard_name,oo.RID,oo.DISORDER,cl.occuphyexaclass_name"
					+ " ,cl.occuphyexaclassID FROM  "
					  +" zyb_occudisease ds,zyb_occuphyexaclass   cl,zyb_occuhazardfactors_occudisease oo,zyb_occuhazardfactors os  "
					  +" where   oo.occudiseaseID = ds.occudiseaseID  AND   oo.occuphyexaclassid = cl.occuphyexaclassID "
					  +" AND  oo.hazardfactorsID=os.hazardfactorsID "
					  +"  AND RID='"+mode.getRID()+"'";
				List<ZyboccuhazardfactorsOccudiseaseDTO>  li =  this.jqm.getList(sql, ZyboccuhazardfactorsOccudiseaseDTO.class);
		return li.get(0);
	}
}
