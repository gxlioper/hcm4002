package com.hjw.wst.action;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.SQLException;
import java.util.List;

import com.google.gson.Gson;
import com.hjw.config.Logincheck;
import com.hjw.util.DateTimeUtil;
import com.hjw.wst.DTO.QuestChargingDTO;
import com.hjw.wst.DTO.QuestExamDTO;
import com.hjw.wst.DTO.QuestRecordDTO;
import com.hjw.wst.DTO.UserDTO;
import com.hjw.wst.domain.QuestExamRecord;
import com.hjw.wst.domain.QuestExamList;
import com.hjw.wst.domain.SysLog;
import com.hjw.wst.model.QuestModel;
import com.hjw.wst.service.QuestExamService;
import com.hjw.wst.service.SyslogService;
import com.opensymphony.xwork2.ModelDriven;
import com.synjones.framework.exception.WebException;
import com.synjones.framework.web.action.BaseAction;

public class QuestExamAction extends BaseAction implements ModelDriven{
	
	private static final long serialVersionUID = 1L;
	private Logincheck logincheck;
	private int rp = 15; // 每页显示的条数
	private int page = 1; // 当前页
	private int rows = 15; // easyui每页显示条数
	
	private QuestExamService questExamService;
	private QuestModel model = new QuestModel();
	
	private SyslogService syslogService;    

   	public void setSyslogService(SyslogService syslogService) {
   		this.syslogService = syslogService;
   	}
   	
   	public SyslogService getSyslogService() {
		return syslogService;
	}
	
	public Logincheck getLogincheck() {
		return logincheck;
	}

	public void setLogincheck(Logincheck logincheck) {
		this.logincheck = logincheck;
	}

	public int getRp() {
		return rp;
	}

	public void setRp(int rp) {
		this.rp = rp;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public QuestExamService getQuestExamService() {
		return questExamService;
	}

	public void setQuestExamService(QuestExamService QuestExamService) {
		this.questExamService = QuestExamService;
	}

	public QuestModel getModel() {
		return model;
	}

	public void setModel(QuestModel model) {
		this.model = model;
	}
	

	public String brainShowPage() throws WebException, SQLException {
		UserDTO user = (UserDTO) session.get("username");
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("1513");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return SUCCESS;
	}
	
	
	
	/**
	 * 
	     * @Title: getMeunListFirst 加载菜单信息  
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String getMeunListFirst()throws WebException, SQLException {
		QuestExamDTO questExamDTO = new QuestExamDTO();
		//brain.setTitleName(model.getTitleName());  //搜索查询
		//可以放置查询参数
		questExamDTO.setLevel(model.getLevel());
		questExamDTO.setQuest_sub_code(model.getQuest_sub_code());
		List<QuestExamDTO> list = this.questExamService.getMeunListFirst(questExamDTO);
		this.outJsonResult(list);
		return NONE;
	}
	
	
	/**
	 * 
	     * @Title: getQuestionList   加载问题信息
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String getQuestionList()throws WebException, SQLException {
		QuestExamDTO questExamDTO = new QuestExamDTO();
		//brain.setTitleName(model.getTitleName());  //搜索查询
		//可以放置查询参数
		questExamDTO.setTitleID(model.getTitleID());
		questExamDTO.setItemlevel(model.getItemlevel());
		questExamDTO.setSex(model.getSex());
		List<QuestExamDTO> list = this.questExamService.getQuestionList(questExamDTO);
		this.outJsonResult(list);
		return NONE;
	}
	
	/**
	 * 
	     * @Title: getQuestAllList   加载此部分所有信息
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String getQuestAllList()throws WebException, SQLException {
		QuestExamDTO questExamDTO = new QuestExamDTO();
		//brain.setTitleName(model.getTitleName());  //搜索查询
		//可以放置查询参数
		questExamDTO.setTitleID(model.getTitleID());
		questExamDTO.setItemlevel(model.getItemlevel());
		questExamDTO.setSex(model.getSex());
		questExamDTO.setQuest_sub_code(model.getQuest_sub_code());
		List<QuestExamDTO> list = this.questExamService.getQuestAllList(questExamDTO);
		this.outJsonResult(list);
		return NONE;
	}
	
	
	/**
	 * 
	     * @Title: clickSaveData   点击保存信息
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: void      
	     * @throws
	 */
	public void clickSaveData()throws WebException, SQLException {
		
			QuestExamDTO br = new QuestExamDTO();
		
		if ((this.model.getItemId() != 0) && (!"".equals(this.model.getItemId()))) {
			//根据ID查询此条信息
			br.setItemId(model.getItemId());//
			QuestExamDTO questExamDTO = questExamService.queryGetIdQuestDto(br);
			//保存问题答案
			QuestExamList recList = new QuestExamList();
			
			recList.setRecordId(model.getRecId()+"");//
			recList.setPeId(model.getPeId());//
			recList.setResultId(questExamDTO.getLinkNo());//
			if("0".equals(questExamDTO.getIsMulSel())) {
				recList.setQuestResult(questExamDTO.getItemName());//
			}else {
				recList.setQuestResult(model.getTextUnit());//
			}
			recList.setResultId_itemID(questExamDTO.getSupItemId());//
			recList.setResultId_itemID1(questExamDTO.getSupItemId());//
			recList.setQuestResult_itemID(questExamDTO.getItemId()+"");//
			recList.setItemTitleID(questExamDTO.getTitleID()+"");//
			recList.setItemIsMulsel(Integer.parseInt(questExamDTO.getIsMulSel()));//
			recList.setItemtextUnit(questExamDTO.getTextUnit());
			recList.setResultId_itemName("");
			recList.setResultId_itemName1("");
			recList.setQuestResult_itemName("");
			
			try {
				QuestExamList recL = new QuestExamList();
				recL.setPeId(model.getPeId());
				recL.setResultId_itemID(questExamDTO.getSupItemId());
				//根据人员编号  问题id 查询是否已答
				int res = questExamService.queryIDAndUser(recL);
				if(res!=100 && res!=1){
					//修改答案
					recList.setQuestResult_itemID(questExamDTO.getItemId()+"");
					this.questExamService.editAnswerList(recList);
					this.message= questExamDTO.getLinkItem()+"*"+questExamDTO.getLinksubItem();
				}else{//新增 
					this.questExamService.saveAnswerList(recList);
					this.message= questExamDTO.getLinkItem()+"*"+questExamDTO.getLinksubItem();
					if("false".equals(model.getIsMuslCheck()) && res==1) {//类型为多选
						//删除该多选的答案
						this.questExamService.delisMuslKey(model.getPeId(), questExamDTO.getItemId()+"");
					}					
				}
			} catch (Exception e) {
				this.message="失败";
				e.printStackTrace();
			}
		}
		
		try {
			response.setContentType("text/html;charset=utf-8");
			response.getWriter().print(new Gson().toJson(this.message));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	
	
	/**
	 * 
	     * @Title: getQuestionHidden   显示隐藏信息
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String getQuestionHidden()throws WebException, SQLException {
		QuestExamDTO questExamDTO = new QuestExamDTO();
		//brain.setTitleName(model.getTitleName());  //搜索查询
		//可以放置查询参数
		questExamDTO.setSupItemId(model.getSupItemId());
		List<QuestExamDTO> list = this.questExamService.getQuestionHidden(questExamDTO);
		this.outJsonResult(list);
		return NONE;
	}
	
	
	/**
	 * 
	     * @Title: getQuestionHiddenAnswer    显示隐藏答案
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String getHiddenAnswer()throws WebException, SQLException {
		QuestExamDTO questExamDTO = new QuestExamDTO();
		//brain.setTitleName(model.getTitleName());  //搜索查询
		//可以放置查询参数
		questExamDTO.setSupItemId(model.getSupItemId());
		List<QuestExamDTO> list = this.questExamService.getHiddenAnswer(questExamDTO);
		this.outJsonResult(list);
		return NONE;
	}
	
	
	
	/**
	 * 
	     * @Title: saveRecordMsg   保存record表记录
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: void      
	     * @throws
	 */
	public void saveRecordMsg()throws WebException, SQLException {
		
		QuestExamRecord record = new QuestExamRecord();
		record.setPeId(model.getPeId());
		record.setInDate(DateTimeUtil.parse());
		UserDTO user = (UserDTO) session.get("username");
		record.setTraner(user.getUserid());
		record.setTranFlag("0");
		record.setDownFlag(0);
		record.setDelFlag("0");
		record.setQuest_sub_code(model.getQuest_sub_code());
		
		try {
			this.questExamService.saveRecordMsg(record);
			
			this.message= record.getRecId()+"";
		} catch (Exception e) {
			this.message="开始答题失败 请重新选择";
			e.printStackTrace();
		}
	
		try {
			response.setContentType("text/html;charset=utf-8");
			response.getWriter().print(new Gson().toJson(this.message));
		} catch (IOException e) {
			e.printStackTrace();
		}
	
	}
	
	
	/**
	 * 
	     * @Title: queryMegFromRecord   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: void      
	     * @throws
	 */
	public void queryMegFromRecord()throws WebException, SQLException {
		
		QuestRecordDTO  br = new QuestRecordDTO();
		br.setQuest_sub_code(model.getQuest_sub_code());
		br.setPeId(model.getPeId());
		QuestRecordDTO record = questExamService.queryMegFromRecord(br);
		
		if(record!=null && !"".equals(record)){
			//存在记录 查询记录
			this.message= record.getRecId()+"";
		}else{
			//开始答题
			this.message="";
		}
		
		try {
				response.setContentType("text/html;charset=utf-8");
				response.getWriter().print(new Gson().toJson(this.message));
			} catch (IOException e) {
				e.printStackTrace();
			}
		
		
	}
	
	
	
	/**
	 * 
	     * @Title: QueryResultMsg    查询record_list信息
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String QueryResultMsg()throws WebException, SQLException {
		QuestRecordDTO record = new QuestRecordDTO();
		//brain.setTitleName(model.getTitleName());  //搜索查询
		//可以放置查询参数
		record.setPeId(model.getPeId());
		record.setRecordId(model.getRecordId());
		List<QuestRecordDTO> list = this.questExamService.QueryResultMsg(record);
		this.outJsonResult(list);
		return NONE;
	}
	
	/**
	 * 
	     * @Title: getQuestAllList_status   获取脑健康部分问题所有信息及问题回答状态1523
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String getQuestAllList_status()throws WebException, SQLException {
		List<QuestExamDTO> list = this.questExamService.getQuestAllList_status(model.getTitleID(), model.getItemlevel(), model.getPeId(), model.getQuest_sub_code());
		this.outJsonResult(list);
		return NONE;
	}
	
	
	
	
	/**
	 * 
	     * @Title: saveEndRecord   修改答题记录
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: void      
	     * @throws
	 */
	public void saveEndRecord()throws WebException, SQLException {
		UserDTO user = (UserDTO) session.get("username");
		QuestExamRecord record = new QuestExamRecord();
		record.setRecId(Integer.parseInt(model.getRecordId())); 
		//record.setPeId(model.getPeId());
		record.setTranDate(DateTimeUtil.parse());
		//UserDTO user = (UserDTO) session.get("username");
		//record.setTraner(user.getUserid());
		
		//record.setQuest_sub_code(model.getQuest_sub_code());
		String depId = model.getDep_id();
		if(!"".equals(depId) && depId!=null) {
			if(depId.equals("173")) { //外科
				record.setTranFlag("1");
			}else if(depId.equals("168")) { //内科
				record.setDelFlag("1");;
			}
		}else {
			record.setTranFlag("1");
			record.setDelFlag("1");
		}
		
		try {
			this.questExamService.saveEndRecord(record, user,this.model.getExam_num(),this.model.getQuest_sub_code());
			this.message= "您已答题结束";
		} catch (Exception e) {
			this.message="确认失败，请重新确认";
			e.printStackTrace();
		}
	
		try {
			response.setContentType("text/html;charset=utf-8");
			response.getWriter().print(new Gson().toJson(this.message));
		} catch (IOException e) {
			e.printStackTrace();
		}
	
	}
	
	
	/**
	 * 
	     * @Title: queryIsPayCharing   查询是否付费
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public void queryIsPayCharing()throws WebException, SQLException {
		UserDTO user = (UserDTO) session.get("username");
		
		QuestChargingDTO charing = new QuestChargingDTO();
		
		charing.setExam_num(model.getExam_num());
		
		charing.setQuest_code(model.getQuest_sub_code());
		
		String result = "false";
		
		List<QuestChargingDTO> list2 = this.questExamService.queryIsPaychargingItemCode(charing);
		for (QuestChargingDTO questCha : list2) {
			if(questCha.getCharging_item_code()!=null  && !"".equals(questCha.getCharging_item_code())) {
				try {
					List<QuestChargingDTO> list = this.questExamService.queryIsPayCharing(charing, user.getCenter_num());
					if(list.size()>0) {
						for (QuestChargingDTO questChargingDto : list) {
							if("G".equals(questChargingDto.getExam_type())) {
								//个人 
								if("Y".equals(questChargingDto.getIs_after_pay())) {
									//后付费 返回可以体检
									result = "true";
								}else if("N".equals(questChargingDto.getIs_after_pay())) {
									//查询是否付费
									if("Y".equals(questChargingDto.getPay_status())) {
										//已经付费
										result = "true";
									}else if("N".equals(questChargingDto.getPay_status())) {
										result = "false";
									}
								}
							}else if("T".equals(questChargingDto.getExam_type())) {
								//团体
								//是否为团体付费
								if("T".equals(questChargingDto.getExam_indicator())) {
									if("R".equals(questChargingDto.getPay_status())) {
										//团体已经付费
										result = "true";
									}
								}else if("G".equals(questChargingDto.getExam_indicator())) {
									//是否后付费
									if("Y".equals(questChargingDto.getIs_after_pay())) {
										//后付费 返回可以体检
										result = "true";
									}else if("N".equals(questChargingDto.getIs_after_pay())) {
										//查询是否付费
										if("Y".equals(questChargingDto.getPay_status())) {
											//已经付费
											result = "true";
										}else if("N".equals(questChargingDto.getPay_status())) {
											result = "false";
										}
									}
								}
							}
						}
					}
					
				} catch (Exception e) {
					this.message="查询失败";
					e.printStackTrace();
				}
				
			}else {
				result = "true";
			}
			this.message= result;
		}
		
		
		try {
			response.setContentType("text/html;charset=utf-8");
			response.getWriter().print(new Gson().toJson(this.message));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	
	/**
	 * 
	     * @Title: insertIsdefaultAnswer  插入一套默认答案 
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: void      
	     * @throws
	 */
	public void insertIsdefaultAnswer()throws WebException, SQLException {
		
		
		QuestRecordDTO  br = new QuestRecordDTO();
		br.setQuest_sub_code(model.getQuest_sub_code());
		br.setPeId(model.getPeId());
		QuestRecordDTO record2 = questExamService.queryMegFromRecord(br);
		
		if(record2!=null && !"".equals(record2)){
			//存在记录 查询记录
			this.message= record2.getRecId()+"";
			
		}else{
			//开始答题
			QuestExamRecord record = new QuestExamRecord();
			record.setPeId(model.getPeId());
			record.setInDate(DateTimeUtil.parse());
			UserDTO user = (UserDTO) session.get("username");
			record.setTraner(user.getUserid());
			record.setTranFlag("0");
			record.setDownFlag(0);
			record.setDelFlag("0");
			record.setQuest_sub_code(model.getQuest_sub_code());
			
			this.questExamService.saveRecordMsg(record);
			
				
			QuestExamDTO bra = new QuestExamDTO();
		
			//根据ID查询此条信息
			bra.setQuest_sub_code(model.getQuest_sub_code());
			List<QuestExamDTO> brain = questExamService.questIsdefaultValue(bra);
			for (QuestExamDTO dto : brain) {
				QuestExamList recList = new QuestExamList();
				
				recList.setRecordId(record.getRecId()+"");//
				recList.setPeId(model.getPeId());//
				recList.setResultId(dto.getLinkNo());//
				if("0".equals(dto.getIsMulSel())) {
					recList.setQuestResult(dto.getItemName());//
				}else {
					recList.setQuestResult(dto.getDefaultResult());//
				}
				recList.setResultId_itemID(dto.getSupItemId());//
				recList.setResultId_itemID1(dto.getSupItemId());//
				recList.setQuestResult_itemID(dto.getItemId()+"");//
				recList.setItemTitleID(dto.getTitleID()+"");//
				recList.setItemIsMulsel(Integer.parseInt(dto.getIsMulSel()));//
				recList.setItemtextUnit(dto.getTextUnit());
				recList.setResultId_itemName("");
				recList.setResultId_itemName1("");
				recList.setQuestResult_itemName("");
				
				try {
					this.questExamService.saveAnswerList(recList);
					this.message= record.getRecId()+"";
				} catch (Exception e) {
					this.message="默认值插入初始化失败";
					e.printStackTrace();
					return;
				}
			}
			
			//根据体检号查询 职业史
			List<QuestExamDTO> resu  = this.questExamService.findByExamNum(br);
			if(resu!=null) {
				if("QS06".equals(model.getQuest_sub_code())) {
					//
					if(resu.size()==1) {
						QuestExamList reStart = new QuestExamList();
						
						reStart.setPeId(model.getPeId());//
						reStart.setQuestResult(resu.get(0).getStartdate());// 开始时间
						reStart.setResultId_itemID("1");// 问题ID
						this.questExamService.editAnswerList(reStart);
						
						reStart.setQuestResult(resu.get(0).getEnddate());// 结束时间
						reStart.setResultId_itemID("380");// 问题ID
						this.questExamService.editAnswerList(reStart);
						
						reStart.setQuestResult(resu.get(0).getCompany());//
						reStart.setResultId_itemID("3");// 问题ID
						this.questExamService.editAnswerList(reStart);
						
						reStart.setQuestResult(resu.get(0).getWorkshop());//部门
						reStart.setResultId_itemID("5");// 问题ID
						this.questExamService.editAnswerList(reStart);
						
						reStart.setQuestResult(resu.get(0).getWorktype());// 工种
						reStart.setResultId_itemID("7");// 问题ID
						this.questExamService.editAnswerList(reStart);
						
						reStart.setQuestResult(resu.get(0).getHarmname());// 危害因素
						reStart.setResultId_itemID("9");// 问题ID
						this.questExamService.editAnswerList(reStart);
						
					}else if(resu.size()==2){
						QuestExamList reStart = new QuestExamList();
						
						reStart.setPeId(model.getPeId());//
						reStart.setQuestResult(resu.get(0).getStartdate());// 开始时间
						reStart.setResultId_itemID("1");// 问题ID
						this.questExamService.editAnswerList(reStart);
						
						reStart.setQuestResult(resu.get(0).getEnddate());// 结束时间
						reStart.setResultId_itemID("380");// 问题ID
						this.questExamService.editAnswerList(reStart);
						
						reStart.setQuestResult(resu.get(0).getCompany());//
						reStart.setResultId_itemID("3");// 问题ID
						this.questExamService.editAnswerList(reStart);
						
						reStart.setQuestResult(resu.get(0).getWorkshop());//部门
						reStart.setResultId_itemID("5");// 问题ID
						this.questExamService.editAnswerList(reStart);
						
						reStart.setQuestResult(resu.get(0).getWorktype());// 工种
						reStart.setResultId_itemID("7");// 问题ID
						this.questExamService.editAnswerList(reStart);
						
						reStart.setQuestResult(resu.get(0).getHarmname());// 危害因素
						reStart.setResultId_itemID("9");// 问题ID
						this.questExamService.editAnswerList(reStart);
						
						reStart.setPeId(model.getPeId());//
						reStart.setQuestResult(resu.get(1).getStartdate());// 开始时间
						reStart.setResultId_itemID("398");// 问题ID
						this.questExamService.editAnswerList(reStart);
						
						reStart.setQuestResult(resu.get(1).getEnddate());// 结束时间
						reStart.setResultId_itemID("399");// 问题ID
						this.questExamService.editAnswerList(reStart);
						
						reStart.setQuestResult(resu.get(1).getCompany());//
						reStart.setResultId_itemID("400");// 问题ID
						this.questExamService.editAnswerList(reStart);
						
						reStart.setQuestResult(resu.get(1).getWorkshop());//部门
						reStart.setResultId_itemID("401");// 问题ID
						this.questExamService.editAnswerList(reStart);
						
						reStart.setQuestResult(resu.get(1).getWorktype());// 工种
						reStart.setResultId_itemID("402");// 问题ID
						this.questExamService.editAnswerList(reStart);
						
						reStart.setQuestResult(resu.get(1).getHarmname());// 危害因素
						reStart.setResultId_itemID("403");// 问题ID
						this.questExamService.editAnswerList(reStart);
					}
				}else if("QS07".equals(model.getQuest_sub_code())){
					if(resu.size()==1) {
						QuestExamList reStart = new QuestExamList();
						
						reStart.setPeId(model.getPeId());//
						reStart.setQuestResult(resu.get(0).getStartdate());// 开始时间
						reStart.setResultId_itemID("21");// 问题ID
						this.questExamService.editAnswerList(reStart);
						
						reStart.setQuestResult(resu.get(0).getEnddate());// 结束时间
						reStart.setResultId_itemID("378");// 问题ID
						this.questExamService.editAnswerList(reStart);
						
						reStart.setQuestResult(resu.get(0).getCompany());//
						reStart.setResultId_itemID("27");// 问题ID
						this.questExamService.editAnswerList(reStart);
						
						reStart.setQuestResult(resu.get(0).getWorkshop());//部门
						reStart.setResultId_itemID("28");// 问题ID
						this.questExamService.editAnswerList(reStart);
						
						reStart.setQuestResult(resu.get(0).getWorktype());// 工种
						reStart.setResultId_itemID("29");// 问题ID
						this.questExamService.editAnswerList(reStart);
						
						QuestExamDTO tto = this.questExamService.findByExamNumScName(br);
						if(tto!=null) {
							reStart.setQuestResult(tto.getSc_classname());// 放射线种类
							reStart.setResultId_itemID("30");// 问题ID
							this.questExamService.editAnswerList(reStart);
						}
						
					}else if(resu.size()==2) {
						
						QuestExamList reStart = new QuestExamList();
						
						reStart.setPeId(model.getPeId());//
						reStart.setQuestResult(resu.get(0).getStartdate());// 开始时间
						reStart.setResultId_itemID("21");// 问题ID
						this.questExamService.editAnswerList(reStart);
						
						reStart.setQuestResult(resu.get(0).getEnddate());// 结束时间
						reStart.setResultId_itemID("378");// 问题ID
						this.questExamService.editAnswerList(reStart);
						
						reStart.setQuestResult(resu.get(0).getCompany());//
						reStart.setResultId_itemID("27");// 问题ID
						this.questExamService.editAnswerList(reStart);
						
						reStart.setQuestResult(resu.get(0).getWorkshop());//部门
						reStart.setResultId_itemID("28");// 问题ID
						this.questExamService.editAnswerList(reStart);
						
						reStart.setQuestResult(resu.get(0).getWorktype());// 工种
						reStart.setResultId_itemID("29");// 问题ID
						this.questExamService.editAnswerList(reStart);
						
						
						/*reStart.setQuestResult(resu.get(0).getSc_classname());// 放射线种类
						reStart.setResultId_itemID("30");// 问题ID
						this.QuestExamService.editAnswerList(reStart);*/
						
						reStart.setPeId(model.getPeId());//
						reStart.setQuestResult(resu.get(1).getStartdate());// 开始时间
						reStart.setResultId_itemID("382");// 问题ID
						this.questExamService.editAnswerList(reStart);
						
						reStart.setQuestResult(resu.get(1).getEnddate());// 结束时间
						reStart.setResultId_itemID("383");// 问题ID
						this.questExamService.editAnswerList(reStart);
						
						reStart.setQuestResult(resu.get(1).getCompany());//
						reStart.setResultId_itemID("384");// 问题ID
						this.questExamService.editAnswerList(reStart);
						
						reStart.setQuestResult(resu.get(1).getWorkshop());//部门
						reStart.setResultId_itemID("385");// 问题ID
						this.questExamService.editAnswerList(reStart);
						
						reStart.setQuestResult(resu.get(1).getWorktype());// 工种
						reStart.setResultId_itemID("386");// 问题ID
						this.questExamService.editAnswerList(reStart);
						
						QuestExamDTO tto = this.questExamService.findByExamNumScName(br);
						if(tto!=null) {
							reStart.setQuestResult(tto.getSc_classname());// 放射线种类
							reStart.setResultId_itemID("30");// 问题ID
							this.questExamService.editAnswerList(reStart);
							
							reStart.setQuestResult(tto.getSc_classname());// 放射线种类
							reStart.setResultId_itemID("387");// 问题ID
							this.questExamService.editAnswerList(reStart);
						}
						
					}
				}
				
			}
			
		}
		
		try {
			response.setContentType("text/html;charset=utf-8");
			response.getWriter().print(new Gson().toJson(this.message));
		} catch (IOException e) {
			e.printStackTrace();
		}
	
	}
	
	/**
	 * 
	     * @Title: queryTitleCont   查询该问卷title信息
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: void      
	     * @throws
	 */
	public void queryTitleCont() throws WebException, SQLException{
		QuestExamDTO bra = new QuestExamDTO();
		//根据ID查询此条信息
		bra.setQuest_sub_code(model.getQuest_sub_code());
		try {
			QuestExamDTO dto = questExamService.queryTitleCont(bra);
			this.message= dto.getClass_name();
		} catch (Exception e) {
			this.message="加载标题信息失败";
			e.printStackTrace();
			return;
		}
		
		
		try {
			response.setContentType("text/html;charset=utf-8");
			response.getWriter().print(new Gson().toJson(this.message));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	
	/**
	 * 
	     * @Title: queryQuestNameFixed   查询定位
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: void      
	     * @throws
	 */
	public void queryQuestNameFixed()throws WebException, SQLException{
		QuestExamDTO bra = new QuestExamDTO();
		//模糊查询此条信息
		bra.setItemName(model.getItemName());
		bra.setQuest_sub_code(model.getQuest_sub_code());
		try {
			QuestExamDTO dto = questExamService.queryQuestNameFixed(bra);
			if(dto!=null) {
				this.message= dto.getItemId()+"";
			}else {
				this.message= "false";
			}
		} catch (Exception e) {
			this.message="加载标题信息失败";
			e.printStackTrace();
			return;
		}
		
		
		try {
			response.setContentType("text/html;charset=utf-8");
			response.getWriter().print(new Gson().toJson(this.message));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
	/**
	 * @throws UnsupportedEncodingException 
	 * 
	     * @Title: queryLikeTitleName   模糊查询检索 
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String queryLikeTitleName() throws  WebException,SQLException, UnsupportedEncodingException{
		QuestExamDTO bra = new QuestExamDTO();
		//模糊查询此条信息
		bra.setItemName(URLDecoder.decode(model.getItemName(), "UTF-8"));
		bra.setSex(URLDecoder.decode(model.getSex(), "UTF-8"));
		bra.setItemlevel(model.getItemlevel());
		bra.setQuest_sub_code(model.getQuest_sub_code());
		this.outJsonResult(this.questExamService.queryLikeTitleName(bra));
		return NONE;
	}
	
	
	
	/**
	 * 
	     * @Title: addZhengZhuangById   增加症状
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String addZhengZhuangById()throws WebException, SQLException {
		QuestExamDTO brain = new QuestExamDTO();
		//可以放置查询参数
		brain.setItemId(model.getItemId());
		
		List<QuestExamDTO> list = this.questExamService.addZhengZhuangById(brain);
		this.outJsonResult(list);
		return NONE;
	}
	
	
	/**
	 * 
	     * @Title: addZhengZhaungUpdate   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public void addZhengZhaungUpdate()throws WebException, SQLException {
		
		QuestExamList recList = new QuestExamList();
		recList.setQuestResult_itemID("");
		recList.setQuestResult(model.getTextUnit());
		recList.setPeId(model.getPeId());
		recList.setResultId_itemID(model.getSupItemId());//
		try {
			this.questExamService.editAnswerList(recList);
			this.message= "true";
		} catch (Exception e) {
			this.message="新增/删除失败";
			e.printStackTrace();
			return;
		}
		
		try {
			response.setContentType("text/html;charset=utf-8");
			response.getWriter().print(new Gson().toJson(this.message));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	 
	
	/**
	 * 
	     * @Title: queryIsNeiWaiAns   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: void      
	     * @throws
	 */
	public void queryIsNeiWaiAns()throws WebException, SQLException {
		
		QuestRecordDTO  br = new QuestRecordDTO();
		br.setQuest_sub_code(model.getQuest_sub_code());
		br.setPeId(model.getPeId());
		QuestRecordDTO record = questExamService.queryMegFromRecord(br);
		
		if(record!=null && !"".equals(record)){
			//存在记录 查询记录         			外科              内科
			this.message= record.getTranFlag()+","+record.getDelFlag();
		}else{
			//开始答题
			this.message="false";
		}
		
		try {
				response.setContentType("text/html;charset=utf-8");
				response.getWriter().print(new Gson().toJson(this.message));
			} catch (IOException e) {
				e.printStackTrace();
			}
		
		
	}
	
	
	
	public String resultWenZhengMsg()throws WebException, SQLException {
		QuestExamList recList = new QuestExamList();
		//可以放置查询参数
		recList.setPeId(model.getPeId());
		
		List<QuestExamDTO> list = this.questExamService.resultWenZhengMsg(recList);
		this.outJsonResult(list);
		return NONE;
	}
	
	

}
