package com.hjw.config;

import javax.servlet.ServletContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/*
 * Title:圈存查询系统
 * Description: 新中新集团
 * 创建日期：May 30, 2006
 * 项目名称：screenObject
 * 所在包名：security.context
 *
 * Copyright: Copyright (c) 2005
 * Company: synjones
 * author yangm
 * version 2.0
 */
    public class SecurityContext {
        private static Log log = LogFactory.getLog(SecurityContext.class);
            
        //初始化spring context
        public static ApplicationContext getContext(ServletContext servletContext){
            ApplicationContext applicationContext=null;
            applicationContext=new ClassPathXmlApplicationContext("applicationContext-security.xml");
            log.debug("加载权限配置文件.");
            return applicationContext;
        }
        
        //获取安全配置对象
        public static SecurityConfig getSecurityConfig(ServletContext servletContext) {
          return (SecurityConfig)getContext(servletContext).getBean("securityConfig");
          }
}
