package com.hjw.wst;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.ParseException;
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

@SuppressWarnings("serial")
public class Upload extends HttpServlet {
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
						name =file1[0].getName();
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
							try{
							XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(file));
							message = readXSSF(company_id, companyname, batch_id, batchname, userid, workbook,
									customerInfoService);
							}catch(Exception ext){
								ext.printStackTrace();
								message="error-文件装载错误！";
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
		if (!companyname.equals(worksheet.getRow(0).getCell(1).toString().replaceAll("[　*| *| *|*]*", ""))
				|| (!batchname.equals(worksheet.getRow(0).getCell(3).toString().replaceAll("[　*| *| *|*]*", "")))) {
			message = ("error-公司或者批次信息和模版中的信息不符！");
		} else {
			HSSFRow firstDataRow = worksheet.getRow(2);
			if (null == firstDataRow ) {
				message = ("error-请从第三行开始写入数据！");
			} else {
				List<ImpCustomerInfo> listDTO = new ArrayList<ImpCustomerInfo>();
				int rowIndex = 2;
				int error = 0;
				boolean hasRecord = true;
				while (hasRecord) {
					try {
						ImpCustomerInfo ii = new ImpCustomerInfo();
						ii.setInfo_type(InfoType.YBTJ.getCode());
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
							row.getCell(0).setCellType(HSSFCell.CELL_TYPE_STRING);
							ii.setArch_num(row.getCell(0).toString().replaceAll("[　*| *| *|*]*", "").trim());
						}
					

						Cell cell = row.getCell(1);
						if (cell != null) {
							cell.setCellType(HSSFCell.CELL_TYPE_STRING);
							String idnum = cell.getStringCellValue();
							ii.setId_num(idnum.replaceAll("[　*| *| *|*]*", "").trim());
						} else {
							ii.setId_num("");
						}
						row.getCell(2).setCellType(Cell.CELL_TYPE_STRING);
						if (row.getCell(2) == null) {
							ii.setCustname("");
						} else {
							ii.setCustname(row.getCell(2).toString().replaceAll("[　*| *| *|*]*", "").trim());
						}
						if (row.getCell(3) == null) {
							ii.setSex("");
						} else {
							ii.setSex(row.getCell(3).toString().replaceAll("[　*| *| *|*]*", "").trim());
						}
						if (row.getCell(4) == null) {
							ii.setAge(1);
						} else {
							row.getCell(4).setCellType(HSSFCell.CELL_TYPE_STRING);
							String str = row.getCell(4).toString().replaceAll("[　*| *| *|*]*", "");
							if (StringUtil.isEmpty(str)) {
								ii.setAge(1);
							} else {
								String[] arr = str.split("\\.");
								ii.setAge(Long.valueOf(arr[0].trim()));
							}
						}

						cell = row.getCell(5);
						if (cell != null) {
							switch (cell.getCellType()) {
							case HSSFCell.CELL_TYPE_NUMERIC:// 数字类型
								if (HSSFDateUtil.isCellDateFormatted(cell)) {// 处理日期格式、时间格式
									SimpleDateFormat sdf = null;
									sdf = new SimpleDateFormat("yyyy-MM-dd");
									Date date = cell.getDateCellValue();
									ii.setBirthday(sdf.format(date));
								} else if (cell.getCellStyle().getDataFormat() == 176) {
									// 处理自定义日期格式：m月d日(通过判断单元格的格式id解决，id的值是58)
									SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
									double value = cell.getNumericCellValue();
									Date date = org.apache.poi.ss.usermodel.DateUtil.getJavaDate(value);
									ii.setBirthday(sdf.format(date));
								} else {
									double value = cell.getNumericCellValue();
									CellStyle style = cell.getCellStyle();
									DecimalFormat format = new DecimalFormat();
									String temp = style.getDataFormatString();
									// 单元格设置成常规
									if (temp.equals("General")) {
										format.applyPattern("#");
									}
									ii.setBirthday(value + "");
								}
								break;
							case HSSFCell.CELL_TYPE_STRING:// String类型
								String bir = cell.getStringCellValue();
								ii.setBirthday(bir.replaceAll("[年月日 -./\\s:]", "-"));
								break;
							case HSSFCell.CELL_TYPE_BLANK:
								ii.setBirthday("");
								break;
							default:
								ii.setBirthday("");
								break;
							}
						} else {
							ii.setBirthday("");
						}

						if (row.getCell(6) == null) {
							ii.setIs_marriage("");
						} else {
							ii.setIs_marriage(row.getCell(6).toString().replaceAll("[　*| *| *|*]*", "").trim());
						}
						if (row.getCell(7) == null) {
							ii.set_level("");
						} else {
							row.getCell(7).setCellType(HSSFCell.CELL_TYPE_STRING);
							ii.set_level(row.getCell(7).toString().replaceAll("[　*| *| *|*]*", "").trim());
						}
						if (row.getCell(8) == null) {
							ii.setPosition("");
						} else {
							row.getCell(8).setCellType(HSSFCell.CELL_TYPE_STRING);
							ii.setPosition(row.getCell(8).toString().replaceAll("[　*| *| *|*]*", "").trim());
						}
						cell = row.getCell(9);
						if (cell != null) {
							cell.setCellType(HSSFCell.CELL_TYPE_STRING);
							String bir = cell.getStringCellValue();
							ii.setTel(bir.replaceAll("[　*| *| *|*]*", "").trim());
						} else {
							ii.setTel("");
						}
						if (row.getCell(10) == null) {
							ii.setRemark("");
						} else {
							row.getCell(10).setCellType(HSSFCell.CELL_TYPE_STRING);
							ii.setRemark(row.getCell(10).toString().replaceAll("[　*| *| *|*]*", "").trim());
						}
						if (row.getCell(11) == null) {
							ii.setExam_type("");
						} else {
							row.getCell(11).setCellType(HSSFCell.CELL_TYPE_STRING);
							ii.setExam_type(row.getCell(11).toString().replaceAll("[　*| *| *|*]*", "").trim());
						}
						if (row.getCell(12) == null) {
							ii.setCustomer_type("");
						} else {
							row.getCell(12).setCellType(HSSFCell.CELL_TYPE_STRING);
							ii.setCustomer_type(row.getCell(12).toString().replaceAll("[　*| *| *|*]*", "").trim());
						}
						if (row.getCell(13) == null) {
							ii.setOthers("");
						} else {
							row.getCell(13).setCellType(HSSFCell.CELL_TYPE_STRING);
							ii.setOthers(row.getCell(13).toString().replaceAll("[　*| *| *|*]*", "").trim());
						}
						if (row.getCell(14) == null) {
							ii.setEmployeeID("");
						} else {
							row.getCell(14).setCellType(HSSFCell.CELL_TYPE_STRING);
							ii.setEmployeeID(row.getCell(14).toString().replaceAll("[　*| *| *|*]*", "").trim());
						}
						if (row.getCell(15) == null) {
							ii.setVisit_no("");
						} else {
							row.getCell(15).setCellType(HSSFCell.CELL_TYPE_STRING);
							ii.setVisit_no(row.getCell(15).toString().replaceAll("[　*| *| *|*]*", "").trim());
						}
						if (row.getCell(16) == null) {
							ii.setIntroducer("");
						} else {
							row.getCell(16).setCellType(HSSFCell.CELL_TYPE_STRING);
							ii.setIntroducer(row.getCell(16).toString().replaceAll("[　*| *| *|*]*", "").trim());
						}
						// 证件类型名称
						// Cell idtypename = row.getCell(1);
						if (row.getCell(17) == null) {
							ii.setIdtypename("");
						} else {
							row.getCell(17).setCellType(HSSFCell.CELL_TYPE_STRING);
							ii.setIdtypename(row.getCell(17).toString().replaceAll("[　*| *| *|*]*", "").trim());
						}
						ii = getcheck(ii,customerInfoService);
						listDTO.add(ii);
					} catch (Exception ex) {
						error++;
					}
					rowIndex++;
					HSSFRow nextDataRow = worksheet.getRow(rowIndex);
					if(nextDataRow != null && nextDataRow.getCell(2) != null){
						nextDataRow.getCell(2).setCellType(Cell.CELL_TYPE_STRING);
					}
					if (nextDataRow != null && nextDataRow.getCell(2) != null
							&& !nextDataRow.getCell(2).getStringCellValue().equals("")) {
						continue;
					} else {
						hasRecord = false;
					}
				}
				customerInfoService.saveImpCustomerInfoForList(listDTO);
				message = "ok-成功导入" + (rowIndex - error - 2) + "条";
			}
		}
		return message;
	}

	/**
	 * 
	 * @Title: readXSSF @Description: 解析exl2007及其以后版本 @param: @param
	 *         company_id @param: @param companyname @param: @param
	 *         batch_id @param: @param batchname @param: @param
	 *         userid @param: @param workbook @param: @param
	 *         customerInfoService @param: @return @return: String @throws
	 */
	private String readXSSF(String company_id, String companyname, String batch_id, String batchname, long userid,
			XSSFWorkbook workbook, CustomerInfoService customerInfoService) {
		String message = "";
		XSSFSheet worksheet = workbook.getSheetAt(0);
		System.out.println(worksheet.getRow(0).getCell(1).toString());
		System.out.println(worksheet.getRow(0).getCell(3).toString());
		if (!companyname.equals(worksheet.getRow(0).getCell(1).toString().replaceAll("[　*| *| *|*]*", ""))
				|| (!batchname.equals(worksheet.getRow(0).getCell(3).toString().replaceAll("[　*| *| *|*]*", "")))) {
			message = ("error-公司或者批次信息和模版中的信息不符！");
		} else {
			XSSFRow firstDataRow = worksheet.getRow(2);
			if (null == firstDataRow) {
				message = ("error-请从第三行开始写入数据！");
			} else {
				List<ImpCustomerInfo> listDTO = new ArrayList<ImpCustomerInfo>();
				int rowIndex = 2;
				int error = 0;
				boolean hasRecord = true;
				while (hasRecord) {
					try {
						ImpCustomerInfo ii = new ImpCustomerInfo();
						ii.setInfo_type(InfoType.YBTJ.getCode());
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
							row.getCell(0).setCellType(HSSFCell.CELL_TYPE_STRING);
							ii.setArch_num(row.getCell(0).toString().replaceAll("[　*| *| *|*]*", "").trim());
						}
					
						Cell cell = row.getCell(1);
						if (cell != null) {
							cell.setCellType(HSSFCell.CELL_TYPE_STRING);
							String idnum = cell.getStringCellValue();
							ii.setId_num(idnum.replaceAll("[　*| *| *|*]*", "").trim());
						} else {
							ii.setId_num("");
						}
						row.getCell(2).setCellType(Cell.CELL_TYPE_STRING);
						if (row.getCell(2) == null) {
							ii.setCustname("");
						} else {
							ii.setCustname(row.getCell(2).toString().replaceAll("[　*| *| *|*]*", "").trim());
						}
						if (row.getCell(3) == null) {
							ii.setSex("");
						} else {
							ii.setSex(row.getCell(3).toString().replaceAll("[　*| *| *|*]*", "").trim());
						}
						if (row.getCell(4) == null) {
							ii.setAge(1);
						} else {
							row.getCell(4).setCellType(HSSFCell.CELL_TYPE_STRING);
							String str = row.getCell(4).toString().replaceAll("[　*| *| *|*]*", "");
							if (StringUtil.isEmpty(str)) {
								ii.setAge(1);
							} else {
								String[] arr = str.split("\\.");
								ii.setAge(Long.valueOf(arr[0].trim()));
							}
						}

						cell = row.getCell(5);
						if (cell != null) {
							System.out.println(cell.getCellStyle().getDataFormat());
							switch (cell.getCellType()) {
							case HSSFCell.CELL_TYPE_NUMERIC:// 数字类型
								if (HSSFDateUtil.isCellDateFormatted(cell)) {// 处理日期格式、时间格式
									SimpleDateFormat sdf = null;
									sdf = new SimpleDateFormat("yyyy-MM-dd");
									Date date = cell.getDateCellValue();
									ii.setBirthday(sdf.format(date));
								} else if (cell.getCellStyle().getDataFormat() == 176) {
									// 处理自定义日期格式：m月d日(通过判断单元格的格式id解决，id的值是58)
									SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
									double value = cell.getNumericCellValue();
									Date date = org.apache.poi.ss.usermodel.DateUtil.getJavaDate(value);
									ii.setBirthday(sdf.format(date));
								} else {
									double value = cell.getNumericCellValue();
									CellStyle style = cell.getCellStyle();
									DecimalFormat format = new DecimalFormat();
									String temp = style.getDataFormatString();
									// 单元格设置成常规
									if (temp.equals("General")) {
										format.applyPattern("#");
									}
									ii.setBirthday(value + "");
								}
								break;
							case HSSFCell.CELL_TYPE_STRING:// String类型
								String bir = cell.getStringCellValue();
								ii.setBirthday(bir.replaceAll("[年月日 -./\\s:]", "-"));
								break;
							case HSSFCell.CELL_TYPE_BLANK:
								ii.setBirthday("");
								break;
							default:
								ii.setBirthday("");
								break;
							}
						} else {
							ii.setBirthday("");
						}

						if (row.getCell(6) == null) {
							ii.setIs_marriage("");
						} else {
							ii.setIs_marriage(row.getCell(6).toString().replaceAll("[　*| *| *|*]*", "").trim());
						}
						if (row.getCell(7) == null) {
							ii.set_level("");
						} else {
							row.getCell(7).setCellType(HSSFCell.CELL_TYPE_STRING);
							ii.set_level(row.getCell(7).toString().replaceAll("[　*| *| *|*]*", "").trim());
						}
						if (row.getCell(8) == null) {
							ii.setPosition("");
						} else {
							row.getCell(8).setCellType(HSSFCell.CELL_TYPE_STRING);
							ii.setPosition(row.getCell(8).toString().replaceAll("[　*| *| *|*]*", "").trim());
						}
						cell = row.getCell(9);
						if (cell != null) {
							cell.setCellType(HSSFCell.CELL_TYPE_STRING);
							String bir = cell.getStringCellValue();
							ii.setTel(bir.replaceAll("[　*| *| *|*]*", "").trim());
						} else {
							ii.setTel("");
						}
						if (row.getCell(10) == null) {
							ii.setRemark("");
						} else {
							row.getCell(10).setCellType(HSSFCell.CELL_TYPE_STRING);
							ii.setRemark(row.getCell(10 ).toString().replaceAll("[　*| *| *|*]*", "").trim());
						}
						if (row.getCell(11) == null) {
							ii.setExam_type("");
						} else {
							row.getCell(11).setCellType(HSSFCell.CELL_TYPE_STRING);
							ii.setExam_type(row.getCell(11).toString().replaceAll("[　*| *| *|*]*", "").trim());
						}
						if (row.getCell(12) == null) {
							ii.setCustomer_type("");
						} else {
							row.getCell(12).setCellType(HSSFCell.CELL_TYPE_STRING);
							ii.setCustomer_type(row.getCell(12).toString().replaceAll("[　*| *| *|*]*", "").trim());
						}
						if (row.getCell(13) == null) {
							ii.setOthers("");
						} else {
							row.getCell(13).setCellType(HSSFCell.CELL_TYPE_STRING);
							ii.setOthers(row.getCell(13).toString().replaceAll("[　*| *| *|*]*", "").trim());
						}
						if (row.getCell(14) == null) {
							ii.setEmployeeID("");
						} else {
							row.getCell(14).setCellType(HSSFCell.CELL_TYPE_STRING);
							ii.setEmployeeID(row.getCell(14).toString().replaceAll("[　*| *| *|*]*", "").trim());
						}
						if (row.getCell(15) == null) {
							ii.setVisit_no("");
						} else {
							row.getCell(15).setCellType(HSSFCell.CELL_TYPE_STRING);
							ii.setVisit_no(row.getCell(15).toString().replaceAll("[　*| *| *|*]*", "").trim());
						}
						if (row.getCell(16) == null) {
							ii.setIntroducer("");
						} else {
							row.getCell(16).setCellType(HSSFCell.CELL_TYPE_STRING);
							ii.setIntroducer(row.getCell(16).toString().replaceAll("[　*| *| *|*]*", "").trim());
						}
						// 证件类型名称
						// Cell idtypename = row.getCell(1);
						if (row.getCell(17) == null) {
							ii.setIdtypename("");
						} else {
							row.getCell(17).setCellType(HSSFCell.CELL_TYPE_STRING);
							ii.setIdtypename(row.getCell(17).toString().replaceAll("[　*| *| *|*]*", "").trim());
						}
						ii = getcheck(ii,customerInfoService);
						listDTO.add(ii);
					} catch (Exception ex) {
						ex.printStackTrace();
						error++;
					}
					rowIndex++;
					XSSFRow nextDataRow = worksheet.getRow(rowIndex);
					
				/*	System.out.println(nextDataRow+"第一列");
					System.out.println(nextDataRow.getCell(3)+"第二列");
					System.out.println(nextDataRow.getCell(3).getStringCellValue()+"第三列");*/
					if(nextDataRow != null && nextDataRow.getCell(2) != null){
						nextDataRow.getCell(2).setCellType(Cell.CELL_TYPE_STRING);
					}
					if (nextDataRow != null && nextDataRow.getCell(2) != null
							&& !nextDataRow.getCell(2).getStringCellValue().equals("")) {
						continue;
					} else {
						hasRecord = false;
					}
				}
				customerInfoService.saveImpCustomerInfoForList(listDTO);
				message = "ok-成功导入" + (rowIndex - error - 2) + "条";
			}
		}
		return message;
	}

	/**
	 * 
	 * @Title: isExcel2007 @Description:
	 *         是否是2007的excel，返回true是2007 @param: @param
	 *         filePath @param: @return @return: boolean @throws
	 */

	public static boolean isExcel2007(String filePath) {
		return filePath.matches("^.+\\.(?i)(xlsx)$");
	}

	private static ImpCustomerInfo getcheck(ImpCustomerInfo ii,CustomerInfoService customerInfoService) throws ParseException {

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
//			String idnum_num = ValidateUtil.IDCardValidate(ii.getId_num());
			String djttype="N";
			String idnum_num="";
			try {
				 djttype = customerInfoService.getCenterconfigByKey("IS_ID_CARD", null).getConfig_value();// 体检人员操作控制, Y 不可以同事操作、N 可以同事操作
			} catch (Exception e) {
				e.printStackTrace();
			}
			if("Y".equals(djttype)){
				 idnum_num = ValidateUtil.IDCardValidatechenk(ii.getId_num());
			}else{
				 idnum_num = ValidateUtil.IDCardValidate(ii.getId_num());
			}
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
								ii.setSex(sexflag);
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

	private static boolean Validate() {
		// TODO Auto-generated method stub
		return false;
	}
}
