package com.hjw.wst.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.hsqldb.lib.StringUtil;

import com.hjw.util.DateTimeUtil;
import com.hjw.wst.DTO.DepExamResultDTO;
import com.hjw.wst.DTO.ExamDepResultDTO;
import com.hjw.wst.DTO.ExamInfoUserDTO;
import com.hjw.wst.DTO.ExamSummaryLogDTO;
import com.hjw.wst.DTO.ExaminfoDiseaseDTO;
import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.wst.DTO.SysLogDTO;
import com.hjw.wst.DTO.UserInfoDTO;
import com.hjw.wst.DTO.WebXtgnbDTO;
import com.hjw.wst.DTO.WebXtgncdDTO;
import com.hjw.wst.domain.SysLog;
import com.hjw.wst.model.SyslogModel;
import com.hjw.wst.service.SyslogService;
import com.synjones.framework.exception.ServiceException;
import com.synjones.framework.persistence.JdbcPersistenceManager;
import com.synjones.framework.persistence.JdbcQueryManager;
import com.synjones.framework.persistence.PersistenceManager;
import com.synjones.framework.persistence.QueryManager;
import com.synjones.framework.support.PageSupport;

import jxl.Workbook;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

public class SyslogServiceImpl implements SyslogService {
	private QueryManager qm;
	private PersistenceManager pm;
	private JdbcPersistenceManager jpm;
	private JdbcQueryManager jqm;
	
	public void saveSysLog(SysLog sysLog) {
		sysLog.setCreatedate(DateTimeUtil.getDateTime());
		pm.save(sysLog);
	}	


	public void delSysLog(SysLog sysLog) {
		pm.remove(sysLog);
	}
	
	public void setQueryManager(QueryManager qm) {
		this.qm = qm;
	}
	
	public void setPersistenceManager(PersistenceManager pm) {
		this.pm = pm;
	}
	
	public void setJdbcPersistenceManager(JdbcPersistenceManager jpm) {
		this.jpm = jpm;
	}
	
	public void setJdbcQueryManager(JdbcQueryManager jqm) {
		this.jqm = jqm;
	}

	/**
	 * 
	     * @Title: findList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param xtgnid 系统主功能id
	     * @param: @param xtgnid2
	     * @param: @param oper_type
	     * @param: @param userid
	     * @param: @param centernum
	     * @param: @param starttime
	     * @param: @param endtime
	     * @param: @param currentPage
	     * @param: @param pageSize
	     * @param: @return      
	     * @return: PageReturnDTO      
	     * @throws
	 */
	public PageReturnDTO findList(String xtgnid,String xtgnid2,String oper_type,String userid,String centernum,String starttime,String endtime,int currentPage, int pageSize)
	{
		 String sql="select s.ID,s.userid,s.oper_type,u.chi_name as username,m.name as xtgnname,m.name as "
				   +" xtgnname2,s.createdate,s.explain,s.remark,s.remark1,s.remark2 "
				   +" from sys_log s left join user_usr u on s.userid=u.ID "
				   +" left join WEB_XTGNB m on m.ID=s.xtgnid2 where s.center_num='"+centernum+"'";
	     if(!StringUtil.isEmpty(starttime))
	     {
	    	 starttime+=" 00:00:00";
	    	 sql+=" and s.createdate>='"+starttime+"' ";
	     }
	     if(!StringUtil.isEmpty(endtime))
	     {
	    	 endtime+=" 23:59:59";
	    	 sql+=" and s.createdate<='"+endtime+"' ";
	     }
	     
	     if(!StringUtil.isEmpty(xtgnid)){
	    	 sql+=" and m.FATHERACTION='"+xtgnid+"' ";
	     }
	     
	     /*if(!StringUtil.isEmpty(xtgnid2)){
	    	 sql+=" and s.xtgnid2=='"+xtgnid2+"' ";
	     }*/
	     
	     if(!StringUtil.isEmpty(userid) && !"0".equals(userid)){
	    	 sql+=" and s.userid='"+userid+"' ";
	     }
	     
	     if(!StringUtil.isEmpty(oper_type)){
	    	 sql+=" and s.oper_type='"+oper_type+"' ";
	     }
	     
	     sql+= " order by s.createdate desc";
	     //System.out.println(sql);
		 PageSupport map = this.jqm.getList(sql,currentPage,pageSize, SysLogDTO.class);
	      PageReturnDTO data = new PageReturnDTO();
	      data.setPage(currentPage);
	      data.setRp(pageSize);
	      data.setTotal(map.getRecTotal());
	      data.setRows(map.getList());
	      return data;
	}


	@Override
	public List<UserInfoDTO> getSyslogUserList(String center_num) throws ServiceException {
		String sql = "select distinct u.id,u.chi_name from user_usr u,exam_user e where u.id = e.user_id and u.is_active = 'Y' and e.center_num='"+center_num+"' order by u.chi_name";
		List<UserInfoDTO> list = this.jqm.getList(sql, UserInfoDTO.class);
		return list;
	}


	@Override
	public List<WebXtgnbDTO> getSyslogXtgnList() throws ServiceException {
		String sql = "SELECT [ID],[URL],[NAME],[TYPE],[REMARK],[FATHERACTION],[ACTIONTYPE],[ADMINTYPE]"
				+" FROM [dbo].[WEB_XTGNB] where ID=FATHERACTION and ACTIONTYPE=1 and TYPE<>2 order by FATHERACTION";
		List<WebXtgnbDTO> list = this.jqm.getList(sql, WebXtgnbDTO.class);
		return list;
	}


	@Override
	public String backupSyslog(String xtgnid, String xtgnid2, String oper_type, String userid, String centernum,
			String starttime, String endtime,String path) throws IOException {
		
		File file = new File(path);
		if(!file.exists()){//判断文件是否存在;
			boolean bol = file.mkdirs();//创建文件夹  
            if(!bol){
            	return "no-创建文件夹失败!";
            }
		}
//		WritableWorkbook workbook = Workbook.createWorkbook(new File(path+"/" + new SimpleDateFormat("yyyyMMddHHmmsss").format(new Date() )+".xls"));
		//第一步，创建一个webbook，对应一个Excel文件  
        HSSFWorkbook wb = new HSSFWorkbook();  
        //第二步，在webbook中添加一个sheet，对应Excel文件中的 sheet  
        HSSFSheet sheet = wb.createSheet("系统日志");  
        //第三步，在sheet中添加表头第0行，注意老版本poi对Excel的行数列数有限制  
        HSSFRow row = sheet.createRow(0);  
        //第四步，创建单元格样式：居中  
        HSSFCellStyle style = wb.createCellStyle();  
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);  
        //第五步，创建表头单元格，并设置样式  
        HSSFCell cell;  

        cell = row.createCell(0);  
        cell.setCellValue("操作人");  
        cell.setCellStyle(style);  

        cell = row.createCell(1);  
        cell.setCellValue("操作类型");  
        cell.setCellStyle(style);  

        cell = row.createCell(2);  
        cell.setCellValue("功能");  
        cell.setCellStyle(style);  

        cell = row.createCell(3);  
        cell.setCellValue("子功能");  
        cell.setCellStyle(style);  

        cell = row.createCell(4);  
        cell.setCellValue("说明");  
        cell.setCellStyle(style);  

        cell = row.createCell(5);  
        cell.setCellValue("操作时间");  
        cell.setCellStyle(style);
		
		 String sql="select s.ID,s.userid,s.oper_type,u.chi_name as username,m.name as xtgnname,m.name as "
				   +" xtgnname2,s.createdate,s.explain,s.remark,s.remark1,s.remark2 "
				   +" from sys_log s left join user_usr u on s.userid=u.ID "
				   +" left join WEB_XTGNB m on m.ID=s.xtgnid2 where s.center_num='"+centernum+"'";
	     if(!StringUtil.isEmpty(starttime))
	     {
	    	 starttime+=" 00:00:00";
	    	 sql+=" and s.createdate>='"+starttime+"' ";
	     }
	     if(!StringUtil.isEmpty(endtime))
	     {
	    	 endtime+=" 23:59:59";
	    	 sql+=" and s.createdate<='"+endtime+"' ";
	     }
	     
	     if(!StringUtil.isEmpty(xtgnid)){
	    	 sql+=" and m.FATHERACTION='"+xtgnid+"' ";
	     }
	     
	     /*if(!StringUtil.isEmpty(xtgnid2)){
	    	 sql+=" and s.xtgnid2=='"+xtgnid2+"' ";
	     }*/
	     
	     if(!StringUtil.isEmpty(userid) && !"0".equals(userid)){
	    	 sql+=" and s.userid='"+userid+"' ";
	     }
	     
	     if(!StringUtil.isEmpty(oper_type)){
	    	 sql+=" and s.oper_type='"+oper_type+"' ";
	     }
	     
	     sql+= " order by s.createdate desc";
	     
	    List<SysLogDTO> list = this.jqm.getList(sql, SysLogDTO.class);
	    
	    for (int i = 0; i < list.size(); i++) {
	    	SysLogDTO sysLog = list.get(i);
	    	
	    	row = sheet.createRow(i+1);
    		
    		row.createCell(0).setCellValue(sysLog.getUsername());  
            row.createCell(1).setCellValue(sysLog.getOper_types());  
            row.createCell(2).setCellValue(sysLog.getXtgnname());  
            row.createCell(3).setCellValue(sysLog.getXtgnname2());  
            row.createCell(4).setCellValue(sysLog.getExplain());  
            row.createCell(5).setCellValue(sysLog.getCreatedate());
            
            String delSql = "delete sys_log where ID = '"+sysLog.getId()+"'";
            
            this.jpm.executeSql(delSql); //删除备份过的日志
		}
	    String fileName = new SimpleDateFormat("yyyyMMddHHmmsss").format(new Date() );
	    FileOutputStream fileStream = new FileOutputStream(path+"\\" + fileName +".xls");
	    wb.write(fileStream);
	    fileStream.close();
	    
	    byte[] buffer = new byte[1024];
	    ZipOutputStream out = new ZipOutputStream(new FileOutputStream(path+"\\" + fileName +".zip"));

	    File excelFile = new File(path+"\\" + fileName +".xls");
		FileInputStream fis = new FileInputStream(excelFile);
		out.putNextEntry(new ZipEntry(excelFile.getName()));

		int len;
		// 读入需要下载的文件的内容，打包到zip文件
		while ((len = fis.read(buffer)) > 0) {
			out.write(buffer, 0, len);
		}
		out.closeEntry();
		fis.close();
		out.close();
		
		excelFile.delete();//删除excel文件
	    
		return "ok-系统日志备份成功!";
	}
	
	@Override
	public PageReturnDTO getExamInfoAuditListLog(SyslogModel model, String center_num, int rows, int page, String sort, String order)
			throws ServiceException {
		int count = 0;
		String sql =" select e.status,e.id,c.arch_num,c.id_num,e.exam_num,e.age,c.user_name,c.sex,e.phone,e.address,e.introducer,"
				   +" convert(varchar(50),e.join_date,23) as join_date,e.exam_type,e.join_date as d, "
				   +" dbo.GetExamSetNameByExamID(e.exam_num) as set_name,v.com_name as company,e.is_guide_back,"
				   +" e.is_need_guide,e.is_need_barcode,e.counter_check,convert(varchar(50),e.register_date,23) as register_date,"
				   +" dbo.GetTeamPayByExamId(e.exam_num) as team_pay,dbo.GetPersonalPayByExamId(e.exam_num) as personal_pay,dbo.GetMinExamDateByExamId(e.exam_num) as exam_date"
				   +" ,(select sum(ec.calculation_amount) from examinfo_charging_item ec where ec.exam_num = e.exam_num and ec.isActive = 'Y' and ec.pay_status <> 'M' and ec.center_num = '"+center_num+"' ) as calculation_amount"
				   +" from customer_info c,exam_info e "
				   + " left join company_info v on v.id=e.company_id "
				   +" where e.customer_id = c.id and e.is_Active = 'Y'";
//		if (model.getArch_num() != null && !"".equals(model.getArch_num())) {// 档案号
//			sql += " and c.arch_num='" + model.getArch_num().trim() + "'";
//			count ++;
//		}
		if (model.getId_num() != null && !"".equals(model.getId_num())) {// 身份证
			sql += " and c.id_num='" + model.getId_num() + "'";
			count ++;
		}  
		if (model.getExam_num() != null && !"".equals(model.getExam_num())) {// 体检号
			sql += " and (e.exam_num='" + model.getExam_num().trim() + "' or c.arch_num='" + model.getExam_num().trim() + "')";
			count ++;
		}
		if (model.getUser_name() != null && !"".equals(model.getUser_name())) {// 体检姓名
			sql += " and c.user_name   like '" + model.getUser_name().trim() + "%'";
			count ++;
		}
	
		if (model.getS_join_date() != null && !"".equals(model.getS_join_date())) {// 体检开始日期
			sql += " and e.join_date >= '" + model.getS_join_date() + "'";
			count ++;
		} 
		if (model.getE_join_date() != null && !"".equals(model.getE_join_date())) {// 体检结束日期
			sql += " and e.join_date <= '" + model.getE_join_date() + "'";
			count ++;
		} 
		
		if (model.getEmployeeID() != null && "".equals(model.getEmployeeID())) {// 工号
			sql += " and e.employeeID='" + model.getEmployeeID().trim() + "'";
			count ++;
		} 
		if (model.getCompany_id() > 0) {
			sql += " and e.company_id='" + model.getCompany_id() + "'";
			count ++;
		}
		if (model.getExam_type() != null && !"".equals(model.getExam_type())){
			sql += " and e.exam_type ='" + model.getExam_type() + "'";
		}
		if (model.getPhone() != null && !"".equals(model.getPhone())){
			sql += " and e.phone = '" + model.getPhone() +"'";
			count ++;
		}
		if (model.getStatus() != null && !"".equals(model.getStatus())){
			sql += " and e.status = '"+model.getStatus()+"'";
		}
		if (model.getSex() != null && !"".equals(model.getSex())){
			sql += " and c.sex = '"+model.getSex()+"' ";
		}
		
		if(sort != null && !"".equals(sort)){
			sql += " order by "+sort+" "+order;
		}else{
			sql += " order by e.join_date desc";
		}
		PageReturnDTO webrole = new PageReturnDTO();
		webrole.setPage(rows);
		webrole.setRp(page);
		if(count == 0){
			return webrole;
		}
		PageSupport map = this.jqm.getList(sql,page, rows,ExamInfoUserDTO.class);
		if ((map != null) && (map.getList() != null)) {
			webrole.setTotal(map.getRecTotal());
			webrole.setRows(map.getList());
		}
		return webrole;
	}
	
	@Override
	public List<ExamDepResultDTO> getExamDepResultCountLog(String exam_num, String center_num) throws ServiceException {
		String sql = "select distinct d.seq_code,d.id,d.dep_name,(select count(*) from exam_dep_result_log l where l.exam_num = ec.exam_num "
				+ "and l.dep_id = d.id) check_count from examinfo_charging_item ec,charging_item c,department_dep d "
				+ "where ec.charging_item_code = c.item_code and c.dep_id = d.id and d.dep_category = '17' and ec.exam_num = '"+exam_num+"' "
				+ "and ec.isActive = 'Y' and ec.pay_status <> 'M' and ec.exam_status <> 'G' and ec.center_num = '"+center_num+"' and c.item_category = '普通类型' "
				+ "order by d.seq_code";
		List<ExamDepResultDTO> list = this.jqm.getList(sql, ExamDepResultDTO.class);
		return list;
	}
	
	@Override
	public List<DepExamResultDTO> getCommonExamDetailListLog(long examinfo_id, long dep_id, long row, String center_num,String exam_num)
			throws ServiceException {
		String sql ="select a.id from (select row=ROW_NUMBER() over (order by e.exam_date), e.id,e.exam_date "
				+ "from exam_dep_result_log e where e.exam_num = '"+exam_num+"' and e.dep_id = "+dep_id+") a where a.row = "+row;
		List<DepExamResultDTO> list = this.jqm.getList(sql, DepExamResultDTO.class);
		if(list.size() > 0){
			sql = "select e.item_name,e.id as item_id,l.exam_result,l.health_level,dl.exam_doctor, dl.exam_date "
					+ "from exam_dep_result_log dl,examinfo_charging_item ec,charging_item c,charging_item_exam_item cit,"
					+ "examination_item e left join common_exam_detail_log l on l.dep_result_id = "+list.get(0).getId()+" and l.item_num = e.item_num "
					+ "where ec.charging_item_code = c.item_code and c.item_code = cit.charging_item_code and cit.item_code = e.item_num and ec.isActive = 'Y' "
					+ "and ec.pay_status <> 'M' and ec.exam_status <> 'G' and ec.center_num = '"+center_num+"' and dl.id = "+list.get(0).getId()+" and dl.exam_num = ec.exam_num "
					+ "and dl.dep_id = c.dep_id "
					+ "union all "
					+ "select '科室结论' as item_name,0 as item_id,dl.exam_result_summary exam_result,'' as health_level,"
					+ "dl.exam_doctor,dl.exam_date from exam_dep_result_log dl where dl.id = "+list.get(0).getId();
			list = this.jqm.getList(sql, DepExamResultDTO.class);
		}
		return list;
	}
	
	@Override
	public List<ExamSummaryLogDTO> getExamSummaryCountLog(String exam_num) throws ServiceException {
		String sql = "select e.id,e.exam_date,e.operation_type,u.chi_name exam_doctor from exam_summary_log e,user_usr u "
				+ "where e.exam_num = '"+exam_num+"' and e.exam_doctor_id = u.id order by e.id";
		List<ExamSummaryLogDTO> list = this.jqm.getList(sql, ExamSummaryLogDTO.class);
		return list;
	}
	
	@Override
	public List<ExaminfoDiseaseDTO> getExamExamDiseaseListLog(long id) throws ServiceException {
		String sql = "select e.disease_name,e.suggest,e.remarke final_remarke from examinfo_disease_log e "
					+ "where e.summary_id = "+id+" order by e.disease_index";
		List<ExaminfoDiseaseDTO> diseaselist = this.jqm.getList(sql, ExaminfoDiseaseDTO.class);
		return diseaselist;
	}


}
