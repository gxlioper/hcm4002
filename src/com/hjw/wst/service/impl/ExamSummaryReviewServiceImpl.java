package com.hjw.wst.service.impl;

import com.hjw.util.DateTimeUtil;
import com.hjw.wst.DTO.ExamSummaryReviewDTO;
import com.hjw.wst.DTO.ExaminfoChargingItemDTO;
import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.wst.DTO.UserDTO;
import com.hjw.wst.domain.CrmSmsSend;
import com.hjw.wst.domain.ExamInfo;
import com.hjw.wst.domain.ExamSummaryReview;
import com.hjw.wst.domain.ExamSummaryReviewItem;
import com.hjw.wst.model.ExamSummaryReviewModel;
import com.hjw.wst.service.ExamSummaryReviewService;
import com.synjones.framework.exception.ServiceException;
import com.synjones.framework.persistence.JdbcPersistenceManager;
import com.synjones.framework.persistence.JdbcQueryManager;
import com.synjones.framework.persistence.PersistenceManager;
import com.synjones.framework.persistence.QueryManager;
import com.synjones.framework.support.PageSupport;

import java.util.List;
import java.util.UUID;

public class ExamSummaryReviewServiceImpl implements ExamSummaryReviewService {

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

	@Override
	public String saveExamSummaryReview(ExamSummaryReviewModel model, UserDTO user,String apptype) throws ServiceException {
		if(model.getId() > 0){
			ExamSummaryReview examSummaryReview = (ExamSummaryReview) this.qm.load(ExamSummaryReview.class, model.getId());
			if("2".equals(examSummaryReview.getReview_status())){
				return "error-该体检人复查信息已通知，不能继续设定";
			}else if("3".equals(examSummaryReview.getReview_status())){
				return "error-该体检人复查信息已作废，不能继续设定";
			}else if("4".equals(examSummaryReview.getReview_status())){
				return "error-该体检人复查信息已取消，不能继续设定";
			}
			examSummaryReview.setReview_title(model.getReview_title());
			examSummaryReview.setReview_date(DateTimeUtil.parse0(model.getReview_date()));
			examSummaryReview.setReview_user(user.getUserid());
			examSummaryReview.setReview_time(DateTimeUtil.parse());
			this.pm.update(examSummaryReview);
		}else{
			ExamSummaryReview examSummaryReview = new ExamSummaryReview();
			examSummaryReview.setExam_num(model.getExam_num());
			examSummaryReview.setReview_title(model.getReview_title());
			examSummaryReview.setReview_date(DateTimeUtil.parse0(model.getReview_date()));
			examSummaryReview.setReview_user(user.getUserid());
			examSummaryReview.setReview_time(DateTimeUtil.parse());
			examSummaryReview.setReview_status("1");
            examSummaryReview.setApptype(apptype);
			this.pm.save(examSummaryReview);
		}
		List<ExamSummaryReviewItem> list = this.qm.find("from ExamSummaryReviewItem e where e.exam_num = '"+model.getExam_num()+"'");
		for(ExamSummaryReviewItem item : list){
			this.pm.remove(item);
		}
		if(model.getLi() != null && !"".equals(model.getLi())){
			String[] ids = model.getLi().split(",");
			for (int i = 0; i < ids.length; i++) {
				ExamSummaryReviewItem item = new ExamSummaryReviewItem();
				item.setExam_num(model.getExam_num());
				item.setCharging_item_id(Long.valueOf(ids[i]));
				item.setCreater(user.getUserid());
				item.setCreate_time(DateTimeUtil.parse());
				item.setApptype(apptype);
				this.pm.save(item);
			}
		}
		return "ok-复查设定成功!";
	}

	@Override
	public ExamSummaryReviewDTO getExamSummaryReview(String exam_num,String apptype) throws ServiceException {
		String sql = "select e.id,e.review_status,e.review_title,CONVERT(varchar(50),e.review_date,23) review_date,"
				+ "u.chi_name review_user,e.review_time,CONVERT(varchar(50),e.notice_time,23) notice_time,e.notice_type,"
				+ "u1.chi_name notice_user from exam_summary_review e left join user_usr u on u.id = e.review_user "
				+ "left join user_usr u1 on u1.id = e.notice_user where e.exam_num = '"+exam_num+"'  and e.apptype= '"+apptype+"'";
		List<ExamSummaryReviewDTO> list = this.jqm.getList(sql, ExamSummaryReviewDTO.class);
		if(list.size() > 0){
			return list.get(0);
		}
		return null;
	}

	@Override
	public PageReturnDTO getExamSummaryReviewList(ExamSummaryReviewModel model, int rows, int page, String sort,
                                                  String order) throws ServiceException {
		String sql = "select c.arch_num,c.sex,c.user_name,c.birthday,e.exam_num,e.age,e.phone,v.com_name company,e.join_date,"
				+ "r.review_status,r.review_title,CONVERT(varchar(50),r.review_date,23) review_date,u.chi_name review_user,"
				+ "r.review_time,r.notice_time,r.notice_type,u1.chi_name notice_user,r.id from exam_info e,customer_info c,"
				+ "v_exam_comp_batch_group v,exam_summary_review r left join user_usr u on u.id = r.review_user "
				+ "left join user_usr u1 on u1.id = r.notice_user where r.exam_num = e.exam_num and e.customer_id = c.id "
				+ "and e.id = v.id";
		int count = 0;
		if(model.getExam_num() != null && !"".equals(model.getExam_num())){
			count ++;
			sql += " and (e.exam_num = '"+model.getExam_num()+"' or c.arch_num = '"+model.getExam_num()+"')";
		}
		if(model.getTime1() != null && !"".equals(model.getTime1())){
			count ++;
			sql += " and e.join_date >='"+model.getTime1()+" 00:00:00.000'";
		}
		if(model.getTime2() != null && !"".equals(model.getTime2())){
			count ++;
			sql += " and e.join_date < '"+model.getTime2()+" 23:59:59.000'";
		}
		if(model.getTime3() != null && !"".equals(model.getTime3())){
			count ++;
			sql += " and r.review_date >='"+model.getTime3()+" 00:00:00.000'";
		}
		if(model.getTime4() != null && !"".equals(model.getTime4())){
			count ++;
			sql += " and r.review_date < '"+model.getTime4()+" 23:59:59.000'";
		}
		if(model.getReview_status() != null && !"".equals(model.getReview_status())){
			sql += " and r.review_status = '"+model.getReview_status()+"'";
		}
		if(model.getReview_title() != null && !"".equals(model.getReview_title())){
			sql += " and r.review_title like '%"+model.getReview_title()+"%'";
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
		PageSupport map = this.jqm.getList(sql, page, rows, ExamSummaryReviewDTO.class);
		
		if ((map != null) && (map.getList() != null)) {
			webrole.setTotal(map.getRecTotal());
			webrole.setRows(map.getList());
		}
		return webrole;
	}

//	@Override
//	public String updateExamSummaryReview(ExamSummaryReviewModel model, UserDTO user) throws ServiceException {
//		String str = "";
//		ExamSummaryReview examSummaryReview = (ExamSummaryReview) this.qm.load(ExamSummaryReview.class, model.getId());
//		examSummaryReview.setReview_status(model.getReview_status());
//		examSummaryReview.setNotice_user(user.getUserid());
//		examSummaryReview.setNotice_time(DateTimeUtil.parse());
//		examSummaryReview.setNotice_type(model.getNotice_type());
//		this.pm.update(examSummaryReview);
//		if("3".equals(model.getReview_status())){//作废复查信息
//			str = "ok-作废复查成功!";
//		}else if("2".equals(model.getReview_status())){//通知复查信息
//			if("1".equals(model.getNotice_type())){
//				JSONObject ob = JSONObject.fromObject(model.getLi());
//				ExamInfo examInfo = (ExamInfo) JSONObject.toBean(ob,ExamInfo.class);
//				CrmSmsSend s = new CrmSmsSend();
//				UUID uuid = UUID.randomUUID();
//				String uid = uuid.toString().replace("-", "");
//				if(examInfo.getExam_num()!=null && !"".equals(examInfo.getExam_num())){
//					s.setUser_type(1);
//				} else {
//					s.setUser_type(0);
//				}
//				String hql = " FROM ExamInfo   where   exam_num = '"+examInfo.getExam_num()+" ' ";
//				List<ExamInfo> ex = this.qm.find(hql);
//				s.setSend_user(user.getUserid());
//				s.setSms_note(model.getSms_note());
//				s.setSms_phone(examInfo.getPhone());
//				s.setSms_batch(uid);
//				s.setSms_type(1);
//				s.setArch_num(model.getArch_num());
//				s.setUser_id(ex.get(0).getId());
//				s.setSms_date(DateTimeUtil.parse());
//				this.pm.save(s);
//				
//				return uid;
//			} else {
//				str = "ok-通知复查成功!";
//			}
//		}else{
//			str = "error-不存在的操作方式!";
//		}
//		return str;
//	}
	public String updateExamSummaryReview(String review_status, String notice_type, String sms_note, ExamSummaryReviewDTO esr, UserDTO user) throws ServiceException {
		String str = "";
		ExamSummaryReview examSummaryReview = (ExamSummaryReview) this.qm.load(ExamSummaryReview.class, esr.getId());
		examSummaryReview.setReview_status(review_status);
		examSummaryReview.setNotice_user(user.getUserid());
		examSummaryReview.setNotice_time(DateTimeUtil.parse());
		examSummaryReview.setNotice_type(notice_type);
		this.pm.update(examSummaryReview);
		if("3".equals(review_status)){//作废复查信息
			str = "ok-作废复查成功!";
		}else if("2".equals(review_status)){//通知复查信息
			if("1".equals(notice_type)){
//				JSONObject ob = JSONObject.fromObject(model.getLi());
//				ExamInfo examInfo = (ExamInfo) JSONObject.toBean(ob,ExamInfo.class);
				CrmSmsSend s = new CrmSmsSend();
				UUID uuid = UUID.randomUUID();
				String uid = uuid.toString().replace("-", "");
				if(esr.getExam_num()!=null && !"".equals(esr.getExam_num())){
					s.setUser_type(1);
				} else {
					s.setUser_type(0);
				}
				String hql = " FROM ExamInfo   where   exam_num = '"+esr.getExam_num()+" ' ";
				List<ExamInfo> ex = this.qm.find(hql);
				s.setSend_user(user.getUserid());
				s.setSms_note(sms_note);
				s.setSms_phone(esr.getPhone());
				s.setSms_batch(uid);
				s.setSms_type(1);
				s.setArch_num(esr.getArch_num());
				s.setUser_id(ex.get(0).getId());
				s.setSms_date(DateTimeUtil.parse());
				this.pm.save(s);
				
				return uid;
			} else {
				str = "ok-通知复查成功!";
			}
		}else{
			str = "error-不存在的操作方式!";
		}
		return str;
	}

	@Override
	public List<ExaminfoChargingItemDTO> getExamSummaryReviewItemList(String exam_num) throws ServiceException {
		String sql = "select a.id,a.charging_item_id charge_item_id,a.creater,a.create_time,"
				+ "b.dep_id,c.dep_name,b.item_code,b.item_name,b.item_type "
				+ "from exam_summary_review_item a,charging_item b,department_dep c  "
				+ "where a.charging_item_id=b.id and b.dep_id=c.id and b.isActive='Y' and a.exam_num = '"+exam_num+"' "
				+ "order by c.seq_code,b.item_seq desc";
		List<ExaminfoChargingItemDTO> list = this.jqm.getList(sql, ExaminfoChargingItemDTO.class);
		return list;
	}

	@Override
	public String saveCancelReview(ExamSummaryReviewModel model, UserDTO user) throws ServiceException {
		ExamSummaryReview examSummaryReview = (ExamSummaryReview) this.qm.load(ExamSummaryReview.class, model.getId());
		examSummaryReview.setReview_status(model.getReview_status());
		examSummaryReview.setNotice_user(user.getUserid());
		examSummaryReview.setNotice_time(DateTimeUtil.parse());
		examSummaryReview.setNotice_type(model.getNotice_type());
		this.pm.update(examSummaryReview);
		if(model.getReview_status().equals("1")){
			return "ok-恢复复查设定成功!";
		}else if(model.getReview_status().equals("4")){
			return "ok-取消复查设定成功!";
		}
		return null;
	}
}
