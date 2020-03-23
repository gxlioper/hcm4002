package com.hjw.crm.service;

import java.sql.SQLException;
import java.util.List;

import com.hjw.crm.domain.CrmVisitPlan;
import com.hjw.crm.model.CrmVisitPlanModel;
import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.wst.DTO.UserDTO;
import com.hjw.wst.domain.ExamInfo;
import com.hjw.wst.domain.WebUserInfo;
import com.synjones.framework.exception.ServiceException;

public interface CrmVisitPlanService {
	//获取健康计划表
	public PageReturnDTO getCrmVisitPlanList(CrmVisitPlanModel model,int page,int rows) throws ServiceException,SQLException;
	public PageReturnDTO getCrmAllVisitPlanList(CrmVisitPlanModel model,int page,int rows, UserDTO user) throws ServiceException,SQLException;
	//获取单个健康计划
	public CrmVisitPlan getCrmVisitPlan(String id) throws ServiceException;
	//删除健康计划
	public String deleteCrmVisitPlan(String ids)throws ServiceException;
	//结束回访
		public String updateCrmVisitPlanEndStatus(String ids,String visit_status)throws ServiceException;
	//新增健康计划
	public void addCrmVisitPlan(CrmVisitPlan crmVisitPlan)throws ServiceException;
	//更新健康计划
	public void updateCrmVisitPlan(CrmVisitPlan crmVisitPlan)throws ServiceException;
	//获取User
	public List<WebUserInfo> getCrmVisitPlanUserList()throws ServiceException,SQLException;
	public ExamInfo getIdByExamInfoExamNum(String exam_num)throws ServiceException,SQLException;
	public List<ExamInfo> getExamInfoByArchNum(String arch_num)throws ServiceException,SQLException;
	public ExamInfo getExamInfoByArchNumAndJoinDate(String arch_num,String join_date)throws ServiceException,SQLException;
	//查询任务个数
	public String getCrmVisitPlanCount(String userid,String plan_visit_date)throws ServiceException,SQLException;
	//查询普通各种级别任务个数
	public String getCrmVisitPlanCount(String userid,String plan_visit_date,String visit_important)throws ServiceException,SQLException;
	//查询答复咨询个数
	public String getCrmVisitPlanCount(String userid,int visit_status)throws ServiceException,SQLException;
	//查询复检个数
	public String getCrmVisitPlanFujianCount(String userid,String plan_visit_date)throws ServiceException,SQLException;
	//查询回放跟踪
	public String getCrmVisitRecordCount(String userid,String plan_visit_date)throws ServiceException,SQLException;
	//查询客户数
	public String getKeHuCount(String userid,String plan_visit_date)throws ServiceException,SQLException;
	//查询已答复或者未答复客户数
	public String getKeHuCount(String userid,String plan_visit_date,String flag)throws ServiceException,SQLException;
	//查询失访记录数
	public String getLostCrm(String userid,String plan_visit_date)throws ServiceException,SQLException;
	public String getDataDict(String name)throws ServiceException,SQLException;
	public void deleteCrmVisitPlanByNum(String visit_num)throws ServiceException,SQLException;
	public String getCrmVisitRecordCountByDate(long userid, String plan_visit_date)throws ServiceException,SQLException;
	public List<UserDTO> getVisitDoctorList()throws ServiceException,SQLException;
	//查询生日客户数量
	public String getShengrikehuCount(String birthday)throws ServiceException,SQLException;
	
	//查询生日客户列表
	public PageReturnDTO getShengrikehuList(CrmVisitPlanModel model,int page,int rows)throws ServiceException,SQLException;
}
