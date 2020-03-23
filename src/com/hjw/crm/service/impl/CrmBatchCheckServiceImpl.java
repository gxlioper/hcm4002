package com.hjw.crm.service.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.hjw.crm.DTO.CrmSalesCostTrDTO;
import com.hjw.crm.DTO.CrmSignBillPlanDTO;
import com.hjw.crm.domain.CrmBatchCheck;
import com.hjw.crm.service.CrmBatchCheckService;
import com.hjw.util.StringUtil;
import com.hjw.wst.DTO.BatchDTO;
import com.hjw.wst.DTO.ContractDTO;
import com.hjw.wst.DTO.Examinatioin_center_dept;
import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.wst.domain.Batch;
import com.hjw.wst.domain.BatchProPlan;
import com.hjw.wst.domain.Contract;
import com.hjw.wst.domain.GroupChargingItem;
import com.hjw.wst.domain.GroupInfo;
import com.hjw.wst.domain.GroupSet;
import com.hjw.wst.model.BatchModel;
import com.hjw.zyb.DTO.ZybEconomicclassDTO;
import com.synjones.framework.exception.ServiceException;
import com.synjones.framework.persistence.JdbcPersistenceManager;
import com.synjones.framework.persistence.JdbcQueryManager;
import com.synjones.framework.persistence.PersistenceManager;
import com.synjones.framework.persistence.QueryManager;
import com.synjones.framework.support.PageSupport;

public class CrmBatchCheckServiceImpl implements CrmBatchCheckService{
	private QueryManager queryManager;
	private JdbcQueryManager jdbcQueryManager;
	private JdbcPersistenceManager jdbcPersistenceManager;
	private PersistenceManager persistenceManager;
	
	public QueryManager getQueryManager() {
		return queryManager;
	}
	public void setQueryManager(QueryManager queryManager) {
		this.queryManager = queryManager;
	}
	public JdbcQueryManager getJdbcQueryManager() {
		return jdbcQueryManager;
	}
	public void setJdbcQueryManager(JdbcQueryManager jdbcQueryManager) {
		this.jdbcQueryManager = jdbcQueryManager;
	}
	public JdbcPersistenceManager getJdbcPersistenceManager() {
		return jdbcPersistenceManager;
	}
	public void setJdbcPersistenceManager(JdbcPersistenceManager jdbcPersistenceManager) {
		this.jdbcPersistenceManager = jdbcPersistenceManager;
	}
	public PersistenceManager getPersistenceManager() {
		return persistenceManager;
	}
	public void setPersistenceManager(PersistenceManager persistenceManager) {
		this.persistenceManager = persistenceManager;
	}
	@Override
	public CrmBatchCheck getCrmBatchCheck(String batch_num, String check_type) throws ServiceException {
		String sql="From CrmBatchCheck c where c.batch_id='"+batch_num+"' and c.check_type='"+check_type+"'";
		List list=queryManager.find(sql);
		if(list.size()>0){
			return (CrmBatchCheck) list.get(0);
		}else{
			return null;
		}
	}
	@Override
	public void addCrmBatchCheck(CrmBatchCheck crmBatchCheck) throws ServiceException {
		persistenceManager.save(crmBatchCheck);
	}
	public BatchDTO getbatchForcomidandname(long id,String batchname,String sign_num,String center_num) throws ServiceException {
		String sql = "select a.id,a.company_id,a.batch_num,a.batch_name,a.pay_way,"
				+ "a.creater,a.create_time,a.updater,a.update_time,a.is_Active,"
				+ "a.exam_item,a.exam_number,a.exam_date,a.exam_date_end,a.charge_type,"
				+ "a.contact_name,a.sales_name,a.introducer_name,a.accommodation,"
				+ "a.dine,a.exam_fee,a.remark,a.phone,a.invoice_title,a.batch_address,"
				+ "a.qian_remark,a.settlement,a.checktype,a.checkuser,a.checkdate,a.checknotice"
				+ " From  batch a  where a.is_Active!='N'  and a.id="+id
				+ " and a.center_num='"+center_num+"' and a.batch_name='"+batchname.trim()+"' order by a.update_time desc";
		System.out.println(sql);
		PageSupport map = this.jdbcQueryManager.getList(sql, 1, 1000, BatchDTO.class);
		BatchDTO webrole = new BatchDTO();
		
		if((map!=null)&&(map.getList()!=null)&&(map.getList().size()>0)){
			webrole = (BatchDTO)map.getList().get(0);
		}

		return webrole;
	}
	@Override


	public List<CrmBatchCheck> getCrmBatchCheck(String batch_id) throws ServiceException {
		String sql="From CrmBatchCheck c where c.batch_id='"+batch_id+"' ";
		List list=queryManager.find(sql);
		if(list!=null&&list.size()>0){
			return list;
		}else{
			return null;
		}
	}
	@Override
	public void deleteCrmBatchCheck(long batch_id) throws ServiceException {
		String sql="";
		Connection connection = null;
		try {
		    connection = this.jdbcQueryManager.getConnection();
			sql ="delete crm_batch_check where batch_id = '"+batch_id+"' ";
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
	public List<BatchDTO> getBatch(String sign_num,String center_num) throws ServiceException, SQLException {
		String sql=" select id,batch_name from batch where sign_num='"+sign_num+"' and center_num='"+center_num+"' and is_Active = 'T' ";
		Connection connection = null;
		Statement statement = null;
		List<BatchDTO> list = new ArrayList<BatchDTO>();
		try {
			// 读取记录数
			connection = this.jdbcQueryManager.getConnection();
			statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			System.out.println(sql);
			while (rs.next()) {
				BatchDTO jd = new BatchDTO();
				jd.setId(Long.valueOf(rs.getString("id")));
				jd.setBatch_name(rs.getString("batch_name"));
				list.add(jd);
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
	public PageReturnDTO crmcontractlistshow(long sign_num,long batchid,int pagesize, int pageno,String center_num) throws ServiceException {
		StringBuffer sql = new StringBuffer("select a.id,a.company_id,a.company_name,a.batch_id,a.batch_name,"
				+ "a.types,a.contract_num,a.validity_date,a.creater,a.create_time,a.updater,a.update_time,"
				+ "a.remark,a.linkman,a.tel,a.checknotice,a.checkuser,a.checkdate,b.overflag ");
		sql.append(" From  contract a,batch b where b.id=a.batch_id");
		sql.append(" and  b.sign_num="+sign_num+" and b.center_num='"+center_num+"'");
		sql.append(" and  a.batch_id="+batchid+" ");
		sql.append(" order by a.update_time desc");
		System.out.println(sql);
		PageSupport map = this.jdbcQueryManager.getList(sql.toString(), pageno, pagesize, ContractDTO.class);
		PageReturnDTO webrole = new PageReturnDTO();
		webrole.setPage(pageno);
		webrole.setRp(pagesize);
		webrole.setTotal(map.getRecTotal());
		webrole.setRows(map.getList());
		return webrole;
	}

	public String getSign_name(String sign_num) throws ServiceException {
		String sql="select d.sign_name from crm_sign_bill_plan d where d.sign_num='"+sign_num+"'";
		Connection connection = null;
		Statement statement = null;
		List<String> list = new ArrayList<String>();
		String flag="0";
		try {
			// 读取记录数
			connection = this.jdbcQueryManager.getConnection();
			statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			System.out.println(sql);
			while (rs.next()) {
				String str=new String();
				str=rs.getString(1);
				list.add(str);
				flag="1";
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
		if(flag=="1"){
			return list.get(0);
		}else{
			return null;
		}
	}
	@Override
	public List<CrmSignBillPlanDTO> getSignBillPlan(String sign_name,long creater, int pagesize, int pageno,String center_num) throws ServiceException, SQLException {
		String sql = " select  c.id as id,c.sign_name,p.com_name,c.sign_num,c.create_time,d.chi_name as name,'1' as flag from crm_sign_bill_plan c left join company_info p on c.company_id=p.id "
				+ " left join user_usr d on c.creater=d.id "
				+ " where sign_name like '%"+sign_name+"%' and c.creater='"+creater+"'" ;
		System.out.println(sql);
		List<CrmSignBillPlanDTO> li =this.jdbcQueryManager.getList(sql, CrmSignBillPlanDTO.class);
		for(CrmSignBillPlanDTO bill :li){
			String sql1= " select b.id as id,b.batch_name as sign_name,c.com_name as com_name,b.batch_num as sign_num  ,b.create_time as create_time,"
					+ " d.chi_name as name,'2' as flag from batch b left join company_info c on b.company_id=c.id "
					+ " left join user_usr d on c.creater=d.id "
					+ " where sign_num='"+bill.getSign_num()+"' and b.center_num='"+center_num+"' ";
			System.out.println(sql1);
			List<BatchDTO> list1 = this.jdbcQueryManager.getList(sql1, BatchDTO.class);
			bill.setChildren(list1);
		}
		
		return li;
	}
	@Override
	public String getSign_num(String id) throws ServiceException, SQLException {
		String sql="select d.sign_num from batch d where d.id='"+id+"'";
		Connection connection = null;
		Statement statement = null;
		List<String> list = new ArrayList<String>();
		String flag="0";
		try {
			// 读取记录数
			connection = this.jdbcQueryManager.getConnection();
			statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			System.out.println(sql);
			while (rs.next()) {
				String str=new String();
				str=rs.getString(1);
				list.add(str);
				flag="1";
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
		if(flag=="1"){
			return list.get(0);
		}else{
			return null;
		}
	}
	@Override
	public Batch getBatchById(String id) throws ServiceException, SQLException {
		List<Batch> list=this.queryManager.find("from Batch where id='"+id+"'");
		if(list!=null&&list.size()>0){
			return list.get(0);
		}else{
			return null;
		}
	}
	@Override
	public List<GroupInfo> getGroupInfoByBatchId(String batch_id) throws ServiceException, SQLException {
		String sql="From GroupInfo c where c.batch_id='"+batch_id+"' ";
		List list=queryManager.find(sql);
		if(list.size()>0){
			return list;
		}else{
			return null;
		}
	}
	@Override
	public void addGroupInfo(GroupInfo groupInfo) throws ServiceException {
		persistenceManager.save(groupInfo);
	}
	@Override
	public List<GroupSet> getGroupSetByGroupId(String group_id) throws ServiceException, SQLException {
		String sql="From GroupSet c where c.group_id='"+group_id+"' ";
		List list=queryManager.find(sql);
		if(list.size()>0){
			return list;
		}else{
			return null;
		}
	}
	@Override
	public void addGroupSet(GroupSet groupSet) throws ServiceException {
		persistenceManager.save(groupSet);
	}
	@Override
	public List<BatchProPlan> getBatchProPlanByBatchId(String batch_id) throws ServiceException, SQLException {
		String sql="From BatchProPlan c where c.batch_id='"+batch_id+"' ";
		List list=queryManager.find(sql);
		if(list.size()>0){
			return list;
		}else{
			return null;
		}
	}
	@Override
	public void addBatchProPlan(BatchProPlan batchProPlan) throws ServiceException {
		persistenceManager.save(batchProPlan);
	}
	@Override
	public List<GroupChargingItem> getGroupChargingItemByBatchId(String batch_id) throws ServiceException {
		String sql="From GroupChargingItem c where c.group_id='"+batch_id+"' ";
		List list=queryManager.find(sql);
		if(list.size()>0){
			return list;
		}else{
			return null;
		}
	}
	@Override
	public void addGroupChargingItem(GroupChargingItem groupChargingItem) throws ServiceException {
		persistenceManager.save(groupChargingItem);
	}
	@Override
	public String tijiaoShenhe(String id) throws ServiceException {
		String sql="";
		Connection connection = null;
		try {
		    connection = this.jdbcQueryManager.getConnection();
			sql ="update batch set is_Active='T' where id='"+id+"'";
			int rs = connection.createStatement().executeUpdate(sql);
			return "1";
		} catch (SQLException e) {
			e.printStackTrace();
			return "0";
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
	public String quxiaoShenhe(String id) throws ServiceException {
		String sql="";
		Connection connection = null;
		try {
		    connection = this.jdbcQueryManager.getConnection();
			sql ="update batch set is_Active='C' where id='"+id+"'";
			int rs = connection.createStatement().executeUpdate(sql);
			return "1";
		} catch (SQLException e) {
			e.printStackTrace();
			return "0";
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
	public List<Batch> getBatchByShenhe(String sign_num,String apptype,String center_num) throws ServiceException {
		String sql="From Batch c where c.sign_num='"+sign_num+"' and c.is_Active='T' and c.center_num='"+center_num+"' and apptype='"+apptype+"'";
		List list=queryManager.find(sql);
		if(list.size()>0){
			return list;
		}else{
			return null;
		}
	}
	
	

	@Override
	public CrmBatchCheck getcrmBatchCheck(long batch_id, String checktype) throws ServiceException {
		List<CrmBatchCheck> list = this.queryManager.find("from CrmBatchCheck c where c.batch_id = "+batch_id +" and c.check_type ='"+checktype+"'");
		if(list.size() != 0){
			return list.get(0);
		}
		return null;
	}
	@Override
	public CrmBatchCheck updateBatchCheck(CrmBatchCheck crmBatchCheck) throws ServiceException {
		this.persistenceManager.update(crmBatchCheck);
		return crmBatchCheck;
	}
	@Override
	public PageReturnDTO getcrmBatchCheckManageList(BatchModel model, int pagesize, int pageno,String center_num) throws ServiceException {
		String sql = "select a.id,a.company_id,a.batch_num,a.batch_name,a.pay_way,"
				+ "a.creater,a.create_time,a.updater,a.update_time,a.is_Active,"
				+ "a.exam_item,a.exam_number,a.exam_date,a.exam_date_end,a.charge_type,"
				+ "a.contact_name,a.sales_name,a.introducer_name,a.accommodation,"
				+ "a.dine,a.exam_fee,a.remark,a.phone,a.invoice_title,a.batch_address,"
				+ "a.qian_remark,a.settlement,b.data_name,a.checktype,a.checkuser,"
				+ "a.checkdate,a.checknotice,a.sign_num,c.sign_name as com_name,a.apptype"
				+ " From  batch a  left join data_dictionary b on a.pay_way=b.id "
				+ " left join crm_sign_bill_plan c on c.sign_num=a.sign_num "
				+ " left join crm_batch_check cb on a.id = cb.batch_id"
				+ " where a.is_Active in('T','Y') and a.center_num='"+center_num+"' cb.check_type = "+model.getCheck_type();
		if(model.getSign_num() != null && !"".equals(model.getSign_num())){
			sql += " and c.sign_num='"+model.getSign_num()+"'";
		}
		if(model.getCheck_status() != null && !"".equals(model.getCheck_status())){
			sql += " and cb.check_status = " + model.getCheck_status();
		}
		sql+= "  order by a.update_time desc";
		PageSupport map = this.jdbcQueryManager.getList(sql, pageno, pagesize, BatchDTO.class);
		List<BatchDTO> list = map.getList();
		List<BatchDTO> temp = new ArrayList<BatchDTO>();
		for(BatchDTO batch : list){
			List<CrmBatchCheck> checklist = this.jdbcQueryManager.getList("select c.check_status,c.check_type from crm_batch_check c where c.batch_id =" +batch.getId(), CrmBatchCheck.class);
			for(CrmBatchCheck check :checklist) {
				if("1".equals(check.getCheck_type())){
					batch.setYscheckstatus(check.getCheck_status()+"");
				}else if("2".equals(check.getCheck_type())){
					batch.setCwcheckstatus(check.getCheck_status()+"");
				}else if("3".equals(check.getCheck_type())){
					batch.setSjcheckstatus(check.getCheck_status()+"");
				}
			}
			temp.add(batch);
		}
		PageReturnDTO webrole = new PageReturnDTO();
		webrole.setPage(pageno);
		webrole.setRp(pagesize);
		webrole.setTotal(map.getRecTotal());
		webrole.setRows(temp);

		return webrole;
	}
	@Override
	public PageReturnDTO getcrmBatchContract(BatchModel model, int pagesize, int pageno,String center_num) throws ServiceException {
		StringBuffer sql = new StringBuffer("select a.id,a.company_id,a.company_name,a.batch_id,a.batch_name,"
				+ "a.types,a.contract_num,a.validity_date,a.creater,a.create_time,a.updater,a.update_time,"
				+ "a.remark,a.linkman,a.tel,a.checknotice,a.checkuser,a.checkdate,b.overflag ");
		sql.append(" From  contract a,batch b where b.id=a.batch_id and b.center_num='"+center_num+"'");
		
		if(model.getCheck_type() != null && !"".equals(model.getCheck_type())){
			sql.append(" and a.types = " + model.getCheck_type());
		}
		if(model.getSign_num() != null && !"".equals(model.getSign_num())){
			sql.append(" and b.sign_num = '"+model.getSign_num()+"'");
		}
		sql.append(" order by a.update_time desc");
		
		PageSupport map = this.jdbcQueryManager.getList(sql.toString(), pageno, pagesize, ContractDTO.class);
		PageReturnDTO webrole = new PageReturnDTO();
		webrole.setPage(pageno);
		webrole.setRp(pagesize);
		webrole.setTotal(map.getRecTotal());
		webrole.setRows(map.getList());
		return webrole;
	}
	@Override
	public List<BatchDTO> getBatchCrm(String sign_num,String center_num) throws ServiceException, SQLException {
		String sql=" select id,batch_name from batch where center_num='"+center_num+"' and  sign_num='"+sign_num+"' and is_Active != 'N' ";
		Connection connection = null;
		Statement statement = null;
		List<BatchDTO> list = new ArrayList<BatchDTO>();
		try {
			// 读取记录数
			connection = this.jdbcQueryManager.getConnection();
			statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			System.out.println(sql);
			while (rs.next()) {
				BatchDTO jd = new BatchDTO();
				jd.setId(Long.valueOf(rs.getString("id")));
				jd.setBatch_name(rs.getString("batch_name"));
				list.add(jd);
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
	public List<Contract> getContractByBatchId(String batch_id) throws ServiceException {
		String sql=" select batch_id,types from contract where batch_id='"+batch_id+"' ";
		Connection connection = null;
		Statement statement = null;
		List<Contract> list = new ArrayList<Contract>();
		try {
			// 读取记录数
			connection = this.jdbcQueryManager.getConnection();
			statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			System.out.println(sql);
			while (rs.next()) {
				Contract jd = new Contract();
				jd.setBatch_id(Long.valueOf(rs.getString("batch_id")));
				jd.setTypes(Integer.valueOf(rs.getString("types")));
				list.add(jd);
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
}
