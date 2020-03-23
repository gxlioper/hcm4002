package com.hjw.wst.service.impl;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import com.hjw.wst.DTO.Colmuns;
import com.hjw.wst.DTO.ExamCountDTO;
import com.hjw.wst.DTO.ExamResultDetailDTO;
import com.hjw.wst.DTO.ExaminationItemDTO;
import com.hjw.wst.DTO.HeaderDTO;
import com.hjw.wst.model.ExamResultDetailModel;
import com.hjw.wst.service.CheckResultStatisticsService;
import com.synjones.framework.exception.ServiceException;
import com.synjones.framework.persistence.JdbcPersistenceManager;
import com.synjones.framework.persistence.JdbcQueryManager;
import com.synjones.framework.persistence.PersistenceManager;
import com.synjones.framework.persistence.QueryManager;

/**
 * 检查结果统计
     * @Title:  火箭蛙体检管理系统   
     * @Package com.hjw.wst.service.impl   
     * @Description:  
     * @author: zr     
     * @date:   2017年7月11日 下午2:48:54   
     * @version V2.0.0.0
 */
public class CheckResultStatisticsServiceImpl  implements  CheckResultStatisticsService{

	private QueryManager qm;
	private JdbcQueryManager jqm;
	private JdbcPersistenceManager jpm;
	private PersistenceManager pm;

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
	public List<ExaminationItemDTO> getExaminationItemList() throws ServiceException {
		String sql = " SELECT distinct ei.id,ei.item_name FROM  charging_item  c,examination_item ei,charging_item_exam_item  cei " 
				+" where  c.isActive='Y'  and  c.dep_category='131'  and   ei.is_Active='Y'   and  ei.item_category='数字型'  " 
				+" and   c.item_code=cei.charging_item_code    and   ei.item_num=cei.item_code   order  by   ei.item_name";
		List<ExaminationItemDTO> li =	this.jqm.getList(sql, ExaminationItemDTO.class);
		return li;
	}

	@Override
	public HeaderDTO getExamResultDetail(ExamResultDetailModel model) throws ServiceException {
		
		HeaderDTO h  = new HeaderDTO();
		List<ExamResultDetailDTO>  li = new  ArrayList<ExamResultDetailDTO>();
		List<ExamCountDTO> tj = new ArrayList<ExamCountDTO>();
		//动态表头
		List<Colmuns> colmun = new ArrayList<Colmuns>();
		Colmuns c1 = new Colmuns();
		c1.setAlign("center");
		c1.setField("exam_num");
		c1.setTitle("体检编码");
		c1.setWidth(10);
		colmun.add(c1);
		
		Colmuns c2 = new Colmuns();
		c2.setAlign("center");
		c2.setField("user_name");
		c2.setTitle("姓名");
		c2.setWidth(10);
		colmun.add(c2);
		
		Colmuns c3 = new Colmuns();
		c3.setAlign("center");
		c3.setField("sex");
		c3.setTitle("性别");
		c3.setWidth(10);
		colmun.add(c3);
		
		Colmuns c4 = new Colmuns();
		c4.setAlign("center");
		c4.setField("age");
		c4.setTitle("年龄");
		c4.setWidth(10);
		colmun.add(c4);
		
		Colmuns c5 = new Colmuns();
		c5.setAlign("center");
		c5.setField("join_date");
		c5.setTitle("体检日期");
		//c5.setWidth(10);
		colmun.add(c5);
		
		double tongji = 0;
		String sql = " SELECT ei.exam_num,ci.user_name,ci.sex,ei.age,ei.join_date,e.exam_result,eit.item_name FROM exam_result_detail e,exam_info ei,customer_info  ci,examination_item  eit 	 "   
			+"	where  ei.is_Active = 'Y'  and   e.exam_num=ei.exam_num  and  ci.id=ei.customer_id  and   ci.is_Active='Y'   and  eit.is_Active='Y'   and  eit.item_category='数字型' "
			+ "  and   eit.id=e.exam_item_id   ";
			if(model.getExam_date1()!=null && !"".equals(model.getExam_date1())){
			   sql +="   and  '"+model.getExam_date1()+"'<=ei.join_date ";
			}
			if(model.getExam_date2()!=null && !"".equals(model.getExam_date2())){
				  sql +="   and  '"+model.getExam_date2()+"  11:59' >=ei.join_date";
				  List<ExamResultDetailDTO>  li1 =  this.jqm.getList(sql,ExamResultDetailDTO.class);
				  if(li1.size()>0){
					  for(int i = 0; i<li1.size()-1;i++){
							for(int j = li1.size()-1;j>i; j--){
								if(li1.get(i).getExam_num().equals(li1.get(j).getExam_num())){
									li1.remove(j);
								}
							}
					  }
					  tongji = li1.size();
				  }
			}
			if(model.getExam_id_1()!=null && !"".equals(model.getExam_id_1()) && model.getS1()!=null && !"".equals(model.getS1()) && model.getValue_1()!=null && !"".equals(model.getValue_1())){
				String sql2=sql;
					   sql2+="   and    e.exam_item_id='"+model.getExam_id_1()+"'   and    CAST(e.exam_result  as   decimal(8,6))"+model.getS1()+"  "+model.getValue_1()+"";
					   sql2+=" order by ei.exam_num";
					   List<ExamResultDetailDTO>  li1 =  this.jqm.getList(sql2,ExamResultDetailDTO.class);
					
					   
					   if(li1.size()>0){
						    for (ExamResultDetailDTO e : li1) {
								e.setItem_name_1(e.getExam_result());
								e.setBiaoshi("1");
							}
						    li.addAll(li1);
						    
						    Colmuns c6 = new Colmuns();
							c6.setAlign("center");
							c6.setField("item_name_1");
							c6.setTitle(li1.get(0).getItem_name()+model.getS1()+model.getValue_1());
							c6.setWidth(20);
							colmun.add(c6);
							
							ExamCountDTO ec = new ExamCountDTO();
							ec.setItem_name(li1.get(0).getItem_name());
							NumberFormat numberFormat = NumberFormat.getInstance();  
							   
					        // 设置精确到小数点后2位  
					   
					        numberFormat.setMaximumFractionDigits(2);  
					        double num = li1.size();
					        String result = numberFormat.format(num/tongji);  
							ec.setCount(result+"%");
							ec.setBijiao(model.getS1()+model.getValue_1());
							tj.add(ec);
							
					   }
					
			} 
			if(model.getExam_id_2()!=null && !"".equals(model.getExam_id_2()) && model.getS2()!=null && !"".equals(model.getS2()) && model.getValue_2()!=null && !"".equals(model.getValue_2())){
				String sql2=sql; 
				sql2+="   and    e.exam_item_id='"+model.getExam_id_2()+"'  and   CAST(e.exam_result  as   decimal(8,6))"+model.getS2()+model.getValue_2();
				sql2+=" order by ei.exam_num";
				  List<ExamResultDetailDTO>  li1 =  this.jqm.getList(sql2,ExamResultDetailDTO.class);
						  if(li1.size()>0){
							  for (ExamResultDetailDTO e : li1) {
									e.setItem_name_2(e.getExam_result());
									e.setBiaoshi("2");
								}
							  	li.addAll(li1);
							    Colmuns c6 = new Colmuns();
								c6.setAlign("center");
								c6.setField("item_name_2");
								c6.setTitle(li1.get(0).getItem_name()+model.getS2()+model.getValue_2());
								c6.setWidth(20);
								colmun.add(c6);
								
								ExamCountDTO ec = new ExamCountDTO();
								ec.setItem_name(li1.get(0).getItem_name());
								NumberFormat numberFormat = NumberFormat.getInstance();  
								   
						        // 设置精确到小数点后2位  
						   
						        numberFormat.setMaximumFractionDigits(2);  
						        double num = li1.size();
						        String result = numberFormat.format(num/tongji);  
								ec.setCount(result+"%");
								ec.setBijiao(model.getS2()+model.getValue_2());
								tj.add(ec);
						   }
			}
			if(model.getExam_id_3()!=null && !"".equals(model.getExam_id_3()) && model.getS3()!=null && !"".equals(model.getS3()) && model.getValue_3()!=null && !"".equals(model.getValue_3())){
				String sql2=sql; 
				sql2+="   and    e.exam_item_id='"+model.getExam_id_3()+"'   and   CAST(e.exam_result  as   decimal(8,6))"+model.getS3()+model.getValue_3();
				sql2+=" order by ei.exam_num";
				  List<ExamResultDetailDTO>  li1 =  this.jqm.getList(sql2,ExamResultDetailDTO.class);
				  if(li1.size()>0){
					  for (ExamResultDetailDTO e : li1) {
							e.setItem_name_3(e.getExam_result());
							e.setBiaoshi("3");
						}
					  	li.addAll(li1);
					   
					    Colmuns c6 = new Colmuns();
						c6.setAlign("center");
						c6.setField("item_name_3");
						c6.setTitle(li1.get(0).getItem_name()+model.getS3()+model.getValue_3());
						c6.setWidth(20);
						colmun.add(c6);
						
						ExamCountDTO ec = new ExamCountDTO();
						ec.setItem_name(li1.get(0).getItem_name());
						NumberFormat numberFormat = NumberFormat.getInstance();  
						   
				        // 设置精确到小数点后2位  
				   
				        numberFormat.setMaximumFractionDigits(2);  
				        double num = li1.size();
				        String result = numberFormat.format(num/tongji);  
						ec.setCount(result+"%");
						ec.setBijiao(model.getS3()+model.getValue_3());
						tj.add(ec);
				   }
			}
			if(model.getExam_id_4()!=null && !"".equals(model.getExam_id_4()) && model.getS4()!=null && !"".equals(model.getS4()) && model.getValue_4()!=null && !"".equals(model.getValue_4())){
				String sql2=sql;  
				sql2+="   and    e.exam_item_id='"+model.getExam_id_4()+"'   and   CAST(e.exam_result  as   decimal(8,6))"+model.getS4()+model.getValue_4();
				sql2+=" order by ei.exam_num";
				  List<ExamResultDetailDTO>  li1 =  this.jqm.getList(sql2,ExamResultDetailDTO.class);
				  if(li1.size()>0){
					  for (ExamResultDetailDTO e : li1) {
							e.setItem_name_4(e.getExam_result());
							e.setBiaoshi("4");
						}
					  	li.addAll(li1);
					   
					    Colmuns c6 = new Colmuns();
						c6.setAlign("center");
						c6.setField("item_name_4");
						c6.setTitle(li1.get(0).getItem_name()+model.getS4()+model.getValue_4());
						c6.setWidth(20);
						colmun.add(c6);
						
						ExamCountDTO ec = new ExamCountDTO();
						ec.setItem_name(li1.get(0).getItem_name());
						NumberFormat numberFormat = NumberFormat.getInstance();  
						   
				        // 设置精确到小数点后2位  
				   
				        numberFormat.setMaximumFractionDigits(2);  
				        double num = li1.size();
				        String result = numberFormat.format(num/tongji);  
						ec.setCount(result+"%");
						ec.setBijiao(model.getS4()+model.getValue_4());
						tj.add(ec);
				   }
			}
			if(model.getExam_id_5()!=null && !"".equals(model.getExam_id_5()) && model.getS5()!=null && !"".equals(model.getS5()) && model.getValue_5()!=null && !"".equals(model.getValue_5())){
				String sql2=sql; 
				sql2+="   and    e.exam_item_id='"+model.getExam_id_5()+"'   and   CAST(e.exam_result  as   decimal(8,6))"+model.getS5()+model.getValue_5();
				sql2+=" order by ei.exam_num";
				  List<ExamResultDetailDTO>  li1 =  this.jqm.getList(sql2,ExamResultDetailDTO.class);
					  if(li1.size()>0){
						  	for (ExamResultDetailDTO e : li1) {
								e.setItem_name_5(e.getExam_result());
								e.setBiaoshi("5");
							}
						  	li.addAll(li1);
						   
						    Colmuns c6 = new Colmuns();
							c6.setAlign("center");
							c6.setField("item_name_5");
							c6.setTitle(li1.get(0).getItem_name()+model.getS5()+model.getValue_5());
							c6.setWidth(20);
							colmun.add(c6);
							
							ExamCountDTO ec = new ExamCountDTO();
							ec.setItem_name(li1.get(0).getItem_name());
							NumberFormat numberFormat = NumberFormat.getInstance();  
							   
					        // 设置精确到小数点后2位  
					   
					        numberFormat.setMaximumFractionDigits(2);  
					        double num = li1.size();
					        String result = numberFormat.format(num/tongji);  
							ec.setCount(result+"%");
							ec.setBijiao(model.getS5()+model.getValue_5());
							tj.add(ec);
					   }
			}
			if(model.getExam_id_6()!=null && !"".equals(model.getExam_id_6()) && model.getS6()!=null && !"".equals(model.getS6()) && model.getValue_6()!=null && !"".equals(model.getValue_6())){
				String sql2=sql;  
				sql2+="   and    e.exam_item_id='"+model.getExam_id_6()+"'   and   CAST(e.exam_result  as   decimal(8,6))"+model.getS6()+model.getValue_6();
				sql2+=" order by ci.user_name";
				  List<ExamResultDetailDTO>  li1 =  this.jqm.getList(sql2,ExamResultDetailDTO.class);
						  if(li1.size()>0){
							  	for (ExamResultDetailDTO e : li1) {
									e.setItem_name_6(e.getExam_result());
									e.setBiaoshi("6");
								}
							  	li.addAll(li1);
							   
							    Colmuns c6 = new Colmuns();
								c6.setAlign("center");
								c6.setField("item_name_6");
								c6.setTitle(li1.get(0).getItem_name()+model.getS6()+model.getValue_6());
								c6.setWidth(20);
								colmun.add(c6);
								
								ExamCountDTO ec = new ExamCountDTO();
								ec.setItem_name(li1.get(0).getItem_name());
								NumberFormat numberFormat = NumberFormat.getInstance();  
								   
						        // 设置精确到小数点后2位  
						   
						        numberFormat.setMaximumFractionDigits(2);  
						        double num = li1.size();
						        String result = numberFormat.format(num/tongji);  
								ec.setCount(result+"%");
								ec.setBijiao(model.getS6()+model.getValue_6());
								tj.add(ec);
						   }
			}
			for(int i = 0; i<li.size()-1;i++){
				for(int j = li.size()-1;j>i; j--){
					if(li.get(i).getExam_num().equals(li.get(j).getExam_num())){
							//把j的数据给i
							String key = li.get(j).getBiaoshi();
							switch (key) {
								case "1":
									li.get(i).setItem_name_1(li.get(j).getItem_name_1());
									li.remove(j);
									break;
									
								case "2":
									li.get(i).setItem_name_2(li.get(j).getItem_name_2());
									li.remove(j);
									break;
									
								case "3":
									li.get(i).setItem_name_3(li.get(j).getItem_name_3());
									li.remove(j);
									break;
									
								case "4":
									li.get(i).setItem_name_4(li.get(j).getItem_name_4());
									li.remove(j);
									break;
									
								case "5":
									li.get(i).setItem_name_5(li.get(j).getItem_name_5());
									li.remove(j);
									break;
									
								case "6":
									li.get(i).setItem_name_6(li.get(j).getItem_name_6());
									li.remove(j);
									break;
		
							}
							
					}
				}
			}
			h.setLi(li);
			h.setColmuli(colmun);
			h.setTj(tj);
		return h;
	}

}
