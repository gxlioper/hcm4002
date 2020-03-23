package com.hjw.zyb;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

import com.hjw.util.DateTimeUtil;
import com.hjw.util.Timeutils;
import com.hjw.util.ValidateUtil;
import com.hjw.wst.domain.Batch;
import com.hjw.wst.domain.CompanyInfo;
import com.hjw.wst.domain.ImpCustomerInfo;
import com.hjw.wst.domain.ImpCustomerInfo.InfoType;
import com.hjw.wst.service.BatchService;
import com.hjw.wst.service.CompanyService;
import com.hjw.wst.service.CustomerInfoService;
import com.hjw.zyb.domain.Impoccuhis;

@SuppressWarnings("serial")
public class ZybUpload extends HttpServlet {
	@SuppressWarnings("unchecked")
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		long userid = Long.valueOf(request.getParameter("userid"));
		String message = "ok-文件导入临时表，请您返回继续操作！";
		String company_id = request.getParameter("company_id");
		String batch_id = request.getParameter("batch_id");
		WebApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(this.getServletContext());
		BatchService batchService = (BatchService) context.getBean("batchService");
		CompanyService companyService = (CompanyService) context.getBean("companyService");
		CustomerInfoService customerInfoService = (CustomerInfoService) context.getBean("customerInfoService");
		String companyname = "";
		String batchname = "";
		boolean flagss = true;
		if ((company_id == null) || (Long.valueOf(company_id.trim()) <= 0)) {
			message = "error-无效单位编码！";
			flagss = false;
		} else {
			CompanyInfo cif = new CompanyInfo();
			cif = companyService.findComByID(Long.valueOf(company_id.trim()));
			companyname = (cif.getCom_Name());

			if ((batch_id == null) || (Long.valueOf(batch_id.trim()) <= 0)) {
				message = "error-无效体检任务编码！";
				flagss = false;
			} else {
				Batch batch = new Batch();
				batch = batchService.getBatchByID(Long.valueOf(batch_id.trim()));
				batchname = (batch.getBatch_name());

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
									customerInfoService);
						} catch (Exception ex) {
							try {
								XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(file));
								message = readXSSF(company_id, companyname, batch_id, batchname, userid, workbook,
										customerInfoService);
							} catch (Exception e) {
								 e.printStackTrace();
							 }
						}

					}
				}
			}
			response.setCharacterEncoding("UTF-8");
			response.getWriter().print(message);
		}
	}

	private String readHSSF(String company_id, String companyname, String batch_id, String batchname, long userid,
			HSSFWorkbook workbook, CustomerInfoService customerInfoService) {
		String message = "";
		HSSFSheet worksheet = workbook.getSheetAt(0);
		if (!companyname.equals(worksheet.getRow(0).getCell(1).toString().trim())
				|| (!batchname.equals(worksheet.getRow(0).getCell(3).toString().trim()))) {
			message = ("error-公司或者批次信息和模版中的信息不符！");
			System.out.println(companyname+"--公司--"+worksheet.getRow(0).getCell(1).toString().trim());
			System.out.println(batchname+"--批次--"+worksheet.getRow(0).getCell(3).toString().trim());
		} else {
			HSSFRow firstDataRow = worksheet.getRow(2);
			if (null == firstDataRow || null == firstDataRow.getCell(2)) {
				message = ("error-请从第三行开始写入数据！");
			} else {
				List<ImpCustomerInfo> listDTO = new ArrayList<ImpCustomerInfo>();
				int rowIndex = 2;
				boolean hasRecord = true;
				while (hasRecord) {
					ImpCustomerInfo ii = new ImpCustomerInfo();
					ii.setInfo_type(InfoType.ZYB.getCode());
					ii.setCompany_id(Long.valueOf(company_id));
					ii.setBatch_id(Long.valueOf(batch_id));
					ii.setCreater(userid);
					ii.setCreate_time(Timeutils.getNowDate());
					ii.setFlags(0);
					ii.setNotices("");
					HSSFRow row = worksheet.getRow(rowIndex);
					if (row.getCell(0) == null) {
						ii.setArch_num("");
					} else {
						ii.setArch_num(row.getCell(0).toString().replaceAll("[　*| *| *|//s*]*", ""));
					}
					if (row.getCell(1) == null) {
						ii.setVisit_no("");
					} else {
						row.getCell(1).setCellType(HSSFCell.CELL_TYPE_STRING);
						ii.setVisit_no(row.getCell(1).toString().replaceAll("[　*| *| *|*]*", "").trim());
					}
					// 证件类型名称
					// Cell idtypename = row.getCell(1);
					if (row.getCell(2) == null) {
						ii.setIdtypename("");
					} else {
						row.getCell(2).setCellType(HSSFCell.CELL_TYPE_STRING);
						ii.setIdtypename(row.getCell(2).toString().replaceAll("[　*| *| *|*]*", "").trim());
					}
					
					Cell cell = row.getCell(3);
					if (cell != null) {
						cell.setCellType(HSSFCell.CELL_TYPE_STRING);
						String idnum = cell.getStringCellValue();
						ii.setId_num(idnum.replaceAll("[　*| *| *]*", ""));
					} else {
						ii.setId_num("");
					}

					if (row.getCell(4) == null) {
						ii.setCustname("");
					} else {
						ii.setCustname(row.getCell(4).toString().replaceAll("[　*| *| *|//s*]*", ""));
					}
					if (row.getCell(5) == null) {
						ii.setSex("");
					} else {
						ii.setSex(row.getCell(5).toString().replaceAll("[　*| *| *|//s*]*", ""));
					}
					if (row.getCell(6) == null) {
						ii.setAge(1);
					} else {
						String str = row.getCell(6).toString().trim();
						if (StringUtil.isEmpty(str) || "".equals(str)) {
							ii.setAge(0);
						} else {
							String[] arr = str.split("\\.");
							ii.setAge(Long.valueOf(arr[0]));
						}
					}

					cell = row.getCell(7);
					if (cell != null) {
						cell.setCellType(HSSFCell.CELL_TYPE_STRING);
						String bir = cell.getStringCellValue();
						ii.setBirthday(bir.replaceAll("[年月日 -./\\s:]", ""));
					} else {
						ii.setBirthday("");
					}

					if (row.getCell(8) == null) {
						ii.setIs_marriage("");
					} else {
						ii.setIs_marriage(row.getCell(8).toString().replaceAll("[　*| *| *]*", ""));
					}
					if (row.getCell(9) == null) {
						ii.set_level("");
					} else {
						ii.set_level(row.getCell(9).toString().replaceAll("[　*| *| *]*", ""));
					}
					if (row.getCell(10) == null) {
						ii.setEmployeeID("");
					} else {
						ii.setEmployeeID(row.getCell(10).toString().replaceAll("[　*| *| *]*", ""));
					}
					cell = row.getCell(11);
					if (cell != null) {
						cell.setCellType(HSSFCell.CELL_TYPE_STRING);
						String bir = cell.getStringCellValue();
						ii.setTel(bir.replaceAll("[　*| *| *]*", ""));
					} else {
						ii.setTel("");
					}
					if (row.getCell(12) == null) {
						ii.setExam_type("");
					} else {
						ii.setExam_type(row.getCell(12).toString().replaceAll("[　*| *| *|//s*]*", ""));
					}
					if (row.getCell(13) == null) {
						ii.setZywhys("");
					} else {
						ii.setZywhys(row.getCell(13).toString().replaceAll("[　*| *| *|//s*]*", ""));
					}
					
					cell = row.getCell(14);
					if (cell == null) {
						ii.setJoinDatetime("");
					} else if("".equals(cell.toString())) {
						ii.setJoinDatetime("");
					} else {
						switch (cell.getCellType()) {
						case HSSFCell.CELL_TYPE_NUMERIC:// 数字类型
							if (HSSFDateUtil.isCellDateFormatted(cell)) {// 处理日期格式、时间格式
								SimpleDateFormat sdf = null;
								sdf = new SimpleDateFormat("yyyy-MM-dd");
								Date date = cell.getDateCellValue();
								ii.setJoinDatetime(sdf.format(date));
							} else if (cell.getCellStyle().getDataFormat() == 176) {
								// 处理自定义日期格式：m月d日(通过判断单元格的格式id解决，id的值是58)
								SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
								double value = cell.getNumericCellValue();
								Date date = org.apache.poi.ss.usermodel.DateUtil.getJavaDate(value);
								ii.setJoinDatetime(sdf.format(date));
							} else {
								double value = cell.getNumericCellValue();
								CellStyle style = cell.getCellStyle();
								DecimalFormat format = new DecimalFormat();
								String temp = style.getDataFormatString();
								// 单元格设置成常规
								if (temp.equals("General")) {
									format.applyPattern("#");
								}
								ii.setJoinDatetime(value + "");
							}
							break;
						case HSSFCell.CELL_TYPE_STRING:// String类型
							String bir = cell.getStringCellValue();
							ii.setJoinDatetime(bir.replaceAll("[年月日 -./\\s:]", "-"));
							break;
						case HSSFCell.CELL_TYPE_BLANK:
							ii.setJoinDatetime("");
							break;
						default:
							ii.setJoinDatetime("");
							break;
						}
					}
					
					if (row.getCell(15) == null || "".equals(row.getCell(15).toString())) {
						ii.setEmployeeage(0);
					} else {
						String str = row.getCell(15).toString().trim();
						String[] arr = str.split("\\.");
						ii.setEmployeeage(Integer.valueOf(arr[0]));
					}
					
					if (row.getCell(16) == null || "".equals(row.getCell(16).toString())) {
						ii.setEmployeemonth(0);
					} else {
						String str = row.getCell(16).toString().trim();
						String[] arr = str.split("\\.");
						ii.setEmployeemonth(Integer.valueOf(arr[0]));
					}
					
					if (row.getCell(17) == null || "".equals(row.getCell(16).toString())) {
						ii.setDamage(0);
					} else {
						String str = row.getCell(16).toString().trim();
						String[] arr = str.split("\\.");
						ii.setDamage(Integer.valueOf(arr[0]));
					}
					if (row.getCell(18) == null || "".equals(row.getCell(18).toString())) {
						ii.setDammonth(0);
					} else {
						String str = row.getCell(18).toString().trim();
						String[] arr = str.split("\\.");
						ii.setDammonth(Integer.valueOf(arr[0]));
					}
					
					if (row.getCell(19) == null) {
						ii.setOccusector("");
					} else {
						ii.setOccusector(row.getCell(19).toString().replaceAll("[　*| *| *]*", ""));
					}
					
					if (row.getCell(20) == null) {
						ii.setOccutypeofwork("");
					} else {
						ii.setOccutypeofwork(row.getCell(20).toString().replaceAll("[　*| *| *]*", ""));
					}
					
					if (row.getCell(21) == null) {
						ii.setCustomer_type("");
					} else {
						ii.setCustomer_type(row.getCell(21).toString().replaceAll("[　*| *| *|//s*]*", ""));
					}
					
					if (row.getCell(22) == null) {
						ii.setSets("");
					} else {
						ii.setSets(row.getCell(22).toString().replaceAll("[　*| *| *|//s*]*", ""));
					}
					
					if (row.getCell(23) == null || "".equals(row.getCell(23).toString())) {
						ii.setExam_indicator("团费");
					} else {
						ii.setExam_indicator(row.getCell(23).toString().replaceAll("[　*| *| *|//s*]*", ""));
					}
					
					if (row.getCell(24) == null || "".equals(row.getCell(24).toString())) {
						ii.setExam_class("职业健康检查");
					} else {
						ii.setExam_class(row.getCell(24).toString().replaceAll("[　*| *| *|//s*]*", ""));
					}
					
					if (row.getCell(23) == null) {
						ii.setRemark("");
					} else {
						ii.setRemark(row.getCell(23).toString().replaceAll("[　*| *| *|//s*]*", ""));
					}
					
					if (row.getCell(24) == null) {
						ii.setOthers("");
					} else {
						ii.setOthers(row.getCell(24).toString().replaceAll("[　*| *| *|//s*]*", ""));
					}
					
					ii = getcheck(ii);
					listDTO.add(ii);
					rowIndex++;
					HSSFRow nextDataRow = worksheet.getRow(rowIndex);
					if (nextDataRow != null && nextDataRow.getCell(4) != null
							&& !nextDataRow.getCell(4).getStringCellValue().equals("")) {
						continue;
					} else {
						hasRecord = false;
					}
				}
				customerInfoService.saveImpCustomerInfoForList(listDTO);
				
				//导入 职业史
				readTwoHSSFSheet(workbook, userid, customerInfoService);
				
				message = "ok-成功导入" + (rowIndex - 2) + "条";
			}
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
			XSSFWorkbook workbook, CustomerInfoService customerInfoService) {
		String message = "";
		XSSFSheet worksheet = workbook.getSheetAt(0);
		if (!companyname.equals(worksheet.getRow(0).getCell(1).toString().trim())
				|| (!batchname.equals(worksheet.getRow(0).getCell(3).toString().trim()))) {
			message = ("error-公司或者批次信息和模版中的信息不符！");
		} else {
			XSSFRow firstDataRow = worksheet.getRow(2);
			if (null == firstDataRow || null == firstDataRow.getCell(2)) {
				message = ("error-请从第三行开始写入数据！");
			} else {
				List<ImpCustomerInfo> listDTO = new ArrayList<ImpCustomerInfo>();
				int rowIndex = 2;
				boolean hasRecord = true;
				while (hasRecord) {
					ImpCustomerInfo ii = new ImpCustomerInfo();
					ii.setInfo_type(InfoType.ZYB.getCode());
					ii.setCompany_id(Long.valueOf(company_id));
					ii.setBatch_id(Long.valueOf(batch_id));
					ii.setCreater(userid);
					ii.setCreate_time(Timeutils.getNowDate());
					ii.setFlags(0);
					ii.setNotices("");
					XSSFRow row = worksheet.getRow(rowIndex);
					if (row.getCell(0) == null) {
						ii.setArch_num("");
					} else {
						ii.setArch_num(row.getCell(0).toString().trim());
					}
					if (row.getCell(1) == null) {
						ii.setVisit_no("");
					} else {
						row.getCell(1).setCellType(HSSFCell.CELL_TYPE_STRING);
						ii.setVisit_no(row.getCell(1).toString().replaceAll("[　*| *| *|*]*", "").trim());
					}
					// 证件类型名称
					// Cell idtypename = row.getCell(1);
					if (row.getCell(2) == null) {
						ii.setIdtypename("");
					} else {
						row.getCell(2).setCellType(HSSFCell.CELL_TYPE_STRING);
						ii.setIdtypename(row.getCell(2).toString().replaceAll("[　*| *| *|*]*", "").trim());
					}
					
					Cell cell = row.getCell(3);
					if (cell != null) {
						cell.setCellType(HSSFCell.CELL_TYPE_STRING);
						String idnum = cell.getStringCellValue();
						ii.setId_num(idnum.trim());
					} else {
						ii.setId_num("");
					}

					if (row.getCell(4) == null) {
						ii.setCustname("");
					} else {
						ii.setCustname(row.getCell(4).toString().trim());
					}
					if (row.getCell(5) == null) {
						ii.setSex("");
					} else {
						ii.setSex(row.getCell(5).toString().trim());
					}
					if (row.getCell(6) == null) {
						ii.setAge(1);
					} else {
						String str = row.getCell(6).toString().trim();
						if (StringUtil.isEmpty(str)) {
							ii.setAge(1);
						} else {
							String[] arr = str.split("\\.");
							ii.setAge(Long.valueOf(arr[0]));
						}
					}

					cell = row.getCell(7);
					if (cell != null) {
						cell.setCellType(HSSFCell.CELL_TYPE_STRING);
						String bir = cell.getStringCellValue();
						ii.setBirthday(bir.replaceAll("[年月日 -./\\s:]", "").trim());
					} else {
						ii.setBirthday("");
					}

					if (row.getCell(8) == null) {
						ii.setIs_marriage("");
					} else {
						ii.setIs_marriage(row.getCell(8).toString().trim());
					}
					if (row.getCell(9) == null) {
						ii.set_level("");
					} else {
						ii.set_level(row.getCell(9).toString().replaceAll("[　*| *| *|//s*]*", ""));
					}
					if (row.getCell(10) == null) {
						ii.setEmployeeID("");
					} else {
						ii.setEmployeeID(row.getCell(10).toString().replaceAll("[　*| *| *|//s*]*", ""));
					}
					cell = row.getCell(11);
					if (cell != null) {
						cell.setCellType(HSSFCell.CELL_TYPE_STRING);
						String bir = cell.getStringCellValue();
						ii.setTel(bir.replaceAll("[　*| *| *|//s*]*", ""));
					} else {
						ii.setTel("");
					}
					if (row.getCell(12) == null) {
						ii.setExam_type("");
					} else {
						ii.setExam_type(row.getCell(12).toString().replaceAll("[　*| *| *|//s*]*", ""));
					}
					if (row.getCell(13) == null) {
						ii.setZywhys("");
					} else {
						ii.setZywhys(row.getCell(13).toString().replaceAll("[　*| *| *|//s*]*", ""));
					}
					cell = row.getCell(14);
					if (cell != null) {
						switch (cell.getCellType()) {
						case HSSFCell.CELL_TYPE_NUMERIC:// 数字类型
							if (HSSFDateUtil.isCellDateFormatted(cell)) {// 处理日期格式、时间格式
								SimpleDateFormat sdf = null;
								sdf = new SimpleDateFormat("yyyy-MM-dd");
								Date date = cell.getDateCellValue();
								ii.setJoinDatetime(sdf.format(date));
							} else if (cell.getCellStyle().getDataFormat() == 176) {
								// 处理自定义日期格式：m月d日(通过判断单元格的格式id解决，id的值是58)
								SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
								double value = cell.getNumericCellValue();
								Date date = org.apache.poi.ss.usermodel.DateUtil.getJavaDate(value);
								ii.setJoinDatetime(sdf.format(date));
							} else {
								double value = cell.getNumericCellValue();
								CellStyle style = cell.getCellStyle();
								DecimalFormat format = new DecimalFormat();
								String temp = style.getDataFormatString();
								// 单元格设置成常规
								if (temp.equals("General")) {
									format.applyPattern("#");
								}
								ii.setJoinDatetime(value + "");
							}
							break;
						case HSSFCell.CELL_TYPE_STRING:// String类型
							String bir = cell.getStringCellValue();
							ii.setJoinDatetime(bir.replaceAll("[年月日 -./\\s:]", "-"));
							break;
						case HSSFCell.CELL_TYPE_BLANK:
							ii.setJoinDatetime("");
							break;
						default:
							ii.setJoinDatetime("");
							break;
						}
					} else {
						ii.setJoinDatetime("");
					}
					if (row.getCell(15) == null) {
						ii.setEmployeeage(0);
					} else {
						String str = row.getCell(15).toString().trim();
						String[] arr = str.split("\\.");
						ii.setEmployeeage(Integer.valueOf(arr[0]));
					}
					
					if (row.getCell(16) == null) {
						ii.setDamage(0);
					} else {
						String str = row.getCell(16).toString().trim();
						String[] arr = str.split("\\.");
						ii.setDamage(Integer.valueOf(arr[0]));
					}
					
					if (row.getCell(17) == null) {
						ii.setOccusector("");
					} else {
						ii.setOccusector(row.getCell(17).toString().replaceAll("[　*| *| *|//s*]*", ""));
					}
					
					if (row.getCell(18) == null) {
						ii.setOccutypeofwork("");
					} else {
						ii.setOccutypeofwork(row.getCell(18).toString().replaceAll("[　*| *| *|//s*]*", ""));
					}
					
					if (row.getCell(19) == null) {
						ii.setCustomer_type("");
					} else {
						ii.setCustomer_type(row.getCell(19).toString().replaceAll("[　*| *| *|//s*]*", ""));
					}
					if (row.getCell(20) == null) {
						ii.setSets("");
					} else {
						ii.setSets(row.getCell(20).toString().replaceAll("[　*| *| *|//s*]*", ""));
					}
					if (row.getCell(21) == null || "".equals(row.getCell(21).toString())) {
						ii.setExam_indicator("团费");
					} else {
						ii.setExam_indicator(row.getCell(21).toString().replaceAll("[　*| *| *|//s*]*", ""));
					}

					if (row.getCell(22) == null || "".equals(row.getCell(22).toString())) {
						ii.setExam_class("职业健康检查");
					} else {
						ii.setExam_class(row.getCell(22).toString().replaceAll("[　*| *| *|//s*]*", ""));
					}
					
					if (row.getCell(23) == null) {
						ii.setRemark("");
					} else {
						ii.setRemark(row.getCell(23).toString().replaceAll("[　*| *| *|//s*]*", ""));
					}
					
					if (row.getCell(24) == null) {
						ii.setOthers("");
					} else {
						ii.setOthers(row.getCell(24).toString().replaceAll("[　*| *| *|//s*]*", ""));
					}
					ii = getcheck(ii);
					listDTO.add(ii);
					rowIndex++;
					XSSFRow nextDataRow = worksheet.getRow(rowIndex);
					if (nextDataRow != null && nextDataRow.getCell(4) != null
							&& !nextDataRow.getCell(4).getStringCellValue().equals("")) {
						continue;
					} else {
						hasRecord = false;
					}
				}
				customerInfoService.saveImpCustomerInfoForList(listDTO);
				//导入职业史
				readTwoXSSFSheet(workbook, userid, customerInfoService);
				
				message = "ok-成功导入" + (rowIndex - 2) + "条";
			}
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

	private static ImpCustomerInfo getcheck(ImpCustomerInfo ii) {
		if (!StringUtil.isEmpty(ii.getIs_marriage())) {
			if (ii.getIs_marriage().indexOf("已") > -1) {
				ii.setIs_marriage("已婚");
			} else if (ii.getIs_marriage().indexOf("未") > -1) {
				ii.setIs_marriage("未婚");
			} else {
				ii.setIs_marriage("");
			}
		}
		/*String id_num_not_null = customerInfoService.getCenterconfigByKey("IS_XIYUAN_IDNUM", user.getCenter_num()).getConfig_value().trim();
		System.out.println(id_num_not_null+"身份证可否为空");*/
		if (("身份证".equals(ii.getIdtypename()) || "".equals(ii.getIdtypename()))&& (StringUtil.isEmpty(ii.getId_num())) && (ii.getAge() <= 0)
				&& (StringUtil.isEmpty(ii.getBirthday()))) {
			ii.setNotices("证件号码、生日、年龄都不存在");
			ii.setFlags(1);
		} else if ((ii.getCustname() == null) || (ii.getCustname().trim().length() <= 0)) {
			ii.setNotices("姓名不能为空");
			ii.setFlags(1);
		}
		// Idtypename是身份证类型进行验证
		if (("身份证".equals(ii.getIdtypename()) || "".equals(ii.getIdtypename())) && ii.getId_num()!=null && !"".equals(ii.getId_num())) {
			// 验证身份证格式
			String idnum_num = ValidateUtil.IDCardValidate(ii.getId_num());
			
				// 18位身份证
				if ("".equals(idnum_num) && ii.getId_num().length() == 18) {
					String bird = ii.getId_num().trim().substring(6, 14);
					Date da = Timeutils.strToDateLong2(bird);
					bird = DateTimeUtil.shortFmt3(da);
					ii.setBirthday(bird);
					ii.setAge(Timeutils.getDayForOld(bird));
					String sexflag = "";
					System.out.println(Integer.valueOf(ii.getId_num().trim().substring(16, 17)));
					if (Integer.valueOf(ii.getId_num().trim().substring(16, 17)) % 2 == 1) {
						// 男
						sexflag = ("男");
					} else {
						// 女
						sexflag = ("女");
					}
					if (StringUtil.isEmpty(ii.getSex())) {
						ii.setSex(sexflag);
					} else {
						if (!StringUtil.isEmpty(ii.getSex())) {
							if (ii.getSex().indexOf("男") > -1) {
								ii.setSex("男");
							} else if (ii.getSex().indexOf("女") > -1) {
								ii.setSex("女");
							} else {
								ii.setSex("");
							}
						}
						if (!sexflag.equals(ii.getSex())) {
							ii.setNotices("身份证与性别不符");
							ii.setFlags(1);
						}
					}
					// 15位身份证
				} else if (("身份证".equals(ii.getIdtypename()) || "".equals(ii.getIdtypename()))&&"".equals(idnum_num) && ii.getId_num().length() == 15) {
					String bird = 19 + ii.getId_num().trim().substring(6, 12);
					System.out.println(bird);
					Date da = Timeutils.strToDateLong2(bird);
					bird = DateTimeUtil.shortFmt3(da);
					ii.setBirthday(bird);
					ii.setAge(Timeutils.getDayForOld(bird));
					String sexflag = "";
					if (Integer.valueOf(ii.getId_num().trim().substring(14, 15)) % 2 == 1) {
						// 男
						sexflag = ("男");
					} else {
						// 女
						sexflag = ("女");
					}
					if (StringUtil.isEmpty(ii.getSex())) {
						ii.setSex(sexflag);
					} else {
						if (!StringUtil.isEmpty(ii.getSex())) {
							if (ii.getSex().indexOf("男") > -1) {
								ii.setSex("男");
							} else if (ii.getSex().indexOf("女") > -1) {
								ii.setSex("女");
							} else {
								ii.setSex("");
							}
						}
						if (!sexflag.equals(ii.getSex())) {
							ii.setNotices("身份证与性别不符");
							ii.setFlags(1);
						}
					}
	
				} else {
					ii.setNotices("身份证无效，请核实");
					ii.setFlags(1);
				}
		} else if (!StringUtil.isEmpty(ii.getBirthday())) {
			if (ii.getBirthday().trim().split("-").length == 1) {
				if (ii.getBirthday().trim().length() == 8) {
					String bird = ii.getBirthday().trim();
					Date da = Timeutils.strToDateLong2(bird);
					bird = DateTimeUtil.shortFmt3(da);
					ii.setBirthday(bird);
				} else {
					ii.setNotices("出生日期格式错误");
					ii.setFlags(1);
				}
			} else if (ii.getBirthday().trim().split("-").length == 3) {
				Date birdate = Timeutils.strToDate(ii.getBirthday().trim());
				String bird = DateTimeUtil.shortFmt3(birdate);
				ii.setBirthday(bird);
				ii.setAge(Timeutils.getDayForOld(bird));
			} else {
				ii.setNotices("出生日期格式错误");
				ii.setFlags(1);
			}
		} else if (ii.getAge() > 0) {
			if (StringUtil.isEmpty(ii.getBirthday())) {
				ii.setBirthday(DateTimeUtil.getDateByAge((int) ii.getAge()));
			}
		}

		if (StringUtil.isEmpty(ii.getSex()) && "".equals(ii.getNotices())) {
			ii.setNotices("性别不能为空");
			ii.setFlags(1);
		}

		if (ii.getAge() <= 0) {
			ii.setNotices("年龄不能为空");
			ii.setFlags(1);
		}

		return ii;
	}
	
	
	/**
	 * 
	     * @Title: readTwoHSSFSheet   
	     * @Description: TODO(导入职业史2017年以前的)   
	     * @param: @param workbook
	     * @param: @param userid
	     * @param: @param customerInfoService
	     * @param: @return      
	     * @return: boolean      
	     * @throws
	 */
	private boolean readTwoHSSFSheet(HSSFWorkbook workbook,long userid,CustomerInfoService customerInfoService) {
		String message = "";
		boolean isInsert = true;
		HSSFSheet worksheet = workbook.getSheetAt(1);//第二张表
		HSSFRow twoRow = worksheet.getRow(1);  //第二行
		
		if (null == twoRow || null == twoRow.getCell(2)) {
			message = ("error-请从第二行开始写入数据！");
		} else {
			List<Impoccuhis> li = new ArrayList<Impoccuhis>();
			int rowIndex = 1;
			boolean hasRecord = true;
			while (hasRecord) {
				Impoccuhis  s = new Impoccuhis();
				HSSFRow row = worksheet.getRow(rowIndex);
				if (row.getCell(0) == null) {
					s.setId_num("");
				} else {
					s.setId_num(row.getCell(0).toString());
					//.replaceAll("[　*| *| *|//s*]*", "")
				}
				if(row.getCell(1)==null){
					s.setCompany("");
				} else {
					s.setCompany(row.getCell(1).toString());
				}
				if(row.getCell(2)==null){
					s.setWorkshop("");
				} else {
					s.setWorkshop(row.getCell(2).toString());
				}
				if(row.getCell(3)==null){
					s.setWorktype("");
				} else {
					s.setWorktype(row.getCell(3).toString());
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
					s.setMeasure("");
				} else {
					s.setMeasure(row.getCell(6).toString());
				}
				if(row.getCell(7)==null) {
					s.setHarmname("");
				} else {
					s.setHarmname(row.getCell(7).toString());
				}
				if(row.getCell(8)==null){
					s.setConcentrations("");
				} else {
					s.setConcentrations(row.getCell(8).toString());
				}
				li.add(s);
				rowIndex++;
				HSSFRow nextDataRow = worksheet.getRow(rowIndex);
				if (nextDataRow != null && nextDataRow.getCell(2) != null
						&& !"".equals(nextDataRow.getCell(2).getStringCellValue())) {
					continue;
				} else {
					hasRecord = false;
				}
			}
			customerInfoService.saveImpoccuhisInfoForList(li, userid, 1 ,0);
			
			message = "ok-成功导入" + (rowIndex - 1) + "条";
			isInsert = true;
		}
		
		return isInsert;
	}
		
		
	/**
	 * 
	     * @Title: readTwoSheet   
	     * @Description: TODO(读取插入第二张表中 2017年以后模板)   
	     * @param: @return      
	     * @return: boolean      
	     * @throws
	 */
	private boolean readTwoXSSFSheet(XSSFWorkbook workbook,long userid,CustomerInfoService customerInfoService) {
		String message = "";
		boolean isInsert = true;
		XSSFSheet worksheet = workbook.getSheetAt(1); //第二张表
		XSSFRow twoRow = worksheet.getRow(1); //第二行
		if (null == twoRow || null == twoRow.getCell(2)) {
			message = ("error-请从第二行开始写入数据！");
		} else {
			List<Impoccuhis> li = new ArrayList<Impoccuhis>();
			int rowIndex = 1;
			boolean hasRecord = true;
			while (hasRecord) {
				Impoccuhis  s = new Impoccuhis();
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
					s.setWorkshop(row.getCell(3).toString().replaceAll("[　*| *| *|//s*]*", ""));
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
					s.setMeasure("");
				} else {
					s.setMeasure(row.getCell(6).toString().replaceAll("[　*| *| *|//s*]*", ""));
				}
				if(row.getCell(7)==null) {
					s.setHarmname("");
				} else {
					s.setHarmname(row.getCell(7).toString().replaceAll("[　*| *| *|//s*]*", ""));
				}
				if(row.getCell(8)==null){
					s.setConcentrations("");
				} else {
					s.setConcentrations(row.getCell(8).toString().replaceAll("[　*| *| *|//s*]*", ""));
				}
				li.add(s);
				rowIndex++;
				XSSFRow nextDataRow = worksheet.getRow(rowIndex);
				if (nextDataRow != null && nextDataRow.getCell(2) != null
						&& !"".equals(nextDataRow.getCell(2).getStringCellValue())) {
					continue;
				} else {
					hasRecord = false;
				}
			}
			//职业病 == 职业史
			customerInfoService.saveImpoccuhisInfoForList(li, userid, 1, 0);
			
			message = "ok-成功导入" + (rowIndex - 1) + "条";
			isInsert = true;
		}
		
		return isInsert;
	}
}
