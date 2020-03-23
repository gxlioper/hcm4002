package com.hjw.wst;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.commons.lang3.StringUtils;

import com.hjw.DTO.FileDTO;
import com.hjw.interfaces.FTPServer.PdfFtpHelper;
import com.hjw.util.TranLogTxt;
import com.hjw.wst.DTO.ReportPdfDTO;
import com.hjw.wst.DTO.UserDTO;
import com.hjw.wst.model.SyslogModel;
import com.hjw.wst.service.CustomerInfoService;
import com.opensymphony.xwork2.ModelDriven;
import com.synjones.framework.persistence.JdbcQueryManager;
import com.synjones.framework.support.PageSupport;
import com.synjones.framework.web.action.BaseAction;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * pdf报告下载功能
     * @Title:  火箭蛙体检管理系统   
     * @Package com.hjw.wst   
     * @Description:  
     * @author: zwx   
     * @date:   2019年3月30日 上午10:08:43   
     * @version V2.0.0.0
 */
public class DownLoadAction extends BaseAction implements ModelDriven{

	private static final long serialVersionUID = 8217814910220881058L;
	
	private SyslogModel model = new SyslogModel();
	
	private JdbcQueryManager jqm;
	private CustomerInfoService customerInfoService;
	
	private int rows = 15; // easyui每页显示条数
	private String sort;
	private String order;
	@Override
	public Object getModel() {
		return model;
	}
	public int getRows() {
		return rows;
	}
	public void setRows(int rows) {
		this.rows = rows;
	}
	public void setModel(SyslogModel model) {
		this.model = model;
	}
	public void setJqm(JdbcQueryManager jqm) {
		this.jqm = jqm;
	}
	public String getSort() {
		return sort;
	}
	public void setSort(String sort) {
		this.sort = sort;
	}
	public String getOrder() {
		return order;
	}
	public void setOrder(String order) {
		this.order = order;
	}
	public void setCustomerInfoService(CustomerInfoService customerInfoService) {
		this.customerInfoService = customerInfoService;
	}
	
	private InputStream fileInputStream;  //输出流变量  
    private String fileName; //下载文件名  
    
	public InputStream getFileInputStream() {
		return fileInputStream;
	}
	public void setFileInputStream(InputStream fileInputStream) {
		this.fileInputStream = fileInputStream;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	 public String exportPDF(){
    	String logname = "exportPDF";
    	try {
			UserDTO user = (UserDTO) session.get("username");
			JSONArray jsonarray = JSONArray.fromObject(user.getFilelist());
			String path = this.customerInfoService.getCenterconfigByKey("ZIP_TMP_PATH",null).getConfig_value();
			if(path == null) {
				path = "D:\\tmp\\";
			}
			File file = new File(path);
			if(!file.exists()){//判断文件是否存在;
				boolean bol = file.mkdirs();//创建文件夹  
	            if(!bol){
	            	return "no-创建文件夹失败!";
	            }
			}
			
			int count = 0;
			ZipOutputStream out = new ZipOutputStream(new FileOutputStream(path+"\\" + user.getUserid() +".zip"));
			for (int i = 0; i < jsonarray.size(); i++) {
				JSONObject jsonobject = jsonarray.getJSONObject(i);
				String join_date = jsonobject.optString("join_date");
				String dep_name = jsonobject.optString("dep_name");
				String user_name = jsonobject.optString("user_name");
				String exam_num = jsonobject.optString("exam_num");
				String id_num = jsonobject.optString("id_num");
				
				String sql = "SELECT top 1 exam_num,arch_num,report_year,pdf_file,report_path,ftp_server,ftp_port,ftp_user,ftp_password,"
						+ "is_finished,report_date,create_time FROM report_pdf where exam_num='"+exam_num+"' order by report_date desc";
				TranLogTxt.liswriteEror_to_txt(logname, "reportPDF： " +sql);
				PageSupport map = this.jqm.getList(sql, 1, 10000, ReportPdfDTO.class);
				ReportPdfDTO pdf = new ReportPdfDTO();
				if((map!=null)&&(map.getList().size()>0)){
					pdf= (ReportPdfDTO)map.getList().get(0);			
				}
				
				String filename = "";
				if(pdf.getExam_num().length()>0){
					PdfFtpHelper pdfh = null;
					try{
						pdfh=new PdfFtpHelper();
						TranLogTxt.liswriteEror_to_txt(logname, "="+pdf.getFtp_server()+"="+pdf.getFtp_port()+"="+pdf.getFtp_user()+"="+pdf.getFtp_password()+"=");
						boolean pdfconn=  pdfh.login(pdf.getFtp_server(), pdf.getFtp_port(), pdf.getFtp_user(), pdf.getFtp_password());
						if(pdfconn){
							FileDTO fd= pdfh.downloadFile(pdf.getReport_path(), pdf.getPdf_file(),false);
							if (fd.isFlag()) {
								count++;
								filename += join_date;
								if(!StringUtils.isEmpty(dep_name)) {
									filename+=("-"+dep_name);
								}
								filename+=("-"+user_name+"-"+exam_num+"-"+id_num);
								File excelFile = new File(path+"\\" + filename +".pdf");
								out.putNextEntry(new ZipEntry(excelFile.getName()));

								// 读入需要下载的文件的内容，打包到zip文件
								out.write(fd.getBytes(), 0, fd.getBytes().length);
								out.closeEntry();
							}				  
						}
					}catch(Exception ex){
						  ex.printStackTrace();
					}finally{
						if(pdfh!=null){
							pdfh.close(); 
						}
					}
				}
			}
			out.close();
			
			fileInputStream = new FileInputStream(path+"\\" + user.getUserid() +".zip");             //文件流  
			fileName = count+"-report.zip"; //设置下载的文件名  
		} catch (IOException e) {
			e.printStackTrace();
		}
    	return "success";
    }
}
