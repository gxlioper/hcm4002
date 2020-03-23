package com.hjw.zyb.service.impl;

import java.util.List;

import com.hjw.util.DateTimeUtil;
import com.hjw.util.IsPrivateCenterConfig;
import com.hjw.wst.DTO.DataDictionaryDTO;
import com.hjw.wst.DTO.ExamInfoUserDTO;
import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.wst.DTO.UserDTO;
import com.hjw.zyb.DTO.ZybAskDiagnosisSampleDTO;
import com.hjw.zyb.DTO.ZybCareerInquisitionItemDTO;
import com.hjw.zyb.DTO.ZybInquisitionResultDTO;
import com.hjw.zyb.domain.ZybInquisitionResult;
import com.hjw.zyb.model.ZybInquisitionResultModel;
import com.hjw.zyb.service.ZybInquisitionResultService;
import com.synjones.framework.exception.ServiceException;
import com.synjones.framework.persistence.JdbcQueryManager;
import com.synjones.framework.persistence.PersistenceManager;
import com.synjones.framework.persistence.QueryManager;
import com.synjones.framework.support.PageSupport;

public class ZybInquisitionResultServiceImpl implements ZybInquisitionResultService{
	
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
	
	@Override
	public PageReturnDTO getExamInfoList(ZybInquisitionResultModel model, UserDTO user, int rows, int page,String sort,String order)
			throws ServiceException {
		String isprivateflag = IsPrivateCenterConfig.isprivateflag(user);
		int count = 0;
		String sql =" select distinct e.status,e.id,c.arch_num,dbo.fun_CharToStar(e.exam_num,'id_num',c.id_num,'"+isprivateflag+"') as id_num,"
				+ "e.exam_num,e.age,dbo.fun_CharToStar(e.exam_num,'name',c.user_name,'"+isprivateflag+"') as user_name,c.sex,"
				+ "dbo.fun_CharToStar(e.exam_num,'phone',e.phone,'"+isprivateflag+"') as phone,e.freeze, "
				+"convert(varchar(50),e.join_date,23) as join_date,e.exam_type,e.join_date as d, "
				+"(case when e.status = 'Z' then e.final_doctor else '' end) as final_doctor, "
				+"(case when es.approve_status = 'A' then u.chi_name else '' end) as check_doctor, "
				+"dbo.GetExamSetNameByExamID(e.exam_num) as set_name,v.com_name as company, "
				+"ec.exam_status,e.apptype "
				+"from customer_info c,exam_info e "
				   + " left join company_info v on v.id=e.company_id "
				+"left join exam_summary es on e.exam_num = es.exam_num   and  es.center_num = '"+user.getCenter_num()+"'  "
				+"left join user_usr u on u.id = es.check_doc ,examinfo_charging_item ec,quest_dict_rec q "
				+"where e.customer_id = c.id and ec.exam_num = e.exam_num and ec.charging_item_code = q.charging_item_code and e.is_Active = 'Y' and e.apptype='2' ";
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
		if (model.getTime1() != null && !"".equals(model.getTime1())) {// 体检开始日期
			sql += " and e.join_date >= '" + model.getTime1() + " 00:00:00.000'";
			count ++;
		} 
		if (model.getTime2() != null && !"".equals(model.getTime2())) {// 体检结束日期
			sql += " and e.join_date < '" + model.getTime2() + " 23:59:59.999'";
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
		if (model.getStatus() != null && !"".equals(model.getStatus())){
			sql += " and e.status = '"+model.getStatus()+"'";
		}
		if (model.getExam_status() != null && !"".equals(model.getExam_status())){
			sql += " and ec.exam_status = '"+model.getExam_status()+"'";
		}
		if(model.getDoctor_name() != null && !"".equals(model.getDoctor_name())){
			sql += " and ec.exam_doctor_name = '"+model.getDoctor_name()+"'";
			count ++;
		}
		if (model.getExam_date1() != null && !"".equals(model.getExam_date1())) {// 检查开始日期
			sql += " and ec.exam_date >= '" + model.getExam_date1() + " 00:00:00.000'";
			count ++;
		} 
		if (model.getExam_date2() != null && !"".equals(model.getExam_date2())) {// 检查结束日期
			sql += " and ec.exam_date < '" + model.getExam_date2() + " 23:59:59.999'";
			count ++;
		}
		if(model.getCustomer_type()!=null && !"".equals(model.getCustomer_type())){
			sql += " and customer_type ='" + model.getCustomer_type() + "'";
			count ++;
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
	public List<ZybAskDiagnosisSampleDTO> getZybInquisitionList(long examinfo_id) throws ServiceException {
		String sql = "select case when d.data_code_children = 'ZYJKJC' then '1' when d.data_code_children = 'FSJKJC' then '2' "
				+ "else '0' end data_code_children,c.sex as remark from exam_info e,customer_info c,data_dictionary d where e.customer_type = d.id "
				+ "and c.id = e.customer_id and e.id = " + examinfo_id;
		List<DataDictionaryDTO> list = this.jqm.getList(sql, DataDictionaryDTO.class);
		if(list.size() == 0){
			return null;
		}else{
			String sql1 = "select za.id,za.name,za.sub_name,case when zi.result is null then za.temp_content else zi.result end as temp_content "
					+ "from zyb_ask_diagnosis_sample za left join zyb_inquisition_result zi on za.id = zi.item_id and zi.item_type = '1' "
					+ "and zi.examinfo_id = "+examinfo_id+" where za.type = "+list.get(0).getData_code_children()+" and za.is_active = 'Y'  ";
					if("男".equals(list.get(0).getRemark())){
						sql1+=" AND  za.sex  in('0','1') ";
					} else if("女".equals(list.get(0).getRemark())){
						sql1+=" AND  za.sex  in('0','2') ";
					} else {//其他
						sql1+=" AND  za.sex='0' ";
					}
						sql1+="  order by za.seq_code  ";
			List<ZybAskDiagnosisSampleDTO> list1 = this.jqm.getList(sql1, ZybAskDiagnosisSampleDTO.class);
			
			if("1".equals(list.get(0).getData_code_children())){
				String sql2 = "";
				if("男".equals(list.get(0).getRemark())){//  <> 2
					sql2 = "select zc.item_code,zc.item_name,zi.result from zyb_career_inquisition_item zc left join "
							+ "zyb_inquisition_result zi on zc.item_code = zi.item_id and zi.item_type = '2' and "
							+ "zi.examinfo_id = "+examinfo_id+" where zc.isshow = '0' and zc.sex <> '2'";
				}else{//<> 1
					sql2 = "select zc.item_code,zc.item_name,zi.result from zyb_career_inquisition_item zc left join "
							+ "zyb_inquisition_result zi on zc.item_code = zi.item_id and zi.item_type = '2' and "
							+ "zi.examinfo_id = "+examinfo_id+" where zc.isshow = '0' and zc.sex <> '1'";
				}
				List<ZybCareerInquisitionItemDTO> listitem = this.jqm.getList(sql2, ZybCareerInquisitionItemDTO.class);
				ZybAskDiagnosisSampleDTO inquisition = new ZybAskDiagnosisSampleDTO();
				
				inquisition.setName("症状");
				inquisition.setCareerItemList(listitem);
				list1.add(inquisition);
			}
			return list1;
		}
	}

	@Override
	public String saveZybInquisitionList(ZybInquisitionResultModel model, UserDTO user) throws ServiceException {
		
		List<ZybInquisitionResult> list = this.qm.find("from ZybInquisitionResult z where z.examinfo_id = " + model.getExaminfo_id());
		for (ZybInquisitionResult zybInquisitionResult : list) {
			this.pm.remove(zybInquisitionResult);
		}
		
		List<ZybInquisitionResultDTO> resultList = model.getInquisitionResultList();
		for (ZybInquisitionResultDTO zybInquisitionResultDTO : resultList) {
			ZybInquisitionResult zybResult = new ZybInquisitionResult();
			zybResult.setExaminfo_id(zybInquisitionResultDTO.getExaminfo_id());
			zybResult.setItem_id(zybInquisitionResultDTO.getItem_id());
			zybResult.setItem_type(zybInquisitionResultDTO.getItem_type());
			zybResult.setResult(zybInquisitionResultDTO.getResult());
			zybResult.setExam_doctor(user.getName());
			zybResult.setExam_date(DateTimeUtil.parse());
			zybResult.setCreater(user.getUserid());
			zybResult.setCreate_time(DateTimeUtil.parse());
			zybResult.setExam_num(zybInquisitionResultDTO.getExam_num());
			this.pm.save(zybResult);
		}
		return "保存成功!";
	}

	@Override
	public ExamInfoUserDTO getExaminfoById(long examinfo_id) throws ServiceException {
		String sql =" select c.id as c_id,ty.type_name,e.id,c.arch_num,c.id_num,e.exam_num,e.age,c.user_name,c.sex,"
				+ " e.phone,e.past_medical_history,e.join_date,convert(varchar(50),e.join_date,23) as join_date,e.picture_path, "
				+ " dbo.GetExamSetNameByExamID(e.exam_num) as set_name,v.com_name as company,dd.data_code_children as exam_types"
				+ " from customer_info c,exam_info e "
				   + " left join company_info v on v.id=e.company_id "
				+ " left join customer_type ty on "
				+ " e.customer_type_id=ty.id left join data_dictionary dd on e.customer_type = dd.id "
				+ " where e.customer_id = c.id and e.is_Active = 'Y' and e.id = " + examinfo_id;  
       List<ExamInfoUserDTO> li = this.jqm.getList(sql,ExamInfoUserDTO.class);
       if(li.size() > 0){
    	   return li.get(0);
       }
       return null;
	}
}
