package com.hjw.zyb.service.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.hjw.util.DateTimeUtil;
import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.wst.DTO.UserDTO;
import com.hjw.zyb.DTO.ZybOccuhazardfactorsDTO;
import com.hjw.zyb.DTO.ZybProposetemplateDTO;
import com.hjw.zyb.DTO.ZybexaminationresultDTO;
import com.hjw.zyb.domain.ZybProposetemplate;
import com.hjw.zyb.model.ZybProposetemplateModel;
import com.hjw.zyb.service.ZybProposetemplateService;
import com.synjones.framework.exception.ServiceException;
import com.synjones.framework.persistence.JdbcPersistenceManager;
import com.synjones.framework.persistence.JdbcQueryManager;
import com.synjones.framework.persistence.PersistenceManager;
import com.synjones.framework.persistence.QueryManager;
import com.synjones.framework.support.PageSupport;
/**
 * 建议词管理
     * @Title:  火箭蛙体检管理系统   
     * @Package com.hjw.zyb.service.impl   
     * @Description:  
     * @author: yangm     
     * @date:   2017年4月27日 上午9:32:51   
     * @version V2.0.0.0
 */
public class ZybProposetemplateServiceImpl implements ZybProposetemplateService {

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
	public List getExaminationresult(ZybProposetemplateModel model) throws SQLException, ServiceException {
		String sql = " select * from zyb_examinationresult  z";
		if(model.getResult_name()!=null && !"".equals(model.getResult_name())){
		sql+="  where  z.result_name  like  '%"+model.getResult_name()+"%'";
		}
		List li = jqm.getList(sql, ZybexaminationresultDTO.class);
		return li;
	}

	@Override
	public PageReturnDTO queryProposetemplate(ZybProposetemplateModel model,
			int page, int pageSize) throws SQLException, ServiceException {
		String  sql = " select p.CONTEXT,p.ENABLE,p.ISDEFAULT,z.result_name  as  OCCUPECLASSID,p.OPTIONID,p.ORGID,p.TEMPLATEID,p.TEMPLATENAME,"
					  +" p.UPDATETIME,u.chi_name  as   user_name from zyb_proposetemplate p  "
				      +"	LEFT JOIN  user_usr  u  ON p.UPDATER=u.id "
				      +"    LEFT JOIN  zyb_examinationresult z  on  z.resultID=p.OPTIONID  ";
				if(model.getOPTIONID()!=null && !"".equals(model.getOPTIONID())){
					sql +="  where   p.OPTIONID='"+model.getOPTIONID()+"'";	
				}
				sql+="  order by  p.UPDATETIME  desc";
				PageSupport  map= jqm.getList(sql,page, pageSize,ZybProposetemplateDTO.class);
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
	public void deleteProposetemplate(ZybProposetemplateModel model)
			throws SQLException, ServiceException {
		
		String hql = " FROM ZybProposetemplate  z   where   z.TEMPLATEID  in("+model.getTEMPLATEID()+")";
		List<ZybProposetemplate> li = qm.find(hql);
		for (ZybProposetemplate zybProposetemplate : li) {
			pm.remove(zybProposetemplate);
		}
		
	}

	@Override
	public ZybProposetemplate getProposetemplate(ZybProposetemplateModel model)
			throws SQLException, ServiceException {
		String hql = " FROM ZybProposetemplate  z   where   z.TEMPLATEID='"+model.getTEMPLATEID()+"'";
		List<ZybProposetemplate> li = qm.find(hql);
		return li.get(0);
	}

	@Override
	public void addProposetemplate(ZybProposetemplate pro) throws SQLException,
			ServiceException {
		pm.save(pro);
	}

	@Override
	public void updateProposetemplate(ZybProposetemplateModel model,UserDTO user)
			throws SQLException, ServiceException {
		String hql = " FROM ZybProposetemplate  z   where   z.TEMPLATEID='"+model.getTEMPLATEID()+"'";
		List<ZybProposetemplate> li = qm.find(hql);
		li.get(0).setCONTEXT(model.getCONTEXT());
		li.get(0).setENABLE(model.isENABLE());
		li.get(0).setISDEFAULT(model.isISDEFAULT());
		li.get(0).setOCCUPECLASSID(model.getOCCUPECLASSID());
		li.get(0).setOPTIONID(model.getOPTIONID());
		li.get(0).setORGID(model.getORGID());
		li.get(0).setTEMPLATEID(model.getTEMPLATEID());
		li.get(0).setTEMPLATENAME(model.getTEMPLATENAME());
		li.get(0).setUPDATER(user.getUserid());
		li.get(0).setUPDATETIME(DateTimeUtil.parse());
		pm.update(li.get(0));
	}

	@Override
	public void updateProposetemplateListchebox(ZybProposetemplateModel model)
			throws SQLException, ServiceException {
		Connection  con = null;
		String Sql="";
		try {
			if("ENABLE".equals(model.getChebox())){
				Sql =" UPDATE  zyb_proposetemplate   SET  ENABLE='"+model.isENABLE()+"'  where   TEMPLATEID='"+model.getTEMPLATEID()+"'";
			} else {
				Sql =" UPDATE  zyb_proposetemplate   SET  ISDEFAULT='"+model.isISDEFAULT()+"'  where   TEMPLATEID='"+model.getTEMPLATEID()+"'";
			}
			con=jqm.getConnection();
			int rs = con.createStatement().executeUpdate(Sql);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				if(con!=null){
					con.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
