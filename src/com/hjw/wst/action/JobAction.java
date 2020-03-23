package com.hjw.wst.action;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONSerializer;

import com.hjw.config.Logincheck;
import com.hjw.wst.DTO.GridListDTO;
import com.hjw.wst.DTO.LogItem;
import com.hjw.wst.DTO.MenuDTO;
import com.hjw.wst.model.JobModel;
import com.hjw.wst.service.MenuGnService;
import com.opensymphony.xwork2.ModelDriven;
import com.synjones.framework.exception.WebException;
import com.synjones.framework.persistence.QueryManager;
import com.synjones.framework.web.action.BaseAction;

public class JobAction extends BaseAction implements ModelDriven{
	private String urlEncoding = "utf-8";
	private static final long serialVersionUID = 1L;
	private JobModel model = new JobModel();
	private QueryManager qm;
	private String ids;
	private MenuGnService menuGnService;
	private Logincheck logincheck;
	private String gwstr;
	 private int rp=15;  //每页显示的条数
	 private int page=1; //当前页
	  private int rows=15; //easyui每页显示条数
	  private LogItem logItem = new LogItem();
		
		
		 public LogItem getLogItem() {
			return logItem;
		}
		public void setLogItem(LogItem logItem) {
			this.logItem = logItem;
		}
	public String getGwstr() {
		return gwstr;
	}
	public void setGwstr(String gwstr) {
		this.gwstr = gwstr;
	}
	public Logincheck getLogincheck() {
		return logincheck;
	}
	public void setLogincheck(Logincheck logincheck) {
		this.logincheck = logincheck;
	}
	public String getIds() {
		return ids;
	}
	public void setIds(String ids) {
		this.ids = ids;
	}
	public void setQueryManager(QueryManager qm) {
		this.qm = qm;
	}
	public Object getModel() {
		return model;
	}
	
	
	public MenuGnService getMenuGnService() {
		return menuGnService;
	}
	public void setMenuGnService(MenuGnService menuGnService) {
		this.menuGnService = menuGnService;
	}

	
	public String jobmainheader() throws WebException{
		List<GridListDTO> strList=new ArrayList<GridListDTO>();
		GridListDTO ls=new GridListDTO();
//		ls.setTitle("岗位编号");
//		ls.setField("id");
//		ls.setWidth(400);
//		ls.setSortable(true);
//		ls.setAlign("left");
//		strList.add(ls);
//		
//		ls=new GridListDTO();
		ls.setTitle("岗位名称");
		ls.setField("gwname");
		ls.setWidth(400);
		ls.setSortable(true);
		ls.setAlign("center");
		strList.add(ls);
		
		ls=new GridListDTO();
		ls.setTitle("所属部门");
		ls.setField("gwdept");
		ls.setWidth(400);
		ls.setSortable(true);
		ls.setAlign("center");
		strList.add(ls);
		
		ls = new GridListDTO();
		ls.setTitle("授权角色");
		ls.setField("publisher");
		ls.setWidth(100);
		ls.setSortable(true);
		ls.setAlign("center");
		strList.add(ls);

		
		ls = new GridListDTO();
		ls.setTitle("修改");
		ls.setField("publishdate");
		ls.setWidth(25);
		ls.setSortable(true);
		ls.setAlign("center");
		strList.add(ls);
		
		ls = new GridListDTO();
		ls.setTitle("删除");
		ls.setField("publishdate");
		ls.setWidth(25);
		ls.setSortable(true);
		ls.setAlign("center");
		strList.add(ls);
		String jsonString = JSONSerializer.toJSON(strList).toString();
		this.outJsonStrResult(jsonString);
		return NONE;
	}
	
	@SuppressWarnings({ "rawtypes", "unused", "unchecked" })
	private List getChild(String[] bms, String id) {
		List ls = new ArrayList();
		
		for(int i=0;i<bms.length;i++){
			MenuDTO me = new MenuDTO();
			String[] bminfo = bms[i].split("#");
			if(id.equals(bminfo[2])){
				me.setId(bminfo[0]);
				me.setText(bminfo[1]);
				me.setValue(bminfo[3]);
				me.setComplete(true);
				me.setCheckstate(0);
				me.setIsexpand(false);
				me.setShowcheck(false);
				List ls1 = getChild(bms,bminfo[0]);
				if(ls1.size()>0){
					me.setChildNodes(ls1);
					me.setHasChildren(true);
				}else{
					me.setChildNodes(null);
					me.setHasChildren(false);
				}
				ls.add(me);
			}
		}
		return ls;
	}


	/**
	 * 用户授权商户
	 * @return
	 */
//	public String getMerccdept(){
//		Branch root=new Branch();
//		Branch node=new Branch();
//		List<Branch> treeList=new ArrayList<Branch>();
//		List<Branch> parentnodes=new ArrayList<Branch>();
//		List<Branch> list=this.trjnSearchService.getAllBranch();
//		if(list!=null&&list.size()>0){
//			root=list.get(0);
//			for(int i=0;i<list.size();i++){
//				node=list.get(i);
//				if(node.getSudeptcode().equals(root.getDeptcode())){
//					parentnodes.add(node);
//					root
//				}
//				
//			}
//		}
//		return null;
//		
//	}
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
	
}

