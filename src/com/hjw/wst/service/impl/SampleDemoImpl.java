package com.hjw.wst.service.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.hjw.util.DateTimeUtil;
import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.wst.DTO.SampleDemoChargingItemDTO;
import com.hjw.wst.DTO.SampleDemoDTO;
import com.hjw.wst.DTO.UserDTO;
import com.hjw.wst.domain.SampleDemo;
import com.hjw.wst.domain.SampleDemoChargingItem;
import com.hjw.wst.model.SampleDemoModel;
import com.hjw.wst.service.SampleDemoService;
import com.synjones.framework.exception.ServiceException;
import com.synjones.framework.persistence.JdbcPersistenceManager;
import com.synjones.framework.persistence.JdbcQueryManager;
import com.synjones.framework.persistence.PersistenceManager;
import com.synjones.framework.persistence.QueryManager;
import com.synjones.framework.support.PageSupport;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
/**
 * 
     * @Title:  火箭蛙体检管理系统   
     * @Package com.hjw.wst.service   
     * @Description:  检验样本
     * @author: 张瑞     
     * @date:   2016年9月5日 上午9:45:06   
     * @version V2.0.0.0
 */

public class  SampleDemoImpl implements SampleDemoService{
	
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

	public SampleDemo findClass(long id) throws ServiceException {
		return (SampleDemo) this.qm.get(SampleDemo.class, id);
	}

	public PageReturnDTO querySampleDemo(SampleDemo demo,int pageSize,int page,String  startStop){
		String sql = "select sa.id,sa.demo_num,sa.demo_name,sa.demo_category,da.data_name as dataname,"
				+ " sa.demo_indicator,sa.demo_color,sa.print_seq,sa.print_copy,sa.remark,sa.isActive,sa.BarCode_Class,"
				+ " u.chi_name as creatername,sa.create_time,us.chi_name as updataname,sa.isPrint_BarCode,"
				+ " sa.update_time FROM sample_demo sa " + " LEFT JOIN user_usr u ON sa.creater=u.id  "
				+ " LEFT JOIN user_usr us ON sa.updater=us.id "
				+ " LEFT JOIN data_dictionary da ON sa.demo_category=da.id" + " where 1=1 ";
		
		if (startStop != null && !"".equals(startStop)) {
			if(startStop.length()==1) {
				sql += " and sa.isActive IN ('"+startStop+"') ";
			}else {
				sql += " and sa.isActive IN ('Y','N') ";
			}
		}
		
		if (demo.getDemo_type() != -1) {
			sql += " and  sa.demo_type ='" + demo.getDemo_type() + "'";
		}
		if (demo.getDemo_num() != null && !demo.getDemo_num().equals("")) {
			sql += " and  sa.demo_num ='" + demo.getDemo_num().trim() + "'";
		}
		if (demo.getDemo_name() != null && !demo.getDemo_name().equals("")) {
			sql += " and  sa.demo_name like '%" + demo.getDemo_name().trim() + "%'";
		}
		if (!"".equals(demo.getDemo_category()) && demo.getDemo_category() != null) {
			sql += "  and  sa.demo_category='" + demo.getDemo_category() + "'";
		}
		sql += "  ORDER BY sa.id DESC";
		PageSupport map = this.jqm.getList(sql, page, pageSize, SampleDemoDTO.class);
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
	public void deleteSampleDemo(String id) throws ServiceException {
			String sql="";
			Connection connection = null;
			try {
				sql+="update sample_demo set isActive='N' where id in("+id+")";
				connection = this.jqm.getConnection();
				int rs = connection.createStatement().executeUpdate(sql);
			} catch (SQLException e) {
				e.printStackTrace();
			}finally{
				try {
					if(connection!=null){
						connection.close();
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
	}

	@Override
	public SampleDemo getSampleDemoBynum(String demo_num) throws ServiceException {
		List ls = qm.find("From SampleDemo Where  demo_num ='" + demo_num.trim() + "'");
			if (ls.size() > 0)
				return (SampleDemo) ls.get(0);
			else
				return null;
	}

	@Override
	public void addSampleDemo(SampleDemo sample) throws ServiceException {
		this.pm.save(sample);
	}

	@Override
	public void updateSampleDemo(SampleDemo sa) throws ServiceException {
		this.pm.update(sa);
	}

	@Override
	public List<SampleDemoChargingItemDTO> getSampleDemoChargingItemList(SampleDemoModel model)
			throws ServiceException {
		String sql = "SELECT sc.charging_id,ci.item_code,ci.item_name FROM  sample_demo_charging_item sc,charging_item  ci "
				+ " WHERE   sc.charging_item_code = ci.item_code " + " AND  ci.isActive='Y'  AND sc.sample_demo_id = '"
				+ model.getSample_demo_id() + "'";
		List<SampleDemoChargingItemDTO> li = this.jqm.getList(sql, SampleDemoChargingItemDTO.class);
		return li;
	}

	@Override
	public void deleteSampleDemoChargingItem(SampleDemoModel model) throws ServiceException {
		SampleDemoChargingItem it = (SampleDemoChargingItem) this.qm.get(SampleDemoChargingItem.class, model.getId());
		this.pm.remove(it);
	}

	public void addSampleDemoChargingItem(SampleDemoModel model, UserDTO user) throws ServiceException {
		String hql = " FROM SampleDemoChargingItem c  where  c.sample_demo_id='"+model.getSample_demo_id()+"'";
		List<SampleDemoChargingItem> sa = this.qm.find(hql);
		if(sa.size()>0){
			for (SampleDemoChargingItem sampleDemoChargingItem : sa) {
				this.pm.remove(sampleDemoChargingItem);
			}
		}
		
		JSONArray arr = JSONArray.fromObject(model.getCharging_id_s());
		List<SampleDemoChargingItemDTO> li = (List<SampleDemoChargingItemDTO>) JSONArray.toCollection(arr,SampleDemoChargingItemDTO.class);
		for (SampleDemoChargingItemDTO s : li) {
			SampleDemoChargingItem item = new SampleDemoChargingItem();
			item.setCharging_id(s.getCharging_id());
			item.setCreate_time(DateTimeUtil.parse());
			item.setCreater(user.getUserid());
			item.setSample_demo_id(model.getSample_demo_id());
			item.setCharging_item_code(s.getItem_code());
			this.pm.save(item);
		}
	}

	public PageReturnDTO getChagingitem(SampleDemoModel model, int page, int pageSize) throws ServiceException {
		String sql = " SELECT c.id,c.item_code,c.item_name FROM   charging_item c  WHERE  c.isActive='Y'  ";
		if (model.getItem_name_s() != null && !"".equals(model.getItem_name_s())) {
			sql += " and  (  c.item_code = '" + model.getItem_name_s().trim() + "'" + "  or c.item_name like '%"
					+ model.getItem_name_s().trim() + "%' " + " or c.item_pinyin like '%"
					+ model.getItem_name_s().trim() + "%' )";
		}
		PageSupport map = this.jqm.getList(sql, page, pageSize, SampleDemoChargingItemDTO.class);
		PageReturnDTO webrole = new PageReturnDTO();
		webrole.setPage(page);
		webrole.setRp(pageSize);
		if ((map != null) && (map.getList() != null)) {
			webrole.setTotal(map.getRecTotal());
			webrole.setRows(map.getList());
		}
		return webrole;
	}

	public List<SampleDemoDTO> getItemSampleDemoList(SampleDemoModel model) throws ServiceException {
		String sql = " SELECT ss.id,ss.demo_num,ss.demo_name FROM   sample_demo_charging_item oo,sample_demo ss  "
					+"  where  oo.sample_demo_id = ss.id  AND   ss.isActive='Y'  AND  oo.charging_id = '"+model.getCharging_id()+"'";
		List<SampleDemoDTO>  li = this.jqm.getList(sql, SampleDemoDTO.class);
		return li;
	}

	@Override
	public String saveDemoTypePage(String ids, int demo_type) throws ServiceException {
		String msg = "设置失败！";
		String sql="";
		Connection connection = null;
		try {
			sql+="update sample_demo set demo_type='"+demo_type+"' where id in("+ids+")";
			connection = this.jqm.getConnection();
			connection.createStatement().executeUpdate(sql);
			msg = "设置成功！";
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException sqle4) {
				sqle4.printStackTrace();
			}
		}
		return msg;
	}
}