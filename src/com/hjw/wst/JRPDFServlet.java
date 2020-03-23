package com.hjw.wst;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.HashMap;
 
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.synjones.framework.persistence.JdbcQueryManager;

import net.sf.jasperreports.engine.JasperRunManager;
 
public class JRPDFServlet extends HttpServlet 
{
    private static final long serialVersionUID = 1L;
 
    public static Connection getConnection(){
        try {
            String url ="jdbc:sqlserver://localhost:1433;DatabaseName=hjwpeis4.0.0.2";
            String userName = "root";
            String password = "HUOjianwa010";
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection connection = DriverManager.getConnection(url,userName,password);
            return connection;
        }catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                 e.printStackTrace();
             }
        return null;
    }
    
    public void service(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException 
    {
    	WebApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(this.getServletContext());
    	JdbcQueryManager jdbcQueryManager = (JdbcQueryManager) context.getBean("jdbcQueryManager");    	
        Connection connection =null;
        try
        {
        	connection =jdbcQueryManager.getConnection();
            //据据jasper文件生成JasperPrint对象
            ServletContext contexts = this.getServletConfig().getServletContext();
            String fileName = request.getParameter("fileName");//ireport编译文件：*.jasper（由模板文件*.jrxml文件编译生成）
            File reportFile = new File(contexts.getRealPath("/WEB-INF/jaspers/"+fileName));
            HashMap<String,Object> parameters = new HashMap<String, Object>();//给报表模板文件传参
 
            //得到枚举类型的参数名称，参数名称若有重复的只能得到第一个--获取页面传来的参数，和模板中文件的sql参数名称一一对应
            Enumeration temp = request.getParameterNames();
               while (temp.hasMoreElements()) 
               {
                String paramName = (String) temp.nextElement();
                String paramValue = request.getParameter(paramName);
                parameters.put(paramName, paramValue);
               }
            byte[] bytes = JasperRunManager.runReportToPdf(reportFile.getPath(), parameters,connection);
            response.setContentType("application/pdf");
            response.setContentLength(bytes.length);
            ServletOutputStream out = response.getOutputStream();
            out.write(bytes, 0, bytes.length);
            out.flush();
            out.close();
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }finally {
        	try{
        		if (connection!=null){
        			connection.close();
        		}
        	}catch(Exception ex){
        		
        	}
        }
    }
}