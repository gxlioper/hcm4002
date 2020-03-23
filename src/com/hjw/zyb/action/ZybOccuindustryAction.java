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
import com.hjw.zyb.DTO.ZybOccuindustryDTO;
import com.hjw.zyb.domain.ZybCheckcriterion;
import com.hjw.zyb.domain.ZybOccuindustry;
import com.hjw.zyb.domain.ZybOccutypeofwork;
import com.hjw.zyb.domain.ZyboccuhazardfactorsCheckcriterion;
import com.hjw.zyb.model.ZybOccuindustryModel;
import com.hjw.zyb.service.ZybOccuindustryService;
import com.opensymphony.xwork2.ModelDriven;
import com.synjones.framework.exception.WebException;
import com.synjones.framework.web.action.BaseAction;


/**
 * 
     * @Title:  火箭蛙体检管理系统   
     * @Package com.hjw.zyb.action   
     * @Description:  
     * @author: zt     
     * @date:   2017年4月28日 下午3:20:40   
     * @version V2.0.0.0
 */
@SuppressWarnings("rawtypes")
public class ZybOccuindustryAction extends BaseAction implements ModelDriven {

	private static final long serialVersionUID = 1L;
	private Log log = LogFactory.getLog(this.getClass());
	private Logincheck logincheck;
	private int page = 1; // 当前页
	private int rows = 15; // easyui每页显示条数
	
	private ZybOccuindustryService zybOccuindustryService;
	private ZybOccuindustryModel model = new ZybOccuindustryModel();
	private SyslogService syslogService;    

	public ZybOccuindustryService getZybOccuindustryService() {
		return zybOccuindustryService;
	}

	//---------------------------------------------------从业行业-------------------------------------------------
	
	
	public void setZybOccuindustryService(
			ZybOccuindustryService zybOccuindustryService) {
		this.zybOccuindustryService = zybOccuindustryService;
	}

	public SyslogService getSyslogService() {
		return syslogService;
	}

	public void setModel(ZybOccuindustryModel model) {
		this.model = model;
	}

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

	/**
	 * 从业行业管理zyb225
	     * @Title: ZybOccuindustryManger   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String ZybOccuindustryManger() throws WebException, SQLException {
		UserDTO user = (UserDTO) session.get("username");
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("9");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("zyb225");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return SUCCESS;
	}
	
	
	/**
	 * 从业行业数据加载zyb226
	     * @Title: occuindustryList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String occuindustryList() throws WebException,SQLException{
		UserDTO user = (UserDTO) session.get("username");
		
		String industry_code=this.model.getIndustry_code();
		String industry_name=this.model.getIndustry_name();
		PageReturnDTO obj=this.zybOccuindustryService.queryByOccuIndustry(industry_code, industry_name, this.rows, this.getPage(),user.getCenter_num());
		this.outJsonResult(obj);
		return NONE;
	}

	
	/**
	 * 从业行业新增页面zyb227
	     * @Title: addOccuIndustry   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String addOccuIndustry() throws WebException,SQLException{
		return SUCCESS;
	}
	
	
	/**
	 * 从业行业编辑页面zyb228
	     * @Title: updateOccuIndustry   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String updateOccuIndustry() throws WebException,SQLException{
		UserDTO user = (UserDTO) session.get("username");
		ZybOccuindustryDTO z=this.zybOccuindustryService.queryByOccuindustryID(this.model.getIndustryID(),user.getCenter_num());
		this.model.setIndustry_code(z.getIndustry_code());
		this.model.setIndustry_name(z.getIndustry_name());
		this.model.setScriptID(z.getScriptID());
		this.model.setShoworder(z.getShoworder());
		this.model.setPackage_name(z.getPackage_name());
		this.model.setPackageID(z.getPackageID());
		this.model.setPhyexeperiod(z.getPhyexeperiod());
		this.model.setTrainperiod(z.getTrainperiod());
		this.model.setCenter_num(z.getCenter_num());
		this.model.setExam_set_code(z.getExam_set_code());
		return SUCCESS;
	}
	
	
	/**
	 * 从业行业获取套餐名称zyb229
	     * @Title: geTExam_set   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String geTExam_set()throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		List<ZybOccuindustryDTO> list=this.zybOccuindustryService.getAllExam_set(user.getCenter_num());
		this.outJsonResult(list);
		return NONE;
	}
	
	
	/**
	 * 从业行业保存zyb230
	     * @Title: saveOccuindustry   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String saveOccuindustry()throws WebException{
		 UserDTO user = (UserDTO) session.get("username");
		if((this.model.getIndustryID()==null)||(this.model.getIndustryID().trim().length()<=0)){
			if((this.model.getIndustry_name()==null)||(this.model.getIndustry_name().trim().length()<=0)){
				this.message="error-数据无效";
			}else{
				 ZybOccuindustry y=new ZybOccuindustry();
				 y.setIndustry_code(this.model.getIndustry_code());
				 y.setIndustry_name(this.model.getIndustry_name());
				 y.setScriptID(this.model.getScriptID());
				 y.setPackageID(this.model.getPackageID());
				 y.setTrainperiod(this.model.getTrainperiod());
				 y.setCenter_num(user.getCenter_num());
				 y.setPhyexeperiod(this.model.getPhyexeperiod());
				 this.zybOccuindustryService.addOccuindustry(y);
				 this.message="ok-添加成功!";
				 SysLog sl =  new SysLog();
				 sl.setCenter_num(user.getCenter_num());
				 sl.setUserid(user.getUserid()+"");
				 sl.setOper_type("1");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
				 sl.setXtgnid("");//可不填写
				 sl.setXtgnid2("zyb230");//子功能id 必须填写
				 sl.setExplain("");//操作说明
				 syslogService.saveSysLog(sl);
			}
		}else{
			 ZybOccuindustry z=this.zybOccuindustryService.queryByID(this.model.getIndustryID());
			 z.setIndustry_code(this.model.getIndustry_code());
			 z.setIndustry_name(this.model.getIndustry_name());
			 z.setScriptID(this.model.getScriptID());
			 z.setPackageID(this.model.getPackageID());
			 z.setCenter_num(user.getCenter_num());
			 z.setTrainperiod(this.model.getTrainperiod());
			 z.setPhyexeperiod(this.model.getPhyexeperiod());
			 this.zybOccuindustryService.updateOccuindustry(z);
			 this.message="ok-修改成功!";
			
			 SysLog sl =  new SysLog();
			 sl.setCenter_num(user.getCenter_num());
			 sl.setUserid(user.getUserid()+"");
			 sl.setOper_type("2");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
			 sl.setXtgnid("");//可不填写
			 sl.setXtgnid2("zyb230");//子功能id 必须填写
			 sl.setExplain("");//操作说明
			 syslogService.saveSysLog(sl);
		 }
		 this.outJsonStrResult(this.message);
		return NONE;
	}
	
	
	/**
	 * 从业行业删除zyb231
	     * @Title: deleteOccuExam   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public  String deleteOccuindustry()throws WebException,SQLException{
		ZybOccuindustry z=this.zybOccuindustryService.queryByID(this.model.getIndustryID());
		this.zybOccuindustryService.deleteOccuindustry(z);
		this.message="删除成功!";
		UserDTO user = (UserDTO) session.get("username");
		 SysLog sl =  new SysLog();
		 sl.setCenter_num(user.getCenter_num());
		 sl.setUserid(user.getUserid()+"");
		 sl.setOper_type("3");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		 sl.setXtgnid("");//可不填写
		 sl.setXtgnid2("zyb231");//子功能id 必须填写
		 sl.setExplain("");//操作说明
		 syslogService.saveSysLog(sl);
		this.outJsonStrResult(this.message);
		return NONE;
	}
	
	
	/**
	 * 从业行业编码验证
	     * @Title: isvalidateOcuindustry   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String isvalidateOcuindustry() throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		ZybOccuindustry z = this.zybOccuindustryService.getByOcuindustrycode(this.model.getIndustry_code(),user);
		if(z==null){
			this.message="ok";
		}else{
			this.message="no";
		}
		this.outJsonStrResult(this.message);
		return NONE;
	}
	
	
	//-----------------------------------------从业工种------------------------------------------------------------
	
	
	/**
	 * 从业工种管理zyb232
	     * @Title: occutypeofworkManager   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String occutypeofworkManager() throws WebException,SQLException{
		 UserDTO user = (UserDTO) session.get("username");
		 SysLog sl =  new SysLog();
		 sl.setCenter_num(user.getCenter_num());
		 sl.setUserid(user.getUserid()+"");
		 sl.setOper_type("9");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		 sl.setXtgnid("");//可不填写
		 sl.setXtgnid2("zyb232");//子功能id 必须填写
		 sl.setExplain("");//操作说明
		 syslogService.saveSysLog(sl);
		return SUCCESS;
	}
	
	/**
	 * 从业工种添加页面zyb233
	     * @Title: addO_tow   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String addOccutypeofwork()throws WebException,SQLException{
		return SUCCESS;
	}
	
	
	/**
	 * 从业工种更新zyb234
	     * @Title: updateOccutypeofwork   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String updateOccutypeofwork()throws WebException,SQLException{
		ZybOccutypeofwork  o=this.zybOccuindustryService.queryByOccugzhid(this.model.getTypeofworkID());
		this.model.setTypeofwork_code(o.getTypeofwork_code());
		this.model.setTypeofwork_name(o.getTypeofwork_name());
		return SUCCESS;
	}
	
	
	/**
	 * 从业工种删除zyb236
	     * @Title: deleteO_tow   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String deleteOccutypeofwork()throws WebException,SQLException{
		ZybOccutypeofwork  o=this.zybOccuindustryService.queryByOccugzhid(this.model.getTypeofworkID());
		this.zybOccuindustryService.deleteOccugzh(o);
		this.message="删除成功!";
		 UserDTO user = (UserDTO) session.get("username");
		 SysLog sl =  new SysLog();
		 sl.setCenter_num(user.getCenter_num());
		 sl.setUserid(user.getUserid()+"");
		 sl.setOper_type("3");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		 sl.setXtgnid("");//可不填写
		 sl.setXtgnid2("zyb236");//子功能id 必须填写
		 sl.setExplain("");//操作说明
		 syslogService.saveSysLog(sl);		
		this.outJsonStrResult(this.message);
		return NONE;
	}
	
	
	/**
	 * 从业工种分页查询zyb235
	     * @Title: getOccutypeofworkList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String getOccutypeofworkList() throws WebException,SQLException{
		String typeofwork_code=this.model.getTypeofwork_code();
		String typeofwork_name=this.model.getTypeofwork_name();
		PageReturnDTO z=this.zybOccuindustryService.queryByOccugzh(typeofwork_code, typeofwork_name, this.rows, this.getPage());
		this.outJsonResult(z);
		return NONE;
	}
	
	
	/**
	 * 从业工种保存-zyb237
	     * @Title: saveOccutypeofwork   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String saveOccutypeofwork()throws WebException,SQLException{
		if(!"".equals(this.model.getTypeofworkID()) && this.model.getTypeofworkID()!=null){
			ZybOccutypeofwork  o=this.zybOccuindustryService.queryByOccugzhid(this.model.getTypeofworkID());
			o.setTypeofwork_code(this.model.getTypeofwork_code());
			o.setTypeofwork_name(this.model.getTypeofwork_name());
			this.zybOccuindustryService.updateOccugzh(o);
			this.message="编辑成功!";
			UserDTO user = (UserDTO) session.get("username");
			 SysLog sl =  new SysLog();
			 sl.setCenter_num(user.getCenter_num());
			 sl.setUserid(user.getUserid()+"");
			 sl.setOper_type("2");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
			 sl.setXtgnid("");//可不填写
			 sl.setXtgnid2("zyb237");//子功能id 必须填写
			 sl.setExplain("");//操作说明
			 syslogService.saveSysLog(sl);
		}else{
			UserDTO user = (UserDTO) session.get("username");
			ZybOccutypeofwork z=new ZybOccutypeofwork();
			z.setTypeofwork_code(this.model.getTypeofwork_code());
			z.setTypeofwork_name(this.model.getTypeofwork_name());
			z.setCenter_num(user.getCenter_num());
			this.zybOccuindustryService.addOccugzh(z);
			this.message="添加成功!";
			
			 SysLog sl =  new SysLog();
			 sl.setCenter_num(user.getCenter_num());
			 sl.setUserid(user.getUserid()+"");
			 sl.setOper_type("1");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
			 sl.setXtgnid("");//可不填写
			 sl.setXtgnid2("zyb237");//子功能id 必须填写
			 sl.setExplain("");//操作说明
			 syslogService.saveSysLog(sl);
		}
		this.outJsonStrResult(this.message);
		return NONE;
	}
	
	/**
	 * 从业工种编码验证zyb238
	     * @Title: isUnique_O   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String isUnique_O() throws WebException{
		ZybOccutypeofwork z = this.zybOccuindustryService.queryBycode(this.model.getTypeofwork_code());
		if(z==null){
			this.message="ok";
		}else{
			this.message="no";
		}
		this.outJsonStrResult(this.message);
		return NONE;
	}
	
	//---------------------------------------------------检查依据---------------------------------------------------------
	
	
	/**
	 * 检查依据管理zyb239
	     * @Title: criterionManager   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String criterionManager()throws WebException,SQLException{
		UserDTO user = (UserDTO) session.get("username");
		 SysLog sl =  new SysLog();
		 sl.setCenter_num(user.getCenter_num());
		 sl.setUserid(user.getUserid()+"");
		 sl.setOper_type("9");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		 sl.setXtgnid("");//可不填写
		 sl.setXtgnid2("zyb239");//子功能id 必须填写
		 sl.setExplain("");//操作说明
		 syslogService.saveSysLog(sl);
		return SUCCESS;
	}
	
	
	/**
	 * 检查依据添加页面zyb240
	     * @Title: addChe_c   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String addChecriterion() throws WebException,SQLException{
		this.model.setHazardfactorsID(this.model.getHazardfactorsID());
		this.model.setOccuphyexaclassID(this.model.getOccuphyexaclassID());
		return SUCCESS;
	}
	
	
	/**
	 * 检查依据删除zyb242
	     * @Title: deleteChecriterion   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String deleteChecriterion() throws WebException,SQLException{
		ZybCheckcriterion c=this.zybOccuindustryService.queryBycheckyjID(this.model.getCriterionID());
		this.zybOccuindustryService.deleteCheckyj(c);
		this.message="删除成功!";
		UserDTO user = (UserDTO) session.get("username");
		 SysLog sl =  new SysLog();
		 sl.setCenter_num(user.getCenter_num());
		 sl.setUserid(user.getUserid()+"");
		 sl.setOper_type("3");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		 sl.setXtgnid("");//可不填写
		 sl.setXtgnid2("zyb242");//子功能id 必须填写
		 sl.setExplain("");//操作说明
		 syslogService.saveSysLog(sl);
		this.outJsonStrResult(this.message);
		return NONE;
	}
	
	
	/**
	 * 检查依据更新zyb241
	     * @Title: updateChe_c   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String updateChecriterion() throws WebException,SQLException{
		ZybCheckcriterion c=this.zybOccuindustryService.queryBycheckyjID(this.model.getCriterionID());
		this.model.setCriterion_name(c.getCriterion_name());
		return SUCCESS;
	}
	
	
	/**
	 * 检查依据分页查询zyb242
	     * @Title: getChecriterionList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String getChecriterionList() throws WebException,SQLException{
		PageReturnDTO l=this.zybOccuindustryService.queryBycheckyjPage(model, this.rows,this.getPage());
		this.outJsonResult(l);
		return NONE;
	}
	
	
	/**
	 * 检查依据保存zyb244
	     * @Title: saveCheckcriterion   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String saveCheckcriterion() throws WebException,SQLException{
		if(!"".equals(this.model.getCriterionID()) && this.model.getCriterionID() !=null){
			this.zybOccuindustryService.updateCheckyj(model);
			this.message="修改成功!";
			UserDTO user = (UserDTO) session.get("username");
			 SysLog sl =  new SysLog();
			 sl.setCenter_num(user.getCenter_num());
			 sl.setUserid(user.getUserid()+"");
			 sl.setOper_type("2");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
			 sl.setXtgnid("");//可不填写
			 sl.setXtgnid2("zyb244");//子功能id 必须填写
			 sl.setExplain("");//操作说明
			 syslogService.saveSysLog(sl);
		}else{
			this.zybOccuindustryService.addCheckyj(model);
			this.message="添加成功!";
			UserDTO user = (UserDTO) session.get("username");
			 SysLog sl =  new SysLog();
			 sl.setCenter_num(user.getCenter_num());
			 sl.setUserid(user.getUserid()+"");
			 sl.setOper_type("1");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
			 sl.setXtgnid("");//可不填写
			 sl.setXtgnid2("zyb244");//子功能id 必须填写
			 sl.setExplain("");//操作说明
			 syslogService.saveSysLog(sl);
		}
		this.outJsonStrResult(this.message);
		return NONE;
	}
	
	
	/**
	 * 检查依据批量删除
	     * @Title: jcyjdeletes   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String jcyjdeletes()throws WebException,SQLException{
		this.zybOccuindustryService.deletejcyjs(this.model.getIds());
		this.message="删除成功!";
		UserDTO user = (UserDTO) session.get("username");
		 SysLog sl =  new SysLog();
		 sl.setCenter_num(user.getCenter_num());
		 sl.setUserid(user.getUserid()+"");
		 sl.setOper_type("3");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		 sl.setXtgnid("");//可不填写
		 sl.setXtgnid2("zyb290");//子功能id 必须填写
		 sl.setExplain("");//操作说明
		 syslogService.saveSysLog(sl);
		 this.outJsonStrResult(this.message);
		return NONE;
	}
	/**
	 * ---------------------------------------因素和检查依据关系维护--------------------
	 */
	/**zyb380
	 *因素检查依据关系管理
	     * @Title: getLbcriterionManager   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String getLbcriterionManager()throws WebException,SQLException{
		UserDTO user = (UserDTO) session.get("username");
		 SysLog sl =  new SysLog();
		 sl.setCenter_num(user.getCenter_num());
		 sl.setUserid(user.getUserid()+"");
		 sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		 sl.setXtgnid("");//可不填写
		 sl.setXtgnid2("zyb380");//子功能id 必须填写
		 sl.setExplain("");//操作说明
		 syslogService.saveSysLog(sl);
		return SUCCESS;
	}
	/**
	 * zyb381因素检查依据修改页面
	     * @Title: getLbcriterionManagerupdatePage   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String getLbcriterionManagerupdatePage()throws WebException,SQLException{
		UserDTO user = (UserDTO) session.get("username");
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("zyb381");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return SUCCESS;
	}
	/**
	 * zyb382检查依据内容
	     * @Title: getLbcriterionManagerComxobox   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String getLbcriterionManagerComxobox()throws WebException,SQLException{
		this.outJsonResult(	this.zybOccuindustryService.getLbcriterionManagerComxobox(model));
		return NONE;
	}
	/**
	 * zyb383保存因素检查依据关系
	     * @Title: getLbcriterionManagerComxobox   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String saveLbcriterionManager()throws WebException,SQLException{
		if(model.getRID()!=null && !"".equals(model.getRID())){
			this.zybOccuindustryService.updateLbcriterionManager(model);
		} else {
			this.zybOccuindustryService.saveLbcriterionManager(model);
		}
		this.outJsonStrResult("保存成功!");;
		return NONE;
	}
	/**
	 * zyb384删除因素检查依据关系
	     * @Title: saveLbcriterionManager   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String deleteLbcriterionManager()throws WebException,SQLException{
		this.zybOccuindustryService.deleteLbcriterionManager(model);
		this.outJsonStrResult("删除成功!");;
		return NONE;
	}
	/**
	 * zyb385因素和检查依据关系列表
	     * @Title: queryLbcriterionManager   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String queryLbcriterionManager()throws WebException,SQLException{
	     PageReturnDTO  dto = this.zybOccuindustryService.queryLbcriterionManager(model, this.rows, page);
		this.outJsonResult(dto);
		return NONE;
	}
	/**
	 * 修改页面zyb386
	     * @Title: updateLbcriterionManagerPage   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String updateLbcriterionManagerPage()throws WebException,SQLException{
		ZyboccuhazardfactorsCheckcriterion   d = this.zybOccuindustryService.getLBZybOccuindustryDTO(model);
			model.setCriterionID(d.getCriterionID());
			model.setDISORDER(d.getDISORDER());
			model.setRID(d.getRID());
		return SUCCESS;
	}
	
}