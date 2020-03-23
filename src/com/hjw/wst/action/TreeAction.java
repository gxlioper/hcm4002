package com.hjw.wst.action;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.hjw.config.TermConfig;

import org.hsqldb.lib.StringUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONSerializer;

import com.hjw.wst.DTO.DepartmentDepDTO;
import com.hjw.wst.DTO.LogItem;
import com.hjw.wst.DTO.MenuDTO;
import com.hjw.wst.DTO.ReportMenuDTO;
import com.hjw.wst.DTO.TreeDTO;
import com.hjw.wst.DTO.UserCenterDepListDTO;
import com.hjw.wst.DTO.UserDTO;
import com.hjw.wst.DTO.WebXtgncdDTO;
import com.hjw.wst.domain.SysLog;
import com.hjw.wst.domain.WebResrelAtionship;
import com.hjw.wst.domain.WebRole;
import com.hjw.wst.domain.WebRoleMenu;
import com.hjw.wst.domain.WebRoleReport;
import com.hjw.wst.domain.WebXtgnb;
import com.hjw.wst.domain.WebXtgncd;
import com.hjw.wst.model.WebXtgncdModel;
import com.hjw.wst.service.CompanyService;
import com.hjw.wst.service.CustomerInfoService;
import com.hjw.wst.service.MenuGnService;
import com.hjw.wst.service.ReportMenuService;
import com.hjw.wst.service.SyslogService;
import com.hjw.wst.service.UserInfoService;
import com.hjw.util.Language;
import com.hjw.util.ValidateTime;
import com.opensymphony.xwork2.ModelDriven;
import com.synjones.framework.aop.SystemType;
import com.synjones.framework.exception.WebException;
import com.synjones.framework.web.action.BaseAction;

/**
 * 
 * 功能、角色、岗位管理
 * 
 * @author yangm
 * @version [3.0.0, Nov 4, 2011]
 * @see [相关类/方法]
 * @since [一卡通网站查询系统]
 */
@SuppressWarnings("rawtypes")
public class TreeAction extends BaseAction implements ModelDriven {

	private static final long serialVersionUID = 1L;
	private WebXtgncdModel model = new WebXtgncdModel();
	private MenuGnService menuGnService;
	private LogItem logItem = new LogItem();
	private CompanyService companyService;
    private ReportMenuService reportMenuService;
    private SyslogService syslogService;
    private CustomerInfoService customerInfoService;
    private UserInfoService userInfoService;    
   
	public void setUserInfoService(UserInfoService userInfoService) {
		this.userInfoService = userInfoService;
	}
	public void setSyslogService(SyslogService syslogService) {
   		this.syslogService = syslogService;
   	}
	public void setReportMenuService(ReportMenuService reportMenuService) {
		this.reportMenuService = reportMenuService;
	}

	public void setCompanyService(CompanyService companyService) {
		this.companyService = companyService;
	}

	public void setCustomerInfoService(CustomerInfoService customerInfoService) {
		this.customerInfoService = customerInfoService;
	}
	@SuppressWarnings("unchecked")
	public String menu() throws WebException, SQLException {
		String sPath = "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath()
				+ request.getServletPath().substring(0, request.getServletPath().lastIndexOf("/") + 1);
		List<WebXtgncdDTO> ls = new ArrayList();
		UserDTO user = new UserDTO();
		user = (UserDTO) session.get("username");
		if (user == null) {
			return "";
		}
		StringBuffer sb = new StringBuffer();
		sb.append(
				"<h3><a href=\"javascript:void(0)\"><img src=\"themes/default/images/blank.gif\" class=\"icon-24 ico-10-1 \" alt=\"\" />应用系统<span class=\"icon arrow\"></span></a></h3>");
		sb.append("<div class=\"sub-menu\"><ul>");
		List<SystemType> apptype=TermConfig.getTermConfig(user.getCenter_num());
			for (SystemType apps : apptype) {
				sb.append("<li><div class=\"next-menu\" onclick=\"addPanelmain('" + apps.getComcode() + "');\">"
						+ apps.getComname() + "</div></li>");
			}
		sb.append("</ul></div>");
		if (StringUtil.isEmpty(this.message)) {
			SystemType st= (SystemType) session.get("defaultapp");
			this.message =st.getComcode();
		}else{
			for (SystemType apps : apptype) {
					if (apps.getComcode().equals(message)) {
						session.put("defaultapp",apps);
						break;
					}
				}
		}
		ls = this.menuGnService.findWebgnList(user.getRolestr(), user.getXtgn(), sPath, this.message);
		
		for (int i = 0; i < ls.size(); i++) {
			WebXtgncdDTO wx = (WebXtgncdDTO) ls.get(i);
			sb.append("<h3><a href=\"javascript:void(0)\"><img src=\"themes/default/images/blank.gif\" class=\"icon-24 "
					+ wx.getIcon_url() + " \" alt=\"\" />" + wx.getName()
					+ "<span class=\"icon arrow\"></span></a></h3>");
			sb.append("<div class=\"sub-menu\"><ul>");
			String url = sPath+"getDepItemDetail.action";
			System.out.print(url);
			for (int j = 0; j < wx.getList().size(); j++) {
				WebXtgncdDTO wxnex = (WebXtgncdDTO) wx.getList().get(j);
				if(!"999".equals(user.getUsertype()) && url.equals(wxnex.getOther_url())){
					continue;
				}
				sb.append("<li><div class=\"next-menu\" onclick=\"addPanel('" + wxnex.getName() + "','"
						+ wxnex.getOther_url() + "','" + wx.getIcon_url() + "','1');\">" + wxnex.getName()
						+ "</div></li>");
			}
			sb.append("</ul></div>");
		}

		ls = this.menuGnService.findWebReportList(user.getRolestr(), user.getReportid(), sPath);
		String IS_BAOBIAO_TREE = this.customerInfoService.getCenterconfigByKey("IS_BAOBIAO_TREE", user.getCenter_num()).getConfig_value().trim();//报表功能是否采用树形菜单
		if("Y".equals(IS_BAOBIAO_TREE)){
			for (WebXtgncdDTO wx:ls) {
	            if((wx.getFather_id()==null)||("null".equals(wx.getFather_id()))||("0".equals(wx.getFather_id()))){
				sb.append("<h3><a href=\"javascript:void(0)\"><img src=\"themes/default/images/blank.gif\" class=\"icon-24 ico-14-1 \" alt=\"\" />" + wx.getName()
						+ "<span class=\"icon arrow\"></span></a></h3>");
				sb.append("<div class=\"sub-menu\"><ul>");
				for (WebXtgncdDTO wxnex:ls) {
					if(wx.getId().equals(wxnex.getFather_id()))
					{
					String otherurl="ReportServer?reportlet="+wxnex.getOther_url()+"&userId="+user.getUserid()+"&op=view";
					sb.append("<li><div class=\"next-menu\" onclick=\"addPanel('" + wxnex.getName() + "','"
							+ otherurl + "','" + wx.getIcon_url() + "','1');\">" + wxnex.getName()
							+ "</div></li>");
					}
				}
				sb.append("</ul></div>");
	            }
			}
		} else {
			if ((ls != null) && (ls.size() > 0)) {
				sb.append(
						"<h3><a href=\"javascript:void(0)\"><img src=\"themes/default/images/blank.gif\" class=\"icon-24 ico-14-1 \" alt=\"\" />统计报表<span class=\"icon arrow\"></span></a></h3>");
				sb.append("<div class=\"sub-menu\"><ul>");
				String otherurl = "reportServermain.action";
				sb.append("<li><div class=\"next-menu\" onclick=\"addPanel('统计报表','" + otherurl
						+ "','','1');\">报表浏览</div></li>");
			}		
		}
		this.outJsonStrResult(sb.toString());
		return NONE;
	}
	
		
	@SuppressWarnings("unchecked")
	public String menuReport() throws WebException, SQLException {
		String sPath = "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath()
				+ request.getServletPath().substring(0, request.getServletPath().lastIndexOf("/") + 1);
		List<WebXtgncdDTO> ls = new ArrayList();
		UserDTO user = new UserDTO();
		user = (UserDTO) session.get("username");
		if (user == null) {
			return "";
		}
		ls = this.menuGnService.findWebReportList(user.getRolestr(), user.getReportid(), sPath);
		StringBuffer sb = new StringBuffer();
		for (WebXtgncdDTO wx:ls) {
            if((wx.getFather_id()==null)||("null".equals(wx.getFather_id()))||("0".equals(wx.getFather_id()))){
			sb.append("<h3><a href=\"javascript:void(0)\"><img src=\"themes/default/images/blank.gif\" class=\"icon-24 ico-14-1 \" alt=\"\" />" + wx.getName()
					+ "<span class=\"icon arrow\"></span></a></h3>");
			sb.append("<div class=\"sub-menu\"><ul>");
			for (WebXtgncdDTO wxnex:ls) {
				if(wx.getId().equals(wxnex.getFather_id()))
				{
				sb.append("<li><div class=\"next-menu\" onclick=\"addPanel('" + wxnex.getName() + "','"
						+ wxnex.getOther_url() + "','" + wx.getIcon_url() + "','1');\">" + wxnex.getName()
						+ "</div></li>");
				}
			}
			sb.append("</ul></div>");
            }
		}
		this.outJsonStrResult(sb.toString());
		return NONE;
	}

	/**
	 * 显示一个系统功能菜单 功能菜单14
	 * 
	 * @return
	 * @throws WebException
	 * @see [类、类#方法、类#成员]
	 */
	public String getMenuzbOne() throws WebException, Exception {
		Language.setLanguage(this.language);
		WebXtgncd wx = this.menuGnService.loadWebXtgncd(this.model.getId());
		String menuString = JSONSerializer.toJSON(wx).toString();
		menuString = menuString.replace("childNodes", "ChildNodes");
		this.outJsonStrResult(menuString);
		return NONE;
	}

	public String getMenuzbByid() throws WebException, Exception {
		Language.setLanguage(this.language);

		WebXtgncd wx = this.menuGnService.loadWebXtgncd(this.model.getId());
		String jsonObject = JSONSerializer.toJSON(wx).toString();

		this.outJsonStrResult(jsonObject);
		return NONE;
	}
	
	
	public String getcomid() throws WebException, Exception {
		String centerkey=com.ymhjw.security.StringUtil.getCompId();
		String jsonObject = "机器识别码： "+centerkey;
		this.outJsonStrResult(jsonObject);
		return NONE;
	}

	/**
	 * 显示一个功能 功能菜单14
	 * 
	 * @return
	 * @throws WebException
	 * @see [类、类#方法、类#成员]
	 */
	@SuppressWarnings("unchecked")
	public String getGnMenuOne() throws WebException, Exception {
		String intss="0";
		this.defaultapp = (SystemType)session.get("defaultapp");
		if(StringUtil.isEmpty(this.defaultapp.getComcode())){
			intss="0";
		}else if("zyb".equals(this.defaultapp.getComcode())){
			intss="1";
		}else if("crm".equals(defaultapp.getComcode())){
			intss="3";
		}else{
			intss="0";
		}
		List<WebXtgncd> wxlist = new ArrayList();
		List<WebXtgncd> webXtgncds1 = new ArrayList();
		List<WebXtgncd> webXtgncds2 = new ArrayList();
		List<WebXtgncd> webXtgncds3 = new ArrayList();
		webXtgncds1 = this.menuGnService.getMenuGn(intss);
		/*
		 * WebXtgncd wb0 = new WebXtgncd(); wb0.setId("root");
		 * wb0.setName(getText("rootml")); wb0.setFather_id("0");
		 * wxlist.add(wb0);
		 */
		for (int i = 0; i < webXtgncds1.size(); i++) {
			WebXtgncd wb1 = webXtgncds1.get(i);
			wb1.setName(wb1.getName());
			wb1.setRemark("---"+wb1.getName());
			wxlist.add(wb1);
			webXtgncds2 = this.menuGnService.getMenuGn(wb1.getId());
			for (int j = 0; j < webXtgncds2.size(); j++) {
				WebXtgncd wb2 = webXtgncds2.get(j);
				wb2.setRemark("------------"+wb2.getName());
				wb2.setName(wb2.getName());
				wxlist.add(wb2);
				webXtgncds3 = this.menuGnService.getMenuGn(wb2.getId());
				for (int m = 0; m < webXtgncds3.size(); m++) {
					WebXtgncd wb3 = webXtgncds3.get(m);
					wb3.setRemark("--------------------"+wb3.getName());
					wb3.setName(wb3.getName());
					wxlist.add(wb3);
				}
			}
		}

		String jsonString = JSONSerializer.toJSON(wxlist).toString();
		this.outJsonStrResult(jsonString);
		return NONE;
	}

	
	/**
	 * 得到功能列表 功能菜单14
	 * 
	 * @return
	 * @throws WebException
	 * @see [类、类#方法、类#成员]
	 */

	public String getGnList() throws WebException, Exception {
		String intss="0";
		this.defaultapp = (SystemType)session.get("defaultapp");
		if("com".equals(this.defaultapp.getComcode())){
			intss="'0','1'";
		}else if("zyb".equals(this.defaultapp.getComcode())){
			intss="'0','2'";
		}else if("crm".equals(defaultapp.getComcode())){
			intss="'0','3'";
		}else{
			intss="'0','1'";
		}
		List<WebXtgnb> wxlist = new ArrayList<WebXtgnb>();
		wxlist = this.menuGnService.getXtgn("1",intss);

		String jsonString = JSONSerializer.toJSON(wxlist).toString();
		this.outJsonStrResult(jsonString);

		return NONE;
	}

	/**
	 * 删除系统功能菜单 功能菜单44
	 * 
	 * @return
	 * @throws WebException
	 * @see [类、类#方法、类#成员]
	 */
	public String delmenu() throws WebException, Exception {
		Language.setLanguage(this.language);

		WebXtgncd wx_old = this.menuGnService.loadWebXtgncd(this.model.getRootid());

		if (wx_old != null) {
			List webxtgnList = this.menuGnService.getMenuGn(wx_old.getId());
			if ((webxtgnList != null) && (webxtgnList.size() > 0)) {

				String jsonString = getText("delermsg");
				this.outJsonStrResult(jsonString);
				return NONE;
			}
			this.menuGnService.delWebXtgncd(this.model.getRootid());
			UserDTO user = (UserDTO) session.get("username");
			SysLog sl =  new SysLog();
			sl.setCenter_num(user.getCenter_num());
			sl.setUserid(user.getUserid()+"");
			sl.setOper_type("3");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
			sl.setXtgnid("");//可不填写
			sl.setXtgnid2("44");//子功能id 必须填写
			sl.setExplain(this.model.getRootid());//操作说明
			syslogService.saveSysLog(sl);
			// 初始化日志对象
			logItem.setFlag(true);
			logItem.setOperId(wx_old.getId() + "");
			logItem.setOperName(wx_old.getName());
			String jsonString = getText("delscmsg");
			this.outJsonStrResult(jsonString);
			return NONE;
		} else {
			String jsonString = "要删除的菜单已经不存在!";
			this.outJsonStrResult(jsonString);
			return NONE;
		}
	}

	/**
	 * 增加功能菜单 功能号42
	 * 
	 * @return
	 * @throws WebException
	 * @throws Exception
	 * @see [类、类#方法、类#成员]
	 */
	public String addmenu() throws WebException, Exception {
		Language.setLanguage(this.language);
		UserDTO user = new UserDTO();
		String sk = (String) session.get("sk");
		user = (UserDTO) session.get(sk);

		WebXtgncd wx = new WebXtgncd();

		if ("root".equals(this.model.getRootid())) {
			wx.setLevels(1);
			wx.setFather_id("0");
			wx.setUsertype(this.model.getUsertype());
			List webxtgnList = this.menuGnService.findWebXtgncdByUsertype(this.model.getUsertype());
			if ((webxtgnList != null) && (webxtgnList.size() > 0)) {

				String jsonString = getText("addermsg");
				this.outJsonStrResult(jsonString);
				return NONE;
			}
		} else {
			WebXtgncd wx_old = this.menuGnService.loadWebXtgncd(this.model.getRootid());
			if (wx_old.getLevels() == 3) {
				String jsonString = getText("addersjmsg");
				this.outJsonStrResult(jsonString);

				return NONE;
			}
			if (wx_old.getLevels() == 0) {
				List webxtgnList = this.menuGnService.findWebXtgncdByUsertype(this.model.getUsertype());
				if ((webxtgnList != null) && (webxtgnList.size() > 0)) {

					String jsonString = getText("addermsg");
					this.outJsonStrResult(jsonString);

					return NONE;
				}
			}

			wx.setLevels(wx_old.getLevels() + 1);
			wx.setFather_id(wx_old.getId());
			wx.setUsertype(wx_old.getUsertype());
		}

		wx.setDatetime(ValidateTime.getDateTime());
		wx.setName(this.model.getName());
		wx.setOther_url(this.model.getOther_url());
		wx.setIndexid(this.model.getIndexid());
		wx.setUrl_type(this.model.getUrl_type());
		wx.setIspop(this.model.getIspop());
		wx.setXtgn_id(this.model.getXtgn_id());
		wx.setRy(user.getUserid() + "");
		this.menuGnService.saveWebXtgncd(wx);
		//UserDTO user = (UserDTO) session.get("username");
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("1");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("42");//子功能id 必须填写
		sl.setExplain(wx.getName());//操作说明
		syslogService.saveSysLog(sl);
		// 初始化日志对象
		logItem.setFlag(true);
		logItem.setOperId(wx.getId() + "");
		logItem.setOperName(wx.getName());
		String jsonString = getText("addscmsg");
		this.outJsonStrResult(jsonString);

		return NONE;
	}

	/**
	 * 
	     * @Title: editmenu   61
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws Exception      
	     * @return: String      
	     * @throws
	 */
	public String editmenu() throws WebException, Exception {
		Language.setLanguage(this.language);
		UserDTO user = new UserDTO();
		String sk = (String) session.get("sk");
		user = (UserDTO) session.get(sk);

		WebXtgncd wx = new WebXtgncd();
		if (this.model.getId() != null && this.model.getId() != "") {
			wx = this.menuGnService.loadWebXtgncd(this.model.getId());
			if ("root".equals(this.model.getRootid())) {
				wx.setLevels(1);
				wx.setFather_id("0");
				wx.setUsertype(this.model.getUsertype());
				List webxtgnList = this.menuGnService.findWebXtgncdByUsertype(this.model.getUsertype());
				if ((webxtgnList != null) && (webxtgnList.size() > 0)) {

					String jsonString = getText("addermsg");
					this.outJsonStrResult(jsonString);
					return NONE;
				}
			} else {
				WebXtgncd wx_old = this.menuGnService.loadWebXtgncd(this.model.getRootid());
				if (wx_old.getLevels() == 4) {

					String jsonString = getText("addersjmsg");
					this.outJsonStrResult(jsonString);

					return NONE;
				}
				if (wx_old.getLevels() == 0) {
					List webxtgnList = this.menuGnService.findWebXtgncdByUsertype(this.model.getUsertype());
					if ((webxtgnList != null) && (webxtgnList.size() > 0)) {

						String jsonString = getText("addermsg");
						this.outJsonStrResult(jsonString);

						return NONE;
					}
				}

				wx.setLevels(wx_old.getLevels() + 1);
				wx.setFather_id(wx_old.getId());
				wx.setUsertype(wx_old.getUsertype());
			}

			wx.setDatetime(ValidateTime.getDateTime());
			new String(this.model.getName().getBytes("utf-8"), "GBK");
			wx.setName(this.model.getName());
			wx.setOther_url(this.model.getOther_url());
			wx.setIndexid(this.model.getIndexid());
			wx.setUrl_type(this.model.getUrl_type());
			wx.setIspop(this.model.getIspop());
			wx.setXtgn_id(this.model.getXtgn_id());
			wx.setRy(user.getUserid() + "");
			this.menuGnService.updateWebXtgncd(wx);
			//UserDTO user = (UserDTO) session.get("username");
			SysLog sl =  new SysLog();
			sl.setCenter_num(user.getCenter_num());
			sl.setUserid(user.getUserid()+"");
			sl.setOper_type("2");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
			sl.setXtgnid("");//可不填写
			sl.setXtgnid2("61");//子功能id 必须填写
			sl.setExplain(wx.getName());//操作说明
			syslogService.saveSysLog(sl);
		}
		// 初始化日志对象
		logItem.setFlag(true);
		logItem.setOperId(wx.getId() + "");
		logItem.setOperName(wx.getName());
		String jsonString = getText("updatescmsg");
		this.outJsonStrResult(jsonString);

		return NONE;
	}

	/**
	 * 获取角色 48
	 * 
	 * @return
	 * @throws WebException
	 * @see [类、类#方法、类#成员]
	 */
	public String rolegn() throws WebException {
		// Language.setLanguage(this.language);
		WebRole wr = this.menuGnService.loadWebRole(this.model.getId());
		this.model.setId(wr.getId() + "");
		this.model.setName(wr.getRolename());
		UserDTO user = (UserDTO) session.get("username");
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("48");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return SUCCESS;
	}

	/**
	 * 获取一个功能 16
	 * 
	 * @return
	 * @throws WebException
	 * @see [类、类#方法、类#成员]
	 */
	public String showrolegnone() throws WebException, Exception {
		Language.setLanguage(this.language);
		WebRole wr = this.menuGnService.loadWebRole(this.model.getId());
		this.model.setId(wr.getId() + "");
		this.model.setName(wr.getRolename());
		return SUCCESS;
	}

	/**
	 * 显示一个角色的所有菜单
	 * 
	 * @return
	 * @throws WebException
	 * @see [类、类#方法、类#成员]
	 */
	@SuppressWarnings("unchecked")
	public String rolegnshowone() throws WebException {
		String intss="0";
		this.defaultapp = (SystemType)session.get("defaultapp");
		if(StringUtil.isEmpty(this.defaultapp.getComcode())){
			intss="0";
		}else if("zyb".equals(this.defaultapp.getComcode())){
			intss="1";
		}else if("crm".equals(this.defaultapp.getComcode())){
			intss="3";
		}else{
			intss="0";
		}
		List<WebRoleMenu> wr = this.menuGnService.fingWebRoleMenu2RoleId(this.model.getId());
		List<MenuDTO> menus = new ArrayList<MenuDTO>(); 
		MenuDTO om = new MenuDTO();
		om.setId(intss);
		om.setCheckstate(0);
		om.setComplete(true);
		om.setHasChildren(true);
		om.setIsexpand(true);
		om.setShowcheck(false);
		om.setText("功能菜单");
		om.setValue("root");
		om.setChildNodes(menus);

		List<WebXtgncd> webXtgncds1 = new ArrayList<WebXtgncd>();
		List<WebXtgncd> webXtgncds2 = new ArrayList<WebXtgncd>();
		List<WebXtgncd> webXtgncds3 = new ArrayList<WebXtgncd>();
		webXtgncds1 = this.menuGnService.getMenuGn(intss);
		for (int i = 0; i < webXtgncds1.size(); i++) {
			WebXtgncd wb1 = webXtgncds1.get(i);
			MenuDTO menu1 = new MenuDTO();
			menu1.setId(wb1.getId());
			menu1.setText(wb1.getName());
			menu1.setValue(wb1.getId() + "");
			menu1.setCheckstate(0);
			menu1.setComplete(true);
			int setCheckstate = 0;
			for (int y = 0; y < wr.size(); y++) {
				WebRoleMenu wm = wr.get(y);
				if (wb1.getId().equals(wm.getGncd_id())) {
					setCheckstate = 1;
					break;
				}
			}
			menu1.setCheckstate(setCheckstate);
			menu1.setShowcheck(true);
			menu1.setIsexpand(true);
			menus.add(menu1);

			webXtgncds2 = this.menuGnService.getMenuGn(wb1.getId());

			if ((webXtgncds2 == null) || (webXtgncds2.size() <= 0)) {
				menu1.setHasChildren(false);
				continue;
			} else {
				menu1.setHasChildren(true);
			}

			List<MenuDTO> menuList2 = new ArrayList();
			menu1.setChildNodes(menuList2);
			// 二级菜单目录1的子菜单1

			for (int j = 0; j < webXtgncds2.size(); j++) {
				MenuDTO menu2 = new MenuDTO();
				WebXtgncd wb2 = webXtgncds2.get(j);
				menu2.setId(wb2.getId());
				menu2.setText(wb2.getName());
				menu2.setValue(wb2.getId() + "");
				menu2.setCheckstate(0);
				menu2.setComplete(true);
				menu2.setIsexpand(true);
				setCheckstate = 0;
				for (int y = 0; y < wr.size(); y++) {
					WebRoleMenu wm = wr.get(y);
					if (wb2.getId().equals(wm.getGncd_id())) {
						setCheckstate = 1;
						break;
					}
				}
				menu2.setCheckstate(setCheckstate);
				menu2.setShowcheck(true);
				menuList2.add(menu2);
				webXtgncds3 = this.menuGnService.getMenuGn(wb2.getId());
				if ((webXtgncds3 == null) || (webXtgncds3.size() <= 0)) {
					menu2.setHasChildren(false);
					continue;
				} else {
					menu2.setHasChildren(true);
				}
				List<MenuDTO> menuList3 = new ArrayList();
				menu2.setChildNodes(menuList3);
				// 二级菜单目录1的子菜单1

				for (int m = 0; m < webXtgncds3.size(); m++) {
					MenuDTO menu3 = new MenuDTO();
					WebXtgncd wb3 = webXtgncds3.get(m);
					menu3.setId(wb3.getId());
					menu3.setText(wb3.getName());
					menu3.setValue(wb3.getId() + "");
					menu3.setCheckstate(0);
					menu3.setComplete(true);
					menu3.setIsexpand(false);
					menu3.setHasChildren(false);
					setCheckstate = 0;
					for (int y = 0; y < wr.size(); y++) {
						WebRoleMenu wm = wr.get(y);
						if (wb3.getId().equals(wm.getGncd_id())) {
							setCheckstate = 1;
							break;
						}
					}
					menu3.setCheckstate(setCheckstate);
					menu3.setShowcheck(true);
					menuList3.add(menu3);
				}
			}
		}

		JSONArray jsonObject = JSONArray.fromObject(om);
		String menuString = "";
		try {
			menuString = jsonObject.toString();
			menuString = menuString.replace("childNodes", "ChildNodes");

		} catch (Exception e) {
			menuString = "ss";
		}
		this.outJsonStrResult(menuString);

		return NONE;
	}

	@SuppressWarnings("unchecked")
	private List<TreeDTO> getngList(List<WebRoleMenu> wr, String role_id, String gn_id) {
		List<TreeDTO> treeList = new ArrayList<TreeDTO>();
		List<WebXtgncd> webXtgncds1 = new ArrayList<WebXtgncd>();
		webXtgncds1 = this.menuGnService.getMenuGn(gn_id);
		for (int i = 0; i < webXtgncds1.size(); i++) {
			WebXtgncd wb1 = webXtgncds1.get(i);
			TreeDTO tree1 = new TreeDTO();
			tree1.setId(wb1.getId() + "");
			tree1.setText(wb1.getName());
			int setCheckstate = 0;
			int rolemenucount = this.menuGnService.fingWebRoleMenu2RoleId(role_id, wb1.getId());
			int menucount = this.menuGnService.fingWebXtgncdfaId(wb1.getId());
			if (rolemenucount == 0) {
				setCheckstate = 0;
				for (int y = 0; y < wr.size(); y++) {
					WebRoleMenu wm = wr.get(y);
					if (wb1.getId().equals(wm.getGncd_id())) {
						setCheckstate = 1;
						break;
					}
				}

			} else if (rolemenucount < menucount)
				setCheckstate = 2;
			else if (rolemenucount == menucount)
				setCheckstate = 1;
			else {
				setCheckstate = 0;
			}
			if (setCheckstate != 1) {
				tree1.setChecked(false);
			} else {
				tree1.setChecked(true);
			}
			tree1.setChildren(getngList(wr, role_id, wb1.getId() + ""));
			treeList.add(tree1);
		}
		return treeList;
	}

	/**
	 * 获取一个功能的菜单-带多选框的
	 * 
	 * @return
	 * @throws WebException
	 * @see [类、类#方法、类#成员]
	 */
	public String rolegnshow() throws WebException {
		String intss="0";
		this.defaultapp = (SystemType)session.get("defaultapp");
		if(StringUtil.isEmpty(this.defaultapp.getComcode())){
			intss="0";
		}else if("zyb".equals(this.defaultapp.getComcode())){
			intss="1";
		}else if("crm".equals(this.defaultapp.getComcode())){
			intss="3";
		}else{
			intss="0";
		}
		List<WebRoleMenu> wr = this.menuGnService.fingWebRoleMenu2RoleId(this.model.getId());
		// List<TreeDTO> treeList=new ArrayList<TreeDTO>();
		TreeDTO tree = new TreeDTO();
		tree.setId(intss);
		tree.setText("功能菜单");
		tree.setChildren(getngList(wr, this.model.getId(), intss));
		JSONArray jsonObject = JSONArray.fromObject(tree);
		this.outJsonStrResult(jsonObject.toString());
		return NONE;
	}
	
	/**
	 * 获取一个功能的菜单-带多选框的
	 * 
	 * @return
	 * @throws WebException
	 * @see [类、类#方法、类#成员]
	 */
	public String centerAndDeptshow() throws WebException {
		String intss="0";
		List<WebRoleMenu> wr = this.menuGnService.fingWebRoleMenu2RoleId(this.model.getId());
		// List<TreeDTO> treeList=new ArrayList<TreeDTO>();
		TreeDTO tree = new TreeDTO();
		tree.setId(intss);
		tree.setText("部门");
		tree.setChildren(getcenterDeptList());
		JSONArray jsonObject = JSONArray.fromObject(tree);
		this.outJsonStrResult(jsonObject.toString());
		return NONE;
	}
	
	/**
	 * 
	     * @Title: getcenterDeptList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return      
	     * @return: List<TreeDTO>      
	     * @throws
	 */
	private List<TreeDTO> getcenterDeptList() {
		List<TreeDTO> treeList = new ArrayList<TreeDTO>();
		UserDTO user = (UserDTO) session.get("username");
		List<UserCenterDepListDTO> ListuserCenterDepListDTO = new ArrayList<UserCenterDepListDTO>();
		if ("999".equals(user.getUsertype())){
			ListuserCenterDepListDTO = this.userInfoService.getCenterAll();
		}else{
			ListuserCenterDepListDTO= this.userInfoService.getCenterByUserName(user.getUsername());
		}
		for (UserCenterDepListDTO ud:ListuserCenterDepListDTO) {
			TreeDTO tree1 = new TreeDTO();
			tree1.setId("c-"+ud.getCenter_id());
			tree1.setText(ud.getCenter_name());
			boolean setCheckstate = false;
			List<UserCenterDepListDTO> myselfudlist=this.userInfoService.getCenterByUserId(this.model.getId());
				for (UserCenterDepListDTO myud:myselfudlist) {
					if (myud.getCenter_id()==ud.getCenter_id()) {
						setCheckstate = true;
						break;
					}
				}
			//tree1.setChecked(setCheckstate);	
			
			 List<DepartmentDepDTO> ddlist=this.userInfoService.getCenterByCenterNum(ud.getCenter_num());
			 List<TreeDTO> treeList1 = new ArrayList<TreeDTO>();
			 this.defaultapp = (SystemType)session.get("defaultapp");
			for(DepartmentDepDTO dd:ddlist){
				TreeDTO tree11 = new TreeDTO();
				tree11.setId("d-"+ud.getCenter_id()+"-"+dd.getId() + "");
				tree11.setText(dd.getDep_name());
				boolean setCheckstate1 = false;				
				List<DepartmentDepDTO> myddlist=this.userInfoService.getCenterByCenterNum(this.model.getId(),ud.getCenter_num(), defaultapp.getComid());
				for(DepartmentDepDTO mydd:myddlist){
					if(mydd.getId()==dd.getId()){
						setCheckstate1=true;
						break;
					}
				}
				tree11.setChecked(setCheckstate1);	
				treeList1.add(tree11);
			}			
			tree1.setChildren(treeList1);			
			treeList.add(tree1);
		}
		return treeList;
	}
	
	
	
	/**
	 * 
	     * @Title: reportshow   
	     * @Description:  显示所有报表 452  
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String reportshow() throws WebException {
		List<WebRoleReport> wr = this.menuGnService.fingWebRoleReport2RoleId(this.model.getId());
		// List<TreeDTO> treeList=new ArrayList<TreeDTO>();
		TreeDTO tree = new TreeDTO();
		tree.setId("0");
		tree.setText("报表模板菜单");
		tree.setChildren(getreportList(wr, this.model.getId(), null));
		JSONArray jsonObject = JSONArray.fromObject(tree);
		this.outJsonStrResult(jsonObject.toString());
		return NONE;
	}
	
	private List<TreeDTO> getreportList(List<WebRoleReport> wr, String role_id, Integer gn_id) {
		List<TreeDTO> treeList = new ArrayList<TreeDTO>();
		List<ReportMenuDTO> webXtgncds1 = new ArrayList<ReportMenuDTO>();
		webXtgncds1 = this.reportMenuService.getReportMenuForFatherId(gn_id);
		for (int i = 0; i < webXtgncds1.size(); i++) {
			ReportMenuDTO wb1 = webXtgncds1.get(i);
			TreeDTO tree1 = new TreeDTO();
			tree1.setId(wb1.getId() + "");
			tree1.setText(wb1.getReport_name());
			int setCheckstate = 0;
			int rolemenucount = this.menuGnService.fingWebReportMenu2RoleId(role_id, wb1.getId());
			int menucount = this.reportMenuService.getReportMenuForFatherIdCount(wb1.getId());
			if (rolemenucount == 0) {
				setCheckstate = 0;
				for (int y = 0; y < wr.size(); y++) {
					WebRoleReport wm = wr.get(y);
					if (wb1.getId()==wm.getRolemenu_id()) {
						setCheckstate = 1;
						break;
					}
				}

			} else if (rolemenucount < menucount)
				setCheckstate = 2;
			else if (rolemenucount == menucount)
				setCheckstate = 1;
			else {
				setCheckstate = 0;
			}
			if (setCheckstate != 1) {
				tree1.setChecked(false);
			} else {
				tree1.setChecked(true);
			}
			Integer web1id = Integer.valueOf(wb1.getId()+"");
			tree1.setChildren(getreportList(wr, role_id,web1id));
			treeList.add(tree1);
		}
		return treeList;
	}
	

	public String companychangshow() throws WebException, Exception {
		UserDTO user = new UserDTO();
		user = (UserDTO) session.get("username");
		List<TreeDTO> tr = new ArrayList<TreeDTO>();
		String IS_COMPANY_SHOW_POW="N";
		try{
			IS_COMPANY_SHOW_POW=this.customerInfoService.getCenterconfigByKey("IS_COMPANY_SHOW_POW", user.getCenter_num()).getConfig_value().trim();
		}catch(Exception ex){
			
		}
		if("Y".equals(IS_COMPANY_SHOW_POW)){//如果需要权限控制
			List<WebResrelAtionship> wrs=user.getWebResource();
	        if(wrs!=null &&wrs.size()>0){
			for(WebResrelAtionship wr :wrs){
				if("RS038".equals(wr.getRes_code())){
					this.model.setRemark("N");
					break;
				}
			}
	        }
		  if("Y".equals(this.model.getRemark())){//如果页面传过来需要权限控制
			  this.model.setRemark("Y");
		  }else{
			  this.model.setRemark("N");
		  }
		}else{
			this.model.setRemark("N");
		}
		tr = this.companyService.getCompanyForNameIsactive(this.model.getName(), "Y",this.model.getRemark(),user.getUserid(),user);
		this.outJsonResult(tr);
		return NONE;

	}

	public String zybcompanychangshow() throws WebException, Exception {
		UserDTO user = new UserDTO();
		user = (UserDTO) session.get("username");
		List<TreeDTO> tr = new ArrayList<TreeDTO>();
		String IS_COMPANY_SHOW_POW="N";
		try{
			IS_COMPANY_SHOW_POW=this.customerInfoService.getCenterconfigByKey("IS_COMPANY_SHOW_POW", user.getCenter_num()).getConfig_value().trim();
		}catch(Exception ex){
			
		}
		if("Y".equals(IS_COMPANY_SHOW_POW)){//如果需要权限控制
			List<WebResrelAtionship> wrs=user.getWebResource();
	        if(wrs!=null &&wrs.size()>0){
			for(WebResrelAtionship wr :wrs){
				if("RS038".equals(wr.getRes_code())){
					this.model.setRemark("N");
					break;
				}
			}
	        }
		  if("Y".equals(this.model.getRemark())){//如果页面传过来需要权限控制
			  this.model.setRemark("Y");
		  }else{
			  this.model.setRemark("N");
		  }
		}else{
			this.model.setRemark("N");
		}
		tr = this.companyService.getZybCompanyForNameIsactive(this.model.getName(), "Y",this.model.getRemark(),user.getUserid(),user);
		this.outJsonResult(tr);
		return NONE;

	}
	
	/**
	 * 
	 * @Title: treecomdeptShow @Description:
	 * 生成部门树 @param: @return @param: @throws WebException @param: @throws
	 * Exception @return: String @throws
	 */
	public String treecomdeptShow() throws WebException, Exception {
		List<TreeDTO> tr = new ArrayList<TreeDTO>();
		tr = this.companyService.getCompanyForName(this.model.getName());
		JSONArray jsonObject = JSONArray.fromObject(tr);
		String menuString = "";
		menuString = jsonObject.toString();
		// menuString = menuString.replace("childNodes", "children");
		this.outJsonStrResult(menuString);
		return NONE;

	}

	/**
	 * 显示功能的树状图，带多选的 功能菜单 14
	 * 
	 * @return
	 * @throws WebException
	 * @see [类、类#方法、类#成员]
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String treeShow() throws WebException, Exception {
		this.defaultapp = (SystemType)session.get("defaultapp");
		String intss="0";
		if(StringUtil.isEmpty(this.defaultapp.getComcode())){
			intss="0";
		}else if("zyb".equals(this.defaultapp.getComcode())){
			intss="1";
		}else if("crm".equals(this.defaultapp.getComcode())){
			intss="3";
		}else{
			intss="0";
		}
		List<TreeDTO> treeList = new ArrayList<TreeDTO>();
		TreeDTO treeDTO = new TreeDTO();
		treeDTO.setChecked(false);
		treeDTO.setText(getText("fnmenu"));
		treeDTO.setId(this.defaultapp.getComid());
		treeDTO.setChildren(treeList);
		List<WebXtgncd> webXtgncds1 = new ArrayList();
		List<WebXtgncd> webXtgncds2 = new ArrayList();
		List<WebXtgncd> webXtgncds3 = new ArrayList();
		webXtgncds1 = this.menuGnService.getMenuGn(intss);
		for (int i = 0; i < webXtgncds1.size(); i++) {
			WebXtgncd web1 = webXtgncds1.get(i);
			TreeDTO tree1 = new TreeDTO();
			tree1.setId(web1.getId() + "");
			tree1.setText(web1.getName());
			treeList.add(tree1);
			webXtgncds2 = this.menuGnService.getMenuGn(web1.getId());
			/*
			 * if ((webXtgncds2 == null) || (webXtgncds2.size() <= 0)) {
			 * tree1.setState("open"); continue; } else {
			 * tree1.setState("closed"); }
			 */
			List<TreeDTO> treeList2 = new ArrayList<TreeDTO>();
			tree1.setChildren(treeList2);
			for (int j = 0; j < webXtgncds2.size(); j++) {
				WebXtgncd wb2 = webXtgncds2.get(j);
				TreeDTO tree2 = new TreeDTO();
				tree2.setId(wb2.getId() + "");
				tree2.setText(wb2.getName());
				treeList2.add(tree2);
				webXtgncds3 = this.menuGnService.getMenuGn(wb2.getId());
				/*
				 * if ((webXtgncds3 == null) || (webXtgncds3.size() <= 0)) {
				 * tree2.setState("open"); continue; } else {
				 * tree2.setState("closed"); }
				 */
				List<TreeDTO> treeList3 = new ArrayList<TreeDTO>();
				tree2.setChildren(treeList3);
				for (int m = 0; m < webXtgncds3.size(); m++) {
					TreeDTO tree3 = new TreeDTO();
					WebXtgncd web3 = webXtgncds3.get(m);
					tree3.setId(web3.getId() + "");
					tree3.setText(web3.getName());
					treeList3.add(tree3);
				}
			}

		}
		JSONArray jsonObject = JSONArray.fromObject(treeDTO);
		String menuString = "";
		menuString = jsonObject.toString();
		// menuString = menuString.replace("childNodes", "children");
		this.outJsonStrResult(menuString);
		return NONE;

	}

	public String delmenuyu() throws WebException, Exception {
		Language.setLanguage(this.language);
		// String sql = this.model.getRootid();
		WebXtgncd wx_old = this.menuGnService.loadWebXtgncd(this.model.getRootid());
		List webxtgnList = this.menuGnService.getMenuGn(wx_old.getId());
		if ((webxtgnList != null) && (webxtgnList.size() > 0)) {

			String jsonString = getText("delermsg");
			this.outJsonStrResult(jsonString);
			return NONE;
		}

		this.menuGnService.delWebXtgncd(this.model.getRootid());
		
		// 初始化日志对象
		logItem.setFlag(true);
		logItem.setOperName(wx_old.getName());

		String jsonString = getText("delscmsg");
		this.outJsonStrResult(jsonString);
		return NONE;

	}

	public Object getModel() {
		return model;
	}

	public void setModel(WebXtgncdModel model) {
		this.model = model;
	}

	public void setMenuGnService(MenuGnService menuGnService) {
		this.menuGnService = menuGnService;
	}

}
