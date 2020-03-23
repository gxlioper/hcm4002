package com.hjw.wst.service.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.hsqldb.lib.StringUtil;

import com.hjw.util.DateTimeUtil;
import com.hjw.wst.DTO.ChargingItemDTO;
import com.hjw.wst.DTO.ExamSetDTO;
import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.wst.DTO.SetChargingItemDTO;
import com.hjw.wst.domain.ChargingItem;
import com.hjw.wst.domain.ExamSet;
import com.hjw.wst.domain.ExamSetType;
import com.hjw.wst.domain.SetChargingItem;
import com.hjw.wst.domain.WebSynchro;
import com.hjw.wst.model.ExamSetModel;
import com.hjw.wst.service.ChargingItemService;
import com.hjw.wst.service.CustomerInfoService;
import com.hjw.wst.service.ExamSetService;
import com.hjw.zyb.DTO.ZybOccuhazardClassDTO;
import com.hjw.zyb.DTO.ZybOccuhazardfactorsDTO;
import com.hjw.zyb.DTO.ZybOccuphyexaClassDTO;
import com.synjones.framework.exception.ServiceException;
import com.synjones.framework.persistence.JdbcPersistenceManager;
import com.synjones.framework.persistence.JdbcQueryManager;
import com.synjones.framework.persistence.PersistenceManager;
import com.synjones.framework.persistence.QueryManager;
import com.synjones.framework.support.PageSupport;

import net.sf.json.JSONArray;

/**
 * 
     * @Title:  火箭蛙体检管理系统   
     * @Package com.hjw.wst.service.impl   
     * @Description: 体检检查套餐 
     * @author:张瑞   
     * @date:   2016年10月10日 上午10:23:14   
     * @version V2.0.0.0
 */
public class ExamSetImpl implements ExamSetService{
	
	private QueryManager qm;
	private JdbcQueryManager jqm;
	private JdbcPersistenceManager jpm;
	private PersistenceManager pm;
	private ChargingItemService chargingItemService;
	private CustomerInfoService customerInfoService;

	
	public void setCustomerInfoService(CustomerInfoService customerInfoService) {
		this.customerInfoService = customerInfoService;
	}

	public ChargingItemService getChargingItemService() {
		return chargingItemService;
	}

	public void setChargingItemService(ChargingItemService chargingItemService) {
		this.chargingItemService = chargingItemService;
	}

	public QueryManager getQueryManager() {
		return qm;
	}

	public void setQueryManager(QueryManager qm) {
		this.qm = qm;
	}

	public JdbcQueryManager getJdbcQueryManager() {
		return jqm;
	}

	public void setJdbcQueryManager(JdbcQueryManager jqm) {
		this.jqm = jqm;
	}

	public JdbcPersistenceManager getJdbcPersistenceManager() {
		return jpm;
	}

	public void setJdbcPersistenceManager(JdbcPersistenceManager jpm) {
		this.jpm = jpm;
	}

	public PersistenceManager getPersistenceManager() {
		return pm;
	}

	public void setPersistenceManager(PersistenceManager pm) {
		this.pm = pm;
	}

	@Override
	public PageReturnDTO queryExamSet(ExamSetDTO exam,int pageSize,int page,String intss,String centernum) throws ServiceException {
		String sql="SELECT  exam_set.price,exam_set.id,exam_set.company_id,exam_set.set_num,exam_set.set_name,exam_set.set_pinyin,"
				+ "exam_set.sex,exam_set.set_discount,exam_set.set_amount,exam_set.survey_minScore,"
				+ "exam_set.survey_maxScore,exam_set.disease_name,exam_set.is_Active,exam_set.creater,"
				+ "exam_set.create_time,exam_set.updater,exam_set.update_time, exam_set.isSynchro ,company_info.com_name,"
				+ "uc.chi_name  creater_name,uu.chi_name  as  update_name    FROM exam_set"
				+ "  LEFT JOIN company_info ON company_info.id=exam_set.company_id"
				+ "  LEFT JOIN user_usr uc ON  exam_set.creater=uc.id"
				+ "  LEFT JOIN user_usr uu ON exam_set.updater=uu.id "
				+ "  where 1 = 1    and  exam_set.center_num='"+centernum+"' and    (app_type='0'  or   app_type='"+intss+"')";
				
				if (exam.getStartStop()!= null && !"".equals(exam.getStartStop())) {//是否启停
					if(exam.getStartStop().length()==1) {
						sql += " and exam_set.is_Active IN ('"+exam.getStartStop()+"') ";
					}else {
						sql += " and exam_set.is_Active IN ('Y','N') ";
					}
				}
		
				if(exam.getSet_name()!=null&&!exam.getSet_name().equals("")){
			sql+=" and  exam_set.set_name like '%"+exam.getSet_name()+"%'";		
				}
				if(null!=exam.getSex()&&!"".equals(exam.getSex())){
			sql+="  and   exam_set.sex='"+exam.getSex()+"'";		
				}
				if(exam.getUpdate_time()!=null&&!exam.getUpdate_time().equals("")){
			sql+=" and exam_set.update_time>='"+exam.getUpdate_time()+"'";			
				}
				if(exam.getUpdate_times()!=null&&!exam.getUpdate_times().equals("")){
			sql+=" and exam_set.update_time<='"+exam.getUpdate_times()+"'";				
				}
				
		    sql+= "  ORDER BY  exam_set.id  DESC";
		PageSupport map=jqm.getList(sql,pageSize,page,ExamSetDTO.class);
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
	public void deleteExamSet(String id,String center_num) throws ServiceException {
		Connection connection = null;
		try {
			connection = this.jqm.getConnection();
			String sql="";
			sql="select COUNT(*) as counts from exam_set a,exam_set_type b "
					+ "where a.exam_set_type=b.id and a.center_num='"+center_num+"' and b.set_class='2' and a.id in ("+id+")";
			ResultSet rs = connection.createStatement().executeQuery(sql);
			int counts=0;
			if (rs.next()) {
				String con = rs.getString("counts");
				if ((con == null) || ("null".equals(con))) {
					counts=0;
				} else {
					counts=rs.getInt("counts");
				}
			}
			rs.close();
			if(counts>0)
			{
				String synstr="delete web_synchro where dataid ='"+id+"' and datatype='2'";
				connection.createStatement().executeUpdate(synstr);
			    synstr="insert web_synchro(datatype,dataid,create_time,synchro,synchrodate) values ('2','"+id+"','" + DateTimeUtil.getDateTime() + "','0','" + DateTimeUtil.getDateTime() + "')";
			    connection.createStatement().executeUpdate(synstr);
			}			
			sql="update exam_set set is_Active='N' where id in("+id+")";
			String hql = "DELETE FROM set_occuhazardfactors WHERE set_id=" + id;
			this.jpm.executeSql(hql);
			connection.createStatement().executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try {
				if(connection!=null){
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public ExamSet addExamSet(ExamSet exa) throws ServiceException {
		this.pm.save(exa);
		ExamSetType est= new ExamSetType();
		est = (ExamSetType) this.qm.get(ExamSetType.class,Long.valueOf(exa.getExam_set_type()).longValue());
		if((est!=null)&&(est.getSet_class()==2)){
			String hql=" from WebSynchro where dataid='"+exa.getId()+"' and datatype='2' ";
			List<WebSynchro> list=this.qm.find(hql);
			if(list.size()!=0){
				WebSynchro ws=list.get(0);
				this.pm.remove(ws);
			}
			WebSynchro wsnew=new WebSynchro();
			wsnew.setCreate_time(DateTimeUtil.parse());
			wsnew.setDataid(exa.getId()+"");
			wsnew.setDatatype('2');
			wsnew.setSynchro('0');
			wsnew.setSynchrodate(DateTimeUtil.parse());
			this.pm.save(wsnew);
		}
		return exa;
	}

	@Override
	public void addSetChargingItem(SetChargingItem item) throws ServiceException{
		ChargingItem c = this.chargingItemService.findChargingItem(item.getCharging_item_id());
		if("2".equals(item.getApptype())) {
			if(1==c.getApp_type()) {
				item.setApptype(c.getApp_type()+"");
			} else {
				item.setApptype("2");
			}
		} 
		item.setCharging_item_code(c.getItem_code());
		this.pm.save(item);
	}

	@Override
	public ExamSet findExamSetId(long id) throws ServiceException {
		return (ExamSet) this.qm.get(ExamSet.class,id);
	}

	@Override
	public List<SetChargingItemDTO> getsetChargingItem(long id,String center_num) throws ServiceException {
		String sql="";
		Connection connection = null;
		List<SetChargingItemDTO> li = new ArrayList<SetChargingItemDTO>();
		try {
			sql+="SELECT sci.ischosen,charging_item.id,sci.itemnum,charging_item.item_code,charging_item.item_name,charging_item.item_type,sci.item_amount  as  amount,"
				+"  department_dep.dep_name,sci.amount  as   item_amount,sci.discount,charging_item.item_seq FROM department_dep,set_charging_item sci"
				+" LEFT JOIN charging_item ON sci.charging_item_code=charging_item.item_code  and  charging_item.isActive='Y' "
				+" LEFT JOIN exam_set eset ON sci.exam_set_id=eset.id"
				+" WHERE charging_item.dep_id=department_dep.id and eset.center_num='"+center_num+"' and sci.exam_set_id='"+id+"'"
				+" order by  department_dep.id asc,charging_item.item_seq  desc";
			connection = this.jqm.getConnection();
			ResultSet rs = connection.createStatement().executeQuery(sql);
			while (rs.next()) {
				SetChargingItemDTO dto = new SetChargingItemDTO();
				dto.setId(Integer.parseInt(rs.getString("id")));
				dto.setItem_code(rs.getString("item_code"));//编码
				dto.setItem_name(rs.getString("item_name"));//名称
				dto.setD_name(rs.getString("dep_name"));//科室名称
				dto.setAmount(rs.getDouble("amount"));//折后
				dto.setItem_amount(rs.getDouble("item_amount"));//原价金额
				dto.setSet_discountss(Double.parseDouble(rs.getString("discount")));//折扣
				dto.setItem_type(rs.getString("item_type"));
				dto.setItemnum(rs.getInt("itemnum"));
				dto.setItem_seq(rs.getLong("item_seq"));
				if("1".equals(rs.getString("ischosen"))){
					dto.setIschosen("必选");
				} else if("2".equals(rs.getString("ischosen"))){
                    dto.setIschosen("其他");
                } else {
					dto.setIschosen("可选");
				}
				//计算折后价格
				/*double a = dto.getAmount()*(dto.getSet_discountss()/10);
				
				BigDecimal b = new BigDecimal(a);
				double f1 = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();*/
				
				dto.setAmounts(dto.getItem_amount());//原价
				li.add(dto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			if(connection!=null){
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return li;
	}

	@Override
	public void deleteSetChargingItem(long id) throws ServiceException {
		String sql="";
		Connection connection = null;
		try {
			sql+="DELETE FROM  set_charging_item   WHERE exam_set_id='"+id+"'";
			connection = this.jqm.getConnection();
			int rs = connection.createStatement().executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try {
				if(connection!=null){
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public ExamSet findEaxmSet(long id) throws ServiceException {
		return	(ExamSet) this.qm.get(ExamSet.class,id);
	}

	@Override
	public void updateEaxmSet(ExamSet exam) throws ServiceException {
		this.pm.update(exam);
		ExamSetType est= new ExamSetType();
		est = (ExamSetType) this.qm.get(ExamSetType.class,Long.valueOf(exam.getExam_set_type()).longValue());
		if((est!=null)&&(est.getSet_class()==2)){
			String hql=" from WebSynchro where dataid='"+exam.getId()+"' and datatype='2' ";
			List<WebSynchro> list=this.qm.find(hql);
			if(list.size()!=0){
				WebSynchro ws=list.get(0);
				this.pm.remove(ws);
			}
			WebSynchro wsnew=new WebSynchro();
			wsnew.setCreate_time(DateTimeUtil.parse());
			wsnew.setDataid(exam.getId()+"");
			wsnew.setDatatype('2');
			wsnew.setSynchro('0');
			wsnew.setSynchrodate(DateTimeUtil.parse());
			this.pm.save(wsnew);
		}
	}
	
	public void updateEaxmSetAll(ExamSet exam, String li) throws ServiceException {
		this.pm.update(exam);
		ExamSetType est = new ExamSetType();
		est = (ExamSetType) this.qm.get(ExamSetType.class, Long.valueOf(exam.getExam_set_type()).longValue());
		if ((est != null) && (est.getSet_class() == 2)) {
			String hql = " from WebSynchro where dataid='" + exam.getId() + "' and datatype='2' ";
			List<WebSynchro> list = this.qm.find(hql);
			if (list.size() != 0) {
				WebSynchro ws = list.get(0);
				this.pm.remove(ws);
			}
			WebSynchro wsnew = new WebSynchro();
			wsnew.setCreate_time(DateTimeUtil.parse());
			wsnew.setDataid(exam.getId() + "");
			wsnew.setDatatype('2');
			wsnew.setSynchro('0');
			wsnew.setSynchrodate(DateTimeUtil.parse());
			this.pm.save(wsnew);
		}

		String hql = " from SetChargingItem where exam_set_id='" + exam.getId() + "' ";
		List<SetChargingItem> list = this.qm.find(hql);
		for (SetChargingItem sci : list) {
			this.pm.remove(sci);
		}

		li = li.replace("}{", "},{");
		li = "[" + li + "]";
		JSONArray liArry = JSONArray.fromObject(li);
		@SuppressWarnings("unchecked")
		List<ChargingItemDTO> lis = (List<ChargingItemDTO>) JSONArray.toCollection(liArry, ChargingItemDTO.class);
		// 添加关系表
		for (ChargingItemDTO dto : lis) {
			SetChargingItem item = new SetChargingItem();
			item.setCharging_item_id(dto.getId());// 收费表id
			item.setExam_set_id(exam.getId());// 检查项目id
			item.setDiscount(dto.getSet_discountss());// 折扣
			item.setAmount(dto.getAmounts());// 折后金额
			item.setItemnum(dto.getItemnum());
			item.setItem_amount(dto.getAmount());// 新怎修改后折扣金额
			item.setUpdater(exam.getUpdater());// 修改用户
			item.setCreate_time(DateTimeUtil.parse());// 修改时间
			item.setCharging_item_code(dto.getItem_code());
			if(exam.getApp_type()==2) {
				ChargingItem c = this.chargingItemService.findChargingItem(item.getCharging_item_id());
				if(0 == c.getApp_type()) {
					item.setApptype("2");
				} else {
					item.setApptype(c.getApp_type()+"");
				}
			} else {
				item.setApptype(exam.getApp_type()+"");
			}
			if("必选".equals(dto.getIschosen().trim())){
				item.setIschosen("1");
			} else if("其他".equals(dto.getIschosen().trim())){
				item.setIschosen("2");
			} else {
				item.setIschosen("0");
			}
			this.addSetChargingItem(item);
		}
	}

	@Override
	public void deletSetChargingItemPl(String ids) throws ServiceException {
		String sql="";
		Connection connection = null;
		try {
			sql+="DELETE FROM  set_charging_item   WHERE exam_set_id in("+ids+")";
			connection = this.jqm.getConnection();
			int rs = connection.createStatement().executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try {
				if(connection!=null){
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void updateEaxmSetS(ExamSet  exam,String li) throws ServiceException {
		//删除
		SetChargingItem em = new SetChargingItem();
		em.setExam_set_id(exam.getId());
		this.pm.remove(em);
			
		li = li.replace("}{", "},{");
		li = "[" + li + "]";
		JSONArray liArry = JSONArray.fromObject(li);
		@SuppressWarnings("unchecked")
		List<ChargingItemDTO> lis = (List<ChargingItemDTO>) JSONArray
				.toCollection(liArry, ChargingItemDTO.class);
		// 添加关系表
		for (ChargingItemDTO dto : lis) {
			SetChargingItem item = new SetChargingItem();
			item.setCharging_item_id(dto.getId());// 收费表id
			item.setExam_set_id(exam.getId());// 检查项目id
			item.setDiscount(dto.getSet_discountss());// 折扣
			item.setAmount(dto.getAmounts());// 折后金额
			item.setItemnum(dto.getItemnum());
			item.setItem_amount(dto.getAmount());//新怎修改后折扣金额
			item.setUpdater(exam.getUpdater());// 修改用户
			item.setCreate_time(DateTimeUtil.parse());// 修改时间
			this.addSetChargingItem(item);
		}
	}

	@Override
	public List<ZybOccuhazardClassDTO>  getOccuhazardfactorsTree(String name) throws ServiceException {
		String sql = " SELECT z.hazardclassID,z.hazardclass_name FROM zyb_occuhazardclass z  ORDER  BY   z.[order]  ";
		//危害类别
		@SuppressWarnings("unchecked")
		List<ZybOccuhazardClassDTO>  zli = this.jqm.getList(sql,ZybOccuhazardClassDTO.class);
//		String id = "";
//		for (ZybOccuhazardClassDTO zo : zli) {
//			id+="'"+zo.getHazardclassID()+"',";
//		}
//		id = id.substring(0,id.length()-1);
		//危害因素
		sql = "SELECT o.hazardfactorsID,o.hazard_name,o.hazardclassID FROM  zyb_occuhazardfactors o ";
		if(!StringUtil.isEmpty(name))
		{
			sql=sql+" where o.hazard_name like '%"+name+"%' ";
		}
		sql=sql+" ORDER  BY   o.[order]";
		@SuppressWarnings("unchecked")
		List<ZybOccuhazardfactorsDTO> oli = this.jqm.getList(sql,ZybOccuhazardfactorsDTO.class);
		//岗前岗后岗中
		sql = " SELECT c.occuphyexaclassID,c.occuphyexaclass_name FROM  zyb_occuphyexaclass c  ORDER  BY  [order] ";
		@SuppressWarnings("unchecked")
		List<ZybOccuphyexaClassDTO> cli = this.jqm.getList(sql,ZybOccuphyexaClassDTO.class);
		
		List<ZybOccuhazardfactorsDTO> oli_s;
		List<ZybOccuhazardClassDTO>  remove_zhili = new ArrayList<ZybOccuhazardClassDTO>();
		boolean flag = false;
		for(int i = 0 ; i < zli.size() ; i++){
			oli_s = new ArrayList<ZybOccuhazardfactorsDTO>();
			for(int f = 0 ; f < oli.size() ; f++){
				if(zli.get(i).getHazardclassID().equals(oli.get(f).getHazardclassID())){
					ZybOccuhazardfactorsDTO z = new ZybOccuhazardfactorsDTO();
					 z.setId(oli.get(f).getHazardfactorsID());
					 z.setText(oli.get(f).getHazard_name());
					 z.setState("closed");
					 z.setChildren(cli);
					 if(oli.get(f).getHazard_name().equals(name)){
						 z.setState("open");
						 flag = true;
					 }
					 oli_s.add(z);
				}
			}
			if(oli_s.size() > 0){
				zli.get(i).setChildren(oli_s);
				if(flag){
					zli.get(i).setState("open");
				}
			}else{
				remove_zhili.add(zli.get(i));
			}
		}
		zli.removeAll(remove_zhili);
		
		//1.放射因素
		String fs_sql = "SELECT o.hazardfactorsID,o.hazard_name,o.hazardclassID FROM  zyb_occuhazardfactors o   where  remark='FSJKJC'";
		List<ZybOccuhazardfactorsDTO> fs_li = this.jqm.getList(fs_sql,ZybOccuhazardfactorsDTO.class);
		if(fs_li!=null && fs_li.size()>0){
			ZybOccuhazardfactorsDTO oc = new ZybOccuhazardfactorsDTO();
			ZybOccuhazardfactorsDTO ys = fs_li.get(0);
			fs_li.get(0).setId(fs_li.get(0).getHazardfactorsID());
			fs_li.get(0).setText(fs_li.get(0).getHazard_name());
			fs_li.get(0).setState("closed");
			
			//1.1职业体检类别
			fs_li.get(0).setChildren(cli);
			
			//2.危害类别
			ZybOccuhazardClassDTO weihaileibie = new ZybOccuhazardClassDTO();
			weihaileibie.setHazardclassID("fs1");
			weihaileibie.setHazardclass_name("放射性健康检查");
			
			//3.职业体检类别
			weihaileibie.setChildren(fs_li);
			zli.add(weihaileibie);
		} else {
			System.out.println("请维护放射性因素（备注：FSJKJC）");
		}
		
		//自选套餐（不和 危害类别、危害因素、岗前岗后岗中 关联的特殊套餐）
		ZybOccuhazardClassDTO zxtc = new ZybOccuhazardClassDTO();
		zxtc.setHazardclassID("zxtc");
		zxtc.setHazardclass_name("自选套餐");
		zxtc.setState("open");
		//zxtc.setChildren(new ArrayList<ZybOccuhazardfactorsDTO>());
		zli.add(zxtc);
		
		return zli;
	}
	public PageReturnDTO queryZybExamSet(ExamSetModel exam,int pageSize,int page,String intss,String center_num) throws ServiceException {
		String sql="SELECT  exam_set.price,exam_set.id,exam_set.company_id,exam_set.set_num,exam_set.set_name,exam_set.set_pinyin,"
				+ "exam_set.sex,exam_set.set_discount,exam_set.set_amount,exam_set.survey_minScore,"
				+ "exam_set.survey_maxScore,exam_set.disease_name,exam_set.is_Active,exam_set.creater,"
				+ "exam_set.create_time,exam_set.updater,exam_set.update_time,company_info.com_name,"
				+ "uc.chi_name  creater_name,uu.chi_name  as  update_name    FROM exam_set"
				+ "  LEFT JOIN company_info ON company_info.id=exam_set.company_id"
				+ "  LEFT JOIN user_usr uc ON  exam_set.creater=uc.id"
				+ "  LEFT JOIN user_usr uu ON exam_set.updater=uu.id "
				+ "  where exam_set.is_Active<>'N' and exam_set.center_num='"+center_num+"'  and    (app_type='0'  or   app_type='2')"
				+ "  and  exam_set.hazardfactorsid in("+exam.getHazardfactorsid()+")      ";
				if(exam.getOccuphyexaclassid()!=null && !"".equals(exam.getOccuphyexaclassid())){
				sql += "  and  exam_set.occuphyexaclassid = '"+exam.getOccuphyexaclassid()+"'   ";
				}
				if(exam.getSet_name()!=null&&!exam.getSet_name().equals("")){
			sql+=" and  exam_set.set_name like '%"+exam.getSet_name()+"%'";		
				}
				if(null!=exam.getSex()&&!"".equals(exam.getSex())){
			sql+="  and   exam_set.sex='"+exam.getSex()+"'";		
				}
				if(exam.getUpdate_time()!=null&&!exam.getUpdate_time().equals("")){
			sql+=" and exam_set.update_time>='"+exam.getUpdate_time()+"'";			
				}
				if(exam.getUpdate_times()!=null&&!exam.getUpdate_times().equals("")){
			sql+=" and exam_set.update_time<='"+exam.getUpdate_times()+"'";				
				}
				
		    sql+= "  ORDER BY  exam_set.id  DESC";
		PageSupport map=jqm.getList(sql,pageSize,page,ExamSetDTO.class);
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
	public void updateIsSynchro(String ids) throws ServiceException {
		String[] id=ids.split(",");
		Connection connection = null;
		try {
			connection = this.jqm.getConnection();
			
			for(int i=0;i<id.length;i++){
				if(!"".equals(id[i]) && id[i]!=null) {
					String sql="update exam_set set isSynchro = 1 where id='"+id[i]+"' ";
					connection.createStatement().executeUpdate(sql);
				}
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try {
				if(connection!=null){
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public boolean queryFromIdIsSynchro(String id) throws ServiceException {
		
		boolean result = false;
		Connection connection = null;
		try {
			connection = this.jqm.getConnection();
			
			if(!"".equals(id) && id!=null) {
				String sql="select exam_set.isSynchro from exam_set where id='"+id+"' ";
				ResultSet rs = connection.createStatement().executeQuery(sql);
				if (rs.next()) {
					int con = rs.getInt("isSynchro");
					if(con==1) {
						result = true;
					}else {
						result = false;
					}
				}
				rs.close();
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try {
				if(connection!=null){
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	@Override
	public PageReturnDTO querySelectExamSettree(ExamSetDTO exam, String center_num,int pageSize,int page) throws ServiceException {
		String sql="SELECT  exam_set.price,exam_set.id,exam_set.company_id,exam_set.set_num,exam_set.set_name,exam_set.set_pinyin,"
				+ "exam_set.sex,exam_set.set_discount,exam_set.set_amount,exam_set.survey_minScore,"
				+ "exam_set.survey_maxScore,exam_set.disease_name,exam_set.is_Active,exam_set.creater,"
				+ "exam_set.create_time,exam_set.updater,exam_set.update_time,company_info.com_name,"
				+ "uc.chi_name  creater_name,uu.chi_name  as  update_name ,exam_set.is_Active   FROM exam_set"
				+ "  LEFT JOIN company_info ON company_info.id=exam_set.company_id"
				+ "  LEFT JOIN user_usr uc ON  exam_set.creater=uc.id"
				+ "  LEFT JOIN user_usr uu ON exam_set.updater=uu.id "
				+ "  where exam_set.is_Active<>'N' and exam_set.center_num='"+center_num+"' ";
		    if(exam.getSettreeid()>0){
		    	sql +=" and settreeid='"+exam.getSettreeid()+"' ";
		    }
				if(exam.getSet_name()!=null&&!exam.getSet_name().equals("")){
			sql+=" and ( exam_set.set_name like '%"+exam.getSet_name()+"%' or exam_set.set_num like '%"+exam.getSet_name()+"%' or exam_set.set_pinyin like '%"+exam.getSet_name()+"%') ";		
				}
				if(null!=exam.getSex()&&!"".equals(exam.getSex())){
			sql+="  and  ( exam_set.sex='"+exam.getSex()+"' or exam_set.sex='全部' )  ";		
				}
				
			String IS_ADD_SETEXAMTYPE=this.customerInfoService.getCenterconfigByKey("IS_ADD_SETEXAMTYPE", center_num).getConfig_value().trim();
			if("Y".equals(IS_ADD_SETEXAMTYPE.toUpperCase())){
				if("G".equals(exam.getExam_type())){
					String IS_ADD_GSETEXAMTYPE=this.customerInfoService.getCenterconfigByKey("IS_ADD_GSETEXAMTYPE", center_num).getConfig_value().trim();
					sql+="  and   exam_set.exam_set_type in ("+IS_ADD_GSETEXAMTYPE+") ";
				}else if("T".equals(exam.getExam_type())){
					String IS_ADD_TSETEXAMTYPE=this.customerInfoService.getCenterconfigByKey("IS_ADD_TSETEXAMTYPE", center_num).getConfig_value().trim();
					sql+="  and   exam_set.exam_set_type in ("+IS_ADD_TSETEXAMTYPE+") ";
				}
			}
		    sql+= "  ORDER BY  exam_set.id  DESC";
		PageSupport map=jqm.getList(sql,pageSize,page,ExamSetDTO.class);
		PageReturnDTO webrole = new PageReturnDTO();
		webrole.setPage(page);
		webrole.setRp(pageSize);
		if ((map != null) && (map.getList() != null)) {
			webrole.setTotal(map.getRecTotal());
			webrole.setRows(map.getList());
		}
		return webrole;
	}
}
