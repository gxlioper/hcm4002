package com.hjw.wst.service.impl;

import com.hjw.crm.domain.CrmBatchCheck;
import com.hjw.util.DateTimeUtil;
import com.hjw.util.Timeutils;
import com.hjw.wst.DTO.BatchDTO;
import com.hjw.wst.DTO.BatchProPlanDTO;
import com.hjw.wst.DTO.ChargingItemDTO;
import com.hjw.wst.DTO.ContractDTO;
import com.hjw.wst.DTO.ExamInfoDTO;
import com.hjw.wst.DTO.ExamSetDTO;
import com.hjw.wst.DTO.FangAnShowDTO;
import com.hjw.wst.DTO.GroupChargingItemDTO;
import com.hjw.wst.DTO.GroupInfoDTO;
import com.hjw.wst.DTO.GroupSetDTO;
import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.wst.DTO.TreeDTO;
import com.hjw.wst.DTO.UserDTO;
import com.hjw.wst.config.GetNumContral;
import com.hjw.wst.domain.Batch;
import com.hjw.wst.domain.BatchFlowLog;
import com.hjw.wst.domain.BatchProPlan;
import com.hjw.wst.domain.ChargingItem;
import com.hjw.wst.domain.CompanyInfo;
import com.hjw.wst.domain.Contract;
import com.hjw.wst.domain.ExamSet;
import com.hjw.wst.domain.GroupChargingItem;
import com.hjw.wst.domain.GroupInfo;
import com.hjw.wst.domain.GroupSet;
import com.hjw.wst.model.BatchModel;
import com.hjw.wst.service.BatchService;
import com.hjw.wst.service.CompanyService;
import com.hjw.wst.service.CustomerInfoService;
import com.synjones.framework.aop.SystemType;
import com.synjones.framework.exception.ServiceException;
import com.synjones.framework.persistence.JdbcPersistenceManager;
import com.synjones.framework.persistence.JdbcQueryManager;
import com.synjones.framework.persistence.PersistenceManager;
import com.synjones.framework.persistence.QueryManager;
import com.synjones.framework.support.PageSupport;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.hsqldb.lib.StringUtil;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * 
 * @Title: 火箭蛙体检管理系统
 * @Package com.hjw.wst.service.impl
 * @Description: 体检单位管理
 * @author: yangm
 * @date: 2016年7月4日 上午9:37:08
 * @version V2.0.0.0
 */
public class BatchServiceImpl implements BatchService {
	private QueryManager qm;
	private JdbcQueryManager jqm;
	private JdbcPersistenceManager jpm;
	private PersistenceManager pm;
    private CustomerInfoService customerInfoService;	
    private CompanyService companyService;
	
	public void setCompanyService(CompanyService companyService) {
		this.companyService = companyService;
	}
	
	public void setCustomerInfoService(CustomerInfoService customerInfoService) {
		this.customerInfoService = customerInfoService;
	}

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

	/**
	 * 
	 * /*sys_param表中的param_id的值(exam_no:体检号；barcode：条码号；vipno：档案号; lis_order_no:lis用117);
        pacs:117PACS序号; studyid: 117医院pacs库studyid; code:字典表编码; arch_num_rz：省公务员入职体检档案号
        contract:合同编号
	 */
	public String GetCreateID(String types, String center_num) {
		return GetNumContral.getInstance().getParamNum(types, center_num);
	}

	/**
	 * 
	 * @Title: getGroupList @Description: 得到分组对应套餐 @param: @param
	 *         group_id @param: @param pagesize @param: @param
	 *         pageno @param: @return @param: @throws ServiceException @return:
	 *         PageReturnDTO @throws
	 */
	public PageReturnDTO getGroupSetList(long group_id, int pagesize, int pageno,String center_num) throws ServiceException {
		String sql = "select a.id,a.set_num,a.set_name,a.set_pinyin,a.sex,"
				+ "a.set_discount,a.set_amount,b.id,b.group_id,b.exam_set_id,"
				+ "b.discount,b.amount,b.isActive,b.final_exam_date,b.creater,"
				+ "b.create_time,b.updater,b.update_time"
				+ " From  exam_set a,group_set b where a.is_Active='Y' and b.isActive='Y' "
				+ " and a.id=b.exam_set_id and a.center_num ='"+center_num+"' and b.group_id=" + group_id
				+ " and a.id=b.exam_set_id order by a.set_seq,b.update_time desc";
		PageSupport map = this.jqm.getList(sql, pageno, pagesize, GroupSetDTO.class);
		PageReturnDTO webrole = new PageReturnDTO();
		webrole.setPage(pageno);
		webrole.setRp(pagesize);
		webrole.setTotal(map.getRecTotal());
		webrole.setRows(map.getList());
		return webrole;
	}

	
	/**
	 * 
	 * <p>
	 * Title: getGroupList
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param batch_id
	 * @param pagesize
	 * @param pageno
	 * @return
	 * @throws ServiceException
	 * @see com.hjw.wst.service.BatchService#getGroupList(long, int, int)
	 */
	public PageReturnDTO getGroupList(long batch_id, int pagesize, int pageno) throws ServiceException {
		String sql = "select a.id,a.batch_id,a.group_num,a.group_name,a.start_date,a.end_date,a.sex,a.min_age,a.max_age,a.is_Marriage,"
				+ "a.posttion,a.discount,a.amount,a.group_index,a.isActive,a.cust_type_id,"
				+ "a.creater,a.create_time,a.updater,a.update_time,a.person_team_amount,"
				+ "a.group_settlement_type,a.exam_indicator,a.maximum_amount,a.status  "
				+ " ,(select count(id) from exam_info e where e.group_id = a.id and e.is_Active = 'Y') exam_count,"
				+ "(select count(id) from exam_info e where e.group_id = a.id and e.is_Active = 'Y' and e.status = 'Y') yexam_count"
				+ " ,(select sum(gc.item_amount) from group_charging_item gc where gc.group_id = a.id and gc.isActive = 'Y') as old_amount "
				+ " From  group_info a"
				+ " where a.batch_id=" + batch_id + " AND a.isActive = 'Y' order by a.group_order, a.update_time desc";
		PageSupport map = this.jqm.getList(sql, pageno, pagesize, GroupInfoDTO.class);
		PageReturnDTO webrole = new PageReturnDTO();
		webrole.setPage(pageno);
		webrole.setRp(pagesize);
		webrole.setTotal(map.getRecTotal());
		List<GroupInfoDTO> gilist= new ArrayList<GroupInfoDTO>();
		for(int i=0;i<map.getList().size();i++){
			GroupInfoDTO gi= new GroupInfoDTO();
			gi=(GroupInfoDTO)map.getList().get(i);
			if(gi.getCust_type_id()!=null){
				gi.setType_name(this.customerInfoService.getcustomerTypeForName(gi.getCust_type_id()));
			}
			gilist.add(gi);
		}
		webrole.setRows(gilist);
		return webrole;
	}
	
	/**
	 * 
	     * @Title: getGroupForbatidandgroupname   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param batch_id
	     * @param: @param groupname
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: GroupInfoDTO      
	     * @throws
	 */
	public GroupInfoDTO getGroupForbatidandgroupname(long batch_id,String groupname) throws ServiceException {
		String sql = "select a.id,a.batch_id,a.group_num,a.group_name," 
	            + "a.start_date,a.end_date,a.sex,a.min_age,a.max_age,a.is_Marriage,"
				+ "a.posttion,a.discount,a.amount,a.group_index,a.isActive,a.cust_type_id,"
				+ "a.creater,a.create_time,a.updater,a.update_time,a.person_team_amount," 
				+ "a.group_settlement_type,a.exam_indicator"
				+ " From  group_info a"
				+ " where a.batch_id=" + batch_id + " and a.group_name='"+groupname+"' and a.isActive='Y' order by a.update_time desc";
		PageSupport map = this.jqm.getList(sql, 1, 100000, GroupInfoDTO.class);
		GroupInfoDTO gi=new GroupInfoDTO();
		if((map!=null)&&(map.getList()!=null)&&(map.getList().size()>0)){
			gi=(GroupInfoDTO)map.getList().get(0);
			if(gi.getCust_type_id()!=null){
			   gi.setType_name(this.customerInfoService.getcustomerTypeForName(gi.getCust_type_id()));
			}
		}
		return gi;
	}
	
	/**
	 * 
	     * @Title: getGroupListForBatchId   
	     * @Description: 方案获取分组信息  
	     * @param: @param batch_id
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<GroupInfoDTO>      
	     * @throws
	 */
	@SuppressWarnings("unchecked")
	public List<GroupInfoDTO> getGroupListForBatchId(long batch_id) throws ServiceException {
		String sql = "select a.id,a.batch_id,a.group_num,a.group_name," 
	            + "a.start_date,a.end_date,a.sex,a.min_age,a.max_age,a.is_Marriage,"
				+ "a.posttion,a.discount,a.amount,a.group_index,a.isActive,a.cust_type_id,"
				+ "a.creater,a.create_time,a.updater,a.update_time,a.person_team_amount," 
				+ "a.group_settlement_type,a.exam_indicator"
				+ " From  group_info a"
				+ " where a.batch_id=" + batch_id + " and a.isActive='Y' and a.status='Y' order by a.update_time desc";
		PageSupport map = this.jqm.getList(sql, 1, 1000, GroupInfoDTO.class);
		List<GroupInfoDTO> webrole = new ArrayList<GroupInfoDTO>();
		if(((map!=null))&&(map.getList()!=null)){
			for(int i=0;i<map.getList().size();i++){
				GroupInfoDTO gi= new GroupInfoDTO();
				gi=(GroupInfoDTO)map.getList().get(i);
				if(gi.getCust_type_id()!=null){
					gi.setType_name(this.customerInfoService.getcustomerTypeForName(gi.getCust_type_id()));
				}
				webrole.add(gi);
			}
		}
		return webrole;
	}

	/**
	 * 
	 * @Title: getGroupList @Description: 得到套餐 @param: @param
	 *         batch_id @param: @param pagesize @param: @param
	 *         pageno @param: @return @param: @throws ServiceException @return:
	 *         PageReturnDTO @throws
	 */
	public List<TreeDTO> getSetList(String setname,String sex,String appType,String center_num) throws ServiceException {
		StringBuffer sql = new StringBuffer("select id,company_id,set_num,set_name,set_pinyin,sex,set_discount,"
				+ "set_amount,survey_minScore,survey_maxScore,disease_name,is_Active,exam_set.set_seq,"
				+ "creater,create_time,updater,update_time" + " From  exam_set where is_Active='Y' and center_num='"+center_num+"' and app_Type='"+appType+"' ");
		if (!StringUtil.isEmpty(sex)) {
			sql.append(" and (sex ='"+sex+"' or sex='全部' ) ");
		}
		
		if (!StringUtil.isEmpty(setname)) {
			sql.append(" and (set_name like '%" + setname + "%'  or set_pinyin  like '" + setname + "%') ");
		}
		sql.append(" order by set_seq,update_time desc");
		System.out.println(sql.toString());
		Connection connection = null;
		Statement statement = null;
		List<TreeDTO> list = new ArrayList<TreeDTO>();
		try {
			// 读取记录数
			connection = this.jqm.getConnection();
			statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(sql.toString());
			while (rs.next()) {
				TreeDTO jd = new TreeDTO();
				jd.setId(rs.getString("id"));
				jd.setText(rs.getString("set_num") + "-" + rs.getString("set_name")+"("+rs.getDouble("set_amount")+")");
				jd.setAttributes("0");
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

	/**
	 * 
	 * @Title: getbatchList @Description: 得到方案列表 @param: @param
	 *         company_Id @param: @param pagesize @param: @param
	 *         pageno @param: @return @param: @throws ServiceException @return:
	 *         PageReturnDTO @throws
	 */
	public PageReturnDTO getbatchList(long company_Id,String apptype, int pagesize, int pageno,String center_num ) throws ServiceException {
		String sql = "select a.id,a.company_id,a.batch_num,a.batch_name,a.pay_way,"
				+ "a.creater,a.create_time,a.updater,a.update_time,a.is_Active,"
				+ "a.exam_item,a.exam_number,a.exam_date,a.exam_date_end,a.charge_type,"
				+ "a.contact_name,a.sales_name,a.introducer_name,a.accommodation,"
				+ "a.dine,a.exam_fee,a.remark,a.phone,a.invoice_title,a.batch_address,a.is_showamount,a.is_showseal,"
				+ "a.qian_remark,a.settlement,b.data_name,a.checktype,a.accountflag,a.overflag,"
				+ " checku.chi_name as checkuname,a.checkdate,accountu.chi_name as accountuname,"
				+ " a.accountdate, createku.chi_name as creaters,a.overdate,overtu.chi_name as overflager "
				+ " From  batch a  "
				+ " left join data_dictionary b on a.pay_way=b.id "
				+ " left join user_usr createku on createku.id=a.creater "
				+ " left join user_usr checku on checku.id=a.checkuser "
				+ " left join user_usr accountu on accountu.id=a.accountcreater "
				+ " left join user_usr overtu on overtu.id=a.overdate "
				+ "where a.is_Active='Y' and a.apptype= '"+apptype+"' and a.center_num='"+center_num+"' and a.company_id=" + company_Id
				+ "  order by a.update_time desc";
		System.out.println(sql);
		PageSupport map = this.jqm.getList(sql, pageno, pagesize, BatchDTO.class);
		PageReturnDTO webrole = new PageReturnDTO();
		webrole.setPage(pageno);
		webrole.setRp(pagesize);
		webrole.setTotal(map.getRecTotal());
		webrole.setRows(map.getList());

		return webrole;
	}
	
	public PageReturnDTO getbatchListSignNum(String sign_num,String apptype, int pagesize, int pageno,String center_num) throws ServiceException {
		String sql = "select a.id,a.company_id,a.batch_num,a.batch_name,a.pay_way,"
				+ "a.creater,a.create_time,a.updater,a.update_time,a.is_Active,"
				+ "a.exam_item,a.exam_number,a.exam_date,a.exam_date_end,a.charge_type,"
				+ "a.contact_name,a.sales_name,a.introducer_name,a.accommodation,"
				+ "a.dine,a.exam_fee,a.remark,a.phone,a.invoice_title,a.batch_address,"
				+ "a.qian_remark,a.settlement,b.data_name,a.checktype,a.checkuser,a.checkdate,a.checknotice,a.sign_num,a.apptype"
				+ " From  batch a  left join data_dictionary b on a.pay_way=b.id "
				+ " left join crm_sign_bill_plan c on c.sign_num=a.sign_num where a.is_Active!='N' and a.center_num='"+center_num+"' and c.sign_num='"+sign_num+"'";
		if(apptype!=null&&apptype.length()>0){
			sql+=" and a.apptype="+apptype+"";
		}
				sql+= "  order by a.update_time desc";
				System.out.println(sql);
		PageSupport map = this.jqm.getList(sql, pageno, pagesize, BatchDTO.class);
		List<BatchDTO> list = map.getList();
		List<BatchDTO> temp = new ArrayList<BatchDTO>();
		for(BatchDTO batch : list){
			List<CrmBatchCheck> checklist = this.jqm.getList("select c.check_status,c.check_type from crm_batch_check c where c.batch_id =" +batch.getId(), CrmBatchCheck.class);
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
	
	/**
	 * 
	 * @Title: getbatchList @Description: 得到方案列表 @param: @param
	 *         company_Id @param: @param pagesize @param: @param
	 *         pageno @param: @return @param: @throws ServiceException @return:
	 *         PageReturnDTO @throws
	 */
	@SuppressWarnings("unchecked")
	public List<BatchDTO> getbatchList(long company_Id,String center_num,String app_type) throws ServiceException {
		String sql = "select a.id,a.company_id,a.batch_num,a.batch_name,a.pay_way,"
				+ "a.creater,a.create_time,a.updater,a.update_time,a.is_Active,"
				+ "a.exam_item,a.exam_number,a.exam_date,a.exam_date_end,a.charge_type,"
				+ "a.contact_name,a.sales_name,a.introducer_name,a.accommodation,"
				+ "a.dine,a.exam_fee,a.remark,a.phone,a.invoice_title,a.batch_address,"
				+ "a.qian_remark,a.settlement,b.data_name,a.checktype,a.checkuser,a.checkdate,a.checknotice"
				+ " From  batch a  left join data_dictionary b on a.pay_way=b.id where a.is_Active='Y' and a.center_num='"+center_num+"' and a.company_id=" + company_Id;
		if(!"".equals(app_type)){
			sql += " and a.apptype = '"+app_type+"'";
		}
		sql += " order by a.update_time desc";
		PageSupport map = this.jqm.getList(sql, 1, 1000, BatchDTO.class);
		List<BatchDTO> webrole = new ArrayList<BatchDTO>();
		
		if((map!=null)&&(map.getList()!=null)){
			webrole = map.getList();
		}

		return webrole;
	}
	
	/**
	 * 
	     * @Title: getbatchForcomidandname   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param company_Id
	     * @param: @param batchname
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: BatchDTO      
	     * @throws
	 */
	public BatchDTO getbatchForcomidandname(long company_Id,String batchname,String center_num,long id) throws ServiceException {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        SystemType defaultapp = (SystemType)request.getSession().getAttribute("defaultapp");
        String apptype = defaultapp.getComid();
		String sql = "select a.id,a.company_id,a.batch_num,a.batch_name,a.pay_way,"
				+ "a.creater,a.create_time,a.updater,a.update_time,a.is_Active,"
				+ "a.exam_item,a.exam_number,a.exam_date,a.exam_date_end,a.charge_type,"
				+ "a.contact_name,a.sales_name,a.introducer_name,a.accommodation,"
				+ "a.dine,a.exam_fee,a.remark,a.phone,a.invoice_title,a.batch_address,"
				+ "a.qian_remark,a.settlement,a.checktype,a.checkuser,a.checkdate,a.checknotice"
				+ " From  batch a  where a.is_Active='Y' and a.company_id=" + company_Id
				+ " and a.center_num='"+center_num+"' and a.batch_name='"+batchname.trim()+"'"
                +" and a.apptype='"+apptype+"' ";
                if(id > 0 ){
                sql+=" and a.id!='"+id+"' ";
                }
                sql+=" order by a.update_time desc";
		PageSupport map = this.jqm.getList(sql, 1, 1000, BatchDTO.class);
		BatchDTO webrole = new BatchDTO();
		
		if((map!=null)&&(map.getList()!=null)&&(map.getList().size()>0)){
			webrole = (BatchDTO)map.getList().get(0);
		}

		return webrole;
	}

	/**
	 * 
	 * <p>
	 * Title: getComByFatherID
	 * </p>
	 * <p>
	 * 通过id获取方案
	 * </p>
	 * 
	 * @param father
	 * @return
	 * @throws ServiceException
	 * @see com.hjw.wst.service.CompanyService#getComByFatherID(java.lang.String)
	 */
	public Batch getBatchByID(long id) throws ServiceException {
		return (Batch) qm.load(Batch.class, id);
	}

	/**
	 * 
	 * <p>
	 * Title: getComByFatherID
	 * </p>
	 * <p>
	 * 通过id获取分组
	 * </p>
	 * 
	 * @param father
	 * @return
	 * @throws ServiceException
	 * @see com.hjw.wst.service.CompanyService#getComByFatherID(java.lang.String)
	 */
	public GroupInfo getGroupByID(long id) throws ServiceException {
		return (GroupInfo) qm.load(GroupInfo.class, id);
	}

	/**
	 * 
	 * @Title: saveBatch @Description:保存方案 @param: @param
	 *         batch @param: @return @param: @throws ServiceException @return:
	 *         Batch @throws
	 */
	public Batch saveBatch(Batch batch) throws ServiceException {
		this.pm.save(batch);
		return batch;
	}

	/**
	 * 
	 * @Title: updateBatch @Description: 修改方案 @param: @param
	 *         batch @param: @return @param: @throws ServiceException @return:
	 *         Batch @throws
	 */
	public Batch updateBatch(Batch batch) throws ServiceException {
		this.pm.update(batch);
		return batch;
	}

	/**
	 * 
	 * @Title: saveGroup @Description: 保存分组 @param: @param
	 *         gi @param: @return @param: @throws ServiceException @return:
	 *         GroupInfo @throws
	 */
	public String saveGroup(GroupInfo gi, List<GroupChargingItemDTO> listitem, List<ExamSetDTO> listset,String center_num)
			throws ServiceException {
		String flags = "0-修改分组信息成功！"; 
		StringBuffer sql = new StringBuffer("insert into group_info (batch_id,group_num,group_name,"
				+ "start_date,end_date,sex,min_age,max_age,is_Marriage,posttion,discount,amount,"
				+ "group_index,isActive,creater,create_time,updater,update_time,"
				+ "person_team_amount,group_settlement_type,exam_indicator,cust_type_id,con_num,maximum_amount) values (" + gi.getBatch_id() + ",'"
				+ gi.getGroup_num() + "','" + gi.getGroup_name() + "','" + DateTimeUtil.getDateTime() + "','"
				+ DateTimeUtil.getDateTime() + "','" + gi.getSex() + "','" + gi.getMin_age() + "','" + gi.getMax_age()
				+ "','" + gi.getIs_Marriage() + "','" + gi.getPosttion() + "'," + gi.getDiscount() + ","
				+ gi.getAmount() + ",'"+gi.getGroup_index()+"','Y'," + gi.getCreater() + ",'" + DateTimeUtil.getDateTime() + "',"
				+ gi.getUpdater() + ",'" + DateTimeUtil.getDateTime() + "',0,'0','" + gi.getExam_indicator() + "','" + gi.getCust_type_id() + "',"+gi.getCon_num()+","+gi.getMaximum_amount()+")");

		System.out.println(sql.toString());
		Connection connection = null;
		Statement statement = null;
		try {
			// 读取记录数
			connection = this.jqm.getConnection();
			connection.setAutoCommit(false);
			PreparedStatement preparedStatement = null;
			preparedStatement = connection.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);
			preparedStatement.executeUpdate();
			ResultSet rs = null;
			rs = preparedStatement.getGeneratedKeys();
			int retId = 0;
			if (rs.next())
				retId = rs.getInt(1);
			else
				throw new Exception("insert or generate keys failed..");
			rs.close();
			preparedStatement.close();
			gi.setId(retId);
			if (gi.getId() > 0) {
				for (int i = 0; i < listset.size(); i++) {
					ExamSetDTO es = new ExamSetDTO();
					es = listset.get(i);
					es = findSetById(es.getSet_num(),center_num);

					sql = new StringBuffer();
					sql.append("insert into group_set (group_id,exam_set_id,discount,amount,"
							+ "isActive,final_exam_date,creater,create_time,updater,update_time) " + "values ("
							+ gi.getId() + "," + es.getId() + "," + es.getSet_discount() + "," + es.getSet_amount()
							+ ",'Y','" + DateTimeUtil.getDateTime() + "'," + gi.getCreater() + ",'"
							+ DateTimeUtil.getDateTime() + "'," + gi.getUpdater() + ",'" + DateTimeUtil.getDateTime()
							+ "')");
					connection.createStatement().executeUpdate(sql.toString());
				}

				for (int i = 0; i < listitem.size(); i++) {
					GroupChargingItemDTO es = new GroupChargingItemDTO();
					es = listitem.get(i);
					ChargingItemDTO cd = findChargeItemById(es.getItem_code());
					es.setCharge_item_id(cd.getId());

					sql = new StringBuffer();
					sql.append("insert into group_charging_item (charge_item_id,group_id,item_amount,"
							+ "discount,amount,isActive,final_exam_date,creater,create_time,updater,update_time,itemnum) "
							+ "values (" + es.getCharge_item_id() + "," + gi.getId() + "," + es.getItem_amount() + ","
							+ es.getDiscount() + "," + es.getAmount() + ",'Y','" + DateTimeUtil.getDateTime() + "',"
							+ gi.getCreater() + ",'" + DateTimeUtil.getDateTime() + "'," + gi.getUpdater() + ",'"
							+ DateTimeUtil.getDateTime() + "',"+es.getItemnum()+")");
					connection.createStatement().executeUpdate(sql.toString());
				}
				connection.commit();
			} else {
				flags = "1-分组信息保存失败！";
				connection.rollback();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			flags = "1-收费项目或套餐错误，分组信息保存失败！";
			try {
				connection.rollback();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} finally {
			try {
				if (statement != null) {
					statement.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return flags;
	}

	/**
	 * 
	 * @Title: updateGroup @Description: 修改 @param: @param
	 * gi @param: @return @param: @throws ServiceException @return:
	 * GroupInfo @throws
	 */
	public GroupInfo updateGroup(GroupInfo gi) throws ServiceException {
		this.pm.update(gi);
		return gi;
	}
	
	/**
	 * 
	     * @Title: useredGroup   
	     * @Description: 判断分组是否被使用  
	     * @param: @param group_id
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: boolean      
	     * @throws
	 */
	public boolean useredGroup(long group_id)throws ServiceException{
		Connection connection = null;
		Statement statement = null;
		boolean message=false;
		int countss = 0;
		try {
			String sqlcount = "select count(*) as counts from exam_info b where b.group_id="+group_id+" and b.is_Active='Y' ";
			connection = this.jqm.getConnection();
			statement = connection.createStatement();
			ResultSet rs1 = statement.executeQuery(sqlcount);
			if (rs1.next()) {
				countss = rs1.getInt("counts");
			}
			rs1.close();			
			if(countss>0){
				message=true;
			}			
		} catch (Exception ex) {
			ex.printStackTrace();
		}finally{
			try{
			if(statement==null){
				statement.close();
			}
			if(connection==null){
				connection.close();
			}
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}
		return message;
	}
	
	/**
	 * 
	     * @Title: examcountforcomid   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param com_id
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: boolean      
	     * @throws
	 */
	public int examcountforcomid(long com_id,UserDTO user)throws ServiceException{
		Connection connection = null;
		Statement statement = null;
		int countss = 0;
		try {
			String sqlcount = "select COUNT(*) as counts from company_info a,exam_info b,company_info_vs_center vs where a.is_Active='Y' and b.is_Active='Y' "
					+ "and b.company_id=a.id and  a.id = vs.company_id  and b.company_id='"+com_id+"' and vs.center_num='"+user.getCenter_num()+"' ";
			connection = this.jqm.getConnection();
			statement = connection.createStatement();
			ResultSet rs1 = statement.executeQuery(sqlcount);
			if (rs1.next()) {
				countss = rs1.getInt("counts");
			}
			rs1.close();			
						
		} catch (Exception ex) {
			ex.printStackTrace();
		}finally{
			try{
			if(statement==null){
				statement.close();
			}
			if(connection==null){
				connection.close();
			}
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}
		return countss;
	}

	/**
	 * 
	     * @Title: delBatch   
	     * @Description:删除体检任务   
	     * @param: @param batch_id
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: String      
	     * @throws
	 */
	@SuppressWarnings("null")
	public boolean useredBatch(long batch_id,String center_num)throws ServiceException{
		Connection connection = null;
		Statement statement = null;
		boolean message=false;
		int countss = 0;
		int checktype=2;
		try {
			String sqlcount = "select count(*) as counts from batch a,exam_info b, where a.id=b.batch_id and a.center_num='"+center_num+"' "
					+ " and a.is_Active='Y' and b.is_Active='Y' and a.id="+batch_id+" ";
			connection = this.jqm.getConnection();
			statement = connection.createStatement();
			ResultSet rs1 = statement.executeQuery(sqlcount);
			if (rs1.next()) {
				countss = rs1.getInt("counts");
			}
			rs1.close();
			
			sqlcount = "select a.checktype from batch a where a.id="+batch_id+" ";
			rs1 = statement.executeQuery(sqlcount);
			if (rs1.next()) {
				checktype = rs1.getInt("checktype");
			}
			rs1.close();
			
			String IS_BATCH_CHECK = this.customerInfoService.getCenterconfigByKey("IS_BATCH_CHECK", center_num).getConfig_value().trim();//是否体检任务自动审核(0:否;1:是)
			if((countss>0)||(checktype==2 && "0".equals(IS_BATCH_CHECK))){
				message=true;
			}			
		} catch (Exception ex) {
			ex.printStackTrace();
		}finally{
			try{
			if(statement==null){
				statement.close();
			}
			if(connection==null){
				connection.close();
			}
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}
		return message;
	}
	/**
	 * 
	 * @Title: updateGroup @Description: 修改分组 @param: @param
	 *         gi @param: @return @param: @throws ServiceException @return:
	 *         GroupInfo @throws
	 */
	@SuppressWarnings("unused")
	public String updateGroup(GroupInfo gi, List<GroupChargingItemDTO> listitem, List<ExamSetDTO> listset,String center_num)
			throws ServiceException {

		Connection connection = null;
		Statement statement = null;
		ExamSetDTO list = new ExamSetDTO();
		int countss = 0;
		/*String sqlcount = "select count(*) as counts from exam_info where  group_id=" + gi.getId()
				+ " and is_Active='Y'";
		try {
			connection = this.jqm.getConnection();
			statement = connection.createStatement();
			ResultSet rs1 = statement.executeQuery(sqlcount);
			if (rs1.next()) {
				countss = rs1.getInt("counts");
			}
			rs1.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}*/

		String flags = "0-修改分组信息成功！";
		if (countss > 0) {
			flags = "1-分组信息已经使用，不能修改！";
		} else {

			try {
				// 读取记录数
				connection = this.jqm.getConnection();
				connection.setAutoCommit(false);
				StringBuffer sql = new StringBuffer("update group_info set batch_id=" + gi.getBatch_id() + ",group_num='"
						+ gi.getGroup_num() + "',group_name='" + gi.getGroup_name() + "',"
						+ "start_date='" + DateTimeUtil.getDateTime() + "',end_date='"
						+ DateTimeUtil.getDateTime() + "',sex='" + gi.getSex() + "',min_age='" + gi.getMin_age() + "',max_age='"
						+ gi.getMax_age() + "',is_Marriage='" + gi.getIs_Marriage() + "',posttion='" + gi.getPosttion() + "',discount="
						+ gi.getDiscount() + ",amount=" + gi.getAmount() + ","
						+ "group_index='"+gi.getGroup_index()+"',group_order='"+gi.getGroup_order()+"',creater=" + gi.getCreater() + ",create_time='"
						+ DateTimeUtil.getDateTime() + "',updater=" + gi.getUpdater() + ",update_time='" + DateTimeUtil.getDateTime()
						+ "',person_team_amount=0,group_settlement_type='0',exam_indicator='"+
						gi.getExam_indicator()+"',cust_type_id='"+gi.getCust_type_id()+"',con_num="+gi.getCon_num()+",maximum_amount="+gi.getMaximum_amount()+" where id=" + gi.getId());
				System.out.println(sql.toString());
				connection.createStatement().executeUpdate(sql.toString());
				
				String updatesql = "update group_set  set isActive='N' where group_id=" + gi.getId();
				connection.createStatement().executeUpdate(updatesql);	
				
				if (gi.getId() > 0) {
					for (int i = 0; i < listset.size(); i++) {
						ExamSetDTO es = new ExamSetDTO();
						es = listset.get(i);
						es = findSetById(es.getSet_num(),center_num);

						sql = new StringBuffer();
						sql.append("insert into group_set (group_id,exam_set_id,discount,amount,"
								+ "isActive,final_exam_date,creater,create_time,updater,update_time) " + "values ("
								+ gi.getId() + "," + es.getId() + "," + es.getSet_discount() + "," + es.getSet_amount()
								+ ",'Y','" + DateTimeUtil.getDateTime() + "'," + gi.getCreater() + ",'"
								+ DateTimeUtil.getDateTime() + "'," + gi.getUpdater() + ",'"
								+ DateTimeUtil.getDateTime() + "')");
						connection.createStatement().executeUpdate(sql.toString());
					}
					updatesql = "update group_charging_item  set isActive='N' where group_id=" + gi.getId();
					connection.createStatement().executeUpdate(updatesql);	
					for (int i = 0; i < listitem.size(); i++) {
						GroupChargingItemDTO es = new GroupChargingItemDTO();
						es = listitem.get(i);
						ChargingItemDTO cd = findChargeItemById(es.getItem_code());
						es.setCharge_item_id(cd.getId());

						sql = new StringBuffer();
						sql.append("insert into group_charging_item (charge_item_id,group_id,item_amount,"
								+ "discount,amount,isActive,final_exam_date,creater,create_time,updater,update_time,itemnum) "
								+ "values (" + es.getCharge_item_id() + "," + gi.getId() + "," + es.getItem_amount()
								+ "," + es.getDiscount() + "," + es.getAmount() + ",'Y','" + DateTimeUtil.getDateTime()
								+ "'," + gi.getCreater() + ",'" + DateTimeUtil.getDateTime() + "'," + gi.getUpdater()
								+ ",'" + DateTimeUtil.getDateTime() + "',"+es.getItemnum()+")");
						connection.createStatement().executeUpdate(sql.toString());
					}
					connection.commit();
				} else {
					flags = "1-分组信息保存失败！";
					connection.rollback();
				}
			} catch (Exception ex) {
				ex.printStackTrace();
				flags = "1-收费项目或套餐错误，分组信息保存失败！";
				try {
					connection.rollback();
				} catch (Exception e) {
					e.printStackTrace();
				}
			} finally {
				try {
					if (statement != null) {
						statement.close();
					}
					if (connection != null) {
						connection.close();
					}
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		}
		return flags;
	}

	/**
	 * 
	 * <p>
	 * Title: findSetById
	 * </p>
	 * <p>
	 * 通过id获取套餐
	 * </p>
	 * 
	 * @param father
	 * @return
	 * @throws ServiceException
	 * @see com.hjw.wst.service.CompanyService#getComByFatherID(java.lang.String)
	 */
	public ExamSetDTO findSetById(long id,String center_num) throws ServiceException {
		StringBuffer sql = new StringBuffer("select id,company_id,set_num,set_name,set_pinyin,sex,set_discount,"
				+ "set_amount,survey_minScore,survey_maxScore,disease_name,is_Active,"
				+ "creater,create_time,updater,update_time" + " From  exam_set where center_num = '"+center_num+"' and  is_Active='Y'");
		sql.append(" and id=" + id + " ");
		sql.append(" order by update_time desc");
		System.out.println(sql.toString());
		Connection connection = null;
		Statement statement = null;
		ExamSetDTO list = new ExamSetDTO();
		try {
			// 读取记录数
			connection = this.jqm.getConnection();
			statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(sql.toString());
			if (rs.next()) {

				list.setId(rs.getInt("id"));
				list.setCompany_id(rs.getLong("company_id"));
				list.setCreate_time(rs.getString("create_time"));
				list.setCreater(rs.getLong("creater"));
				list.setDisease_name(rs.getString("disease_name"));
				list.setIs_Active(rs.getString("is_Active"));
				list.setSet_amount(rs.getDouble("set_amount"));
				list.setSet_discount(rs.getDouble("set_discount"));
				list.setSet_name(rs.getString("set_name"));
				list.setSet_num(rs.getString("set_num"));
				list.setSet_pinyin(rs.getString("set_pinyin"));
				list.setSex(rs.getString("sex"));
				list.setSurvey_maxScore(rs.getDouble("survey_maxScore"));
				list.setSurvey_minScore(rs.getDouble("survey_minScore"));
				list.setUpdate_time(rs.getString("update_time"));
				list.setUpdater(rs.getLong("updater"));
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

	/**
	 * 
	 * <p>
	 * Title: findSetById
	 * </p>
	 * <p>
	 * 通过套餐编号获取套餐
	 * </p>
	 * 
	 * @param father
	 * @return
	 * @throws ServiceException
	 * @see com.hjw.wst.service.CompanyService#getComByFatherID(java.lang.String)
	 */
	public ExamSetDTO findSetById(String set_num,String center_num) throws ServiceException {
		StringBuffer sql = new StringBuffer("select id,company_id,set_num,set_name,set_pinyin,sex,set_discount,"
				+ "set_amount,survey_minScore,survey_maxScore,disease_name,is_Active,"
				+ "creater,create_time,updater,update_time" + " From  exam_set where center_num='"+center_num+"' and is_Active='Y'");
		sql.append(" and set_num='" + set_num + "' ");
		sql.append(" order by update_time desc");
		System.out.println(sql.toString());
		Connection connection = null;
		Statement statement = null;
		ExamSetDTO list = new ExamSetDTO();
		try {
			// 读取记录数
			connection = this.jqm.getConnection();
			statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(sql.toString());
			if (rs.next()) {

				list.setId(rs.getInt("id"));
				list.setCompany_id(rs.getLong("company_id"));
				list.setCreate_time(rs.getString("create_time"));
				list.setCreater(rs.getLong("creater"));
				list.setDisease_name(rs.getString("disease_name"));
				list.setIs_Active(rs.getString("is_Active"));
				list.setSet_amount(rs.getDouble("set_amount"));
				list.setSet_discount(rs.getDouble("set_discount"));
				list.setSet_name(rs.getString("set_name"));
				list.setSet_num(rs.getString("set_num"));
				list.setSet_pinyin(rs.getString("set_pinyin"));
				list.setSex(rs.getString("sex"));
				//list.setSurvey_maxScore(Double.parseDouble(rs.getString("survey_maxScore")));
				//list.setSurvey_minScore(Double.parseDouble(rs.getString("survey_minScore")));
				list.setUpdate_time(rs.getString("update_time"));
				list.setUpdater(rs.getLong("updater"));
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

	/**
	 * 
	 * @Title: getsetForchargItemListBysetnum @Description:
	 *         通过套餐编号获取收费项目 @param: @param
	 *         setnum @param: @return @param: @throws ServiceException @return:
	 *         List<GroupChargingItemDTO> @throws
	 */
	@SuppressWarnings("unchecked")
	public List<GroupChargingItemDTO> getsetForchargItemListBysetnum(String setnum,String center_num) throws ServiceException {
		StringBuffer sql = new StringBuffer("select c.id,c.item_code,c.item_name,c.sex,c.isActive,"
				+ "c.item_type,d.dep_name,a.discount,a.amount,a.item_amount,b.set_num,a.itemnum,c.item_discount,a.ischosen  "
				+ " From  set_charging_item a,exam_set b,charging_item c,department_dep d ,department_vs_center de where b.set_num='" + setnum
				+ "' "
				+ " and de.dep_id = d.id  and de.center_num ='"+center_num+"' and c.isActive='Y' and b.center_num ='"+center_num+"' and b.is_Active='Y' and c.isActive='Y' and c.dep_id = d.id and a.charging_item_code=c.item_code "
				+ "and a.exam_set_id=b.id  ");
		sql.append(" order by d.seq_code,c.item_seq");
		System.out.println(sql.toString());
		PageSupport map = this.jqm.getList(sql.toString(), 1, 10000, GroupChargingItemDTO.class);
		List<GroupChargingItemDTO> mapList = new ArrayList<GroupChargingItemDTO>();
		if (map.getList() != null) {
			mapList = map.getList();
		}
		return mapList;
	}

	/**
	 * 
	 * @Title: getchargItemList @Description: 得到体检项目列表 @param: @param
	 *         group_id @param: @param pagesize @param: @param
	 *         pageno @param: @return @param: @throws ServiceException @return:
	 *         PageReturnDTO @throws
	 */
	public PageReturnDTO getchargItemList(String app_type,String setname,String sex,long dep_id, String center_num, int pagesize, int pageno) throws ServiceException {
		StringBuffer sql = new StringBuffer(
				"select a.app_type,a.id,a.dep_id,a.sam_demo_id,a.sam_report_demo_id,a.item_code,a.item_name,"
						+ "a.item_pinyin,a.item_category,a.sex,vs.center_price as item_amount,a.dep_category,a.isOnlyApplyOrReport,"
						+ "a.item_seq,a.guide_category,a.his_num,a.exam_num,a.view_num,a.isActive,a.creater,a.create_time,"
						+ "a.updater,a.update_time,a.calculation_amount,a.interface_flag,a.item_type,a.charge_inter_num,"
						+ "a.item_abbreviation,b.dep_name, a.item_seq,vs.item_discount");
		sql.append(" From  charging_item a,charging_item_vs_center vs,department_dep b,department_vs_center de where a.dep_id = b.id and a.isActive='Y'  ");
		sql.append("  and de.dep_id = b.id and de.center_num ='"+center_num+"' and   a.item_code = vs.charging_item_code   and vs.center_num='"+center_num+"'  ");
		/*if("2".equals(app_type)){
			sql.append("  and  a.app_type in(0,2) ");
		} else if("99".equals(app_type)) {
			sql.append("  and  a.app_type in(1) ");
		} else {
			sql.append("  and  a.app_type in(1,0)   ");
		}*/
		if (!StringUtil.isEmpty(sex)) {
			sql.append(" and (a.sex ='" +sex+ "' or a.sex='全部') ");
		}
		if (!StringUtil.isEmpty(setname)) {
			String ADD_ITEM_SEARCH_TYPE = this.customerInfoService.getCenterconfigByKey("ADD_ITEM_SEARCH_TYPE", center_num).getConfig_value().trim();
			if("0".equals(ADD_ITEM_SEARCH_TYPE)){
				sql.append(" and (a.item_name like '" + setname + "%'  or a.item_pinyin  like '" + setname + "%') ");
			}else{
				sql.append(" and (a.item_name like '%" + setname + "%'  or a.item_pinyin  like '%" + setname + "%') ");
			}
		}
		if(dep_id > 0){
			sql.append(" and b.id = "+dep_id);
		}
		sql.append("  order by a.item_seq,a.id desc");
		PageSupport map = this.jqm.getList(sql.toString(), pageno, pagesize, ChargingItemDTO.class);
		PageReturnDTO webrole = new PageReturnDTO();
		webrole.setPage(pageno);
		webrole.setRp(pagesize);
		webrole.setTotal(map.getRecTotal());
		webrole.setRows(map.getList());
		return webrole;
	}

	/**
	 * 
	 * @Title: getGroupchargItemList @Description: 分组收费项目列表 @param: @param
	 *         setname @param: @param pagesize @param: @param
	 *         pageno @param: @return @param: @throws ServiceException @return:
	 *         PageReturnDTO @throws
	 */
	public PageReturnDTO getGroupchargItemList(long groupid, int pagesize, int pageno,String center_num) throws ServiceException {
		StringBuffer sql = new StringBuffer("select a.id,a.charge_item_id,a.group_id,a.item_amount,a.discount,"
				+ "a.amount,a.isActive,a.itemnum,a.final_exam_date,a.creater,a.create_time,a.updater,a.update_time,b.item_code,b.item_name,c.dep_name,b.item_type,b.item_discount ");
		sql.append(
				" From  group_charging_item a,charging_item b,department_dep c,department_vs_center de where b.dep_id = c.id and a.charge_item_id = b.id and a.isActive='Y' ");
		sql.append("  and de.dep_id = c.id   and de.center_num ='"+center_num+"'  and a.group_id=" + groupid);
		sql.append("  order by b.item_seq,a.update_time desc");
		System.out.println(sql.toString());
		PageSupport map = this.jqm.getList(sql.toString(), pageno, pagesize, GroupChargingItemDTO.class);
		PageReturnDTO webrole = new PageReturnDTO();
		webrole.setPage(pageno);
		webrole.setRp(pagesize);
		webrole.setTotal(map.getRecTotal());
		webrole.setRows(map.getList());
		return webrole;
	}

	/**
	 * 
	 * @Title: findChargeItemById @Description: 收费项目num获取收费项目 @param: @param
	 *         item_num @param: @return @param: @throws
	 *         ServiceException @return: ExamSetDTO @throws
	 */
	public ChargingItemDTO findChargeItemById(String item_num) throws ServiceException {
		ChargingItemDTO ci = new ChargingItemDTO();
		StringBuffer sql = new StringBuffer("select id,dep_id,sam_demo_id,sam_report_demo_id,"
				+ "item_code,item_name,item_pinyin,item_category,sex,amount,dep_category,"
				+ "isOnlyApplyOrReport,item_seq,guide_category,his_num,exam_num,view_num,app_type,"
				+ "isActive,creater,create_time,updater,update_time,calculation_amount,"
				+ "interface_flag,item_type,charge_inter_num,item_abbreviation "
				+ "From charging_item where "
				+ "  item_code = '"+ item_num + "' ");
		sql.append("  order by update_time desc");
		PageSupport map = this.jqm.getList(sql.toString(), 1, 1000, ChargingItemDTO.class);
		if ((map != null) && (map.getList() != null))
			ci = (ChargingItemDTO) map.getList().get(0);

		return ci;
	}

	/**
	 * 
	     * @Title: getbatchproList   
	     * @Description: 获取人员计划信息 
	     * @param: @param barch_id
	     * @param: @param pagesize
	     * @param: @param pageno
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: PageReturnDTO      
	     * @throws
	 */
	public PageReturnDTO getbatchproList(long barch_id, int pagesize, int pageno) throws ServiceException {

		StringBuffer sql = new StringBuffer("select id,plandate,batch_id,per_num,creater,creater_date,remark ");
		sql.append(" From  batch_Per_plan ");
		sql.append(" where  batch_id=" + barch_id);
		sql.append("  order by creater_date desc");
		PageSupport map = this.jqm.getList(sql.toString(), pageno, pagesize, BatchProPlanDTO.class);
		PageReturnDTO webrole = new PageReturnDTO();
		webrole.setPage(pageno);
		webrole.setRp(pagesize);
		webrole.setTotal(map.getRecTotal());
		webrole.setRows(map.getList());
		return webrole;

	}
	
	/**
	 * 
	     * @Title: saveBatchProPlan   
	     * @Description: 保存   
	     * @param: @param bpp
	     * @param: @throws ServiceException      
	     * @return: void      
	     * @throws
	 */
	public void saveBatchProPlan(BatchProPlan bpp) throws ServiceException {
		this.pm.save(bpp);
	}
	
	/**
	 * 
	     * @Title: delBatchProPlanForBatchId   
	     * @Description: batchId 删除方案人员计划
	     * @param: @param batchId
	     * @param: @throws ServiceException      
	     * @return: void      
	     * @throws
	 */
	public void delBatchProPlanForBatchId(long batchId)throws ServiceException {
		Connection connection = null;		
		try {
			// 读取记录数
			connection = this.jqm.getConnection();
			connection.createStatement().executeUpdate("delete from batch_Per_plan where batch_id="+batchId);
			
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			try {
				
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException sqle4) {
				sqle4.printStackTrace();
			}
		}
	}
	
	/**
	 * 
	     * @Title: getFangAnShow   
	     * @Description: 获取方案的所有信息   
	     * @param: @param batch_id
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: FangAnShowDTO      
	     * @throws
	 */
	public FangAnShowDTO getFangAnShow(long batch_id,String center_num)throws ServiceException {
				
		List<GroupSetDTO> groupSetList = new ArrayList<GroupSetDTO>();//套餐 中文名称
		
		List<GroupInfoDTO>	groupItemList= new ArrayList<GroupInfoDTO>();
		
		List<GroupChargingItemDTO> itemList= new ArrayList<GroupChargingItemDTO>();
		
		List<BatchProPlanDTO> bppList = new ArrayList<BatchProPlanDTO>();	
		
		StringBuffer sql = new StringBuffer("select c.id,c.group_num,c.group_name,c.amount,c.discount "
				+ "from group_info c where  c.batch_id="+batch_id+"  and c.isActive='Y'");
		System.out.println(sql.toString());
		Connection connection = null;
		Statement statement = null;
		try {
			// 读取记录数
			connection = this.jqm.getConnection();
			statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(sql.toString());
			while (rs.next()) {
				GroupSetDTO gs = new GroupSetDTO();
				GroupInfoDTO gi = new GroupInfoDTO();
				long group_id =rs.getLong("id");
				gi.setId(group_id);
				gi.setGroup_name(rs.getString("group_name"));
				gi.setAmount(rs.getDouble("amount"));
				gi.setDiscount(rs.getDouble("discount"));
				gi.setItemnames("");
				
				gs.setId(rs.getLong("id"));
				gs.setSet_name(rs.getString("group_name"));			
				gs.setDisease_name("");
				//获取分组对应的套餐
				Statement statement2 = null;
				try {
					statement2 = connection.createStatement();
					// 读取记录数
					StringBuffer sqlset = new StringBuffer("select n.id,n.set_num,n.set_name from group_set m, exam_set n "
							+ "where m.exam_set_id = n.id n.center_num='"+center_num+"' and m.isActive='Y' and m.group_id="+group_id+"");
					ResultSet setrs = statement2.executeQuery(sqlset.toString());
					while (setrs.next()) {
						gs.setDisease_name(gs.getDisease_name()+setrs.getString("set_name")+",");
					}
					setrs.close();
				} catch (SQLException ex) {
					ex.printStackTrace();
				}finally{
					try{
						if (statement2!=null){
							statement2.close();
						}					    
					}catch(Exception ex){}
				}
				
				//获取分组对应体检项目
				try {
					// 读取记录数
					statement2 = connection.createStatement();
					StringBuffer sqlset = new StringBuffer("select a.id,c.item_code,c.item_name,a.amount,"
							+ "a.discount,a.item_amount from charging_item c,group_charging_item a "
							+ "where a.charge_item_id=c.id and a.group_id="+group_id
							+"  and c.isActive='Y' and a.isActive='Y'");
					ResultSet setrs = statement2.executeQuery(sqlset.toString());
					while (setrs.next()) {
						gi.setItemnames(gi.getItemnames()+setrs.getString("item_name")+",");
					}
					setrs.close();
				} catch (SQLException ex) {
					ex.printStackTrace();
				} finally{
					try{
						if (statement2!=null){
							statement2.close();
						}					    
					}catch(Exception ex){}
				}
				groupSetList.add(gs);
				groupItemList.add(gi);
			}
			rs.close();
			
			
			//获取分组对应的套餐
			Statement statement3 = null;
			try {
				// 读取记录数
				statement3 = connection.createStatement();
				StringBuffer sqlset = new StringBuffer("select m.item_name,m.notices from charging_item m,"
						+ "(select distinct c.id  from group_charging_item a,group_info b,charging_item c where "
						+ "a.charge_item_id=c.id and a.group_id=b.id and b.batch_id="+batch_id+" and a.isActive='Y' "
						+ "and b.isActive='Y' and c.isActive='Y' ) n where n.id=m.id");
				ResultSet setrs = statement3.executeQuery(sqlset.toString());
				while (setrs.next()) {
					GroupChargingItemDTO gci=new GroupChargingItemDTO();
					gci.setItem_name(setrs.getString("item_name"));
					gci.setExam_num(setrs.getString("notices"));
					itemList.add(gci);
				}
				setrs.close();
			} catch (SQLException ex) {
				ex.printStackTrace();
			} finally {
				try {
					if (statement3 != null) {
						statement3.close();
					}
				} catch (SQLException sqle4) {
					sqle4.printStackTrace();
				}
			}
			
					//获取分组对应的套餐
					Statement statement4 = null;
					try {
						// 读取记录数
						statement4 = connection.createStatement();
						StringBuffer sqlset = new StringBuffer("select a.plandate,a.per_num from batch_Per_plan a where a.batch_id="+batch_id+" order by a.plandate");
						ResultSet setrs = statement4.executeQuery(sqlset.toString());
						while (setrs.next()) {
							BatchProPlanDTO bpp=new BatchProPlanDTO();
							bpp.setPlandate(setrs.getString("plandate"));
							bpp.setPer_num(setrs.getLong("per_num"));
							bppList.add(bpp);
						}
						setrs.close();
					} catch (SQLException ex) {
						ex.printStackTrace();
					} finally {
						try {
							if (statement4 != null) {
								statement4.close();
							}
						} catch (SQLException sqle4) {
							sqle4.printStackTrace();
						}
					}
			
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
		FangAnShowDTO fa=new FangAnShowDTO();
		fa.setGroupItemList(groupItemList);
		fa.setGroupSetList(groupSetList);
		fa.setItemList(itemList);
		fa.setBppList(bppList);
		return fa;
	}

	
	/**
	 * 
	     * @Title: saveContract   
	     * @Description: 保存合同  
	     * @param: @param ct
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: Contract      
	     * @throws
	 */
	public Contract saveContract(Contract ct)throws ServiceException {
		this.pm.save(ct);
		return ct;
	}
	
	/**
	 * 
	     * @Title: updateContract   
	     * @Description: 修改合同  
	     * @param: @param ct
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: Contract      
	     * @throws
	 */
	public Contract updateContract(Contract ct)throws ServiceException {
		this.pm.update(ct);
		return ct;
	}
	
	/**
	 * 
	     * @Title: loadContract   
	     * @Description: 加载合同  
	     * @param: @param id
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: Contract      
	     * @throws
	 */
	public Contract loadContract(long id)throws ServiceException {
		Contract ct = new Contract();
		ct = (Contract)this.qm.load(Contract.class, id);
		return ct;
	}	
	
	
	/**
	 * 
	     * @Title: getContractForBrachId   
	     * @Description: 获取合同信息   
	     * @param: @param barch_id
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: ContractDTO      
	     * @throws
	 */
	public ContractDTO getContractForBrachId(long barch_id) throws ServiceException {
		ContractDTO c = new ContractDTO();
		StringBuffer sql = new StringBuffer("select id,company_id,company_name,batch_id,batch_name,"
				+ "types,contract_num,validity_date,creater,create_time,updater,update_time,"
				+ "remark,linkman,tel,checknotice,checkuser,checkdate ");
		sql.append(" From  contract ");
		sql.append(" where  batch_id=" + barch_id);
		PageSupport map = this.jqm.getList(sql.toString(), 1, 1000, ContractDTO.class);
		if((map!=null)&&(map.getList()!=null)&&(map.getList().size()>0)){
			c=(ContractDTO)map.getList().get(0);
		}		
		return c;
	}
	
	/**
	 * 
	     * @Title: getContractForContNum   
	     * @Description: 通过合同编号查询合同  
	     * @param: @param barch_id
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: ContractDTO      
	     * @throws
	 */
	public ContractDTO getContractForContNum(String contnum) throws ServiceException {
		ContractDTO c = new ContractDTO();
		StringBuffer sql = new StringBuffer("select id,company_id,company_name,batch_id,batch_name,"
				+ "types,contract_num,validity_date,creater,create_time,updater,update_time,"
				+ "remark,linkman,tel,checknotice,checkuser,checkdate ");
		sql.append(" From  contract ");
		sql.append(" where  contract_num='"+contnum+"'");
		PageSupport map = this.jqm.getList(sql.toString(), 1, 1000, ContractDTO.class);
		if((map!=null)&&(map.getList()!=null)&&(map.getList().size()>0)){
			c=(ContractDTO)map.getList().get(0);
		}		
		return c;
	}
	
	/**
	 * 
	     * @Title: contractlistshow   
	     * @Description: 获取合同列表
	     * @param: @param pagesize
	     * @param: @param pageno
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: PageReturnDTO      
	     * @throws
	 */
	public PageReturnDTO contractlistshow(long comid,long batchid,int pagesize, int pageno,String center_num) throws ServiceException {
		StringBuffer sql = new StringBuffer("select a.id,a.company_id,a.company_name,a.batch_id,a.batch_name,"
				+ "a.types,a.contract_num,a.validity_date,a.creater,a.create_time,a.updater,a.update_time,"
				+ "a.remark,a.linkman,a.tel,a.checknotice,a.checkuser,a.checkdate,b.overflag ");
		sql.append(" From  contract a,batch b where b.id=a.batch_id");
		sql.append(" and  a.company_id="+comid+" ");
		sql.append(" and  a.batch_id="+batchid+" and b.center_num='"+center_num+"' ");
		sql.append(" order by a.update_time desc");
		PageSupport map = this.jqm.getList(sql.toString(), pageno, pagesize, ContractDTO.class);
		PageReturnDTO webrole = new PageReturnDTO();
		webrole.setPage(pageno);
		webrole.setRp(pagesize);
		webrole.setTotal(map.getRecTotal());
		webrole.setRows(map.getList());
		return webrole;
	}
	
	/**
	 * 
	     * @Title: contractlistshow   
	     * @Description: 单位id获取合同  
	     * @param: @param comid
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<ContractDTO>      
	     * @throws
	 */
	public List<ContractDTO> contractlistshow(long comid) throws ServiceException {
		StringBuffer sql = new StringBuffer("select id,company_id,company_name,batch_id,batch_name,"
				+ "types,contract_num,validity_date,creater,create_time,updater,update_time,"
				+ "remark,linkman,tel,checknotice,checkuser,checkdate ");
		sql.append(" From  contract where 1=1");
		sql.append(" and  company_id="+comid+" ");		
		sql.append(" order by update_time desc");
		List<ContractDTO> list = new ArrayList<ContractDTO>();
		PageSupport map = this.jqm.getList(sql.toString(), 1, 1000, ContractDTO.class);
		if((map!=null)&&(map.getList()!=null))
			list = map.getList();
		return list;
	}
	/**
	 * 
	     * @Title: contractlistshow   
	     * @Description: 合同编号获取合同
	     * @param: @param pagesize
	     * @param: @param pageno
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: PageReturnDTO      
	     * @throws
	 */
	public ContractDTO contractForContractNum(String contractNum) throws ServiceException {
		StringBuffer sql = new StringBuffer("select id,company_id,company_name,batch_id,batch_name,"
				+ "types,contract_num,validity_date,creater,create_time,updater,update_time,"
				+ "remark,linkman,tel,checknotice,checkuser,checkdate ");
		sql.append(" From  contract where ");
		sql.append(" contract_num='"+contractNum+"' ");		
		PageSupport map = this.jqm.getList(sql.toString(), 1, 1000, ContractDTO.class);
		ContractDTO ct =new ContractDTO();
        if((map.getList()!=null)&&(map.getList().size()>0)){
        	ct = (ContractDTO)map.getList().get(0);
        }
		return ct;
	}
	
	/**
	 * 
	     * @Title: checkitemflag   
	     * @Description: 判断是否存在相同的检查项目，检查为 131  
	     * @param: @param item_codes
	     * @param: @param item_code
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: boolean      
	     * @throws
	 */
	public boolean checkitemflag(String item_codes,String item_code, String center_num) throws ServiceException{
		boolean falgs=false;
		List<String> item_numold=new ArrayList<String>();
		List<String> item_numnew=new ArrayList<String>();
		if(!"".equals(item_codes) && item_codes != null){
			StringBuffer sql = new StringBuffer("select m.item_num from (select a.item_code,b.item_num from charging_item a,"
					+ "examination_item b,charging_item_exam_item c,department_dep d ,department_vs_center de where a.item_code=c.charging_item_code "
					+ "and b.item_num=c.item_code  and de.dep_id = d.id   and a.isActive='Y' and b.is_Active='Y' and a.dep_id=d.id  and de.center_num ='"+center_num+"'  "
					+ "and d.dep_category='131' and a.item_code in ("+item_codes+")) m group by m.item_num ");
			//System.out.println(sql.toString());
			Connection connection = null;
			Statement statement = null;
			try {
				// 读取记录数
				connection = this.jqm.getConnection();
				statement = connection.createStatement();
				ResultSet rs = statement.executeQuery(sql.toString());
				while (rs.next()) {
					item_numold.add(rs.getString("item_num"));
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
		}
		 Connection connection1 = null;
 		Statement statement1 = null;
 		try {
 			// 读取记录数
			StringBuffer sql = new StringBuffer("select b.item_num from charging_item a,"
 					+ "examination_item b,charging_item_exam_item c,department_dep d,department_vs_center de where a.item_code=c.charging_item_code "
 					+ "and b.item_num=c.item_code and a.isActive='Y' and b.is_Active='Y' and a.dep_id=d.id and de.dep_id = d.id   "
 					+ "and d.dep_category='131' and a.item_code ='"+item_code+"'  and de.center_num ='"+center_num+"' ");
 			connection1 = this.jqm.getConnection();
 			statement1 = connection1.createStatement();
 			ResultSet rs1 = statement1.executeQuery(sql.toString());
 			while (rs1.next()) {
 				item_numnew.add(rs1.getString("item_num"));
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

 		if(item_numold.size() > 0 && item_numnew.size() > 0){
			for(int i=0;i<item_numold.size();i++){
				for(int j=0;j<item_numnew.size();j++){
					if((item_numold.get(i)!=null)&&(item_numnew.get(j)!=null)&&(item_numold.get(i).equals(item_numnew.get(j)))){
						falgs=true;
						break;
					}
				}
				if(falgs){
					break;
				}
			}
		}
		return falgs;
	}
	
	 public double getGroupcharItemAmt(long groupid)throws ServiceException
			  {
			    Connection connection = null;
			    Statement statement = null;
			    boolean message = false;
			    double countss = 0.0D;
			    try
			    {
			      String sqlcount = "select sum(a.item_amount*a.itemnum) as counts  From  group_charging_item a where   a.isActive='Y'  and a.group_id=" + groupid;
			      connection = this.jqm.getConnection();
			      statement = connection.createStatement();
			      ResultSet rs1 = statement.executeQuery(sqlcount);
			      if (rs1.next()) {
			        countss = rs1.getDouble("counts");
			      }
			      rs1.close();
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
			    return countss;
			  }
	 
	 /**
		 * 
		     * @Title: getCompanyForName   
		     * @Description: 获取有效单位为name的模糊查询
		     * @param: @param name
		     * @param: @return
		     * @param: @throws ServiceException      
		     * @return: List<TreeDTO>      
		     * @throws
		 */
		public List<TreeDTO> getCompanyForNameBatch(String name,String ispower,String apptype,UserDTO user)throws ServiceException{
			StringBuffer sqltext= new StringBuffer();
			 if((name==null)||(name.trim().length()<=0)){
	            	name="9999";
	            }else{
	            	name=name.trim();
	            }
			sqltext.append(" select top 40 bc.id as barchid,a.id as comid,bc.batch_name,a.com_name from batch bc,company_info a,company_info_vs_center vs  "
					+ "where bc.is_Active='Y' "
					+ "and bc.checktype='2' and bc.company_id=a.id  and vs.company_id = a.id AND vs.center_num = '"+user.getCenter_num()+"'  AND "
					+ " a.is_Active='Y'   and (a.com_name like '%"+name+"%' or a.name_pinyin like '%"+name+"%')");
			 if("Y".equals(ispower)){
       		    sqltext.append(" and a.creater='"+user.getUserid()+"' ");  
       	     }
			sqltext.append(" and bc.apptype = '"+apptype+"'  order by a.create_time desc");
			Connection connection = null;
			Statement statement = null;
			List<TreeDTO> list = new ArrayList<TreeDTO>();
			try {
				// 读取记录数
				connection = this.jqm.getConnection();
				statement = connection.createStatement();
				ResultSet rs1 = statement.executeQuery(sqltext.toString());
				while (rs1.next()) {
					TreeDTO jd =new TreeDTO();
					jd.setId(rs1.getString("comid")+"-"+rs1.getString("barchid"));
					jd.setText(rs1.getString("batch_name")+"("+rs1.getString("com_name")+")");
					jd.setAttributes("0");
					list.add(jd);
				}
				rs1.close();
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
		
		
		/**
		 * 
		     * @Title: getGroupInfoById   
		     * @Description: TODO(这里用一句话描述这个方法的作用)   
		     * @param: @param id
		     * @param: @return
		     * @param: @throws ServiceException      
		     * @return: GroupInfo      
		     * @throws
		 */
		public GroupInfo getGroupInfoById(long id) throws ServiceException {
			List ls = qm.find("From GroupInfo Where id ="+id+"");
			if(ls.size()>0)
				return (GroupInfo) ls.get(0);
			else
				return null;
		}
		
		/**
		 * 
		     * @Title: getExaminfoSetById   
		     * @Description: TODO(这里用一句话描述这个方法的作用)   
		     * @param: @param id
		     * @param: @return
		     * @param: @throws ServiceException      
		     * @return: ChargingItem      
		     * @throws
		 */
		public ExamSet getExamSetByNum(String set_num,String center_num) throws ServiceException{
			List ls = qm.find("From ExamSet Where center_num ='"+center_num+"' AND set_num ='"+set_num+"'");
			if(ls.size()>0)
				return (ExamSet) ls.get(0);
			else
				return null;
		}
		
		/**
		 * 
		     * @Title: getChargingItemById   
		     * @Description: TODO(这里用一句话描述这个方法的作用)   
		     * @param: @param id
		     * @param: @return
		     * @param: @throws ServiceException      
		     * @return: ChargingItem      
		     * @throws
		 */
		public ChargingItem getChargingItemById(long id) throws ServiceException {
			List ls = qm.find("From ChargingItem Where id ="+id+"");
			if(ls.size()>0)
				return (ChargingItem) ls.get(0);
			else
				return null;
		}
		
		/**
		 * 
		     * @Title: getChargingItemBycode   
		     * @Description: TODO(这里用一句话描述这个方法的作用)   
		     * @param: @param item_code
		     * @param: @return
		     * @param: @throws ServiceException      
		     * @return: ChargingItem      
		     * @throws
		 */
		public ChargingItem getChargingItemBycode(String item_code) throws ServiceException {
			List ls = qm.find("From ChargingItem Where item_code ='"+item_code+"'");
			if(ls.size()>0)
				return (ChargingItem) ls.get(0);
			else
				return null;
		}
		
		/**
		 * 
		     * @Title: saveGroupInfo   
		     * @Description: TODO(这里用一句话描述这个方法的作用)   
		     * @param: @param gi
		     * @param: @return
		     * @param: @throws ServiceException      
		     * @return: GroupInfo      
		     * @throws
		 */
		public GroupInfo saveGroupInfo(GroupInfo gi) throws ServiceException {
			this.pm.save(gi);
			return gi;
		}
		
		public List<GroupChargingItem> getGroupChargingItemForgroupid(long group_id)throws ServiceException {
			List ls = qm.find("From GroupChargingItem Where group_id ="+group_id+" and isActive='Y' ");
			return ls;
		}
		
		/**
		 * 
		     * @Title: getGroupSetById   
		     * @Description: TODO(这里用一句话描述这个方法的作用)   
		     * @param: @param group_id
		     * @param: @return
		     * @param: @throws ServiceException      
		     * @return: List<GroupSet>      
		     * @throws
		 */
		public List<GroupSet> getGroupSetById(long group_id) throws ServiceException {
			List ls = qm.find("From GroupSet Where group_id ="+group_id+" and isActive='Y' ");
			return ls;
		}
		
		/**
		 * 
		     * @Title: copyGroup   
		     * @Description: 拷贝分组   
		     * @param: @param batch_id
		     * @param: @param ids
		     * @param: @param userid
		     * @param: @throws ServiceException      
		     * @return: void      
		     * @throws
		 */
		public void copyGroup(long batch_id,String ids,long userid) throws ServiceException {
			String[] idss=ids.split(",");
			for(int i=0;i<idss.length;i++){
				long groupid = Long.valueOf(idss[i]);
				GroupInfo groupInfo=new GroupInfo();
				groupInfo=this.getGroupByID(groupid);
				if(groupInfo!=null){
					GroupInfo groupInfonew=new GroupInfo();
					groupInfonew.setIsActive("Y");
					groupInfonew.setBatch_id(batch_id);
					groupInfonew.setCreater(userid);
					groupInfonew.setUpdater(userid);
					groupInfonew.setCreate_time(DateTimeUtil.parse());
					groupInfonew.setUpdate_time(DateTimeUtil.parse());
					groupInfonew.setAmount(groupInfo.getAmount());
					groupInfonew.setDiscount(groupInfo.getDiscount());
					groupInfonew.setEnd_date(groupInfo.getEnd_date());
					groupInfonew.setExam_indicator(groupInfo.getExam_indicator());
					groupInfonew.setGroup_index(groupInfo.getGroup_index());
					groupInfonew.setGroup_name(groupInfo.getGroup_name());
					groupInfonew.setGroup_num(groupInfo.getGroup_num());
					groupInfonew.setGroup_settlement_type(groupInfo.getGroup_settlement_type());
					groupInfonew.setIs_Marriage(groupInfo.getIs_Marriage());
					groupInfonew.setMax_age(groupInfo.getMax_age());
					groupInfonew.setMin_age(groupInfo.getMin_age());
					groupInfonew.setPerson_team_amount(groupInfo.getPerson_team_amount());
					groupInfonew.setPosttion(groupInfo.getPosttion());
					groupInfonew.setSex(groupInfo.getSex());
					groupInfonew.setStart_date(groupInfo.getStart_date());
					groupInfonew=this.saveGroupInfo(groupInfonew);
					
					List<GroupSet> gslist=new ArrayList<GroupSet>();
					gslist = getGroupSetById(groupid);
					for(GroupSet gs:gslist){
						GroupSet gsnew =  new GroupSet();
						gsnew.setIsActive("Y");
						gsnew.setGroup_id(groupInfonew.getId());
						gsnew.setCreater(userid);
						gsnew.setUpdater(userid);
						gsnew.setCreate_time(DateTimeUtil.getDateTime());
						gsnew.setUpdate_time(DateTimeUtil.getDateTime());
						gsnew.setAmount(gs.getAmount());
						gsnew.setDiscount(gs.getDiscount());
						gsnew.setExam_set_id(gs.getExam_set_id());
						gsnew.setFinal_exam_date(gs.getFinal_exam_date());
						this.pm.save(gsnew);
					}
					
					List<GroupChargingItem> gcilist = new ArrayList<GroupChargingItem>();
					gcilist=this.getGroupChargingItemForgroupid(groupid);
					double amts = 0;
					for(GroupChargingItem gci:gcilist){
						ChargingItem ci =  new ChargingItem();
						ci=this.getChargingItemById(gci.getCharge_item_id());
						if((ci!=null)&&("Y".equals(ci.getIsActive()))){
							GroupChargingItem gcinew= new GroupChargingItem();
							gcinew.setGroup_id(groupInfonew.getId());
							gcinew.setIsActive("Y");
							gcinew.setCreater(userid);
							gcinew.setUpdater(userid);
							gcinew.setCreate_time(DateTimeUtil.getDateTime());
							gcinew.setUpdate_time(DateTimeUtil.getDateTime());
							gcinew.setItem_amount(ci.getAmount());
							BigDecimal bd = new BigDecimal(0);
							bd = new BigDecimal(ci.getAmount()*gci.getItemnum()*gci.getDiscount()/10);
							double tamt = bd.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
							gcinew.setAmount(tamt);
							gcinew.setCharge_item_id(gci.getCharge_item_id());
							gcinew.setDiscount(gci.getDiscount());
							gcinew.setItemnum(gci.getItemnum());
							gcinew.setFinal_exam_date(gci.getFinal_exam_date());
							amts=amts+tamt;
							this.pm.save(gcinew);
						}						
					}
					groupInfonew.setAmount(amts);
					pm.update(groupInfonew);
				}
			}
		}
		
		/**
		 * 
		     * @Title: getGroupListForId   
		     * @Description: TODO(这里用一句话描述这个方法的作用)   
		     * @param: @param batch_id
		     * @param: @return
		     * @param: @throws ServiceException      
		     * @return: GroupInfoDTO      
		     * @throws
		 */
		public GroupInfoDTO getGroupListForId(long batch_id) throws ServiceException {
			GroupInfoDTO gi=new GroupInfoDTO();
			String sql = "select a.id,a.batch_id,a.group_num,a.group_name," 
		            + "a.start_date,a.end_date,a.sex,a.min_age,a.max_age,a.is_Marriage,"
					+ "a.posttion,a.discount,a.amount,a.group_index,a.isActive,convert(varchar(50),a.cust_type_id) as cust_type_id,"
					+ "a.creater,a.create_time,a.updater,a.update_time,a.person_team_amount," 
					+ "a.group_settlement_type,a.exam_indicator "
					+ " From  group_info a"
					+ "  where a.id=" + batch_id + " and a.isActive='Y' order by a.update_time desc";
			PageSupport map = this.jqm.getList(sql, 1, 1000, GroupInfoDTO.class);
		
			if(((map!=null))&&(map.getList()!=null)&&(map.getList().size()>0)){
				gi=(GroupInfoDTO)map.getList().get(0);
				if(gi.getCust_type_id()!=null){
				   gi.setType_name(this.customerInfoService.getcustomerTypeForName(gi.getCust_type_id()));
				}
			}
			return gi;
		}

		@Override
		public void saveCrmBatchCheck(CrmBatchCheck crmBatchCheck) throws ServiceException {
			this.pm.save(crmBatchCheck);			
		}
		
		public PageReturnDTO getCrmGroupList(long batch_id, int pagesize, int pageno) throws ServiceException {
			String sql = "select a.id,a.batch_id,a.group_num,a.group_name,a.start_date,a.end_date,a.sex,a.min_age,a.max_age,a.is_Marriage,"
					+ "a.posttion,a.discount,a.amount,a.group_index,a.isActive,convert(varchar(50),a.cust_type_id) as cust_type_id,"
					+ "a.creater,a.create_time,a.updater,a.update_time,a.person_team_amount,"
					+ "a.group_settlement_type,a.exam_indicator "
					+ " From  group_info a"
					+ "  where a.batch_id=" + batch_id + " and a.isActive=!'N' order by a.update_time desc";
			PageSupport map = this.jqm.getList(sql, pageno, pagesize, GroupInfoDTO.class);
			PageReturnDTO webrole = new PageReturnDTO();
			webrole.setPage(pageno);
			webrole.setRp(pagesize);
			webrole.setTotal(map.getRecTotal());
			List<GroupInfoDTO> gilist= new ArrayList<GroupInfoDTO>();
			for(int i=0;i<map.getList().size();i++){
				GroupInfoDTO gi= new GroupInfoDTO();
				gi=(GroupInfoDTO)map.getList().get(i);
				if(gi.getCust_type_id()!=null){
					gi.setType_name(this.customerInfoService.getcustomerTypeForName(gi.getCust_type_id()));
				}
				gilist.add(gi);
			}
			webrole.setRows(gilist);
			return webrole;
		}

		@Override
		public BatchDTO getBatchDTOById(String id) throws ServiceException {
			String sql="select cm.com_num,cm.com_name,b.batch_num,c.sign_name,b.batch_name,CONVERT(varchar(50),b.create_time,23) "
					+ "  as create_time ,b.checktype , b.accountflag from batch b left join crm_sign_bill_plan c on b.sign_num=c.sign_num "
					+ "  left join company_info cm on cm.id=b.company_id where b.id='"+id+"'";
			Connection connection = null;
			Statement statement = null;
			BatchDTO batch=new BatchDTO();
			try {
				// 读取记录数
				connection = this.jqm.getConnection();
				statement = connection.createStatement();
				ResultSet rs = statement.executeQuery(sql);
				while (rs.next()) {
					batch.setCom_name(rs.getString("com_name"));
					batch.setCom_num(rs.getString("com_num"));
					batch.setBatch_num(rs.getString("batch_num"));
					batch.setSign_name(rs.getString("sign_name"));
					batch.setBatch_name(rs.getString("batch_name"));
					batch.setCreate_time(rs.getString("create_time"));
					batch.setChecktype(rs.getInt("checktype"));
					batch.setAccountflag(rs.getInt("accountflag"));
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
			return batch;
		}

		public List<GroupChargingItemDTO> getItemSampleDemo(long item_id, String center_num) throws ServiceException {
			String cfg_value = this.customerInfoService.getCenterconfigByKey("IS_CHANGE_CHARGING_ITEM", center_num).getConfig_value().trim();
			List<GroupChargingItemDTO> li = new ArrayList<GroupChargingItemDTO>();
			if("Y".equals(cfg_value)){
				String sql = " SELECT ci.id,ci.item_code,ci.item_name,ci.sex,ci.amount,dep.dep_name,ci.item_discount FROM   charging_item   ci,sample_demo_charging_item  sa,  "
						   +"  department_dep  dep  "
						   +"  where  ci.item_code = sa.charging_item_code  AND   ci.isActive='Y'  AND  dep.id = ci.dep_id  and "
						   +" sa.sample_demo_id = (SELECT fc.sam_demo_id FROM  charging_item  fc   WHERE  fc.isActive='Y'   AND   fc.id='"+item_id+"' ) ";
				 		   li = this.jqm.getList(sql,GroupChargingItemDTO.class);
			}
			return li;
		}

		@Override
		public ChargingItemDTO findChargeItemById(long item_id) throws ServiceException {
			ChargingItemDTO ci = new ChargingItemDTO();
			StringBuffer sql = new StringBuffer("select id,dep_id,sam_demo_id,sam_report_demo_id,"
					+ "item_code,item_name,item_pinyin,item_category,sex,amount,dep_category,"
					+ "isOnlyApplyOrReport,item_seq,guide_category,his_num,exam_num,view_num,"
					+ "isActive,creater,create_time,updater,update_time,calculation_amount,calculation_rate,"
					+ "interface_flag,item_type,charge_inter_num,item_abbreviation "
					+ "From charging_item where id = '"
					+ item_id + "'");
			sql.append("  order by update_time desc");
			PageSupport map = this.jqm.getList(sql.toString(), 1, 1000, ChargingItemDTO.class);
			if ((map != null) && (map.getList() != null))
				ci = (ChargingItemDTO) map.getList().get(0);

			return ci;
		}
		
		@Override
		public int getGroupForbatidOrder(long batch_id) throws ServiceException {
			int gi=0;
			String sql = "select a.id,a.batch_id "
					+ " From  group_info a"
					+ " where a.id=" + batch_id + " and a.isActive='Y' ";
			PageSupport map = this.jqm.getList(sql, 1, 1000, GroupInfoDTO.class);		
			if(((map!=null))&&(map.getList()!=null)&&(map.getList().size()>0)){
				gi=map.getList().size();
			}
			return gi;
		}
		
		@Override
		public String setCopybatchToCom(long com_id, long batch_id, int group_order, long userid, String center_num)
				throws ServiceException {
			Batch bc = new Batch();
			Batch model = new Batch();
			model = this.getBatchByID(batch_id);			
				bc.setAccommodation(model.getAccommodation());
				bc.setBatch_address(model.getBatch_address());
				bc.setBatch_name(model.getBatch_name()+"01");
				bc.setBatch_num(model.getBatch_num()+"01");
				bc.setCharge_type(model.getCharge_type());
				bc.setCompany_id(com_id);
				bc.setContact_name(model.getContact_name());
				bc.setDine(model.getDine());
				bc.setExam_date(model.getExam_date());
				bc.setExam_date_end(model.getExam_date_end());
				bc.setExam_fee(model.getExam_fee());
				bc.setExam_item(model.getExam_item());
				bc.setExam_number(model.getExam_number());
				bc.setId(model.getId());
				bc.setIntroducer_name(model.getIntroducer_name());
				bc.setInvoice_title(model.getInvoice_title());
				bc.setIs_Active("Y");
				bc.setIs_showamount(model.getIs_showamount());
				bc.setIs_showseal(model.getIs_showseal());
				bc.setPay_way(model.getPay_way());
				bc.setPhone(model.getPhone());
				bc.setQian_remark(model.getQian_remark());
				bc.setRemark(model.getRemark());
				bc.setSales_name(model.getSales_name());
				bc.setCenter_num(center_num);
				//String checktypes = this.customerInfoService.getCenterconfigByKey("IS_BATCH_CHECK", user.getCenter_num()).getConfig_value().trim();
				//if ("0".equals(checktypes)) {
					bc.setChecktype(0);
				//} else if ("1".equals(checktypes)) {
					bc.setChecktype(2);
					bc.setChecknotice("系统自动发起体检任务审核");
					bc.setCheckuser(userid);
				//}
				
			bc.setCreater(userid);
			bc.setCreate_time(DateTimeUtil.parse());
			bc.setUpdater(userid);
			bc.setUpdate_time(DateTimeUtil.parse());
			this.pm.save(bc);

			CompanyInfo cif = new CompanyInfo();
			cif = companyService.findComByID(bc.getCompany_id());
			// 合同审核
			ContractDTO cd = new ContractDTO();
			cd = this.getContractForBrachId(bc.getId());
			if ((cd == null) || (cd.getId() <= 0)) {// 新增合同
				Contract ct = new Contract();
				ct.setBatch_id(bc.getId());
				ct.setBatch_name(bc.getBatch_name());
				ct.setCompany_id(bc.getCompany_id());
				ct.setCompany_name(cif.getCom_Name());
				String connum = customerInfoService.getCenterconfigByKey("IS_CONTRACT_NO", center_num).getConfig_value();
				connum = connum + GetNumContral.getInstance().getParamNum("contract", center_num);
				ct.setContract_num(connum);
				ct.setCreater(userid);
				ct.setCreate_time(DateTimeUtil.parse());
				ct.setUpdater(userid);
				ct.setUpdate_time(DateTimeUtil.parse());
				ct.setLinkman(bc.getContact_name());
				ct.setTel(bc.getPhone());
				String addday = this.customerInfoService.getCenterconfigByKey("IS_CONTRACT_EXADATE", center_num)
						.getConfig_value().trim();
				String vdate = DateTimeUtil.DateAdd(Integer.valueOf(addday));
				ct.setValidity_date(vdate);
				ct.setCheckdate(Timeutils.getNowDate());
				ct.setCheckuser(userid);
				if (bc.getChecktype() == 2) {
					String checktypesss = this.customerInfoService.getCenterconfigByKey("IS_CONTRACT_CHECK", center_num)
							.getConfig_value().trim();
					if ("0".equals(checktypesss)) {
						ct.setTypes(0);
					} else if ("1".equals(checktypesss)) {
						ct.setTypes(2);
						ct.setChecknotice("系统自动发起合同审核");
					}
				}
				this.pm.save(ct);
			}
			
			//
			List<GroupInfoDTO> gin=new ArrayList<GroupInfoDTO>();  
			gin= getGroupListForBatchId(batch_id);
			for(GroupInfoDTO groupInfo:gin){
				if(groupInfo!=null){
					group_order++;
					GroupInfo groupInfonew=new GroupInfo(); 
					groupInfonew.setIsActive("Y");
					groupInfonew.setBatch_id(bc.getId());
					groupInfonew.setCreater(userid);
					groupInfonew.setUpdater(userid);
					groupInfonew.setCreate_time(DateTimeUtil.parse());
					groupInfonew.setUpdate_time(DateTimeUtil.parse());
					groupInfonew.setAmount(groupInfo.getAmount());
					groupInfonew.setDiscount(groupInfo.getDiscount());
					groupInfonew.setEnd_date(DateTimeUtil.parse());
					groupInfonew.setExam_indicator(groupInfo.getExam_indicator());
					groupInfonew.setGroup_index(groupInfo.getGroup_index());
					groupInfonew.setGroup_name(groupInfo.getGroup_name());
					groupInfonew.setGroup_num(groupInfo.getGroup_num());
					groupInfonew.setGroup_settlement_type(groupInfo.getGroup_settlement_type());
					groupInfonew.setIs_Marriage(groupInfo.getIs_Marriage());
					groupInfonew.setMax_age(groupInfo.getMax_age());
					groupInfonew.setMin_age(groupInfo.getMin_age());
					groupInfonew.setPerson_team_amount(groupInfo.getPerson_team_amount());
					groupInfonew.setPosttion(groupInfo.getPosttion());
					groupInfonew.setSex(groupInfo.getSex());
					groupInfonew.setStart_date(DateTimeUtil.parse());
					groupInfonew.setGroup_order(group_order);
					groupInfonew=this.saveGroupInfo(groupInfonew);
					
					List<GroupSet> gslist=new ArrayList<GroupSet>();
					gslist = getGroupSetById(groupInfo.getId());
					for(GroupSet gs:gslist){
						GroupSet gsnew =  new GroupSet();
						gsnew.setIsActive("Y");
						gsnew.setGroup_id(groupInfonew.getId());
						gsnew.setCreater(userid);
						gsnew.setUpdater(userid);
						gsnew.setCreate_time(DateTimeUtil.getDateTime());
						gsnew.setUpdate_time(DateTimeUtil.getDateTime());
						gsnew.setAmount(gs.getAmount());
						gsnew.setDiscount(gs.getDiscount());
						gsnew.setExam_set_id(gs.getExam_set_id());
						gsnew.setFinal_exam_date(gs.getFinal_exam_date());
						this.pm.save(gsnew);
					}
					
					List<GroupChargingItem> gcilist = new ArrayList<GroupChargingItem>();
					gcilist=this.getGroupChargingItemForgroupid(groupInfo.getId());
					double amts = 0;
					for(GroupChargingItem gci:gcilist){
						ChargingItem ci =  new ChargingItem();
						ci=this.getChargingItemById(gci.getCharge_item_id());
						if((ci!=null)&&("Y".equals(ci.getIsActive()))){
							GroupChargingItem gcinew= new GroupChargingItem();
							gcinew.setGroup_id(groupInfonew.getId());
							gcinew.setIsActive("Y");
							gcinew.setCreater(userid);
							gcinew.setUpdater(userid);
							gcinew.setCreate_time(DateTimeUtil.getDateTime());
							gcinew.setUpdate_time(DateTimeUtil.getDateTime());
							gcinew.setItem_amount(ci.getAmount());
							BigDecimal bd = new BigDecimal(0);
							bd = new BigDecimal(ci.getAmount()*gci.getItemnum()*gci.getDiscount()/10);
							double tamt = bd.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
							gcinew.setAmount(tamt);
							gcinew.setCharge_item_id(gci.getCharge_item_id());
							gcinew.setDiscount(gci.getDiscount());
							gcinew.setItemnum(gci.getItemnum());
							gcinew.setFinal_exam_date(gci.getFinal_exam_date());
							amts=amts+tamt;
							this.pm.save(gcinew);
						}						
					}
					groupInfonew.setAmount(amts);
					pm.update(groupInfonew);
				}
			}
			return "ok-操作成功！";
		}
		
		@Override
		public Batch findClass(long id) {
			return (Batch) this.qm.get(Batch.class, id);
		}

		@Override
		public GroupInfo updateGroupInfo(GroupInfo groupInfo) throws ServiceException {
			this.pm.update(groupInfo);
			return groupInfo;
		}
		/**
		 * 
		     * @Title: getsetForchargItemListBysetnum   
		     * @Description: 通过套餐编号获取收费项目   
		     * @param: @param setnum
		     * @param: @return
		     * @param: @throws ServiceException      
		     * @return: List<GroupChargingItemDTO>      
		     * @throws
		 */
		public List<GroupChargingItemDTO> getsetForchargItemListBysetnumNOECI(String setnum,String setTypes,String examnum, String center_num) throws ServiceException{
			StringBuffer sql = new StringBuffer("select c.id,c.item_code,c.item_name,c.sex,c.isActive,c.item_type,d.dep_name,"
					+ "a.discount,a.amount,a.item_amount,b.set_num,a.itemnum  "
					+ "From  set_charging_item a,exam_set b,charging_item c,department_dep d "
					+ "where b.set_num='"+setnum+"' and c.isActive='Y' and b.is_Active='Y' and c.isActive='Y' and c.dep_id = d.id "
					+ "and a.charging_item_code=c.item_code and a.exam_set_id=b.id "
					+ "and c.item_code not in(select eci.charging_item_code from examinfo_charging_item eci "
					+ "where  eci.isActive='Y' and b.center_num='"+center_num+"' and eci.pay_status <>'M' and eci.center_num = '"+center_num+"' and eci.exam_num='"+examnum+"' )  order by d.seq_code,c.item_seq");
			System.out.println(sql.toString());
			PageSupport map = this.jqm.getList(sql.toString(), 1, 10000, GroupChargingItemDTO.class);
			List<GroupChargingItemDTO> mapList = new ArrayList<GroupChargingItemDTO>();
			if (map.getList() != null) {
				mapList = map.getList();
			}
			return mapList;
		}

		
		/**
		 * 
		     * @Title: saveBatchFlowLog   
		     * @Description: TODO(这里用一句话描述这个方法的作用)   
		     * @param: @param bfl
		     * @param: @throws ServiceException      
		     * @return: void      
		     * @throws
		 */
		
		public BatchFlowLog saveBatchFlowLog(BatchFlowLog bfl) throws ServiceException{
			this.pm.save(bfl);
			return bfl;
		}
		
		/**
		 * 
		 * @Title: getExaminfoChargingItemforExamId @Description:
		 * TODO(这里用一句话描述这个方法的作用) @param: @param
		 * examid @param: @return @param: @throws ServiceException @return:
		 * List<ExaminfoChargingItemDTO> @throws
		 */
		public List<ExamInfoDTO> getExaminfoYforGroupId(long batchid,long groupid) throws ServiceException {
			String sql = "select a.id,a.exam_num,a.status "
					+ "from exam_info a   "
					+ "where a.is_Active='Y' and a.status='Y' and a.group_id =" + groupid
					+ " and a.batch_id='"+batchid+"' ";
			PageSupport map = this.jqm.getList(sql, 1, 10000, ExamInfoDTO.class);
			List<ExamInfoDTO> list = new ArrayList<ExamInfoDTO>();
			if ((map != null) && (map.getList() != null) && (map.getList().size() > 0)) {
				list=map.getList();
			}
			return list;
		}

		@Override
		public BatchDTO queryFromBatchExamnumOrExamid(String exam_num,String center_num) throws ServiceException {
			String sql = "SELECT b.accountflag, b.overflag FROM batch b,exam_info e where e.batch_id = b.id and b.center_num='"+center_num+"' ";
					
					if(!"".equals(exam_num) && exam_num!=null) {
						sql += "and e.exam_num = '"+exam_num+"' ";
					}
			List<BatchDTO> list = this.jqm.getList(sql,BatchDTO.class);
			if(list != null && list.size()>0) {
				return list.get(0);
			}
			return null;
		}

		@Override
		public BatchDTO getBathByGroupid(long group_id,String center_num) throws ServiceException {
			String sql = "select b.id,b.company_id ,b.batch_num ,b.batch_name ,b.checktype from batch b , group_info g where g.batch_id = b.id and b.center_num='"+center_num+"' and g.id = '"+group_id+"'";
			List<BatchDTO> list = this.jqm.getList(sql, BatchDTO.class);
			return list.get(0);
		}
		

		@Override
		public String updateGroupStatusById(BatchModel model, UserDTO user) throws ServiceException {
			String sql = "update  group_info set status = '"+model.getStatus()+"' where id = "+model.getId();
			 this.jpm.execSql(sql);
			return "ok-操作成功。";
		}
		
		@Override
		public String checkAccountflagAndOverflag(long batchId) throws ServiceException {
			if(batchId == 0) {//个检
				return "ok";
			}
            Batch batch = (Batch)qm.load(Batch.class, batchId);
            if ("1".equals(batch.getOverflag())) {
                return "error-批次任务已经封帐，操作不能继续";
            }
            if(batch.getAccountflag()==1) {
            	return "error-批次任务已经锁定，操作不能继续";
            }
            return "ok";
		}
}
