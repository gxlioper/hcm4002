<%@page import="javax.management.MBeanServerFactory"%>
<%@page import="javax.management.ObjectName"%>
<%@page import="javax.management.MBeanServer"%>
<%@page import="com.mchange.v2.c3p0.ComboPooledDataSource"%>
<%@page import="java.util.*"%>
<%@page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@page import="org.springframework.context.ApplicationContext"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<br>
<%
String portStr = null;
MBeanServer mBeanServer = null;  
ArrayList<MBeanServer> mBeanServers = MBeanServerFactory.findMBeanServer(null);  
if (mBeanServers.size() > 0) {
    mBeanServer = mBeanServers.get(0);  
}  
if (mBeanServer == null) {
    throw new IllegalStateException("没有发现JVM中关联的MBeanServer.");  
}  
Set<ObjectName> objectNames = null;  
try {
    objectNames = mBeanServer.queryNames(new ObjectName("Catalina:type=Connector,*"), null);  
    if (objectNames == null || objectNames.size() <= 0) {  
        throw new IllegalStateException("没有发现JVM中关联的MBeanServer : " + mBeanServer.getDefaultDomain() + " 中的对象名称.");  
    }  
        for (ObjectName objectName : objectNames) {
            String protocol = (String) mBeanServer.getAttribute(objectName, "protocol");
            //out.println(protocol);
            if (protocol.equals("HTTP/1.1") || protocol.equals("org.apache.jk.server.JkCoyoteHandler")) {  
                portStr = String.valueOf(mBeanServer.getAttribute(objectName, "port"));  
                break;  
            }  

     }  
} catch (Throwable e) {
    e.printStackTrace();  
}
out.println("当前tomcat端口>>>"+portStr);  
System.out.println("当前tomcat端口>>>"+portStr);  

%>
</body>
</html>