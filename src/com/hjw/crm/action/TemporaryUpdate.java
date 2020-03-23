package com.hjw.crm.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
import org.hibernate.id.IdentityGenerator.GetGeneratedKeysDelegate;
import org.hsqldb.lib.StringUtil;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.fr.third.org.apache.poi.hssf.usermodel.HSSFDateUtil;
import com.hjw.crm.DTO.TemporaryCustomerInfoDTO;
import com.hjw.crm.domain.TemporaryCustomerInfo;
import com.hjw.crm.service.TemporaryCustomerInfoService;
import com.hjw.util.DateTimeUtil;
import com.hjw.util.Timeutils;
import com.hjw.wst.domain.Batch;
import com.hjw.wst.domain.CompanyInfo;
import com.hjw.wst.domain.CustomerInfo;
import com.hjw.wst.domain.CustomerMemberInfo;
import com.hjw.wst.domain.ImpCustomerInfo;
import com.hjw.wst.service.BatchService;
import com.hjw.wst.service.CompanyService;
import com.hjw.wst.service.CustomerInfoService;
@SuppressWarnings("serial")
public class TemporaryUpdate extends HttpServlet  {
	@SuppressWarnings("unchecked")
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		String message = "ok-文件导入临时表，请您返回继续操作！";

		WebApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(this.getServletContext());
	
		TemporaryCustomerInfoService temporaryCustomerInfoService = (TemporaryCustomerInfoService)context.getBean("temporaryCustomerInfoService");
		boolean flagss=true;
	
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
					flagss=false;
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
							flagss=false;
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
							flagss=false;
						}
					}
					
                   if(flagss){
                	                  	   
                      try{ 
                    	  HSSFWorkbook wb = new HSSFWorkbook(new FileInputStream(file));  
                    	  message = readHSSF(wb,temporaryCustomerInfoService);
                       }catch(Exception ex) 
                       {  
                    	   XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(file));
                    	   try {
							message = readXSSF(workbook,temporaryCustomerInfoService);
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
                       }

				}
			
		}
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(message);
	
	}
	SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private String readHSSF(HSSFWorkbook workbook,TemporaryCustomerInfoService temporaryCustomerInfoService) throws ParseException{
		String message="";
		String regex = "(^\\d{18}$)|(^\\d{15}$)";
		Pattern pattern = Pattern.compile(regex);
		HSSFSheet worksheet = workbook.getSheetAt(0);
 		
        	  HSSFRow firstDataRow = worksheet.getRow(2);
 			   if (null== firstDataRow) {
 				message = ("error-请从第三行开始写入数据！");
 			   }else{
 				List<TemporaryCustomerInfo> listDTO = new ArrayList<TemporaryCustomerInfo>();
 				int rowIndex = 2;
				boolean hasRecord = true; 
				while (hasRecord) {
					TemporaryCustomerInfo ii = new TemporaryCustomerInfo();
				    ii.setFlag(0);
				    ii.setNotices("");
				    HSSFRow row = worksheet.getRow(rowIndex);
				    Cell  cell=row.getCell(0);;if(cell!=null){
				    	cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				    	String user_name= cell.getStringCellValue();
				    	ii.setUser_name(user_name.replaceAll("[　*| *| *|//s*]*", ""));
				    	}else{ii.setUser_name("");}
				    cell=row.getCell(1);;if(cell!=null){cell.setCellType(HSSFCell.CELL_TYPE_STRING);String id_num= cell.getStringCellValue();ii.setId_num(id_num.replaceAll("[　*| *| *|//s*]*", ""));}else{ii.setId_num("");}
				    cell=row.getCell(2);;if(cell!=null){cell.setCellType(HSSFCell.CELL_TYPE_STRING);String sex= cell.getStringCellValue();ii.setSex(sex.replaceAll("[　*| *| *|//s*]*", ""));}else{ii.setSex("");}
				    cell=row.getCell(3);;if(cell!=null){cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				    String nation= cell.getStringCellValue().replaceAll("[　*| *| *|//s*]*", "");
				    		;ii.setNation(temporaryCustomerInfoService.getDataId(nation));}else{ii.setNation("");}
				    cell=row.getCell(4);;if(cell!=null){
				    	cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
				    	Double birthday= cell.getNumericCellValue();
				    	Date date=HSSFDateUtil.getJavaDate(birthday);
				    	ii.setBirthday(DateTimeUtil.shortFmt3(date));
				    	}else{ii.setBirthday(null);}
				    cell=row.getCell(5);;if(cell!=null){cell.setCellType(HSSFCell.CELL_TYPE_STRING);String phone= cell.getStringCellValue();ii.setPhone(phone.replaceAll("[　*| *| *|//s*]*", ""));}else{ii.setPhone("");}
				    cell=row.getCell(6);;if(cell!=null){cell.setCellType(HSSFCell.CELL_TYPE_STRING);String address= cell.getStringCellValue();ii.setAddress(address.replaceAll("[　*| *| *|//s*]*", ""));}else{ii.setAddress("");}
				    cell=row.getCell(7);;if(cell!=null){cell.setCellType(HSSFCell.CELL_TYPE_STRING);String email= cell.getStringCellValue();ii.setEmail(email.replaceAll("[　*| *| *|//s*]*", ""));}else{ii.setEmail("");}
				    cell=row.getCell(8);;if(cell!=null){cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				    String level= cell.getStringCellValue().replaceAll("[　*| *| *|//s*]*", "");
				    String level_code=temporaryCustomerInfoService.getDataId(level);
				    if(level_code!=null&&level_code.length()>0){
				    	ii.setLevel(Long.valueOf(level_code));
				    }else{
				    	ii.setLevel(0);
				    }
				    }else{ii.setLevel(0);}
				    cell=row.getCell(9);;if(cell!=null){cell.setCellType(HSSFCell.CELL_TYPE_STRING);String integral= cell.getStringCellValue();ii.setIntegral(Long.valueOf(integral.replaceAll("[　*| *| *|//s*]*", "")));}else{ii.setIntegral(0);}
				    if(ii.getUser_name()!=null&&ii.getUser_name().length()>0){
				    	if(ii.getId_num()!=null&&ii.getId_num().length()>0){
				    		Matcher matcher = pattern.matcher(ii.getId_num());
				    		if(matcher.matches()){
				    			List<TemporaryCustomerInfo> list=temporaryCustomerInfoService.getTemporaryCustomerInfoByIdBum(ii.getId_num());
				    			if(list!=null&&list.size()>0){
				    				ii.setFlag(1);
							    	ii.setNotices("该身份证号已存在");
				    			}
				    			List<CustomerInfo> list2=temporaryCustomerInfoService.getCustomerInfoByIdBum(ii.getId_num());
				    			if(list2!=null&&list2.size()>0){
				    				ii.setFlag(2);
				    				ii.setNotices("该会有有过体检，但之前不是会员");
				    				List<CustomerMemberInfo> list1=temporaryCustomerInfoService.getCustomerMemberInfoByIdBum(list2.get(0).getArch_num());
					    			if(list1!=null&&list1.size()>0){
					    				ii.setFlag(1);
								    	ii.setNotices("会有表中该的身份证号已经注册");
					    			}
				    			}
				    			
				    		}else{
				    			ii.setFlag(1);
						    	ii.setNotices("身份证号格式错误");
				    		}
				    	}else{
				    		ii.setFlag(1);
					    	ii.setNotices("身份证号为空");
				    	}
				    }else{
				    	ii.setFlag(1);
				    	ii.setNotices("姓名为空");
				    }
					listDTO.add(ii);
					rowIndex++;
					HSSFRow nextDataRow = worksheet.getRow(rowIndex);
					if (nextDataRow != null) {
						continue;
					} else {
						hasRecord = false;
					}
				}
				temporaryCustomerInfoService.saveTemporaryCustomerInfoForList(listDTO);
				message = "ok-成功导入" + (rowIndex - 2) + "条";
			}
		
 			   return message;
	}
	
	private String readXSSF(XSSFWorkbook workbook,TemporaryCustomerInfoService temporaryCustomerInfoService) throws ParseException {
		String message = "";
		String regex = "(^\\d{18}$)|(^\\d{15}$)";
		Pattern pattern = Pattern.compile(regex);
		XSSFSheet worksheet = workbook.getSheetAt(0);
		
			XSSFRow firstDataRow = worksheet.getRow(2);
			if (null == firstDataRow) {
				message = ("error-请从第三行开始写入数据！");
			} else {
				List<TemporaryCustomerInfo> listDTO = new ArrayList<TemporaryCustomerInfo>();
				int rowIndex = 2;
				boolean hasRecord = true;
				while (hasRecord) {
					TemporaryCustomerInfo ii = new TemporaryCustomerInfo();
					ii.setFlag(0);
					ii.setNotices("");
					XSSFRow row = worksheet.getRow(rowIndex);
					  Cell  cell=row.getCell(0);;if(cell!=null){cell.setCellType(HSSFCell.CELL_TYPE_STRING);String user_name= cell.getStringCellValue();ii.setUser_name(user_name.replaceAll("[　*| *| *|//s*]*", ""));}else{ii.setUser_name("");}
					    cell=row.getCell(1);;if(cell!=null){cell.setCellType(HSSFCell.CELL_TYPE_STRING);String id_num= cell.getStringCellValue();ii.setId_num(id_num.replaceAll("[　*| *| *|//s*]*", ""));}else{ii.setId_num("");}
					    cell=row.getCell(2);;if(cell!=null){cell.setCellType(HSSFCell.CELL_TYPE_STRING);String sex= cell.getStringCellValue();ii.setSex(sex.replaceAll("[　*| *| *|//s*]*", ""));}else{ii.setSex("");}
					    cell=row.getCell(3);;if(cell!=null){cell.setCellType(HSSFCell.CELL_TYPE_STRING);
					    String nation= cell.getStringCellValue().replaceAll("[　*| *| *|//s*]*", "");
			    		;ii.setNation(temporaryCustomerInfoService.getDataId(nation));}else{ii.setNation("");}
					    cell=row.getCell(4);;if(cell!=null){
					    	cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
					    	Double birthday= cell.getNumericCellValue();
					    	Date date=HSSFDateUtil.getJavaDate(birthday);
					    	ii.setBirthday(DateTimeUtil.shortFmt3(date));
					    	}else{
					    		ii.setBirthday(null);}
					    cell=row.getCell(5);;if(cell!=null){cell.setCellType(HSSFCell.CELL_TYPE_STRING);String phone= cell.getStringCellValue();ii.setPhone(phone.replaceAll("[　*| *| *|//s*]*", ""));}else{ii.setPhone("");}
					    cell=row.getCell(6);;if(cell!=null){cell.setCellType(HSSFCell.CELL_TYPE_STRING);String address= cell.getStringCellValue();ii.setAddress(address.replaceAll("[　*| *| *|//s*]*", ""));}else{ii.setAddress("");}
					    cell=row.getCell(7);;if(cell!=null){cell.setCellType(HSSFCell.CELL_TYPE_STRING);String email= cell.getStringCellValue();ii.setEmail(email.replaceAll("[　*| *| *|//s*]*", ""));}else{ii.setEmail("");}
					    cell=row.getCell(8);;if(cell!=null){cell.setCellType(HSSFCell.CELL_TYPE_STRING);
					    String level= cell.getStringCellValue().replaceAll("[　*| *| *|//s*]*", "");
					    String level_code=temporaryCustomerInfoService.getDataId(level);
					    if(level_code!=null&&level_code.length()>0){
					    	ii.setLevel(Long.valueOf(level_code));
					    }else{
					    	ii.setLevel(0);
					    }
					    }else{ii.setLevel(0);}
					    cell=row.getCell(9);;if(cell!=null){cell.setCellType(HSSFCell.CELL_TYPE_STRING);String integral= cell.getStringCellValue();ii.setIntegral(Long.valueOf(integral.replaceAll("[　*| *| *|//s*]*", "")));}else{ii.setIntegral(0);}
					    if(ii.getUser_name()!=null&&ii.getUser_name().length()>0){
					    	if(ii.getId_num()!=null&&ii.getId_num().length()>0){
					    		Matcher matcher = pattern.matcher(ii.getId_num());
					    		if(matcher.matches()){
					    			List<TemporaryCustomerInfo> list=temporaryCustomerInfoService.getTemporaryCustomerInfoByIdBum(ii.getId_num());
					    			if(list!=null&&list.size()>0){
					    				ii.setFlag(1);
								    	ii.setNotices("该身份证号已存在");
					    			}
					    			List<CustomerInfo> list2=temporaryCustomerInfoService.getCustomerInfoByIdBum(ii.getId_num());
					    			if(list2!=null&&list2.size()>0){
					    				ii.setFlag(2);
					    				ii.setNotices("该会有有过体检，但之前不是会员");
					    				List<CustomerMemberInfo> list1=temporaryCustomerInfoService.getCustomerMemberInfoByIdBum(list2.get(0).getArch_num());
						    			if(list1!=null&&list1.size()>0){
						    				ii.setFlag(1);
									    	ii.setNotices("会有表中该的身份证号已经注册");
						    			}
					    			}
					    		}else{
					    			ii.setFlag(1);
							    	ii.setNotices("身份证号格式错误");
					    		}
					    	}else{
					    		ii.setFlag(1);
						    	ii.setNotices("身份证号为空");
					    	}
					    }else{
					    	ii.setFlag(1);
					    	ii.setNotices("姓名为空");
					    }

					ii = getcheck(ii);
					listDTO.add(ii);
					rowIndex++;
					XSSFRow nextDataRow = worksheet.getRow(rowIndex);
					if (nextDataRow != null) {
						continue;
					} else {
						hasRecord = false;
					}
				}
				temporaryCustomerInfoService.saveTemporaryCustomerInfoForList(listDTO);
				message = "ok-成功导入" + (rowIndex - 2) + "条";
			
		}
		return message;
	}
	/**
	 * 
	     * @Title: isExcel2007   
	     * @Description: 是否是2007的excel，返回true是2007    
	     * @param: @param filePath
	     * @param: @return      
	     * @return: boolean      
	     * @throws
	 */
  
    public static boolean isExcel2007(String filePath)  
    {    
        return filePath.matches("^.+\\.(?i)(xlsx)$");    
    }  
  
    private static TemporaryCustomerInfo getcheck(TemporaryCustomerInfo ii) throws ParseException{
    	if (((ii.getId_num() == null) || (ii.getId_num().trim().length() != 18)) 
				&& ((ii.getBirthday() == null))) {
			ii.setNotices("证件号格式错误、生日都不存在");
			ii.setFlag(1);
		}else{

		if (!StringUtil.isEmpty(ii.getId_num())) {
			if (ii.getId_num().trim().length() != 18) {
				ii.setNotices("身份证号位数不对");
				ii.setFlag(1);
			} else {
					String bird = ii.getId_num().trim().substring(6, 14);
					Date da = Timeutils.strToDateLong2(bird);
					bird = DateTimeUtil.shortFmt3(da);
					ii.setBirthday(bird);
					
					
					if (Integer.valueOf(ii.getId_num().trim().substring(16, 17)) % 2 == 1) {
				           //男
						ii.setSex("男");
				       }else{
				           //女
				    	 ii.setSex("女");
				       }
			}
		} else if (!StringUtil.isEmpty(ii.getBirthday().toString())) {
			if (ii.getBirthday().toString().trim().length() != 10) {
				if (ii.getBirthday().toString().trim().length() == 8) {
					String bird = ii.getBirthday().toString().trim();
					Date da = Timeutils.strToDateLong2(bird);
					bird = DateTimeUtil.shortFmt3(da);
					ii.setBirthday(bird);
				} else {
					ii.setNotices("出生日期格式错误");
					ii.setFlag(1);
				}
			}
          
		} 
		if (StringUtil.isEmpty(ii.getSex())) {
			ii.setNotices("性别不能为空");
			ii.setFlag(1);
		}
    }
    return ii;
  }

}
