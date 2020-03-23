package com.hjw.util;

/*
 *
 * Copyright: Copyright (c) 2005
 * Company: 
 * author 
 * version 2.0
 */

public class UserInfoName {

    /**
     * @author: 
     * @create: Dec 25, 2008
     * @document:
     * @param s
     * @return
     */
    public static String getUserlockName(String isactive)    
    {   
    	String name="";
    	if("Y".equals(isactive.trim()))
    	{
    		name="启用";
    	}else{
    		name="停用";
    	}
    	return name;

    }   
	
    /**
     * @author: 
     * @create: Dec 25, 2008
     * @document:
     * @param s
     * @return
     */
    public static String getUserTypeName(String usertype)    
    {   
    	String name="";
    	if("admin".equals(usertype.trim()))
    	{  name="系统管理员";
    	}else{
    		name="普通用户";
    	}
    	return name;
 
    }   
       
  
}