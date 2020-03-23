package com.hjw.wst.action;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.hjw.wst.domain.Exam_user;
import com.hjw.wst.domain.Examinatioin_center;
import com.hjw.wst.domain.SysLog;
import com.hjw.wst.domain.WebUserInfo;
import com.hjw.wst.model.Examinatioin_centerModel;
import com.hjw.config.Logincheck;
import com.hjw.crm.DTO.CompanyInfoDTO;
import com.hjw.util.DateTimeUtil;
import com.hjw.wst.DTO.Examinatioin_centerDTO;
import com.hjw.wst.DTO.Examinatioin_center_dept;
import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.wst.DTO.UserDTO;
import com.hjw.wst.service.Examinatioin_centerService;
import com.hjw.wst.service.SyslogService;
import com.hjw.wst.service.UserInfoService;
import com.opensymphony.xwork2.ModelDriven;
import com.synjones.framework.exception.WebException;
import com.synjones.framework.web.action.BaseAction;



/**
 * 
     * @Title:  火箭蛙体检管理系统   
     * @Package com.hjw.wst.action   
     * @Description:  
     * @date:   2016年8月22日 下午12:11:35   
     * @version V2.0.0.0
 * @param <Department>
 */
@SuppressWarnings("rawtypes")
public class Examinatioin_centerAction<Department> extends BaseAction implements ModelDriven {

	private static final long serialVersionUID = 1L;
	private Log log = LogFactory.getLog(this.getClass());
	private Logincheck logincheck;
	private Examinatioin_centerModel model = new Examinatioin_centerModel();
	private Examinatioin_centerService examinatioin_centerService;
	private int page = 1; // 当前页
	private int rows = 15; // easyui每页显示条数
	private SyslogService syslogService;    
	private UserInfoService userInfoService;	
	
   	public void setUserInfoService(UserInfoService userInfoService) {
		this.userInfoService = userInfoService;
	}
	public void setSyslogService(SyslogService syslogService) {
   		this.syslogService = syslogService;
   	}
	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}
	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}


	public Log getLog() {
		return log;
	}

	public void setLog(Log log) {
		this.log = log;
	}

	public Logincheck getLogincheck() {
		return logincheck;
	}

	public void setLogincheck(Logincheck logincheck) {
		this.logincheck = logincheck;
	}

	public Examinatioin_centerService getExaminatioin_centerService() {
		return examinatioin_centerService;
	}

	public void setExaminatioin_centerService(Examinatioin_centerService examinatioin_centerService) {
		this.examinatioin_centerService = examinatioin_centerService;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public void setModel(Examinatioin_centerModel model) {
		this.model = model;
	}
	/**
	 * 体检中心管理
	     * @Title: Examinatioin_center   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String Examinatioin_center() throws WebException, SQLException {
		UserDTO user = (UserDTO) session.get("username");
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("114");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return SUCCESS;
	}
	/**
	 * 
	     * @Title: department_dep  
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String Examinatioin() throws WebException, SQLException {
		
		return SUCCESS;
	}
	/**
	 * 分页
	     * @Title: ExaminatioinList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String ExaminatioinList()  throws WebException {
		String center_num=this.model.getCenter_num();
		String center_name=this.model.getCenter_name();
		PageReturnDTO list = this.examinatioin_centerService.queryPageExaminatioin(center_num,center_name,rows,page);
		this.outJsonResult(list);
		return NONE;
	}
	/**
	 * 更新
	     * @Title: updaterExam   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String updaterExam() throws WebException{
		Examinatioin_center exam = this.examinatioin_centerService.loadExaminatioin(this.model.getId());
		this.model.setCenter_num(exam.getCenter_num());
		this.model.setCenter_name(exam.getCenter_name());
		this.model.setCreater(exam.getCreater());
		this.model.setCreate_time(exam.getCreate_time());
		this.model.setUpdater(exam.getUpdater());
		this.model.setUpdate_time(exam.getUpdate_time());
		this.model.setPhoto_function_status(exam.getPhoto_function_status());
		this.model.setLimit_count_s( exam.getLimit_count()+"" );
		return SUCCESS;
}
	/**
	 * 删除
	     * @Title: deleteExam   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String deleteExamnatioin() throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		Examinatioin_center exam = this.examinatioin_centerService.loadExaminatioin(this.model.getId());
		exam.setIs_active("N");
		List<Examinatioin_center> list=this.examinatioin_centerService.getExaminatioin_centerListByParentId(String.valueOf(exam.getId()));
		if(list!=null&&list.size()>0){
			for(Examinatioin_center examinatioin_center:list){
				this.examinatioin_centerService.delExamination(examinatioin_center);
			}
		}
		this.examinatioin_centerService.delExamination(exam);
		this.message="删除成功！";
		this.outJsonStrResult(this.message);
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("3");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("116");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return NONE;
	}
	/**
	 * 添加
	     * @Title: addExam   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String addExam() throws WebException{
		return SUCCESS;
	}
	
	public String saveExam() throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		if(this.model.getId() > 0){
			Examinatioin_center exam = this.examinatioin_centerService.loadExaminatioin(this.model.getId());
			exam.setCenter_num(model.getCenter_num());
			exam.setCenter_name(model.getCenter_name());
			exam.setUpdater(user.getUserid());
			exam.setUpdate_time(DateTimeUtil.parse());
			exam.setPhoto_function_status(model.getPhoto_function_status());
			exam.setLimit_count( model.getLimit_count() );
			this.examinatioin_centerService.updExamination(exam);
			this.message="修改成功！";
			SysLog sl =  new SysLog();
			sl.setCenter_num(user.getCenter_num());
			sl.setUserid(user.getUserid()+"");
			sl.setOper_type("2");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
			sl.setXtgnid("");//可不填写
			sl.setXtgnid2("118");//子功能id 必须填写
			sl.setExplain("修改");//操作说明
			syslogService.saveSysLog(sl);
		}else{
			Examinatioin_center exam = new Examinatioin_center();
			exam.setCenter_num(model.getCenter_num());
			exam.setCenter_name(model.getCenter_name());
			exam.setCreater(user.getUserid());
			exam.setCreate_time(DateTimeUtil.parse());
			exam.setPhoto_function_status(model.getPhoto_function_status());
			exam.setIs_active("Y");
			exam.setLimit_count( model.getLimit_count() );
			this.examinatioin_centerService.addExamination(exam);
			this.message = "添加成功！";
			SysLog sl =  new SysLog();
			sl.setCenter_num(user.getCenter_num());
			sl.setUserid(user.getUserid()+"");
			sl.setOper_type("1");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
			sl.setXtgnid("");//可不填写
			sl.setXtgnid2("118");//子功能id 必须填写
			sl.setExplain("插入");//操作说明
			syslogService.saveSysLog(sl);
		}
		
		this.outJsonStrResult(this.message);
		
		return NONE;
	}
	
    @Override
	public Object getModel() {
		return model;
	}
	/**
	 * 验证编码唯一性
	     * @Title: isUnique   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String isUniqueExam() throws WebException{
		Examinatioin_center exam = this.examinatioin_centerService.queryByNum(model.getCenter_num());
		if(exam==null){
			this.message="ok";
		}else{
			this.message="no";
		}
		this.outJsonStrResult(this.message);
		return NONE;
	}
	/**
	 * 查询全部
	     * @Title: queryAllExam   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String queryAllExam()  throws WebException {
		List list=this.examinatioin_centerService.queryAllExaminatioin();
		this.outJsonResult(list);
		return NONE;
	}
	/**
	 * 添加体检部门
	     * @Title: addExamDept   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String addExamDept() throws WebException {
		return SUCCESS;
	}
	/**
	 * 获取部门列表
	     * @Title: getDeptList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String getDeptList() throws WebException {
		List<Examinatioin_center_dept> list = this.examinatioin_centerService.getDept();
		this.outJsonResult(list);
		return NONE;
	}
	
	/**
	 * 
	     * @Title: getExaminatioin_center   
	     * @Description: 获取体检部门树状结构   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String getExaminatioin_center()throws WebException {
		List<Examinatioin_center> list = this.examinatioin_centerService.getExaminatioin_centerList(model.getCenter_name(),model.getCenter_num());
		PageReturnDTO page = new PageReturnDTO();
		page.setRows(list);
		page.setTotal(list.size());
		this.outJsonResult(page);
		return NONE;
	}
	/**
	 * 
	     * @Title: getExaminatioin_centerList   
	     * @Description: 获取体检部门树状结构   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String getExaminatioin_centerList()throws WebException {
		UserDTO user = (UserDTO) session.get("username");
		WebUserInfo wu= this.userInfoService.getUserInfoByname(user.getUsername());
		long centerid=0;
		if(wu.getExam_center_id().longValue()>0){
			centerid=user.getCenter_id();
		}else{
			centerid=0;
		}
		List<Examinatioin_center_dept> list = this.examinatioin_centerService.getExaminatioin_centerListDTO(centerid,String.valueOf(model.getId()));
		this.outJsonResult(list);
		return NONE;
	}
	/**
	 * 
	     * @Title: saveExamDept   
	     * @Description: 保存体检部门信息   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String saveExamDept()throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		if(this.model.getId() > 0){
			Examinatioin_center exam = this.examinatioin_centerService.loadExaminatioin(this.model.getId());
			List<Examinatioin_center> e=this.examinatioin_centerService.getExaminatioin_centerListByParent(model.getParent_id().trim());
			if(e!=null&&e.size()>0){
				exam.setParent_id(e.get(0).getId());
			}
			exam.setCenter_num(model.getCenter_num());
			exam.setCenter_name(model.getCenter_name());
			exam.setUpdater(user.getUserid());
			exam.setUpdate_time(DateTimeUtil.parse());
			exam.setPhoto_function_status("9");
			exam.setLimit_count( 0 );
			this.examinatioin_centerService.updExamination(exam);
			this.message="修改成功！";
			SysLog sl =  new SysLog();
			sl.setCenter_num(user.getCenter_num());
			sl.setUserid(user.getUserid()+"");
			sl.setOper_type("2");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
			sl.setXtgnid("");//可不填写
			sl.setXtgnid2("118");//子功能id 必须填写
			sl.setExplain("修改");//操作说明
			syslogService.saveSysLog(sl);
		}else{
			Examinatioin_center exam = new Examinatioin_center();
			exam.setParent_id(Long.valueOf(model.getParent_id()));
			exam.setCenter_num(model.getCenter_num());
			exam.setCenter_name(model.getCenter_name());
			exam.setCreater(user.getUserid());
			exam.setCreate_time(DateTimeUtil.parse());
			exam.setPhoto_function_status("9");
			exam.setIs_active("Y");
			exam.setLimit_count( 0 );
			this.examinatioin_centerService.addExamination(exam);
			this.message = "添加成功！";
			SysLog sl =  new SysLog();
			sl.setCenter_num(user.getCenter_num());
			sl.setUserid(user.getUserid()+"");
			sl.setOper_type("1");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
			sl.setXtgnid("");//可不填写
			sl.setXtgnid2("118");//子功能id 必须填写
			sl.setExplain("插入");//操作说明
			syslogService.saveSysLog(sl);
		}
		
		this.outJsonStrResult(this.message);
		
		return NONE;
	}
	/**
	 * 更新部门
	     * @Title: updaterExamDept   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String updaterExamDept()throws WebException{
		List<Examinatioin_center> exlist=this.examinatioin_centerService.getExaminatioin_centerList(Long.valueOf(model.getId()));
		if(exlist!=null&&exlist.size()>0){
			model.setId(exlist.get(0).getId());
			List<Examinatioin_center> par=this.examinatioin_centerService.getExaminatioin_centerList(exlist.get(0).getParent_id());
			if(par!=null&&par.size()>0){
				model.setParent_id(par.get(0).getCenter_name());	
			}
			model.setCenter_name(exlist.get(0).getCenter_name());
			model.setCenter_num(exlist.get(0).getCenter_num());
		}
		return SUCCESS;
	}
	
	
	
	/**
	 * 
	     * @Title: queryNotices   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String queryNotices() throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		Examinatioin_center noties = this.examinatioin_centerService.queryByNum(user.getCenter_num());
		if(noties!=null&&noties.getNotices()!=null&&noties.getNotices().trim().length()>0){
			this.message="ok-"+noties.getNotices().trim();
		}else{
			this.message="error-查询数据错误";
		}
		this.outJsonStrResult(this.message);
		return NONE;
	}
	
    
}