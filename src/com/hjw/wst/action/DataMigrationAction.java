package com.hjw.wst.action;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.dispatcher.multipart.MultiPartRequestWrapper;
import org.apache.struts2.dispatcher.multipart.UploadedFile;

import com.hjw.interfaces.DBServer.SqlServerDatabaseSource;
import com.hjw.util.Base64;
import com.hjw.util.DateTimeUtil;
import com.hjw.util.FileUtils;
import com.hjw.util.PdfFtpHelper;
import com.hjw.util.SQLiteDatabaseSource;
import com.hjw.wst.DTO.DataBaseConfigDTO;
import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.wst.DTO.UserDTO;
import com.hjw.wst.domain.ExamInfo;
import com.hjw.wst.domain.ViewExamImage;
import com.hjw.wst.domain.ViewExamImageBase64;
import com.hjw.wst.model.DataMigrationModel;
import com.hjw.wst.service.CommService;
import com.hjw.wst.service.CustomerInfoService;
import com.hjw.wst.service.DataMigrationService;
import com.opensymphony.xwork2.ModelDriven;
import com.synjones.framework.exception.WebException;
import com.synjones.framework.web.action.BaseAction;

import net.sf.json.JSONObject;

/**
 * 体检车数据迁移功能
     * @Title:  火箭蛙体检管理系统   
     * @Package com.hjw.wst.action   
     * @Description:  
     * @author: dq     
     * @date:   2019年12月27日 上午10:45:17   
     * @version V2.0.0.0
 */
public class DataMigrationAction extends BaseAction implements ModelDriven{

	private static final long serialVersionUID = 8217814910220881058L;
	private DataMigrationModel model = new DataMigrationModel();
	private DataMigrationService dataMigrationService;
	private CustomerInfoService customerInfoService;
	private CommService commService;
	private InputStream inputStream;  //输出流变量  
    private String fileName; //下载文件名  
	private int rows = 15; // easyui每页显示条数
	private String sort;
	private String order;
	
	public void setDataMigrationService(DataMigrationService dataMigrationService) {
		this.dataMigrationService = dataMigrationService;
	}
	
	public void setCustomerInfoService(CustomerInfoService customerInfoService) {
		this.customerInfoService = customerInfoService;
	}

	public void setCommService(CommService commService) {
		this.commService = commService;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
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

	/**
	 * 1765 获取数据库/ftp 配置页面
	     * @Title: getDataBaseConfigPage   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String getDataBaseConfigPage() throws WebException{
		return SUCCESS;
	}
	
	/**
	 * 1766 获取数据库/ftp 配置信息
	     * @Title: getDataBaseConfigList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String getDataBaseConfigList() throws WebException{
		List<DataBaseConfigDTO> list = this.dataMigrationService.getDataBaseConfigList();
		this.outJsonResult(list);
		return NONE;
	}
	
	/**
	 * 1767 获取数据库/ftp配置编辑页面
	     * @Title: getDataBaseConfigEditPage   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String getDataBaseConfigEditPage() throws WebException{
		DataBaseConfigDTO dto = this.dataMigrationService.getDataBaseConfigOne(model.getType());
		this.model.setDatabase_url(dto.getDatabase_url());
		this.model.setDatabase_port(dto.getDatabase_port());
		this.model.setDatabase_uame(dto.getDatabase_uame());
		this.model.setUsername(dto.getUsername());
		this.model.setPassword(dto.getPassword());
		return SUCCESS;
	}
	
	/**
	 * 1768 保存数据库/ftp配置信息
	     * @Title: saveDataBaseConfig   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String saveDataBaseConfig() throws WebException{
		DataBaseConfigDTO dto = new DataBaseConfigDTO();
		dto.setType(this.model.getType());
		dto.setDatabase_url(this.model.getDatabase_url());
		dto.setDatabase_port(this.model.getDatabase_port());
		dto.setDatabase_uame(this.model.getDatabase_uame());
		dto.setUsername(this.model.getUsername());
		dto.setPassword(this.model.getPassword());
		this.message = this.dataMigrationService.saveDataBaseConfig(dto);
		this.outJsonStrResult(this.message);
		return NONE;
	}

	/**
	 * 1762 获取数据导入导出页面
	     * @Title: getDataMigrationPage   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String getDataMigrationPage() throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		this.model.setS_join_date(DateTimeUtil.getDate2());
		this.model.setE_join_date(DateTimeUtil.getDate2());
		String IS_OUT_REG = "N";
		try {
			IS_OUT_REG = this.customerInfoService.getCenterconfigByKey("IS_OUT_REG", user.getCenter_num()).getConfig_value().trim();
		} catch (Exception e) {
		}
		this.model.setIs_out_reg(IS_OUT_REG);
		String IS_OUT_DIRECTLY = "N";
		try {
			IS_OUT_DIRECTLY = this.customerInfoService.getCenterconfigByKey("IS_OUT_DIRECTLY", user.getCenter_num()).getConfig_value().trim();
		} catch (Exception e) {
		}
		
		if("Y".equals(IS_OUT_DIRECTLY)){//直接连接模式
			return "success_con";
		}else{//离线文件导入导出模式
			return "success_file";
		}
	}
	
	/**
	 * 1763 获取需要导出的数据列表
	     * @Title: getDataMigrationList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String getDataMigrationList() throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		PageReturnDTO page = this.dataMigrationService.getDataMigrationList(model,this.rows, this.getPage(),this.sort,this.order,user);
		this.outJsonResult(page);
		return NONE;
	}
	
	/**
	 * 1764直连方式数据导入导出
	     * @Title: importDataMigrationDirectlyConnected   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String importDataMigrationDirectlyConnected() throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		String picpath = this.commService.getDatadis("TPLJ").getName();
		//检查院内数据库连接配置
		DataBaseConfigDTO dto = this.dataMigrationService.getDataBaseConfigOne(0);
		if(dto == null){
			this.outJsonStrResult("con_error-数据库配置不存在，请检查!");
			return NONE;
		}
		Connection connection = null;
		String url = "jdbc:sqlserver://"+dto.getDatabase_url()+":"+dto.getDatabase_port()+";DatabaseName="+dto.getDatabase_uame();
		try {
			connection = SqlServerDatabaseSource.getConnection(url, dto.getUsername(), dto.getPassword());
		} catch (Exception e) {
			e.printStackTrace();
			this.outJsonStrResult("con_error-数据库配置错误，请检查!");
			return NONE;
		}
		//检查院内ftp连接配置
		DataBaseConfigDTO dtof = this.dataMigrationService.getDataBaseConfigOne(1);
		PdfFtpHelper ftp = null;
		if(dtof == null){
			this.outJsonStrResult("ftp_error-FTP配置不存在，请检查!");
			return NONE;
		}else{
			ftp = PdfFtpHelper.getInstance();
			try {
				boolean _login = ftp.login(dtof.getDatabase_url(), Integer.parseInt(dtof.getDatabase_port()),dtof.getUsername(),dtof.getPassword());
				if(!_login){
					this.outJsonStrResult("ftp_error-FTP配置错误，请检查!");
					return NONE;
				}
			} catch (Exception e) {
				e.printStackTrace();
				this.outJsonStrResult("ftp_error-FTP配置错误，请检查!");
				return NONE;
			}
		}
		//查询单位、批次、分组信息
		List<Map<String, Object>> comlist = this.dataMigrationService.getDataMigrationCompany(this.model.getExam_nums(),user.getCenter_num());
		
		//同步保存单位批次分组信息
		for (int i = 0; i < comlist.size(); i++) {
			this.dataMigrationService.saveDataMigrationCom(comlist.get(i), user, connection, dto);
		}
		
		//外检车库查出需要导出的数据
		List<Map<String,Object>> list = this.dataMigrationService.getNeedDataMigration(this.model.getExam_nums(),user.getCenter_num());
		
		int count = 0;
		for (int i = 0; i < list.size(); i++) {
			List<ViewExamImage> exam_view_image = new ArrayList<ViewExamImage>();
			exam_view_image.addAll((List<ViewExamImage>) list.get(i).get("exam_view_image"));
			List<ExamInfo> examinfo = (List<ExamInfo>) list.get(i).get("examinfo");
			String exam_num = examinfo.get(0).getExam_num();
			boolean flag = this.dataMigrationService.saveDataMigration(list.get(i), user, connection, dto);
			if(flag){
				//处理图片文件
				for (int j = 0; j < exam_view_image.size(); j++) {
					String imgFile = exam_view_image.get(j).getImage_path();
					String[] file_path = imgFile.split("/");
					String ftpDirName="";
					String ftpFileName="";
					String path = picpath + "\\";
					for (int k = 0; k < file_path.length; k++) {
						if(!"".equals(file_path[k])){
							if(file_path[k].indexOf(".") > -1){
								ftpFileName = file_path[k];
							}else{
								ftpDirName += file_path[k] + "/";
								path += file_path[k] + "\\";
							}
						}
					}
					FileInputStream in = null;
					try
			        {
			            in = new FileInputStream(path + ftpFileName);
			            ftp.uploadFile(in, ftpDirName, ftpFileName);
			        }
			        catch (IOException e){
			            e.printStackTrace();
			        }
				}
				
				//体检车保存导出记录表
				this.dataMigrationService.saveExportExaminfo(exam_num);
				count ++;
			}
		}
		
		try {
			SqlServerDatabaseSource.close(connection);
		} catch (Exception e) {
			e.printStackTrace();
		}
		ftp.close();
		this.outJsonStrResult("ok-本次共导入("+list.size()+")人，其中成功导入("+count+")人、失败("+(list.size() - count)+")人。");
		return NONE;
	}
	/**
	 * 1769 导出数据到数据库文件
	     * @Title: exportDataMigrationDb   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String exportDataMigrationDb() throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		String picpath = this.commService.getDatadis("TPLJ").getName();
		String sqlite_path = "D:";
		String sqlite_name = "sqlite_peis.db";
		try {
			sqlite_path = this.customerInfoService.getCenterconfigByKey("SQLITE_PATH", user.getCenter_num()).getConfig_value().trim();
		} catch (Exception e) {
		}
		File f_dir = new File(sqlite_path);
        if (!f_dir.exists()) {
            f_dir.mkdirs();
        }

		FileUtils.delFile(sqlite_path +"/"+sqlite_name);
		Connection connection = null;
		try {
			connection = SQLiteDatabaseSource.getConnection(sqlite_path +"/"+sqlite_name);
			//创建数据库表
			this.dataMigrationService.createDataMigrationTable(connection);
			
			//查询单位、批次、分组信息
			List<Map<String, Object>> comlist = this.dataMigrationService.getDataMigrationCompany(this.model.getExam_nums(),user.getCenter_num());
					
			//同步保存单位批次分组信息
			for (int i = 0; i < comlist.size(); i++) {
				this.dataMigrationService.saveDataMigrationComDb(comlist.get(i), user, connection);
			}
					
			//外检车库查出需要导出的数据
			List<Map<String,Object>> list = this.dataMigrationService.getNeedDataMigration(this.model.getExam_nums(),user.getCenter_num());
					
			int count = 0;
			for (int i = 0; i < list.size(); i++) {
				List<ViewExamImage> exam_view_image = (List<ViewExamImage>) list.get(i).get("exam_view_image");
				List<ViewExamImageBase64> imagebase64 = new ArrayList<ViewExamImageBase64>();
				List<ExamInfo> examinfo = (List<ExamInfo>) list.get(i).get("examinfo");
				String exam_num = examinfo.get(0).getExam_num();
				for (int j = 0; j < exam_view_image.size(); j++) {
					ViewExamImageBase64 base = new ViewExamImageBase64();
					base.setExam_num(exam_view_image.get(j).getExam_num());
					base.setImage_path(exam_view_image.get(j).getImage_path());
					
					String imgFile = picpath + exam_view_image.get(j).getImage_path();
			        InputStream in = null;
			        byte[] data = null;
					try
			        {
			            in = new FileInputStream(imgFile);
			            data = new byte[in.available()];
			            in.read(data);
			            in.close();
			        }
			        catch (IOException e)
			        {
			            e.printStackTrace();
			        }
					base.setBase64(Base64.base64Encode(data));
					imagebase64.add(base);
				}
				list.get(i).put("imagebase64", imagebase64);
				if(i == 0){
					list.get(i).put("is_out_reg", model.getIs_out_reg());
					list.get(i).put("center_num", user.getCenter_num());
				}
				boolean flag = this.dataMigrationService.saveDataMigrationDb(list.get(i), user, connection);
				if(flag){
					//体检车保存导出记录表
					this.dataMigrationService.saveExportExaminfo(exam_num);
					count ++;
				}
			}
			
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		try {
			SQLiteDatabaseSource.close(connection);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			inputStream = new FileInputStream(sqlite_path +"/"+sqlite_name);//文件流  
			fileName = sqlite_name; //设置下载的文件名  
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	/**
	 *1770 导入上传数据库文件
	     * @Title: importDataMigrationDb   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String importDataMigrationDb() throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		response.setContentType("text/html;charset=UTF-8");
		String picpath = this.commService.getDatadis("TPLJ").getName();
		String sqlite_path = "D:";
		String sqlite_name = "sqlite_peis.db";
		JSONObject json = new JSONObject();
		try {
			sqlite_path = this.customerInfoService.getCenterconfigByKey("SQLITE_PATH", user.getCenter_num()).getConfig_value().trim();
		} catch (Exception e) {
		}
		String IS_OUT_REG = "N";
		try {
			IS_OUT_REG = this.customerInfoService.getCenterconfigByKey("IS_OUT_REG", user.getCenter_num()).getConfig_value().trim();
		} catch (Exception e) {
		}
		File f_dir = new File(sqlite_path);
        if (!f_dir.exists()) {
            f_dir.mkdirs();
        }
        MultiPartRequestWrapper multiWrapper = (MultiPartRequestWrapper) ServletActionContext.getRequest();
        UploadedFile[] files = multiWrapper.getFiles("importDb");
        if ((files != null) && (files.length > 0)) {
            try {
				InputStream sis1 = new FileInputStream(files[0].getAbsolutePath());
				File file = new File(sqlite_path +"/"+sqlite_name);
                file.createNewFile();
                BufferedInputStream bis=new BufferedInputStream(sis1);
                BufferedOutputStream bos=new BufferedOutputStream(new FileOutputStream(sqlite_path +"/"+sqlite_name));
                int len;
                while((len=bis.read())!=-1) {
                    bos.write(len);
                }
                bis.close();
                bos.close();
                
			} catch (IOException e) {
				e.printStackTrace();
				json.put("msg", "数据导入失败!");
                json.put("state", "N");
			}
        }
        Connection connection = null;
        try {
			connection = SQLiteDatabaseSource.getConnection(sqlite_path +"/"+sqlite_name);
			String isExport = this.dataMigrationService.vieryExportDb(connection, IS_OUT_REG, user.getCenter_num());
			if("ok".equals(isExport)){
				//查询单位、批次、分组信息
				List<Map<String, Object>> comlist = this.dataMigrationService.getDataMigrationCompanyByCon(connection);
						
				//同步保存单位批次分组信息
				for (int i = 0; i < comlist.size(); i++) {
					this.dataMigrationService.saveDataMigrationCom(comlist.get(i), user, null, null);
				}
						
				//外检车库查出需要导出的数据
				List<Map<String,Object>> list = this.dataMigrationService.getNeedDataMigrationByCon(connection);
						
				int count = 0;
				for (int i = 0; i < list.size(); i++) {
					boolean flag = this.dataMigrationService.saveDataMigration(list.get(i), user, null, null);
					if(flag){
						List<ViewExamImageBase64> imagebase64 = (List<ViewExamImageBase64>) list.get(i).get("imagebase64");
						for (int j = 0; j < imagebase64.size(); j++) {
							String imgFile = imagebase64.get(j).getImage_path();
							String[] file_path = imgFile.split("/");
							String ftpDirName=picpath+"\\";
							String ftpFileName="";
							for (int k = 0; k < file_path.length; k++) {
								if(!"".equals(file_path[k])){
									if(file_path[k].indexOf(".") > -1){
										ftpFileName = file_path[k];
									}else{
										ftpDirName += file_path[k] + "\\";
									}
								}
							}
							f_dir = new File(ftpDirName);
							if (!f_dir.exists()) {
			                    f_dir.mkdirs();
			                }
							if(imagebase64.get(j).getBase64() != null){
								byte[] bmpfiledata64 = Base64.base64Decode(imagebase64.get(j).getBase64());
								File file = new File(ftpDirName+ftpFileName);
					            file.createNewFile();
					            FileOutputStream fos = new FileOutputStream(ftpDirName+ftpFileName);
					            fos.write(bmpfiledata64);
					            fos.close();
							}
						}
						count ++;
					}
				}
				json.put("msg", "ok-本次共导入("+list.size()+")人，其中成功导入("+count+")人、失败("+(list.size() - count)+")人。");
		        json.put("state", "Y");
			}else{
				json.put("msg", isExport);
	            json.put("state", "N");
			}
		} catch (Exception e) {
			e.printStackTrace();
			json.put("msg", "数据导入失败!");
            json.put("state", "N");
		}
        try {
			SQLiteDatabaseSource.close(connection);
		} catch (Exception e) {
			e.printStackTrace();
		}
        PrintWriter out;
        try {
            out = response.getWriter();
            out.print(json);
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
		return NONE;
	}

	public DataMigrationModel getModel() {
		return model;
	}

	public void setModel(DataMigrationModel model) {
		this.model = model;
	}
}