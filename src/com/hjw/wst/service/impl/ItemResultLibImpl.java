package com.hjw.wst.service.impl;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

import com.hjw.wst.DTO.ExaminationItemDTO;
import com.hjw.wst.DTO.ItemResultLibDTO;
import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.wst.domain.ExaminationItem;
import com.hjw.wst.domain.ItemResultLib;
import com.hjw.wst.service.ItemResultLibService;
import com.synjones.framework.exception.ServiceException;
import com.synjones.framework.persistence.JdbcPersistenceManager;
import com.synjones.framework.persistence.JdbcQueryManager;
import com.synjones.framework.persistence.PersistenceManager;
import com.synjones.framework.persistence.QueryManager;
import com.synjones.framework.support.PageSupport;
/**
 * 
     * @Title:  火箭蛙体检管理系统   
     * @Package com.hjw.wst.service.impl   
     * @Description: 项目结果知识库 
     * @author: zr     
     * @date:   2016年10月26日 上午11:24:28   
     * @version V2.0.0.0
 */
public class ItemResultLibImpl implements ItemResultLibService {
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
	public PageReturnDTO queryItemResultLib(ItemResultLibDTO dto,int page,int pageSize, String center_num ) throws ServiceException {
		String sql="SELECT "
				  +" item_result_lib.id,"
				  +" examination_item.item_num,"
				  + "examination_item.item_name,"
				  + "case when  examination_item.default_value = item_result_lib.id then '是' else '否' end default_value_s,"
				  +" item_result_lib.exam_item_id,"
				  +" item_result_lib.dep_id,"
				  +" item_result_lib.exam_result,"
				  +" item_result_lib.result_num,"
				  +" item_result_lib.isActive,"
				  +" item_result_lib.creater,"
				  +" item_result_lib.create_time,"
				  +" item_result_lib.updater,"
				  +" item_result_lib.update_time,"
				  +" item_result_lib.seq_code,"
				  +" item_result_lib.common_words,"
				  +" item_result_lib.exam_conclusion,"
				  +" u.chi_name as creaters,"
				  +" up.chi_name as updaters,"
				  +" dep.dep_name"
				  +" FROM item_result_lib  "
				  + " LEFT JOIN  examination_item ON item_result_lib.item_code=examination_item.item_num  and   examination_item.is_Active='Y' "
				  +" LEFT JOIN department_dep dep ON  item_result_lib.dep_id=dep.id  and  dep.isActive='Y'"
				  +" LEFT JOIN department_vs_center ded ON  item_result_lib.dep_id=ded.dep_id"
				  +" LEFT JOIN user_usr  u ON   item_result_lib.creater=u.id"
				  +" LEFT JOIN user_usr  up  ON   item_result_lib.updater=up.id"
				  +" where  item_result_lib.isActive='Y'  and ded.center_num ='"+center_num+"' ";
				  if(dto.getDep_id()>0){
				sql+="   and  item_result_lib.dep_id='"+dto.getDep_id()+"'";
				  }
				  if(dto.getExam_item_id()>0){
			    sql+="  and  item_result_lib.exam_item_id='"+dto.getExam_item_id()+"'";
				  }
				  if(dto.getExam_result()!=null && !"".equals(dto.getExam_result())){
			    sql+="  and   item_result_lib.exam_result  like '%"+dto.getExam_result().trim()+"%'  or  item_result_lib.result_num  like '%"+dto.getExam_result().trim()+"%'";
				  }
				sql+=" ORDER BY  item_result_lib.create_time  DESC";
		PageSupport map=this.jqm.getList(sql,page,pageSize,ItemResultLibDTO.class);
		PageReturnDTO webrole = new PageReturnDTO();
		webrole.setPage(page);
		webrole.setRp(pageSize);
		if ((map != null) && (map.getList() != null)) {
			webrole.setTotal(map.getRecTotal());
			webrole.setRows(map.getList());
		}
		return webrole;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ExaminationItemDTO> getExaminationItem(long dep_id)  throws ServiceException {
		String  sql  = "SELECT DISTINCT  e.item_name,e.id  FROM examination_item e,charging_item c,charging_item_exam_item g  where "
				  +"  e.is_Active='Y'  and   c.isActive='Y'  and   c.item_code=g.charging_item_code   and  e.item_num=g.item_code     ";
				  if (dep_id>0) {
				sql += "  and    c.dep_id='" + dep_id + "'";	  
				  }
			    sql += "  ORDER BY  e.item_name";
		return  this.jqm.getList(sql,ExaminationItemDTO.class);
	}

	@Override
	public ItemResultLib addItemResultLib(ItemResultLib item) throws ServiceException {
		this.pm.save(item);
		return item;
	}

	@Override
	public ItemResultLib findItemResultLib(Long itme) throws ServiceException {
		return 	(ItemResultLib) qm.get(ItemResultLib.class,itme);
	}

	@Override
	public ItemResultLib updateItemResultLib(ItemResultLib item) throws ServiceException {
			this.pm.update(item);
		return item;
	}

	@Override
	public void delItemResultLib(String ids) throws ServiceException {
		if(ids!=null && !"".equals(ids)){
			String sql="";
				sql+="FROM ItemResultLib  where id in("+ids+")";
				List<ItemResultLib> li = this.qm.find(sql);
				if(li.size()>0){
					for (ItemResultLib te : li) {
						te.setIsActive("N");
						this.pm.update(te);
						sql="select *  from    item_result_lib  l  left  join   examination_item e   on  l.exam_item_id=e.id  and  e.is_Active='Y'   where  l.isActive='Y'"
								+ "  and  l.id='"+te.getId()+"'";
						List<ExaminationItemDTO> ex = this.jqm.getList(sql,ExaminationItemDTO.class);
						
						if(ex.size()>0){
								int i=0;
								for (int j = 0; j < ex.size(); j++) {
									sql=" FROM  ExaminationItem  where  id='"+ex.get(i).getId()+"'";
									List<ExaminationItem>  am = qm.find(sql);
									if(am.size()>0){
										for (ExaminationItem examinationItem : am) {
											examinationItem.setDefault_value(null);
											this.pm.update(examinationItem);
										}
									}
								}
						}
					}
				}
		}
			
	}
	

	@Override
	public List<ExaminationItemDTO> getDepItemResultLib(ItemResultLibDTO dep_id,String center_num )   throws ServiceException {
				String sql="";
				Connection connection = null;
				List<ExaminationItemDTO> li = new ArrayList<ExaminationItemDTO>();
				try {
					sql+="SELECT distinct ex.id,ex.item_name FROM charging_item_exam_item e"
						+"  LEFT JOIN charging_item c ON  e.charging_item_code=c.item_code  and     c.isActive='Y'"		 
						+"  LEFT JOIN  examination_item ex ON  e.item_code=ex.item_num  and   ex.is_Active='Y'"
						+" LEFT JOIN department_vs_center ded ON  c.dep_id=ded.dep_id"
						+"  LEFT JOIN  department_dep d ON  c.dep_id=d.id      where    1=1 and ded.center_num ='"+center_num+"'   ";						 
							if(dep_id.getDep_id()>0){
						sql+="    and  d.id='"+dep_id.getDep_id()+"'";		
							}
						connection = this.jqm.getConnection();
						ResultSet rs = connection.createStatement().executeQuery(sql);
						while(rs.next()){
							ExaminationItemDTO dto = new ExaminationItemDTO();
							dto.setId(rs.getLong("id"));
							dto.setItem_name(rs.getString("item_name"));
							li.add(dto);
						}
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
		return li;
	}
	public  ExaminationItem  getExaminationItemResult(String id) throws ServiceException{
		String sql="SELECT * from   item_result_lib	 l   left  join  examination_item  e   on  l.exam_item_id=e.id	 "
				+ "    and   e.is_Active='Y'   where		  l.isActive='Y'     "
				+ " AND  l.id="+id+"";
		List<ItemResultLibDTO>  li = this.jqm.getList(sql,ItemResultLibDTO.class);
		ExaminationItem  tem = new ExaminationItem();
		if(li.size()>0){
			if(li.get(0).getExam_item_id()>0){
				tem=(ExaminationItem) qm.get(ExaminationItem.class,li.get(0).getExam_item_id());
			}
		}
		return tem;
	}

	@Override
	public void updateExaminationItems_up(ExaminationItem  examinationI) throws ServiceException {
		this.pm.update(examinationI);
//		String f="";
//		if(default_value>0){
//			f=default_value+"";
//		}
//		String sql="";
//		Connection connection = null;
//		try {
//			sql=" update  examination_item  set  default_value='"+f+"'  where   id='"+id+"'  ";
//			connection = this.jqm.getConnection();
//			int rs = connection.createStatement().executeUpdate(sql);
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}finally {
//			try {
//				if (connection != null) {
//					connection.close();
//				}
//			} catch (SQLException sqle4) {
//				sqle4.printStackTrace();
//			}
//		}
	}

	@Override
	public int getExaminationItem_default(long id) throws ServiceException {
		String sql = "SELECT * FROM examination_item  e  where    e.default_value='"+id+"'  and  e.is_Active='Y'   ";
		List li = jqm.getList(sql, ItemResultLibDTO.class);
		return li.size();
	}
}
