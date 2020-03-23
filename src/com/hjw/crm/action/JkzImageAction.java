package com.hjw.crm.action;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class JkzImageAction extends HttpServlet{
	private static final long serialVersionUID = -1L;  
	  
    public void doGet(HttpServletRequest request, HttpServletResponse response)  
            throws ServletException, IOException {  
        this.doPost(request, response);  
    }  
  
    public void doPost(HttpServletRequest request, HttpServletResponse response)  
            throws ServletException, IOException {  
        //读取本地图片输入流 
    	try {
    		FileInputStream inputStream = new FileInputStream(request.getParameter("pic"));  
            int i = inputStream.available();  
            //byte数组用于存放图片字节数据  
            byte[] buff = new byte[i];  
            inputStream.read(buff);  
            //记得关闭输入流  
            inputStream.close();  
            //设置发送到客户端的响应内容类型  
            response.setContentType("image/*");  
            OutputStream out = response.getOutputStream();  
            out.write(buff);  
            //关闭响应输出流  
            out.close();  	
		} catch (Exception e) {
			
		}
    }  
}
