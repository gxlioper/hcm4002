package com.hjw.wst.service.impl;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.fr.third.org.hsqldb.lib.StringUtil;
import com.hjw.util.DateTimeUtil;
import com.hjw.util.IsPrivateCenterConfig;
import com.hjw.wst.DTO.ExamInfoDTO;
import com.hjw.wst.DTO.ExamInfoUserDTO;
import com.hjw.wst.DTO.GroupSetDTO;
import com.hjw.wst.DTO.JobDTO;
import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.wst.DTO.ReportReceiveDTO;
import com.hjw.wst.DTO.UserDTO;
import com.hjw.wst.domain.ExamInfo;
import com.hjw.wst.domain.ReportReceive;
import com.hjw.wst.model.ReportManagerModel;
import com.hjw.wst.service.BatchService;
import com.hjw.wst.service.ReportManagerService;
import com.synjones.framework.exception.ServiceException;
import com.synjones.framework.persistence.JdbcPersistenceManager;
import com.synjones.framework.persistence.JdbcQueryManager;
import com.synjones.framework.persistence.PersistenceManager;
import com.synjones.framework.persistence.QueryManager;
import com.synjones.framework.support.PageSupport;

public class ReportManagerServiceImpl implements ReportManagerService {

	private QueryManager qm;
	private JdbcQueryManager jqm;
	private JdbcPersistenceManager jpm;
	private PersistenceManager pm;
	private BatchService batchService;	
	
	public void setBatchService(BatchService batchService) {
		this.batchService = batchService;
	}

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
	public PageReturnDTO getExamInfoList(ReportManagerModel model,int pagesize, int pageno,String sort,String order,UserDTO user) throws ServiceException{
		String isprivateflag = IsPrivateCenterConfig.isprivateflag(user);
		StringBuffer sb = new StringBuffer();
		sb.append("select CONVERT(varchar(50),r.receive_date,23) as receive_date,c.arch_num,e.exam_num,dbo.fun_CharToStar(e.exam_num,'id_num',c.id_num,'"+isprivateflag+"') as id_num,e.id,dbo.fun_CharToStar(e.exam_num,'name',c.user_name,'"+isprivateflag+"') as user_name,c.sex,e.age,"
				+ "dbo.fun_CharToStar(e.exam_num,'phone',e.phone,'"+isprivateflag+"') as phone,e.is_marriage,CONVERT(varchar(50),e.join_date,20) as join_date,(e.final_doctor +'/'+ CONVERT(varchar(50),e.final_date,20)) as final_date,"
				+ "e.is_report_print,dbo.GetExamSetNameByExamID(e.exam_num) as set_name,e.exam_type,v.com_name company,e.is_report_tidy,"
				+ "(case when r.receive_type is null then '0' else r.receive_type end) as receive_type, "
				+ "(select top 1 r.print_doctor from examinfo_print_report r where r.examinfo_id = e.id and r.rep_type='P' order by r.print_time desc) print_doctor,"
				+ "(select top 1 r.print_time from examinfo_print_report r where r.examinfo_id = e.id and r.rep_type='P' order by r.print_time desc) print_time "
				+ " ,(e.report_tidy_user + '/' + CONVERT(varchar(50),e.report_tidy_time,20)) report_tidy_user,(u.chi_name +'/'+ CONVERT(varchar(50),es.check_time,20)) as update_time ,e.getReportWay "
				+ "from customer_info c,exam_info e "
				   + " left join company_info v on v.id=e.company_id "
				+ "left join report_receive r on r.exam_num = e.exam_num "
				+ "left join exam_summary es on e.exam_num = es.exam_num   and es.center_num = '"+user.getCenter_num()+"'  "
				+ "left join user_usr u on u.id = es.check_doc "
				+ "where c.id = e.customer_id and e.is_Active = 'Y' ");
		int count = 0;
		if(model.getArch_num() != null && !"".equals(model.getArch_num())){
			sb.append(" and c.arch_num = '"+model.getArch_num()+"'");
			count ++;
		}
		if(!"".equals(model.getExam_num())){
			sb.append(" and e.exam_num = '"+model.getExam_num()+"'");
			count ++;
		}
		if(model.getId_num() != null && !"".equals(model.getId_num())){
			sb.append(" and c.id_num = '"+model.getId_num()+"'");
			count ++;
		}
		if(model.getUser_name() != null && !"".equals(model.getUser_name())){
			sb.append(" and c.user_name like '"+model.getUser_name()+"%'");
			count ++;
		}
		if(model.getPhone() != null && !"".equals(model.getPhone())){
			sb.append(" and e.phone = '"+model.getPhone()+"'");
			count ++;
		}
		if(model.getJoin_date() != null && !"".equals(model.getJoin_date())){
			sb.append(" and e.join_date >= '"+model.getJoin_date()+" 00:00:00'");
			count ++;
		}
		if(model.getJoin_date1() != null && !"".equals(model.getJoin_date1())){
			sb.append(" and e.join_date <= '"+model.getJoin_date1()+" 23:59:59'");
			count ++;
		}
		if(model.getCom_name() != null && !"".equals(model.getCom_name())){
			sb.append(" and e.company_id = "+model.getCom_name());
			count++;
		}
		if(model.getIs_report_print() != null && !"".equals(model.getIs_report_print())){
			sb.append(" and e.is_report_print = '"+model.getIs_report_print() + "'");
		}
		if(model.getIs_report_tidy() != null && !"".equals(model.getIs_report_tidy())){
			sb.append(" and e.is_report_tidy = '"+model.getIs_report_tidy() + "'");
		}
		if(model.getReceive_type() != null && !"".equals(model.getReceive_type())){
			if("0".equals(model.getReceive_type())){
				sb.append(" and r.receive_type is null ");
			}else{
				sb.append(" and r.receive_type = " + model.getReceive_type());
			}
		}
		if(model.getCreate_time() != null && !"".equals(model.getCreate_time())){
			sb.append(" and r.receive_date >= '"+model.getCreate_time()+"'" );
			count ++;
		}
		if(model.getCreate_time1() != null && !"".equals(model.getCreate_time1())){
			sb.append(" and r.receive_date <= '"+model.getCreate_time1()+"'" );
			count ++;
		}
		
		if(model.getReport_tidy_time() != null && !"".equals(model.getReport_tidy_time())){
			sb.append(" and e.report_tidy_time >= '"+model.getReport_tidy_time()+"'" );
			count ++;
		}
		if(model.getReport_tidy_time1() != null && !"".equals(model.getReport_tidy_time1())){
			sb.append(" and e.report_tidy_time <= '"+model.getReport_tidy_time1()+"'" );
			count ++;
		}
		if(count == 0){
			sb.append(" and e.exam_num = '' ");
		}
		if(sort != null && !"".equals(sort)){
			if("create_time".equals(sort)){
				sb.append(" order by print_time "+order);
			}else{
				sb.append(" order by "+sort+" "+order);
			}
		}
		PageSupport map = this.jqm.getList(sb.toString(), pageno, pagesize, ExamInfoDTO.class);
		PageReturnDTO pageDto = new PageReturnDTO();
		pageDto.setPage(pageno);
		pageDto.setRp(pagesize);
		pageDto.setTotal(map.getRecTotal());
		pageDto.setRows(map.getList());
		return pageDto;
	}

	@Override
	@SuppressWarnings("unchecked")
	public ExamInfo getExamInfoByExamNum(String examNum) throws ServiceException {
		String sql = "from ExamInfo e where e.exam_num = '"+examNum.trim()+"'";
		List<ExamInfo> list = this.qm.find(sql);
		if(list.size() != 0){
			return list.get(0);
		}
		return null;
	}

	@Override
	public ExamInfo updateExamInfo(ExamInfo examInfo) throws ServiceException {
		this.pm.update(examInfo);
		return examInfo;
	}

	@Override
	@SuppressWarnings("unchecked")
	public ReportReceive getInitializeReport(String exam_num) throws ServiceException {
		
		String sql = " select e.exam_num, c.user_name as receive_name, "
				+ " e.phone as receive_phone , (case when e.getReportWay = '1' then e.reportAddress else e.address end) as receive_address "
				+ " from exam_info e,customer_info c where c.id = e.customer_id "
				+ " and e.exam_num = '"+exam_num+"'";
		List<ReportReceive> list = this.jqm.getList(sql, ReportReceive.class);
		if(list.size() != 0){
			return list.get(0);
		}
		return null;
	}

	@Override
	public ReportReceive saveReportRecive(ReportReceive reportReceive) throws ServiceException {
		this.pm.save(reportReceive);
		return reportReceive;
	}
	
	@Override
	public String updateReportRemarke(String exam_num,String remarke,UserDTO user) throws ServiceException{
		List<ReportReceive> list = this.qm.find("from ReportReceive r where r.exam_num = '"+exam_num+"'");
		if(list.size() != 0){
			ReportReceive reportReceive = list.get(0);
			reportReceive.setReceive_remark(remarke);
			reportReceive.setUpdater(user.getUserid());
			reportReceive.setCreate_time(DateTimeUtil.parse());
			this.pm.update(reportReceive);
			return "修改备注成功!";
		}
		return "";
	}

	@Override
	@SuppressWarnings("unchecked")
	public ReportReceiveDTO getReportReceive(String exam_num) throws ServiceException {
		String sql = " select r.*,c.user_name from report_receive r,exam_info e,customer_info c "
				   + " where r.exam_num = e.exam_num and e.customer_id = c.id"
				   + " and e.exam_num = '"+exam_num+"'";
		List<ReportReceiveDTO> list = this.jqm.getList(sql, ReportReceiveDTO.class);
		if(list.size() != 0){
			return list.get(0);
		}
		return null;
	}

	@Override
	public void deleteReportReceive(String exam_nums) throws ServiceException {
		String sql = "delete report_receive where exam_num in ("+exam_nums+")";
		this.jpm.execSql(sql);
	} 
	
	/**
	 * 
	     * @Title: GetzocforcomBatch   
	     * @Description: 通过单位批次获取职业体检类型  
	     * @param: @param comid
	     * @param: @param batchid
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<JobDTO>      
	     * @throws
	 */
	public List<JobDTO> GetzocforcomBatch(long comid,long batchid) throws ServiceException{
		String sqltext = "select zoc.occuphyexaclassID,zoc.occuphyexaclass_name from  "
				+ "zyb_occuphyexaclass zoc where zoc.occuphyexaclassID in("
				+ " select zeo.occuphyexaclassid from exam_info ei,zyb_exam_occuhazardfactors zeo "
				+ "where ei.exam_num=zeo.exam_num and ei.company_id='"+comid+"' and ei.batch_id='"+batchid+"' "
						+ "group by zeo.occuphyexaclassid"
				+ ") order by zoc.[order]";
		Connection connection = null;
		Statement statement = null;
		List<JobDTO> list = new ArrayList<JobDTO>();
		try {
			// 读取记录数
			connection = this.jqm.getConnection();
			statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(sqltext);
			while (rs.next()) {
				JobDTO jd =new JobDTO();
				jd.setId(rs.getString("occuphyexaclassID"));
				jd.setName(rs.getString("occuphyexaclass_name"));
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
	     * @Title: GetzofforcomBatch   
	     * @Description: 通过单位批次获取职业危害因素类型
	     * @param: @param comid
	     * @param: @param batchid
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<JobDTO>      
	     * @throws
	 */
	public List<JobDTO> GetzofforcomBatch(long comid,long batchid) throws ServiceException{
		String sqltext = "select zof.hazardfactorsID,zof.hazard_code,zof.hazard_name from  "
				+ "zyb_occuhazardfactors zof where zof.hazard_code in("
				+ " select zeo.hazard_code from exam_info ei,zyb_exam_occuhazardfactors zeo "
				+ "where ei.exam_num=zeo.exam_num and ei.company_id='"+comid+"' and ei.batch_id='"+batchid+"' "
				+ "  group by zeo.hazard_code) order by zof.[order]";
		Connection connection = null;
		Statement statement = null;
		List<JobDTO> list = new ArrayList<JobDTO>();
		try {
			// 读取记录数
			connection = this.jqm.getConnection();
			statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(sqltext);
			while (rs.next()) {
				JobDTO jd =new JobDTO();
				jd.setId(rs.getString("hazard_code"));
				jd.setName(rs.getString("hazard_name"));
				jd.setRemark(rs.getString("hazardfactorsID"));
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
	     * @Title: getTermReportUserZybList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param com_id
	     * @param: @param batchid
	     * @param: @param exam_num
	     * @param: @param zytjlx
	     * @param: @param status
	     * @param: @param username
	     * @param: @param zywhys
	     * @param: @param levels
	     * @param: @param tjlx
	     * @param: @param userid
	     * @param: @param centernum
	     * @param: @param pagesize
	     * @param: @param pageno
	     * @param: @param sort
	     * @param: @param order
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: PageReturnDTO      
	     * @throws
	 */
	public PageReturnDTO getTermReportUserZybList(long com_id, long batchid, String comon_report_type, String exam_num, String zytjlx,
			String status,String is_report_print,String username, String zywhys, String levels,String tjlx,
			long userid, String centernum,String chkItem, int pagesize, int pageno, String sort, String order,UserDTO user,String center_num)
			throws ServiceException{
		String isprivateflag = IsPrivateCenterConfig.isprivateflag(user);
		StringBuffer sb = new StringBuffer();
		sb.append("select distinct c.id, (select da.data_name from data_dictionary da where da.data_code = 'TJLX' AND da.id = c.comon_report_type) as comonReportType ,"
				+ " dbo.fun_CharToStar(c.exam_num,'name',a.user_name,'"+isprivateflag+"') as user_name,a.arch_num,dbo.fun_CharToStar(c.exam_num,'id_num',a.id_num,'"+isprivateflag+"') as id_num,a.sex,c.age,c.exam_num,c.is_marriage,"
				+ "c.position,c._level,c.remarke,c.others,c.status,c.exam_type"
				+ ",c.employeeID");
		sb.append(" ,c.is_need_barcode,c.is_need_guide,dbo.fun_CharToStar(c.exam_num,'phone',c.phone,'"+isprivateflag+"') as phone,"
				+ "c.register_date,c.join_date,c.exam_times,x.chi_name,c.is_guide_back,c.opreport_print_status is_report_print,t.data_name as tjlx,u.dep_name as deptname, c.update_time");//,"+ "zeo.occuphyexaclassid as zytjlx,zeo.hazard_code as zywhys,zoc.occuphyexaclass_name as zytjlxname,zof.hazard_name as zywhysname 
		sb.append(" from customer_info a ,exam_info c ");
		sb.append(" left join company_department u on u.id=c._level" );
		sb.append(" left join data_dictionary t on t.id=c.customer_type ");
		sb.append(" left join user_usr x on x.id=c.creater ");
		sb.append(" ,zyb_exam_occuhazardfactors zeo ");
		sb.append(" left join zyb_occuphyexaclass zoc on zoc.occuphyexaclassID=zeo.occuphyexaclassid ");
		sb.append(" left join zyb_occuhazardfactors zof on zof.hazard_code=zeo.hazard_code ");
		sb.append(" where c.customer_id=a.id and c.exam_num=zeo.exam_num");
		sb.append(" and c.is_Active='Y' and c.exam_type='T' and c.apptype='2' ");
		sb.append(" and  c.center_num='" + centernum + "' ");
		
		if(chkItem.trim().indexOf("serch") < 0){//不包含
			
			if(com_id > 0 || chkItem.trim().indexOf("exam_num") < 0) {
				sb.append(" and c.company_id=" + com_id + " ");
			}
			if(batchid > 0 || chkItem.trim().indexOf("exam_num") < 0) {
				sb.append(" and c.batch_id = '" + batchid + "' ");
			}
		}
		
		if (!StringUtil.isEmpty(exam_num) || chkItem.trim().indexOf("serch") > 0) {
			sb.append(" and c.exam_num = '" + exam_num + "' ");
		}
		if (!StringUtil.isEmpty(tjlx)) {
			sb.append(" and c.customer_type in (" + tjlx + ") ");
		}

		if (!StringUtil.isEmpty(zytjlx)) {
			sb.append(" and zeo.occuphyexaclassid in (" + zytjlx + ") ");
		}

		if (!StringUtil.isEmpty(zywhys)) {
			sb.append(" and zeo.hazard_code in (" + zywhys + ") ");
		}

		if (!StringUtil.isEmpty(username)) {
			sb.append(" and a.user_name like '%" + username + "%' ");
		}

		if (!StringUtil.isEmpty(levels)) {
			sb.append(" and c._level in(" + levels + ") ");
		}

		if (!StringUtil.isEmpty(status)) {
			sb.append(" and c.status in (" + status + ") ");
		}
		
		if (!StringUtil.isEmpty(is_report_print)) {
			sb.append(" and c.opreport_print_status = '" + is_report_print + "' ");
		}
		
		//--类型
		if (!StringUtil.isEmpty(comon_report_type)) {
			sb.append(" and c.comon_report_type = '" + comon_report_type + "' ");
		}
		
		if (sort != null && !"".equals(sort)) {
			sb.append(" order by " + sort + " " + order);
		} else {
			sb.append(" order by c.update_time desc ");
		}
		PageSupport map = this.jqm.getList(sb.toString(), pageno, pagesize, ExamInfoUserDTO.class);
		List<ExamInfoUserDTO> list = new ArrayList<ExamInfoUserDTO>();
		PageReturnDTO webrole = new PageReturnDTO();
		webrole.setPage(pageno);
		webrole.setRp(pagesize);
		if ((map != null) && (map.getList() != null) && (map.getList().size() > 0)) {
			webrole.setTotal(map.getRecTotal());
			for (int i = 0; i < map.getList().size(); i++) {
				ExamInfoUserDTO eu = new ExamInfoUserDTO();
				eu = (ExamInfoUserDTO) map.getList().get(i);

				String setnums = "";
				PageReturnDTO batchlist = this.batchService.getGroupSetList(eu.getGroup_id(), 1000, 1,center_num);
				if ((batchlist != null) && (batchlist.getRows() != null) && (batchlist.getRows().size() > 0)) {
					for (int j = 0; j < batchlist.getRows().size(); j++) {
						GroupSetDTO gs = new GroupSetDTO();
						gs = (GroupSetDTO) batchlist.getRows().get(j);
						setnums = setnums + "," + gs.getSet_name();
					}
					if (setnums.length() > 1)
						setnums = setnums.trim().substring(1, setnums.trim().length());
					eu.setSet_name(setnums);
				}

				if ((eu.getRegister_date() != null) && (eu.getRegister_date().trim().length() > 10)) {
					eu.setRegister_date(eu.getRegister_date().trim().substring(0, 10));
					if (eu.getRegister_date().indexOf("1900-01-01") >= 0) {
						eu.setRegister_date("");
					}
				}

				if ((eu.getJoin_date() != null) && (eu.getJoin_date().trim().length() > 10)) {
					eu.setJoin_date(eu.getJoin_date().trim().substring(0, 10));
					if (eu.getJoin_date().indexOf("1900-01-01") >= 0) {
						eu.setJoin_date("");
					}
				}

				eu.setRemark1(eu.getIs_need_guides() + "/" + eu.getIs_need_barcodes() + "/" + eu.getStatuss());

				eu.setLis(eu.getYlis() + "/" + eu.getWlis());
				eu.setPacs(eu.getYpacs() + "/" + eu.getWpacs());
				list.add(eu);
			}
			webrole.setRows(list);
		}
		return webrole;
	}
	
	/**
	 * 
	     * @Title: getTermReportUserList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param com_id
	     * @param: @param batchid
	     * @param: @param exam_num
	     * @param: @param groupid
	     * @param: @param status
	     * @param: @param sex
	     * @param: @param username
	     * @param: @param set_id
	     * @param: @param rylb
	     * @param: @param levels
	     * @param: @param tjlx
	     * @param: @param userid
	     * @param: @param centernum
	     * @param: @param pagesize
	     * @param: @param pageno
	     * @param: @param sort
	     * @param: @param order
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: PageReturnDTO      
	     * @throws
	 */
	public PageReturnDTO getTermReportUserList(long com_id, long batchid, String exam_num, long groupid,
			String status, String sex, String username, long set_id, String rylb, String levels,String tjlx,
			long userid, String centernum,String chkItem, int pagesize, int pageno, String sort, String order,UserDTO user,String center_num)
			throws ServiceException {
		String isprivateflag = IsPrivateCenterConfig.isprivateflag(user);//判断用户是否有隐私权限 Y 有 N 无
		if(com_id==0) {com_id=-1;}
		if(batchid==0){batchid=-1;}
		StringBuffer sb = new StringBuffer();
		sb.append("select c.id,dbo.fun_CharToStar(c.exam_num,'name',a.user_name,'"+isprivateflag+"') as user_name,a.arch_num,dbo.fun_CharToStar(c.exam_num,'id_num',a.id_num,'"+isprivateflag+"') as id_num,a.sex,c.age,c.exam_num,c.is_marriage,"
				+ "c.position,c._level,c.group_id,n.group_name,c.remarke,c.others,c.status,c.exam_type"
				+ ",c.employeeID");
		sb.append(" ,c.is_need_barcode,c.is_need_guide,dbo.fun_CharToStar(c.exam_num,'phone',c.phone,'"+isprivateflag+"') as phone,n.group_name,"
				+ "c.register_date,c.join_date,c.exam_times,x.chi_name,c.is_guide_back,t.data_name as tjlx,u.dep_name as deptname ");
		sb.append(" from customer_info a ,exam_info c ");
		sb.append(" left join company_department u on u.id=c._level" );
		sb.append(" left join data_dictionary t on t.id=c.customer_type ");
		sb.append(" left join user_usr x on x.id=c.creater ");
		sb.append(" left join group_info  n on n.id=c.group_id ");
		sb.append(" left join examinfo_set m on m.exam_num=c.exam_num and m.isActive='Y' ");
		sb.append(" where c.customer_id=a.id ");
		sb.append(" and c.is_Active='Y' and c.exam_type='T' and c.apptype='1' ");
		sb.append(" and  c.center_num='" + centernum + "' ");
		if(chkItem.trim().indexOf("serch") < 0){
			sb.append(" and c.company_id=" + com_id + " ");
			sb.append(" and c.batch_id = '" + batchid + "' ");
		}
		if (!StringUtil.isEmpty(exam_num) || chkItem.trim().indexOf("serch") > 0) {
			sb.append(" and c.exam_num = '" + exam_num + "' ");
		}
		
		if (!StringUtil.isEmpty(tjlx)) {
			sb.append(" and c.customer_type in (" + tjlx + ") ");
		}

		if (!StringUtil.isEmpty(rylb)) {
			sb.append(" and c.customer_type_id in (" + rylb + ") ");
		}

		if (groupid > 0) {
			sb.append(" and c.group_id = '" + groupid + "' ");
		}

		if (!StringUtil.isEmpty(username)) {
			sb.append(" and a.user_name like '%" + username + "%' ");
		}

		if (!StringUtil.isEmpty(sex)) {
			sb.append(" and a.sex = '" + sex + "' ");
		}

		if (!StringUtil.isEmpty(levels)) {
			sb.append(" and c._level in(" + levels + ") ");
		}

		if (!StringUtil.isEmpty(status)) {
			sb.append(" and c.status in (" + status + ") ");
		}

		if (set_id > 0) {
			sb.append(" and m.exam_set_id= '" + set_id + "' ");
		}
		
		if (sort != null && !"".equals(sort)) {
			sb.append(" order by " + sort + " " + order);
		} else {
			sb.append(" order by c.update_time desc ");
		}
		PageSupport map = this.jqm.getList(sb.toString(), pageno, pagesize, ExamInfoUserDTO.class);
		List<ExamInfoUserDTO> list = new ArrayList<ExamInfoUserDTO>();
		PageReturnDTO webrole = new PageReturnDTO();
		webrole.setPage(pageno);
		webrole.setRp(pagesize);
		if ((map != null) && (map.getList() != null) && (map.getList().size() > 0)) {
			webrole.setTotal(map.getRecTotal());
			for (int i = 0; i < map.getList().size(); i++) {
				ExamInfoUserDTO eu = new ExamInfoUserDTO();
				eu = (ExamInfoUserDTO) map.getList().get(i);

				String setnums = "";
				PageReturnDTO batchlist = this.batchService.getGroupSetList(eu.getGroup_id(), 1000, 1,center_num);
				if ((batchlist != null) && (batchlist.getRows() != null) && (batchlist.getRows().size() > 0)) {
					for (int j = 0; j < batchlist.getRows().size(); j++) {
						GroupSetDTO gs = new GroupSetDTO();
						gs = (GroupSetDTO) batchlist.getRows().get(j);
						setnums = setnums + "," + gs.getSet_name();
					}
					if (setnums.length() > 1)
						setnums = setnums.trim().substring(1, setnums.trim().length());
					eu.setSet_name(setnums);
				}

				if ((eu.getRegister_date() != null) && (eu.getRegister_date().trim().length() > 10)) {
					eu.setRegister_date(eu.getRegister_date().trim().substring(0, 10));
					if (eu.getRegister_date().indexOf("1900-01-01") >= 0) {
						eu.setRegister_date("");
					}
				}

				if ((eu.getJoin_date() != null) && (eu.getJoin_date().trim().length() > 10)) {
					eu.setJoin_date(eu.getJoin_date().trim().substring(0, 10));
					if (eu.getJoin_date().indexOf("1900-01-01") >= 0) {
						eu.setJoin_date("");
					}
				}

				eu.setRemark1(eu.getIs_need_guides() + "/" + eu.getIs_need_barcodes() + "/" + eu.getStatuss());

				eu.setLis(eu.getYlis() + "/" + eu.getWlis());
				eu.setPacs(eu.getYpacs() + "/" + eu.getWpacs());
				list.add(eu);
			}
			webrole.setRows(list);
		}
		return webrole;
	}

	@Override
	public void updateReportEdit(String ids, String comon_report_type) throws ServiceException {
		
		String sql= "update exam_info set comon_report_type = '"+comon_report_type+"' where id in ("+ids+") ";
		this.jpm.execSql(sql);
	}
}
