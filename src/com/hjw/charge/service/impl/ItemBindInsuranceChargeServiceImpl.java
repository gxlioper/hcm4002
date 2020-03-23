package com.hjw.charge.service.impl;

import java.util.List;

import com.hjw.charge.DTO.ItemBindInsuranceDTO;
import com.hjw.charge.DTO.DictCityClinicItemPeisDTO;
import com.hjw.charge.DTO.DictInsuranceClinicItemDTO;
import com.hjw.charge.DTO.DictProvClinicItemPeisDTO;
import com.hjw.charge.DTO.DictProvInsuranceClinicItemDTO;
import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.charge.model.ItemBindInsuranceModel;
import com.hjw.charge.service.ItemBindInsuranceChargeService;
import com.synjones.framework.exception.ServiceException;
import com.synjones.framework.persistence.JdbcPersistenceManager;
import com.synjones.framework.persistence.JdbcQueryManager;
import com.synjones.framework.persistence.PersistenceManager;
import com.synjones.framework.persistence.QueryManager;
import com.synjones.framework.support.PageSupport;

public class ItemBindInsuranceChargeServiceImpl implements ItemBindInsuranceChargeService {

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
	public PageReturnDTO getChargingItemListForProv(ItemBindInsuranceModel model, int pagesize, int page)
			throws ServiceException {
		
		String sql = " select ci.id,ci.dep_id,dep.dep_name,ci.item_code,ci.item_name,ci.amount,(case when dpc.prov_count is null then '否' else '是' end) is_bind_prov  from charging_item ci " 
				+ " left join department_dep dep on ci.dep_id = dep.id "
				+ " left join "  
				+ " (select dpi.peis_item_code,count(*) as prov_count from dict_prov_clinic_item_peis dpi group by dpi.peis_item_code) dpc "  
				+ " on dpc.peis_item_code = ci.item_code " 
				+ " where ci.isActive = 'Y' ";
		if(model.getItem_code()!=null && !"".equals(model.getItem_code())) {
			sql += " and ci.item_code = '"+model.getItem_code()+"'";
		}
		if(model.getItem_name()!=null && !"".equals(model.getItem_name())) {
			sql += " and ci.item_name   like '%"+model.getItem_name()+"%'";
		}
		if(model.getDep_id()!=0) {
			sql += " and ci.dep_id = '"+model.getDep_id()+"'";
		}
		if(model.getIs_bind_prov()!=null && !"".equals(model.getIs_bind_prov())) {
			if("Y".equals(model.getIs_bind_prov())) {
				sql += " and dpc.prov_count >=1 ";
			}
			if("N".equals(model.getIs_bind_prov())) {
				sql += " and dpc.prov_count is null ";
			}
		}
		
		PageSupport map = (PageSupport) this.jqm.getList(sql, page, pagesize, ItemBindInsuranceDTO.class);
		PageReturnDTO webrole = new PageReturnDTO();
		webrole.setPage(page);
		webrole.setRp(pagesize);
		if ((map != null) && (map.getList() != null)) {
			webrole.setTotal(map.getRecTotal());
			webrole.setRows(map.getList());
		}
		return webrole;
		
	}

	@Override
	public PageReturnDTO getChargingItemListForCity(ItemBindInsuranceModel model, int pagesize, int page)
			throws ServiceException {
		
		String sql = " select ci.id,ci.dep_id,dep.dep_name,ci.item_code,ci.item_name,ci.amount,(case when dcc.city_count is null then '否' else '是' end) is_bind_city  from charging_item ci " 
				+ " left join department_dep dep on ci.dep_id = dep.id "
				+ " left join "  
				+ " (select dci.peis_item_code,count(*) as city_count from dict_city_clinic_item_peis dci group by dci.peis_item_code) dcc "  
				+ " on dcc.peis_item_code = ci.item_code " 
				+ " where ci.isActive = 'Y' ";
		if(model.getItem_code()!=null && !"".equals(model.getItem_code())) {
			sql += " and ci.item_code = '"+model.getItem_code()+"'";
		}
		if(model.getItem_name()!=null && !"".equals(model.getItem_name())) {
			sql += " and ci.item_name = '"+model.getItem_name()+"'";
		}
		if(model.getDep_id()!=0) {
			sql += " and ci.dep_id = '"+model.getDep_id()+"'";
		}
		if(model.getIs_bind_city()!=null && !"".equals(model.getIs_bind_city())) {
			if("Y".equals(model.getIs_bind_city())) {
				sql += " and dcc.city_count >=1 ";
			}
			if("N".equals(model.getIs_bind_city())) {
				sql += " and dcc.city_count is null ";
			}
		}
		
		PageSupport map = (PageSupport) this.jqm.getList(sql, page, pagesize, ItemBindInsuranceDTO.class);
		PageReturnDTO webrole = new PageReturnDTO();
		webrole.setPage(page);
		webrole.setRp(pagesize);
		if ((map != null) && (map.getList() != null)) {
			webrole.setTotal(map.getRecTotal());
			webrole.setRows(map.getList());
		}
		return webrole;
		
	}
	@Override
	public PageReturnDTO getCityInsuranceItemList(DictInsuranceClinicItemDTO dto, int page, int pageSize)
			throws ServiceException {
		String sql = "select ic.item_code as item_code_city,ic.item_name as item_name_city,ic.short_code,ic.price "
				+ "from dict_Insurance_clinic_item ic where 1=1";
		if(dto.getItem_name_city()!=null && !"".equals(dto.getItem_name_city())) {
			sql += "and (ic.item_name like '%"+dto.getItem_name_city().trim()+"%' or ic.short_code like '%"+dto.getItem_name_city().trim()+"%')";
		}
		if(dto.getItem_code_city()!=null && !"".equals(dto.getItem_code_city())) {
			sql += "and ic.item_code = '"+dto.getItem_code_city().trim()+"'";
		}
		sql += "order by ic.item_name ";
		PageSupport map = (PageSupport)this.jqm.getList(sql, page, pageSize, DictInsuranceClinicItemDTO.class);
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
	public List<DictCityClinicItemPeisDTO> getCityItemList(ItemBindInsuranceModel model) throws ServiceException {
		String sql = "select cp.medical_item_code as clinic_item_code,ci.item_name as item_name_city,cp.medical_item_code as clinic_item_code,cp.item_num,ci.price as item_price from medical_price_medical_item cp " + 
				"left join dict_Insurance_clinic_item ci on cp.medical_item_code = ci.item_code "
				+ "where cp.medical_type='02'  AND cp.medical_price_id = "+model.getId()+"";
		List<DictCityClinicItemPeisDTO> dtoList = this.jqm.getList(sql,DictCityClinicItemPeisDTO.class);
		return dtoList;
	}

	@Override
	public List<DictCityClinicItemPeisDTO> saveDictCityClinicItemPeis(String item_code,
			List<DictCityClinicItemPeisDTO> list) throws ServiceException {
		
		String sql = "select cp.peis_item_code,cp.clinic_item_code,cp.item_num,cp.item_price "
				+ "from dict_city_clinic_item_peis cp where cp.peis_item_code = '"+item_code+"'";
		List<DictCityClinicItemPeisDTO> cityList = this.jqm.getList(sql, DictCityClinicItemPeisDTO.class);
		if(cityList.size()>0) {
			String delSql = "delete from dict_city_clinic_item_peis  where peis_item_code = '"+item_code+"'";
			this.jpm.execSql(delSql);
		}
		
		for(int i=0;i<list.size();i++) {
			DictCityClinicItemPeisDTO dto = list.get(i);
			String insertSql = "insert into dict_city_clinic_item_peis (peis_item_code,clinic_item_code,item_num,item_price) values "
					+ "('"+item_code+"','"+dto.getClinic_item_code()+"',"+dto.getItem_num()+","+dto.getItem_price()+")";
			this.jpm.execSql(insertSql);
		}
		
		return list;
	}

	@Override
	public PageReturnDTO getProvInsuranceItemList(DictProvInsuranceClinicItemDTO dto, int page, int pageSize)
			throws ServiceException {
		String sql = "select pi.item_code as item_code_prov,pi.item_name as item_name_prov,pi.short_code,pi.price "
				+ "from dict_prov_Insurance_clinic_item pi where 1=1";
		if(dto.getItem_name_prov()!=null && !"".equals(dto.getItem_name_prov())) {
			sql += "and (pi.item_name like '%"+dto.getItem_name_prov().trim()+"%' or pi.short_code like '%"+dto.getItem_name_prov().trim()+"%')";
		}
		if(dto.getItem_code_prov()!=null && !"".equals(dto.getItem_code_prov())) {
			sql += "and pi.item_code = '"+dto.getItem_code_prov().trim()+"'";
		}
		sql += "order by pi.item_name ";
		PageSupport map = (PageSupport)this.jqm.getList(sql, page, pageSize, DictProvInsuranceClinicItemDTO.class);
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
	public List<DictProvClinicItemPeisDTO> getProvItemList(String peis_item_code) throws ServiceException {
		String sql = "select cp.clinic_item_code,pi.item_name as item_name_prov,cp.clinic_item_code,cp.item_num,cp.item_price from dict_prov_clinic_item_peis cp " + 
				"left join dict_prov_Insurance_clinic_item pi on cp.clinic_item_code = pi.item_code "
				+ "where cp.peis_item_code = '"+peis_item_code+"'";
		List<DictProvClinicItemPeisDTO> dtoList = this.jqm.getList(sql,DictProvClinicItemPeisDTO.class);
		return dtoList;
	}

	@Override
	public List<DictProvClinicItemPeisDTO> saveDictProvClinicItemPeis(String item_code,
			List<DictProvClinicItemPeisDTO> list) throws ServiceException {
		String sql = "select cp.peis_item_code,cp.clinic_item_code,cp.item_num,cp.item_price "
				+ "from dict_prov_clinic_item_peis cp where cp.peis_item_code = '"+item_code+"'";
		List<DictProvClinicItemPeisDTO> cityList = this.jqm.getList(sql, DictCityClinicItemPeisDTO.class);
		if(cityList.size()>0) {
			String delSql = "delete from dict_prov_clinic_item_peis  where peis_item_code = '"+item_code+"'";
			this.jpm.execSql(delSql);
		}
		
		for(int i=0;i<list.size();i++) {
			DictProvClinicItemPeisDTO dto = list.get(i);
			String insertSql = "insert into dict_prov_clinic_item_peis (peis_item_code,clinic_item_code,item_num,item_price) values "
					+ "('"+item_code+"','"+dto.getClinic_item_code()+"',"+dto.getItem_num()+","+dto.getItem_price()+")";
			this.jpm.execSql(insertSql);
		}
		
		return list;
	}

}
