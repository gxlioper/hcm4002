package com.hjw.wst.action;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

import org.jasig.cas.client.authentication.AttributePrincipal;

import com.hjw.config.TermConfig;
import com.hjw.util.EncryptUtils;
import com.hjw.wst.DTO.DepUserDTO;
import com.hjw.wst.DTO.JobDTO;
import com.hjw.wst.DTO.UserCenterDepListDTO;
import com.hjw.wst.DTO.UserDTO;
import com.hjw.wst.action.AccountAction.MyComparator;
import com.hjw.wst.domain.DepartmentDep;
import com.hjw.wst.domain.Examinatioin_center;
import com.hjw.wst.domain.SysLog;
import com.hjw.wst.domain.WebResrelAtionship;
import com.hjw.wst.domain.WebRole;
import com.hjw.wst.domain.WebRoleMenu;
import com.hjw.wst.domain.WebRoleReport;
import com.hjw.wst.domain.WebUserInfo;
import com.hjw.wst.domain.WebUserjsb;
import com.hjw.wst.domain.WebXtgncd;
import com.hjw.wst.service.DepartmentService;
import com.hjw.wst.service.Examinatioin_centerService;
import com.hjw.wst.service.MenuGnService;
import com.hjw.wst.service.SyslogService;
import com.hjw.wst.service.UserInfoService;
import com.synjones.framework.aop.SystemType;
import com.synjones.framework.exception.WebException;
import com.synjones.framework.web.action.BaseAction;

public class CASClientAction extends BaseAction{
	
	private UserInfoService userInfoService;
	private MenuGnService menuGnService;
	private SyslogService syslogService;	
	private DepartmentService departmentService;
	private Examinatioin_centerService examinatioin_centerService;
	
	
	public void setExaminatioin_centerService(Examinatioin_centerService examinatioin_centerService) {
		this.examinatioin_centerService = examinatioin_centerService;
	}

	public void setDepartmentService(DepartmentService departmentService) {
		this.departmentService = departmentService;
	}

	public void setSyslogService(SyslogService syslogService) {
		this.syslogService = syslogService;
	}

	public void setMenuGnService(MenuGnService menuGnService) {
		this.menuGnService = menuGnService;
	}

	public void setUserInfoService(UserInfoService userInfoService) {
		this.userInfoService = userInfoService;
	}

	/**
	 * 
	     * @Title: get_CAS_NeuSoft   
	     * @Description: 湖北宜昌  
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String get_CAS_NeuSoft() throws WebException {
		AttributePrincipal principal = (AttributePrincipal) request.getUserPrincipal();
        if(principal==null){
            this.message = "单点登录获取信息错误!";
            return "commerror";
        }else{
		String userName = principal.getName();
		session.remove("rand");
		session.remove("username");
		session.remove("userinfo");
		session.put("sk", "username");
		UserDTO user = new UserDTO();
		
		String usertype = "2";
		if (userName == null && userName.trim().length() <= 0) {
			 this.message = "单点登录获取工号错误!";
			 return "commerror";
		} else {
			WebUserInfo wui = userInfoService.getUserInfoByWorkNum(userName);
			if (wui == null) {
				 this.message = "用户名不存在!";
				 return "commerror";
			} else {
				if (wui.getIs_active().equals("N")) {
					 this.message = "用户已停用!";
					 return "commerror";
				} else {
					session.put("usertype", usertype);
					boolean loginflag = false;
					List<SystemType> apptype=TermConfig.getTermConfig(user.getCenter_num());
					for (SystemType apps : apptype) {
							if (apps.getComid().equals("1")) {
								loginflag = true;
								session.put("defaultapp", apps);
								break;
							}
						}
					
					if (!loginflag) {
						 this.message = "无系统授权!";
						 return "commerror";
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

						List<DepUserDTO> list = this.userInfoService.getDepUserByID(wui.getId(),"1" ,user.getCenter_num(),"seq_code");
						long dep_id = 0;
						String dep_num = "";
						if (list == null || list.size() <= 0) {
							 this.message = "无系统授权部门!";
							return "commerror";
						} else {

							dep_id = list.get(0).getDep_id();
							DepartmentDep dd = this.departmentService.loadDepartmentDep(dep_id);
							dep_num = dd.getDep_num();

							user.setJob(getJobInfo(wui.getId() + ""));// 保存角色list
							user.setXtgn(this.geusertxt(user.getJob(), usertype));// 通过角色获的所有系统功能
							user.setRolestr(this.geusertxt1(user.getJob(), usertype));
							user.setReportid(this.geusertxtreport(user.getJob(), usertype));
							user.setUsertype(usertype);
							user.setDep_id(dep_id);
							user.setDep_url(dd.getDep_link().trim());
							user.setDeptCode(dd.getDep_num());
							user.setIsPrint(dd.getIsPrint_Barcode());
							long center_id = 27;
							Examinatioin_center center = this.examinatioin_centerService.loadExaminatioin(center_id);
							user.setCenter_num(center.getCenter_num());
							user.setCenter_name(center.getCenter_name());
							user.setCenter_id(center_id);
							UUID uuid = UUID.randomUUID();
							user.setUuid(uuid.toString());
							session.put("username", user);
							session.put("userinfo", wui);

							user = (UserDTO) session.get("username");
							SysLog sl = new SysLog();
							sl.setCenter_num(user.getCenter_num());
							sl.setUserid(user.getUserid() + "");
							sl.setOper_type("9");// 0 查询，1插入 2修改 3 删除 4导入 5，登录 9
													// 其他
							sl.setXtgnid("12");// 可不填写
							sl.setXtgnid2("12");// 子功能id 必须填写
							sl.setExplain("单点登录");// 操作说明
							syslogService.saveSysLog(sl);
							return SUCCESS;
						}
					}
				}
			}
		}
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
	
}
