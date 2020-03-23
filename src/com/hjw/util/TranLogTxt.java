package com.hjw.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TranLogTxt {
	private static final String filepaht="d://log//";

	/**
	 * 
	 * @Title: writeEror_to_txt
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param: @param title 标题
	 * @param: @param id id
	 * @param: @param content 说明
	 * @param: @throws IOException
	 * @return: void
	 * @throws
	 */
	public static void liswriteEror_to_txt(String filetype,String content){
		try{
		FileWriter fw = null;
			String path = "log";
			//path= Thread.currentThread().getContextClassLoader().getResource("")+"log/";
			//path=path.substring(6,path.length()); 
			path = filepaht;
			//System.out.println(path);
			//String path = "c:/transferlog";
			File f = new File(path);
			if(!f.exists() && !f.isDirectory())      
			{        
			    f.mkdir();    
			} 
			path = path + DateTimeUtil.getDate() + "-"+filetype+".txt";
			f = new File(path);
			// 如果文件不存在,就动态创建文件
			if (!f.exists()) {
				f.createNewFile();
			}
			fw = null;
			String writeDate = DateTimeUtil.getDateTime() + ":" + content;
			// 设置为:True,表示写入的时候追加数据
			fw = new FileWriter(f, true);
			// 回车并换行
			fw.write(writeDate + "\r\n");
			fw.close();
		} catch (Exception ex){
			ex.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @Title: writeEror_to_txt
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param: @param title 标题
	 * @param: @param id id
	 * @param: @param content 说明
	 * @param: @throws IOException
	 * @return: void
	 * @throws
	 */
	public static void liswriteEror_to_txt_single(String filetype,String content){
		try{
		FileWriter fw = null;
			String path = "log";
			//path= Thread.currentThread().getContextClassLoader().getResource("")+"log/";
			//path=path.substring(6,path.length()); 
			path = filepaht;
			//System.out.println(path);
			//String path = "c:/transferlog";
			File f = new File(path);
			if(!f.exists() && !f.isDirectory())      
			{        
			    f.mkdir();    
			} 
			//path = path + DateTimeUtil.getDate() + "-"+filetype+".txt";
			path = path + filetype+".txt";
			f = new File(path);
			// 如果文件不存在,就动态创建文件
			if (!f.exists()) {
				f.createNewFile();
			}
			fw = null;
			String writeDate = DateTimeUtil.getDateTime() + ":" + content;
			// 设置为:True,表示写入的时候追加数据
			fw = new FileWriter(f, false);
			// 回车并换行
			fw.write(writeDate + "\r\n");
			fw.close();
		} catch (Exception ex){
			ex.printStackTrace();
		}
	}
	

	/**
	 * 
	     * @Title: liswriteEror_to_txt   
	     * @Description: 刪除日志   
	     * @param: @param filetype
	     * @param: @param days      
	     * @return: void      
	     * @throws
	 */
	public static void lisdelEror_to_txt(String filetype, int days) {
		try {
			String path = "log";
			// path=
			// Thread.currentThread().getContextClassLoader().getResource("")+"log/";
			// path=path.substring(6,path.length());
			path = filepaht;
			for (int i = 0; i < (30 - days); i++) {
				int daynum = days + i;
				String datetime = TranLogTxt.getdateDelDay(daynum);
				System.out.println(datetime);
				path = path + datetime + "-" + filetype + ".txt";
				File f = new File(path);
				// 如果文件不存在,就动态创建文件
				if (f.exists()) {
					f.delete();
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	/**
	 * 指定日期減去天数后的日期
	 * @param num 为增加的天数
	 * @param newDate 创建时间
	 * @return
	 * @throws ParseException
	 */
	public static String getdateDelDay(int num){
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
			Date currdate = new Date();
			Calendar ca = Calendar.getInstance();
			ca.set(ca.DATE,ca.get(ca.DATE) - num);
			currdate = ca.getTime();
			String enddate = format.format(currdate);
			return enddate;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	public static void lisdelEror_to_txt(int days) {
		try {
			File folder = new File(filepaht);// 默认目录
	        if (!folder.exists()) {// 如果文件夹不存在
	            System.out.println("目录不存在：" + folder.getAbsolutePath());
	            return;
	        }else{
	        	for (int i = 0; i < (365 - days); i++) {
					int daynum = days + i;
					String keyword = TranLogTxt.getdateDelDay(daynum)+"-";
					//System.out.println(keyword);
					File[] result = TextSearchFile.searchFile(folder, keyword);// 调用方法获得文件数组
					if(result!=null && result.length>0){
					 for (int fi = 0; fi < result.length; fi++) {// 循环显示文件
						 try{
						 File file = result[fi];
						 System.out.println(file.getName());
						 file.delete(); 
						 }catch(Exception ex){ex.printStackTrace();}
				        }
					}
	        	}
	        }
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	// 测试方法

	public static void main(String[] args) throws IOException {
		
	}

}
