package com.hjw.crm.service.impl;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.methods.multipart.StringPart;
import org.apache.commons.httpclient.params.HttpMethodParams;

import com.hjw.crm.DTO.JianKzDTO;
import com.hjw.crm.domain.ExamJkzFlag;
import com.hjw.crm.model.JianKzModel;
import com.hjw.crm.service.JianKzService;
import com.hjw.util.DateTimeUtil;
import com.hjw.util.IsPrivateCenterConfig;
import com.hjw.util.TranLogTxt;
import com.hjw.wst.DTO.ExamInfoDTO;
import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.wst.DTO.UserDTO;
import com.hjw.config.GetHealthNumContral;
import com.hjw.wst.domain.ExamInfo;
import com.hjw.zyb.domain.ExamInfoExt;
import com.hjw.wst.service.CompanyService;
import com.hjw.wst.service.CustomerInfoService;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import com.synjones.framework.exception.ServiceException;
import com.synjones.framework.persistence.JdbcPersistenceManager;
import com.synjones.framework.persistence.JdbcQueryManager;
import com.synjones.framework.persistence.PersistenceManager;
import com.synjones.framework.persistence.QueryManager;
import com.synjones.framework.support.PageSupport;


public class JianKzServiceImpl implements JianKzService{
	private QueryManager queryManager;
	private JdbcQueryManager jdbcQueryManager;
	private JdbcPersistenceManager jdbcPersistenceManager;
	private PersistenceManager persistenceManager;
	private CompanyService companyService;
	private CustomerInfoService customerInfoService;
	
	public CustomerInfoService getCustomerInfoService() {
		return customerInfoService;
	}

	public void setCustomerInfoService(CustomerInfoService customerInfoService) {
		this.customerInfoService = customerInfoService;
	}

	public CompanyService getCompanyService() {
		return companyService;
	}

	public void setCompanyService(CompanyService companyService) {
		this.companyService = companyService;
	}

	public QueryManager getQueryManager() {
		return queryManager;
	}

	public void setQueryManager(QueryManager queryManager) {
		this.queryManager = queryManager;
	}

	public JdbcQueryManager getJdbcQueryManager() {
		return jdbcQueryManager;
	}

	public void setJdbcQueryManager(JdbcQueryManager jdbcQueryManager) {
		this.jdbcQueryManager = jdbcQueryManager;
	}

	public JdbcPersistenceManager getJdbcPersistenceManager() {
		return jdbcPersistenceManager;
	}

	public void setJdbcPersistenceManager(JdbcPersistenceManager jdbcPersistenceManager) {
		this.jdbcPersistenceManager = jdbcPersistenceManager;
	}

	public PersistenceManager getPersistenceManager() {
		return persistenceManager;
	}

	public void setPersistenceManager(PersistenceManager persistenceManager) {
		this.persistenceManager = persistenceManager;
	}
	/**
	 * 总检室保存健康证信息
	     * <p>Title: saveExamSummaryJianKangZheng</p>   
	     * <p>Description: </p>   
	     * @param model
	     * @param user
	     * @return
	     * @throws ServiceException   
	     * @see com.hjw.crm.service.JianKzService#saveExamSummaryJianKangZheng(com.hjw.crm.model.JianKzModel, com.hjw.wst.DTO.UserDTO)
	 */
	@Override
	public String saveExamSummaryJianKangZheng(JianKzModel model,UserDTO user)throws ServiceException{
//		String sql1 = "select e.exam_num,x.occusectorid as clinic_no from exam_info e left join exam_ext_info x "
//				+ "on e.exam_num = x.exam_num where e.exam_num = '"+model.getExam_num()+"'";
		
//		List<ExamInfoDTO> listExam = this.jdbcQueryManager.getList(sql1, ExamInfoDTO.class);
		List<ExamInfoExt> listExam = this.queryManager.find("from ExamInfoExt e where e.exam_num = '"+model.getExam_num()+"'");
		List<ExamInfo> listinfo = this.queryManager.find("from ExamInfo e where e.exam_num ='"+model.getExam_num()+"'");
		Date exam_date = DateTimeUtil.parse();
		if (listinfo.size() > 0) {
			if (listinfo.get(0).getJoin_date() != null && listinfo.get(0).getJoin_date().length() > 0) {
				exam_date = DateTimeUtil.parse(listinfo.get(0).getJoin_date());
			}
		}
		String health_no = null;
		ExamInfoExt examinfo = null;
		if(listExam.size() <= 0){
			return "error-行业编号为空,请先选择行业!";
		}else{
			examinfo = listExam.get(0);
			if(examinfo.getOccusectorid() == null || "".equals(examinfo.getOccusectorid())){
				return "error-行业编号为空,请先选择行业!";
			}
			if("".equals(model.getHealth_no())){
				health_no = GetHealthNumContral.getInstance().getHealthNum(examinfo.getOccusectorid());
			}else{
				health_no = model.getHealth_no();
			}
		}
		
		String sql = "from ExamJkzFlag e where e.exam_num = '"+model.getExam_num()+"'";
		List<ExamJkzFlag> list = this.queryManager.find(sql);
		if(list.size() == 0){
			ExamJkzFlag examjkz = new ExamJkzFlag();
			examjkz.setExam_num(model.getExam_num());
			examjkz.setExamflag("0");
			examjkz.setCreate_time(DateTimeUtil.parse());
			examjkz.setSffz(model.getSffz());
			examjkz.setFzrq(DateTimeUtil.parse());
			examjkz.setYxq(DateTimeUtil.getDateAddyear(exam_date, 1));
			examjkz.setDjczy(user.getName());
			examjkz.setSfdy("0");
			examjkz.setHealth_no(health_no);
			this.persistenceManager.save(examjkz);
		} else {
			ExamJkzFlag examjkz = list.get(0);
			if (examjkz.getExamflag().equals("1")) {
				return "error-该体检者已上传健康证,不能继续操作!";
			}
			if (examjkz.getSfdy().equals("1")) {
				return "error-该体检者已打印健康证,不能继续操作!";
			}
			examjkz.setSffz(model.getSffz());
			examjkz.setFzrq(DateTimeUtil.parse());
			examjkz.setYxq(DateTimeUtil.getDateAddyear(exam_date, 1));
			examjkz.setDjczy(user.getName());
			examjkz.setHealth_no(health_no);
			this.persistenceManager.update(examjkz);
		}
		return "ok-保存成功!";
	}
	
	public List<JianKzDTO> getOldHealthNoOneYear(String arch_num) throws ServiceException{
		String sql = "select j.health_no,j.fzrq,j.sffz,e.exam_num,convert(varchar(50),e.join_date,23) as rq"
				+ " from exam_info e,customer_info c,exam_jiankangzheng j"
				+" where e.customer_id = c.id and e.exam_num = j.exam_num and c.arch_num = '"+arch_num+"'"
				+" and e.join_date > '"+DateTimeUtil.shortFmt2(DateTimeUtil.getDateAddyear(DateTimeUtil.parse(),-1))+"'";
		List<JianKzDTO> list = this.jdbcQueryManager.getList(sql, JianKzDTO.class);
		return list;
	}
	
	/**
	 * 详细页面健康证上传 
	     * <p>Title: jianKangZhuangChuanShu</p>   
	     * <p>Description: </p>   
	     * @param model
	     * @return
	     * @throws UnsupportedEncodingException
	     * @throws IllegalArgumentException
	     * @throws IllegalAccessException
	     * @throws FileNotFoundException   
	     * @see com.hjw.crm.service.JianKzService#jianKangZhuangChuanShu(com.hjw.crm.model.JianKzModel)
	 */
	@Override
	public String jianKangZhuangChuanShu(JianKzModel model, String center_num) throws UnsupportedEncodingException, IllegalArgumentException, IllegalAccessException, FileNotFoundException {
		model=this.getNullTo(model);
		//String url="http://59.46.43.51:82/TiJian/Employee/sytjPostFileSqlSave.jsp";
		String url=this.customerInfoService.getCenterconfigByKey("JKZNEI_ACTION", center_num).getConfig_value();
		String sql="insert into cyrytjdjbmaintrigger("
				+ "dwbm,pic,bh,xm,xb,nl,dw,hy,gz,dh,sfzh,rq,"
				+ "hjg,sfhg,sfdy,jg_xin,jg_gan,jg_pi,jg_tppa,jg_pf,"
				+ "jg_qt,jg_lj,jg_sh,jg_xt,jg_alt,jg_hbsag,jg_hbeag,"
				+ "jg_rpr,jg_lqj,jg_hav,jg_hiv,sffz,fzrq,yxq,djczy,dyrq,dwdz"
				+ ") values('"+model.getDwbm()+"','Employee/PicTemp/38/"+model.getBh()+".jpg','"+model.getBh()+"','"+model.getXm()+"',"
						+ "'"+model.getXb()+"','"+model.getNl()+"','"+model.getDw()+"','"+model.getHycode()+"','"+model.getGzcode()+"',"
								+ "'"+model.getDh()+"','"+model.getSfzh()+"','"+model.getRq()+"','"+model.getHjgcode()+"','"+model.getSfhgcode()+"',"
										+ "'"+model.getSfdy()+"','"+model.getJg_xincode()+"','"+model.getJg_gancode()+"','"+model.getJg_picode()+"',"
												+ "'"+model.getJg_tppacode()+"','"+model.getJg_pfcode()+"','"+model.getJg_qtcode()+"',"
														+ "'"+model.getJg_ljcode()+"','"+model.getJg_shcode()+"','"+model.getJg_xtcode()+"',"
																+ "'"+model.getJg_altcode()+"','"+model.getJg_hbsagcode()+"','"+model.getJg_hbeagcode()+"',"
																		+ "'"+model.getJg_rprcode()+"','"+model.getJg_lqjcode()+"','"+model.getJg_havcode()+"',"
																				+ "'"+model.getJg_hivcode()+"','"+model.getSffzcode()+"','"+model.getFzrq()+"','"+model.getYxq()+"','"+model.getDjczy()+"','"+model.getDyrq()+"','"+model.getDwdz()+"')";

		String fname="";
		String filepath="";
		if(model.getPic()!=null&&model.getPic().length()>0){
			filepath="Employee/PicTemp/38/";
			fname=model.getBh()+".jpg";
		}
		byte[] b=null;
		b=sql.getBytes("gbk");
		sql=Base64.encode(b);
		System.out.println(sql);
		String result=chuanshuto(url,sql,fname,filepath,model.getBh(),model.getPic());
		return result;
	}
	
	private JianKzModel getNullTo(JianKzModel model) throws IllegalArgumentException, IllegalAccessException{
		for(Field f:model.getClass().getDeclaredFields()){
			f.setAccessible(true);
			if(f.get(model)==null){
				f.set(model, "");
			}
		}
		return model;
	}
	private String chuanshuto(String url,String sql,String fname,String filepath,String exam_num,String pic) throws UnsupportedEncodingException, FileNotFoundException{
			HttpClient httpClient = new HttpClient();
			PostMethod postMethod=new PostMethod(url);
			String responseMsg="";
		try {
			//File f=new File(pic);
			Part[] part={  new StringPart("sql", sql, "UTF-8"),  
	                new StringPart("fname", fname, "UTF-8"),  
	                new StringPart("filepath", filepath, "UTF-8"),
	                new StringPart("pic", pic, "UTF-8"),
	              };  
			MultipartRequestEntity entity=new MultipartRequestEntity(part,  new HttpMethodParams());
			postMethod.setRequestEntity(entity);
			httpClient.executeMethod(postMethod);
			responseMsg = postMethod.getResponseBodyAsString().trim();
			System.out.println("返回值为："+responseMsg);
			String flag=responseMsg.substring(0,6);
			responseMsg=responseMsg.substring(6,responseMsg.length());
			if(flag.equals("111111")){
				updateFlag(exam_num,"1");
				
			}else if(flag.equals("000000")){
				updateFlag(exam_num,"2");
			
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			responseMsg="上传失败，照片文件未找到";
		}catch (Exception e) {
			e.printStackTrace();
			responseMsg="上传失败";
		}finally{
			postMethod.releaseConnection();
		}
		TranLogTxt.liswriteEror_to_txt("jiankangzhengLog","req:sql:"+sql+"\r\nfname:"+fname+"\r\nfilepath:"+filepath+"\r\npic:"+pic+"\r\nres:"+responseMsg);
		return responseMsg;
	}

	private void updateFlag(String exam_num,String flag)throws ServiceException, SQLException {
//		ExamJkzFlag jkz=this.getExamJkzFlag(exam_num);
//		if(jkz!=null){
//			jkz.setExamflag(flag);
//			this.persistenceManager.update(jkz);
//		}
		String sql = "update exam_jiankangzheng set examflag = '"+flag+"' where health_no = '"+exam_num+"'";
		this.jdbcPersistenceManager.executeSql(sql);
	}
	private ExamJkzFlag getExamJkzFlag(String exam_num)throws ServiceException, SQLException {
		String sql=" from ExamJkzFlag where health_no='"+exam_num+"'";
		List<ExamJkzFlag> list=this.queryManager.find(sql);
		if(list.size()>0){
			return list.get(0);
		}else{
			return null;
		}
	}
	public PageReturnDTO getJKZList(JianKzModel model, int page, int rows,UserDTO user) throws ServiceException, SQLException {
		String isprivateflag = IsPrivateCenterConfig.isprivateflag(user);//判断用户是否有隐私权限 Y 有 N 无
		if(model.getPra()==0){
			return new PageReturnDTO();
		}else{
		String sql="select ef.health_no as bh,dbo.fun_CharToStar(ei.exam_num,'name',ci.user_name,'"+isprivateflag+"') as xm,ci.sex as xb,"
				+ "	convert(varchar,DATEDIFF(YYYY,ci.birthday,GETDATE())) as nl,ei.company as dw,"
				+ "dee.industry_name as hy,dee.industry_name as gz,CONVERT(varchar(50),ei.join_date,23) as rq,"
				+ "'合格' as hjg,CONVERT(varchar(50),ef.fzrq,23)  as fzrq,ef.djczy as djczy,ISNULL(ef.examflag,'0') as examflag,ei.exam_num as exam_num "
				+ "from exam_info ei "
				+ "left join customer_info ci on ei.customer_id=ci.id "
				+ "left join exam_ext_info ee on ei.exam_num=ee.exam_num "
				+ "left join zyb_economicindustry dee on ee.occusectorid=dee.industry_code "
				+ "left join company_info cm on ei.company_id=cm.id "
				+ "left join exam_jiankangzheng ef on ef.exam_num=ei.exam_num ";
		List<String> strlist=new ArrayList<String>();
		strlist.add(" ei.customer_type='"+getDataDictionaryJKZ()+"' ");
		
		if(model.getExam_num()!=null&&model.getExam_num().length()>0){
			strlist.add(" ei.exam_num='"+model.getExam_num()+"' ");
		}
		
		if(model.getChi_name()!=null&&model.getChi_name().length()>0){
			strlist.add(" ci.user_name like '%"+model.getChi_name()+"%' ");
		}
		if(model.getJoinstart_date()!=null&&model.getJoinstart_date().length()>0){
			strlist.add(" ei.join_date>='"+model.getJoinstart_date()+" 23:59:59' ");
		}
		if(model.getJoinend_date()!=null&&model.getJoinend_date().length()>0){
			strlist.add(" ei.join_date<='"+model.getJoinend_date()+" 23:59:59' ");
		}
		if(model.getSfhg()!=null&&model.getSfhg().length()>0){
			strlist.add(" ef.sffz='"+model.getSfhg()+"' " );
		}
		if(model.getExamflag()!=null&&model.getExamflag().length()>0){
			if(model.getExamflag().equals("已上传")){
				strlist.add(" ef.examflag='1' ");	
			}else if(model.getExamflag().equals("未上传")){
				strlist.add(" ef.examflag='0' ");
			}else if(model.getExamflag().equals("上传失败")){
				strlist.add("  ef.examflag='2' ");
			}
		}
		if(model.getSfdy()!=null&&model.getSfdy().length()>0){
			if(model.getSfdy().equals("1")){
				strlist.add(" ef.sfdy='1' ");	
			}else{
				strlist.add(" ef.sfdy='0' ");	
			}
			
		}
		
		if(model.getCompany_id()!=null&&model.getCompany_id().length()>0){
			if(!model.getCompany_id().equals("0")){
				strlist.add(" ei.company_id='"+model.getCompany_id()+"' ");
			}
		}
		if(model.getArch_num()!=null&&model.getArch_num().length()>0){
			strlist.add(" ci.arch_num='"+model.getArch_num()+"' ");
		}
		if(model.getSfzh()!=null&&model.getSfzh().length()>0){
			strlist.add(" ci.id_num='"+model.getSfzh()+"' ");
		}
		if(strlist!=null&&strlist.size()>0){
			sql+=" where ";
			if(strlist.size()>1){
				sql+=strlist.get(0);
				for(int i =1;i<=strlist.size()-1;i++){
					sql+=" and "+strlist.get(i);
				}
			}else{
				sql+=strlist.get(0);
			}
		}
		sql+=" order by ei.join_date desc";
		System.out.println(sql);
		PageSupport map = jdbcQueryManager.getList(sql,page,rows,JianKzDTO.class);
		
		PageReturnDTO webrole = new PageReturnDTO();
		webrole.setPage(page);
		webrole.setRp(rows);
		if ((map != null) && (map.getList() != null)) {
			webrole.setTotal(map.getRecTotal());
			webrole.setRows(map.getList());
		}
		return webrole;
		}
	}
		
	private String getDataDictionaryJKZ()throws ServiceException, SQLException {
		String sql="select id from data_dictionary where data_code='TJLX' and data_code_children='JKZTJ'";
		System.out.println(sql);
		Connection connection = null;
		Statement statement = null;
		String str="";
		try {
			// 读取记录数
			connection = this.jdbcQueryManager.getConnection();
			statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			System.out.println(sql);
			while (rs.next()) {
				str=rs.getString("id");
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
		return str;
	}
	@Override
	public String jianKangZhuangBuFenChuanShu(String exam_nums, String center_num) throws ServiceException, SQLException, UnsupportedEncodingException, IllegalArgumentException, IllegalAccessException, FileNotFoundException {
		String[] str=new String[]{};
		str=exam_nums.split(",");
		int j=0;
		int k=0;
		for(int i=0;i<str.length;i++){
			JianKzModel model=this.getResultJianKzModel(str[i], center_num);
			String examflag=this.getExamFlag(model.getBh());
			String res="";
			if(!examflag.equals("1")){
				 res=this.jianKangZhuangChuanShu(model, center_num);
			}else{
				k=k+1;
			}
			if(res.contains("失败")){
				j=j+1;
			}
		}
		int x=str.length-j-k;
		String ret="成功"+x+"条，失败"+j+"条，之前已上传"+k+"条";
		return ret;
	}

	@Override
	public String jianKangZhuangAllChuanShu(JianKzModel model, String center_num) throws ServiceException, SQLException, UnsupportedEncodingException, IllegalArgumentException, IllegalAccessException, FileNotFoundException {
		List<String> examlist=this.getJianKzModelList(model);
		int j=0;
		int k=0;
		for(int i=0;i<examlist.size();i++){
			JianKzModel jianKzModel=this.getResultJianKzModel(examlist.get(i), center_num);
			String examflags=this.getExamFlag(jianKzModel.getBh());
			String res="";
			if(!examflags.equals("1")){
			res=this.jianKangZhuangChuanShu(jianKzModel, center_num);
			}else{
				k=k+1;
			}
			if(res.contains("失败")){
				j=j+1;
			}
		}
		int x=examlist.size()-j-k;
		String ret="成功"+x+"条，失败"+j+"条，之前已上传"+k+"条";
		return ret;
	}
	private List<String> getJianKzModelList(JianKzModel model) throws ServiceException, SQLException {
		String sql="select ei.exam_num as bh  "
				+ "from exam_info ei "
				+ "left join customer_info ci on ei.customer_id=ci.id "
				+ "left join exam_jiankangzheng ef on ef.exam_num=ei.exam_num ";
		List<String> strlist=new ArrayList<String>();
		strlist.add(" ei.customer_type='"+getDataDictionaryJKZ()+"' ");
		strlist.add(" ef.sffz='1' ");
		if(model.getExam_num()!=null&&model.getExam_num().length()>0){
			strlist.add(" ei.exam_num='"+model.getExam_num()+"' ");
		}
		if(model.getChi_name()!=null&&model.getChi_name().length()>0){
			strlist.add(" ci.user_name like '%"+model.getChi_name()+"%' ");
		}
		if(model.getJoinstart_date()!=null&&model.getJoinstart_date().length()>0){
			strlist.add(" ei.join_date>='"+model.getJoinstart_date()+" 00:00:00' ");
		}
		if(model.getJoinend_date()!=null&&model.getJoinend_date().length()>0){
			strlist.add(" ei.join_date<='"+model.getJoinend_date()+" 23:59:59' ");
		}
		if(model.getExamflag()!=null&&model.getExamflag().length()>0){
			if(model.getExamflag().equals("已上传")){
				strlist.add(" ef.examflag='1' ");	
			}else if(model.getExamflag().equals("未上传")){
				strlist.add(" ef.examflag='0' ");
			}else if(model.getExamflag().equals("上传失败")){
				strlist.add("  ef.examflag='2' ");
			}
		}
		if(model.getSfhg()!=null&&model.getSfhg().length()>0){
			strlist.add(" ef.sffz='"+model.getSfhg()+"' " );
		}
		if(model.getCompany_id()!=null&&model.getCompany_id().length()>0){
			if(!model.getCompany_id().equals("0")){
				strlist.add(" ei.company_id='"+model.getCompany_id()+"' ");
			}
		}
		if(model.getArch_num()!=null&&model.getArch_num().length()>0){
			strlist.add(" ci.arch_num='"+model.getArch_num()+"' ");
		}
		if(model.getSfzh()!=null&&model.getSfzh().length()>0){
			strlist.add(" ci.id_num='"+model.getSfzh()+"' ");
		}
		if(strlist!=null&&strlist.size()>0){
			sql+=" where ";
			if(strlist.size()>1){
				sql+=strlist.get(0);
				for(int i =1;i<=strlist.size()-1;i++){
					sql+=" and "+strlist.get(i);
				}
			}else{
				sql+=strlist.get(0);
			}
		}
		sql+=" order by ei.join_date desc";
		System.out.println(sql);
		Connection connection = null;
		Statement statement = null;
		List<String> examlist=new ArrayList<String>();
		try {
			// 读取记录数
			connection = this.jdbcQueryManager.getConnection();
			statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			System.out.println(sql);
			while (rs.next()) {
				String exam_numstr=rs.getString("bh");
				examlist.add(exam_numstr);
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
		return examlist;
	}
	private JianKzModel getJianKzModel(String exam_num, String center_num) throws ServiceException, SQLException {
		String dwbm=this.customerInfoService.getCenterconfigByKey("JKZNEI_DWBM", center_num).getConfig_value();
		String dwbmname=this.customerInfoService.getCenterconfigByKey("JKZNEI_DWBMNAME", center_num).getConfig_value();
		String sql="select "
				+ "'"+dwbm+"' as dwbm,"
				+ "'"+dwbmname+"' as dwbmname,"
				+ "ei.picture_path as pic,"
				+ "ejkz.health_no as bh,"
				+ "ci.user_name as xm,"
				+ "ci.sex as xb,"
				+ "convert(varchar,DATEDIFF(YYYY,ci.birthday,GETDATE())) as nl,"
				+ "ei.company as dw,"
				+ "cf.address as dwdz,"
				+ "ee.occusectorid as hycode,"
				+ "dee.industry_name as hy,"
				+ "ee.occusectorid as gzcode,"
				+ "dee.industry_name as gz,"
				+ "ei.phone as dh,"
				+ "ci.id_num as sfzh,"
				+ "CONVERT(varchar(100),ei.join_date,20) as rq,"
				+ "'0701' as hjgcode,"
				+ "ejkz.sffz as sfhgcode,"
				+ "ejkz.sffz as sffzcode,"
				+ "CONVERT(varchar(100),ejkz.fzrq,20) as fzrq,"
				+ "CONVERT(varchar(100),ejkz.yxq,20) as yxq,"
				+ "ejkz.djczy as djczy,"
				+ "CONVERT(varchar(100),ejkz.dyrq,20) as dyrq "
				+ "from exam_info ei "
				+ "left join customer_info ci on ci.id=ei.customer_id  "
				+ "left join company_info cf on ei.company_id=cf.id "
				+ "left join exam_ext_info ee on ei.exam_num=ee.exam_num "
				+ "left join zyb_economicindustry dee on ee.occusectorid=dee.industry_code "
				+ "left join zyb_economicindustry fee on ee.occutypeofworkid=fee.industry_code "
				+ " join exam_jiankangzheng ejkz on ei.exam_num=ejkz.exam_num "
				+ "where ei.exam_num='"+exam_num+"'";
		System.out.println(sql);
		Connection connection = null;
		Statement statement = null;
		JianKzModel huomodel=new JianKzModel();
		try {
			// 读取记录数
			connection = this.jdbcQueryManager.getConnection();
			statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			System.out.println(sql);
			while (rs.next()) {
				huomodel.setDwbm(rs.getString("dwbm"));
				huomodel.setDwbmname(rs.getString("dwbmname"));
				huomodel.setPic(rs.getString("pic"));
				huomodel.setBh(rs.getString("bh"));
				huomodel.setXm(rs.getString("xm"));
				huomodel.setXb(rs.getString("xb"));
				huomodel.setNl(rs.getString("nl"));
				huomodel.setDw(rs.getString("dw"));
				huomodel.setHycode(rs.getString("hycode"));
				huomodel.setHy(rs.getString("hy"));
				huomodel.setGzcode(rs.getString("gzcode"));
				huomodel.setGz(rs.getString("gz"));
				huomodel.setDh(rs.getString("dh"));
				huomodel.setSfzh(rs.getString("sfzh"));
				huomodel.setRq(rs.getString("rq"));
				huomodel.setHjgcode(rs.getString("hjgcode"));
				huomodel.setSfhgcode(rs.getString("sfhgcode"));
				huomodel.setFzrq(rs.getString("fzrq"));
				huomodel.setSffzcode(rs.getString("sffzcode"));
				huomodel.setYxq(rs.getString("yxq"));
				huomodel.setDjczy(rs.getString("djczy"));
				huomodel.setDyrq(rs.getString("dyrq"));
				huomodel.setDwdz(rs.getString("dwdz"));
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
		return huomodel;
	}

	@Override
	public JianKzModel getResultJianKzModel(String exam_num, String center_num) throws ServiceException, SQLException {
		JianKzModel huomodel=new JianKzModel();
		huomodel=this.getJianKzModel(exam_num, center_num);
		/*String pic=huomodel.getPic();
		String picl="";
		if(pic!=null&&pic.length()>0){
			picl=this.companyService.getDatadis("TPLJ","1").get(0).getName();
			if(picl.contains("\\")){
				picl=picl.replaceAll("\\\\", "/");
			}
			huomodel.setPic(picl+"/"+pic);
		}*/
		System.out.println("123123123123123____"+exam_num);
		String jg_xin=this.checkResult(exam_num, "jg_xin");
		String jg_gan=this.checkResult(exam_num, "jg_gan");
		String jg_pi=this.checkResult(exam_num, "jg_pi");
		String jg_tppa=this.checkResult(exam_num, "jg_tppa");
		String jg_pf=this.checkResult(exam_num, "jg_pf");
		String jg_qt=this.checkResult(exam_num, "jg_qt");
		String jg_lj=this.checkResult(exam_num, "jg_lj");
		String jg_sh=this.checkResult(exam_num, "jg_sh");
		String jg_xt=this.checkResult(exam_num, "jg_xt");
		String jg_alt=this.checkResult(exam_num, "jg_alt");
		String jg_hbsag=this.checkResult(exam_num, "jg_hbsag");
		String jg_hbeag=this.checkResult(exam_num, "jg_hbeag");
		String jg_rpr=this.checkResult(exam_num, "jg_rpr");
		String jg_lqj=this.checkResult(exam_num,"jg_lqj");
		String jg_hav=this.checkResult(exam_num, "jg_hav");
		String jg_hiv=this.checkResult(exam_num, "jg_hiv");
		
		huomodel.setJg_xin(jg_xin);
		huomodel.setJg_gan(jg_gan);
		huomodel.setJg_pf(jg_pf);
		huomodel.setJg_pi(jg_pi);
		huomodel.setJg_tppa(jg_tppa);
		huomodel.setJg_lj(jg_lj);
		huomodel.setJg_qt(jg_qt);
		huomodel.setJg_sh(jg_sh);
		huomodel.setJg_xt(jg_xt);
		huomodel.setJg_alt(jg_alt);
		huomodel.setJg_hbsag(jg_hbsag);
		huomodel.setJg_hbeag(jg_hbeag);
		huomodel.setJg_rpr(jg_rpr);
		
		huomodel.setJg_lqj(jg_lqj);
		huomodel.setJg_hav(jg_hav);
		huomodel.setJg_hiv(jg_hiv);
		if(huomodel.getJg_xin().contains("不正常")){
			huomodel.setJg_xincode("0002");
		}else if(huomodel.getJg_xin().contains("正常")){
			huomodel.setJg_xincode("0001");
		}else if(huomodel.getJg_xin().equals("未见异常")){
			huomodel.setJg_xincode("0001");
		}else if(huomodel.getJg_xin().equals("未见明显异常")){
			huomodel.setJg_xincode("0001");
		}
		
		if(huomodel.getJg_gan().contains("不正常")){
			huomodel.setJg_gancode("0004");
		}else if(huomodel.getJg_gan().contains("正常")){
			huomodel.setJg_gancode("0003");
		}else if(huomodel.getJg_gan().equals("未见异常")){
			huomodel.setJg_gancode("0003");
		}else if(huomodel.getJg_gan().equals("未见明显异常")){
			huomodel.setJg_gancode("0003");
		}
		
		if(huomodel.getJg_pi().contains("不正常")){
			huomodel.setJg_picode("0006");
		}else if(huomodel.getJg_pi().contains("正常")){
			huomodel.setJg_picode("0005");
		}else if(huomodel.getJg_pi().equals("未见异常")){
			huomodel.setJg_picode("0005");
		}else if(huomodel.getJg_pi().equals("未见明显异常")){
			huomodel.setJg_picode("0005");
		}
		
		 if(huomodel.getJg_tppa().contains("阳性")){
			huomodel.setJg_tppacode("0402");
		}else if(huomodel.getJg_tppa().contains("阴性")){
			huomodel.setJg_tppacode("0401");
		}
		
		if(huomodel.getJg_pf().contains("手癣")){
			huomodel.setJg_pfcode("00110001");
		}else if(huomodel.getJg_pf().contains("指甲癣")){
			huomodel.setJg_pfcode("00110002");
		}else if(huomodel.getJg_pf().contains("手湿疹")){
			huomodel.setJg_pfcode("00110003");
		}else if(huomodel.getJg_pf().contains("银屑病")){
			huomodel.setJg_pfcode("00110004");
		}else if(huomodel.getJg_pf().contains("渗出性皮肤病")){
			huomodel.setJg_pfcode("00110005");
		}else if(huomodel.getJg_pf().contains("化脓性皮肤病")){
			huomodel.setJg_pfcode("00110006");
		}else if(huomodel.getJg_pf().contains("正常")){
			huomodel.setJg_pfcode("0010");
		}else if(huomodel.getJg_pf().equals("未见异常")){
			huomodel.setJg_pfcode("0010");
		}else if(huomodel.getJg_pf().equals("未见明显异常")){
			huomodel.setJg_pfcode("0010");
		}
		
		if(huomodel.getJg_qt().contains("不正常")){
			huomodel.setJg_qtcode("0015");
		}else if(huomodel.getJg_qt().contains("正常")){
			huomodel.setJg_qtcode("0014");
		}else if(huomodel.getJg_qt().equals("未见异常")){
			huomodel.setJg_qtcode("0014");
		}else if(huomodel.getJg_qt().equals("未见明显异常")){
			huomodel.setJg_qtcode("0014");
		}
		
		if(huomodel.getJg_lj().contains("鲍氏志贺氏菌")){
			huomodel.setJg_ljcode("02020007");
		}else if(huomodel.getJg_lj().contains("福氏志贺氏菌")){
			huomodel.setJg_ljcode("02020008");
		}else if(huomodel.getJg_lj().contains("宋内氏志贺氏菌")){
			huomodel.setJg_ljcode("02020009");
		}else if(huomodel.getJg_lj().contains("志贺氏菌")){
			huomodel.setJg_ljcode("02020011");
		}else if(huomodel.getJg_lj().contains("便复查")){
			huomodel.setJg_ljcode("0205");
		}else if(huomodel.getJg_lj().contains("未检出")){
			huomodel.setJg_ljcode("0201");
		}
		
		if(huomodel.getJg_sh().contains("便复查")){
			huomodel.setJg_shcode("0206");
		}else if(huomodel.getJg_sh().contains("未检出")){
			huomodel.setJg_shcode("0203");
		}else if(huomodel.getJg_sh().contains("检出")){
			huomodel.setJg_shcode("0204");
		}
		
		if(huomodel.getJg_xt().contains("陈旧性肺结核")){
			huomodel.setJg_xtcode("0103");
		}else if(huomodel.getJg_xt().contains("左侧胸膜炎影像")){
			huomodel.setJg_xtcode("010301");
		}else if(huomodel.getJg_xt().contains("右侧胸膜炎影像")){
			huomodel.setJg_xtcode("010302");
		}else if(huomodel.getJg_xt().contains("两侧胸膜炎影像")){
			huomodel.setJg_xtcode("010303");
		}else if(huomodel.getJg_xt().contains("左肺炎症")){
			huomodel.setJg_xtcode("010304");
		}else if(huomodel.getJg_xt().contains("右肺炎症")){
			huomodel.setJg_xtcode("010305");
		}else if(huomodel.getJg_xt().contains("左右肺炎症")){
			huomodel.setJg_xtcode("010306");
		}else if(huomodel.getJg_xt().contains("左肺上野斑片影")){
			huomodel.setJg_xtcode("01040021");
		}else if(huomodel.getJg_xt().contains("左肺上中野斑片影")){
			huomodel.setJg_xtcode("01040022");
		}else if(huomodel.getJg_xt().contains("左肺中野斑片影")){
			huomodel.setJg_xtcode("01040023");
		}else if(huomodel.getJg_xt().contains("右肺上野斑片影")){
			huomodel.setJg_xtcode("01040024");
		}else if(huomodel.getJg_xt().contains("右肺上中野斑片影")){
			huomodel.setJg_xtcode("01040025");
		}else if(huomodel.getJg_xt().contains("右肺中野斑片影")){
			huomodel.setJg_xtcode("01040026");
		}else if(huomodel.getJg_xt().contains("两肺上野斑片影")){
			huomodel.setJg_xtcode("01040027");
		}else if(huomodel.getJg_xt().contains("两肺上中野斑片影")){
			huomodel.setJg_xtcode("01040028");
		}else if(huomodel.getJg_xt().contains("左肺上野片影")){
			huomodel.setJg_xtcode("01040029");
		}else if(huomodel.getJg_xt().contains("左肺中野片影")){
			huomodel.setJg_xtcode("01040031");
		}else if(huomodel.getJg_xt().contains("左肺下野片影")){
			huomodel.setJg_xtcode("01040032");
		}else if(huomodel.getJg_xt().contains("右肺中野片影")){
			huomodel.setJg_xtcode("01040033");
		}else if(huomodel.getJg_xt().contains("右肺下野片影")){
			huomodel.setJg_xtcode("01040034");
		}else if(huomodel.getJg_xt().contains("右肺上野片影")){
			huomodel.setJg_xtcode("01040035");
		}else if(huomodel.getJg_xt().contains("未见异常")){
			huomodel.setJg_xtcode("0101");
		}
		
		if(huomodel.getJg_alt()!=null&&huomodel.getJg_alt().length()>0){
			if(Integer.valueOf(huomodel.getJg_alt())<40){
				huomodel.setJg_altcode("0301");
			}else if(Integer.valueOf(huomodel.getJg_alt())>=40){
				huomodel.setJg_altcode("0302");
			}	
		}
		
		 if(huomodel.getJg_hbsag().contains("HBsAg(+)")){
			huomodel.setJg_hbsagcode("0502");
		}else if(huomodel.getJg_hbsag().contains("血复查")){
			huomodel.setJg_hbsagcode("0507");
		}else if(huomodel.getJg_hbsag().contains("HBsAg(-)")){
			huomodel.setJg_hbsagcode("0501");
		}
		
		if(huomodel.getJg_hbeag().contains("HBeAg(-)")){
			huomodel.setJg_hbeagcode("0503");
		}else if(huomodel.getJg_hbeag().contains("HBeAg(+)")){
			huomodel.setJg_hbeagcode("0504");
		}else if(huomodel.getJg_hbeag().contains("HBeAg(+)")){
			huomodel.setJg_hbeagcode("0504");
		}else if(huomodel.getJg_hbeag().contains("HBeAg血复查")){
			huomodel.setJg_hbeagcode("0505");
		}else if(huomodel.getJg_hbeag().contains("HBeAg(+)血复查")){
			huomodel.setJg_hbeagcode("0506");
		}else if(huomodel.getJg_hbeag().contains("缓报")){
			huomodel.setJg_hbeagcode("0508");
		}
		
		if(huomodel.getJg_rpr().contains("RPR(+)血复查")){
			huomodel.setJg_rprcode("0604");
		}else if(huomodel.getJg_rpr().contains("RPR(+)")){
			huomodel.setJg_rprcode("0611");
		}else if(huomodel.getJg_rpr().contains("RPR(-)")){
			huomodel.setJg_rprcode("0603");
		}
		
		if(huomodel.getJg_lqj().contains("检出")){
			huomodel.setJg_lqjcode("0608");
		}else if(huomodel.getJg_lqj().contains("未检出")){
			huomodel.setJg_lqjcode("0607");
		}
		
		if(huomodel.getJg_hav().contains("阳性")){
			huomodel.setJg_havcode("0402");
		}else if(huomodel.getJg_hav().contains("阴性")){
			huomodel.setJg_havcode("0401");
		}
		
		if(huomodel.getJg_hiv().contains("阳性")){
			huomodel.setJg_hivcode("0602");
		}else if(huomodel.getJg_hiv().contains("阴性")){
			huomodel.setJg_hivcode("0601");
		}
		return huomodel;
	}
	private String checkResult(String exam_num,String piny)throws ServiceException, SQLException {
		String data_type=this.checkItem(piny);
		String result="";
		if(data_type.equals("3")){
			result=this.resultCommon(exam_num, piny);
		}else if(data_type.equals("1")){
			result=this.resultLis(exam_num, piny);
		}else if(data_type.equals("2")){
			result=this.resultPacs(exam_num, piny);
		}else{
			result="";
		}
		return result;
	}
	private String checkItem(String piny)throws ServiceException, SQLException {
		String sql="select hh.data_type as data_type from huo_vs_hop hh where hh.hos_piny='"+piny+"'";
		Connection connection = null;
		Statement statement = null;
		String result="";
		try {
			// 读取记录数
			connection = this.jdbcQueryManager.getConnection();
			statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			System.out.println(sql);
			while (rs.next()) {
				if(rs.getString("data_type")==null||rs.getString("data_type").length()==0){
					result="";	
				}else{
					result=rs.getString("data_type");
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
		return result;
	}
	private String resultCommon(String exam_num,String piny)throws ServiceException, SQLException {
		String sql="select ced.exam_result as result from common_exam_detail ced left join exam_info ei on ced.exam_num=ei.exam_num left join examination_item eit on ced.item_code=eit.item_num left join huo_vs_hop hh on hh.huo_data_code=eit.item_num where ei.exam_num='"+exam_num+"' and hh.hos_piny='"+piny+"' ";
		Connection connection = null;
		Statement statement = null;
		String result="";
		try {
			// 读取记录数
			connection = this.jdbcQueryManager.getConnection();
			statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			System.out.println(sql);
			while (rs.next()) {
				if(rs.getString("result")==null||rs.getString("result").length()==0){
					result="";	
				}else{
					result=rs.getString("result");
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
		return result;
	}
	private String resultLis(String exam_num,String piny)throws ServiceException, SQLException {
		String sql="select ced.exam_result as result from exam_result_detail ced left join exam_info ei on ced.exam_num=ei.exam_num left join examination_item eit on ced.item_code=eit.item_num left join huo_vs_hop hh on hh.huo_data_code=eit.item_num  where ei.exam_num='"+exam_num+"' and hh.hos_piny='"+piny+"' ";
		Connection connection = null;
		Statement statement = null;
		String result="";
		try {
			// 读取记录数
			connection = this.jdbcQueryManager.getConnection();
			statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			System.out.println(sql);
			while (rs.next()) {
				if(rs.getString("result")==null||rs.getString("result").length()==0){
					result="";	
				}else{
					result=rs.getString("result");
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
		return result;
	}
	private String resultPacs(String exam_num,String piny)throws ServiceException, SQLException {
		String sql="select ced.exam_result as result from view_exam_detail ced "
				+ "left join exam_info ei on ei.exam_num=ced.exam_num "
				+ "left join pacs_detail pd on ced.pacs_req_code=pd.pacs_req_code "
				+ "left join charging_item chi on pd.chargingItem_num=chi.item_code "
				+ "left join huo_vs_hop hh on hh.huo_data_code=chi.item_code "
				+ "where  ei.exam_num='"+exam_num+"' and hh.hos_piny='"+piny+"' ";
		Connection connection = null;
		Statement statement = null;
		String result="";
		try {
			// 读取记录数
			connection = this.jdbcQueryManager.getConnection();
			statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			System.out.println(sql);
			while (rs.next()) {
				if(rs.getString("result")==null||rs.getString("result").length()==0){
					result="";	
				}else{
					result=rs.getString("result");
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
		return result;
	}

	@Override
	public String getExamFlag(String exam_num) throws ServiceException, SQLException {
		String sql="select examflag from exam_jiankangzheng where health_no='"+exam_num+"'";
		Connection connection = null;
		Statement statement = null;
		String result="";
		try {
			// 读取记录数
			connection = this.jdbcQueryManager.getConnection();
			statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			System.out.println(sql);
			while (rs.next()) {
				if(rs.getString("examflag")==null||rs.getString("examflag").length()==0){
					result="";	
				}else{
					result=rs.getString("examflag");
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
		return result;
	}
}
