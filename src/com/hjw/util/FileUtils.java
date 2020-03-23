package com.hjw.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.LinkedList;
import java.util.List;

import org.apache.struts2.ServletActionContext;

public class FileUtils {
	
	public static int  FILEBYTE= (1024 *200 );
	
	public static void inputstreamtofile(InputStream ins,File file) {
		  try {
		   OutputStream os = new FileOutputStream(file);
		   int bytesRead = 0;
		   byte[] buffer = new byte[8192];
		   while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
		    os.write(buffer, 0, bytesRead);
		   }
		   os.close();
		   ins.close();
		  } catch (Exception e) {
		   e.printStackTrace();
		  }
		 }
	/**
	 * 复制单个文件
	 * 
	 * @param oldPathFile
	 *            准备复制的文件源
	 * @param newPathFile
	 *            拷贝到新绝对路径带文件名
	 * @return
	 * @throws IOException 
	 */
	public static boolean copyFile(File oldPathFile, String newPathFile) throws IOException {
	
			int bytesum = 0;
			int byteread = 0;

			if (oldPathFile.exists()) { // 文件存在时
				InputStream inStream = new FileInputStream(oldPathFile); // 读入原文件
			
				FileOutputStream fs = new FileOutputStream(newPathFile);
				byte[] buffer = new byte[FILEBYTE];
				while ((byteread = inStream.read(buffer)) != -1) {
					bytesum += byteread; // 字节数 文件大小
					fs.write(buffer, 0, byteread);
				}
				fs.flush();
				fs.close();
				inStream.close();
				return true;
			}else{
				return false;
			}
	}
	/**
	 * 
	 *  Function:获取后缀
	 * 
	 *  @author 罗志峰  DateTime Oct 25, 2012 3:37:24 PM
	 *  @param pictype
	 *  @return
	 */
	public static String getPictype(String pictype) {
		return pictype.substring(pictype.lastIndexOf("."));
	}
	/**
	 * 单文件上传
	 * 
	 */
	public static String SingleFileUpload(File file, String fileFileName,String path) {
		if (file == null) {

		} else {
			String filename = fileFileName;
			String absolutepath = getFilePath(path);
			if (createDirectory(absolutepath)) {
				try {
					copyFile(file, absolutepath + "/" + filename);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			return path + filename;
		}
		return null;
	}
	/**
	 * 多文件上传
	 * 
	 */
	public static List<String> ManyFileUpload(List<File> files, List<String> fileNames,String path) {
		List<String> list = new LinkedList<String>();
		if(files!=null&&files.size()>0){
			for (int i = 0; i < files.size(); i++) {
				if(files.get(i)!=null){
					String filename = Timeutils.getFileData()+i+getPictype(fileNames.get(i));
					String absolutepath = getFilePath(path);
					if (createDirectory(absolutepath)) {
						try {
							copyFile(files.get(i), absolutepath + "/" + filename);
							list.add(path+filename);
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
		
		return list;
	}
	private static boolean createDirectory(String Directory) {

		File directory = new File(Directory);
		if (!directory.exists()) {
			if (directory.mkdirs())
				return true;
		}
		return true;

	}
	public static String getFilePath(String FILE_DEFAULT_PATH) {
		File file = new File(FILE_DEFAULT_PATH);
		if (!file.exists()) {
			file.mkdirs();
		}
		return ServletActionContext.getRequest().getRealPath(FILE_DEFAULT_PATH)
				.replace("\\", "/");
	}
	
	/**
	 * 删除文件
	 * 
	 * @param filePathAndName
	 *            文本文件完整绝对路径及文件名
	 * @return Boolean 成功删除返回true遭遇异常返回false
	 */
	public static boolean delFile(String filePathAndName) {
		filePathAndName=filePathAndName.replace('\\', '/');
		System.out.println(filePathAndName);
		boolean bea = false;
		try {
			String filePath = filePathAndName;
			File myDelFile = new File(filePath);
			if (myDelFile.exists()) {
			   bea = myDelFile.delete();
			} else {
				bea = false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bea;
	}
	
	/**
	 * 根据路径获取该路径下的所有文件
	     * @Title: findSysFile   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param path
	     * @param: @return      
	     * @return: File[]      
	     * @throws
	 */
	public static File[] findSysFile(String path){
        File file=new File(path);
        if(file.exists()){
	        File[] arr=file.listFiles();
	        return arr;
        }
        return null;
    }
	/**
	 * 根据文件大小换算文件的显示大小
	     * @Title: getPrintSize   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param size
	     * @param: @return      
	     * @return: String      
	     * @throws
	 */
    public static String getPrintSize(long size) {
    	float GB = 1024 * 1024 * 1024;//定义GB的计算常量
    	float MB = 1024 * 1024;//定义MB的计算常量
    	float KB = 1024;//定义KB的计算常量
        if (size/GB >= 1) {
        	int a =(int) (size/GB * 10);
        	return String.valueOf((a / 10)) + "."  
            + String.valueOf((a % 10)) + "GB";  
        }else if(size/MB >= 1){
        	int a =(int) (size/MB * 10);
        	return String.valueOf((a / 10)) + "."  
            + String.valueOf((a % 10)) + "MB";
        }else if (size/KB >= 1) {
        	int a =(int) (size/KB * 10);
        	return String.valueOf((a / 10)) + "."  
            + String.valueOf((a % 10)) + "KB";  
        }else {  
        	return size + "B"; 
        }  
    }  
    

    /**
     * 根据文件流判断图片类型
     * @param fis
     * @return jpg/png/gif/bmp
     */
	public static String getPicType(byte[] fis) {
		String TYPE_GIF = "gif";
		String TYPE_PNG = "png";
		String TYPE_BMP = "bmp";
		String TYPE_JPG = "jpg";
		String TYPE_UNKNOWN = "unknown";
		// 读取文件的前几个字节来判断图片格式
		try {
			byte[] b = new byte[4];
			for (int i = 0; i < 4; i++) {
				b[i] = fis[i];
			}

			String type = StringUtil.bytesToHexString(b).toUpperCase();
			if (type.contains("FFD8FF")) {
				return TYPE_JPG;
			} else if (type.contains("89504E47")) {
				return TYPE_PNG;
			} else if (type.contains("47494638")) {
				return TYPE_GIF;
			} else if (type.contains("424D")) {
				return TYPE_BMP;
			} else {
				return TYPE_UNKNOWN;
			}
		} catch (Exception ex) {
			return TYPE_UNKNOWN;
		}
	}

}
