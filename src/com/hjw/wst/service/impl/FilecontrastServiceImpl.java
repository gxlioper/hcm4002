package com.hjw.wst.service.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.hjw.wst.DTO.CustomerfileinformationqueryDTO;
import com.hjw.wst.DTO.ExaminfoDiseaseDTO;
import com.hjw.wst.service.FilecontrastService;
import com.synjones.framework.exception.ServiceException;
import com.synjones.framework.persistence.JdbcPersistenceManager;
import com.synjones.framework.persistence.JdbcQueryManager;
import com.synjones.framework.persistence.PersistenceManager;
import com.synjones.framework.persistence.QueryManager;

/**
 * 
     * @Title:  火箭蛙体检管理系统   
     * @Package com.hjw.wst.service.impl   
     * @Description:  档案信息
     * @author: zr     
     * @date:   2016年11月14日 下午4:24:03   
     * @version V2.0.0.0
 */
public class  FilecontrastServiceImpl   implements  FilecontrastService{
	
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
	public List<CustomerfileinformationqueryDTO> queryCustomerInfo(CustomerfileinformationqueryDTO dto, String center_num) throws ServiceException {
		
	//体检信息
    String sql="SELECT   top  3 fo.id,c.arch_num,fo.exam_num,fo.status,fo.register_date,c.id_num,c.user_name,"
    		+ "  c.sex,fo.age,fo.company,fo.address,fo.phone,fo.join_date  as  tj_time,a.amount"
    		+ "   FROM exam_info fo   "
		    + "   LEFT JOIN customer_info c on   fo.customer_id=c.id    and    c.is_Active='Y'   "
//		    + "  left JOIN  examinfo_set  eset  on   fo.id=eset.examinfo_id   and   eset.isActive='Y'"
//		    + "   left JOIN  exam_set   xset  on     xset.id=eset.exam_set_id    and  xset.is_Active='Y'
			+" LEFT JOIN (select sum(ec.amount) amount,ec.exam_num  from examinfo_charging_item ec where ec.isActive = 'Y' and ec.pay_status <> 'M' and ec.center_num = '"+center_num+"' "
			+"	   group by ec.exam_num) a on a.exam_num= fo.exam_num "
		    + "   where    fo.is_Active='Y'"
		    + "    and  fo.status='Z'   and   fo.customer_id = '"+dto.getId()+"'"
		    + "    ORDER BY  fo.join_date  desc  ";
		List<CustomerfileinformationqueryDTO>   li = this.jqm.getList(sql,CustomerfileinformationqueryDTO.class);
		
		
		if(li.size()>0){
			Connection connection = null;
			
			String s_name ="";
			try {
				for (int i = 0; i<li.size(); i++) {
					sql="";
					s_name="";
					sql+="SELECT dbo.GetExamSetNameByExamID('"+li.get(i).getExam_num()+"')";
					connection = this.jqm.getConnection();
					ResultSet rs = connection.createStatement().executeQuery(sql);
					if(rs.next()){
						s_name=rs.getString(1);
						li.get(i).setTj_exam_set(s_name);
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
		
		
		
		if( li != null ){
			if( li.size()>0 ){
				String yxzb = "";
				for (CustomerfileinformationqueryDTO customerfileinformationqueryDTO : li) {
					yxzb += customerfileinformationqueryDTO.getId()+",";
				}
				yxzb = yxzb.substring(0,yxzb.length()-1);
				
				//体检次数
				  sql=" SELECT  cishu=count(*) FROM  exam_info fo "
					 +" LEFT JOIN  customer_info cu  on  fo.customer_id=cu.id  where  "
					 +"	  fo.is_Active='Y'  and   cu.is_Active='Y'  and  fo.status='Z' "
					 +"	  and  fo.customer_id='"+dto.getId()+"' ";
				List<CustomerfileinformationqueryDTO>   cishu = this.jqm.getList(sql,CustomerfileinformationqueryDTO.class);  
				
				//阳性指标
				  sql=" SELECT se.exam_info_id,se.disease_name,se.disease_index FROM "
			    		+ " examinfo_disease se where se.isActive='Y'  and   se.disease_type='Y'   and"
			    		+ " exam_info_id in ("+yxzb+")  ORDER BY  se.disease_index";
			    List<ExaminfoDiseaseDTO>  to = this.jqm.getList(sql,ExaminfoDiseaseDTO.class);
			    String  yx="";
				 
			    //参检用户
				 for (int i = 0; i < li.size(); i++) {
					 yx="";
					 //阳性指标
					 if( to != null ){
						 for (int j = 0; j < to.size(); j++) {
							 if( li.get(i).getId() == to.get(j).getExam_info_id()){
								 yx+="("+to.get(j).getDisease_index()+")"+to.get(j).getDisease_name()+";";
							 }
						 }
					     li.get(i).setYxzb(yx);
					 }
				  li.get(i).setCishu(cishu.get(0).getCishu());
				}
			} 
		}
		return li;
		
	}

	@Override
	public CustomerfileinformationqueryDTO getCustomerInfo(CustomerfileinformationqueryDTO dto,String center_num) throws ServiceException {
		CustomerfileinformationqueryDTO cus = new CustomerfileinformationqueryDTO();
		//套餐
		String sql="";
		Connection connection = null;
			try {
				sql+="SELECT  es.set_name as  tj_exam_set,ef.amount  as  examSet_amount FROM exam_info fo,examinfo_set ef,exam_set  es "
						+"where ef.exam_num=fo.exam_num  and   ef.exam_set_id=es.id and ef.isActive='Y' and es.is_Active='Y' and  fo.is_Active='Y'"
						+" and es.center_num='"+center_num+"' and  fo.exam_num='"+dto.getExam_num()+"'";
				connection = this.jqm.getConnection();
				ResultSet rs = connection.createStatement().executeQuery(sql);
				if(rs.next()){
					cus.setTj_exam_set(rs.getString("tj_exam_set"));
					cus.setExamSet_amount(rs.getDouble("examSet_amount"));
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
			//获取体检次数
		   String sqll="";
		    try {
			sqll+="SELECT count(*) FROM  exam_info fo"
				   +" LEFT JOIN  customer_info cu  on  fo.customer_id=cu.id  where  "
				   + " fo.is_Active='Y'  and   cu.is_Active='Y'  and  fo.status='Z'"
				   + "  and  cu.arch_num='"+dto.getArch_num().trim()+"'";
			      connection = this.jqm.getConnection();
				 ResultSet rs = connection.createStatement().executeQuery(sqll);
				 if(rs.next()){
					cus.setNum(rs.getString(1)); //体检次数
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
		    //阳性指标
		    String sqlll=" SELECT se.disease_name,se.disease_index FROM "
		    		+ " examinfo_disease se where se.isActive='Y'  and  "
		    		+ " exam_info_id='"+dto.getId()+"'  ORDER BY  se.disease_index";
		    List<ExaminfoDiseaseDTO>  to = this.jqm.getList(sqlll,ExaminfoDiseaseDTO.class);
		    String  yx="";
		    if(to.size()>0){
		    	for (ExaminfoDiseaseDTO dt : to) {
					yx+="("+dt.getDisease_index()+")"+dt.getDisease_name()+",";
				}
		    }
		    cus.setYxfx(yx);
			return cus;
	}

}
