package com.hjw.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Properties;

/**
 * @author
 * @version
 */
public class SetSystemProperty {
	// 属性文件的路径
	private Properties props = new Properties();
	private String profilepath = "";
	private String profiletmppath="";

	public SetSystemProperty(String propfile,String protmpfile) {
		profilepath = propfile;
		this.profiletmppath=protmpfile;
		try {
			FileInputStream fis = new FileInputStream(profilepath);
			props.load(fis);
			fis.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.exit(-1);
		} catch (IOException e) {
			System.exit(-1);
		}
	}

	/**
	 * 读取属性文件中相应键的值
	 * 
	 * @param key
	 *            主键
	 * @return String
	 */
	public String getKeyValue(String key) {
		String keyvalue="";
		keyvalue = props.getProperty(key);
		return keyvalue;
	}

	/**
	 * 
	 * @param filePath
	 * @param fileName
	 * @param title
	 * @param key
	 * @return
	 */
	public void updateProperties(String title,String key) {
		String pathAddFile = ""; // write file with path and name
		String tempFile = "";
		String strTemp = ""; // use for identify if the modify is success
		pathAddFile = profilepath;
		tempFile = profiletmppath;

		File aFile = new File(pathAddFile);
		File tFile = new File(tempFile);
		if (!aFile.exists()) {
           System.out.println(pathAddFile+"不存在");
		}
		
		try {
			FileReader fr = new FileReader(pathAddFile);
			BufferedReader br = new BufferedReader(fr);
			try {
				FileWriter fw = new FileWriter(tempFile);
				PrintWriter out = new PrintWriter(fw);
				String strLine = br.readLine().trim();
				while (strLine != null) {
					if (strLine.startsWith(title)) {
						strLine = title + "=" + key;
						strTemp = "1";
					}
					out.write(strLine);
					out.println();
					out.flush();
					// read next line
					strLine = br.readLine();
				}
				fw.close();
				out.close();
				// close BufferedReader object
				
				// delete properties file
				
			} catch (Exception ex2) {
				ex2.printStackTrace();
			}
			br.close();
			fr.close();
		} catch (Exception ex1) {
			ex1.printStackTrace();
		}
		
		if(!aFile.delete()){
			System.out.println(pathAddFile+"不能删除");
		}
		// rename temp file to properties file
		if (!tFile.exists()) {
			System.out.println(tempFile+"不存在");
		}
		tFile.renameTo(aFile);
		if (!strTemp.equals("1")) {
			System.out.println("配置文件重新命名时错误");
		}

	}

}