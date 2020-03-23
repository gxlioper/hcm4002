package com.hjw.util;

import java.io.BufferedOutputStream;
import java.io.UnsupportedEncodingException;
import java.text.MessageFormat;
import java.util.Calendar;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fr.json.JSONObject;

/**
 * 
     * @Title:  火箭蛙体检管理系统   
     * @Package com.hjw.util   导出TXT文件工具类
     * @Description:  
     * @author: Tian     
     * @date:   2018年8月1日 下午4:19:25   
     * @version V2.0.0.0
 */
public class ExportTxtFile {
	
	 
	/* public static void main(String[] args) {
		 //获取有效的数据
	        String list = "i am godtrue 我最帅";//伪代码
	        //将集合转换成字符串
	        //ExportTxtFile.writeToTxt(response,list,"浙二日志信息");
	}*/
	
	

    /**
     * 导出文本文件
     * @param response
     * @param jsonString
     * @param fileName
     */
    public static void writeToTxt(HttpServletResponse response,String expCont,String fileName) {//设置响应的字符集
        response.setCharacterEncoding("utf-8");
        //设置响应内容的类型
        response.setContentType("text/plain");
        //设置文件的名称和格式
        response.addHeader("Content-Disposition","attachment; filename="+ExportTxtFile.genAttachmentFileName(fileName,"第三方日志信息")+".txt");//通过后缀可以下载不同的文件格式
        BufferedOutputStream buff = null;
        ServletOutputStream outStr = null;
        try {
            outStr = response.getOutputStream();
            buff = new BufferedOutputStream(outStr);
            buff.write(delNull(expCont).getBytes("UTF-8"));
            buff.flush();
            buff.close();
        } catch (Exception e) {
            System.out.println("----导出文件文件出错"+e);
        } finally {try {
                buff.close();
                outStr.close();
            } catch (Exception e) {
                System.out.println("----关闭流对象出错 "+e);
            }
        }
    }

    /**
     * 如果字符串对象为 null，则返回空字符串，否则返回去掉字符串前后空格的字符串
     * @param str
     * @return
     */
    public static String delNull(String str) {
            String returnStr="";
            if (StringUtils.isNotBlank(str)) {
                returnStr=str.trim();
            }
            return returnStr;
    }
	
	    
    /**
     * 生成导出附件中文名。应对导出文件中文乱码
     * <p>
     * response.addHeader("Content-Disposition", "attachment; filename=" + cnName);
     * 
     * @param cnName
     * @param defaultName
     * @return
     */
    public static String genAttachmentFileName(String cnName, String defaultName) {
        try {
//	            fileName = URLEncoder.encode(fileName, "UTF-8");
            cnName = new String(cnName.getBytes("gb2312"), "ISO8859-1");
            /*
            if (fileName.length() > 150) {
                fileName = new String( fileName.getBytes("gb2312"), "ISO8859-1" );
            }
            */
        } catch (Exception e) {
            cnName = defaultName;
        }
        return cnName;
    }

}
