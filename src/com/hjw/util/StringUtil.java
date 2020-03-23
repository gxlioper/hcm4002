package com.hjw.util;

import java.math.BigDecimal;
import java.security.MessageDigest;
import java.util.*;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description: ��վ��ѯ������ϵͳ
 * </p>
 * <p>
 * Copyright: Copyright (c) 2003
 * </p>
 * <p>
 * Company: Syntong
 * </p>
 * 
 * @author Allen Lv
 * @version 1.0
 */

public class StringUtil {

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List quoteStrList(List list) {
		List tmpList = list;
		list = new ArrayList();
		Iterator i = tmpList.iterator();
		while (i.hasNext()) {
			String str = (String) i.next();
			str = "'" + str + "'";
			list.add(str);
		}
		return list;
	}

	@SuppressWarnings("rawtypes")
	public static String join(List list, String delim) {
		if (list == null || list.size() < 1) {
			return null;
		}
		StringBuffer buf = new StringBuffer();
		Iterator i = list.iterator();
		while (i.hasNext()) {
			buf.append((String) i.next());
			if (i.hasNext()) {
				buf.append(delim);
			}
		}
		return buf.toString();
	}

	/**
	 * ���ֽ�����ת��Ϊ����String
	 * 
	 * @param b
	 *            byte[]
	 * @return String
	 */
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

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List split(String str, String delim) {
		List splitList = null;
		StringTokenizer st = null;

		if (str == null) {
			return splitList;
		}

		if (delim != null) {
			st = new StringTokenizer(str, delim);
		} else {
			st = new StringTokenizer(str);

		}
		if (st != null && st.hasMoreTokens()) {
			splitList = new ArrayList();

			while (st.hasMoreTokens()) {
				splitList.add(st.nextToken());
			}
		}
		return splitList;
	}

	public static String createBreaks(String input, int maxLength) {
		char chars[] = input.toCharArray();
		int len = chars.length;
		StringBuffer buf = new StringBuffer(len);
		int count = 0;
		int cur = 0;
		for (int i = 0; i < len; i++) {
			if (Character.isWhitespace(chars[i])) {
				count = 0;
			}
			if (count >= maxLength) {
				count = 0;
				buf.append(chars, cur, i - cur).append(" ");
				cur = i;
			}
			count++;
		}
		buf.append(chars, cur, len - cur);
		return buf.toString();
	}

	/**
	 * Escape SQL tags, ' to ''; \ to \\.
	 * 
	 * @param input
	 *            string to replace
	 * @return string
	 */
	public static String escapeSQLTags(String input) {
		if (input == null || input.length() == 0) {
			return input;
		}
		StringBuffer buf = new StringBuffer();
		char ch = ' ';
		for (int i = 0; i < input.length(); i++) {
			ch = input.charAt(i);
			if (ch == '\\') {
				buf.append("\\\\");
			} else if (ch == '\'') {
				buf.append("\'\'");
			} else {
				buf.append(ch);
			}
		}
		return buf.toString();
	}

	/**
	 * Escape HTML tags.
	 * 
	 * @param input
	 *            string to replace
	 * @return string
	 */
	public static String deHTML(String input) {
		if (input == null || input.length() == 0) {
			return input;
		}
		StringBuffer buf = new StringBuffer();
		String ch = "%20";
		int i = 0;
		do {
			i = input.indexOf(ch);
			buf.append(input.substring(0, i) + " ");
			input = input.substring(i + 3, input.length());
		} while (input.indexOf(ch) > -1);
		buf.append(input);
		return buf.toString();
	}

	/**
	 * Escape HTML tags.
	 * 
	 * @param input
	 *            string to replace
	 * @return string
	 */
	public static String enHTML(String input) {
		if (input == null || input.length() == 0) {
			return input;
		}
		StringBuffer buf = new StringBuffer();
		char ch = ' ';
		for (int i = 0; i < input.length(); i++) {
			ch = input.charAt(i);
			if (ch == ' ') {
				buf.append("%20");
			} else {
				buf.append(ch);
			}
		}
		return buf.toString();
	}

	/**
	 * Escape HTML tags.
	 * 
	 * @param input
	 *            string to replace
	 * @return string
	 */
	public static String escapeHTMLTags(String input) {
		if (input == null || input.length() == 0) {
			return input;
		}
		StringBuffer buf = new StringBuffer();
		char ch = ' ';
		for (int i = 0; i < input.length(); i++) {
			ch = input.charAt(i);
			if (ch == '<') {
				buf.append("&lt;");
			} else if (ch == '>') {
				buf.append("&gt;");
			} else if (ch == '&') {
				buf.append("&amp;");
			} else if (ch == '"') {
				buf.append("&quot;");
			} else {
				buf.append(ch);
			}
		}
		return buf.toString();
	}

	/**
	 * Convert new lines, \n or \r\n to <BR />.
	 * 
	 * @param input
	 *            string to convert
	 * @return string
	 */
	public static String convertNewlines(String input) {
		input = replace(input, "\r\n", "\n");
		input = replace(input, "\n", "<BR />");
		return input;
	}

	public static String replace(String mainString, String oldString,
			String newString) {
		if (mainString == null) {
			return null;
		}
		int i = mainString.lastIndexOf(oldString);
		if (i < 0) {
			return mainString;
		}
		StringBuffer mainSb = new StringBuffer(mainString);
		while (i >= 0) {
			mainSb.replace(i, i + oldString.length(), newString);
			i = mainString.lastIndexOf(oldString, i - 1);
		}
		return mainSb.toString();
	}

	/**
	 * Check a string null or blank.
	 * 
	 * @param param
	 *            string to check
	 * @return boolean
	 */
	public static boolean nullOrBlank(String param) {
		return (param == null || param.trim().equals("")) ? true : false;
	}

	public static String notNull(String param) {
		return param == null ? "" : param.trim();
	}

	/**
	 * Parse a string to int.
	 * 
	 * @param param
	 *            string to parse
	 * @return int value, on exception return 0.
	 */

	public static int parseInt(String param) {
		int i = 0;
		try {
			i = Integer.parseInt(param);
		} catch (Exception e) {
			i = (int) parseFloat(param);
		}
		return i;
	}

	public static long parseLong(String param) {
		long l = 0;
		try {
			l = Long.parseLong(param);
		} catch (Exception e) {
			l = (long) parseDouble(param);
		}
		return l;
	}

	public static float parseFloat(String param) {
		float f = 0;
		try {
			f = Float.parseFloat(param);
		} catch (Exception e) {
			//
		}
		return f;
	}

	public static double parseDouble(String param) {
		double d = 0;
		try {
			d = Double.parseDouble(param);
		} catch (Exception e) {
			//
		}
		return d;
	}
	
	/**
	 * 
	     * @Title: isDouble   
	     * @Description: 判断字符串是否为数字
	     * @param: @param param
	     * @param: @return      
	     * @return: boolean      
	     * @throws
	 */
	public static boolean isDouble(String param){
		try {
			Double.parseDouble(param);
		} catch (Exception e) {
			return false;
		}
		return true;
	}
	
	public static boolean isInt(String param){
		try {
			Integer.parseInt(param);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	/**
	 * Parse a string to boolean.
	 * 
	 * @param param
	 *            string to parse
	 * @return boolean value, if param begin with(1,y,Y,t,T) return true, on
	 *         exception return false.
	 */
	public static boolean parseBoolean(String param) {
		if (nullOrBlank(param)) {
			return false;
		}
		switch (param.charAt(0)) {
		case '1':
		case 'y':
		case 'Y':
		case 't':
		case 'T':
			return true;
		}
		return false;
	}

	/**
	 * Convert URL .
	 * 
	 * @param input
	 *            string to convert
	 * @return string
	 */
	public static String convertURL(String input) {
		if (input == null || input.length() == 0) {
			return input;
		}
		StringBuffer buf = new StringBuffer(input.length() + 25);
		char chars[] = input.toCharArray();
		int len = input.length();
		int index = -1;
		int i = 0;
		int j = 0;
		int oldend = 0;
		while (++index < len) {
			char cur = chars[i = index];
			j = -1;
			if ((cur == 'f' && index < len - 6 && chars[++i] == 't'
					&& chars[++i] == 'p' || cur == 'h' && (i = index) < len - 7
					&& chars[++i] == 't' && chars[++i] == 't'
					&& chars[++i] == 'p'
					&& (chars[++i] == 's' || chars[--i] == 'p'))
					&& i < len - 4
					&& chars[++i] == ':'
					&& chars[++i] == '/'
					&& chars[++i] == '/') {
				j = ++i;
			}
			if (j > 0) {
				if (index == 0 || (cur = chars[index - 1]) != '\''
						&& cur != '"' && cur != '<' && cur != '=') {
					cur = chars[j];
					while (j < len) {
						if (cur == ' ' || cur == '\t' || cur == '\''
								|| cur == '"' || cur == '<' || cur == '['
								|| cur == '\n' || cur == '\r' && j < len - 1
								&& chars[j + 1] == '\n') {
							break;
						}
						if (++j < len) {
							cur = chars[j];
						}
					}
					cur = chars[j - 1];
					if (cur == '.' || cur == ',' || cur == ')' || cur == ']') {
						j--;
					}
					buf.append(chars, oldend, index - oldend);
					buf.append("<a href=\"");
					buf.append(chars, index, j - index);
					buf.append('"');
					buf.append(" target=\"_blank\"");
					buf.append('>');
					buf.append(chars, index, j - index);
					buf.append("</a>");
				} else {
					buf.append(chars, oldend, j - oldend);
				}
				oldend = index = j;
			} else if (cur == '[' && index < len - 6
					&& chars[i = index + 1] == 'u' && chars[++i] == 'r'
					&& chars[++i] == 'l'
					&& (chars[++i] == '=' || chars[i] == ' ')) {
				j = ++i;
				int u2;
				int u1 = u2 = input.indexOf("]", j);
				if (u1 > 0) {
					u2 = input.indexOf("[/url]", u1 + 1);
				}
				if (u2 < 0) {
					buf.append(chars, oldend, j - oldend);
					oldend = j;
				} else {
					buf.append(chars, oldend, index - oldend);
					buf.append("<a href =\"");
					String href = input.substring(j, u1).trim();
					if (href.indexOf("javascript:") == -1
							&& href.indexOf("file:") == -1) {
						buf.append(href);
					}
					buf.append("\" target=\"_blank");
					buf.append("\">");
					buf.append(input.substring(u1 + 1, u2).trim());
					buf.append("</a>");
					oldend = u2 + 6;
				}
				index = oldend;
			}
		}
		if (oldend < len) {
			buf.append(chars, oldend, len - oldend);
		}
		return buf.toString();
	}

	/**
	 * Display a string in html page, call methods: escapeHTMLTags, convertURL,
	 * convertNewlines.
	 * 
	 * @param input
	 *            string to display
	 * @return string
	 */
	public static String dspHtml(String input) {
		String str = input;
		str = createBreaks(str, 80);
		str = escapeHTMLTags(str);
		str = convertURL(str);
		str = convertNewlines(str);
		return str;
	}

	public static String lpad(String s, int iLength, String sNewChar) {
		String sTmp = s;
		if (sTmp != null) {
			int iCount = iLength - sTmp.length();
			for (int i = 0; i < iCount; i++) {
				sTmp = sNewChar + sTmp;
			}
		}
		return sTmp;
	}

	public static String rpad(String s, int iLength, String sNewChar) {
		String sTmp = s;
		if (sTmp != null) {
			int iCount = iLength - sTmp.length();
			for (int i = 0; i < iCount; i++) {
				sTmp += sNewChar;
			}
		}
		return sTmp;
	}

	public static String md5(String message) {
		String md5 = "";
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] array = md.digest(message.getBytes());
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < array.length; i++) {
				sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100)
						.substring(1, 3));
			}
			md5 = sb.toString();
		} catch (Exception e) {
		}
		return md5.toLowerCase();
	}

	public static String getpasswd(String strkey, String passwd) {
		String pass = "";
		String str = "0123456789abcdefghijklmnopqrstrvwxyz";
		for (int i = 0; i < passwd.length(); i++) {
			char a = passwd.charAt(i);
			int j = str.indexOf(String.valueOf(a));
			pass = pass + strkey.substring(j, j + 1);
		}
		return pass;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List getnorepeat(String str) {
		 String[] zy =str.split(","); 
         ArrayList list=new ArrayList(); 
        for(int i=0;i<zy.length;i++)   
        { 
             if(!list.contains(zy[i])) 
             list.add(zy[i]);       
         }  
		return list;
	}
	 public static String gbEncoding(String gbString) {  
	        char[] utfBytes = gbString.toCharArray();  
	        String unicodeBytes = "";  
	        for (int byteIndex = 0; byteIndex < utfBytes.length; byteIndex++) {  
	            String hexB = Integer.toHexString(utfBytes[byteIndex]);  
	            if (hexB.length() <= 2) {  
	                hexB = "00" + hexB;  
	            }  
	            unicodeBytes = unicodeBytes + "\\u" + hexB;  
	        }  
	        System.out.println("unicodeBytes is: " + unicodeBytes);  
	        return unicodeBytes;  
	    }  
	  
	    public static String decodeUnicode(String dataStr) {  
	        int start = 0;  
	        int end = 0;  
	        final StringBuffer buffer = new StringBuffer();  
	        while (start > -1) {  
	            end = dataStr.indexOf("\\u", start + 2);  
	            String charStr = "";  
	            if (end == -1) {  
	                charStr = dataStr.substring(start + 2, dataStr.length());  
	            } else {  
	                charStr = dataStr.substring(start + 2, end);  
	            }  
	            char letter = (char) Integer.parseInt(charStr, 16); // 16进制parse整形字符串。  
	            buffer.append(new Character(letter).toString());  
	            start = end;  
	        }  
	        return buffer.toString();  
	    }  
	    
	    
	    public static String comparesort(String dataStr) {  
	    	@SuppressWarnings({ "unused", "rawtypes" })
			class MyComparator implements Comparator{
				 public  int compare(Object str1, Object str2 ){
				   return (str1.toString()).compareTo(str2.toString());
				  
				 }
			}
	    //String dataStr="14-1,12-2,15-0";
	     String[] pss =dataStr.split(",");
	  	 // java.util.Arrays.sort(pss,new MyComparator());
	  	  String newstr1 = null;
	  	  for (int i = 0; i<pss.length; i++){
	  		  if (newstr1==null) newstr1=(String)pss[i];
	  			else newstr1 = newstr1+","+(String)pss[i]; 
	  		 
	  	}
	  	 
	  	//System.out.println (newstr1); 结果12-2,14-1,15-0
	        return newstr1;  
	    }
	    
	    /**
	    * 字符串+1方法，该方法将其结尾的整数+1,适用于任何以整数结尾的字符串,不限格式，不限分隔符。
	    * @author zxcvbnmzb
	    * @param testStr 要+1的字符串
	    * @return +1后的字符串
	    * @exception NumberFormatException
	    */
	    public static String addOne(String testStr){
	        String[] strs = testStr.split("[^0-9]");//根据不是数字的字符拆分字符串
	        String numStr = strs[strs.length-1];//取出最后一组数字
	        if(numStr != null && numStr.length()>0){//如果最后一组没有数字(也就是不以数字结尾)，抛NumberFormatException异常
	            int n = numStr.length();//取出字符串的长度
	            Double num = Double.parseDouble(numStr)+1;//将该数字加一
	            BigDecimal bigDecimal = new BigDecimal(num);  
	            String added = bigDecimal.toString();
	            n = Math.min(n, added.length());
	            //拼接字符串
	            return testStr.subSequence(0, testStr.length()-n)+added;
	        }else{
	            throw new NumberFormatException();
	        }
	    }  
	    
	    public static String getXmlNodeValue(String xml,String node){
	    	String val="";
	    	try {
				Document doc = DocumentHelper.parseText(xml);
				Element root = doc.getRootElement();
				 System.out.println("根节点：" + root.getName()); // 拿到根节点的名称
				 
				Iterator i = root.elementIterator(node);
				while (i.hasNext()) {
					Element foo = (Element) i.next();
					val = foo.getStringValue();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
	    	return val;
	    }
	    
	    
	    /**
		 * 获取字符串的长度，对双字符（包括汉字）按两位计数
		 * 
		 * @param value
		 * @return
		 */
		public static int getStrLength(String value) {
			int valueLength = 0;
			String chinese = "[\u0391-\uFFE5]";
			for (int i = 0; i < value.length(); i++) {
				String temp = value.substring(i, i + 1);
				if (temp.matches(chinese)) {
					valueLength += 2;
				} else {
					valueLength += 1;
				}
			}
			return valueLength;
		}
		/**
		 * 判断返回是数字的字符
		     * @Title: getCheckStringInt   
		     * @Description: TODO(这里用一句话描述这个方法的作用)   
		     * @param: @param str
		     * @param: @return      
		     * @return: List      
		     * @throws
		 */
		public static List getCheckStringInt(String str){
			String[] zy =str.split(","); 
	        ArrayList list=new ArrayList(); 
	        for(int i=0;i<zy.length;i++){ 
	        	if(StringUtil.isInt(zy[i])) 
	        	list.add(zy[i]);       
	        }  
			return list;
		}
		
		// 将数字转化为大写    
	    public static String numToUpper(int num) {    
	        // String u[] = {"零","壹","贰","叁","肆","伍","陆","柒","捌","玖"};    
	        String u[] = { "十", "一", "二", "三", "四", "五", "六", "七", "八", "九" }; 
	        String u1[] = { "", "一", "二", "三", "四", "五", "六", "七", "八", "九" };
	        
	        char[] str = String.valueOf(num).toCharArray();    
	        String rstr = ""; 
	        if(num >= 10 && num < 20){
	        	rstr = "十"+u1[Integer.parseInt(str[1] + "")];
	        }else{
	        	for (int i = 0; i < str.length; i++) {    
	                rstr = rstr + u[Integer.parseInt(str[i] + "")];    
	            } 
	        }
	        return "（"+rstr+"）";    
	    }
	    
	    /**
	     * 
	         * @Title: escapeExprSpecialWord   
	         * @Description: TODO(这里用一句话描述这个方法的作用)   
	         * @param: @param keyword
	         * @param: @return      
	         * @return: String      
	         * @throws
	     */
	    public static String escapeExprSpecialWord(String keyword) {
	    	//System.out.println(keyword);
	    	
	        if (StringUtils.isNotBlank(keyword)) {  
	            String[] fbsArr = { "\\", "$","\'",",", "(", ")", "*", "+", ".", "[", "]", "?", "^", "{", "}", "|" };  
	            for (String key : fbsArr) {  
	                if (keyword.contains(key)) {  
	                    keyword = keyword.replace(key, " ");  
	                }  
	            }  
	        }  
	        //System.out.println(keyword);
	        return keyword;  
	    }
	    
	    /**
	     * byte数组转换成16进制字符串
	     * @param src
	     * @return
	     */
	    public static String bytesToHexString(byte[] src){    
	           StringBuilder stringBuilder = new StringBuilder();    
	           if (src == null || src.length <= 0) {    
	               return null;    
	           }    
	           for (int i = 0; i < src.length; i++) {    
	               int v = src[i] & 0xFF;    
	               String hv = Integer.toHexString(v);    
	               if (hv.length() < 2) {    
	                   stringBuilder.append(0);    
	               }    
	               stringBuilder.append(hv);    
	           }    
	           return stringBuilder.toString();    
	       }
	    
	    /**
	     * 
	         * @Title: checkString   
	         * @Description: 判断某个字符串是否在另外一个字符串数组里面   
	         * @param: @param resources
	         * @param: @param str
	         * @param: @param flagstr
	         * @param: @return      
	         * @return: boolean      
	         * @throws
	     */
	    public static boolean checkString(String resources,String flagstr,String str){
	    	if((resources==null)||(resources.trim().length()<=0)){
	    		return false;
	    	}else if((str==null)||(str.trim().length()<=0)){
	    		return false;	
	    	}else if((flagstr==null)||(flagstr.trim().length()<=0)){
	    		return false;	
	    	}else{
	    		boolean f=false;
	    		String[] res= resources.trim().split(flagstr.trim());
	    		for(String ress:res){
	    			if(str.trim().equals(ress.trim())){
	    				f=true;
	    				break;
	    			}
	    		}
	    		return f;
	    	}
	    }
	    
	    /**
		 * 检查字符串是否在这个池里面
		 * @param strs 字符串池
		 * @param str  字符串
		 * @return
		 */
		public static int getcheckStr(String strs,String str){
			int f=0;  // 有：1，没有：0
			String[] strings=strs.split(",");
			for(int i=0;i<strings.length;i++){
				if(str.equals(strings[i])){
					f=1;
					break;
				}
			}
			return f;
		}
	    
	    public static void main(String[] args) {
			String str = "(3)'II度肥胖";
			System.out.println(escapeExprSpecialWord(str));
		}
}
