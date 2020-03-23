package com.hjw.util;

/**
 */
public interface CommSet {
    /**
     */
    public static final int page_PageSize =17;
    
    /**
     */
    public static final int menu_PageSize = 8;
    /**
     */
    public static final int accinfo_PageSize = 9;
    /**
     */
    public static final int feeinfo_PageSize = 9;
    
    /**
     */
    public static final int homelm1_PageSize = 5;
    /**
     */
    public static final int homelm2_PageSize =8;

    /**
     */
    public static final int homelm3_PageSize =5;

    /**
     */
    public static final int homemarquee_PageSize = 10;

    public static final int timeout = 3;
    
    public static final int transferTime=20;
    
    public static final String cofigFileName = "/WEB-INF/config/pro.properties";
    public static final String tempConfigFileName="/install/db/pro.properties";
    public static final String key="syn123to";
    public static final String iniFileName = "pos.ini";
    public static final String initmpFileName = "/install/db/pos.ini";
    public static final String dbFrom = "/install/db/SecondVersion-All-2.6.1.0.sql";
    
    //客户端数据库配置路径
    public static final String ClientFileName="/card/WEB-INF/config/pro.properties";
    public static final String tempClientFileName="/install/db/client/pro.properties";
   
  public static final int webservice_timeout=10000;//5秒
    
    public static final String lis_sample="sample";
}
