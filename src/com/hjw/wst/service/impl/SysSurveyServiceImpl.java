package com.hjw.wst.service.impl;

import java.util.List;

import com.fr.report.core.A.s;
import com.hjw.util.DateTimeUtil;
import com.hjw.wst.DTO.ChargingItemDTO;
import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.wst.DTO.SurveyExamInfoDTO;
import com.hjw.wst.DTO.SysQuestionItemsDTO;
import com.hjw.wst.DTO.SysQuestionOptionsDTO;
import com.hjw.wst.DTO.SysSurveyOptionsListDTO;
import com.hjw.wst.DTO.SysSurveyQuestionDTO;
import com.hjw.wst.DTO.UserDTO;
import com.hjw.wst.DTO.sysSurveyQuestionRelationDTO;
import com.hjw.wst.domain.SysQuestionItems;
import com.hjw.wst.domain.SysQuestionOptions;
import com.hjw.wst.domain.SysSurveyQuestion;
import com.hjw.wst.domain.sysSurvey;
import com.hjw.wst.domain.sysSurveyQuestionRelation;
import com.hjw.wst.model.SysSurveyQuestionModel;
import com.hjw.wst.service.SysSurveyService;
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
     * @Description:  问卷问题
     * @author: zr     
     * @date:   2017年1月17日 下午3:17:29   
     * @version V2.0.0.0
 */
public class SysSurveyServiceImpl  implements SysSurveyService{
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
	public PageReturnDTO getSysSurveyQuestionList(SysSurveyQuestionModel model,int page,int pageSize) throws ServiceException {
			String sql = "select s.objectId,s.code,s.name,d.data_name  as   qust_type_id,s.sex,s.age,s.age_to,s.marriageState   from  sys_survey_question  s"
					+ "  LEFT JOIN   data_dictionary  d   ON    S.qust_type_id=d.id"
					+ " where  delete_flg='0'   and  s.qust_type_id=d.id   ";
					if(  model.getCode() != null && !"".equals( model.getCode() )  ){
				  sql += "  and   code='"+model.getCode().trim()+"'";	
					}
					if(  model.getName() != null && !"".equals( model.getName() )  ){
				  sql += "  and   name  like   '%"+model.getName().trim()+"%'";		
					}
					if( model.getQust_type_id() != null && !"".equals( model.getQust_type_id() ) ){
				  sql += " and   qust_type_id='"+model.getQust_type_id()+"'"	;
					}
				  sql += " ORDER BY  s.code  ";
			PageSupport map=jqm.getList(sql,page,pageSize,SysSurveyQuestionDTO.class);
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
	public PageReturnDTO getSysSurveyChargItemList(String  code,String name,int page,int pageSize) throws ServiceException {
		String sql = "SELECT * FROM charging_item c  WHERE   c.isActive='Y' ";
				 if( code != null && !"".equals(code)){
			   sql+="  and   c.item_code='"+code.trim()+"' ";	 
				 }
				 if(  name != null && !"".equals(name) ){
			   sql+=  "   and   c.item_name  like   '%"+name.trim()+"%'";	 
				 }
			  sql += "  ORDER BY  c.update_time   desc  ";
		PageSupport map=jqm.getList(sql,page,pageSize,ChargingItemDTO.class);
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
	public void addSysSurvey(SysSurveyQuestionModel model, UserDTO user) throws ServiceException {
			//保存问题
			SysSurveyQuestion ti = new  SysSurveyQuestion();
			ti.setCode( model.getCode() );
			ti.setName( model.getName() );
			ti.setQust_type_id( model.getQust_type_id() );
			ti.setSex( model.getSex() );
			ti.setAge( model.getAge() );
			ti.setAge_to( model.getAge_to() );
			ti.setMarriageState( model.getMarriageState() );
			ti.setAnswer_type( model.getAnswer_type() );
			ti.setCreater_id(""+user.getUserid());
			ti.setCreate_date( DateTimeUtil.parse() );
			ti.setMarriageState( model.getMarriageState() );
			ti.setDelete_flg("0");
			ti.setQust_type_id( model.getQust_type_id() );
			ti.setDep_homepage_show(model.getDep_homepage_show());
			this.pm.save(ti);
			
			//选项
			if( model.getWenti()!=null&&!"".equals(model.getWenti())){
				JSONArray liArry = JSONArray.fromObject( model.getWenti() );
				List<SysQuestionOptions> lis = (List<SysQuestionOptions>) JSONArray
						.toCollection(liArry,SysQuestionOptions.class);
							for (SysQuestionOptions s : lis) {
									SysQuestionOptions o = new  SysQuestionOptions();
									o.setQuest_id(ti.getObjectId()+"");
									o.setCode( s.getCode() );
									o.setContent( s.getContent() );
									o.setNext_quest_code( s.getNext_quest_code() );
									o.setCreater_id(""+user.getUserid());
									o.setCreate_date( DateTimeUtil.parse() );
									o.setDelete_flg("0");
									this.pm.save(o);
									
									//推荐项目
									if( model.getShoufeixm()!=null && !"".equals(model.getShoufeixm())){
										JSONArray liArryxm = JSONArray.fromObject( model.getShoufeixm() );
										List<SysQuestionItems> xm = (List<SysQuestionItems>) JSONArray
										.toCollection(liArryxm,SysQuestionItems.class);
										for (SysQuestionItems e : xm) {
											if( o.getCode().equals(e.getQuest_option_id())){
												SysQuestionItems q = new SysQuestionItems();
												q.setCharge_items_id( e.getCharge_items_id() );
												q.setQuest_option_id( o.getObjectId()+"" );
												this.pm.save(q);
											}
										}
									}	
							}
			}
	}

	@Override
	public SysSurveyQuestionDTO getSysSurveyQuestionDTO(SysSurveyQuestionModel model) throws ServiceException {
		String sql=" select * from  sys_survey_question  where  objectId="+model.getObjectId();
		List<SysSurveyQuestionDTO>  li = this.jqm.getList(sql,SysSurveyQuestionDTO.class);
		return li.get(0);
	}

	@Override
	public List<SysQuestionOptionsDTO> getSysQuestionItemsDTO(SysSurveyQuestionModel model) throws ServiceException {
		String  sql=" select  * from  sys_question_options  where  delete_flg='0'   and  quest_id="+model.getObjectId();
		List<SysQuestionOptionsDTO>	li = this.jqm.getList(sql,SysQuestionOptionsDTO.class);
		return li;
	}

	@Override
	public List<SysQuestionItemsDTO> getSysQuestionItemsDTOList(String  id) throws ServiceException {
		String sql="  SELECT  s.objectId,se.id  as  so_id,so.code,se.item_code,se.item_name FROM  sys_question_items s "
				+"LEFT JOIN   charging_item    se      ON   s.charge_items_id=se.id   and   se.isActive='Y'  "
				+"LEFT JOIN	 sys_question_options so  ON  s.quest_option_id=so.objectId    where   quest_option_id     in("+id+") ";
		List<SysQuestionItemsDTO>  li = this.jqm.getList(sql,SysQuestionItemsDTO.class);
		return li;
	}

	@Override
	public void updateSysSurvey(SysSurveyQuestionModel model, UserDTO user) throws ServiceException {
		SysSurveyQuestion sys = new SysSurveyQuestion();
		sys = (SysSurveyQuestion) this.qm.get(SysSurveyQuestion.class,model.getObjectId());
		sys.setObjectId( model.getObjectId() );
		sys.setCode( model.getCode() );
		sys.setName( model.getName() );
		sys.setQust_type_id( model.getQust_type_id() );
		sys.setSex( model.getSex() );
		sys.setAge( model.getAge() );
		sys.setAge_to( model.getAge_to() );
		sys.setMarriageState( model.getMarriageState() );
		sys.setAnswer_type( model.getAnswer_type() );
		sys.setMarriageState( model.getMarriageState() );
		sys.setUpdater_id(""+user.getUserid());
		sys.setUpdate_date( DateTimeUtil.parse());
		//sys.setDelete_flg("Y");
		sys.setQust_type_id( model.getQust_type_id() );
		sys.setDep_homepage_show(model.getDep_homepage_show());
		
		//修改问卷问题
		this.pm.update(sys);
		
		//删除选项
		String sql = " from  SysQuestionOptions  where   quest_id='"+sys.getObjectId()+"'";
		List<SysQuestionOptions>  so =  this.qm.find(sql);
		   if(so!=null){
			   for (SysQuestionOptions sysQuestionOptions : so) {
				   sql =" from  SysQuestionItems  where   quest_option_id="+sysQuestionOptions.getObjectId();
				   //删除选项对应项目
				   List<SysQuestionItems>  st =  this.qm.find(sql);
					   if(st!=null){
						   for (SysQuestionItems sysQuestionItems : st) {
							   this.pm.remove(sysQuestionItems);
						   }
					   }
					   this.pm.remove(sysQuestionOptions);
			   }
		   }
		
		    //保存选项
		    if( model.getWenti()!=null && !"".equals( model.getWenti() )){
		    
				JSONArray liArry = JSONArray.fromObject( model.getWenti() );
				List<SysQuestionOptions> lis = (List<SysQuestionOptions>) JSONArray
				.toCollection(liArry,SysQuestionOptions.class);
				
				for (SysQuestionOptions s : lis) {
						SysQuestionOptions o = new  SysQuestionOptions();
						o.setQuest_id(model.getObjectId()+"");
						o.setCode( s.getCode() );
						o.setContent( s.getContent() );
						o.setNext_quest_code( s.getNext_quest_code() );
						o.setCreater_id(""+user.getUserid());
						o.setCreate_date( DateTimeUtil.parse() );
						o.setDelete_flg("0");
						this.pm.save(o);
						
						//推荐项目
						if( model.getShoufeixm()!=null && !"".equals( model.getShoufeixm() )){
							JSONArray liArryxm = JSONArray.fromObject( model.getShoufeixm() );
							List<SysQuestionItems> xm = (List<SysQuestionItems>) JSONArray
							.toCollection(liArryxm,SysQuestionItems.class);
							for (SysQuestionItems e : xm) {
								if( o.getCode().equals(e.getQuest_option_id())){
									SysQuestionItems q = new SysQuestionItems();
									q.setCharge_items_id( e.getCharge_items_id() );
									q.setQuest_option_id( o.getObjectId()+"" );
									this.pm.save(q);
								}
							}
						}	
				}
		    }
	}

	@Override
	public void deleteSysSurvey(String ids,UserDTO  user) throws ServiceException {
		//删除问题
		if(ids.length()>0){
		
			String  sql = " From   SysSurveyQuestion   where    objectId  in("+ids+")";
			String ds="";
			List<SysSurveyQuestion> li = this.qm.find(sql);
			if(li.size()>0){
				for (SysSurveyQuestion sysSurveyQuestion : li) {
					sysSurveyQuestion.setDelete_flg( "1" );
					sysSurveyQuestion.setDeleter_id( ""+user.getUserid() );
					sysSurveyQuestion.setDelete_date( DateTimeUtil.parse() );
					this.pm.update(sysSurveyQuestion);
					ds+=sysSurveyQuestion.getObjectId()+",";
						 //删除选项
				    	sql = " from  SysQuestionOptions  where   quest_id='"+sysSurveyQuestion.getObjectId()+"'";
					    List<SysQuestionOptions>  so =  this.qm.find(sql);
					    if( so.size()>0 ){
						    	for (SysQuestionOptions sysQuestionOptions : so) {
						    		sysQuestionOptions.setDelete_flg("1");
						    		sysQuestionOptions.setDeleter_id( ""+user.getUserid() );
						    		sysQuestionOptions.setDelete_date( DateTimeUtil.parse() );
						    		this.pm.update(sysQuestionOptions);
						    		
						    		//删除选项对应项目
						    		sql = " From  SysQuestionItems  where   quest_option_id="+sysQuestionOptions.getObjectId();
						    		List<SysQuestionItems>  st =  this.qm.find(sql);
						    		if(st.size()>0){
						    			for (SysQuestionItems sysQuestionItems : st) {
						    				this.pm.remove(sysQuestionItems);
						    			}
						    		}
						    	}
					    }
						
				}
				ds=ds.substring(0,ds.length()-1);
				//删除已答问卷
				sql="  FROM sysSurveyQuestionRelation   WHERE  question_id   in("+ds+")";
				List<sysSurveyQuestionRelation>  sqri = qm.find(sql);
					if(sqri.size()>0){
						for (sysSurveyQuestionRelation sysSurveyQuestionRelation : sqri) {
							pm.remove(sysSurveyQuestionRelation);
						}
					}
			}
			
		}
	}

	@Override
	public SurveyExamInfoDTO getSurveyExamInfoDTO(String exam_num,String center_num) throws ServiceException {
		String  sql = " SELECT  ci.id,ei.exam_num,ci.user_name,ci.sex,ei.age,v.com_name,ei.phone,da.data_name,"
				+ "eset.set_name,ei.customer_type_id,da.data_name  FROM  " 
				   +"	exam_info  ei" 
				   +" LEFT  JOIN    customer_info  ci   on  ei.customer_id=ci.id   and    ci.is_Active='Y'	"
				   + " left join company_info v on v.id=ei.company_id "
				   +"	LEFT  JOIN    data_dictionary    da   on  ei.customer_type_id=da.id  and   da.isActive='Y' "
				   +"	and   da.isActive='Y'   and   da.data_code='RYLX'  "
				   +"	LEFT  JOIN   	examinfo_set  es  on   ei.exam_num=es.exam_num   and   es.isActive='Y'  "
				   +"	LEFT  JOIN    exam_set  eset  on    eset.id=es.exam_set_id  and  eset.is_Active='Y'  "
				   +"	where ei.is_Active='Y'  and     ei.exam_num='"+exam_num.trim()+"' ";
		List<SurveyExamInfoDTO>  dto = this.jqm.getList(sql,SurveyExamInfoDTO.class);
		return  dto!=null&&dto.size()>0?dto.get(0):null;
	}

	@Override
	public SysSurveyOptionsListDTO getSysSurveyList(SysSurveyQuestionModel model) throws ServiceException {
		String  sql=" SELECT  ci.id  as  c_id,ei.is_marriage,ci.sex,ei.age  FROM   exam_info ei   left  join   customer_info   ci   on    ci.is_Active='Y'	and   ei.customer_id=ci.id "
				+ " where   ei.is_Active='Y'   "; 
				if(model.getCustomer_id()!=null && !"".equals(model.getCustomer_id())){
				sql+=" and   ei.customer_id='"+model.getCustomer_id()+"'";	
				}
				if(model.getExam_num_x()!=null && !"".equals(model.getExam_num_x())){
					sql+=" and   ei.exam_num='"+model.getExam_num_x()+"'";		
				}
		List<SurveyExamInfoDTO>  su = this.jqm.getList(sql,SurveyExamInfoDTO.class);
		
		
		//问题
		sql = "select *  from   sys_survey_question   where   delete_flg='0'  ";
			if("男".equals(su.get(0).getSex())){
				sql+="  and  sex <> 'F' ";
			}else{
				sql+="  and  sex <> 'M' ";
			}
			if(!"".equals(su.get(0).getIs_marriage()) && su.get(0).getIs_marriage()!=null){
				if( "未婚".equals(su.get(0).getIs_marriage()) ){
				sql+="   and   (marriageState='2'  or  marriageState='0'  )";			
				}else{
				sql+="   and   (marriageState='1'  or  marriageState='0'  )";		
				}
			}
			if(!"".equals(su.get(0).getAge())){
				sql+="  and	 '"+su.get(0).getAge()+"'>=age  and   '"+su.get(0).getAge()+"'<=age_to";
			}
			if("1".equals(model.getDep_homepage_show())){
				sql+="  and  dep_homepage_show='1'";
			}
		List<SysSurveyQuestionDTO>	 sysSurveyQuestionList = this.jqm.getList(sql,SysSurveyQuestionDTO.class);
		
		SysSurveyOptionsListDTO dto  = new SysSurveyOptionsListDTO();
		if( sysSurveyQuestionList.size()>0 ){
					String  ids = "";
				for (SysSurveyQuestionDTO s : sysSurveyQuestionList) {
					ids+=s.getObjectId()+",";
				}
					
				//选项	
				ids = ids.substring(0,ids.length()-1);	
				sql = "select * from  sys_question_options   s where   quest_id  in("+ids+")  ";
				List<SysQuestionOptionsDTO>  sysQuestionOptionsList = this.jqm.getList(sql,SysQuestionOptionsDTO.class);
				
				//已选择选项
				sql ="  SELECT   sq.survey_id,sq.question_id,sq.code FROM   sys_survey  s,sys_survey_question_relation  sq "
					+"   WHERE    sq.survey_id=s.objectId       and  s.delete_flg='0'   and   s.enable='1'   and    s.user_id='"+su.get(0).getC_id()+"'   ";
				List<sysSurveyQuestionRelationDTO>  sr =  this.jqm.getList(sql,sysSurveyQuestionRelationDTO.class);
				dto.setSysSurveyQuestionList(	sysSurveyQuestionList	);
				dto.setSysQuestionOptionsList(	sysQuestionOptionsList	);
				dto.setSurverList(sr);
		
		}
		
		return dto;
	}

	@Override
	public void saveQuestionnaireSurvey(SysSurveyQuestionModel model, UserDTO user) throws ServiceException {
		
			
			
			
			String hql = " FROM  sysSurvey  where  user_id='"+ model.getUser_id()+"'";
			List<sysSurvey>  suu = this.qm.find(hql);
			String id = "";
			if( suu!=null && suu.size()>0){
				hql = "  From   sysSurveyQuestionRelation   where  survey_id='"+suu.get(0).getObjectId()+"'";
				List<sysSurveyQuestionRelation>  ls = this.qm.find(hql);
				if(ls!=null && ls.size()>0){
					for (sysSurveyQuestionRelation sysSurveyQuestionRelation : ls) {
						this.pm.remove(sysSurveyQuestionRelation);
					}
				}
				suu.get(0).setUser_id( model.getUser_id() );
				suu.get(0).setPerson_name( model.getPerson_name() );
				suu.get(0).setSex( model.getSex() );
				suu.get(0).setAge( model.getAge() );
				suu.get(0).setEnable("1");
				suu.get(0).setCreater_id( user.getUserid()+"");
				suu.get(0).setDelete_flg("0");
				suu.get(0).setCreate_date( DateTimeUtil.parse() );
				suu.get(0).setExam_center_id(user.getCenter_id()+"");
				this.pm.update(suu.get(0));
				id=suu.get(0).getObjectId()+"";
			}else{
				sysSurvey  su = new  sysSurvey();
				su.setUser_id( model.getUser_id() );
				su.setPerson_name( model.getPerson_name() );
				su.setSex( model.getSex() );
				su.setAge( model.getAge() );
				su.setEnable("1");
				su.setCreater_id( user.getUserid()+"");
				su.setDelete_flg("0");
				su.setCreate_date( DateTimeUtil.parse() );
				su.setExam_center_id(user.getCenter_id()+"");
				this.pm.save(su);
				id=su.getObjectId()+"";
			}
			
			
			if(model.getShujulist()!=null && !"".equals(model.getShujulist())){
				
				JSONArray liArry = JSONArray.fromObject(model.getShujulist());
				List<sysSurveyQuestionRelation> lis = (List<sysSurveyQuestionRelation>) JSONArray
				.toCollection(liArry,sysSurveyQuestionRelation.class);
				for (sysSurveyQuestionRelation s : lis) {
					s.setSurvey_id(id);
					pm.save(s);
				}
			}
	}
}
