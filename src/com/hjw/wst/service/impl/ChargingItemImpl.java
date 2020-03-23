package com.hjw.wst.service.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.http.impl.cookie.DateUtils;
import org.springframework.util.StringUtils;

import com.hjw.interfaces.util.DateUtil;
import com.hjw.util.DateTimeUtil;
import com.hjw.wst.DTO.ChargingItemDTO;
import com.hjw.wst.DTO.ChargingItemExamItemDTO;
import com.hjw.wst.DTO.DepartmentDepDTO;
import com.hjw.wst.DTO.ExaminationItemDTO;
import com.hjw.wst.DTO.HisClinicItemPriceListDTO;
import com.hjw.wst.DTO.HisDictDeptDTO;
import com.hjw.wst.DTO.JobDTO;
import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.wst.DTO.SampleDemoDTO;
import com.hjw.wst.DTO.SampleReportDemoDto;
import com.hjw.wst.DTO.ThridLisClassDTO;
import com.hjw.wst.DTO.ThridLisItemDTO;
import com.hjw.wst.DTO.UserDTO;
import com.hjw.wst.domain.ChargingItem;
import com.hjw.wst.domain.ChargingItemExamItem;
import com.hjw.wst.domain.ChargingItemSchedule;
import com.hjw.wst.domain.ChargingItemVScenter;
import com.hjw.wst.domain.ChargingItemVScenterPKID;
import com.hjw.wst.domain.DepartmentDep;
import com.hjw.wst.domain.ExamSet;
import com.hjw.wst.domain.ExaminationItem;
import com.hjw.wst.domain.LimitChargingItem;
import com.hjw.wst.domain.SetChargingItem;
import com.hjw.wst.model.ChargingItemModel;
import com.hjw.wst.service.ChargingItemService;
import com.hjw.wst.service.WebSynchroService;
import com.synjones.framework.exception.ServiceException;
import com.synjones.framework.persistence.JdbcPersistenceManager;
import com.synjones.framework.persistence.JdbcQueryManager;
import com.synjones.framework.persistence.PersistenceManager;
import com.synjones.framework.persistence.QueryManager;
import com.synjones.framework.support.PageSupport;

import net.sf.json.JSONArray;

/**
 * 
 * @Title: 火箭蛙体检管理系统
 * @Package com.hjw.wst.service.impl
 * @Description:
 * @author: 收费项目
 * @date: 2016年9月25日 下午4:15:45
 * @version V2.0.0.0
 */
public class ChargingItemImpl implements ChargingItemService {
	private QueryManager qm;
	private JdbcQueryManager jqm;
	private JdbcPersistenceManager jpm;
	private PersistenceManager pm;
	private WebSynchroService webSynchroService;

	
	
	public void setWebSynchroService(WebSynchroService webSynchroService) {
		this.webSynchroService = webSynchroService;
	}

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
	public PageReturnDTO queryChargingItem(ChargingItemDTO cha, int pagesize, int page, String ks, String exam_item_id, String center_num)
			throws ServiceException {
		boolean fd = true;
		String sql = "SELECT  distinct(charging_item.item_code),sx.limit_count,charging_item.id,charging_item.dep_id,charging_item.sam_demo_id,"
				+ "charging_item.sam_report_demo_id,charging_item.item_code,charging_item.item_name, charging_item.item_discount,"
				+ "charging_item.item_pinyin,charging_item.item_category,charging_item.sex,"
				+ "charging_item.amount,charging_item.dep_category,charging_item.isOnlyApplyOrReport,"
				+ "charging_item.item_seq,charging_item.guide_category,charging_item.his_num,"
				+ "charging_item.exam_num,charging_item.view_num,charging_item.isActive,charging_item.creater,"
				+ "charging_item.create_time,charging_item.updater,charging_item.update_time,"
				+ "charging_item.calculation_amount,charging_item.calculation_rate,charging_item.interface_flag,charging_item.item_type,"
				+ "charging_item.charge_inter_num,charging_item.item_abbreviation,charging_item.notices,"
				+ "charging_item.item_note,charging_item.hiscodeClass,charging_item.perform_dept,"
				+ "de.dep_name as d_name,sa.demo_name as s_name,mo.demo_name as report_name,di.data_name as dname,"
				+ " his_dict_dept.dept_name,df.data_name  as  f_name"
				+ " FROM charging_item   LEFT JOIN department_dep de on charging_item.dep_id=de.id"
				+ " left join department_vs_center ded on ded.dep_id = charging_item.dep_id  "
				+ " LEFT JOIN sample_demo sa on charging_item.sam_demo_id=sa.id "
				+ " LEFT JOIN sample_report_demo mo on charging_item.sam_report_demo_id=mo.id"
				+ " LEFT JOIN data_dictionary as di  on charging_item.dep_category=di.id "
				+ " LEFT JOIN his_dict_dept on charging_item.perform_dept=his_dict_dept.dept_code"
				+ " LEFT JOIN  limit_charging_item  sx   on   sx.charging_item_code=charging_item.item_code"
				+ " LEFT JOIN data_dictionary as df  on charging_item.finance_class =df.id "
				+ " WHERE 1=1 ";
		if(!StringUtils.isEmpty(cha.getIsActive())){
			sql+=" AND charging_item.isActive='"+cha.getIsActive()+"' ";
		}
		if (cha.getStartStop() != null && !"".equals(cha.getStartStop())) {// 收费编号
			if(cha.getStartStop().length()==1) {
				sql += " AND charging_item.isActive IN ('"+cha.getStartStop()+"') ";
			}else {
				sql += " AND charging_item.isActive IN ('Y','N') ";
			}
		}
		if (cha.getDemo_type() != 0) {
			sql += " AND  sa.demo_type ='" + cha.getDemo_type() + "'";
		}
		if (cha.getItem_code() != null && !cha.getItem_code().equals("")) {// 收费编号
			sql += " and item_code='" + cha.getItem_code().trim() + "'";
		}
		if (cha.getItem_name() != null && !cha.getItem_name().equals("")) {// 收费名称
			sql += " and (item_name  like '%" + cha.getItem_name().trim() + "%' or item_pinyin like '%"
					+ cha.getItem_name().trim() + "%') ";
		}
		if (cha.getView_num() != null && !cha.getView_num().equals("")) {// 影像编号
			sql += " and view_num='" + cha.getView_num().trim() + "'";
		}
		if (cha.getExam_num() != null && !cha.getExam_num().equals("")) {// 检验编码
			sql += " and charging_item.exam_num='" + cha.getExam_num().trim() + "'";
		}
		if (cha.getDep_id() > 0) {// 所属科室
			sql += " and charging_item.dep_id='" + cha.getDep_id() + "'";
		}
		if (ks != null && !"".equals(ks)) {
			sql += " and charging_item.dep_id  in(select  et.id  from   department_dep  et    where   et.isActive='Y'   AND     et.dep_category='"
					+ ks + "' )";
			sql += "  and   charging_item.sam_demo_id  is  NULL ";
		}
		if (cha.getPerform_dept() != null && !cha.getPerform_dept().equals("")) {// 执行科室
			sql += " and perform_dept='" + cha.getPerform_dept().trim() + "'";
		}
		if (cha.getInterface_flag() != null && !cha.getInterface_flag().equals("")) {// 标识
			sql += " and interface_flag='" + cha.getInterface_flag().trim() + "'";
		}
		if (cha.getGuide_category() != null && !cha.getGuide_category().equals("")) {// 导引单
			sql += " and guide_category='" + cha.getGuide_category().trim() + "'";
		}
		if (cha.getHis_num() != null && !cha.getHis_num().equals("")) {// his编码
			sql += " and his_num='" + cha.getHis_num().trim() + "'";
		}
		if (cha.getItem_pinyin() != null && !"".equals(cha.getItem_pinyin())) {
			sql += " and item_pinyin  like'%" + cha.getItem_pinyin().trim() + "%'   ";
		}
		if(cha.getItem_name_s()!=null && !"".equals( cha.getItem_name_s() )){
			sql += "   and (item_name  like'%" + cha.getItem_name_s().trim() + "%'   or  "
					+ "  item_code = '"+cha.getItem_name_s().trim()+"'  "
					+ "  or item_pinyin like '%"+cha.getItem_name_s().trim()+"%' )    ";
		}
		
		if (cha.getMccf().equals("项目名称重复")) {
			fd = false;
			sql += " and  charging_item.item_name   in   (select   charging_item.item_name   from"
					+ "  charging_item   where   charging_item.isActive='Y'  group   by   charging_item.item_name  having count(charging_item.item_name)>1)   ORDER BY   charging_item.item_name ";
		}
		if (exam_item_id != null && !"".equals(exam_item_id)) {
			sql += " and de.dep_category='21' and charging_item.id not in (select cii.charging_item_id from charging_item_exam_item cii,examination_item e where cii.item_code = e.item_num and e.item_result_type = 1)";
		}
		if (fd) {
			sql += "	ORDER BY charging_item.id DESC";
		}
		PageSupport map = (PageSupport) this.jqm.getList(sql, page, pagesize, ChargingItemDTO.class);
		PageReturnDTO webrole = new PageReturnDTO();
		webrole.setPage(page);
		webrole.setRp(pagesize);
		if ((map != null) && (map.getList() != null)) {
            List<ChargingItemDTO> newList=new ArrayList<ChargingItemDTO>();
			for(int i=0; map.getList().size()>i; i++){
                ChargingItemDTO dto = (ChargingItemDTO) map.getList().get(i);
                Connection connection = null;
                try {
                    connection = this.jqm.getConnection();
                    String searchCenter = "     SELECT ec.center_name FROM charging_item_vs_center cvs,examinatioin_center ec " +
                            "  WHERE cvs.center_num = ec.center_num and cvs.charging_item_code='" + dto.getItem_code() + "'";
                    ResultSet rs = connection.createStatement().executeQuery(searchCenter);
                    String centername = "";
                    while (rs.next()) {
                        centername = centername + "--" + rs.getString("center_name");
                    }
                    if(centername.length()>0){
                        centername=centername.substring(2,centername.length());
                        dto.setCenter_name(centername);
                    }
                } catch (SQLException ex)  {
                    ex.printStackTrace();
                } finally {
                    try {
                        if (connection != null) {
                            connection.close();
                        }
                    } catch (SQLException sqle4) {
                        sqle4.printStackTrace();
                    }
                }
                newList.add(dto);
            }
			map.setList(newList);
            webrole.setTotal(map.getRecTotal());
            webrole.setRows(map.getList());
		}
		return webrole;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DepartmentDepDTO> getDepartmentDep(String center_num) throws ServiceException {
		String sql = "SELECT  department_dep.id,department_dep.dep_name  FROM department_dep left join department_vs_center ded on ded.dep_id = department_dep.id where   isActive='Y'  and ded.center_num ='"+center_num+"' ";
		List<DepartmentDepDTO> li = jqm.getList(sql, DepartmentDepDTO.class);
		return li;
	}
	public List<DepartmentDepDTO> getDepartmentDepBarCode( String center_num) throws ServiceException {
		String sql = "SELECT  department_dep.id,department_dep.dep_name  FROM department_dep  left join department_vs_center ded on ded.dep_id = department_dep.id where   isActive='Y'   and ded.center_num ='"+center_num+"' "
				+ "  and  department_dep.isPrint_Barcode = '0'";
		List<DepartmentDepDTO> li = jqm.getList(sql, DepartmentDepDTO.class);
		return li;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<HisDictDeptDTO> getHisDictDept() throws ServiceException {
		String sql = "SELECT convert(VARCHAR(50),dept_code) as dept_code,convert(VARCHAR(50),dept_code) +'----'+dept_name  as  dept_name,dept_class,input_code  FROM  his_dict_dept";
		List<HisDictDeptDTO> li = jqm.getList(sql, HisDictDeptDTO.class);
		return li;
	}

	@Override
	public void updateChargingItemDel(String id) throws ServiceException {
		String sql = "";
		Connection connection = null;
		try {
			sql += "update charging_item set isActive='N' where id in(" + id + ")";
			connection = this.jqm.getConnection();
			connection.createStatement().executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	@SuppressWarnings("rawtypes")
	@Override
	public ChargingItemExamItem getChargingIteId(long id) throws ServiceException {
		List ls = qm.find("From ChargingItemExamItem  Where  charging_item_id ='" + id + "'");
		if (ls.size() > 0)
			return (ChargingItemExamItem) ls.get(0);
		else
			return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SampleDemoDTO> getItemSampleDemo() throws ServiceException {
		String sql = "SELECT sample_demo.id,sample_demo.demo_name  FROM sample_demo  where  isActive='Y'";
		List<SampleDemoDTO> ls = jqm.getList(sql, SampleDemoDTO.class);
		return ls;
	}

    public List<SampleDemoDTO> getItemSampleDemoByDemoType(int demoType) throws ServiceException {
        String sql = "SELECT sample_demo.id,sample_demo.demo_name  FROM sample_demo  where  isActive='Y'";
                if(demoType > 0){
                    sql+=" and demo_type="+demoType;
                }
        List<SampleDemoDTO> ls = jqm.getList(sql, SampleDemoDTO.class);
        return ls;
    }

	@SuppressWarnings("unchecked")
	@Override
	public List<SampleReportDemoDto> getItemSampleReportDemo() throws ServiceException {
		String sql = "SELECT  sample_report_demo.id,sample_report_demo.demo_name  FROM sample_report_demo   where  is_Active='Y'";
		List<SampleReportDemoDto> li = jqm.getList(sql, SampleReportDemoDto.class);
		return li;
	}

	@Override
	public ChargingItem addChargingIte(ChargingItem item,ChargingItemModel model,UserDTO user) throws ServiceException {
		String hql = " FROM  ChargingItemExamItem  WHERE  charging_item_id = '"+model.getId()+"'";
		List<ChargingItemExamItem> li = this.qm.find(hql);
		for (int i = 0 ; i < li.size() ; i++) {
			this.pm.remove(li.get(i));
		}
		this.pm.save(item);//保存收费项目
        if(!StringUtils.isEmpty(model.getCenter_num())){
            String[] centerNums = model.getCenter_num().split(",");
            //多体检中心添加数据
            for(int i=0; i<centerNums.length; i++){
                ChargingItemVScenter  cct = new ChargingItemVScenter();
                ChargingItemVScenterPKID pkid = new ChargingItemVScenterPKID();
                cct = new ChargingItemVScenter();
                pkid = new ChargingItemVScenterPKID();
                pkid.setCharging_item_code(item.getItem_code());
                pkid.setCenter_num( centerNums[i] );
                cct.setChargingItemVScenterPKID(pkid);
                cct.setCalculation_amount(item.getCalculation_amount());
                cct.setCalculation_rate(item.getCalculation_rate());
                cct.setCenter_price(item.getAmount());
                cct.setCerate_time(DateUtil.parse());
                cct.setCharge_inter_num(item.getCharge_inter_num());
                cct.setCharging_item_number( (int)item.getCharging_item_number());
                cct.setCreater(item.getCreater());
                cct.setExam_num(item.getExam_num());
                cct.setHis_num(item.getHis_num());
                cct.setInterface_flag(item.getInterface_flag());
                cct.setItem_discount(item.getItem_discount());
                cct.setLimit_num(item.getLimit_num());
                cct.setPerform_dept(item.getPerform_dept());
                cct.setPrice(item.getAmount());
                cct.setView_num(item.getView_num());
                this.pm.save(cct);
            }
        }

		this.webSynchroService.updateWebSynchro(item.getId()+"", '1');
		savelimitChargingItem(item.getId(),model.getLimit_count(),user,item.getItem_code());//项目上限
		JSONArray json = JSONArray.fromObject(model.getJid());
		List<ChargingItemExamItemDTO> bm = (List<ChargingItemExamItemDTO>) JSONArray.toCollection(json,ChargingItemExamItemDTO.class);
				
	     for(int o=0;o<bm.size();o++){
     		ChargingItemExamItem  am = new ChargingItemExamItem();
     		am.setCreater(user.getUserid());//收费项目关系表--创建人
     		am.setCreate_time(DateTimeUtil.parse());//检查项目关系表--创建时间
     		am.setCharging_item_id(item.getId());
     		am.setUpdater(user.getUserid());
     		am.setUpdate_time(DateTimeUtil.parse());
     		am.setExam_item_id(bm.get(o).getExam_item_id());
     		am.setCharging_item_code(item.getItem_code());
     		am.setItem_code(bm.get(o).getItem_num());
     		this.pm.save(am);
     		updateSeq_code(bm.get(o).getSeq_code(),bm.get(o).getExam_item_id());
	        		
	     }
		return item;
	}

	@Override
	public void addChargingItemExamItem(ChargingItemExamItem item) throws ServiceException {
		this.pm.save(item);
	}

	@Override
	public ChargingItem findChargingItem(long id) throws ServiceException {
		return (ChargingItem) this.qm.get(ChargingItem.class, id);
	}

	@Override
	public List<ExaminationItemDTO> getChargingItemExamItem(long id) throws ServiceException {
		String sql = "";
		Connection connection = null;
		List<ExaminationItemDTO> li = new ArrayList<ExaminationItemDTO>();
		try {
			sql += "SELECT  ex.seq_code,cha.id as cid,ex.id,ex.item_name,ex.item_num,ex.item_result_type  from charging_item_exam_item ch,"
					+ " examination_item ex,charging_item cha"
					+ " WHERE ch.charging_item_code=cha.item_code and ch.item_code=ex.item_num and  ch.charging_item_id='" + id
					+ "'      order   by   ex.seq_code";
			connection = this.jqm.getConnection();
			ResultSet rs = connection.createStatement().executeQuery(sql);
			while (rs.next()) {
				ExaminationItemDTO dto = new ExaminationItemDTO();
				//dto.setId(Integer.parseInt(rs.getString("id")));
				dto.setExam_item_id(rs.getString("id"));
				dto.setItem_name(rs.getString("item_name"));
				dto.setItem_num(rs.getString("item_num"));
				dto.setCid(Integer.parseInt(rs.getString("cid")));// 收费项目id
				dto.setSeq_code(rs.getLong("seq_code"));
				dto.setItem_result_type(rs.getLong("item_result_type"));
				li.add(dto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return li;
	}

	@SuppressWarnings("unused")
	@Override
	public void delChargingItemExamItem(long id) throws ServiceException {
		String sql = "";
		Connection connection = null;
		try {
			sql += "DELETE FROM charging_item_exam_item WHERE charging_item_exam_item.charging_item_id='" + id + "'";
			connection = this.jqm.getConnection();
			int rs = connection.createStatement().executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public ChargingItem updateChargingItem(ChargingItem item,ChargingItemModel model,UserDTO user) throws ServiceException {
		String hql = " FROM  ChargingItemExamItem  WHERE  charging_item_id = '"+model.getId()+"'";
		List<ChargingItemExamItem> li = this.qm.find(hql);
		for (int i = 0 ; i < li.size() ; i++) {
			this.pm.remove(li.get(i));
		}
		pm.update(item);
		
		Connection connection = null;
		Statement stet = null;
		try {
			connection = this.jqm.getConnection();
		    String sql = " update charging_item_vs_center set charge_inter_num = '"+item.getCharge_inter_num()+"',"
		    		+ "interface_flag='"+item.getInterface_flag()+"',his_num='"+item.getHis_num()+"',"
					+ "exam_num = '"+item.getExam_num()+"',view_num='"+item.getView_num()+"',"
					+ "perform_dept = '"+item.getPerform_dept()+"',calculation_amount="+item.getCalculation_amount()+","
					+ "calculation_rate="+item.getCalculation_rate()+",charging_item_number='"+item.getCharging_item_number()+"',"
					+ "item_discount = "+item.getItem_discount()+",limit_num="+model.getLimit_count()+","
		    		+ "center_price='"+item.getAmount()+"' WHERE "
		    		+ "  center_num='001'  AND charging_item_code = '"+model.getItem_code()+"' ";
		    stet = connection.createStatement();
		    stet.executeUpdate(sql);
		} catch (SQLException e) {
			try {
				if(stet!=null){
					stet.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
			throw new ServiceException(e.toString());
		} finally {
			try {
				if(stet!=null){
					stet.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
		this.webSynchroService.updateWebSynchro(model.getId()+"",'1');
		updatelimitChargingItem(item.getId(),model.getLimit_count(),user);
		
		JSONArray json = JSONArray.fromObject(model.getJid());
		List<ChargingItemExamItemDTO> bm = (List<ChargingItemExamItemDTO>) JSONArray.toCollection(json,ChargingItemExamItemDTO.class);

        for(int o=0;o<bm.size();o++){
        		ChargingItemExamItem  am = new ChargingItemExamItem();
        		am.setCreater(user.getUserid());//收费项目关系表--创建人
        		am.setCreate_time(DateTimeUtil.parse());//检查项目关系表--创建时间
        		am.setCharging_item_id(item.getId());
        		am.setUpdater(user.getUserid());
        		am.setUpdate_time(DateTimeUtil.parse());
        		am.setExam_item_id(bm.get(o).getExam_item_id());
        		am.setCharging_item_code(item.getItem_code());
        		am.setItem_code(bm.get(o).getItem_num());
        		this.pm.save(am);
        		updateSeq_code(bm.get(o).getSeq_code(),am.getExam_item_id());
	        		
        }
		return item;
	}
	//修改编码
	public void updateSeq_code(long seq_code,long exam_item_id)throws ServiceException {
		ExaminationItem ex = (ExaminationItem) qm.get(ExaminationItem.class,exam_item_id);
		ex.setSeq_code(seq_code);
		this.pm.update(ex);
	}

	@Override
	public ChargingItem findClass(long id) throws ServiceException {
		return (ChargingItem) this.qm.get(ChargingItem.class, id);
	}

	@Override
	public DepartmentDep getDep_categoryId(long id) throws ServiceException {
		return (DepartmentDep) this.qm.get(DepartmentDep.class, id);
	}

	@Override
	public long getBMYZ(String bianma, String zhi) throws ServiceException {
		long a = 1;
		String sql = "";
		Connection connection = null;
		try {
			sql += "SELECT * FROM charging_item   WHERE  isActive='Y'  and  " + bianma + "='" + zhi + "'";
			connection = this.jqm.getConnection();
			ResultSet rs = connection.createStatement().executeQuery(sql);
			if (rs.next()) {
				a = 0;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return a;
	}
	@Override
	public long centerBMYZ(String bianma, String zhi,String center_num) throws ServiceException {
		long a = 1;
		String sql = "";
		Connection connection = null;
		try {
			sql += "SELECT * FROM charging_item_vs_center  c WHERE c.center_num = '"+center_num+"' "
					+ " AND  c."+bianma+ " ='" + zhi + "'";
			connection = this.jqm.getConnection();
			ResultSet rs = connection.createStatement().executeQuery(sql);
			if (rs.next()) {
				a = 0;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return a;
	}

	@Override
	public PageReturnDTO getHisClinic(HisClinicItemPriceListDTO dto, int page, int pageSize) throws ServiceException {
		String sql = "SELECT   c.item_code as item_code_c,c.item_name as item_name_c,"
				+ "c.item_class as item_class_c,c.input_code as input_code_c,c.price"
				+ "  FROM his_clinic_item as  c  where 1=1 ";
		if (dto.getItem_name_c() != null && !"".equals(dto.getItem_name_c())) {
			sql += "  and  (c.item_name  like '%" + dto.getItem_name_c().trim() + "%'" + " or  c.input_code   like  '%"
					+ dto.getItem_name_c().trim() + "%')";
		}
		if (dto.getItem_code_c() != null && !"".equals(dto.getItem_code_c())) {
			sql += "  and   c.item_code='" + dto.getItem_code_c().trim() + "'";
		}
		sql += " order by  c.item_name";
		PageSupport map = (PageSupport) this.jqm.getList(sql, page, pageSize, HisClinicItemPriceListDTO.class);
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
	public double getHisPriceS(String item_code, String item_class_c) throws ServiceException {
		Connection connection = null;
		String sql = "";
		double PriceZJ = 0;
		try {
			sql += "select   [dbo].[fun_GetPriceByHisClinicItemCode]('" + item_code + "','" + item_class_c + "')";
			connection = this.jqm.getConnection();
			ResultSet rs = connection.createStatement().executeQuery(sql);
			if (rs.next()) {
				PriceZJ = rs.getDouble(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return PriceZJ;
	}

	@Override
	public List<HisClinicItemPriceListDTO> getHisjg(HisClinicItemPriceListDTO dto) throws ServiceException {
		String sql = "SELECT	p.item_class	as   item_class_p,p.item_code  as  item_code_p,p.item_name  as item_name_p,"
				+ "p.units,p.price	 from his_clinic_item_v_price_list   hcp,his_price_list  p,his_clinic_item   c"
				+ " where   hcp.charge_item_code=p.item_code   and  hcp.clinic_item_code =c.item_code"
				+ " and  hcp.charge_item_class=p.item_class  and  hcp.clinic_item_class=c.item_class  "
				// + " and hcp.units=p.units and
				// hcp.charge_item_spec=p.item_spec"
				+ "  and  '" + dto.getSystemdate() + "'>=p.start_date   and  '" + dto.getSystemdate() + "'<=p.stop_date"
				+ " and 	hcp.clinic_item_code='" + dto.getClinic_item_code() + "'" + "  order by  p.item_name";
		List<HisClinicItemPriceListDTO> li = this.jqm.getList(sql, HisClinicItemPriceListDTO.class);
		return li;
	}

	@Override
	public List<DepartmentDepDTO> getkbld(long id) throws ServiceException {
		String sql = "   SELECT da.id  as   zdid,da.data_name   from   department_dep"
				+ "    LEFT  JOIN   data_dictionary   da    ON    department_dep.dep_category=da.id  WHERE  department_dep.id="
				+ id;
		List<DepartmentDepDTO> dto = this.jqm.getList(sql, DepartmentDepDTO.class);
		return dto;
	}

	public void savelimitChargingItem(long id, long limit_count, UserDTO user,String item_code) throws ServiceException {
		LimitChargingItem li = new LimitChargingItem();
		li.setCenter_num(user.getCenter_num());
		li.setCharging_item_id(id);
		li.setLimit_count(limit_count);
		li.setCreater(user.getUserid());
		li.setCreate_time(DateTimeUtil.parse());
		li.setCharging_item_code(item_code);
		this.pm.save(li);
	}

	@Override
	public void updatelimitChargingItem(long id, long limit_count, UserDTO user) throws ServiceException {
		String sql = "SELECT * FROM  limit_charging_item   WHERE  charging_item_id='" + id + "'";
		List<LimitChargingItem> li = jqm.getList(sql, LimitChargingItem.class);
		if (li.size() > 0) {
			li.get(0).setCenter_num(user.getCenter_num());
			li.get(0).setCharging_item_id(id);
			li.get(0).setLimit_count(limit_count);
			li.get(0).setUpdater(user.getUserid());
			li.get(0).setUpdate_time(DateTimeUtil.parse());
			this.pm.update(li.get(0));
		} else {
			LimitChargingItem it = new LimitChargingItem();
			it.setCenter_num(user.getCenter_num());
			it.setCharging_item_id(id);
			it.setLimit_count(limit_count);
			it.setUpdater(user.getUserid());
			it.setUpdate_time(DateTimeUtil.parse());
			this.pm.save(it);
		}
	}

	public LimitChargingItem getlimitChargingItem(long id,ChargingItem item,UserDTO user) throws ServiceException {
		String sql = "select  *  from    limit_charging_item    where   (charging_item_id='" + id + "' "
				+ "or  charging_item_code='"+item.getItem_code()+"'   )  and center_num = '"+user.getCenter_num()+"' ";
		List<LimitChargingItem> li = jqm.getList(sql, LimitChargingItem.class);
		return li.size() > 0 ? li.get(0) : null;
	}

	public PageReturnDTO getThridLisClassList(ChargingItemModel model, int page, int pageSize) throws ServiceException {
		String sql = " select * from  thrid_lis_class  where  1=1 ";
		if (model.getLisid() != null && !"".equals(model.getLisid())) {
			sql += "  and   lisid='" + model.getLisid().trim() + "'";
		}
		if (model.getLisclassname() != null && !"".equals(model.getLisclassname())) {
			sql += "  and  lisclassname  like    '%" + model.getLisclassname().trim() + "%'";
				}
				if(model.getCreate_time()!=null && !"".equals(model.getCreate_time())){
					 sql +="   and   create_time>='"+model.getCreate_time()+"'";
				}
				if(model.getCreate_time2()!=null && !"".equals(model.getCreate_time2())){
					 sql +="  and  create_time<='"+model.getCreate_time()+"  23:59'";
		}
		sql += " order by   create_time  desc";
		PageSupport map = (PageSupport) this.jqm.getList(sql, page, pageSize, ThridLisClassDTO.class);
		PageReturnDTO webrole = new PageReturnDTO();
		webrole.setPage(page);
		webrole.setRp(pageSize);
		if ((map != null) && (map.getList() != null)) {
			webrole.setTotal(map.getRecTotal());
			webrole.setRows(map.getList());
		}
		return webrole;
	}

	public List<ThridLisItemDTO> getThridLisItemList(ChargingItemModel model) throws ServiceException {
		String sql = " SELECT * FROM    thrid_lis_class_item lci,thrid_lis_item l	"
				+ " where   lci.lisitemid = l.lisitemid  and  lci.lisclassid='" + model.getLisid() + "'  ";
		List<ThridLisItemDTO> li = this.jqm.getList(sql, ThridLisItemDTO.class);
		return li;
	}

	@Override
	public boolean updateHIsPriceSynchro(ChargingItemModel model) throws ServiceException {
		String sql = "";
		Connection connection = null;
		ResultSet rs = null;
		ResultSet pri = null;
		int rsup;
		ResultSet s = null;
		double amount;
		boolean fal = false;
		try {
			sql = " SELECT  c.id,c.item_code,c.his_num,c.item_class,amount FROM   charging_item c  where  isActive='Y' and  his_num <> '' ";
			connection = this.jqm.getConnection();
			connection.setAutoCommit(false);
			rs = connection.createStatement().executeQuery(sql);
			while (rs.next()) {
				sql = "	SELECT h.clinic_item_code FROM    his_clinic_item_v_price_list h,his_price_list p   where  "
						+ "	h.charge_item_code=p.item_code  and   h.charge_item_class=p.item_class and	"
						+ "	getdate()>=p.start_date and GETDATE()<=p.stop_date  and  h.clinic_item_code= '"
						+ rs.getString("his_num") + "'  and  " + "  h.clinic_item_class ='" + rs.getString("item_class")
						+ "'";
				s = connection.createStatement().executeQuery(sql);
				if (s.next()) {
					sql = "select   [dbo].[fun_GetPriceByHisClinicItemCode]('" + rs.getString("his_num") + "','"
							+ rs.getString("item_class") + "')";
					pri = connection.createStatement().executeQuery(sql);
					if (pri.next()) {
						amount = pri.getDouble(1);
						
						sql = " update charging_item set  amount = " + amount + "  where  id = '" + rs.getLong("id")+ "'";
						connection.createStatement().executeUpdate(sql);
						String sql1 = " update charging_item_vs_center set price = '"+amount+"',center_price = '"+amount+"' where charging_item_code = '"+rs.getString("item_code")+"'";
						connection.createStatement().executeUpdate(sql1);
					}
				} else {
					// sql = " update charging_item set his_num='',amount=0
					// where id='"+rs.getString("id")+"' ";
					// connection.createStatement().executeUpdate(sql);
				}
			}
			connection.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			fal = true;
		} finally {
			try {
				connection.setAutoCommit(true);
				if (pri != null) {
					pri.close();
				}
				if (rs != null) {
					rs.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return fal;
	}

	@Override
	public PageReturnDTO getItemSchedule(ChargingItemModel model, String center_num,int page,int pagesize) throws ServiceException {
//		String sql = " SELECT  ci.item_code,ci.item_name,ch.schedule_time,ch.schedule_number,uu.chi_name,ch.update_time,ch.schedule_number  FROM  "
//				+ " charging_item_schedule  ch  " + "  left join charging_item  ci   on  ci.id = ch.charging_item_id  "
//				+ " LEFT JOIN  user_usr  uu ON  uu.id = ch.updater  "
//				+ " WHERE   ci.isActive='Y' AND  ch.schedule_time>='" + model.getSchedule_time() + " 00:00:00:000'  "
//						+ " and   ch.schedule_time<='"+model.getSchedule_time()+" 23:59:59:000'";
		String sql = " SELECT  de.dep_name,ch.id as id_s,ch.charging_item_id as id,ci.item_code,ci.item_name,ch.schedule_time,ch.schedule_number,uu.chi_name,ch.update_time,ch.schedule_number, "
				+"	 ( SELECT count(*)  FROM exam_info  eo,examinfo_charging_item  em  WHERE  "
				+"		 eo.exam_num = em.exam_num   and   em.isActive='Y' and em.center_num = '"+center_num+"' and  eo.register_date>='"+model.getSchedule_time()+" 00:00:00:000'  "
				+"		  AND   eo.register_date<='"+model.getSchedule_time()+" 23:59:59:000' "
				+"		 )  as  yuyue_num,( SELECT count(*)  FROM exam_info  eo,examinfo_charging_item  em  WHERE   "
				+"		 eo.exam_num = em.exam_num   and   em.isActive='D' and em.center_num = '"+center_num+"' and  eo.register_date>='"+model.getSchedule_time()+" 00:00:00:000'  "
				+"		  AND   eo.register_date<='"+model.getSchedule_time()+"  23:59:59:000' "
				+"		 )  dengji_number "
				+"		 FROM   charging_item_schedule  ch     "
				+"		 left join charging_item  ci   on  ci.item_code = ch.charging_item_code  "
				+"		 LEFT JOIN  user_usr  uu ON  uu.id = ch.updater  "
				+ "      LEFT JOIN department_dep de on ci.dep_id=de.id   "
				+ "      LEFT JOIN department_vs_center ded on ci.dep_id=ded.dep_id   "
				+"		 WHERE   ci.isActive='Y' and  ded.center_num ='"+center_num+"'  "
				+"		 AND  ch.schedule_time>='"+model.getSchedule_time()+" 00:00:00:000'   and   ch.schedule_time<='"+model.getSchedule_time()+" 23:59:59:000'";
				if(model.getDep_id()>0){
					sql+= "      and   de.id = '"+model.getDep_id()+"'";
				}
				if(model.getItem_name()!=null && !"".equals(model.getItem_name())){
				   sql+="  and   ci.item_name like '%"+model.getItem_name()+"%'"; 	
				}
				if(model.getExam_num()!=null && !"".equals(model.getExam_num())){
					sql+=" and ci.exam_num='"+model.getExam_num().trim()+"'";
				}
				sql+=" order by ci.item_name";
		PageSupport map = (PageSupport) this.jqm.getList(sql, page, pagesize, ChargingItemDTO.class);
		PageReturnDTO webrole = new PageReturnDTO();
		webrole.setPage(page);
		webrole.setRp(pagesize);
		if ((map != null) && (map.getList() != null)) {
			webrole.setTotal(map.getRecTotal());
			webrole.setRows(map.getList());
		}
		return webrole;
	}

	@Override
	public void saveItemSchedule(String schedule_time, String ids,UserDTO user,long Schedule_number) throws ServiceException {
		
		String hql = " FROM  ChargingItemSchedule c  where  c.charging_item_id in("+ids+")";
		List<ChargingItemSchedule> li = this.qm.find(hql);
		for (ChargingItemSchedule chargingItemSchedule : li) {
			this.pm.remove(chargingItemSchedule);
		}
		
		String[] id = ids.split(",");
		String[] s_time = schedule_time.split(",");
		ChargingItemSchedule ch = new ChargingItemSchedule();
		for(int i = 0 ; i < id.length ; i++){
			for(int j = 0 ; j < s_time.length ; j++){
				ch = new ChargingItemSchedule();
				ch.setSchedule_number(Schedule_number);
				ch.setSchedule_time(DateTimeUtil.parse0(s_time[j]));
				ch.setCharging_item_id(Integer.parseInt(id[i]));
				ch.setCreate_time(DateTimeUtil.parse());
				ch.setUpdate_time(DateTimeUtil.parse());
				ch.setCreater(user.getUserid());
				ch.setUpdater(user.getUserid());
				ChargingItem cs=findChargingItem(Integer.parseInt(id[i]));
				ch.setCharging_item_code(cs.getItem_code());
				this.pm.save(ch);
			}
		}
	}

	public void  deleteItemSchedule(ChargingItemModel model) throws ServiceException {
		ChargingItemSchedule ch = (ChargingItemSchedule) this.qm.get(ChargingItemSchedule.class,model.getId());
		this.pm.remove(ch);
	}

	public void updateItemSchedule(ChargingItemModel model, UserDTO user) throws ServiceException {
		ChargingItemSchedule ch = (ChargingItemSchedule) this.qm.get(ChargingItemSchedule.class,model.getId());
		ch.setSchedule_number(model.getSchedule_number());
		ch.setSchedule_time(DateTimeUtil.parse0(model.getSchedule_time()));
		ch.setUpdater(user.getUserid());
		ch.setUpdate_time(DateTimeUtil.parse());
		this.pm.update(ch);
	}

	@Override
	public ChargingItem updateSetChargingItem(ChargingItem item) throws ServiceException {
		String hql = " FROM  SetChargingItem  WHERE  charging_item_id = "+item.getId();
		List<SetChargingItem> li = this.qm.find(hql);
		for (int i = 0 ; i < li.size() ; i++) {
			SetChargingItem sci = li.get(i);
			sci.setItem_amount(item.getAmount());
			sci.setAmount(sci.getDiscount() * sci.getItem_amount() * 0.1);
			sci.setCharging_item_code(item.getItem_code());
			this.pm.update(sci);

			String hql2 = " from SetChargingItem where exam_set_id='" + sci.getExam_set_id() + "' ";
			List<SetChargingItem> list = this.qm.find(hql2);
			double price = 0.0;
			double set_amount = 0.0;
			for (SetChargingItem ci : list) {
				price += ci.getItem_amount();
				set_amount += ci.getAmount();
			}
			ExamSet examSet = (ExamSet)this.qm.load(ExamSet.class, sci.getExam_set_id());
			examSet.setPrice(price);
			examSet.setSet_amount(set_amount);
			this.pm.update(examSet);
		}
		return item;
	}
	@Override
	public ChargingItem updateChargingItemStopOrStart(ChargingItem ci) throws ServiceException {
		this.pm.update(ci);
		return ci;
	}

	@Override
	public String getItemClassName(String itemclass,String hiscodeclass)throws ServiceException{
		String name = "";
		Connection connection = null;
		try {
			String sql = "select data_name from data_dictionary d where d.data_code='HISZLLB'  and d.data_code_children='"+itemclass+"'";
			connection = this.jqm.getConnection();
			ResultSet rs1 = connection.createStatement().executeQuery(sql);
			if (rs1.next()) {
				name=rs1.getString("data_name");
			}
			rs1.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return name;
	}
	
	@Override
	public List<ChargingItemDTO> getChargingItemLb(long id) throws ServiceException {
		String sql =" SELECT * FROM   charging_item  c   where       c.isActive='Y'  "
				 +" AND c.item_type=(SELECT h.item_type FROM charging_item   h  where h.id='"+id+"'  AND  h.isActive='Y')  AND   c.id <>'"+id+"' "
				 		+ "  and      ( c.item_type <>''  OR    c.item_type  <> NULL  )  ";
				List<ChargingItemDTO> li = this.jqm.getList(sql, ChargingItemDTO.class);
		return li;
	}
	
	/**
	 * 
	     * @Title: updateHisCharginigItem   
	     * @Description: 更新his关联码   
	     * @param: @param dto
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: String      
	     * @throws
	 */
	public String updateHisCharginigItem(HisClinicItemPriceListDTO dto,long userid) throws ServiceException{
		String message="";
		ChargingItem ci= new ChargingItem();
		ci=this.findChargingItem(dto.getId());
		if((ci==null)||(ci.getId()<=0)){
			message="error-无效的收费项目";
		}else{
			ci.setHis_num(dto.getCharge_item_code());
			ci.setHiscodeClass(dto.getHisCode_class());
			ci.setItem_class(dto.getCharge_item_class());
			ci.setAmount(dto.getDamount());
			ci.setUpdater(userid);
			ci.setUpdate_time(DateTimeUtil.parse());
			this.pm.save(ci);
			message="ok-更新完成";
		}
		return message;
	}
	
	/**
	 * 
	     * @Title: getHisPriceList   
	     * @Description: 获取价表列表  
	     * @param: @param dto
	     * @param: @param page
	     * @param: @param pageSize
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<HisClinicItemPriceListDTO>      
	     * @throws
	 */
	public PageReturnDTO getHisPriceList(HisClinicItemPriceListDTO dto, int page, int pageSize) throws ServiceException {
		String sql = "SELECT p.item_class as item_class_p,p.item_code as item_code_p,p.item_spec as charge_item_spec,p.item_name  as item_name_p,d.data_name as item_class_name"
				+ ",p.units,p.price from his_price_list  p "
				+ " left join data_dictionary d on d.data_code='HISJBLB' and d.data_code_children=p.item_class"
				+ " where is_active<>'N' ";
		if (dto.getItem_name_c() != null && !"".equals(dto.getItem_name_c())) {
			sql += "  and  p.item_name  like '%" + dto.getItem_name_c().trim() + "%' ";
		}
		if (dto.getItem_code_c() != null && !"".equals(dto.getItem_code_c())) {
			sql += "  and p.item_code='" + dto.getItem_code_c().trim() + "' ";
		}
		if (dto.getCharge_item_class() != null && !"".equals(dto.getCharge_item_class())) {
			sql += "  and   p.item_class='" + dto.getCharge_item_class().trim() + "'";
		}
		sql += "  order by  p.item_name";
		PageSupport map = (PageSupport) this.jqm.getList(sql, page, pageSize, HisClinicItemPriceListDTO.class);
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
	public List<ChargingItemDTO>  getChargingItemListByDepId(long dep_id,String com_id) throws ServiceException {
		String sql = "select  id,item_name ,item_code  from charging_item  where dep_id = "+dep_id+"  and isActive = 'Y' order by item_seq asc";
		List<ChargingItemDTO> list = this.jqm.getList(sql, ChargingItemDTO.class);
		return list;
	}

	@Override
	public ChargingItem getItemNameCheck(ChargingItemModel model) throws ServiceException {
		String sql = "select  id ,item_code,item_name from charging_item  where isActive = 'Y' and item_name = '"+model.getItem_name()+"'";
		List<ChargingItem> list = this.jqm.getList(sql, ChargingItem.class);
		ChargingItem chargingItem = new ChargingItem();
		if (list.size() > 0) {
			chargingItem = list.get(0);
		}
		return chargingItem;
	}

	@Override
	public String saveSeleteItem(ChargingItemModel model,UserDTO user) throws ServiceException {
		String c_item_code = "'"+model.getItem_code_s().replace(",","','")+"'";
		String sql_center_item = " SELECT cc.charging_item_code FROM charging_item_vs_center cc where  cc.charging_item_code in("+c_item_code+")  and  cc.center_num='"+user.getCenter_num()+"'";
		List<ChargingItemDTO> center_item = this.jqm.getList(sql_center_item, ChargingItemDTO.class);
		
		String sql = " select [id],[dep_id],[sam_demo_id],[sam_report_demo_id],[item_code],[item_name],[item_pinyin],[item_category] "
				+" ,[sex],[amount],[dep_category],[isOnlyApplyOrReport],[item_seq],[guide_category],[his_num],[exam_num],[view_num]"
				+" ,[isActive],[creater],[create_time],[updater],[update_time],[calculation_amount],[interface_flag],[item_type]"
				+" ,[charge_inter_num],[item_abbreviation],[notices],[item_note],[perform_dept],[item_class],[synchro],[synchrodate]"
				+" ,[remark],[finance_class],[limit_num],[charging_item_number],[app_type],[calculation_rate],[hiscodeClass]"
				+" ,[item_discount] from  charging_item  where  isActive='Y'  AND item_code in("+c_item_code+")";
		List<ChargingItemDTO> list = this.jqm.getList(sql, ChargingItemDTO.class);
		
		List<ChargingItemDTO> li = new ArrayList<ChargingItemDTO>();
		for(int i = 0 ; i< list.size() ; i++){
			boolean status = true;
			for(int j = 0 ; j < center_item.size() ; j++){
				if(list.get(i).getItem_code().equals(center_item.get(j).getCharging_item_code())){
					status = false;
					break;
				}
			}
			if(status){
				li.add(list.get(i));
			}
		}
		ChargingItemVScenter cct = new ChargingItemVScenter();
		ChargingItemVScenterPKID pkid = new ChargingItemVScenterPKID();
		for(int  i = 0 ; i < li.size() ; i++){
			cct = new ChargingItemVScenter();
			pkid = new ChargingItemVScenterPKID();
			pkid.setCenter_num(user.getCenter_num());
			pkid.setCharging_item_code(li.get(i).getItem_code());
			cct.setChargingItemVScenterPKID(pkid);
			
			cct.setCalculation_amount(li.get(i).getCalculation_amount());
			cct.setCalculation_rate(li.get(i).getCalculation_rate());
			cct.setCenter_price(li.get(i).getAmount());
			cct.setCerate_time(DateUtil.parse());
			cct.setCharge_inter_num(li.get(i).getCharge_inter_num());
			cct.setCharging_item_number(li.get(i).getCharging_item_number());
			cct.setCreater(li.get(i).getCreater());
			cct.setExam_num(li.get(i).getExam_num());
			cct.setHis_num(li.get(i).getHis_num());
			cct.setInterface_flag(li.get(i).getInterface_flag());
			cct.setItem_discount(li.get(i).getItem_discount());
			cct.setLimit_num(li.get(i).getLimit_num());
			cct.setPerform_dept(li.get(i).getPerform_dept());
			cct.setPrice(li.get(i).getAmount());
			cct.setView_num(li.get(i).getView_num());
			this.pm.save(cct);
			//updatelimitChargingItem(li.get(i).getId(),li.get(i).getLimit_count(),user);
			
			String hql = " FROM  LimitChargingItem   WHERE  charging_item_id='" + li.get(i).getId() + "'";
			List<LimitChargingItem> liitem = this.qm.find(hql);
			if (liitem.size() > 0) {
				liitem.get(0).setCenter_num(user.getCenter_num());
				liitem.get(0).setCharging_item_id(li.get(i).getId());
				liitem.get(0).setLimit_count(li.get(i).getLimit_count());
				liitem.get(0).setUpdater(user.getUserid());
				liitem.get(0).setUpdate_time(DateTimeUtil.parse());
				this.pm.update(liitem.get(0));
			} else {
				LimitChargingItem it = new LimitChargingItem();
				it.setCenter_num(user.getCenter_num());
				it.setCharging_item_id(li.get(i).getId());
				it.setLimit_count(li.get(i).getLimit_count());
				it.setUpdater(user.getUserid());
				it.setUpdate_time(DateTimeUtil.parse());
				this.pm.save(it);
			}
		}
		return "ok";
	}

	@Override
	public PageReturnDTO queryChargingItemCenter(ChargingItemDTO cha, int pagesize, int page, String ks,
			String exam_item_id, UserDTO user) throws ServiceException {
		boolean fd = true;
		String sql = "SELECT  distinct(charging_item.item_code),sx.limit_count,charging_item.id,charging_item.dep_id,charging_item.sam_demo_id,"
				+ "charging_item.sam_report_demo_id,charging_item.item_code,charging_item.item_name, charging_item.item_discount,"
				+ "charging_item.item_pinyin,charging_item.item_category,charging_item.sex,"
				+ " vc.center_price  amount,charging_item.dep_category,charging_item.isOnlyApplyOrReport,"
				+ "charging_item.item_seq,charging_item.guide_category,vc.his_num,"
				+ "vc.exam_num,vc.view_num,charging_item.isActive,vc.creater,"
				+ "vc.cerate_time,vc.updater,vc.update_time,"
				+ "vc.calculation_amount,vc.calculation_rate,vc.interface_flag,charging_item.item_type,vc.charging_item_number,"
				+ "vc.charge_inter_num,charging_item.item_abbreviation,charging_item.notices,"
				+ "charging_item.item_note,charging_item.hiscodeClass,vc.perform_dept,"
				+ "de.dep_name as d_name,sa.demo_name as s_name,mo.demo_name as report_name,di.data_name as dname,"
				+ " his_dict_dept.dept_name,df.data_name  as  f_name"
				+ " FROM charging_item_vs_center vc"
				+ " LEFT JOIN charging_item on vc.charging_item_code = item_code  "
				+ "  LEFT JOIN department_dep de on charging_item.dep_id=de.id"
				+ "  LEFT JOIN department_vs_center ded on charging_item.dep_id=ded.dep_id  "
				+ " LEFT JOIN sample_demo sa on charging_item.sam_demo_id=sa.id "
				+ " LEFT JOIN sample_report_demo mo on charging_item.sam_report_demo_id=mo.id"
				+ " LEFT JOIN data_dictionary as di  on charging_item.dep_category=di.id "
				+ " LEFT JOIN his_dict_dept on charging_item.perform_dept=his_dict_dept.dept_code"
				+ " LEFT JOIN  limit_charging_item  sx   on   sx.charging_item_code=charging_item.item_code"
				+ " LEFT JOIN data_dictionary as df  on charging_item.finance_class =df.id "
				+ " WHERE 1=1 and  vc.charging_item_code = charging_item.item_code and vc.center_num = '"+user.getCenter_num()+"'  and ded.center_num ='"+user.getCenter_num()+"' ";
		if (cha.getStartStop() != null && !"".equals(cha.getStartStop())) {// 收费编号
			if(cha.getStartStop().length()==1) {
				sql += " AND charging_item.isActive IN ('"+cha.getStartStop()+"') ";
			}else {
				sql += " AND charging_item.isActive IN ('Y','N') ";
			}
		}
		if (cha.getDemo_type() != 0) {
			sql += " AND  sa.demo_type ='" + cha.getDemo_type() + "'";
		}
		if (cha.getItem_code() != null && !cha.getItem_code().equals("")) {// 收费编号
			sql += " and item_code='" + cha.getItem_code().trim() + "'";
		}
		if (cha.getItem_name() != null && !cha.getItem_name().equals("")) {// 收费名称
			sql += " and (item_name  like '%" + cha.getItem_name().trim() + "%' or item_pinyin like '%"
					+ cha.getItem_name().trim() + "%') ";
		}
		if (cha.getView_num() != null && !cha.getView_num().equals("")) {// 影像编号
			sql += " and vc.view_num='" + cha.getView_num().trim() + "'";
		}
		if (cha.getExam_num() != null && !cha.getExam_num().equals("")) {// 检验编码
			sql += " and vc.exam_num='" + cha.getExam_num().trim() + "'";
		}
		if (cha.getDep_id() > 0) {// 所属科室
			sql += " and charging_item.dep_id='" + cha.getDep_id() + "'";
		}
		if (ks != null && !"".equals(ks)) {
			sql += " and charging_item.dep_id  in(select  et.id  from   department_dep  et LEFT JOIN department_vs_center ded on et.id=ded.dep_id    where   et.isActive='Y'   and ded.center_num ='"+user.getCenter_num()+"'  AND     et.dep_category='"
					+ ks + "' )";
			sql += "  and   charging_item.sam_demo_id  is  NULL ";
		}
		if (cha.getPerform_dept() != null && !cha.getPerform_dept().equals("")) {// 执行科室
			sql += " and vc.perform_dept='" + cha.getPerform_dept().trim() + "'";
		}
		if (cha.getInterface_flag() != null && !cha.getInterface_flag().equals("")) {// 标识
			sql += " and interface_flag='" + cha.getInterface_flag().trim() + "'";
		}
		if (cha.getGuide_category() != null && !cha.getGuide_category().equals("")) {// 导引单
			sql += " and guide_category='" + cha.getGuide_category().trim() + "'";
		}
		if (cha.getHis_num() != null && !cha.getHis_num().equals("")) {// his编码
			sql += " and vc.his_num='" + cha.getHis_num().trim() + "'";
		}
		if (cha.getItem_pinyin() != null && !"".equals(cha.getItem_pinyin())) {
			sql += " and item_pinyin  like'%" + cha.getItem_pinyin().trim() + "%'   ";
		}
		if(cha.getItem_name_s()!=null && !"".equals( cha.getItem_name_s() )){
			sql += "   and (item_name  like'%" + cha.getItem_name_s().trim() + "%'   or  "
					+ "  item_code = '"+cha.getItem_name_s().trim()+"'  "
					+ "  or item_pinyin like '%'"+cha.getItem_name_s().trim()+"'%' )    ";
		}
		
		if (cha.getMccf().equals("项目名称重复")) {
			fd = false;
			sql += " and  charging_item.item_name   in   (select   charging_item.item_name   from"
					+ "  charging_item   where   charging_item.isActive='Y'  group   by   charging_item.item_name  having count(charging_item.item_name)>1)   ORDER BY   charging_item.item_name ";
		}
		if (exam_item_id != null && !"".equals(exam_item_id)) {
			sql += " and de.dep_category='21' and charging_item.id not in (select cii.charging_item_id from charging_item_exam_item cii,examination_item e where cii.item_code = e.item_num and e.item_result_type = 1)";
		}
		if (fd) {
			sql += "	ORDER BY charging_item.id DESC";
		}
		PageSupport map = (PageSupport) this.jqm.getList(sql, page, pagesize, ChargingItemDTO.class);
		PageReturnDTO webrole = new PageReturnDTO();
		webrole.setPage(page);
		webrole.setRp(pagesize);
		if ((map != null) && (map.getList() != null)) {
			webrole.setTotal(map.getRecTotal());
			webrole.setRows(map.getList());
		}
		return webrole;
	}

	@Override
	public ChargingItemDTO getchargingItemVsCenter(ChargingItemModel model, UserDTO user) throws ServiceException {
	String sql = " SELECT [center_num],[charging_item_code],[price],[calculation_amount],[calculation_rate] "
			   +"   ,[charging_item_number],[item_discount],[limit_num],[charge_inter_num],[interface_flag] "
			   +"   ,[center_price],[his_num],[exam_num],[view_num],[perform_dept],[creater],[cerate_time] "
			   +"   ,[updater],[update_time]  "
			   +"   FROM [charging_item_vs_center]  WHERE [charging_item_code] = '"+model.getItem_code()+"' AND "
	   		   + " [center_num] = '"+user.getCenter_num()+"'";
			  List<ChargingItemDTO> li = this.jqm.getList(sql, ChargingItemDTO.class);
			  if(li!=null && li.size()>0){
				  return li.get(0);
			  } else {
					return null;
			  }
	}

	@Override
	public boolean updateChargingItemCenter(ChargingItemModel model, UserDTO user) throws ServiceException{
		Connection connection = null;
		boolean fal = false;
		updatelimitChargingItem(model.getId(),model.getLimit_count(),user);
		try {
			connection = this.jqm.getConnection();
		    String sql = " update charging_item_vs_center set charge_inter_num = '"+model.getCharge_inter_num()+"',"
		    		+ "interface_flag='"+model.getInterface_flag()+"',his_num='"+model.getHis_num()+"',"
					+ "exam_num = '"+model.getExam_num()+"',view_num='"+model.getView_num()+"',"
					+ "perform_dept = '"+model.getPerform_dept()+"',calculation_amount="+model.getCalculation_amount()+","
					+ "calculation_rate="+model.getCalculation_rate()+",charging_item_number='"+model.getCharging_item_number()+"',"
					+ "item_discount = "+model.getItem_discount()+",limit_num="+model.getLimit_count()+","
		    		+ "center_price='"+model.getAmount()+"' WHERE "
		    		+ "  center_num='"+user.getCenter_num()+"'  AND charging_item_code = '"+model.getItem_code()+"' ";
			connection.createStatement().executeUpdate(sql);
			fal = true;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return fal;
	}

	@Override
	public boolean deleteChargingItemCenter(ChargingItemModel model, UserDTO user) throws ServiceException {
		Connection connection = null;
		boolean fal = false;
		try {
			connection = this.jqm.getConnection();
		    String sql = " delete from charging_item_vs_center   WHERE "
		    		+ "  center_num='"+user.getCenter_num()+"'  AND charging_item_code='"+model.getItem_code_s()+"'";
			connection.createStatement().executeUpdate(sql);
			fal = true;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return fal;
	}
}
