package com.hjw.wst.service.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.hsqldb.lib.StringUtil;

import com.hjw.wst.DTO.QuestChargingDTO;
import com.hjw.wst.DTO.QuestExamDTO;
import com.hjw.wst.DTO.QuestRecordDTO;
import com.hjw.wst.DTO.UserDTO;
import com.hjw.util.DateTimeUtil;
import com.hjw.wst.DTO.MenuDTO;
import com.hjw.wst.domain.QuestExamRecord;
import com.hjw.wst.domain.QuestExamList;
import com.hjw.wst.service.QuestExamService;
import com.synjones.framework.exception.ServiceException;
import com.synjones.framework.persistence.JdbcPersistenceManager;
import com.synjones.framework.persistence.JdbcQueryManager;
import com.synjones.framework.persistence.PersistenceManager;
import com.synjones.framework.persistence.QueryManager;

public class QuestExamServiceImpl implements QuestExamService{
	
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
	public List<QuestExamDTO> getMeunListFirst(QuestExamDTO questExamDTO) throws ServiceException {
		String sql="select * from quest_dict_title q where "
				+ " q.Level = '"+questExamDTO.getLevel()+"' "
				+ " and q.quest_sub_code = '" +questExamDTO.getQuest_sub_code()+ "' "
				+ " and q.isVisable = '1' ORDER BY q.seqNo";
		
		List<QuestExamDTO> list = this.jqm.getList(sql,QuestExamDTO.class);
		return  list;
	}

	@Override
	public List<QuestExamDTO> getQuestionList(QuestExamDTO questExamDTO)
			throws ServiceException {
		
		String sql="select q.*,t.titleColumn,t.titleName from quest_dict_Item q "
				+ "LEFT JOIN quest_dict_title t on q.titleId = t.titleId where "  
				+ " q.titleId = '"+questExamDTO.getTitleID()+"' "
				+ " and q.Itemlevel  = '"+questExamDTO.getItemlevel()+"'" ;
				if(questExamDTO.getItemlevel() == 0) {
					//一级菜单拼接
					sql += " and q.Sex in ('"+questExamDTO.getSex()+"','全部') ";
				}
				sql += " ORDER BY q.seqNo ";
		
		List<QuestExamDTO> list = this.jqm.getList(sql,QuestExamDTO.class);
		return  list;
	}

	@Override
	public List<QuestExamDTO> getQuestAllList(QuestExamDTO questExamDTO)
			throws ServiceException {
		String sql="select qdi.* , qdt.titleColumn,qdt.titleName  from quest_dict_Item qdi "
					+" left join quest_dict_title qdt on qdi.titleId = qdt.titleID and qdt.quest_sub_code = '"+questExamDTO.getQuest_sub_code()+"' "
					+" where qdt.isVisable = 1 and qdi.Itemlevel = "+questExamDTO.getItemlevel()+" "
					+ "and qdt.supID is not null " ;
		if(questExamDTO.getItemlevel() == 0) {
			//一级菜单拼接
			sql += "and qdi.Sex in ('"+questExamDTO.getSex()+"','全部') ";
		}
		if(questExamDTO.getTitleID() > 0) {
			sql += "and qdt.supID = "+questExamDTO.getTitleID();
		}
		sql += " order by qdt.seqNo,qdi.seqNo";
		
		List<QuestExamDTO> list = this.jqm.getList(sql,QuestExamDTO.class);
		return  list;
	}
	
	@Override
	public List<QuestExamDTO> getQuestAllList_status(int titleID, int itemlevel, String exam_num, String quest_sub_code)
			throws ServiceException {
		String sql="select qdi.*,qdt.supID, CASE WHEN qel.peId is null THEN 0 ELSE 1 END AS selected, qel.itemtextUnit "
				+" from quest_dict_Item qdi "
				+" left join quest_dict_title qdt on qdi.titleId = qdt.titleID and qdt.quest_sub_code = '"+quest_sub_code+"' "
				+" left join quest_exam_list qel on qel.questResult_itemID = qdi.ItemId and qel.resultId_itemID = qdi.supItemId and qel.peId = '"+exam_num+"' "
				+" where qdi.Itemlevel = "+itemlevel+" and qdt.supID is not null ";
		if(titleID > 0) {
			sql+=" and qdt.supID = "+titleID;
		}
		sql+=" order by qdt.supID,qdi.supItemId, qdi.seqNo";
		
		List<QuestExamDTO> list = this.jqm.getList(sql,QuestExamDTO.class);
		return  list;
	}

	@Override
	public QuestExamDTO queryGetIdQuestDto(QuestExamDTO questExamDTO)
			throws ServiceException {
		String sql= " select * from quest_dict_Item q  where q.ItemId = '"+questExamDTO.getItemId()+"'";
				
		List<QuestExamDTO> list = this.jqm.getList(sql, QuestExamDTO.class);
		if(list.size() !=0){
			return list.get(0);
		}
		return new QuestExamDTO();
		
	}

	@Override
	public QuestExamList saveAnswerList(QuestExamList questList)
			throws ServiceException {
		this.pm.save(questList);
		return questList;
	}
	
	
	@Override
	public QuestExamList editAnswerList(QuestExamList questList)
			throws ServiceException {
		String sql= "UPDATE quest_exam_List SET ";
				
				if(questList.getQuestResult_itemID()!=null &&!"".equals(questList.getQuestResult_itemID())) {
					sql += "questResult_itemID = '"+questList.getQuestResult_itemID()+"', ";
				};
				sql +="questResult = '"+questList.getQuestResult()+"' "
					+ "WHERE peId = '"+questList.getPeId()+"' "
					+ "AND resultId_itemID = '"+questList.getResultId_itemID()+"'";
				this.jpm.execSql(sql);
		return questList;
	}

	@Override
	public int queryIDAndUser(QuestExamList questList)
			throws ServiceException {
		String sql= "SELECT itemIsMulsel FROM quest_exam_List q "
				+ "WHERE q.peId = '"+questList.getPeId()+"' "
				+ "AND q.resultId_itemID = '"+questList.getResultId_itemID()+"'";
		
		List<QuestExamDTO> list = this.jqm.getList(sql, QuestExamDTO.class);
		if(list.size() > 0){
			return list.get(0).getItemIsMulsel();
		}else{
			return 100;
		}
	}

	@Override
	public List<QuestExamDTO> getQuestionHidden(QuestExamDTO brain)
			throws ServiceException {
		String sql="select * from quest_dict_Item q "
				+ "where q.ItemId in ("+brain.getSupItemId()+") ";
		
		List<QuestExamDTO> list = this.jqm.getList(sql,QuestExamDTO.class);
		return  list;
	}

	@Override
	public List<QuestExamDTO> getHiddenAnswer(QuestExamDTO brain)
			throws ServiceException {
		String sql="select * from quest_dict_Item q "
				+ "where q.supItemId in ("+brain.getSupItemId()+") "
				+ "ORDER BY q.seqNo";
		
		List<QuestExamDTO> list = this.jqm.getList(sql,QuestExamDTO.class);
		return  list;
	}

	@Override
	public QuestExamRecord saveRecordMsg(QuestExamRecord record)
			throws ServiceException {
		this.pm.save(record);
		return record;
	}
	
	
	@Override
	public QuestRecordDTO queryMegFromRecord(QuestRecordDTO record)
			throws ServiceException {
		String sql= "select *  from quest_exam_Record q where "
				+ "q.quest_sub_code = '"+record.getQuest_sub_code()+"' "
				+ "and q.peId = '"+record.getPeId()+"' ";
				
		List<QuestRecordDTO> list = this.jqm.getList(sql, QuestRecordDTO.class);
		if(list.size() !=0){
			return list.get(0);
		}
		return null;
		
	}

	@Override
	public List<QuestRecordDTO> QueryResultMsg(QuestRecordDTO rec)
			throws ServiceException {
		
		String sql="SELECT * from quest_exam_List q where "
				+ "q.peId = '"+rec.getPeId()+"' "
				+ "and q.recordId = '"+rec.getRecordId()+"' ";
		
		List<QuestRecordDTO> list = this.jqm.getList(sql,QuestRecordDTO.class);
		return  list;
	}

	@Override
	public QuestExamRecord saveEndRecord(QuestExamRecord record, UserDTO user,String exam_num,String querytitle) throws ServiceException {
		
		String sql= 
				"UPDATE quest_exam_Record SET tranDate = getdate()";
				if(record.getTranFlag()!=null && record.getTranFlag()!="0") {
					
					sql += " , tranFlag = '"+record.getTranFlag()+"' ";
				}
				if(record.getDelFlag()!=null && record.getDelFlag()!="0") {
					
					sql += " , delFlag = '"+record.getDelFlag()+"' ";
				}				
				sql += "WHERE recId = '"+record.getRecId()+"'";
				this.jpm.execSql(sql);
		if(!StringUtil.isEmpty(exam_num) && !StringUtil.isEmpty(querytitle)){
		sql = "    update examinfo_charging_item  set  exam_status='Y',exam_doctor_id='"+user.getUserid()+"',exam_doctor_name='"+user.getUsername()+"',exam_date='"+DateTimeUtil.getDate3()+"', exam_center_num = '"+user.getCenter_num()+"' where exam_num ='"+exam_num+"' "
				+ " and charging_item_code in ( "
				+ "select q.charging_item_code from quest_dict_rec q,quest_dict_list pl where q.quest_code=pl.quest_code and pl.quest_sub_code='"+querytitle+"')"
				+ " and isActive='Y' and pay_status<>'M' and center_num = '"+user.getCenter_num()+"'";
		this.jpm.execSql(sql);		
		}
		getJWS(exam_num);
		return record;
	}
	
	/**
	 * 
	     * @Title: getJWS   健康体检问诊既往史设置
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param exam_num
	     * @param: @return      
	     * @return: String      
	     * @throws
	 */
	private void getJWS(String exam_num){
		String sql="SELECT ItemName as text from quest_exam_list,quest_dict_Item where itemid=questResult_itemID "
				+ " and supItemId=663 and  itemTitleID=89 and peId='"+exam_num+"'";
		List<MenuDTO> list = this.jqm.getList(sql,MenuDTO.class);
		
		sql="SELECT ItemName as text from quest_exam_list,quest_dict_Item where itemid=questResult_itemID "
				+ " and supItemId=795 and  itemTitleID=89 and peId='"+exam_num+"'";
		List<MenuDTO> list1 = this.jqm.getList(sql,MenuDTO.class);
		
		String str="";
		for(MenuDTO md :list){
			str=str+","+md.getText();
		}
		
		for(MenuDTO md :list1){
			str=str+","+md.getText();
		}
		if(str.trim().length()>0){
			str=str.substring(1,str.length());
		}
		this.jpm.execSql("update exam_info set past_medical_history='"+str+"' where exam_num='"+exam_num+"'");
	}

	@Override
	public List<QuestChargingDTO> queryIsPayCharing(QuestChargingDTO charing, String center_num) throws ServiceException {	
		String sql="SELECT a.exam_num, c.item_name, a.exam_type, "
				+ "a.is_after_pay, b.exam_indicator, b.pay_status "
				+ "FROM exam_info a, examinfo_charging_item b, charging_item c "
				+ "WHERE a.exam_num = '"+charing.getExam_num()+"' "
				+ "AND c.item_code = ( SELECT w.charging_item_code FROM "
					+ "quest_dict_rec w WHERE w.quest_code = ( SELECT q.quest_code FROM "
					+ "quest_dict_list q "
					+ "WHERE q.quest_sub_code = '"+charing.getQuest_code()+"' ) ) "
				+ "AND a.exam_num = b.exam_num "
				+ "AND c.id = b.charge_item_id and b.center_num = '"+center_num+"' ";		
		List<QuestChargingDTO> list = this.jqm.getList(sql,QuestChargingDTO.class);
		return  list;
	}

	@Override
	public List<QuestChargingDTO> queryIsPaychargingItemCode(QuestChargingDTO charing) throws ServiceException {
		
		String sql="SELECT b.charging_item_code FROM quest_dict_list a, quest_dict_rec b "
				+ "WHERE a.quest_sub_code = '"+charing.getQuest_code()+"' AND a.quest_code = b.quest_code";
		
		List<QuestChargingDTO> list = this.jqm.getList(sql,QuestChargingDTO.class);
		return  list;
	}

	@Override
	public List<QuestExamDTO> questIsdefaultValue(QuestExamDTO brain) throws ServiceException {
		String sql="SELECT * FROM quest_dict_Item q WHERE q.IsDefault = '1' "
				+ "AND q.Itemlevel = '1' "
				+ "AND q.titleId IN ( SELECT a.titleID FROM quest_dict_title a "
				+ "WHERE a.quest_sub_code = '"+brain.getQuest_sub_code()+"' )";
		List<QuestExamDTO> list = this.jqm.getList(sql,QuestExamDTO.class);
		return list;
	}

	@Override
	public QuestExamDTO queryTitleCont(QuestExamDTO brain) throws ServiceException {
		
		String sql= "SELECT a.class_name FROM quest_dict_rec a WHERE a.quest_code = "
				+ "( SELECT q.quest_code FROM quest_dict_list q "
				+ "WHERE q.quest_sub_code = '"+brain.getQuest_sub_code()+"' )";
				
		List<QuestExamDTO> list = this.jqm.getList(sql, QuestExamDTO.class);
		if(list.size() !=0){
			return list.get(0);
		}
		return new QuestExamDTO();
	}

	@Override
	public QuestExamDTO queryQuestNameFixed(QuestExamDTO brain) throws ServiceException {
		
		String sql= "SELECT qdi.ItemId FROM quest_dict_Item qdi LEFT JOIN "
				+ "quest_dict_title qdt ON qdi.titleId = qdt.titleID WHERE qdi.Itemlevel = '0'  "
				+ "AND qdt.quest_sub_code = '"+brain.getQuest_sub_code()+"' AND qdi.isVisable = '1' "
				+ "AND qdi.ItemName LIKE '%"+brain.getItemName()+"%' "
				+ "ORDER BY qdt.supID, qdi.supItemId, qdi.seqNo";
				
		List<QuestExamDTO> list = this.jqm.getList(sql, QuestExamDTO.class);
		if(list.size() !=0){
			return list.get(0);
		}
		return new QuestExamDTO();
	}

	@Override
	public List<QuestExamDTO> queryLikeTitleName(QuestExamDTO brain) throws ServiceException {
		
		String sql="SELECT qdi.ItemId, qdi.ItemName, qdt.titleID, qdt.titleName FROM quest_dict_Item qdi "
				+ "LEFT JOIN quest_dict_title qdt ON qdi.titleId = qdt.titleID "
				+ "WHERE  qdi.Itemlevel = "+brain.getItemlevel()+" "
				+ " AND qdt.isVisable = 0 AND qdt.supID IS NOT NULL ";
				
				if(brain.getItemlevel() == 0) {
					//一级菜单拼接
					sql += "and qdi.Sex in ('"+brain.getSex()+"','全部') ";
				}
				if(brain.getItemName()!=null &&!"".equals(brain.getItemName())) {
					sql += "AND qdi.ItemName LIKE '%"+brain.getItemName()+"%' or qdi.itemPinYin like '%"+brain.getItemName()+"%'  ";
				}
				sql += " AND qdt.quest_sub_code = '"+brain.getQuest_sub_code()+"' order by qdt.supID,qdt.seqNo,qdi.seqNo";
				
		List<QuestExamDTO> list = this.jqm.getList(sql,QuestExamDTO.class);
		return list;
	}

	@Override
	public List<QuestExamDTO> addZhengZhuangById(QuestExamDTO brain) throws ServiceException {

		String sql="SELECT s.*, w.titleColumn FROM ( SELECT q.* FROM quest_dict_Item q "
				+ "WHERE q.ItemId = '"+brain.getItemId()+"' ) s "
				+ "LEFT JOIN quest_dict_title w ON s.titleId = w.titleID";
				
		List<QuestExamDTO> list = this.jqm.getList(sql,QuestExamDTO.class);
		return list;
	}

	@Override
	public List<QuestExamDTO> resultWenZhengMsg(QuestExamList recList) throws ServiceException {
		
		String sql="SELECT w.*, til.titleColumn FROM ( SELECT q.resultId_itemID FROM quest_exam_List "
				+ "q WHERE q.itemTitleID in ( SELECT t.titleID FROM quest_dict_title t "
				+ "WHERE t. LEVEL = '1' AND t.isVisable = '0' ) AND peId = '"+recList.getPeId()+"' "
				+ "AND questResult = '+' ) a LEFT JOIN quest_dict_Item w "
				+ "ON a.resultId_itemID = w.ItemId LEFT JOIN quest_dict_title til "
				+ "ON w.titleId = til.titleID";
				
		List<QuestExamDTO> list = this.jqm.getList(sql,QuestExamDTO.class);
		return list;
	}

	@Override
	public List<QuestExamDTO> findByExamNum(QuestRecordDTO br) throws ServiceException {
		String sql="SELECT zo.exam_num, zo.startdate, zo.enddate, zo.company, zo.workshop, "
				+ "zo.worktype,zo.harmname  FROM zyb_occuhis zo "
				+ "WHERE zo.exam_num = '"+br.getPeId()+"'";
				
		List<QuestExamDTO> list = this.jqm.getList(sql,QuestExamDTO.class);
		return list;
	}

	@Override
	public QuestExamDTO findByExamNumScName(QuestRecordDTO br) throws ServiceException {
		
		String sql= "select zs.sc_classname from exam_ext_typeofocc et, "
				+ "zyb_source_career_class zs where et.exam_num = '"+br.getPeId()+"' "
				+ "and zs.sc_classid = et.sc_classcode";
				
		List<QuestExamDTO> list = this.jqm.getList(sql, QuestExamDTO.class);
		if(list.size() !=0){
			return list.get(0);
		}
		return null;
	}

	
	@Override
	public void delisMuslKey(String exam_num,String questResult_itemID)throws ServiceException {
		Connection connection = null;		
		try {
			// 读取记录数
			connection = this.jqm.getConnection();
			connection.createStatement().executeUpdate( "delete from quest_exam_List where peId='"+exam_num+"' AND  questResult_itemID='"+questResult_itemID+"'" );
			
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
	}


}
