package com.hjw.crm.service;

import java.sql.SQLException;
import java.util.List;

import com.hjw.crm.DTO.CrmPlanTacticsDTO;
import com.hjw.crm.domain.CrmPlanTactics;
import com.hjw.crm.domain.CrmPlanTacticsDetail;
import com.hjw.crm.domain.CrmVisitLost;
import com.hjw.crm.model.CrmVisitLostModel;
import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.wst.DTO.UserDTO;
import com.synjones.framework.exception.ServiceException;

public interface CrmVisitLostService {
	public PageReturnDTO getCrmVisitLostList(CrmVisitLostModel model,long userid,int page,int pageSize) throws ServiceException,SQLException;
	public String addCrmVisitLost(CrmVisitLost crmVisitLost) throws ServiceException,SQLException;
	//策略编码获取策略
	public List<CrmPlanTactics> getPlanTacticsByNum(String tactics_num)throws ServiceException,SQLException;
	public String savePlanTactics(CrmPlanTactics crmPlanTactics)throws ServiceException,SQLException;
	/**获取策略列表
	 * getPlantacTicsList
	 * @throws SQLException 
	 * @throws ServiceException 
	 */
	public PageReturnDTO getPlantacTicsList(CrmVisitLostModel model, int page, int rows) throws ServiceException, SQLException;
	public String updatePlanTactics(CrmPlanTactics crmPlanTactics)throws ServiceException, SQLException;
	/**crm314 保存策略明细信息 
	 * savePlanTacticsDetail
	 * @throws SQLException 
	 * @throws ServiceException 
	 */
	public String savePlanTacticsDetail(CrmPlanTacticsDetail crmPlanTacticsDetail)throws ServiceException, SQLException;
	/**getPlantacTicsDetailList
	 * crm315 策略明细信息列表
	 */
	public PageReturnDTO getPlantacTicsDetailList(CrmVisitLostModel model, int page, int rows) throws SQLException;
	
	public CrmPlanTacticsDetail getPlanTacticsDetailById(long tactics_detail_id)throws SQLException;
	
	public String updatePlanTacticsDetail(CrmPlanTacticsDetail crmPlan)throws SQLException;
	
	public String delPlanTacticsDetail(String tactics_detail_ids)throws SQLException;
	
	public String delPlanTactics(String tactics_nums)throws SQLException;
	
	public List<CrmPlanTacticsDTO> getCrmVisitPlanTacticsType()throws SQLException;
	
	public List<CrmPlanTacticsDTO> getTacticsNoticesList(int tactics_type)throws SQLException;
}
