package com.hjw.filter;

import java.util.Map;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.axis.utils.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import com.hjw.wst.DTO.JobDTO;
import com.hjw.wst.DTO.UserDTO;
import com.hjw.wst.service.MenuGnService;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;

/**
 * 
 * 拦截获取资源
 * 
 * @author skk
 * @version [3.0.0, Nov 15, 2011]
 * @see [相关类/方法]
 * @since [一卡通网站查询系统]
 */
public class ResInterceptor implements Interceptor {
	private Log log = LogFactory.getLog(ResInterceptor.class);
	private MenuGnService menuGnService;
	private String zyid;

	public void destroy() {

	}

	public void init() {

	}

	public String intercept(ActionInvocation invocation) throws Exception {
		
		return invocation.invoke();
	}

	public MenuGnService getMenuGnService() {
		return menuGnService;
	}

	public void setMenuGnService(MenuGnService menuGnService) {
		this.menuGnService = menuGnService;
	}

	public String getZyid() {
		return zyid;
	}

	public void setZyid(String zyid) {
		this.zyid = zyid;
	}

}
