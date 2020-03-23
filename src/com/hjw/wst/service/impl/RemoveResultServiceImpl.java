package com.hjw.wst.service.impl;

import java.util.List;

import com.hjw.wst.DTO.ExaminfoChargingItemDTO;
import com.hjw.wst.domain.CommonExamDetail;
import com.hjw.wst.domain.ExamInfo;
import com.hjw.wst.domain.ExamResultDetail;
import com.hjw.wst.domain.ExamdepResult;
import com.hjw.wst.domain.ExaminfoChargingItem;
import com.hjw.wst.domain.ViewExamDetail;
import com.hjw.wst.domain.ViewExamImage;
import com.hjw.wst.domain.ViewExamItem;
import com.hjw.wst.model.RemoveResulModel;
import com.hjw.wst.service.RemoveResultService;
import com.synjones.framework.exception.ServiceException;
import com.synjones.framework.persistence.JdbcQueryManager;
import com.synjones.framework.persistence.PersistenceManager;
import com.synjones.framework.persistence.QueryManager;

public class RemoveResultServiceImpl implements RemoveResultService {
	private QueryManager qm;
	private JdbcQueryManager jqm;
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
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ExaminfoChargingItemDTO> getCheckedExamChargingItem(String exam_num, String center_num) throws ServiceException {
		
		String sql =" select d.id dep_id,d.dep_name,ec.charge_item_id,c.item_code,c.item_name,ec.id,ec.exam_doctor_name,ec.exam_status,ec.pay_status,"
				   +" convert(varchar(50),ec.exam_date,20) exam_date,c.dep_category from examinfo_charging_item ec,exam_info e,charging_item c,department_dep d"
				   +" where ec.exam_num = e.exam_num and ec.charging_item_code = c.item_code and c.dep_id = d.id and e.exam_num = '"+exam_num+"'"
				   +" and ec.isActive = 'Y' and c.dep_category in(17,131,21) and ec.exam_status = 'Y' and ec.pay_status <> 'M' and ec.center_num = '"+center_num+"' order by d.seq_code,c.item_seq";
		List<ExaminfoChargingItemDTO> list = this.jqm.getList(sql, ExaminfoChargingItemDTO.class);
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public String removeResult(RemoveResulModel model) throws ServiceException {
		
		List<ExaminfoChargingItemDTO> list = model.getItemList();
		
		for(ExaminfoChargingItemDTO examItem : list){
			ExaminfoChargingItem examCItem = (ExaminfoChargingItem) this.qm.load(ExaminfoChargingItem.class, examItem.getId());
			
			examCItem.setExam_date(null);
			examCItem.setExam_status("N");
			examCItem.setExam_doctor_id(0);
			examCItem.setExam_doctor_name(null);
			this.pm.update(examCItem);
			
			if("17".equals(examItem.getDep_category())){//普通科室
				String sql = "from CommonExamDetail c where c.exam_num = '"+model.getExam_num()+"' and c.item_code in "
						   + "(select ce.item_code from ChargingItemExamItem ce where ce.charging_item_code = '"+examItem.getItem_code()+"')";
				List<CommonExamDetail> comlist = this.qm.find(sql);
				
				for(CommonExamDetail commonExamDetail : comlist){
					this.pm.remove(commonExamDetail);
				}
				
				String sql2 = " from CommonExamDetail c where c.exam_num = '"+model.getExam_num()+"' and "
						    + " c.item_code in (select ce.item_code from ChargingItemExamItem ce where ce.charging_item_code in "
						    + " ( select ci.item_code from ChargingItem ci where ci.dep_id = "+examItem.getDep_id()+"))";
				List<CommonExamDetail> comlist2 = this.qm.find(sql2);
				if(comlist2.size() == 0){
					String sql3 = "from ExamdepResult e where e.exam_num = '"+model.getExam_num()+"' and e.dep_id = "+examItem.getDep_id();
					List<ExamdepResult> examdep = this.qm.find(sql3);
					if(examdep.size() > 0){
						this.pm.remove(examdep.get(0));
					}
				}
			}else if("21".equals(examItem.getDep_category())){//影像科室
				String sql = " from ViewExamDetail v where v.pacs_req_code = (select p.pacs_req_code from PacsSummary p "
						   + " where p.examinfo_num = '"+model.getExam_num()+"' and p.examinfo_sampleId in (select pd.examinfo_sampleId "
						   + " from Pacsdetail pd where pd.summary_id = p.id and pd.chargingItem_num = '"+examItem.getItem_code()+"'))";
				List<ViewExamDetail> viewList = this.qm.find(sql);
				if(viewList.size() > 0){
					ViewExamDetail viewExamDetail = viewList.get(0);
					
					String sql2 = "from ViewExamItem v where v.view_exam_detail_id = "+viewExamDetail.getId();
					
					List<ViewExamItem> viewItemList = this.qm.find(sql2);
					for(ViewExamItem viewExamItem : viewItemList){
						this.pm.remove(viewExamItem);
					}
					
					String sql3 = "from ViewExamImage v where v.view_exam_id = "+viewExamDetail.getId();
					List<ViewExamImage> viewImageList = this.qm.find(sql3);
					for(ViewExamImage viewExamImage:viewImageList){
						this.pm.remove(viewExamImage);
					}
					
					this.pm.remove(viewExamDetail);
				}
			}else if("131".equals(examItem.getDep_category())){//检验科
				String sql = " from ExamResultDetail es where es.exam_num = '"+model.getExam_num()+"' and es.item_code in "
						   + " (select ce.item_code from ChargingItemExamItem ce where ce.charging_item_code = '"+examItem.getItem_code()+"')";
				
				List<ExamResultDetail> examresultList = this.qm.find(sql);
				for(ExamResultDetail examResultDetail : examresultList){
					this.pm.remove(examResultDetail);
				}
			}
		}
		return "清除成功!";
	}

	@Override
	public ExamInfo getExamInfo(long id) throws ServiceException {
		ExamInfo examInfo = (ExamInfo) this.qm.load(ExamInfo.class, id);
		return examInfo;
	}
}
