package com.hjw.wst.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.Cookie;

import org.apache.struts2.ServletActionContext;

import com.google.gson.Gson;
import com.hjw.config.Logincheck;
import com.hjw.config.TermConfig;
import com.hjw.util.DESMessage;
import com.hjw.util.EncryptUtils;
import com.hjw.wst.DTO.DepUserDTO;
import com.hjw.wst.DTO.ECenterDTO;
import com.hjw.wst.DTO.ExamInfoCountDTO;
import com.hjw.wst.DTO.JobDTO;
import com.hjw.wst.DTO.LoginDTO;
import com.hjw.wst.DTO.SysVersionDTO;
import com.hjw.wst.DTO.SystemInformsDTO;
import com.hjw.wst.DTO.TreeDTO;
import com.hjw.wst.DTO.UserCenterDepListDTO;
import com.hjw.wst.DTO.UserDTO;
import com.hjw.wst.DTO.UserInfoDTO;
import com.hjw.wst.domain.DepartmentDep;
import com.hjw.wst.domain.Examinatioin_center;
import com.hjw.wst.domain.SysLog;
import com.hjw.wst.domain.WebResrelAtionship;
import com.hjw.wst.domain.WebRole;
import com.hjw.wst.domain.WebRoleMenu;
import com.hjw.wst.domain.WebRoleReport;
import com.hjw.wst.domain.WebUserInfo;
import com.hjw.wst.domain.WebUserjsb;
import com.hjw.wst.domain.WebXtgnb;
import com.hjw.wst.domain.WebXtgncd;
import com.hjw.wst.model.AccountModel;
import com.hjw.wst.service.CustomerInfoService;
import com.hjw.wst.service.DepartmentService;
import com.hjw.wst.service.Examinatioin_centerService;
import com.hjw.wst.service.LoginService;
import com.hjw.wst.service.MenuGnService;
import com.hjw.wst.service.SyslogService;
import com.hjw.wst.service.UserInfoService;
import com.opensymphony.xwork2.ModelDriven;
import com.synjones.framework.aop.SystemType;
import com.synjones.framework.exception.WebException;
import com.synjones.framework.web.action.BaseAction;
import com.ymhjw.Util.CenterBean;
import com.ymhjw.Util.CenterBeans;
import com.ymhjw.security.HjwInfo;

@SuppressWarnings("rawtypes")
public class AccountAction extends BaseAction implements ModelDriven {

	private static final long serialVersionUID = 1L; 
	private AccountModel model = new AccountModel();
	private LoginService loginService;
	private Logincheck logincheck;
	private MenuGnService menuGnService;
	private String username;
	private String userpwd;
	private String randcode;
	private String usertype;
	private String logintype;
	private long dep_id;
	private long center_id;
	private String dep_num;
	private String center_num;
	private UserInfoService userInfoService;
	private DepartmentService departmentService;
	private CustomerInfoService customerInfoService;
	private int rows = 15; // easyui每页显示条数
	private String id;//cookieid	
	private Examinatioin_centerService examinatioin_centerService;
	private SyslogService syslogService; 	

	public String getCenter_num() {
		return center_num;
	}

	public void setCenter_num(String center_num) {
		this.center_num = center_num;
	}

	public void setSyslogService(SyslogService syslogService) {
		this.syslogService = syslogService;
	}

	public void setExaminatioin_centerService(Examinatioin_centerService examinatioin_centerService) {
		this.examinatioin_centerService = examinatioin_centerService;
	}

	public void setDepartmentService(DepartmentService departmentService) {
		this.departmentService = departmentService;
	}

	public void setCustomerInfoService(CustomerInfoService customerInfoService) {
		this.customerInfoService = customerInfoService;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public Logincheck getLogincheck() {
		return logincheck;
	}

	public void setLogincheck(Logincheck logincheck) {
		this.logincheck = logincheck;
	}

	public String getRandcode() {
		return randcode;
	}

	public void setRandcode(String randcode) {
		this.randcode = randcode;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUserpwd() {
		return userpwd;
	}

	public void setUserpwd(String userpwd) {
		this.userpwd = userpwd;
	}

	public String getUsertype() {
		return usertype;
	}

	public void setUsertype(String usertype) {
		this.usertype = usertype;
	}

	public String getLogintype() {
		return logintype;
	}

	public void setLogintype(String logintype) {
		this.logintype = logintype;
	}
	
	public long getDep_id() {
		return dep_id;
	}

	public void setDep_id(long dep_id) {
		this.dep_id = dep_id;
	}

	public long getCenter_id() {
		return center_id;
	}

	public void setCenter_id(long center_id) {
		this.center_id = center_id;
	}

	public void setLoginService(LoginService loginService) {
		this.loginService = loginService;
	}

	public void setUserInfoService(UserInfoService userInfoService) {
		this.userInfoService = userInfoService;
	}

	public void setDep_num(String dep_num) {
		this.dep_num = dep_num;
	}

	public String homeloginurl() throws WebException, SQLException, ParseException {
		String authUrl = this.loginService.getUnifiedauthUrl();
		this.outJsonStrResult(authUrl);
		return NONE;
	}

	/**
	 * 
	     * @Title: getSyssystem   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String getSyssystem() throws WebException{
		List<TreeDTO> tdlist = new ArrayList<TreeDTO>();
//		if((termConfig==null)||(termConfig.apptype!=null)||(termConfig.apptype.size()>0)){
//			for(int i=0;i<termConfig.apptype.size();i++){
//				SystemType apps=termConfig.apptype.get(i);
//				TreeDTO td = new TreeDTO();
//				td.setId(apps.getComid());
//				td.setText(apps.getComname());
//				if(i==0){
//					td.setState("selected");
//				}else{
//					td.setState("");
//				}
//				tdlist.add(td);
//			}
//		}
		TreeDTO td = new TreeDTO();
		td.setId("1");
		td.setText("健康体检系统");
		td.setState("selected");
		tdlist.add(td);
		this.outJsonResult(tdlist);
		return NONE;
	}
	
	/**
	 * 获取登录人的功能、商户、部门信息
	 * 
	 * @param jobinfo
	 * @return
	 * @type 1表示部门，2表示商户，0表示功能
	 * @see [类、类#方法、类#成员]
	 */
	private String geusertxt(List<JobDTO> jobinfo, String type) {
		String strrole = "";// 获取所有的角色对应的菜单名称
		if ((jobinfo != null) && (jobinfo.size() > 0)) {
			for (int i = 0; i < jobinfo.size(); i++) {
				JobDTO job = new JobDTO();
				job = jobinfo.get(i);
				List<WebRoleMenu> list = menuGnService.fingWebRoleMenu2RoleId(job.getId());
				for (WebRoleMenu rm : list) {
					WebXtgncd wxcd = menuGnService.loadWebXtgncd(rm.getGncd_id());
					try{
					strrole = strrole + "'" + wxcd.getXtgn_id() + "',";
					}catch(Exception ex){}
				}
			}
			strrole = strrole + "'" + "999999" + "'";
		} else {
			strrole = "'999999'";
		}

		return getString(strrole);
	}
	
	/**
	 * 获取登录人的功能、商户、部门信息
	 * 
	 * @param jobinfo
	 * @return
	 * @type 1表示部门，2表示商户，0表示功能
	 * @see [类、类#方法、类#成员]
	 */
	private String geusertxtreport(List<JobDTO> jobinfo, String type) {
		String strrole = "";// 获取所有的角色对应的菜单名称
		if ((jobinfo != null) && (jobinfo.size() > 0)) {
			for (int i = 0; i < jobinfo.size(); i++) {
				JobDTO job = new JobDTO();
				job = jobinfo.get(i);
				List<WebRoleReport> list = menuGnService.fingWebRoleReport2RoleId(job.getId());
				for (WebRoleReport rm : list) {
					
					  strrole = strrole + "'" + rm.getRolemenu_id() + "',";
					
				}
			}
			strrole = strrole + "'" + "999999" + "'";
		} else {
			strrole = "'999999'";
		}

		return getString(strrole);
	}

	/**
	 * 
	 * @Title: geusertxt1 @Description: 获取所有角色的数据 @param: @param
	 * jobinfo @param: @param type @param: @return @return: String @throws
	 */
	private String geusertxt1(List<JobDTO> jobinfo, String type) {
		String strjs = "";
		if ((jobinfo != null) && (jobinfo.size() > 0)) {
			for (int i = 0; i < jobinfo.size(); i++) {
				JobDTO job = new JobDTO();
				job = jobinfo.get(i);
				strjs = strjs + "'" + job.getId() + "',";
			}
			strjs = strjs + "'" + "999999" + "'";
		} else {
			strjs = "'999999'";
		}
		
		return getString(strjs);
	}

	/**
	 * 取消重复的字符
	 * 
	 * @param str
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	@SuppressWarnings({ "unchecked", "unused", "rawtypes" })
	private String getString(String str) {
		if ((str == null) || (str.length() < 1))
			return null;
		String newstr = null;
		String[] strold = str.split(",");
		List strnew = new ArrayList();
		for (int i = 0; i < strold.length; i++) {
			boolean m = false;
			for (int j = 0; j < strnew.size(); j++) {
				if (((String) strnew.get(j)).equals(strold[i])) {
					m = true;
				}
			}
			if (!m) {
				strnew.add(strold[i]);
			}
		}
		for (int i = 0; i < strnew.size(); i++) {
			if (newstr == null)
				newstr = (String) strnew.get(i);
			else
				newstr = newstr + "," + (String) strnew.get(i);
		}
		// if ((newstr!=null)&&(newstr.indexOf(",")>0))
		// newstr = newstr.substring(0, newstr.length() - 1);
		ArrayList arrayList = new ArrayList();

		String[] pss = newstr.split(",");
		java.util.Arrays.sort(pss, new MyComparator());
		String newstr1 = null;
		for (int i = 0; i < pss.length; i++) {
			if (newstr1 == null)
				newstr1 = (String) pss[i];
			else
				newstr1 = newstr1 + "," + (String) pss[i];
			// System.out.println (pss[i]);
		}

		return newstr1;
	}

	@SuppressWarnings("rawtypes")
	class MyComparator implements Comparator {
		public int compare(Object str1, Object str2) {
			return (str1.toString()).compareTo(str2.toString());

		}
	}
	/**
	 * 
	 * @Title: getJobInfo @Description: 获取用户对应角色 @param: @param
	 * userid @param: @return @return: List<JobDTO> @throws
	 */
	private List<JobDTO> getJobInfo(String userid) {
		List<WebUserjsb> ls = this.menuGnService.findWebUserjsByUserid(userid);
		List<JobDTO> joblist = new ArrayList<JobDTO>();
		JobDTO job = new JobDTO();// 保存角色信息
		for (int i = 0; i < ls.size(); i++) {
			WebUserjsb ug = ls.get(i);
			job = new JobDTO();
			job.setId(ug.getRole_id());
			WebRole gw = this.menuGnService.loadWebRole(ug.getRole_id());
			if (gw != null)
				job.setName(gw.getRolename());
			else
				job.setName("");
			joblist.add(job);
		}
		return joblist;
	}

	public static boolean isNumeric2(String str) {
		for (int i = str.length(); --i >= 0;) {
			if (!Character.isDigit(str.charAt(i))) {
				return false;
			}
		}
		return true;
	}

	public String loginout() throws Exception {

		session.remove("userinfo");
		session.remove("username"); 
		return "loginout";
	}

	public String personlist() throws Exception {

		return "person";
	}
	
	/**
	 * 
	     * @Title: sysmanager   
	     * @Description: 获取管理员的权限   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String sysmanager()throws WebException,IOException{
		UserDTO user = new UserDTO();
		user= (UserDTO)session.get("username");
		if(!"999".equals(user.getUsertype())){
			this.outJsonStrResult("no");
		}else{
			List<JobDTO> list = new ArrayList<JobDTO>();
			JobDTO jb = new JobDTO();
			WebXtgnb webxt = menuGnService.findWebXtgnbById("1");
			jb.setName(webxt.getName());
			jb.setId("javascript:addPanel_other(\""+webxt.getName()+"\",\""+webxt.getUrl()+"\",\"themes/default/images/blank.gif\",\"1\")");
			jb.setRemark("icon ico-edit");
			list.add(jb);
			
			jb = new JobDTO();
			webxt = menuGnService.findWebXtgnbById("14");
			jb.setName(webxt.getName());
			jb.setId("javascript:addPanel_other(\""+webxt.getName()+"\",\""+webxt.getUrl()+"\",\"themes/default/images/blank.gif\",\"1\")");
			jb.setRemark("icon ico-person");
			list.add(jb);
			
			jb = new JobDTO();
			webxt = menuGnService.findWebXtgnbById("15");
			jb.setName(webxt.getName());
			jb.setId("javascript:addPanel_other(\""+webxt.getName()+"\",\""+webxt.getUrl()+"\",\"themes/default/images/blank.gif\",\"1\")");
			jb.setRemark("icon ico-purview");
			list.add(jb);
			this.outJsonResult(list);
		}
		return NONE;
	}
		
	/**
	 * 
	     * @Title: hjwonelogin   
	     * @Description: 火箭蛙老版本单点登录-发出端
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String hjwonelogin()throws WebException,Exception {
		UserDTO user = new UserDTO();
		user = (UserDTO)session.get("username");
		if((user==null)||(user.getUserid()<=0))
		{
			this.message="error-获取用户名称错误";	
		}else{
			String strkey="h8j1w8ww";
			String userid = DESMessage.encString(user.getUserid()+"", strkey);
			String centerid = DESMessage.encString(user.getCenter_id()+"", strkey);
			String deptid = DESMessage.encString(user.getDep_id()+"", strkey);
			String cookieid="cookie"+user.getUserid();
			// new一个Cookie对象,键值对为参数 
			Cookie cookie = new Cookie(cookieid,centerid+"-"+deptid+"-"+userid);  
			// 设置Cookie最大生存时间,以秒为单位,负数的话为浏览器进程,关闭浏览器Cookie消失  
			cookie.setMaxAge(-1);  // 一天  
			cookie.setPath("/");
			// 将Cookie添加到Response中,使之生效  
			ServletActionContext.getResponse().addCookie(cookie);  //addCookie后，如果已经存在相同名字的cookie，则最新的覆盖旧的cookie
			//注意：在Struts中可以使用 ServletActionContext.getResponse()获得respone对象
			this.message="ok-"+cookieid;	
		}
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.print(message);
		return NONE;
	}
	
	/**
	 * 
	     * @Title: hjwlogin   
	     * @Description: 火箭蛙老版本单点登录   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	@SuppressWarnings("unchecked")
	public String singlePEISLogin()throws WebException {
		session.remove("rand");
		session.remove("username");
		session.remove("userinfo");
		session.put("sk", "username");		
		UserDTO user = new UserDTO();
		if((this.getId()==null)||(this.getId().trim().length()<=0))
		{
			this.message="单点登录，获取系统id错误！";
			return "commerror";
		}else{
		//读取Cookie只能从request中获取全部Cookie,然后循环迭代。
		//在Struts中可以使用 ServletActionContext.getRequest()获得request对象
		// 从request中获取Cookie,拿到的是一个Cookie数组  
		Cookie[] cookies = ServletActionContext.getRequest().getCookies();  
		
		String cookiesvalue ="";
		
		// 然后迭代之  
		if (cookies != null && cookies.length > 0) { //如果没有设置过Cookie会返回null  
		    for (Cookie cookie : cookies) {
              if(this.id.equals(cookie.getName())){
            	  cookiesvalue=cookie.getValue();
            	  cookie.setMaxAge(0);  
                  // 设回Response中生效  
                  response.addCookie(cookie);
                  break;
              }
		    }  
		}		
		
		if ((cookiesvalue==null)||(cookiesvalue.trim().length()<=0)||(cookiesvalue.trim().split("-").length!=3)){
			this.message="单点登录，获取系统属性错误！";
			return "commerror";
		}else{
			try{
				String strkey="h8j1w8ww";
				String centerid = DESMessage.decString(cookiesvalue.trim().split("-")[0], strkey);
				String deptid = DESMessage.decString(cookiesvalue.trim().split("-")[1], strkey);
				String userid = DESMessage.decString(cookiesvalue.trim().split("-")[2], strkey);
				if((userid==null)||(userid.trim().length()<=0)){
				   this.message="获取用户名称错误";	
				   return "commerror";
				}else {
					WebUserInfo wui = userInfoService.loadUserInfo(Long.valueOf(userid));
					if (wui == null) {
						message = "用户名不存在!";
						return "commerror";
					} else {			
							session.put("usertype","1");
							session.put("logintype","1");
							user.setUserid(wui.getId());
							user.setName(wui.getChi_Name());
							user.setUsername(wui.getLog_Name());
							user.setTel(wui.getPhone_num());
							user.setEmail(wui.getEmail());
							user.setSessionId(this.request.getSession().getId());
							user.setJob(getJobInfo(wui.getId() + ""));// 保存角色list
							user.setXtgn(this.geusertxt(user.getJob(), usertype));// 通过角色获的所有系统功能
							user.setRolestr(this.geusertxt1(user.getJob(), usertype));
							user.setReportid(this.geusertxtreport(user.getJob(), usertype));
							user.setUsertype("1");
							user.setDep_id(Long.valueOf(deptid));
							user.setWork_num(wui.getWork_num());
							long lcenterid = Long.valueOf(centerid);
							if (lcenterid<=0)
							{
								lcenterid=27;
							}
							user.setCenter_num(this.examinatioin_centerService.loadExaminatioin(lcenterid).getCenter_num());
							user.setCenter_id(Long.valueOf(centerid));
							
							UUID uuid = UUID.randomUUID();
							user.setUuid(uuid.toString());
							session.put("username", user);
							session.put("userinfo", wui);
							return "loginok";
				    }
				    }
			}catch(Exception ex){
				this.message="单点登录，数据验证错误！";
				return "commerror";
			}
		}
		
		}		
		}
	
	@SuppressWarnings("unchecked")
	public String login() throws WebException,Exception {
		String usertype = this.usertype;
//		String rand = (String) session.get("rand");
		session.remove("rand");
		session.remove("username");
		session.remove("userinfo");
		session.put("sk", "username");
		UserDTO user = new UserDTO();
		String mes = "";
//		if (!this.randcode.equalsIgnoreCase(rand)) {
//			this.message = "验证码不正确";
//			response.setContentType("text/html;charset=utf-8");
//			response.getWriter().print(new Gson().toJson(this.message));
//			return NONE;
//		}
		WebUserInfo wui = userInfoService.getUserInfoByname(this.username);
		if (wui == null) {
			mes = "用户名不存在!";
		} else {
			String newpasswd = EncryptUtils.encryptMD5(userpwd).toLowerCase();
			if (wui.getIs_active().equals("N")) {
				mes = "用户已停用!";
			} else if (!wui.getPwd_encrypted().toLowerCase().equals(newpasswd)) {
				mes = "密码不正确!";
			} else {
				session.put("usertype", this.usertype);			
				Examinatioin_center center = this.examinatioin_centerService.queryByNum(this.center_num);
				user.setCenter_num(center.getCenter_num());
				user.setCenter_name(center.getCenter_name());
				user.setCenter_id(center.getId());
				
				List<SystemType> apptype=TermConfig.getTermConfig(user.getCenter_num());
				
				boolean loginflag = false;
				session.remove("defaultapp");
					for (SystemType apps:apptype){
						if (apps.getComid().equals(this.logintype)){
							loginflag = true;							
							session.put("defaultapp", apps);
							break;
						}
					}
					
					session.remove("iszyb");
					for (SystemType apps:apptype){
						if ("zyb".equals(apps.getComcode())){					
							session.put("iszyb", true);
							break;
						}
					}
					
				if (!loginflag) {
					mes = "无系统授权!";
				} else {
					user.setUserid(wui.getId());
					user.setName(wui.getChi_Name());
					user.setUsername(wui.getLog_Name());
					user.setTel(wui.getPhone_num());
					user.setEmail(wui.getEmail());
					user.setWork_num(wui.getWork_num());
					user.setSessionId(this.request.getSession().getId());
					// 资源
					List<WebResrelAtionship> webResource = this.userInfoService.getWebResource(wui.getId());
					user.setWebResource(webResource);
					mes = "loginok";
				}
			}
		}

		if (this.logincheck.getAdminUser().equals(this.username)) {
			usertype = "1";// 管理员
		} else {
			usertype = "2";// 一般用户
		}
		if ("loginok".equals(mes)) {
			user.setJob(getJobInfo(wui.getId() + ""));// 保存角色list
			user.setXtgn(this.geusertxt(user.getJob(), usertype));// 通过角色获的所有系统功能
			user.setRolestr(this.geusertxt1(user.getJob(), usertype));
			user.setReportid(this.geusertxtreport(user.getJob(), usertype));
			user.setUsertype(usertype);
			user.setDep_id(this.dep_id);
			DepartmentDep dd = this.departmentService.loadDepartmentDep(this.dep_id);
			if(dd == null){
				dd = this.departmentService.queryByNum(this.dep_num);
			}
			if(dd != null){
				user.setDep_id(dd.getId());
				user.setDep_url(dd.getDep_link().trim());
				user.setDeptCode(dd.getDep_num());
				user.setIsPrint(dd.getIsPrint_Barcode());
			}			
			
			UserInfoDTO utdo= new UserInfoDTO();
			utdo=this.userInfoService.getUsername(this.username);
			if (utdo!=null && utdo.getLog_Name()!=null && utdo.getExam_center_id() == 0){
				user.setUsertype("999");
			}		
			
			
			UUID uuid = UUID.randomUUID();
			user.setUuid(uuid.toString());
			
			session.put("username", user);
			session.put("userinfo", wui);
			
			user = (UserDTO) session.get("username");
			SysLog sl =  new SysLog();
			sl.setCenter_num(user.getCenter_num());
			sl.setUserid(user.getUserid()+"");
			sl.setOper_type("5");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
			sl.setXtgnid("12");//可不填写
			sl.setXtgnid2("12");//子功能id 必须填写
			sl.setExplain("");//操作说明
			syslogService.saveSysLog(sl);
		}
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().print(new Gson().toJson(mes));
		return NONE;
	}
	
	/**
	 * 	
	     * @Title: getCenterUserOfDepList   
	     * @Description: 通过体检中心编号和登录人员id获取所有体检中心部门   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String getCenterUserOfDepList() throws WebException, SQLException{
		UserInfoDTO utdo= new UserInfoDTO();
		utdo=this.userInfoService.getUsername(this.username);
		List<DepUserDTO> list = new ArrayList<DepUserDTO>();
		if (utdo!=null && utdo.getLog_Name()!=null){
			if (utdo.getExam_center_id() == 0) {
				// 超级管理员
					DepUserDTO du = new DepUserDTO();
					du.setId(0);
					du.setDep_id(52);
					du.setDep_name("登记台");
					du.setUser_id(utdo.getId());
					list.add(du);
					this.outJsonResult(list);				
			}else{
					String depOrderType = this.customerInfoService.getCenterconfigByKey("DEP_ORDER_TYPE","000")
							.getConfig_value().trim();
					UserCenterDepListDTO cen=new UserCenterDepListDTO();
					if ("1".equals(depOrderType)) {
						list = this.userInfoService.getDepUser(this.username, this.logintype,this.center_num, "dep_name");
					} else if ("2".equals(depOrderType)) {
						list = this.userInfoService.getDepUser(this.username, this.logintype, this.center_num, "seq_code");
					} else {
						list = this.userInfoService.getDepUser(this.username, this.logintype, this.center_num,  "dep_name");
					}
					this.outJsonResult(list);	
			}
		}		
		return NONE;
	}
	
	
		
	/**
	 * 获取体检中心列表
	     * @Title: getCenterOfSystem   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws Exception      
	     * @return: String      
	     * @throws
	 */
	public String getCenterOfSystem()throws WebException,Exception{
		
		/**
		 * 保存数据到application
		 */
		List<ECenterDTO> ecenterList=new ArrayList<ECenterDTO>();
		ecenterList=this.userInfoService.getCheckCenterAll();		
		Map<String,Object> application=new HashMap<String, Object>();
		application=ServletActionContext.getContext().getApplication();
		int centerCount=0;
		if(ecenterList!=null){
			centerCount=ecenterList.size();
		}
		application.put("centerCount", centerCount);//保存体检中心数量
		application.put("ecenterList", ecenterList);//保存体检中心编号和名称
		ServletActionContext.getContext().setApplication(application);		
		
		List<UserCenterDepListDTO> ListuserCenterDepListDTO = new ArrayList<UserCenterDepListDTO>();
		ListuserCenterDepListDTO = this.userInfoService.getCenterAll();			
		this.outJsonResult(ListuserCenterDepListDTO);
		return NONE;
	}
	/**
	 * 
	     * @Title: getCenterUserOfSystem   
	     * @Description: 通过体检中心获取子系统   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String getCenterUserOfSystem()throws WebException,Exception{
		List<SystemType> apptype=new ArrayList<SystemType>();
		CenterBeans cbs=new CenterBeans();
		cbs.getcenters();
		CenterBean cb=cbs.getCenter(this.center_num);
        String xmessage=cb.getXmessage();
		String centerkey=com.ymhjw.security.StringUtil.getCompId();
		HjwInfo  dm=com.ymhjw.security.StringUtil.getString(xmessage,centerkey);
		String sysString = com.ymhjw.security.StringUtil.getSysString(dm.getSystems());
		if (dm.getSystems() != null) {
			String[] sysStrss = sysString.split(",");
			if (sysStrss != null && sysStrss.length > 0) {
				for (int i = 0; i < sysStrss.length; i++) {
					String[] onesysStrss = sysStrss[i].split("-");
					SystemType es = new SystemType();
					String syscode=onesysStrss[0].toLowerCase();
					if ("hjw_jktjxt".equals(syscode)) {
						es.setComname("健康体检系统");
						es.setComcode("com");
						es.setComid(onesysStrss[2]);
						es.setValid_date(onesysStrss[1]);
					} else  if ("hjw_crmxt".equals(syscode)) {
						es.setComname("客户管理系统");
						es.setComcode("crm");
						es.setComid(onesysStrss[2]);
						es.setValid_date(onesysStrss[1]);
					} else if ("hjw_zybxt".equals(syscode)) {
						es.setComname("职业病系统");
						es.setComcode("zyb");
						es.setComid(onesysStrss[2]);
						es.setValid_date(onesysStrss[1]);
					}					
					apptype.add(es);
				}
			}
		}
		this.outJsonResult(apptype);
		return NONE;
	}
	
	/**
	 * 根据用户名查询 体检信息和用户科室信息
	     * @Title: getCenterAndDepList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String getCenterAndDepList() throws WebException, SQLException{
		/**
		 * 保存数据到application
		 */
		List<ECenterDTO> ecenterList=new ArrayList<ECenterDTO>();
		ecenterList=this.userInfoService.getCheckCenterAll();		
		Map<String,Object> application=new HashMap<String, Object>();
		application=ServletActionContext.getContext().getApplication();
		int centerCount=0;
		if(ecenterList!=null){
			centerCount=ecenterList.size();
		}
		application.put("centerCount", centerCount);//保存体检中心数量
		application.put("ecenterList", ecenterList);//保存体检中心编号和名称
		ServletActionContext.getContext().setApplication(application);		
		
		UserInfoDTO utdo= new UserInfoDTO();
		utdo=this.userInfoService.getUsername(this.username);
		if (utdo==null || utdo.getLog_Name()==null){
			this.message = "no";
			this.outJsonStrResult(this.message);
		}else{
			LoginDTO login=new LoginDTO();
			List<UserCenterDepListDTO> ListuserCenterDepListDTO = new ArrayList<UserCenterDepListDTO>();
			if (utdo.getExam_center_id() == 0) {
				// 超级管理员
				ListuserCenterDepListDTO = this.userInfoService.getCenterAll();
				login.setListCenter(ListuserCenterDepListDTO);
				if (ListuserCenterDepListDTO!=null && ListuserCenterDepListDTO.size()>0) {
					List<DepUserDTO> list = new ArrayList<DepUserDTO>();
					DepUserDTO du = new DepUserDTO();
					du.setId(0);
					du.setDep_id(52);
					du.setDep_name("登记台");
					du.setUser_id(utdo.getId());
					list.add(du);
					login.setDepUserList(list);
					this.outJsonResult(login);
				}else{
					this.message = "no";
					this.outJsonStrResult(this.message);
				}
				
			}else{
				ListuserCenterDepListDTO= this.userInfoService.getCenterByUserName(this.username);
				login.setListCenter(ListuserCenterDepListDTO);
				if (ListuserCenterDepListDTO!=null && ListuserCenterDepListDTO.size()>0) {
					List<DepUserDTO> list = null;
					String depOrderType = this.customerInfoService.getCenterconfigByKey("DEP_ORDER_TYPE","000")
							.getConfig_value().trim();
					if ("1".equals(depOrderType)) {
						list = this.userInfoService.getDepUser(this.username, this.logintype,ListuserCenterDepListDTO.get(0).getCenter_num(), "dep_name");
					} else if ("2".equals(depOrderType)) {
						list = this.userInfoService.getDepUser(this.username, this.logintype, ListuserCenterDepListDTO.get(0).getCenter_num(), "seq_code");
					} else {
						list = this.userInfoService.getDepUser(this.username, this.logintype, ListuserCenterDepListDTO.get(0).getCenter_num(),  "dep_name");
					}
					login.setDepUserList(list);
					this.outJsonResult(login);
				}else{
					this.message = "no";
					this.outJsonStrResult(this.message);
				}				
			}
		}		
		return NONE;
	}
	
	/**
	 *   检索名称
	     * @Title: autoGetUsername   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String autoGetUsername() throws WebException, SQLException{
		String temp = "";
		for (int i = 0; i < this.username.length(); i++) {
			if (i == this.username.length() - 1) {
				temp = temp + this.username.substring(i, i + 1);
			} else {
				temp = temp + this.username.substring(i, i + 1) + "%";
			}
		}
		List<UserInfoDTO> list = this.userInfoService.autoGetUsername(temp,this.center_num);
		this.outJsonResult(list);
		return NONE;
	}

	/**
	 * 获取用户名，科室名，用户拥有科室信息
	     * @Title: getUserAndUserDep   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String getUserAndUserDep() throws WebException, SQLException{
		UserDTO user = (UserDTO) session.get("username");
		this.defaultapp = (SystemType)session.get("defaultapp");	
		UserCenterDepListDTO userCenterDepListDTO = this.userInfoService.getCenterByUserIdAndDepId(user.getUserid(), user.getDep_id());
		String depOrderType = this.customerInfoService.getCenterconfigByKey("DEP_ORDER_TYPE",user.getCenter_num()).getConfig_value().trim();
		List<DepUserDTO> list = null;
		if("1".equals(depOrderType)) {
			list = this.userInfoService.getDepUserByID(user.getUserid(),this.defaultapp.getComid(),user.getCenter_num(),"dep_name");
		} else {
			list = this.userInfoService.getDepUserByID(user.getUserid(),this.defaultapp.getComid(),user.getCenter_num(),"seq_code");
		}
		userCenterDepListDTO.setDepUserList(list);
		
		String date = com.hjw.util.DateTimeUtil.getDate2();
		String week = com.hjw.util.DateTimeUtil.getWeek(date +" 00:00");
		userCenterDepListDTO.setCurrent_date(date+" "+week);
		userCenterDepListDTO.setUser_uuid(user.getUuid());
		userCenterDepListDTO.setCenter_name(user.getCenter_name()+"  "+this.defaultapp.getComname());
		this.outJsonResult(userCenterDepListDTO);
		return NONE;
	}
	
	/**
	 * 获取系统通知内容
	     * @Title: getUserSystemInfromsList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String getUserSystemInfromsList() throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		SystemInformsDTO si= new SystemInformsDTO();
		if(user != null){
			si= this.userInfoService.getSystemInfomsDTO(user.getUserid());
		}
		this.outJsonResult(si);
		return NONE;
	} 
	/**
	 * 系统消息 标记为已读
	     * @Title: updateUserSystemInfroms   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	
	public String updateUserSystemInfroms() throws WebException{
		UserDTO user = (UserDTO) session.get("username");
        if(this.model.getCardid()>0){
		  this.userInfoService.updateUserSystemInfroms(this.model.getCardid(),user.getUserid());
        }
		this.outJsonStrResult("ok");
		return NONE;
	}
	/**
	 * 获取更多通知页面
	     * @Title: getMoreUserSystemInformsPage   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String getMoreUserSystemInformsPage() throws WebException{
		return SUCCESS;
	}
	
	/**
	 * 切换科室
	     * @Title: changeDepartMent   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	@SuppressWarnings("unchecked")
	public String changeDepartMent() throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		session.remove("username");
		user.setDep_id(this.dep_id);
		DepartmentDep dd = new DepartmentDep();
		dd = this.departmentService.loadDepartmentDep(this.dep_id);
		user.setDep_url(dd.getDep_link().trim());
		user.setDeptCode(dd.getDep_num());
		user.setIsPrint(dd.getIsPrint_Barcode());
		/*if(this.dep_id==52 || "22".equals(dd.getDep_category()) || "21".equals(dd.getDep_category()) || "17".equals(dd.getDep_category())){
			if((dd==null)||(dd.getDep_link()==null)||(dd.getDep_link().trim().length()<=0)){
				user.setDep_url("person.jsp");
			}else{
				user.setDep_url(dd.getDep_link().trim());
			}
		}else if(this.dep_id==47){
			if((dd==null)||(dd.getDep_link()==null)||(dd.getDep_link().trim().length()<=0)){
				user.setDep_url("person.jsp");
			}else{
				user.setDep_url(dd.getDep_link().trim());
			}
		}else if(this.dep_id==56){
			if((dd==null)||(dd.getDep_link()==null)||(dd.getDep_link().trim().length()<=0)){
				user.setDep_url("person.jsp");
			}else{
				user.setDep_url(dd.getDep_link().trim());
			}
		}else if(this.dep_id==54){//总检科室
			if((dd==null)||(dd.getDep_link()==null)||(dd.getDep_link().trim().length()<=0)){
				user.setDep_url("person.jsp");
			}else{
				user.setDep_url(dd.getDep_link().trim());
			}	
		}else{
			user.setDep_url("person.jsp");
		}*/
		
		UUID uuid = UUID.randomUUID();
		user.setUuid(uuid.toString());
		session.put("username", user);
		
		this.message = "切换科室成功";
		this.outJsonStrResult(this.message);
		return NONE;
	}
	
	/**
	 * 查询本科室当天体检人数
	     * @Title: getCheckedCustomerCount   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String getCheckedCustomerCount() throws WebException, SQLException{
		UserDTO user = (UserDTO) session.get("username");
		List<ExamInfoCountDTO> list = this.userInfoService.getCheckedCustomerCount(user.getDep_id(), user.getCenter_num(), com.hjw.util.DateTimeUtil.getDate2());
		this.outJsonResult(list);
		return NONE;
	}
	
	/**
	 * 验证session是否相同
	     * @Title: verifyUser   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String verifyUser() throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		if(user!=null){
			if(user != null && user.getUuid() != null && user.getUuid().equals(this.model.getUser_uuid())){
				this.message = "ok";
			}else{
				this.message = "no";
			}
		}else{
			this.message = "no";
		}
		this.outJsonStrResult(this.message);
		return NONE;
	}

	public Object getModel() {
		return model;
	}

	public void setModel(AccountModel model) {
		this.model = model;
	}

	public void setMenuGnService(MenuGnService menuGnService) {
		this.menuGnService = menuGnService;
	}
	
	/**
	 * 获取版本号
	     * @Title: getVersion   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return      
	     * @return: String      
	     * @throws
	 */
	public String getVersion(){
		//String banbenhao=this.logincheck.getVersion();
		String banbenhao=this.userInfoService.getSysVersion();
		this.outJsonStrResult(banbenhao);
		return NONE;
	}
	
	public  String showversion(){
		return SUCCESS;
	}
	
	/**
	 *获取版本信息
	     * @Title: getVersionInfos   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return      
	     * @return: String      
	     * @throws
	 */
	public  String getVersionInfos(){
		List<SysVersionDTO> list = new ArrayList<SysVersionDTO>();
		list=this.userInfoService.getSysVersionList();       
        this.outJsonResult(list);
		return NONE;
	}
}
