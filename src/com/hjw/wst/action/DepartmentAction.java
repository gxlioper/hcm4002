package com.hjw.wst.action;

import java.util.List;
import java.sql.SQLException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import com.hjw.wst.domain.DepartmentDep;
import com.hjw.wst.domain.SysLog;
import com.hjw.wst.model.DepartmentModel;
import com.hjw.config.Logincheck;
import com.hjw.util.DateTimeUtil;
import com.hjw.wst.DTO.DepartmentDepDTO;
import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.wst.DTO.UserCenterDepListDTO;
import com.hjw.wst.DTO.UserDTO;
import com.hjw.wst.service.DepartmentService;
import com.hjw.wst.service.SyslogService;
import com.hjw.wst.service.UserInfoService;
import com.hjw.wst.service.WebSynchroService;
import com.opensymphony.xwork2.ModelDriven;
import com.synjones.framework.exception.ServiceException;
import com.synjones.framework.exception.WebException;
import com.synjones.framework.web.action.BaseAction;
import com.twelvemonkeys.lang.StringUtil;
import org.springframework.util.StringUtils;


/**
 * 
     * @Title:  火箭蛙体检管理系统   
     * @Package com.hjw.wst.action   
     * @Description:  
     * @date:   2016年8月22日 下午12:11:35   
     * @version V2.0.0.0
 */
@SuppressWarnings("rawtypes")
public class DepartmentAction extends BaseAction implements ModelDriven {

	private static final long serialVersionUID = 1L;
	@SuppressWarnings("unused")
	private Log log = LogFactory.getLog(this.getClass());
	@SuppressWarnings("unused")
	private Logincheck logincheck;
	
	private DepartmentModel model = new DepartmentModel();
	private int page = 1; // 当前页
	private int rows = 15; // easyui每页显示条数
	
	 private SyslogService syslogService;
	 private WebSynchroService webSynchroService;
	 private UserInfoService userInfoService;
	 
	 public void setUserInfoService(UserInfoService userInfoService) {
		this.userInfoService = userInfoService;
	}

	public WebSynchroService getWebSynchroService() {
		return webSynchroService;
	}

	public void setWebSynchroService(WebSynchroService webSynchroService) {
		this.webSynchroService = webSynchroService;
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

	private DepartmentService departmentService;
	
	
	public void setDepartmentService(DepartmentService departmentService) {
		this.departmentService = departmentService;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public DepartmentService getDepartmentService() {
		return departmentService;
	}

	@Override
	public Object getModel() {
		// TODO Auto-generated method stub
		return model;
	}
	
	public void setModel(DepartmentModel model) {
		this.model = model;
	}

	/**
	 * 
	     * @Title: department_dep  
	     * 排班管理主界面 （100）
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String department() throws WebException, SQLException {
		UserDTO user = (UserDTO) session.get("username");
		int centerCount =(Integer)(ServletActionContext.getContext().getApplication().get("centerCount"));  //获取有效授权体检中心个数
		
		if(!"999".equals(user.getUsertype())&&centerCount>1){
			this.message="此功能只有超级管理员有权维护！";
			return "commerror";
		}else{
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("100");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return SUCCESS;
		}
	}
	/**
	 * 科室检查页面
	     * @Title: departInspec   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String departInspec() throws WebException, SQLException {
		return SUCCESS;
	}
	/**
	 * 分页查询
	     * @Title: departmentList   
	     * @Description: 科室(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String departmentList()  throws WebException {
		UserDTO user = (UserDTO) session.get("username");
		String dep_name=this.model.getDep_name();
		String dep_num=this.model.getDep_num();
		String dep_category=this.model.getDep_category();
		String sex=this.model.getSex();
		PageReturnDTO list =null;
		if("999".equals(user.getUsertype()) && StringUtil.isEmpty(model.getC_center_num())){
			list = this.departmentService.departmentListAll(dep_name,dep_num,dep_category,sex,this.rows, this.getPage(),model);
		}else{
//			if(StringUtil.isEmpty(model.getC_center_num())){
//				user.setCenter_num(null);
//			} 
			list = this.departmentService.departmentList(dep_name,dep_num,dep_category,sex,user.getCenter_num(),this.rows, this.getPage());
		}
		this.outJsonResult(list);
		return NONE;
	}
	/**
	 * 查询全部
	     * @Title: departmentAll   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String departmentAll()  throws WebException {
		UserDTO user = (UserDTO) session.get("username");
		List<DepartmentDepDTO> list=this.departmentService.queryAllDepartmentDep(	user.getCenter_num());
		this.outJsonResult(list);
		return NONE;
	}
	
	/**
	 * 
	     * @Title: updaterdep   
	     * @Description: 科室更新(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String depupdater() throws WebException{
		DepartmentDep dep = this.departmentService.loadDepartmentDep(this.model.getId());
		this.model.setDep_num(dep.getDep_num());
		this.model.setDep_name(dep.getDep_name());
		this.model.setDep_category(dep.getDep_category());
		this.model.setSex(dep.getSex());
		this.model.setSeq_code(dep.getSeq_code());
		this.model.setRemark(dep.getRemark());
		this.model.setDep_link(dep.getDep_link());
		this.model.setCreater(dep.getCreater());
		this.model.setUpdater(dep.getUpdater());
		this.model.setIsPrint_Barcode(dep.getIsPrint_Barcode());
		this.model.setDep_inter_num(dep.getDep_inter_num());
		this.model.setCalculation_rate(dep.getCalculation_rate());
		return SUCCESS;
}
	/**
	 * @throws SQLException 
	 * @throws ServiceException 
	 * 
	     * @Title: deleteDept   106
	     * @Description: 删除科室(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String deletedep() throws WebException, ServiceException, SQLException{
		DepartmentDep dep = this.departmentService.loadDepartmentDep(this.model.getId());
		dep.setIsActive("N");
		this.departmentService.updateDept(dep);
		this.webSynchroService.updateWebSynchro(this.model.getId()+"",'3');
		this.message="删除成功！";
		this.outJsonStrResult(this.message);
		UserDTO user = (UserDTO) session.get("username");
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("3");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("106");//子功能id 必须填写
		sl.setExplain("删除科室 "+dep.getDep_name());//操作说明
		syslogService.saveSysLog(sl);
		return NONE;
	}
	/**
	 * 
	     * @Title: departadd   
	     * @Description: 添加科室(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String departadd() throws WebException{
		return SUCCESS;
	}
	
	/**
	 * @throws SQLException 
	 * @throws ServiceException 
	 * 
	     * @Title: baocunDepartment   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   109
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String baocunDepartment() throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		if(this.model.getId() > 0){
			DepartmentDep dep = this.departmentService.loadDepartmentDep(this.model.getId());
			dep.setDep_num(model.getDep_num());
			dep.setDep_name(model.getDep_name());
			dep.setDep_category(model.getDep_category());
			dep.setSex(model.getSex());
			dep.setSeq_code(model.getSeq_code());
			dep.setRemark(model.getRemark());
			dep.setDep_link(model.getDep_link());
			dep.setUpdater(user.getUserid());
			dep.setUpdate_time(DateTimeUtil.parse());
			dep.setIsPrint_Barcode(model.getIsPrint_Barcode());
			dep.setDep_inter_num(model.getDep_inter_num());
			dep.setCalculation_rate(model.getCalculation_rate());
			this.departmentService.updateDept(dep);
			
			
			
			this.webSynchroService.updateWebSynchro(model.getId()+"", '3');	
			
			//UserDTO user = (UserDTO) session.get("username");
			SysLog sl =  new SysLog();
			sl.setCenter_num(user.getCenter_num());
			sl.setUserid(user.getUserid()+"");
			sl.setOper_type("2");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
			sl.setXtgnid("");//可不填写
			sl.setXtgnid2("109");//子功能id 必须填写
			sl.setExplain("修改科室 "+dep.getDep_name());//操作说明
			syslogService.saveSysLog(sl);
			this.message="修改成功！";
		}else{
			DepartmentDep dep = new DepartmentDep();
			dep.setDep_num(model.getDep_num());
			dep.setDep_name(model.getDep_name());
			dep.setDep_category(model.getDep_category());
			dep.setSex(model.getSex());
			dep.setSeq_code(model.getSeq_code());
			dep.setRemark(model.getRemark());
			dep.setDep_link(model.getDep_link());
			dep.setCreater(user.getUserid());
			dep.setCreate_time(DateTimeUtil.parse());
			dep.setIsPrint_Barcode(model.getIsPrint_Barcode());
			dep.setDep_inter_num(model.getDep_inter_num());
			dep.setIsActive("Y");
			dep.setCalculation_rate(model.getCalculation_rate());

			dep = this.departmentService.addDepart(dep);
			this.webSynchroService.updateWebSynchro(dep.getId()+"", '3');
			//同部数据到多体检中心关系
			if(!StringUtils.isEmpty(model.getCenterNums())){
			    String[] centerNums = model.getCenterNums().split(",");
			    for(int  i=0; i<centerNums.length; i++){
			        model.setId((int)dep.getId());
			        model.setCenter_num(centerNums[i]);
                    departmentService.saveDepCenter(model,user);
                }
            }
			//this.departmentService.saveDepartmentCenter(dep.getId()+"", user.getCenter_num(), user.getUserid());
			//UserDTO user = (UserDTO) session.get("username");
			SysLog sl =  new SysLog();
			sl.setCenter_num(user.getCenter_num());
			sl.setUserid(user.getUserid()+"");
			sl.setOper_type("1");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
			sl.setXtgnid("");//可不填写
			sl.setXtgnid2("109");//子功能id 必须填写
			sl.setExplain("删除部门 "+dep.getDep_name());//操作说明
			syslogService.saveSysLog(sl);
			this.message = "添加成功！";
		}
		
		this.outJsonStrResult(this.message);
		
		return NONE;
	}
	
	/**
	 *  验证科室编码唯一性
	     * @Title: isUnique   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param dep_num
	     * @param: @return      
	     * @return: boolean      
	     * @throws
	 */
	public String isUnique() throws WebException{
		DepartmentDep dep = this.departmentService.queryByNum(model.getDep_num());
		if(dep==null){
			this.message="ok";
		}else{
			this.message="no";
		}
		this.outJsonStrResult(this.message);
		return NONE;
	}
	/**
	 * 
	     * @Title: updatedepRate   
	     * @Description: 批量设置   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws ServiceException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String updatedepRate() throws WebException, ServiceException, SQLException{
		if(this.model.getId()<=0)
		{
			this.message="error-无效部门！";
		}else{
		DepartmentDep dep = this.departmentService.loadDepartmentDep(this.model.getId());
		if(dep.getId()<=0){
			this.message="error-无效部门！";
		} else {
				this.departmentService.updateCIRate(this.model.getId(), dep.getCalculation_rate());
				//this.webSynchroService.updateWebSynchro(this.model.getId() + "", '3');
				this.message = "ok-设置完成！";
				UserDTO user = (UserDTO) session.get("username");
				SysLog sl = new SysLog();
				sl.setCenter_num(user.getCenter_num());
				sl.setUserid(user.getUserid() + "");
				sl.setOper_type("9");// 0 查询，1插入 2修改 3 删除 4导入 5，登录 9 其他
				sl.setXtgnid("");// 可不填写
				sl.setXtgnid2("106");// 子功能id 必须填写
				sl.setExplain("收费项目设置利润率 " + dep.getDep_name());// 操作说明
				syslogService.saveSysLog(sl);
			}
		}
		this.outJsonStrResult(this.message);
		return NONE;
	}
	
	/**
	 * 
	     * @Title: updateExaminationStopOrStart   
	     * @Description: TODO(科室部门启用/停用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String updateDepartStopOrStart()throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		try {
			DepartmentDep eti = this.departmentService.loadDepartmentDep((int)model.getId());
			if(eti.getIsActive().equals("Y")){
				eti.setIsActive("N");
				eti.setUpdate_time(DateTimeUtil.parse());;
				eti.setUpdater(user.getUserid());
			}else if(eti.getIsActive().equals("N")){
				eti.setIsActive("Y");
				eti.setUpdate_time(DateTimeUtil.parse());
				eti.setUpdater(user.getUserid());
			}
			this.departmentService.updateDept(eti);
			this.message="ok-成功";
		} catch (Exception e) {
			this.message="error-"+e.toString();
			e.printStackTrace();
		}

		this.outJsonStrResult(this.message);
		
		return NONE;
	}
	
	/**
	 * 
	     * @Title: detptocenter   
	     * @Description: 部门设置体检中心 1947-父功能101
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String detptocenter() throws WebException{		
		return SUCCESS;
	}
	
	/**
	 * 
	     * @Title: getdetptocenter   
	     * @Description: 部门设置体检中心-获取体检中心 1948-父功能101
	     * @param: @return      
	     * @return: String      
	     * @throws
	 */
	public String getdetptocenter()throws WebException{		
		List<UserCenterDepListDTO> list = this.userInfoService.getCenterAll();
		for(UserCenterDepListDTO depuser : list){
			String centerNum = this.departmentService.getCenterforDepid(this.model.getId()+"", depuser.getCenter_num());
			if(centerNum == null|| centerNum.trim().length()<=0){
				depuser.setChi_name("false");
			}else{
				depuser.setChi_name("true");
			}
		}
		this.outJsonResult(list);
		return NONE;
	}
	
	/**
	 * 
	     * @Title: detptocentersave   
	     * @Description:部门设置体检中心-保存体检中心 1949-父功能101   
	     * @param: @return      
	     * @return: String      
	     * @throws
	 */
	public String detptocentersave()throws WebException{		
		UserDTO user = (UserDTO) session.get("username");
		this.departmentService.saveDepartmentCenter(this.model.getId()+"", this.model.getRemark(),user.getUserid());
		this.outJsonStrResult("ok");
		return NONE;
	}
	/**
	 * 设置多体检中心页面
	     * @Title: bachDepCenter   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String bachDepCenter() throws WebException{		
		return SUCCESS;
	}
	/**
	 * 
	     * @Title: detptoBachcentersave   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String detptoBachcentersave()throws WebException{		
		UserDTO user = (UserDTO) session.get("username");
		this.departmentService.saveDepartmentBachCenter(this.model.getDep_ids(), this.model.getRemark(),user.getUserid());
		this.outJsonStrResult("设置成功！");
		return NONE;
	}
	/**
	 * 多体检中心-科室管理2511
	     * @Title: centerdepartment   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String centerDepartmentDep() throws WebException{
		return SUCCESS;
	}
	/**
	 * 批量设置当前体检中心科室2512
	     * @Title: batchCenterDep   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String batchCenterDep()  throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		this.departmentService.updateBatcahDepCenter(this.model,user);
		return NONE;
	}
	/**
	 * 多体检中心删除科室2513
	     * @Title: deleteCenterDepdeleteCenterDep   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String deleteCenterDep()  throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		String res = this.departmentService.deleteCenterDep(model, user);
		if("ok".equals(res)){
			this.message = "删除成功";
		} else {
			this.message = "操作失败";
		}
		this.outJsonStrResult(this.message);
		return NONE;
	}
	/**
	 * 多体检中心，修改科室页面2514
	     * @Title: getCenterDep   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String updateCenterDepPage()  throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		DepartmentDepDTO dep = this.departmentService.getCenterDep(model, user);
		this.model.setDep_num(dep.getDep_num());
		this.model.setDep_name(dep.getDep_name());
		this.model.setDep_category(dep.getDep_category());
		this.model.setSex(dep.getSex());
		this.model.setSeq_code(dep.getSeq_code());
		this.model.setRemark(dep.getRemark());
		this.model.setDep_link(dep.getDep_link());
		this.model.setCreater(dep.getCreater());
		this.model.setUpdater(dep.getUpdater());
		this.model.setIsPrint_Barcode(dep.getIsPrint_Barcode());
		this.model.setDep_inter_num(dep.getDep_inter_num());
		this.model.setCalculation_rate(dep.getCalculation_rate());
		this.model.setRemark1(dep.getRemark1());
		this.model.setDep_address(dep.getDep_address());
		this.model.setSynchro(dep.getSynchro());
		this.model.setIsPrint_Barcode_c(dep.getIsPrint_Barcode_c());
		this.model.setView_result_type(dep.getView_result_type());
		return SUCCESS;
	}
	/**
	 *多体检中心修改科室2515
	     * @Title: deleteCenterDep   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String updateCenterDep()  throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		this.departmentService.updateCenterDep(model, user);
		this.outJsonStrResult("修改成功");
		return NONE;
	}
	/**
	 * 多体检中心设置利润率2516
	     * @Title: updatedepRate   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws ServiceException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String centerupdatedepRate() throws WebException, ServiceException, SQLException{
		UserDTO user = (UserDTO) session.get("username");
		if(this.model.getId()<=0)
		{
			this.message="error-无效科室！";
		}else{
			DepartmentDepDTO dep = this.departmentService.getCenterDep(model, user);
		if(dep.getDep_id()<=0){
			this.message="error-无效科室！";
		} else {
				this.departmentService.centerupdateCIRate(this.model.getId(), dep.getCalculation_rate(),user,model);
				this.message = "ok-设置完成！";
				SysLog sl = new SysLog();
				sl.setCenter_num(user.getCenter_num());
				sl.setUserid(user.getUserid() + "");
				sl.setOper_type("9");// 0 查询，1插入 2修改 3 删除 4导入 5，登录 9 其他
				sl.setXtgnid("");// 可不填写
				sl.setXtgnid2("106");// 子功能id 必须填写
				sl.setExplain("多体检中心收费项目设置利润率 " + dep.getDep_name());// 操作说明
				syslogService.saveSysLog(sl);
			}
		}
		this.outJsonStrResult(this.message);
		return NONE;
	}
	/**
	 * 按照医生和体检号获取科室和收费项目 树
	 * @Title: getDepartmentDepListByExamnum
	 * @Description: TODO()
	 * @param: @return
	 * @param: @throws WebException
	 * @return: String
	 * @throws
	 */
	public String getDepartmentDepListByExamnum(){
		UserDTO user = (UserDTO) session.get("username");
		List<DepartmentDepDTO>  list = this.departmentService.getDepartmentDepListByExamnum(this.model,user);
		this.outJsonResult(list);
		return NONE;
	}

	/**
	 * 查询科室结论 建议。
	 * @Title: getDepartmentResultById
	 * @Description: TODO()
	 * @param: @return
	 * @param: @throws WebException
	 * @return: String
	 * @throws
	 */
	public String getDepartmentResultById(){
		UserDTO user = (UserDTO) session.get("username");
		com.hjw.wst.DTO.ExamDepResultDTO eti = this.departmentService.getDepartmentResultById(this.model,user);
		this.outJsonResult(eti);
		return NONE;
	}
}