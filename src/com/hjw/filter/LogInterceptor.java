package com.hjw.filter;


import java.lang.reflect.Field;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import com.hjw.wst.DTO.LogItem;

import com.hjw.wst.domain.WebUserInfo;
import com.hjw.wst.domain.WebXtgnb;
import com.hjw.wst.service.MenuGnService;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ActionProxy;
import com.opensymphony.xwork2.config.entities.ActionConfig;
import com.opensymphony.xwork2.interceptor.Interceptor;


/**
 * 
 * 拦截日志保存
 * 
 * @author skk
 * @version [3.0.0, Nov 15, 2011]
 * @see [相关类/方法]
 * @since [一卡通报表系统]
 */
public class LogInterceptor implements Interceptor {
	private Log log = LogFactory.getLog(LogInterceptor.class);
	private String bizType;
	private String operType;
	private String isActionOr;//多种操作类型是否为同一action，1为是
	private MenuGnService menuGnService;

	public void destroy() {

	}

	public void init() {

	}

	public String intercept(ActionInvocation invocation) throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		Map session = ActionContext.getContext().getSession();
		WebUserInfo user = (WebUserInfo)session.get("userinfo");
		
		String rs = invocation.invoke();
		Object actionObj = invocation.getAction();
		Field field = actionObj.getClass().getDeclaredField("logItem");
		field.setAccessible(true);
		LogItem logItem= (LogItem)field.get(actionObj);
		if(user != null && logItem!=null && logItem.isFlag()){
			
			StringBuffer explain1 = new StringBuffer();
			ActionProxy proxy = invocation.getProxy();
			ActionConfig config = proxy.getConfig();
			//获取action类名
			//String classname = config.getClassName() ; 
			String namespace = proxy.getNamespace();
			String actName = proxy.getActionName();
			String actionPath = "";
			if(!"/".equals(namespace)&&!"".equals(namespace)){
			    namespace = namespace.substring(1);
				actionPath = namespace+"/"+actName+".action";
			}else{
				actionPath = actName+".action";
			}
			WebXtgnb webXtgn = menuGnService.getXtgnByUrl(actionPath);
			String actionName = "";
			if(webXtgn != null)
				actionName = webXtgn.getName();
			
			
			
			explain1.append(user.getChi_Name()).append(actionName).append(logItem.getOperName());
			
		}
		return rs;
	}

	public void setBizType(String bizType) {
		this.bizType = bizType;
	}

	public void setOperType(String operType) {
		this.operType = operType;
	}

	

	public String getIsActionOr() {
		return isActionOr;
	}

	public void setIsActionOr(String isActionOr) {
		this.isActionOr = isActionOr;
	}

	public MenuGnService getMenuGnService() {
		return menuGnService;
	}

	public void setMenuGnService(MenuGnService menuGnService) {
		this.menuGnService = menuGnService;
	}

}
