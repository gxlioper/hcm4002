package com.hjw.zyb;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.struts2.dispatcher.multipart.MultiPartRequestWrapper;
import org.apache.struts2.dispatcher.multipart.UploadedFile;
import org.hsqldb.lib.StringUtil;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.hjw.util.DateTimeUtil;
import com.hjw.util.Timeutils;
import com.hjw.wst.domain.Batch;
import com.hjw.wst.domain.CompanyInfo;
import com.hjw.wst.domain.ImpCustomerInfo;
import com.hjw.wst.service.BatchService;
import com.hjw.wst.service.CompanyService;
import com.hjw.wst.service.CustomerInfoService;
import com.hjw.zyb.domain.ZybOccuHis;
import com.hjw.zyb.service.ZybOccuhisService;
/**上传放射职业史
 * 
     * @Title:  火箭蛙体检管理系统   
     * @Package com.hjw.zyb   
     * @Description:  
     * @author: zr     
     * @date:   2017年7月19日 上午9:40:40   
     * @version V2.0.0.0
 */
@SuppressWarnings("serial")
public class ZybUploadFS extends HttpServlet {
	@SuppressWarnings("unchecked")
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		long userid = Long.valueOf(request.getParameter("userid"));
		String message = "ok-文件导入临时表，请您返回继续操作！";
		String company_id = "2";
		String batch_id ="2";
		WebApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(this.getServletContext());
		BatchService batchService = (BatchService) context.getBean("batchService");
		CompanyService companyService = (CompanyService) context.getBean("companyService");
		CustomerInfoService customerInfoService = (CustomerInfoService) context.getBean("customerInfoService");
		ZybOccuhisService zybOccuhisService = (ZybOccuhisService) context.getBean("zybOccuhisService");
		String companyname = "";
		String batchname = "";
		boolean flagss = true;
		if ((company_id == null) || (Long.valueOf(company_id.trim()) <= 0)) {
			message = "error-无效单位编码！";
			flagss = false;
		} else {
			/*CompanyInfo cif = new CompanyInfo();
			cif = companyService.findComByID(Long.valueOf(company_id.trim()));
			companyname = (cif.getCom_Name());
*/
			if ((batch_id == null) || (Long.valueOf(batch_id.trim()) <= 0)) {
				message = "error-无效体检任务编码！";
				flagss = false;
			} else {
/*				Batch batch = new Batch();
				batch = batchService.getBatchByID(Long.valueOf(batch_id.trim()));
				batchname = (batch.getBatch_name());
*/
				MultiPartRequestWrapper multiWrapper = (MultiPartRequestWrapper) request;
				Enumeration en = multiWrapper.getFileParameterNames();
				File file = null;
				String[] fileNames = null;

				String name = "";
				String extName = "";
				String fileName = "";
				long size = 0;
				if (en == null) {
					message = "error-无效文件！";
					flagss = false;
				} else {
					while (en.hasMoreElements()) {
						String inputName = (String) en.nextElement();
						fileNames = multiWrapper.getFileNames(inputName);
						fileName = fileNames[0];
						UploadedFile[] file1 = null;
						file1 = multiWrapper.getFiles(inputName);
						file = new File(file1[0].getAbsolutePath());
						name = file1[0].getName();
						size = file1[0].length();

						// 打印文件大小
						System.out.println("文件大小：" + size + " ");
						if (size > 20971520) {
							message = ("error-文件大小不能超过20M！");
							flagss = false;
						}
						if (fileName == null || fileName.trim().equals("")) {
							continue;
						}
						// 文件原有名称
						// 扩展名格式：
						if (fileName.lastIndexOf(".") >= 0) {
							extName = fileName.substring(fileName.lastIndexOf("."));
						}

						if (!extName.toLowerCase().equals(".xls") && !extName.toLowerCase().equals(".xlsx")) {
							message = ("error-文件类型错误！");
							flagss = false;
						}
					}

					if (flagss) {

						try {
							HSSFWorkbook wb = new HSSFWorkbook(new FileInputStream(file));
							message = readHSSF(company_id, companyname, batch_id, batchname, userid, wb,
									customerInfoService,zybOccuhisService);
						} catch (Exception ex) {
							XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(file));
							message = readXSSF(company_id, companyname, batch_id, batchname, userid, workbook,
									customerInfoService,zybOccuhisService);
						}

					}
				}
			}
			response.setCharacterEncoding("UTF-8");
			response.getWriter().print(message);
		}
	}

	private String readHSSF(String company_id, String companyname, String batch_id, String batchname, long userid,
			HSSFWorkbook workbook, CustomerInfoService customerInfoService,ZybOccuhisService zybOccuhisService) {
		String message = "";
		HSSFSheet worksheet = workbook.getSheetAt(0);
			HSSFRow firstDataRow = worksheet.getRow(1);
			if (null == firstDataRow || null == firstDataRow.getCell(1)) {
				message = ("error-请从第二行开始写入数据！");
			} else {
				List<ZybOccuHis> li = new ArrayList<ZybOccuHis>();
				int rowIndex = 1;
				boolean hasRecord = true;
				while (hasRecord) {
					ZybOccuHis  s = new ZybOccuHis();
					HSSFRow row = worksheet.getRow(rowIndex);
					if (row.getCell(0) == null) {
						s.setId_num("");
					} else {
						s.setId_num(row.getCell(0).toString().replaceAll("[　*| *| *|//s*]*", ""));
					}
					if(row.getCell(1)==null){
						s.setCompany("");
					} else {
						s.setCompany(row.getCell(1).toString().replaceAll("[　*| *| *|//s*]*", ""));
					}
					if(row.getCell(2)==null){
						s.setWorkshop("");
					} else {
						s.setWorkshop(row.getCell(2).toString().replaceAll("[　*| *| *|//s*]*", ""));
					}
					if(row.getCell(3)==null){
						s.setWorktype("");
					} else {
						s.setWorktype(row.getCell(3).toString().replaceAll("[　*| *| *|//s*]*", ""));
					}
					
					
					Cell cell  = row.getCell(4);
					if (cell != null) {
						s.setStartdate(DateTimeUtil.shortFmt3(cell.getDateCellValue()));
					} else {
						s.setStartdate("");
					}
					cell  = row.getCell(5);
					if (cell != null) {
						s.setEnddate(DateTimeUtil.shortFmt3(cell.getDateCellValue()));
					} else {
						s.setEnddate("");
					}
					if(row.getCell(6)==null){
						s.setRadiation("");
					} else {
						s.setRadiation(row.getCell(6).toString().replaceAll("[　*| *| *|//s*]*", ""));
					}
					if(row.getCell(7)==null) {
						s.setMan_haur("");
					} else {
						s.setMan_haur(row.getCell(7).toString().replaceAll("[　*| *| *|//s*]*", ""));
					}
					if(row.getCell(8)==null){
						s.setCumulative_exposure("");
					} else {
						s.setCumulative_exposure(row.getCell(8).toString().replaceAll("[　*| *| *|//s*]*", ""));
					}
					if(row.getCell(9)==null){
						s.setHistory_excessive("");
					} else {
						s.setHistory_excessive(row.getCell(9).toString().replaceAll("[　*| *| *|//s*]*", ""));
					}
					if(row.getCell(10)==null){
						s.setRemark("");
					} else {
						s.setRemark(row.getCell(10).toString().replaceAll("[　*| *| *|//s*]*", ""));
					}
					s.setIsradiation("1");
					li.add(s);
					rowIndex++;
					HSSFRow nextDataRow = worksheet.getRow(rowIndex);
					if (nextDataRow != null && nextDataRow.getCell(2) != null
							&& !nextDataRow.getCell(2).getStringCellValue().equals("")) {
						continue;
					} else {
						hasRecord = false;
					}
				}
				message = "ok-成功导入" + (rowIndex - 1) + "条";
				String examnum = 	zybOccuhisService.saveOccuhis(li,userid);
				message = "{\"message\":\""+message+"\",\"exam_num\":\""+examnum+"\"}";
			}
		return message;
	}

	/**
	 * 
	 * @Title: readXSSF @Description: 解析exl2007及其以后版本 @param: @param
	 * company_id @param: @param companyname @param: @param
	 * batch_id @param: @param batchname @param: @param userid @param: @param
	 * workbook @param: @param customerInfoService @param: @return @return:
	 * String @throws
	 */
	private String readXSSF(String company_id, String companyname, String batch_id, String batchname, long userid,
			XSSFWorkbook workbook, CustomerInfoService customerInfoService,ZybOccuhisService zybOccuhisService) {
		String message = "";
		XSSFSheet worksheet = workbook.getSheetAt(0);
		XSSFRow firstDataRow = worksheet.getRow(2);
		if (null == firstDataRow || null == firstDataRow.getCell(2)) {
			message = ("error-请从第二行开始写入数据！");
		} else {
			List<ZybOccuHis> li = new ArrayList<ZybOccuHis>();
			int rowIndex = 1;
			boolean hasRecord = true;
			while (hasRecord) {
				ZybOccuHis  s = new ZybOccuHis();
				XSSFRow row = worksheet.getRow(rowIndex);
				if (row.getCell(0) == null) {
					s.setId_num("");
				} else {
					s.setId_num(row.getCell(0).toString().replaceAll("[　*| *| *|//s*]*", ""));
				}
				if(row.getCell(1)==null){
					s.setCompany("");
				} else {
					s.setCompany(row.getCell(1).toString().replaceAll("[　*| *| *|//s*]*", ""));
				}
				if(row.getCell(2)==null){
					s.setWorkshop("");
				} else {
					s.setWorkshop(row.getCell(2).toString().replaceAll("[　*| *| *|//s*]*", ""));
				}
				if(row.getCell(3)==null){
					s.setWorktype("");
				} else {
					s.setWorktype(row.getCell(3).toString().replaceAll("[　*| *| *|//s*]*", ""));
				}
				
				
				Cell cell  = row.getCell(4);
				if (cell != null) {
					s.setStartdate(DateTimeUtil.shortFmt3(cell.getDateCellValue()));
				} else {
					s.setStartdate("");
				}
				cell  = row.getCell(5);
				if (cell != null) {
					s.setEnddate(DateTimeUtil.shortFmt3(cell.getDateCellValue()));
				} else {
					s.setEnddate("");
				}
				if(row.getCell(6)==null){
					s.setRadiation("");
				} else {
					s.setRadiation(row.getCell(6).toString().replaceAll("[　*| *| *|//s*]*", ""));
				}
				if(row.getCell(7)==null) {
					s.setMan_haur("");
				} else {
					s.setMan_haur(row.getCell(7).toString().replaceAll("[　*| *| *|//s*]*", ""));
				}
				if(row.getCell(8)==null){
					s.setCumulative_exposure("");
				} else {
					s.setCumulative_exposure(row.getCell(8).toString().replaceAll("[　*| *| *|//s*]*", ""));
				}
				if(row.getCell(9)==null){
					s.setHistory_excessive("");
				} else {
					s.setHistory_excessive(row.getCell(9).toString().replaceAll("[　*| *| *|//s*]*", ""));
				}
				if(row.getCell(10)==null){
					s.setRemark("");
				} else {
					s.setRemark(row.getCell(10).toString().replaceAll("[　*| *| *|//s*]*", ""));
				}
				s.setIsradiation("1");
				li.add(s);
				rowIndex++;
				XSSFRow nextDataRow = worksheet.getRow(rowIndex);
				if (nextDataRow != null && nextDataRow.getCell(2) != null
						&& !nextDataRow.getCell(2).getStringCellValue().equals("")) {
					continue;
				} else {
					hasRecord = false;
				}
			}
		message = "ok-成功导入" + (rowIndex - 1) + "条";
		String examnum = 	zybOccuhisService.saveOccuhis(li,userid);
		message = "{\"message\":\""+message+"\",\"exam_num\":\""+examnum+"\"}";
		}
		return message;
	}

	/**
	 * 
	 * @Title: isExcel2007 @Description:
	 * 是否是2007的excel，返回true是2007 @param: @param
	 * filePath @param: @return @return: boolean @throws
	 */

	public static boolean isExcel2007(String filePath) {
		return filePath.matches("^.+\\.(?i)(xlsx)$");
	}


}
