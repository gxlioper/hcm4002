package com.hjw.util;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;



/*
 *
 * Copyright: Copyright (c) 2005
 * Company: 
 * author yangm
 * version 2.0
 */

public class FormatUtil {
    public FormatUtil() {
    }
    
    /**
     * base64编码
     * @author: yangm
     * @create: Dec 25, 2008
     * @document:
     * @param s
     * @return
     */
    public static String base64Encode(byte[] s)    
    {   
        if (s == null)   
            return null;   
        BASE64Encoder b = new BASE64Encoder();   
        return b.encode(s);   
    }   
    
    /**
     * 两个Double数相除，并保留scale位小数
     * @param v1
     * @param v2
     * @param scale
     * @return Double
    */
	 public static Double div(Double v1,Double v2,int scale){
	        if(scale<0){
	            throw new IllegalArgumentException(
	            "The scale must be a positive integer or zero");
	         }
	        if(v2==0){
	        	return 0*1.0;
	        }else{
	         BigDecimal b1 = new BigDecimal(v1.toString());
	         BigDecimal b2 = new BigDecimal(v2.toString());
	        return b1.divide(b2,scale,BigDecimal.ROUND_HALF_UP).doubleValue();
	        }
	     }

       
    /**
     * base64解码
     * @author: yangm
     * @create: Dec 25, 2008
     * @document:
     * @param s
     * @return
     * @throws IOException
     */
    public static byte[] base64Decode(String s) throws IOException    
    {   
        if (s == null)   
        {   
           return null;   
        }   
        BASE64Decoder decoder = new BASE64Decoder();   
        byte[] b = decoder.decodeBuffer(s);   
        return b;   
    }   

    public static String getDecimalFormat(double d) {
        DecimalFormat fmt = new DecimalFormat("##,###,###,###,##0.00");
        return fmt.format(d);
    }

    public static String getIdFormat(long l) {
        /*
         * String lStr = new String().valueOf(l); String tmpS = new
         * String("000000000000").substring(0,(12 - lStr.length())); return tmpS +
         * lStr;
         */
        DecimalFormat fmt = new DecimalFormat("000000000000");
        return fmt.format(l);

    }

    public static String[] split(String str, String flagstr) {
        String[] newStr = new String[10];
        if (str == null) {
            newStr = null;
        } else if (str.indexOf(flagstr) < 0) {
            newStr[0] = str;
        } else {
            int i = 0;
            while (str.indexOf(flagstr) >= 0) {
                int sint = str.indexOf(flagstr);
                newStr[i] = str.substring(0, sint);
                str = str.substring(sint + 1, str.length());
                i++;
            }
            newStr[i] = str;

        }

        return newStr;
    }

    /**
     * 
     * @author: yangm
     * @create: Jul 7, 2006
     * @document:
     * @param money
     * @return
     */
    public static String getStrMoney(long money) {
        String smoney = "0";
        if (money != 0) {
            smoney = money + "";
            if (money >= 100) {
                smoney = smoney.substring(0, smoney.length() - 2)
                        + "."
                        + smoney
                                .substring(smoney.length() - 2, smoney.length());
            } else if (money < 10) {
                smoney = "0.0" + money;
            } else {
                smoney = "0." + money;
            }
        }
        return smoney;
    }

    /**
     * 
     * @author: yangm
     * @create: Jul 7, 2006
     * @document:
     * @param money
     * @return
     */
    public static String getStringMoney(String smoney) {
        if ((smoney != null) && (!smoney.equals(""))) {
            if (smoney.length() > 2) {
                smoney = smoney.substring(0, smoney.length() - 2)
                        + "."
                        + smoney
                                .substring(smoney.length() - 2, smoney.length());
            } else if (smoney.length() == 2) {
                smoney = "0." + smoney;
            } else if (smoney.length() == 1) {
                smoney = "0.0" + smoney;
            }
        }
        return smoney;
    }

    /**
     * 
     * @author: yangm
     * @create: Jul 7, 2006
     * @document:
     * @param money
     * @return
     */
    public static String getStringMoneys(String money) {

        if ((money != null) && (!money.equals(""))) {
            if (money.indexOf('.') == -1) {
                money = money + "00";
            } else if (money.substring(money.indexOf('.') + 1, money.length())
                    .length() == 1) {
                money = money.substring(0, money.indexOf('.'))
                        + money.substring(money.indexOf('.') + 1, money
                                .indexOf('.') + 2);
                money = money + "0";
            } else {
                money = money.substring(0, money.indexOf('.'))
                        + money.substring(money.indexOf('.') + 1, money
                                .indexOf('.') + 3);
            }
        }
        return money;
    }

    /**
     * 
     * @author: yangm
     * @create: Jul 7, 2006
     * @document:
     * @param money
     * @return
     */
    public static long getLongMoney(String money) {
        long lmoney = 0;
        if (money != null) {
            if (money.indexOf('.') == -1) {
                money = money + "00";
            } else if (money.substring(money.indexOf('.') + 1, money.length())
                    .length() == 1) {
                money = money.substring(0, money.indexOf('.'))
                        + money.substring(money.indexOf('.') + 1, money
                                .indexOf('.') + 2);
                money = money + "0";
            } else {
                money = money.substring(0, money.indexOf('.'))
                        + money.substring(money.indexOf('.') + 1, money
                                .indexOf('.') + 3);
            }

            lmoney = Long.valueOf(money.trim()).longValue();
        }
        return lmoney;
    }

    /**
     * 
     * @author: yangm
     * @create: Aug 16, 2006
     * @document:
     * @param amt
     * @return
     */
    public static String getStringAmt(String vqoAmts) {
        String amt = "";
        if (vqoAmts != null) {
            boolean flag = false;
            for (int i = 0; i < vqoAmts.length(); i++) {
                if (flag) {
                    amt = amt + vqoAmts.substring(i, i + 1);
                } else {
                    if (!vqoAmts.substring(i, i + 1).equals("0")) {
                        amt = amt + vqoAmts.substring(i, i + 1);
                        flag = true;
                    }
                }
            }
        }
        return amt;
    }

    public static String contralStr(String str) {
        if (str != null) {
            str = str.trim();
            if (str.length() > 8) {
                return str.substring(0, 8) + "..";
            } else {
                return str;
            }
        } else {
            return null;
        }
    }

    /**
     *
     * @param date
     * @return
     */

    public static String changeFormat(String date) {
      return date.substring(0, 4) + "-" + date.substring(4, 6) + "-" +
          date.substring(6, 8);
    }

    /**
     * 
     * @param fen
     * @return
     * @author Zhu Zhenxing
     */
    public static String fen2yuan(long fen) {
        DecimalFormat fmt = new DecimalFormat("0.00");
        return fmt.format(fen / 100.00);
    }
    /**
     * 
     * @param fen
     * @return
     * @author Zhu Zhenxing
     */
    public static String fen2yuan(String fen) {
        return fen2yuan(Long.parseLong(fen));
    }
    
    /**
     * 
     * @param fen
     * @return
     * @author Zhu Zhenxing
     */
    public static String yuan2fen(double yuan) {
        DecimalFormat fmt = new DecimalFormat("0");
        return fmt.format(yuan * 100);
    }
    /**
     * 
     * @param fen
     * @return
     * @author Zhu Zhenxing
     */
    public static String yuan2fen(String yuan) {
        return yuan2fen(Double.parseDouble(yuan));
    }
    
    /**
     * @author: yangm
     * @create: 2008-7-21
     * @document:
     * @param str
     * @return
     */
    public static boolean isNumeric(String str){ 
        Pattern pattern = Pattern.compile("^(-?\\d+)(\\.\\d+)?$"); 
        return pattern.matcher(str).matches();    
     } 
    
    /**
     * 
         * @Title: isNumericZS   
         * @Description: 判断是整数   
         * @param: @param str
         * @param: @return      
         * @return: boolean      
         * @throws
     */
    public static boolean isNumericZS(String str){
    	   Pattern pattern = Pattern.compile("[0-9]*");
    	   Matcher isNum = pattern.matcher(str);
    	   if( !isNum.matches() ){
    	       return false;
    	   }
    	   return true;
    	}
    
    public static boolean checknumber(String accback)
	{
		try{
			double i = Double.valueOf(accback);
			return true;
		}catch(Exception ex)
		{
			return false;
		}
	}

	public static void  main(String[] dd){
		System.out.println(1111);
		boolean ddd = FormatUtil.isNumericZS("1234");
    	System.out.println(ddd);
    }
}
