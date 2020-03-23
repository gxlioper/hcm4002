package com.hjw.wst.action;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.hjw.util.DateTimeUtil;
import com.hjw.util.PinyinUtil;
import com.hjw.wst.DTO.ExaminationItemDTO;
import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.wst.DTO.ThridLisItemDTO;
import com.hjw.wst.DTO.UserDTO;
import com.hjw.wst.domain.ChargingItemExamItem;
import com.hjw.wst.domain.ExamItemRefandDang;
import com.hjw.wst.domain.ExaminationItem;
import com.hjw.wst.domain.SysLog;
import com.hjw.wst.domain.WebSynchro;
import com.hjw.wst.model.ExaminationItemModel;
import com.hjw.wst.service.BatchService;
import com.hjw.wst.service.ChargingItemExamItemService;
import com.hjw.wst.service.ExamItemRefandDangService;
import com.hjw.wst.service.ExaminationItemService;
import com.hjw.wst.service.SyslogService;
import com.hjw.wst.service.WebSynchroService;
import com.opensymphony.xwork2.ModelDriven;
import com.synjones.framework.exception.ServiceException;
import com.synjones.framework.exception.WebException;
import com.synjones.framework.web.action.BaseAction;

@SuppressWarnings("serial")
public class ExaminationItemAction extends BaseAction implements ModelDriven {
	private ExaminationItemModel model = new ExaminationItemModel();
	private ExaminationItemService  examinationItem;			//数字型
	private ExamItemRefandDangService examItemRefandDangService;//短文本型
	private BatchService batchService;
	private ChargingItemExamItemService chargingItemExamItemService;//收费项目关系
	private int page = 1;
	private int rows = 15;
	private String rt="";
	private String ids;
	private String pinying;
	
	private String referA;
	private String referB;
	private String referC;
	private String referD;

	private String CrisisA;
	private String CrisisB;
	private String CrisisC;
	private String CrisisD;
	
	private SyslogService syslogService;
	private WebSynchroService webSynchroService;
	
	
	
	
	public WebSynchroService getWebSynchroService() {
		return webSynchroService;
	}

	public void setWebSynchroService(WebSynchroService webSynchroService) {
		this.webSynchroService = webSynchroService;
	}

	public SyslogService getSyslogService() {
		return syslogService;
	}

	public void setSyslogService(SyslogService syslogService) {
		this.syslogService = syslogService;
	}

	public String getRt() {
		return rt;
	}

	public void setRt(String rt) {
		this.rt = rt;
	}

	public void setBatchService(BatchService batchService) {
		this.batchService = batchService;
	}
	
	public ChargingItemExamItemService getChargingItemExamItemService() {
		return chargingItemExamItemService;
	}

	public void setChargingItemExamItemService(ChargingItemExamItemService chargingItemExamItemService) {
		this.chargingItemExamItemService = chargingItemExamItemService;
	}

	/**
	 * 
	     * @Title: ExaminationItem   
	     * @Description: TODO(检查项目页面167)   
	     * @param: @return      
	     * @return: String      
	     * @throws
	 */
	public String examinationItem() throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("167");//子功能id 必须填写
		sl.setExplain("检查项目管理");//操作说明
		syslogService.saveSysLog(sl);
		return SUCCESS;
	}
	/**
	 * 
	     * @Title: queryExaminationItem   
	     * @Description: TODO(检查项目列表168)   
	     * @param: @return      
	     * @return: String      
	     * @throws
	 */
	@SuppressWarnings("unchecked")
	public String queryExaminationItem() throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		ExaminationItem ex= new ExaminationItem();
		ex.setItem_num(model.getItem_num());
		ex.setItem_name(model.getItem_name());
		ex.setExam_num(model.getExam_num());
		ex.setView_num(model.getView_num());
		ex.setDep_id(model.getDept_id());
		ex.setItem_class_id(model.getItem_class_id());
		ex.setRemark(model.getRemark());
		PageReturnDTO pageDTO = this.examinationItem.queryExaminationItem(ex,this.getRows(),this.getPage(),this.rt,model.getCharging_item_id(),model.getStartStop(),user.getCenter_num());
		List<ExaminationItemDTO> dtioli=new ArrayList<ExaminationItemDTO>();// 发送到界面的List
		List<ExaminationItemDTO> li=new ArrayList<ExaminationItemDTO>();//获取所有数据List
		li=pageDTO.getRows();	//获取所有数据
		for (ExaminationItemDTO tema : li) {
		  if(tema!=null){
			ExaminationItemDTO tion=new ExaminationItemDTO();
			//短文本型
			if(tema.getItem_category().equals("短文本型")){
				String r="";//参考值
				String d="";//危机值
				List<ExamItemRefandDang> dang=this.examItemRefandDangService.getExamItemRefandDanga(tema.getId());//获取所有文本值
				//遍历文本值
				for (ExamItemRefandDang ds : dang) {
				  if(ds!=null){
					//参考值
					if(ds.getIs_ReforDang().equals("R")){
						r+=ds.getVal_info()+",";
					}
					//危机值
					if(ds.getIs_ReforDang().equals("D")){
						d+=ds.getVal_info()+",";
					}
				  }
				}
				tion.setReference(r);	//参考值
				tion.setRisk(d);		//危机值
			}else if(tema.getItem_category().equals("数字型")){
				    /**
				     * 参考值
				     */
			String	references="";	
					if( tema.getRef_Mmin()!=null && tema.getRef_Mmax()!=null && tema.getRef_Fmin()!=null && tema.getRef_Fmax()!=null){
						   references+="男";
							if(tema.getRef_Mmin()!=null){
									references+=tema.getRef_Mmin();//男性最小值
							}
									references+="~";
							if(tema.getRef_Mmax()!=null){
									references+=tema.getRef_Mmax();//男性最大值
							}
									references+="  女"	;	
							if(tema.getRef_Fmin()!=null){
									references+=tema.getRef_Fmin();//女性最小值	
							}
									references+="~";
							if(tema.getRef_Fmax()!=null){
									references+=tema.getRef_Fmax();//女性最大值				
							}
					}else{
						if(tema.getRef_Mmin()!=null || tema.getRef_Mmax()!=null){
							 references+="男";
							if(tema.getRef_Mmin()!=null){
								references+=tema.getRef_Mmin();//男性最小值
							}
									references+="~";
							if(tema.getRef_Mmax()!=null){
									references+=tema.getRef_Mmax();//男性最大值
							}
							references+="  ";
						}
						if(tema.getRef_Fmin()!=null || tema.getRef_Fmax()!=null){
							references+="女"	;	
							if(tema.getRef_Fmin()!=null){
									references+=tema.getRef_Fmin();//女性最小值	
							}
									references+="~";
							if(tema.getRef_Fmax()!=null){
									references+=tema.getRef_Fmax();//女性最大值				
							}
						}
					}
			
			     
		            tion.setReference(references);//参考值封装
		            /**
		             * 危机值
		             */
		            String	risks="";
		            if(tema.getDang_Mmin()!=null && tema.getDang_Mmax()!=null && tema.getDang_Fmin()!=null && tema.getDang_Fmax()!=null){
		            		risks+="男";    
					    if(tema.getDang_Mmin()!=null){
					    	risks+=tema.getDang_Mmin();//男性最小危机值值
					    }
					    	risks+="~";
			      		if(tema.getDang_Mmax()!=null){
			      			risks+=tema.getDang_Mmax();//男性最大危机值值	
			      		}
			      			risks+="  女";		
			      		if(tema.getDang_Fmin()!=null){
		      				risks+=tema.getDang_Fmin();//女性最小危机值		
			      		}
			      			risks+="~";
			      		if(tema.getDang_Fmax()!=null){
			      			risks+=tema.getDang_Fmax();//女性最大危机值		
			      		}
		            }else{
		            	if(tema.getDang_Mmin()!=null || tema.getDang_Mmax()!=null){
		            		risks+="男";    
						    if(tema.getDang_Mmin()!=null){
						    	risks+=tema.getDang_Mmin();//男性最小危机值值
						    }
						    	risks+="~";
				      		if(tema.getDang_Mmax()!=null){
				      			risks+=tema.getDang_Mmax();//男性最大危机值值	
				      		}
		            	}
		            	if(tema.getDang_Fmin()!=null || tema.getDang_Fmax()!=null){
		            		risks+="  女";		
				      		if(tema.getDang_Fmin()!=null){
			      				risks+=tema.getDang_Fmin();//女性最小危机值		
				      		}
				      			risks+="~";
				      		if(tema.getDang_Fmax()!=null){
				      			risks+=tema.getDang_Fmax();//女性最大危机值		
				      		}
		            	}
		            }
			
				    tion.setRisk(risks);  		  //危机值封装
			}
			tion.setId(tema.getId());						//id
			tion.setItem_num(tema.getItem_num());			//编号
			tion.setItem_name(tema.getItem_name());		//名称
			tion.setItem_pinyin(tema.getItem_pinyin());	//拼音
			tion.setItem_eng_name(tema.getItem_eng_name());//英文名称
			tion.setItem_unit(tema.getItem_unit());    	//单位
			tion.setExam_num(tema.getExam_num());			//关联检验编码
			tion.setView_num(tema.getView_num());          //关联影像编码
			tion.setItem_category(tema.getItem_category());//项目结果类型
			tion.setIs_prints(tema.getIs_prints());        //是否打印
			tion.setSeq_code(tema.getSeq_code());   		//顺序码
			tion.setDefault_value(tema.getDefault_value());//默认值
			tion.setItem_description(tema.getItem_description());//检查项目解释
			tion.setRemark(tema.getRemark());   				  //备注
			tion.setDataName(tema.getDataName());                //项目分类
			tion.setCreatername(tema.getCreatername());          //创建用户
			tion.setCreate_time(tema.getCreate_time());   		  //创建时间
			tion.setUpdatername(tema.getUpdatername());   		  //修改用户
			tion.setUpdate_time(tema.getUpdate_time());   		  //修改时间
			tion.setSex(tema.getSex());
			tion.setDep_name(tema.getDep_name());
			tion.setText(tema.getText());
			tion.setItem_class_id(tema.getItem_class_id());
			tion.setItem_class_name(tema.getItem_class_name());
			tion.setCharging_item_id(tema.getCharging_item_id());
			tion.setExam_itemid(tema.getExam_itemid());
			tion.setCteid(tema.getCteid());
			tion.setIs_Active(tema.getIs_Active());
            tion.setDisease_count(tema.getDisease_count());
			dtioli.add(tion);
		  }
		}
		pageDTO.setRows(dtioli);
		this.outJsonResult(pageDTO);
		return NONE;
	}
	/**
	 * @throws SQLException 
	 * @throws ServiceException 
	 * 
	     * @Title: deleteExaminationItem   
	     * @Description: TODO(删除检查项目169)   
	     * @param: @return      
	     * @return: String      
	     * @throws
	 */
	public String deleteExaminationItem() throws WebException, ServiceException, SQLException{
		this.examinationItem.deleteExaminationItem(ids);
		this.webSynchroService.updateWebSynchro(ids,'4');
		this.message ="删除成功";
		this.outJsonStrResult(message);
		
		UserDTO user = (UserDTO) session.get("username");
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("3");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("169");//子功能id 必须填写
		sl.setExplain("删除检查项目");//操作说明
		syslogService.saveSysLog(sl);
		
		return NONE;
	}
	/**
	 * 
	     * @Title: addExaminationItemPage   
	     * @Description: TODO(检查项目新增页面170)   
	     * @param: @return      
	     * @return: String      
	     * @throws
	 */
	public String addExaminationItemPage() throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		model.setItem_num(this.batchService.GetCreateID("exam_item_code", user.getCenter_num()));
		return SUCCESS;
	}
	/**
	 * @throws SQLException 
	 * @throws ServiceException 
	 * 
	     * @Title: addExaminationItem   
	     * @Description: TODO(检查项目添加171)   
	     * @param: @return      
	     * @return: String      
	     * @throws
	 */
	public String addExaminationItem() throws WebException, ServiceException, SQLException{
		UserDTO user=(UserDTO) session.get("username");
		ExaminationItem tem = new ExaminationItem();
	if(model.getItem_category().equals("数字型")){
		tem.setDep_id(model.getDept_id());
		tem.setItem_num(model.getItem_num());
		tem.setItem_name(model.getItem_name());
		tem.setItem_pinyin(model.getItem_pinyin());
		tem.setItem_eng_name(model.getItem_eng_name());
		tem.setItem_unit(model.getItem_unit());
		tem.setExam_num(model.getExam_num());
		tem.setView_num(model.getView_num());
		tem.setItem_category(model.getItem_category());
		tem.setIs_print(model.getIs_print());
		tem.setSeq_code(model.getSeq_code());
		tem.setItem_description(model.getItem_description());
		tem.setRemark(model.getRemark());
		tem.setRef_Mmax(model.getRef_Mmax());
		tem.setRef_Mmin(model.getRef_Mmin());
		tem.setRef_Fmax(model.getRef_Fmax());
		tem.setRef_Fmin(model.getRef_Fmin());
		tem.setDang_Mmax(model.getDang_Mmax());
		tem.setDang_Mmin(model.getDang_Mmin());
		tem.setDang_Fmax(model.getDang_Fmax());
		tem.setDang_Fmin(model.getDang_Fmin());
		tem.setBrief_mark(model.getBrief_mark());//记入小结标示
		tem.setBrief(model.getBrief());//记入小结
		tem.setSex(model.getSex());
		tem.setCreater(user.getUserid());						//用户
		tem.setCreate_time(DateTimeUtil.parse());				//用户创建时间
		tem.setDefault_value(model.getDefault_value());			//默认值
		tem.setItem_type(model.getItem_type());					//项目类型
		tem.setIs_Active("Y");
		tem.setError_max(model.getError_max());
		tem.setError_min(model.getError_min());
		tem.setItem_result_type(model.getItem_result_type());  //项目结果类型
		this.examinationItem.addExaminationItem(tem, user.getCenter_num());			//执行增加
		this.webSynchroService.updateWebSynchro(tem.getId()+"", '4');
		
		this.message="添加成功";
		this.outJsonStrResult(message);
		
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("1");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("171");//子功能id 必须填写
		sl.setExplain("新增检查项目");//操作说明
		syslogService.saveSysLog(sl);
		
	}else if(model.getItem_category().equals("短文本型")){
		tem.setDep_id(model.getDept_id());
		tem.setItem_num(model.getItem_num());
		tem.setItem_name(model.getItem_name());
		tem.setItem_pinyin(model.getItem_pinyin());
		tem.setItem_eng_name(model.getItem_eng_name());
		tem.setItem_unit(model.getItem_unit());
		tem.setExam_num(model.getExam_num());
		tem.setView_num(model.getView_num());
		tem.setItem_category(model.getItem_category());
		tem.setIs_print(model.getIs_print());
		tem.setSeq_code(model.getSeq_code());
		tem.setItem_description(model.getItem_description());
		tem.setRemark(model.getRemark());
		tem.setItem_type(model.getItem_type());
		tem.setIs_Active("Y");
		tem.setCreater(user.getUserid());
		tem.setCreate_time(DateTimeUtil.parse());
		tem.setDefault_value(model.getDefault_value());			//默认值
		tem.setItem_type(model.getItem_type());					//项目分类	
		tem.setBrief_mark(model.getBrief_mark());//记入小结标示
		tem.setBrief(model.getBrief());//记入小结
		tem.setSex(model.getSex());
		tem.setError_max(model.getError_max());
		tem.setError_min(model.getError_min());
		tem.setItem_result_type(model.getItem_result_type());  //项目结果类型
		tem=this.examinationItem.addExaminationItem(tem, user.getCenter_num());
		this.webSynchroService.updateWebSynchro(tem.getId()+"", '4');
		//参考值1--A
		if(this.referA!=null&&!this.referA.equals("")){
			ExamItemRefandDang dang = new ExamItemRefandDang();//创建文本对象
			dang.setExam_item_id(tem.getId());			  	   //外键检查项目id
			dang.setIs_ReforDang("R");						   //参考值值R
			dang.setVal_info(this.referA);					   //内容
			dang.setVal_index(1);							   //第1个输入框
			dang.setCreater(user.getUserid());			   //用户
			dang.setCreate_time(DateTimeUtil.parse()); 		   //用户时间
			dang.setItem_code(tem.getItem_num());
			this.examItemRefandDangService.addexamItemRefandDang(dang);
		}
		//参考值2--B
		if(this.referB!=null&&!this.referB.equals("")){
			ExamItemRefandDang dang = new ExamItemRefandDang();//创建文本对象
			dang.setExam_item_id(tem.getId());			  	   //外键检查项目id
			dang.setIs_ReforDang("R");						   //参考值值R
			dang.setVal_info(this.referB);					   //内容
			dang.setVal_index(2);							   //第2个输入框
			dang.setCreater(user.getUserid());			   //用户
			dang.setCreate_time(DateTimeUtil.parse()); 		   //用户时间
			dang.setItem_code(tem.getItem_num());
			this.examItemRefandDangService.addexamItemRefandDang(dang);
		}
		//参考值3--C
		if(this.referC!=null&&!this.referC.equals("")){
			ExamItemRefandDang dang = new ExamItemRefandDang();//创建文本对象
			dang.setExam_item_id(tem.getId());			  	   //外键检查项目id
			dang.setIs_ReforDang("R");						   //参考值值R
			dang.setVal_info(this.referC);					   //内容
			dang.setVal_index(3);							   //第3个输入框
			dang.setCreater(user.getUserid());			   //用户
			dang.setCreate_time(DateTimeUtil.parse()); 		   //用户时间
			dang.setItem_code(tem.getItem_num());
			this.examItemRefandDangService.addexamItemRefandDang(dang);
		}
		//参考值4--D
		if(this.referD!=null&&!this.referD.equals("")){
			ExamItemRefandDang dang = new ExamItemRefandDang();//创建文本对象
			dang.setExam_item_id(tem.getId());			  	   //外键检查项目id
			dang.setIs_ReforDang("R");						   //参考值值R
			dang.setVal_info(this.referD);					   //内容
			dang.setVal_index(4);							   //第4个输入框
			dang.setCreater(user.getUserid());			   //用户
			dang.setCreate_time(DateTimeUtil.parse()); 		   //用户时间
			dang.setItem_code(tem.getItem_num());
			this.examItemRefandDangService.addexamItemRefandDang(dang);
		}
		//危机值1--A
		if(this.CrisisA!=null&&!this.CrisisA.equals("")){
			ExamItemRefandDang dang = new ExamItemRefandDang();//创建文本对象
			dang.setExam_item_id(tem.getId());			  	   //外键检查项目id
			dang.setIs_ReforDang("D");						   //危机值D
			dang.setVal_info(this.CrisisA);					   //内容
			dang.setVal_index(1);							   //第1个输入框
			dang.setCreater(user.getUserid());			   //用户
			dang.setCreate_time(DateTimeUtil.parse()); 		   //用户时间
			dang.setItem_code(tem.getItem_num());
			this.examItemRefandDangService.addexamItemRefandDang(dang);
		}
		//危机值2--B
		if(this.CrisisB!=null&&!this.CrisisB.equals("")){
			ExamItemRefandDang dang = new ExamItemRefandDang();//创建文本对象
			dang.setExam_item_id(tem.getId());			  	   //外键检查项目id
			dang.setIs_ReforDang("D");						   //危机值D
			dang.setVal_info(this.CrisisB);					   //内容
			dang.setVal_index(2);							   //第2个输入框
			dang.setCreater(user.getUserid());			   //用户
			dang.setCreate_time(DateTimeUtil.parse()); 		   //用户时间
			dang.setItem_code(tem.getItem_num());
			this.examItemRefandDangService.addexamItemRefandDang(dang);
		}
		//危机值3--C
		if(this.CrisisC!=null&&!this.CrisisC.equals("")){
			ExamItemRefandDang dang = new ExamItemRefandDang();//创建文本对象
			dang.setExam_item_id(tem.getId());			  	   //外键检查项目id
			dang.setIs_ReforDang("D");						   //危机值D
			dang.setVal_info(this.CrisisC);					   //内容
			dang.setVal_index(3);							   //第3个输入框
			dang.setCreater(user.getUserid());			   //用户
			dang.setCreate_time(DateTimeUtil.parse()); 		   //用户时间
			dang.setItem_code(tem.getItem_num());
			this.examItemRefandDangService.addexamItemRefandDang(dang);
		}
		//危机值4--D
		if(this.CrisisD!=null&&!this.CrisisD.equals("")){
			ExamItemRefandDang dang = new ExamItemRefandDang();//创建文本对象
			dang.setExam_item_id(tem.getId());			  	   //外键检查项目id
			dang.setIs_ReforDang("D");						   //危机值D
			dang.setVal_info(this.CrisisD);					   //内容
			dang.setVal_index(4);							   //第4个输入框
			dang.setCreater(user.getUserid());			   //用户
			dang.setCreate_time(DateTimeUtil.parse()); 		   //用户时间
			dang.setItem_code(tem.getItem_num());
			this.examItemRefandDangService.addexamItemRefandDang(dang);
		}
		this.message="添加成功";
		this.outJsonStrResult(message);
		
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("1");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("171");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		
	}
		return NONE;
	}
	/**
	 * 
	     * @Title: pinying   
	     * @Description: TODO(检查项目名称转拼音172)   
	     * @param: @return      
	     * @return: String      
	     * @throws
	 */
	public String pinying() throws WebException{
		String py = PinyinUtil.getTheFirstMathedPinYin(this.pinying);
		if(py!=null){
			this.outJsonStrResult(py);
		}
		return this.NONE;
	}
	/**
	 * 
	     * @Title: getexam_num   
	     * @Description: TODO(验证检验编码唯一173)   
	     * @param: @return      
	     * @return: String      
	     * @throws
	 */
	@SuppressWarnings("static-access")
	public String getexamnum() throws WebException{
		if(model.getExam_num()!=null&&!model.getExam_num().equals("")){
			ExaminationItem exa = examinationItem.getexam_num(model.getExam_num());
			if(exa==null){
				this.message="ok";
			}else{
				this.message="no";
			}
			this.outJsonStrResult(message);
		}else{
			this.message="ok";
			this.outJsonStrResult(this.message);
		}
		return this.NONE;
	}
	/**
	 * 
	     * @Title: getviewnum   
	     * @Description: TODO(验证关联影像编码174)   
	     * @param: @return      
	     * @return: String      
	     * @throws
	 */
	@SuppressWarnings("static-access")
	public String getviewnum() throws WebException{
		if(model.getView_num()!=null&&!model.getView_num().equals("")){
			ExaminationItem exa = examinationItem.getviewnum(model.getView_num());
			if(exa==null){
				this.message="ok";
			}else{
				this.message="no";
			}
			this.outJsonStrResult(message);
		}else{
			this.message="ok";
			this.outJsonStrResult(this.message);
		}
		return this.NONE;
	}
	/**
	 * 
	     * @Title: updateExaminationItemPage   
	     * @Description: TODO(修改页面175)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	@SuppressWarnings("static-access")
	public String updateExaminationItemPage() throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		ExaminationItem tem = this.examinationItem.findClass(model.getId(), user.getCenter_num());
		model.setId(tem.getId());
		model.setDept_id(tem.getDep_id());
		model.setItem_num(tem.getItem_num());
		model.setItem_name(tem.getItem_name());
		model.setItem_pinyin(tem.getItem_pinyin());
		model.setItem_eng_name(tem.getItem_eng_name());
		model.setItem_unit(tem.getItem_unit());
		model.setExam_num(tem.getExam_num());
		model.setView_num(tem.getView_num());
		model.setItem_category(tem.getItem_category());
		model.setDefault_value(tem.getDefault_value());
		model.setIs_print(tem.getIs_print());
		model.setSeq_code(tem.getSeq_code());
		model.setItem_description(tem.getItem_description());
		model.setRemark(tem.getRemark());
		model.setBrief_mark(tem.getBrief_mark());//记入小结标示
		model.setBrief(tem.getBrief());//记入小结
		model.setSex(tem.getSex());
		model.setError_max(tem.getError_max());
		model.setError_min(tem.getError_min());
		model.setItem_result_type(tem.getItem_result_type());
	if(tem.getItem_category().equals("数字型")){
		model.setRef_Mmax(tem.getRef_Mmax());
		model.setRef_Mmin(tem.getRef_Mmin());
		model.setRef_Fmax(tem.getRef_Fmax());
		model.setRef_Fmin(tem.getRef_Fmin());
		model.setDang_Mmax(tem.getDang_Mmax());
		model.setDang_Mmin(tem.getDang_Mmin());
		model.setDang_Fmax(tem.getDang_Fmax());
		model.setDang_Fmin(tem.getDang_Fmin());
		model.setDefault_value(tem.getDefault_value());			//默认值
		model.setItem_type(tem.getItem_type());					//项目类型
	}else{
			//短文本型
		List<ExamItemRefandDang> li= this.examItemRefandDangService.getExamItemRefandDanga(tem.getId());
		for (ExamItemRefandDang dan : li) {
			//参考值1
			if(dan.getVal_index()==1&&dan.getIs_ReforDang().equals("R")){
				this.setReferA(dan.getVal_info());
			}
			//参考值2
			if(dan.getVal_index()==2&&dan.getIs_ReforDang().equals("R")){
				this.setReferB(dan.getVal_info());
			}
			//参考值3
			if(dan.getVal_index()==3&&dan.getIs_ReforDang().equals("R")){
				this.setReferC(dan.getVal_info());
			}
			//参考值4
			if(dan.getVal_index()==4&&dan.getIs_ReforDang().equals("R")){
				this.setReferD(dan.getVal_info());
			}
			//危机值1
			if(dan.getVal_index()==1&&dan.getIs_ReforDang().equals("D")){
				this.setCrisisA(dan.getVal_info());
			}
			//危机值2
			if(dan.getVal_index()==2&&dan.getIs_ReforDang().equals("D")){
				this.setCrisisB(dan.getVal_info());
			}
			//危机值3
			if(dan.getVal_index()==3&&dan.getIs_ReforDang().equals("D")){
				this.setCrisisC(dan.getVal_info());
			}
			//危机值4
			if(dan.getVal_index()==4&&dan.getIs_ReforDang().equals("D")){
				this.setCrisisD(dan.getVal_info());
			}
		}
	}
	return this.SUCCESS;
}
	/**
	 * @throws SQLException 
	 * @throws ServiceException 
	 * 
	     * @Title: updateExaminationItem   
	     * @Description: TODO(检查项目修改方法176)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String updateExaminationItem() throws WebException, ServiceException, SQLException{
		//tem.setItem_num(model.getItem_num());
		UserDTO user=(UserDTO) session.get("username");
		if(model.getItem_category().equals("数字型")){
			ExaminationItem tem = this.examinationItem.findClass(model.getId(), user.getCenter_num());
			tem.setDep_id(model.getDept_id());
			tem.setItem_name(model.getItem_name());
			tem.setItem_pinyin(model.getItem_pinyin());
			tem.setItem_eng_name(model.getItem_eng_name());
			tem.setItem_unit(model.getItem_unit());
			tem.setExam_num(model.getExam_num());
			tem.setView_num(model.getView_num());
			tem.setItem_category(model.getItem_category());
			tem.setIs_print(model.getIs_print());
			tem.setSeq_code(model.getSeq_code());
			tem.setItem_description(model.getItem_description());
			tem.setRemark(model.getRemark());
			tem.setRef_Mmax(model.getRef_Mmax());
			tem.setRef_Mmin(model.getRef_Mmin());
			tem.setRef_Fmax(model.getRef_Fmax());
			tem.setRef_Fmin(model.getRef_Fmin());
			tem.setDang_Mmax(model.getDang_Mmax());
			tem.setDang_Mmin(model.getDang_Mmin());
			tem.setDang_Fmax(model.getDang_Fmax());
			tem.setDang_Fmin(model.getDang_Fmin());
			tem.setError_max(model.getError_max());
			tem.setError_min(model.getError_min());
			tem.setSex(model.getSex());
			tem.setUpdater(user.getUserid());						//修改用户
			tem.setUpdate_time(DateTimeUtil.parse());				//修改时间创建时间
			tem.setItem_type(model.getItem_type());					//项目类型
			//tem.setIs_Active("Y");
			tem.setBrief_mark(model.getBrief_mark());//记入小结标示
			tem.setBrief(model.getBrief());//记入小结
			tem.setItem_result_type(model.getItem_result_type());  //项目结果类型
			this.examinationItem.updateExaminationItem(tem, user.getCenter_num());	    //执行修改
		
			this.webSynchroService.updateWebSynchro(model.getId()+"", '4');
			
			
			this.message="修改成功";
			this.outJsonStrResult(message);
		}else{
			ExaminationItem tem = this.examinationItem.findClass(model.getId(), user.getCenter_num());
			tem.setDep_id(model.getDept_id());
			tem.setItem_name(model.getItem_name());
			tem.setItem_pinyin(model.getItem_pinyin());
			tem.setItem_eng_name(model.getItem_eng_name());
			tem.setItem_unit(model.getItem_unit());
			tem.setExam_num(model.getExam_num());
			tem.setView_num(model.getView_num());
			tem.setItem_category(model.getItem_category());
			tem.setIs_print(model.getIs_print());
			tem.setSeq_code(model.getSeq_code());
			tem.setItem_description(model.getItem_description());
			tem.setRemark(model.getRemark());
			tem.setItem_type(model.getItem_type());
			tem.setUpdater(user.getUserid());
			tem.setUpdate_time(DateTimeUtil.parse());
			tem.setItem_type(model.getItem_type());					//项目类型
			tem.setBrief_mark(model.getBrief_mark());//记入小结标示
			tem.setBrief(model.getBrief());//记入小结
			tem.setError_max(model.getError_max());
			tem.setError_min(model.getError_min());
			tem.setSex(model.getSex());
			tem.setItem_result_type(model.getItem_result_type());  //项目结果类型
			this.examinationItem.updateExaminationItem(tem, user.getCenter_num());
			
			this.webSynchroService.updateWebSynchro(model.getId()+"", '4');	
			
			//List<ExamItemRefandDang> li= this.examItemRefandDangService.getExamItemRefandDanga(model.getId());
			int lii=examItemRefandDangService.deletExamItemRefandDang(model.getId());
			//数值修改文本，文本走添加方法
							//参考值1-A
							if(this.referA!=null&&!this.referA.equals("")){
								ExamItemRefandDang dang = new ExamItemRefandDang();//创建文本对象
								dang.setExam_item_id(tem.getId());			  	   //外键检查项目id
								dang.setIs_ReforDang("R");						   //参考值值R
								dang.setVal_info(this.referA);					   //内容
								dang.setVal_index(1);							   //第1个输入框
								dang.setCreater(user.getCenter_id());			   //用户
								dang.setCreate_time(DateTimeUtil.parse()); 		   //用户时间
								dang.setItem_code(tem.getItem_num());
								this.examItemRefandDangService.addexamItemRefandDang(dang);
							}
							//参考值2--B
							if(this.referB!=null&&!this.referB.equals("")){
								ExamItemRefandDang dang = new ExamItemRefandDang();//创建文本对象
								dang.setExam_item_id(tem.getId());			  	   //外键检查项目id
								dang.setIs_ReforDang("R");						   //参考值值R
								dang.setVal_info(this.referB);					   //内容
								dang.setVal_index(2);							   //第2个输入框
								dang.setCreater(user.getCenter_id());			   //用户
								dang.setCreate_time(DateTimeUtil.parse()); 		   //用户时间
								dang.setItem_code(tem.getItem_num());
								this.examItemRefandDangService.addexamItemRefandDang(dang);
							}
							//参考值3--C
							if(this.referC!=null&&!this.referC.equals("")){
								ExamItemRefandDang dang = new ExamItemRefandDang();//创建文本对象
								dang.setExam_item_id(tem.getId());			  	   //外键检查项目id
								dang.setIs_ReforDang("R");						   //参考值值R
								dang.setVal_info(this.referC);					   //内容
								dang.setVal_index(3);							   //第3个输入框
								dang.setCreater(user.getCenter_id());			   //用户
								dang.setCreate_time(DateTimeUtil.parse()); 		   //用户时间
								dang.setItem_code(tem.getItem_num());
								this.examItemRefandDangService.addexamItemRefandDang(dang);
							}
							//参考值4--D
							if(this.referD!=null&&!this.referD.equals("")){
								ExamItemRefandDang dang = new ExamItemRefandDang();//创建文本对象
								dang.setExam_item_id(tem.getId());			  	   //外键检查项目id
								dang.setIs_ReforDang("R");						   //参考值值R
								dang.setVal_info(this.referD);					   //内容
								dang.setVal_index(4);							   //第4个输入框
								dang.setCreater(user.getCenter_id());			   //用户
								dang.setCreate_time(DateTimeUtil.parse()); 		   //用户时间
								dang.setItem_code(tem.getItem_num());
								this.examItemRefandDangService.addexamItemRefandDang(dang);
							}
							//危机值1--A
							if(this.CrisisA!=null&&!this.CrisisA.equals("")){
								ExamItemRefandDang dang = new ExamItemRefandDang();//创建文本对象
								dang.setExam_item_id(tem.getId());			  	   //外键检查项目id
								dang.setIs_ReforDang("D");						   //危机值D
								dang.setVal_info(this.CrisisA);					   //内容
								dang.setVal_index(1);							   //第1个输入框
								dang.setCreater(user.getCenter_id());			   //用户
								dang.setCreate_time(DateTimeUtil.parse()); 		   //用户时间
								dang.setItem_code(tem.getItem_num());
								this.examItemRefandDangService.addexamItemRefandDang(dang);
							}
							//危机值2--B
							if(this.CrisisB!=null&&!this.CrisisB.equals("")){
								ExamItemRefandDang dang = new ExamItemRefandDang();//创建文本对象
								dang.setExam_item_id(tem.getId());			  	   //外键检查项目id
								dang.setIs_ReforDang("D");						   //危机值D
								dang.setVal_info(this.CrisisB);					   //内容
								dang.setVal_index(2);							   //第2个输入框
								dang.setCreater(user.getCenter_id());			   //用户
								dang.setCreate_time(DateTimeUtil.parse()); 		   //用户时间
								dang.setItem_code(tem.getItem_num());
								this.examItemRefandDangService.addexamItemRefandDang(dang);
							}
							//危机值3--C
							if(this.CrisisC!=null&&!this.CrisisC.equals("")){
								ExamItemRefandDang dang = new ExamItemRefandDang();//创建文本对象
								dang.setExam_item_id(tem.getId());			  	   //外键检查项目id
								dang.setIs_ReforDang("D");						   //危机值D
								dang.setVal_info(this.CrisisC);					   //内容
								dang.setVal_index(3);							   //第3个输入框
								dang.setCreater(user.getCenter_id());			   //用户
								dang.setCreate_time(DateTimeUtil.parse()); 		   //用户时间
								dang.setItem_code(tem.getItem_num());
								this.examItemRefandDangService.addexamItemRefandDang(dang);
							}
							//危机值4--D
							if(this.CrisisD!=null&&!this.CrisisD.equals("")){
								ExamItemRefandDang dang = new ExamItemRefandDang();//创建文本对象
								dang.setExam_item_id(tem.getId());			  	   //外键检查项目id
								dang.setIs_ReforDang("D");						   //危机值D
								dang.setVal_info(this.CrisisD);					   //内容
								dang.setVal_index(4);							   //第4个输入框
								dang.setCreater(user.getCenter_id());			   //用户
								dang.setCreate_time(DateTimeUtil.parse()); 		   //用户时间
								dang.setItem_code(tem.getItem_num());
								this.examItemRefandDangService.addexamItemRefandDang(dang);
							}
					/*}else{
							for (ExamItemRefandDang dan : li) {
								//参考值1
								if(dan.getVal_index()==1&&dan.getIs_ReforDang().equals("R")){
									ExamItemRefandDang exa=this.examItemRefandDangService.findClass(dan.getId());
									exa.setVal_info(this.referA);
									exa.setUpdater(user.getCenter_id());			   //修改用户
									exa.setUpdate_time(DateTimeUtil.parse()); 		   //修改用户时间
									this.examItemRefandDangService.updateExamItemRefandDang(exa);
								}
								//参考值2
								if(dan.getVal_index()==2&&dan.getIs_ReforDang().equals("R")){
									ExamItemRefandDang exa=this.examItemRefandDangService.findClass(dan.getId());
									exa.setVal_info(this.referB);
									exa.setUpdater(user.getCenter_id());			   //修改用户
									exa.setUpdate_time(DateTimeUtil.parse()); 		   //修改用户时间
									this.examItemRefandDangService.updateExamItemRefandDang(exa);
								}
								//参考值3
								if(dan.getVal_index()==3&&dan.getIs_ReforDang().equals("R")){
									ExamItemRefandDang exa=this.examItemRefandDangService.findClass(dan.getId());
									exa.setVal_info(this.referC);
									exa.setUpdater(user.getCenter_id());			   //修改用户
									exa.setUpdate_time(DateTimeUtil.parse()); 		   //修改用户时间
									this.examItemRefandDangService.updateExamItemRefandDang(exa);
								}
								//参考值4
								if(dan.getVal_index()==4&&dan.getIs_ReforDang().equals("R")){
									ExamItemRefandDang exa=this.examItemRefandDangService.findClass(dan.getId());
									exa.setVal_info(this.referD);
									exa.setUpdater(user.getCenter_id());			   //修改用户
									exa.setUpdate_time(DateTimeUtil.parse()); 		   //修改用户时间
									this.examItemRefandDangService.updateExamItemRefandDang(exa);
								}
								//危机值1
								if(dan.getVal_index()==1&&dan.getIs_ReforDang().equals("D")){
									ExamItemRefandDang exa=this.examItemRefandDangService.findClass(dan.getId());
									exa.setVal_info(this.CrisisA);
									exa.setUpdater(user.getCenter_id());			   //修改用户
									exa.setUpdate_time(DateTimeUtil.parse()); 		   //修改用户时间
									this.examItemRefandDangService.updateExamItemRefandDang(exa);
								}
								//危机值2
								if(dan.getVal_index()==2&&dan.getIs_ReforDang().equals("D")){
									ExamItemRefandDang exa=this.examItemRefandDangService.findClass(dan.getId());
									exa.setVal_info(this.CrisisB);
									exa.setUpdater(user.getCenter_id());			   //修改用户
									exa.setUpdate_time(DateTimeUtil.parse()); 		   //修改用户时间
									this.examItemRefandDangService.updateExamItemRefandDang(exa);
								}
								//危机值3
								if(dan.getVal_index()==3&&dan.getIs_ReforDang().equals("D")){
									ExamItemRefandDang exa=this.examItemRefandDangService.findClass(dan.getId());
									exa.setVal_info(this.CrisisC);
									exa.setUpdater(user.getCenter_id());			   //修改用户
									exa.setUpdate_time(DateTimeUtil.parse()); 		   //修改用户时间
									this.examItemRefandDangService.updateExamItemRefandDang(exa);
								}
								//危机值4
								if(dan.getVal_index()==4&&dan.getIs_ReforDang().equals("D")){
									ExamItemRefandDang exa=this.examItemRefandDangService.findClass(dan.getId());
									exa.setVal_info(this.CrisisD);
									exa.setUpdater(user.getCenter_id());			   //修改用户
									exa.setUpdate_time(DateTimeUtil.parse()); 		   //修改用户时间
									this.examItemRefandDangService.updateExamItemRefandDang(exa);
								}
							}*/
			this.message="修改成功";
			this.outJsonStrResult(message);
			
			SysLog sl =  new SysLog();
			sl.setCenter_num(user.getCenter_num());
			sl.setUserid(user.getUserid()+"");
			sl.setOper_type("2");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
			sl.setXtgnid("");//可不填写
			sl.setXtgnid2("176");//子功能id 必须填写
			sl.setExplain("");//操作说明
			syslogService.saveSysLog(sl);
			
		}
		return this.NONE;
	}
	/**
	 * 
	     * @Title: getItemId   
	     * @Description: TODO(检查项目验证是否已收费177)   
	     * @param: @return      
	     * @return: String      
	     * @throws
	 */
	public String getItemId(){
		ChargingItemExamItem em = this.chargingItemExamItemService.getItemId(model.getId());
		if(em==null){
			this.message="ok";//可删除
		}else{
			this.message="no";//不可删除
		}
		this.outJsonStrResult(message);
		return this.NONE;
	}
	/**
	 * 检验项目明细页面913
	     * @Title: getThridLisItemPage   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String getThridLisItemPage() throws  WebException{
		UserDTO user=(UserDTO) session.get("username");
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("913");//子功能id 必须填写
		sl.setExplain("");//操作说明
		return SUCCESS;
	}
	/**
	 * Lis检验项目细项列表914
	     * @Title: get   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String getExamintionThridLisItemList() throws  WebException{
		List<ThridLisItemDTO>  li = this.examinationItem.getThridLisItemList(model);
		this.outJsonResult(li);
		this.model.setExam_num(model.getExam_num());
		UserDTO user=(UserDTO) session.get("username");
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("914");//子功能id 必须填写
		sl.setExplain("");//操作说明
		return NONE;
	}
	/**
	 *  Lis检验项目细项列表915带分页
	     * @Title: getExamintionThridLisItemTable   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String getExamintionThridLisItemTable() throws  WebException{
		PageReturnDTO  li = this.examinationItem.getThridLisItemTable(model,this.pagesize,this.page);
		this.outJsonResult(li);
		
		UserDTO user=(UserDTO) session.get("username");
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("915");//子功能id 必须填写
		sl.setExplain("");//操作说明
		return NONE;
	}
	
	/**
	 * 
	     * @Title: queryExaminationItem   
	     * @Description: TODO(中心表带配置的 检查项目列表)   
	     * @param: @return      
	     * @return: String      
	     * @throws
	 */
	@SuppressWarnings("unchecked")
	public String queryExaminationItemByConfig() throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		ExaminationItem ex= new ExaminationItem();
		ex.setItem_num(model.getItem_num());
		ex.setItem_name(model.getItem_name());
		ex.setExam_num(model.getExam_num());
		ex.setView_num(model.getView_num());
		ex.setDep_id(model.getDept_id());
		ex.setItem_class_id(model.getItem_class_id());
		ex.setRemark(model.getRemark());
		PageReturnDTO pageDTO = this.examinationItem.queryExaminationItemByConfig(ex,this.getRows(),this.getPage(),this.rt,model.getCharging_item_id(), user.getCenter_num());
		List<ExaminationItemDTO> dtioli=new ArrayList<ExaminationItemDTO>();// 发送到界面的List
		List<ExaminationItemDTO> li=new ArrayList<ExaminationItemDTO>();//获取所有数据List
		li=pageDTO.getRows();	//获取所有数据
		for (ExaminationItemDTO tema : li) {
		  if(tema!=null){
			ExaminationItemDTO tion=new ExaminationItemDTO();
			//短文本型
			if(tema.getItem_category().equals("短文本型")){
				String r="";//参考值
				String d="";//危机值
				List<ExamItemRefandDang> dang=this.examItemRefandDangService.getExamItemRefandDanga(tema.getId());//获取所有文本值
				//遍历文本值
				for (ExamItemRefandDang ds : dang) {
				  if(ds!=null){
					//参考值
					if(ds.getIs_ReforDang().equals("R")){
						r+=ds.getVal_info()+",";
					}
					//危机值
					if(ds.getIs_ReforDang().equals("D")){
						d+=ds.getVal_info()+",";
					}
				  }
				}
				tion.setReference(r);	//参考值
				tion.setRisk(d);		//危机值
			}else if(tema.getItem_category().equals("数字型")){
				    /**
				     * 参考值
				     */
			String	references="";	
					if( tema.getRef_Mmin()!=null && tema.getRef_Mmax()!=null && tema.getRef_Fmin()!=null && tema.getRef_Fmax()!=null){
						   references+="男";
							if(tema.getRef_Mmin()!=null){
									references+=tema.getRef_Mmin();//男性最小值
							}
									references+="~";
							if(tema.getRef_Mmax()!=null){
									references+=tema.getRef_Mmax();//男性最大值
							}
									references+="  女"	;	
							if(tema.getRef_Fmin()!=null){
									references+=tema.getRef_Fmin();//女性最小值	
							}
									references+="~";
							if(tema.getRef_Fmax()!=null){
									references+=tema.getRef_Fmax();//女性最大值				
							}
					}else{
						if(tema.getRef_Mmin()!=null || tema.getRef_Mmax()!=null){
							 references+="男";
							if(tema.getRef_Mmin()!=null){
								references+=tema.getRef_Mmin();//男性最小值
							}
									references+="~";
							if(tema.getRef_Mmax()!=null){
									references+=tema.getRef_Mmax();//男性最大值
							}
							references+="  ";
						}
						if(tema.getRef_Fmin()!=null || tema.getRef_Fmax()!=null){
							references+="女"	;	
							if(tema.getRef_Fmin()!=null){
									references+=tema.getRef_Fmin();//女性最小值	
							}
									references+="~";
							if(tema.getRef_Fmax()!=null){
									references+=tema.getRef_Fmax();//女性最大值				
							}
						}
					}
			
			     
		            tion.setReference(references);//参考值封装
		            /**
		             * 危机值
		             */
		            String	risks="";
		            if(tema.getDang_Mmin()!=null && tema.getDang_Mmax()!=null && tema.getDang_Fmin()!=null && tema.getDang_Fmax()!=null){
		            		risks+="男";    
					    if(tema.getDang_Mmin()!=null){
					    	risks+=tema.getDang_Mmin();//男性最小危机值值
					    }
					    	risks+="~";
			      		if(tema.getDang_Mmax()!=null){
			      			risks+=tema.getDang_Mmax();//男性最大危机值值	
			      		}
			      			risks+="  女";		
			      		if(tema.getDang_Fmin()!=null){
		      				risks+=tema.getDang_Fmin();//女性最小危机值		
			      		}
			      			risks+="~";
			      		if(tema.getDang_Fmax()!=null){
			      			risks+=tema.getDang_Fmax();//女性最大危机值		
			      		}
		            }else{
		            	if(tema.getDang_Mmin()!=null || tema.getDang_Mmax()!=null){
		            		risks+="男";    
						    if(tema.getDang_Mmin()!=null){
						    	risks+=tema.getDang_Mmin();//男性最小危机值值
						    }
						    	risks+="~";
				      		if(tema.getDang_Mmax()!=null){
				      			risks+=tema.getDang_Mmax();//男性最大危机值值	
				      		}
		            	}
		            	if(tema.getDang_Fmin()!=null || tema.getDang_Fmax()!=null){
		            		risks+="  女";		
				      		if(tema.getDang_Fmin()!=null){
			      				risks+=tema.getDang_Fmin();//女性最小危机值		
				      		}
				      			risks+="~";
				      		if(tema.getDang_Fmax()!=null){
				      			risks+=tema.getDang_Fmax();//女性最大危机值		
				      		}
		            	}
		            }
			
				    tion.setRisk(risks);  		  //危机值封装
			}
			tion.setId(tema.getId());						//id
			tion.setItem_num(tema.getItem_num());			//编号
			tion.setItem_name(tema.getItem_name());		//名称
			tion.setItem_pinyin(tema.getItem_pinyin());	//拼音
			tion.setItem_eng_name(tema.getItem_eng_name());//英文名称
			tion.setItem_unit(tema.getItem_unit());    	//单位
			tion.setExam_num(tema.getExam_num());			//关联检验编码
			tion.setView_num(tema.getView_num());          //关联影像编码
			tion.setItem_category(tema.getItem_category());//项目结果类型
			tion.setIs_prints(tema.getIs_prints());        //是否打印
			tion.setSeq_code(tema.getSeq_code());   		//顺序码
			tion.setDefault_value(tema.getDefault_value());//默认值
			tion.setItem_description(tema.getItem_description());//检查项目解释
			tion.setRemark(tema.getRemark());   				  //备注
			tion.setDataName(tema.getDataName());                //项目分类
			tion.setCreatername(tema.getCreatername());          //创建用户
			tion.setCreate_time(tema.getCreate_time());   		  //创建时间
			tion.setUpdatername(tema.getUpdatername());   		  //修改用户
			tion.setUpdate_time(tema.getUpdate_time());   		  //修改时间
			tion.setSex(tema.getSex());
			tion.setDep_name(tema.getDep_name());
			tion.setText(tema.getText());
			tion.setItem_class_id(tema.getItem_class_id());
			tion.setItem_class_name(tema.getItem_class_name());
			tion.setCharging_item_id(tema.getCharging_item_id());
			tion.setExam_itemid(tema.getExam_itemid());
			tion.setCteid(tema.getCteid());
			dtioli.add(tion);
		  }
		}
		pageDTO.setRows(dtioli);
		this.outJsonResult(pageDTO);
		return NONE;
	}
	
	
	/**
	 * 
	     * @Title: queryExaminationItem   
	     * @Description: TODO(新增管理 检查项目列表)   
	     * @param: @return      
	     * @return: String      
	     * @throws
	 */
	@SuppressWarnings("unchecked")
	public String queryExamItemByOwnDept() throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		ExaminationItem ex= new ExaminationItem();
		ex.setItem_num(model.getItem_num());
		ex.setItem_name(model.getItem_name());
		ex.setExam_num(model.getExam_num());
		ex.setView_num(model.getView_num());
		ex.setDep_id(model.getDept_id());
		ex.setItem_class_id(model.getItem_class_id());
		ex.setRemark(model.getRemark());
		PageReturnDTO pageDTO = this.examinationItem.queryExamItemByOwnDept(ex,this.getRows(),this.getPage(),this.rt,model.getCharging_item_id(), user.getCenter_num());
		List<ExaminationItemDTO> dtioli=new ArrayList<ExaminationItemDTO>();// 发送到界面的List
		List<ExaminationItemDTO> li=new ArrayList<ExaminationItemDTO>();//获取所有数据List
		li=pageDTO.getRows();	//获取所有数据
		for (ExaminationItemDTO tema : li) {
		  if(tema!=null){
			ExaminationItemDTO tion=new ExaminationItemDTO();
			//短文本型
			if(tema.getItem_category().equals("短文本型")){
				String r="";//参考值
				String d="";//危机值
				List<ExamItemRefandDang> dang=this.examItemRefandDangService.getExamItemRefandDanga(tema.getId());//获取所有文本值
				//遍历文本值
				for (ExamItemRefandDang ds : dang) {
				  if(ds!=null){
					//参考值
					if(ds.getIs_ReforDang().equals("R")){
						r+=ds.getVal_info()+",";
					}
					//危机值
					if(ds.getIs_ReforDang().equals("D")){
						d+=ds.getVal_info()+",";
					}
				  }
				}
				tion.setReference(r);	//参考值
				tion.setRisk(d);		//危机值
			}else if(tema.getItem_category().equals("数字型")){
				    /**
				     * 参考值
				     */
			String	references="";	
					if( tema.getRef_Mmin()!=null && tema.getRef_Mmax()!=null && tema.getRef_Fmin()!=null && tema.getRef_Fmax()!=null){
						   references+="男";
							if(tema.getRef_Mmin()!=null){
									references+=tema.getRef_Mmin();//男性最小值
							}
									references+="~";
							if(tema.getRef_Mmax()!=null){
									references+=tema.getRef_Mmax();//男性最大值
							}
									references+="  女"	;	
							if(tema.getRef_Fmin()!=null){
									references+=tema.getRef_Fmin();//女性最小值	
							}
									references+="~";
							if(tema.getRef_Fmax()!=null){
									references+=tema.getRef_Fmax();//女性最大值				
							}
					}else{
						if(tema.getRef_Mmin()!=null || tema.getRef_Mmax()!=null){
							 references+="男";
							if(tema.getRef_Mmin()!=null){
								references+=tema.getRef_Mmin();//男性最小值
							}
									references+="~";
							if(tema.getRef_Mmax()!=null){
									references+=tema.getRef_Mmax();//男性最大值
							}
							references+="  ";
						}
						if(tema.getRef_Fmin()!=null || tema.getRef_Fmax()!=null){
							references+="女"	;	
							if(tema.getRef_Fmin()!=null){
									references+=tema.getRef_Fmin();//女性最小值	
							}
									references+="~";
							if(tema.getRef_Fmax()!=null){
									references+=tema.getRef_Fmax();//女性最大值				
							}
						}
					}
			
			     
		            tion.setReference(references);//参考值封装
		            /**
		             * 危机值
		             */
		            String	risks="";
		            if(tema.getDang_Mmin()!=null && tema.getDang_Mmax()!=null && tema.getDang_Fmin()!=null && tema.getDang_Fmax()!=null){
		            		risks+="男";    
					    if(tema.getDang_Mmin()!=null){
					    	risks+=tema.getDang_Mmin();//男性最小危机值值
					    }
					    	risks+="~";
			      		if(tema.getDang_Mmax()!=null){
			      			risks+=tema.getDang_Mmax();//男性最大危机值值	
			      		}
			      			risks+="  女";		
			      		if(tema.getDang_Fmin()!=null){
		      				risks+=tema.getDang_Fmin();//女性最小危机值		
			      		}
			      			risks+="~";
			      		if(tema.getDang_Fmax()!=null){
			      			risks+=tema.getDang_Fmax();//女性最大危机值		
			      		}
		            }else{
		            	if(tema.getDang_Mmin()!=null || tema.getDang_Mmax()!=null){
		            		risks+="男";    
						    if(tema.getDang_Mmin()!=null){
						    	risks+=tema.getDang_Mmin();//男性最小危机值值
						    }
						    	risks+="~";
				      		if(tema.getDang_Mmax()!=null){
				      			risks+=tema.getDang_Mmax();//男性最大危机值值	
				      		}
		            	}
		            	if(tema.getDang_Fmin()!=null || tema.getDang_Fmax()!=null){
		            		risks+="  女";		
				      		if(tema.getDang_Fmin()!=null){
			      				risks+=tema.getDang_Fmin();//女性最小危机值		
				      		}
				      			risks+="~";
				      		if(tema.getDang_Fmax()!=null){
				      			risks+=tema.getDang_Fmax();//女性最大危机值		
				      		}
		            	}
		            }
			
				    tion.setRisk(risks);  		  //危机值封装
			}
			tion.setId(tema.getId());						//id
			tion.setItem_num(tema.getItem_num());			//编号
			tion.setItem_name(tema.getItem_name());		//名称
			tion.setItem_pinyin(tema.getItem_pinyin());	//拼音
			tion.setItem_eng_name(tema.getItem_eng_name());//英文名称
			tion.setItem_unit(tema.getItem_unit());    	//单位
			tion.setExam_num(tema.getExam_num());			//关联检验编码
			tion.setView_num(tema.getView_num());          //关联影像编码
			tion.setItem_category(tema.getItem_category());//项目结果类型
			tion.setIs_prints(tema.getIs_prints());        //是否打印
			tion.setSeq_code(tema.getSeq_code());   		//顺序码
			tion.setDefault_value(tema.getDefault_value());//默认值
			tion.setItem_description(tema.getItem_description());//检查项目解释
			tion.setRemark(tema.getRemark());   				  //备注
			tion.setDataName(tema.getDataName());                //项目分类
			tion.setCreatername(tema.getCreatername());          //创建用户
			tion.setCreate_time(tema.getCreate_time());   		  //创建时间
			tion.setUpdatername(tema.getUpdatername());   		  //修改用户
			tion.setUpdate_time(tema.getUpdate_time());   		  //修改时间
			tion.setSex(tema.getSex());
			tion.setDep_name(tema.getDep_name());
			tion.setText(tema.getText());
			tion.setItem_class_id(tema.getItem_class_id());
			tion.setItem_class_name(tema.getItem_class_name());
			tion.setCharging_item_id(tema.getCharging_item_id());
			tion.setExam_itemid(tema.getExam_itemid());
			tion.setCteid(tema.getCteid());
			tion.setItem_result_type(tema.getItem_result_type());
			dtioli.add(tion);
		  }
		}
		pageDTO.setRows(dtioli);
		this.outJsonResult(pageDTO);
		return NONE;
	}
	/**
	 * 
	     * @Title: updateExaminationStopOrStart   
	     * @Description: TODO(检查项目启用/停用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String updateExaminationStopOrStart()throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		try {
			ExaminationItem eti = this.examinationItem.findClass(Long.valueOf(model.getIds()), user.getCenter_num());
			if(eti.getIs_Active().equals("Y")){
				eti.setIs_Active("N");
				eti.setUpdate_time(DateTimeUtil.parse());;
				eti.setUpdater(user.getUserid());
			}else if(eti.getIs_Active().equals("N")){
				eti.setIs_Active("Y");
				eti.setUpdate_time(DateTimeUtil.parse());
				eti.setUpdater(user.getUserid());
			}
			this.examinationItem.updateExaminationItem(eti, user.getCenter_num());
			this.message="ok-成功";
		} catch (Exception e) {
			this.message="error-"+e.toString();
			e.printStackTrace();
		}

		this.outJsonStrResult(this.message);
		
		return NONE;
	}
	
	@Override
	public Object getModel() {
		return model;
	}
	public int getPage() {
		return page;
	}
	public int getRows() {
		return rows;
	}
	
	public void setPage(int page) {
		this.page = page;
	}
	public void setRows(int rows) {
		this.rows = rows;
	}
	
	public String getIds() {
		return ids;
	}
	public ExaminationItemService getExaminationItem() {
		return examinationItem;
	}

	public void setExaminationItem(ExaminationItemService examinationItem) {
		this.examinationItem = examinationItem;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}
	public String getPinying() {
		return pinying;
	}
	public void setPinying(String pinying) {
		this.pinying = pinying;
	}
	public String getReferA() {
		return referA;
	}
	public void setReferA(String referA) {
		this.referA = referA;
	}
	public String getReferB() {
		return referB;
	}
	public void setReferB(String referB) {
		this.referB = referB;
	}
	public String getReferC() {
		return referC;
	}
	public void setReferC(String referC) {
		this.referC = referC;
	}
	public String getReferD() {
		return referD;
	}
	public void setReferD(String referD) {
		this.referD = referD;
	}
	public String getCrisisA() {
		return CrisisA;
	}
	public void setCrisisA(String crisisA) {
		CrisisA = crisisA;
	}
	public String getCrisisB() {
		return CrisisB;
	}
	public void setCrisisB(String crisisB) {
		CrisisB = crisisB;
	}
	public String getCrisisC() {
		return CrisisC;
	}
	public void setCrisisC(String crisisC) {
		CrisisC = crisisC;
	}
	public String getCrisisD() {
		return CrisisD;
	}
	public void setCrisisD(String crisisD) {
		CrisisD = crisisD;
	}
	public ExamItemRefandDangService getExamItemRefandDangService() {
		return examItemRefandDangService;
	}
	public void setExamItemRefandDangService(ExamItemRefandDangService examItemRefandDangService) {
		this.examItemRefandDangService = examItemRefandDangService;
	}
	public void setModel(ExaminationItemModel model) {
		this.model = model;
	}
	
}
