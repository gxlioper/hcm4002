package com.hjw.wst.service.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.hsqldb.lib.StringUtil;

import com.hjw.util.DateTimeUtil;
import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.wst.DTO.ThridInterfaceLogDto;
import com.hjw.wst.DTO.ThridInterfaceLogLisResultDto;
import com.hjw.wst.DTO.ThridInterfaceLogPaceResultDto;
import com.hjw.wst.model.ThridInterfaceLogModel;
import com.hjw.wst.service.ThridInterfaceLogService;
import com.synjones.framework.exception.ServiceException;
import com.synjones.framework.persistence.JdbcPersistenceManager;
import com.synjones.framework.persistence.JdbcQueryManager;
import com.synjones.framework.persistence.PersistenceManager;
import com.synjones.framework.persistence.QueryManager;
import com.synjones.framework.support.PageSupport;

public class ThridInterfaceLogServiceImpl implements ThridInterfaceLogService{
	
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
	public PageReturnDTO queryThirdInterfaceLog(ThridInterfaceLogModel model, String center_num, int pageno, int pagesize)
			throws ServiceException {
		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT til.id, til.req_no, til.exam_no, til.message_id, "
				+ "( SELECT config_remark FROM webservice_configuration WHERE config_key = til.message_name and center_num='"+center_num+"' ) AS message_name, "
				+ "til.message_date, til.message_type, til.sender, til.receiver, "
				+ "til.flag, til.xtgnb_id ,til.message_inout FROM thrid_interface_log til ");
		sb.append(" where 1=1 ");
		
		if (model.getReq_no() != null && !"".equals(model.getReq_no())) {
			sb.append(" and til.req_no='" + model.getReq_no() + "' ");
		}
		
		if (model.getExam_no() != null && !"".equals(model.getExam_no())) {
			sb.append(" and til.exam_no='" + model.getExam_no() + "' ");
		}
		
		if (model.getMessage_name() != null && !"".equals(model.getMessage_name())) {
			sb.append(" and til.message_name like '%" + model.getMessage_name() + "%' ");
		}
		
		if (model.getMessage_type() != null && !"".equals(model.getMessage_type())) {
			sb.append(" and til.message_type='" + model.getMessage_type() + "' ");
		}
		
		if (model.getFlag() != null) {
			sb.append(" and til.flag='" + model.getFlag() + "' ");
		}
		
		if (!StringUtil.isEmpty(model.getMessage_startDate())) {
			sb.append(" and til.message_date >= '"+model.getMessage_startDate() +"'");
		}
		
		if (!StringUtil.isEmpty(model.getMessage_endDate())) {
			sb.append(" and til.message_date < '"+model.getMessage_endDate()+"'");
		}
		
		if (model.getSender() != null && !"".equals(model.getSender())) {
			sb.append(" and til.sender='" + model.getSender() + "' ");
		}
		
		if (model.getReceiver() != null && !"".equals(model.getReceiver())) {
			sb.append(" and til.receiver='" + model.getReceiver() + "' ");
		}
		PageSupport map = this.jqm.getList(sb.toString(), pageno, pagesize, ThridInterfaceLogDto.class);
		List<ThridInterfaceLogDto> list = new ArrayList<ThridInterfaceLogDto>();
		PageReturnDTO webrole = new PageReturnDTO();
		webrole.setPage(pageno);
		webrole.setRp(pagesize);
		if ((map != null) && (map.getList() != null) && (map.getList().size() > 0)) {
			webrole.setTotal(map.getRecTotal());
			for (int i = 0; i < map.getList().size(); i++) {
				ThridInterfaceLogDto eu = new ThridInterfaceLogDto();
				eu = (ThridInterfaceLogDto) map.getList().get(i);

				if ((eu.getMessage_date() != null) && (eu.getMessage_date().trim().length() > 19)) {
					eu.setMessage_date(eu.getMessage_date().trim().substring(0, 19));
					if ("1900-01-01 24:00".equals(eu.getMessage_date())) {
						eu.setMessage_date("");
					}
				}

				list.add(eu);
			}
			webrole.setRows(list);
		}
		return webrole;
	}

	@Override
	public PageReturnDTO getLogDetailDrid(String tilId, int pageno, int pagesize) throws ServiceException {
		StringBuffer sb = new StringBuffer();
		sb.append("select timl.id,timl.til_id,timl.ldate,timl.lmessage "
				+ "from thrid_interface_message_log timl ");
		sb.append(" where 1=1 ");
		
		if (tilId != null && !"".equals(tilId)) {
			sb.append(" and timl.til_id ='" + tilId + "' ");
		}
		
		PageSupport map = this.jqm.getList(sb.toString(), pageno, pagesize, ThridInterfaceLogDto.class);
		List<ThridInterfaceLogDto> list = new ArrayList<ThridInterfaceLogDto>();
		PageReturnDTO webrole = new PageReturnDTO();
		webrole.setPage(pageno);
		webrole.setRp(pagesize);
		if ((map != null) && (map.getList() != null) && (map.getList().size() > 0)) {
			webrole.setTotal(map.getRecTotal());
			for (int i = 0; i < map.getList().size(); i++) {
				ThridInterfaceLogDto eu = new ThridInterfaceLogDto();
				eu = (ThridInterfaceLogDto) map.getList().get(i);
				//截取字符串长度
				if ((eu.getLmessage() != null) && (eu.getLmessage().trim().length() > 18)) {
					eu.setLmessage(eu.getLmessage().trim().substring(0, 18));
				}
				list.add(eu);
			}
			webrole.setRows(list);
		}
		return webrole;
		
	}

	@Override
	public ThridInterfaceLogDto viewLogDetailPage(String id) throws ServiceException {
		String sql= " select til.* from thrid_interface_log til where til.id ='"+id+"' ";
		List<ThridInterfaceLogDto> list = this.jqm.getList(sql, ThridInterfaceLogDto.class);
		if(list.size() !=0){
			return list.get(0);
		}
		return null;
	}

	@Override
	public List<ThridInterfaceLogDto> queryLogDetailList(String id) throws ServiceException {
		String sql= " SELECT timl.id, timl.til_id, timl.ldate, timl.lmessage "
				+ "FROM thrid_interface_message_log timl "
				+ "WHERE timl.til_id = '"+id+"' ORDER BY timl.seq_code ";
		List<ThridInterfaceLogDto> list = this.jqm.getList(sql, ThridInterfaceLogDto.class);
		return list;
	}

	@Override
	public ThridInterfaceLogDto viewDetailLogQuery(String id) throws ServiceException {
		String sql= "SELECT timl.id, timl.til_id, timl.ldate, timl.lmessage "
				+ "FROM thrid_interface_message_log timl WHERE id ='"+id+"' ORDER BY timl.seq_code ";
		List<ThridInterfaceLogDto> list = this.jqm.getList(sql, ThridInterfaceLogDto.class);
		if(list.size() !=0){
			return list.get(0);
		}
		return null;
	}

	@Override
	public List<ThridInterfaceLogDto> viewDetailLogList(String id) throws ServiceException {
		
		String sql= "SELECT timl.id, timl.til_id, timl.ldate, timl.lmessage "
				+ "FROM thrid_interface_message_log timl WHERE id in ( "+id+" ) ";
		List<ThridInterfaceLogDto> list = this.jqm.getList(sql, ThridInterfaceLogDto.class);
		return list;
		
	}

	@Override
	public String deleteDetailLogMsg(String ids) throws ServiceException {
		String sql = "";
		Connection connection = null;
		String str = "error-删除失败";
		try {
			connection = this.jqm.getConnection();
			sql = "DELETE from  thrid_interface_message_log where id in (" + ids + ") ";
			connection.createStatement().executeUpdate(sql);
			
			str = " ok-删除成功";
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
		return str;
	}

	@Override
	public String deleteLogMaterAndDetail(String ids) throws ServiceException {
		Connection connection = null;
		String str = "error-删除失败";
		try {
			connection = this.jqm.getConnection();
			//删除附表
			String sql = "DELETE from  thrid_interface_message_log where til_id in ('" + ids + "') ";
			connection.createStatement().executeUpdate(sql);
			//删除主表
			String sql2 = "DELETE from  thrid_interface_log where id in ('" + ids + "') ";
			connection.createStatement().executeUpdate(sql2);
			str = " ok-删除成功";
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
		return str;
	}

	@Override
	public List<ThridInterfaceLogDto> queryAllMessageName() throws ServiceException {
		
		String sql= " select wsc.config_key,wsc.config_url,wsc.config_method,wsc.config_remark from webservice_configuration wsc ORDER BY config_remark";
		List<ThridInterfaceLogDto> list = this.jqm.getList(sql, ThridInterfaceLogDto.class);
		return list;
		
	}

	@Override
	public ThridInterfaceLogLisResultDto viewLogDetailLis(String til_id) throws ServiceException {
		String sql= " select id,exam_num,sample_barcode,lis_item_code,lis_item_name,"
				+ "report_item_code,report_item_name,exam_date,item_result,item_unit,"
				+ "flag,ref,doctor,sh_doctor,audit_date,note,read_flag,create_time "
				+ "from lis_result  where til_id ='"+til_id+"' ";
		List<ThridInterfaceLogLisResultDto> list = this.jqm.getList(sql, ThridInterfaceLogLisResultDto.class);
		if(list.size() !=0){
			return list.get(0);
		}
		return null;
	}

	@Override
	public ThridInterfaceLogPaceResultDto viewLogDetailPacs(String til_id) throws ServiceException {
		String sql= " select id,req_no,pacs_checkno,exam_num,item_name,pacs_item_code,study_type,"
				+ "study_body_part,clinic_diagnose,clinic_advice,is_abnormal,report_img_path,"
				+ "img_path,study_state,reg_doc,check_doc,check_date,report_doc,report_date,"
				+ "audit_doc,audit_date,note,status,trans_date,is_tran_image,is_report_image,"
				+ "create_time from pacs_result where til_id ='"+til_id+"' ";
		List<ThridInterfaceLogPaceResultDto> list = this.jqm.getList(sql, ThridInterfaceLogPaceResultDto.class);
		if(list.size() !=0){
			return list.get(0);
		}
		return null;
	}

}
