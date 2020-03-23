package com.hjw.wst.action;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import com.hjw.wst.DTO.BatchDTO;
import com.hjw.wst.DTO.CenterConfigurationDTO;
import com.hjw.wst.DTO.ContractDTO;
import com.hjw.wst.DTO.JobDTO;
import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.wst.DTO.TreeDTO;
import com.hjw.wst.DTO.UserDTO;
import com.hjw.wst.DTO.UserUseDTO;
import com.hjw.wst.domain.CompanyDepartment;
import com.hjw.wst.domain.CompanyInfo;
import com.hjw.wst.domain.Introducer;
import com.hjw.wst.domain.SysLog;
import com.hjw.wst.domain.WebResrelAtionship;
import com.hjw.wst.model.CompanyInfoModel;
import com.hjw.wst.service.BatchService;
import com.hjw.wst.service.CommService;
import com.hjw.wst.service.CompanyService;
import com.hjw.wst.service.CustomerInfoService;
import com.hjw.wst.service.SyslogService;
import com.hjw.wst.service.WebSynchroService;
import com.hjw.zyb.DTO.ZybEconomicTreeDTO;
import com.hjw.zyb.service.ZybOccuService;
import com.opensymphony.xwork2.ModelDriven;
import com.hjw.config.Logincheck;
import com.hjw.crm.DTO.CompanyInfoDTO;
import com.hjw.util.DateTimeUtil;
import com.hjw.util.Language;
import com.synjones.framework.aop.SystemType;
import com.synjones.framework.exception.WebException;
import com.synjones.framework.web.action.BaseAction;
import com.twelvemonkeys.lang.StringUtil;

@SuppressWarnings("rawtypes")
public class CommonAction extends BaseAction implements ModelDriven {

	private static final long serialVersionUID = 1L;
	@SuppressWarnings("unused")
	private Log log = LogFactory.getLog(this.getClass());
	@SuppressWarnings("unused")
	private Logincheck logincheck;
	private CompanyService companyService;
	private int rp = 15; // 每页显示的条数
	private int page = 1; // 当前页
	private int rows = 15; // easyui每页显示条数
	private CompanyInfoModel model = new CompanyInfoModel();
    private CustomerInfoService customerInfoService;   
    private BatchService batchService;
    private CommService commService; 
    private SyslogService syslogService;   
    private ZybOccuService zybOccuService;
    
    private WebSynchroService webSynchroService;

   	public void setWebSynchroService(WebSynchroService webSynchroService) {
		this.webSynchroService = webSynchroService;
	}
	public void setZybOccuService(ZybOccuService zybOccuService) {
		this.zybOccuService = zybOccuService;
	}
	public void setSyslogService(SyslogService syslogService) {
   		this.syslogService = syslogService;
   	}
	public int getRp() {
		return rp;
	}

	public void setRp(int rp) {
		this.rp = rp;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public void setCommService(CommService commService) {
		this.commService = commService;
	}

	public void setBatchService(BatchService batchService) {
		this.batchService = batchService;
	}

	public void setCustomerInfoService(CustomerInfoService customerInfoService) {
		this.customerInfoService = customerInfoService;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public Object getModel() {
		return model;
	}

	public void setModel(CompanyInfoModel model) {
		this.model = model;
	}

	public void setCompanyService(CompanyService companyService) {
		this.companyService = companyService;
	}

	public String TermAccount() throws WebException, SQLException {
		Language.setLanguage(this.language);
		return "TermAccount";
	}

	/**
	 * 用户管理主界面 1
	 * 
	 * @return
	 * @throws WebException
	 * @throws SQLException
	 * @see [类、类#方法、类#成员]
	 */
	@SuppressWarnings("unchecked")
	public String usermanager() throws WebException, SQLException {
		UserDTO user = (UserDTO) session.get("username");
		int centerCount =(Integer)(ServletActionContext.getContext().getApplication().get("centerCount"));  //获取有效授权体检中心个数
		
		if(!"999".equals(user.getUsertype())&&centerCount>1){
			this.message="此功能只有超级管理员有权维护！";
			return "commerror";
		} else {
			SysLog sl = new SysLog();
			sl.setCenter_num(user.getCenter_num());
			sl.setUserid(user.getUserid() + "");
			sl.setOper_type("0");// 0 查询，1插入 2修改 3 删除 4导入 5，登录 9 其他
			sl.setXtgnid("");// 可不填写
			sl.setXtgnid2("1");// 子功能id 必须填写
			sl.setExplain("");// 操作说明
			syslogService.saveSysLog(sl);
			return SUCCESS;
		}
	}

	/**
	 * 持卡人基本信息查询 功能编码：3
	 * 
	 * @return
	 * @throws WebException
	 * @throws SQLException
	 * @see [类、类#方法、类#成员]
	 */
	@SuppressWarnings("unchecked")
	public String UserInfo() throws WebException, SQLException {
		Language.setLanguage(this.language);
		this.session.remove("language");
		this.session.put("language", language);
		return "UserInfo";
	}

	/**
	 * 初始化菜单 功能编码：14
	 * 
	 * @return
	 * @throws WebException
	 * @throws SQLException
	 * @see [类、类#方法、类#成员]
	 */
	@SuppressWarnings("unchecked")
	public String createMenu() throws WebException, SQLException {
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
		sl.setXtgnid2("14");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return "createMenu";
		}
	}

	/**
	 * 岗位授权 对应功能16
	 * 
	 * @return
	 * @throws WebException
	 * @throws SQLException
	 * @see [类、类#方法、类#成员]
	 */
	@SuppressWarnings("unchecked")
	public String getJob() throws WebException, SQLException {
		Language.setLanguage(this.language);
		this.session.remove("language");
		this.session.put("language", language);
		String sk = (String) session.get("sk");
		if (sk.equals("username")) {
			return "gwmanager";
		}
		return "getJob";
	}

	@SuppressWarnings("unchecked")
	public String addxxfb() throws WebException, SQLException {
		Language.setLanguage(this.language);
		this.session.remove("language");
		this.session.put("language", language);
		return "addxxfb";
	}

	@SuppressWarnings("unchecked")
	public String gwshEdit() throws WebException, SQLException {
		Language.setLanguage(this.language);
		this.session.remove("language");
		this.session.put("language", language);
		return "gwshEdit";
	}

	/**
	 * 
	 * @Title: managercomdept @Description: 功能id 200
	 *         体检单位管理 @param: @return @param: @throws
	 *         WebException @param: @throws SQLException @return: String @throws
	 */
	public String managercomdept() throws WebException, SQLException {
		UserDTO user = (UserDTO) session.get("username");
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("200");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return SUCCESS;
	}

	/**
	 * 角色管理 对应功能 15
	 * 
	 * @return
	 * @throws WebException
	 * @throws SQLException
	 * @see [类、类#方法、类#成员]
	 */
	@SuppressWarnings("unchecked")
	public String rolemain() throws WebException, SQLException {
		UserDTO user = (UserDTO) session.get("username");
		int centerCount =(Integer)(ServletActionContext.getContext().getApplication().get("centerCount"));  //获取有效授权体检中心个数
		
		if(!"999".equals(user.getUsertype())&&centerCount>1){
			this.message="此功能只有超级管理员有权维护！";
			return "commerror";
		}else{
			return "rolemain";
		}
	}

	public void setLogincheck(Logincheck logincheck) {
		this.logincheck = logincheck;
	}

	/**
	 * 
	 * @Title: comdeptListTab @Description:单位部门管理
	 *         -202 @param: @return @param: @throws WebException @param: @throws
	 *         SQLException @return: String @throws
	 */
	public String comdeptListTab() throws WebException, SQLException {
		UserDTO user = (UserDTO) session.get("username");
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("202");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return SUCCESS;
	}

	/**
	 * 单位管理 205 @Title: companydwmanager @Description:
	 * TODO(这里用一句话描述这个方法的作用) @param: @return @param: @throws
	 * WebException @param: @throws SQLException @return: String @throws
	 */
	public String companydwmanager() throws WebException, SQLException {
		CompanyInfo cinf = new CompanyInfo();
		if (this.model.getId() <= 0) {
			model.setDep_Name("根目录（一级单位）");
			model.setCompany_Id(0);
		} else {
			cinf = this.companyService.getComByID(this.model.getId());
		}
		this.model.setCom_Name(cinf.getCom_Name());
		UserDTO user = (UserDTO) session.get("username");
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("205");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return SUCCESS;
	}

	public String dwshowall() throws WebException, SQLException {
		PageReturnDTO rolemain = new PageReturnDTO();
		rolemain = this.companyService.getDwList(this.model.getId(), this.rows, this.getPage());
		rolemain.setRows(rolemain.getRows());
		this.outJsonResult(rolemain);
		return NONE;
	}

	/**
	 * 
	 * @Title: companychange @Description: 210
	 *         改变单位层级关系 @param: @return @param: @throws
	 *         WebException @param: @throws SQLException @return: String @throws
	 */
	public String companychange() throws WebException, SQLException {
		CompanyInfo cinf = new CompanyInfo();
		if (this.model.getId() <= 0) {
			model.setDep_Name("根目录（一级单位）");
			model.setCompany_Id(0);
		} else {
			cinf = this.companyService.getComByID(this.model.getId());
			this.model.setAddress(cinf.getAddress());
			this.model.setCom_Level(cinf.getCom_Level());
			this.model.setCom_Name(cinf.getCom_Name());
			this.model.setCom_Num(cinf.getCom_Num());
			this.model.setCom_Type(cinf.getCom_Type());
			this.model.setContact_Name(cinf.getContact_Name());
			this.model.setContact_Phone(cinf.getContact_Phone());
			this.model.setCreate_Time(cinf.getCreate_Time());
			this.model.setCreater(cinf.getCreater());
			this.model.setEmail(cinf.getEmail());
			this.model.setId(cinf.getId());
			this.model.setIs_Active(cinf.getIs_Active());
			this.model.setParent_com_id(cinf.getParent_com_id());
			this.model.setRemark(cinf.getRemark());
			this.model.setUpdate_Time(cinf.getUpdate_Time());
			this.model.setUpdater(cinf.getUpdater());
			this.model.setName_pinyin(cinf.getName_pinyin());
			if ((this.model.getParent_com_id() == null) || (this.model.getParent_com_id() <= 0)) {
				model.setDep_Name("根目录（一级单位）");
				model.setCompany_Id(0);
				model.setFather_con_num("000");
			} else {

				CompanyInfo cfath = this.companyService.getComByID(cinf.getParent_com_id());
				model.setDep_Name(cfath.getCom_Name());
				model.setCompany_Id(cfath.getId());
				model.setFather_con_num(cfath.getCom_Num());
			}
		}
		UserDTO user = (UserDTO) session.get("username");
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("210");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return SUCCESS;
	}
	
	/**
	 * 
	     * @Title: checkcomnum   
	     * @Description: 检测单位编号是否重复 433   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String checkcomnum() throws WebException {
		if(this.model.getCom_Num().length()>0){
			int comcount = this.companyService.findComByComNum(this.model.getId(),this.model.getCom_Num(),this.model.getEdittype());
			if(comcount>0){
				this.message="error";
			}else{
				this.message="ok";
			}
		}else{
			this.message="ok";
		}
		this.outJsonStrResult(this.message);
		return NONE;
	}

	/**
	 * 
	 * @Title: companyedit @Description:
	 *         体检单位新增和删除-203 @param: @return @param: @throws
	 *         WebException @param: @throws SQLException @return: String @throws
	 */
	public String companyedit() throws WebException, SQLException {
		UserDTO user = (UserDTO) session.get("username");
		CompanyInfo cinf = new CompanyInfo();
		CompanyInfoDTO cinfDTO = new CompanyInfoDTO();
		if (this.model.getId() <= 0) {
			model.setDep_Name("顶级部门");
			model.setCompany_Id(0);
			model.setCom_Num("");
		} else {
			cinf = this.companyService.getComByID(this.model.getId());
			cinfDTO = companyService.getCenterComByID(this.model.getId(),user.getCenter_num());
			if(cinf.getCom_Num()==null||cinf.getCom_Num().trim().length()<=0){
				cinf.setCom_Num(this.batchService.GetCreateID("com_num", user.getCenter_num()));
				cinf=this.companyService.saveCompanyInfo(cinf,user);
			}
			this.model.setAddress(cinfDTO.getAddress());
			this.model.setCom_Level(cinf.getCom_Level());
			this.model.setCom_Name(cinf.getCom_Name());
			this.model.setCom_Num(cinf.getCom_Num());
			this.model.setCom_Type(cinfDTO.getCom_Type());
			this.model.setContact_Name(cinfDTO.getContact_Name());
			this.model.setContact_Phone(cinfDTO.getContact_Phone());
			this.model.setCreate_Time(cinf.getCreate_Time());
			this.model.setCreater(cinf.getCreater());
			this.model.setEmail(cinfDTO.getEmail());
			this.model.setId(cinf.getId());
			this.model.setIs_Active(cinf.getIs_Active());
			this.model.setParent_com_id(cinf.getParent_com_id());
			this.model.setRemark(cinfDTO.getRemark());
			this.model.setUpdate_Time(cinf.getUpdate_Time());
			this.model.setUpdater(cinf.getUpdater());
			this.model.setName_pinyin(cinf.getName_pinyin());
			this.model.setKeShi_Name(cinfDTO.getKeShi_Name()); //科室联系人
			this.model.setAssigned_unit_code(cinf.getAssigned_unit_code());
			this.model.setCom_fax(cinfDTO.getCom_fax());
			this.model.setCom_phone(cinfDTO.getCom_phone());
			this.model.setCom_jianjie(cinfDTO.getCom_jianjie()); //公司简介
			if ((this.model.getParent_com_id() == null) || (this.model.getParent_com_id() <= 0)) {
				model.setDep_Name("根目录（一级单位）");
				model.setCompany_Id(0);
				model.setFather_con_num("000");
			} else {
				CompanyInfo cfath = this.companyService.getComByID(cinf.getParent_com_id());
				model.setDep_Name(cfath.getCom_Name());
				model.setCompany_Id(cfath.getId());
				model.setFather_con_num(cfath.getCom_Num());
			}
		}
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("203");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return SUCCESS;
	}

	/**
	 * 
	 * @Title: getDatadis @Description: 获取类型信息 @param: @return @param: @throws
	 *         WebException @param: @throws SQLException @return: String @throws
	 */
	public String getDatadis() throws WebException, SQLException {
		SystemType defaultapp = (SystemType) session.get("defaultapp");
		List<JobDTO> list = new ArrayList<JobDTO>();
		if ("active".equals(this.model.getCom_Type())) {    
			JobDTO jd = new JobDTO();
			jd.setId("Y");
			jd.setName("激活");
			list.add(jd);
			jd = new JobDTO();
			jd.setId("N");
			jd.setName("未激活");
			list.add(jd);
		}else if("DYXS".equals(this.model.getCom_Type())){
			JobDTO jd = new JobDTO();
			jd.setId("0");
			jd.setName("不显示");
			list.add(jd);
			jd = new JobDTO();
			jd.setId("1");
			jd.setName("显示");
			list.add(jd);
		}else if("REPORTTYPE".equals(this.model.getCom_Type())){
			JobDTO jd = new JobDTO();
			jd.setId("");
			jd.setName("不选");
			list.add(jd);
			jd = new JobDTO();
			jd.setId("CPT");
			jd.setName(".CPT");
			list.add(jd);
			jd = new JobDTO();
			jd.setId("FRM");
			jd.setName(".FRM");
			list.add(jd);	
			jd = new JobDTO();
			jd.setId("VIEW");
			jd.setName(".CPT&VIEW");
			list.add(jd);
		}else if("EXAMSTATUS4".equals(this.model.getCom_Type())){
			JobDTO jd = new JobDTO();
			jd = new JobDTO();
			jd.setId("'Y'");
			jd.setName("预约");
			list.add(jd);
			jd = new JobDTO();
			jd.setId("'D'");
			jd.setName("登记");
			list.add(jd);
			jd = new JobDTO();
			jd.setId("'J'");
			jd.setName("检查中");
			list.add(jd);	
			jd = new JobDTO();
			jd.setId("'Z'");
			jd.setName("已终检");
			list.add(jd);
			jd = new JobDTO();
			jd.setId("'W'");
			jd.setName("无需总检");
			list.add(jd);
			jd = new JobDTO();
			jd.setId("'T'");
			jd.setName("特殊通知");
			list.add(jd);
		}else if("EXAMTYPE".equals(this.model.getCom_Type())){
			JobDTO jd = new JobDTO();
			jd.setId("T");
			jd.setName("团体体检");
			list.add(jd);
			jd = new JobDTO();
			jd.setId("G");
			jd.setName("个人体检");
			list.add(jd);						
		}else if("WHCD".equals(this.model.getCom_Type())){//文化程度
			list = companyService.getDegreeOfedu();
			List<JobDTO> listnew = new ArrayList<JobDTO>();			
			JobDTO jd = new JobDTO();
			jd = new JobDTO();
			jd.setId("");
			jd.setName("不选择");
			listnew.add(jd);
			for(JobDTO jdto:list){
				listnew.add(jdto);
			}
			list = listnew;
		}else if("ZZMM".equals(this.model.getCom_Type())){//政治面貌
			list = companyService.getDatadis("ZZMM",defaultapp.getComid());
			List<JobDTO> listnew = new ArrayList<JobDTO>();			
			JobDTO jd = new JobDTO();
			jd = new JobDTO();
			jd.setId("");
			jd.setName("不选择");
			listnew.add(jd);
			for(JobDTO jdto:list){
				listnew.add(jdto);
			}
			list = listnew;
		}else if("HFLX".equals(this.model.getCom_Type())){
			list = companyService.getDatadis("HYZT",defaultapp.getComid());
			List<JobDTO> listnew = new ArrayList<JobDTO>();			
			JobDTO jd = new JobDTO();
			jd = new JobDTO();
			jd.setId("");
			jd.setName("不选择");
			listnew.add(jd);
			for(JobDTO jdto:list){
				jdto.setId(jdto.getName());
				listnew.add(jdto);
			}
			list = listnew;
		}/*else if("HFLX".equals(this.model.getCom_Type())){
			JobDTO jd = new JobDTO();
			jd.setId("");
			jd.setName("不选择");
			list.add(jd);
			jd = new JobDTO();
			jd.setId("未婚");
			jd.setName("未婚");
			list.add(jd);
			jd = new JobDTO();
			jd.setId("已婚");
			jd.setName("已婚");
			list.add(jd);
			jd = new JobDTO();
			jd.setId("离异");
			jd.setName("离异");
			list.add(jd);
			jd = new JobDTO();
			jd.setId("丧偶");
			jd.setName("丧偶");
			list.add(jd);
			jd = new JobDTO();
			jd.setId("已婚未育");
			jd.setName("已婚未育");
			list.add(jd);
			jd = new JobDTO();
			jd.setId("已婚已育");
			jd.setName("已婚已育");
			list.add(jd);		
		}*/else if("EXAMSTATUS".equals(this.model.getCom_Type())){
			JobDTO jd = new JobDTO();
			jd.setId("");
			jd.setName("所有");
			list.add(jd);
			jd = new JobDTO();
			jd.setId("Y");
			jd.setName("已预约");
			list.add(jd);
			jd = new JobDTO();
			jd.setId("D");
			jd.setName("已登记");
			list.add(jd);
			jd = new JobDTO();
			jd.setId("J");
			jd.setName("检查中");
			list.add(jd);
			jd = new JobDTO();
			jd.setId("Z");
			jd.setName("已终检");
			list.add(jd);
		}else if("EXAMSTATUS2".equals(this.model.getCom_Type())){
			JobDTO jd = new JobDTO();
			jd.setId("");
			jd.setName("所有");
			list.add(jd);
			jd = new JobDTO();
			jd.setId("Y");
			jd.setName("预约");
			list.add(jd);
			jd = new JobDTO();
			jd.setId("D");
			jd.setName("登记");
			list.add(jd);
			jd = new JobDTO();
			jd.setId("J");
			jd.setName("检查中");
			list.add(jd);			
		}else if("EXAMSTATUS3".equals(this.model.getCom_Type())){
			JobDTO jd = new JobDTO();
			jd = new JobDTO();
			jd.setId("'Y'");
			jd.setName("预约");
			list.add(jd);
			jd = new JobDTO();
			jd.setId("'D'");
			jd.setName("登记");
			list.add(jd);
			jd = new JobDTO();
			jd.setId("'J'");
			jd.setName("检查中");
			list.add(jd);	
			jd = new JobDTO();
			jd.setId("'Z'");
			jd.setName("已终检");
			list.add(jd);
		}else if("FASHLX".equals(this.model.getCom_Type())){//方案审核类型	
			JobDTO jd = new JobDTO();
			jd.setId("2");
			jd.setName("方案审核通过");
			list.add(jd);			
			jd = new JobDTO();
			jd.setId("1");
			jd.setName("方案审核未通过");
			list.add(jd);
		}else if("BATCHSTATS".equals(this.model.getCom_Type())){//体检状态	
			JobDTO jd = new JobDTO();
			jd.setId("2");
			jd.setName("体检任务审核通过");
			list.add(jd);			
			jd = new JobDTO();
			jd.setId("1");
			jd.setName("体检任务审核未通过");
			list.add(jd);
			jd = new JobDTO();
			jd.setId("0");
			jd.setName("体检任务未审核");
			list.add(jd);			
		}else if("HTSHLX".equals(this.model.getCom_Type())){//合同审核类型		
			JobDTO jd = new JobDTO();
			jd.setId("2");
			jd.setName("合同审核通过");
			list.add(jd);			
			jd = new JobDTO();
			jd.setId("1");
			jd.setName("合同审核未通过");
			list.add(jd);			
		}else if("RQSTATUS".equals(this.model.getCom_Type())){//读取状态	
			JobDTO jd = new JobDTO();
			jd.setId("0");
			jd.setName("未读取");
			list.add(jd);			
			jd = new JobDTO();
			jd.setId("1");
			jd.setName("已读取");
			list.add(jd);	
		}else if("XBLX".equals(this.model.getCom_Type())){
			JobDTO jd = new JobDTO();
			jd.setId("");
			jd.setName("不选择");
			list.add(jd);
			jd = new JobDTO();
			jd.setId("女");
			jd.setName("女");
			list.add(jd);
			jd = new JobDTO();
			jd.setId("男");
			jd.setName("男");
			list.add(jd);
		}else if("XBLX2".equals(this.model.getCom_Type())){
			JobDTO jd = new JobDTO();
			jd.setId("女");
			jd.setName("女");
			list.add(jd);
			jd = new JobDTO();
			jd.setId("男");
			jd.setName("男");
			list.add(jd);
		}else if("FFFS".equals(this.model.getCom_Type())){//付费方式
			JobDTO jd = new JobDTO();
			jd = new JobDTO();
			jd.setId("T");
			jd.setName("团体结算");
			list.add(jd);
			jd = new JobDTO();
			jd.setId("G");
			jd.setName("个人结算");
			list.add(jd);
		}else if("MZLX2".equals(this.model.getCom_Type())){//民族
			list = companyService.getDatadis("MZLX",defaultapp.getComid());
			List<JobDTO> listnew = new ArrayList<JobDTO>();			
			JobDTO jd = new JobDTO();
			jd = new JobDTO();
			jd.setId("");
			jd.setName("不选择");
			listnew.add(jd);
			for(JobDTO jdto:list){
				listnew.add(jdto);
			}
			list = listnew;
		}else if("HY".equals(this.model.getCom_Type())){//行业
			List<ZybEconomicTreeDTO> zyblist= new ArrayList<ZybEconomicTreeDTO>();
			zyblist = this.zybOccuService.getIndustryAll();
			List<JobDTO> listnew = new ArrayList<JobDTO>();			
			JobDTO jd = new JobDTO();
			jd = new JobDTO();
			jd.setId("");
			jd.setName("不选择");
			listnew.add(jd);
			for(ZybEconomicTreeDTO jdto:zyblist){
				JobDTO jd1 = new JobDTO();
				jd1.setId(jdto.getIndustry_code());
				jd1.setName(jdto.getIndustry_name_f());
				listnew.add(jd1);
			}
			list = listnew;
		}else if("JBFL".equals(this.model.getCom_Type())){//疾病分类
			list = companyService.getDatadis("JBFL",defaultapp.getComid());
			List<JobDTO> listnew = new ArrayList<JobDTO>();			
			JobDTO jd = new JobDTO();
			jd = new JobDTO();
			jd.setId("0");
			jd.setName("不选择");
			listnew.add(jd);
			for(JobDTO jdto:list){
				listnew.add(jdto);
			}
			list = listnew;
		}else if("USERUSR".equals(this.model.getCom_Type())){//人员信息
			UserDTO user = (UserDTO) session.get("username");
			list = companyService.getUsers(user.getCenter_id());
		}else if("JINGJILX".equals(this.model.getCom_Type())){//经济类型
			list = companyService.getDatadisjjlx();
		}else if("HANGYELX".equals(this.model.getCom_Type())){//行业类型
			list = companyService.getDatadishylx();
		}else if("DANWEIGM".equals(this.model.getCom_Type())){//单位规模
			list = companyService.getDatadisdanwgm();
		}else if("XINGZHENGQH".equals(this.model.getCom_Type())){//行政区划
			list = companyService.getDatadixzqh();
		}else if("USERUSR2".equals(this.model.getCom_Type())){//人员信息
			UserDTO user = (UserDTO) session.get("username");
			list = companyService.getUsers(user.getCenter_id());
			List<JobDTO> listnew = new ArrayList<JobDTO>();			
			JobDTO jd = new JobDTO();
			jd = new JobDTO();
			jd.setId("");
			jd.setName("不选择");
			listnew.add(jd);
			for(JobDTO jdto:list){
				listnew.add(jdto);
			}
			list = listnew;
		}else if("REPORTPARENT".equals(this.model.getCom_Type())){//报表级别			
			list = companyService.getReportMenu();			
		}else if("SFLX2".equals(this.model.getCom_Type())){//身份类型  
			list = companyService.getDatadis("SFLX",defaultapp.getComid());
			List<JobDTO> listnew = new ArrayList<JobDTO>();			
			JobDTO jd = new JobDTO();
			jd = new JobDTO();
			jd.setId("");
			jd.setName("不选择");
			listnew.add(jd);
			for(JobDTO jdto:list){
				listnew.add(jdto);
			}
			list = listnew;
		}else if("DXMBNR".equals(this.model.getCom_Type())){//身份类型  
			list = companyService.getDatadis("DXMBNR",defaultapp.getComid());
		
		}else if("DXMBLX".equals(this.model.getCom_Type())){//身份类型  
			list = companyService.getDatadis("DXMBLX",defaultapp.getComid());
			
		}else if("RYLB".equals(this.model.getCom_Type())){
			list = this.customerInfoService.getcustomerType();
		}else if("RYLB2".equals(this.model.getCom_Type())){
			list = this.customerInfoService.getcustomerType();
			List<JobDTO> listnew = new ArrayList<JobDTO>();			
			JobDTO jd = new JobDTO();
			jd = new JobDTO();
			jd.setId("");
			jd.setName("不选择");
			listnew.add(jd);
			for(JobDTO jdto:list){
				listnew.add(jdto);
			}
			list = listnew;
		} else if ("USERS".equals(this.model.getCom_Type())) {
			UserDTO user = (UserDTO) session.get("username");
			String xtgn_id = "";
			String res_code = "";
			switch (this.model.getFlow_code()) {
			case "h" : 
				xtgn_id="1200";
				res_code="RS014";
				break;
			case "s" : 
				xtgn_id="1211";
				res_code="RS015";
				break;
			case "z" : 
				xtgn_id="1230";
				res_code="RS016";
				break;
			case "p" : 
				xtgn_id="1218";
				res_code="RS017";
				break;
			case "e" : 
				xtgn_id="1224";
				res_code="yes";
				break;
			case "m" : 
				xtgn_id="1234";
				res_code="RS019";
				break;
			case "v" : 
				xtgn_id="1308";
				res_code="yes";
				break;
			}
			if(this.model.getFromUserId() > 0) {
				List<UserUseDTO> li = this.customerInfoService.getUserListByWeb_xtgnid(xtgn_id);
				for (UserUseDTO us : li) {
					if (us.getId() != this.model.getFromUserId()) {
						JobDTO jd = new JobDTO();
						jd.setId(us.getId() + "");
						jd.setName(us.getChi_name());
						list.add(jd);
					}
				}
			} else {
				JobDTO jd = new JobDTO();
				jd = new JobDTO();
				jd.setId(user.getUserid() + "");
				jd.setName("本人");
				list.add(jd);
				
				String all = "'" + user.getUserid() + "'";
				List<UserUseDTO> li = new ArrayList<>();
				if("yes".equals(res_code)) {
					li = this.customerInfoService.getUserListByWeb_xtgnid(xtgn_id);
				} else {
					List<WebResrelAtionship> webResource = user.getWebResource();
					if(webResource!=null){
						for (int i = 0; i < webResource.size(); i++) {
							if((webResource.get(i).getRes_code().equals(res_code) && "1".equals(webResource.get(i).getDatavalue())) ){
								li = this.customerInfoService.getUserListByWeb_xtgnid(xtgn_id);
								break;
							}
						}
					}
				}
				boolean hasResource = false;
				for (UserUseDTO us : li) {
					if (us.getId() != user.getUserid()) {
						hasResource = true;
						jd = new JobDTO();
						jd.setId(us.getId() + "");
						jd.setName(us.getChi_name());
						list.add(jd);
						all +=  ",'" + us.getId() + "'";
					}
				}
				if(hasResource) {
					jd = new JobDTO();
					jd.setId(all);
					jd.setName("以上全部");
					list.add(jd);
				}
			}
		} else if ("CHECKERS".equals(this.model.getCom_Type())) {
			String rscode = "RS001";	//总检人员审核权限
			List<UserUseDTO> li = new ArrayList<>();
			li = this.customerInfoService.getUserListByRSCode(rscode);
			for (UserUseDTO us : li) {
				JobDTO jd = new JobDTO();
				jd.setId(us.getId() + "");
				jd.setName(us.getChi_name());
				list.add(jd);
			}
		} else if ("WJZLX".equals(this.model.getCom_Type())) {//危急值类型
			list = companyService.getDatadis("WJZLX",defaultapp.getComid());
			List<JobDTO> listnew = new ArrayList<JobDTO>();			
			JobDTO jd = new JobDTO();
			jd = new JobDTO();
			jd.setId("");
			jd.setName("不选择");
			listnew.add(jd);
			for(JobDTO jdto:list){
				listnew.add(jdto);
			}
			list = listnew;
		} else {
			list = companyService.getDatadis(this.model.getCom_Type(),defaultapp.getComid());
		}
		this.outJsonResult(list);
		return NONE;
	}

	
/**
 * 
     * @Title: getDatadisKongGe   
     * @Description: TODO(这里用一句话描述这个方法的作用)   
     * @param: @return
     * @param: @throws WebException
     * @param: @throws SQLException      
     * @return: String      
     * @throws
 */
public String getDatadisKongGe() throws WebException, SQLException {
	SystemType app_type = (SystemType) session.get("defaultapp");
	List<JobDTO> list = new ArrayList<JobDTO>();
	if ("active".equals(this.model.getCom_Type())) {    
		JobDTO jd = new JobDTO();
		jd.setId("Y");
		jd.setName("激活");
		list.add(jd);
		jd = new JobDTO();
		jd.setId("N");
		jd.setName("未激活");
		list.add(jd);
	}else if("HFLX".equals(this.model.getCom_Type())){
		JobDTO jd = new JobDTO();
		jd.setId("");
		jd.setName("不选择");
		list.add(jd);
		jd = new JobDTO();
		jd.setId("已婚");
		jd.setName("已婚");
		list.add(jd);
		jd = new JobDTO();
		jd.setId("未婚");
		jd.setName("未婚");
		list.add(jd);			
	}else if("EXAMSTATUS".equals(this.model.getCom_Type())){
		JobDTO jd = new JobDTO();
		jd.setId("");
		jd.setName("所有");
		list.add(jd);
		jd = new JobDTO();
		jd.setId("Y");
		jd.setName("预约");
		list.add(jd);
		jd = new JobDTO();
		jd.setId("D");
		jd.setName("登记");
		list.add(jd);
		jd = new JobDTO();
		jd.setId("J");
		jd.setName("检查中");
		list.add(jd);
		jd = new JobDTO();
		jd.setId("Z");
		jd.setName("已终检");
		list.add(jd);
	}else if("FASHLX".equals(this.model.getCom_Type())){//方案审核类型	
		JobDTO jd = new JobDTO();
		jd.setId("2");
		jd.setName("方案审核通过");
		list.add(jd);			
		jd = new JobDTO();
		jd.setId("1");
		jd.setName("方案审核未通过");
		list.add(jd);	
	}else if("BATCHSTATS".equals(this.model.getCom_Type())){//体检状态	
		JobDTO jd = new JobDTO();
		jd.setId("2");
		jd.setName("体检任务审核通过");
		list.add(jd);			
		jd = new JobDTO();
		jd.setId("1");
		jd.setName("体检任务审核未通过");
		list.add(jd);
		jd = new JobDTO();
		jd.setId("0");
		jd.setName("体检任务未审核");
		list.add(jd);			
	}else if("HTSHLX".equals(this.model.getCom_Type())){//合同审核类型		
		JobDTO jd = new JobDTO();
		jd.setId("2");
		jd.setName("合同审核通过");
		list.add(jd);			
		jd = new JobDTO();
		jd.setId("1");
		jd.setName("合同审核未通过");
		list.add(jd);			
	}else if("XBLX".equals(this.model.getCom_Type())){
		JobDTO jd = new JobDTO();
		jd.setId("");
		jd.setName("不选择");
		list.add(jd);
		jd = new JobDTO();
		jd.setId("男");
		jd.setName("男");
		list.add(jd);
		jd = new JobDTO();
		jd.setId("女");
		jd.setName("女");
		list.add(jd);
	}else if("XBLX2".equals(this.model.getCom_Type())){
		JobDTO jd = new JobDTO();
		jd.setId("男");
		jd.setName("男");
		list.add(jd);
		jd = new JobDTO();
		jd.setId("女");
		jd.setName("女");
		list.add(jd);
	}else if("FFFS".equals(this.model.getCom_Type())){//付费方式
		JobDTO jd = new JobDTO();
		jd = new JobDTO();
		jd.setId("T");
		jd.setName("团体结算");
		list.add(jd);
		jd = new JobDTO();
		jd.setId("G");
		jd.setName("个人结算");
		list.add(jd);
	}else if("MZLX2".equals(this.model.getCom_Type())){//民族
		list = companyService.getDatadis("MZLX",app_type.getComid());
		List<JobDTO> listnew = new ArrayList<JobDTO>();			
		JobDTO jd = new JobDTO();
		jd = new JobDTO();
		jd.setId("");
		jd.setName("不选择");
		listnew.add(jd);
		for(JobDTO jdto:list){
			listnew.add(jdto);
		}
		list = listnew;
	}else if("SFLX2".equals(this.model.getCom_Type())){//身份类型
		list = companyService.getDatadis("SFLX",app_type.getComid());
		List<JobDTO> listnew = new ArrayList<JobDTO>();			
		JobDTO jd = new JobDTO();
		jd = new JobDTO();
		jd.setId("");
		jd.setName("不选择");
		listnew.add(jd);
		for(JobDTO jdto:list){
			listnew.add(jdto);
		}
		list = listnew;
	}else if("RYLB".equals(this.model.getCom_Type())){
		list = this.customerInfoService.getcustomerType();
	} else {
		List<JobDTO> jj= companyService.getDatadis(this.model.getCom_Type(),app_type.getComid());
		JobDTO dto = new JobDTO();
		dto.setName("无");
		list.add(dto);
		for (JobDTO job : jj) {
			JobDTO sd = new JobDTO();
			sd.setId(job.getId());
			sd.setName(job.getName());
			list.add(sd);
		}
	}
	this.outJsonResult(list);
	return NONE;
}

	/**
	 * 
	 * @Title: companychangedo @Description:
	 *         执行单位移动 494 @param: @return @param: @throws
	 *         WebException @param: @throws SQLException @return: String @throws
	 */
	public String companychangedo() throws WebException, SQLException {
		UserDTO user = (UserDTO) session.get("username");
		//long oldid = this.model.getId();
		long oldlevel = 0;
		long newid = 0;
		long newlevel = 0;
		boolean flags = true;
		CompanyInfo newci = new CompanyInfo();
		if ((this.model.getParent_com_id() == null) || (this.model.getParent_com_id().longValue() < 0)) {
			this.message = "error-目标单位选择无效！";
			flags = false;
		} else if (this.model.getParent_com_id().longValue() == 0) {
			newid = 0;
			newlevel = 1;
			//newcom_num = "000";
		} else {
			newid = this.model.getParent_com_id().longValue();			
			newci = this.companyService.findComByID(newid);
			if ((newci == null) || (newci.getId() <= 0)) {
				this.message = "error-无此目标单位，选择无效！";
				flags = false;
			} else {
				newlevel = newci.getCom_Level();
				//newcom_num = newci.getCom_Num();
			}
		}

		if (flags) {
			if (this.model.getId() <= 0) {
				this.message = "error-原单位选择无效！";
			} else {
				
				boolean sflag = this.companyService.getcompanySubNodeById(this.model.getId(),this.model.getParent_com_id().longValue());
				if(sflag){
					this.message = "error-不能将单位移动到自己的子单位下面！";
				}else{
				CompanyInfo oldci = new CompanyInfo();
				oldci = this.companyService.findComByID(this.model.getId());
				
				String oldname=oldci.getCom_Name().trim();
				CompanyInfo checkciold = new CompanyInfo();
				checkciold = this.companyService.getComByName(newci.getId(),oldname,user);
				if((checkciold!=null)&&(checkciold.getId()>0)){
					this.message = "error-移动无效或者相同的单位名称已经存在！";
				}else{
				
				if ((oldci == null) || (oldci.getId() <= 0)) {
					CompanyDepartment oldcinf = new CompanyDepartment();
					oldcinf = this.companyService.getCompanyDepartmentByid(this.model.getId());
					if ((oldcinf == null) || (oldcinf.getId() <= 0)) {
						this.message = "error-无此单位或者部门，选择无效！";
					} else {
						// 移动部门
						if (newid <= 0) {
							this.message = "error-不能将部门放在一级单位目录下面，选择无效！";
						} else {
							CompanyDepartment newcinf = new CompanyDepartment();
							newcinf = oldcinf;
							newcinf.setCompany_Id(newid);
							newcinf.setUpdater(user.getUserid());
							newcinf.setUpdate_Time(DateTimeUtil.parse());
							this.companyService.updateCompanyDepartment(newcinf);
							this.message = "ok-单位部门移动成功！";
							
							//UserDTO user = (UserDTO) session.get("username");
							SysLog sl =  new SysLog();
							sl.setCenter_num(user.getCenter_num());
							sl.setUserid(user.getUserid()+"");
							sl.setOper_type("2");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
							sl.setXtgnid("");//可不填写
							sl.setXtgnid2("494");//子功能id 必须填写
							sl.setExplain("单位移动 "+oldcinf.getId()+" "+newid);//操作说明
							syslogService.saveSysLog(sl);
						}
					}
				} else {
					int maxlevel = this.companyService.getcompanyById(oldci.getId());
			          oldlevel = oldci.getCom_Level();
			          if (maxlevel - oldlevel + newlevel > 5L)
			          {
			            this.message = "error-单位级别超限（目前只支持5级），选择无效！";
			          }
			          else
			          {
			            newci = oldci;
			            newci.setParent_com_id(Long.valueOf(newid));
			            newci.setCom_Level(newlevel + 1L);
			            newci.setUpdater(Long.valueOf(user.getUserid()));
			            newci.setUpdate_Time(DateTimeUtil.parse());
			            boolean msflag = this.companyService.chargeCompanyInfo(newci);
			            
			            if (msflag) {
			              this.message = "ok-单位部门移动成功！";
			              SysLog sl =  new SysLog();
							sl.setCenter_num(user.getCenter_num());
							sl.setUserid(user.getUserid()+"");
							sl.setOper_type("2");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
							sl.setXtgnid("");//可不填写
							sl.setXtgnid2("494");//子功能id 必须填写
							sl.setExplain("单位移动 "+oldci.getId()+" "+newid);//操作说明
							syslogService.saveSysLog(sl);
			            } else {
			              this.message = "error-单位部门移动失败！";
			            }
			          }
				}
				}
				}
			}
		}
		this.outJsonStrResult(message);
		return NONE;

	}

	/**
	 * 
	 * @Title: editcompany @Description: 单位信息维护 495 @param: @return @param: @throws
	 *         WebException @param: @throws SQLException @return: String @throws
	 */
	public String editcompany() throws WebException, SQLException {
		UserDTO user = (UserDTO) session.get("username");
		if (this.model.getEdittype() <= 0) {
			this.message = "error-无效请求！";
		}else{
		 if((this.model.getCom_Level()==0)&&(this.model.getEdittype()!=4)){
			this.model.setEdittype(2);
			this.model.setCom_Level(1);
		}
		
		if (this.model.getEdittype() == 1) {// 本级单位下创建新单位
			if (model.getCom_Level() >= 5) {
				this.message = "error-不能在本级单位下创建新单位，因为只支持5级单位！";
			} else {
				
				CompanyInfo ciold = new CompanyInfo();
				ciold = this.companyService.getComByName(model.getId(),this.model.getCom_Name().trim(),user);
				if((ciold!=null)&&(ciold.getId()>0)){
					this.message = "error-相同的单位名称已经存在!-"+ciold.getId();
				}else{
				CompanyInfo ci = new CompanyInfo();
				ci.setAddress(model.getAddress());
				ci.setParent_com_id(model.getId());
				ci.setCom_Level(model.getCom_Level() + 1);
				ci.setCom_Name(model.getCom_Name());
				ci.setCom_Num(this.batchService.GetCreateID("com_num", user.getCenter_num()));
				ci.setCom_Type(model.getCom_Type());
				ci.setContact_Name(model.getContact_Name());
				ci.setContact_Phone(model.getContact_Phone());
				ci.setCreater(user.getUserid());
				ci.setUpdater(user.getUserid());
				ci.setUpdate_Time(DateTimeUtil.parse());
				ci.setCreate_Time(DateTimeUtil.parse());
				ci.setEmail(model.getEmail());
				ci.setIs_Active(model.getIs_Active());
				ci.setRemark(model.getRemark());
				ci.setName_pinyin(model.getName_pinyin());
				this.companyService.saveCompanyInfo(ci,user);
				ci.setKeShi_Name(model.getKeShi_Name()); //科室联系人
				ci.setCom_phone(model.getCom_phone());
				ci.setCom_fax(model.getCom_fax());
				ci.setCom_jianjie(model.getCom_jianjie()); //公司简介
				ci.setAssigned_unit_code(model.getAssigned_unit_code());
				webSynchroService.updateWebSynchro(ci.getId()+"", '6');
				this.message = "ok-单位创建成功！";
				
				SysLog sl =  new SysLog();
				sl.setCenter_num(user.getCenter_num());
				sl.setUserid(user.getUserid()+"");
				sl.setOper_type("1");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
				sl.setXtgnid("");//可不填写
				sl.setXtgnid2("495");//子功能id 必须填写
				sl.setExplain("单位创建 "+ci.getId()+" "+ci.getCom_Name());//操作说明
				syslogService.saveSysLog(sl);
			}
			}

		} else if (this.model.getEdittype() == 2) {// 上级单位下创建新单位
			CompanyInfo ciold = new CompanyInfo();
			ciold = this.companyService.getComByName(model.getCompany_Id(),this.model.getCom_Name().trim(),user);
			if((ciold!=null)&&(ciold.getId()>0)){
				this.message = "error-相同的单位名称已经存在！";
			}else{
				CompanyInfo ci = new CompanyInfo();
				ci.setAddress(model.getAddress());
				ci.setParent_com_id(model.getCompany_Id());
				ci.setCom_Num(this.batchService.GetCreateID("com_num", user.getCenter_num()));
				ci.setCom_Level(model.getCom_Level());
				ci.setCom_Name(model.getCom_Name());
				ci.setCom_Type(model.getCom_Type());
				ci.setContact_Name(model.getContact_Name());
				ci.setContact_Phone(model.getContact_Phone());
				ci.setCreater(user.getUserid());
				ci.setUpdater(user.getUserid());
				ci.setUpdate_Time(DateTimeUtil.parse());
				ci.setCreate_Time(DateTimeUtil.parse());
				ci.setEmail(model.getEmail());
				ci.setIs_Active(model.getIs_Active());
				ci.setRemark(model.getRemark());
				ci.setName_pinyin(model.getName_pinyin());
				ci.setKeShi_Name(model.getKeShi_Name());
				ci.setKeShi_Name(model.getKeShi_Name()); //科室联系人
				ci.setCom_phone(model.getCom_phone());
				ci.setCom_fax(model.getCom_fax());
				ci.setAssigned_unit_code(model.getAssigned_unit_code());
				ci.setCom_jianjie(model.getCom_jianjie()); //公司简介
				this.companyService.saveCompanyInfo(ci,user);
				webSynchroService.updateWebSynchro(ci.getId()+"", '6');
				this.message = "ok-单位创建成功！";
				SysLog sl =  new SysLog();
				sl.setCenter_num(user.getCenter_num());
				sl.setUserid(user.getUserid()+"");
				sl.setOper_type("1");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
				sl.setXtgnid("");//可不填写
				sl.setXtgnid2("495");//子功能id 必须填写
				sl.setExplain("单位创建 "+ci.getId()+" "+ci.getCom_Name());//操作说明
				syslogService.saveSysLog(sl);
			}
		} else if (this.model.getEdittype() == 3) {// 修改
			CompanyInfo ci = new CompanyInfo();
			ci = this.companyService.findComByID(this.model.getId());
			if ((ci == null) || (ci.getCom_Level() <= 0)) {
				this.message = "error-单位不存在，无法修改！";
			} else {
				
				CompanyInfo ciold = new CompanyInfo();
				ciold = this.companyService.getComByName(model.getCompany_Id(),model.getCom_Name().trim(),user);
				if((ciold!=null)&&(ciold.getId()>0)&&(ciold.getId()!=this.model.getId())){
					this.message = "error-相同的单位名称已经存在！";
				}else{
				
				ci.setAddress(model.getAddress());
				ci.setParent_com_id(model.getCompany_Id());
				ci.setCom_Level(model.getCom_Level());
				ci.setCom_Name(model.getCom_Name().trim());
				ci.setCom_Type(model.getCom_Type());
				ci.setContact_Name(model.getContact_Name());
				ci.setContact_Phone(model.getContact_Phone());
				ci.setCom_Num(model.getCom_Num());
				ci.setUpdater(user.getUserid());
				ci.setUpdate_Time(DateTimeUtil.parse());
				ci.setEmail(model.getEmail());
				ci.setIs_Active(model.getIs_Active());
				ci.setName_pinyin(model.getName_pinyin());
				ci.setRemark(model.getRemark());
				ci.setKeShi_Name(model.getKeShi_Name()); //科室联系人
				ci.setCom_phone(model.getCom_phone());
				ci.setCom_fax(model.getCom_fax());
				ci.setAssigned_unit_code(model.getAssigned_unit_code());
				ci.setCom_jianjie(model.getCom_jianjie()); //公司简介
				if (ci.getParent_com_id() == 1)
					ci.setParent_com_id(null);
				this.companyService.updateCompanyInfo(ci,user);
				webSynchroService.updateWebSynchro(ci.getId()+"", '6');
				this.message = "ok-单位修改成功！";
				SysLog sl =  new SysLog();
				sl.setCenter_num(user.getCenter_num());
				sl.setUserid(user.getUserid()+"");
				sl.setOper_type("2");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
				sl.setXtgnid("");//可不填写
				sl.setXtgnid2("495");//子功能id 必须填写
				sl.setExplain("单位修改 "+ci.getId()+" "+ci.getCom_Name());//操作说明
				syslogService.saveSysLog(sl);
				
				customerInfoService.updateCompanyNameInExamInfo(model.getCom_Name().trim(), ci.getId());
				}
			}
		} else if (this.model.getEdittype() == 4) {// 删除
			CompanyInfo ci = new CompanyInfo();
			ci = this.companyService.findComByID(this.model.getId());
			if ((ci == null) || (ci.getCom_Level() <= 0)) {
				this.message = "error-单位不存在，无法删除！";
			} else {
				List<CompanyDepartment> list = new ArrayList<CompanyDepartment>();
				list = this.companyService.getCompanyDepartmentByComid(this.model.getId());
				if((list!=null)&&(list.size()>0)){
					this.message = "error-单位下面存在部门，无法删除！";
				}else{
					List<BatchDTO> listb=new ArrayList<BatchDTO>();
					listb =this.batchService.getbatchList(this.model.getId(),user.getCenter_num(),"");
					if((listb!=null)&&(listb.size()>0)){
						this.message = "error-单位下面存在体检任务，无法删除！";
					}else{
						 List<ContractDTO> listc=new ArrayList<ContractDTO>();
						 listc =this.batchService.contractlistshow(this.model.getId());
						 if((listc!=null)&&(listc.size()>0)){
								this.message = "error-单位下面存在合同，无法删除！";
							}else{
								List<TreeDTO>  comlist=new ArrayList<TreeDTO>();
								comlist=this.companyService.getCompanyForAll(this.model.getId());
								boolean delflag=false;
								for(TreeDTO tree:comlist){
									int counts = this.batchService.examcountforcomid(Long.valueOf(tree.getId()),user);
									if(counts>0){
										delflag=true;
										break;
									}
								}
								if(delflag){
									this.message = "error-单位下面存在人员，无法删除！";
								}else{
								ci.setUpdater(user.getUserid());
								ci.setUpdate_Time(DateTimeUtil.parse());
								int cont = this.companyService.updateCompanyInfoIsActive(ci,user);
								if(cont==1){
									ci.setIs_Active("N");
									ci.setUpdater(user.getUserid());
                                    ci.setUpdate_Time(DateTimeUtil.parse());
									companyService.removeCompanyInfo(ci);
								}
								webSynchroService.updateWebSynchro(ci.getId()+"", '6');
								this.message = "ok-单位删除成功！";
								SysLog sl =  new SysLog();
								sl.setCenter_num(user.getCenter_num());
								sl.setUserid(user.getUserid()+"");
								sl.setOper_type("3");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
								sl.setXtgnid("");//可不填写
								sl.setXtgnid2("495");//子功能id 必须填写
								sl.setExplain("单位创建 "+ci.getId()+" "+ci.getCom_Name());//操作说明
								syslogService.saveSysLog(sl);
							}
							}					
					}					
				}				
			}
		} else {
			this.message = "error-无效参数！";
		}
		}
		this.outJsonStrResult(message);
		return NONE;
	}

	/**
	 * 
	 * @Title: danweiedit @Description: 部门编辑 @param: @return @param: @throws
	 *         WebException @param: @throws SQLException @return: String @throws
	 */
	public String danweiedit() throws WebException, SQLException {
		CompanyInfo ci = new CompanyInfo();
		ci = this.companyService.findComByID(this.model.getCompany_Id());
		if (this.model.getId() > 0) {
			CompanyDepartment cinf = new CompanyDepartment();
			cinf = this.companyService.getCompanyDepartmentByid(this.model.getId());
			this.model.setDep_Name(cinf.getDep_Name());
			this.model.setCom_Level(cinf.getId());
			this.model.setCompany_Id(this.model.getCompany_Id());
			this.model.setId(cinf.getId());
			this.model.setCom_Name(ci.getCom_Name());
		} else {
			this.model.setCom_Name(ci.getCom_Name());
			this.model.setId(0);
		}

		return SUCCESS;
	}

	/**
	 * 
	 * @Title: danweieditdo @Description: 单位部门保存 496 @param: @return @param: @throws
	 *         WebException @param: @throws SQLException @return: String @throws
	 */
	public String danweieditdo() throws WebException, SQLException {
		UserDTO user = (UserDTO) session.get("username");

		if (this.model.getId() > 0) {
			CompanyDepartment cinf = new CompanyDepartment();
			cinf = this.companyService.getCompanyDepartmentByid(this.model.getId());
			cinf.setDep_Name(this.model.getDep_Name());
			cinf.setUpdater(user.getUserid());
			cinf.setUpdate_Time(DateTimeUtil.parse());
			this.companyService.updateCompanyDepartment(cinf);
			this.message = "ok-单位部门修改成功！";
			SysLog sl =  new SysLog();
			sl.setCenter_num(user.getCenter_num());
			sl.setUserid(user.getUserid()+"");
			sl.setOper_type("2");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
			sl.setXtgnid("");//可不填写
			sl.setXtgnid2("496");//子功能id 必须填写
			sl.setExplain("单位部门修改 "+cinf.getCompany_Id()+" "+cinf.getDep_Name());//操作说明
			syslogService.saveSysLog(sl);
		} else {
			CompanyDepartment cinf = new CompanyDepartment();
			cinf.setDep_Name(this.model.getDep_Name());
			cinf.setCompany_Id(this.model.getCompany_Id());
			cinf.setCreater(user.getUserid());
			cinf.setUpdater(user.getUserid());
			cinf.setUpdate_Time(DateTimeUtil.parse());
			cinf.setCreate_Time(DateTimeUtil.parse());
			this.companyService.saveCompanyDepartment(cinf);
			SysLog sl =  new SysLog();
			sl.setCenter_num(user.getCenter_num());
			sl.setUserid(user.getUserid()+"");
			sl.setOper_type("1");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
			sl.setXtgnid("");//可不填写
			sl.setXtgnid2("496");//子功能id 必须填写
			sl.setExplain("单位部门创建 "+cinf.getCompany_Id()+" "+cinf.getDep_Name());//操作说明
			syslogService.saveSysLog(sl);
			this.message = "ok-单位部门添加成功！";
		}
		this.outJsonStrResult(this.message);
		return NONE;
	}

	/**
	 * 
	 * @Title: danweieditdo @Description: 单位部门保存 208 @param: @return @param: @throws
	 *         WebException @param: @throws SQLException @return: String @throws
	 */
	public String deldanwei() throws WebException{
		if (this.model.getId() > 0) {
			CompanyDepartment cinf = new CompanyDepartment();
			cinf = this.companyService.getCompanyDepartmentByid(this.model.getId());
			this.companyService.delCompanyDepartment(cinf);
			this.message = "ok-单位部门删除成功！";
			UserDTO user = (UserDTO) session.get("username");
			SysLog sl =  new SysLog();
			sl.setCenter_num(user.getCenter_num());
			sl.setUserid(user.getUserid()+"");
			sl.setOper_type("1");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
			sl.setXtgnid("");//可不填写
			sl.setXtgnid2("208");//子功能id 必须填写
			sl.setExplain("单位部门删除 "+cinf.getCompany_Id()+" "+cinf.getDep_Name());//操作说明
			syslogService.saveSysLog(sl);
		} else {
			this.message = "error-无效部门id！";
		}
		this.outJsonStrResult(this.message);
		return NONE;
	}
	
	/**
	 * 
	     * @Title: itempriceshow   
	     * @Description:价表或者诊疗项目更新查询   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	
	public String itempriceshow() throws WebException {
		UserDTO user = (UserDTO) session.get("username");
		CenterConfigurationDTO cc = new CenterConfigurationDTO();
		cc = this.customerInfoService.getCenterconfigByKey("IS_PRIVE_DAY", user.getCenter_num());
		if ("Y".equals(cc.getIs_active())) {
			return SUCCESS;
		}else{
			this.message = "无效请求！";
			return "commerror";
		}		
	}
	
	/**
	 * 
	     * @Title: itempricetotal   
	     * @Description:   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String itempricetotal() throws WebException {
		UserDTO user = (UserDTO) session.get("username");
		CenterConfigurationDTO cc = new CenterConfigurationDTO();
		cc = this.customerInfoService.getCenterconfigByKey("IS_PRIVE_DAY", user.getCenter_num());
		this.message = "error";
		if ("Y".equals(cc.getIs_active())) {
			String days = cc.getConfig_value();
			int idays = Integer.valueOf(days);
			String datetime = DateTimeUtil.DateDiff2(idays);
			//List<JobDTO> list = new ArrayList<JobDTO>();
			//list = this.commService.itemTotal(datetime);
			
			List<JobDTO> listprice = new ArrayList<JobDTO>();
			listprice = this.commService.priceTotal(datetime);
			
			if((listprice!=null)&&(listprice.size()>0)){
				this.message="ok";
			}/*else{
				listprice = this.commService.itemTotal(datetime);
				if((listprice!=null)&&(listprice.size()>0)){
					this.message="ok";
				}
			}*/
			
		}
		this.outJsonStrResult(this.message);
		return NONE;
	}
	/**
	 * 
	     * @Title: pricetotal   
	     * @Description: 诊疗项目查询  
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String itemtotal() throws WebException {
		UserDTO user = (UserDTO) session.get("username");
		CenterConfigurationDTO cc = new CenterConfigurationDTO();
		cc = this.customerInfoService.getCenterconfigByKey("IS_PRIVE_DAY", user.getCenter_num());
		PageReturnDTO contractlist = new PageReturnDTO();
		if ("Y".equals(cc.getIs_active())) {
			String days = cc.getConfig_value();
			int idays = Integer.valueOf(days);
			String datetime = DateTimeUtil.DateDiff2(idays);
			contractlist=this.commService.itemshow(datetime,page,this.rows);
		}
		this.outJsonResult(contractlist);
		return NONE;
	}
	
	/**
	 * 
	     * @Title: pricetotal   
	     * @Description: 价表项目查询  
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String pricetotal() throws WebException {
		UserDTO user = (UserDTO) session.get("username");
		CenterConfigurationDTO cc = new CenterConfigurationDTO();
		cc = this.customerInfoService.getCenterconfigByKey("IS_PRIVE_DAY", user.getCenter_num());
		PageReturnDTO contractlist = new PageReturnDTO();
		if ("Y".equals(cc.getIs_active())) {
			String days = cc.getConfig_value();
			int idays = Integer.valueOf(days);
			String datetime = DateTimeUtil.DateDiff2(idays);
			contractlist=this.commService.priceShow(datetime,page,this.rows);
		}
		this.outJsonResult(contractlist);
		return NONE;
	}
	
	/**
	 * 
	     * @Title: queryIntroducerList   
	     * @Description: 获取所有介绍人 1359
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String queryIntroducerList() throws WebException, SQLException {
		List<Introducer> list = this.customerInfoService.queryIntroducerList("");
		this.outJsonResult(list);
		return NONE;
	}
	/**
	 * 
	     * @Title: tqdw   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String tqdw()throws WebException, SQLException {
		return SUCCESS;
	}
	/**
	 * 获取单位列表
	     * @Title: getCompanyInfoList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String getCompanyInfoList()throws WebException, SQLException {
		UserDTO user = (UserDTO) session.get("username");
		PageReturnDTO  li = this.companyService.getCompanyInfoList(model,page,this.pagesize,user);
		this.outJsonResult(li);
		return NONE;
	}
	/**
	 * 
	     * @Title: extractCompanInfo   
	     * @Description: TODO(提取单位)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String extractCompanInfo()throws WebException, SQLException {
		UserDTO user = (UserDTO) session.get("username");
		String msg = this.companyService.saveCompanInfo(model,user);
		this.outJsonStrResult(msg);
		return NONE;
	}
}