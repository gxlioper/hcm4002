package com.hjw.wst.service.impl;

import java.util.List;

import com.hjw.util.DateTimeUtil;
import com.hjw.wst.DTO.ExamInfoDTO;
import com.hjw.wst.DTO.ReportReceiveDTO;
import com.hjw.wst.DTO.UserDTO;
import com.hjw.wst.domain.ExamInfo;
import com.hjw.wst.domain.ReportReceive;
import com.hjw.wst.model.RegisterModel;
import com.hjw.wst.service.ReportReceiveService;
import com.synjones.framework.exception.ServiceException;
import com.synjones.framework.persistence.JdbcPersistenceManager;
import com.synjones.framework.persistence.JdbcQueryManager;
import com.synjones.framework.persistence.PersistenceManager;
import com.synjones.framework.persistence.QueryManager;

/**
 * 
     * @Title:  火箭蛙体检管理系统   
     * @Package com.hjw.wst.service   
     * @Description:  报告领取方式
     * @author: zr     
     * @date:   2017年2月22日 下午3:42:13   
     * @version V2.0.0.0
 */
public   class ReportReceiveImpl  implements   ReportReceiveService{
	
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
	/**
	 * 
	     * @Title: getReportReceive   
	     * @Description: TODO(获取报告领取方式)   
	     * @param: @param e_num
	     * @param: @return
	     * @param: @throws SQLException      
	     * @return:
	     * @throws
	 */
	public  ExamInfoDTO getReportReceive(String e_num)  throws  ServiceException{
		String hql="  SELECT exam_num,reportAddress,getReportWay FROM   exam_info  where   exam_num='"+e_num+"'";
		List<ExamInfoDTO> re =  this.jqm.getList(hql, ExamInfoDTO.class);
		return  re!=null&&re.size()>0?re.get(0):null;
	}
	public  void  saveReportReceive(RegisterModel  model,UserDTO  user) throws   ServiceException{
		String hql="From  ExamInfo  where   exam_num='"+model.getExam_num().trim()+"'";
		List<ExamInfo> ex =  qm.find(hql);
		ex.get(0).setGetReportWay(model.getReceive_type());
		ex.get(0).setReportAddress(model.getReceive_address());
		ex.get(0).setZiqu_report_time(model.getZiqu_report_time());
		this.pm.update(ex.get(0));
	
	}
}
