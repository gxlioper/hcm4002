package com.hjw.zyb.action;

import java.sql.SQLException;
import java.util.List;









import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.hjw.config.Logincheck;
import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.wst.DTO.UserDTO;
import com.hjw.wst.domain.SysLog;
import com.hjw.wst.service.SyslogService;
import com.hjw.zyb.DTO.ZybEconomicTreeDTO;
import com.hjw.zyb.domain.ZybEconomicIndustry;
import com.hjw.zyb.domain.ZybPhyexaclass;
import com.hjw.zyb.domain.ZybTypeOfWork;
import com.hjw.zyb.model.ZybOccuModel;
import com.hjw.zyb.service.ZybOccuService;
import com.opensymphony.xwork2.ModelDriven;
import com.synjones.framework.aop.SystemType;
import com.synjones.framework.exception.WebException;
import com.synjones.framework.web.action.BaseAction;


/**
 * 
     * @Title:  火箭蛙体检管理系统   
     * @Package com.hjw.zyb.action   
     * @Description:  
     * @author: zt     
     * @date:   2017年4月27日 下午3:53:25   
     * @version V2.0.0.0
 */
@SuppressWarnings("rawtypes")
public class ZybOccuAction extends BaseAction implements ModelDriven {

	private static final long serialVersionUID = 1L;
	private Log log = LogFactory.getLog(this.getClass());
	private Logincheck logincheck;
	private int page = 1; // 当前页
	private int rows = 15; // easyui每页显示条数
	private ZybOccuService zyb_occuService;
	private ZybOccuModel model = new ZybOccuModel();
	private SyslogService syslogService;    


	public void setSyslogService(SyslogService syslogService) {
	   	this.syslogService = syslogService;
	}

	public Object getModel() {
		return model;
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

	public ZybOccuService getZyb_occuService() {
		return zyb_occuService;
	}

	public void setZyb_occuService(ZybOccuService zyb_occuService) {
		this.zyb_occuService = zyb_occuService;
	}

	public void setModel(ZybOccuModel model) {
		this.model = model;
	}

	
	
	//--------------------------------------------------经济行业管理-------------------------------------------------------
	
	
	
	/**
	 * 经济行业数据加载
	 * zyb204
	     * @Title: getIndustrytreegrid   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws Exception      
	     * @return: String      
	     * @throws
	 */
	public String getIndustrytreegrid()throws WebException, Exception{
		String industry_code=this.model.getIndustry_code();
		String industry_name=this.model.getIndustry_name();
		
		SystemType app_type = (SystemType) session.get("defaultapp");
		int industry_type = 0;
		if("2".equals(app_type.getComid())) {//职业病
			industry_type = 1;
		}
		PageReturnDTO i = this.zyb_occuService.queryByIndustry(industry_code,industry_name, industry_type,this.rows,this.getPage());
		this.outJsonResult(i);
		return NONE;
	}
	
	
	/**
	 * 行业管理zyb203
	     * @Title: industryManger   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	  public String industryManger() throws WebException, SQLException {
		UserDTO user = (UserDTO) session.get("username");
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("9");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("zyb203");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return SUCCESS;
	  }
	  
	  
	/**
	 * 经济行业删除 zyb_205
	     * @Title: deleteIndustry   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String deleteIndustry() throws WebException{
		ZybEconomicIndustry in = this.zyb_occuService.queryById_EI(this.model.getIndustryID());
		
		if(this.model.getParentID()!=null && !"".equals(this.model.getParentID()) ){
			this.zyb_occuService.deleteIndustryc(in);
			this.message="删除成功！";
			this.outJsonStrResult(this.message);
			UserDTO user = (UserDTO) session.get("username");
			SysLog sl =  new SysLog();
			sl.setCenter_num(user.getCenter_num());
			sl.setUserid(user.getUserid()+"");
			sl.setOper_type("3");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
			sl.setXtgnid("");//可不填写
			sl.setXtgnid2("zyb205");//子功能id 必须填写
			sl.setExplain("");//操作说明
			syslogService.saveSysLog(sl);
		}else {
			List<ZybEconomicTreeDTO> n = this.zyb_occuService.getind(this.model.getIndustryID());
			if(n.size()==0){
				this.zyb_occuService.deleteIndustryc(in);
				this.message="删除成功！";
				this.outJsonStrResult(this.message);
				UserDTO user = (UserDTO) session.get("username");
				SysLog sl =  new SysLog();
				sl.setCenter_num(user.getCenter_num());
				sl.setUserid(user.getUserid()+"");
				sl.setOper_type("3");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
				sl.setXtgnid("");//可不填写
				sl.setXtgnid2("zyb205");//子功能id 必须填写
				sl.setExplain("");//操作说明
				syslogService.saveSysLog(sl);
			}else if(n.size()>=1){
				this.message = "请先删除子级菜单";
				this.outJsonStrResult(this.message);
				return NONE;
			}
			
		}
		
		
		return NONE;
	}
	
	
	
	/**
	 * 经济行业修改
	     * @Title: updateIndustry   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String updateIndustry() throws WebException{
		ZybEconomicIndustry in = this.zyb_occuService.queryById_EI(this.model.getIndustryID());
		this.model.setIndustry_code(in.getIndustry_code());
		this.model.setIndustry_name(in.getIndustry_name());
		this.model.set_parentId(in.getParentID());
		if(in.getParentID()!=null && !"".equals(in.getParentID()) && in.getParentID()!="0"){
			ZybEconomicIndustry e=this.zyb_occuService.getp_name(in.getParentID());
			this.model.setIndustry_name_f(e.getIndustryID());
			this.model.setParentID(e.getIndustryID());
		}
		return SUCCESS;
	}
	
	
	/**
	 * 经济行业查询全部
	     * @Title: getAllIndustry   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String getAllIndustry() throws WebException{
		SystemType app_type = (SystemType) session.get("defaultapp");
		int industry_type = 0;
		if("2".equals(app_type.getComid())) {//职业病
			industry_type = 1;
		}
		List list=this.zyb_occuService.getIndustryAllByType(industry_type);
		this.outJsonResult(list);
		return NONE;
	}
	
	
	
	/**
	 * 经济行业增加
	     * @Title: addIndustry   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String addIndustry() throws WebException{
		
		return SUCCESS;
	}
	
	
	/**
	 * 经济行业父级新增
	     * @Title: addIndustry_f   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String addIndustry_f() throws WebException{
		
		return SUCCESS;
	}
	
	
	
	/**
	 * 行业同级新增保存zyb209
	     * @Title: saveInd_f   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String saveIndustry_f()throws WebException{
		UserDTO user = (UserDTO) session.get("username");
			ZybEconomicIndustry ei=new ZybEconomicIndustry();
			ei.setIndustry_code(this.model.getIndustry_code());
			ei.setIndustry_name(this.model.getIndustry_name());
			
			SystemType app_type = (SystemType) session.get("defaultapp");
			if("2".equals(app_type.getComid())) {//职业病
				ei.setIndustry_type(1);
			}
			this.zyb_occuService.addIndustry(ei);
			this.message = "父级单位添加成功！";
			SysLog sl =  new SysLog();
			sl.setCenter_num(user.getCenter_num());
			sl.setUserid(user.getUserid()+"");
			sl.setOper_type("1");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
			sl.setXtgnid("");//可不填写
			sl.setXtgnid2("zyb209");//子功能id 必须填写
			sl.setExplain("");//操作说明
			syslogService.saveSysLog(sl);
		this.outJsonStrResult(this.message);
		return NONE;
	}
	/**
	 * 经济行业保存zyb211
	     * @Title: saveIndustry   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String saveIndustry() throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		if( !"".equals(this.model.getIndustryID()) && this.model.getIndustryID() !=null){
			ZybEconomicIndustry in = this.zyb_occuService.queryById_EI(this.model.getIndustryID());
			in.setIndustry_code(model.getIndustry_code());
			in.setIndustry_name(model.getIndustry_name());
			
			if(in.getParentID()!=null && !"".equals(in.getParentID())){
				in.setParentID(model.getParentID());
				this.zyb_occuService.updateIndustry(in);
				this.message="子级单位修改成功！";
				SysLog sl =  new SysLog();
				sl.setCenter_num(user.getCenter_num());
				sl.setUserid(user.getUserid()+"");
				sl.setOper_type("2");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
				sl.setXtgnid("");//可不填写
				sl.setXtgnid2("zyb211");//子功能id 必须填写
				sl.setExplain("");//操作说明
				syslogService.saveSysLog(sl);
			}else{
				in.setParentID("");
				this.zyb_occuService.updateIndustry(in);
				this.message="父级单位修改成功！";
				SysLog sl =  new SysLog();
				sl.setCenter_num(user.getCenter_num());
				sl.setUserid(user.getUserid()+"");
				sl.setOper_type("2");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
				sl.setXtgnid("");//可不填写
				sl.setXtgnid2("zyb211");//子功能id 必须填写
				sl.setExplain("");//操作说明
				syslogService.saveSysLog(sl);
			}
			
			
		}else{
			ZybEconomicIndustry in = new ZybEconomicIndustry();
			in.setIndustry_code(model.getIndustry_code());
			in.setIndustry_name(model.getIndustry_name());
			in.setParentID(model.getParentID());
			
			SystemType app_type = (SystemType) session.get("defaultapp");
			if("2".equals(app_type.getComid())) {//职业病
				in.setIndustry_type(1);
			}
			this.zyb_occuService.addIndustry(in);
			this.message = "子级添加成功！";
			SysLog sl =  new SysLog();
			sl.setCenter_num(user.getCenter_num());
			sl.setUserid(user.getUserid()+"");
			sl.setOper_type("1");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
			sl.setXtgnid("");//可不填写
			sl.setXtgnid2("zyb211");//子功能id 必须填写
			sl.setExplain("");//操作说明
			syslogService.saveSysLog(sl);
			
		}
		
		this.outJsonStrResult(this.message);
		
		return NONE;
	}
	
	public String isParent() throws WebException{
		List<ZybEconomicTreeDTO> n = this.zyb_occuService.getind(this.model.getIndustryID());
		if(n.size()==0){
			this.message="ok";
		}else{
			this.message="no";
		}
		this.outJsonStrResult(this.message);
		return NONE;
	}
	
	public String getIndustrycode()throws WebException,SQLException{
		ZybEconomicIndustry e=this.zyb_occuService.getIndustrycode(this.model.getIndustry_code());
		if(e!=null){
			this.message="no";
		}else{
			this.message="ok";
		}
		this.outJsonStrResult(this.message);
		return NONE;
	}
	
	
	
	//-------------------------------------------------工种管理-------------------------------------------------------
	
	
	
	
	
	/**
	 * 工种管理zyb212
	     * @Title: typeofworkManger   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String typeofworkManger() throws WebException, SQLException {
		UserDTO user = (UserDTO) session.get("username");
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("9");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("zyb212");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return SUCCESS;
	}
	
	
	
	/**
	 * 工种分页查询
	     * @Title: gettypeOfWorkList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String gettypeOfWorkList() throws WebException,SQLException{
		String typeofwork_code=this.model.getTypeofwork_code();
		String typeofwork_name=this.model.getTypeofwork_name();
		PageReturnDTO t=this.zyb_occuService.queryByAllTypeOfWork( typeofwork_name,typeofwork_code,this.rows,this.getPage());
		this.outJsonResult(t);
		return NONE;
	}
	
	/**
	 * 工种新增
	     * @Title: addtow   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String addtow() throws WebException,SQLException{
		
		return  SUCCESS;
	}
	
	
	/**
	 * 工种管理删除zyb216
	     * @Title: deletetypeOfWork   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String deletetypeOfWork() throws WebException,SQLException{
		ZybTypeOfWork w=this.zyb_occuService.queryByIdTypeOfWork(this.model.getTypeofworkID());
		this.zyb_occuService.deleteTypeOfWork(w);
		this.message="删除成功!";
		UserDTO user = (UserDTO) session.get("username");
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("3");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("zyb216");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		this.outJsonStrResult(this.message);
		return NONE;
	}
	
	/**
	 * 工种修改zyb215
	     * @Title: updatetypeOfWork   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String updatetypeOfWork()throws WebException,SQLException{
		ZybTypeOfWork t=this.zyb_occuService.queryByIdTypeOfWork(this.model.getTypeofworkID());
		this.model.setTypeofwork_code(t.getTypeofwork_code());
		this.model.setTypeofwork_name(t.getTypeofwork_name());
		return SUCCESS;
	}
	
	/**
	 * 工种保存zyb217
	     * @Title: saveTypeOfWork   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String saveTypeOfWork() throws WebException,SQLException{
		
		if(this.model.getTypeofworkID()!=null && !"".equals(this.model.getTypeofworkID())){
			
			ZybTypeOfWork w=this.zyb_occuService.queryByIdTypeOfWork(this.model.getTypeofworkID());
			w.setTypeofwork_code(this.model.getTypeofwork_code());
			w.setTypeofwork_name(this.model.getTypeofwork_name());
			this.zyb_occuService.updateTypeOfWork(w);
			this.message="修改成功";
			UserDTO user = (UserDTO) session.get("username");
			SysLog sl =  new SysLog();
			sl.setCenter_num(user.getCenter_num());
			sl.setUserid(user.getUserid()+"");
			sl.setOper_type("2");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
			sl.setXtgnid("");//可不填写
			sl.setXtgnid2("zyb217");//子功能id 必须填写
			sl.setExplain("");//操作说明
			syslogService.saveSysLog(sl);
		}else{
			ZybTypeOfWork t=new ZybTypeOfWork();
			t.setTypeofwork_code(this.model.getTypeofwork_code());
			t.setTypeofwork_name(this.model.getTypeofwork_name());
			this.zyb_occuService.addTypeOfWork(t);
			this.message="添加成功";
			UserDTO user = (UserDTO) session.get("username");
			SysLog sl =  new SysLog();
			sl.setCenter_num(user.getCenter_num());
			sl.setUserid(user.getUserid()+"");
			sl.setOper_type("1");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
			sl.setXtgnid("");//可不填写
			sl.setXtgnid2("zyb217");//子功能id 必须填写
			sl.setExplain("");//操作说明
			syslogService.saveSysLog(sl);
		}
		
		this.outJsonStrResult(this.message);
		return NONE;
	}
	
	
	
	/**
	 * 工种管理批量删除 
	     * @Title: deletetypeofworks   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String  deletetypeofworks()throws WebException,SQLException{
		this.zyb_occuService.deletes(this.model.getIds());
		this.outJsonStrResult(this.message="删除成功!");
		UserDTO user = (UserDTO) session.get("username");
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("1");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("zyb217");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return NONE;
	}
	
	
	
	/**
	 * 工种管理编码验证
	     * @Title: validatecode   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String validatecode()throws WebException,SQLException{
		ZybTypeOfWork t=this.zyb_occuService.getBycode(this.model.getTypeofwork_code());
		if(t==null){
			this.message="ok";
		}else{
			this.message="no";
		}
		this.outJsonStrResult(this.message);
		return NONE;
	}
	
	
	
	//-----------------------------------------------体检类别管理--------------------------------------------------------
	
	
	/**
	 * 体检类别管理zyb218
	     * @Title: physicalexaminationclassManage   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String physicalexaminationclassManage() throws WebException,SQLException{
		UserDTO user = (UserDTO) session.get("username");
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("9");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("zyb218");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return SUCCESS;
	}
	
	
	
	/**
	 * 体检类别分页查询
	     * @Title: getPhyclassList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String getPhyclassList() throws  WebException,SQLException{
		String phyexaclass_code=this.model.getPhyexaclass_code();
		String phyexaclass_name=this.model.getPhyexaclass_name();
		PageReturnDTO p=this.zyb_occuService.queryByPagePhyexaclass(phyexaclass_code, phyexaclass_name, this.rows, this.getPage());
		this.outJsonResult(p);
		return NONE;
	}
	
	
	
	/**
	 * 体检类别添加
	     * @Title: addPhyclass   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String addPhyclass()throws WebException,SQLException{
		
		return SUCCESS;
	}
	
	
	
	/**
	 *体检类别更新
	     * @Title: updatePhyclass   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String updatePhyclass()throws WebException,SQLException{
		ZybPhyexaclass p=this.zyb_occuService.queryByPhyexaclassid(this.model.getPhyexaclassID());
		this.model.setPhyexaclass_code(p.getPhyexaclass_code());
		this.model.setPhyexaclass_name(p.getPhyexaclass_name());
		this.model.setRemark(p.getRemark());
		this.model.setIsprintcard(p.getIsprintcard());
		this.model.setIsupload(p.getIsupload());
		this.model.setCenter_num(p.getCenter_num());
		this.model.setShoworder(p.getShoworder());
		return SUCCESS;
	}
	
	/**
	 * 体检类别删除zyb223
	     * @Title: deletePhyclass   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String deletePhyclass()throws WebException,SQLException{
		ZybPhyexaclass p=this.zyb_occuService.queryByPhyexaclassid(this.model.getPhyexaclassID());
		this.zyb_occuService.deletePhyexaclass(p);
		this.message="删除成功";
		UserDTO user = (UserDTO) session.get("username");
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("3");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("zyb223");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		this.outJsonStrResult(this.message);
		return NONE;
	}
	
	
	/**
	 * 体检类别保存zyb222
	     * @Title: savePhyExaCla   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public  String savePhyExaCla()throws WebException,SQLException{
		if(this.model.getPhyexaclassID()!=null && !"".equals(this.model.getPhyexaclassID())){
			ZybPhyexaclass h=this.zyb_occuService.queryByPhyexaclassid(this.model.getPhyexaclassID());
			h.setPhyexaclass_code(this.model.getPhyexaclass_code());
			h.setPhyexaclass_name(this.model.getPhyexaclass_name());
			h.setIsprintcard(this.model.getIsprintcard());
			h.setIsupload(this.model.getIsupload());
			h.setRemark(this.model.getRemark());
			h.setCenter_num(this.model.getCenter_num());
			h.setShoworder(this.model.getShoworder());
			this.zyb_occuService.updatePhyexaclass(h);
			this.message="修改成功!";
			UserDTO user = (UserDTO) session.get("username");
			SysLog sl =  new SysLog();
			sl.setCenter_num(user.getCenter_num());
			sl.setUserid(user.getUserid()+"");
			sl.setOper_type("2");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
			sl.setXtgnid("");//可不填写
			sl.setXtgnid2("zyb222");//子功能id 必须填写
			sl.setExplain("");//操作说明
			syslogService.saveSysLog(sl);
		}else{
			ZybPhyexaclass p=new ZybPhyexaclass();
			p.setPhyexaclass_code(this.model.getPhyexaclass_code());
			p.setPhyexaclass_name(this.model.getPhyexaclass_name());
			p.setIsprintcard(this.model.getIsprintcard());
			p.setIsupload(this.model.getIsupload());
			p.setRemark(this.model.getRemark());
			p.setCenter_num(this.model.getCenter_num());
			p.setShoworder(this.model.getShoworder());
			this.zyb_occuService.addPhyexaclass(p);
			this.message="添加成功!";
			UserDTO user = (UserDTO) session.get("username");
			SysLog sl =  new SysLog();
			sl.setCenter_num(user.getCenter_num());
			sl.setUserid(user.getUserid()+"");
			sl.setOper_type("1");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
			sl.setXtgnid("");//可不填写
			sl.setXtgnid2("zyb222");//子功能id 必须填写
			sl.setExplain("");//操作说明
			syslogService.saveSysLog(sl);
		}
		
		this.outJsonStrResult(this.message);
		return NONE;
	}
	
	
	/**
	 * 体检类别编码验证
	     * @Title: isvalidatephyexa   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String isvalidatephyexa()throws WebException,SQLException{
		ZybPhyexaclass p=this.zyb_occuService.getByphyexacode(this.model.getPhyexaclass_code());
		if(p!=null){
			this.message="no";
		}else{
			this.message="ok";
		}
		
		this.outJsonStrResult(this.message);
		return NONE;
	}
}