package com.hjw.wst;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts2.ServletActionContext;

import com.hjw.util.DateTimeUtil;
import com.hjw.util.HttpUtil;
import com.hjw.wst.DTO.ExamInfoUserDTO;
import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.wst.DTO.UserDTO;
import com.hjw.wst.domain.CompanyInfo;
import com.hjw.wst.service.CompanyService;
import com.hjw.wst.service.CustomerInfoService;
import com.opensymphony.xwork2.ModelDriven;
import com.synjones.framework.web.action.BaseAction;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class ExcelExportAction extends BaseAction implements ModelDriven{
	
	private String filelist;//需要导出的文件内容
	private InputStream excelStream;  //输出流变量  
    private String excelFileName; //下载文件名  
    private String excelSheet;    //sheet名
    private String excelTitle;  //文件表头
    private String excelurl;
    private String queryParams;
    
	public String getExcelurl() {
		return excelurl;
	}

	public void setExcelurl(String excelurl) {
		this.excelurl = excelurl;
	}

	public String getQueryParams() {
		return queryParams;
	}

	public void setQueryParams(String queryParams) {
		this.queryParams = queryParams;
	}

	public String getExcelSheet() {
		return excelSheet;
	}

	public void setExcelSheet(String excelSheet) {
		this.excelSheet = excelSheet;
	}

	public String getExcelTitle() {
		return excelTitle;
	}

	public void setExcelTitle(String excelTitle) {
		this.excelTitle = excelTitle;
	}

	public String getFilelist() {
		return filelist;
	}

	public void setFilelist(String filelist) {
		this.filelist = filelist;
	}

	@Override
	public Object getModel() {
		return null;
	}
	
	private long batch_id;
	private long company_id;
	private long group_id;
	private String app_type = "1";
	
	private CustomerInfoService customerInfoService;
	private CompanyService companyService;
	
	public void setCustomerInfoService(CustomerInfoService customerInfoService) {
		this.customerInfoService = customerInfoService;
	}
	
	public void setCompanyService(CompanyService companyService) {
		this.companyService = companyService;
	}

	public long getBatch_id() {
		return batch_id;
	}

	public void setBatch_id(long batch_id) {
		this.batch_id = batch_id;
	}

	public long getCompany_id() {
		return company_id;
	}

	public void setCompany_id(long company_id) {
		this.company_id = company_id;
	}

	public long getGroup_id() {
		return group_id;
	}

	public void setGroup_id(long group_id) {
		this.group_id = group_id;
	}

	public String getApp_type() {
		return app_type;
	}

	public void setApp_type(String app_type) {
		this.app_type = app_type;
	}

	/** 导出Excel*/  
    public String exportExcel() {  
        try {
        	CompanyInfo company = this.companyService.findComByID(company_id);
            //第一步，创建一个webbook，对应一个Excel文件  
            HSSFWorkbook wb = new HSSFWorkbook();  
            //第二步，在webbook中添加一个sheet，对应Excel文件中的 sheet  
            HSSFSheet sheet = wb.createSheet(company.getCom_Name());  
            //第三步，在sheet中添加表头第0行，注意老版本poi对Excel的行数列数有限制  
            HSSFRow row = sheet.createRow(0);  
            //第四步，创建单元格样式：居中  
            HSSFCellStyle style = wb.createCellStyle();  
            style.setAlignment(HSSFCellStyle.ALIGN_CENTER);  
            //第五步，创建表头单元格，并设置样式  
            HSSFCell cell;  
  
            cell = row.createCell(0);  
            cell.setCellValue("档案号");  
            cell.setCellStyle(style);  
  
            cell = row.createCell(1);  
            cell.setCellValue("体检号");  
            cell.setCellStyle(style);  
  
            cell = row.createCell(2);  
            cell.setCellValue("身份证号");  
            cell.setCellStyle(style);  
  
            cell = row.createCell(3);  
            cell.setCellValue("工号");  
            cell.setCellStyle(style);  
  
            cell = row.createCell(4);  
            cell.setCellValue("姓名");  
            cell.setCellStyle(style);  
  
            cell = row.createCell(5);  
            cell.setCellValue("性别");  
            cell.setCellStyle(style);
            
            cell = row.createCell(6);  
            cell.setCellValue("婚否");  
            cell.setCellStyle(style);
            
            cell = row.createCell(7);  
            cell.setCellValue("年龄");  
            cell.setCellStyle(style);
            
            cell = row.createCell(8);  
            cell.setCellValue("电话");  
            cell.setCellStyle(style);
            
            cell = row.createCell(9);  
            cell.setCellValue("分组名称");  
            cell.setCellStyle(style);
            
            cell = row.createCell(10);  
            cell.setCellValue("套餐");  
            cell.setCellStyle(style);
            
            cell = row.createCell(11);  
            cell.setCellValue("体检日期");  
            cell.setCellStyle(style);
            
            cell = row.createCell(12);  
            cell.setCellValue("职务");  
            cell.setCellStyle(style);
            
            cell = row.createCell(13);  
            cell.setCellValue("部门");  
            cell.setCellStyle(style);
            
            cell = row.createCell(14);  
            cell.setCellValue("检验(Y/N)");  
            cell.setCellStyle(style);
            
            cell = row.createCell(15);  
            cell.setCellValue("检查(Y/N)");  
            cell.setCellStyle(style);
            
            cell = row.createCell(16);  
            cell.setCellValue("导检单/条码/报到");  
            cell.setCellStyle(style);
            
            cell = row.createCell(17);  
            cell.setCellValue("其他");  
            cell.setCellStyle(style);
  
            //第六步，写入实体数据，实际应用中这些数据从数据库得到  
            UserDTO user = (UserDTO) session.get("username");
            PageReturnDTO map = this.customerInfoService.getExamInfoUserList(company_id,batch_id,0,group_id,null,null,null,null,null,null,null,null,null,null,null,0,null,user.getCenter_num(),app_type,100000,1,null,null,user);
            if ((map != null) && (map.getRows() != null) && (map.getRows().size() > 0)) {
            	for(int i=0;i<map.getRows().size();i++){
            		ExamInfoUserDTO eu = (ExamInfoUserDTO) map.getRows().get(i);
            		
            		row = sheet.createRow(i+1);
            		
            		row.createCell(0).setCellValue(eu.getArch_num());  
                    row.createCell(1).setCellValue(eu.getExam_num());  
                    row.createCell(2).setCellValue(eu.getId_num());  
                    row.createCell(3).setCellValue(eu.getEmployeeID());  
                    row.createCell(4).setCellValue(eu.getUser_name());  
                    row.createCell(5).setCellValue(eu.getSex());
                    row.createCell(6).setCellValue(eu.getIs_marriage());
                    row.createCell(7).setCellValue(eu.getAge());
                    row.createCell(8).setCellValue(eu.getPhone());
                    row.createCell(9).setCellValue(eu.getGroup_name());
                    row.createCell(10).setCellValue(eu.getSet_name());
                    row.createCell(11).setCellValue(eu.getRegister_date());
                    row.createCell(12).setCellValue(eu.getPosition());
                    row.createCell(13).setCellValue(eu.getDep_name());
                    row.createCell(14).setCellValue(eu.getLis());
                    row.createCell(15).setCellValue(eu.getPacs());
                    row.createCell(16).setCellValue(eu.getRemark1());
                    row.createCell(17).setCellValue(eu.getOthers());
            	}
            }
            //第七步，将文件存到流中  
            ByteArrayOutputStream os = new ByteArrayOutputStream();  
            wb.write(os);  
            byte[] fileContent = os.toByteArray();  
            ByteArrayInputStream is = new ByteArrayInputStream(fileContent);  
  
            excelStream = is;             //文件流  
            excelFileName = "report.xls"; //设置下载的文件名  
        }  
        catch(Exception e) {  
            e.printStackTrace();  
        }  
  
        return "success";  
    }
    
    /**
     * 先将列表数据保存在session中
         * @Title: saveDatagridData   
         * @Description: TODO(这里用一句话描述这个方法的作用)   
         * @param: @return      
         * @return: String      
         * @throws
     */
    public String saveDatagridData(){
    	UserDTO user = (UserDTO) session.get("username");
    	user.setFilelist(this.filelist);
    	return NONE;
    }
  
    /**
     * 表格数据excel导出
         * @Title: datagridExportExcel   
         * @Description: TODO(这里用一句话描述这个方法的作用)   
         * @param: @return      
         * @return: String      
         * @throws
     */
    public String datagridExportExcel(){
    	try {
			//第一步，创建一个webbook，对应一个Excel文件  
			HSSFWorkbook wb = new HSSFWorkbook();  
			//第二步，在webbook中添加一个sheet，对应Excel文件中的 sheet  
			HSSFSheet sheet = wb.createSheet("work1");  
			//第四步，创建单元格样式：居中  
			HSSFCellStyle style = wb.createCellStyle();  
			style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			UserDTO user = (UserDTO) session.get("username");
			JSONArray jsonarray = JSONArray.fromObject(user.getFilelist());
			for (int i = 0; i < jsonarray.size(); i++) {
				JSONObject jsonobject = jsonarray.getJSONObject(i);
				Iterator keys = jsonobject.keys();
				
				HSSFRow row = sheet.createRow(i);
				//第五步，创建表头单元格，并设置样式  
			    HSSFCell cell;
				int count = 0;
				while(keys.hasNext()){
					String key = keys.next().toString();  
			        String value = jsonobject.optString(key);
			        cell = row.createCell(count);
			        cell.setCellValue(value);  
			        if(i == 0){
			        	cell.setCellStyle(style);
					}
			        count ++;
				}
			}
			
			//第七步，将文件存到流中  
			ByteArrayOutputStream os = new ByteArrayOutputStream();  
			wb.write(os);  
			byte[] fileContent = os.toByteArray();  
			ByteArrayInputStream is = new ByteArrayInputStream(fileContent);  

			excelStream = is;             //文件流  
			excelFileName = "report.xls"; //设置下载的文件名  
		} catch (IOException e) {
			e.printStackTrace();
		}
    	return "success";
    }
    
    /**
     * 通用列表导出excel
         * @Title: datagridExportExcelNew   
         * @Description: TODO(这里用一句话描述这个方法的作用)   
         * @param: @return      
         * @return: String      
         * @throws
     */
    public String datagridExportExcelNew(){
    	try {
    		System.out.println("开始时间："+DateTimeUtil.getDate3());
    		//转换中文参数
//    		excelFileName = URLDecoder.decode(excelFileName, "UTF-8");
        	excelSheet = URLDecoder.decode(excelSheet, "UTF-8");
        	excelTitle = URLDecoder.decode(excelTitle, "UTF-8");
        	excelurl = URLDecoder.decode(excelurl, "UTF-8");
        	queryParams = URLDecoder.decode(queryParams, "UTF-8");
			//解析请求参数
			JSONObject jsonobject = JSONObject.fromObject(queryParams);
			Map<String,Object> paramsMap = new HashMap<String,Object>();
			Iterator keys = jsonobject.keys();
			while(keys.hasNext()){
				String key = keys.next().toString();  
		        String value = jsonobject.optString(key);
		        paramsMap.put(key, value);
			}
			HttpServletRequest request = ServletActionContext.getRequest();
			String sessionId = request.getSession().getId();
			long page = Long.valueOf(paramsMap.get("page").toString());
			long rows = Long.valueOf(paramsMap.get("rows").toString());
			
			//获取需导出数据
			JSONArray excellist = new JSONArray();
			while(rows > 0){
				paramsMap.put("page",page+"");
				paramsMap.put("rows","1000");
				
				//发送请求获取列表数据
				String result = HttpUtil.doPostParam(excelurl,paramsMap,"UTF-8",sessionId);
				
				jsonobject = JSONObject.fromObject(result);
				String objlist = jsonobject.get("rows").toString();
				JSONArray obja = JSONArray.fromObject(objlist);
				excellist.addAll(obja);
				
				page += 1;
				rows -= 1000;
			}
			
			//第一步，创建一个webbook，对应一个Excel文件  
			HSSFWorkbook wb = new HSSFWorkbook();  
			//第二步，在webbook中添加一个sheet，对应Excel文件中的 sheet  
			HSSFSheet sheet = wb.createSheet(excelSheet);  
			//第四步，创建单元格样式：居中  
			HSSFCellStyle style = wb.createCellStyle();  
			style.setAlignment(HSSFCellStyle.ALIGN_CENTER);

			JSONObject titleobj = JSONObject.fromObject(excelTitle);
			Iterator colnumkeys = titleobj.keys();
			
			HSSFRow trow = sheet.createRow(0);
			//第五步，创建表头单元格，并设置样式  
		    HSSFCell tcell;
			int count = 0;
			while(colnumkeys.hasNext()){
				String colnumkey = colnumkeys.next().toString();  
		        String value = titleobj.optString(colnumkey);
		        tcell = trow.createCell(count);
		        tcell.setCellValue(value);  
		        tcell.setCellStyle(style);
		        count ++;
			}
			//写入导出数据
			for (int i = 0; i < excellist.size(); i++) {
				JSONObject exceldate = excellist.getJSONObject(i);
				
				HSSFRow row = sheet.createRow(i+1);
				//第五步，创建表头单元格，并设置样式  
			    HSSFCell cell;
				count = 0;
				colnumkeys = titleobj.keys();
				while(colnumkeys.hasNext()){
					String colnumkey = colnumkeys.next().toString();  
			        String value = exceldate.optString(colnumkey);
			        cell = row.createCell(count);
			        cell.setCellValue(value);  
			        count ++;
				}
			}
			
			//第七步，将文件存到流中  
			ByteArrayOutputStream os = new ByteArrayOutputStream();  
			wb.write(os);  
			byte[] fileContent = os.toByteArray();  
			ByteArrayInputStream is = new ByteArrayInputStream(fileContent);  

			excelStream = is;             //文件流  
			System.out.println("结束时间："+DateTimeUtil.getDate3());
		} catch (IOException e) {
			e.printStackTrace();
		}
    	return SUCCESS;
    }
  
    //-------------------------------------------------------------  
  
    public InputStream getExcelStream() {  
        return excelStream;  
    }  
    public void setExcelStream(InputStream excelStream) {  
        this.excelStream = excelStream;  
    }  
    public String getExcelFileName() {  
        return excelFileName;  
    }  
    public void setExcelFileName(String excelFileName) {  
        this.excelFileName = excelFileName;  
    }
}
