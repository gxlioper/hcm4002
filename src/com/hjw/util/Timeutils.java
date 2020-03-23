package com.hjw.util;

import java.text.*;
import java.util.*;

import com.hjw.wst.domain.sysSurvey;

public class Timeutils
{

    public Timeutils()
    {
    }
    
    /**
     * 返回当前时间字符串例如： 2013-08-14 11:48:17
     * {方法功能中文描述}
     * 
     * @return
     * @author: 路志友
     */
    public static String getNowDate()
    {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return formatter.format(currentTime);
    }
  
    public static Date getNowDateShort()
    {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = formatter.format(currentTime);
        ParsePosition pos = new ParsePosition(8);
        Date currentTime_2 = formatter.parse(dateString, pos);
        return currentTime_2;
    }

    /**
     * 返回字符串 去掉时分秒例如：2013-08-14
     * {方法功能中文描述}
     * 
     * @return
     * @author: 路志友
     */
    public static String getStringDateShort()
    {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = formatter.format(currentTime);
        return dateString;
    }
    /**
     *返回当前时间 字符串 例如：20130814
     * {方法功能中文描述}
     * 
     * @return
     * @author: 路志友
     */
    public static String getStringFileDateShort()
    {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        String dateString = formatter.format(currentTime);
        return dateString;
    }
    /**
     * 返回当前时间 字符串时分秒 例如：11:54:07
     * {方法功能中文描述}
     * 
     * @return
     * @author: 路志友
     */
    public static String getTimeShort()
    {
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        Date currentTime = new Date();
        String dateString = formatter.format(currentTime);
        return dateString;
    }
    /**
     * 获得当前时间 加上一天 例如 传入"2012-12-11" 输出"2012-12-12"
     * {方法功能中文描述}
     * 
     * @param arg
     * @return
     * @throws ParseException
     * @author: 路志友
     */
   public static String getTimeAddOne(String arg) throws ParseException{
	   
       SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
       Long ms =formatter.parse(arg).getTime();
       long one = 24*60*60*1000;
       Long msl =ms+one;
       Date date = new Date(msl);
       String addone =formatter.format(date);
       return addone;
   }
     /**
      * 将正常字符床日期 转化为Tue Dec 11 00:00:00 PST 2012
      * {方法功能中文描述}
      * 
      * @param strDate
      * @return
      * @author: 路志友
      */
    public static Date strToDateLong(String strDate)
    {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        ParsePosition pos = new ParsePosition(0);
        Date strtodate = formatter.parse(strDate, pos);
        return strtodate;
    }
    
    public static Date strToDateLong2(String strDate)
    {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        ParsePosition pos = new ParsePosition(0);
        Date strtodate = formatter.parse(strDate, pos);
        return strtodate;
    }
    /**
     * 返回当前时间的时分秒 例如16:00:00
     * {方法功能中文描述}
     * 
     * @param dateDate
     * @return
     * @author: 路志友
     */
    public static String timeToStrLong(Date dateDate)
    {
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        String dateString = formatter.format(dateDate);
        return dateString;
    }
    /**
     * 返回时间的 1969-12-31 16:00:00
     * {方法功能中文描述}
     * 
     * @param dateDate
     * @return
     * @author: 路志友
     */
    public static String dateToStrLong(Date dateDate)
    {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(dateDate);
        return dateString;
    }
    /**
     * 返回时间 1969-12-31
     * {方法功能中文描述}
     * 
     * @param dateDate
     * @return
     * @author: 路志友
     */
    public static String dateToStr(Date dateDate)
    {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = formatter.format(dateDate);
        return dateString;
    }
/**
 * yyMMdd转化成yy-MM-dd
 * {方法功能中文描述}
 * 
 * @param str
 * @return
 * @author: 路志友
 * @throws ParseException 
 */
    public static String toformat(String str) throws ParseException{
        SimpleDateFormat sf1 = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat sf2 =new SimpleDateFormat("yyyy-MM-dd");
        String sfstr = "";
            sfstr = sf2.format(sf1.parse(str));
       return sfstr;
        
    }
    

    /**
     * 返回时间格式  Tue Dec 11 00:00:00 PST 2012
     * {方法功能中文描述}
     * 
     * @param strDate
     * @return
     * @author: 路志友
     */
    public static Date strToDate(String strDate)
    {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        ParsePosition pos = new ParsePosition(0);
        Date strtodate = formatter.parse(strDate, pos);
        return strtodate;
    }
    /**
     * 返回当前date类型日期
     * {方法功能中文描述}
     * 
     * @return
     * @author: 路志友
     */
    public static Date getNow()
    {
        Date currentTime = new Date();
        return currentTime;
    }
    /**
     * 返回类型 Sun Jan 05 05:07:43 PST 48453
     * {方法功能中文描述}
     * 
     * @param day
     * @return
     * @author: 路志友
     */
    public static Date getLastDate(long day)
    {
        Date date = new Date();
        long date_3_hm = date.getTime() - 0x74bad00L * day;
        Date date_3_hm_date = new Date(date_3_hm);
        return date_3_hm_date;
    }
    /**
     * 返回类型 例如：20130814140823
     * {方法功能中文描述}
     * 
     * @return
     * @author: 路志友
     */
    public static String getStringToday()
    {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String dateString = formatter.format(currentTime);
        return dateString;
    }
    /**
     * 返回 当前时  例如14
     * {方法功能中文描述}
     * 
     * @return
     * @author: 路志友
     */
    public static String getHour()
    {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(currentTime);
        String hour = dateString.substring(11, 13);
        return hour;
    }
    /**
     * 返回分  例如10
     * {方法功能中文描述}
     * 
     * @return
     * @author: 路志友
     */
    public static String getTime()
    {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(currentTime);
        String min = dateString.substring(14, 16);
        return min;
    }
     /**
      * 返回   例如2012-12-11 00:00:00
      * {方法功能中文描述}
      * 
      * @param sformat
      * @return
      * @author: 路志友
      */
    public static String getUserDate(String sformat)
    {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat(sformat);
        String dateString = formatter.format(currentTime);
        return dateString;
    }
     /**
      * 
      * {方法功能中文描述}
      * 
      * @param st1
      * @param st2
      * @return
      * @author: 路志友
      */
    public static String getTwoHour(String st1, String st2)
    {
        String kk[] = (String[])null;
        String jj[] = (String[])null;
        kk = st1.split(":");
        jj = st2.split(":");
        if(Integer.parseInt(kk[0]) < Integer.parseInt(jj[0]))
            return "0";
        double y = Double.parseDouble(kk[0]) + Double.parseDouble(kk[1]) / 60D;
        double u = Double.parseDouble(jj[0]) + Double.parseDouble(jj[1]) / 60D;
        if(y - u > 0.0D)
            return (new StringBuilder(String.valueOf(y - u))).toString();
        else
            return "0";
    }

    public static String getTwoDay(String sj1, String sj2)
    {
        SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
        long day = 0L;
        try
        {
            Date date = myFormatter.parse(sj1);
            Date mydate = myFormatter.parse(sj2);
            day = (date.getTime() - mydate.getTime()) / 0x5265c00L;
        }
        catch(Exception e)
        {
            return "";
        }
        return (new StringBuilder(String.valueOf(day))).toString();
    }
    
    /**
     * 
         * @Title: getDayForOld   
         * @Description: 计算年龄   
         * @param: @param startdate
         * @param: @return      
         * @return: int      
         * @throws
     */
	public static int getDayForOld(String birthday) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Date date = null;
		try {
			date = sdf.parse(birthday);
		} catch (ParseException e) {
			e.printStackTrace();
		}
//		String myDate = getStringFileDateShort();
//		int month = Integer.valueOf(myDate.substring(4, 6)).intValue();
//		int day = Integer.valueOf(myDate.substring(6, 8)).intValue();
//		int year = Integer.valueOf(myDate.substring(0, 4)).intValue();
//		int age = year - Integer.valueOf(birthday.substring(0, 4)).intValue() - 1;
//		if (Integer.valueOf(birthday.substring(4,6)).intValue() < month
//				|| (Integer.valueOf(birthday.substring(4,6)).intValue() == month&& Integer.valueOf(birthday.substring(6,8)).intValue() >= day)) {
//			age++;
//		}
//		return age;
		Calendar cal = Calendar.getInstance();

    	if (cal.before(date)) {
    		throw new IllegalArgumentException(
    				"The birthDay is before Now.It's unbelievable!");
    	}

    	int yearNow = cal.get(Calendar.YEAR);
    	int monthNow = cal.get(Calendar.MONTH) + 1;
    	int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);

    	cal.setTime(date);
    	int yearBirth = cal.get(Calendar.YEAR);
    	int monthBirth = cal.get(Calendar.MONTH) + 1;
    	int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);

    	int age = yearNow - yearBirth;

    	if (monthNow <= monthBirth) {
    		if (monthNow == monthBirth) {
    			// monthNow==monthBirth 
    			if (dayOfMonthNow < dayOfMonthBirth) {
    				age--;
    			}
    		} else {
    			// monthNow>monthBirth 
    			age--;
    		}
    	}
    	return age;
	}
    
   

    public static String getPreTime(String sj1, String jj)
    {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String mydate1 = "";
        try
        {
            Date date1 = format.parse(sj1);
            long Time = date1.getTime() / 1000L + (long)(Integer.parseInt(jj) * 60);
            date1.setTime(Time * 1000L);
            mydate1 = format.format(date1);
        }
        catch(Exception exception) { }
        return mydate1;
    }

    public static String getNextDay(String nowdate, String delay)
    {
        String mdate;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        mdate = "";
        Date d = strToDate(nowdate);
        long myTime = d.getTime() / 1000L + (long)(Integer.parseInt(delay) * 24 * 60 * 60);
        d.setTime(myTime * 1000L);
        mdate = format.format(d);
        return mdate;
   
    }

    public static boolean isLeapYear(String ddate)
    {
        Date d = strToDate(ddate);
        GregorianCalendar gc = (GregorianCalendar)Calendar.getInstance();
        gc.setTime(d);
        int year = gc.get(1);
        if(year % 400 == 0)
            return true;
        if(year % 4 == 0)
            return year % 100 != 0;
        else
            return false;
    }


    public static String getEDate(String str)
    {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        ParsePosition pos = new ParsePosition(0);
        Date strtodate = formatter.parse(str, pos);
        String j = strtodate.toString();
        String k[] = j.split(" ");
        return (new StringBuilder(String.valueOf(k[2]))).append(k[1].toUpperCase()).append(k[5].substring(2, 4)).toString();
    }

    public static String getEndDateOfMonth(String dat)
    {
        String str = dat.substring(0, 8);
        String month = dat.substring(5, 7);
        int mon = Integer.parseInt(month);
        if(mon == 1 || mon == 3 || mon == 5 || mon == 7 || mon == 8 || mon == 10 || mon == 12)
            str = (new StringBuilder(String.valueOf(str))).append("31").toString();
        else
        if(mon == 4 || mon == 6 || mon == 9 || mon == 11)
            str = (new StringBuilder(String.valueOf(str))).append("30").toString();
        else
        if(isLeapYear(dat))
            str = (new StringBuilder(String.valueOf(str))).append("29").toString();
        else
            str = (new StringBuilder(String.valueOf(str))).append("28").toString();
        return str;
    }

    public static boolean isSameWeekDates(Date date1, Date date2)
    {
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.setTime(date1);
        cal2.setTime(date2);
        int subYear = cal1.get(1) - cal2.get(1);
        if(subYear == 0)
        {
            if(cal1.get(3) == cal2.get(3))
                return true;
        } else
        if(1 == subYear && 11 == cal2.get(2))
        {
            if(cal1.get(3) == cal2.get(3))
                return true;
        } else
        if(-1 == subYear && 11 == cal1.get(2) && cal1.get(3) == cal2.get(3))
            return true;
        return false;
    }

    public static String getSeqWeek()
    {
        Calendar c = Calendar.getInstance(Locale.CHINA);
        String week = Integer.toString(c.get(3));
        if(week.length() == 1)
            week = (new StringBuilder("0")).append(week).toString();
        String year = Integer.toString(c.get(1));
        return (new StringBuilder(String.valueOf(year))).append(week).toString();
    }

    public static String getWeek(String sdate, String num)
    {
        Date dd = strToDate(sdate);
        Calendar c = Calendar.getInstance();
        c.setTime(dd);
        if(num.equals("1"))
            c.set(7, 2);
        else
        if(num.equals("2"))
            c.set(7, 3);
        else
        if(num.equals("3"))
            c.set(7, 4);
        else
        if(num.equals("4"))
            c.set(7, 5);
        else
        if(num.equals("5"))
            c.set(7, 6);
        else
        if(num.equals("6"))
            c.set(7, 7);
        else
        if(num.equals("0"))
            c.set(7, 1);
        return (new SimpleDateFormat("yyyy-MM-dd")).format(c.getTime());
    }

    public static String getWeek(String sdate)
    {
        Date date = strToDate(sdate);
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return (new SimpleDateFormat("EEEE")).format(c.getTime());
    }

    public static String getWeekStr(String sdate)
    {
        String str = "";
        str = getWeek(sdate);
        if("1".equals(str))
            str = "\u661F\u671F\u65E5";
        else
        if("2".equals(str))
            str = "\u661F\u671F\u4E00";
        else
        if("3".equals(str))
            str = "\u661F\u671F\u4E8C";
        else
        if("4".equals(str))
            str = "\u661F\u671F\u4E09";
        else
        if("5".equals(str))
            str = "\u661F\u671F\u56DB";
        else
        if("6".equals(str))
            str = "\u661F\u671F\u4E94";
        else
        if("7".equals(str))
            str = "\u661F\u671F\u516D";
        return str;
    }

    public static long getDays(String date1, String date2)
    {
        if(date1 == null || date1.equals(""))
            return 0L;
        if(date2 == null || date2.equals(""))
            return 0L;
        SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        Date mydate = null;
        try
        {
            date = myFormatter.parse(date1);
            mydate = myFormatter.parse(date2);
        }
        catch(Exception exception) { }
        long day = (date.getTime() - mydate.getTime()) / 0x5265c00L;
        return day;
    }

    public static String getNowMonth(String sdate)
    {
        sdate = (new StringBuilder(String.valueOf(sdate.substring(0, 8)))).append("01").toString();
        Date date = strToDate(sdate);
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int u = c.get(7);
        String newday = getNextDay(sdate, (new StringBuilder(String.valueOf(1 - u))).toString());
        return newday;
    }

    public static String getNo(int k)
    {
        return (new StringBuilder(String.valueOf(getUserDate("yyyyMMddhhmmss")))).append(getRandom(k)).toString();
    }

    public static String getRandom(int i)
    {
        Random jjj = new Random();
        if(i == 0)
            return "";
        String jj = "";
        for(int k = 0; k < i; k++)
            jj = (new StringBuilder(String.valueOf(jj))).append(jjj.nextInt(9)).toString();

        return jj;
    }

    public static boolean RightDate(String date)
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        if(date == null)
            return false;
        if(date.length() > 10)
            sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        else
            sdf = new SimpleDateFormat("yyyy-MM-dd");
        try
        {
            sdf.parse(date);
        }
        catch(ParseException pe)
        {
            return false;
        }
        return true;
    }

    public static String getFileData()
    {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		Random random = new Random(System. currentTimeMillis());
        return formatter.format(currentTime)+String.valueOf(random.nextInt(100));
    }

    public static long getTimenow()
    {
        return System.currentTimeMillis();
    }

    public static boolean isDateBefore(String date1, String date2) throws ParseException
    {
        DateFormat df = DateFormat.getDateTimeInstance();
        return df.parse(date1).before(df.parse(date2));
   

    }


    public static String get(String s){
    	return s.substring(s.lastIndexOf("."));
    }
   
    
    public static String getStringYear()
    {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy");
        String dateString = formatter.format(currentTime);
        return dateString;
    }
    
    public static String getStringMonth()
    {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("MM");
        String dateString = formatter.format(currentTime);
        return dateString;
    }
    
	public static String DateToString(Date date, String format) {
		// TODO Auto-generated method stub
             SimpleDateFormat formatter = new SimpleDateFormat(format);
	        return formatter.format(date);
	
	}
	 public static String strDateCn()
	    {
		 Date currentTime = new Date();
		 	Calendar cl = Calendar.getInstance();
	        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日");
	        String dateString = formatter.format(currentTime);
	        String realdate = dateString +"  "+ getWeekDay(cl);
	        return realdate;
	    }
	 
	 
	 private static String getWeekDay(Calendar c){		   
		   if(c == null){
			   c = Calendar.getInstance();
		   }		  
		   if(Calendar.MONDAY == c.get(Calendar.DAY_OF_WEEK)){
		    return "星期一";
		   }
		   if(Calendar.TUESDAY == c.get(Calendar.DAY_OF_WEEK)){
		    return "星期二";
		   }
		   if(Calendar.WEDNESDAY == c.get(Calendar.DAY_OF_WEEK)){
		    return "星期三";
		   }
		   if(Calendar.THURSDAY == c.get(Calendar.DAY_OF_WEEK)){
		    return "星期四";
		   }
		   if(Calendar.FRIDAY == c.get(Calendar.DAY_OF_WEEK)){
		    return "星期五";
		   }
		   if(Calendar.SATURDAY == c.get(Calendar.DAY_OF_WEEK)){
		    return "星期六";
		   }
		   if(Calendar.SUNDAY == c.get(Calendar.DAY_OF_WEEK)){
		    return "星期日";
		   }
		  
		   return "星期一";
		}
	 /**
	  * 
	  * 功能描述： oneDate>twoDate=1  ,oneDate<twoDate=-1、oneDate=twoDate=0  
	  * 格式：yyyy-MM-dd
	  * 
	  * @param oneDate  
	  * @param twoDate
	  * @return
	  *
	  * @author <a href="wanghc@landagr.com">王海朝</a>
	  *
	  * @since Jun 5, 2012
	  *
	  * @update:[变更日期YYYY-MM-DD][更改人姓名][变更描述]
	  */
	 public static int compare_date(String oneDate, String twoDate) 
	 {           
	     DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	     try {
	            java.util.Date d1 = df.parse(oneDate);
	            java.util.Date d2 = df.parse(twoDate);
	            if (d1.getTime() > d2.getTime())
	               {
	                    
	                     return 1;
	               }
	            else if (d1.getTime() < d2.getTime()) 
	              {
	                   
	                     return -1;
	              } 
	            else 
	            {
	                     return 0;
	             }
	     } catch (Exception exception) {
	             exception.printStackTrace();
	     }
	     return 0;

	   }
	 
	 
	 
	 /**
	  * 当前日期与之前时间间隔
	  * {方法功能中文描述}
	  * 
	  * @param args
	  * @throws ParseException
	  * @author: 路志友
	  */
	 public static Long secode(String time){

	     SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	     Date currentTime=new Date();
	     //将截取到的时间字符串转化为时间格式的字符串
	     Date beginTime;
        try {
            beginTime = sdf.parse(time);
            //默认为毫秒，除以1000是为了转换成秒
            long interval=(currentTime.getTime()-beginTime.getTime())/1000;//秒
            long day=interval/(24*3600);//天
            long hour=interval%(24*3600)/3600;//小时
            long minute=interval%3600/60;//分钟
            long second=interval%60;//秒
            System.out.println("两个时间相差："+day+"天"+hour+"小时"+minute+"分"+second+"秒");
            return interval;
            
        }
        catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
	    
         return null;
	    }

  /**
   * 两个时间之差
   * {方法功能中文描述}
   * 
   * @param time1
   * @param time2
   * @return
   * @author: 路志友
   */
	 public static Long secode1(String time1,String time2){
	 
	 SimpleDateFormat dfs = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
     java.util.Date begin;
    try {
        begin = dfs.parse(time1);
        java.util.Date end = dfs.parse(time2);
        long between=(end.getTime()-begin.getTime())/1000;//除以1000是为了转换成秒


        long day1=between/(24*3600);
        long hour1=between%(24*3600)/3600;
        long minute1=between%3600/60;
        long second1=between%60/60;
        System.out.println(""+day1+"天"+hour1+"小时"+minute1+"分"+second1+"秒");
        return between;
    }
    catch (ParseException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }
     
    return null;
}
	 
	    public static void main(String[] aa) throws ParseException{
	        
	        int sfstr = Timeutils.getDayForOld("19830702");
	       System.out.println(sfstr); 
	    }
	 
}