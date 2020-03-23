package com.hjw.charge.service.impl;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.springframework.util.StringUtils;

import com.hjw.interfaces.util.DateUtil;
import com.hjw.charge.DTO.ChargingItemMedicalPriceDTO;
import com.hjw.charge.DTO.DicProvInsuranceClinicItemDTO;
import com.hjw.charge.DTO.MedicalPriceListDTO;
import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.wst.DTO.UserDTO;
import com.hjw.wst.domain.ChargingItem;
import com.hjw.charge.domain.MedicalPriceListCharge;
import com.hjw.charge.domain.MedicalPriceMedicalItemCharge;
import com.hjw.charge.model.MedicalModel;
import com.hjw.charge.service.MedicalChargeService;
import com.synjones.framework.exception.ServiceException;
import com.synjones.framework.persistence.JdbcPersistenceManager;
import com.synjones.framework.persistence.JdbcQueryManager;
import com.synjones.framework.persistence.PersistenceManager;
import com.synjones.framework.persistence.QueryManager;
import com.synjones.framework.support.PageSupport;

import net.sf.json.JSONArray;

public class MedicalChargeServiceImpl implements MedicalChargeService{
	private QueryManager qm;
	private JdbcQueryManager jqm;
	private PersistenceManager pm;
	private JdbcPersistenceManager jpm;
	
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
	public PageReturnDTO queryMedical(MedicalModel model, UserDTO user, int rows, int page) throws ServiceException {
		String sql = " SELECT id,item_name,item_spec,units,price FROM medical_price_list WHERE  is_active='Y' ";
				if(model.getId()>0){
				sql +="  AND  id="+model.getId()+" ";	
				}
				if(!StringUtils.isEmpty(model.getItem_name())){
				sql+= " AND item_name  like '%"+model.getItem_name().trim()+"%' ";
				}
		PageSupport map = (PageSupport) this.jqm.getList(sql, page, rows, MedicalPriceListDTO.class);
		PageReturnDTO webrole = new PageReturnDTO();
		webrole.setPage(page);
		webrole.setRp(rows);
		if ((map != null) && (map.getList() != null)) {
			webrole.setTotal(map.getRecTotal());
			webrole.setRows(map.getList());
		}
		return webrole;
	}

	@Override
	public List getChargingItemMedicalPrice(MedicalModel model) throws ServiceException {
		String sql = " SELECT gip.item_num,pr.id,pr.item_name,pr.item_spec,pr.units,pr.price FROM charging_item_medical_price gip,medical_price_list pr "
				    +" WHERE pr.id = gip.medical_price_id AND pr.is_active = 'Y' "
				    + " AND gip.charge_item_code = '"+model.getC_charge_item_code()+"'";
		List<MedicalPriceListDTO>  li = this.jqm.getList(sql,MedicalPriceListDTO.class);
		return li;
	}

	@Override
	public String saveChargingItemMedicalPrice(MedicalModel model,UserDTO user) throws ServiceException {
		List<ChargingItem> ls = qm.find("From ChargingItem  Where  item_code ='"+ model.getC_charge_item_code()+"'");
		ChargingItem chargingItem = ls.get(0);
		double amount = chargingItem.getAmount();
		JSONArray liArry = JSONArray.fromObject(model.getItem_list());
		@SuppressWarnings("unchecked")
	    List<ChargingItemMedicalPriceDTO> list = (List<ChargingItemMedicalPriceDTO>) JSONArray.toCollection(liArry, ChargingItemMedicalPriceDTO.class);
		//double item_price = 0;
		double item_num;
		BigDecimal item_price = new BigDecimal(0);
		for(int i = 0 ; i < list.size() ; i++){
			BigDecimal b1 = new BigDecimal(Integer.toString(list.get(i).getItem_num()));
	        BigDecimal b2 = new BigDecimal(Double.toString(list.get(i).getPrice()));
	        item_price = item_price.add(b1.multiply(b2)) ;
		}
		if(amount!=item_price.doubleValue()){
			return "error-价表价格与收费项目价格不一样，不能保存";
		}
		Connection connection = null;
		Statement stmt = null;
		
		try {
			connection = this.jqm.getConnection();
			connection.setAutoCommit(false);
			
			String sql = "DELETE FROM  charging_item_medical_price WHERE  charge_item_code = '"+model.getC_charge_item_code()+"' ";
			stmt = connection.createStatement();
			stmt.executeUpdate(sql);
			
			for(int i = 0 ; i < list.size() ; i++){
				String insert = "INSERT INTO charging_item_medical_price( "
				+" item_num,charge_item_code,medical_price_id,creater,create_date,updater,update_date)  "
				+" values("+list.get(i).getItem_num()+",'"+model.getC_charge_item_code()+"',"+list.get(i).getMedical_price_id()+","
				+ ""+user.getUserid()+",GETDATE(),"+user.getUserid()+",GETDATE()) ";
				stmt.executeUpdate(insert);
			}
			connection.commit();
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}finally {
			try {
				if(stmt!=null){
					stmt.close();
				}
				if(connection!=null){
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return "ok-保存成功";
	}
	public MedicalPriceListCharge getMedicalPriceById(MedicalModel model, UserDTO user) throws ServiceException {
		String hql = " FROM MedicalPriceListCharge WHERE id="+model.getId();
		List<MedicalPriceListCharge> li = this.qm.find(hql);
		if(li!=null && li.size() > 0){
			return li.get(0); 
		} else {
			return null;
		}
	}
	@Override
	public String saveMedicalPrice(MedicalModel model, UserDTO user) throws ServiceException {
		MedicalPriceListCharge me = new MedicalPriceListCharge();
		if(model.getId() > 0){
			me = (MedicalPriceListCharge) this.qm.get(MedicalPriceListCharge.class, model.getId());
		}
		me.setItem_class(model.getItem_class());
		me.setItem_name(model.getItem_name());
		me.setItem_spec(model.getItem_spec());
		me.setUnits(model.getUnits());
		me.setPrice(model.getPrice());
		me.setInput_code(model.getInput_code());
		me.setMemo(model.getMemo());
		me.setPrice(model.getPrice());
		if(model.getId() > 0){
			me.setId(model.getId());
			me.setUpdater(user.getUserid());
			me.setUpdate_date(DateUtil.parse());
			this.pm.update(me);
		} else {
			me.setCreater(user.getUserid());
			me.setCreate_date(DateUtil.parse());
			me.setIs_active("Y");
			me.setStart_date(DateUtil.parse());
			me.setStop_date(DateUtil.parse());
			this.pm.save(me);
		}
		return "ok";
	}

	@Override
	public int deleteMedicalPrice(MedicalModel model, UserDTO user) throws ServiceException {
		String sql = " update medical_price_list set  is_active='N' WHERE  id in("+model.getIds()+") ";
		return 	this.jpm.execSql(sql);
	}

	@Override
	public String saveMedicalPriceMedicalItem(MedicalModel model, UserDTO user) throws ServiceException {	
		String hql = " FROM MedicalPriceMedicalItemCharge WHERE medical_type='"+model.getMedical_type()+"'  AND  medical_price_id="+model.getMedical_price_id();
		List<MedicalPriceMedicalItemCharge> li = this.qm.find(hql);
		if(li!=null && li.size()>0){
			for(int i = 0 ; i < li.size() ; i++){
				this.pm.remove(li.get(i));
			}
		}
		JSONArray liArry = JSONArray.fromObject(model.getItem_list());
		@SuppressWarnings("unchecked")
	    List<MedicalPriceMedicalItemCharge> list = (List<MedicalPriceMedicalItemCharge>) JSONArray
	    	.toCollection(liArry, MedicalPriceMedicalItemCharge.class);
		for(int i = 0 ; i < list.size() ; i++){
			MedicalPriceMedicalItemCharge item = new MedicalPriceMedicalItemCharge();
			item.setCreate_date(DateUtil.parse());
			item.setCreater(user.getUserid());
			item.setItem_num(list.get(i).getItem_num());
			item.setMedical_item_code(list.get(i).getMedical_item_code());
			item.setMedical_price_id(list.get(i).getMedical_price_id());
			item.setMedical_type(model.getMedical_type());
			this.pm.save(item);
		}
		return "ok";
	}

	@Override
	public PageReturnDTO queryDicProvInsuranceClinicItem(MedicalModel model, int rows, int page)
			throws ServiceException {
		String sql = " SELECT d.item_code,d.item_name,d.price,d.short_code FROM "
					+"  dict_prov_Insurance_clinic_item d WHERE  1=1 ";
					if(!StringUtils.isEmpty(model.getItem_code())){
						sql+=" AND d.item_code='"+model.getItem_code()+"  '";
					}
					if(!StringUtils.isEmpty(model.getItem_name())){
						sql+=" AND d.item_name like '%"+model.getItem_name()+"%' ";
					}
					
					PageSupport map = (PageSupport) this.jqm.getList(sql, page, rows, DicProvInsuranceClinicItemDTO.class);
					PageReturnDTO webrole = new PageReturnDTO();
					webrole.setPage(page);
					webrole.setRp(rows);
					if ((map != null) && (map.getList() != null)) {
						webrole.setTotal(map.getRecTotal());
						webrole.setRows(map.getList());
					}
		return webrole;
	}

	@Override
	public List<DicProvInsuranceClinicItemDTO> getDicProvInsuranceClinicItemList(MedicalModel model)
			throws ServiceException {
		String sql = " SELECT d.item_code,d.item_name,d.price,d.short_code,mi.item_num FROM "
				+" medical_price_medical_item mi,dict_prov_Insurance_clinic_item d "
				+" WHERE  mi.medical_item_code = d.item_code AND mi.medical_type='03'"
				+" AND mi.medical_price_id="+model.getId();
		List<DicProvInsuranceClinicItemDTO> li = this.jqm.getList(sql, DicProvInsuranceClinicItemDTO.class);
		return li;
	}
}
