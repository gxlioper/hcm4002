package com.hjw.util;

import java.io.ByteArrayOutputStream;

public class FormatTransfer {
	
    private static String hexString = "0123456789ABCDEF";
    
    public static byte[] toLH(int n) {
        byte[] b = new byte[4];
        b[0] = (byte) (n & 0xff);
        b[1] = (byte) (n >> 8 & 0xff);
        b[2] = (byte) (n >> 16 & 0xff);
        b[3] = (byte) (n >> 24 & 0xff);
        return b;
    }


    public static byte[] toHH(int n) {
        byte[] b = new byte[4];
        b[3] = (byte) (n & 0xff);
        b[2] = (byte) (n >> 8 & 0xff);
        b[1] = (byte) (n >> 16 & 0xff);
        b[0] = (byte) (n >> 24 & 0xff);
        return b;
    }


    public static byte[] toLH(short n) {
        byte[] b = new byte[2];
        b[0] = (byte) (n & 0xff);
        b[1] = (byte) (n >> 8 & 0xff);
        return b;
    }


    public static byte[] toHH(short n) {
        byte[] b = new byte[2];
        b[1] = (byte) (n & 0xff);
        b[0] = (byte) (n >> 8 & 0xff);
        return b;
    }


    public static byte[] toLH(char ch) {
        int temp = (int) ch;
        byte[] b = new byte[2];
        for (int i = b.length - 1; i > -1; i--) {
            b[i] = new Integer(temp & 0xff).byteValue(); 
            temp = temp >> 8; 
        }
        return b;
    }


    public static byte[] toLH(long l) {
        byte[] b = new byte[4];
        b[0] = (byte) (l & 0xff);
        b[1] = (byte) (l >> 8 & 0xff);
        b[2] = (byte) (l >> 16 & 0xff);
        b[3] = (byte) (l >> 24 & 0xff);
        return b;
    }



    public static char CharTobytes(byte[] b) {
        int s = 0;
        if (b[0] > 0)
            s += b[0];
        else
            s += 256 + b[0];
        s *= 256;
        if (b[1] > 0)
            s += b[1];
        else
            s += 256 + b[1];
        char ch = (char) s;
        return ch;
    }


    public static byte[] toLH(float f) {
        return toLH(Float.floatToRawIntBits(f));

    }


    public static byte[] toHH(float f) {
        return toHH(Float.floatToRawIntBits(f));
    }


    public static byte[] stringToBytes(String s, int length) {
        while (s.getBytes().length < length) {
            s += " ";
        }
        return s.getBytes();
    }


    public static String bytesToString(byte[] b) {
        StringBuffer result = new StringBuffer("");
        if (b != null) {
            int length = b.length;
            for (int i = 0; i < length; i++) {
                result.append((char) (b[i] & 0xff));
            }
        }
        return result.toString();
    }


    public static String bytesToStringCN(byte[] b) {
        StringBuffer result = new StringBuffer("");
        int length = b.length;
        for (int i = 0; i < length; i++) {
            result.append((char) (b[i] & 0xff));
        }
        String res = result.toString();
        String resu = null;
        try {
            resu = new String(res.trim().getBytes("ISO-8859-1"), "GBK");
        } catch (Exception ex) {
        }
        return resu;
    }


    public static byte[] stringToBytes(String s) {
        return s.getBytes();
    }


    public static int hBytesToInt(byte[] b) {
        int s = 0;
        for (int i = 0; i < 3; i++) {
            if (b[i] >= 0) {
                s = s + b[i];
            } else {
                s = s + 256 + b[i];
            }
            s = s * 256;
        }
        if (b[3] >= 0) {
            s = s + b[3];
        } else {
            s = s + 256 + b[3];
        }
        return s;
    }


    public static int lBytesToInt(byte[] b) {
        int s = 0;
        for (int i = 0; i < 3; i++) {
            if (b[3 - i] >= 0) {
                s = s + b[3 - i];
            } else {
                s = s + 256 + b[3 - i];
            }
            s = s * 256;
        }
        if (b[0] >= 0) {
            s = s + b[0];
        } else {
            s = s + 256 + b[0];
        }
        return s;
    }


    public static short hBytesToShort(byte[] b) {
        int s = 0;
        if (b[0] >= 0) {
            s = s + b[0];
        } else {
            s = s + 256 + b[0];
        }
        s = s * 256;
        if (b[1] >= 0) {
            s = s + b[1];
        } else {
            s = s + 256 + b[1];
        }
        short result = (short) s;
        return result;
    }


    public static short lBytesToShort(byte[] b) {
        int s = 0;
        if (b[1] >= 0) {
            s = s + b[1];
        } else {
            s = s + 256 + b[1];
        }
        s = s * 256;
        if (b[0] >= 0) {
            s = s + b[0];
        } else {
            s = s + 256 + b[0];
        }
        short result = (short) s;
        return result;
    }


    public static float hBytesToFloat(byte[] b) {
        int i = 0;
        Float F = new Float(0.0);
        i = ((((b[0] & 0xff) << 8 | (b[1] & 0xff)) << 8) | (b[2] & 0xff)) << 8
                | (b[3] & 0xff);
        return F.intBitsToFloat(i);
    }


    public static long hBytesToLong(byte[] b) {
        long i = 0;
        Long F = new Long(0);
        i = ((((b[0] & 0xff) << 8 | (b[1] & 0xff)) << 8) | (b[2] & 0xff)) << 8
                | (b[3] & 0xff);

        return i;
    }


    public static float lBytesToFloat(byte[] b) {
        int i = 0;
        Float F = new Float(0.0);
        i = ((((b[3] & 0xff) << 8 | (b[2] & 0xff)) << 8) | (b[1] & 0xff)) << 8
                | (b[0] & 0xff);
        return F.intBitsToFloat(i);
    }


    public static byte[] bytesReverseOrder(byte[] b) {
        int length = b.length;
        byte[] result = new byte[length];
        for (int i = 0; i < length; i++) {
            result[length - i - 1] = b[i];
        }
        return result;
    }

    public static void printBytes(byte[] bb) {
        int length = bb.length;
        for (int i = 0; i < length; i++) {
            System.out.print(bb[i] + " ");
        }

    }

    public static void logBytes(byte[] bb) {
        int length = bb.length;
        String out = "";
        for (int i = 0; i < length; i++) {
            out = out + bb[i] + " ";
        }

    }


    public static int reverseInt(int i) {
        int result = FormatTransfer.hBytesToInt(FormatTransfer.toLH(i));
        return result;
    }


    public static short reverseShort(short s) {
        short result = FormatTransfer.hBytesToShort(FormatTransfer.toLH(s));
        return result;
    }


    public static float reverseFloat(float f) {
        float result = FormatTransfer.hBytesToFloat(FormatTransfer.toLH(f));
        return result;
    }

    public static String hexString2binaryString(String hexString) {
        if (hexString == null || hexString.length() % 2 != 0)
            return null;
        String bString = "", tmp;
        for (int i = 0; i < hexString.length(); i++) {
            tmp = "0000"
                    + Integer.toBinaryString(Integer.parseInt(hexString
                            .substring(i, i + 1), 16));
            bString += tmp.substring(tmp.length() - 4);
        }
        return bString;
    }

    // ------------------------------------------------------
    public static String binaryString2hexString(String bString) {
        if (bString == null || bString.equals("") || bString.length() % 8 != 0)
            return null;
        StringBuffer tmp = new StringBuffer();
        int iTmp = 0;
        for (int i = 0; i < bString.length(); i += 4) {
            iTmp = 0;
            for (int j = 0; j < 4; j++) {
                iTmp += Integer.parseInt(bString.substring(i + j, i + j + 1)) << (4 - j - 1);
            }
            tmp.append(Integer.toHexString(iTmp));
        }
        return tmp.toString();
    }


    public static String ResultShow(String str) {
        if ((str != null) && (str != "")) {
            byte[] oldstr = str.getBytes();
            byte[] newstr = new byte[oldstr.length];
            int j = 0;
            for (int i = 0; i < oldstr.length; i++) {
                if (oldstr[i] != 0) {
                    newstr[j] = oldstr[i];
                    j++;
                }
            }
            return FormatTransfer.bytesToStringCN(newstr);
        } else {
            return str;
        }
    }

    public static String hexToDecimal(String strHex,int bitWidth){
      String strDec = ""; 

      if(strHex.length() == 0)
        return "";

      String strBin = hexToBinary(strHex);
      strDec = binaryToDecimal(strBin,bitWidth);

      return strDec;
    }



    public static String hexToBinary(String strHex){
      String strBin = "";

      if(strHex.length() <= 0) {
        return "";
      }


      char[] arrHex = strHex.toLowerCase().toCharArray();

      for(int i = 0; i < arrHex.length; i++) {
        strBin += getBinstrFromHexchar(arrHex[i]);
      }

      return strBin;
    }


    public static String binaryToHex(String strBin){
      String strHex = "";

      if(strBin.length() <= 0) {
        return "";
      }

      int lengthOfBin = strBin.length();
      for(int i = 0; i < (4 - lengthOfBin % 4) && (lengthOfBin % 4) != 0; i++) {
        strBin = "0" + strBin;
      }

      for(int i = 0; i < strBin.length(); i += 4) {
        String strDec = strBin.substring(i, i + 4);
        strHex += getHexcharFromDecnum(Integer.parseInt(strDec,2));
      }

      return strHex;

    }

    public static String binaryToDecimal(String strBin,int bitWidth){

      long dec = 0;

      boolean isNegative = false; 

      if(strBin.length() <= 0) {
        return "";
      }

      if(strBin.length() == bitWidth && strBin.charAt(0) == '1') {

        strBin = strBin.substring(1);
        isNegative = true;

        StringBuffer sbBinTemp = new StringBuffer(strBin);

        int index = strBin.length() - 1;
        while(index >= 0) {
          if (sbBinTemp.charAt(index) == '1') {
            sbBinTemp.setCharAt(index, '0');
            break;
          } else {
            sbBinTemp.setCharAt(index, '1');
            index--;
          }
        }

        for(index = 0; index < sbBinTemp.length(); index++) {
          if (sbBinTemp.charAt(index) == '1') {
            sbBinTemp.setCharAt(index, '0');
          } else {
            sbBinTemp.setCharAt(index, '1');
          }
        }
        strBin = sbBinTemp.toString();
      }

      return strBin;
    }



    private static String getBinstrFromHexchar(char cHex) {
      String strBin = "";

      switch (cHex) {
        case '0':
          strBin = "0000"; 
          break;
        case '1':
          strBin = "0001";
          break;
        case '2':
          strBin = "0010";
          break;
        case '3':
          strBin = "0011";
          break;
        case '4':
          strBin = "0100";
          break;
        case '5':
          strBin = "0101";
          break;
        case '6':
          strBin = "0110";
          break;
        case '7':
          strBin = "0111";
          break;
        case '8':
          strBin = "1000";
          break;
        case '9':
          strBin = "1001";
          break;
        case 'a':
          strBin = "1010";
          break;
        case 'b':
          strBin = "1011";
          break;
        case 'c':
          strBin = "1100";
          break;
        case 'd':
          strBin = "1101";
          break;
        case 'e':
          strBin = "1110";
          break;
        case 'f':
          strBin = "1111";
          break;
      }

      return strBin;
    }


    private static char getHexcharFromDecnum(int iDec) {
      char cHex = 'x';

      switch (iDec) {
        case 0:
          cHex = '0';
          break;
        case 1:
          cHex = '1';
          break;
        case 2:
          cHex = '2';
          break;
        case 3:
          cHex = '3';
          break;
        case 4:
          cHex = '4';
          break;
        case 5:
          cHex = '5';
          break;
        case 6:
          cHex = '6';
          break;
        case 7:
          cHex = '7';
          break;
        case 8:
          cHex = '8';
          break;
        case 9:
          cHex = '9';
          break;
        case 10:
          cHex = 'A';
          break;
        case 11:
          cHex = 'B';
          break;
        case 12:
          cHex = 'C';
          break;
        case 13:
          cHex = 'D';
          break;
        case 14:
          cHex = 'E';
          break;
        case 15:
          cHex = 'F';
          break;
      }
      return cHex;
    }
    
    public static byte[] HexString1Bytes(String src) {
  	   byte[] ret = new byte[20];
  	   byte[] tmp = src.getBytes();
  	   for (int i = 0; i < tmp.length; i++) {
  	     ret[i] = (byte) (tmp[i]-Byte.parseByte("18"));
  	   }
  	   return ret;
  	}
    
    public static byte[] HexString2Bytes(String src) {
 	   byte[] ret = new byte[100];
 	   byte[] tmp = src.getBytes();
 	   for (int i = 0; i < ret.length; i++) {
 	     ret[i] = uniteBytes(tmp[i * 2], tmp[i * 2 + 1]);
 	   }
 	   return ret;
 	}
    
    public static byte[] HexStringsfBytes(String src) {
   	   byte[] ret = new byte[src.length()];
   	   byte[] tmp = src.getBytes();
   	   for (int i = 0; i < tmp.length; i++) {
   	     ret[i] = (byte) (tmp[i]-Byte.parseByte("18"));
   	   }
   	   return ret;
   	} 
    public static byte uniteBytes(byte src0, byte src1) {
 	   byte _b0 = Byte.decode("0x" + new String(new byte[] {src0})).byteValue();
 	   _b0 = (byte) (_b0 << 4);
 	   byte _b1 = Byte.decode("0x" + new String(new byte[] {src1})).byteValue();
 	   byte ret = (byte) (_b0 ^ _b1);
 	   ret=(byte) (ret - Byte.parseByte("48"));
 	   return ret;
 	}
    
    public static String bytesToString2(byte[] b) {
        StringBuffer result = new StringBuffer("");
        if (b != null) {
            int length = b.length;
            for (int i = 0; i < length; i++) {
                result.append(b[i]);
            }
        }
        return result.toString();
    }
    

    public static byte[] ToBCD(String a)
    {
        char[] aCh = a.toCharArray();
        byte[] rByte = new byte[aCh.length / 2 + aCh.length % 2];
        int i = 0;
        int j = 0;
        while (i < aCh.length)
        {
            int num1 = (int)(aCh[i])-48;
            i++;
            int num2 = 0;
            if (i < aCh.length)
            {
            	 num2 = (int)(aCh[i]) - 48;
            }
            i++;
            rByte[j] = (byte)((((byte)num1) << 4) + (((byte)num2)));
            j++;
        }
        return rByte;
    }
    public static String BCDToStr(byte[] bcd)
    {
    	StringBuffer sb = new StringBuffer();
        for (int i = 0; i < bcd.length; i++)
        {
            sb.append(bcd[i] >> 4); 
            sb.append(((byte)(bcd[i] << 4)) >> 4);              
        }
        return sb.toString();
    } 

 public static String bcd2Str(byte[] bytes){
     StringBuffer temp=new StringBuffer(bytes.length*2);

     for(int i=0;i<bytes.length;i++){
      temp.append((byte)((bytes[i]& 0xf0)>>>4));
      temp.append((byte)(bytes[i]& 0x0f));
     }
     return temp.toString().substring(0,1).equalsIgnoreCase("0")?temp.toString().substring(1):temp.toString();
 }

 public static String bcdtoStr(byte[] bytes){
     StringBuffer temp=new StringBuffer(bytes.length*2);

     for(int i=0;i<bytes.length;i++){
    	 if(bytes[i]<10){
    		 temp.append("0");
    		 temp.append(bytes[i]);
    	 }else
    		 temp.append(bytes[i]);
     }
     return temp.toString();
 }

 public static byte[] str2Bcd(String asc) {
     int len = asc.length();
     int mod = len % 2;

     if (mod != 0) {
      asc = "0" + asc;
      len = asc.length();
     }

     byte abt[] = new byte[len];
     if (len >= 2) {
      len = len / 2;
     }

     byte bbt[] = new byte[len];
     abt = asc.getBytes();
     int j, k;

     for (int p = 0; p < asc.length()/2; p++) {
      if ( (abt[2 * p] >= '0') && (abt[2 * p] <= '9')) {
       j = abt[2 * p] - '0';
      } else if ( (abt[2 * p] >= 'a') && (abt[2 * p] <= 'z')) {
       j = abt[2 * p] - 'a' + 0x0a;
      } else {
       j = abt[2 * p] - 'A' + 0x0a;
      }

      if ( (abt[2 * p + 1] >= '0') && (abt[2 * p + 1] <= '9')) {
       k = abt[2 * p + 1] - '0';
      } else if ( (abt[2 * p + 1] >= 'a') && (abt[2 * p + 1] <= 'z')) {
       k = abt[2 * p + 1] - 'a' + 0x0a;
      }else {
       k = abt[2 * p + 1] - 'A' + 0x0a;
      }

      int a = (j << 4) + k;
      byte b = (byte) a;
      bbt[p] = b;
     }
     return bbt;
 }


 public static String encode(String str) {
     byte[] bytes = str.getBytes();
     StringBuffer sb = new StringBuffer(bytes.length * 2);
     for (int i = 0; i < bytes.length; i++) {
         sb.append(hexString.charAt((bytes[i] & 0xf0) >> 4));
         sb.append(hexString.charAt((bytes[i] & 0x0f) >> 0));
     }
     return sb.toString();
 }


 public static String decode(String bytes) {
     ByteArrayOutputStream baos = new ByteArrayOutputStream(
             bytes.length() / 2);
     for (int i = 0; i < bytes.length(); i += 2)
         baos.write((hexString.indexOf(bytes.charAt(i)) << 4 | hexString
                 .indexOf(bytes.charAt(i + 1))));
     return new String(baos.toByteArray());
 }
 
 public static String buling(String strs,int n){
	 while(strs.length()!=n){
		 strs+="0";
	 }
	 return strs.toString();
 }
 
 public static long ipToLong(String strIp) {
		long[] ip = new long[4];
		int position1 = strIp.indexOf(".");
		int position2 = strIp.indexOf(".", position1 + 1);
		int position3 = strIp.indexOf(".", position2 + 1);
		ip[0] = Long.parseLong(strIp.substring(0, position1));
		ip[1] = Long.parseLong(strIp.substring(position1 + 1, position2));
		ip[2] = Long.parseLong(strIp.substring(position2 + 1, position3));
		ip[3] = Long.parseLong(strIp.substring(position3 + 1));
		return ip[3] + (ip[2] << 8) + (ip[1] << 16) + (ip[0] << 24);
	}
 
 public static long ipToLong2(String strIp) {
		long[] ip = new long[4];
		int position1 = strIp.indexOf(".");
		int position2 = strIp.indexOf(".", position1 + 1);
		int position3 = strIp.indexOf(".", position2 + 1);
		ip[0] = Long.parseLong(strIp.substring(0, position1));
		ip[1] = Long.parseLong(strIp.substring(position1 + 1, position2));
		ip[2] = Long.parseLong(strIp.substring(position2 + 1, position3));
		ip[3] = Long.parseLong(strIp.substring(position3 + 1));
		return ip[0] + (ip[1] << 8) + (ip[2] << 16) + (ip[3] << 24);
	}
 
 
 /**   
  * 字符串转换成十六进制字符串  
  * @param String str 待转换的ASCII字符串  
  * @return String 每个Byte之间空格分隔，如: [61 6C 6B]  
  */      
 public static String str2HexStr(String str)    
 {      
   
     char[] chars = hexString.toCharArray();      
     StringBuilder sb = new StringBuilder("");    
     byte[] bs = str.getBytes();      
     int bit;      
         
     for (int i = 0; i < bs.length; i++)    
     {      
         bit = (bs[i] & 0x0f0) >> 4;      
         sb.append(chars[bit]);      
         bit = bs[i] & 0x0f;      
         sb.append(chars[bit]);    
     }      
     return sb.toString().trim();      
 }    
     
 /**   
  * 十六进制转换字符串  
  * @param String str Byte字符串(Byte之间无分隔符 如:[616C6B])  
  * @return String 对应的字符串  
  */      
 public static String hexStr2Str(String hexStr)    
 {      
     String str = hexString;      
     char[] hexs = hexStr.toCharArray();      
     byte[] bytes = new byte[hexStr.length() / 2];      
     int n;      
   
     for (int i = 0; i < bytes.length; i++)    
     {      
         n = str.indexOf(hexs[2 * i]) * 16;      
         n += str.indexOf(hexs[2 * i + 1]);      
         bytes[i] = (byte) (n & 0xff);      
     }      
     return new String(bytes);      
 }    
     
 /**  
  * bytes转换成十六进制字符串  
  * @param byte[] b byte数组  
  * @return String 每个Byte值之间空格分隔  
  */    
 public static String byte2HexStr(byte[] b)    
 {    
     String stmp="";    
     StringBuilder sb = new StringBuilder("");    
     for (int n=0;n<b.length;n++)    
     {    
         stmp = Integer.toHexString(b[n] & 0xFF);    
         sb.append((stmp.length()==1)? "0"+stmp : stmp);    
         sb.append(" ");    
     }    
     return sb.toString().toUpperCase().trim();    
 }    
     
 /**  
  * bytes字符串转换为Byte值  
  * @param String src Byte字符串，每个Byte之间没有分隔符  
  * @return byte[]  
  */    
 public static byte[] hexStr2Bytes(String src)    
 {    
     int m=0,n=0;    
     int l=src.length()/2;    
     System.out.println(l);    
     byte[] ret = new byte[l];    
     for (int i = 0; i < l; i++)    
     {    
         m=i*2+1;    
         n=m+1;    
         ret[i] = Byte.decode("0x" + src.substring(i*2, m) + src.substring(m,n));    
     }    
     return ret;    
 }    
   
 /**  
  * String的字符串转换成unicode的String  
  * @param String strText 全角字符串  
  * @return String 每个unicode之间无分隔符  
  * @throws Exception  
  */    
 public static String strToUnicode(String strText)    
     throws Exception    
 {    
     char c;    
     StringBuilder str = new StringBuilder();    
     int intAsc;    
     String strHex;    
     for (int i = 0; i < strText.length(); i++)    
     {    
         c = strText.charAt(i);    
         intAsc = (int) c;    
         strHex = Integer.toHexString(intAsc);    
         if (intAsc > 128)    
             str.append("\\u" + strHex);    
         else // 低位在前面补00    
             str.append("\\u00" + strHex);    
     }    
     return str.toString();    
 }    
     
 /**  
  * unicode的String转换成String的字符串  
  * @param String hex 16进制值字符串 （一个unicode为2byte）  
  * @return String 全角字符串  
  */    
 public static String unicodeToString(String hex)    
 {    
     int t = hex.length() / 6;    
     StringBuilder str = new StringBuilder();    
     for (int i = 0; i < t; i++)    
     {    
         String s = hex.substring(i * 6, (i + 1) * 6);    
         // 高位需要补上00再转    
         String s1 = s.substring(2, 4) + "00";    
         // 低位直接转    
         String s2 = s.substring(4);    
         // 将16进制的string转为int    
         int n = Integer.valueOf(s1, 16) + Integer.valueOf(s2, 16);    
         // 将int转换为字符    
         char[] chars = Character.toChars(n);    
         str.append(new String(chars));    
     }    
     return str.toString();    
 }   
 

    public static void main(String[] args) {
    	String str = FormatTransfer.str2HexStr("wk00003800").trim();
    	System.out.println(str);
    	str = FormatTransfer.hexStr2Str(str);
    	System.out.println(str);
    	}
    
}
