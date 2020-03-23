package com.hjw.wst.service.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hjw.util.DateTimeUtil;
import com.hjw.util.IsPrivateCenterConfig;
import com.hjw.util.ProduceSql;
import com.hjw.util.ResultSetMapper;
import com.hjw.wst.DTO.CompanyInfoVsCenterDTO;
import com.hjw.wst.DTO.DataBaseConfigDTO;
import com.hjw.wst.DTO.ExamInfoUserDTO;
import com.hjw.wst.DTO.ExaminfoBatchDTO;
import com.hjw.wst.DTO.OutDataExpDTO;
import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.wst.DTO.UserDTO;
import com.hjw.wst.config.GetNumContral;
import com.hjw.wst.config.GetOtherNumContral;
import com.hjw.wst.domain.Batch;
import com.hjw.wst.domain.CommonExamDetail;
import com.hjw.wst.domain.CompanyInfo;
import com.hjw.wst.domain.Critical;
import com.hjw.wst.domain.CustomerInfo;
import com.hjw.wst.domain.DataMigrationCompany;
import com.hjw.wst.domain.DataMigrationExam;
import com.hjw.wst.domain.ExamInfo;
import com.hjw.wst.domain.ExamResultChargingItem;
import com.hjw.wst.domain.ExamResultDetail;
import com.hjw.wst.domain.ExamSummary;
import com.hjw.wst.domain.ExamdepResult;
import com.hjw.wst.domain.ExaminfoChargingItem;
import com.hjw.wst.domain.ExaminfoDisease;
import com.hjw.wst.domain.GroupChargingItem;
import com.hjw.wst.domain.GroupInfo;
import com.hjw.wst.domain.GroupSet;
import com.hjw.wst.domain.PacsSummary;
import com.hjw.wst.domain.Pacsdetail;
import com.hjw.wst.domain.SampleExamDetail;
import com.hjw.wst.domain.ViewExamDetail;
import com.hjw.wst.domain.ViewExamImage;
import com.hjw.wst.domain.ViewExamImageBase64;
import com.hjw.wst.domain.ViewExamItem;
import com.hjw.wst.model.DataMigrationModel;
import com.hjw.wst.service.DataMigrationService;
import com.synjones.framework.exception.ServiceException;
import com.synjones.framework.persistence.JdbcPersistenceManager;
import com.synjones.framework.persistence.JdbcQueryManager;
import com.synjones.framework.support.PageSupport;

public class DataMigrationServiceImpl implements DataMigrationService {
	
	private JdbcQueryManager jqm;
	private JdbcPersistenceManager jpm; 
	
	public void setJdbcPersistenceManager(JdbcPersistenceManager jpm) {
		this.jpm = jpm;
	}

	public void setJdbcQueryManager(JdbcQueryManager jqm) {
		this.jqm = jqm;
	}
	
	@Override
	public List<DataBaseConfigDTO> getDataBaseConfigList() throws ServiceException {
		String sql = "select * from data_base_config d";
		List<DataBaseConfigDTO> list = this.jqm.getList(sql, DataBaseConfigDTO.class);
		return list;
	}

	@Override
	public String saveDataBaseConfig(DataBaseConfigDTO dto) throws ServiceException {
		String sql = "update data_base_config set database_url='"+dto.getDatabase_url()+"',database_port='"+dto.getDatabase_port()+"',"
				+ "database_uame='"+dto.getDatabase_uame()+"',username='"+dto.getUsername()+"',password='"+dto.getPassword()+"' where type="+dto.getType();
		this.jpm.executeSql(sql);
		return "ok-保存配置成功!";
	}
	
	@Override
	public DataBaseConfigDTO getDataBaseConfigOne(long type) throws ServiceException {
		String sql = "select * from data_base_config d where d.type="+type;
		List<DataBaseConfigDTO> list = this.jqm.getList(sql, DataBaseConfigDTO.class);
		if(list.size() > 0){
			return list.get(0);
		}
		return null;
	}

	@Override
	public List<Map<String,Object>> getNeedDataMigration(String exam_nums,String center_num) throws ServiceException {
		String[] exam_num = exam_nums.split(",");
		
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < exam_num.length; i++) {
			Map<String,Object> map = new HashMap<String,Object>();
			
			List<CustomerInfo> customer = this.jqm.getList("select c.* from customer_info c,exam_info e where e.customer_id = c.id and e.exam_num = '"+exam_num[i]+"'", CustomerInfo.class);//档案信息
			map.put("customer", customer);
			
			List<ExamInfo> examinfo = this.jqm.getList("select * from exam_info e where e.exam_num = '"+exam_num[i]+"'", ExamInfo.class);//体检信息
			map.put("examinfo", examinfo);
			
			List<ExaminfoBatchDTO> examinfobatch = this.jqm.getList("select * from examinfo_batch e where e.exam_num = '"+exam_num[i]+"'", ExaminfoBatchDTO.class);//
			map.put("examinfobatch", examinfobatch);
			
			List<ExaminfoChargingItem> examitem = this.jqm.getList("select * from examinfo_charging_item ec where ec.exam_num = '"+exam_num[i]+"'", ExaminfoChargingItem.class); //体检项目信息
			map.put("examitem",examitem);
			
			List<SampleExamDetail> exam_sample = this.jqm.getList("select * from sample_exam_detail s where s.exam_num = '"+exam_num[i]+"'", SampleExamDetail.class); //采样结果表
			map.put("exam_sample", exam_sample);
			
			List<ExamResultChargingItem> exam_sample_eci = this.jqm.getList("select er.* from sample_exam_detail s,examResult_chargingItem er where s.sample_barcode = er.bar_code and er.result_type = 'sample' and s.exam_num = '"+exam_num[i]+"'", ExamResultChargingItem.class);//采样结果表-收费项目关系表
			map.put("exam_sample_eci", exam_sample_eci);
			
			List<PacsSummary> pacs_summary = this.jqm.getList("select * from pacs_summary p where p.examinfo_num = '"+exam_num[i]+"'", PacsSummary.class); //影像申请主表
			map.put("pacs_summary", pacs_summary);
			
			List<Pacsdetail> pacs_detail = this.jqm.getList("select d.* from pacs_summary p,pacs_detail d where p.pacs_req_code = d.pacs_req_code and p.examinfo_num = '"+exam_num[i]+"'", Pacsdetail.class); //影像申请明细表
			map.put("pacs_detail", pacs_detail);
			
			List<ViewExamDetail> exam_view = this.jqm.getList("select * from view_exam_detail v where v.exam_num = '"+exam_num[i]+"'", ViewExamDetail.class);//影像科室结果信息
			map.put("exam_view", exam_view);
			
			List<ViewExamItem> exam_view_item = this.jqm.getList("select i.* from view_exam_detail v,view_exam_item i where i.view_exam_detail_id = v.id and v.exam_num = '"+exam_num[i]+"'", ViewExamItem.class);//影像科室检查项目结果信息
			map.put("exam_view_item", exam_view_item);
			
			List<ViewExamImage> exam_view_image = this.jqm.getList("select * from view_exam_image i where i.exam_num = '"+exam_num[i]+"'", ViewExamImage.class);;//影像科室图片信息
			map.put("exam_view_image", exam_view_image);
			
			List<ExamdepResult> exam_dep = this.jqm.getList("select * from exam_dep_result e where e.exam_num = '"+exam_num[i]+"'", ExamdepResult.class); //普通科室检查结果
			map.put("exam_dep", exam_dep);
			
			List<CommonExamDetail> exam_common = this.jqm.getList("select * from common_exam_detail c where c.exam_num = '"+exam_num[i]+"'", CommonExamDetail.class); //普通科室明细表
			map.put("exam_common", exam_common);
			
			List<ExamResultDetail> exam_result = this.jqm.getList("select * from exam_result_detail e where e.exam_num = '"+exam_num[i]+"'", ExamResultDetail.class);//检验科检查结果信息
			map.put("exam_result", exam_result);
			
			List<ExamSummary> exam_summary = this.jqm.getList("select * from exam_summary e where e.exam_num = '"+exam_num[i]+"'", ExamSummary.class);//总检主表信息
			map.put("exam_summary", exam_summary);	
			
			List<ExaminfoDisease> exam_disease = this.jqm.getList("select * from examinfo_disease e where e.exam_num = '"+exam_num[i]+"'", ExaminfoDisease.class);//总检疾病信息
			map.put("exam_disease", exam_disease);
			
			List<Critical> examcritical = this.jqm.getList("select * from exam_Critical_detail e where e.exam_num = '"+exam_num[i]+"'", Critical.class); //危机值表信息
			map.put("examcritical", examcritical);
			
			List<DataMigrationExam> dataMigrationExam = this.jqm.getList("select d.arch_num_bar as arch_num,d.arch_num as arch_num_bar,d.exam_num_bar as exam_num,d.exam_num as exam_num_bar from data_migration_exam d where d.exam_num = '"+exam_num[i]+"'", DataMigrationExam.class);
			map.put("dataMigrationExam", dataMigrationExam);
			
			list.add(map);
		}
		return list;
	}
	
	public List<Map<String,Object>> getNeedDataMigrationByCon(Connection connection) throws ServiceException{
		
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		List<ExamInfo> examList = ResultSetMapper.mapRersultSetToObject(connection,"select * from exam_info ", ExamInfo.class);//体检信息
		
		for (int i = 0; i < examList.size(); i++) {
			Map<String,Object> map = new HashMap<String,Object>();
			
			List<CustomerInfo> customer = ResultSetMapper.mapRersultSetToObject(connection,"select c.* from customer_info c,exam_info e where e.customer_id = c.id and e.exam_num = '"+examList.get(i).getExam_num()+"'", CustomerInfo.class);//档案信息
			map.put("customer", customer);
			
			List<ExamInfo> examinfo = new ArrayList<ExamInfo>();
			examinfo.add(examList.get(i));
			map.put("examinfo", examinfo);
			
			List<ExaminfoBatchDTO> examinfobatch = ResultSetMapper.mapRersultSetToObject(connection,"select * from examinfo_batch e where e.exam_num = '"+examList.get(i).getExam_num()+"'", ExaminfoBatchDTO.class);//
			map.put("examinfobatch", examinfobatch);
			
			List<ExaminfoChargingItem> examitem = ResultSetMapper.mapRersultSetToObject(connection,"select * from examinfo_charging_item ec where ec.exam_num = '"+examList.get(i).getExam_num()+"'", ExaminfoChargingItem.class); //体检项目信息
			map.put("examitem",examitem);
			
			List<SampleExamDetail> exam_sample = ResultSetMapper.mapRersultSetToObject(connection,"select * from sample_exam_detail s where s.exam_num = '"+examList.get(i).getExam_num()+"'", SampleExamDetail.class); //采样结果表
			map.put("exam_sample", exam_sample);
			
			List<ExamResultChargingItem> exam_sample_eci = ResultSetMapper.mapRersultSetToObject(connection,"select er.* from sample_exam_detail s,examResult_chargingItem er where s.sample_barcode = er.bar_code and er.result_type = 'sample' and s.exam_num = '"+examList.get(i).getExam_num()+"'", ExamResultChargingItem.class);//采样结果表-收费项目关系表
			map.put("exam_sample_eci", exam_sample_eci);
			
			List<PacsSummary> pacs_summary = ResultSetMapper.mapRersultSetToObject(connection,"select * from pacs_summary p where p.examinfo_num = '"+examList.get(i).getExam_num()+"'", PacsSummary.class); //影像申请主表
			map.put("pacs_summary", pacs_summary);
			
			List<Pacsdetail> pacs_detail = ResultSetMapper.mapRersultSetToObject(connection,"select d.* from pacs_summary p,pacs_detail d where p.pacs_req_code = d.pacs_req_code and p.examinfo_num = '"+examList.get(i).getExam_num()+"'", Pacsdetail.class); //影像申请明细表
			map.put("pacs_detail", pacs_detail);
			
			List<ViewExamDetail> exam_view = ResultSetMapper.mapRersultSetToObject(connection,"select * from view_exam_detail v where v.exam_num = '"+examList.get(i).getExam_num()+"'", ViewExamDetail.class);//影像科室结果信息
			map.put("exam_view", exam_view);
			
			List<ViewExamItem> exam_view_item = ResultSetMapper.mapRersultSetToObject(connection,"select i.* from view_exam_detail v,view_exam_item i where i.view_exam_detail_id = v.id and v.exam_num = '"+examList.get(i).getExam_num()+"'", ViewExamItem.class);//影像科室检查项目结果信息
			map.put("exam_view_item", exam_view_item);
			
			List<ViewExamImage> exam_view_image = ResultSetMapper.mapRersultSetToObject(connection,"select * from view_exam_image i where i.exam_num = '"+examList.get(i).getExam_num()+"'", ViewExamImage.class);;//影像科室图片信息
			map.put("exam_view_image", exam_view_image);
			
			List<ExamdepResult> exam_dep = ResultSetMapper.mapRersultSetToObject(connection,"select * from exam_dep_result e where e.exam_num = '"+examList.get(i).getExam_num()+"'", ExamdepResult.class); //普通科室检查结果
			map.put("exam_dep", exam_dep);
			
			List<CommonExamDetail> exam_common = ResultSetMapper.mapRersultSetToObject(connection,"select * from common_exam_detail c where c.exam_num = '"+examList.get(i).getExam_num()+"'", CommonExamDetail.class); //普通科室明细表
			map.put("exam_common", exam_common);
			
			List<ExamResultDetail> exam_result = ResultSetMapper.mapRersultSetToObject(connection,"select * from exam_result_detail e where e.exam_num = '"+examList.get(i).getExam_num()+"'", ExamResultDetail.class);//检验科检查结果信息
			map.put("exam_result", exam_result);
			
			List<ExamSummary> exam_summary = ResultSetMapper.mapRersultSetToObject(connection,"select * from exam_summary e where e.exam_num = '"+examList.get(i).getExam_num()+"'", ExamSummary.class);//总检主表信息
			map.put("exam_summary", exam_summary);	
			
			List<ExaminfoDisease> exam_disease = ResultSetMapper.mapRersultSetToObject(connection,"select * from examinfo_disease e where e.exam_num = '"+examList.get(i).getExam_num()+"'", ExaminfoDisease.class);//总检疾病信息
			map.put("exam_disease", exam_disease);
			
			List<Critical> examcritical = ResultSetMapper.mapRersultSetToObject(connection,"select * from exam_Critical_detail e where e.exam_num = '"+examList.get(i).getExam_num()+"'", Critical.class); //危机值表信息
			map.put("examcritical", examcritical);
			
			List<DataMigrationExam> dataMigrationExam = ResultSetMapper.mapRersultSetToObject(connection,"select d.arch_num_bar as arch_num,d.arch_num as arch_num_bar,d.exam_num_bar as exam_num,d.exam_num as exam_num_bar from data_migration_exam d where d.exam_num = '"+examList.get(i).getExam_num()+"'", DataMigrationExam.class);
			map.put("dataMigrationExam", dataMigrationExam);
			
			List<ViewExamImageBase64> imagebase64 = ResultSetMapper.mapRersultSetToObject(connection,"select * from view_exam_image_base64 where exam_num = '"+examList.get(i).getExam_num()+"'", ViewExamImageBase64.class);
			map.put("imagebase64", imagebase64);
			
			list.add(map);
		}
		return list;
	}

	@Override
	public boolean saveDataMigration(Map<String, Object> map, UserDTO user,Connection connection,DataBaseConfigDTO dto) throws ServiceException {
		boolean is_id_insert = false;
		try {
			if(connection == null){
				connection = this.jqm.getConnection();
			}
			
			connection.setAutoCommit(false);
			List<CustomerInfo> customerlist = (List<CustomerInfo>) map.get("customer");
			CustomerInfo customer = customerlist.get(0);
			List<ExamInfo> examinfolist = (List<ExamInfo>) map.get("examinfo");
			ExamInfo examinfo = examinfolist.get(0);
			
			List<DataMigrationExam> dataMigrationExam = (List<DataMigrationExam>) map.get("dataMigrationExam");
			List<DataMigrationExam> oldDataMigrationExam = ResultSetMapper.mapRersultSetToObject(connection, "select * from data_migration_exam d where d.exam_num_bar = '"+examinfo.getExam_num()+"'", DataMigrationExam.class);
			if(dataMigrationExam.size() <= 0){
				dataMigrationExam.addAll(oldDataMigrationExam);
			}
			if(dataMigrationExam.size() > 0){//已导，修改处理
				String arch_num_bar = customer.getArch_num();
				String exam_num_bar = examinfo.getExam_num();
				//处理修改档案信息
				List<CustomerInfo> oldcustomer = ResultSetMapper.mapRersultSetToObject(connection, "select * from customer_info c where c.arch_num = '"+dataMigrationExam.get(0).getArch_num()+"'", CustomerInfo.class);
				if(oldcustomer.size() > 0){
					customer.setId(oldcustomer.get(0).getId());
					customer.setArch_num(oldcustomer.get(0).getArch_num());
					connection.createStatement().executeUpdate(ProduceSql.getUpdateSql("customer_info", customer));
				}
				
				if(examinfo.getExam_type().equals("T")){
					List<DataMigrationCompany> datamigration = ResultSetMapper.mapRersultSetToObject(connection, "select * from data_migration_company d where d.batch_id_car = "+ examinfo.getBatch_id(), DataMigrationCompany.class);
					for (int i = 0; i < datamigration.size(); i++) {
						if(examinfo.getGroup_id() == datamigration.get(i).getGroup_id_car()){
							examinfo.setGroup_id(datamigration.get(i).getGroup_id());
							examinfo.setBatch_id(datamigration.get(i).getBatch_id());
							examinfo.setCompany_id(datamigration.get(i).getCom_id());
						}
					}
				}
				//处理修改体检信息
				List<ExamInfo> oldexaminfo = ResultSetMapper.mapRersultSetToObject(connection, "select * from exam_info e where e.exam_num = '"+dataMigrationExam.get(0).getExam_num()+"'", ExamInfo.class);
				if(oldexaminfo.size() > 0){
					examinfo.setId(oldexaminfo.get(0).getId());
					examinfo.setCustomer_id(customer.getId());
					examinfo.setExam_num(oldexaminfo.get(0).getExam_num());
					
					connection.createStatement().executeUpdate(ProduceSql.getUpdateSql("exam_info", examinfo));
				}
				//处理体检信息收费项目表
				List<ExaminfoChargingItem> examitem = (List<ExaminfoChargingItem>) map.get("examitem");
				for (int i = 0; i < examitem.size(); i++) {
					List<ExaminfoChargingItem> oldexamitem = ResultSetMapper.mapRersultSetToObject(connection, "select * from examinfo_charging_item eci where eci.exam_num = '"+examinfo.getExam_num()+"' and eci.charging_item_code = '"+examitem.get(i).getCharging_item_code()+"'", ExaminfoChargingItem.class);
					if(oldexamitem.size() > 0){
						if(!examitem.get(i).getExam_status().equals("Y") && oldexamitem.get(0).getExam_status().equals("Y")){
							//体检车未检，院内已检，不做处理操作，已院内为准
						}else{
							examitem.get(i).setId(oldexamitem.get(0).getId());
							examitem.get(i).setExaminfo_id(examinfo.getId());
							examitem.get(i).setExam_num(examinfo.getExam_num());
							connection.createStatement().executeUpdate(ProduceSql.getUpdateSql("examinfo_charging_item", examitem.get(i)));
						}
					}else{
						examitem.get(i).setExaminfo_id(examinfo.getId());
						examitem.get(i).setExam_num(examinfo.getExam_num());
						connection.createStatement().executeUpdate(ProduceSql.getInsertSql("examinfo_charging_item", examitem.get(i),is_id_insert));
					}
				}
				//处理采样结果表
				List<SampleExamDetail> exam_sample = (List<SampleExamDetail>) map.get("exam_sample");
				List<ExamResultChargingItem> exam_sample_eci = (List<ExamResultChargingItem>) map.get("exam_sample_eci");
				List<ExamResultChargingItem> old_exam_sample_eci = ResultSetMapper.mapRersultSetToObject(connection,"select er.* from sample_exam_detail s,examResult_chargingItem er where s.sample_barcode = er.bar_code and er.result_type = 'sample' and s.exam_num = '"+examinfo.getExam_num()+"'", ExamResultChargingItem.class);
				for (int i = 0; i < exam_sample.size(); i++) {
					String old_bar_code = exam_sample.get(i).getSample_barcode();
					List<SampleExamDetail> oldsample = ResultSetMapper.mapRersultSetToObject(connection, "select * from sample_exam_detail s where s.sample_id = '"+exam_sample.get(i).getSample_id()+"' and s.exam_num = '"+exam_sample.get(i).getExam_num()+"'", SampleExamDetail.class);
					boolean falg = true;
					for (int j = 0; j < oldsample.size(); j++) {
						if(oldsample.get(j).getSample_barcode().equals(exam_sample.get(i).getSample_barcode())){
							exam_sample.get(i).setId(oldsample.get(j).getId());
							exam_sample.get(i).setExam_info_id(examinfo.getId());
							exam_sample.get(i).setExam_num(examinfo.getExam_num());
							
							connection.createStatement().executeUpdate(ProduceSql.getUpdateSql("sample_exam_detail", exam_sample.get(i)));
							falg = false;
							break;
						}
					}
					
					if (falg) {
						List<SampleExamDetail> oldsample1 = ResultSetMapper.mapRersultSetToObject(connection, "select * from sample_exam_detail s where s.sample_barcode = '"+exam_sample.get(i).getSample_barcode()+"'", SampleExamDetail.class);
						if(oldsample1.size() > 0){
							if(dto!= null){
								exam_sample.get(i).setSample_barcode(GetOtherNumContral.getInstance().getParamNum("barcode", user.getCenter_num(), dto));
							}else if(dto == null){// 
								exam_sample.get(i).setSample_barcode(GetNumContral.getInstance().getParamNum("barcode", user.getCenter_num()));
							}
						}
						exam_sample.get(i).setExam_info_id(examinfo.getId());
						exam_sample.get(i).setExam_num(examinfo.getExam_num());

						connection.createStatement().executeUpdate(ProduceSql.getInsertSql("sample_exam_detail", exam_sample.get(i),is_id_insert));
						
						ResultSet executesample = connection.createStatement().executeQuery("select SCOPE_IDENTITY() as id ");// 主键id
						if (executesample.next()) {
							exam_sample.get(i).setId(executesample.getInt("id"));
						}
					}
					List<ExamResultChargingItem> removesampleitem = new ArrayList<ExamResultChargingItem>();
					for (int j = 0; j < exam_sample_eci.size(); j++) {
						if (old_bar_code.equals(exam_sample_eci.get(j).getBar_code())) {
							boolean r_flag = true;
							for (int j2 = 0; j2 < old_exam_sample_eci.size(); j2++) {
								if(exam_sample_eci.get(j).getCharging_item_code().equals(old_exam_sample_eci.get(j2).getCharging_item_code())){
									exam_sample_eci.get(j).setId(old_exam_sample_eci.get(j2).getId());
									exam_sample_eci.get(j).setBar_code(exam_sample.get(i).getSample_barcode());
									exam_sample_eci.get(j).setExam_id(exam_sample.get(i).getId());
									
									connection.createStatement().executeUpdate(ProduceSql.getUpdateSql("examResult_chargingItem", exam_sample_eci.get(j)));
									
									r_flag = false;
									removesampleitem.add(exam_sample_eci.get(j));
									old_exam_sample_eci.remove(j2);
									break;
								}
							}
							if(r_flag){
								exam_sample_eci.get(j).setBar_code(exam_sample.get(i).getSample_barcode());
								exam_sample_eci.get(j).setExam_id(exam_sample.get(i).getId());

								connection.createStatement().executeUpdate(ProduceSql.getInsertSql("examResult_chargingItem", exam_sample_eci.get(j),is_id_insert));
								removesampleitem.add(exam_sample_eci.get(j));
							}
						}
					}
					exam_sample_eci.removeAll(removesampleitem);
				}
				//删除体检车已减项的记录
				for (int i = 0; i < old_exam_sample_eci.size(); i++) {
					List<ExaminfoChargingItem> oldexamitem = ResultSetMapper.mapRersultSetToObject(connection, "select * from examinfo_charging_item eci where eci.exam_num = '"+examinfo.getExam_num()+"' and eci.charging_item_code = '"+examitem.get(i).getCharging_item_code()+"' and eci.isActive = 'Y' and eci.pay_status <> 'M'", ExaminfoChargingItem.class);
					if(oldexamitem.size() <= 0){
						connection.createStatement().executeUpdate("delete from examResult_chargingItem where id = "+old_exam_sample_eci.get(i).getId());
					}
				}
				connection.createStatement().executeUpdate("delete from sample_exam_detail where exam_num = '"+examinfo.getExam_num()+"' and (select COUNT(*) from examResult_chargingItem er where er.bar_code = sample_exam_detail.sample_barcode and er.result_type = 'sample') = 0");
				
				//处理影像结果表
				List<PacsSummary> pacs_summary = (List<PacsSummary>) map.get("pacs_summary");
				List<Pacsdetail> pacs_detail = (List<Pacsdetail>) map.get("pacs_detail");
				List<Pacsdetail> old_pacs_detail = ResultSetMapper.mapRersultSetToObject(connection, "select d.* from pacs_summary p,pacs_detail d where p.pacs_req_code = d.pacs_req_code and p.examinfo_num = '"+examinfo.getExam_num()+"'", Pacsdetail.class);
				List<ViewExamDetail> exam_view = (List<ViewExamDetail>) map.get("exam_view");
				List<ViewExamItem> exam_view_item = (List<ViewExamItem>) map.get("exam_view_item");
				List<ViewExamImage> exam_view_image = (List<ViewExamImage>) map.get("exam_view_image");
				for (int i = 0; i < pacs_summary.size(); i++) {
					String old_req_num = pacs_summary.get(i).getPacs_req_code();
					List<PacsSummary> oldpacs = ResultSetMapper.mapRersultSetToObject(connection, "select * from pacs_summary p where p.examinfo_num = '"+examinfo.getExam_num()+"' and p.examinfo_sampleId = "+pacs_summary.get(i).getExaminfo_sampleId(), PacsSummary.class);
					boolean flag = true;
					for (int j = 0; j < oldpacs.size(); j++) {
						if(oldpacs.get(j).getPacs_req_code().equals(pacs_summary.get(i).getPacs_req_code())){
							pacs_summary.get(i).setId(oldpacs.get(j).getId());
							pacs_summary.get(i).setExaminfo_num(examinfo.getExam_num());
							connection.createStatement().executeUpdate(ProduceSql.getUpdateSql("pacs_summary", pacs_summary.get(i)));
							flag = false;
							break;
						}
					}
					if(flag){
						List<PacsSummary> oldpacs1 = ResultSetMapper.mapRersultSetToObject(connection, "select * from pacs_summary p where p.pacs_req_code = '"+old_req_num+"'", PacsSummary.class);
						if(oldpacs1.size() > 0){
							if(dto != null){
								pacs_summary.get(i).setPacs_req_code(GetOtherNumContral.getInstance().getParamNum("pacs_req_num", user.getCenter_num(), dto));
							}else if(dto == null){// 
								pacs_summary.get(i).setPacs_req_code(GetNumContral.getInstance().getParamNum("pacs_req_num", user.getCenter_num()));
							}
						}
						pacs_summary.get(i).setExaminfo_num(examinfo.getExam_num());
						connection.createStatement().executeUpdate(ProduceSql.getInsertSql("pacs_summary", pacs_summary.get(i),is_id_insert));
						ResultSet executepacs = connection.createStatement().executeQuery("select SCOPE_IDENTITY() as id ");//主键id
						if (executepacs.next()) {
							pacs_summary.get(i).setId(executepacs.getInt("id"));
						}
					}
					List<Pacsdetail> removeDetail = new ArrayList<Pacsdetail>();
					for (int j = 0; j < pacs_detail.size(); j++) {
						if(old_req_num.equals(pacs_detail.get(j).getPacs_req_code())){
							boolean r_flag = true;
							for (int j2 = 0; j2 < old_pacs_detail.size(); j2++) {
								if(pacs_detail.get(j).getChargingItem_num().equals(old_pacs_detail.get(j2).getChargingItem_num())){
									pacs_detail.get(j).setId(old_pacs_detail.get(j2).getId());
									pacs_detail.get(j).setExaminfo_num(examinfo.getExam_num());
									pacs_detail.get(j).setPacs_req_code(pacs_summary.get(i).getPacs_req_code());
									pacs_detail.get(j).setSummary_id(pacs_summary.get(i).getId());
									connection.createStatement().executeUpdate(ProduceSql.getUpdateSql("pacs_detail", pacs_detail.get(j)));
									
									removeDetail.add(pacs_detail.get(j));
									old_pacs_detail.remove(j2);
									r_flag = false;
									break;
								}
							}
							if(r_flag){
								pacs_detail.get(j).setExaminfo_num(examinfo.getExam_num());
								pacs_detail.get(j).setPacs_req_code(pacs_summary.get(i).getPacs_req_code());
								pacs_detail.get(j).setSummary_id(pacs_summary.get(i).getId());
								connection.createStatement().executeUpdate(ProduceSql.getInsertSql("pacs_detail", pacs_detail.get(j),is_id_insert));
								removeDetail.add(pacs_detail.get(j));
							}
						}
					}
					pacs_detail.removeAll(removeDetail);
					
					
					//处理影像结果表
					List<ViewExamDetail> removeview = new ArrayList<ViewExamDetail>();
					for (int j = 0; j < exam_view.size(); j++) {
						if(old_req_num.equals(exam_view.get(j).getPacs_req_code())){
							long old_view_exam_id = exam_view.get(j).getId();
							exam_view.get(j).setExam_info_id(examinfo.getId());
							exam_view.get(j).setExam_num(examinfo.getExam_num());
							exam_view.get(j).setPacs_id(pacs_summary.get(i).getId());
							exam_view.get(j).setPacs_req_code(pacs_summary.get(i).getPacs_req_code());
							
							List<ViewExamDetail> old_exam_view = ResultSetMapper.mapRersultSetToObject(connection, "select * from view_exam_detail v where v.pacs_req_code = '"+pacs_summary.get(i).getPacs_req_code()+"'", ViewExamDetail.class);
							if(old_exam_view.size() > 0){
								exam_view.get(j).setId(old_exam_view.get(0).getId());
								connection.createStatement().executeUpdate(ProduceSql.getUpdateSql("view_exam_detail", exam_view.get(j)));
							}else{
								connection.createStatement().executeUpdate(ProduceSql.getInsertSql("view_exam_detail", exam_view.get(j),is_id_insert));
								
								ResultSet executeview = connection.createStatement().executeQuery("select SCOPE_IDENTITY() as id ");//主键id
								if (executeview.next()) {
									exam_view.get(j).setId(executeview.getInt("id"));
								}
								removeview.add(exam_view.get(j));
							}
							
							connection.createStatement().executeUpdate("delete from view_exam_item where view_exam_detail_id = "+exam_view.get(j).getId());
							List<ViewExamItem> removeviewitem = new ArrayList<ViewExamItem>();
							for (int k = 0; k < exam_view_item.size(); k++) {
								if(old_view_exam_id == exam_view_item.get(k).getView_exam_detail_id()){
									exam_view_item.get(k).setView_exam_detail_id(exam_view.get(j).getId());
									connection.createStatement().executeUpdate(ProduceSql.getInsertSql("view_exam_item", exam_view_item.get(k),is_id_insert));
									removeviewitem.add(exam_view_item.get(k));
								}
							}
							exam_view_item.removeAll(removeviewitem);
							
							connection.createStatement().executeUpdate("delete from view_exam_image where pacs_req_code = '"+exam_view.get(j).getPacs_req_code()+"'");
							List<ViewExamImage> removeviewimage = new ArrayList<ViewExamImage>();
							for (int k = 0; k < exam_view_image.size(); k++) {
								if(old_req_num.equals(exam_view_image.get(k).getPacs_req_code())){
									exam_view_image.get(k).setExam_num(examinfo.getExam_num());
									exam_view_image.get(k).setView_exam_id(exam_view.get(j).getId());
									exam_view_image.get(k).setPacs_req_code(pacs_summary.get(i).getPacs_req_code());
									
									connection.createStatement().executeUpdate(ProduceSql.getInsertSql("view_exam_image", exam_view_image.get(k),is_id_insert));
									removeviewimage.add(exam_view_image.get(k));
								}
							}
							exam_view_image.removeAll(removeviewimage);
						}
					}
					exam_view.removeAll(removeview);
				}
				//删除体检车减项的影像申请单
				for (int i = 0; i < old_pacs_detail.size(); i++) {
					List<ExaminfoChargingItem> oldexamitem = ResultSetMapper.mapRersultSetToObject(connection, "select * from examinfo_charging_item eci where eci.exam_num = '"+examinfo.getExam_num()+"' and eci.charging_item_code = '"+old_pacs_detail.get(i).getChargingItem_num()+"' and eci.isActive = 'Y' and eci.pay_status <> 'M'", ExaminfoChargingItem.class);
					if(oldexamitem.size() <= 0){
						connection.createStatement().executeUpdate("delete from pacs_detail where id = "+old_pacs_detail.get(i).getId());
					}
				}
				connection.createStatement().executeUpdate("delete from pacs_summary where examinfo_num = '"+examinfo.getExam_num()+"' and (select COUNT(*) from pacs_detail er where er.pacs_req_code = pacs_summary.pacs_req_code) = 0");
				
				//处理普通科室结论表
				List<ExamdepResult> exam_dep = (List<ExamdepResult>) map.get("exam_dep");
				for (int i = 0; i < exam_dep.size(); i++) {
					exam_dep.get(i).setExam_info_id(examinfo.getId());
					exam_dep.get(i).setExam_num(examinfo.getExam_num());
					List<ExamdepResult> old_exam_dep = ResultSetMapper.mapRersultSetToObject(connection, "select * from exam_dep_result e where e.exam_num = '"+examinfo.getExam_num()+"' and e.dep_id = '"+exam_dep.get(i).getDep_id()+"'", ExamdepResult.class);
					if(old_exam_dep.size() > 0){
						exam_dep.get(i).setId(old_exam_dep.get(0).getId());
						connection.createStatement().executeUpdate(ProduceSql.getUpdateSql("⁯exam_dep_result", exam_dep.get(i)));
					}else{
						connection.createStatement().executeUpdate(ProduceSql.getInsertSql("⁯exam_dep_result", exam_dep.get(i),is_id_insert));
					}
				}
				//处理普通科室检查明细表
				List<CommonExamDetail> exam_common = (List<CommonExamDetail>) map.get("exam_common");
				for (int i = 0; i < exam_common.size(); i++) {
					exam_common.get(i).setExam_info_id(examinfo.getId());
					exam_common.get(i).setExam_num(examinfo.getExam_num());
					List<CommonExamDetail> old_exam_common = ResultSetMapper.mapRersultSetToObject(connection, "select * from common_exam_detail c where c.exam_num = '"+examinfo.getExam_num()+"' and c.charging_item_code = '"+exam_common.get(i).getCharging_item_code()+"' and item_code = '"+exam_common.get(i).getItem_code()+"'", CommonExamDetail.class);
					if(old_exam_common.size() > 0){
						exam_common.get(i).setId(old_exam_common.get(0).getId());
						connection.createStatement().executeUpdate(ProduceSql.getUpdateSql("⁯common_exam_detail", exam_common.get(i)));
					}else{
						connection.createStatement().executeUpdate(ProduceSql.getInsertSql("⁯common_exam_detail", exam_common.get(i),is_id_insert));
					}
				}
				//处理检验科检查结果表
				List<ExamResultDetail> exam_result = (List<ExamResultDetail>) map.get("exam_result");
				for (int i = 0; i < exam_result.size(); i++) {
					exam_result.get(i).setExam_info_id(examinfo.getId());
					exam_result.get(i).setExam_num(examinfo.getExam_num());
					List<ExamResultDetail> old_exam_result = ResultSetMapper.mapRersultSetToObject(connection, "select * from exam_result_detail e where e.exam_num = '"+examinfo.getExam_num()+"' and e.charging_item_code = '"+exam_result.get(i).getCharging_item_code()+"' and e.item_code = '"+exam_result.get(i).getItem_code()+"'", ExamResultDetail.class);
					if(old_exam_result.size() > 0){
						exam_result.get(i).setId(old_exam_result.get(0).getId());
						connection.createStatement().executeUpdate(ProduceSql.getUpdateSql("⁯exam_result_detail", exam_result.get(i)));
					}else{
						connection.createStatement().executeUpdate(ProduceSql.getInsertSql("⁯exam_result_detail", exam_result.get(i),is_id_insert));
					}
				}
				//体检车总检日期大于等于体检系统总检日期，以体检车主检为准
				if(oldexaminfo.get(0).getFinal_date() != null && oldexaminfo.get(0).getFinal_date().compareTo(examinfo.getFinal_date()) >= 0){
					//处理总检结论表
					List<ExamSummary> exam_summary = (List<ExamSummary>) map.get("exam_summary");
					for (int i = 0; i < exam_summary.size(); i++) {
						exam_summary.get(i).setExam_info_id(examinfo.getId());
						exam_summary.get(i).setExam_num(examinfo.getExam_num());
						List<ExamResultDetail> old_exam_summary = ResultSetMapper.mapRersultSetToObject(connection, "select * from exam_summary e where e.exam_num = '"+examinfo.getExam_num()+"' and e.app_type = '"+exam_summary.get(i).getApp_type()+"'", ExamResultDetail.class);
						if(old_exam_summary.size() > 0){
							exam_summary.get(i).setId(old_exam_summary.get(0).getId());
							connection.createStatement().executeUpdate(ProduceSql.getUpdateSql("exam_summary", exam_summary.get(i)));
						}else{
							connection.createStatement().executeUpdate(ProduceSql.getInsertSql("exam_summary", exam_summary.get(i),is_id_insert));
						}
					}
					
					connection.createStatement().executeUpdate("delete from examinfo_disease where exam_num = '"+examinfo.getExam_num()+"'");
					//处理总检阳性发现表
					List<ExaminfoDisease> exam_disease = (List<ExaminfoDisease>) map.get("exam_disease");
					for (int i = 0; i < exam_disease.size(); i++) {
						exam_disease.get(i).setExam_info_id(examinfo.getId());
						exam_disease.get(i).setExam_num(examinfo.getExam_num());
						connection.createStatement().executeUpdate(ProduceSql.getInsertSql("examinfo_disease", exam_disease.get(i),is_id_insert));
					}
				}
				
				//处理危机值表
				List<Critical> examcritical = (List<Critical>) map.get("examcritical");
				for (int i = 0; i < examcritical.size(); i++) {
					List<Critical> oldexamcri = ResultSetMapper.mapRersultSetToObject(connection, "select * from exam_Critical_detail c where c.exam_num = '"+examinfo.getExam_num()+"' and c.critical_class_parent_id = '"+examcritical.get(i).getCritical_class_parent_id()+"' and c.critical_class_id = '"+examcritical.get(i).getCritical_class_id()+"'", Critical.class);
					examcritical.get(i).setExam_info_id(examinfo.getId());
					examcritical.get(i).setExam_num(examinfo.getExam_num());
					if(oldexamcri.size() > 0){
						examcritical.get(i).setId(oldexamcri.get(0).getId());
						connection.createStatement().executeUpdate(ProduceSql.getUpdateSql("⁯exam_Critical_detail", examcritical.get(i)));
					}else{
						connection.createStatement().executeUpdate(ProduceSql.getInsertSql("⁯exam_Critical_detail", examcritical.get(i),is_id_insert));
					}
				}
				if(oldDataMigrationExam.size() <= 0){
					DataMigrationExam dataexam = new DataMigrationExam();
					dataexam.setArch_num(customer.getArch_num());
					dataexam.setArch_num_bar(arch_num_bar);
					dataexam.setExam_num(examinfo.getExam_num());
					dataexam.setExam_num_bar(exam_num_bar);
					dataexam.setCreater(user.getUserid());
					dataexam.setCreate_time(DateTimeUtil.setTimestamp());
					connection.createStatement().executeUpdate(ProduceSql.getInsertSql("⁯data_migration_exam", dataexam,is_id_insert));
				}
			}else{ //未导，新增处理
				
				//处理档案信息
				String arch_num = "";
				String arch_num_bar = customer.getArch_num();
				long customer_id = 0;
				if(customer.getId_num() != null && !"".equals(customer.getId_num())){
					List<CustomerInfo> oldcustomer = ResultSetMapper.mapRersultSetToObject(connection, "select * from customer_info c where c.id_num = '"+customer.getId_num()+"'", CustomerInfo.class);
					if(oldcustomer.size() > 0){
						customer_id = oldcustomer.get(0).getId();
						arch_num = oldcustomer.get(0).getArch_num();
					}
				}
				if(!"".equals(arch_num)){
					customer.setId(customer_id);
					customer.setArch_num(arch_num);
					
					connection.createStatement().executeUpdate(ProduceSql.getUpdateSql("customer_info", customer));
				}else{
					if(dto != null){
						arch_num = GetOtherNumContral.getInstance().getParamNum("vipno", user.getCenter_num(), dto);
						customer.setArch_num(arch_num);
					}else if(dto == null){// 
						arch_num =  GetNumContral.getInstance().getParamNum("vipno", user.getCenter_num());
						customer.setArch_num(arch_num);
					}
					connection.createStatement().executeUpdate(ProduceSql.getInsertSql("customer_info", customer,is_id_insert));
					
					ResultSet executeQuery = connection.createStatement().executeQuery("select SCOPE_IDENTITY() as id ");//主键id
					if (executeQuery.next()) {
						customer_id = executeQuery.getInt("id");
					}
					
				}
				examinfo.setCustomer_id(customer_id);
				
				// 处理保存体检信息
				String exam_num = "";
				String exam_num_bar = examinfo.getExam_num();
				long exam_info_id = 0;
				if(examinfo.getExam_type().equals("T")){
					List<DataMigrationCompany> datamigration = ResultSetMapper.mapRersultSetToObject(connection, "select * from data_migration_company d where d.batch_id_car = "+ examinfo.getBatch_id(), DataMigrationCompany.class);
					for (int i = 0; i < datamigration.size(); i++) {
						if(examinfo.getGroup_id() == datamigration.get(i).getGroup_id_car()){
							examinfo.setGroup_id(datamigration.get(i).getGroup_id());
							examinfo.setBatch_id(datamigration.get(i).getBatch_id());
							examinfo.setCompany_id(datamigration.get(i).getCom_id());
						}
					}
				}
				List<ExamInfo> oldexaminfo = ResultSetMapper.mapRersultSetToObject(connection, "select * from exam_info e where e.exam_num = '"+exam_num_bar+"'", ExamInfo.class);
				if(oldexaminfo.size() > 0){
					if(dto != null){
						exam_num = GetOtherNumContral.getInstance().getParamNum("exam_no", user.getCenter_num(), dto);
					}else if(dto == null){// 
						exam_num = GetNumContral.getInstance().getParamNum("exam_no", user.getCenter_num());
					}
				}else{
					exam_num = examinfo.getExam_num();
				}
				examinfo.setExam_num(exam_num);
				connection.createStatement().executeUpdate(ProduceSql.getInsertSql("exam_info", examinfo,is_id_insert));
				
				ResultSet executeQuery = connection.createStatement().executeQuery("select SCOPE_IDENTITY() as id ");//主键id
				if (executeQuery.next()) {
					exam_info_id = executeQuery.getInt("id");
				}
				//处理保存体检信息批次关系表 团体体检
				if(examinfo.getExam_type().equals("T")){
					List<ExaminfoBatchDTO> examinfo_batch = (List<ExaminfoBatchDTO>) map.get("examinfobatch");
					if(examinfo_batch.size() > 0){
						examinfo_batch.get(0).setBatch_id(examinfo.getBatch_id());
						examinfo_batch.get(0).setExaminfo_id(exam_info_id);
						examinfo_batch.get(0).setExam_num(exam_num);
						connection.createStatement().executeUpdate(ProduceSql.getInsertSql("examinfo_batch", examinfo_batch.get(0),is_id_insert));
					}
				}
				//处理保存体检收费项目表
				List<ExaminfoChargingItem> examitem = (List<ExaminfoChargingItem>) map.get("examitem");
				for (int i = 0; i < examitem.size(); i++) {
					examitem.get(i).setExaminfo_id(exam_info_id);
					examitem.get(i).setExam_num(exam_num);
					connection.createStatement().executeUpdate(ProduceSql.getInsertSql("examinfo_charging_item", examitem.get(i),is_id_insert));
				}
				
				//处理采样结果表
				List<SampleExamDetail> exam_sample = (List<SampleExamDetail>) map.get("exam_sample");
				List<ExamResultChargingItem> exam_sample_eci = (List<ExamResultChargingItem>) map.get("exam_sample_eci");
				for (int i = 0; i < exam_sample.size(); i++) {
					String old_bar_code = exam_sample.get(i).getSample_barcode();
					List<SampleExamDetail> oldsample = ResultSetMapper.mapRersultSetToObject(connection, "select * from sample_exam_detail s where s.sample_barcode = '"+exam_sample.get(i).getSample_barcode()+"'", SampleExamDetail.class);
					if(oldsample.size() > 0){
						if(dto != null){
							exam_sample.get(i).setSample_barcode(GetOtherNumContral.getInstance().getParamNum("barcode", user.getCenter_num(), dto));
						}else if(dto == null){// 
							exam_sample.get(i).setSample_barcode(GetNumContral.getInstance().getParamNum("barcode", user.getCenter_num()));
						}
					}
					exam_sample.get(i).setExam_info_id(exam_info_id);
					exam_sample.get(i).setExam_num(exam_num);
					connection.createStatement().executeUpdate(ProduceSql.getInsertSql("sample_exam_detail", exam_sample.get(i),is_id_insert));
					long sample_id = 0;
					ResultSet executesample = connection.createStatement().executeQuery("select SCOPE_IDENTITY() as id ");//主键id
					if (executesample.next()) {
						sample_id = executesample.getInt("id");
					}
					List<ExamResultChargingItem> removesampleitem = new ArrayList<ExamResultChargingItem>();
					for (int j = 0; j < exam_sample_eci.size(); j++) {
						if(old_bar_code.equals(exam_sample_eci.get(j).getBar_code())){
							exam_sample_eci.get(j).setBar_code(exam_sample.get(i).getSample_barcode());
							exam_sample_eci.get(j).setExam_id(sample_id);
							
							connection.createStatement().executeUpdate(ProduceSql.getInsertSql("examResult_chargingItem", exam_sample_eci.get(j),is_id_insert));
							
							removesampleitem.add(exam_sample_eci.get(j));
						}
					}
					exam_sample_eci.removeAll(removesampleitem);
				}
				
				//处理影像结果表
				List<PacsSummary> pacs_summary = (List<PacsSummary>) map.get("pacs_summary");
				List<Pacsdetail> pacs_detail = (List<Pacsdetail>) map.get("pacs_detail");
				List<ViewExamDetail> exam_view = (List<ViewExamDetail>) map.get("exam_view");
				List<ViewExamItem> exam_view_item = (List<ViewExamItem>) map.get("exam_view_item");
				List<ViewExamImage> exam_view_image = (List<ViewExamImage>) map.get("exam_view_image");
				for (int i = 0; i < pacs_summary.size(); i++) {
					String old_req_num = pacs_summary.get(i).getPacs_req_code();
					List<PacsSummary> oldpacs = ResultSetMapper.mapRersultSetToObject(connection, "select * from pacs_summary p where p.pacs_req_code = '"+old_req_num+"'", PacsSummary.class);
					if(oldpacs.size() > 0){
						if(dto != null){
							pacs_summary.get(i).setPacs_req_code(GetOtherNumContral.getInstance().getParamNum("pacs_req_num", user.getCenter_num(), dto));
						}else if(dto == null){// 
							pacs_summary.get(i).setPacs_req_code(GetNumContral.getInstance().getParamNum("pacs_req_num", user.getCenter_num()));
						}
					}
					pacs_summary.get(i).setExaminfo_num(exam_num);
					connection.createStatement().executeUpdate(ProduceSql.getInsertSql("pacs_summary", pacs_summary.get(i),is_id_insert));
					long pacs_id = 0;
					
					ResultSet executepacs = connection.createStatement().executeQuery("select SCOPE_IDENTITY() as id ");//主键id
					if (executepacs.next()) {
						pacs_id = executepacs.getInt("id");
					}
					
					List<Pacsdetail> removeDetail = new ArrayList<Pacsdetail>();
					for (int j = 0; j < pacs_detail.size(); j++) {
						if(old_req_num.equals(pacs_detail.get(j).getPacs_req_code())){
							pacs_detail.get(j).setExaminfo_num(exam_num);
							pacs_detail.get(j).setPacs_req_code(pacs_summary.get(i).getPacs_req_code());
							pacs_detail.get(j).setSummary_id(pacs_id);
							
							connection.createStatement().executeUpdate(ProduceSql.getInsertSql("pacs_detail", pacs_detail.get(j),is_id_insert));
							removeDetail.add(pacs_detail.get(j));
						}
					}
					pacs_detail.removeAll(removeDetail);
					
					//处理影像结果表
					List<ViewExamDetail> removeview = new ArrayList<ViewExamDetail>();
					for (int j = 0; j < exam_view.size(); j++) {
						if(old_req_num.equals(exam_view.get(j).getPacs_req_code())){
							exam_view.get(j).setExam_info_id(exam_info_id);
							exam_view.get(j).setExam_num(exam_num);
							exam_view.get(j).setPacs_id(pacs_id);
							exam_view.get(j).setPacs_req_code(pacs_summary.get(i).getPacs_req_code());
							
							connection.createStatement().executeUpdate(ProduceSql.getInsertSql("view_exam_detail", exam_view.get(j),is_id_insert));
							long view_exam_id = 0;
							
							ResultSet executeview = connection.createStatement().executeQuery("select SCOPE_IDENTITY() as id ");//主键id
							if (executeview.next()) {
								view_exam_id = executeview.getInt("id");
							}
							
							removeview.add(exam_view.get(j));
							
							List<ViewExamItem> removeviewitem = new ArrayList<ViewExamItem>();
							for (int k = 0; k < exam_view_item.size(); k++) {
								if(exam_view.get(j).getId() == exam_view_item.get(k).getView_exam_detail_id()){
									exam_view_item.get(k).setView_exam_detail_id(view_exam_id);
									
									connection.createStatement().executeUpdate(ProduceSql.getInsertSql("view_exam_item", exam_view_item.get(k),is_id_insert));
									removeviewitem.add(exam_view_item.get(k));
								}
							}
							exam_view_item.removeAll(removeviewitem);
							
							List<ViewExamImage> removeviewimage = new ArrayList<ViewExamImage>();
							for (int k = 0; k < exam_view_image.size(); k++) {
								if(old_req_num.equals(exam_view_image.get(k).getPacs_req_code())){
									exam_view_image.get(k).setExam_num(exam_num);
									exam_view_image.get(k).setView_exam_id(view_exam_id);
									exam_view_image.get(k).setPacs_req_code(pacs_summary.get(i).getPacs_req_code());
									
									connection.createStatement().executeUpdate(ProduceSql.getInsertSql("⁯view_exam_image", exam_view_image.get(k),is_id_insert));
									removeviewimage.add(exam_view_image.get(k));
								}
							}
							exam_view_image.removeAll(removeviewimage);
						}
					}
					exam_view.removeAll(removeview);
				}
				//处理普通科室结论表
				List<ExamdepResult> exam_dep = (List<ExamdepResult>) map.get("exam_dep");
				for (int i = 0; i < exam_dep.size(); i++) {
					exam_dep.get(i).setExam_info_id(exam_info_id);
					exam_dep.get(i).setExam_num(exam_num);
					connection.createStatement().executeUpdate(ProduceSql.getInsertSql("⁯exam_dep_result", exam_dep.get(i),is_id_insert));
				}
				//处理普通科室检查明细表
				List<CommonExamDetail> exam_common = (List<CommonExamDetail>) map.get("exam_common");
				for (int i = 0; i < exam_common.size(); i++) {
					exam_common.get(i).setExam_info_id(exam_info_id);
					exam_common.get(i).setExam_num(exam_num);
					connection.createStatement().executeUpdate(ProduceSql.getInsertSql("⁯common_exam_detail", exam_common.get(i),is_id_insert));
				}
				//处理检验科检查结果表
				List<ExamResultDetail> exam_result = (List<ExamResultDetail>) map.get("exam_result");
				for (int i = 0; i < exam_result.size(); i++) {
					exam_result.get(i).setExam_info_id(exam_info_id);
					exam_result.get(i).setExam_num(exam_num);
					connection.createStatement().executeUpdate(ProduceSql.getInsertSql("⁯exam_result_detail", exam_result.get(i),is_id_insert));
				}
				//处理总检结论表
				List<ExamSummary> exam_summary = (List<ExamSummary>) map.get("exam_summary");
				for (int i = 0; i < exam_summary.size(); i++) {
					exam_summary.get(i).setExam_info_id(exam_info_id);
					exam_summary.get(i).setExam_num(exam_num);
					connection.createStatement().executeUpdate(ProduceSql.getInsertSql("⁯exam_summary", exam_summary.get(i),is_id_insert));
				}
				//处理总检阳性发现表
				List<ExaminfoDisease> exam_disease = (List<ExaminfoDisease>) map.get("exam_disease");
				for (int i = 0; i < exam_disease.size(); i++) {
					exam_disease.get(i).setExam_info_id(exam_info_id);
					exam_disease.get(i).setExam_num(exam_num);
					connection.createStatement().executeUpdate(ProduceSql.getInsertSql("⁯examinfo_disease", exam_disease.get(i),is_id_insert));
				}
				//处理危机值表
				List<Critical> examcritical = (List<Critical>) map.get("examcritical");
				for (int i = 0; i < examcritical.size(); i++) {
					examcritical.get(i).setExam_info_id(exam_info_id);
					examcritical.get(i).setExam_num(exam_num);
					connection.createStatement().executeUpdate(ProduceSql.getInsertSql("exam_Critical_detail", examcritical.get(i),is_id_insert));
				}
				
				DataMigrationExam dataexam = new DataMigrationExam();
				dataexam.setArch_num(arch_num);
				dataexam.setArch_num_bar(arch_num_bar);
				dataexam.setExam_num(exam_num);
				dataexam.setExam_num_bar(exam_num_bar);
				dataexam.setCreater(user.getUserid());
				dataexam.setCreate_time(DateTimeUtil.setTimestamp());
				connection.createStatement().executeUpdate(ProduceSql.getInsertSql("⁯data_migration_exam", dataexam,is_id_insert));
			}
			connection.commit();
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	@Override
	public boolean saveDataMigrationDb(Map<String, Object> map, UserDTO user,Connection connection) throws ServiceException {
		boolean is_id_insert = true;
		try {
			connection.setAutoCommit(false);
			List<CustomerInfo> customerlist = (List<CustomerInfo>) map.get("customer");
			for (int i = 0; i < customerlist.size(); i++) {
				connection.createStatement().executeUpdate(ProduceSql.getInsertSql("customer_info", customerlist.get(i),is_id_insert));
			}
			List<ExamInfo> examinfolist = (List<ExamInfo>) map.get("examinfo");
			for (int i = 0; i < examinfolist.size(); i++) {
				connection.createStatement().executeUpdate(ProduceSql.getInsertSql("exam_info", examinfolist.get(i),is_id_insert));
			}
			
			//处理保存体检信息批次关系表 团体体检
			List<ExaminfoBatchDTO> examinfo_batch = (List<ExaminfoBatchDTO>) map.get("examinfobatch");
			for (int i = 0; i < examinfo_batch.size(); i++) {
				connection.createStatement().executeUpdate(ProduceSql.getInsertSql("examinfo_batch", examinfo_batch.get(i),is_id_insert));
			}
					
			//处理保存体检收费项目表
			List<ExaminfoChargingItem> examitem = (List<ExaminfoChargingItem>) map.get("examitem");
			for (int i = 0; i < examitem.size(); i++) {
				connection.createStatement().executeUpdate(ProduceSql.getInsertSql("examinfo_charging_item", examitem.get(i),is_id_insert));
			}
				
			//处理采样结果表
			List<SampleExamDetail> exam_sample = (List<SampleExamDetail>) map.get("exam_sample");
			for (int i = 0; i < exam_sample.size(); i++) {
				connection.createStatement().executeUpdate(ProduceSql.getInsertSql("sample_exam_detail", exam_sample.get(i),is_id_insert));
			}
			
			List<ExamResultChargingItem> exam_sample_eci = (List<ExamResultChargingItem>) map.get("exam_sample_eci");
			for (int i = 0; i < exam_sample_eci.size(); i++) {
				connection.createStatement().executeUpdate(ProduceSql.getInsertSql("examResult_chargingItem", exam_sample_eci.get(i),is_id_insert));
			}
				
			//处理影像结果表
			List<PacsSummary> pacs_summary = (List<PacsSummary>) map.get("pacs_summary");
			for (int i = 0; i < pacs_summary.size(); i++) {
				connection.createStatement().executeUpdate(ProduceSql.getInsertSql("pacs_summary", pacs_summary.get(i),is_id_insert));
			}
			
			List<Pacsdetail> pacs_detail = (List<Pacsdetail>) map.get("pacs_detail");
			for (int i = 0; i < pacs_detail.size(); i++) {
				connection.createStatement().executeUpdate(ProduceSql.getInsertSql("pacs_detail", pacs_detail.get(i),is_id_insert));
			}
				
			List<ViewExamDetail> exam_view = (List<ViewExamDetail>) map.get("exam_view");
			for (int i = 0; i < exam_view.size(); i++) {
				connection.createStatement().executeUpdate(ProduceSql.getInsertSql("view_exam_detail", exam_view.get(i),is_id_insert));
			}
				
			List<ViewExamItem> exam_view_item = (List<ViewExamItem>) map.get("exam_view_item");
			for (int i = 0; i < exam_view_item.size(); i++) {
				connection.createStatement().executeUpdate(ProduceSql.getInsertSql("view_exam_item", exam_view_item.get(i),is_id_insert));
			}
				
			List<ViewExamImage> exam_view_image = (List<ViewExamImage>) map.get("exam_view_image");
			for (int i = 0; i < exam_view_image.size(); i++) {
				connection.createStatement().executeUpdate(ProduceSql.getInsertSql("⁯view_exam_image", exam_view_image.get(i),is_id_insert));
			}
				
			//处理普通科室结论表
			List<ExamdepResult> exam_dep = (List<ExamdepResult>) map.get("exam_dep");
			for (int i = 0; i < exam_dep.size(); i++) {
				connection.createStatement().executeUpdate(ProduceSql.getInsertSql("⁯exam_dep_result", exam_dep.get(i),is_id_insert));
			}
			
			//处理普通科室检查明细表
			List<CommonExamDetail> exam_common = (List<CommonExamDetail>) map.get("exam_common");
			for (int i = 0; i < exam_common.size(); i++) {
				connection.createStatement().executeUpdate(ProduceSql.getInsertSql("⁯common_exam_detail", exam_common.get(i),is_id_insert));
			}
			
			//处理检验科检查结果表
			List<ExamResultDetail> exam_result = (List<ExamResultDetail>) map.get("exam_result");
			for (int i = 0; i < exam_result.size(); i++) {
				connection.createStatement().executeUpdate(ProduceSql.getInsertSql("⁯exam_result_detail", exam_result.get(i),is_id_insert));
			}
			
			//处理总检结论表
			List<ExamSummary> exam_summary = (List<ExamSummary>) map.get("exam_summary");
			for (int i = 0; i < exam_summary.size(); i++) {
				connection.createStatement().executeUpdate(ProduceSql.getInsertSql("⁯exam_summary", exam_summary.get(i),is_id_insert));
			}
				
			//处理总检阳性发现表
			List<ExaminfoDisease> exam_disease = (List<ExaminfoDisease>) map.get("exam_disease");
			for (int i = 0; i < exam_disease.size(); i++) {
				connection.createStatement().executeUpdate(ProduceSql.getInsertSql("⁯examinfo_disease", exam_disease.get(i),is_id_insert));
			}
			
			//处理危机值表
			List<Critical> examcritical = (List<Critical>) map.get("examcritical");
			for (int i = 0; i < examcritical.size(); i++) {
				connection.createStatement().executeUpdate(ProduceSql.getInsertSql("exam_Critical_detail", examcritical.get(i),is_id_insert));
			}
			
			List<DataMigrationExam> dataMigrationExam = (List<DataMigrationExam>) map.get("dataMigrationExam");
			for (int i = 0; i < dataMigrationExam.size(); i++) {
				connection.createStatement().executeUpdate(ProduceSql.getInsertSql("⁯data_migration_exam", dataMigrationExam.get(i),is_id_insert));
			}
			
			List<ViewExamImageBase64> imagebase64 = (List<ViewExamImageBase64>) map.get("imagebase64");
			for (int i = 0; i < imagebase64.size(); i++) {
				connection.createStatement().executeUpdate(ProduceSql.getInsertSql("view_exam_image_base64", imagebase64.get(i),is_id_insert));
			}
			if(map.get("is_out_reg") != null && map.get("center_num") != null){
				String is_out_reg = (String) map.get("is_out_reg");
				String center_num = (String) map.get("center_num");
				String sql = "insert into export_source(is_out_reg,center_num) values('"+is_out_reg+"','"+center_num+"')";
				connection.createStatement().executeUpdate(sql);
			}
			
			connection.commit();
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	@Override
	public List<Map<String, Object>> getDataMigrationCompany(String exam_nums, String center_num)
			throws ServiceException {
		exam_nums = "'"+exam_nums.replaceAll(",", "','")+"'";
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		
		String sql = "select distinct e.company_id as com_id,e.batch_id,e.group_id,c.com_name from exam_info e,company_info c where c.id = e.company_id and e.is_Active = 'Y' and e.exam_type = 'T' and e.exam_num in ("+exam_nums+")";
		List<DataMigrationCompany> mcompany = this.jqm.getList(sql, DataMigrationCompany.class);
		
		for (int i = 0; i < mcompany.size(); i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			
			List<CompanyInfo> company = this.jqm.getList("select * from company_info c where c.id =" + mcompany.get(i).getCom_id(), CompanyInfo.class);
			map.put("company", company);
			
			List<CompanyInfoVsCenterDTO> companyCenter = this.jqm.getList("select * from company_info_vs_center c where c.company_id = "+mcompany.get(i).getCom_id()+" and c.center_num='"+center_num+"'",CompanyInfoVsCenterDTO.class);
			map.put("companyCenter", companyCenter);
			
			List<Batch> batch = this.jqm.getList("select * from batch b where b.id =" +mcompany.get(i).getBatch_id(), Batch.class);
			map.put("batch", batch);
			
			List<GroupInfo> group = this.jqm.getList("select * from group_info g where g.id =" + mcompany.get(i).getGroup_id(), GroupInfo.class);
			map.put("group", group);
			
			List<GroupSet> groupset = this.jqm.getList("select * from group_set s where s.group_id = " + mcompany.get(i).getGroup_id(), GroupSet.class);
			map.put("groupset", groupset);
			
			List<GroupChargingItem> groupitem = this.jqm.getList("select * from group_charging_item g where g.group_id = "+ mcompany.get(i).getGroup_id(), GroupChargingItem.class);
			map.put("groupitem", groupitem);
			
			List<DataMigrationCompany> datamigration = this.jqm.getList("select d.com_id as com_id_car,d.com_id_car as com_id,d.com_name,d.batch_id as batch_id_car,d.batch_id_car as batch_id,d.group_id as group_id_car,d.group_id_car as group_id from data_migration_company d where d.batch_id = '"+mcompany.get(i).getBatch_id()+"'", DataMigrationCompany.class);
			map.put("datamigration", datamigration);
			
			list.add(map);
		}
		return list;
	}
	
	public List<Map<String,Object>> getDataMigrationCompanyByCon(Connection connection) throws ServiceException{
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		
		String sql = "select distinct e.company_id as com_id,e.batch_id,e.group_id,c.com_name from exam_info e,company_info c where c.id = e.company_id and e.is_Active = 'Y' and e.exam_type = 'T'";
		List<DataMigrationCompany> mcompany = ResultSetMapper.mapRersultSetToObject(connection, sql, DataMigrationCompany.class);
		
		for (int i = 0; i < mcompany.size(); i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			
			List<CompanyInfo> company = ResultSetMapper.mapRersultSetToObject(connection, "select * from company_info c where c.id =" + mcompany.get(i).getCom_id(), CompanyInfo.class);
			map.put("company", company);
			
			Statement st = null;
			ResultSet rs = null;
			List<CompanyInfoVsCenterDTO> companyCenter = new ArrayList<CompanyInfoVsCenterDTO>();
			try {
				sql = "select c.company_id,c.center_num,is_owner,creater,create_time from company_info_vs_center c where c.company_id = "+mcompany.get(i).getCom_id();
				st = connection.createStatement();
				rs = st.executeQuery(sql);
				while(rs.next()){
					CompanyInfoVsCenterDTO dto = new CompanyInfoVsCenterDTO();
					dto.setCompany_id(rs.getInt("company_id"));
					dto.setCenter_num(rs.getString("center_num"));
					dto.setIs_owner(rs.getString("is_owner"));
					dto.setCreate_time(rs.getString("create_time"));
					dto.setCreater(rs.getInt("creater"));
					companyCenter.add(dto);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}finally {
				try {
					if(rs != null){
						rs.close();
					}
					if(st != null){
						st.close();
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			map.put("companyCenter", companyCenter);
			
			List<Batch> batch = ResultSetMapper.mapRersultSetToObject(connection,"select * from batch b where b.id =" +mcompany.get(i).getBatch_id(), Batch.class);
			map.put("batch", batch);
			
			List<GroupInfo> group = ResultSetMapper.mapRersultSetToObject(connection,"select * from group_info g where g.id =" + mcompany.get(i).getGroup_id(), GroupInfo.class);
			map.put("group", group);
			
			List<GroupSet> groupset = ResultSetMapper.mapRersultSetToObject(connection,"select * from group_set s where s.group_id = " + mcompany.get(i).getGroup_id(), GroupSet.class);
			map.put("groupset", groupset);
			
			List<GroupChargingItem> groupitem = ResultSetMapper.mapRersultSetToObject(connection,"select * from group_charging_item g where g.group_id = "+ mcompany.get(i).getGroup_id(), GroupChargingItem.class);
			map.put("groupitem", groupitem);
			
			List<DataMigrationCompany> datamigration = ResultSetMapper.mapRersultSetToObject(connection,"select d.com_id as com_id_car,d.com_id_car as com_id,d.com_name,d.batch_id as batch_id_car,d.batch_id_car as batch_id,d.group_id as group_id_car,d.group_id_car as group_id from data_migration_company d where d.batch_id = '"+mcompany.get(i).getBatch_id()+"'", DataMigrationCompany.class);
			map.put("datamigration", datamigration);
			
			list.add(map);
		}
		return list;
	}

	@Override
	public boolean saveDataMigrationCom(Map<String,Object> map,UserDTO user,Connection connection,DataBaseConfigDTO dto) throws ServiceException{
		Boolean is_id_insert = false;
		Boolean con_flag = false;
		try {
			if(connection == null){
				connection = this.jqm.getConnection();
				con_flag = true;
			}
			connection.setAutoCommit(false);
			
			List<CompanyInfo> company = (List<CompanyInfo>) map.get("company");
			List<CompanyInfoVsCenterDTO> companyCenter = (List<CompanyInfoVsCenterDTO>) map.get("companyCenter");
			List<Batch> batch = (List<Batch>) map.get("batch");
			List<GroupInfo> group = (List<GroupInfo>) map.get("group");
			List<GroupSet> groupset = (List<GroupSet>) map.get("groupset");
			List<GroupChargingItem> groupitem = (List<GroupChargingItem>) map.get("groupitem");
			List<DataMigrationCompany> datamigration = (List<DataMigrationCompany>) map.get("datamigration");
			if(datamigration.size() <= 0){
				datamigration = ResultSetMapper.mapRersultSetToObject(connection, "select * from data_migration_company d where d.batch_id_car = "+ batch.get(0).getId(), DataMigrationCompany.class);
			}
		
			long com_id = 0;
			long com_id_bar = company.get(0).getId();
			long batch_id = 0;
			long batch_id_bar = batch.get(0).getId();
			
			List<CompanyInfo> oldcompany = ResultSetMapper.mapRersultSetToObject(connection, "select * from company_info c where c.com_name = '"+company.get(0).getCom_Name()+"'", CompanyInfo.class);
			if(oldcompany.size() > 0){
				company.get(0).setId(oldcompany.get(0).getId());
				connection.createStatement().executeUpdate(ProduceSql.getUpdateSql("company_info", company.get(0)));
				com_id = oldcompany.get(0).getId();
			}else{
				if(dto != null){
					company.get(0).setCom_Num(GetOtherNumContral.getInstance().getParamNum("com_num", user.getCenter_num(), dto));
				}else if(dto == null){// 
					company.get(0).setCom_Num(GetNumContral.getInstance().getParamNum("com_num", user.getCenter_num()));
				}
				connection.createStatement().executeUpdate(ProduceSql.getInsertSql("company_info", company.get(0),is_id_insert));
				
				ResultSet executeQuery = connection.createStatement().executeQuery("select SCOPE_IDENTITY() as id ");//主键id
				if (executeQuery.next()) {
					com_id = executeQuery.getInt("id");
				}
			}
			
			//删除、插入体检中心与单位关系表
			connection.createStatement().executeUpdate("delete from company_info_vs_center where company_id = "+com_id+" and center_num='"+companyCenter.get(0).getCenter_num()+"'");
			String sql = "insert into company_info_vs_center(company_id,center_num,is_owner,creater,create_time,contact_name,"
					+ "contact_phone,com_phone,com_jianjie,com_fax,com_type,keShi_Name,email,address,remark) "
						+" values("+com_id+",'"+companyCenter.get(0).getCenter_num()+"','"+companyCenter.get(0).getIs_owner()+"',"+companyCenter.get(0).getCreater()+",'"+companyCenter.get(0).getCreate_time()+"',"
								+ "'"+company.get(0).getContact_Name()+"','"+company.get(0).getContact_Phone()+"','"+company.get(0).getCom_phone()+"',"
								+ "'"+company.get(0).getCom_jianjie()+"','"+company.get(0).getCom_fax()+"','"+company.get(0).getCom_Type()+"',"
								+ "'"+company.get(0).getKeShi_Name()+"','"+company.get(0).getEmail()+"','"+company.get(0).getAddress()+"',"
								+ "'"+company.get(0).getRemark()+"') ";
			connection.createStatement().executeUpdate(sql);
			if(datamigration.size() <= 0){//未同步，新增单位批次
				List<Batch> oldbatch = ResultSetMapper.mapRersultSetToObject(connection, "select * from batch b where b.company_id = '"+com_id+"' and b.batch_name = '"+batch.get(0).getBatch_name()+"' and b.center_num = '"+user.getCenter_num()+"' and b.is_Active = 'Y'", Batch.class);
				if(oldbatch.size() > 0){
					batch.get(0).setBatch_name(batch.get(0).getBatch_name()+"（外检）");
				}
				if(dto != null){
					batch.get(0).setBatch_num(GetOtherNumContral.getInstance().getParamNum("batch_num", user.getCenter_num(), dto));
				}else if(dto == null){// 
					batch.get(0).setBatch_num(GetNumContral.getInstance().getParamNum("batch_num", user.getCenter_num()));
				}
				batch.get(0).setCompany_id(com_id);
				connection.createStatement().executeUpdate(ProduceSql.getInsertSql("batch", batch.get(0),is_id_insert));
				
				ResultSet executeQuery = connection.createStatement().executeQuery("select SCOPE_IDENTITY() as id ");//主键id
				if (executeQuery.next()) {
					batch_id = executeQuery.getInt("id");
				}
				
				if(group.size() > 0){
					for (int i = 0; i < group.size(); i++) {
						long group_id = 0;
						GroupInfo groupinfo = group.get(i);
						groupinfo.setBatch_id(batch_id);
						connection.createStatement().executeUpdate(ProduceSql.getInsertSql("group_info", groupinfo,is_id_insert));
						
						ResultSet executegroup = connection.createStatement().executeQuery("select SCOPE_IDENTITY() as id ");//主键id
						if (executegroup.next()) {
							group_id = executegroup.getInt("id");
						}
						
						List<GroupSet> removeset = new ArrayList<GroupSet>();
						for (int j = 0; j < groupset.size(); j++) {
							if(groupinfo.getId() == groupset.get(j).getGroup_id()){
								groupset.get(j).setGroup_id(group_id);
								connection.createStatement().executeUpdate(ProduceSql.getInsertSql("group_set", groupset.get(j),is_id_insert));
								removeset.add(groupset.get(j));
							}
						}
						groupset.removeAll(removeset);
						
						List<GroupChargingItem> removeitem = new ArrayList<GroupChargingItem>();
						for (int j = 0; j < groupitem.size(); j++) {
							if(groupinfo.getId() == groupitem.get(j).getGroup_id()){
								groupitem.get(j).setGroup_id(group_id);
								connection.createStatement().executeUpdate(ProduceSql.getInsertSql("group_charging_item", groupitem.get(j),is_id_insert));
								removeitem.add(groupitem.get(j));
							}
						}
						groupitem.removeAll(removeitem);
						
						DataMigrationCompany dataMigration = new DataMigrationCompany();
						dataMigration.setCom_id(com_id);
						dataMigration.setCom_id_car(com_id_bar);
						dataMigration.setCom_name(company.get(0).getCom_Name());
						dataMigration.setBatch_id(batch_id);
						dataMigration.setBatch_id_car(batch_id_bar);
						dataMigration.setGroup_id(group_id);
						dataMigration.setGroup_id_car(groupinfo.getId());
								
						connection.createStatement().executeUpdate(ProduceSql.getInsertSql("data_migration_company", dataMigration,is_id_insert));
					}
				}else{
					DataMigrationCompany dataMigration = new DataMigrationCompany();
					dataMigration.setCom_id(com_id);
					dataMigration.setCom_id_car(com_id_bar);
					dataMigration.setCom_name(company.get(0).getCom_Name());
					dataMigration.setBatch_id(batch_id);
					dataMigration.setBatch_id_car(batch_id_bar);
					dataMigration.setGroup_id(0);
					dataMigration.setGroup_id_car(0);
							
					connection.createStatement().executeUpdate(ProduceSql.getInsertSql("data_migration_company", dataMigration,is_id_insert));
				}
				
			}else{//已同步，修改信息
				List<Batch> oldbatch = ResultSetMapper.mapRersultSetToObject(connection,"select * from batch b where b.id ="+datamigration.get(0).getBatch_id(),Batch.class);
				
				batch.get(0).setBatch_name(oldbatch.get(0).getBatch_name());
				batch.get(0).setBatch_num(oldbatch.get(0).getBatch_num());
				batch.get(0).setCompany_id(oldbatch.get(0).getCompany_id());
				batch.get(0).setId(datamigration.get(0).getBatch_id());
				connection.createStatement().executeUpdate(ProduceSql.getUpdateSql("batch", batch.get(0)));
				
				for (int i = 0; i < datamigration.size(); i++) {
					if(datamigration.get(i).getGroup_id() == 0){
						continue;
					}
					for (int j = 0; j < group.size(); j++) {
						if(datamigration.get(i).getGroup_id_car() == group.get(j).getId()){
							group.get(j).setId(datamigration.get(i).getGroup_id());
							group.get(j).setBatch_id(datamigration.get(i).getBatch_id());
							connection.createStatement().executeUpdate(ProduceSql.getUpdateSql("group_info", group.get(j)));
							
							connection.createStatement().executeUpdate("delete from group_set where group_id = '"+datamigration.get(i).getGroup_id()+"'");
							List<GroupSet> removeset = new ArrayList<GroupSet>();
							for (int k = 0; k < groupset.size(); k++) {
								if(datamigration.get(i).getGroup_id_car() == groupset.get(k).getGroup_id()){
									groupset.get(k).setGroup_id(datamigration.get(i).getGroup_id());
									connection.createStatement().executeUpdate(ProduceSql.getInsertSql("group_set", groupset.get(k),is_id_insert));
									removeset.add(groupset.get(k));
								}
							}
							groupset.removeAll(removeset);
							
							connection.createStatement().executeUpdate("delete from group_charging_item where group_id = '"+datamigration.get(i).getGroup_id()+"'");
							List<GroupChargingItem> removeitem = new ArrayList<GroupChargingItem>();
							for (int k = 0; k < groupitem.size(); k++) {
								if(datamigration.get(i).getGroup_id_car() == groupitem.get(k).getGroup_id()){
									groupitem.get(k).setGroup_id(datamigration.get(i).getGroup_id());
									connection.createStatement().executeUpdate(ProduceSql.getInsertSql("group_charging_item", groupitem.get(k),is_id_insert));
									removeitem.add(groupitem.get(k));
								}
							}
							groupitem.removeAll(removeitem);
							group.remove(j);
						}
					}
				}
				for (int i = 0; i < group.size(); i++) {
					long group_id = 0;
					GroupInfo groupinfo = group.get(i);
					groupinfo.setBatch_id(batch_id);
					connection.createStatement().executeUpdate(ProduceSql.getInsertSql("group_info", groupinfo,is_id_insert));
					
					ResultSet executegroup = connection.createStatement().executeQuery("select SCOPE_IDENTITY() as id ");//主键id
					if (executegroup.next()) {
						group_id = executegroup.getInt("id");
					}
					
					List<GroupSet> removeset = new ArrayList<GroupSet>();
					for (int j = 0; j < groupset.size(); j++) {
						if(groupinfo.getId() == groupset.get(j).getGroup_id()){
							groupset.get(j).setGroup_id(group_id);
							connection.createStatement().executeUpdate(ProduceSql.getInsertSql("group_set", groupset.get(j),is_id_insert));
							removeset.add(groupset.get(j));
						}
					}
					groupset.removeAll(removeset);
					
					List<GroupChargingItem> removeitem = new ArrayList<GroupChargingItem>();
					for (int j = 0; j < groupitem.size(); j++) {
						if(groupinfo.getId() == groupitem.get(j).getGroup_id()){
							groupitem.get(j).setGroup_id(group_id);
							connection.createStatement().executeUpdate(ProduceSql.getInsertSql("group_charging_item", groupitem.get(j),is_id_insert));
							removeitem.add(groupitem.get(j));
						}
					}
					groupitem.removeAll(removeitem);
					
					DataMigrationCompany dataMigration = new DataMigrationCompany();
					dataMigration.setCom_id(com_id);
					dataMigration.setCom_id_car(com_id_bar);
					dataMigration.setCom_name(company.get(0).getCom_Name());
					dataMigration.setBatch_id(batch_id);
					dataMigration.setBatch_id_car(batch_id_bar);
					dataMigration.setGroup_id(group_id);
					dataMigration.setGroup_id_car(groupinfo.getId());
						
					connection.createStatement().executeUpdate(ProduceSql.getInsertSql("data_migration_company", dataMigration,is_id_insert));
				}
			}
			connection.commit();
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}finally {
			if(con_flag && connection != null){
				try {
					connection.close();
				} catch (SQLException e) {
				}
			}
		}
		return true;
	}
	
	@Override
	public boolean saveDataMigrationComDb(Map<String,Object> map,UserDTO user,Connection connection) throws ServiceException{
		Boolean is_id_insert = true;
		try {
			connection.setAutoCommit(false);
			
			List<CompanyInfo> company = (List<CompanyInfo>) map.get("company");
			List<CompanyInfoVsCenterDTO> companyCenter = (List<CompanyInfoVsCenterDTO>) map.get("companyCenter");
			for (int i = 0; i < company.size(); i++) {
				connection.createStatement().executeUpdate(ProduceSql.getInsertSql("company_info", company.get(i),is_id_insert));
				for (int j = 0; j < companyCenter.size(); j++) {
					if(companyCenter.get(j).getCompany_id() == company.get(i).getId()){
						String sql = "insert into company_info_vs_center(company_id,center_num,is_owner,creater,create_time,contact_name,"
								+ "contact_phone,com_phone,com_jianjie,com_fax,com_type,keShi_Name,email,address,remark) "
									+" values("+companyCenter.get(j).getCompany_id()+",'"+companyCenter.get(j).getCenter_num()+"','"+companyCenter.get(j).getIs_owner()+"',"+companyCenter.get(j).getCreater()+",'"+companyCenter.get(j).getCreate_time()+"',"
											+ "'"+company.get(i).getContact_Name()+"','"+company.get(i).getContact_Phone()+"','"+company.get(i).getCom_phone()+"',"
											+ "'"+company.get(i).getCom_jianjie()+"','"+company.get(i).getCom_fax()+"','"+company.get(i).getCom_Type()+"',"
											+ "'"+company.get(i).getKeShi_Name()+"','"+company.get(i).getEmail()+"','"+company.get(i).getAddress()+"',"
											+ "'"+company.get(i).getRemark()+"') ";
						connection.createStatement().executeUpdate(sql);
					}
				}
			}
			
			List<Batch> batch = (List<Batch>) map.get("batch");
			for (int i = 0; i < batch.size(); i++) {
				connection.createStatement().executeUpdate(ProduceSql.getInsertSql("batch", batch.get(i),is_id_insert));
			}
			List<GroupInfo> group = (List<GroupInfo>) map.get("group");
			for (int i = 0; i < group.size(); i++) {
				connection.createStatement().executeUpdate(ProduceSql.getInsertSql("group_info", group.get(i),is_id_insert));
			}
			List<GroupSet> groupset = (List<GroupSet>) map.get("groupset");
			for (int i = 0; i < groupset.size(); i++) {
				connection.createStatement().executeUpdate(ProduceSql.getInsertSql("group_set", groupset.get(i),is_id_insert));
			}
			
			List<GroupChargingItem> groupitem = (List<GroupChargingItem>) map.get("groupitem");
			for (int i = 0; i < groupitem.size(); i++) {
				connection.createStatement().executeUpdate(ProduceSql.getInsertSql("group_charging_item", groupitem.get(i),is_id_insert));
			}
			List<DataMigrationCompany> datamigration = (List<DataMigrationCompany>) map.get("datamigration");
			for (int i = 0; i < datamigration.size(); i++) {
				connection.createStatement().executeUpdate(ProduceSql.getInsertSql("data_migration_company", datamigration.get(i),is_id_insert));
			}
			connection.commit();
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		return true;
	}

	@Override
	public PageReturnDTO getDataMigrationList(DataMigrationModel model, int rows, int page,
			String sort, String order, UserDTO user) throws ServiceException {
		String isprivateflag = IsPrivateCenterConfig.isprivateflag(user);
		int count = 0;
		String sql =" select e.status,e.id,c.arch_num,dbo.fun_CharToStar(e.exam_num,'id_num',c.id_num,'"+isprivateflag+"') as id_num,e.exam_num,e.age,"
				+ "dbo.fun_CharToStar(e.exam_num,'name',c.user_name,'"+isprivateflag+"') as user_name,c.sex,"
						+ "dbo.fun_CharToStar(e.exam_num,'phone',e.phone,'"+isprivateflag+"') as phone,e.address,e.introducer,"
				   +" convert(varchar(50),e.join_date,23) as join_date,e.exam_type,e.join_date as d, "
				   +" dbo.GetExamSetNameByExamID(e.exam_num) as set_name,v.com_name as company,e.is_guide_back,"
				   +" e.is_need_guide,e.is_need_barcode,e.counter_check,convert(varchar(50),e.register_date,23) as register_date,"
				   +" dbo.GetTeamPayByExamId(e.exam_num) as team_pay,dbo.GetPersonalPayByExamId(e.exam_num) as personal_pay,dbo.GetMinExamDateByExamId(e.exam_num) as exam_date"
				   +",case when od.exam_num is null then '否' else '是' end exam_export,case when dm.exam_num is null then '否' else '是' end exam_import "
				   +" from customer_info c,exam_info e "
				   + " left join company_info v on v.id=e.company_id "
				   +" left join out_data_exp od on od.exam_num = e.exam_num "
				   +" left join data_migration_exam dm on dm.exam_num = e.exam_num "
				   +" where e.customer_id = c.id and e.is_Active = 'Y' and e.center_num = '"+user.getCenter_num()+"'";
		if (model.getArch_num() != null && !"".equals(model.getArch_num())) {// 档案号
			sql += " and c.arch_num='" + model.getArch_num().trim() + "'";
			count ++;
		}
		if (model.getId_num() != null && !"".equals(model.getId_num())) {// 身份证
			sql += " and c.id_num='" + model.getId_num() + "'";
			count ++;
		}  
		if (model.getExam_num() != null && !"".equals(model.getExam_num())) {// 体检号
			sql += " and e.exam_num='" + model.getExam_num().trim() + "'";
			count ++;
		}
		if (model.getUser_name() != null && !"".equals(model.getUser_name())) {// 体检姓名
			sql += " and c.user_name   like '" + model.getUser_name().trim() + "%'";
			count ++;
		}
		if("1".equals(model.getDep_category())){
			sql += " and e.id in (select distinct ec.examinfo_id  from examinfo_charging_item ec where 1=1 and ec.center_num = '"+user.getCenter_num()+"' ";
			if (model.getS_join_date() != null && !"".equals(model.getS_join_date())) {// 体检开始日期
				sql += " and ec.exam_date >= '" + model.getS_join_date() + "'";
				count ++;
			} 
			if (model.getE_join_date() != null && !"".equals(model.getE_join_date())) {// 体检结束日期
				sql += " and ec.exam_date <= '" + model.getE_join_date() + "'";
				count ++;
			} 
			sql += ")";
			
		}else{
			if (model.getS_join_date() != null && !"".equals(model.getS_join_date())) {// 体检开始日期
				sql += " and e.join_date >= '" + model.getS_join_date() + "'";
				count ++;
			} 
			if (model.getE_join_date() != null && !"".equals(model.getE_join_date())) {// 体检结束日期
				sql += " and e.join_date <= '" + model.getE_join_date() + "'";
				count ++;
			} 
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
		if(model.getLevel() != null && !"".equals(model.getLevel())){
			sql += " and e._level = '"+model.getLevel()+"'";
			count ++;
		}
		
		if("1".equals(model.getDep_category())){
			sql = "select * from ("+sql+") e where 1=1";
			if (model.getS_join_date() != null && !"".equals(model.getS_join_date())) {// 体检开始日期
				sql += " and  e.exam_date >= '" + model.getS_join_date() + "'";
			} 
			if (model.getE_join_date() != null && !"".equals(model.getE_join_date())) {// 体检结束日期
				sql += " and  e.exam_date  <='" + model.getE_join_date() + "'";
			} 
		}
		
		if("N".equals(model.getExam_export())){
			sql += " and od.exam_num is null";
		}else if("Y".equals(model.getExam_export())){
			sql += " and od.exam_num is not null";
		}
		
		if("N".equals(model.getExam_import())){
			sql += " and dm.exam_num is null";
		}else if("Y".equals(model.getExam_import())){
			sql += " and dm.exam_num is not null";
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
	public boolean createDataMigrationTable(Connection connection) throws ServiceException {
		try {
			connection.setAutoCommit(false);
			
			connection.createStatement().executeUpdate(ProduceSql.getCreateSql("company_info", CompanyInfo.class));
			connection.createStatement().executeUpdate(ProduceSql.getCreateSql("batch", Batch.class));
			connection.createStatement().executeUpdate(ProduceSql.getCreateSql("group_info", GroupInfo.class));
			connection.createStatement().executeUpdate(ProduceSql.getCreateSql("group_set", GroupSet.class));
			connection.createStatement().executeUpdate(ProduceSql.getCreateSql("group_charging_item", GroupChargingItem.class));
			connection.createStatement().executeUpdate(ProduceSql.getCreateSql("data_migration_company", DataMigrationCompany.class));
			connection.createStatement().executeUpdate(ProduceSql.getCreateSql("customer_info", CustomerInfo.class));
			connection.createStatement().executeUpdate(ProduceSql.getCreateSql("exam_info", ExamInfo.class));
			connection.createStatement().executeUpdate(ProduceSql.getCreateSql("examinfo_batch", ExaminfoBatchDTO.class));
			connection.createStatement().executeUpdate(ProduceSql.getCreateSql("examinfo_charging_item", ExaminfoChargingItem.class));
			connection.createStatement().executeUpdate(ProduceSql.getCreateSql("sample_exam_detail", SampleExamDetail.class));
			connection.createStatement().executeUpdate(ProduceSql.getCreateSql("examResult_chargingItem", ExamResultChargingItem.class));
			connection.createStatement().executeUpdate(ProduceSql.getCreateSql("pacs_summary", PacsSummary.class));
			connection.createStatement().executeUpdate(ProduceSql.getCreateSql("pacs_detail", Pacsdetail.class));
			connection.createStatement().executeUpdate(ProduceSql.getCreateSql("view_exam_detail", ViewExamDetail.class));
			connection.createStatement().executeUpdate(ProduceSql.getCreateSql("view_exam_item", ViewExamItem.class));
			connection.createStatement().executeUpdate(ProduceSql.getCreateSql("view_exam_image", ViewExamImage.class));
			connection.createStatement().executeUpdate(ProduceSql.getCreateSql("exam_dep_result", ExamdepResult.class));
			connection.createStatement().executeUpdate(ProduceSql.getCreateSql("common_exam_detail", CommonExamDetail.class));
			connection.createStatement().executeUpdate(ProduceSql.getCreateSql("⁯exam_result_detail", ExamResultDetail.class));
			connection.createStatement().executeUpdate(ProduceSql.getCreateSql("exam_summary", ExamSummary.class));
			connection.createStatement().executeUpdate(ProduceSql.getCreateSql("examinfo_disease", ExaminfoDisease.class));
			connection.createStatement().executeUpdate(ProduceSql.getCreateSql("exam_Critical_detail", Critical.class));
			connection.createStatement().executeUpdate(ProduceSql.getCreateSql("data_migration_exam", DataMigrationExam.class));
			connection.createStatement().executeUpdate(ProduceSql.getCreateSql("view_exam_image_base64", ViewExamImageBase64.class));
			
			String sql = "CREATE TABLE company_info_vs_center (company_id int NOT NULL,[center_num] [varchar](50) NOT NULL,[is_owner] [char](1) NOT NULL,"
					+ "[creater] [int] NULL,[create_time] [varchar](200) NULL,[contact_phone] [varchar](45) NULL,[contact_name] [varchar](45) NULL,"
					+ "[com_phone] [varchar](45) NULL,[com_jianjie] [varchar](200) NULL,[com_fax] [varchar](50) NULL,[com_type] [varchar](45) NULL,"
					+ "[keShi_Name] [varchar](20) NULL,[email] [varchar](45) NULL,[address] [varchar](200) NULL,[remark] [varchar](500) NULL)";
			connection.createStatement().executeUpdate(sql);
			
			sql = "create table export_source(is_out_reg varchar(50),center_num varchar(50))"; //导出来源表，体检车导出或院内导出
			connection.createStatement().executeUpdate(sql);
			connection.commit();
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	@Override
	public void saveExportExaminfo(String exam_num) throws ServiceException{
		List<OutDataExpDTO> list = this.jqm.getList("select * from out_data_exp where exam_num = '"+exam_num+"'", OutDataExpDTO.class);
		String sql = null;
		if(list.size() > 0){
			sql = "update out_data_exp set exp_date='"+DateTimeUtil.getDate3()+"' where exam_num ='"+exam_num+"'";
		}else{
			sql = "insert into out_data_exp(exam_num,exp_date) values('"+exam_num+"','"+DateTimeUtil.getDate3()+"')";
		}
		this.jpm.executeSql(sql);
	}

	@Override
	public String vieryExportDb(Connection connection, String is_out_reg, String center_num) throws ServiceException {
		String sql = "select * from export_source ";
		Statement st = null;
		ResultSet rs = null;
		boolean flag = true;
		boolean flag_center = true;
		try {
			st = connection.createStatement();
			rs = st.executeQuery(sql);
			if (rs != null) {
				while (rs.next()) {
					if(rs.getString("is_out_reg").equals(is_out_reg)){
						flag = false;
					}
					if(!rs.getString("center_num").equals(center_num)){
						flag_center = false;
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				if(rs != null){
					rs.close();
				}
				if(st != null){
					st.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if(!flag){
			if(is_out_reg.equals("Y")){//体检车
				return "体检车导出数据文件不能导入体检车系统!";
			}else{
				return "院内导出数据文件不能导入院内系统!";
			}
		}
		if(!flag_center){
			return "导入的数据所属体检中心与登录体检中心不符，请重新登录导入!";
		}
		return "ok";
	}
}
