package com.hjw.wst.service.impl;

import com.fr.third.org.hsqldb.lib.StringUtil;
import com.hjw.crm.DTO.CompanyInfoDTO;
import com.hjw.crm.DTO.CrmCompanyContactsDTO;
import com.hjw.interfaces.util.DateUtil;
import com.hjw.util.Common;
import com.hjw.util.DateTimeUtil;
import com.hjw.wst.DTO.ChargingItemDTO;
import com.hjw.wst.DTO.CompanyDepartmentDTO;
import com.hjw.wst.DTO.CompanyInfoVsCenterDTO;
import com.hjw.wst.DTO.DataDictionaryDTO;
import com.hjw.wst.DTO.JobDTO;
import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.wst.DTO.TreeDTO;
import com.hjw.wst.DTO.UserDTO;
import com.hjw.wst.domain.CompanyDepartment;
import com.hjw.wst.domain.CompanyInfo;
import com.hjw.wst.domain.DataDictionary;
import com.hjw.wst.domain.WebConfig;
import com.hjw.wst.model.CompanyInfoModel;
import com.hjw.wst.service.CompanyService;
import com.synjones.framework.exception.ServiceException;
import com.synjones.framework.persistence.JdbcPersistenceManager;
import com.synjones.framework.persistence.JdbcQueryManager;
import com.synjones.framework.persistence.PersistenceManager;
import com.synjones.framework.persistence.QueryManager;
import com.synjones.framework.support.PageSupport;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

/**
 * 
     * @Title:  火箭蛙体检管理系统   
     * @Package com.hjw.wst.service.impl   
     * @Description:  体检单位管理
     * @author: yangm     
     * @date:   2016年7月4日 上午9:37:08   
     * @version V2.0.0.0
 */
public class CompanyServiceImpl implements CompanyService {
	private QueryManager qm;
	private JdbcQueryManager jqm;
	private JdbcPersistenceManager jpm;
	private PersistenceManager pm;
	private CompanyService companyService;
	
	public CompanyService getCompanyService() {
		return companyService;
	}

	public void setCompanyService(CompanyService companyService) {
		this.companyService = companyService;
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
	
	/**
	 * 
	     * @Title: GetCreateID   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param com_level 单位级别
	     * @param: @param parent_com_id 父级单位编码(一级单位传000)
	     * @param: @param param_com_num
	     * @param: @return      
	     * @return: String      
	     * @throws
	 */
public String GetCreateID(long com_level,long parent_com_id,String param_com_num) {
		
		String typeid="";
		Connection connection = null;
		try{
			connection = this.jqm.getConnection();
			if(com_level==1){
				param_com_num="000";
			}
			//获取档案号
			CallableStatement c=connection.prepareCall("{call pro_company_num(?,?,?,?)}");  
			c.setLong(1, com_level);		
			c.setLong(2, parent_com_id);	
			c.setString(3, param_com_num);	
			//注册存储过程的第二个参数  
			c.registerOutParameter(4,java.sql.Types.VARCHAR);  
			//执行存储过程  
			c.execute();  			 
			//得到存储过程的输出参数值  
			typeid =c.getString(4);  
			}catch(Exception ex){
				ex.printStackTrace();
			}finally{
				try {
					
					if (connection != null) {
						connection.close();
					}
				} catch (SQLException sqle4) {
					sqle4.printStackTrace();
				}
			}
		return typeid;
	}

	/**
	 * 
	     * <p>Title: getComByFatherID</p>   
	     * <p>通过father获取单位 </p>   
	     * @param father
	     * @return
	     * @throws ServiceException   
	     * @see com.hjw.wst.service.CompanyService#getComByFatherID(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public List<CompanyInfo> getComByFatherID(long father) throws ServiceException {
		return qm.find("  from CompanyInfo  where is_Active='Y' and parent_com_id='" + father + "' order by id desc");
	}
	
	/**
	 * 
	     * <p>Title: getComByFatherID</p>   
	     * <p>通过id获取单位 </p>   
	     * @param father
	     * @return
	     * @throws ServiceException   
	     * @see com.hjw.wst.service.CompanyService#getComByFatherID(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public CompanyInfo findComByID(long id) throws ServiceException{
		List<CompanyInfo> list = new ArrayList<CompanyInfo>();
		list = qm.find("  from CompanyInfo  where id='" + id + "' order by id desc");
		if (list != null && list.size() > 0){
			return (CompanyInfo)list.get(0);
		}else 
			return null;
	}
	
	public CompanyInfo findComBySignNum(String sign_num) throws ServiceException{
		String sql=" select s.id,s.com_name,cr.contacts_name  as contact_name,cr.telephone as contact_phone  from company_info s left join crm_sign_bill_plan c on "
				+ "  c.company_id=s.id "
				+ "   left join crm_company_contacts cr on cr.company_id=s.id "
				+ " where c.sign_num='" + sign_num + "' order by s.id desc";
		System.out.println(sql);
		Connection connection = null;
		Statement statement = null;
		List<CompanyInfo> list = new ArrayList<CompanyInfo>();
		try {
			// 读取记录数
			connection = this.jqm.getConnection();
			statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			while (rs.next()) {
				CompanyInfo companyInfo=new CompanyInfo();
				companyInfo.setId(Long.valueOf(rs.getString("id")));
				companyInfo.setCom_Name(rs.getString("com_name"));
				companyInfo.setContact_Name(rs.getString("contact_name"));
				companyInfo.setContact_Phone(rs.getString("contact_phone"));
				list.add(companyInfo);
			}
			rs.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			try {
				if (statement != null) {
					statement.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException sqle4) {
				sqle4.printStackTrace();
			}
		}
		return list.get(0);
	}
		
	/**
	 * 
	     * @Title: findComByComNum   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param comnum
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: int      
	     * @throws
	 */
	public int findComByComNum(long comid,String comnum,int edittype) throws ServiceException{
		List<CompanyInfo> list = new ArrayList<CompanyInfo>();
		if(edittype==3){
			list = qm.find("  from CompanyInfo  where is_Active='Y' and id<>"+comid+" and com_num='" + comnum + "' order by id desc");
		}else{
		    list = qm.find("  from CompanyInfo  where is_Active='Y' and com_num='" + comnum + "' order by id desc");
		}
		if (list == null){
			return 0;
		}else{
			return list.size();
		}	
	}
	
	/**
	 * 
	     * @Title: findComByName   
	     * @Description: 通过部门名称获取编号   
	     * @param: @param comname
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: CompanyInfo      
	     * @throws
	 */
	public CompanyInfo findComByName(String comname) throws ServiceException{
		List<CompanyInfo> list = new ArrayList<CompanyInfo>();
		list = qm.find("  from CompanyInfo  where is_Active='Y' and com_Name='" + comname + "' order by id desc");
		if (list != null && list.size() > 0){
			return (CompanyInfo)list.get(0);
		}else 
			return null;
	}
	/**
	 * 
	     * <p>Title: getComByFatherID</p>   
	     * <p>通过id获取单位 </p>   
	     * @param father
	     * @return
	     * @throws ServiceException   
	     * @see com.hjw.wst.service.CompanyService#getComByFatherID(java.lang.String)
	 */
	public CompanyInfo getComByID(long id) throws ServiceException {
		return (CompanyInfo)qm.load(CompanyInfo.class, id);
	}
	
	
	/**
	 * 
	     * @Title: saveCompanyInfo   
	     * @Description: 增加单位   
	     * @param: @param companyInfo
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: CompanyInfo      
	     * @throws
	 */
	public CompanyInfo saveCompanyInfo(CompanyInfo companyInfo,UserDTO user) throws ServiceException {
		CompanyInfo ci = new CompanyInfo();
		String center_num = user.getCenter_num();
		UserDTO user2 = new UserDTO();
		//user.setCenter_num("");
		ci = companyService.getComByName(companyInfo.getParent_com_id(),companyInfo.getCom_Name().trim(),user2);
		//其他体检中心无此单位则添加，有则直接提取
		if(ci == null || "".equals(ci.getCom_Name())){
			this.pm.save(companyInfo);
		} else {
			companyInfo.setId(ci.getId());
		}
		String sql = "insert into company_info_vs_center(company_id,center_num,is_owner,creater,create_time,contact_name,"
				+ "contact_phone,com_phone,com_jianjie,com_fax,com_type,keShi_Name,email,address,remark) "
					+" values("+companyInfo.getId()+",'"+center_num+"','1',"+user.getUserid()+",'"+DateTimeUtil.getDateTime()+"',"
							+ "'"+companyInfo.getContact_Name()+"','"+companyInfo.getContact_Phone()+"','"+companyInfo.getCom_phone()+"',"
							+ "'"+companyInfo.getCom_jianjie()+"','"+companyInfo.getCom_fax()+"','"+companyInfo.getCom_Type()+"',"
							+ "'"+companyInfo.getKeShi_Name()+"','"+companyInfo.getEmail()+"','"+companyInfo.getAddress()+"',"
							+ "'"+companyInfo.getRemark()+"') ";
		System.out.println(sql);
		Connection connection = null;
		Statement statement = null;
		List<CompanyInfo> list = new ArrayList<CompanyInfo>();
		try {
			// 读取记录数
			connection = this.jqm.getConnection();
			statement = connection.createStatement();
			statement.executeUpdate(sql);
		} catch (SQLException ex) {
			ex.printStackTrace();
			
				try {
					if (statement != null) {
						statement.close();
					}
					if (connection != null) {
						connection.close();
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			throw new ServiceException(ex.toString());
		} finally {
			try {
				if (statement != null) {
					statement.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException sqle4) {
				sqle4.printStackTrace();
			}
		}
		return companyInfo;
	}
	
	/**
	 * 
	     * @Title: saveCompanyInfo   
	     * @Description: 修改单位   
	     * @param: @param companyInfo
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: CompanyInfo      
	     * @throws
	 */
	public CompanyInfo updateCompanyInfo(CompanyInfo companyInfo,UserDTO user) throws ServiceException{
	    String s_sql = " SELECT ci_cv.center_num FROM company_info ci,company_info_vs_center ci_cv " +
                "WHERE  ci.id = ci_cv.company_id AND  ci.is_Active = 'Y'  " +
                "  and com_name='"+companyInfo.getCom_Name()+"' ";
	    List<CompanyInfoDTO> li = this.jqm.getList(s_sql,CompanyInfoDTO.class);
	    boolean flag = false;
	    if(li!=null && li.size()>0){
            for (CompanyInfoDTO l:li) {
                if(user.getCenter_num().equals(l.getCenter_num())){
                    flag = true;
                    break;
                }
            }
        }
	    //单位中间库，有体检中心有此单位则新增单位
	    if( !flag ){
            this.pm.save(companyInfo);
            String sql = "insert into company_info_vs_center(company_id,center_num,is_owner,creater,create_time,contact_name,"
                    + "contact_phone,com_phone,com_jianjie,com_fax,com_type,keShi_Name,email,address,remark) "
                    +" values("+companyInfo.getId()+",'"+user.getCenter_num()+"','1',"+user.getUserid()+",'"+DateTimeUtil.getDateTime()+"',"
                    + "'"+companyInfo.getContact_Name()+"','"+companyInfo.getContact_Phone()+"','"+companyInfo.getCom_phone()+"',"
                    + "'"+companyInfo.getCom_jianjie()+"','"+companyInfo.getCom_fax()+"','"+companyInfo.getCom_Type()+"',"
                    + "'"+companyInfo.getKeShi_Name()+"','"+companyInfo.getEmail()+"','"+companyInfo.getAddress()+"',"
                    + "'"+companyInfo.getRemark()+"') ";
            System.out.println(sql);
            this.jpm.execSql(sql);
        //没有体检中心有此单位则修改
        } else {
            this.pm.update(companyInfo);
            String sql = " update company_info_vs_center set company_id="+companyInfo.getId()+",center_num='"+user.getCenter_num()+"',"
                    + "is_owner='0',creater="+user.getUserid()+",create_time = '"+DateTimeUtil.getDateTime()+"',contact_name='"+companyInfo.getContact_Name()+"',"
                    +"contact_phone='"+companyInfo.getContact_Phone()+"',com_phone='"+companyInfo.getCom_Name()+"',com_jianjie='"+companyInfo.getCom_jianjie()+"',"
                    + "com_fax='"+companyInfo.getCom_fax()+"',com_type='"+companyInfo.getCom_Type()+"',keShi_Name='"+companyInfo.getKeShi_Name()+"',"
                    + "email='"+companyInfo.getEmail()+"',address='"+companyInfo.getAddress()+"',remark='"+companyInfo.getRemark()+"'"
                    + "  where  center_num='"+user.getCenter_num()+"' AND company_id = "+companyInfo.getId();
            System.out.println(sql);
            Connection connection = null;
            Statement statement = null;
            this.jpm.execSql(sql);
        }
		return companyInfo;
	}

    @Override
    public void removeCompanyInfo(CompanyInfo companyInfo) throws ServiceException {
        this.pm.update(companyInfo);
    }


    /**
	 * 
	     * @Title: saveCompanyInfo   
	     * @Description: 单位级别修改   
	     * @param: @param companyInfo
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: CompanyInfo      
	     * @throws
	 */
	public boolean chargeCompanyInfo(CompanyInfo cif) throws ServiceException{
		Connection connection = null;
		Statement statement = null;
		long level= cif.getCom_Level();
		long level1 = level+1;
		long level2 = level+2;
		long level3 = level+3;
		long level4 = level+4;
		try {
			// 读取记录数
			//第1级
			connection = this.jqm.getConnection();
			connection.setAutoCommit(false);
			String sqltext0 = "update Company_Info set parent_com_id="+cif.getParent_com_id()+",com_level="+level+",updater="+cif.getUpdater()+",update_Time='"+DateTimeUtil.getDateTime()+"' where id=" + cif.getId();
			connection.createStatement().executeUpdate(sqltext0);

			//第2级
			Statement statement1 = null;
			statement1 = connection.createStatement();
			StringBuffer sqltext1= new StringBuffer();
					long comid1=0;
					sqltext1.append("SELECT id FROM company_info where is_Active='Y' and parent_com_id ="+cif.getId()+"");
					ResultSet rs1 = statement1.executeQuery(sqltext1.toString());
					while (rs1.next()) {						
						comid1=rs1.getInt("id");
						connection.createStatement().executeUpdate("update Company_Info set com_level="+level1+",updater="+cif.getUpdater()+",update_Time='"+DateTimeUtil.getDateTime()+"' where id=" + comid1);
						
						
                        //第3级
						Statement statement2 = null;
							statement2 = connection.createStatement();
							StringBuffer sqltext2= new StringBuffer();
							long comid2=0;
							sqltext2.append("SELECT id FROM company_info where is_Active='Y' and parent_com_id ="+comid1+"");
							ResultSet rs2 = statement2.executeQuery(sqltext2.toString());
							while (rs2.next()) {
								comid2=rs2.getInt("id");
								connection.createStatement().executeUpdate("update Company_Info set com_level="+level2+",updater="+cif.getUpdater()+",update_Time='"+DateTimeUtil.getDateTime()+"' where id=" + comid2);
								
								    //第4级
								    Statement statement3 = null;
									statement3 = connection.createStatement();
									StringBuffer sqltext3= new StringBuffer();
									long comid3=0;
									sqltext3.append("SELECT id FROM company_info where is_Active='Y' and parent_com_id ="+comid2+"");
									ResultSet rs3 = statement3.executeQuery(sqltext3.toString());
									while (rs3.next()) {
										comid3=rs3.getInt("id");
										connection.createStatement().executeUpdate("update Company_Info set com_level="+level3+",updater="+cif.getUpdater()+",update_Time='"+DateTimeUtil.getDateTime()+"' where id=" + comid3);

										//第5级
										Statement statement4 = null;
											statement4 = connection.createStatement();
											StringBuffer sqltext4= new StringBuffer();
											long comid4=0;
											sqltext4.append("SELECT id FROM company_info where is_Active='Y' and parent_com_id ="+comid2+"");
											ResultSet rs4 = statement4.executeQuery(sqltext4.toString());
											while (rs4.next()) {
												comid4=rs4.getInt("id");
												connection.createStatement().executeUpdate("update Company_Info set com_level="+level4+",updater="+cif.getUpdater()+",update_Time='"+DateTimeUtil.getDateTime()+"' where id=" + comid4);
											}
											rs4.close();
											statement4.close();
										
									}
									rs3.close();
									statement3.close();
								
								
							}
							rs2.close();
							statement2.close();
						
			}
			rs1.close();
			statement1.close();
			connection.commit();
			
		} catch (SQLException ex) {
			ex.printStackTrace();
			try{
			    connection.rollback();
			}catch(SQLException sqlex){
				sqlex.printStackTrace();
			}
		} finally {
			try {
				if (statement != null) {
					statement.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException sqle4) {
				sqle4.printStackTrace();
			}
		}
		return true;
	}
	
	/**
	 * 
	     * @Title: getComByLevel   
	     * @Description: TODO通过层级获取单位 
	     * @param: @param levels
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<CompanyInfo>      
	     * @throws
	 */
	@SuppressWarnings("unchecked")
	public List<CompanyInfo> getComByLevel(String levels) throws ServiceException {
		return qm.find("  from CompanyInfo  where is_Active='Y' and com_Level='" + levels + "' order by id desc");
	}
	
	/**
	 * 
	     * @Title: getComByName   
	     * @Description:根据单位名称查询单位
	     * @param: @param comname
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: CompanyInfo      
	     * @throws
	 */
	@SuppressWarnings("unchecked")
	public CompanyInfo getComByName(long father_id,String comname,UserDTO user) throws ServiceException {
		CompanyInfo ci = new CompanyInfo();
		List<CompanyInfo> list = new ArrayList<CompanyInfo>();
		String sql1 =" SELECT c.id   from company_info  c,company_info_vs_center vs  "
				     +" where  c.id = vs.company_id AND  c.is_Active='Y' AND   c.com_Name='" + comname + "' and c.parent_com_id="+father_id+"  ";
					 if(!StringUtils.isEmpty(user.getCenter_num())){
					 sql1 +="	 AND vs.center_num='"+user.getCenter_num()+"'";
					 }
				   
		List<CompanyInfo> li = this.jqm.getList(sql1,CompanyInfo.class);
		if(li!=null&&li.size()>0){
			ci = (CompanyInfo) qm.get(CompanyInfo.class,li.get(0).getId());
		}
//		String hql = " from CompanyInfo c"
//				+ " where  c.is_Active='Y' AND c.center_num = '"+user.getCenter_num()+"'   "
//				+ "  AND c.com_Name='" + comname + "' and c.parent_com_id="+father_id+" ";
//		list = qm.find(hql);
//		if((list!=null)&&(list.size()>0)){
//			ci=list.get(0);
//		}
		return ci;
	}
	
	/**
	 * 
	     * @Title: getCompanyDepartmentByFatherid   
	     * @Description: 获取部门下的单位   
	     * @param: @param company_Id
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<CompanyDepartment>      
	     * @throws
	 */
	@SuppressWarnings("unchecked")
	public List<CompanyDepartment> getCompanyDepartmentByComid(long company_Id) throws ServiceException {
		List<CompanyDepartment> list = new ArrayList<CompanyDepartment>();
		list = qm.find("  from CompanyDepartment  where company_Id='" + company_Id + "' order by id desc");
		return list;		
	}
	
	/**
	 * 
	     * @Title: getCompanyDepartmentById   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param id
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: CompanyDepartment      
	     * @throws
	 */
	public CompanyDepartment getCompanyDepartmentById(long id) throws ServiceException {
		List<CompanyDepartment> list = new ArrayList<CompanyDepartment>();
		CompanyDepartment cd = new CompanyDepartment();
		list = qm.find("  from CompanyDepartment  where id=" + id + " order by id desc");
		if((list!=null)&&(list.size()>0)){
			cd=(CompanyDepartment)list.get(0);
		}
		return cd;		
	}
	
	/**
	 * 
	     * @Title: getCompanyDepartmentByid   
	     * @Description: 通过单位id获取部门
	     * @param: @param com_id
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<CompanyDepartment>      
	     * @throws
	 */
	@SuppressWarnings("unchecked")
	public CompanyDepartment getCompanyDepartmentByid(long id) throws ServiceException {
		List<CompanyDepartment> list = new ArrayList<CompanyDepartment>();
		list = qm.find("  from CompanyDepartment  where id='" + id + "' order by id desc");
		if (list != null && list.size() > 0){
			return (CompanyDepartment)list.get(0);
		}else return null;
		
	}
	
	/**
	 * 
	     * @Title: getCompanyDepartmentByid   
	     * @Description: 获取部门  
	     * @param: @param com_id
	     * @param: @param dep_Name
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: CompanyDepartment      
	     * @throws
	 */
	@SuppressWarnings("unchecked")
	public CompanyDepartment getCompanyDepartmentByid(long com_id,String dep_Name) throws ServiceException {
		List<CompanyDepartment> list = new ArrayList<CompanyDepartment>();
		list = qm.find("  from CompanyDepartment  where company_Id=" + com_id + " "
				+ "and dep_Name='"+dep_Name+"'  order by id desc");
		if (list != null && list.size() > 0){
			return (CompanyDepartment)list.get(0);
		}else return null;
		
	}
	
	/**
	 * 
	     * @Title: getcompanyByNum   
	     * @Description: 通过单位模糊查询最大的级别  
	     * @param: @param com_num
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: int      
	     * @throws
	 */
	public int getcompanyById(long id)throws ServiceException{
		
		Connection connection = null;
		Statement statement = null;
		int count = 0;
		int count1 = 0;
		int count2 = 0;
		int count3 = 0;
		int count4 = 0;
		try {
			// 读取记录数
			connection = this.jqm.getConnection();
			statement = connection.createStatement();
			StringBuffer sqltext= new StringBuffer();
			sqltext.append("SELECT com_level FROM company_info where id ="+id+"");
			ResultSet rs = statement.executeQuery(sqltext.toString());
			if (rs.next()) {
				count = rs.getInt("com_level");

				Statement statement1 = null;
				try {
					// 读取记录数
					statement1 = connection.createStatement();
					StringBuffer sqltext1= new StringBuffer();
					long comid1=0;
					sqltext1.append("SELECT com_level,id FROM company_info where is_Active='Y' and parent_com_id ="+id+"");
					ResultSet rs1 = statement1.executeQuery(sqltext1.toString());
					while (rs1.next()) {
						count1 = rs1.getInt("com_level");
						comid1=rs1.getInt("id");
						
						Statement statement2 = null;
						try {
							// 读取记录数
							statement2 = connection.createStatement();
							StringBuffer sqltext2= new StringBuffer();
							long comid2=0;
							sqltext2.append("SELECT com_level,id FROM company_info where is_Active='Y' and parent_com_id ="+comid1+"");
							ResultSet rs2 = statement2.executeQuery(sqltext2.toString());
							
							while (rs2.next()) {
								count2 = rs2.getInt("com_level");
								comid2=rs2.getInt("id");
								

								Statement statement3 = null;
								try {
									// 读取记录数
									statement3 = connection.createStatement();
									StringBuffer sqltext3= new StringBuffer();
									long comid3=0;
									sqltext3.append("SELECT com_level,id FROM company_info where is_Active='Y' and parent_com_id ="+comid2+"");
									ResultSet rs3 = statement3.executeQuery(sqltext3.toString());
									while (rs3.next()) {
										count3 = rs3.getInt("com_level");
										comid3=rs3.getInt("id");
										
										Statement statement4 = null;
										try {
											// 读取记录数
											statement4 = connection.createStatement();
											StringBuffer sqltext4= new StringBuffer();
											//long comid4=0;
											sqltext4.append("SELECT com_level,id FROM company_info where is_Active='Y' and parent_com_id ="+comid3+"");
											ResultSet rs4 = statement4.executeQuery(sqltext4.toString());
											while (rs4.next()) {
												count4 = rs4.getInt("com_level");
												//comid4=rs4.getInt("id");
											}
											rs4.close();
										} catch (SQLException ex) {
											ex.printStackTrace();
										} finally {
											try {
												if (statement4 != null) {
													statement4.close();
												}
											} catch (SQLException sqle4) {
												sqle4.printStackTrace();
											}
										}
										
									}
									rs3.close();
								} catch (SQLException ex) {
									ex.printStackTrace();
								} finally {
									try {
										if (statement3 != null) {
											statement3.close();
										}
									} catch (SQLException sqle4) {
										sqle4.printStackTrace();
									}
								}
								
								
							}
							rs2.close();
						} catch (SQLException ex) {
							ex.printStackTrace();
						} finally {
							try {
								if (statement2 != null) {
									statement2.close();
								}
							} catch (SQLException sqle4) {
								sqle4.printStackTrace();
							}
						}
						
						
					}
					rs1.close();
				} catch (SQLException ex) {
					ex.printStackTrace();
				} finally {
					try {
						if (statement1 != null) {
							statement1.close();
						}
					} catch (SQLException sqle4) {
						sqle4.printStackTrace();
					}
				}
				
		
			}
			rs.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			try {
				if (statement != null) {
					statement.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException sqle4) {
				sqle4.printStackTrace();
			}
		}
		
		if(count4>0){
			return count4;
		}else if(count3>0){
			return count3;
		}else if(count2>0){
			return count2;
		}else if(count1>0){
			return count1;
		}else if(count>0){
			return count;
		}else{
			return count;
		}		  
	}
	
	/**
	 * 
	     * @Title: getcompanyParsentNodeById   
	     * @Description: 在customer_company保存要导入的部门的父级部门  并返回当前 selfcomid 在customer_company部门的id
	     * @param: @param selfcomid
	     * @param: @param rescomid
	     * @param: @param userid
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: long      
	     * @throws
	 */
	public void getcompanyParsentNodeById(long selfcomid,long rescomid,long userid,long centerid)throws ServiceException{ 
		Connection connection = null;
		Statement statement = null;
		long[] idsd=new long[5];
		int indexid=0;
		try {
			if(selfcomid==rescomid){
				idsd[indexid]=selfcomid;
			}else{
				idsd[indexid]=selfcomid;
				indexid=indexid+1;
				
			// 读取记录数
			connection = this.jqm.getConnection();
			statement = connection.createStatement();
			StringBuffer sqltext= new StringBuffer();
			sqltext.append("SELECT parent_com_id FROM company_info where  id ="+selfcomid+"");
			ResultSet rs1 = statement.executeQuery(sqltext.toString());
			long comid1=0;
			if (rs1.next()) {			
				comid1 = rs1.getLong("parent_com_id");
				idsd[indexid]=comid1;
				indexid=indexid+1;
				if(comid1!=rescomid){
				
				Statement statement2 = null;
					// 读取记录数
					statement2 = connection.createStatement();
					StringBuffer sqltext2= new StringBuffer();
					long comid2=0;
					sqltext2.append("SELECT parent_com_id FROM company_info where id ="+comid1+"");
					ResultSet rs2 = statement2.executeQuery(sqltext2.toString());
					if (rs2.next()) {
						comid2=rs2.getInt("parent_com_id");
						idsd[indexid]=comid2;
						indexid=indexid+1;						
						if(comid2!=rescomid){
				

						Statement statement3 = null;
							// 读取记录数
							statement3 = connection.createStatement();
							StringBuffer sqltext3= new StringBuffer();
							long comid3=0;
							sqltext3.append("SELECT parent_com_id FROM company_info where id="+comid2+"");
							ResultSet rs3 = statement3.executeQuery(sqltext3.toString());
							
							if (rs3.next()) {
								comid3=rs3.getInt("parent_com_id");
								idsd[indexid]=comid3;
								indexid=indexid+1;		
								if(comid3!=rescomid){
																
								
								Statement statement4 = null;
									// 读取记录数
									statement4 = connection.createStatement();
									StringBuffer sqltext4= new StringBuffer();
									long comid4=0;
									sqltext4.append("SELECT parent_com_id FROM company_info where is_Active='Y' and  id="+comid3+"");
									ResultSet rs4 = statement4.executeQuery(sqltext4.toString());
									if (rs4.next()) {
										comid4=rs4.getInt("parent_com_id");
										idsd[indexid]=comid4;
										indexid=indexid+1;		
										if(comid4!=rescomid){
															
										

										Statement statement5 = null;
											// 读取记录数
											statement5 = connection.createStatement();
											StringBuffer sqltext5= new StringBuffer();
											long comid5=0;
											sqltext5.append("SELECT parent_com_id FROM company_info where is_Active='Y' and id ="+comid4+"");
											ResultSet rs5 = statement5.executeQuery(sqltext5.toString());
											if (rs5.next()) {
												comid5=rs5.getInt("parent_com_id");
												idsd[indexid]=comid5;
												indexid=indexid+1;	
											}
											rs5.close();
										    statement5.close();
										}
											
									}
									rs4.close();
									statement4.close();
							}
								
								
							}
							rs3.close();
							statement3.close();
						}
						
						
					}
					rs2.close();
					statement2.close();
				
					}
		
			}
			rs1.close();
			statement.close();

			
			}
		} catch (SQLException ex) {
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
		
		long comid=0;
		try {
			connection = this.jqm.getConnection();
			Statement statement1 = null;
			statement1 = connection.createStatement();
		for(int i=0;i<idsd.length;i++){
			long com_id = idsd[i];
			if(com_id>0)
			{      
				
				StringBuffer sqltext1= new StringBuffer();
				long comid1=0;
				sqltext1.append("SELECT id FROM customer_company where com_id ="+com_id+" and batchcom_id="+rescomid+"");
				ResultSet rs1 = statement1.executeQuery(sqltext1.toString());
				if (!rs1.next()) {					
					connection.createStatement().executeUpdate("insert into customer_company (batchcom_id,center_id,com_id,creater,create_time,updater,update_time,remark,remark2)"
					+ " values("+rescomid+","+centerid+","+com_id+"," + userid + ",'" + DateTimeUtil.getDateTime() + "',"	+ userid + ",'" + DateTimeUtil.getDateTime() + "','','')");   
				}
				rs1.close();
				
		     }
		    }
		    
		/*StringBuffer sqltext1= new StringBuffer();
		sqltext1.append("SELECT id FROM customer_company where com_id ="+selfcomid+" and center_id="+centerid+" and batchcom_id="+rescomid+"");
		ResultSet rs1 = statement1.executeQuery(sqltext1.toString());
		if (rs1.next()) {					
			comid=rs1.getInt("id");
		}
		rs1.close();		
		statement1.close();*/
		    
			} catch (SQLException ex) {
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
		//return comid;
		
	}
	
	/**
	 * 
	     * @Title: getcompanySubNodeById   
	     * @Description:   判断目标结点是否源结点的子结点，如果是 返回true，否则返回false
	     * @param: @param oldid
	     * @param: @param newid
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: boolean      
	     * @throws
	 */
	public boolean getcompanySubNodeById(long oldid,long newid)throws ServiceException{
		
		Connection connection = null;
		Statement statement = null;
		boolean count = false;
		boolean count1 = false;
		boolean count2 = false;
		boolean count3 = false;
		boolean count4 = false;
		boolean count5 = false;
		try {
			if(oldid==newid){
				count=true;
			}else{
			// 读取记录数
			connection = this.jqm.getConnection();
			statement = connection.createStatement();
			StringBuffer sqltext= new StringBuffer();
			sqltext.append("SELECT id FROM company_info where is_Active='Y' and parent_com_id ="+oldid+"");
			ResultSet rs1 = statement.executeQuery(sqltext.toString());
			while (rs1.next()) {
				long comid1=0;
				comid1 = rs1.getLong("id");
				if(comid1==newid){
					count1=true;
					break;
				}
				Statement statement2 = null;
				try {
					// 读取记录数
					statement2 = connection.createStatement();
					StringBuffer sqltext2= new StringBuffer();
					long comid2=0;
					sqltext2.append("SELECT id FROM company_info where is_Active='Y' and parent_com_id ="+comid1+"");
					ResultSet rs2 = statement2.executeQuery(sqltext2.toString());
					while (rs2.next()) {
						comid2=rs2.getInt("id");
						if(comid2==newid){
							count2=true;
							break;
						}
						

						Statement statement3 = null;
						try {
							// 读取记录数
							statement3 = connection.createStatement();
							StringBuffer sqltext3= new StringBuffer();
							long comid3=0;
							sqltext3.append("SELECT id FROM company_info where is_Active='Y' and parent_com_id ="+comid2+"");
							ResultSet rs3 = statement3.executeQuery(sqltext3.toString());
							
							while (rs3.next()) {
								comid3=rs3.getInt("id");
								if(comid3==newid){
									count3=true;
									break;
								}
								
								
								Statement statement4 = null;
								try {
									// 读取记录数
									statement4 = connection.createStatement();
									StringBuffer sqltext4= new StringBuffer();
									long comid4=0;
									sqltext4.append("SELECT com_level,id FROM company_info where is_Active='Y' and parent_com_id ="+comid3+"");
									ResultSet rs4 = statement4.executeQuery(sqltext4.toString());
									while (rs4.next()) {
										comid4=rs4.getInt("id");
										if(comid4==newid){
											count4=true;
											break;
										}
										

										Statement statement5 = null;
										try {
											// 读取记录数
											statement5 = connection.createStatement();
											StringBuffer sqltext5= new StringBuffer();
											long comid5=0;
											sqltext5.append("SELECT com_level,id FROM company_info where is_Active='Y' and parent_com_id ="+comid4+"");
											ResultSet rs5 = statement5.executeQuery(sqltext5.toString());
											while (rs5.next()) {
												comid5=rs5.getInt("id");
												if(comid5==newid){
													count5=true;
													break;
												}
											}
											rs5.close();
										} catch (SQLException ex) {
											count=true;
											ex.printStackTrace();
										} finally {
											try {
												if (statement5 != null) {
													statement5.close();
												}
												
											} catch (SQLException sqle4) {
												sqle4.printStackTrace();
											}
										}
										
									}
									rs4.close();
								} catch (SQLException ex) {
									count=true;
									ex.printStackTrace();
								} finally {
									try {
										if (statement4 != null) {
											statement4.close();
										}
										
									} catch (SQLException sqle4) {
										sqle4.printStackTrace();
									}
								}
								
								
							}
							rs3.close();
						} catch (SQLException ex) {
							ex.printStackTrace();
							count=true;
						} finally {
							try {
								if (statement3 != null) {
									statement3.close();
								}
								
							} catch (SQLException sqle4) {
								sqle4.printStackTrace();
							}
						}
						
						
					}
					rs2.close();
				} catch (SQLException ex) {
					ex.printStackTrace();
					count=true;
				} finally {
					try {
						if (statement2 != null) {
							statement2.close();
						}
						
					} catch (SQLException sqle4) {
						sqle4.printStackTrace();
					}
				}
				
		
			}
			rs1.close();
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
			count=true;
		} finally {
			try {
				if (statement != null) {
					statement.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException sqle4) {
				sqle4.printStackTrace();
			}
		}

		return count||count1||count2||count3||count4||count5;
	}
	/**
	 * 
	     * @Title: getDataDictionaryByid   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param id
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: DataDictionary      
	     * @throws
	 */
	public DataDictionary getDataDictionaryByid(String id) throws ServiceException {
		List<DataDictionary> list = new ArrayList<DataDictionary>();
		list = qm.find("  from DataDictionary  where id='" + id + "' ");
		if (list != null && list.size() > 0){
			return (DataDictionary)list.get(0);
		}else
			return null;
		
	}
	
	/**
	 * 
	     * @Title: saveCompanyDepartment   
	     * @Description: 保存部门  
	     * @param: @param cdt
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: CompanyDepartment      
	     * @throws
	 */
	public CompanyDepartment saveCompanyDepartment(CompanyDepartment cdt) throws ServiceException{
		this.pm.save(cdt);
		return cdt;
	}
	
	/**
	 * 
	     * @Title: saveCompanyDepartment   
	     * @Description: 修改部门  
	     * @param: @param cdt
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: CompanyDepartment      
	     * @throws
	 */
	public CompanyDepartment updateCompanyDepartment(CompanyDepartment cdt) throws ServiceException{
		this.pm.update(cdt);
		return cdt;
	}
	
	/**
	 * 
	     * @Title: saveCompanyDepartment   
	     * @Description: 删除部门  
	     * @param: @param cdt
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: CompanyDepartment      
	     * @throws
	 */
	public void delCompanyDepartment(CompanyDepartment cdt) throws ServiceException{
		this.pm.remove(cdt);
	}
	
	/**
	 * 
	     * @Title: getCompanyDepartment   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param code
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: CompanyDepartment      
	     * @throws
	 */
	 public CompanyDepartment getCompanyDepartment(long code) throws ServiceException 
	    {
		 CompanyDepartment webConfig =(CompanyDepartment)this.qm.get(CompanyDepartment.class, code);
	        return webConfig;
	    }
	/**
	 * 
	     * @Title: getDwList   
	     * @Description: 获取单位对于部门   
	     * @param: @param company_Id
	     * @param: @param pagesize
	     * @param: @param pageno
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: PageReturnDTO      
	     * @throws
	 */
	@SuppressWarnings({ "unused", "rawtypes" })
	public PageReturnDTO getDwList(long company_Id,int pagesize, int pageno) throws ServiceException {
		String sql = "select id,company_Id,dep_Name,creater,create_Time,updater,update_Time"
				+ " From  Company_Department where company_Id="+company_Id+
				" order by id desc";
		PageSupport map = this.jqm.getList(sql, pageno, pagesize, CompanyDepartmentDTO.class);
		List mapList = map.getList();
		PageReturnDTO webrole = new PageReturnDTO();
		webrole.setPage(pageno);
		webrole.setRp(pagesize);
		webrole.setTotal(map.getRecTotal());
		webrole.setRows(map.getList());
		
		return webrole;
	}
	
	/**
	 * 
	     * @Title: getDatadis   
	     * @Description: 获取经济类型
	     * @param: @param data_code
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<JobDTO>      
	     * @throws
	 */
	public List<JobDTO> getDatadisjjlx()throws ServiceException {
		String sqltext = "select t.economicID,t.economicclass_code,t.economicclass_name,t.parentID "
				+ "from zyb_economicclass t where t.parentID='' or t.parentID is null"
				+ " order by t.economicclass_code";
		Connection connection = null;
		Statement statement = null;
		int count = 0;
		List<JobDTO> list = new ArrayList<JobDTO>();
		try {
			// 读取记录数
			connection = this.jqm.getConnection();
			statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(sqltext);
			while (rs.next()) {
				JobDTO jd =new JobDTO();
				jd.setId(rs.getString("economicclass_code"));
				jd.setName("----"+rs.getString("economicclass_name"));
				jd.setRemark(rs.getString("parentID"));
				list.add(jd);
				
				String sqltext1 = "select t.economicID,t.economicclass_code,t.economicclass_name,t.parentID "
						+ "from zyb_economicclass t where t.parentID='"+rs.getString("economicID")+"' order by t.economicclass_code";
				Connection connection1 = null;
				Statement statement1 = null;
				
				try {
					// 读取记录数
					connection1 = this.jqm.getConnection();
					statement1 = connection1.createStatement();
					ResultSet rs1 = statement1.executeQuery(sqltext1);
					while (rs1.next()) {
						JobDTO jd1 =new JobDTO();
						jd1.setId(rs1.getString("economicclass_code"));
						jd1.setName("--------"+rs1.getString("economicclass_name"));
						jd1.setRemark(rs1.getString("parentID"));
						list.add(jd1);						
					}
					rs1.close();
				} catch (SQLException ex) {
					ex.printStackTrace();
				} finally {
					try {
						if (statement1 != null) {
							statement1.close();
						}
						if (connection1 != null) {
							connection1.close();
						}
					} catch (SQLException sqle4) {
						sqle4.printStackTrace();
					}
				}				
			}
			rs.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			try {
				if (statement != null) {
					statement.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException sqle4) {
				sqle4.printStackTrace();
			}
		}
		return list;
	}
	
	/**
	 * 
	     * @Title: getDatadishylx   
	     * @Description: 行业类型  
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<JobDTO>      
	     * @throws
	 */
	public List<JobDTO> getDatadishylx()throws ServiceException {
		String sqltext = "select t.industryID,t.industry_code,t.industry_name,t.parentID "
				+ "from zyb_economicindustry t "
				+ "where t.parentID='' or t.parentID is null order by t.industry_code";
		Connection connection = null;
		Statement statement = null;
		int count = 0;
		List<JobDTO> list = new ArrayList<JobDTO>();
		try {
			// 读取记录数
			connection = this.jqm.getConnection();
			statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(sqltext);
			while (rs.next()) {
				JobDTO jd =new JobDTO();
				jd.setId(rs.getString("industry_code"));
				jd.setName("----"+rs.getString("industry_name"));
				jd.setRemark(rs.getString("parentID"));
				list.add(jd);
				
				String sqltext1 = "select t.industryID,t.industry_code,t.industry_name,t.parentID  "
						+ "from zyb_economicindustry t where t.parentID='"+rs.getString("industryID")+"' order by t.industry_code";
				Connection connection1 = null;
				Statement statement1 = null;
				
				try {
					// 读取记录数
					connection1 = this.jqm.getConnection();
					statement1 = connection1.createStatement();
					ResultSet rs1 = statement1.executeQuery(sqltext1);
					while (rs1.next()) {
						JobDTO jd1 =new JobDTO();
						jd1.setId(rs1.getString("industry_code"));
						jd1.setName("--------"+rs1.getString("industry_name"));
						jd1.setRemark(rs1.getString("parentID"));
						list.add(jd1);						
					}
					rs1.close();
				} catch (SQLException ex) {
					ex.printStackTrace();
				} finally {
					try {
						if (statement1 != null) {
							statement1.close();
						}
						if (connection1 != null) {
							connection1.close();
						}
					} catch (SQLException sqle4) {
						sqle4.printStackTrace();
					}
				}				
			}
			rs.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			try {
				if (statement != null) {
					statement.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException sqle4) {
				sqle4.printStackTrace();
			}
		}
		return list;
	}
	
	/**
	 * 
	     * @Title: getDatadisdanwgm   
	     * @Description:单位规模   
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<JobDTO>      
	     * @throws
	 */
	public List<JobDTO> getDatadisdanwgm()throws ServiceException {
		String sqltext = "select t.scale_code,t.scale_name from zyb_enterprisescale t order by t.scale_code";
		Connection connection = null;
		Statement statement = null;
		List<JobDTO> list = new ArrayList<JobDTO>();
		try {
			// 读取记录数
			connection = this.jqm.getConnection();
			statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(sqltext);
			while (rs.next()) {
				JobDTO jd =new JobDTO();
				jd.setId(rs.getString("scale_code"));
				jd.setName(rs.getString("scale_name"));
				jd.setRemark(rs.getString("scale_code"));
				list.add(jd);
			}
			rs.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			try {
				if (statement != null) {
					statement.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException sqle4) {
				sqle4.printStackTrace();
			}
		}
		return list;
	}
	
	/**
	 * 
	     * @Title: getDatadixzqh   
	     * @Description: 行政区划   
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<JobDTO>      
	     * @throws
	 */
	public List<JobDTO> getDatadixzqh()throws ServiceException {
		String sqltext = "select t.areacode_code as areacode_code,t.areacode_name,t.parentID from zyb_areacode t order by t.areacode_code";
		Connection connection = null;
		Statement statement = null;
		List<JobDTO> list = new ArrayList<JobDTO>();
		try {
			// 读取记录数
			connection = this.jqm.getConnection();
			statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(sqltext);
			while (rs.next()) {
				JobDTO jd =new JobDTO();
				jd.setId(rs.getString("areacode_code"));
				jd.setName(rs.getString("areacode_name"));
				jd.setRemark(rs.getString("parentID"));
				list.add(jd);
			}
			rs.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			try {
				if (statement != null) {
					statement.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException sqle4) {
				sqle4.printStackTrace();
			}
		}
		return list;
	}
	/**
	 * 
	     * @Title: getDatadis   
	     * @Description: 获取数据字典  
	     * @param: @param data_code
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<JobDTO>      
	     * @throws
	 */
	public List<JobDTO> getDatadis(String data_code,String data_class)throws ServiceException {
		String sqltext = "select id,data_name,remark,data_code_children from data_dictionary where data_code='" + data_code
				+ "' and isActive='Y' and data_class in ('0','"+data_class+"') order by seq_code";
		Connection connection = null;
		Statement statement = null;
		int count = 0;
		List<JobDTO> list = new ArrayList<JobDTO>();
		try {
			// 读取记录数
			connection = this.jqm.getConnection();
			statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(sqltext);
			while (rs.next()) {
				JobDTO jd =new JobDTO();
				jd.setId(rs.getString("id"));
				jd.setName(rs.getString("data_name"));
				jd.setRemark(rs.getString("remark"));
				jd.setData_code_children(rs.getString("data_code_children"));
				list.add(jd);
			}
			rs.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			try {
				if (statement != null) {
					statement.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException sqle4) {
				sqle4.printStackTrace();
			}
		}
		return list;
	}
	
	/**
	 * 
	     * @Title: getUsers   
	     * @Description: 获取用户数据  
	     * @param: @param data_code
	     * @param: @param exam_center_id
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<JobDTO>      
	     * @throws
	 */
	public List<JobDTO> getUsers(long exam_center_id)throws ServiceException {
		String sqltext = "select id,chi_name,log_name,work_num from user_usr where is_active='Y' and exam_center_id="+exam_center_id+" order by chi_name ";
		Connection connection = null;
		Statement statement = null;
		List<JobDTO> list = new ArrayList<JobDTO>();
		try {
			// 读取记录数
			connection = this.jqm.getConnection();
			statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(sqltext);
			while (rs.next()) {
				JobDTO jd =new JobDTO();
				jd.setId(rs.getString("id"));
				jd.setName(rs.getString("chi_name"));
				jd.setRemark(rs.getString("work_num"));
				list.add(jd);
			}
			rs.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			try {
				if (statement != null) {
					statement.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException sqle4) {
				sqle4.printStackTrace();
			}
		}
		return list;
	}
	
	/**
	 * 
	     * @Title: getReportMenu   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param exam_center_id
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<JobDTO>      
	     * @throws
	 */
	public List<JobDTO> getReportMenu()throws ServiceException {
		StringBuffer sb = new StringBuffer();
		sb.append("select a.id,a.parent_id,a.report_name,a.report_address,a.is_active,a.creater,fromusr.chi_name as creaters,"
				+ "a.create_time,a.updater,tousr.chi_name as updaters,a.update_time,a.seq_code,a.report_type FROM report_menu a ");
		sb.append(" left join user_usr fromusr on fromusr.id=a.creater ");
		sb.append(" left join user_usr tousr on tousr.id=a.updater  ");
		sb.append(" where a.is_active='Y' ");
        sb.append( " and (a.parent_id is null or a.parent_id=0) " );
        sb.append("  order by a.id ");	
        Connection connection = null;
		Statement statement = null;
		List<JobDTO> list = new ArrayList<JobDTO>();
		
		JobDTO jd =new JobDTO();
		jd.setId("0");
		jd.setName("父菜单");
		jd.setRemark("");
		list.add(jd);
		
		try {
			// 读取记录数
			connection = this.jqm.getConnection();
			statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(sb.toString());
			while (rs.next()) {
				jd =new JobDTO();
				jd.setId(rs.getString("id"));
				jd.setName(rs.getString("report_name"));
				jd.setRemark(rs.getString("report_address"));
				list.add(jd);
			}
			rs.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			try {
				if (statement != null) {
					statement.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException sqle4) {
				sqle4.printStackTrace();
			}
		}
		return list;
	}
	
	/**
	 * 
	     * @Title: getDatadisForName   
	     * @Description: 通过姓名获取数据字典  
	     * @param: @param dataname
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: JobDTO      
	     * @throws
	 */
	public JobDTO getDatadisForName(String types,String dataname)throws ServiceException {
		String sqltext = "select id,data_name from data_dictionary where isActive='Y' and data_code='"+types+"' and data_name='" + dataname+ "' ";
		Connection connection = null;
		Statement statement = null;
		JobDTO jd = new JobDTO();
		try {
			// 读取记录数
			connection = this.jqm.getConnection();
			statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(sqltext);
			if(rs.next()) {
				jd.setId(rs.getString("id"));
				jd.setName(rs.getString("data_name"));
			}
			rs.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			try {
				if (statement != null) {
					statement.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException sqle4) {
				sqle4.printStackTrace();
			}
		}
		return jd;
	}
	
	/**
	 * 
	     * @Title: getDatadisForName   
	     * @Description: 通过姓名获取数据字典  
	     * @param: @param dataname
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: JobDTO      
	     * @throws
	 */
	public JobDTO getDatadisForChildrenCode(String types,String dataname)throws ServiceException {
		String sqltext = "select id,data_name from data_dictionary where isActive='Y' and data_code='"+types+"' and data_code_children='" + dataname+ "' ";
		Connection connection = null;
		Statement statement = null;
		JobDTO jd = new JobDTO();
		try {
			// 读取记录数
			connection = this.jqm.getConnection();
			statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(sqltext);
			if(rs.next()) {
				jd.setId(rs.getString("id"));
				jd.setName(rs.getString("data_name"));
			}
			rs.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			try {
				if (statement != null) {
					statement.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException sqle4) {
				sqle4.printStackTrace();
			}
		}
		return jd;
	}
	/**
	 * 
	     * @Title: getDatadis   
	     * @Description: 获取数据字典  
	     * @param: @param data_code
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<JobDTO>      
	     * @throws
	 */
	public DataDictionary getDatadisForId(long id)throws ServiceException {
		String sqltext = "select id,data_name from data_dictionary where id=" + id
				+ " ";
		Connection connection = null;
		Statement statement = null;
		DataDictionary dd = new DataDictionary();
		try {
			// 读取记录数
			connection = this.jqm.getConnection();
			statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(sqltext);
			if (rs.next()) {
				dd.setId(rs.getLong("id"));
				dd.setData_name(rs.getString("data_name"));
			}
			rs.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			try {
				if (statement != null) {
					statement.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException sqle4) {
				sqle4.printStackTrace();
			}
		}
		return dd;
	}
	
	/**
	 * 
	     * @Title: getCompanyForName   
	     * @Description: 获取有效单位为name的模糊查询
	     * @param: @param name
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<TreeDTO>      
	     * @throws
	 */
	public List<TreeDTO> getCompanyForNameIsactive(String name,String isactive,String ispower,long userid,UserDTO user)throws ServiceException{
		StringBuffer sqltext= new StringBuffer();
        if(StringUtil.isEmpty(name)){
        	sqltext.append("select top 200  c.id,c.parent_com_id,c.com_name,c.com_num,c.create_time  from  company_info c,company_info_vs_center vs"
        			+ " where c.is_active = 'Y' and  vs.company_id = c.id AND vs.center_num='"+user.getCenter_num()+"'");
        }else{
        	sqltext.append("select top 200  c.id,c.parent_com_id,c.com_name,c.com_num,c.create_time  from  company_info c,company_info_vs_center vs"
        			+ " where c.is_active = 'Y'  and  vs.company_id = c.id AND vs.center_num='"+user.getCenter_num()+"'   ");
        	sqltext.append(" and (c.com_name like '%"+name+"%' or c.name_pinyin like '%"+name+"%' or c.com_num like '%"+name+"%')");
	    }

		if("Y".equals(ispower)){ //是否关联创建者查询
			sqltext.append(" and c.creater='"+userid+"'");
		}
		sqltext.append(" order by c.create_time desc "); // 创建时间倒叙

		Connection connection = null;
		Statement statement = null;
		List<TreeDTO> list = new ArrayList<>();
		List<TreeDTO> list2 = new ArrayList<>();
		try {
			// 读取记录数
			connection = this.jqm.getConnection();
			statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(sqltext.toString());
			while (rs.next()) {
				TreeDTO jd =new TreeDTO();
				jd.setId(rs.getLong("id")+"");
				jd.setText("【"+rs.getString("com_num")+"】"+rs.getString("com_name"));
				jd.setAttributes(rs.getLong("parent_com_id")+"");
				list.add(jd);
			 	String sql="";
				if("".equals(jd.getAttributes()) ||jd.getAttributes() == null || "0".equals(jd.getAttributes())){//是父级单位
					 sql = "select  c.id,c.parent_com_id,c.com_name,c.com_num,c.create_time  from  company_info c,company_info_vs_center vs "
				 		+ " where vs.company_id = c.id and c.is_active = 'Y' and vs.center_num='"+user.getCenter_num()+"'  and parent_com_id = '"+jd.getId()+"' order by c.create_time desc";
				}else{//子单位
					 sql = "select  c.id,c.parent_com_id,c.com_name,c.com_num,c.create_time  from  company_info c,company_info_vs_center vs "
					 		+ " where vs.company_id = c.id  and vs.center_num='"+user.getCenter_num()+"' and c.is_active = 'Y'  and id = '"+jd.getAttributes()+"' order by c.create_time desc";
				}
				Connection connection1 = null;
				Statement statement1 = null;
				try {
					// 读取记录数
					connection1 = this.jqm.getConnection();
					statement1 = connection1.createStatement();
					ResultSet rs1 = statement1.executeQuery(sql);
					while (rs1.next()) {
						TreeDTO jdd =new TreeDTO();
						jdd.setId(rs1.getLong("id")+"");
						jdd.setText("【"+rs1.getString("com_num")+"】"+rs1.getString("com_name"));
						jdd.setAttributes(rs1.getLong("parent_com_id")+"");
						list2.add(jdd);
					}
					rs1.close();
				} catch (SQLException ex) {
					ex.printStackTrace();
				} finally {
					try {
						if (statement1 != null) {
							statement1.close();
						}
						if (connection1 != null) {
							connection1.close();
						}
					} catch (SQLException sqle4) {
						sqle4.printStackTrace();
					}
				}
			}
			rs.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			try {
				if (statement != null) {
					statement.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException sqle4) {
				sqle4.printStackTrace();
			}
		}
		List<TreeDTO>  li = new ArrayList<>();
		for (TreeDTO treeDTO : list2) {
			if(!list.contains(treeDTO)){
				list.add(treeDTO);
			}
		}
		for (TreeDTO treeDTO : list) {
			li.add(treeDTO);
		}
		return li;
	}
	
	
	/**
	 * 
	     * @Title: getCompanyForName   
	     * @Description: 获取有效单位为name的模糊查询
	     * @param: @param name
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<TreeDTO>      
	     * @throws
	 */
	public List<TreeDTO> getZybCompanyForNameIsactive(String name,String isactive,String ispower,long userid,UserDTO user)throws ServiceException{
		StringBuffer sqltext= new StringBuffer();
        if(StringUtil.isEmpty(name)){
        	sqltext.append("select top 200  c.id,c.parent_com_id,c.com_name,c.com_num,c.create_time  from  company_info c,batch b,company_info_vs_center vs"
        			+ " where c.is_active = 'Y' and  vs.company_id = c.id and b.company_id=c.id and b.apptype='2' AND vs.center_num='"+user.getCenter_num()+"'");
        }else{
        	sqltext.append("select top 200  c.id,c.parent_com_id,c.com_name,c.com_num,c.create_time  from  company_info c,batch b,company_info_vs_center vs"
        			+ " where c.is_active = 'Y'  and  vs.company_id = c.id and b.company_id=c.id and b.apptype='2' AND vs.center_num='"+user.getCenter_num()+"'   ");
        	sqltext.append(" and (c.com_name like '%"+name+"%' or c.name_pinyin like '%"+name+"%' or c.com_num like '%"+name+"%')");
	    }

		if("Y".equals(ispower)){ //是否关联创建者查询
			sqltext.append(" and c.creater='"+userid+"'");
		}
		sqltext.append(" order by c.create_time desc "); // 创建时间倒叙

		Connection connection = null;
		Statement statement = null;
		List<TreeDTO> list = new ArrayList<>();
		List<TreeDTO> list2 = new ArrayList<>();
		try {
			// 读取记录数
			connection = this.jqm.getConnection();
			statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(sqltext.toString());
			while (rs.next()) {
				TreeDTO jd =new TreeDTO();
				jd.setId(rs.getLong("id")+"");
				jd.setText("【"+rs.getString("com_num")+"】"+rs.getString("com_name"));
				jd.setAttributes(rs.getLong("parent_com_id")+"");
				list.add(jd);
			 	String sql="";
				if("".equals(jd.getAttributes()) ||jd.getAttributes() == null || "0".equals(jd.getAttributes())){//是父级单位
					 sql = "select  c.id,c.parent_com_id,c.com_name,c.com_num,c.create_time  from  company_info c,batch b,company_info_vs_center vs "
				 		+ " where vs.company_id = c.id and b.company_id=c.id and b.apptype='2' and c.is_active = 'Y' and vs.center_num='"+user.getCenter_num()+"'  and parent_com_id = '"+jd.getId()+"' order by c.create_time desc";
				}else{//子单位
					 sql = "select  c.id,c.parent_com_id,c.com_name,c.com_num,c.create_time  from  company_info c,batch b,company_info_vs_center vs "
					 		+ " where vs.company_id = c.id and b.company_id=c.id and b.apptype='2' and vs.center_num='"+user.getCenter_num()+"' and c.is_active = 'Y'  and id = '"+jd.getAttributes()+"' order by c.create_time desc";
				}
				Connection connection1 = null;
				Statement statement1 = null;
				try {
					// 读取记录数
					connection1 = this.jqm.getConnection();
					statement1 = connection1.createStatement();
					ResultSet rs1 = statement1.executeQuery(sql);
					while (rs1.next()) {
						TreeDTO jdd =new TreeDTO();
						jdd.setId(rs1.getLong("id")+"");
						jdd.setText("【"+rs1.getString("com_num")+"】"+rs1.getString("com_name"));
						jdd.setAttributes(rs1.getLong("parent_com_id")+"");
						list2.add(jdd);
					}
					rs1.close();
				} catch (SQLException ex) {
					ex.printStackTrace();
				} finally {
					try {
						if (statement1 != null) {
							statement1.close();
						}
						if (connection1 != null) {
							connection1.close();
						}
					} catch (SQLException sqle4) {
						sqle4.printStackTrace();
					}
				}
			}
			rs.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			try {
				if (statement != null) {
					statement.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException sqle4) {
				sqle4.printStackTrace();
			}
		}
		List<TreeDTO>  li = new ArrayList<>();
		for (TreeDTO treeDTO : list2) {
			if(!list.contains(treeDTO)){
				list.add(treeDTO);
			}
		}
		for (TreeDTO treeDTO : list) {
			li.add(treeDTO);
		}
		return li;
	}
	
	
	/**
	 * 
	     * @Title: getCompanyForAll   
	     * @Description: 获取所有节点对应的包括子节点 
	     * @param: @param comid
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<TreeDTO>      
	     * @throws
	 */
	public List<TreeDTO> getCompanyForAll(long comid)throws ServiceException{
		StringBuffer sqltext= new StringBuffer();
		sqltext.append("WITH w1( id, parentId, name, level) AS (SELECT id,parent_com_id,com_name,0 AS level "
				+ "FROM company_info WHERE  id = '"+comid+"' and is_Active='Y' UNION ALL SELECT"
				+ " company_info.id,company_info.parent_com_id,company_info.com_name,level + 1"
				+ " FROM company_info JOIN w1 ON company_info.parent_com_id= w1.id and company_info.is_Active='Y')"
				+ " SELECT * FROM w1;");
		Connection connection = null;
		Statement statement = null;
		List<TreeDTO> list = new ArrayList<TreeDTO>();
		try {
			// 读取记录数
			connection = this.jqm.getConnection();
			statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(sqltext.toString());
			while (rs.next()) {
				TreeDTO jd =new TreeDTO();
				jd.setId(rs.getString("id"));
				jd.setText(rs.getString("name"));
				jd.setAttributes(rs.getString("parentId"));
				list.add(jd);
			}
			rs.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			try {
				if (statement != null) {
					statement.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException sqle4) {
				sqle4.printStackTrace();
			}
		}
		return list;
	}
	
	/**
	 * 
	     * @Title: getCompanyForName   
	     * @Description: 获取所有单位名称为name的模糊查询
	     * @param: @param name
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<TreeDTO>      
	     * @throws
	 */
	public List<TreeDTO> getCompanyForName(String name)throws ServiceException{
		StringBuffer sqltext= new StringBuffer();

		sqltext.append("with cte as ( "+
				 "select * from PEIS.dbo.company_info c where (c.com_name like '%"+name+"%' or c.name_pinyin like '%"+name+"%') "+
				 "union all "+
				 "select c.* FROM PEIS.dbo.company_info c,cte b "+
				 " where c.parent_com_id  = b.id) "+
				 "select distinct c.id,c.parent_com_id,c.com_name,c.create_time from cte c order by create_time desc");
		Connection connection = null;
		Statement statement = null;
		List<TreeDTO> list = new ArrayList<TreeDTO>();
		try {
			// 读取记录数
			connection = this.jqm.getConnection();
			statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(sqltext.toString());
			while (rs.next()) {
				TreeDTO jd =new TreeDTO();
				jd.setId(rs.getString("id"));
				jd.setText(rs.getString("com_name"));
				jd.setAttributes(rs.getString("parent_com_id"));
				list.add(jd);
			}
			rs.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			try {
				if (statement != null) {
					statement.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException sqle4) {
				sqle4.printStackTrace();
			}
		}
		return list;
	}
	
	/**
	 * 
	     * @Title: getCompanyPersonForId   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param 通过id获取子菜单
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<TreeDTO>      
	     * @throws
	 */
	public List<TreeDTO> getCompanyPersonForId(long id)throws ServiceException{
		StringBuffer sqltext= new StringBuffer();
		sqltext.append("with cte as ( "+
				 "select * from company_info c where c.id = "+id+" and c.is_Active='Y' "+
				 "union all "+
				 "select c.* FROM company_info c,cte b "+
				 " where c.id  = b.parent_com_id and c.is_Active='Y' ) "+
				 "select distinct c.id,c.parent_com_id,c.com_name,c.create_time from cte c order by create_time desc");
		Connection connection = null;
		Statement statement = null;
		List<TreeDTO> list = new ArrayList<TreeDTO>();
		try {
			// 读取记录数
			connection = this.jqm.getConnection();
			statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(sqltext.toString());
			while (rs.next()) {
				TreeDTO jd =new TreeDTO();
				jd.setId(rs.getString("id"));
				jd.setText(rs.getString("com_name"));
				jd.setAttributes(rs.getString("parent_com_id"));
				list.add(jd);
			}
			rs.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			try {
				if (statement != null) {
					statement.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException sqle4) {
				sqle4.printStackTrace();
			}
		}
		return list;
	}
	
	/**
	 * 
	     * @Title: getCompanyForBatchComId   
	     * @Description: 通过体检计划获取计划内部门   
	     * @param: @param id
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<TreeDTO>      
	     * @throws
	 */
	public List<TreeDTO> getCompanyForBatchComId(long id)throws ServiceException {
		StringBuffer sqltext= new StringBuffer();
		sqltext.append("with cte as (select id,parent_com_id,com_name,is_Active,create_time from company_info "
				+ " where Id = '"+id+"' union all select d.id,d.parent_com_id,d.com_name,d.is_Active,"
						+ "d.create_time from cte c inner join company_info d on c.id = d.parent_com_id and d.is_Active='Y') "
						+ "select * from cte order by create_time desc ");
		Connection connection = null;
		Statement statement = null;
		int count = 0;
		List<TreeDTO> list = new ArrayList<TreeDTO>();
		try {
			// 读取记录数
			connection = this.jqm.getConnection();
			statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(sqltext.toString());
			while (rs.next()) {
				TreeDTO jd =new TreeDTO();
				jd.setId(rs.getString("id"));
				jd.setText(rs.getString("com_Name"));
				jd.setAttributes(rs.getString("parent_com_id"));
				list.add(jd);
			}
			rs.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			try {
				if (statement != null) {
					statement.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException sqle4) {
				sqle4.printStackTrace();
			}
		}
		return list;
	}
	
	/**
	 * 
	     * @Title: getCompanyForName   
	     * @Description: 获取有效的单位
	     * @param: @param isactive
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<TreeDTO>      
	     * @throws
	 */
	public List<TreeDTO> getCompanyForNameIsactive(String isactive)throws ServiceException {
		StringBuffer sqltext= new StringBuffer();
		sqltext.append("select id,parent_com_id,com_Name from Company_Info where 1=1 ");
		sqltext.append(" and is_Active='"+isactive+"' order by create_time desc");
		Connection connection = null;
		Statement statement = null;
		int count = 0;
		List<TreeDTO> list = new ArrayList<TreeDTO>();
		try {
			// 读取记录数
			connection = this.jqm.getConnection();
			statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(sqltext.toString());
			while (rs.next()) {
				TreeDTO jd =new TreeDTO();
				jd.setId(rs.getString("id"));
				jd.setText(rs.getString("com_Name"));
				jd.setAttributes(rs.getString("parent_com_id"));
				list.add(jd);
			}
			rs.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			try {
				if (statement != null) {
					statement.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException sqle4) {
				sqle4.printStackTrace();
			}
		}
		return list;
	}

	//文化程度
	public List<JobDTO> getDegreeOfedu()throws ServiceException {
		String sqltext = "select id,edudegree_code,edudegree_name from degreeOfedu order by edudegree_code ";
		Connection connection = null;
		Statement statement = null;
		List<JobDTO> list = new ArrayList<JobDTO>();
		try {
			// 读取记录数
			connection = this.jqm.getConnection();
			statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(sqltext);
			while (rs.next()) {
				JobDTO jd =new JobDTO();
				jd.setId(rs.getString("edudegree_code"));
				jd.setName(rs.getString("edudegree_name"));
				jd.setRemark(rs.getString("id"));
				list.add(jd);
			}
			rs.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			try {
				if (statement != null) {
					statement.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException sqle4) {
				sqle4.printStackTrace();
			}
		}
		return list;
	}
	
	/**
	 * 
	     * @Title: getDatadis   
	     * @Description: 获取数据字典  
	     * @param: @param data_code
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<JobDTO>      
	     * @throws
	 */
	public List<JobDTO> getDatadis(String data_code)throws ServiceException {
		String sqltext = "select id,data_name,remark,data_code_children from data_dictionary where data_code='" + data_code
				+ "' and isActive='Y' order by seq_code";
		Connection connection = null;
		Statement statement = null;
		int count = 0;
		List<JobDTO> list = new ArrayList<JobDTO>();
		try {
			// 读取记录数
			connection = this.jqm.getConnection();
			statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(sqltext);
			while (rs.next()) {
				JobDTO jd =new JobDTO();
				jd.setId(rs.getString("id"));
				jd.setName(rs.getString("data_name"));
				jd.setRemark(rs.getString("remark"));
				jd.setData_code_children(rs.getString("data_code_children"));
				list.add(jd);
			}
			rs.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			try {
				if (statement != null) {
					statement.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException sqle4) {
				sqle4.printStackTrace();
			}
		}
		return list;
	}
	
	/**
	 * 
	     * @Title: getBatchForComId   
	     * @Description: 根据单位ID获取批次   
	     * @param: @param id
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<TreeDTO>      
	     * @throws
	 */
	public List<TreeDTO> getBatchForComId(long id,String apptype,String center_num)throws ServiceException {
		StringBuffer sqltext= new StringBuffer();
		sqltext.append("select id,batch_name,is_Active,create_time from batch  where company_id = '"+id+"' and center_num='"+center_num+"' and is_Active='Y' and checktype='2' and apptype='"+apptype+"' order by create_time desc  ");
		Connection connection = null;
		Statement statement = null;
		int count = 0;
		List<TreeDTO> list = new ArrayList<TreeDTO>();
		try {
			// 读取记录数
			connection = this.jqm.getConnection();
			statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(sqltext.toString());
			while (rs.next()) {
				TreeDTO jd =new TreeDTO();
				jd.setId(rs.getString("id"));
				jd.setText(rs.getString("batch_name"));
				list.add(jd);
			}
			rs.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			try {
				if (statement != null) {
					statement.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException sqle4) {
				sqle4.printStackTrace();
			}
		}
		return list;
	}

	public PageReturnDTO getCompanyInfoList(CompanyInfoModel model,int page,int pagesize,UserDTO user) throws ServiceException {
		String sql =" SELECT * FROM company_info c,company_info_vs_center cvc "
				+" where c.id = cvc.company_id  AND  c.is_Active = 'Y' AND  cvc.center_num!='"+user.getCenter_num()+"' ";
				if(!StringUtils.isEmpty(model.getCom_Name())){
			   sql += " AND c.com_name like '%"+model.getCom_Name()+"%'";	
				}
		PageSupport map = (PageSupport) this.jqm.getList(sql,page,pagesize,CompanyInfoDTO.class);
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
	public String saveCompanInfo(CompanyInfoModel model, UserDTO user) throws ServiceException {
		String sql_id = " SELECT id FROM company_info ci,company_info_vs_center cvc  "
							+" WHERE cvc.center_num = '"+user.getCenter_num()+"' AND  ci.id = cvc.company_id  AND ci.is_Active = 'Y' AND ci.id ="+model.getId();
		List<CompanyInfoDTO> li_id = this.jqm.getList(sql_id, CompanyInfoDTO.class);
		if( li_id!=null && li_id.size() > 0 ){
			return "单位已存在";
		}
		
		String sql = " SELECT id,com_name FROM company_info WHERE  id="+model.getId();
		List<CompanyInfoDTO> li = this.jqm.getList(sql, CompanyInfoDTO.class);
		Connection connection = null;
		Statement statement = null;
		try {
			connection = this.jqm.getConnection();
			statement = connection.createStatement();
			String sql_save = " insert into company_info_vs_center(company_id,center_num,is_owner,creater,create_time) "
					   +"	values("+li.get(0).getId()+",'"+user.getCenter_num()+"',0,"+user.getUserid()+",GETDATE())  ";
			statement.execute(sql_save);
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			try {
				if (statement != null) {
					statement.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException sqle4) {
				sqle4.printStackTrace();
			}
		}
		return "ok";
	}

	@Override
	public int updateCompanyInfoIsActive(CompanyInfo companyInfo,UserDTO user) throws ServiceException {
		Connection common = null;
		Statement statement = null;
		int cont = 0;
		try {
			common = this.jqm.getConnection();
			statement = common.createStatement();
			String sql_vs_d = " DELETE FROM company_info_vs_center WHERE company_id ="+companyInfo.getId()+""
					+ " AND center_num = '"+user.getCenter_num()+"'";
			statement.executeUpdate(sql_vs_d);
			
			String sql = " SELECT id FROM company_info c,company_info_vs_center vs "
					+"  WHERE  c.id = vs.company_id AND c.is_Active = 'Y' AND id ="+companyInfo.getId();
			ResultSet rs = statement.executeQuery(sql);
			
			if(!rs.next()){
				cont++;
//				String sql2 = "DELETE FROM company_info";
//                String sql1 = " update company_info SET is_Active='N' WHERE id="+companyInfo.getId();
//                System.out.println(sql1);
//                statement.executeUpdate(sql2);
				//updateCompanyInfo(companyInfo,user);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
				try {
					if(statement!=null){
						statement.close();
					}
					if(common!=null){
						common.close();
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
		return cont;
	}

	@Override
	public CompanyInfoDTO getCenterComByID(long id, String center_num) throws ServiceException {
		String sql = " SELECT * FROM company_info_vs_center WHERE company_id="+id+"  AND center_num='"+center_num+"' ";
		List<CompanyInfoDTO> li = this.jqm.getList(sql,CompanyInfoDTO.class);
		if(li!=null && li.size()>0){
			return li.get(0);
		}
		return null;
	}
}
