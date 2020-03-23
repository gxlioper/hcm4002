package com.hjw.wst.service.impl;

import java.util.List;

import com.hjw.util.CommSet;
import com.hjw.util.DateTimeUtil;
import com.hjw.util.EncryptUtils;
import com.hjw.wst.DTO.ExamInfoDTO;
import com.hjw.wst.DTO.SampleExamDetailDTO;
import com.hjw.wst.DTO.UserDTO;
import com.hjw.wst.DTO.WebResourceDTO;
import com.hjw.wst.config.GetNumContral;
import com.hjw.wst.domain.ChargingItem;
import com.hjw.wst.domain.ExamResultChargingItem;
import com.hjw.wst.domain.PacsSummary;
import com.hjw.wst.domain.SampleExamDetail;
import com.hjw.wst.model.SamplingDepartmentModel;
import com.hjw.wst.service.SamplingDepartmentService;
import com.synjones.framework.exception.ServiceException;
import com.synjones.framework.persistence.JdbcPersistenceManager;
import com.synjones.framework.persistence.JdbcQueryManager;
import com.synjones.framework.persistence.PersistenceManager;
import com.synjones.framework.persistence.QueryManager;

public class SamplingDepartmentServiceImpl implements SamplingDepartmentService {
	private QueryManager qm;
	private JdbcQueryManager jqm;
	private JdbcPersistenceManager jpm;
	private PersistenceManager pm;
	
	public void setPersistenceManager(PersistenceManager pm) {
		this.pm = pm;
	}

	public void setQueryManager(QueryManager qm) {
		this.qm = qm;
	}

	public void setJdbcQueryManager(JdbcQueryManager jqm) {
		this.jqm = jqm;
	}

	public void setJdbcPersistenceManager(JdbcPersistenceManager jpm) {
		this.jpm = jpm;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<SampleExamDetailDTO> getSamplingDetailList(String exam_num, String center_num) throws ServiceException {
		String sql = " select (case when (sd.BarCode_Class = 1 and (se.is_binding is null or se.is_binding = 0)) then '' else se.sample_barcode end)"
				   + " as sample_barcode,se.id,se.status,(case when se.is_binding is null then 0 else se.is_binding end) as is_binding,se.sample_id,sd.demo_name,"
				   + " sd.BarCode_Class,ci.item_name,ci.item_code,ci.id as item_id,sd.demo_color,sd.demo_indicator,ec.is_application,se.print_num,sd.print_dep "
				   + " from exam_info e,sample_exam_detail se,sample_demo sd,examinfo_charging_item ec,charging_item ci,examResult_chargingItem er "
				   + " where e.exam_num = se.exam_num and se.sample_barcode=er.bar_code "
				   + "and er.charging_item_code = ec.charging_item_code and er.result_type = 'sample'"
				   + " and se.sample_id = sd.id and ci.item_code = ec.charging_item_code and e.exam_num = ec.exam_num and "
				   + " ec.isActive = 'Y' and ec.pay_status != 'M' and ec.center_num = '"+center_num+"'"
				   + " and (e.is_after_pay = 'Y' or ec.pay_status != 'N') and e.exam_num = '"+exam_num+"' order by sd.print_seq,se.id";
		List<SampleExamDetailDTO> list = this.jqm.getList(sql, SampleExamDetailDTO.class);
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public SampleExamDetail getSampleDetailByBarcode(String barCode) throws ServiceException {
		List<SampleExamDetail> list = this.qm.find("from SampleExamDetail where sample_barcode = '"+barCode+"'");
		if(list.size() != 0){
			return list.get(0);
		}
		return null;
	}

	@Override
	public String samplingSample(SamplingDepartmentModel model, UserDTO user) throws ServiceException {
		List<SampleExamDetailDTO> listDto = model.getSampleExamDetailList();
		
		for(SampleExamDetailDTO sampl : listDto){
			SampleExamDetail sampleExamDetail = (SampleExamDetail) this.qm.load(SampleExamDetail.class, sampl.getId());
			
			List<ExamResultChargingItem> examitem = this.qm.find("from ExamResultChargingItem e where e.bar_code = '"+ sampleExamDetail.getSample_barcode() +"' and e.result_type = '"+CommSet.lis_sample+"'");
			
			sampleExamDetail.setSample_barcode(sampl.getSample_barcode());
			sampleExamDetail.setIs_binding(1);
			sampleExamDetail.setStatus("Y");
			sampleExamDetail.setCheck_id(user.getUserid());
			sampleExamDetail.setCheck_doctor(user.getName());
			sampleExamDetail.setCheck_date(DateTimeUtil.parse());
			
			this.pm.update(sampleExamDetail);
			
			for (int i = 0; i < examitem.size(); i++) {
				ExamResultChargingItem item = examitem.get(i);
				
				item.setBar_code(sampl.getSample_barcode());
				this.pm.update(item);
			}
		}
		
		return "保存成功!";
	}

	@SuppressWarnings("unchecked")
	@Override
	public String canlSampleExamDetail(SamplingDepartmentModel model, UserDTO user) throws ServiceException {
		
		SampleExamDetail oldSampleExamDetail = (SampleExamDetail) this.qm.load(SampleExamDetail.class, model.getSampleId());
		
		if(oldSampleExamDetail == null){
			return "该样本记录不存在!";
		}
		
		List<ExamResultChargingItem> resultItemList = this.qm.find("from ExamResultChargingItem where bar_code = '"
		+ oldSampleExamDetail.getSample_barcode() +"' and result_type = '"+CommSet.lis_sample+"'");
		
		for(ExamResultChargingItem examResultChargingItem : resultItemList){
			this.pm.remove(examResultChargingItem);
		}
		
		this.pm.remove(oldSampleExamDetail);
		
		
		List<SampleExamDetail> list = this.qm.find("from SampleExamDetail where exam_num = '"+oldSampleExamDetail.getExam_num()
		+"' and sample_id = " +oldSampleExamDetail.getSample_id()+ " and status = 'W'");
		
		SampleExamDetail newSampleExamDetail;
		if(list.size() != 0){
			newSampleExamDetail = list.get(0);
		}else{
			newSampleExamDetail = new SampleExamDetail();
			
			newSampleExamDetail.setExam_info_id(oldSampleExamDetail.getExam_info_id());                    	   
			newSampleExamDetail.setSample_id(oldSampleExamDetail.getSample_id());
			newSampleExamDetail.setSample_barcode(GetNumContral.getInstance().getParamNum("barcode", user.getCenter_num()));
			newSampleExamDetail.setStatus("W");
			newSampleExamDetail.setPic_path("");
			newSampleExamDetail.setCenter_num(user.getCenter_num());
			newSampleExamDetail.setCreater(user.getUserid());
			newSampleExamDetail.setCreate_time(DateTimeUtil.parse());
			newSampleExamDetail.setUpdater(user.getUserid());
			newSampleExamDetail.setUpdate_time(DateTimeUtil.parse());
			newSampleExamDetail.setExam_num(oldSampleExamDetail.getExam_num());
			
			this.pm.save(newSampleExamDetail);
		}
		
		for(ExamResultChargingItem examResultChargingItem : resultItemList){
			ExamResultChargingItem erci = new ExamResultChargingItem();
			
			erci.setCharging_id(examResultChargingItem.getCharging_id());
            erci.setExam_id(newSampleExamDetail.getId());
            erci.setResult_type(CommSet.lis_sample);
            erci.setIsActive("Y");
            erci.setCreater(user.getUserid());
            erci.setCreate_time(DateTimeUtil.parse());
            erci.setUpdater(user.getUserid());
            erci.setUpdate_time(DateTimeUtil.parse());
            erci.setBar_code(newSampleExamDetail.getSample_barcode());
            erci.setCharging_item_code(examResultChargingItem.getCharging_item_code());
            this.pm.save(erci);
		}
		
		return "取消采样成功!";
	}

	@SuppressWarnings("unchecked")
	@Override
	public ExamInfoDTO getExamInfoByBarCode(String barCode) throws ServiceException {
		
		String sql = "select s.exam_num from sample_exam_detail s where s.sample_barcode = '"+barCode+"'";
		List<ExamInfoDTO> list = this.jqm.getList(sql, ExamInfoDTO.class);
		if(list.size() != 0){
			return list.get(0);
		}
		return null;
	}

	@Override
	@SuppressWarnings("unchecked")
	public String mergeSampleDemo(SamplingDepartmentModel model) throws ServiceException {
		String[] ids = model.getOld_sampleIds().split(",");
		List<SampleExamDetail> sampleExamDetail = this.qm.find("from SampleExamDetail s where s.id = "+model.getSampleId());
		if(sampleExamDetail.size() <= 0){
			return "强制合并失败!";
		}
		for (int i = 0; i < ids.length; i++) {
			List<SampleExamDetail> oldSampleExamDetail = this.qm.find("from SampleExamDetail s where s.id = " + Long.valueOf(ids[i]));
			
			if(oldSampleExamDetail.size() > 0){
				this.pm.remove(oldSampleExamDetail.get(0));
				List<ExamResultChargingItem> resultItemList = this.qm.find("from ExamResultChargingItem where bar_code ='"
						+ oldSampleExamDetail.get(0).getSample_barcode() +"' and result_type = '"+CommSet.lis_sample+"'");
				for (ExamResultChargingItem item : resultItemList) {
					item.setExam_id(model.getSampleId());
					item.setBar_code(sampleExamDetail.get(0).getSample_barcode());
					this.pm.update(item);
				}
			}
		}
		return "强制合并成功!";
	}

	@Override
	@SuppressWarnings("unchecked")
	public String verifyUserPrintBarcode(SamplingDepartmentModel model, UserDTO user) throws ServiceException {
		if("".equals(model.getUser_name()) || model.getUser_name() == null){//表示获取当前登录人员的打印资源
			
		}else{//获取重新输入的用户名、密码的打印资源
			String sql = "select u.id as userid,u.chi_name,u.pwd_encrypted as usertype,u.is_active as center_num from user_usr u where u.log_name = '"+model.getUser_name().trim()+"'";
			List<UserDTO> list = this.jqm.getList(sql, UserDTO.class);
			if(list.size() == 0){
				return "no1-用户名不存在!";
			}
			user = list.get(0);
			String newpasswd=EncryptUtils.encryptMD5(model.getKey_word()).toLowerCase();
			if("N".equals(user.getCenter_num())){
				return "no2-用户已停用!";
			}else if(!user.getUsertype().toLowerCase().equals(newpasswd)){
				return "no3-密码不正确!";
			}
		}
		String sql = "select w.datavalue from WEB_RESRELATIONSHIP w where w.res_type = 2 and w.res_code = 'RS012' and w.userorroleid = " + user.getUserid();
		List<WebResourceDTO> list = this.jqm.getList(sql, WebResourceDTO.class);
		if(list.size() == 0){
			return "ok-1";
		}
		return "ok-"+list.get(0).getDatavalue();
	}

	@Override
	public String splitUpSampleExamDetail(SamplingDepartmentModel model, UserDTO user) throws ServiceException {
		
		List<ExamResultChargingItem> resultItemList = this.qm.find("from ExamResultChargingItem where exam_id = "
				+ model.getId() +" and result_type = '"+CommSet.lis_sample+"' and charging_id in ("+model.getCharingids()+")");
		for (ExamResultChargingItem item : resultItemList) {
			this.pm.remove(item);
		}
		SampleExamDetail newSampleExamDetail = new SampleExamDetail();
		
		newSampleExamDetail.setExam_info_id(model.getExaminfo_id());                    	   
		newSampleExamDetail.setSample_id(model.getSampleId());
		newSampleExamDetail.setSample_barcode(GetNumContral.getInstance().getParamNum("barcode", user.getCenter_num()));
		newSampleExamDetail.setStatus("W");
		newSampleExamDetail.setPic_path("");
		newSampleExamDetail.setCenter_num(user.getCenter_num());
		newSampleExamDetail.setCreater(user.getUserid());
		newSampleExamDetail.setCreate_time(DateTimeUtil.parse());
		newSampleExamDetail.setUpdater(user.getUserid());
		newSampleExamDetail.setUpdate_time(DateTimeUtil.parse());
		newSampleExamDetail.setExam_num(model.getExam_num());
		
		this.pm.save(newSampleExamDetail);
		
		String[] ids = model.getCharingids().split(",");
		for (int i = 0; i < ids.length; i++) {
			ChargingItem item = (ChargingItem) this.qm.load(ChargingItem.class, Long.valueOf(ids[i]));
			ExamResultChargingItem erci = new ExamResultChargingItem();
			
			erci.setCharging_id(Integer.valueOf(ids[i]));
            erci.setExam_id(newSampleExamDetail.getId());
            erci.setResult_type(CommSet.lis_sample);
            erci.setIsActive("Y");
            erci.setCreater(user.getUserid());
            erci.setCreate_time(DateTimeUtil.parse());
            erci.setUpdater(user.getUserid());
            erci.setUpdate_time(DateTimeUtil.parse());
            erci.setBar_code(newSampleExamDetail.getSample_barcode());
            erci.setCharging_item_code(item.getItem_code());
            this.pm.save(erci);
		}
		return "拆分样本成功!";
	}

	@Override
	public List<SampleExamDetailDTO> getTeamSampleListItem(String ids, String center_num) throws ServiceException {
		String sql = "select distinct s.sample_id,sd.demo_name,c.item_name,c.item_code,sd.print_seq,c.item_seq "
				+ " from examinfo_charging_item ec,sample_exam_detail s,examResult_chargingItem er,sample_demo sd,charging_item  c "
				+ " where ec.isActive = 'Y' and ec.exam_num = s.exam_num  and ec.charging_item_code = c.item_code and s.sample_id = sd.id"
				+ " and ec.isActive = 'Y' and s.sample_barcode=er.bar_code and er.charging_item_code = ec.charging_item_code "
				+ " and er.result_type = 'sample' and ec.change_item != 'C' and ec.pay_status != 'M' and ec.center_num = '"+center_num+"' "
				+ " and ec.exam_num in ('"+ids.replaceAll(",", "','")+"') order by s.sample_id,sd.print_seq,c.item_seq";
		List<SampleExamDetailDTO> list = this.jqm.getList(sql, SampleExamDetailDTO.class);
		return list;
	}

	@Override
	@SuppressWarnings("unchecked")
	public void saveTeamSampleListItem(SamplingDepartmentModel model, UserDTO user) throws ServiceException {
		String[] examinfo_nums = model.getExaminfo_ids().split(",");
		String[] ids = model.getOld_sampleIds().split(",");
		for (int i = 0; i < examinfo_nums.length; i++) {
			List<SampleExamDetail> newSampleExamDetails = this.qm.find("from SampleExamDetail s where s.exam_num = "+examinfo_nums[i]+" and s.sample_id = "+model.getSampleId()+" and s.status = 'W'");
			for (int j = 0; j < ids.length; j++) {
				List<SampleExamDetail> oldSampleExamDetails = this.qm.find("from SampleExamDetail s where s.exam_num = "+examinfo_nums[i]+" and s.sample_id = "+ids[j]+" and s.status = 'W'");
				if(newSampleExamDetails.size() > 0 && oldSampleExamDetails.size() > 0){
					this.pm.remove(oldSampleExamDetails.get(0));
					List<ExamResultChargingItem> resultItemList = this.qm.find("from ExamResultChargingItem where exam_id = "
							+ oldSampleExamDetails.get(0).getId() +" and result_type = '"+CommSet.lis_sample+"'");
					for (ExamResultChargingItem item : resultItemList) {
						item.setExam_id(newSampleExamDetails.get(0).getId());
						this.pm.update(item);
					}
				}
			}
		}
	}

	@Override
	public List<SampleExamDetailDTO> getDepPrintSanpleItem(String exam_num, long dep_id, String center_num) throws ServiceException {
		String sql = "select (case when (sd.BarCode_Class = 1 and (se.is_binding is null or se.is_binding = 0)) then '' else se.sample_barcode end) "
				+ "as sample_barcode,se.id,se.status,(case when se.is_binding is null then 0 else se.is_binding end) as is_binding,se.sample_id,sd.demo_name,"
				+ "sd.BarCode_Class,ci.item_name,ci.item_code,ci.id as item_id,sd.demo_color,sd.demo_indicator,ec.is_application,se.print_num,sd.print_dep,"
				+ "'J' as dep_type,d.dep_name from exam_info e,sample_exam_detail se,sample_demo sd,examinfo_charging_item ec,charging_item ci,"
				+ "examResult_chargingItem er,department_dep d where e.exam_num = se.exam_num and se.sample_barcode=er.bar_code "
				+ "and er.charging_item_code = ec.charging_item_code "
				+ "and er.result_type = 'sample' and ci.dep_id = d.id and se.sample_id = sd.id and ci.item_code = ec.charging_item_code and e.exam_num = ec.exam_num and "
				+ "ec.isActive = 'Y' and ec.pay_status != 'M' and ec.center_num = '"+center_num+"' and (e.is_after_pay = 'Y' or ec.pay_status != 'N') "
				+ "and e.exam_num = '"+exam_num+"' and sd.print_dep = "+dep_id
				+ " union all "
				+ "select p.pacs_req_code sample_barcode,p.id,p.sample_status,'' as is_binding,p.examinfo_sampleId,sd.demo_name,"
				+ "sd.BarCode_Class,ci.item_name,ci.item_code,ci.id as item_id,sd.demo_color,sd.demo_indicator,ec.is_application,0 as print_num,sd.print_dep,"
				+ "'Y' as dep_type,d.dep_name from exam_info e,pacs_summary p,sample_demo sd,examinfo_charging_item ec,charging_item ci,pacs_detail ps,"
				+ "department_dep d where e.exam_num = p.examinfo_num and p.pacs_req_code = ps.pacs_req_code and ps.chargingItem_num = ci.item_code and ci.dep_id = d.id "
				+ "and p.examinfo_sampleId = sd.id and ci.item_code = ec.charging_item_code and e.exam_num = ec.exam_num and ec.isActive = 'Y' and ec.pay_status != 'M' and ec.center_num = '"+center_num+"' "
				+ "and (e.is_after_pay = 'Y' or ec.pay_status != 'N') and e.exam_num = '"+exam_num+"' and sd.print_dep = "+ dep_id+" order by se.sample_id";
		
		List<SampleExamDetailDTO> list = this.jqm.getList(sql, SampleExamDetailDTO.class);
		return list;
	}

	@Override
	public void saveDepSampleItem(SamplingDepartmentModel model, UserDTO user) throws ServiceException {
		List<SampleExamDetailDTO> listDto = model.getSampleExamDetailList();
		
		for(SampleExamDetailDTO sampl : listDto){
			if("J".equals(sampl.getDep_type())){
				SampleExamDetail sampleExamDetail = (SampleExamDetail) this.qm.load(SampleExamDetail.class, sampl.getId());
				
//				sampleExamDetail.setSample_barcode(sampl.getSample_barcode());
				sampleExamDetail.setIs_binding(1);
				sampleExamDetail.setStatus("Y");
				sampleExamDetail.setCheck_id(user.getUserid());
				sampleExamDetail.setCheck_doctor(user.getName());
				sampleExamDetail.setCheck_date(DateTimeUtil.parse());
				
				this.pm.update(sampleExamDetail);
			}else if("Y".equals(sampl.getDep_type())){
				PacsSummary pacsSummary = (PacsSummary) this.qm.load(PacsSummary.class, sampl.getId());
				
				pacsSummary.setSample_status("Y");
				pacsSummary.setSample_doc_id(user.getUserid());
				pacsSummary.setSample_date(DateTimeUtil.parse());
				
				this.pm.update(pacsSummary);
			}
		}
	}
	
	@Override
	public String canlSampleLisPacsStatus(long id, String type, UserDTO user) throws ServiceException {
		if("J".equals(type)){
			SampleExamDetail sampleExamDetail = (SampleExamDetail) this.qm.load(SampleExamDetail.class, id);
			//sampleExamDetail.setBarcode_back("");
			sampleExamDetail.setStatus("W");
			sampleExamDetail.setCheck_id(0);
			sampleExamDetail.setCheck_doctor(null);
			sampleExamDetail.setCheck_date(null);
			this.pm.update(sampleExamDetail);
		}else if("Y".equals(type)){
			PacsSummary pacsSummary = (PacsSummary) this.qm.load(PacsSummary.class, id);
			//pacsSummary.setBarcode_back("");
			pacsSummary.setSample_status("N");
			pacsSummary.setSample_doc_id(0);
			pacsSummary.setSample_date(null);
			this.pm.update(pacsSummary);
		}
		return "ok-取消采样成功!";
	}
}
