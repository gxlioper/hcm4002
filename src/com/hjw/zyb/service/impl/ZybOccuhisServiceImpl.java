package com.hjw.zyb.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;

import com.hjw.util.DateTimeUtil;
import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.zyb.DTO.ExaminfCustomerDTO;
import com.hjw.zyb.DTO.ZybDiseaseHistoryDTO;
import com.hjw.zyb.DTO.ZybOccuHisDTO;
import com.hjw.zyb.DTO.ZybOccuindustryDTO;
import com.hjw.zyb.domain.ZybDiseaseHistory;
import com.hjw.zyb.domain.ZybOccuHis;
import com.hjw.zyb.model.ZybDiseaseHistoryModel;
import com.hjw.zyb.model.ZybImpCustomerInfoModel;
import com.hjw.zyb.service.ZybOccuhisService;
import com.synjones.framework.exception.ServiceException;
import com.synjones.framework.persistence.JdbcQueryManager;
import com.synjones.framework.persistence.PersistenceManager;
import com.synjones.framework.persistence.QueryManager;
import com.synjones.framework.support.PageSupport;

public class ZybOccuhisServiceImpl implements  ZybOccuhisService{

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
	public String saveOccuhis(List<ZybOccuHis> li,long userid) throws ServiceException {
		String st = "";
		for (int i = 0; i < li.size(); i++) {
			if(i==li.size()-1){
				st+="'"+li.get(i).getId_num()+"'";
			} else {
				st+="'"+li.get(i).getId_num()+"',";
			}
		}
		String sql = "SELECT c.id_num,e.exam_num,c.arch_num FROM   customer_info  c,exam_info  e"
				+" where   c.is_Active='Y'  and   e.is_Active='Y' and e.apptype=2  and  id_num in("+st+")  and   c.id=e.customer_id   order  by   c.id  desc, e.create_time desc ";
		List<ExaminfCustomerDTO>  eli = this.jqm.getList(sql, ExaminfCustomerDTO.class);
		
		List<ZybOccuHis> oli = new ArrayList<ZybOccuHis>();
		ZybOccuHis z = null;
		String examnum = "";
		for(int o = 0; o < li.size(); o ++){
			for(int j=0; j < eli.size() ; j++){
					if(li.get(o).getId_num().equals(eli.get(j).getId_num())){
						z = new ZybOccuHis();
						 z.setId_num(eli.get(j).getId_num());
						 z.setArch_num(eli.get(j).getArch_num());
						 z.setExam_num(eli.get(j).getExam_num());
						 z.setCompany(li.get(o).getCompany());
						 z.setWorkshop(li.get(o).getWorkshop());
						 z.setWorktype(li.get(o).getWorktype());
						 z.setStartdate(li.get(o).getStartdate());
						 z.setEnddate(li.get(o).getEnddate());
						 z.setMeasure(li.get(o).getMeasure());
						 z.setHarmname(li.get(o).getHarmname());
						 z.setConcentrations(li.get(o).getConcentrations());
						 z.setIsradiation(li.get(o).getIsradiation());
						 z.setCreater(userid);
						 z.setCreate_date(DateTimeUtil.getDate3());
						 z.setRadiation(li.get(o).getRadiation());
						 z.setMan_haur(li.get(o).getMan_haur());
						 z.setCumulative_exposure(li.get(o).getCumulative_exposure());
						 z.setHistory_excessive(li.get(o).getHistory_excessive());
						 z.setRemark(li.get(o).getRemark());
						 this.pm.save(z);
						 examnum+="'"+z.getExam_num()+"',";
						 break;
					}
			}
		}
		if(examnum.length()>1) {
			examnum = examnum.substring(0,examnum.length()-1);
		}
		return examnum;
	}

	@Override
	public String saveDiseaseHistory(List<ZybDiseaseHistory> li, long userid)
			throws ServiceException {
		String st = "";
		for (int i = 0; i < li.size(); i++) {
			if(i==li.size()-1){
				st+="'"+li.get(i).getId_num()+"'";
			} else {
				st+="'"+li.get(i).getId_num()+"',";
			}
		}
		String sql = "SELECT c.id_num,e.exam_num,c.arch_num FROM   customer_info  c,exam_info  e"
				+" where   c.is_Active='Y'  and   e.is_Active='Y'  and  id_num in("+st+")  and   c.id=e.customer_id   order  by   c.id  desc  ";
		List<ExaminfCustomerDTO>  eli = this.jqm.getList(sql, ExaminfCustomerDTO.class);
		
		
		List<ZybDiseaseHistory> oli = new ArrayList<ZybDiseaseHistory>();
		ZybDiseaseHistory z = null;
		String examnum = "";
		for(int o = 0; o < li.size(); o ++){
			for(int j=0; j < eli.size() ; j++){
					if(li.get(o).getId_num().equals(eli.get(j).getId_num())){
						z = new ZybDiseaseHistory();
						 z.setId_num(eli.get(j).getId_num());
						 z.setArch_num(eli.get(j).getArch_num());
						 z.setExam_num(eli.get(j).getExam_num());
						 z.setDiseases(li.get(o).getDiseases());
						 z.setDiagnosisdate(li.get(o).getDiagnosisdate());
						 z.setDiagnosiscom(li.get(o).getDiagnosiscom());
						 z.setDiagnosisnotice(li.get(o).getDiagnosisnotice());
						 z.setDiseasereturn(li.get(o).getDiseasereturn());
						 z.setRemark1(li.get(o).getRemark1());
						 z.setRemark2(li.get(o).getRemark2());
						 z.setRemark3(li.get(o).getRemark3());
						 z.setCreater(userid);
						 z.setCreate_time(DateTimeUtil.parse());
						 this.pm.save(z);
						 examnum+="'"+z.getExam_num()+"',";
						 break;
					}
			}
		}
		examnum = examnum.substring(0,examnum.length()-1);
		return examnum;
	}

	@Override
	public PageReturnDTO getDiseaseHistoryTable(ZybImpCustomerInfoModel model,
			int page, int pageSize) throws ServiceException {
		String sql = " SELECT z.Id,z.diseases,z.diagnosiscom,z.diagnosisdate,z.diagnosisnotice,z.diseasereturn FROM   Zyb_disease_History  z"
				+ "  where   z.exam_num = '"+model.getExam_num()+"'";
		PageSupport map = this.jqm.getList(sql, page, pageSize,ZybDiseaseHistoryDTO.class);
		PageReturnDTO webrole = new PageReturnDTO();
		webrole.setPage(page);
		webrole.setRp(pageSize);
		if ((map != null) && (map.getList() != null)) {
			webrole.setTotal(map.getRecTotal());
			webrole.setRows(map.getList());
		}
		return webrole;
	}

	@Override
	public void saveDiseaseHistoryadd(ZybDiseaseHistory di)
			throws ServiceException {
		this.pm.save(di);
		
	}

	@Override
	public void deleteDiseaseHistory(String ids) throws ServiceException {
		if(!"".equals(ids)){
			String hql = " FROM   ZybDiseaseHistory   z   where    z.Id  in ("+ids+")";
			List<ZybDiseaseHistory> li = this.qm.find(hql);
			for (ZybDiseaseHistory zybDiseaseHistory : li) {
				this.pm.remove(zybDiseaseHistory);
			}
		}
	}
}
