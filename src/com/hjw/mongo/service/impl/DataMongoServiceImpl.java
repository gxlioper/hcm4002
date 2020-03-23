package com.hjw.mongo.service.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import org.bson.Document;

import com.fr.third.org.hsqldb.lib.StringUtil;
import com.hjw.mongo.MongoConnect;
import com.hjw.mongo.DTO.DataMongoConditionDTO;
import com.hjw.mongo.DTO.DataMongoDTO;
import com.hjw.mongo.DTO.SQLColumDTO;
import com.hjw.mongo.DTO.SearchScientificFactor;
import com.hjw.mongo.model.DataMongoModel;
import com.hjw.mongo.service.DataMongoService;
import com.hjw.wst.DTO.ChargingItemDTO;
import com.hjw.wst.DTO.ExaminationItemDTO;
import com.hjw.wst.DTO.PageDTO;
import com.hjw.wst.DTO.TreeDTO;
import com.hjw.wst.DTO.UserDTO;
import com.hjw.wst.service.CustomerInfoService;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.QueryOperators;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.synjones.framework.exception.ServiceException;
import com.synjones.framework.persistence.JdbcQueryManager;
import com.ymhjw.Util.DateTimeUtil;

public class DataMongoServiceImpl implements DataMongoService {

	private JdbcQueryManager jqm;
	private MongoDatabase mongoDatabase=null;
	private Connection connection=null;
	private CustomerInfoService customerInfoService;
	private MongoConnect mongoConnect;
	
	public void setMongoConnect(MongoConnect mongoConnect) {
		this.mongoConnect = mongoConnect;
	}


	public void setCustomerInfoService(CustomerInfoService customerInfoService) {
		this.customerInfoService = customerInfoService;
	}


	public void setJdbcQueryManager(JdbcQueryManager jqm) {
		this.jqm = jqm;
	}
	

	public Connection getConnection(){
        try {
            String url ="jdbc:sqlserver://localhost:1433;DatabaseName=peis_dbgj_190510";
            String userName = "sa";
            String password = "HUOjianwa010";
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            connection = DriverManager.getConnection(url,userName,password);
            return connection;
        }catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                 e.printStackTrace();
             }
        return null;
    }
	

	@SuppressWarnings("resource")
	public MongoDatabase getMongoConnection(){
        	try{   
   	         // 连接到 mongodb 服务
   	         MongoClient mongoClient = new MongoClient(mongoConnect.getIp(),mongoConnect.getPort());
   	         // 连接到数据库
   	         mongoDatabase = mongoClient.getDatabase(mongoConnect.getDatabaseName());  
            return mongoDatabase;
        }catch (Exception e) {
                 e.printStackTrace();
        }
        return null;
    }
	
	/**
	 * 获取查询条件
	     * @Title: getResearchDataconditions   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param model
	     * @param: @param user
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<SearchScientificFactor>      
	     * @throws
	 */
	public SearchScientificFactor getResearchDataconditionsId(long id) throws ServiceException{
		Connection connection = null;
		SearchScientificFactor s=new SearchScientificFactor();
		try {
			connection = this.jqm.getConnection();
			String sql="select title,creater,create_time,remark,conditions,conditions1,center_num from search_scientific_factor"
					+ " where id='"+id+"'";
			ResultSet rs = connection.createStatement().executeQuery(sql);
			if (rs.next()) {
				s.setCenter_num(rs.getString("center_num"));
				s.setConditions(rs.getString("conditions"));
				s.setConditions1(rs.getString("conditions1"));
				s.setCreate_time(rs.getString("create_time"));
				s.setCreater(rs.getLong("creater"));
				s.setRemark(rs.getString("remark"));
				s.setTitle(rs.getString("title"));
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return s;
	}
	
	/**
	 * 获取查询条件
	     * @Title: getResearchDataconditions   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param model
	     * @param: @param user
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<SearchScientificFactor>      
	     * @throws
	 */
	public List<SearchScientificFactor> getResearchDataconditions(UserDTO user) throws ServiceException{		
		Connection connection = null;
		List<SearchScientificFactor> list=new ArrayList<SearchScientificFactor>();
		try {
			connection = this.jqm.getConnection();
			String sql="select id,title,creater,create_time,remark,conditions,conditions1,center_num from search_scientific_factor"
					+ " where creater='"+user.getUserid()+"' and center_num='"+user.getCenter_num()+"' order by create_time desc";
			ResultSet rs = connection.createStatement().executeQuery(sql);
			while (rs.next()) {
				SearchScientificFactor s = new SearchScientificFactor();
				s.setCenter_num(rs.getString("center_num"));
				s.setConditions(rs.getString("conditions"));
				s.setConditions1(rs.getString("conditions1"));
				s.setCreate_time(rs.getString("create_time"));
				s.setCreater(rs.getLong("creater"));
				s.setRemark(rs.getString("remark"));
				s.setTitle(rs.getString("title"));
				s.setId(rs.getLong("id"));
				list.add(s);
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return list;
	}
	
	public String getResearchDatadel(long id) throws ServiceException{
		Connection connection = null;
		String itemcode="error";
		try {
			connection = this.jqm.getConnection();
			String sql="delete from search_scientific_factor where id= '"+id+"'";
			connection.createStatement().executeUpdate(sql);
			itemcode="ok";
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return itemcode;
	}
	
	/**
	 * 保存查询条件
	     * <p>Title: getResearchDatasave</p>   
	     * <p>Description: </p>   
	     * @param model
	     * @param user
	     * @return
	     * @throws ServiceException   
	     * @see com.hjw.mongo.service.DataMongoService#getResearchDatasave(com.hjw.mongo.model.DataMongoModel, com.hjw.wst.DTO.UserDTO)
	 */
	public String getResearchDatasave(DataMongoModel model,UserDTO user) throws ServiceException{		
		Connection connection = null;
		String itemcode="error";
		try {
			connection = this.jqm.getConnection();
			String sql="insert into search_scientific_factor (title,creater,create_time,remark,conditions,conditions1,center_num)values('"+model.getSearchType()+"_"+DateTimeUtil.getDateTime().substring(0,10)+"','"
				      +user.getUserid()+"','"+DateTimeUtil.getDateTime()+"','','"+model.getConditions()+"','"+model.getConditions1()+"','"+user.getCenter_num()+"')";
			connection.createStatement().executeUpdate(sql);
			itemcode="ok";
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return itemcode;
	}

	public DataMongoDTO getResearchDataList(DataMongoModel model, UserDTO user, int pageSize, int pageNum, String sort,
			String order,String searchtype) throws Exception {
		PageDTO pageDTO = new PageDTO();
		pageDTO.setPageSize(pageSize);
		pageDTO.setPageNum(pageNum);
		pageDTO.setPageCount(0);
		
		mongoDatabase = getMongoConnection();
		String centernum="001";

		BasicDBObject query = new BasicDBObject();
		query.put("center_num", centernum);

		if (model.getExam_num() != null && !"".equals(model.getExam_num())) {
			query.put("exam_num", model.getExam_num());
		}
		if (model.getUser_name() != null && !"".equals(model.getUser_name())) {
			query.put("user_name", model.getUser_name());
		}
		if ((model.getTime1() != null && !"".equals(model.getTime1()))
				&& (model.getTime2() != null && !"".equals(model.getTime2()))) {
			String startDate = model.getTime1() + " 00:00:00";
			String endDate = model.getTime2() + " 23:59:59";
			DBObject dbObject = new BasicDBObject();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			dbObject.put("$gte", sdf.parse(startDate));
			dbObject.put("$lte",  sdf.parse(endDate));
			query.put("join_date",dbObject);
			//query.put("join_date", new BasicDBObject("$gte", "ISODate("+startDate+")").append("$lte","ISODate("+endDate+")"));
			//query.put("join_date","{$gte:ISODate("+startDate+"),$lte:ISODate("+endDate+")}");
		}

		if (model.getMin_age() >= 0) {
			query.put("age", new BasicDBObject("$gte", (int)model.getMin_age()).append("$lte",(int)model.getMax_age()));
			
			//query.put("age","{$gte:"+model.getMin_age()+",$lte:"+model.getMax_age()+"}");
		}

		if (model.getExam_type() != null && !"".equals(model.getExam_type())) {
			query.put("exam_type", model.getExam_type());
		}
		if (model.getSex() != null && !"".equals(model.getSex())) {
			query.put("sex", model.getSex());
		}
		if (model.getCompany_id() > 0) {
			long[] inList = getCompantForID(model.getCompany_id());
			query.put("com_id", new BasicDBObject(QueryOperators.IN,inList));
		}

		for (int i = 0; i < model.getCondition().size(); i++) {
			DataMongoConditionDTO condition = model.getCondition().get(i);	
			
			if(condition.getDcondition().equals("and")){  //大条件 包含
				
				if (condition.getCondition().equals("in")) {// 小条件 包含的关系处理，
				String result = condition.getResult();
				if (condition.getDep_category().equals("21")) {// 影像按照收费项目查询
					String chargingnum = chargingItemForCode(condition.getCharging_id()).getCode();
					if (chargingnum.length() > 0) {
						Pattern pattern = Pattern.compile(condition.getResult(), Pattern.CASE_INSENSITIVE);
						query.put(chargingnum + "_R", pattern);
					}
				} else {// 其他按照检查项目查询					
					String itemnum = examinfoItemForCode(condition.getItem_id()).getCode();
					if (result.split(",").length > 1) {
						try {
							query.put(itemnum + "_F", new BasicDBObject("$gte", Double.valueOf(result.split(",")[0]).doubleValue()).append("$lte",Double.valueOf(result.split(",")[1]).doubleValue()));
						} catch (Exception ex) {
							Pattern pattern = Pattern.compile(condition.getResult(), Pattern.CASE_INSENSITIVE);
							query.put(itemnum + "_R", pattern);

						}
					} else if (result.split("，").length > 1) {
						try {
							query.put(itemnum + "_F", new BasicDBObject("$gte", Double.valueOf(result.split("，")[0]).doubleValue()).append("$lte",Double.valueOf(result.split("，")[1]).doubleValue()));
						} catch (Exception ex) {
							Pattern pattern = Pattern.compile(condition.getResult(), Pattern.CASE_INSENSITIVE);
							query.put(itemnum + "_R", pattern);
						}
					} else {
						try {
							query.put(itemnum + "_D", result);
						} catch (Exception ex) {
							Pattern pattern = Pattern.compile(condition.getResult(), Pattern.CASE_INSENSITIVE);
							query.put(itemnum + "_R", pattern);
						}
					}
				}
			}
		}
		}

		List<DBObject> list2 = new ArrayList<DBObject>();
		for (int i = 0; i < model.getCondition().size(); i++) {
			DataMongoConditionDTO condition = model.getCondition().get(i);
			if(condition.getDcondition().equals("and")){  //大条件 包含
			if (condition.getCondition().equals("not in")) {// 不包含关系处理
				String result = condition.getResult();
				if (condition.getDep_category().equals("21")) {// 影像按照收费项目查询
					String chargingnum = chargingItemForCode(condition.getCharging_id()).getCode();
					if (chargingnum.length() > 0) {
						String[] inList = {condition.getResult()};
						BasicDBObject nin = new BasicDBObject("$nin",inList);
						query.put(chargingnum + "_R", nin);
					}
				} else {// 其他按照检查项目查询
					String itemnum = examinfoItemForCode(condition.getItem_id()).getCode();
					if (result.split(",").length > 1) {
						try {
							BasicDBObject q000 = new BasicDBObject(itemnum + "_F",
									new BasicDBObject("$lt", Double.valueOf(result.split(",")[0]).doubleValue()));
							BasicDBObject q001 = new BasicDBObject(itemnum + "_F",
									new BasicDBObject("$gt", Double.valueOf(result.split(",")[1]).doubleValue()));
							List<DBObject> list00 = new ArrayList<DBObject>();
							list00.add(q000);
							list00.add(q001);
							BasicDBObject q00 = new BasicDBObject();
							q00.put("$or", list00);
							list2.add(q00);
						} catch (Exception ex) {
							String[] inList = {condition.getResult()};
							BasicDBObject nin = new BasicDBObject("$nin",inList);
							query.put(itemnum + "_R", nin);
						}
					} else if (result.split("，").length > 0) {
						try {
							BasicDBObject q000 = new BasicDBObject(itemnum + "_F",
									new BasicDBObject("$lt", Double.valueOf(result.split("，")[0]).doubleValue()));
							BasicDBObject q001 = new BasicDBObject(itemnum + "_F",
									new BasicDBObject("$gt", Double.valueOf(result.split("，")[1]).doubleValue()));
							List<DBObject> list00 = new ArrayList<DBObject>();
							list00.add(q000);
							list00.add(q001);
							BasicDBObject q00 = new BasicDBObject();
							q00.put("$or", list00);
							list2.add(q00);
						} catch (Exception ex) {
							String[] inList = {condition.getResult()};
							BasicDBObject nin = new BasicDBObject("$nin",inList);
							query.put(itemnum + "_R", nin);
						}
					} else {
						try {
							query.put(itemnum + "_F", result);
						} catch (Exception ex) {
							String[] inList = {condition.getResult()};
							BasicDBObject nin = new BasicDBObject("$nin",inList);
							query.put(itemnum + "_R", nin);

						}
					}
				}
			}
		}
		}
		if (list2 != null && list2.size() > 0) {
			query.put("$and", list2);
		}
		
		//--------------------------------------------------------------
		List<DBObject> listor = new ArrayList<DBObject>();
		
		boolean flags=false;
		List<DBObject> list3 = new ArrayList<DBObject>();
		for (int i = 0; i < model.getCondition().size(); i++) {
			DataMongoConditionDTO condition = model.getCondition().get(i);	
			
			if(condition.getDcondition().equals("or")){ //大条件 包含				
				if (condition.getCondition().equals("in")) {// 小条件 包含的关系处理，
				String result = condition.getResult();
				if (condition.getDep_category().equals("21")) {// 影像按照收费项目查询
					String chargingnum = chargingItemForCode(condition.getCharging_id()).getCode();
					if (chargingnum.length() > 0) {
						Pattern pattern = Pattern.compile(condition.getResult(), Pattern.CASE_INSENSITIVE);
						BasicDBObject q000 = new BasicDBObject(chargingnum + "_R",pattern);
						list3.add(q000);
						flags=true;
					}
				} else {// 其他按照检查项目查询					
					String itemnum = examinfoItemForCode(condition.getItem_id()).getCode();
					if (result.split(",").length > 1) {
						try {
							BasicDBObject q000 = new BasicDBObject(itemnum + "_F", new BasicDBObject("$gte", Double.valueOf(result.split(",")[0]).doubleValue()).append("$lte",Double.valueOf(result.split(",")[1]).doubleValue()));
							list3.add(q000);
							flags=true;
						} catch (Exception ex) {
							Pattern pattern = Pattern.compile(condition.getResult(), Pattern.CASE_INSENSITIVE);
							BasicDBObject q000 = new BasicDBObject(itemnum + "_R",pattern);
							list3.add(q000);
							flags=true;
						}
					} else if (result.split("，").length > 1) {
						try {
							BasicDBObject q000 = new BasicDBObject(itemnum + "_F", new BasicDBObject("$gte", Double.valueOf(result.split("，")[0]).doubleValue()).append("$lte",Double.valueOf(result.split("，")[1]).doubleValue()));
							list3.add(q000);
							flags=true;
						} catch (Exception ex) {
							Pattern pattern = Pattern.compile(condition.getResult(), Pattern.CASE_INSENSITIVE);
							BasicDBObject q000 = new BasicDBObject(itemnum + "_R",pattern);
							list3.add(q000);
							flags=true;
						}
					} else {
						try {
							BasicDBObject q000 = new BasicDBObject(itemnum + "_D", result);
							list3.add(q000);
							flags=true;
						} catch (Exception ex) {
							flags=true;
							Pattern pattern = Pattern.compile(condition.getResult(), Pattern.CASE_INSENSITIVE);
							BasicDBObject q000 = new BasicDBObject(itemnum + "_R",pattern);
							list3.add(q000);
						}
					}
				}
			}
			
		}
		}
		if(list3!=null && list3.size()>0){
			BasicDBObject q = new BasicDBObject();
			q.put("$and", list3);
			listor.add(q);
		}
		
		//List<DBObject> list4 = new ArrayList<DBObject>();
		for (int i = 0; i < model.getCondition().size(); i++) {
			DataMongoConditionDTO condition = model.getCondition().get(i);
			if(condition.getDcondition().equals("or")){ //大条件 包含
			if (condition.getCondition().equals("not in")) {// 不包含关系处理
				String result = condition.getResult();
				if (condition.getDep_category().equals("21")) {// 影像按照收费项目查询
					String chargingnum = chargingItemForCode(condition.getCharging_id()).getCode();
					if (chargingnum.length() > 0) {
						String[] inList = {condition.getResult()};
						BasicDBObject nin = new BasicDBObject("$nin",inList);
						BasicDBObject q000 = new BasicDBObject(chargingnum + "_R",nin);
						List<DBObject> list00 = new ArrayList<DBObject>();
						list00.add(q000);
						BasicDBObject q00 = new BasicDBObject();
						q00.put("$or", list00);
						listor.add(q00);
					}
				} else {// 其他按照检查项目查询
					String itemnum = examinfoItemForCode(condition.getItem_id()).getCode();
					if (result.split(",").length > 1) {
						try {
							BasicDBObject q000 = new BasicDBObject(itemnum + "_F",
									new BasicDBObject("$lt", Double.valueOf(result.split(",")[0]).doubleValue()));
							BasicDBObject q001 = new BasicDBObject(itemnum + "_F",
									new BasicDBObject("$gt", Double.valueOf(result.split(",")[1]).doubleValue()));
							List<DBObject> list00 = new ArrayList<DBObject>();
							list00.add(q000);
							list00.add(q001);		
							BasicDBObject q00 = new BasicDBObject();
							q00.put("$or", list00);
							listor.add(q00);							
						} catch (Exception ex) {
							String[] inList = {condition.getResult()};
							BasicDBObject nin = new BasicDBObject("$nin",inList);
							BasicDBObject q000 = new BasicDBObject(itemnum + "_R",nin);
							BasicDBObject q00 = new BasicDBObject();
							List<DBObject> list00 = new ArrayList<DBObject>();
							list00.add(q000);
							q00.put("$or", list00);
							listor.add(q00);
							//flags=true;
						}
					} else if (result.split("，").length > 0) {
						try {
							BasicDBObject q000 = new BasicDBObject(itemnum + "_F",
									new BasicDBObject("$lt", Double.valueOf(result.split("，")[0]).doubleValue()));
							BasicDBObject q001 = new BasicDBObject(itemnum + "_F",
									new BasicDBObject("$gt", Double.valueOf(result.split("，")[1]).doubleValue()));
							List<DBObject> list00 = new ArrayList<DBObject>();
							list00.add(q000);
							list00.add(q001);
							BasicDBObject q00 = new BasicDBObject();
							q00.put("$or", list00);
							listor.add(q00);
							//flags=true;
						} catch (Exception ex) {
							String[] inList = {condition.getResult()};
							BasicDBObject nin = new BasicDBObject("$nin",inList);
							BasicDBObject q000 = new BasicDBObject(itemnum + "_R",nin);
							BasicDBObject q00 = new BasicDBObject();
							List<DBObject> list00 = new ArrayList<DBObject>();
							list00.add(q000);
							q00.put("$or", list00);
							listor.add(q00);
							//flags=true;
						}
					} else {
						try {
							BasicDBObject q000 = new BasicDBObject(itemnum + "_F",result);
							BasicDBObject q00 = new BasicDBObject();
							List<DBObject> list00 = new ArrayList<DBObject>();
							list00.add(q000);
							q00.put("$or", list00);
							listor.add(q00);
							//flags=true;
						} catch (Exception ex) {
							String[] inList = {condition.getResult()};
							BasicDBObject nin = new BasicDBObject("$nin",inList);
							BasicDBObject q000 = new BasicDBObject(itemnum + "_R",nin);
							List<DBObject> list00 = new ArrayList<DBObject>();
							list00.add(q000);
							BasicDBObject q00 = new BasicDBObject();
							q00.put("$or", list00);
							listor.add(q00);
							//flags=true;
						}
					}
				}
			}
			}			
		}
		
		/*if(list4!=null && list4.size()>0){
			BasicDBObject q = new BasicDBObject();
			q.append("$or", list4);
			listor.add(q);
		}*/
		
		if (listor!=null&&listor.size()>0) {
			query.put("$or", listor);
		}
		
		DataMongoDTO data= new DataMongoDTO();
		MongoCollection<Document> collection= null;
		if("zyb".equals(model.getSearchType())){
			collection = mongoDatabase.getCollection("exam_info_zyb");
		}else{
			collection = mongoDatabase.getCollection("exam_info");
		}
		long counts=1000;
		MongoCursor<Document> cursor= null;
		if (!"ALL".equals(searchtype)) {
			counts = collection.count(query);
			pageDTO.setPageTotal((int) counts);
			cursor = collection.find(query).skip((pageNum - 1) * pageSize).limit(pageSize).iterator();//
		}else{
			 cursor = collection.find(query).iterator();//
		}
		List<String> objects = new ArrayList<String>();
		while (cursor.hasNext()) {
			Document document = cursor.next();
			try {
				objects.add(document.toJson().toString());
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		pageDTO.setPageCount(((int)counts + pageSize - 1) / pageSize);
		
		if (pageDTO.getPageCount() == 0) {
			pageDTO.setFirstPage(false);
			pageDTO.setProPage(false);
			pageDTO.setTailPage(false);
			pageDTO.setNextPage(false);
		} else {
			if (pageNum == 1) {
				pageDTO.setFirstPage(false);
				pageDTO.setProPage(false);
			} else {
				pageDTO.setFirstPage(true);
				pageDTO.setProPage(true);
			}
			if (pageNum == pageDTO.getPageCount()) {
				pageDTO.setTailPage(false);
				pageDTO.setNextPage(false);
			} else {
				pageDTO.setTailPage(true);
				pageDTO.setNextPage(true);
			}
		}
		data.setPageDTO(pageDTO);
		data.setDatas(objects);
		cursor.close();
		return data;
	}
	
	private Date getDatetime(String strdate){
		try{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.parse(strdate);
		}catch(Exception ex){
			return null;
		}
	}
	
	/**
	 * 获取收费项目编码
	     * @Title: chargingItemForCode   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param chargingid
	     * @param: @return      
	     * @return: String      
	     * @throws
	 */
	public SQLColumDTO chargingItemForCode(long chargingid){
		Connection connection = null;
		SQLColumDTO item= new SQLColumDTO();
		try {
			connection = getConnection();//this.jqm.getConnection();
			StringBuilder sqlstr = new StringBuilder();
			sqlstr.append("select ci.id,ci.item_code,ci.item_name from charging_item ci where ci.id='"+chargingid+"' and ci.item_category<>'耗材类型'");
			ResultSet rs = connection.createStatement().executeQuery(sqlstr.toString());
			if (rs.next()) {
				item.setCode(rs.getString("item_code"));
				item.setId(rs.getLong("id"));
				item.setName(rs.getString("item_name"));
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return item;
	}
	
	/**
	 * 获取检查项目编码
	     * @Title: examinfoItemForCode   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param itemid
	     * @param: @return      
	     * @return: String      
	     * @throws
	 */
	public SQLColumDTO examinfoItemForCode(long itemid){
		Connection connection = null;
		SQLColumDTO item=new SQLColumDTO();
		try {
			connection = getConnection();//this.jqm.getConnection();
			StringBuilder sqlstr = new StringBuilder();
			sqlstr.append("select ei.id,ei.item_name,ei.item_num from examination_item ei where ei.id='"+itemid+"'");
			ResultSet rs = connection.createStatement().executeQuery(sqlstr.toString());
			if (rs.next()) {
				item.setCode(rs.getString("item_num"));
				item.setId(rs.getLong("id"));
				item.setName(rs.getString("item_name"));
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return item;
	}

	@Override
	public List<ChargingItemDTO> getChargingItemListByq(String q,long add_i) throws ServiceException {
		if(q == null){
			q="";
		}else{
			q = q.replaceAll("'", "");
		}
		String sql = "select c.id,c.item_name,"+add_i+" as item_seq,c.dep_category,c.sam_demo_id,c.item_code from charging_item c,department_dep d where c.dep_id = d.id "
				+ "and c.item_category<>'耗材类型' and (c.item_name like '"+q+"%' or c.item_pinyin like '"+q+"%') order by d.seq_code,c.item_seq";
		Connection connection = null;
		Statement statement = null;
		List<ChargingItemDTO> list= new ArrayList<ChargingItemDTO>();
		try {
			// 读取记录数
			connection = getConnection();//this.jqm.getConnection();
			statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			while (rs.next()) {
				ChargingItemDTO c= new ChargingItemDTO();
				c.setId(rs.getLong("id"));
				c.setItem_name(rs.getString("item_name"));
				c.setItem_seq(rs.getLong("item_seq"));
				c.setSam_demo_id(rs.getLong("sam_demo_id"));
				c.setDep_category(rs.getString("dep_category"));
				c.setItem_code(rs.getString("item_code"));
				list.add(c);
			}
			rs.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			try {
				if (statement != null) {
					statement.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException sqle4) {
				sqle4.printStackTrace();
			}
		}
		return list;
	}

	@Override
	public List<ExaminationItemDTO> getExaminationItemListByq(long charging_id,String q,long add_i) throws ServiceException {
		if(q == null){
			q="";
		}else{
			q = q.replaceAll("'", "");
		}
		String sql = "select e.id,e.item_name,e.item_category,"+add_i+" as seq_code from examination_item e,charging_item_exam_item ec where e.id = ec.exam_item_id and e.is_Active = 'Y' "
				+ "and ec.charging_item_id = '"+charging_id+"' and (e.item_name like '"+q+"%' or e.item_pinyin like '"+q+"%') order by e.seq_code";
		
		Connection connection = null;
		Statement statement = null;
		List<ExaminationItemDTO> list= new ArrayList<ExaminationItemDTO>();
		try {
			// 读取记录数
			connection = getConnection();//this.jqm.getConnection();
			statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			while (rs.next()) {
				ExaminationItemDTO e= new ExaminationItemDTO();
				e.setId(rs.getLong("id"));
				e.setItem_name(rs.getString("item_name"));
				e.setItem_category(rs.getString("item_category"));
				e.setSeq_code(rs.getLong("seq_code"));
				list.add(e);
			}
			rs.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			try {
				if (statement != null) {
					statement.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException sqle4) {
				sqle4.printStackTrace();
			}
		}
		return list;
	}
	
	private long[] getCompantForID(long id){
		
				String sql = "select id from company_info where id='"+id+"' or parent_com_id='"+id+"' and is_active = 'Y' ";
				
				Connection connection = null;
				Statement statement = null;
				List<Long> strs= new ArrayList<Long>();
				try {
					// 读取记录数
					connection = getConnection();//this.jqm.getConnection();
					statement = connection.createStatement();
					ResultSet rs = statement.executeQuery(sql);
					while (rs.next()) {
						strs.add(rs.getLong("id"));						
					}
					rs.close();
				} catch (SQLException ex) {
					ex.printStackTrace();
				} finally {
					try {
						if (statement != null) {
							statement.close();
						}
						if (connection != null) {
							connection.close();
						}
					} catch (SQLException sqle4) {
						sqle4.printStackTrace();
					}
				}
				
				long[] strlist= new long[strs.size()];
				for(int i=0;i<strs.size();i++){
					strlist[i]=strs.get(i);
				}
				return strlist;
			}
	
	public List<TreeDTO> getCompanyForNameIsactive(String name,UserDTO user)throws ServiceException{
		StringBuffer sqltext= new StringBuffer();
		String IS_SEARCHTOTAL_TYPE="1";
		try{
			IS_SEARCHTOTAL_TYPE = this.customerInfoService.getCenterconfigByKey("IS_SEARCHTOTAL_TYPE", null).getConfig_value().trim();
		}catch(Exception ex){}
		if("1".equals(IS_SEARCHTOTAL_TYPE)){
			if (StringUtil.isEmpty(name)) {
				sqltext.append(
						"select top 200  c.id,c.parent_com_id,c.com_name,c.com_num,c.create_time  from  company_info c "
								+ " where c.is_active = 'Y' ");
			} else {
				sqltext.append(
						"select top 200  c.id,c.parent_com_id,c.com_name,c.com_num,c.create_time  from  company_info c"
								+ " where c.is_active = 'Y'  ");
				sqltext.append(" and (c.com_name like '%" + name + "%' or c.name_pinyin like '%" + name
						+ "%' or c.com_num like '%" + name + "%')");
			}

			sqltext.append(" order by c.create_time desc "); // 创建时间倒叙
		
		} else {
			if (StringUtil.isEmpty(name)) {
				sqltext.append(
						"select top 200  c.id,c.parent_com_id,c.com_name,c.com_num,c.create_time  from  company_info c,company_info_vs_center vs"
								+ " where c.is_active = 'Y' and  vs.company_id = c.id AND vs.center_num='"
								+ user.getCenter_num() + "'");
			} else {
				sqltext.append(
						"select top 200  c.id,c.parent_com_id,c.com_name,c.com_num,c.create_time  from  company_info c,company_info_vs_center vs"
								+ " where c.is_active = 'Y'  and  vs.company_id = c.id AND vs.center_num='"
								+ user.getCenter_num() + "'   ");
				sqltext.append(" and (c.com_name like '%" + name + "%' or c.name_pinyin like '%" + name
						+ "%' or c.com_num like '%" + name + "%')");
			}

			sqltext.append(" order by c.create_time desc "); // 创建时间倒叙
		}
		Connection connection = null;
		Statement statement = null;
		List<TreeDTO> list = new ArrayList<>();
		List<TreeDTO> list2 = new ArrayList<>();
		try {
			// 读取记录数
			connection = getConnection();//this.jqm.getConnection();
			statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(sqltext.toString());
			while (rs.next()) {
				TreeDTO jd =new TreeDTO();
				jd.setId(rs.getLong("id")+"");
				jd.setText("【"+rs.getString("com_num")+"】"+rs.getString("com_name"));
				jd.setAttributes(rs.getLong("parent_com_id")+"");
				list.add(jd);
			 	String sql="";
			 	if("1".equals(IS_SEARCHTOTAL_TYPE)){
			 		if("".equals(jd.getAttributes()) ||jd.getAttributes() == null || "0".equals(jd.getAttributes())){//是父级单位
						 sql = "select  c.id,c.parent_com_id,c.com_name,c.com_num,c.create_time  from  company_info c "
					 		+ " where c.is_active = 'Y'   and parent_com_id = '"+jd.getId()+"' order by c.create_time desc";
					}else{//子单位
						 sql = "select  c.id,c.parent_com_id,c.com_name,c.com_num,c.create_time  from  company_info c "
						 		+ " where c.is_active = 'Y'  and id = '"+jd.getAttributes()+"' order by c.create_time desc";
					}
			 	}else{
				if("".equals(jd.getAttributes()) ||jd.getAttributes() == null || "0".equals(jd.getAttributes())){//是父级单位
					 sql = "select  c.id,c.parent_com_id,c.com_name,c.com_num,c.create_time  from  company_info c,company_info_vs_center vs "
				 		+ " where vs.company_id = c.id and c.is_active = 'Y' and vs.center_num='"+user.getCenter_num()+"'  and parent_com_id = '"+jd.getId()+"' order by c.create_time desc";
				}else{//子单位
					 sql = "select  c.id,c.parent_com_id,c.com_name,c.com_num,c.create_time  from  company_info c,company_info_vs_center vs "
					 		+ " where vs.company_id = c.id  and vs.center_num='"+user.getCenter_num()+"' and c.is_active = 'Y'  and id = '"+jd.getAttributes()+"' order by c.create_time desc";
				}
			 	}
				Connection connection1 = null;
				Statement statement1 = null;
				try {
					// 读取记录数
					connection1 = getConnection();//this.jqm.getConnection();
					statement1 = connection1.createStatement();
					ResultSet rs1 = statement1.executeQuery(sql);
					while (rs1.next()) {
						TreeDTO jdd =new TreeDTO();
						jdd.setId(rs1.getLong("id")+"");
						jdd.setText("【"+rs1.getString("com_num")+"】"+rs1.getString("com_name"));
						jdd.setAttributes(rs1.getLong("parent_com_id")+"");
						list2.add(jdd);
					}
					rs1.close();
				} catch (SQLException ex) {
					ex.printStackTrace();
				} finally {
					try {
						if (statement1 != null) {
							statement1.close();
						}
						if (connection1 != null) {
							connection1.close();
						}
					} catch (SQLException sqle4) {
						sqle4.printStackTrace();
					}
				}
			}
			rs.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			try {
				if (statement != null) {
					statement.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException sqle4) {
				sqle4.printStackTrace();
			}
		}
		List<TreeDTO>  li = new ArrayList<>();
		for (TreeDTO treeDTO : list2) {
			if(!list.contains(treeDTO)){
				list.add(treeDTO);
			}
		}
		for (TreeDTO treeDTO : list) {
			li.add(treeDTO);
		}
		return li;
	}
	
}
