package com.hjw.wst;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUpload;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.RequestContext;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.servlet.ServletRequestContext;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.struts2.dispatcher.multipart.MultiPartRequestWrapper;
import org.apache.struts2.dispatcher.multipart.UploadedFile;
import org.hsqldb.lib.StringUtil;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.hjw.util.Base64;
import com.hjw.util.DateTimeUtil;
import com.hjw.util.Timeutils;
import com.hjw.util.ValidateTime;
import com.hjw.util.ValidateUtil;
import com.hjw.wst.domain.Batch;
import com.hjw.wst.domain.CompanyInfo;
import com.hjw.wst.domain.ImpCustomerInfo;
import com.hjw.wst.domain.ImpCustomerInfo.InfoType;
import com.hjw.wst.service.BatchService;
import com.hjw.wst.service.CommService;
import com.hjw.wst.service.CompanyService;
import com.hjw.wst.service.CustomerInfoService;

@SuppressWarnings("serial")
public class DjdUpload extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final long FILE_MAX_SIZE = 1024 * 1024 * 200;
	private static final String FILE_SAVE_PATH = "E:\\";
  	/**
     * @see HttpServlet#HttpServlet()
     */
    public DjdUpload() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		//解决中文乱码问题
		//response.setContentType("textml;charset=utf-8");
		//response.getWriter().write("这是第一个servlet程序。当前时间为："+new Date());
		System.out.println("doGet,访问时间："+new Date());
		//doPost(request,response);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		WebApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(this.getServletContext());
		CommService commService = (CommService) context.getBean("commService");
		CustomerInfoService customerInfoService = (CustomerInfoService) context.getBean("customerInfoService");
		String exam_num = request.getParameter("exam_num");
		String userid = request.getParameter("userid");
		if (StringUtil.isEmpty(exam_num)) {
			response.getWriter().write("体检编号无效");
		} else if (StringUtil.isEmpty(userid)) {
			response.getWriter().write("操作者无效，请重新登录");
		} else {

			MultiPartRequestWrapper multiWrapper = (MultiPartRequestWrapper) request;
			Enumeration en = multiWrapper.getFileParameterNames();
			File file = null;
			String[] fileNames = null;
			long size = 0;
			if (en == null) {
				response.getWriter().write("无效文件！");
			} else {
				while (en.hasMoreElements()) {
					String inputName = (String) en.nextElement();
					fileNames = multiWrapper.getFileNames(inputName);
					UploadedFile[] file1 = null;
					file1 = multiWrapper.getFiles(inputName);
					file = new File(file1[0].getAbsolutePath());
					size = file1[0].length();
					// 打印文件大小
					//System.out.println("文件大小：" + size + " ");
					if (size > 20971520) {
						response.getWriter().write("文件大小不能超过20M！");
					}
				}

				String saveName = exam_num + ".jpg";
                String datetime = DateTimeUtil.getDate().substring(0, 4);
				String picpath = commService.getDatadis("TPLJ").getName();
				String path = picpath + "\\";
				File f_dir = new File(path);
				if (!f_dir.exists()) {
					f_dir.mkdirs();
				}
				f_dir = new File(path + "DJDimage\\" + datetime + "\\");
				if (!f_dir.exists()) {
					f_dir.mkdirs();
				}
				InputStream input = null;
				OutputStream output = null;
				try {
					input = new FileInputStream(file);
					output = new FileOutputStream(f_dir + "\\" + saveName);
					byte[] buf = new byte[1024];
					int bytesRead;
					while ((bytesRead = input.read(buf)) > 0) {
						output.write(buf, 0, bytesRead);
					}
					String urlpath="DJDimage/" + datetime + "/"+saveName;
					customerInfoService.addDJDimage(Long.valueOf(userid),urlpath,exam_num);
				}catch (Exception ex){
					response.getWriter().write("上传失败");
				} finally {
					input.close();
					output.close();
				}
				// 解决中文乱码问题
				// response.setContentType("textml;charset=utf-8");
				response.getWriter().write("上传成功");
			}
		}
	}
}

