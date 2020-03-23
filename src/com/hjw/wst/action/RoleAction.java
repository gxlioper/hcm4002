package com.hjw.wst.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONSerializer;

import org.apache.commons.lang.StringUtils;
import org.hsqldb.lib.StringUtil;

import com.google.gson.Gson;
import com.hjw.wst.DTO.DepartmentDepDTO;
import com.hjw.wst.DTO.GridListDTO;
import com.hjw.wst.DTO.LogItem;
import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.wst.DTO.UserCenterDepListDTO;
import com.hjw.wst.DTO.UserDTO;
import com.hjw.wst.DTO.WebRoleDTO;
import com.hjw.wst.domain.DepUser;
import com.hjw.wst.domain.DepUserPrint;
import com.hjw.wst.domain.Exam_user;
import com.hjw.wst.domain.Examinatioin_center;
import com.hjw.wst.domain.SysLog;
import com.hjw.wst.domain.WebRole;
import com.hjw.wst.domain.WebRoleMenu;
import com.hjw.wst.domain.WebRoleReport;
import com.hjw.wst.domain.WebUserInfo;
import com.hjw.wst.domain.WebUserjsb;
import com.hjw.wst.model.RoleModel;
import com.hjw.wst.service.Examinatioin_centerService;
import com.hjw.wst.service.MenuGnService;
import com.hjw.wst.service.SyslogService;
import com.hjw.wst.service.UserInfoService;
import com.hjw.config.Logincheck;
import com.hjw.util.DateTimeUtil;
import com.hjw.util.Language;
import com.hjw.util.ValidateTime;
import com.opensymphony.xwork2.ModelDriven;
import com.synjones.framework.aop.SystemType;
import com.synjones.framework.exception.WebException;
import com.synjones.framework.util.BeanUtil;
import com.synjones.framework.web.action.BaseAction;


/**
 * 
 * 操作角色、角色岗位等
 * 
 * @author yangm
 * @version [3.0.0, Nov 4, 2011]
 * @see [相关类/方法]
 * @since [一卡通网站查询系统]
 */
@SuppressWarnings("rawtypes")
public class RoleAction extends BaseAction implements ModelDriven {

	private static final long serialVersionUID = 1L;
	private RoleModel model = new RoleModel();
	private MenuGnService menuGnService;
	@SuppressWarnings("unused")
	private Logincheck logincheck;
	private UserInfoService userInfoService;
	private int rows = 15; // easyui每页显示条数
	private LogItem logItem = new LogItem();
	private SyslogService syslogService;
	private Examinatioin_centerService examinatioin_centerService;
	
	
		
	public void setExaminatioin_centerService(Examinatioin_centerService examinatioin_centerService) {
		this.examinatioin_centerService = examinatioin_centerService;
	}

	public void setSyslogService(SyslogService syslogService) {
		this.syslogService = syslogService;
	}

	public LogItem getLogItem() {
		return logItem;
	}

	public void setLogItem(LogItem logItem) {
		this.logItem = logItem;
	}
	/**
	 * 保存用户角色信息 对应功能16
	 * 
	 * @return
	 * @throws WebException
	 * @see [类、类#方法、类#成员]
	 */
	public String userrolesave() throws WebException, Exception {
		this.defaultapp = (SystemType)session.get("defaultapp");
		if ((this.model.getId()==null)||(this.model.getId().trim().length()<=0)) {
			String jsonObject = "没有选择人员，操作不能继续";
			this.outJsonStrResult(jsonObject);
			return NONE;
		}
		
		if ((this.model.getRemark() == null)
				|| ("undefined".equals(this.model.getRemark()))) {
			this.menuGnService.delUserRole(this.model.getId()+"",this.defaultapp.getComid());
			
			String jsonObject = "已经撤销用户对应的角色。";
			this.outJsonStrResult(jsonObject);

			return NONE;
		}

		
		String[] gnid = this.model.getRemark().split(",");
		if (gnid.length <= 0) {
			String jsonObject = "选择了无效的角色";
			this.outJsonStrResult(jsonObject);
			return NONE;
		}
		
		this.menuGnService.delUserRole(this.model.getId()+"",this.defaultapp.getComid());
		for (int i = 0; i < gnid.length; i++) {
			WebUserjsb wrm = new WebUserjsb();
			wrm.setRole_id(gnid[i].trim());
			wrm.setUser_id(Integer.valueOf(this.model.getId()));
			wrm.setApptype(this.defaultapp.getComid());
			this.menuGnService.saveWebRyjsb(wrm);
		}
		UserDTO user = (UserDTO)session.get("username");
		
		if ("999".equals(user.getUsertype())) //只有超级管理员可以管理用户
		{
			String[] centerid = this.model.getCenter().split(",");
			this.menuGnService.delUserDep(this.defaultapp.getComid(), this.model.getId(), "");// 删除用户在本子系统的部门
			this.menuGnService.delCenter(Long.valueOf(this.model.getId()), "");// 删除用户的体检中心
			
			List<String> centerList = new ArrayList<String>();
			for (int j = 0; j < centerid.length; j++) {
				String allid = centerid[j].trim();
				String[] idss = allid.split("-");
				if ("c".equals(idss[0])) {
					boolean sss=false;
					for(String cid:centerList){
						if(cid.equals(idss[1].trim())){
							sss=true;
							break;
						}
					}
					if(!sss){
						centerList.add(idss[1].trim());
					}
				}
				if ("d".equals(idss[0])) {
					boolean sss=false;
					for(String cid:centerList){
						if(cid.equals(idss[1].trim())){
							sss=true;
							break;
						}
					}
					if(!sss){
						centerList.add(idss[1].trim());
					}
				}
			}
			
			for(String cid:centerList){
				Exam_user exam_user = new Exam_user();
				exam_user.setUser_id(Long.valueOf(this.model.getId()));
				exam_user.setExam_center_id(Long.valueOf(cid.trim()));
				exam_user.setIs_default("0");
				String center_num = this.examinatioin_centerService.loadExaminatioin(Long.valueOf(cid.trim()))
						.getCenter_num();
				exam_user.setCenter_num(center_num);
				this.menuGnService.saveCenter(exam_user);
			}
			
			for (int j = 0; j < centerid.length; j++) {
				String allid = centerid[j].trim();
				String[] idss = allid.split("-");
				if ("d".equals(idss[0])) {// 保存体检部门
					String center_num = this.examinatioin_centerService.loadExaminatioin(Long.valueOf(idss[1].trim()))
							.getCenter_num();
					DepUser depUser = new DepUser();
					depUser.setDep_id(Integer.parseInt(idss[2].trim()));
					depUser.setUser_id(Integer.parseInt(this.model.getId()));
					depUser.setCreater(user.getUserid());
					depUser.setCreate_time(DateTimeUtil.parse());
					depUser.setUpdater(user.getUserid());
					depUser.setUpdate_time(DateTimeUtil.parse());
					depUser.setApptype(this.defaultapp.getComid());
					depUser.setCenter_num(center_num);
					this.menuGnService.saveDepUser(depUser);
				}
			}
		}
			
		String jsonObject = "人员授权成功";
		this.outJsonStrResult(jsonObject);
		return NONE;
	}

	/**
	 * 获得角色头信息 对应功能15
	 * 
	 * @return
	 * @throws WebException
	 * @see [类、类#方法、类#成员]
	 */
	public String rolemainheader() throws WebException {
		List<GridListDTO> strlist = new ArrayList<GridListDTO>();
		GridListDTO ls = new GridListDTO();
		ls.setTitle("角色名称");
		ls.setField("rolename");
		ls.setWidth(400);
		ls.setSortable(true);
		ls.setAlign("left");
		strlist.add(ls);

		/*ls = new GridListDTO();
		ls.setDisplay(getText("operator"));
		ls.setName("ry");
		ls.setWidth(150);
		ls.setSortable(true);
		ls.setAlign("right");
		strlist.add(ls);

		ls = new GridListDTO();
		ls.setDisplay(getText("operdate"));
		ls.setName("datetime");
		ls.setWidth(160);
		ls.setSortable(true);
		ls.setAlign("right");
		strlist.add(ls);*/

		ls = new GridListDTO();
		ls.setTitle("授权功能");
		ls.setField("publisher");
		ls.setWidth(100);
		ls.setSortable(true);
		ls.setAlign("center");
		strlist.add(ls);

		
		ls = new GridListDTO();
		ls.setTitle("修改");
		ls.setField("publishdate");
		ls.setWidth(25);
		ls.setSortable(true);
		ls.setAlign("center");
		strlist.add(ls);
		
		ls = new GridListDTO();
		ls.setTitle("删除");
		ls.setField("publishdate");
		ls.setWidth(25);
		ls.setSortable(true);
		ls.setAlign("center");
		strlist.add(ls);

		
		String jsonString = JSONSerializer.toJSON(strlist).toString();
		this.outJsonStrResult(jsonString);
		return NONE;
	}

	/**
	 * 保存岗位和角色的关系 16
	 * 
	 * @return
	 * @throws WebException
	 * @throws Exception
	 * @see [类、类#方法、类#成员]
	 */
	public String userrole() throws WebException, Exception {
		Language.setLanguage(this.language);
		WebUserInfo wr = this.userInfoService.loadUserInfo(Integer.valueOf(this.model.getId()));
		//model.setGwbm(wr.getId());
		model.setGwbm(wr.getChi_Name());
		model.setGwmc(wr.getLog_Name());
		//model.setBmbh("");
		
		return SUCCESS;
	}
	
	/**
	 * 
	     * @Title: getroleAll   
	     * @Description: 获取所有角色
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws Exception      
	     * @return: String      
	     * @throws
	 */
	public String getroleAll() throws WebException, Exception {
		
		this.defaultapp=(SystemType) session.get("defaultapp");
		List<WebRole> wrlist = new ArrayList<WebRole>();
		List<WebRoleDTO> wrd = new ArrayList<WebRoleDTO>();
		wrlist = this.menuGnService.getWebRule(this.defaultapp.getComid());
		for (int i = 0; i < wrlist.size(); i++) {
			WebRoleDTO webr = new WebRoleDTO();
			WebRole wr = (WebRole) wrlist.get(i);
			BeanUtil.copy(webr, wr);

			List jssbList = this.menuGnService.findWebUserjsbByUserid(this.model
					.getId(), webr.getId(),this.defaultapp.getComid());
			if ((jssbList != null) && (jssbList.size() > 0)) {
				webr.setSelected("checked");
			}
			wrd.add(webr);
		}

		
		String jsonObject = JSONSerializer.toJSON(wrd).toString();
		this.outJsonStrResult(jsonObject);

		return NONE;
	}
	
	/**
	 * 显示角色列表 角色管理，对应功能15
	 * 
	 * @return
	 * @throws WebException
	 * @see [类、类#方法、类#成员]
	 */
	public String rolemainshow() throws WebException, Exception {
		this.defaultapp = (SystemType)session.get("defaultapp");
		PageReturnDTO rolemain=this.menuGnService.getRoleList(this.rows,this.page,this.defaultapp.getComid());
		this.outJsonResult(rolemain);
		return NONE;
	}

	/**
	 * 修改、增加角色 对应功能15
	 * 
	 * @return
	 * @throws WebException
	 * @see [类、类#方法、类#成员]
	 */
	public String roleedit() throws WebException, Exception {

		Language.setLanguage(this.language);

		if ((this.model.getId()!=null)&&(this.model.getId().trim().length()>0)) {
			WebRole wr = this.menuGnService.loadWebRole(this.model.getId());
			this.model.setId(wr.getId());
			this.model.setRolename(wr.getRolename());
		}
		return SUCCESS;
	}



	/**
	 * 执行修改或者增加角色信息 对应功能52
	 * 
	 * @return
	 * @throws WebException
	 * @see [类、类#方法、类#成员]
	 */
	public String roleeditdo() throws WebException, Exception {
		UserDTO user = new UserDTO();
		String sk = (String) session.get("sk");
		user = (UserDTO) session.get(sk);
		if ((this.model.getId()!=null)&&(this.model.getId().trim().length()>0)) {
			WebRole wr = this.menuGnService.loadWebRole(this.model.getId());
			wr.setRolename(this.model.getRolename());
			wr.setDatetime(ValidateTime.getDateTime());
			wr.setRy(user.getUserid()+"");
			this.menuGnService.updateWebRole(wr);
			this.message="修改角色成功！";
			//UserDTO user = (UserDTO) session.get("username");
			SysLog sl =  new SysLog();
			sl.setCenter_num(user.getCenter_num());
			sl.setUserid(user.getUserid()+"");
			sl.setOper_type("2");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
			sl.setXtgnid("");//可不填写
			sl.setXtgnid2("52");//子功能id 必须填写
			sl.setExplain("修改用户角色 "+wr.getRolename());//操作说明
			syslogService.saveSysLog(sl);
//			初始化日志对象
	        logItem.setFlag(true);
	        logItem.setOperId(wr.getId()+"");
	        logItem.setOperName(wr.getRolename());
			
		} else {
			this.defaultapp = (SystemType)session.get("defaultapp");
			WebRole wr = new WebRole();
			wr.setRolename(this.model.getRolename());
			wr.setDatetime(ValidateTime.getDateTime());
			wr.setRy(user.getUserid()+"");
			wr.setApptype(defaultapp.getComid());
			this.menuGnService.saveWebRole(wr);
			this.message="新增角色成功！";
			//UserDTO user = (UserDTO) session.get("username");
			SysLog sl =  new SysLog();
			sl.setCenter_num(user.getCenter_num());
			sl.setUserid(user.getUserid()+"");
			sl.setOper_type("1");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
			sl.setXtgnid("");//可不填写
			sl.setXtgnid2("52");//子功能id 必须填写
			sl.setExplain("保存用户角色 "+wr.getRolename());//操作说明
			syslogService.saveSysLog(sl);
//			初始化日志对象
	        logItem.setFlag(true);
	        logItem.setOperId(wr.getId()+"");
	        logItem.setOperName(wr.getRolename());
		}
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().print(new Gson().toJson(this.message));
		return NONE;
	}

	/**
	 * 保存角色对应的功能 53
	 * 
	 * @return
	 * @throws WebException
	 * @throws IOException 
	 * @see [类、类#方法、类#成员]
	 */
	public String rolegnsave() throws WebException, IOException {
		this.defaultapp = (SystemType)session.get("defaultapp");
		try {
			//得到角色对应的功能菜单
			if(StringUtils.isNotBlank(this.model.getRolegnid())){
				String[] gnid=this.model.getRolegnid().split(",");
				/*
				 * 修改角色对应的功能菜单
				 */
				//删除角色之前对应的功能菜单
				try {
					this.menuGnService.delWebRoleMenu(this.model.getId()+"",this.defaultapp.getComid());
					for(int i=0;i<gnid.length;i++){
						WebRoleMenu wrm=new WebRoleMenu();
						wrm.setRole_id(this.model.getId());
						wrm.setGncd_id(gnid[i].trim());
						wrm.setApptype(this.defaultapp.getComid());
						this.menuGnService.saveWebRoleMenu(wrm);
						UserDTO user = (UserDTO) session.get("username");
						SysLog sl =  new SysLog();
						sl.setCenter_num(user.getCenter_num());
						sl.setUserid(user.getUserid()+"");
						sl.setOper_type("2");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
						sl.setXtgnid("");//可不填写
						sl.setXtgnid2("53");//子功能id 必须填写
						sl.setExplain("保存角色对应的功能菜单 "+this.model.getId()+" "+gnid[i]);//操作说明
						syslogService.saveSysLog(sl);
					}
					
				} catch (Exception e) {
					this.message="授权菜单失败";
					e.printStackTrace();
				}
			}
			
			
			if(StringUtils.isNotBlank(this.model.getReportids())){
				String[] reprotid=this.model.getReportids().split(",");
				/*
				 * 修改角色对应的功能菜单
				 */
				//删除角色之前对应的功能菜单
				try {
					this.menuGnService.delWebRoleReport(this.model.getId()+"");
					for(int i=0;i<reprotid.length;i++){
						WebRoleReport wrm=new WebRoleReport();
						wrm.setRole_id(this.model.getId());
						wrm.setRolemenu_id(Integer.valueOf(reprotid[i].trim()));
						this.menuGnService.saveWebRoleReport(wrm);
						UserDTO user = (UserDTO) session.get("username");
						SysLog sl =  new SysLog();
						sl.setCenter_num(user.getCenter_num());
						sl.setUserid(user.getUserid()+"");
						sl.setOper_type("2");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
						sl.setXtgnid("");//可不填写
						sl.setXtgnid2("53");//子功能id 必须填写
						sl.setExplain("保存角色对应的报表 "+this.model.getId()+" "+reprotid[i]);//操作说明
						syslogService.saveSysLog(sl);
					}
					this.message="授权菜单成功";
				} catch (Exception e) {
					this.message="授权菜单失败";
					e.printStackTrace();
				}
			}
			
			this.message="授权菜单成功";
		} catch (Exception e) {
			this.message +="操作失败！";
		}
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().print(new Gson().toJson(this.message));
		return NONE;
		
	}

	/**
	 * 删除角色 49
	 * 
	 * @return
	 * @throws WebException
	 * @see [类、类#方法、类#成员]
	 */
	public String delrole() throws WebException, Exception {
		this.defaultapp = (SystemType)session.get("defaultapp");
		WebRole wr = this.menuGnService.loadWebRole(this.model.getId());
		this.menuGnService.delWebRoleMenu(this.model.getId()+"",this.defaultapp.getComid());
		
		this.menuGnService.delWebRole(wr);
		UserDTO user = (UserDTO) session.get("username");
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("2");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("53");//子功能id 必须填写
		sl.setExplain("保存角色对应的报表 "+wr.getRolename());//操作说明
		syslogService.saveSysLog(sl);
		this.message="删除角色成功！";
//		初始化日志对象
        logItem.setFlag(true);
        logItem.setOperId(wr.getId()+"");
        logItem.setOperName(wr.getRolename());
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().print(new Gson().toJson(this.message));
		return NONE;
		
	}


	public String queryAllWebRole()  throws WebException, Exception{
		List<WebRoleDTO> list=this.menuGnService.queryAllWebRole();
		this.outJsonResult(list);
		return NONE;
	}

	public Object getModel() {
		return model;
	}

	public void setModel(RoleModel model) {
		this.model = model;
	}

	public void setMenuGnService(MenuGnService menuGnService) {
		this.menuGnService = menuGnService;
	}

	public void setLogincheck(Logincheck logincheck) {
		this.logincheck = logincheck;
	}


	public void setUserInfoService(UserInfoService userInfoService) {
		this.userInfoService = userInfoService;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}
	
	
	/**
	 * 科室打印页面
	     * @Title: printdepshow   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws Exception      
	     * @return: String      
	     * @throws
	 */
	public String printdepshow() throws WebException, Exception{		
		return SUCCESS;
	}
	
	public String printdepsave() throws WebException, Exception {		
		String[] depid = this.model.getUserDepId().split(",");
		if (depid.length <= 0) {
			String jsonObject = "选择了无效的科室";
			this.outJsonStrResult(jsonObject);
			return NONE;
		}
		
		this.menuGnService.delPrintDep(this.model.getId());
		UserDTO user = (UserDTO)session.get("username");
		for(int i = 0; i < depid.length; i++){
			DepUserPrint depUser = new DepUserPrint();
			depUser.setCenter_num(user.getCenter_num());
			depUser.setDep_id(Integer.parseInt(depid[i]));
			depUser.setUser_id(Integer.parseInt(this.model.getId()));
			depUser.setCreater(user.getUserid());
			depUser.setCreate_time(DateTimeUtil.parse());
			depUser.setUpdater(user.getUserid());
			depUser.setUpdate_time(DateTimeUtil.parse());
			
			this.menuGnService.saveDepUserPrint(depUser);
			//UserDTO user = (UserDTO) session.get("username");
			SysLog sl =  new SysLog();
			sl.setCenter_num(user.getCenter_num());
			sl.setUserid(user.getUserid()+"");
			sl.setOper_type("1");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
			sl.setXtgnid("");//可不填写
			sl.setXtgnid2("16");//子功能id 必须填写
			sl.setExplain("保存打印科室 "+depUser.getDep_id());//操作说明
			syslogService.saveSysLog(sl);
			//this.menuGnService.delCenter(this.model.getId()+"");
		}
		
		String jsonObject = "科室打印成功 ！";
		this.outJsonStrResult(jsonObject);
		return NONE;
	}
}
