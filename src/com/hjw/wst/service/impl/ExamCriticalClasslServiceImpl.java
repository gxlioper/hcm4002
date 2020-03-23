package com.hjw.wst.service.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;

import com.fr.general.DateUtils;
import com.hjw.interfaces.util.DateUtil;
import com.hjw.util.BaseServiceImpl;
import com.hjw.wst.DTO.ChargingItemDTO;
import com.hjw.wst.DTO.ExamCriticalClassDTO;
import com.hjw.wst.DTO.ExamCriticalLogiDTO;
import com.hjw.wst.DTO.ExamCriticalLogicDTO;
import com.hjw.wst.DTO.ExamCriticalLogicItemConditionDTO;
import com.hjw.wst.DTO.ExamCriticalLogicItemDTO;
import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.wst.DTO.UserDTO;
import com.hjw.wst.domain.ExamCriticalClass;
import com.hjw.wst.domain.ExamCriticalLogic;
import com.hjw.wst.domain.ExamCriticalLogicItem;
import com.hjw.wst.domain.ExamCriticalLogicItemCondition;
import com.hjw.wst.model.ExamCriticalClassModel;
import com.hjw.wst.service.ExamCriticalClasslService;
import com.synjones.framework.exception.ServiceException;
import com.synjones.framework.support.PageSupport;

import net.sf.json.JSONArray;

public class ExamCriticalClasslServiceImpl extends BaseServiceImpl implements ExamCriticalClasslService {

	@Override
	public void save(ExamCriticalClass entity) throws ServiceException{
		this.pm.save(entity);
	}

	@Override
	public void update(ExamCriticalClass entity) throws ServiceException{
		ExamCriticalClass ecs = new ExamCriticalClass();
		ecs = (ExamCriticalClass) this.qm.get(ExamCriticalClass.class,entity.getId());
		ecs.setId(entity.getId());
		ecs.setCreate_time(DateUtil.parse());
		ecs.setCreater(entity.getCreater());
		ecs.setCritical_class_level(entity.getCritical_class_level());
		ecs.setCritical_class_name(entity.getCritical_class_name());
		ecs.setSeq_code(entity.getSeq_code());
		ecs.setRemark(entity.getRemark());
		ecs.setParent_id(entity.getParent_id());
	}

	@SuppressWarnings("unlikely-arg-type")
	@Override
	public void remove(ExamCriticalClass entity) throws ServiceException{
		Connection con = null;
		Statement statement = null;
		try {
			StringBuffer sql = new StringBuffer("DELETE FROM  exam_Critical_class where ");
			if(2 == entity.getCritical_class_level()) {
				sql.append(" id = "+entity.getId());
			} else {
				sql.append(" id = "+entity.getId());
				sql.append(" or parent_id = "+entity.getId());
			}
			con = this.jqm.getConnection();
			statement = con.createStatement();
			System.out.println(sql.toString());
			statement.executeUpdate(sql.toString());
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				if(statement!=null) {
					statement.close();
				}
				if(con!=null) {
					con.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public PageReturnDTO queryExamCriticalClass(ExamCriticalClassModel model, int page, int pagesize, UserDTO user)
			throws ServiceException {
		StringBuffer sql = new StringBuffer(" SELECT seq_code,remark,id,critical_class_name,critical_class_level,parent_id,creater,create_time FROM exam_Critical_class");
		sql.append(" WHERE critical_class_level = '1' ");
		if(!StringUtils.isEmpty(model.getCritical_class_name())) {
			sql.append(" and  critical_class_name   like   '%"+model.getCritical_class_name().trim()+"%'   ");
		}
		sql.append(" order  by  seq_code ");
		PageSupport map=jqm.getList(sql.toString(),page,pagesize,ExamCriticalClassDTO.class);
		PageReturnDTO webrole = new PageReturnDTO();
		webrole.setPage(page);
		webrole.setRp(pagesize);
		if ((map != null) && (map.getList() != null)) {
			webrole.setTotal(map.getRecTotal());
			List<ExamCriticalClassDTO> li = map.getList();
			for(int i = 0 ; i < li.size() ;i++) {
				sql = new StringBuffer("SELECT seq_code,remark,'open' as state,id,critical_class_name,critical_class_level,parent_id,creater,create_time FROM exam_Critical_class");
				sql.append(" WHERE critical_class_level = '2' and parent_id="+li.get(i).getId()+"  order  by  seq_code  ");
				List<ExamCriticalClassDTO> crilist = jqm.getList(sql.toString(),ExamCriticalClassDTO.class);
				li.get(i).setChildren(crilist);
			}
			webrole.setRows(li);
		}
		return webrole;
	}
	@SuppressWarnings({ "unchecked", "unlikely-arg-type" })
	public List<ExamCriticalClassDTO> list(ExamCriticalClassModel model, UserDTO user)
			throws ServiceException {
		StringBuffer sql = new StringBuffer(" SELECT 'closed' as state,id,critical_class_name,critical_class_level,parent_id,creater,create_time FROM exam_Critical_class");
		if(1==model.getCritical_class_level()) {
			sql.append(" WHERE critical_class_level = '2' AND parent_id="+model.getId());
		} else {
			sql.append(" WHERE critical_class_level = '1' ");
			if(model.getId()>0) {
				sql.append("  AND id="+model.getId());
			}
		}
		List<ExamCriticalClassDTO> li = this.jqm.getList(sql.toString(), ExamCriticalClassDTO.class);
		return li;
	}
	public ExamCriticalClass getExamCriticalClassById(long id) throws ServiceException {
		return (ExamCriticalClass) this.qm.get(ExamCriticalClass.class,id);
	}

	@Override
	public PageReturnDTO queryCriticalLogic(ExamCriticalClassModel model,int page,int pagesize,UserDTO user) throws ServiceException {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT ecl.sex,CONVERT(varchar,ecl.age_min)+' -- '+CONVERT(varchar,ecl.age_max) as age_min,dil.disease_type,dil.disease_name,ecl.critical_suggestion,ecl.disease_num,ecl.id,ecl.isActive,ecl.critical_class_id,ecl.critical_class_level,ecl.critical_class_parent_id,");
		sql.append("(SELECT ecclass.critical_class_name FROM exam_Critical_class ecclass where  ecclass.id = ecl.critical_class_parent_id and ecclass.critical_class_level=1 ) as critical_class_d_name,");
		sql.append("(SELECT ecclass.critical_class_name FROM exam_Critical_class ecclass where  ecclass.id = ecl.critical_class_id and ecclass.critical_class_level=2 ) as critical_class_z_name,");
		sql.append("(SELECT dd.data_name FROM data_dictionary dd where  data_code='WJZDJ' and  ecl.critical_class_level=dd.id and  dd.isActive='Y' ) as critical_class_level_name ");
		sql.append(" FROM	exam_Critical_logic ecl left join disease_knowloedge_lib dil on dil.disease_num=ecl.disease_num  and  dil.isActive='Y' "
				+ " WHERE  1=1");
		if(model.getParent_id()>0) {
			sql.append(" AND ecl.critical_class_parent_id = "+model.getParent_id());
		}
		if(model.getCritical_class_id()>0) {
			sql.append(" AND ecl.critical_class_id = "+model.getCritical_class_id());
		}
		if(model.getCritical_class_level() > 0 ) {
			sql.append(" AND ecl.critical_class_level = "+model.getCritical_class_level());
		}
		if(!StringUtils.isEmpty(model.getIsActive())) {
			sql.append(" AND ecl.isActive = '"+model.getIsActive()+"' ");
		}
		if(!StringUtils.isEmpty(model.getDisease_type())) {
			sql.append(" AND dil.disease_type='"+model.getDisease_type()+"'");
		}
		if(!StringUtils.isEmpty(model.getDisease_name())) {
			sql.append(" AND dil.disease_name like '%"+model.getDisease_name()+"%'");
		}
		PageSupport map=jqm.getList(sql.toString(),page,pagesize,ExamCriticalLogiDTO.class);
		PageReturnDTO webrole = new PageReturnDTO();
		webrole.setPage(page);
		webrole.setRp(pagesize);
		webrole.setTotal(map.getRecTotal());
			List<ExamCriticalLogiDTO> list = map.getList();
			List<ExamCriticalLogiDTO> lists = new ArrayList<ExamCriticalLogiDTO>();
			StringBuffer info = new StringBuffer();
			StringBuffer info_item = new StringBuffer();
			for( int i = 0 ; i < list.size() ; i++) {
				sql = new StringBuffer();
				info = new StringBuffer();
				sql.append(" SELECT id,logic_item_name FROM exam_Critical_logic_item WHERE logic_id='"+list.get(i).getId()+"'   order  by  logic_index");
				@SuppressWarnings("unchecked")
				List<ExamCriticalLogicItemDTO> logic_item_list = this.jqm.getList(sql.toString(),ExamCriticalLogicItemDTO.class);
				for(int j = 0 ; j < logic_item_list.size() ; j++) {
					info.append(logic_item_list.get(j).getLogic_item_name()+":");
					sql = new StringBuffer();
					sql.append(" SELECT case when eclic.item_num = '' then ci.item_name else ei.item_name end item_name,condition,condition_value FROM exam_Critical_logic_item_condition eclic ");
					sql.append(" left join examination_item ei on ei.item_num=eclic.item_num "
							+" left join charging_item ci on ci.item_code = eclic.charging_item_code "
							+ "where eclic.logic_item_id='"+logic_item_list.get(j).getId()+"' ");
					sql.append(" order by  logic_index");
					List<ExamCriticalLogicItemConditionDTO> exam_Critical_logic_item_condition = this.jqm.getList(sql.toString(), ExamCriticalLogicItemConditionDTO.class);
					info_item = new StringBuffer();
					int f = 0;
					for(ExamCriticalLogicItemConditionDTO ee:exam_Critical_logic_item_condition) {
						f++;
						info_item.append(ee.getItem_name()+ee.getCondition()+ee.getCondition_value());
						if(f!=exam_Critical_logic_item_condition.size()) {
							info_item.append("&nbsp;&nbsp;&nbsp;&nbsp;并且&nbsp;&nbsp;&nbsp;&nbsp;");
						}
					}
					info.append(info_item.toString()+"<br/>");
				}
				list.get(i).setInfo(info.toString());
				lists.add(list.get(i));
			}
			webrole.setRows(lists);
		return webrole;
	}

	@SuppressWarnings("unused")
	@Override
	public String saveExamCriticalLogic(ExamCriticalClassModel model, UserDTO user) throws ServiceException{
		String msg = "";
		String sql = "";
		Connection connection = null;
	    PreparedStatement pstmt = null;
		Statement stmt = null;
		ResultSet res = null;
		try {
				connection = jqm.getConnection();
				connection.setAutoCommit(false);
				ExamCriticalLogicItemCondition ecds = new ExamCriticalLogicItemCondition();
				ExamCriticalLogic eclc = new ExamCriticalLogic();
				ExamCriticalLogicDTO eclcDTO = new ExamCriticalLogicDTO();
				//1.修改危急值逻辑
				//修改主表信息-删除条件-条件值记录
				if(!StringUtils.isEmpty(model.getEcl_id())) {
					String hql = " FROM  ExamCriticalLogic WHERE id='"+model.getEcl_id()+"'";
					List<ExamCriticalLogic> ecllist = this.qm.find(hql);
					if(ecllist!=null && ecllist.size()>0) {
						eclc = ecllist.get(0);
					}
					eclc.setCritical_class_id(model.getCritical_class_id());
					eclc.setCritical_class_level(model.getCritical_class_level());
					eclc.setCritical_class_parent_id(model.getCritical_class_parent_id());
					eclc.setUpdater(user.getUserid());
					eclc.setUpdate_time(DateUtil.parse());
					eclc.setDisease_num(model.getDisease_num());
					eclc.setCritical_suggestion(model.getCritical_suggestion());
					eclc.setAge_max(model.getAge_max());
					eclc.setAge_min(model.getAge_min());
					eclc.setSex(model.getSex());
					this.pm.update(eclc);
					sql = " SELECT id,logic_id,logic_item_name,logic_index FROM exam_Critical_logic_item WHERE  logic_id='"+eclc.getId()+"'";
					List<ExamCriticalLogicItem> examCriticalLogicItem = this.jqm.getList(sql, ExamCriticalLogicItem.class);
					for(ExamCriticalLogicItem exac:examCriticalLogicItem) {
						sql =  "  delete from exam_Critical_logic_item_condition  WHERE logic_item_id =? ";
						pstmt = connection.prepareStatement(sql);
						pstmt.setString(1,exac.getId());
						pstmt.executeUpdate();
						sql = "  delete from exam_Critical_logic_item where  id=?";
						pstmt = connection.prepareStatement(sql);
						pstmt.setString(1,exac.getId());
						pstmt.executeUpdate();
					}
					connection.commit();
					msg = "修改成功";
				} else {
					//2.新增危急值逻辑主表信息
					sql = " INSERT INTO exam_Critical_logic(id,critical_class_parent_id,critical_class_id,critical_class_level,"
							+ "isActive,creater,create_time,disease_num,critical_suggestion,sex,age_min,age_max) "
						+ "	 values(?,?,?,?,?,?,?,?,?,?,?,?)";
					pstmt = connection.prepareStatement(sql);
					String uuidu = UUID.randomUUID().toString().replaceAll("-",""); 
					eclc.setId(uuidu);
					pstmt.setString(1,uuidu);
					pstmt.setLong(2,model.getCritical_class_parent_id());
					pstmt.setLong(3,model.getCritical_class_id());
					pstmt.setLong(4,model.getCritical_class_level());
					pstmt.setString(5,"Y");
					pstmt.setLong(6,user.getUserid());
					Timestamp transdate = null;
					transdate = new java.sql.Timestamp(DateUtil.parse().getTime());
					pstmt.setTimestamp(7,transdate);
					pstmt.setString(8,model.getDisease_num());
					pstmt.setString(9,model.getCritical_suggestion());
					pstmt.setString(10,model.getSex());
					pstmt.setInt(11,model.getAge_min());
					pstmt.setInt(12,model.getAge_max());
					pstmt.execute();
					connection.commit();
					msg = "保存成功";
				}
				//3.保存条件，条件值
				JSONArray jsonArray = JSONArray.fromObject(model.getLi());
				List<ExamCriticalLogicItemDTO> examli = (List<ExamCriticalLogicItemDTO>) JSONArray.toCollection(jsonArray, ExamCriticalLogicItemDTO.class);
				ExamCriticalLogicItem ec = new ExamCriticalLogicItem();
				List<ExamCriticalLogicItemConditionDTO> ecdlistDTO = new ArrayList<ExamCriticalLogicItemConditionDTO>();
				int i = 0;
				int index = 0;
				sql ="  insert into exam_Critical_logic_item_condition(id,logic_item_id,item_num,condition,condition_value,logic_index,charging_item_code) "
					+"	 values(?,?,?,?,?,?,?)   ";
				String insert_item__sql = " insert into  exam_Critical_logic_item(id,logic_id,logic_item_name,logic_index) values(?,?,?,?) ";
				for(ExamCriticalLogicItemDTO ex : examli) {
					index = 0;
					String uuid0 = UUID.randomUUID().toString().replaceAll("-","");  
					pstmt = connection.prepareStatement(insert_item__sql);
					pstmt.setString(1,uuid0);
					pstmt.setString(2,eclc.getId());
					pstmt.setString(3,ex.getLogic_item_name());
					i++;
					pstmt.setLong(4,i);
					//3.1保存条件
					pstmt.execute();
					connection.commit();
					JSONArray conArry = JSONArray.fromObject(ex.getItemConditions());
					ecdlistDTO = (List<ExamCriticalLogicItemConditionDTO>) JSONArray.toCollection(conArry,ExamCriticalLogicItemConditionDTO.class);
					for (ExamCriticalLogicItemConditionDTO eitem : ecdlistDTO) {
						//3.2保存条件值
						String uuid = UUID.randomUUID().toString().replaceAll("-","");  
						pstmt = connection.prepareStatement(sql);
						pstmt.setString(1,uuid);
						pstmt.setString(2, uuid0);
						pstmt.setString(3, eitem.getItem_num());
						pstmt.setString(4, eitem.getCondition());
						pstmt.setString(5, eitem.getCondition_value());
						index++;
						pstmt.setLong(6,index);
						pstmt.setString(7,eitem.getCharging_item_code());
						pstmt.execute();
					}
					connection.commit();
				}
				//4.自动生成规则
//				sql = "  SELECT eclic.item_num,eclic.charging_item_code,ecli.logic_id,ecli.logic_index,eclic.logic_item_id"
//					+ ",eclic.condition,eclic.condition_value"
//					+ " FROM exam_Critical_logic_item ecli,exam_Critical_logic_item_condition eclic "
//					+ " where ecli.id = eclic.logic_item_id  AND "
//					+ "  ecli.logic_id='"+eclc.getId()+"' order by eclic.logic_index";
//				List<ExamCriticalLogicItemConditionDTO> item_cdt_li = this.jqm.getList(sql, ExamCriticalLogicItemConditionDTO.class);
//				for (ExamCriticalLogicItemConditionDTO eitem : item_cdt_li) {
//					eitem.setLogic_index(eitem.getLogic_index());
//					eitem.setLogic_item_id(ec.getId());
//					eitem.setLogic_id(eclc.getId());//主表ID
//					saveAutoExamCriticalLogicItemCondition(eitem,index,connection,pstmt,stmt,res);
//				}
		} catch (Exception e1) {
			e1.printStackTrace();
			try {
				connection.rollback();
				if(res!=null) {
					res.close();
				}
				if(stmt!=null) {
					stmt.close();
				}
				if(pstmt!=null) {
					pstmt.close();
				}
				if(connection!=null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			throw new ServiceException(e1.toString());
		}finally {
			try {
				if(res!=null) {
					res.close();
				}
				if(stmt!=null) {
					stmt.close();
				}
				if(pstmt!=null) {
					pstmt.close();
				}
				if(connection!=null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return msg;
	}
		/**
		 * 自动生成规则
		     * @Title: saveAutoExamCriticalLogicItemCondition   
		     * @Description: TODO(这里用一句话描述这个方法的作用)   
		     * @param: @param excdtion
		     * @param: @param index
		     * @param: @param connection
		     * @param: @param pstmt
		     * @param: @param stmt
		     * @param: @param res
		     * @param: @throws SQLException      
		     * @return: void      
		     * @throws
		 */
//	public void saveAutoExamCriticalLogicItemCondition(ExamCriticalLogicItemConditionDTO excdtion,long index,
//			Connection connection,PreparedStatement pstmt,Statement stmt,ResultSet res
//			) throws Exception {
//		//4.1根据条件值收费项目编码查询  ，收费项目是哪个科室
//		String sql = " SELECT ci.id,ci.dep_category,ci.sam_demo_id,ci.item_code FROM charging_item ci WHERE  ci.item_code = '"+excdtion.getCharging_item_code()+"'";
//		List<ChargingItemDTO> chili = this.jqm.getList(sql, ChargingItemDTO.class);
//		if(chili!=null && chili.size()>0) {
//			ChargingItemDTO ca = chili.get(0);
//			List<ChargingItemDTO> cli = new ArrayList<ChargingItemDTO>();
//			//4.1.1  21影响   131检验  信息
//			if("21".equals(ca.getDep_category()) || "131".equals(ca.getDep_category())) {
//				sql = " SELECT ci.id,ci.item_code,ci.item_name FROM charging_item ci  WHERE  ci.sam_demo_id="+ca.getSam_demo_id()+" "
//					+ " AND ci.dep_category="+ca.getDep_category()+" AND ci.isActive='Y'   and  ci.item_code!='"+ca.getItem_code()+"'";
//				 cli = this.jqm.getList(sql, ChargingItemDTO.class);
//			//4.1.2 普通项目  信息	 
//			} else {
//				sql ="  SELECT  distinct cii.id,cii.item_code FROM charging_item cii,charging_item_exam_item ciei,examination_item  eit" + 
//					 "  where   cii.isActive='Y' AND  cii.isActive='Y' "+
//					 "  AND ciei.exam_item_id=eit.id AND    cii.id = ciei.charging_item_id AND eit.item_num='"+excdtion.getItem_num()+"'";
//				cli = this.jqm.getList(sql, ChargingItemDTO.class);
//			}
//			String sql2 = "";
//			String sql3 = "";
//			String sql4 = "";
//			String insert_item__sql = "";
//			//4.2遍历所有有相同检查项目的收费项目
//			for(ChargingItemDTO li : cli) {
//				//查询检查项目是否在危急值逻辑中存在
//				sql3="	SELECT condition.id FROM exam_Critical_logic ecl  "+
//					 "	left join exam_Critical_logic_item e_item  on ecl.id = e_item.logic_id  and  ecl.isActive='Y' "+
//					 "	left join exam_Critical_logic_item_condition condition on condition.logic_item_id = e_item.id "+
//					 "	where  ecl.id='"+excdtion.getLogic_id()+"' and  condition.charging_item_code='"+li.getItem_code()+ "' and "+
//					 " condition.item_num = '"+excdtion.getItem_num()+"'";
//				stmt = connection.createStatement();
//				res = stmt.executeQuery(sql3);
//				//如果存在停止此次循环，查询下一个条件
//				if(res.next()) {
//					continue;
//				} else {
//						//获取条件个数
//						sql4 = " SELECT id,logic_id,logic_item_name,logic_index FROM exam_Critical_logic_item  where  logic_id='"+excdtion.getLogic_id()+"'  order by logic_index ";
//						List<ExamCriticalLogicItemDTO> lili = this.jqm.getList(sql4,ExamCriticalLogicItemDTO.class);
//						//遍历条件
//						String fal = "N";
//						for(int j = 0 ; j < lili.size() ; j++) {
//							//A.生成规则--检查项目是否在条件中存在--遍历条件
//							sql2="	SELECT condition.id FROM exam_Critical_logic ecl,exam_Critical_logic_item e_item,exam_Critical_logic_item_condition condition   "+
//								 "	WHERE ecl.id = e_item.logic_id  and  ecl.isActive='Y' "+
//								 "	AND condition.logic_item_id = e_item.id "+
//								 "	AND  ecl.id='"+excdtion.getLogic_id()+"'  "+
//								 "	AND  ecl.id='"+excdtion.getLogic_id()+"'  and e_item.logic_index='"+lili.get(j).getLogic_index()+"'"+
//					 		     " and   condition.item_num='"+excdtion.getItem_num()+"'";
//							List<ExamCriticalLogicItemDTO> chekeitem_list = this.jqm.getList(sql2,ExamCriticalLogicItemDTO.class);
//							if(chekeitem_list!=null && chekeitem_list.size() > 0) {
//							} else {
//								//B.生成规则--检查项目-收费项目-是否在条件中存在--遍历条件
//								sql2="	SELECT condition.id FROM exam_Critical_logic ecl,exam_Critical_logic_item e_item,exam_Critical_logic_item_condition condition   "+
//									 "	WHERE ecl.id = e_item.logic_id  and  ecl.isActive='Y' "+
//									 "	AND  ecl.id='"+excdtion.getLogic_id()+"'  and e_item.logic_index='"+lili.get(j).getLogic_index()+"'"+
//						 		     "  and  condition.charging_item_code='"+li.getItem_code()+"' and condition.item_num='"+excdtion.getItem_num()+"'";
//								List<ExamCriticalLogicItemDTO> charging_item_exam_item_list = this.jqm.getList(sql2,ExamCriticalLogicItemDTO.class);
//								if(charging_item_exam_item_list!=null && charging_item_exam_item_list.size() > 0 ) {
//								} else {
//									//A,B同时不存在//在此条件生成规则
//									//条件--下--添加条件值
//									//添加新条件值
//									sql ="  insert into exam_Critical_logic_item_condition(id,logic_item_id,item_num,condition,condition_value,logic_index,charging_item_code) "
//										+"	values(?,?,?,?,?,?,?)   ";
//									String uuid = UUID.randomUUID().toString().replaceAll("-","");  
//									pstmt = connection.prepareStatement(sql);
//									pstmt.setString(1,uuid);
//									pstmt.setString(2, lili.get(j).getId());
//									pstmt.setString(3, excdtion.getItem_num());
//									pstmt.setString(4, excdtion.getCondition());
//									pstmt.setString(5, excdtion.getCondition_value());
//									
//									sql2="	SELECT condition.id FROM exam_Critical_logic ecl,exam_Critical_logic_item e_item,exam_Critical_logic_item_condition condition   "+
//											 "	WHERE ecl.id = e_item.logic_id  and  ecl.isActive='Y' "+
//											 "	AND  ecl.id='"+excdtion.getLogic_id()+"'  and e_item.logic_index='"+lili.get(j).getLogic_index()+"'"+
//							 		         " order by condition.logic_index desc";
//									List<ExamCriticalLogicItemDTO> index_li = this.jqm.getList(sql2,ExamCriticalLogicItemDTO.class);
//									long condition_index = index_li.get(0).getLogic_index();
//									condition_index++;
//									pstmt.setLong(6,condition_index);
//									pstmt.setString(7,li.getItem_code());
//									pstmt.execute();
//									connection.commit();
//								}
//							}
//							//遍历到最后一个条件时，
//							if(j == lili.size()-1) {
//								//相同检查项目的收费项目不存在时，添加新的条件，条件值
//								sql2="	SELECT condition.id FROM exam_Critical_logic ecl,exam_Critical_logic_item e_item,exam_Critical_logic_item_condition condition   "+
//									 "	WHERE ecl.id = e_item.logic_id  and  ecl.isActive='Y' "+
//									 "	AND condition.logic_item_id = e_item.id "+
//									 "	AND  ecl.id='"+excdtion.getLogic_id()+"'  "+
//						 		     " and  condition.charging_item_code='"+li.getItem_code()+"' and condition.item_num='"+excdtion.getItem_num()+"'";
//								List<ExamCriticalLogicItemDTO> charging_item_exam_item_list = this.jqm.getList(sql2,ExamCriticalLogicItemDTO.class);
//								//相同检查项目的收费项目不存在时，添加新的条件，条件值
//								if(charging_item_exam_item_list==null || charging_item_exam_item_list.size()==0) {
//									sql2=" SELECT logic_index  FROM exam_Critical_logic_item where  logic_id='"+excdtion.getLogic_id()+"'  order  by  logic_index desc "; 
//									List<ExamCriticalLogicItemDTO> item_li = this.jqm.getList(sql2, ExamCriticalLogicItemDTO.class);
//									ExamCriticalLogicItemDTO item = item_li.get(0);
//									long i_index = item_li.get(0).getLogic_index();
//									//添加新条件
//									insert_item__sql = " insert into  exam_Critical_logic_item(id,logic_id,logic_item_name,logic_index) values(?,?,?,?) ";
//									String uuid0 = UUID.randomUUID().toString().replaceAll("-","");  
//									pstmt = connection.prepareStatement(insert_item__sql);
//									pstmt.setString(1,uuid0);
//									pstmt.setString(2,excdtion.getLogic_id());
//									i_index++;
//									pstmt.setLong(4,i_index);
//									i_index--;
//									pstmt.setString(3,item.condition_name[(int) i_index]);
//									pstmt.execute();
//									connection.commit();
//									//添加新条件值
//									sql ="  insert into exam_Critical_logic_item_condition(id,logic_item_id,item_num,condition,condition_value,logic_index,charging_item_code) "
//										+"	values(?,?,?,?,?,?,?)   ";
//									String uuid = UUID.randomUUID().toString().replaceAll("-","");  
//									pstmt = connection.prepareStatement(sql);
//									pstmt.setString(1,uuid);
//									pstmt.setString(2,uuid0);
//									pstmt.setString(3, excdtion.getItem_num());
//									pstmt.setString(4, excdtion.getCondition());
//									pstmt.setString(5, excdtion.getCondition_value());
//									//条件值--生成最新排序索引
//									sql2="	SELECT condition.id FROM exam_Critical_logic ecl,exam_Critical_logic_item e_item,exam_Critical_logic_item_condition condition   "+
//											 "	WHERE ecl.id = e_item.logic_id  and  ecl.isActive='Y' "+
//											 "	AND  ecl.id='"+excdtion.getLogic_id()+"'  and e_item.logic_index='"+lili.get(j).getLogic_index()+"'"+
//							 		         " order by condition.logic_index desc";
//									List<ExamCriticalLogicItemDTO> index_li = this.jqm.getList(sql2,ExamCriticalLogicItemDTO.class);
//									long condition_index = index_li.get(0).getLogic_index();
//									condition_index++;
//									pstmt.setLong(6,condition_index);
//									pstmt.setString(7,li.getItem_code());
//									pstmt.execute();
//									connection.commit();
//								}
//							}
//						}
//				}
//			}
//		}
//	}

	@Override
	public void removeExamCriticalLogic(ExamCriticalClassModel model) throws ServiceException {
		StringBuffer hql = new StringBuffer("FROM ExamCriticalLogic WHERE id in("+model.getIds()+")");
		List<ExamCriticalLogic> zli = this.qm.find(hql.toString());
		for(ExamCriticalLogic zlist:zli) {
			pm.remove(zlist);
			hql = new StringBuffer(" FROM ExamCriticalLogicItem WHERE logic_id='"+zlist.getId()+"'");
			List<ExamCriticalLogicItem> ecli = qm.find(hql.toString());
			for(ExamCriticalLogicItem eli : ecli) {
				pm.remove(eli);
				hql = new StringBuffer(" FROM ExamCriticalLogicItemCondition WHERE logic_item_id='"+eli.getId()+"'");
				List<ExamCriticalLogicItemCondition> ecili = qm.find(hql.toString());
				for(ExamCriticalLogicItemCondition elist:ecili) {
					pm.remove(elist);
				}
			}
		}
	}

	@Override
	public void updateExamCriticalLogicIsAcive(ExamCriticalClassModel model, UserDTO user) throws ServiceException {
		String hql = "FROM ExamCriticalLogic WHERE id = '"+model.getEcl_id()+"'";
		ExamCriticalLogic exa = new ExamCriticalLogic();
		List<ExamCriticalLogic> exali = qm.find(hql);
		if(exali!=null && exali.size() > 0 ) {
			exa = exali.get(0);
		}
		exa.setUpdater(user.getUserid());
		exa.setUpdate_time(DateUtil.parse());
		if("Y".equals(exa.getIsActive())) {
			exa.setIsActive("N");
		} else {
			exa.setIsActive("Y");
		}
		pm.update(exa);
	}

	@Override
	public List<ExamCriticalLogicItemDTO> getExamCriticalLogicItemList(ExamCriticalClassModel model)
			throws ServiceException {
		StringBuffer sql = new StringBuffer("SELECT id,logic_item_name FROM exam_Critical_logic_item WHERE  logic_id='"+model.getEcl_id()+"' order by logic_index");
		List<ExamCriticalLogicItemDTO> tli = jqm.getList(sql.toString(), ExamCriticalLogicItemDTO.class);
		List<ExamCriticalLogicItemDTO> condition = new ArrayList<ExamCriticalLogicItemDTO>();
		for(ExamCriticalLogicItemDTO tf : tli) {
			sql = new StringBuffer(" SELECT ci.item_name as charging_item_name,c.charging_item_code,e.item_name,c.item_num,c.condition_value,c.condition FROM exam_Critical_logic_item_condition  c");
			sql.append(" LEFT JOIN charging_item ci ON ci.item_code = c.charging_item_code and ci.isActive='Y' ");
			sql.append(" LEFT JOIN examination_item e ON e.item_num = c.item_num and e.is_Active='Y' ");
			sql.append("  WHERE  c.logic_item_id = '"+tf.getId()+"' order by c.logic_index");
			List<ExamCriticalLogicItemConditionDTO> gzli = jqm.getList(sql.toString(), ExamCriticalLogicItemConditionDTO.class);
			tf.setItemConditions(gzli);
			condition.add(tf);
		}
		return condition;
	}

	@Override
	public ExamCriticalLogic getExamCriticalLogic(ExamCriticalClassModel model) throws ServiceException {
		List<ExamCriticalLogic> li = this.qm.find("FROM ExamCriticalLogic WHERE id='"+model.getEcl_id()+"'");
		if(li!=null && li.size()>0) {
			return li.get(0);
		} else {
			return null;
		}
	}

}









