package com.hjw.util;
import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

import org.apache.commons.lang.StringUtils;




/**
 * 
 * 文件名： Common.java

 * 功能描述：xxx
 *
 
 * 创建日期： 2012年5月29日
 *
 * Copyright(C) 2012, by 
 *
 * 原始作者: 罗志峰
 *
 */
public class Common  {

	
    public static boolean checkingImg(String coverFileName) {
        if (StringUtils.isBlank(coverFileName)) {
            return false;
        }
        String reg = ".JPG_.GIF_.PNG_.BMP";
        return reg.contains(coverFileName.toUpperCase().trim());
    }
    
    
    /**
     * 
     * Function:根据原路径路径取得缩略图路径
     *          注意 若为绝对路径返回的是绝对路径
     *              若为相对路径返回的也是相对路径
     * 
     * @author 罗志峰 DateTime Nov 27, 2012 11:16:24 AM
     * @param oldImg
     *            /attac ... /*.jpg
     * @return /attac ... /thumbnail_*.jpg
     */
    public static String getThumbnail(String oldImg) {

        return oldImg.substring(0, oldImg.lastIndexOf("/") + 1 ) + oldImg.substring(oldImg.lastIndexOf("/") + 1);
    }
    
    /**
     * 测试端口
     * @param ip
     * @param port
     * @return
     */
    public static String testPort(String ip,int port){
        Socket connect = new Socket();         
        try {  
            connect.connect(new InetSocketAddress(ip.trim(), port),100);                
            boolean res = connect.isConnected();  
            if(res){
                return "1";                
            }else{
                return "0";
            }
           // System.out.println("dddddd" + res);  
        } catch (IOException e) {  
            e.printStackTrace();  
        }finally{  
           try {  
                connect.close();  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
        }  
        return "0";
        
    };
    
    
    public static void delfile(String path){
        
        File file =new File(path);
        file.delete();
    }
    
    
    
    public static void main(String[] args){
        int a =(int) 3.8;
        System.out.println(a);
        
    }
    // 检查是否是图片格式
    public static boolean checkIsImage(String imgStr) {
        boolean flag = false;
        if (imgStr != null) {
            if (imgStr.equalsIgnoreCase(".gif")
                    || imgStr.equalsIgnoreCase(".jpg")
                    || imgStr.equalsIgnoreCase(".jpeg")
                    || imgStr.equalsIgnoreCase(".png")) {
                flag = true;
            }
        }
        return flag;
    }
}
