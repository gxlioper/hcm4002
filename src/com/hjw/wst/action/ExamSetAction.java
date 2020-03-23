package com.hjw.wst.action;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.hjw.zyb.DTO.SetOccuhazardfactorsDTO;
import com.hjw.zyb.service.SetOccuhazardfactorsService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.google.gson.Gson;
import com.hjw.util.DateTimeUtil;
import com.hjw.util.Language;
import com.hjw.wst.DTO.ChargingItemDTO;
import com.hjw.wst.DTO.ExamSetDTO;
import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.wst.DTO.SetChargingItemDTO;
import com.hjw.wst.DTO.UserDTO;
import com.hjw.wst.domain.ExamSet;
import com.hjw.wst.domain.SetChargingItem;
import com.hjw.wst.domain.SysLog;
import com.hjw.wst.domain.WebResrelAtionship;
import com.hjw.wst.domain.WebSynchro;
import com.hjw.wst.model.ExamSetModel;
import com.hjw.wst.service.BatchService;
import com.hjw.wst.service.ExamSetService;
import com.hjw.wst.service.SyslogService;
import com.hjw.wst.service.WebSynchroService;
import com.hjw.zyb.DTO.ZybOccuhazardClassDTO;
import com.hjw.zyb.DTO.ZybOccuhazardfactorsDTO;
import com.opensymphony.xwork2.ModelDriven;
import com.synjones.framework.aop.SystemType;
import com.synjones.framework.exception.WebException;

import org.hsqldb.lib.StringUtil;

import com.synjones.framework.web.action.BaseAction;

@SuppressWarnings("serial")
public class ExamSetAction extends BaseAction implements ModelDriven {
	private ExamSetService examSetService;// 体验套餐
	private ExamSetModel model = new ExamSetModel();
	private int page = 1; // 当前页
	private int rows = 15; // easyui每页显示条数
	private String ids;// 批量删除
	private String li;
	private String intss = "";//职业病//普通体检
	private String name;
	@SuppressWarnings("unused")
	private BatchService batchService;
	private SyslogService syslogService;
	private WebSynchroService webSynchroService;
	private SetOccuhazardfactorsService setOccuhazardfactorsService;


	public SetOccuhazardfactorsService getSetOccuhazardfactorsService() {
		return setOccuhazardfactorsService;
	}

	public void setSetOccuhazardfactorsService(SetOccuhazardfactorsService setOccuhazardfactorsService) {
		this.setOccuhazardfactorsService = setOccuhazardfactorsService;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public WebSynchroService getWebSynchroService() {
		return webSynchroService;
	}

	public void setWebSynchroService(WebSynchroService webSynchroService) {
		this.webSynchroService = webSynchroService;
	}

	public String getIntss() {
		return intss;
	}

	public void setIntss(String intss) {
		this.intss = intss;
	}

	public void setSyslogService(SyslogService syslogService) {
		this.syslogService = syslogService;
	}

	public void setBatchService(BatchService batchService) {
		this.batchService = batchService;
	}

	public String getLi() {
		return li;
	}

	public void setLi(String li) {
		this.li = li;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	@Override
	public Object getModel() {
		return model;
	}

	public ExamSetService getExamSetService() {
		return examSetService;
	}

	public void setExamSetService(ExamSetService examSetService) {
		this.examSetService = examSetService;
	}

	public void setModel(ExamSetModel model) {
		this.model = model;
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

	/**
	 * @throws
	 * @Title: EaxmSetPage
	 * @Description: TODO(体检套餐管理页面190)
	 * @param: @return
	 * @return: String
	 */
	public String examSetPage() {
		this.defaultapp = (SystemType) session.get("defaultapp");
		this.setIntss(this.defaultapp.getComid());
		UserDTO user = (UserDTO) session.get("username");
		//根据用户资源判断是否展示折扣率跟折扣后金额
		List<WebResrelAtionship> wrs = user.getWebResource();
		if (wrs != null && wrs.size() > 0) {
			for (WebResrelAtionship wr : wrs) {
				if ("RS049".equals(wr.getRes_code())) {
					this.model.setIs_show_discount(1);
					break;
				}
			}
		}
		SysLog sl = new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid() + "");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("190");//子功能id 必须填写
		sl.setExplain("体检套餐管理");//操作说明
		syslogService.saveSysLog(sl);


		return SUCCESS;
	}

	/**
	 * @throws
	 * @Title: queryExamSet
	 * @Description: TODO(体检套餐列表191)
	 * @param: @return
	 * @return: String
	 */
	public String queryExamSet() throws WebException, SQLException {
		UserDTO user = (UserDTO) session.get("username");
		this.defaultapp = (SystemType) session.get("defaultapp");
		ExamSetDTO exam = new ExamSetDTO();
		exam.setSet_name(model.getSet_name());
		exam.setUpdate_time(model.getUpdate_time());
		exam.setUpdate_times(model.getUpdate_times());
		exam.setSex(model.getSex());
		exam.setStartStop(model.getStartStop());
		PageReturnDTO dto = this.examSetService.queryExamSet(exam, page, rows, this.defaultapp.getComid(), user.getCenter_num());
		this.outJsonResult(dto);
		return NONE;
	}

	/**
	 * @throws
	 * @Title: deletexamSet
	 * @Description: TODO(删除体检套餐192)
	 * @param: @return
	 * @return: String
	 */
	public String deletexamSet() throws WebException, SQLException {
		UserDTO user = (UserDTO) session.get("username");
		if (!this.model.getIds().equals("")) {
			StringBuffer bufferId = new StringBuffer();
			String[] idIsSyna = this.model.getIds().split(",");
			for (int i = 0; i < idIsSyna.length; i++) {
				boolean result = this.examSetService.queryFromIdIsSynchro(idIsSyna[i]);
				if (result) {
					bufferId.append(idIsSyna[i] + ",");
				}
			}
			String lastId = "";
			if (bufferId.toString().length() > 1) { //有id时候执行
				lastId = bufferId.toString().substring(0, bufferId.toString().length() - 1);
				this.webSynchroService.updateWebSynchro(lastId, '2');
			}
			this.examSetService.deleteExamSet(this.model.getIds(), user.getCenter_num());// 删除套餐记录
			this.examSetService.deletSetChargingItemPl(this.model.getIds());// 删除体检套餐关系表记录

			this.message = "删除成功";
		}
		this.outJsonStrResult(this.message);


//		UserDTO user = (UserDTO) session.get("username");
		SysLog sl = new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid() + "");
		sl.setOper_type("3");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("192");//子功能id 必须填写
		sl.setExplain("删除体检套餐");//操作说明
		syslogService.saveSysLog(sl);

		return NONE;
	}

	/**
	 * @throws
	 * @Title: addExamSet
	 * @Description: TODO(体检套餐添加界面193)
	 * @param: @return
	 * @return: String
	 */
	public String addExamSet() {
		UserDTO user = (UserDTO) session.get("username");

		model.setSet_num(batchService.GetCreateID("set_num", user.getCenter_num()));
//		model.setHazardfactorsid(model.getHazardfactorsid().trim());
//		model.setOccuphyexaclassid(model.getOccuphyexaclassid().trim());

		@SuppressWarnings("unused")
		List<WebResrelAtionship> webResource = user.getWebResource();

		if (webResource != null) {
			for (int i = 0; i < webResource.size(); i++) {
				if (webResource.get(i).getRes_code().equals("RS004")) {
					model.setWebResource(webResource.get(i).getDatavalue());
					break;
				}
			}
		}

		//根据用户资源判断是否展示折扣率跟折扣后金额
		List<WebResrelAtionship> wrs = user.getWebResource();
		if (wrs != null && wrs.size() > 0) {
			for (WebResrelAtionship wr : wrs) {
				if ("RS049".equals(wr.getRes_code())) {
					this.model.setIs_show_discount(1);
					break;
				}
			}
		}
		this.defaultapp = (SystemType) session.get("defaultapp");
		this.setIntss(this.defaultapp.getComid());
		return SUCCESS;
	}

	/**
	 * @throws
	 * @Title: addExamSetFunc
	 * @Description: TODO(体检套餐添加方法194)
	 * @param: @return
	 * @return: String
	 */
	public String addExamSetFunc() throws WebException, SQLException {
		UserDTO user = (UserDTO) session.get("username");
		ExamSet exam = new ExamSet();
		exam.setSet_name(model.getSet_name());// 套餐名称
		exam.setSet_pinyin(model.getSet_pinyin());// 套餐拼音
		exam.setSex(model.getSex());// 适应人群 男，女
		exam.setSet_num(model.getSet_num());//编码
		exam.setCreater(user.getUserid());// 用户
		exam.setCreate_time(DateTimeUtil.parse());// 时间
		exam.setIs_Active("Y");
		exam.setCenter_num(user.getCenter_num());
		this.defaultapp = (SystemType) session.get("defaultapp");

		exam.setApp_type(Integer.parseInt(defaultapp.getComid()));//体检类别（2职业病体检1普通体检）
		exam.setSet_discount(model.getSet_discount());// 总折扣
		exam.setPrice(model.getPrice());// 原价
		exam.setSet_amount(model.getSet_amount());// 总折后价格
		exam.setExam_set_type(model.getExam_set_type());
		exam.setSet_seq(model.getSet_seq());
		exam.setHazardfactorsid(model.getHazardfactorsid());
		exam.setOccuphyexaclassid(model.getOccuphyexaclassid());
		exam.setIsSynchro(0);
		ExamSet am = this.examSetService.addExamSet(exam);
		//this.webSynchroService.updateWebSynchro(am.getId()+"", '2');

		// 获取datagrid数据
		String li = this.li;
		li = li.replace("}{", "},{");
		li = "[" + li + "]";
		JSONArray liArry = JSONArray.fromObject(li);
		@SuppressWarnings("unchecked")
		List<ChargingItemDTO> lis = (List<ChargingItemDTO>) JSONArray
				.toCollection(liArry, ChargingItemDTO.class);
		// 添加关系表
		for (ChargingItemDTO dto : lis) {
			SetChargingItem item = new SetChargingItem();
			item.setCharging_item_id(dto.getId());// 收费表id
			item.setExam_set_id(am.getId());// 检查项目id
			item.setDiscount(dto.getSet_discountss());// 折扣
			item.setAmount(dto.getAmounts());// 折后金额
			item.setItem_amount(dto.getAmount());
			item.setItemnum(dto.getItemnum());
			item.setCreater(user.getUserid());// 创建用户
			item.setCreate_time(DateTimeUtil.parse());// 时间
			item.setApptype(this.defaultapp.getComid());
			if ("必选".equals(dto.getIschosen().trim())) {
				item.setIschosen("1");
			} else if("其他".equals(dto.getIschosen().trim())){
				item.setIschosen("2");
			} else {
				item.setIschosen("0");
			}
			this.examSetService.addSetChargingItem(item);
		}
		model.setId(exam.getId());
		model.setSet_id(exam.getId());
		if(!StringUtil.isEmpty(model.getHazard_list())){
			this.setOccuhazardfactorsService.saveExamSetHazard(model,user);
		}
		this.message = "添加成功";
		this.outJsonStrResult(this.message);

		SysLog sl = new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid() + "");
		sl.setOper_type("1");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("194");//子功能id 必须填写
		sl.setExplain("新增体检套餐");//操作说明
		syslogService.saveSysLog(sl);

		return NONE;
	}


	/**
	 * @throws
	 * @Title: updateExamSetPage
	 * @Description: TODO(修改体检套餐页面195)
	 * @param: @return
	 * @return: String
	 */
	public String updateExamSetPage() throws WebException, SQLException {
		ExamSet am = this.examSetService.findExamSetId(model.getId());
		model.setId(am.getId());// id
		model.setSet_name(am.getSet_name());// 名称
		model.setSet_pinyin(am.getSet_pinyin());// 拼音
		model.setSex(am.getSex());// 使用人群性别
		model.setSet_num(am.getSet_num());
		model.setSet_amount(am.getSet_amount());// 折后金额
		model.setSet_discount(am.getSet_discount());// 总折扣
		model.setPrice(am.getPrice());// 原价
		model.setExam_set_type(am.getExam_set_type());
		model.setSet_seq(am.getSet_seq());
		model.setHazardfactorsid(am.getHazardfactorsid());
		model.setOccuphyexaclassid(am.getOccuphyexaclassid());
		model.setIsSynchro(am.getIsSynchro());
		UserDTO user = (UserDTO) session.get("username");
		@SuppressWarnings("unused")
		List<WebResrelAtionship> webResource = user.getWebResource();

		if (webResource != null) {
			for (int i = 0; i < webResource.size(); i++) {
				if (webResource.get(i).getRes_code().equals("RS004")) {
					model.setWebResource(webResource.get(i).getDatavalue());
					break;
				}
			}
		}

		//根据用户资源判断是否展示折扣率跟折扣后金额
		List<WebResrelAtionship> wrs = user.getWebResource();
		if (wrs != null && wrs.size() > 0) {
			for (WebResrelAtionship wr : wrs) {
				if ("RS049".equals(wr.getRes_code())) {
					this.model.setIs_show_discount(1);
					break;
				}
			}
		}
		this.defaultapp = (SystemType) session.get("defaultapp");
		this.setIntss(this.defaultapp.getComid());

		return SUCCESS;
	}

	/**
	 * @throws
	 * @Title: getsetChargingItem
	 * @Description: TODO(体检套餐获取已选择项目196)
	 * @param: @return
	 * @param: @throws WebException
	 * @param: @throws SQLException
	 * @return: String
	 */
	public String getsetChargingItem() throws WebException, SQLException {
		UserDTO user = (UserDTO) session.get("username");
		List<SetChargingItemDTO> li;
		if (model.getId() > 0) {
			li = this.examSetService.getsetChargingItem(model.getId(), user.getCenter_num());
		} else {
			li = new ArrayList<SetChargingItemDTO>();
		}
		this.outJsonResult(li);
		return NONE;
	}

	/**
	 * @throws
	 * @Title: updateEaxmSet
	 * @Description: TODO(体检套餐修改197)
	 * @param: @return
	 * @param: @throws WebException
	 * @param: @throws SQLException
	 * @return: String
	 */
	public String updateEaxmSet() throws WebException, SQLException {
		//this.examSetService.deleteSetChargingItem(model.getId());// 删除已选择的项目--然后修改重新添加
		UserDTO user = (UserDTO) session.get("username");
		ExamSet exam = this.examSetService.findEaxmSet(model.getId());// 查找实体类
		exam.setId(model.getId());
		exam.setSet_name(model.getSet_name());// 套餐名称
		exam.setSet_pinyin(model.getSet_pinyin());// 套餐拼音
		exam.setSex(model.getSex());// 适应人群 男，女
		exam.setSet_num(model.getSet_num());// 编码
		exam.setUpdater(user.getUserid());// 修改用户
		exam.setUpdate_time(DateTimeUtil.parse());// 修改时间
		exam.setIs_Active("Y");
		exam.setSet_discount(model.getSet_discount());// 总折扣
		exam.setPrice(model.getPrice());// 原价
		exam.setSet_amount(model.getSet_amount());// 总折后价格
		exam.setExam_set_type(model.getExam_set_type());// 套餐类别
		exam.setSet_seq(model.getSet_seq());
		exam.setHazardfactorsid(model.getHazardfactorsid());
		exam.setOccuphyexaclassid(model.getOccuphyexaclassid());


		this.defaultapp = (SystemType) session.get("defaultapp");
		exam.setApp_type(Integer.parseInt(defaultapp.getComid()));

		this.examSetService.updateEaxmSetAll(exam, this.li);


		boolean result = this.examSetService.queryFromIdIsSynchro(model.getId() + "");

		if (result) {
			StringBuffer buffId = new StringBuffer();
			String list = this.li.replace("}{", "},{");
			list = "[" + list + "]";
			JSONArray liArry = JSONArray.fromObject(list);
			for (int K = 0; K < liArry.size(); K++) {
				JSONObject job = liArry.getJSONObject(K);
				buffId.append(job.get("id").toString() + ",");
			}
			String idAdd = buffId.toString().substring(0, buffId.toString().length() - 1);
			//体检套餐中的收费项目
			this.webSynchroService.updateWebSynchro(idAdd, '1');

			this.webSynchroService.updateWebSynchro(model.getId() + "", '2');
		}


		// 修改收费项目西项目
		//this.examSetService.updateEaxmSetS(exam, this.li);
		//关联因素关系保存
		if(!StringUtil.isEmpty(model.getHazard_list())){
			model.setSet_id(exam.getId());
			this.setOccuhazardfactorsService.saveExamSetHazard(model,user);
		}
		this.message = "修改成功";
		this.outJsonStrResult(this.message);

		SysLog sl = new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid() + "");
		sl.setOper_type("2");// 0 查询，1插入 2修改 3 删除 4导入 5，登录 9 其他
		sl.setXtgnid("");// 可不填写
		sl.setXtgnid2("197");// 子功能id 必须填写
		sl.setExplain("修改体检套餐");// 操作说明
		syslogService.saveSysLog(sl);

		return NONE;
	}

	/**
	 * 启用/停用
	 *
	 * @throws
	 * @Title: getstartorblock
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param: @throws WebException
	 * @param: @throws Exception
	 * @return: void
	 */
	public void getstartorblock() throws WebException, Exception {
		Language.setLanguage(this.language);
		UserDTO user = (UserDTO) session.get("username");
		try {
			ExamSet am = this.examSetService.findExamSetId(model.getId());
			if (am.getIs_Active().equals("Y")) {
				am.setIs_Active("N");
				am.setUpdater(user.getUserid());
				am.setUpdate_time(DateTimeUtil.parse());
			} else if (am.getIs_Active().equals("N")) {
				am.setIs_Active("Y");
				am.setUpdater(user.getUserid());
				am.setUpdate_time(DateTimeUtil.parse());
			}

			this.examSetService.updateEaxmSet(am);
			this.message = "success";
		} catch (Exception e) {
			this.message = "error";
			e.printStackTrace();
		}
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().print(new Gson().toJson(this.message));

		SysLog sl = new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid() + "");
		sl.setOper_type("9");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("1014");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
	}

	/**
	 * @throws
	 * @Title: setweixinadddo
	 * @Description: 执行微信同步操作 889
	 * @param: @throws WebException
	 * @param: @throws Exception
	 * @return: void
	 */
	public void setweixinadddo() throws WebException {
		if (StringUtil.isEmpty(this.model.getIds())) {
			this.message = "error-无效套餐";
		} else {
			String[] ids = this.model.getIds().split(",");
			for (int i = 0; i < ids.length; i++) {
				this.webSynchroService.updateWebSynchro(ids[i] + "", '2');
			}
			//修改是否同步
			this.examSetService.updateIsSynchro(this.model.getIds());

			this.message = "ok-操作成功";
			UserDTO user = (UserDTO) session.get("username");
			SysLog sl = new SysLog();
			sl.setCenter_num(user.getCenter_num());
			sl.setUserid(user.getUserid() + "");
			sl.setOper_type("9");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
			sl.setXtgnid("");//可不填写
			sl.setXtgnid2("889");//子功能id 必须填写
			sl.setExplain("");//操作说明
			syslogService.saveSysLog(sl);
		}
		this.outJsonStrResult(this.message);
	}
	//--------------------------------职业病套餐页面-----------------------------

	/**
	 * 职业病套餐页面zyb373
	 *
	 * @throws
	 * @Title: getZybExamSetPage
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param: @return
	 * @param: @throws WebException
	 * @return: String
	 */
	public String getZybExamSetPage() throws WebException {
		UserDTO user = (UserDTO) session.get("username");
		//根据用户资源判断是否展示折扣率跟折扣后金额
		List<WebResrelAtionship> wrs = user.getWebResource();
		if (wrs != null && wrs.size() > 0) {
			for (WebResrelAtionship wr : wrs) {
				if ("RS049".equals(wr.getRes_code())) {
					this.model.setIs_show_discount(1);
					break;
				}
			}
		}
		return SUCCESS;
	}

	/**
	 * 职业危害因素树zyb374
	 *
	 * @throws
	 * @Title: getZybExamSetTree
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param: @return
	 * @param: @throws WebException
	 * @return: String
	 */
	public String getZybExamSetTree() throws WebException {
		List<ZybOccuhazardClassDTO> li = this.examSetService.getOccuhazardfactorsTree(this.name);
		this.outJsonResult(li);
		return NONE;
	}

	/*zyb375
	 * 职业病套餐列表
	 */
	public String getZybExamSetList() throws WebException {
		UserDTO user = (UserDTO) session.get("username");
		PageReturnDTO dto = examSetService.queryZybExamSet(model, page, rows, this.defaultapp.getComid(), user.getCenter_num());
		this.outJsonResult(dto);
		return NONE;
	}

	/**
	 * @throws
	 * @Title: getExamSetTreeSelect
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param: @return
	 * @param: @throws Exception
	 * @return: String
	 */
	public String getExamSetTreeSelect() throws Exception {
		this.model.setSex(java.net.URLDecoder.decode(this.model.getSex(), "UTF-8"));
		return SUCCESS;
	}

	/**
	 * @throws
	 * @Title: querySelectExamSettree
	 * @Description: TODO(体检套餐列表1255)
	 * @param: @return
	 * @return: String
	 */
	public String querySelectExamSettree() throws WebException, SQLException {
		UserDTO user = (UserDTO) session.get("username");
		ExamSetDTO exam = new ExamSetDTO();
		exam.setSet_name(model.getSet_name());
		exam.setSex(model.getSex());
		exam.setExam_type(model.getExam_type());
		exam.setSettreeid(model.getSettreeid());
		PageReturnDTO dto = this.examSetService.querySelectExamSettree(exam, user.getCenter_num(), page, rows);
		this.outJsonResult(dto);
		return NONE;
	}

	/**
	 * 职业病自选套餐因素关系维护页面2543
	 *
	 * @return
	 * @throws WebException
	 * @throws SQLException
	 */
	public String zybExamSetHazardPage() throws WebException, SQLException {
		ExamSet am = this.examSetService.findExamSetId(model.getId());
		model.setId(am.getId());// id
		model.setSet_name(am.getSet_name());// 名称
		model.setSet_pinyin(am.getSet_pinyin());// 拼音
		model.setSex(am.getSex());// 使用人群性别
		model.setSet_num(am.getSet_num());
		model.setSet_amount(am.getSet_amount());// 折后金额
		model.setSet_discount(am.getSet_discount());// 总折扣
		model.setPrice(am.getPrice());// 原价
		model.setExam_set_type(am.getExam_set_type());
		model.setSet_seq(am.getSet_seq());
		model.setHazardfactorsid(am.getHazardfactorsid());
		model.setOccuphyexaclassid(am.getOccuphyexaclassid());
		model.setIsSynchro(am.getIsSynchro());
		UserDTO user = (UserDTO) session.get("username");
		SysLog sl = new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid() + "");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("2543");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return SUCCESS;
	}

	/**
	 * 套餐因素关系列表	2544
	 *
	 * @return
	 * @throws WebException
	 * @throws SQLException
	 */
	public String getExamSetHazardList() throws WebException, SQLException {
		List<SetOccuhazardfactorsDTO> li = this.setOccuhazardfactorsService.getSetOccuhazardfactorsDTOList(model.getSet_id());
		this.outJsonResult(li);
		return NONE;
	}

	/**
	 * 自选套餐和因素关系保存 2545
	 *
	 * @return
	 * @throws WebException
	 * @throws SQLException
	 */
	public String saveExamSetHazard() throws WebException, SQLException {
		UserDTO user = (UserDTO) session.get("username");
		String msg = this.setOccuhazardfactorsService.saveExamSetHazard(model, user);
		this.outJsonStrResult(msg);
		return NONE;
	}

	/**
	 * 2546职业病自选套餐，新增页面
	 *
	 * @throws
	 * @Title: addExamSet
	 * @Description: TODO(体检套餐添加界面193)
	 * @param: @return
	 * @return: String
	 */
	public String zybAddExamSetZXPage() {
		UserDTO user = (UserDTO) session.get("username");
		model.setSet_num(batchService.GetCreateID("set_num", user.getCenter_num()));
		if(model.getId()>0){
			ExamSet am = this.examSetService.findExamSetId(model.getId());
			model.setId(am.getId());// id
			model.setSet_name(am.getSet_name());// 名称
			model.setSet_pinyin(am.getSet_pinyin());// 拼音
			model.setSex(am.getSex());// 使用人群性别
			model.setSet_num(am.getSet_num());
			model.setSet_amount(am.getSet_amount());// 折后金额
			model.setSet_discount(am.getSet_discount());// 总折扣
			model.setPrice(am.getPrice());// 原价
			model.setExam_set_type(am.getExam_set_type());
			model.setSet_seq(am.getSet_seq());
			model.setHazardfactorsid(am.getHazardfactorsid());
			model.setOccuphyexaclassid(am.getOccuphyexaclassid());
			model.setIsSynchro(am.getIsSynchro());
		}
		@SuppressWarnings("unused")
		List<WebResrelAtionship> webResource = user.getWebResource();

		if (webResource != null) {
			for (int i = 0; i < webResource.size(); i++) {
				if (webResource.get(i).getRes_code().equals("RS004")) {
					model.setWebResource(webResource.get(i).getDatavalue());
					break;
				}
			}
		}

		//根据用户资源判断是否展示折扣率跟折扣后金额
		List<WebResrelAtionship> wrs = user.getWebResource();
		if (wrs != null && wrs.size() > 0) {
			for (WebResrelAtionship wr : wrs) {
				if ("RS049".equals(wr.getRes_code())) {
					this.model.setIs_show_discount(1);
					break;
				}
			}
		}
		this.defaultapp = (SystemType) session.get("defaultapp");
		this.setIntss(this.defaultapp.getComid());
		return SUCCESS;
	}
}