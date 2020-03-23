package com.hjw.wst.service;

import java.util.List;

import com.hjw.wst.domain.WebXtgnb;
import com.hjw.wst.domain.WebXtgncd;
import com.synjones.framework.exception.ServiceException;

/*
 * Title:       金龙卡金融化一卡通网站查询、触摸屏查询子系统
 * Description: 新中新集团
 * 创建日期：     Jun 8, 200710:48:46 AM
 * 项目名称：     webschoolcard
 * 所在包名：     com.synjones.wst.service
 *
 * Copyright:   Copyright (c) 2007－2008
 * Company:     syntongs
 * author       yangm
 * version      2.3.1.0
 */

public interface LoginService {


   public String getUnifiedauthUrl()throws ServiceException;

   
   /**
    * 获取系统功能
   * @param saction
   * @return
   * @throws ServiceException
   * @see [类、类#方法、类#成员]
    */
	public WebXtgnb getWebXtgnb(String saction,String centernum) throws ServiceException;
	
	public String getRolegn(int role_id) throws ServiceException;
	
	public boolean getXtgncdByGnid(String gnid,String usertype) throws ServiceException;
	
	public WebXtgncd getXtgncdByGnid(String gnid) throws ServiceException;
	
	public WebXtgncd getXtgncdById(String id) throws ServiceException;

}
