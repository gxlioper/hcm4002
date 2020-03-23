package com.hjw.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Random;

public class ValidateTime {

  
  public static boolean dateBiJiao(String Date_start,
                                   String Date_end) {
    boolean restemp = true;

    String currentDate = getCurrentTime();
    int[] str_date_start = date2Data(Date_start);
    int[] str_date_end = date2Data(Date_end);
    int[] str_userDate = date2Data(currentDate);

    for (int i = 0; i < 3; i++) {
      if (str_date_start[i] < str_date_end[i]) {
        restemp = true;
        break;
      }
      if (str_date_start[i] > str_date_end[i]) {
        restemp = false;
        break;
      }
    }
    if (restemp) {
      for (int i = 0; i < 3; i++) {
        if (str_userDate[i] > str_date_start[i]) {
          restemp = true;
          break;
        }
        if (str_userDate[i] < str_date_start[i]) {
          restemp = false;
          break;
        }
      }
      if (restemp) {
        for (int i = 0; i < 3; i++) {
          if (str_userDate[i] < str_date_end[i]) {
            restemp = true;
            break;
          }
          if (str_userDate[i] > str_date_end[i]) {
            restemp = false;
            break;
          }
        }
      }
    }
    return restemp;
  }

  public static boolean dateBiJiao(String user_date, String Date_start,
                                   String Date_end) {
    boolean restemp = true;
    int[] str_date_start = date2Data(Date_start);
    int[] str_date_end = date2Data(Date_end);
    int[] str_userDate = date2Data(user_date);

    for (int i = 0; i < 3; i++) {
      if (str_date_start[i] < str_date_end[i]) {
        restemp = true;
        break;
      }
      if (str_date_start[i] > str_date_end[i]) {
        restemp = false;
        break;
      }
    }
    if (restemp) {
      for (int i = 0; i < 3; i++) {
        if (str_userDate[i] > str_date_start[i]) {
          restemp = true;
          break;
        }
        if (str_userDate[i] < str_date_start[i]) {
          restemp = false;
          break;
        }
      }
      if (restemp) {
        for (int i = 0; i < 3; i++) {
          if (str_userDate[i] < str_date_end[i]) {
            restemp = true;
            break;
          }
          if (str_userDate[i] > str_date_end[i]) {
            restemp = false;
            break;
          }
        }
      }
    }
    return restemp;
  }

  
  private static int[] date2Data(String datetmp) {
    int[] tmpdate = new int[3];
    tmpdate[0] = Integer.parseInt(datetmp.trim().substring(0, 4).trim());
    tmpdate[1] = Integer.parseInt(datetmp.trim().substring(4, 6).trim());
    tmpdate[2] = Integer.parseInt(datetmp.trim().substring(6, 8).trim());
    return tmpdate;
  }

  
  public static String getNextYear(String time) {
    int[] timetmp = date2Data(time);
    StringBuffer bs = new StringBuffer();
    bs.append(timetmp[0]);
    bs.append("/");
    bs.append(timetmp[1]);
    bs.append("/");
    bs.append(timetmp[2]);
    Calendar calendar = Calendar.getInstance();
    SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
    calendar.setTime(new Date(bs.toString()));
    calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) + 1);
    calendar.set(Calendar.DATE, calendar.get(Calendar.DAY_OF_MONTH) - 1);

    return formatter.format(calendar.getTime());
  }

  
  public static String getCurrentTime() {
    Date da = new Date();
    SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
    String currentDate = formatter.format(da);
    return currentDate;
  }
  
  public static String getCurrentTime(Date da) {
	    SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
	    String currentDate = formatter.format(da);
	    return currentDate;
	  }
 
  public static String getDayTimeBefor() {
	  Calendar cal = Calendar.getInstance(); 
	  Calendar calClone = (Calendar) cal.clone(); 
	  calClone.setTime(new Date()); 
	  calClone.set(Calendar.DAY_OF_YEAR, calClone.get(Calendar.DAY_OF_YEAR) - 1); 
    SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
    String currentDate = formatter.format(calClone.getTime());
    return currentDate;
  }
  
  public static String getDayTime() {
    Date da = new Date();
    SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
    String currentDate = formatter.format(da);
    return currentDate;
  }

  public static boolean dateSfdq(String cfsj, int yuesu) {
    boolean bl = false;
    int[] timetmp = date2Data(cfsj);
    StringBuffer bs = new StringBuffer();
    bs.append(timetmp[0]);
    bs.append("/");
    bs.append(timetmp[1]);
    bs.append("/");
    bs.append(timetmp[2]);
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(new Date(bs.toString()));
    calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + yuesu);
    SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
    String time = formatter.format(calendar.getTime());
    if (Integer.parseInt(time) <= Integer.parseInt(getCurrentTime())) {
      bl = true;
    }
    return bl;
  }

  public static String getPageFormat(String datetime)
  {
      String fmtstr="";
      if(datetime.length()>=4)
      {
          String nianstr=datetime.substring(0,4);
          fmtstr=nianstr+"年";
          if(datetime.length()>=6)
          {
              String yuestr=datetime.substring(4,6);
              try{
                  yuestr=(new Integer(yuestr)).toString();
              }
              catch (Exception e)
              {
                  yuestr="1";
              }
              fmtstr=fmtstr+yuestr+"月";             
              if(datetime.length()>=8)
              {
                  String ristr=datetime.substring(6,8);
                  try
                  {
                      ristr=(new Integer(ristr)).toString();                      
                  }
                  catch(Exception e)
                  {
                      ristr="1";
                  }
                  fmtstr=fmtstr+ristr+"日";
              }
          }
          
          

      }
      return fmtstr;
  }
  
  public static boolean getComTime(String formca, String toca) {
      Calendar fromret = new GregorianCalendar();
      Calendar toret = new GregorianCalendar();
      Calendar ret = new GregorianCalendar();
      boolean isContain = true;
      try {

          Date da = new Date();
          DateFormat format = new SimpleDateFormat("HH:mm");
          String currentDate = format.format(da);
          fromret.setTime(format.parse(formca)); 
          toret.setTime(format.parse(toca));
          ret.setTime(format.parse(currentDate));
          isContain = ret.after(fromret) && ret.before(toret);
      } catch (Exception ex) {
      }
      return isContain;
  }
  
  /**
	 * Parse a datetime string.
	 * 
	 * @param param
	 *            datetime string, pattern: "yyyy-MM-dd HH:mm:ss".
	 * @return java.util.Date
	 */
	public static Date parse(String param) {
		Date date = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			date = sdf.parse(param);
		} catch (Exception ex) {
		}
		return date;
	}
  
	public static String getDateTime() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dt = sdf.format(new Date());
		return dt;
	}
	
  public static int fdif(String datevar,String datevar2,double vdays)
    {
      String  aa=datevar;
      String yearvar=aa.substring(0,4);
      String monthvar=aa.substring(4,6);
      String dayvar=aa.substring(6,8);
      String bb= yearvar+ "/"+ monthvar + "/"+ dayvar;
      Date dx = new Date(bb);
      
      String aa2=datevar2;
      String yearvar2=aa2.substring(0,4);
      String monthvar2=aa2.substring(4,6);
      String dayvar2=aa2.substring(6,8);
      String bb2= yearvar2+ "/"+ monthvar2 + "/"+ dayvar2;

      double date1 = Double.parseDouble(datevar);
      double date2 = Double.parseDouble(datevar2);
      Date dx2 = new Date(bb2);
          double vvday=Math.floor((dx2.getTime() - dx.getTime()) / (24 * 60 * 60 * 1000));
          if (vvday > vdays)
           {   
            return 1;
           }else if (date1>date2){
             return 2;
           }else
           {
           return 0;
           }
     }
  
  public static boolean fdiftime(String start,int vdays)
  {
	  Calendar cstart = new GregorianCalendar();
      Calendar cend = new GregorianCalendar();
      boolean isContain = true;
      try {

          Date da = new Date();
          DateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
          String send = format.format(da);
          cstart.setTime(format.parse(start)); 
          cend.setTime(format.parse(send));

        double vvday=Math.floor((cend.getTimeInMillis() - cstart.getTimeInMillis()) / (1000));
        if (vvday > vdays)
         {   
          return false;
         }else
         {
         return true;
         }
      }catch(Exception ex)
      {
    	  return false;
      }
   }
	/** *//**
   * 把字节数组转换成16进制字符串
   * @param bArray
   * @return
   */
public static final String bytesToHexString(byte[] bArray) {
   StringBuffer sb = new StringBuffer(bArray.length);
   String sTemp;
   for (int i = 0; i < bArray.length; i++) {
    sTemp = Integer.toHexString(0xFF & bArray[i]);
    if (sTemp.length() < 2)
     sb.append(0);
    sb.append(sTemp.toUpperCase());
   }
   return sb.toString();
}
  public static String getDateTimes() {
      SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
      String dt = sdf.format(new Date());
      return dt;
  }
  
  public static String getDayTimeAll() {
	    Date da = new Date();
	    SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmssSSS");
	    String currentDate = formatter.format(da);
	    return currentDate;
	  }
  
  public static String addDay(String s, int n) { 
        try {   
          SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");  
          Calendar cd = Calendar.getInstance();   
          cd.setTime(sdf.parse(s));   
         cd.add(Calendar.YEAR, n);//增加一天   
         //cd.add(Calendar.MONTH, n);//增加一个月    
        return sdf.format(cd.getTime());   
     } catch (Exception e) {   
          return null;   
       }   
 }   
  
  public static void  main(String[] dd){
		List<String> list=new ArrayList<String>();
		list.add("1");
		list.add("2");
		
  	System.out.println(list.toString());
  }
}
